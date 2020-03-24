package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppOutlayDetail;
import com.winsafe.drp.dao.Outlay;
import com.winsafe.drp.dao.OutlayDetail;
import com.winsafe.drp.dao.OutlayDetailForm;
import com.winsafe.drp.dao.OutlayForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class OutlayDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
		      AppOutlay ao = new AppOutlay();     
//		      AppUsers au = new AppUsers();
//		      AppDept ad = new AppDept();
		      AppCashBank apcb= new AppCashBank();

		      Outlay ol = ao.getOutlayByID(id);
		      OutlayForm olf= new OutlayForm();
		      olf.setId(id);
		      olf.setOutlayid(ol.getOutlayid());
//				olf.setOutlayidname(au.getUsersByid(ol.getOutlayid()).getRealname());
		      olf.setOutlaydept(ol.getOutlaydept());
//				olf.setOutlaydeptname(ad.getDeptByID(ol.getOutlaydept()).getDeptname());
		      olf.setCastdept(ol.getCastdept());
//				olf.setCastdeptname(ad.getDeptByID(ol.getCastdept()).getDeptname());
		      olf.setCaster(ol.getCaster());
//				olf.setCastername(au.getUsersByid(ol.getCaster()).getRealname());
				olf.setTotaloutlay(ol.getTotaloutlay());
				olf.setThisresist(ol.getThisresist());
				olf.setFundsrcname(apcb.getCashBankById(ol.getFundsrc()).getCbname());
				olf.setFactpay(ol.getFactpay());
				olf.setRemark(ol.getRemark());
				olf.setIsaudit(ol.getIsaudit());
//				olf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//						request, ol.getIsaudit(), "global.sys.SystemResource"));
				olf.setAuditid(ol.getAuditid());
//				if (ol.getAuditid() > 0) {
//					olf.setAuditidname(au.getUsersByid(ol.getAuditid())
//							.getRealname());
//				} else {
//					olf.setAuditidname("");
//				}
				olf.setAuditdate(DateUtil.formatDate(ol.getAuditdate()));
				olf.setIsendcase(ol.getIsendcase());
				olf.setEndcaseid(ol.getEndcaseid());
//				olf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
//						request, ol.getIsendcase(), "global.sys.SystemResource"));
//				if (ol.getEndcaseid() !=null && ol.getEndcaseid() > 0) {
//					olf.setEndcaseidname(au.getUsersByid(ol.getEndcaseid())
//							.getRealname());
//				} else {
//					olf.setEndcaseidname("");
//				}
				olf.setEndcasedate(DateUtil.formatDate(ol.getEndcasedate()));		
				olf.setMakeid(ol.getMakeid());
//				olf.setMakeidname(au.getUsersByid(ol.getMakeid()).getRealname());
				olf.setMakedate(DateUtil.formatDate(ol.getMakedate()));
				AppOutlayDetail aod = new AppOutlayDetail();
				List wdls = aod.getOutlayDetailByOID(id);
				ArrayList als = new ArrayList();

				for (int i = 0; i < wdls.size(); i++) {
					OutlayDetailForm odf = new OutlayDetailForm();
					OutlayDetail o = (OutlayDetail) wdls.get(i);
					odf.setId(o.getId());
					odf.setOutlayprojectidname(Internation.getStringByKeyPositionDB("OutlayProject",
							o.getOutlayprojectid()));					
					odf.setOutlaysum(o.getOutlaysum());
					odf.setVoucher(o.getVoucher());
					odf.setRemark(o.getRemark());
					als.add(odf);
				}

		      request.setAttribute("als",als);
		      request.setAttribute("olf",olf);

		     // DBUserLog.addUserLog(userid,9,"费用报销>>费用详情,编号："+id); 
		      return mapping.findForward("detail");
		    } catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
