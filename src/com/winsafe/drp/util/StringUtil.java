package com.winsafe.drp.util;

import java.io.UnsupportedEncodingException;

public class StringUtil
{
  public static void main(String[] args)    throws Exception
  {
    String s = "测试产品";
    String temp = fillBack(s, 32, "1");
    System.out.println(temp);
  }

  public static String fillBefore(String oldStr, int length, String fillStr)
    throws Exception
  {
    return fillString(oldStr, length, fillStr, true);
  }

  public static String fillBack(String oldStr, int length, String fillStr)
    throws Exception
  {
    return fillString(oldStr, length, fillStr, false);
  }

  private static String fillString(String oldStr, int length, String fillStr, boolean before)
    throws UnsupportedEncodingException
  {
    return fillString(oldStr, length, fillStr, before, "utf-8");
  }

  private static String fillString(String oldStr, int length, String fillStr, boolean before, String charSet)
    throws UnsupportedEncodingException
  {
    String str = oldStr == null ? "" : oldStr;
    if (str.getBytes(charSet).length > length)
    {
    	str = str.substring(0, str.length()-1);
    	return fillString(str, length, fillStr, before, "utf-8");
    }
    while (str.getBytes(charSet).length < length){
    	 if (before)
         {
           str = fillStr + str;
         }
         else
         {
           str = str + fillStr;
         }
    }

    return str;
  }
}