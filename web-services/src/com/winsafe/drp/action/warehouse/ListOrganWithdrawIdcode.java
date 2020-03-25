package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.action.common.ListBaseIdcodeDetailAction;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganWithdrawIdcode extends ListBaseIdcodeDetailAction {
   
    protected List getIdcodeList(HttpServletRequest request, String prid,String billid) throws Exception{
    	int pagesize = 20;
    	String batch = request.getParameter("batch");
    	if ( batch == null ){
    		batch = (String)request.getSession().getAttribute("batch");
    	}
    	request.getSession().setAttribute("batch", batch);
    	AppOrganWithdrawIdcode auv = new AppOrganWithdrawIdcode();   
    	String flag = request.getParameter("flag");
    	String whereSql = "";
    	if("PW".equals(flag)) {
    		AppTakeTicket att = new AppTakeTicket();
    		TakeTicket tt = att.getTakeTicket(billid);
    		String Condition = " owi.ttid='"+tt.getId()+"' and owi.productid='"+prid+"'"; 
//    		String Condition = " owi.owid='"+billid+"' and owi.productid='"+prid+"' and owi.isidcode=1"; 
    		 Map map=new HashMap(request.getParameterMap());
             Map tmpMap = EntityManager.scatterMap(map);
             String[] tablename={"TakeTicketIdcode"};
             whereSql = EntityManager.getTmpWhereSql(map, tablename); 
             String blur = DbUtil.getOrBlur(map, tmpMap, "owi.idcode", "owi.batch", "owi.producedate"); 
             whereSql = whereSql + blur +Condition ; 
             whereSql = DbUtil.getWhereSql(whereSql); 
             return auv.searchPlantWithdrawIdcode(request, pagesize, whereSql);
    	} else {
    		String Condition = " owid='"+billid+"' and productid='"+prid+"'"; 
            Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"OrganWithdrawIdcode"};
            whereSql = EntityManager.getTmpWhereSql(map, tablename); 
           // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
            String blur = DbUtil.getOrBlur(map, tmpMap, "IDCode","Batch"); 
            whereSql = whereSql + blur +Condition ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
            return auv.searchOrganWithdrawIdcode(request, pagesize, whereSql);
    	}
    }
}
