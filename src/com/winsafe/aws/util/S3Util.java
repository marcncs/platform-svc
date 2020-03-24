package com.winsafe.aws.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.catalina.tribes.util.UUIDGenerator;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;


public class S3Util {
	private static AmazonS3 s3;
	private static String AWS_ACCESS_KEY = ""; // 【你的 access_key】
	private static String AWS_SECRET_KEY = ""; // 【你的 aws_secret_key】

	private static String bucketName = ""; // 【你 bucket 的名字】 # 首先需要保证 s3 上已经存在该存储桶

	static {
		try {
			AWS_ACCESS_KEY = SysConfig.getSysConfig().getProperty("AWS_ACCESS_KEY");
			AWS_SECRET_KEY = SysConfig.getSysConfig().getProperty("AWS_SECRET_KEY");
			bucketName = SysConfig.getSysConfig().getProperty("aws.s3.bucket.name");
		} catch (IOException e) {
			WfLogger.error("", e);
		}
		s3 = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
		Region region = Region.getRegion(Regions.fromName("cn-north-1"));
		s3.setRegion(region); // 此处根据自己的 s3 地区位置改变
		String serviceEndpoint = region.getServiceEndpoint(ServiceAbbreviations.S3);
		s3.setEndpoint(serviceEndpoint);
	}

	public static void uploadToS3(File tempFile, String remoteFilepath, String remoteFileName) throws Exception {
		try {
			String bucketPath = bucketName + remoteFilepath;
			s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile));
			/*
			 * GeneratePresignedUrlRequest urlRequest = new
			 * GeneratePresignedUrlRequest(bucketName, remoteFileName); URL url =
			 * s3.generatePresignedUrl(urlRequest); return url.toString();
			 */
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		}
	}
	
	public static void uploadToS3(InputStream is, String remoteFilepath, String remoteFileName) throws Exception {
		File file = createTempFile(is);
		try {
			String bucketPath = bucketName + remoteFilepath;
			s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, file));
			/*
			 * GeneratePresignedUrlRequest urlRequest = new
			 * GeneratePresignedUrlRequest(bucketName, remoteFileName); URL url =
			 * s3.generatePresignedUrl(urlRequest); return url.toString();
			 */
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		} finally {
			file.deleteOnExit();
		}
	}

	private static File createTempFile(InputStream is) throws Exception {
		File file = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
		try (OutputStream outputStream = new FileOutputStream(file);){
			saveFile(is, outputStream);
		} finally {
			is.close();
		}
		return file;
	}

	private static void saveFile(InputStream inputStream, OutputStream outputStream) throws Exception {
		int bytesWritten = 0;
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, bytesWritten, byteCount);
		}
		outputStream.flush();	
	}

	public static void downloadFromS3ToFile(File destFile, String remoteFileName) throws Exception {
		String bucketPath = bucketName + SysConfig.getSysConfig().getProperty("upload.path");
		try (OutputStream outputStream = new FileOutputStream(destFile);
				InputStream is = s3.getObject(new GetObjectRequest(bucketPath, remoteFileName)).getObjectContent();) {
			saveFile(is, outputStream);
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		}
	}
	
	public static File downloadFile(String remoteFilePath) throws Exception {
		String suffix = remoteFilePath.substring(remoteFilePath.lastIndexOf(".")+1,remoteFilePath.length());
		File file = File.createTempFile(UUID.randomUUID().toString(), suffix);
		String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/")+1,remoteFilePath.length());
		String filePath = remoteFilePath.replace("/"+fileName, "");
		String bucketPath = bucketName + filePath;
		try (OutputStream outputStream = new FileOutputStream(file);
				InputStream is = s3.getObject(new GetObjectRequest(bucketPath, fileName)).getObjectContent();) {
			saveFile(is, outputStream);
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		}
		return file;
	}
	
	public static void getFileFromS3(OutputStream os, String remoteFilePath, String remoteFileName) throws Exception {
		String bucketPath = bucketName + remoteFilePath;
		try (InputStream is = s3.getObject(new GetObjectRequest(bucketPath, remoteFileName)).getObjectContent();) {
			saveFile(is, os);
		} catch (Exception e) {
			WfLogger.error("", e);
			throw e;
		} finally {
			os.close();
		}
	}
}
