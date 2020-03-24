import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.digest.DigestUtils;

import com.winsafe.drp.util.HttpUtils;
import com.winsafe.hbm.util.Encrypt;

public class Upload {
	public static void main(String[] args) throws Exception {
		String pwd="ir2g6n4+nY8tZa6vBowAYA==";
		System.out.println(Encrypt.getSecret("Flzx3000c,", 3));
		System.out.println(Encrypt.getSecret(pwd, 2));
		/*String pwd = DigestUtils.md5Hex("Flzx3000c,");
		String user = "rtci";
		String filename = "cssi_td_sale_list0.txt";
		String url= "https://bayer-test.seepln.com:8080/rtcidata/"+user+"/"+pwd+"/"+filename;
		File file = new File("C:\\Users\\ryan.xi\\Desktop\\RTCI\\rtci_20191118.txt");
		StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));){
			String s = "";
	        while ((s =br.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
	            sb.append(s + "\r\n");//将读取的字符串添加换行符后累加存放在缓存中
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        String data = sb.toString();
        System.out.println(data);
		
		System.out.println("请求URL："+url);
		HttpUtils.uploadToCSSI(url, data);*/
	}
}
