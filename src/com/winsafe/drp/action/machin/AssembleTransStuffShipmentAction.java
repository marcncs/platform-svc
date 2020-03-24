package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStuffShipmentBill;
import com.winsafe.drp.dao.AppStuffShipmentBillDetail;
import com.winsafe.drp.dao.StuffShipmentBill;
import com.winsafe.drp.dao.StuffShipmentBillDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AssembleTransStuffShipmentAction
    extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long warehouseid = Long.valueOf(request.getParameter("warehouseid"));
		UsersBean usersBean = UserManager.getUser(request);
//		Long userid = usersBean.getUserid();

		try {
			String[] strpid = request.getParameterValues("pid");
			String[] strdetaidid = request.getParameterValues("detailid");
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductcode=request.getParameterValues("productcode");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");		
			String[] strunitid = request.getParameterValues("unitid");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] stroperatorquantity = request.getParameterValues("operatorquantity");

			for(int n=0;n<strpid.length;n++){
			System.out.println("1=="+strpid[n]);
			System.out.println("2=="+strdetaidid[n]);
			System.out.println("3=="+strproductid[n]);
			System.out.println("4=="+strproductcode[n]);
			System.out.println("5=="+strproductname[n]);
			System.out.println("6=="+strspecmode[n]);
			System.out.println("7=="+strunitid[n]);
			System.out.println("8=="+stroperatorquantity[n]);
			}
		
			
						
			AppProductStockpile aps = new AppProductStockpile();
			double ccount = 0.00;		
			
			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				//System.out.println("--aab--"+strproductid[j]);
				//System.out.println("--cc-"+stroperatorquantity[i]);
				//System.out.println("---aa==="+aps.getProductStockpileByProductID(strproductid[j],warehouseid));
				ccount =Double.valueOf(stroperatorquantity[i])- aps.getProductStockpileByProductID(strproductid[j],warehouseid);
				if (ccount > 0) {
					String result = "databases.makeshipment.nostockpile";
					request.setAttribute("result", result);
					return new ActionForward("/sys/lockrecord.jsp");
				}
			}
			
			StuffShipmentBill ssb = new StuffShipmentBill();
			String ssid = MakeCode.getExcIDByRandomTableName(
					"stuff_shipment_bill", 2, "ML");
			ssb.setId(ssid);			
			ssb.setWarehouseid(warehouseid);
			ssb.setShipmentsort(Integer.valueOf(request
					.getParameter("shipmentsort")));
//			ssb.setShipmentdept(request
//					.getParameter("shipmentdept"));
//			ssb.setRequiredate(new Date(request.getParameter("requiredate")
//					.replace('-', '/')));
//			ssb.setBillno(request.getParameter("billno"));
			ssb.setRemark("组装单转材料出库");
			ssb.setIsrefer(0);
			ssb.setApprovestatus(0);
			ssb.setIsaudit(0);
			ssb.setAuditid(Long.valueOf(0));
//			ssb.setMakeid(userid);
			ssb.setMakedate(DateUtil.getCurrentDate());
	
			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);			
				setShipmentBill(ssid, strproductid[j],strproductcode[j], strproductname[j], strspecmode[j],
						Long.valueOf(strunitid[j]), Double.valueOf(stroperatorquantity[i]), warehouseid, Long.valueOf(strdetaidid[j]));
			}
			AppStuffShipmentBill assb = new AppStuffShipmentBill();
			assb.addStuffShipmentBill(ssb);

			request.setAttribute("result", "databases.add.success");

//			DBUserLog.addUserLog(userid,"销售订单转出库单"); 
			return mapping.findForward("make");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	public void setShipmentBill(String ssid, String productid,String productcode,
			String productname, String specmode, Long unitid, Double quantity,
			Long warehouseid, Long detailid) throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		//ProductStockpile ps = new ProductStockpile();
		List ps =aps.getProductStockpileByProductIDWID(productid,warehouseid);
		for(int p=0;p<1;p++){
			Object[] o = (Object[]) ps.get(p);
		double scount = Double.valueOf(o[4].toString());
		//System.out.println("----scount====="+scount);
		StuffShipmentBill sb = new StuffShipmentBill();
		StuffShipmentBillDetail sbd = new StuffShipmentBillDetail();
		sbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"stuff_shipment_bill_detail", 0, "")));
		sbd.setSsid(ssid);
		sbd.setProductid(productid);
//		sbd.setProductcode(productcode);
		sbd.setProductname(productname);
		sbd.setSpecmode(specmode);
		sbd.setUnitid(unitid);
		String batch = o[2].toString();
		sbd.setBatch(batch);
		sbd.setUnitprice(Double.valueOf(0.00));
		if(quantity >scount){
		sbd.setQuantity(scount);
		}else{
			sbd.setQuantity(quantity);
		}
		sbd.setSubsum(Double.valueOf(0.00));

		AppStuffShipmentBillDetail aspb = new AppStuffShipmentBillDetail();
		aspb.addStuffShipmentBillDetail(sbd);
		
		
		AppAssembleDetail appad = new AppAssembleDetail();
//		appad.updAlreadyQuantity(detailid, sbd.getQuantity());

		//
//		aps.prepareOut(productid, warehouseid, batch, sbd.getQuantity());
		
		if(quantity >scount){
			setShipmentBill(ssid, productid,productcode, productname, specmode,
					unitid, quantity-scount, warehouseid, detailid);
		}
		}
	}
}
