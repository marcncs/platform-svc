package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetailForm;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdPurchaseBillAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
		try {
			AppPurchaseBill apb = new AppPurchaseBill();
			AppProvider apv = new AppProvider();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			PurchaseBill pb = apb.getPurchaseBillByID(id);

			if (pb.getIsaudit() == 1) { 
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}

			PurchaseBillForm pbf = new PurchaseBillForm();
			pbf.setId(id);
			pbf.setPpid(pb.getPpid());
			// pbf.setPurchasesortname(Internation.getSelectTagByKeyAllDBDef("PurchaseSort",
			// "purchasesort", pb.getPurchasesort()));
			pbf.setPid(pb.getPid());
			pbf.setPname(pb.getPname());
			pbf.setPlinkman(pb.getPlinkman());
			pbf.setTel(pb.getTel());
			pbf.setPurchasedept(pb.getPurchasedept());
			// pbf.setPurchasedeptname(ad.getDeptByID(id))
			pbf.setPurchaseid(pb.getPurchaseid());
			pbf.setPaymode(pb.getPaymode());
			pbf.setInvmsg(pb.getInvmsg());
			pbf.setTotalsum(pb.getTotalsum());
			pbf.setReceivedate(pb.getReceivedate());
			pbf.setReceiveaddr(pb.getReceiveaddr());
			pbf.setRemark(pb.getRemark());

			AppPurchaseBillDetail apad = new AppPurchaseBillDetail();
			List padls = apad.getPurchaseBillDetailByPbID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < padls.size(); i++) {
				PurchaseBillDetailForm pbdf = new PurchaseBillDetailForm();
				PurchaseBillDetail o = (PurchaseBillDetail) padls.get(i);
				pbdf.setProductid(o.getProductid());
				pbdf.setProductname(o.getProductname());
				pbdf.setSpecmode(o.getSpecmode());
				pbdf.setUnitid(o.getUnitid());
				pbdf.setUnitname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid()));
				pbdf.setRequiredate(o.getRequiredate());
				pbdf.setUnitprice(o.getUnitprice());
				pbdf.setQuantity(o.getQuantity());
				pbdf.setSubsum(o.getSubsum());
				als.add(pbdf);
			}

			List aldept = ad.getDeptByOID(users.getMakeorganid());
			

			List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			

			AppInvoiceConf aic = new AppInvoiceConf();
			List ils = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < ils.size(); u++) {
				InvoiceConf ic = (InvoiceConf) ils.get(u);

				icls.add(ic);
			}
			request.setAttribute("icls", icls);

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
