package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddPaymentApplyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			AppDept ad = new AppDept();
			List aldept = ad.getDeptByOID(users.getMakeorganid());
			
			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			

			String paymentmodename = Internation.getSelectPayByAllDB(
					"paymentmode", false);

			request.setAttribute("aldept", aldept);
			request.setAttribute("als", als);
			request.setAttribute("paymentmodename", paymentmodename);
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// HibernateUtil.closeSession();
		}

		return mapping.getInputForward();
	}

}
