package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrderDetailForm;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseOrderProductListAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String poid = request.getParameter("PIID");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			AppPurchaseOrder api = new AppPurchaseOrder();
			PurchaseOrder pi = api.getPurchaseOrderByID(poid);
			PurchaseOrderForm pif = new PurchaseOrderForm();
			AppCustomer ap = new AppCustomer();
			pif.setId(poid);
			pif.setPid(pi.getPid());
			pif.setProvidename(ap.getCustomer(pi.getPid()).getCname());
			pif.setPlinkman(pi.getPlinkman());
			pif.setTel(pi.getTel());
//			pif.setPurchasedept(pi.getPurchasedept());
//			pif.setPurchaseid(pi.getPurchaseid());
			pif.setReceiveaddr(DateUtil.formatDate(pi.getReceivedate()));
			pif.setReceiveaddr(pi.getReceiveaddr());
			pif.setBatch(pi.getBatch());

			Double totalsum = 0.00;
			AppPurchaseOrderDetail apad = new AppPurchaseOrderDetail();
			List ppdls = apad.getPurchaseOrderDetailByPoID(poid);
			ArrayList als = new ArrayList();

			PurchaseOrderDetail pod = null;
			for (int i = 0; i < ppdls.size(); i++) {
				pod = (PurchaseOrderDetail) ppdls.get(i);
				PurchaseOrderDetailForm pbdf = new PurchaseOrderDetailForm();
				pbdf.setProductid(pod.getProductid());
				pbdf.setProductname(pod.getProductname());
				pbdf.setSpecmode(pod.getSpecmode());
				pbdf.setUnitid(pod.getUnitid());
				pbdf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", pod.getUnitid()));
				pbdf.setUnitprice(pod.getUnitprice());
				pbdf.setQuantity(pod.getQuantity());
				pbdf.setIncomequantity(pod.getIncomequantity());
				pbdf.setSubsum(pod.getSubsum());
				totalsum += pbdf.getSubsum();
				als.add(pbdf);
			}
			
			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
			
			String incomesortname = Internation.getSelectTagByKeyAll(
					"PurchaseIncomeSort", request, "incomesort", false, null);

			request.setAttribute("alw", alw);
			request.setAttribute("incomesortname", incomesortname);
			request.setAttribute("pif", pif);
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("piid", poid);
			request.setAttribute("als", als);

//			DBUserLog.addUserLog(userid, "采购订单存货列表");
			return mapping.findForward("productlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
