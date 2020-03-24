package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.action.common.ListBaseIdcodeDetailAction;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListPurchaseIncomeIdcode extends ListBaseIdcodeDetailAction {

   
    protected List getIdcodeList(HttpServletRequest request, String prid,String billid) throws Exception{
    	int pagesize = 20;
    	AppPurchaseIncomeIdcode auv = new AppPurchaseIncomeIdcode();      	
    	String Condition = " piid='"+billid+"' and productid='"+prid+"' and isidcode=1"; 
        Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"PurchaseIncomeIdcode"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
       // String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"CreateDate"); 
        String blur = DbUtil.getOrBlur(map, tmpMap, "IDCode","Batch"); 
        whereSql = whereSql + blur +Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
    	
       return auv.searchPurchaseIncomeIdcode(request, pagesize, whereSql);
    }
}
