package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil; 

public class ListUsersAction extends BaseAction {
	
	private AppUsers appUsers=new AppUsers();

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	//初始化
    	initdata(request);
        int pagesize = 10;
        String userType=request.getParameter("userType");
        String organType=request.getParameter("organType");
        String organModel=request.getParameter("organModel");
        String isCwid=request.getParameter("isCwid");
        String isLocked=request.getParameter("isLocked");
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        try {
            Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"Users"};
            String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param); 
            String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
                    "u.CreateDate", param); 
            String blur = DbUtil.getOrBlurForSql(map, tmpMap,param , "u.RealName", "u.loginname"); 
            
            String Condition = "";
	       	 if(DbUtil.isDealer(users)) {
	       		 Condition = " (u.makeorganid in (select visitorgan from  User_Visit uv where uv.userid ='"+ userid + "' )" +
		 				" or  u.makeorganid in (select visitorgan from  Organ_Visit ov where ov.userid ='"+ userid + "' ) " +
		 				") and ";
	       	 } else {
	       		 Condition += DbUtil.getWhereCondition(users, "o")+" and ";
	       	 }
	       	 
	       	if(!StringUtil.isEmpty(organType) || !StringUtil.isEmpty(organModel)) {
	//       		Condition += " u.makeorganid in (select id from Organ where ";
	       		if(!StringUtil.isEmpty(organType)) {
	       			Condition += "o.organType=? and ";
	       			param.put("organType", organType);
	       		}
	       		if(!StringUtil.isEmpty(organModel)) {
	       			Condition += "o.organModel=? and ";
	       			param.put("organModel", organModel);
	       		}
	//       		Condition += ") ";
	       	}
	       	if(!StringUtil.isEmpty(isCwid)) {
	       		Condition += "u.isCwid=? and ";
	       		param.put("isCwid", Integer.parseInt(isCwid));
	       	}
	       	if(!StringUtil.isEmpty(isLocked)) {
	       		Condition += "u.isLocked=? and ";
	       		param.put("isLocked", Integer.parseInt(isLocked));
	       	}
            
            whereSql = whereSql + timeCondition + blur +Condition ; 
            
            if(!StringUtil.isEmpty(userType)) {
            	whereSql += " u.UserType =?";
            	param.put("UserType", Integer.parseInt(userType));
            }
            
            whereSql = DbUtil.getWhereSql(whereSql); 
           
            List<Map<String,String>> usList = appUsers.getUsersList(request, pagesize, whereSql, param);

            // 判断权限控制级别是机构还是仓库
            Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
            // 管辖权限
            String ruleAuth = systemPro.getProperty("ruleAuth");
            String ruleAuthUrl = "/users/listUserVisitAction.do";
            if(Constants.AUTH_RULE_ORGAN.equals(ruleAuth)){
            	ruleAuthUrl = "/users/listUserVisitAction.do";
            }else if (Constants.AUTH_RULE_WAREHOUSE.equals(ruleAuth)){
            	ruleAuthUrl = "/users/listRuleUserWHAction.do";
            }
            
            // 业务往来权限
            String visitAuth = systemPro.getProperty("visitAuth");
            String visitAuthUrl = "/users/listOrganVisitAction.do";
            if(Constants.AUTH_VISIT_ORGAN.equals(visitAuth)){
            	visitAuthUrl = "/users/listOrganVisitAction.do";
            }else if(Constants.AUTH_RULE_WAREHOUSE.equals(visitAuth)){
            	visitAuthUrl = "/users/listOrganVisitAction.do";
			}
            
            AppBaseResource appBaseResource = new AppBaseResource();
            Map<Integer, String> usersType = appBaseResource.getBaseResourceMap("SalesUserType");
            List<UsersForm> usersList = new ArrayList<UsersForm>();
            for(Map<String,String> usersMap : usList) {
            	UsersForm users = new UsersForm();
            	MapUtil.mapToObject(usersMap, users);
            	if(usersType.get(users.getUserType()) != null) {
            		users.setUserTypeName(usersType.get(users.getUserType()));
            	}
            	usersList.add(users);
            }
            
            request.setAttribute("ruleAuthUrl", ruleAuthUrl);
            request.setAttribute("visitAuthUrl", visitAuthUrl);
            request.setAttribute("usList", usersList);
            request.setAttribute("salesUserType", usersType);
            
            
            
            DBUserLog.addUserLog(userid,"系统管理","用户管理>>列表用户");
            return mapping.findForward("usersList");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
