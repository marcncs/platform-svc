package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.server.StockMoveService;
import com.winsafe.drp.util.AuditMailUtil;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;

public class AuditStockMoveAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		AppMoveApply ama = new AppMoveApply();
		StockMoveService sms = new StockMoveService();
		String username = request.getParameter("Username");
		String billNo = request.getParameter("billNo");
		String isApprove = request.getParameter("isApprove");
		String disApproveReason = request.getParameter("reason");
		
		try {
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			MoveApply ma = ama.getMoveApplyByID(billNo);
			if(ma == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,未找到单据");
				return null;
			}
			
			if(!UserType.CM.getValue().equals(loginUsers.getUserType())
					&& !StockMoveConfirmStatus.NOT_AUDITED.getValue().equals(ma.getIsratify())
					&& !StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED.getValue().equals(ma.getIsratify())) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,单据状态不正确");
				return null;
			} else if(UserType.CM.getValue().equals(loginUsers.getUserType())
					&& !StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue().equals(ma.getIsratify())
					&& !StockMoveConfirmStatus.IN_ASM_APPROVED.getValue().equals(ma.getIsratify())) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,单据状态不正确");
				return null;
			}
			
		
			if(StockMoveConfirmStatus.NOT_AUDITED.getValue().equals(ma.getIsratify())) {
				if("1".equals(isApprove)) {
					ma.setIsratify(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
				} else {
					ma.setIsratify(StockMoveConfirmStatus.OUT_ASM_DISAPPROVE.getValue());
					ma.setBlankoutreason(disApproveReason);
				}
				
			} else if(StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED.getValue().equals(ma.getIsratify())){
				if("1".equals(isApprove)) {
					ma.setIsratify(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
				} else {
					ma.setIsratify(StockMoveConfirmStatus.IN_ASM_DISAPPROVED.getValue());
					ma.setBlankoutreason(disApproveReason);
				}
			} else if(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue().equals(ma.getIsratify())) {
				if("1".equals(isApprove)) {
					ma.setIsratify(StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED.getValue());
				} else {
					ma.setIsratify(StockMoveConfirmStatus.CHANNEL_MANAGER_DISAPPROVED.getValue());
					ma.setBlankoutreason(disApproveReason);
				}
			} else if(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue().equals(ma.getIsratify())) {
				if("1".equals(isApprove)) {
					ma.setIsratify(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue());
				} else {
					ma.setIsratify(StockMoveConfirmStatus.CHANNEL_DISAPPROVED.getValue());
					ma.setBlankoutreason(disApproveReason);
				}
			}
			
			//审批通过
			if(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue().equals(ma.getIsratify())){
				WfLogger.error("审核转仓申请单"+ma.getId());
				AppMoveApplyDetail amad = new AppMoveApplyDetail();
				List<MoveApplyDetail> mads = amad.getMoveApplyDetailByAmID(ma.getId());
				for(MoveApplyDetail mad : mads) {
					mad.setCanquantity(mad.getQuantity());
				}
				sms.addStockMove(ma, mads, loginUsers);
			}
			
			//发送邮件
			AuditMailUtil.addNotificationMail(ma);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "审核成功", billNo+ma.getIsratify()
					,loginUsers.getUserid(),"APP_RI","审核转仓单",true);
		} catch (Exception e) {
			WfLogger.error("审核转仓申请单", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,系统异常");
			throw e;
		}
		
		return null;
	}
	
}

