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
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppLoanOutDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.LoanOut;
import com.winsafe.drp.dao.LoanOutDetail;
import com.winsafe.drp.dao.LoanOutDetailForm;
import com.winsafe.drp.dao.LoanOutForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class LoanOutDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppLoanOut aso = new AppLoanOut();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			LoanOut so = aso.getLoanOutByID(id);
			LoanOutForm sof = new LoanOutForm();

			sof.setId(id);
			sof.setUid(so.getUid());
			sof.setUname(so.getUname());
			sof.setReceiveman(so.getReceiveman());
			sof.setTel(so.getTel());
			Long saledept = Long.valueOf(so.getSaledept().toString());
//			sof.setSaledeptname(ad.getDeptByID(saledept).getDeptname());
//			sof.setSaleidname(au.getUsersByid(so.getSaleid()).getRealname());
			sof
					.setConsignmentdate(DateUtil.formatDate(so
							.getConsignmentdate()));
			sof.setTransportmodename(Internation.getStringByKeyPositionDB(
					"TransportMode", so.getTransportmode()));
			sof.setTransitname(Internation.getStringByKeyPositionDB("Transit",
					so.getTransit()));
			sof.setTransportaddr(so.getTransportaddr());
			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());

//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
			sof.setIsaudit(so.getIsaudit());
			sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaudit(), "global.sys.SystemResource"));
			if (so.getAuditid() != null && so.getAuditid() > 0) {
//				sof.setAuditidname(au.getUsersByid(so.getAuditid())
//						.getRealname());
			} else {
				sof.setAuditidname("");
			}
			sof.setAuditdate(DateUtil.formatDate(so.getAuditdate()));
			sof.setIsreceive(so.getIsreceive());
			sof.setIsreceivename(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsreceive(), "global.sys.SystemResource"));
			if (so.getReceiveid() != null && so.getReceiveid() > 0) {
//				sof.setReceiveidname(au.getUsersByid(so.getReceiveid())
//						.getRealname());
			} else {
				sof.setReceiveidname("");
			}
			sof.setReceivedate(DateUtil.formatDate(so.getReceivedate()));
			sof.setIstranssale(so.getIstranssale());
			sof.setIstranssalename(Internation.getStringByKeyPosition(
					"YesOrNo", request, so.getIstranssale(),
					"global.sys.SystemResource"));
			if (so.getTranssaleid() != null && so.getTranssaleid() > 0) {
//				sof.setTranssaleidname(au.getUsersByid(so.getTranssaleid())
//						.getRealname());
			} else {
				sof.setTranssaleidname("");
			}
			sof.setTranssaledate(DateUtil.formatDate(so.getTranssaledate()));

			AppLoanOutDetail asld = new AppLoanOutDetail();
			List sals = asld.getLoanOutDetailByLoid(id);
			ArrayList als = new ArrayList();
			AppWarehouse aw = new AppWarehouse();

			LoanOutDetail sod = null;
			for (int i = 0; i < sals.size(); i++) {
				sod = (LoanOutDetail) sals.get(i);
				LoanOutDetailForm sodf = new LoanOutDetailForm();
				sodf.setId(sod.getId());
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
				sodf.setUnitprice(sod.getUnitprice());
				sodf.setQuantity(sod.getQuantity());
				sodf.setTakequantity(sod.getTakequantity());
				sodf.setBackquantity(sod.getBackquantity());
				sodf.setCost(sod.getCost());
				sodf.setSubsum(sod.getSubsum());
				sodf.setBatch(sod.getBatch());
				als.add(sodf);
			}

			request.setAttribute("als", als);
			request.setAttribute("sof", sof);
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "借出单详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
