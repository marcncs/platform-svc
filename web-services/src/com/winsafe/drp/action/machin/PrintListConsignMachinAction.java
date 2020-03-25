package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintListConsignMachinAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String Condition = " (cm.makeid='" + userid + "' "
					+ getOrVisitOrgan("cm.makeorganid") + ") ";

			String[] tablename = { "ConsignMachin" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("CompleteIntendDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppUsers au = new AppUsers();
			AppProvider ap = new AppProvider();
			AppConsignMachin api = new AppConsignMachin();
			List pils = api.getConsignMachin(whereSql);
			ArrayList alpi = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				ConsignMachin a = (ConsignMachin) pils.get(i);
				ConsignMachinForm af = new ConsignMachinForm();
				af.setId(a.getId());
				af.setPidname(ap.getProviderByID(a.getPid()).getPname());
				af.setCproductid(a.getCproductid());
				af.setCproductname(a.getCproductname());
				af.setCspecmode(a.getCspecmode().length() > 10 ? a
						.getCspecmode().substring(0, 10)
						+ "..." : a.getCspecmode());
				af.setCunitidname(HtmlSelect.getResourceName(request,
						"CountUnit", a.getCunitid()));
				af.setCquantity(a.getCquantity());
				af.setCompleteintenddate(DateUtil.formatDate(a
						.getCompleteintenddate()));
				af.setMakeidname(au.getUsersByID(a.getMakeid()).getRealname());
				af.setIsaudit(a.getIsaudit());
				af.setIsendcase(a.getIsendcase());
				alpi.add(af);
			}

			request.setAttribute("alpi", alpi);
			 DBUserLog.addUserLog(userid,3,"生产组装>>打印委外加工单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
