package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.dao.DrawShipmentBillDetailForm;
import com.winsafe.drp.dao.DrawShipmentBillForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class DrawShipmentBillDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppDrawShipmentBill aosb = new AppDrawShipmentBill();

			AppUsers au = new AppUsers();
			AppWarehouse aw = new AppWarehouse();
//			AppCustomer ac = new AppCustomer();
			AppOrgan ao = new AppOrgan();
//			AppDept ad = new AppDept();
			DrawShipmentBill osb = aosb.getDrawShipmentBillByID(id);
			DrawShipmentBillForm osbf = new DrawShipmentBillForm();

			osbf.setId(id);
			osbf.setWarehouseidname(aw.getWarehouseByID(osb.getWarehouseid())
					.getWarehousename());		
			osbf.setDrawdate(DateUtil.formatDate(osb.getDrawdate()));
			osbf.setTotalsum(osb.getTotalsum());
			osbf.setRemark(osb.getRemark());
			osbf.setIsaudit(osb.getIsaudit());
			osbf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, osb.getIsaudit(), "global.sys.SystemResource"));
			if (osb.getAuditid() !=null && osb.getAuditid() > 0) {
				osbf.setAuditidname(au.getUsersByid(osb.getAuditid())
						.getRealname());
			} else {
				osbf.setAuditidname("");
			}
			osbf.setAuditdate(DateUtil.formatDate(osb.getAuditdate()));
			osbf.setIsblankout(osb.getIsblankout());
			osbf.setIsblankoutname(Internation.getStringByKeyPosition(
					"YesOrNo", request, osb.getIsblankout(),
					"global.sys.SystemResource"));
			if (osb.getBlankoutid() != null && osb.getBlankoutid() > 0) {
				osbf.setBlankoutidname(au.getUsersByid(osb.getBlankoutid())
						.getRealname());
			} else {
				osbf.setBlankoutidname("");
			}
			osbf.setBlankoutdate(DateUtil.formatDate(osb.getBlankoutdate()));
			osbf.setBlankoutreason(osb.getBlankoutreason());
			osbf.setMakeorganidname(ao.getOrganByID(osb.getMakeorganid()).getOrganname());
			osbf.setMakeidname(au.getUsersByid(osb.getMakeid())
					.getRealname());
			osbf.setMakedate(DateUtil.formatDate(osb.getMakedate()));
			osbf.setIsendcase(osb.getIsendcase());
	        osbf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
		              request,
		              osb.getIsendcase(), "global.sys.SystemResource"));
	        if ( osbf.getEndcaseid() !=null && osbf.getEndcaseid() >0 ){
	        	osbf.setEndcaseidname(au.getUsersByid(osb.getEndcaseid()).getRealname());
	        }
	        osbf.setEndcasedate(DateUtil.formatDate(osb.getEndcasedate()));

			AppDrawShipmentBillDetail aosbd = new AppDrawShipmentBillDetail();
			List sals = aosbd.getDrawShipmentBillDetailByDsid(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < sals.size(); i++) {
				DrawShipmentBillDetailForm osbdf = new DrawShipmentBillDetailForm();
				DrawShipmentBillDetail o = (DrawShipmentBillDetail) sals.get(i);
				osbdf.setProductid(o.getProductid());
				osbdf.setProductname(o.getProductname());
				osbdf.setSpecmode(o.getSpecmode());
				// padf.setUnitid(Integer.valueOf(o[3].toString()));
				osbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(o.getUnitid()
								.toString())));
				osbdf.setBatch(o.getBatch());
				osbdf.setUnitprice(o.getUnitprice());
				osbdf.setQuantity(o.getQuantity());
				osbdf.setTakequantity(o.getTakequantity());
				osbdf.setSubsum(o.getSubsum());
				als.add(osbdf);
			}

			request.setAttribute("als", als);
			request.setAttribute("osbf", osbf);

			DBUserLog.addUserLog(userid,7, "仓库管理>>领用出库单详情,编号："+id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
