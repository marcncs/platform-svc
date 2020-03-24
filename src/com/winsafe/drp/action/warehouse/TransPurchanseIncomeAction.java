package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class TransPurchanseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pbid = request.getParameter("pbid");			
		super.initdata(request);
		try {
			AppProvider apv = new AppProvider();
			PurchaseIncome pi = new PurchaseIncome();
			AppPurchaseIncome api = new AppPurchaseIncome();
			String piid = MakeCode.getExcIDByRandomTableName("purchase_income",2, "PI");
			pi.setId(piid);
			pi.setWarehouseid(request.getParameter("warehouseid"));
			pi.setPoid(pbid);
			pi.setProvideid(request.getParameter("pvid"));
			pi.setProvidename(request.getParameter("providename"));		
			pi.setPlinkman(request.getParameter("plinkman"));
			pi.setTel(request.getParameter("tel"));
			pi.setPaymode(RequestTool.getInt(request, "paymode"));
			pi.setPrompt(apv.getProviderPrompt(pi.getProvideid()));
			pi.setIncomedate(DateUtil.StringToDate(request.getParameter("incomedate")));
			pi.setRemark("采购订单转采购入库单。");
			pi.setMakeorganid(users.getMakeorganid());
			pi.setMakedeptid(users.getMakedeptid());
			pi.setMakeid(userid);
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setIsaudit(0);
			pi.setAuditid(0);
			pi.setIstally(0);
			
			 StringBuffer keyscontent = new StringBuffer();
			    AppProvider ap = new AppProvider();
			    Provider p = ap.getProviderByID(pi.getProvideid());
			    keyscontent.append(pi.getId()).append(",").append(p!=null?p.getPname():"").append(",")
			    .append(p!=null?p.getMobile():"").append(",").append(pi.getPlinkman()).append(",")
			    .append(pi.getPoid());
			
			
			String[] strpid = request.getParameterValues("pid");
			String[] strdetaidid = request.getParameterValues("detailid");
			String[] productid = request.getParameterValues("productid");
			String[] productname = request.getParameterValues("productname");
			String[] specmode = request.getParameterValues("specmode");		
			int[] unitid = RequestTool.getInts(request,"unitid");
			double[] unitprice = RequestTool.getDoubles(request,"unitprice");
			String[] stroperatorquantity = request.getParameterValues("operatorquantity");

			Double price, subsum, operatorquantity;
			Double totalsum = 0.00;	
			Integer detailid;
		
			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();			
			
			List podlist = apbd.getPurchaseBillDetailByPbID(pbid);
			PurchaseBillDetail pod =null;
			int totalquantity=0;
			int totalincomequantity=0;
			for ( int i=0; i< podlist.size(); i++ ){
				pod = (PurchaseBillDetail)podlist.get(i);
				totalquantity += pod.getQuantity();
				totalincomequantity += pod.getIncomequantity();
			}

			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_income_idcode","piid",pi.getWarehouseid());
			AppPurchaseIncomeDetail apid = new AppPurchaseIncomeDetail();
			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				PurchaseIncomeDetail pbd = new PurchaseIncomeDetail();
				pbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_detail", 0, "")));
				pbd.setPiid(piid);
				detailid = Integer.valueOf(strdetaidid[j]);
				//productid = strproductid[j];
				pbd.setProductid(productid[j]);
				pbd.setProductname(productname[j]);
				pbd.setSpecmode(specmode[j]);
				pbd.setUnitid(unitid[j]);
				price = unitprice[j];
				pbd.setUnitprice(price);
				operatorquantity = Double.valueOf(stroperatorquantity[i]);				
				pbd.setQuantity(operatorquantity);
				subsum = price * operatorquantity;
				pbd.setSubsum(subsum);
				totalsum += subsum;
				totalincomequantity +=operatorquantity;

				apid.addPurchaseIncomeDetail(pbd);
				
				apbd.updIncomeQuantity(detailid, operatorquantity);
				wbds.add(pi.getId(), productid[i], unitid[i], pbd.getQuantity());
			}
			pi.setTotalsum(totalsum);
			pi.setKeyscontent(keyscontent.toString());
			api.addPurchaseIncome(pi);
			
			
			AppPurchaseBill apb = new AppPurchaseBill();
			if ( totalquantity == totalincomequantity ){
				
				apb.updIstransferAdsum(pbid);
			}

			request.setAttribute("result", "databases.input.success");
			DBUserLog.addUserLog(userid, 7, "入库>>确认采购订单转采购入库");

			return mapping.findForward("input");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
