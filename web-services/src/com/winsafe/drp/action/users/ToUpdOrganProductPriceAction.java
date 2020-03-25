package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;

public class ToUpdOrganProductPriceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		try {
			String oid = request.getParameter("oid");
			OrganService os = new OrganService();	
			Organ o = os.getOrganByID(oid);
			if (!o.getSysid().startsWith(users.getOrgansysid()) || o.getSysid().equals(users.getOrgansysid()) ) {
				request.setAttribute("result", "对不起，您无权限修改产品订购价格！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			String opids = request.getParameter("opids").substring(1);
			AppOrganPriceii appod = new AppOrganPriceii();
			String where = " where  p.id=op.productid and op.organid=o.id and op.organid='"+oid+"' and op.id in("+opids+") ";
			List list = appod.findOrganPriceii(where);
			
			
			request.setAttribute("spals", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("toupd");
	}

}
