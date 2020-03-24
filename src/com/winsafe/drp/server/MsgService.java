package com.winsafe.drp.server;

import java.util.Map;

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.AppMsgTemplate;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.MsgTemplate;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author: jelli 
 * @version:2009-9-17 下午02:08:01 
 * @copyright:www.winsafe.cn
 */
public class MsgService {
	private UsersBean users;
	private AppMsg appmsg = new AppMsg();
	
	private int isAutoSend = 0 ;
	private int msgType;
	private Map<String,String> map;
	
	private String param[];
	private String repaleParam[];
	
	private AppMsgTemplate appmt = new AppMsgTemplate();
	private MsgTemplate template;
	
//	public MsgService(Map<String,String> map, UsersBean users, int msgType){
//		this.map = map;
//		this.users = users;
//		this.msgType = msgType;
//	}
	
	public MsgService(String param[],String repaleParam[], UsersBean users, int msgType){
		this.param = param;
		this.repaleParam = repaleParam;
		this.users = users;
		this.msgType = msgType;
	}
	
	public void addmag(String mobile) throws Exception{		
		deal(mobile);
	}
	
	public void addmag(int isAutoSend, String mobile) throws Exception{		
		this.isAutoSend = isAutoSend;
		deal(mobile);
	}
	
	private void deal(String mobile) throws Exception{
		template = appmt.getMsgTemplateById(msgType);
		
		if ( template.getIsuse() == 0 ){
			return;
		}
		addMsg(mobile, getContent());
	}
	
	private void addMsg(String mobile, String content) throws Exception {		
		Msg msg = new Msg();
		msg.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("msg", 0, "")));
		msg.setMsgsort(1);
		msg.setMobileno(mobile);
		msg.setMsgcontent(content);
		msg.setMakeorganid(users==null?"":users.getMakeorganid());
		msg.setMakedeptid(users==null?0:users.getMakedeptid());
		msg.setMakeid(users==null?0:users.getUserid());
		msg.setMakedate(DateUtil.getCurrentDate());
		msg.setIsdeal(0);
		msg.setIsaudit(0);
		msg.setAuditid(0);
		if ( isAutoSend == 1 ){
			msg.setIsaudit(1);
			msg.setAuditid(users==null?0:users.getUserid());
			msg.setAuditdate(DateUtil.getCurrentDate());
		}
		appmsg.addMsg(msg);
	}
	
	
//	private String getContent() throws Exception{
//		String content = ""; 
//		switch ( msgType ){
//			
//			case 1 : content = getMemberMsg(); break;
//			
//			case 2 : content = getSaleOrderMsg(); break;
//			
//			case 3 : content = getIntegralMsg(); break;
//			
//			case 4 : content = searchSaleOrderMsg(); break;
//			
//			case 5 : content = getRateMsg(); break;
//			
//			case 6 : content = getMoneyMsg(); break;
//		}
//		
//		return content;
//	}

	
	
	private String getMemberMsg() throws Exception{
		return template.getTemplatecontent().replace("name", map.get("name"));
	}
	
	
	private String getSaleOrderMsg() throws Exception{
		String sb = new String();
		sb = template.getTemplatecontent().replace("name", map.get("name"));
		sb = sb.replace("product", map.get("product"));
		sb = sb.replace("money", map.get("money"));
		return sb;
	}
	
	
	private String getIntegralMsg() throws Exception{
		String sb = new String();
		sb = template.getTemplatecontent().replace("name", map.get("name"));
		sb = sb.replace("integral", ""+map.get("integral"));
		return sb;
	}
	
	
	public String searchSaleOrderMsg() throws Exception{
		String sb = new String();
		sb = template.getTemplatecontent().replace("name", map.get("name"));
		sb = sb.replace("billno", map.get("billno"));
		sb = sb.replace("product",  map.get("product"));		
		sb = sb.replace("money", map.get("money"));
		return sb;
	}
	
	
	private String getRateMsg() throws Exception{
		String sb = new String();
		sb = template.getTemplatecontent().replace("name", map.get("name"));
		sb = sb.replace("rate", map.get("rate"));
		return sb;
	}
	
	private String getMoneyMsg() throws Exception{
		String sb = new String();
		sb = template.getTemplatecontent().replace("name", map.get("name"));
		sb = sb.replace("money", map.get("money"));
		return sb;
	}
	
	private String getContent(){		
		StringBuffer sb = new StringBuffer();
		sb.append(template.getTemplatecontent());
		int i=0;
		while(i<param.length){
  			String paraVal=param[i];  			
    		String repaleVal=repaleParam[i];    		
    		int tmpIdx=sb.indexOf(paraVal);
  			if(tmpIdx!=-1){
  				sb.replace(tmpIdx,tmpIdx+paraVal.length(),repaleVal);
  			}  
  	        i++;
  		}
		
		return sb.toString();
	}
	
	public static void main(String[] args){
		String param[] =new String[]{"name", "applytime"};
		String repaleParam[] =new String[]{"jelli", "2009-10-10"};
		StringBuffer sb = new StringBuffer();
		sb.append("您好于申请的billno号换货单已审核，请发货。");
		int i=0;
		while(i<param.length){
  			String paraVal=param[i];  			
    		String repaleVal=repaleParam[i];    		
    		int tmpIdx=sb.indexOf(paraVal);
  			if(tmpIdx!=-1){
  				sb.replace(tmpIdx,tmpIdx+paraVal.length(),repaleVal);
  			}  
  	        i++;
  		}
		System.out.println(sb.toString());
	}
	
	
	
}
