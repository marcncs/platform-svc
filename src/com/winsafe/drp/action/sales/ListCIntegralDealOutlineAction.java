package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppViewCIntegralDeal;
import com.winsafe.drp.dao.ViewCIntegralDeal;
import com.winsafe.drp.dao.ViewCIntegralDealForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListCIntegralDealOutlineAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppViewCIntegralDeal acid = new AppViewCIntegralDeal();
		AppCustomer ac = new AppCustomer();
		AppUsers au = new AppUsers();
		AppOrgan ao = new AppOrgan();

		int pagesize = 20;
		try {
			
//			Long userid = users.getUserid();
			//String Condition = " c.makeid like '" + userid + "%' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"ViewCIntegralDeal"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			
			String integralCondition = DbUtil.getPriceCondition(map, tmpMap,
			"DealIntegral");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			 String blur = DbUtil.getOrBlur(map, tmpMap, "Mobile","BillNo");
			// 
			whereSql = whereSql + integralCondition +timeCondition +blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " ViewCIntegralDeal as c", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			List usList = acid.getViewCIntegralDeal(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			//System.out.println("---a---------"+usList.size());
			for (int t = 0; t < usList.size(); t++) {
				ViewCIntegralDealForm cdf = new ViewCIntegralDealForm();
				// customer=(Customer)usList.get(t);
				ViewCIntegralDeal o = (ViewCIntegralDeal) usList.get(t);
				//cdf.setId(o.getId());
				cdf.setBillno(o.getVcidPK().getBillno());
				cdf.setOrganidname(ao.getOrganByID(o.getVcidPK().getOrganid()).getOrganname());
				cdf.setCid(o.getVcidPK().getCid());
				cdf.setCidname(ac.getCustomer(cdf.getCid()).getCname());
				//cdf.setIsortname(Internation.getStringByKeyPositionDB("ISort",o.getIsort()));
				cdf.setDealintegral(o.getDealintegral());
				cdf.setCompleteintegral(o.getCompleteintegral());
				cdf.setMakedate(String.valueOf(o.getMakedate()));
				hList.add(cdf);

			}

			request.setAttribute("hList", hList);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());
			request.setAttribute("ols",ols);
			
//			String isortselect = Internation.getSelectTagByKeyAllDB("ISort",
//					 "ISort", true);
//
//			request.setAttribute("isortselect", isortselect);
//			List uls = au.getIDAndLoginName();
//			ArrayList als = new ArrayList();
//			for (int u = 0; u < uls.size(); u++) {
//				UsersBean ubs = new UsersBean();
//				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
//				ubs.setRealname(ub[2].toString());
//				als.add(ubs);
//			}
//			request.setAttribute("als", als);


//			DBUserLog.addUserLog(userid, "列表会员积分");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
