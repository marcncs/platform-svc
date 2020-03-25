package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProductRedeploy;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ProductRedeploy;
import com.winsafe.drp.dao.ProductRedeployForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListProductRedeployAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String IsAuditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);
		super.initdata(request);super.initdata(request);try{
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from product";
			String[] tablename = { "ProductRedeploy" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setDynamicPager(request, "ProductRedeploy",
					whereSql, pagesize, "productCondition"));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0];
			whereSql = (String) obj[1];

			AppProductRedeploy ap = new AppProductRedeploy();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			List apls = ap.getProductRedeploy(pagesize, whereSql, tmpPgInfo);
			ArrayList prls = new ArrayList();

			for (int p = 0; p < apls.size(); p++) {
				ProductRedeployForm pf = new ProductRedeployForm();
				ProductRedeploy ob = (ProductRedeploy) apls.get(p);
				pf.setId(ob.getId());
				pf.setRedeployidname(au.getUsersByid(ob.getRedeployid())
						.getRealname());
				pf.setRedeploydeptname(ad.getDeptByID(ob.getRedeploydept())
						.getDeptname());
				pf.setAuditdatename(DateUtil.formatDate(ob.getAuditdate()));
				pf.setMakeidname(au.getUsersByid(ob.getMakeid())
						.getRealname());
				pf.setAuditidname(ob.getAuditid()==null?"":au.getUsersByid(ob.getAuditid())
						.getRealname());
				pf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
						request, ob.getIsaudit(), "global.sys.SystemResource"));

				prls.add(pf);
			}

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}

			request.setAttribute("IsAuditselect", IsAuditselect);
			request.setAttribute("als", als);
			request.setAttribute("prls", prls);
//			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "查询存货");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
