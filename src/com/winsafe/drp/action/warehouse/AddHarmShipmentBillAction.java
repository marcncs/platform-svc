package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddHarmShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			HarmShipmentBill osb = new HarmShipmentBill();
			String osid = MakeCode.getExcIDByRandomTableName("harm_shipment_bill",2, "HS");
			osb.setId(osid);
			osb.setWarehouseid(request.getParameter("warehouseid"));			
			osb.setShipmentdept(RequestTool.getInt(request,"shipmentdept"));			
			osb.setHarmdate(DateUtil.StringToDate(request.getParameter("harmdate")));
			osb.setRemark(request.getParameter("remark"));
			osb.setIsaudit(0);
			osb.setAuditid(0);
			osb.setMakeorganid(users.getMakeorganid());
			osb.setMakedeptid(users.getMakedeptid());
			osb.setMakeid(userid);
			osb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			osb.setIsblankout(0);
			osb.setBlankoutid(0);
			osb.setIsendcase(0);
			osb.setEndcaseid(0);
			osb.setTakestatus(0);
			

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			String batch[] = request.getParameterValues("batch");
			//double unitprice[] = RequestTool.getDoubles(request,"unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");

			Double totalsum = 0.00;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(osb.getId()).append(",").append(osb.getWarehouseid()).append(",")
			.append(osb.getRemark());
			
			AppHarmShipmentBill aosb = new AppHarmShipmentBill();
			AppHarmShipmentBillDetail aspb = new AppHarmShipmentBillDetail();

			for (int i = 0; i < productid.length; i++) {				
				HarmShipmentBillDetail osbd = new HarmShipmentBillDetail();
				osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"harm_shipment_bill_detail", 0, "")));
				osbd.setHsid(osid);
				osbd.setProductid(productid[i]);
				osbd.setProductname(productname[i]);
				osbd.setSpecmode(specmode[i]);
				osbd.setUnitid(unitid[i]);
				osbd.setBatch(batch[i]);
				osbd.setUnitprice(0.00);
				osbd.setQuantity(quantity[i]);
				osbd.setSubsum(osbd.getUnitprice() * osbd.getQuantity());
				osbd.setTakequantity(0d);
				
				aspb.addHarmShipmentBillDetail(osbd);
				//totalsum += unitprice[i] * quantity[i];
				
			}
			osb.setTotalsum(totalsum);
			osb.setKeyscontent(keyscontent.toString());
			aosb.addHarmShipmentBill(osb);

			request.setAttribute("result", "databases.add.success");

            DBUserLog.addUserLog(userid,7,"报损>>新增报损出库,编号："+osid); 			
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
