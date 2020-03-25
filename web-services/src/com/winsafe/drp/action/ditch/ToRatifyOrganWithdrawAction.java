package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06 www.winsafe.cn
 */
public class ToRatifyOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ama = appAMA.getOrganWithdrawByID(id);
			
			if (ama.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noauditnoratify");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
			List<OrganWithdrawDetail> listAmad = appAMAD.getOrganWithdrawDetailByOwid(id);
			
			UsersBean users = UserManager.getUser(request);
			request.setAttribute("organid", users.getMakeorganid());
			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
