package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBillDetailForm;
import com.winsafe.drp.dao.HarmShipmentBillForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class HarmShipmentBillDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppHarmShipmentBill aosb = new AppHarmShipmentBill();
			HarmShipmentBill osb = new HarmShipmentBill();
			AppUsers au = new AppUsers();
//			AppWarehouse aw = new AppWarehouse();
//			AppDept ad = new AppDept();
			osb = aosb.getHarmShipmentBillByID(id);
			HarmShipmentBillForm osbf = new HarmShipmentBillForm();

			osbf.setId(id);
			osbf.setWarehouseid(osb.getWarehouseid());
//			osbf.setWarehouseidname(aw.getWarehouseByID(osb.getWarehouseid())
//					.getWarehousename());
			osbf.setShipmentdept(osb.getShipmentdept());
//			osbf.setShipmentdeptname(ad.getDeptByID(osb.getShipmentdept())
//					.getDeptname());			
			osbf.setHarmdate(DateUtil.formatDate(osb.getHarmdate()));
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
			osbf.setMakeorganid(osb.getMakeorganid());
			osbf.setMakedeptid(osb.getMakedeptid());
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

			AppHarmShipmentBillDetail aosbd = new AppHarmShipmentBillDetail();
			List sals = aosbd.getHarmShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < sals.size(); i++) {
				HarmShipmentBillDetailForm osbdf = new HarmShipmentBillDetailForm();
				HarmShipmentBillDetail o = (HarmShipmentBillDetail) sals.get(i);
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

			DBUserLog.addUserLog(userid, 7,"报损>>报损出库单详情,编号："+id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
