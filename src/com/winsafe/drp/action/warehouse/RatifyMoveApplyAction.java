package com.winsafe.drp.action.warehouse;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.server.StockMoveService;
import com.winsafe.drp.util.AuditMailUtil;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.task.SapUploadTask;

public class RatifyMoveApplyAction extends BaseAction {
	
	AppStockMove asm = new AppStockMove();
	AppStockMoveDetail asmd = new AppStockMoveDetail();
	AppWarehouse aw = new AppWarehouse();
	AppFUnit appfu = new AppFUnit();
	AppProductStockpileAll appps = new AppProductStockpileAll();
	AppMoveApplyDetail amad = new AppMoveApplyDetail();
	AppUserCustomer appUserCustomer = new AppUserCustomer();
	private static Logger logger = Logger.getLogger(SapUploadTask.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String noflag=request.getParameter("noflag");
		
		super.initdata(request);
		try{
			String id = request.getParameter("id");
			AppMoveApply asm = new AppMoveApply();
			MoveApply sm = asm.getMoveApplyByID(id);
			List<MoveApplyDetail> mads = new ArrayList<MoveApplyDetail>();
			String strdetailid[] = request.getParameterValues("detailid");//编号
			String strcanquantity[] = request.getParameterValues("canquantity");//数量

			for (int i = 0; i < strdetailid.length; i++) {
				MoveApplyDetail mad = amad
						.getMoveApplyDetailByID(strdetailid[i]);
				mad.setCanquantity(Double.valueOf(strcanquantity[i]));
				mads.add(mad);
			}
			
			// 如果仓库属性[是否负库存]为0,则检查库存
			Warehouse outWarehouse = aw.getWarehouseByID(sm.getOutwarehouseid());
			StringBuffer errorMsg = new StringBuffer();
			if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
				for (MoveApplyDetail sod : mads)
				{
					double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getCanquantity());
					double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sm.getOutwarehouseid());
					if (q > stock)
					{
						errorMsg.append("产品 [ " +sod.getProductid()+" "+ sod.getProductname() + " ] 库存不足<br/>");
					}
				}
			}
			if(errorMsg.length() > 0){
				request.setAttribute("result", errorMsg.toString() + "<br/>不能批准!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			//不批准原因
			String noreason=request.getParameter("reason");
			
			
			//查看是否有审批权限
			AppRole appRole = new AppRole();
			boolean firstAuditRole = false;
			boolean secondAuditRole = false;
			
			List<Role> roles = appRole.getRolesByUserid(userid);
			for(Role role : roles) {
				if("转仓审批一".equals(role.getRolename())) {
					firstAuditRole = true;
				}
				if("转仓审批二".equals(role.getRolename())) {
					secondAuditRole = true;
				}
			}
			
			if(firstAuditRole && StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue().equals(sm.getIsratify())) {
				if("1".equals(noflag)) {
					sm.setBlankoutreason(noreason);
					sm.setIsratify(StockMoveConfirmStatus.CHANNEL_MANAGER_DISAPPROVED.getValue());
				} else {
					//如果是机构内转仓 转一审核通过后就不需要再往后审批了
					if(sm.getOutorganid().equals(sm.getInorganid())){//机构一致，则为机构内转仓
						sm.setIsratify(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue());
					}else{
						sm.setIsratify(StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED.getValue());
					}
				}
			} else if(secondAuditRole && StockMoveConfirmStatus.IN_ASM_APPROVED.getValue().equals(sm.getIsratify())) {
				if("1".equals(noflag)) {
					sm.setBlankoutreason(noreason);
					sm.setIsratify(StockMoveConfirmStatus.CHANNEL_DISAPPROVED.getValue());
				} else {
					sm.setIsratify(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue());
				}
			} else {
				request.setAttribute("result", "当前用户没有该单据审核权限");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			//审批通过
			if(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue().equals(sm.getIsratify())){
				logger.error("审核转仓申请单"+sm.getId());
				StockMoveService sms = new StockMoveService();
				sms.addStockMove(sm, mads, users);
			}
			
			sm.setRatifyid(userid);
			sm.setRatifydate(DateUtil.getCurrentDate());
			asm.updMoveApply(sm);
			
			AuditMailUtil.addNotificationMail(sm);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, "机构间转仓申请", "销售管理>>批准机构间转仓申请,编号：" + id);
			return mapping.findForward("ratify");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		} 
	}
	
	
		
}
