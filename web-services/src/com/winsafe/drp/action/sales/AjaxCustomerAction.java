package com.winsafe.drp.action.sales;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerSimple;
import com.winsafe.drp.dao.Linkman;


public class AjaxCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		String mobile = request.getParameter("mobile");
		String officetel = request.getParameter("officetel");
		String cname = request.getParameter("cname");	
		String temp = request.getParameter("temp");
		String rate = request.getParameter("rate");
		
		try {			
			AppMemberGrade amg = new AppMemberGrade();
			AppObjIntegral aoi = new AppObjIntegral();
			AppCustomer app = new AppCustomer();
			Customer c = new Customer();
			Linkman linkman = null;
			char[] size = new char[]{'a'};			
			if ( cid != null ){
				c = query(app, "cid", cid, size, rate);
			}else if ( mobile != null ){				
				c = query(app, "mobile", mobile, size, rate);
			}else if ( officetel != null ){
				c = query(app, "officetel", officetel, size, rate);
			}else if ( cname != null ){				
				cname = new String(cname.getBytes("ISO-8859-1"), "GB2312");				
				c = query(app, "cname", cname, size, rate);
			}
			if ( c != null ){				
				AppLinkMan applm = new AppLinkMan();
				linkman = applm.getMainLinkmanByCid(c.getCid());
			}		
			CustomerSimple cs = new CustomerSimple();
			if(c != null){
			cs.setCid(c.getCid());
			cs.setCname(c.getCname());
			cs.setTickettitle(c.getTickettitle());
			cs.setIntegral(aoi.getBalance(c.getCid(),1));
			cs.setRate(c.getRate());
			cs.setRatename(amg.getMemberGradeByID(Integer.valueOf(c.getRate())).getGradename());
			cs.setPolicyid(c.getPolicyid());
			cs.setMobile(c.getMobile());
			cs.setOfficetel(c.getOfficetel());
			}
			
			JSONObject json = new JSONObject();			
			json.put("customer", cs);	
			json.put("linkman", linkman);
			json.put("sizes", size[0]=='b'?2:0);
			json.put("temp", temp);
			response.setContentType("text/html; charset=UTF-8");
			//response.setHeader("X-JSON", json.toString());			
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

		    
//			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"查询联系人"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return new ActionForward(mapping.getInput());
		return null;
	}
	
	private Customer query(AppCustomer app, String column, String value, char[] size, String rate) throws Exception{
		String sqlrate = "";
		if ( rate != null && !"".equals(rate) ){
			sqlrate = " and rate=5 ";
		}
		String wsql = " where " + column +" like '"+value+"%' and isdel=0 " +sqlrate;
		List clist =  app.getCustomerBySql(wsql);		
		Customer c = null;
		if ( clist.size() == 1 ){
			c = (Customer)clist.get(0);			
		}else if ( clist.size() > 1 ){
			size[0] = 'b';
		}
		return c;
	}

}
