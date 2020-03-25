package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdPurchaseBillAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("pid");
		if (pid == null || pid.equals("null") || pid.equals("")) {
			request.setAttribute("result", "databases.upd.fail");
			return new ActionForward("/sys/lockrecord.jsp");
		}
		
		AppPurchaseBill apb = new AppPurchaseBill();
		String id = request.getParameter("ID");

		PurchaseBill pb = apb.getPurchaseBillByID(id);
		PurchaseBill oldpb = (PurchaseBill) BeanUtils.cloneBean(pb);
		pb.setId(id);
		pb.setPid(pid);
		pb.setPname(request.getParameter("pname"));
		AppProvider appProvider = new AppProvider();
		pb.setPrompt(appProvider.getProviderPrompt(pid));
		pb.setPlinkman(request.getParameter("plinkman"));
		pb.setPurchasedept(RequestTool.getInt(request, "purchasedept"));
		pb.setPurchaseid(RequestTool.getInt(request, "purchaseid"));
		pb.setPaymode(RequestTool.getInt(request, "paymode"));
		pb.setInvmsg(RequestTool.getInt(request, "invmsg"));
		pb.setReceivedate(RequestTool.getDate(request, "receivedate"));
		pb.setReceiveaddr(request.getParameter("receiveaddr"));
		pb.setRemark(request.getParameter("remark"));	
		pb.setKeyscontent(pb.getId()+","+pb.getPid()+","+pb.getPname()+","+pb.getTel());
		

		
		String productid[] = request.getParameterValues("productid");
		String productname[] = request.getParameterValues("productname");
		String specmode[] = request.getParameterValues("specmode");
		int unitid[] = RequestTool.getInts(request, "unitid");
		Date requiredate[] = RequestTool.getDates(request, "requiredate");
		double unitprice[] = RequestTool.getDoubles(request, "unitprice");
		double quantity[] = RequestTool.getDoubles(request, "quantity");
		double totalsum = 0.00;

		
		try {

			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();
			AppProviderProduct app = new AppProviderProduct();
			apbd.delPurchaseBillDetailByPbID(id);
			if (productid != null) {
			for (int i = 0; i < productid.length; i++) {
				PurchaseBillDetail pbd = new PurchaseBillDetail();
				pbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"purchase_bill_detail", 0, "")));
				pbd.setPbid(id);
				pbd.setProductid(productid[i]);
				pbd.setProductname(productname[i]);
				pbd.setSpecmode(specmode[i]);
				pbd.setUnitid(unitid[i]);
				pbd.setRequiredate(requiredate[i]);
				pbd.setUnitprice(unitprice[i]);
				pbd.setQuantity(quantity[i]);
				pbd.setIncomequantity(0d);
				pbd.setSubsum(pbd.getQuantity()*pbd.getUnitprice());
				apbd.addPurchaseBillDetail(pbd);
				app.UpdProviderProduct(unitprice[i], pid, productid[i]);
				totalsum+=pbd.getSubsum();

			}
			}
			pb.setTotalsum(totalsum);
			apb.updPurchaseBillByID(pb);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,2,"采购管理>>修改采购单,编号 ："+id,oldpb,pb);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
