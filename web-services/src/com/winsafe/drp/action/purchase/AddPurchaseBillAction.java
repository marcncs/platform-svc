package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPurchaseBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String pid = request.getParameter("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			PurchaseBill pb = new PurchaseBill();
			String pbid = MakeCode.getExcIDByRandomTableName("purchase_bill",
					2, "PO");
			pb.setId(pbid);
			pb.setPpid("");
			pb.setPid(pid);
			pb.setPname(request.getParameter("pname"));
			AppProvider appProvider = new AppProvider();
			pb.setPrompt(appProvider.getProviderPrompt(pid));
			pb.setPlinkman(request.getParameter("plinkman"));
			pb.setTel(request.getParameter("tel"));
			pb.setPurchasedept(RequestTool.getInt(request, "purchasedept"));
			pb.setPurchaseid(RequestTool.getInt(request, "purchaseid"));
			pb.setPaymode(RequestTool.getInt(request, "paymode"));			
			pb.setReceivedate(RequestTool.getDate(request, "receivedate"));
			pb.setInvmsg(RequestTool.getInt(request, "invmsg"));
			pb.setIsmaketicket(0);
			pb.setReceiveaddr(request.getParameter("receiveaddr"));
			pb.setMakeorganid(users.getMakeorganid());
			pb.setMakedeptid(users.getMakedeptid());
			pb.setMakeid(userid);
			pb.setMakedate(DateUtil.getCurrentDate());
			pb.setIsaudit(0);
			pb.setAuditid(Integer.valueOf(0));
			pb.setIsratify(0);
			pb.setRatifyid(Integer.valueOf(0));
			pb.setRemark(request.getParameter("remark"));
			pb.setIstransferadsum(0);
			pb.setKeyscontent(pb.getId()+","+pb.getPid()+","+pb.getPname()+","+pb.getTel());

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			Date requiredate[] = RequestTool.getDates(request, "requiredate");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double totalsum = 0.00;

			AppProviderProduct app = new AppProviderProduct();
			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();
			if (productid != null) {
				for (int i = 0; i < productid.length; i++) {
					PurchaseBillDetail pbd = new PurchaseBillDetail();
					pbd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("purchase_bill_detail",
									0, "")));
					pbd.setPbid(pbid);
					pbd.setProductid(productid[i]);
					pbd.setProductname(productname[i]);
					pbd.setSpecmode(specmode[i]);
					pbd.setUnitid(unitid[i]);
					pbd.setRequiredate(requiredate[i]);
					pbd.setUnitprice(unitprice[i]);
					pbd.setQuantity(quantity[i]);
					pbd.setSubsum(pbd.getUnitprice() * pbd.getQuantity());
					pbd.setIncomequantity(0d);
					apbd.addPurchaseBillDetail(pbd);
					totalsum += pbd.getSubsum();

					app.UpdProviderProduct(pbd.getUnitprice(), pid, productid[i]);

				}
			}
			pb.setTotalsum(totalsum);			
			AppPurchaseBill apa = new AppPurchaseBill();
			apa.addPurchaseBill(pb);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>新增采购单,编号：" + pb.getId());

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
