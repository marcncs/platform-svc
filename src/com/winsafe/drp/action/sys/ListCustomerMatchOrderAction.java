package com.winsafe.drp.action.sys;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomerMatchOrder;
import com.winsafe.drp.dao.CustomerMatchOrder;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListCustomerMatchOrderAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	super.initdata(request);
        AppCustomerMatchOrder appCmo=new AppCustomerMatchOrder();
        int pagesize = 10;

        try {
            Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"CustomerMatchOrder"};
            String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
            String blur = DbUtil.getOrBlur(map, tmpMap, "siName", "organName","productLine"); 
            whereSql = whereSql + blur ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
           
            
            List<CustomerMatchOrder> cmoList = appCmo.getCustomerMatchOrders(request, pagesize, whereSql);

            request.setAttribute("cmoList", cmoList);

            DBUserLog.addUserLog(userid,11,"用户管理>>配货顺序");
            return mapping.findForward("customerMatchOrderList");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
