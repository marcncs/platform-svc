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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListPayableAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			String gz = request.getParameter("GreatZero");
			if (gz == null || gz.equals("")) {
				gz = "0";
			}

			String Condition = " (pa.makeid='" + userid + "' "
					+ getOrVisitOrgan("pa.makeorganid") + ") ";

			if (gz.equals("1")) {
				Condition += " and (pa.payablesum-pa.alreadysum)!=0 and ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Payable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "BillNo");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppPayable apa = new AppPayable();
			UsersService au = new UsersService();
			OrganService ao = new OrganService();
			AppCustomer ac = new AppCustomer();
			AppProvider ap = new AppProvider();

			List pbls = apa.getPayable(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			AppPayableObject apo = new AppPayableObject();
			Integer objectsort;
			String payableobjectname = "";
			for (int i = 0; i < pbls.size(); i++) {
				PayableForm pf = new PayableForm();
				Payable o = (Payable) pbls.get(i);
				pf.setId(o.getId());
				pf.setPoid(o.getPoid());
				objectsort = apo.getPayableObjectByOIDOrgID(o.getPoid(),
						o.getMakeorganid()).getObjectsort();
				if (objectsort == 0) {
					payableobjectname = ao.getOrganName(o.getPoid());
				}
				if (objectsort == 1) {
					payableobjectname = ac.getCustomer(o.getPoid()).getCname();
				}
				if (objectsort == 2) {
					payableobjectname = ap.getProviderByID(o.getPoid())
							.getPname();
				}
				pf.setPayableobjectname(payableobjectname);
				pf.setSettlementsum(o.getPayablesum());
				pf.setPayablesum(o.getPayablesum() - o.getAlreadysum());
				pf.setBillno(o.getBillno());
				if (pf.getBillno().length() > 0) {
					pf.setBn(pf.getBillno().substring(0, 2));
				} else {
					pf.setBn("");
				}
				pf.setMakeorganid(o.getMakeorganid());
				pf.setMakeidname(au.getUsersName(o.getMakeid()));
				pf.setMakedate(DateUtil.formatDateTime(o.getMakedate()));
				pf.setAwakedate(o.getAwakedate());
				int a = 0;
				if (o.getAwakedate() != null) {
					a = DateUtil.getDayDifference(DateUtil
							.getCurrentDateString(), DateUtil.formatDate(o
							.getAwakedate()));
				}
				if (a > 0) {
					pf.setOverage(a);
				}
				pf.setPayabledescribe(o.getPayabledescribe());
				alpl.add(pf);
			}

			request.setAttribute("alpl", alpl);

			 DBUserLog.addUserLog(userid,9,"账务管理>>列表应付款结算");
			return mapping.findForward("payableall");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
