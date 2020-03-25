package com.winsafe.drp.keyretailer.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.pojo.SBonusAppraise;
import com.winsafe.drp.util.DBUserLog;

public class UpdSBonusAppraiseAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdSBonusAppraiseAction.class);
	
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	/*private AppSBonusAccount appSBonusAccount = new AppSBonusAccount();
	private SBonusService sBonusService = new SBonusService();
	private AppOrgan appOrgan = new AppOrgan();*/
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String accountId = request.getParameter("accountId");
			Double appraise =  Double.parseDouble((request.getParameter("appraise")==null?"0":request.getParameter("appraise")));
			SBonusAppraise sBonusAppraise = appSBonusAppraise.getSBonusAppraiseByAccountId(accountId);
			sBonusAppraise.setAppraise(appraise);
			sBonusAppraise.setActualPoint(appraise * sBonusAppraise.getBonusPoint());
			appSBonusAppraise.updSBonusAppraise(sBonusAppraise);
			
			/*Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1;
//			String id = request.getParameter("id");
			String accountId = request.getParameter("accountId");
//			String productId = request.getParameter("productId");
			double bonus = Double.parseDouble(request.getParameter("bonus")); 
			SBonusAppraise sBonusAppraise = appSBonusAppraise.getSBonusAppraiseByAccountId(accountId);
			SBonusAccount sBonusAccount = appSBonusAccount.getSBonusAccountByAccountId(accountId);
			Organ organ =  appOrgan.getOrganByID(sBonusAccount.getOrganId());
			//更新积分
			if(sBonusAppraise == null) { 
				sBonusAppraise = sBonusService.addSBonusAppraise(sBonusAccount.getAccountId(),sBonusAccount.getOrganId(),year);
			}
			sBonusAppraise.setActualPoint(sBonusAppraise.getActualPoint() + bonus);
			appSBonusAppraise.updSBonusAppraise(sBonusAppraise);
			
			//新增详情
			sBonusService.addSBonusDetail(sBonusAccount.getAccountId(), bonus, 0d, null, sBonusAccount.getOrganId(),organ.getId(), year, month, BonusType.ADJUST, 0, "");
			//新增消息
			sBonusService.addBonusAffiche("", "", organ.getId());*/
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改,编号" + sBonusAppraise.getId());
			return mapping.findForward("updresult");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
			throw e;
		}
	}
}
