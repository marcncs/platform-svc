package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.dao.WithdrawDetail;
import com.winsafe.drp.dao.WithdrawDetailForm;
import com.winsafe.drp.dao.WithdrawForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {

			AppWithdraw aso = new AppWithdraw();
			Withdraw so = aso.getWithdrawByID(id);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			if (so.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			WithdrawForm sof = new WithdrawForm();
			sof.setId(so.getId());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setCmobile(so.getCmobile());
			sof.setClinkman(so.getClinkman());
			sof.setTel(so.getTel());
			sof.setWarehouseid(so.getWarehouseid());
			sof.setWithdrawcause(so.getWithdrawcause());
			sof.setTotalsum(so.getTotalsum());
			sof.setBillno(so.getBillno());
			sof.setPaymentmodename(Internation.getSelectPayAllDBDef(
					"paymentmode", so.getPaymentmode()));
			sof.setWithdrawsortname(Internation.getSelectTagByKeyAllDBDef(
					"WithdrawSort",  "withdrawsort", so.getWithdrawsort()));

			AppWithdrawDetail asld = new AppWithdrawDetail();
			List slls = asld.getWithdrawDetailByWID(id);
			ArrayList als = new ArrayList();

			WithdrawDetail wd = null;
			WithdrawDetailForm sldf = null;
			for (int i = 0; i < slls.size(); i++) {
				wd = (WithdrawDetail) slls.get(i);
				sldf = new WithdrawDetailForm();
				sldf.setProductid(wd.getProductid());
				sldf.setProductname(wd.getProductname());
				sldf.setSpecmode(wd.getSpecmode());
				sldf.setUnitid(wd.getUnitid());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", wd.getUnitid()));
				sldf.setUnitprice(wd.getUnitprice());
				sldf.setTaxunitprice(wd.getTaxunitprice());
				sldf.setQuantity(wd.getQuantity());
				sldf.setSubsum(wd.getSubsum());
				sldf.setDiscount(wd.getDiscount());
				sldf.setTaxrate(wd.getTaxrate());
				als.add(sldf);
			}

			
//			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}

			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
//			request.setAttribute("alw", alw);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
