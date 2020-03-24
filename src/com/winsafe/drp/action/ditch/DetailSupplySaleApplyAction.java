package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06 www.winsafe.cn
 */
public class DetailSupplySaleApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			String ISAUDIT = request.getParameter("ISAUDIT");
			AppSupplySaleApplyDetail appAMAD = new AppSupplySaleApplyDetail();
			List<SupplySaleApplyDetail> listAmad = appAMAD
					.getSupplySaleAplyBySSID(id);
			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			SupplySaleApply ama = appAMA.getSupplySaleApplyByID(id);

			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);

			AppInvoiceConf aic = new AppInvoiceConf();
			String invmsgname = aic.getInvoiceConfById(ama.getInvmsg())
					.getIvname();
			request.setAttribute("invmsgname", invmsgname);
			request.setAttribute("ISAUDIT", ISAUDIT);
			return mapping.findForward("detail");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
