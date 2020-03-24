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
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.CIntegralDealForm;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListCIntegralDealAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppCIntegralDeal acid = new AppCIntegralDeal();
		AppCustomer ac = new AppCustomer();
		AppUsers au = new AppUsers();
		AppOrgan ao = new AppOrgan();

		int pagesize = 20;
		try {
			
//			Long userid = users.getUserid();
			String Condition = "  c.cid=ct.cid ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"CIntegralDeal", "Customer"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap, "dealprice"); 
			 String blur = DbUtil.getOrBlur(map, tmpMap, "ct.cid","ct.cname","ct.mobile","c.billno");
			whereSql = whereSql + blur + timeCondition+priceCondition+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " CIntegralDeal as c, Customer as ct", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			List usList = acid.getCIntegralDealCustomer(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				CIntegralDealForm cdf = new CIntegralDealForm();
				Object[] ob = (Object[])usList.get(t);
				CIntegralDeal o = (CIntegralDeal) ob[0];
				Customer customer=(Customer)ob[1];
				cdf.setId(o.getId());
				cdf.setBillno(o.getBillno());
				cdf.setOrganidname(ao.getOrganByID(o.getOrganid()).getOrganname());
				cdf.setCid(o.getCid());
				cdf.setCidname(customer.getCname());
				cdf.setMobile(customer.getMobile());
				cdf.setIsortname(Internation.getStringByKeyPositionDB("ISort",o.getIsort()));
				cdf.setDealintegral(o.getDealintegral());
				cdf.setCompleteintegral(o.getCompleteintegral());
				cdf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
				hList.add(cdf);

			}

			request.setAttribute("hList", hList);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());
			request.setAttribute("ols",ols);
			
			String isortselect = Internation.getSelectTagByKeyAllDB("ISort",
					 "ISort", true);

			request.setAttribute("isortselect", isortselect);
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
