package com.winsafe.drp.action.warehouse;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.util.JsonUtil;

/**
 * 批量新增条码
* @Title: CheckAndGenerateIdcode.java
* @author: wenping 
* @CreateTime: Jan 5, 2013 2:21:49 PM
* @version:
 */
public class CheckAndGenerateIdcode extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		StringBuilder sb =new StringBuilder();
		try {
			String startno=request.getParameter("startno");
			String endno=request.getParameter("endno");
			
			//只针对箱码
			if(!isBoxCode(response, startno, json)){
				return null;
			}
			
			if(!isBoxCode(response, endno, json)){
				return null;
			}
			
			//起始与终止要同为国内或国外的箱码
			if(startno.length()!=endno.length()){
				json.put("result", 1);
				JsonUtil.setJsonObj(response, json);
				return null;
			}
			
			//国内
			if(startno.substring(0, 1).equals("C")){
				boolean ispass = generateIdcode(response, json, sb, startno, endno,"C");			
				if(!ispass){
					return null;
				}
			}else{//国外
				boolean ispass = generateIdcode(response, json, sb, startno, endno,"2");			
				if(!ispass){
					return null;
				}
			}
		} catch (Exception e) {
			json.put("result", 0);
			JsonUtil.setJsonObj(response, json);
			return null;
		}
//		request.getSession().setAttribute("resultVal", sb.toString());
		json.put("resultVal", sb.toString());
		JsonUtil.setJsonObj(response, json);
		
		return null;
	}

	/**
	 * 由起始码与结束码生成范围内的所有条码
	* @param response
	* @param json
	* @param sb
	* @param startno
	* @param endno
	* @param flag
	* @return
	* @throws IOException
	* @author wenping   
	* @CreateTime Jan 9, 2013 11:49:36 AM
	 */
	private boolean generateIdcode(HttpServletResponse response, JSONObject json, StringBuilder sb, String startno, String endno,String flag)
			throws IOException {
		String sprefix = null;
		String ssuffix = null;
		String eprefix =null;
		String esuffix =null;
		if(flag.equals("C")){
			sprefix = startno.substring(0, 4);
			ssuffix = startno.substring(4, 13);
			
			eprefix = endno.substring(0, 4);
			esuffix = endno.substring(4, 13);
		}else{
			sprefix = startno.substring(0, 3);
			ssuffix = startno.substring(3, 13);
			
			eprefix = endno.substring(0, 3);
			esuffix = endno.substring(3, 13);
		}
		
		if(!sprefix.equals(eprefix)){
			json.put("result", 3);
			JsonUtil.setJsonObj(response, json);
			return false;
		}
		
//		int sno = Integer.parseInt(ssuffix);
//		int eno = Integer.parseInt(esuffix);
//		if(sno>eno){
//			int tem = sno;
//			sno = eno;
//			eno = tem;
//		}
		
		long sno = Long.parseLong(ssuffix);
		long eno = Long.parseLong(esuffix);
		if(sno>eno){
			long tem = sno;
			sno = eno;
			eno = tem;
		}
		
		
		//通过起始码与结束码生成所有条码
		if(flag.equals("C")){
			for(long i=sno;i<=eno;i++){
				String idcode  = sprefix+String.format("%09d", i);
				sb.append(idcode);
				sb.append("\n");
			}
		}else{
			for(long i=sno;i<=eno;i++){
				String idcode  = sprefix+String.format("%010d", i)+"000";
				sb.append(idcode);
				sb.append("\n");
			}
		}
		
		return true;
	}

	/**
	 * 
	* 判断条码是否为箱码
	* @param response
	* @param idcode
	* @param json
	* @return
	* @throws IOException
	* @author wenping   
	* @CreateTime Jan 7, 2013 2:57:30 PM
	 */
	private boolean isBoxCode(HttpServletResponse response, String idcode, JSONObject json) throws IOException {
		if(idcode.substring(0, 1).equals("C") || idcode.substring(0, 1).equals("2")){
			if(idcode.substring(0, 1).equals("2") && !idcode.substring(idcode.length()-3).equals("000")){
				json.put("result", 2);
				JsonUtil.setJsonObj(response, json);
				return false;
			}
		}else{
			json.put("result", 2);
			JsonUtil.setJsonObj(response, json);
			return false;
		}
		return true;
	}
}
