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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.dao.VocationOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class VocationOrderDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// System.out.println("--"+MakeCode.getRandom());
		String id = request.getParameter("ID");
		
//		Long userid = users.getUserid();

		try {
			AppVocationOrder aso = new AppVocationOrder();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			AppInvoiceConf aic = new AppInvoiceConf();
			VocationOrder so = aso.getVocationOrderByID(id);
			VocationOrderForm sof = new VocationOrderForm();

			sof.setId(id);
			sof.setCustomerbillid(so.getCustomerbillid());
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setCmobile(so.getCmobile());
			sof.setDecideman(so.getDecideman());
			sof.setDecidemantel(so.getDecidemantel());
			sof.setReceiveman(so.getReceiveman());
			sof.setReceivemobile(so.getReceivemobile());
			sof.setReceivetel(so.getReceivetel());
			// sof.setSalesortname(Internation.getStringByKeyPosition("SaleSort",
			// request, so.getSalesort(), "global.sys.SystemResource"));
//			sof.setSaledeptname(ad.getDeptByID(so.getSaledept()).getDeptname());
//			sof.setSaleidname(au.getUsersByid(so.getSaleid()).getRealname());
			sof.setPaymentmodename(Internation.getStringByPayPositionDB(so.getPaymentmode()));
			sof.setConsignmentdate(DateUtil.formatDateTime(so
							.getConsignmentdate()));
			sof.setSourcename(Internation.getStringByKeyPositionDB("Source", so.getSource()));
			sof.setTransportmodename(Internation.getStringByKeyPositionDB(
					"TransportMode", so.getTransportmode()));
			// sof.setTransitname(Internation.getStringByKeyPositionDB("Transit",
			// so.getTransit()));
			sof.setTransportaddr(so.getTransportaddr());
			sof.setInvmsg(so.getInvmsg());
			sof.setInvmsgname(aic.getInvoiceConfById(so.getInvmsg().intValue())
					.getIvname());
			sof.setIsmaketicketname(Internation.getStringByKeyPosition(
					"YesOrNo", request, so.getIsmaketicket(),
					"global.sys.SystemResource"));
			sof.setTickettitle(so.getTickettitle());
			AppOrgan appor = new AppOrgan();
			sof.setMakeorganidname(appor.getOrganByID(so.getMakeorganid())
					.getOrganname());
//			sof.setMakedeptidname(ad.getDeptByID(so.getMakedeptid())
//					.getDeptname());
			sof.setEquiporganidname(appor.getOrganByID(so.getEquiporganid())
					.getOrganname());

			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());

//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedate(DateUtil.formatDateTime(so.getMakedate()));
			if (so.getUpdateid() != null && so.getUpdateid() > 0) {
//				sof.setUpdateidname(au.getUsersByid(so.getUpdateid())
//						.getRealname());
			} else {
				sof.setUpdateidname("");
			}
			sof.setLastupdate(DateUtil.formatDateTime(so.getLastupdate()));
			sof.setIsaudit(so.getIsaudit());
			sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaudit(), "global.sys.SystemResource"));
			if (so.getAuditid() != null && so.getAuditid() > 0) {
//				sof.setAuditidname(au.getUsersByid(so.getAuditid())
//						.getRealname());
			} else {
				sof.setAuditidname("");
			}
			sof.setAuditdate(DateUtil.formatDateTime(so.getAuditdate()));
			sof.setIsendcase(so.getIsendcase());
			sof.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsendcase(), "global.sys.SystemResource"));
			if (so.getEndcaseid() != null && so.getEndcaseid() > 0) {
//				sof.setEndcaseidname(au.getUsersByid(so.getEndcaseid())
//						.getRealname());
			} else {
				sof.setEndcaseidname("");
			}
			sof.setEndcasedate(DateUtil.formatDateTime(so.getEndcasedate()));
			sof.setIsblankout(so.getIsblankout());
			sof.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsblankout(), "global.sys.SystemResource"));
			if (so.getBlankoutid() != null && so.getBlankoutid() > 0) {
//				sof.setBlankoutidname(au.getUsersByid(so.getBlankoutid())
//						.getRealname());
			} else {
				sof.setBlankoutidname("");
			}
			sof.setBlankoutdate(DateUtil.formatDateTime(so.getBlankoutdate()));
			sof.setBlankoutreason(so.getBlankoutreason());

			AppVocationOrderDetail asld = new AppVocationOrderDetail();
			List sals = asld.getVocationOrderDetailObjectBySOID(id);
			ArrayList als = new ArrayList();
			AppWarehouse aw = new AppWarehouse();
			AppOrganPrice aop = new AppOrganPrice();

			VocationOrderDetail sod = null;
			for (int i = 0; i < sals.size(); i++) {
				sod = (VocationOrderDetail) sals.get(i);
				SaleOrderDetailForm sodf = new SaleOrderDetailForm();
				sodf.setProductid(sod.getProductid());
				sodf.setProductname(sod.getProductname());
				sodf.setSpecmode(sod.getSpecmode());
				if (null != sod.getWarehouseid() && sod.getWarehouseid() > 0) {
//					sodf.setWarehouseidname(aw.getWarehouseByID(
//							sod.getWarehouseid()).getWarehousename());
				}
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(sod.getUnitid()
								.toString())));
				sodf.setOrgunitprice(sod.getOrgunitprice());
				sodf.setUnitprice(sod.getUnitprice());
				sodf.setTaxunitprice(sod.getTaxunitprice());
				sodf.setQuantity(sod.getQuantity());
				sodf.setTakequantity(sod.getTakequantity());
				sodf.setDiscount(sod.getDiscount());
				sodf.setTaxrate(sod.getTaxrate());
				sodf.setCost(sod.getCost());
				sodf.setSubsum(sod.getSubsum());
				// sodf.setBatch(sod.getBatch());
				als.add(sodf);
			}

					
			request.setAttribute("als", als);
			request.setAttribute("sof", sof);

//			DBUserLog.addUserLog(userid, "行业销售单详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
