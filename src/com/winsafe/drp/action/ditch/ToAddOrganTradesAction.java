package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:33:59 www.winsafe.cn
 */
public class ToAddOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			String organid = users.getMakeorganid();
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(organid);

			String parentid = organ.getParentid();
			if (parentid.equals("0")) {
				parentid = users.getMakeorganid();
			}
			request.setAttribute("makeorganid", users.getMakeorganid());
			request.setAttribute("organid", parentid);
			request.setAttribute("oaddr", os.getOrganByID(parentid).getOaddr());
			request.setAttribute("roaddr", organ.getOaddr());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("add");
	}

}
