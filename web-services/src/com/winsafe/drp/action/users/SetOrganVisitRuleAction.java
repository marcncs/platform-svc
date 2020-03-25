package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;

public class SetOrganVisitRuleAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//所需要dao类
		AppOrganVisit aov = new AppOrganVisit();
		AppMakeConf appmc = new AppMakeConf();
		AppWarehouseVisit appWV = new AppWarehouseVisit();
		
		//初始化
		initdata(request);
		
		try {
			String KeyWord = request.getParameter("KeyWord");
			
			String type = request.getParameter("type");
			String uid = request.getParameter("uid");
			
			//全部选中功能
			if ("checkAll".equals(type)) {
				String Condition = " o.sysid like '" + users.getOrgansysid()+ "%' ";
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "Organ" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename);
				String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName");
				whereSql = whereSql + blur + Condition;
				whereSql = DbUtil.getWhereSql(whereSql);
				//批量添加业务往来机构
				aov.addAllOrganVisit(whereSql, uid);
				//更新make_conf
				appmc.updMakeConf("organ_visit","organ_visit");
				//批量添加业务往来仓库
				appWV.addRuleWhByOrganWhere(whereSql.replace("where", " and "), uid);
				//更新make_conf
				appmc.updMakeConf("warehouse_visit","warehouse_visit");
				DBUserLog.addUserLog(Integer.valueOf(uid), "系统管理", "用户管理>>列表用户业务往来权限>>新增机构所有机构");
				
			} else {
				String strspeed = request.getParameter("speedstr");
				String[] organids = strspeed.split(",");
				for(String oid : organids){
					OrganVisit newov = new OrganVisit();
					Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
					newov.setId(id);
					newov.setUserid(Integer.valueOf(uid));
					newov.setVisitorgan(oid);
					aov.SaveOrganVisit(newov);
					//增加业务往来仓库
					appWV.addVWByOid(oid, uid);
					//更新make_conf
					appmc.updMakeConf("warehouse_visit","warehouse_visit");
					DBUserLog.addUserLog(Integer.valueOf(uid), "系统管理", "用户管理>>列表用户业务往来权限>>新增机构:"+strspeed);
				}
			}
			//返回页面的值
			request.setAttribute("KeyWord", KeyWord);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("set");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
