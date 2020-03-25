package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddDrawShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		super.initdata(request);
		try {
			DrawShipmentBill dsb = new DrawShipmentBill();
			String dsid = MakeCode.getExcIDByRandomTableName("draw_shipment_bill",
					2, "DS");
			dsb.setId(dsid);
			dsb.setWarehouseid(request.getParameter("warehouseid"));			
			dsb.setDrawdate(DateUtil.StringToDate(request.getParameter("drawdate")));
			dsb.setRemark(request.getParameter("remark"));
			dsb.setIsaudit(0);
			dsb.setAuditid(0);
			dsb.setMakeorganid(users.getMakeorganid());
			dsb.setMakedeptid(users.getMakedeptid());
			dsb.setMakeid(userid);
			dsb.setMakedate(DateUtil.getCurrentDate());
			dsb.setIsblankout(0);
			dsb.setBlankoutid(0);
			dsb.setIsendcase(0);
			dsb.setEndcaseid(0);
			dsb.setTakestatus(0);
			

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			//double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");

			Double totalsum = 0.00;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(dsid).append(",").append(dsb.getWarehouseid())
			.append(",").append(dsb.getRemark());

			AppDrawShipmentBill adsb = new AppDrawShipmentBill();			
			AppDrawShipmentBillDetail aspb = new AppDrawShipmentBillDetail();
			for (int i = 0; i < productid.length; i++) {				
				DrawShipmentBillDetail dsbd = new DrawShipmentBillDetail();
				dsbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"draw_shipment_bill_detail", 0, "")));
				dsbd.setDsid(dsid);
				dsbd.setProductid(productid[i]);
				dsbd.setProductname(productname[i]);
				dsbd.setSpecmode(specmode[i]);
				dsbd.setUnitid(unitid[i]);
				//osbd.setBatch(batch);
				dsbd.setUnitprice(0.00);
				dsbd.setQuantity(quantity[i]);
				dsbd.setSubsum(dsbd.getUnitprice() * quantity[i]);
				dsbd.setTakequantity(0d);				
				aspb.addDrawShipmentBillDetail(dsbd);
			}
			dsb.setTotalsum(totalsum);
			dsb.setKeyscontent(keyscontent.toString());
			adsb.addDrawShipmentBill(dsb);

			
			request.setAttribute("result", "databases.add.success");

            DBUserLog.addUserLog(userid,7,"仓库管理>>新增领用出库,编号："+dsid); 
			
			return mapping.findForward("addresult");
		} catch (Exception e) {			
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
