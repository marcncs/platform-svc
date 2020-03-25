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
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddCarAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {

			String carbrand = request.getParameter("carbrand");
			Integer carsort = Integer.valueOf(request.getParameter("carsort"));
			String worth = request.getParameter("worth");
			String purchasedate = request.getParameter("purchasedate");

			Car so = new Car();
			String cid = MakeCode.getExcIDByRandomTableName("car", 0, "");
			so.setId(Integer.valueOf(cid));
			so.setPurchasedate(DateUtil.StringToDate(purchasedate));
			so.setCarbrand(carbrand);
			so.setCarsort(carsort);
			so.setIsleisure(0);
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			so.setMakeid(userid);
			so.setWorth(DataValidate.IsDouble(worth) ? Double.valueOf(worth)
					: 0d);

			AppCar asl = new AppCar();
			asl.addCar(so);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 8, "新增车辆资料,车牌："+carbrand);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();

		}

		return new ActionForward(mapping.getInput());
	}

}
