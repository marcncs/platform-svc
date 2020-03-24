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
import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListMemberAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ListMemberAction.class);
	private AppMember appMember=new AppMember();

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	//初始化
    	initdata(request);
        int pagesize = 10;

        try {
        	String Condition = "";
            Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"Member"};
            String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
            String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
                    "CreateDate"); 
            String blur = DbUtil.getOrBlur(map, tmpMap, "loginname"); 
            whereSql = whereSql + timeCondition + blur +Condition ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
           
            List usList = appMember.getMembers(request, pagesize, whereSql);

            request.setAttribute("usList", usList);
            
            DBUserLog.addUserLog(userid,"系统管理","注册会员>>列表");
            return mapping.findForward("usersList");

        } catch (Exception e) {
        	logger.error("", e);
        }

        return null;
    }
}
