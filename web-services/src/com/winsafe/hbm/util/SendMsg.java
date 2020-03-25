package com.winsafe.hbm.util;

import com.winsafe.hbm.util.DateUtil;
import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.util.FileConstant;

public class SendMsg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void sendMsg(Msg msg, int userid) throws Exception{
			AppMsg am = new AppMsg();
			String qurl = "you are http url" 
				   + FileConstant.group_msguserid + "&password=" + java.net.URLEncoder.encode(FileConstant.group_msgpassword,"UTF-8")
				   + "&destnumbers=" + am.filternums(msg.getMobileno())
				   + "&msg=" + java.net.URLEncoder.encode(msg.getMsgcontent(), "UTF-8")
				   + "&sendtime=" + ""; 
			org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
		    org.dom4j.Document doc = reader.read(new java.net.URL(qurl));
		    //String xml=  new String(doc.asXML().getBytes(),"UTF-8");
		    //System.out.println(xml);
		    if ("0".endsWith(doc.valueOf("/root/@return"))) {
		    	msg.setIsaudit(1); 
		    	msg.setAuditid(userid);
		    	msg.setAuditdate(DateUtil.getCurrentDate());
		    	msg.setIsdeal(1);		      
		    }else{
		    }
	}

}
