package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrderDetailForm;
import com.winsafe.drp.dao.PeddleOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class PeddleOrderDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// System.out.println("--"+MakeCode.getRandom());
		String id = request.getParameter("ID");

		try {
			AppPeddleOrder aso = new AppPeddleOrder();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			AppWarehouse aw = new AppWarehouse();
			PeddleOrder so = aso.getPeddleOrderByID(id);
			PeddleOrderForm sof = new PeddleOrderForm();
			AppInvoiceConf aic= new AppInvoiceConf();

			sof.setId(id);
//			sof.setCustomerbillid(so.getCustomerbillid());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setCmobile(so.getCmobile());
			sof.setReceiveman(so.getReceiveman());
			sof.setReceivetel(so.getReceivetel());
			sof.setPaymentmode(so.getPaymentmode());
//			sof.setWarehouseid(so.getWarehouseid());
			if(so.getWarehouseid()!=null&&so.getWarehouseid()>0){
//			sof.setWarehouseidname(aw.getWarehouseByID(so.getWarehouseid()).getWarehousename());
			}else{
				sof.setWarehouseidname("");
			}
			sof.setInvmsg(so.getInvmsg());
			sof.setInvmsgname(aic.getInvoiceConfById(so.getInvmsg()).getIvname());
			sof.setIsmaketicket(so.getIsmaketicket());
			sof.setIsmaketicketname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsmaketicket(), "global.sys.SystemResource"));
			sof.setMakeorganid(so.getMakeorganid());
			
 
			sof.setPaymentmodename(Internation.getStringByPayPositionDB(so.getPaymentmode()));
//			sof
//					.setConsignmentdate(DateUtil.formatDate(so
//							.getConsignmentdate()));
//			sof.setTransportmodename(Internation.getStringByKeyPositionDB(
//					"TransportMode", so.getTransportmode()));
//			sof.setTransitname(Internation.getStringByKeyPositionDB("Transit",
//					so.getTransit()));
//			sof.setTransportaddr(so.getTransportaddr());
			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());

//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedatename(DateUtil.formatDate(so.getMakedate()));
			if (so.getUpdateid() != null && so.getUpdateid() > 0) {
//				sof.setUpdateidname(au.getUsersByid(so.getUpdateid())
//						.getRealname());
			} else {
				sof.setUpdateidname("");
			}
			sof.setLastupdatename(DateUtil.formatDateTime(so.getLastupdate()));
			sof.setIsaudit(so.getIsaudit());
			sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaudit(), "global.sys.SystemResource"));
			if (so.getAuditid() != null && so.getAuditid() > 0) {
//				sof.setAuditidname(au.getUsersByid(so.getAuditid())
//						.getRealname());
			} else {
				sof.setAuditidname("");
			}
			sof.setIsaccountname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaccount(), "global.sys.SystemResource"));
			sof.setAuditdatename(DateUtil.formatDateTime(so.getAuditdate()));
		 
		 
		 
			sof.setIsblankout(so.getIsblankout());
			sof.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsblankout(), "global.sys.SystemResource"));
			if (so.getBlankoutid() != null && so.getBlankoutid() > 0) {
//				sof.setBlankoutidname(au.getUsersByid(so.getBlankoutid())
//						.getRealname());
			} else {
				sof.setBlankoutidname("");
			}
			sof.setBlankoutdatename(DateUtil.formatDateTime(so.getBlankoutdate()));
			sof.setBlankoutreason(so.getBlankoutreason());

			AppPeddleOrderDetail asld = new AppPeddleOrderDetail();
			List sals = asld.getPeddleOrderDetailObjectByPOID(id);
			ArrayList als = new ArrayList();
			

			PeddleOrderDetail sod = null;
			for (int i = 0; i < sals.size(); i++) {
				sod = (PeddleOrderDetail) sals.get(i);
				PeddleOrderDetailForm sodf = new PeddleOrderDetailForm();
				sodf.setId(sod.getId());
				sodf.setProductid(sod.getProductid());
				sodf.setProductname(sod.getProductname());
				sodf.setSpecmode(sod.getSpecmode());
//				if (null != sod.getWarehouseid() && sod.getWarehouseid() > 0) {
//					sodf.setWarehouseidname(aw.getWarehouseByID(
//							sod.getWarehouseid()).getWarehousename());
//				}
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(sod.getUnitid()
								.toString())));
				sodf.setUnitprice(sod.getUnitprice());
				sodf.setTaxunitprice(sod.getTaxunitprice());
				sodf.setQuantity(sod.getQuantity());
//				sodf.setTakequantity(sod.getTakequantity());
				sodf.setDiscount(sod.getDiscount());
				sodf.setTaxrate(sod.getTaxrate());
				sodf.setCost(sod.getCost());
				sodf.setSubsum(sod.getSubsum());
				sodf.setBatch(sod.getBatch());
				als.add(sodf);
			}

			request.setAttribute("als", als);
			request.setAttribute("sof", sof);
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "销售订单详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
