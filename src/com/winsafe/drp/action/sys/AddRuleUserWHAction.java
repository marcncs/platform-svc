package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddRuleUserWHAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddRuleUserWHAction.class);
	private AppUserVisit auv = new AppUserVisit();
	private AppMakeConf appmc = new AppMakeConf();
	private AppRuleUserWH appRuWH = new AppRuleUserWH();
	private AppWarehouse appWarehouse = new AppWarehouse();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		
		try {
			String KeyWord = request.getParameter("KeyWord");
			String makeorganid = request.getParameter("makeorganid");
			
			String type = request.getParameter("type");
			String wid = request.getParameter("WID");
			
			//全部选中功能
			if ("checkAll".equals(type)) { 
				 String Condition = " u.makeorganid in (select id from Organ o where o.sysid like  '"+users.getOrgansysid()+"%' ) "; 
		            
				if(!StringUtil.isEmpty(makeorganid)){
	            	Condition += " and u.makeorganid = '" + makeorganid + "' ";
	            }
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "Users" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename);
				String blur = DbUtil.getOrBlur(map, tmpMap, "u.userid","u.loginname","u.realname");
				whereSql = whereSql + blur + Condition;
				//whereSql = DbUtil.getWhereSql(whereSql);
				//批量添加管辖仓库
				appRuWH.addRuleWhByUsersWhere(whereSql, wid);
				//更新make_conf
				appmc.updMakeConf("RULE_USER_WH","RULE_USER_WH");
				//批量添加管辖机构
				Warehouse w = appWarehouse.getWarehouseByID(wid);
				auv.addAllUserVisitByOid(whereSql, w.getMakeorganid());
				//删除多余的管辖机构的数据
				//auv.delNoUseUserVisit(uid);
				DBUserLog.addUserLog(Integer.valueOf(userid), "系统管理", "机构设置>>列表管辖用户>>新增所有用户");
			} else {
				String strspeed = request.getParameter("speedstr");
				String[] uids = strspeed.split(",");
				Warehouse w = appWarehouse.getWarehouseByID(wid);
				for(String uid : uids){
					//增加管辖仓库
					appRuWH.addRuleUserWh(wid,Integer.valueOf(uid));
					// 判断机构中的所有仓库是否全部都被管辖
					UserVisit userVisit = auv.findVisitOrgan(Integer.valueOf(uid),w.getMakeorganid());
					if(userVisit == null){
						UserVisit newuv = new UserVisit();
						Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_visit", 0, ""));
						newuv.setId(id);
						newuv.setUserid(Integer.valueOf(uid));
						newuv.setVisitorgan(w.getMakeorganid());
						newuv.setVisitdept("");
						auv.SaveUserVisit(newuv);
					}
					appmc.updMakeConf("RULE_USER_WH","RULE_USER_WH");
					DBUserLog.addUserLog(Integer.valueOf(uid), "系统管理", "机构设置>>列表管辖用户>>新增用户:"+strspeed);
				}
			}
			//返回页面的值
			request.setAttribute("KeyWord", KeyWord);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("set");
			
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
}
