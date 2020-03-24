package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.action.common.ListBaseIdcodeDetailAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListTakeTicketIdcode extends ListBaseIdcodeDetailAction {

   
    protected List getIdcodeList(HttpServletRequest request, String prid,String billid) throws Exception{
    	String batch = request.getParameter("batch");
    	if ( batch == null ){
    		batch = (String)request.getSession().getAttribute("batch");
    	}
    	request.getSession().setAttribute("batch", batch);
    	int pagesize = 20;
    	AppTakeTicketIdcode auv = new AppTakeTicketIdcode();      	
    	//String Condition = " ttid='"+billid+"' and productid='"+prid+"' and batch='"+batch+"' and isidcode=1"; 
    	String Condition = " ttid='"+billid+"' and productid='"+prid+"' and isidcode=1"; 
        Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"TakeTicketIdcode"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
       // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
        String blur = DbUtil.getOrBlur(map, tmpMap, "IDCode","Batch"); 
        whereSql = whereSql + blur +Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
        AppTakeTicket appTakeTicket = new AppTakeTicket();
        TakeTicket tt = appTakeTicket.getTakeTicketById(billid);
        Integer isAudit = tt.getIsaudit();
        request.setAttribute("isAudit", isAudit);
       return auv.searchTakeTicketIdcode(request, pagesize, whereSql);
    }
}
