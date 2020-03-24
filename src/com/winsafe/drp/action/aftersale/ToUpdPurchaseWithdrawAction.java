package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.dao.PurchaseWithdrawDetailForm;
import com.winsafe.drp.dao.PurchaseWithdrawForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdPurchaseWithdrawAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean usersBean = UserManager.getUser(request);
		Integer userid = usersBean.getUserid();
		try {

			AppPurchaseWithdraw aso = new AppPurchaseWithdraw();
			PurchaseWithdraw so = aso.getPurchaseWithdrawByID(id);
			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			PurchaseWithdrawForm sof = new PurchaseWithdrawForm();
			sof.setId(so.getId());
			sof.setPid(so.getPid());
			sof.setPname(so.getPname());
			sof.setPlinkman(so.getPlinkman());
			sof.setTel(so.getTel());
			sof.setWarehouseid(so.getWarehouseid());
			sof.setWithdrawcause(so.getWithdrawcause());
			sof.setTotalsum(so.getTotalsum());

			AppPurchaseWithdrawDetail asld = new AppPurchaseWithdrawDetail();
			List slls = asld.getPurchaseWithdrawDetailByPWID(id);
			ArrayList als = new ArrayList();

			PurchaseWithdrawDetail wd = null;
			PurchaseWithdrawDetailForm sldf = null;
			for (int i = 0; i < slls.size(); i++) {
				wd = (PurchaseWithdrawDetail) slls.get(i);
				sldf = new PurchaseWithdrawDetailForm();
				sldf.setProductid(wd.getProductid());
				sldf.setProductname(wd.getProductname());
				sldf.setSpecmode(wd.getSpecmode());
				sldf.setUnitid(wd.getUnitid());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", wd.getUnitid()));
				sldf.setBatch(wd.getBatch());
				sldf.setUnitprice(wd.getUnitprice());
				sldf.setQuantity(wd.getQuantity());
				sldf.setSubsum(wd.getSubsum());
				als.add(sldf);
			}

			
			AppWarehouse aw = new AppWarehouse();
			List wls = aw.getEnableWarehouseByVisit(userid);
			

			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
			request.setAttribute("alw", wls);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
