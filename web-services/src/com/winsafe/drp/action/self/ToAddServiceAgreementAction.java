package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.hbm.util.Internation;

public class ToAddServiceAgreementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String scontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "scontent", false);
			String sstatusselect = Internation.getSelectTagByKeyAllDB(
					"SStatus", "sstatus", false);
			String rankselect = Internation.getSelectTagByKeyAllDB(
					"Rank", "rank", false);
			
			request.setAttribute("scontentselect", scontentselect);
			request.setAttribute("sstatusselect", sstatusselect);
			request.setAttribute("rankselect", rankselect);

			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//HibernateUtil.closeSession();
		}
		return mapping.getInputForward();
	}

}
