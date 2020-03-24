package com.winsafe.drp.action.sys;

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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringFilters;

public class SelectRuleUserWHAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SelectRuleUserWHAction.class);
	
	private AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
	private AppOrgan appOrgan = new AppOrgan();
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	//初始化
    	initdata(request);
    	
    	int pagesize = 20;
        try {
        	String wid = request.getParameter("WID");
			String KeyWord=request.getParameter("KeyWord");
			String makeorganid = request.getParameter("makeorganid");
        	//查询条件
        	Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"Users"};
            String whereSql = EntityManager.getTmpWhereSql(map, tablename);
            //KeyWord模糊查询
            String blur = DbUtil.getOrBlur(map, tmpMap, "u.userid","u.loginname","u.realname");
            String Condition = " u.makeorganid = o.id and o.sysid like '"+users.getOrgansysid()+"%' ";
            Condition +=  " and not exists (select rw.id from RuleUserWh  rw where u.userid=rw.userId and  rw.warehouseId='"+wid+"') "; 
//            String Condition = " w.makeorganid in (select id from Organ o where o.sysid like '"+users.getOrgansysid()+"%' and o.organType="+organtype+" and o.organModel="+organmodel+
            if(!StringUtil.isEmpty(makeorganid)){
            	Condition += " and u.makeorganid = '" + makeorganid + "' ";
            }
            whereSql = whereSql + blur +Condition ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
            KeyWord=StringFilters.filterSql(KeyWord);
            request.setAttribute("KeyWord", KeyWord);
			
            List<Map> rwlist  = appRuleUserWH.getNoRuleUsers(request,pagesize,whereSql);
			
            request.setAttribute("rwlist", rwlist);
            return mapping.findForward("rw");

        } catch (Exception e) {
        	logger.error("", e);
        }

        return null;
    }
}
