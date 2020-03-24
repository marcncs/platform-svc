package com.winsafe.drp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

public class CheckPsdStrong {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String psd = "aaa1aa";
		System.out.println(CheckPsdStrong.getPsdStrongLevel(psd));
	}
	
	public static Integer getPsdStrongLevel(String password){
		Integer strongLevel = 0;
		//长度大于6
		if(password.length() >= 6){
			strongLevel += 1;
		} 
		//存在数字
		String NumRegex = ("\\d");   
		Boolean existNumber = matchResult(NumRegex,password);
		if(existNumber == true){
		    strongLevel += 1;
		}
		//存在字母
		String strRegex = ("[a-zA-Z]");
		Boolean existStr = matchResult(strRegex,password);
		if(existStr == true){
		    strongLevel += 1;
		}
		return strongLevel;
	}
	
	private static Boolean matchResult(String regex,String str){
		Pattern pattern = Pattern.compile(regex);
		for (int i = 0; i < str.length(); i++) {
			Matcher matcher = pattern.matcher(str.substring(i, i+1));
			if(matcher.matches() == true){
				return true;
			}
		}
		return false;
	}

}
