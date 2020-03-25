package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringFilters;

/**
 * windrp
 * 
 * 主要功能 增加仓库权限
 * 
 * @author 俞子坚 2010-4-7
 * 
 */
public class SelectUserVisitOrganWHIframeAction extends Action {

	/**
	 * 页面跳转默认execute方法
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
		
		String type=request.getParameter("type");
		if(StringUtil.isEmpty(type)){
			return mapping.findForward("success");
		}else if("list".equals(type)){
			// 选中用户信息ID
			Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());

			String KeyWord=request.getParameter("KeyWord");
			KeyWord=StringFilters.filterSql(KeyWord);
			
			List<RuleUserWh> rules;


			String organs = "";
			AppUserVisit auv = new AppUserVisit();
			//通过UserId查询UserVisit
			UserVisit userVisit = auv.getUserVisitByUserID(userId);
			//如果UserVisit不为空
			if(null != userVisit){
				String organStrs = userVisit.getVisitorgan();
				if(!StringUtil.isEmpty(organStrs)){
					String[] oIds = organStrs.split(",");
					organs = com.winsafe.hbm.util.StringUtil.getString(oIds, 0, true);
				}else{
					organs = "";
				}	
				
			}else{
				organs = "";
			}
			
			AppWarehouse appW=new AppWarehouse();

			List<Warehouse> warehouseList=appW.getRWNotInOrganIds(organs,userId);
			
			for(Warehouse w:warehouseList){
				// 插入数据库
				appRuleUserWH.addRuleUserWh(w.getId(),userId);
			}

			//依据用户编号 得到所有权限仓库
			rules=appRuleUserWH.getRuleByUserId(request,Constants.PAGE_SIZE,userId,KeyWord);

            request.setAttribute("ruleWarehouses", rules);
            request.setAttribute("KeyWord", KeyWord);
			return mapping.findForward("list");
		}else if("reset".equals(type)){
			// 选中用户信息ID
			Integer userId = Integer.valueOf(request.getSession().getAttribute("visituser").toString());
			// 根据登陆者ID得到权限机构列表
			AppUserVisit auv = new AppUserVisit();
			String whereSql = " where exists (select u.id from UserVisit "
					+ "as u where instr(o.id,u.visitorgan)>=1 and u.userid="
					+ userId + ") ";
			List<Organ> vulist = auv.getAllUserVisitOrgan(whereSql);
			// 所有仓库列表
			List<Warehouse> allWarehouses = new ArrayList<Warehouse>();
			AppWarehouse awh = new AppWarehouse();
			for(Organ organ : vulist) {
				//根据机构ID找到相应的所属仓库
				List<Warehouse> warehouses = awh.getWarehouseListByOID(organ.getId());
				allWarehouses.addAll(warehouses);
			}
			//检查是否有新增仓库
		    //初始化用户-仓库权限表       将所有有权限机构的仓库设置到表中
			List<RuleUserWh> rules = appRuleUserWH.chkUserWarehouse(allWarehouses, userId);
		    //清空数据库垃圾数据
			appRuleUserWH.delOldUserWarehouse(userId.toString(),rules,allWarehouses);
			
			HibernateUtil.commitTransaction();

			String KeyWord=request.getParameter("KeyWord");
			KeyWord=StringFilters.filterSql(KeyWord);

			//依据用户编号 得到所有权限仓库
			rules=appRuleUserWH.getRuleByUserId(request,Constants.PAGE_SIZE,userId,KeyWord);

			return mapping.findForward("success");
		}else{
			return null;
		}
	}
}
