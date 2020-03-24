package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.MakeCode;

public class SetOrganProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String oid = (String) request.getSession().getAttribute("sjoid");
		try {
			String[] pid = request.getParameterValues("pid");
			OrganService os = new OrganService();
			AppOrganProduct aop = new AppOrganProduct();
//			AppOrganPriceii appprice = new AppOrganPriceii();
			OrganProduct op = new OrganProduct();
			for (int i = 0; i < pid.length; i++) {
				op.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"organ_product", 0, "")));
				op.setOrganid(oid);
				op.setProductid(pid[i]);
				aop.addOrganProductNoExist(op);
//				appprice.delOrganPriceiiByOIDPID(oid, pid[i]);
//				appprice.copyProductPriceii(oid, pid[i], os.getOrganByID(oid)
//						.getRate());
			}

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			// DBUserLog.addUserLog(userid,"设置机构选择产品");
			request.setAttribute("result", "databases.add.success");
			request.setAttribute("forward",
					"../users/listOrganProductForSelectAction.do?OID=" + oid);
			return mapping.findForward("set");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
