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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.ReceivableObjectForm;
import com.winsafe.drp.dao.RecevablePayableService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintListReceivableObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		OrganService ao = new OrganService();
		try {

			String Condition = " (ro.makeid='" + userid + "' "
					+ getOrVisitOrgan("ro.makeorganid") + ") ";

			String[] tablename = { "ReceivableObject" };

			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppReceivableObject aro = new AppReceivableObject();
			AppCustomer ac = new AppCustomer();
			UsersService au = new UsersService();
			AppProvider ap = new AppProvider();

			String promisedate = "";
			List pbls = aro.getReceivableObject(whereSql);
			ArrayList alpl = new ArrayList();
			String oid = "";

			for (int i = 0; i < pbls.size(); i++) {
				ReceivableObjectForm rf = new ReceivableObjectForm();
				ReceivableObject o = (ReceivableObject) pbls.get(i);
				oid = o.getOid();
				rf.setOid(oid);
				rf.setObjectsort(o.getObjectsort());
				rf.setObjectsortname(HtmlSelect.getNameByOrder(request,
						"ObjectSort", o.getObjectsort()));
				if (rf.getObjectsort() == 0) {
					rf.setPayer(ao.getOrganName(oid));
				}
				if (rf.getObjectsort() == 1) {
					rf.setPayer(ac.getCustomer(oid).getCname());
				}
				if (rf.getObjectsort() == 2) {
					rf.setPayer(ap.getProviderByID(oid).getPname());
				}
				RecevablePayableService aprd = new RecevablePayableService(oid,
						o.getMakeorganid(), map.get("BeginDate").toString(),
						map.get("EndDate").toString());
				rf.setPrevioussum(DataFormat.dataFormat(aprd
						.getPrevioussumByRCV()));
				rf.setCurrentsum(DataFormat.dataFormat(aprd.getCurrentSum(-1)));
				rf.setCurrentalreadysum(DataFormat.dataFormat(aprd
						.getCurrentAlreadySum(-1)));
				rf.setWaitreceivablesum(DataFormat.dataFormat(rf
						.getPrevioussum()
						+ rf.getCurrentsum() - rf.getCurrentalreadysum()));
				rf.setMakeorganid(o.getMakeorganid());
				rf.setMakeorganidname(ao.getOrganName(o.getMakeorganid()));
				rf.setMakeidname(au.getUsersName(o.getMakeid()));
				rf.setMakedate(DateUtil.formatDate(o.getMakedate()));
				promisedate = String.valueOf(o.getPromisedate());
				if (promisedate != null && !promisedate.equals("null")
						&& promisedate.length() > 0) {
					rf.setPromisedate(promisedate.substring(0, 10));
				} else {
					rf.setPromisedate("");
				}

				alpl.add(rf);
			}
			request.setAttribute("alpl", alpl);
			DBUserLog.addUserLog(userid, 9, "账务管理>>打印应收款对象");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
