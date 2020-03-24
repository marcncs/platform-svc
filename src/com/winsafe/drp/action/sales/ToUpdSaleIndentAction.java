package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentDetail;
import com.winsafe.drp.dao.SaleIndentDetailForm;
import com.winsafe.drp.dao.SaleIndentForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		

		initdata(request);
		try {
			AppSaleIndent aso = new AppSaleIndent();
			AppLinkMan appLinkMan = new AppLinkMan();
			SaleIndent so = new SaleIndent();
			AppCustomer ac = new AppCustomer();
			AppUsers au = new AppUsers();
			so = aso.getSaleIndentByID(id);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			SaleIndentForm sof = new SaleIndentForm();
			sof.setId(so.getId());
			sof.setCustomerbillid(so.getCustomerbillid());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());

			sof.setConsignmentdate(DateUtil.formatDate(so
							.getConsignmentdate()));

			sof.setPaymentmode(so.getPaymentmode());

			sof.setTotalsum(so.getTotalsum());
			sof.setTransportmode(so.getTransportmode());

			sof.setReceiveman(so.getReceiveman());
			sof.setTel(so.getTel());
			sof.setRemark(so.getRemark());

			AppSaleIndentDetail asld = new AppSaleIndentDetail();
			List slls = asld.getSaleIndentDetailBySIID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < slls.size(); i++) {
				SaleIndentDetailForm sodf = new SaleIndentDetailForm();
				SaleIndentDetail o = (SaleIndentDetail) slls.get(i);
				sodf.setProductid(o.getProductid());
				sodf.setProductname(o.getProductname());
				sodf.setSpecmode(o.getSpecmode());
				sodf.setUnitid(Integer.valueOf(o.getUnitid()));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid().intValue()));
				sodf.setUnitprice(Double.valueOf(o.getUnitprice()));
				sodf.setQuantity(Double.valueOf(o.getQuantity()));
				sodf.setSubsum(Double.valueOf(o.getSubsum()));
				sodf.setDiscount(o.getDiscount());
				sodf.setTaxrate(o.getTaxrate());
				als.add(sodf);
			}

			

			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
