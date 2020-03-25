package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.util.DBUserLog;

public class DelSampleBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String sbid = request.getParameter("SBID");
			AppSampleBill asb = new AppSampleBill();
			SampleBill sb = asb.findByID(sbid);
			SampleBill oldsb = (SampleBill) BeanUtils.cloneBean(sb);
			if (sb.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			
			AppSampleBillDetail asbd = new AppSampleBillDetail();
			asbd.deleteBySbid(sbid);
			asb.delete(sb);
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid,5, "会员管理>>删除样品记录,编号："+sbid,oldsb,sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
