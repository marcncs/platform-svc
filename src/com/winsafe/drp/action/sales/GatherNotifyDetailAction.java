package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppGatherNotify;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.GatherNotify;
import com.winsafe.drp.dao.GatherNotifyForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class GatherNotifyDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppGatherNotify aso = new AppGatherNotify();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			GatherNotify so = aso.getGatherNotifyByID(Long.valueOf(id));
			GatherNotifyForm sof = new GatherNotifyForm();

			sof.setId(so.getId());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setClinkman(so.getClinkman());
			sof.setTel(so.getTel());
//			sof.setSaledeptname(ad.getDeptByID(so.getSaledept()).getDeptname());
			sof.setBillno(so.getBillno());
			sof.setTotalsum(so.getTotalsum());
			sof.setMemo(so.getMemo());
//			sof.setSaleidname(au.getUsersByid(so.getSaleid()).getRealname());
			sof.setPaymentmodename(Internation.getStringByPayPositionDB(so.getPaymentmode()));
			sof.setBankaccount(so.getBankaccount());
//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedate(DateUtil.formatDate(so.getMakedate()));

			sof.setIsendcase(so.getIsendcase());
			sof.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsendcase(), "global.sys.SystemResource"));

			if (so.getEndcaseid() != null && so.getEndcaseid() > 0) {
//				sof.setEndcaseidname(au.getUsersByid(so.getEndcaseid())
//						.getRealname());
			} else {
				sof.setEndcaseidname("");
			}
			sof.setEndcasedate(DateUtil.formatDate(so.getEndcasedate()));

			request.setAttribute("sof", sof);
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "打款通知详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
