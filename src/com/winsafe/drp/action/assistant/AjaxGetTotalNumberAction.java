package com.winsafe.drp.action.assistant;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppPointNumber;

public class AjaxGetTotalNumberAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String pointNumber = request.getParameter("pointNumber");
		String beginDate = request.getParameter("BeginDate");
		String endDate = request.getParameter("EndDate");
		
		String isactive = request.getParameter("isactive");
		String beginActiveDate = request.getParameter("beginActiveDate");
		String endActiveDate = request.getParameter("endActiveDate");
		
		//String sql ="select * from BdPointsNumber where  Points_Number is null";
		 String sql ="";
			
			if(!StringUtil.isEmpty(pointNumber) ){
				sql = "select * from BdPointsNumber where Points_Number='"+pointNumber+"'";
			}
			
			if(!StringUtil.isEmpty(beginDate) ){
				if(!StringUtil.isEmpty(sql)){
					sql+="  and AddDT>='"+beginDate+"'";
				}else{
					sql="select * from BdPointsNumber where AddDT>='"+beginDate+"'";
				}
			}
			
			if(!StringUtil.isEmpty(endDate) ){
				if(!StringUtil.isEmpty(sql)){
					sql+="  and AddDT<='"+endDate+"'";
				}else{
					sql="select * from BdPointsNumber where AddDT<='"+endDate+"'";
				}
			}
			
			
			if(!StringUtil.isEmpty(isactive) ){
				if(!StringUtil.isEmpty(sql)){
					sql+="  and IsActivated="+isactive;
				}else{
					sql = "select * from BdPointsNumber where IsActivated="+isactive;
				}
			}
			
			if(!StringUtil.isEmpty(beginActiveDate) ){
				if(!StringUtil.isEmpty(sql)){
					sql+="  and ActivatedDT>='"+beginActiveDate+"'";
				}else{
					sql="select * from BdPointsNumber where ActivatedDT>='"+beginActiveDate+"'";
				}
			}
			
			if(!StringUtil.isEmpty(endActiveDate) ){
				if(!StringUtil.isEmpty(sql)){
					sql+="  and ActivatedDT<='"+endActiveDate+" 23:59:59'";
				}else{
					sql="select * from BdPointsNumber where ActivatedDT<='"+endActiveDate+" 23:59:59'";
				}
			}
			
			
			if(StringUtil.isEmpty(sql)){
				sql ="select * from BdPointsNumber where  Points_Number is null";
			}
			

		int result = AppPointNumber.getTotalCount( sql);
		try {
			
			JSONObject json = new JSONObject();			
			if(result>100000){
				json.put("returnCode", 1);				
			}else{
				json.put("returnCode", 0);				
			}
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
