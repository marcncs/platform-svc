package com.github.speedwing.log4j.cloudwatch.appender;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.*;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class CloudwatchAppender extends AppenderSkeleton {

    private final Boolean DEBUG_MODE = System.getProperty("log4j.debug") != null;

    /**
     * Used to make sure that on close() our daemon thread isn't also trying to sendMessage()s
     */
    private Object sendMessagesLock = new Object();

    /**
     * The queue used to buffer log entries
     */
    private LinkedBlockingQueue<LoggingEvent> loggingEventsQueue;

    /**
     * the AWS Cloudwatch Logs API client
     */
    private AWSLogs awsLogsClient;

    private AtomicReference<String> lastSequenceToken = new AtomicReference<>();

    /**
     * The AWS Cloudwatch Log group name
     */
    private String logGroupName;

    /**
     * The AWS Cloudwatch Log stream name
     */
    private String logStreamName;

    /**
     * The AWS region
     */
    private Optional<String> region = Optional.empty();

    /**
     * The queue / buffer size
     */
    private int queueLength = 1024;

    /**
     * The maximum number of log entries to send in one go to the AWS Cloudwatch Log service
     */
    private int messagesBatchSize = 128;

    /**
     * True if the cloudwatch appender resources have been correctly initialised
     */
    private AtomicBoolean cloudwatchAppenderInitialised = new AtomicBoolean(false);

    /**
     * Used to keep the daemon thread alive.
     */
    private AtomicBoolean keepDaemonActive = new AtomicBoolean(false);

    public CloudwatchAppender() {
        super();
    }

    public CloudwatchAppender(Layout layout, String logGroupName, String logStreamName, Optional<String> region) {
        super();
        this.setLayout(layout);
        this.setLogGroupName(logGroupName);
        this.setLogStreamName(logStreamName);
        this.setRegion(region);
        this.activateOptions();
    }


    public void setLogGroupName(String logGroupName) {
        this.logGroupName = logGroupName;
    }

    public void setLogStreamName(String logStreamName) {
        this.logStreamName = logStreamName;
    }

    public void setRegion(Optional<String> region) {
        this.region = region;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public void setMessagesBatchSize(int messagesBatchSize) {
        this.messagesBatchSize = messagesBatchSize;
    }

    @Override
    protected void append(LoggingEvent event) {
        if (cloudwatchAppenderInitialised.get()) {
            loggingEventsQueue.offer(event);
        } else {
            // just do nothing
        }
    }

    private void sendMessages() {
        synchronized (sendMessagesLock) {
            LoggingEvent polledLoggingEvent;

            List<LoggingEvent> loggingEvents = new ArrayList<>();

            try {

                while ((polledLoggingEvent = loggingEventsQueue.poll()) != null && loggingEvents.size() <= messagesBatchSize) {
                    loggingEvents.add(polledLoggingEvent);
                }
           
                List<InputLogEvent> inputLogEvents = loggingEvents.stream()
                        .map(loggingEvent -> new InputLogEvent().withTimestamp(loggingEvent.getTimeStamp()).withMessage(layout.format(loggingEvent)))
                        .sorted(comparing(InputLogEvent::getTimestamp))
                        .collect(toList());

                if (!inputLogEvents.isEmpty()) {

                    PutLogEventsRequest putLogEventsRequest = new PutLogEventsRequest(
                            logGroupName,
                            logStreamName,
                            inputLogEvents);

                    try {
                        putLogEventsRequest.setSequenceToken(lastSequenceToken.get());
                        PutLogEventsResult result = awsLogsClient.putLogEvents(putLogEventsRequest);
                        lastSequenceToken.set(result.getNextSequenceToken());
                    } catch (DataAlreadyAcceptedException dataAlreadyAcceptedExcepted) {
                        putLogEventsRequest.setSequenceToken(dataAlreadyAcceptedExcepted.getExpectedSequenceToken());
                        PutLogEventsResult result = awsLogsClient.putLogEvents(putLogEventsRequest);
                        lastSequenceToken.set(result.getNextSequenceToken());
                        if (DEBUG_MODE) {
                            dataAlreadyAcceptedExcepted.printStackTrace();
                        }
                    } catch (InvalidSequenceTokenException invalidSequenceTokenException) {
                        putLogEventsRequest.setSequenceToken(invalidSequenceTokenException.getExpectedSequenceToken());
                        PutLogEventsResult result = awsLogsClient.putLogEvents(putLogEventsRequest);
                        lastSequenceToken.set(result.getNextSequenceToken());
                        if (DEBUG_MODE) {
                            invalidSequenceTokenException.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                if (DEBUG_MODE) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() {
        while (loggingEventsQueue != null && !loggingEventsQueue.isEmpty()) {
            this.sendMessages();
        }
        keepDaemonActive.set(false);
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    private Regions getAwsRegion() {

        if (this.region.isPresent()) {
            return Regions.fromName(this.region.get());
        } else {
            // Try first for an environment variable
            String configuredDefaultRegion = System.getenv("AWS_DEFAULT_REGION");
            if (configuredDefaultRegion != null) {
                return Regions.fromName(configuredDefaultRegion);
            }

            // Try and get our current region...
            Region region = Regions.getCurrentRegion();
            if (region != null) {
                return Regions.fromName(region.getName());
            }
        }

        // Default to us-east-1
        return Regions.US_EAST_1;
    }

    @Override
    public void activateOptions() {
        super.activateOptions();
        if (isBlank(logGroupName) || isBlank(logStreamName)) {
            Logger.getRootLogger().error("Could not initialise CloudwatchAppender because either or both LogGroupName(" + logGroupName + ") and LogStreamName(" + logStreamName + ") are null or empty");
            this.close();
        } else {
            this.awsLogsClient = AWSLogsClientBuilder
                    .standard()
                    .withRegion(this.getAwsRegion())
                    .build();
            loggingEventsQueue = new LinkedBlockingQueue<>(queueLength);
            try {
                initializeCloudwatchResources();
                keepDaemonActive.set(true);
                initCloudwatchDaemon();
                cloudwatchAppenderInitialised.set(true);
            } catch (Exception e) {
                Logger.getRootLogger().error("Could not initialise Cloudwatch Logs for LogGroupName: " + logGroupName + " and LogStreamName: " + logStreamName, e);
                if (DEBUG_MODE) {
                    System.err.println("Could not initialise Cloudwatch Logs for LogGroupName: " + logGroupName + " and LogStreamName: " + logStreamName);
                    e.printStackTrace();
                }
            }
        }
    }

    private void initCloudwatchDaemon() {
        Thread t = new Thread(() -> {
            while (keepDaemonActive.get()) {
                try {
                    if (loggingEventsQueue.size() > 0) {
                        sendMessages();
                    }
                    Thread.currentThread().sleep(20L);
                } catch (InterruptedException e) {
                    if (DEBUG_MODE) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName(CloudwatchAppender.class.getSimpleName() + " daemon thread");
        t.setDaemon(true);
        t.start();
    }

    private void initializeCloudwatchResources() {

        DescribeLogGroupsRequest describeLogGroupsRequest = new DescribeLogGroupsRequest();
        describeLogGroupsRequest.setLogGroupNamePrefix(logGroupName);

        Optional<LogGroup> logGroupOptional = awsLogsClient
                .describeLogGroups(describeLogGroupsRequest)
                .getLogGroups()
                .stream()
                .filter(logGroup -> logGroup.getLogGroupName().equals(logGroupName))
                .findFirst();

        if (!logGroupOptional.isPresent()) {
            CreateLogGroupRequest createLogGroupRequest = new CreateLogGroupRequest().withLogGroupName(logGroupName);
            awsLogsClient.createLogGroup(createLogGroupRequest);
        }

        DescribeLogStreamsRequest describeLogStreamsRequest = new DescribeLogStreamsRequest().withLogGroupName(logGroupName).withLogStreamNamePrefix(logStreamName);

        Optional<LogStream> logStreamOptional = awsLogsClient
                .describeLogStreams(describeLogStreamsRequest)
                .getLogStreams()
                .stream()
                .filter(logStream -> logStream.getLogStreamName().equals(logStreamName))
                .findFirst();

        if (!logStreamOptional.isPresent()) {
            Logger.getLogger(this.getClass()).info("About to create LogStream: " + logStreamName + "in LogGroup: " + logGroupName);
            CreateLogStreamRequest createLogStreamRequest = new CreateLogStreamRequest().withLogGroupName(logGroupName).withLogStreamName(logStreamName);
            awsLogsClient.createLogStream(createLogStreamRequest);
        }

    }

    private boolean isBlank(String string) {
        return null == string || string.trim().length() == 0;
    }

}
