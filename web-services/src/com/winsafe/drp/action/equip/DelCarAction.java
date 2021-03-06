package com.winsafe.drp.action.equip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.Car;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelCarAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String cid = request.getParameter("CID");
			AppCar aso = new AppCar();
			Car so = aso.getCarByID(cid);
			if (so.getIsleisure() == 1) {
				request.setAttribute("result", "对不起，该车已被使用不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			 aso.delCar(cid);


			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 8, "删除车辆资料,车牌:"+so.getCarbrand(), so);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
