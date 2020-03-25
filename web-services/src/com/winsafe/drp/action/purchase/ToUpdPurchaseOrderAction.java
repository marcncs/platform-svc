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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrderDetailForm;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdPurchaseOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
		try {
			AppPurchaseOrder apb = new AppPurchaseOrder();
			PurchaseOrder pb = new PurchaseOrder();
			AppCustomer apv = new AppCustomer();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			pb = apb.getPurchaseOrderByID(id);
			
			if(pb.getIsblankout()==1){
	          	 String result = "databases.record.blankoutnooperator";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			if (pb.getApprovestatus()!=0) { 
		        String result = "databases.record.lock";
		        request.setAttribute("result", result);
		        return mapping.findForward("lock");
		      }

			PurchaseOrderForm pbf = new PurchaseOrderForm();
			pbf.setId(id);
			pbf.setPpid(pb.getPpid());
			pbf.setPid(pb.getPid());
			pbf.setProvidename(apv.getCustomer(pb.getPid()).getCname());
			pbf.setPlinkman(pb.getPlinkman());
			pbf.setTel(pb.getTel());
			pbf.setBatch(pb.getBatch());
			pbf.setPaymentmodename(Internation.getSelectPayAllDBDef(
					"paymentmode", pb.getPaymentmode()));
			pbf.setTotalsum(pb.getTotalsum());
			pbf.setReceivedate(DateUtil.formatDate(pb.getReceivedate()));
			pbf.setReceiveaddr(pb.getReceiveaddr());
			pbf.setRemark(pb.getRemark());

			AppPurchaseOrderDetail apad = new AppPurchaseOrderDetail();
			List padls = apad.getPurchaseOrderDetailByPoID(id);
			ArrayList als = new ArrayList();
			AppProduct ap = new AppProduct();

			
			PurchaseOrderDetail pod = null;
			for (int i = 0; i < padls.size(); i++) {
				pod = (PurchaseOrderDetail) padls.get(i);
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
				als.add(pbdf);
			}

			List aldept = ad.getDeptByOID(users.getMakeorganid());
			

			List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			

			request.setAttribute("auls", auls);
			request.setAttribute("aldept", aldept);
			request.setAttribute("pbf", pbf);
			request.setAttribute("als", als);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
