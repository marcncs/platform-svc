package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncomeDetailForm;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseOrderProductListAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String poid = request.getParameter("PIID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			AppPurchaseOrder api = new AppPurchaseOrder();
			PurchaseOrder pi = api.getPurchaseOrderByID(poid);
			PurchaseOrderForm pif = new PurchaseOrderForm();
			AppCustomer apv = new AppCustomer();
			pif.setId(poid);
			pif.setPid(pi.getPid());
			pif.setProvidename(apv.getCustomer(pi.getPid()).getCname());
			pif.setPlinkman(pi.getPlinkman());
			pif.setTel(pi.getTel());
			pif.setReceiveaddr(DateUtil.formatDate(pi.getReceivedate()));
			pif.setReceiveaddr(pi.getReceiveaddr());
			pif.setBatch(pi.getBatch());

			Double totalsum = 0.00;			
			AppPurchaseIncomeDetail apppid = new AppPurchaseIncomeDetail();
			List ppdls = apppid.getPurchaseIncomeDetailByPoid(poid);
			ArrayList als = new ArrayList();

			PurchaseIncomeDetail pod = null;
			for (int i = 0; i < ppdls.size(); i++) {
				pod = (PurchaseIncomeDetail) ppdls.get(i);
				PurchaseIncomeDetailForm pbdf = new PurchaseIncomeDetailForm();
				pbdf.setProductid(pod.getProductid());
				pbdf.setProductname(pod.getProductname());
				pbdf.setSpecmode(pod.getSpecmode());
				pbdf.setUnitid(pod.getUnitid());
				pbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", pod.getUnitid()));
				pbdf.setUnitprice(pod.getUnitprice());
				pbdf.setQuantity(pod.getQuantity());
				pbdf.setSubsum(pod.getSubsum());
				totalsum += pbdf.getSubsum();
				als.add(pbdf);
			}

			AppDept ad = new AppDept();
			List aldept = ad.getDeptByOID(users.getMakeorganid());
			

			AppUsers au = new AppUsers();
			List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			

			request.setAttribute("auls", auls);
			request.setAttribute("aldept", aldept);
			request.setAttribute("pif", pif);
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("piid", poid);
			request.setAttribute("als", als);
			
			//DBUserLog.addUserLog(userid, "采购订单存货列表");
			return mapping.findForward("productlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
