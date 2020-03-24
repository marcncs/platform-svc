package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppOutlayDetail;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.dao.OutlayDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddOutlayAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		AppApproveFlowService apfs = new AppApproveFlowService();
		List detaillist = apfs.getFlowDetail("O");

		try {
			Outlay ol = new Outlay();
			String oid = MakeCode.getExcIDByRandomTableName("outlay", 2, "OL");
			ol.setId(oid);

			ol.setOutlayid(RequestTool.getInt(request,"outlayid"));
			ol.setOutlaydept(RequestTool.getInt(request,"outlaydept"));
			ol.setCastdept(RequestTool.getInt(request,"castdept"));
			ol.setCaster(RequestTool.getInt(request,"caster"));
			double totaloutlay = 0.00;
			String strthisresist = request.getParameter("thisresist");
			String strfactpay = request.getParameter("factpay");
			// String strtotaloutlay = request.getParameter("totaloutlay");
			ol.setThisresist(DataValidate.IsDouble(strthisresist) ? Double
					.valueOf(strthisresist) : 0d);
			ol.setFactpay(DataValidate.IsDouble(strfactpay) ? Double
					.valueOf(strfactpay) : 0d);
			ol.setFundsrc(RequestTool.getInt(request,"fundsrc"));
			ol.setRemark(request.getParameter("memo"));
			ol.setIsaudit(0);
			ol.setAuditid(0);
			ol.setIsendcase(0);
			ol.setMakeid(userid);
			ol.setMakedate(DateUtil.getCurrentDate());
			ol.setMakedeptid(users.getMakedeptid());
			ol.setMakeorganid(users.getMakeorganid());

			AppOutlay aol = new AppOutlay();

			
			String stroutlayproject[] = request
					.getParameterValues("OutlayProject");
			String strremark[] = request.getParameterValues("remark");
			String stroutlaysum[] = request.getParameterValues("outlaysum");

			Double outlaysum;
			Integer outlayproject;
			String remark;
			AppOutlayDetail aod = new AppOutlayDetail();
			for (int i = 0; i < stroutlayproject.length; i++) {
				outlayproject = Integer.valueOf(stroutlayproject[i]);
				remark = strremark[i];
				// attachemployee = strattachemployee[i];
				if (DataValidate.IsDouble(stroutlaysum[i])) {
					outlaysum = Double.valueOf(stroutlaysum[i]);
				} else {
					outlaysum = Double.valueOf(0.00);
				}
				totaloutlay += outlaysum;
				OutlayDetail od = new OutlayDetail();
				od.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"outlay_detail", 0, "")));
				od.setOid(oid);
				od.setOutlayprojectid(outlayproject);
				od.setRemark(remark);
				od.setOutlaysum(outlaysum);

				aod.addOutlayDetail(od);
			}
			ol.setTotaloutlay(totaloutlay);
			aol.addOutlay(ol);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,9, "新增费用");

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			
		}

		return new ActionForward(mapping.getInput());
	}
}
