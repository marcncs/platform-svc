package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DemandPriceForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListDemandPriceByCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);

		
//		Long userid = users.getUserid();
		try {
			String strCid = request.getParameter("CID");

			if (strCid == null || strCid.equals("")) {
				strCid = (String) request.getSession().getAttribute("cid");
			}
			String cid = strCid;
			request.getSession().setAttribute("cid", cid);

//			String Condition = " dp.cid='" + cid + "' and dp.makeid like '"
//					+ userid + "%' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "DemandPrice" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
//			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			// Object obj[] =
			// (DbUtil.setPager(request,"SaleLog as sl ",whereSql,pagesize));
			Object obj[] = DbUtil.setDynamicPager(request,
					"DemandPrice as dp ", whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppCustomer ac = new AppCustomer();
			AppDemandPrice asl = new AppDemandPrice();
			List pils = asl.getDemandPrice(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				DemandPriceForm dpf = new DemandPriceForm();
				Object[] o = (Object[]) pils.get(i);
				dpf.setId(Long.valueOf(o[0].toString()));
				dpf.setCidname(ac.getCustomer(o[1].toString()).getCname());
				dpf.setDemandname(String.valueOf(o[3]));
				dpf.setTotalsum(Double.valueOf(o[9].toString()));
				dpf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, Integer.parseInt(o[6].toString()),
						"global.sys.SystemResource"));
//				dpf.setMakeidname(au.getUsersByid(
//						Long.valueOf(o[4].toString())).getRealname());
				dpf.setMakedate(String.valueOf(o[5]).substring(0, 10));

				also.add(dpf);
			}
			request.setAttribute("also", also);
			request.setAttribute("isauditselect", isauditselect);

//			DBUserLog.addUserLog(userid, "列表销售订单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
