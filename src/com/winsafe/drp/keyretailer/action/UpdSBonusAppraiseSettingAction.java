package com.winsafe.drp.keyretailer.action;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.keyretailer.util.BonusAfficheMsg;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdSBonusAppraiseSettingAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdSBonusAppraiseSettingAction.class);
	
	private AppSBonusTarget appSBonusTarget = new AppSBonusTarget();
	/*private AppSBonusAccount appSBonusAccount = new AppSBonusAccount();
	private SBonusService sBonusService = new SBonusService();
	private AppOrgan appOrgan = new AppOrgan();*/
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String year = request.getParameter("year");
			String fromOrganId = request.getParameter("foid");
			String toOrganId = request.getParameter("toid");
			
			AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
			SBonusConfig sbc = appSBonusConfig.getPeriodConfig(year, DateUtil.getCurrentDateString(), 2);
			SBonusConfig ratio = appSBonusConfig.getSBonusConfigByYearAndType(Integer.parseInt(year), 3);
			
			List<SBonusTarget> sBonusTargetList = appSBonusTarget.getSBonusTargetForAppraise(year,fromOrganId,toOrganId);
			
			boolean isReached = false;
			if("complete".equals(request.getParameter("type"))) { //管理员确认
				if(sbc != null) {
					request.setAttribute("result", "确认失败,当前评价周期还未结束!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				for(SBonusTarget sbt : sBonusTargetList) {
					if(sbt.getIsComplete() != null && sbt.getIsComplete() == 1) {
						request.setAttribute("result", "该记录已确认过!");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
					if(sbt.getFinalBonusPoint() != null 
							&& sbt.getRebate()/sbt.getFinalBonusPoint() > Double.valueOf(ratio.getValue())) {
						isReached = true;
					}
					sbt.setIsComplete(1);
					sbt.setCompleteDate(DateUtil.getCurrentDate());
					appSBonusTarget.updSBonusTarget(sbt);
				}
				if(isReached) {
					AppOrgan appOrgan = new AppOrgan();
					Organ fromOrgan = appOrgan.getOrganByID(fromOrganId);
					Organ toOrgan = appOrgan.getOrganByID(toOrganId);
					AfficheService afficheService = new AfficheService();
					afficheService.addAffiche(AfficheType.BONUS_CONFIRM, BonusAfficheMsg.BONUS_CONFIRM_TITLE,BonusAfficheMsg.getError(BonusAfficheMsg.BONUS_CONFIRM, toOrgan.getOrganname(), year,fromOrgan.getOrganname()), toOrganId, users.getMakeorganid());
				}
			} else { //协议支持度设置
				if(sbc == null) {
					request.setAttribute("result", "设置失败,当前日期不在评价周期内!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				Integer isSupported = Integer.parseInt(request.getParameter("isSupported"));
				for(SBonusTarget sbt : sBonusTargetList) {
					if(sbt.getIsComplete() != null && sbt.getIsComplete() == 1) {
						request.setAttribute("result", "该记录已确认过,不能再重新评价!");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
					sbt.setIsSupported(isSupported);
					sbt.setSupportDate(DateUtil.getCurrentDate());
					appSBonusTarget.updSBonusTarget(sbt);
				}
			}
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改,编号" +year+fromOrganId+toOrganId+request.getParameter("isSupported")+request.getParameter("type"));
			return mapping.findForward("updresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
}
