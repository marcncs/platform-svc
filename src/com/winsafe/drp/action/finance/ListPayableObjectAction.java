package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayableObjectForm;
import com.winsafe.drp.dao.RecevablePayableService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ListPayableObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		OrganService ao = new OrganService();
		try {

			String Condition = " (po.makeid='" + userid + "' "
					+ getOrVisitOrgan("po.makeorganid") + ") ";
			String[] tablename = { "PayableObject" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppPayableObject apo = new AppPayableObject();
			UsersService au = new UsersService();
			AppCustomer ac = new AppCustomer();
			AppProvider ap = new AppProvider();

			List pbls = apo.getPayableObject(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			String oid = "";
			for (int i = 0; i < pbls.size(); i++) {
				PayableObject po = (PayableObject) pbls.get(i);
				PayableObjectForm pof = new PayableObjectForm();
				oid = po.getOid();
				pof.setOid(oid);
				pof.setObjectsort(po.getObjectsort());
				pof.setObjectsortname(HtmlSelect.getNameByOrder(request,
						"ObjectSort", po.getObjectsort()));

				if (pof.getObjectsort() == 0) {
					pof.setPayee(ao.getOrganName(oid));
				}
				if (pof.getObjectsort() == 1) {
					pof.setPayee(ac.getCustomer(oid).getCname());
				}
				if (pof.getObjectsort() == 2) {
					pof.setPayee(ap.getPName(oid));
				}

				RecevablePayableService aprd = new RecevablePayableService(oid,
						po.getMakeorganid(), map.get("BeginDate").toString(),
						map.get("EndDate").toString());
				pof.setPrevioussum(DataFormat.dataFormat(aprd
						.getPrevioussumByP()));
				pof.setCurrentsum(DataFormat.dataFormat(aprd
						.getPayableCurrentSum()));
				pof.setCurrentalreadysum(DataFormat.dataFormat(aprd
						.getPayableCurrentAlreadySum()));
				pof.setWaitpayablesum(DataFormat.dataFormat(pof
						.getPrevioussum()
						+ pof.getCurrentsum() - pof.getCurrentalreadysum()));
				pof.setMakeorganid(po.getMakeorganid());
				pof.setMakeorganidname(ao.getOrganName(po.getMakeorganid()));
				pof.setMakeidname(au.getUsersName(po.getMakeid()));
				pof.setMakedate(DateUtil.formatDate(po.getMakedate()));

				alpl.add(pof);
			}
			request.setAttribute("alpl", alpl);
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));

			DBUserLog.addUserLog(userid, 9, "账务管理>>列表应付款对象");
			return mapping.findForward("payableobject");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
