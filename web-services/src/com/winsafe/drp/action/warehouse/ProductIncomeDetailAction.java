package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;

public class ProductIncomeDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		String type = request.getParameter("type");
		super.initdata(request);
		try {
			AppProductIncome api = new AppProductIncome();
			ProductIncome pi = api.getProductIncomeByID(id);
			AppProduct ap = new AppProduct();
			AppFUnit af = new AppFUnit();

			AppProductIncomeDetail aspb = new AppProductIncomeDetail();
			List<ProductIncomeDetail> spils = aspb.getProductIncomeDetailByPbId(id);
//			for(ProductIncomeDetail pid :spils ){
//				pid.setNccode(ap.getProductByID(pid.getProductid()).getNccode());
//				if(pid.getUnitid()==1){
//					pid.setBoxQuantity(pid.getQuantity());
//				}else{
//					pid.setBoxQuantity(ArithDouble.div(pid.getQuantity(), af.getXQuantity(pid.getProductid(), 1)));
//				}
//				pid.setBoxQuantity(0d);
//			}

			request.setAttribute("als", spils);
			request.setAttribute("pif", pi);
			request.setAttribute("type", type);
			request.setAttribute("flag", request.getParameter("flag"));

			DBUserLog.addUserLog(userid, 7, "产成品入库>>产成品入库详情,编号:" + id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
