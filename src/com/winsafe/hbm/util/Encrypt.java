package com.winsafe.hbm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

//import com.eitop.platform.tools.encrypt.xStrEncrypt;

public class Encrypt {

	public static String getSecret(String str, int num) {
		String pStr = "";
		String pStr1 = "";
		String pStr2 = "";
		String pStr3 = "";
		String tmpStr = "1234567890abcdef";
		String newString = str + tmpStr;
		String key = "eps&$*#";
		try {
			// pStr1 = xStrEncrypt.StrEncrypt(newString,key);
			// 使用md5加密
			
			// pStr2 = xStrEncrypt.StrDecrypt(str,key);
			// pStr3 = xStrEncrypt.StrEncrypt(newString,key);
			
			if (num == 1) { 
				pStr1 = DigestUtils.md5Hex(str);
				pStr = pStr1.toUpperCase();
			} else if (num == 2) {
				pStr2 = CustomAES.decode(str);
//				pStr = pStr2.substring(0, (pStr2.length() - 16));
				pStr = pStr2;
			} else if (num == 3) {
				pStr3 = CustomAES.encode(str);
				pStr = pStr3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pStr;
	}

	/**
	 * 生成MD5码,测试修改密码用
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		System.out.println(Encrypt.getSecret("yFZdaYCR25gr9MU2cHuavQ==", 2));//测试数据库密码
//		System.out.println(Encrypt.getSecret("KtsaBrq8m6T1GTgSZwf5gg==", 2));
//		System.out.println(Encrypt.getSecret("9/n1DT9zvRO3g2hK7/LbeQ==", 2));
		//System.out.println(Encrypt.getSecret("yFZdaYCR25gr9MU2cHuavQ==", 2));
//		System.out.println(Encrypt.getSecret("1234+-*/asdf", 3));
//		System.out.println(Encrypt.getSecret("LB_ThXy_JdsEC5TEIn", 3));
//		System.out.println(Encrypt.getSecret("z+CzigqDvw+wlaHWZVt9JzuNpgm6hNP9SeB0BSRHflo=", 2));
//		File file = new File("D:/mobile.txt");
//		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//		for (String line = br.readLine(); line != null; line = br.readLine()) {
//			System.out.println(Encrypt.getSecret(line, 2)); 
//		}
		//System.out.println(Encrypt.getSecret("9/n1DT9zvRO3g2hK7/LbeQ==", 2));
		//System.out.println(Encrypt.getSecret("iAPjuZ7wQQiP480+9zCOig==", 2));
		//System.out.println(Encrypt.getSecret("dGfgY8/VtpS8Znm1l6BU8ESrNpOGf6krM3qV8Dv5+SE=", 2));
		//System.out.println(Encrypt.getSecret("9v3Pm8445J1ye2MbPmuv3Pm8==", 2));
//		System.out.println(Encrypt.getSecret("202068324335", 3));
//		System.out.println(new String(Base64.encodeBase64("gC+2L49fIjyq/r5U+YrnYQ".getBytes())));
		System.out.println(Encrypt.getSecret("GR4XWNvYioWOBr+583hEEg==", 2));//测试数据库密码
	}

}
