package com.winsafe.drp.action.equip;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.Car;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdCarAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {

			String carbrand = request.getParameter("carbrand");
			String id = request.getParameter("id");

			Integer carsort = Integer.valueOf(request.getParameter("carsort"));
			String worth = request.getParameter("worth");

			String purchasedate = request.getParameter("purchasedate");
			AppCar asl = new AppCar();
			Car so = asl.getCarByID(id);
			Car oldso = (Car) BeanUtils.cloneBean(so);
			so.setId(Integer.valueOf(id));
			String tmppurchasedate = purchasedate.replace('-', '/');
			if (tmppurchasedate != null && tmppurchasedate.trim().length() > 0) {
				so.setPurchasedate(new Date(tmppurchasedate));
			}

			so.setCarbrand(carbrand);
			so.setCarsort(carsort);
			so.setIsleisure(0);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			so.setMakeid(userid);
			so.setWorth(Double.valueOf(worth));

			asl.updCar(so);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 8, "修改车辆资料,车牌:" + so.getCarbrand(),
					oldso, so);
			return mapping.findForward("success");
		} catch (Exception e) {
			System.out.println("the exception is:" + e.toString());
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
