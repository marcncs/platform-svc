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

public class ConfirmSBonusAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ConfirmSBonusAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			initdata(request);
			String year = request.getParameter("year"); 
			AppSBonusTarget appSBonusTarget = new AppSBonusTarget();
			AppOrgan appOrgan = new AppOrgan();
			
			if(appSBonusTarget.isConfirmed(year)) {
				request.setAttribute("result", "该年度已确认");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
			SBonusConfig sbc = appSBonusConfig.getPeriodConfig(year, DateUtil.getCurrentDateString(), 2);
			SBonusConfig ratio = appSBonusConfig.getSBonusConfigByYearAndType(Integer.parseInt(year), 3);
			if(ratio == null) {
				request.setAttribute("result", "该年度达标系数未设置 ");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			if(sbc != null) {
				request.setAttribute("result", "确认失败,当前评价周期还未结束!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			List<SBonusTarget> sBonusTargetList = appSBonusTarget.getNotCompletedTargetByYear(year);
			appSBonusTarget.confirmAll(year);
		 	for(SBonusTarget sbt : sBonusTargetList) {
				if(sbt.getFinalBonusPoint() != null && sbt.getRebate() != null
						&& sbt.getRebate()/sbt.getFinalBonusPoint() > Double.valueOf(ratio.getValue())) {
					Organ fromOrgan = appOrgan.getOrganByID(sbt.getFromOrganId());
					Organ toOrgan = appOrgan.getOrganByID(sbt.getToOrganId());
					AfficheService afficheService = new AfficheService();
					afficheService.addAffiche(AfficheType.BONUS_CONFIRM, BonusAfficheMsg.BONUS_CONFIRM_TITLE,BonusAfficheMsg.getError(BonusAfficheMsg.BONUS_CONFIRM, toOrgan.getOrganname(), year,fromOrgan.getOrganname()), sbt.getToOrganId(), users.getMakeorganid());
				}
			}
			
			DBUserLog.addUserLog(request, "更新,年度"+year);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("updresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
}
