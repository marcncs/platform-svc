package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppOutlayDetail;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.dao.OutlayDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdOutlayAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		super.initdata(request);
		try {
			AppOutlay aol = new AppOutlay();
			Outlay ol = aol.getOutlayByID(id);
			Outlay oldW = (Outlay) BeanUtils.cloneBean(ol);
			ol.setOutlayid(RequestTool.getInt(request, "outlayid"));
			ol.setOutlaydept(RequestTool.getInt(request, "outlaydept"));
			ol.setCastdept(RequestTool.getInt(request, "castdept"));
			ol.setCaster(RequestTool.getInt(request, "caster"));
			double totaloutlay = 0.00;
			String strthisresist = request.getParameter("thisresist");
			String strfactpay = request.getParameter("factpay");
			// String strtotaloutlay = request.getParameter("totaloutlay");
			ol.setThisresist(DataValidate.IsDouble(strthisresist) ? Double
					.valueOf(strthisresist) : 0d);
			ol.setFactpay(DataValidate.IsDouble(strfactpay) ? Double
					.valueOf(strfactpay) : 0d);
			ol.setFundsrc(RequestTool.getInt(request, "fundsrc"));
			ol.setRemark(request.getParameter("memo"));

			
			String stroutlayproject[] = request
					.getParameterValues("OutlayProject");
			String strremark[] = request.getParameterValues("remark");
			double outlaysum[] = RequestTool.getDoubles(request, "outlaysum");
			String voucher[] = request.getParameterValues("voucher");

			Integer outlayproject;
			String remark;

			AppOutlayDetail aod = new AppOutlayDetail();

			aod.delOutlayByOID(id);
			for (int i = 0; i < stroutlayproject.length; i++) {
				outlayproject = Integer.valueOf(stroutlayproject[i]);
				remark = strremark[i];
				// attachemployee = strattachemployee[i];

				totaloutlay += outlaysum[i];
				OutlayDetail od = new OutlayDetail();
				od.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"outlay_detail", 0, "")));
				od.setOid(id);
				od.setOutlayprojectid(outlayproject);
				od.setRemark(remark);
				od.setVoucher(voucher[i]);
				od.setOutlaysum(outlaysum[i]);

				aod.addOutlayDetail(od);
			}
			ol.setTotaloutlay(totaloutlay);
			aol.updOutlay(ol);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 9, "费用报销>>修改费用,编号：" + id, oldW, ol);
			return mapping.findForward("result");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
