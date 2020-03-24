package com.winsafe.drp.action.assistant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPointNumber;
import com.winsafe.drp.dao.PointNumber;
import com.winsafe.drp.util.Dateutil;

/**
 * 积分码查询
 * 
 * @Title: ListPointsNumberAction.java
 * @author: wenping
 * @CreateTime: Mar 5, 2013 9:23:27 AM
 * @version:
 */
public class ListPointsNumberAction extends BaseAction {
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = 20;
		super.initdata(request);

		try {

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
				
			
			System.out.println(sql);
			
		List result =	AppPointNumber.jdbcSqlserverQuery(request, sql, pageSize);
//		BasePage bp = new BasePage(request, pageSize);		
//		PageResult pr = jdbcSqlserverQuery(sql, bp.getPageNo(), bp.getPageSite());
//		bp.setPage(pr.getPage());
			
			List<PointNumber> list = new ArrayList<PointNumber>();
//			if(!"".equals(sql)){
//				rs = statement.executeQuery(sql);
//			}
			PointNumber pn = null;
			Map map=null;
//			if(rs!=null){
				for(int i=0;i<result.size();i++){
					map = (HashMap)result.get(i);
					
					pn= new PointNumber();
					pn.setPointsNumber((String)map.get("Points_Number"));
					pn.setProBarcode((String)map.get("ProBarcode"));
					pn.setCartonBarcode((String)map.get("CartonBarcode"));
					String AddDT = (String)map.get("AddDT");
					Date date = Dateutil.formatDatetime(AddDT);
					pn.setAddDT(Dateutil.formatDate(date));
					
					pn.setFwfindCount(Integer.parseInt((String)map.get("FWFindCount")));
					pn.setFwfindDT((String)map.get("FWFindDT"));
					
					String PointsDT = (String)map.get("PointsDT");
					date = Dateutil.formatDatetime(PointsDT);
					pn.setPointsDT(Dateutil.formatDateTime(date));
					
					String ActivatedDT = (String)map.get("ActivatedDT");
					date = Dateutil.formatDatetime(ActivatedDT);
					pn.setActivatedDT(Dateutil.formatDateTime(date));
					
					pn.setIsPoints(Integer.parseInt((String)map.get("IsPoints")));
					pn.setIsActivated(Integer.parseInt((String)map.get("IsActivated")));
					pn.setIsUsePoint(Integer.parseInt((String)map.get("IsUsePoint")));
					pn.setProNo((String)map.get("ProNo"));
					pn.setOther((String)map.get("Other"));
					
					list.add(pn);
				}
//			}

			request.setAttribute("list", list);
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
