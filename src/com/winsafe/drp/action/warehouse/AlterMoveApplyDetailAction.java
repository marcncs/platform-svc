package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AlterMoveApplyDetailForm;
import com.winsafe.drp.dao.AlterMoveApplyForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.hbm.util.Internation;

public class AlterMoveApplyDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String id = request.getParameter("ID");
		try {
			AppAlterMoveApply asm = new AppAlterMoveApply();
			AppInvoiceConf aic = new AppInvoiceConf();
			AppUsers au = new AppUsers();
			AppOrgan ao = new AppOrgan();
			//AppWarehouse aw = new AppWarehouse();

			AlterMoveApply sm = asm.getAlterMoveApplyByID(id);
			AlterMoveApplyForm smf = new AlterMoveApplyForm();
			smf.setId(id);
			smf.setMovedate(sm.getMovedate());
			smf.setOutorganid(sm.getOutorganid());
			smf.setOutorganidname(ao.getOrganByID(sm.getOutorganid()).getOrganname());
			smf.setMakeorganidname(sm.getMakeorganidname());
			smf.setTotalsum(sm.getTotalsum());
			smf.setInvmsgname(aic.getInvoiceConfById(sm.getInvmsg()).getIvname());
			smf.setTickettitle(sm.getTickettitle());
			smf.setPaymentmodename(Internation.getStringByPayPositionDB(sm.getPaymentmode()));
			smf.setTransportmodename(Internation.getStringByKeyPositionDB(
					"TransportMode", sm.getTransportmode()));
			smf.setTransportaddr(sm.getTransportaddr());
			
			smf.setMovecause(sm.getMovecause());
			smf.setRemark(sm.getRemark());
			smf.setIstransname(Internation.getStringByKeyPosition(
					"YesOrNo", request, sm.getIstrans(),
			"global.sys.SystemResource"));
			smf.setIsaudit(sm.getIsaudit());
			smf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, sm.getIsaudit(), "global.sys.SystemResource"));
			if (sm.getAuditid() > 0) {
				smf.setAuditidname(au.getUsersByid(sm.getAuditid())
						.getRealname());
			} else {
				smf.setAuditidname("");
			}
			smf.setAuditdate(sm.getAuditdate());
			//smf.setAuditdatename(DateUtil.formatDate(sm.getAuditdate()));

			smf.setMakeidname(au.getUsersByid(sm.getMakeid()).getRealname());
			smf.setMakedate(sm.getMakedate());
			//smf.setMakedatename(DateUtil.formatDate(sm.getMakedate()));
			smf.setIsratify(sm.getIsratify());
			smf.setIsratifyname(Internation.getStringByKeyPosition("YesOrNo",
					request, sm.getIsratify(), "global.sys.SystemResource"));
			if (sm.getIsratify() > 0) {
				smf.setRatifyidname(au.getUsersByid(sm.getRatifyid())
						.getRealname());
			} else {
				smf.setRatifyidname("");
			}
			smf.setRatifydate(sm.getRatifydate());
			//smf.setShipmentdatename(DateUtil.formatDate(sm.getShipmentdate()));
			
//			smf.setIstally(sm.getIstally());
//			smf.setIstallyname(Internation.getStringByKeyPosition("YesOrNo",
//					request, sm.getIstally(), "global.sys.SystemResource"));
//			if (sm.getTallyid() > 0) {
//				smf.setTallyidname(au.getUsersByid(sm.getTallyid())
//						.getRealname());
//			} else {
//				smf.setTallyidname("");
//			}
//			smf.setTallydate(sm.getTallydate());
			
//			smf.setIscomplete(sm.getIscomplete());
//			smf.setIscompletename(Internation.getStringByKeyPosition("YesOrNo",
//					request, sm.getIscomplete(), "global.sys.SystemResource"));
//			smf.setReceiveorganidname(sm.getReceiveorganidname());
//			if (sm.getReceiveid() > 0) {
//				smf.setReceiveidname(au.getUsersByid(sm.getReceiveid())
//						.getRealname());
//			} else {
//				smf.setReceiveidname("");
//			}
//			smf.setReceivedate(sm.getReceivedate());
			smf.setIsblankout(sm.getIsblankout());
			smf.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, sm.getIsblankout(), "global.sys.SystemResource"));
			if (sm.getBlankoutid() != null && sm.getBlankoutid() > 0) {
				smf.setBlankoutidname(au.getUsersByid(sm.getBlankoutid())
						.getRealname());
			} else {
				smf.setBlankoutidname("");
			}
			smf.setBlankoutdate(sm.getBlankoutdate());
			smf.setBlankoutreason(sm.getBlankoutreason());
			//smf.setReceivedatename(DateUtil.formatDate(sm.getReceivedate()));

			AppAlterMoveApplyDetail asmd = new AppAlterMoveApplyDetail();
			List spils = asmd.getAlterMoveApplyDetailByAmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < spils.size(); i++) {
				AlterMoveApplyDetailForm smdf = new AlterMoveApplyDetailForm();
				AlterMoveApplyDetail o = (AlterMoveApplyDetail) spils.get(i);
				smdf.setProductid(o.getProductid());
				smdf.setProductname(o.getProductname());
				smdf.setSpecmode(o.getSpecmode());
				smdf.setUnitid(o.getUnitid());
				smdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", o.getUnitid().intValue()));
				smdf.setUnitprice(o.getUnitprice());
				smdf.setCanquantity(o.getCanquantity());
				smdf.setQuantity(o.getQuantity());
				smdf.setAlreadyquantity(o.getAlreadyquantity());
				smdf.setSubsum(o.getSubsum());

				als.add(smdf);
			}

			request.setAttribute("smid", id);
			request.setAttribute("als", als);
			request.setAttribute("smf", smf);

			//DBUserLog.addUserLog(userid, "机构调拨申请单详情,编号："+id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
