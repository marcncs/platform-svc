package com.winsafe.drp.action.assistant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.message.MessageElement;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.ws.DataBaseWebService;
import com.winsafe.drp.ws.DataBaseWebServiceLocator;
import com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType;
import com.winsafe.drp.ws.Fwnumber;
import com.winsafe.drp.ws.GetFWAddInfoResponseGetFWAddInfoResult;
import com.winsafe.hbm.util.DateUtil;

public class ListFWNAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			//页面检索条形码条件
			String keyWord = request.getParameter("KeyWord");
			
			DataBaseWebService db = new DataBaseWebServiceLocator();
			DataBaseWebServiceSoap_PortType sp = db.getDataBaseWebServiceSoap();
			GetFWAddInfoResponseGetFWAddInfoResult result = sp.getFWAddInfo(keyWord);
		
			List<Fwnumber> list = new ArrayList<Fwnumber>();
			Fwnumber fb = null;
			if ( result != null ){
				MessageElement[] me = result.get_any();
				for ( int i=0; i<me.length; i++ ){
					fb = new Fwnumber();
					DOMReader xmlReader = new DOMReader();
					Document doc = xmlReader.read(me[i].getAsDocument());
					Element root =  doc.getRootElement();
					if(!root.getName().equals("diffgram")){
						continue;
					}
					
					fb.setPronumber(doc.valueOf("/diffgr:diffgram/NewDataSet/barcode/PRO_Number"));
					fb.setAdddt(doc.valueOf("/diffgr:diffgram/NewDataSet/barcode/AddDT"));
					fb.setFindcount(doc.valueOf("/diffgr:diffgram/NewDataSet/barcode/FindCount"));
					String finddt = DateUtil.formatDateTime(StringToDatetime(doc.valueOf("/diffgr:diffgram/NewDataSet/barcode/FindDT")));
					fb.setFinddt(finddt);
					String winflag = doc.valueOf("/diffgr:diffgram/NewDataSet/barcode/WinFlag");
					if(winflag.equals("")){
						winflag = "否";
					}else{
						winflag = "是";
					}
					fb.setWinflag(winflag);
					list.add(fb);
					
				}
			}
			request.setAttribute("list", list);
		}catch ( Exception e ){
			e.printStackTrace();
		}
		
			
		return mapping.findForward("list");
		
	}
	
	public  Date StringToDatetime(String strDatetime){
	    DateFormat tempformat = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date=new Date();
	    try{
	      if(strDatetime==null||strDatetime.equals("null")||strDatetime.equals("")){
	        strDatetime = "00000000000000";
	      }
	        date = tempformat.parse(strDatetime);
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    return date;
	  } 

}
