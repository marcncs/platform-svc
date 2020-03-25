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
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.LoanOut;
import com.winsafe.drp.dao.LoanOutForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListLoanOutAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);

		String isreceiveselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsReceive", true, null);

		String istranssaleselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsTransSale", true, null);

		
//		Long userid = users.getUserid();
		try {

//			String Condition = " makeid like '" + userid + "%' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "LoanOut" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
//			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "LoanOut", whereSql,
					pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppLoanOut asl = new AppLoanOut();
			List pils = asl.searchLoanOut(pagesize, whereSql, tmpPgInfo);
			ArrayList also = new ArrayList();
			LoanOut so = null;
			for (int i = 0; i < pils.size(); i++) {
				so = (LoanOut) pils.get(i);
				LoanOutForm sof = new LoanOutForm();
				sof.setId(so.getId());
				sof.setUid(so.getUid());
				sof.setUname(so.getUname());
				sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
				sof.setConsignmentdate(DateUtil.formatDate(so
						.getConsignmentdate()));
				sof.setTotalsum(so.getTotalsum());
				sof.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsaudit(),
						"global.sys.SystemResource"));
				sof.setIsreceivename(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIsreceive(),
						"global.sys.SystemResource"));
				sof.setIstranssalename(Internation.getStringByKeyPosition(
						"YesOrNo", request, so.getIstranssale(),
						"global.sys.SystemResource"));
//				sof.setMakeidname(au.getUsersByid(so.getMakeid())
//						.getRealname());
				sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
				also.add(sof);
			}

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}

			request.setAttribute("als", als);

			request.setAttribute("also", also);
			request.setAttribute("isreceiveselect", isreceiveselect);
			request.setAttribute("isauditselect", isauditselect);
			request.setAttribute("istranssaleselect", istranssaleselect);

//			DBUserLog.addUserLog(userid, "列表销售订单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
