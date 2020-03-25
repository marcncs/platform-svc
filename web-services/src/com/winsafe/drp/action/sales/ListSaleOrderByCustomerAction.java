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
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListSaleOrderByCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		
//		Long userid = users.getUserid();
		try {
			String strCid = request.getParameter("CID");

			if (strCid == null || strCid.equals("")) {
				strCid = (String) request.getSession().getAttribute("cid");
			}

			String cid = strCid;
			request.getSession().setAttribute("cid", strCid);
			String isreferselect = "";
			isreferselect = Internation.getSelectTagByKeyAll("IsRefer",
					request, "IsRefer", true, null);

			String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "IsAudit", true, null);

			String isblankoutselect = Internation.getSelectTagByKeyAll(
					"YesOrNo", request, "IsBlankOut", true, null);

			String isendcaseselect = Internation.getSelectTagByKeyAll(
					"YesOrNo", request, "IsEndcase", true, null);

//			String Condition = "so.cid='" + cid + "' and so.makeid=" + userid;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleOrder" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
//			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "SaleOrder as so ",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppSaleOrder asl = new AppSaleOrder();
			List pils = asl.searchSaleOrder(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			SaleOrder so = null;
			for (int i = 0; i < pils.size(); i++) {
				so = (SaleOrder) pils.get(i);
				SaleOrderForm sof = new SaleOrderForm();
				sof.setId(so.getId());
				sof.setCustomerbillid(so.getCustomerbillid());
				sof.setCid(so.getCid());
				sof.setCname(so.getCname());
				sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
				sof.setConsignmentdate(DateUtil.formatDate(so
						.getConsignmentdate()));
				sof.setTotalsum(so.getTotalsum());
				sof.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsaudit(),
						"global.sys.SystemResource"));
				sof.setIsblankoutname(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsblankout(),
						"global.sys.SystemResource"));
				sof.setIsendcasename(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsendcase(),
						"global.sys.SystemResource"));
				sof.setMakeidname(au.getUsersByid(so.getMakeid())
						.getRealname());
				also.add(sof);
			}

			request.setAttribute("cid", cid);
			request.setAttribute("also", also);
			request.setAttribute("isreferselect", isreferselect);
			request.setAttribute("isauditselect", isauditselect);
			request.setAttribute("isblankoutselect", isblankoutselect);
			request.setAttribute("isendcaseselect", isendcaseselect);

//			DBUserLog.addUserLog(userid, "列表销售订单从客户");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
