package com.winsafe.hbm.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


public class DataFormat {

  public static double formatTwoPoint(double price){
     DecimalFormat df1 = new DecimalFormat("0.00");
     String strdb = df1.format(price);    
     return Double.parseDouble(strdb);
  }
  
  public static double dataFormat(double price){
	     DecimalFormat df1 = new DecimalFormat("0.00");
	     String strdb = df1.format(price);
	     return Double.parseDouble(strdb);
	  }
  
  public static String dataFormatStr(double price){
	     DecimalFormat df1 = new DecimalFormat("0.00");
	     return df1.format(price);
	  }
  
  public static String douFormatStrInt(double price){
	     return  String.valueOf((int)Math.floor(price));
	 }
  
  public static String currencyFormat(double price){
	     NumberFormat nf = NumberFormat.getCurrencyInstance();
	     return nf.format(price);
	  }
  
  public static double strToDouble(String str){
	  try{
		  return Double.valueOf(str);
	  }catch(Exception e){
		  return 0.00;
	  }
  }
  public static int strToInt(String str){
	  try{
		  return Integer.valueOf(str);
	  }catch(Exception e){
		  return 0;
	  }
  }
  
  
  
  
  public static double countPrice(double quantity, double unitprice, double discount, double taxrate){
	  double sum = 0.00;
	  sum = quantity * unitprice * discount/100;// * (1+taxrate/100);
	  return formatTwoPoint(sum);
  }
  
  
  public static void main(String[] args){
	//  double sum = countPrice(2, 100.5, 100, 0);
	  //String sum =currencyFormat(126.568);
//	  double sum = formatTwoPoint(89);
//	  System.out.println(sum);	
	  
	  System.out.println(douFormatStrInt(9.90));	
	  
  }
  
  
  public static double ForDight(String strdight,Integer how) { 	  
		double rmb=0d;
		double yuan=0;
		//System.out.println("---dd--"+strdight);
		if(strdight.indexOf(".")>0){
			//System.out.println("--a-"+strdight.length());
			//System.out.println("---"+strdight.substring((strdight.length()-4),3));
		yuan=5-Double.valueOf(strdight.substring((strdight.length()-4),3));
		}else{
		yuan=5-Double.valueOf(strdight.substring(strdight.length()-1,1));
		}

		double dight = Double.valueOf(strdight);
		if(yuan>=0&&yuan<5){
			rmb = dight + yuan;
		}else{
			rmb = Math.round(dight*Math.pow(10,how))/Math.pow(10,how);
		}
		return rmb; 
	}
  

  	public static String getProductCodeDef(String pid){
  		Random rd = new Random();
		String code = rd.nextInt(1000000000)+"000";
  		try{
	  		code = (pid + code).substring(0, 13);
  		}catch ( Exception e ){
  			e.printStackTrace();
  		}
  		
  		return code;
  	}
  	

  	public static String getFormatNums(long id, int length) {
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMinimumIntegerDigits(length);
		df.setGroupingUsed(false);
		return df.format(id);
	}
  
  

}
