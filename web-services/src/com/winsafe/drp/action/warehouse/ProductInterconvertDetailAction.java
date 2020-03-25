package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.util.DBUserLog;

public class ProductInterconvertDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String id = request.getParameter("ID");
		try {
			AppProductInterconvert asm = new AppProductInterconvert();
//			AppUsers au = new AppUsers();
//			AppWarehouse aw = new AppWarehouse();

			ProductInterconvert sm = asm.getProductInterconvertByID(id);
//			ProductInterconvertForm smf = new ProductInterconvertForm();
//			smf.setId(id);
//			smf.setMovedatename(DateUtil.formatDate(sm.getMovedate()));
//			smf.setOutwarehouseidname(aw.getWarehouseByID(
//					sm.getOutwarehouseid()).getWarehousename());
//			smf.setInwarehouseidname(aw.getWarehouseByID(sm.getInwarehouseid())
//					.getWarehousename());
//			smf.setTotalsum(sm.getTotalsum());
//			smf.setMovecause(sm.getMovecause());
//			smf.setRemark(sm.getRemark());
//
//			smf.setIsaudit(sm.getIsaudit());
//			smf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//					request, sm.getIsaudit(), "global.sys.SystemResource"));
//			if (sm.getAuditid() > 0) {
//				smf.setAuditidname(au.getUsersByid(sm.getAuditid())
//						.getRealname());
//			} else {
//				smf.setAuditidname("");
//			}
//			smf.setAuditdate(sm.getAuditdate());
//			smf.setAuditdatename(DateUtil.formatDate(sm.getAuditdate()));
//			smf.setMakeidname(au.getUsersByid(sm.getMakeid()).getRealname());
//			smf.setMakedate(sm.getMakedate());
//			smf.setMakedatename(DateUtil.formatDate(sm.getMakedate()));
//			smf.setIsshipment(sm.getIsshipment());
//			smf.setIsshipmentname(Internation.getStringByKeyPosition("YesOrNo",
//					request, sm.getIsshipment(), "global.sys.SystemResource"));
//			if (sm.getShipmentid() > 0) {
//				smf.setShipmentidname(au.getUsersByid(sm.getShipmentid())
//						.getRealname());
//			} else {
//				smf.setShipmentidname("");
//			}
//			smf.setShipmentdate(sm.getShipmentdate());
//			smf.setShipmentdatename(DateUtil.formatDate(sm.getShipmentdate()));
//			smf.setIscomplete(sm.getIscomplete());
//			smf.setIscompletename(Internation.getStringByKeyPosition("YesOrNo",
//					request, sm.getIscomplete(), "global.sys.SystemResource"));
//			if (sm.getReceiveid() > 0) {
//				smf.setReceiveidname(au.getUsersByid(sm.getReceiveid())
//						.getRealname());
//			} else {
//				smf.setReceiveidname("");
//			}
//			smf.setReceivedate(sm.getReceivedate());
//			smf.setReceivedatename(DateUtil.formatDate(sm.getReceivedate()));
			AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();
			List spils = asmd.getProductInterconvertDetailBySamID(id);
//			ArrayList als = new ArrayList();
//
//			for (int i = 0; i < spils.size(); i++) {
//				ProductInterconvertDetailForm smdf = new ProductInterconvertDetailForm();
//				ProductInterconvertDetail o = (ProductInterconvertDetail) spils
//						.get(i);
//				smdf.setProductid(o.getProductid());
//				smdf.setProductname(o.getProductname());
//				smdf.setSpecmode(o.getSpecmode());
//				smdf.setUnitid(o.getUnitid());
//				smdf.setUnitidname(Internation.getStringByKeyPositionDB(
//						"CountUnit", o.getUnitid().intValue()));
//				smdf.setBatch(o.getBatch());
//				smdf.setUnitprice(o.getQuantity());
//				smdf.setSubsum(o.getSubsum());
//
//				smdf.setQuantity(o.getQuantity());
//
//				als.add(smdf);
//			}

			// AppProductInterconvertDetail asma = new
			// AppProductInterconvertDetail();
			// List slrv = asma.getProductInterconvertDetailBySmIDNew(id);
			// ArrayList rvls = new ArrayList();
			// for (int r = 0; r < slrv.size(); r++) {
			// ProductInterconvertDetailForm oslaf = new
			// ProductInterconvertDetailForm();
			// ProductInterconvertDetail or = (ProductInterconvertDetail)
			// slrv.get(r);
			// oslaf.setApprovename(Internation.getStringByKeyPosition(
			// "SubApproveStatus", request, Integer.parseInt(or[4]
			// .toString()), "global.sys.SystemResource"));
			// oslaf.setApproveidname(au.getUsersByID(
			// Long.valueOf(or[2].toString())).getRealname());
			// oslaf.setApprovecontent(or[3].toString());
			// oslaf.setActidname(Internation.getStringByKeyPositionDB("ActID",
			// Integer.parseInt(or[6]
			// .toString())));
			// rvls.add(oslaf);
			// }

			// request.setAttribute("rvls", rvls);
			request.setAttribute("smid", id);
			request.setAttribute("als", spils);
			request.setAttribute("smf", sm);

			DBUserLog.addUserLog(userid, 7, "商品互转>>商品互转详情,编号：" + id);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
