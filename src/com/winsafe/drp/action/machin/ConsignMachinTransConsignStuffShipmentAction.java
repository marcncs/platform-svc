package com.winsafe.drp.action.machin;
//package com.winsafe.drp.action.machin;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.AppConsignMachinDetail;
//import com.winsafe.drp.dao.AppProductStockpile;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.DbUtil;
//import com.winsafe.drp.util.MakeCode;
//import com.winsafe.drp.util.RequestTool;
//
//public class ConsignMachinTransConsignStuffShipmentAction
//    extends Action {
//
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		String pvid = request.getParameter("pvid");
//		String plinkman = request.getParameter("plinkman");
//		String tel = request.getParameter("tel");
//		String sendaddr = request.getParameter("sendaddr");
//		Long warehouseid = Long.valueOf(request.getParameter("warehouseid"));
//		UsersBean usersBean = UserManager.getUser(request);
//		Integer userid = usersBean.getUserid();
//
//		try {
//			String[] strpid = request.getParameterValues("pid");
//			String[] strdetaidid = request.getParameterValues("detailid");
//			String[] strproductid = request.getParameterValues("productid");
//			String[] productcode = RequestTool.getStrings(request,"productcode");
//			String[] strproductname = request.getParameterValues("productname");
//			String[] strspecmode = request.getParameterValues("specmode");		
//			String[] strunitid = request.getParameterValues("unitid");
//			String[] strunitprice = request.getParameterValues("unitprice");
//			String[] stroperatorquantity = request.getParameterValues("operatorquantity");
//			String productid;
//			Double price, subsum, operatorquantity;
//			Double totalsum = 0.00;	
//			
//			
//			
//						
//			AppProductStockpile aps = new AppProductStockpile();
//			double ccount = 0.00;		
//			
//			for (int i = 0; i < strpid.length; i++) {
//				int j = Integer.parseInt(strpid[i]);
//				ccount =Double.valueOf(stroperatorquantity[i])- aps.getProductStockpileByProductID(strproductid[j],
//						warehouseid);
//				if (ccount > 0) {
//					String result = "databases.makeshipment.nostockpile";
//					request.setAttribute("result", result);
//					return new ActionForward("/sys/lockrecord.jsp");
//				}
//			}
//			
//			ConsignStuffShipment ssb = new ConsignStuffShipment();
//			String ssid = MakeCode.getExcIDByRandomTableName(
//					"consign_stuff_shipment", 2, "CL");
//			ssb.setId(ssid);
//			ssb.setPid(pvid);
//			ssb.setPlinkman(plinkman);
//			ssb.setTel(tel);
//			ssb.setSendaddr(sendaddr);
//			ssb.setWarehouseid(warehouseid);
//			ssb.setConsignshipmentsort(Integer.valueOf(request
//					.getParameter("consignshipmentsort")));
//			//ssb.setShipmentdept(Long.valueOf(request
//			//		.getParameter("shipmentdept")));
//			ssb.setRequiredate(new Date(request.getParameter("requiredate")
//					.replace('-', '/')));
//			ssb.setBillno(request.getParameter("billno"));
//			ssb.setRemark("委外加工单转材料出库");
//			ssb.setIsaudit(0);
//			ssb.setAuditid(Long.valueOf(0));
//			ssb.setMakeid(userid);
//			ssb.setMakedate(DateUtil.getCurrentDate());
//	
//			for (int i = 0; i < strpid.length; i++) {
//				int j = Integer.parseInt(strpid[i]);	
//				setShipmentBill(ssid, strproductid[j],productcode[j], strproductname[j], strspecmode[j],
//						Long.valueOf(strunitid[j]), Double.valueOf(stroperatorquantity[i]), warehouseid, Long.valueOf(strdetaidid[j]));
//			}
//			AppConsignStuffShipment assb = new AppConsignStuffShipment();
//			assb.addConsignStuffShipment(ssb);
//
//			request.setAttribute("result", "databases.add.success");
//
//			DBUserLog.addUserLog(userid,"委外加工单转委外材料出库单"); 
//			return mapping.findForward("make");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ActionForward(mapping.getInput());
//	}
//
//	public void setShipmentBill(String ssid, String productid,String productcode,
//			String productname, String specmode, Long unitid, Double quantity,
//			Long warehouseid, Long detailid) throws Exception {
//		AppProductStockpile aps = new AppProductStockpile();
//		//ProductStockpile ps = new ProductStockpile();
//		List ps =aps.getProductStockpileByProductIDWID(productid,warehouseid);
//		for(int p=0;p<1;p++){
//			Object[] o = (Object[]) ps.get(p);
//		double scount = Double.valueOf(o[4].toString());
//		//System.out.println("----scount====="+scount);
//		ConsignStuffShipment sb = new ConsignStuffShipment();
//		ConsignStuffShipmentDetail sbd = new ConsignStuffShipmentDetail();
//		sbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//				"consign_stuff_shipment_detail", 0, "")));
//		sbd.setCsid(ssid);
//		sbd.setProductid(productid);
//		sbd.setProductcode(productcode);
//		sbd.setProductname(productname);
//		sbd.setSpecmode(specmode);
//		sbd.setUnitid(unitid);
//		String batch = o[2].toString();
//		sbd.setBatch(batch);
//		sbd.setUnitprice(Double.valueOf(0.00));
//		if(quantity >scount){
//		sbd.setQuantity(scount);
//		}else{
//			sbd.setQuantity(quantity);
//		}
//		sbd.setSubsum(Double.valueOf(0.00));
//
//		AppConsignStuffShipmentDetail aspb = new AppConsignStuffShipmentDetail();
//		aspb.addConsignStuffShipmentDetail(sbd);
//		
//		
//		AppConsignMachinDetail appad = new AppConsignMachinDetail();
//		appad.updAlreadyQuantity(detailid, sbd.getQuantity());
//
//		//
//		aps.prepareOut(productid, warehouseid, batch, sbd.getQuantity());
//		
//		if(quantity >scount){
//			setShipmentBill(ssid, productid,productcode, productname, specmode,
//					unitid, quantity-scount, warehouseid, detailid);
//		}
//		}
//	}
//}
