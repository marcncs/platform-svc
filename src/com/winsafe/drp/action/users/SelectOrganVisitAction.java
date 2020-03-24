package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringFilters;

public class SelectOrganVisitAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SelectOrganVisitAction.class);
	
	private AppOrganVisit aov = new AppOrganVisit();
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	//初始化
    	initdata(request);
    	
    	int pagesize = 20;
        try {
        	String userid = request.getParameter("uid");
			String bigRegionName=request.getParameter("bigRegionName");
			String officeName=request.getParameter("officeName");
			String KeyWord=request.getParameter("KeyWord");
        	//查询条件
        	Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"Organ"};
            String whereSql = EntityManager.getTmpWhereSql(map, tablename);
            //KeyWord模糊查询
            String blur = DbUtil.getOrBlur(map, tmpMap, "ID","OrganName"); 
            String Condition = " o.sysid like '"+users.getOrgansysid()+"%' and not exists (select u.id from OrganVisit as u where o.id=u.visitorgan and  u.userid="+userid+") "; 
            whereSql = whereSql + blur +Condition ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
            
			KeyWord=StringFilters.filterSql(KeyWord);
			bigRegionName=StringFilters.filterSql(bigRegionName);
			officeName=StringFilters.filterSql(officeName);
            request.setAttribute("KeyWord", KeyWord);
            request.setAttribute("bigRegionName", bigRegionName);
            request.setAttribute("officeName", officeName);
			
            List vulist = aov.getOrganVisit(request,pagesize,whereSql);
            
            request.setAttribute("vulist", vulist);
            return mapping.findForward("uv");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
