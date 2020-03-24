package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.action.common.ListBaseIdcodeDetailAction;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListProductInterconvertIdcode extends ListBaseIdcodeDetailAction {

   
    protected List getIdcodeList(HttpServletRequest request, String prid,String billid) throws Exception{
    	int pagesize = 20;
    	String batch = request.getParameter("batch");
    	if ( batch == null ){
    		batch = (String)request.getSession().getAttribute("batch");
    	}
    	request.getSession().setAttribute("batch", batch);
    	AppProductInterconvertIdcode auv = new AppProductInterconvertIdcode();      	
    	String Condition = " piid='"+billid+"' and productid='"+prid+"' and batch='"+batch+"' and isidcode=1"; 
        Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"ProductInterconvertIdcode"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
       // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
        String blur = DbUtil.getOrBlur(map, tmpMap, "IDCode","Batch"); 
        whereSql = whereSql + blur +Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
    	
       return auv.searchProductInterconvertIdcode(request, pagesize, whereSql);
    }
}
