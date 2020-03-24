package com.winsafe.drp.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map; 
import java.util.Random;
 

public class VaildDateUtil {
	
	private static Map<String, String> passwordKey = new HashMap<String, String>();
	 
	private static String[] enStr = {"A","B","C","D","E","F","G","H","I","J","K","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	static{
		passwordKey.put("01", "EDRYWP");
		passwordKey.put("02", "DWRYSQ");
		passwordKey.put("03", "OWBKDL");
		passwordKey.put("04", "RGHJSW");
		passwordKey.put("05", "FJGSQL");
		passwordKey.put("06", "WRFGBW");
		passwordKey.put("07", "QCZIRI");
		passwordKey.put("08", "FCSWJS");
		passwordKey.put("09", "BLSRSG");
		passwordKey.put("10", "WKRHLT");
		passwordKey.put("11", "XWEFWI");
		passwordKey.put("12", "IWRTWF");
		passwordKey.put("13", "LQOYHL");
		passwordKey.put("14", "WQZFWK");
		passwordKey.put("15", "SWKLWX");
		passwordKey.put("16", "DCJBEL");
		passwordKey.put("17", "TIQEGV");
		passwordKey.put("18", "JQERVI");
		passwordKey.put("19", "PQCBMW");
		passwordKey.put("20", "WQKZLW");
		passwordKey.put("21", "NWRNMX");
		passwordKey.put("22", "DWTVTU");
		passwordKey.put("23", "JCFVFS");
		passwordKey.put("24", "JNBSWG");
		passwordKey.put("25", "OWLQQP");
		passwordKey.put("26", "LQRHFL");
		passwordKey.put("27", "WFKSAY");
		passwordKey.put("28", "WWDKUZ");
		passwordKey.put("29", "SXOMXZ");
		passwordKey.put("30", "UIMWXF");
		passwordKey.put("31", "ADWNTE");
	}
	
	
	public Date getDateByCode(String code){
		String firstNumber = code.substring(6,12);
		String secondNumber = code.substring(19,24);
		String thirdNumber = code.substring(31,36);
		String fourthNumber = code.substring(43,48);
		String validate = firstNumber + secondNumber + thirdNumber + fourthNumber;
		Date returnDate = Dateutil.StringToDate(validate);
		return returnDate;
	} 
	
	private String createValidDateStr(String dateStr){
		String firstNumberCode = dateStr.substring(0,2);
		String secondNumberCode = dateStr.substring(2,4);
		String thirdNumberCode = dateStr.substring(4,6);
		String fourthNumberCode = dateStr.substring(6,8);
		String firstNumber = passwordKey.get(firstNumberCode);
		String secondNumber = passwordKey.get(secondNumberCode);
		String thirdNumber = passwordKey.get(thirdNumberCode);
		String fourthNumber = passwordKey.get(fourthNumberCode);
		StringBuffer validDate = new StringBuffer();
		validDate.append(getRandomStr());
		validDate.append(firstNumber);
		validDate.append(getRandomStr());
		validDate.append(secondNumber);
		validDate.append(getRandomStr());
		validDate.append(thirdNumber);
		validDate.append(getRandomStr());
		validDate.append(fourthNumber);
		
		return validDate.toString();
	}
	
	public String getRandomStr(){
		Random random = new Random();
		String returnStr = "";
		for (int i = 0; i < 6; i++) {
			String str = enStr[random.nextInt(enStr.length)];
			returnStr += str;
		}
		return returnStr;
	}
	
	public static void main(String[] args) {
	      VaildDateUtil v = new VaildDateUtil();
	      System.out.println(v.createValidDateStr("20121231")); 
	}    
	 
}
