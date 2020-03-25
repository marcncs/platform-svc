import java.io.File;
import java.io.IOException;
import java.net.URL;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.winsafe.aws.util.S3Util;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;

public class TestS3 {
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
		s3.setRegion(Region.getRegion(Regions.US_EAST_1)); // 此处根据自己的 s3 地区位置改变
	}

	public static void main(String[] args) throws Exception {
		S3Util.uploadToS3(new File("d:/test.pdf"), "/upload/test", "test.pdf");
		/*File uploadFile = new File("d:/test.pdf");
        String uploadKey = "test";
        String url = uploadToS3(uploadFile,uploadKey);
        System.out.println();*/
	}

	public static String uploadToS3(File tempFile, String remoteFileName) throws IOException {
		try {
			String bucketPath = bucketName + "/upload/test";
			s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile));
			GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
			URL url = s3.generatePresignedUrl(urlRequest);
			return url.toString();
		} catch (AmazonServiceException ase) {
			WfLogger.error("", ase);
		} catch (AmazonClientException ace) {
			WfLogger.error("", ace);
		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return null;
	}
}
