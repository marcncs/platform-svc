package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppInvoiceConf;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06 www.winsafe.cn
 */
public class DetailAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			String ISAUDIT = request.getParameter("ISAUDIT");
			AppAlterMoveApplyDetail appAMAD = new AppAlterMoveApplyDetail();
			List listAmad = appAMAD.getAlterMoveApplyDetailByAmID(id);
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AlterMoveApply ama = appAMA.getAlterMoveApplyByID(id);
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
