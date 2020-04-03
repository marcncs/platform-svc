FROM 543378705537.dkr.ecr.cn-north-1.amazonaws.com.cn/rtci-base:latest

COPY output/msal4jsample.war /tomcat/apache-tomcat/webapps
COPY output/RTCIService.war /tomcat/apache-tomcat/webapps

## WORKDIR /tomcat/apache-tomcat/webapps

CMD ["/bin/startup.sh"]
