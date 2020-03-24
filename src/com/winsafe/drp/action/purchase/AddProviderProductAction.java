package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.ProviderProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddProviderProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String pid = (String) request.getSession().getAttribute("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");

			Integer unitid;
			Double unitprice;
			for (int i = 0; i < strproductid.length; i++) {
				unitid = Integer.valueOf(strunitid[i]);
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}
				ProviderProduct pp = new ProviderProduct();
				pp.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"provider_product", 0, "")));
				pp.setProviderid(pid);
				pp.setProductid(strproductid[i]);
				pp.setPvproductname(strproductname[i]);
				pp.setPvspecmode(strspecmode[i]);
				pp.setCountunit(unitid);
				pp.setBarcode("");
				pp.setPrice(unitprice);
				pp.setPricedate(DateUtil.getCurrentDate());

				AppProviderProduct app = new AppProviderProduct();
				app.addProviderProduct(pp);
			}

			request.setAttribute("result", "databases.add.success");

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "供应商资料>>新增供应商产品,供应商编号：" + pid);

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
