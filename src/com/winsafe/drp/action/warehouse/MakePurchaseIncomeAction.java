package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class MakePurchaseIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MakeCode mc = new MakeCode();
		UsersBean users = UserManager.getUser(request);
		AppPurchaseOrderDetail apppod = new AppPurchaseOrderDetail();
//		Long userid = users.getUserid();
		////Connection conn = null;
		super.initdata(request);try{

			PurchaseIncome pb = new PurchaseIncome();
			String pbid = mc
					.getExcIDByRandomTableName("purchase_income", 2, "PI");
			pb.setId(pbid);
			String poid = request.getParameter("poid");
			pb.setPoid(poid);
			String providerid = request.getParameter("pvid");
//			pb.setWarehouseid(Long.parseLong(request.getParameter("warehouseid")));
			pb.setProvideid(providerid);
			pb.setProvidename(request.getParameter("providename"));
			pb.setPlinkman(request.getParameter("plinkman"));
			pb.setTel(request.getParameter("tel"));
//			pb.setBatch("");
//			pb.setIncomesort(Integer.parseInt(request.getParameter("incomesort")));
			pb.setIncomedate(DateUtil.StringToDate(request
					.getParameter("incomedate")));
			pb.setTotalsum(0.00);
			pb.setIsaudit(0);
//			pb.setMakeid(userid);
			pb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			pb.setRemark("采购订单转采购入库");

			String[] strpid = request.getParameterValues("pid");
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] stroperatorquantity = request.getParameterValues("operatorquantity");
			String[] strsubsum = request.getParameterValues("subsum");

			String productid;
			Double price, subsum, operatorquantity;
			Double totalsum = 0.00;			

			
			List podlist = apppod.getPurchaseOrderDetailByPoID(poid);
			PurchaseOrderDetail pod =null;
			int totalquantity=0;
			int totalincomequantity=0;
			for ( int i=0; i< podlist.size(); i++ ){
				pod = (PurchaseOrderDetail)podlist.get(i);
				totalquantity += pod.getQuantity();
				totalincomequantity += pod.getIncomequantity();
			}
			
			//Session 
			//
			
			
			AppPurchaseIncomeDetail apbd = new AppPurchaseIncomeDetail();
			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				PurchaseIncomeDetail pbd = new PurchaseIncomeDetail();
//				pbd.setId(Long.valueOf(mc.getExcIDByRandomTableName(
//						"purchase_income_detail", 0, "")));
				pbd.setPiid(pbid);
				productid = strproductid[j];
				pbd.setProductid(productid);
				pbd.setProductname(strproductname[j]);
				pbd.setSpecmode(strspecmode[j]);
				pbd.setUnitid(Integer.valueOf(strunitid[j]));
				price = Double.valueOf(strunitprice[j]);
				pbd.setUnitprice(price);
				operatorquantity = Double.valueOf(stroperatorquantity[i]);				
				pbd.setQuantity(operatorquantity);
				subsum = price * operatorquantity;
				pbd.setSubsum(subsum);
				totalsum += subsum;
				totalincomequantity +=operatorquantity;

				 apbd.addPurchaseIncomeDetail(pbd);
				
				 apppod.updIncomeQuantity(poid, productid, operatorquantity);
				// AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
				// String upd =
				// apad.updPurchasePlanDetailChangeQuantity(ppid,productid,quantity);

			}
			pb.setTotalsum(totalsum);


			AppPurchaseIncome apb = new AppPurchaseIncome();
			apb.addPurchaseIncome(pb);			
			

			
			AppPurchaseOrder appl = new AppPurchaseOrder();
			if ( totalquantity == totalincomequantity ){

				appl.updIsChange(poid);
			}
			
			
			request.setAttribute("result", "databases.add.success");

//			DBUserLog.addUserLog(userid, "生成采购入库单");
			

			return mapping.findForward("make");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
			
		}
		return new ActionForward(mapping.getInput());
	}

}
