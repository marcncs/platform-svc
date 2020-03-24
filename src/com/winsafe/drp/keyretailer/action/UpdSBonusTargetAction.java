package com.winsafe.drp.keyretailer.action;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;

public class UpdSBonusTargetAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdSBonusTargetAction.class);
	
	private AppSBonusTarget ast = new AppSBonusTarget();
	private AppOrgan ao = new AppOrgan();
	private AppSBonusSetting ass = new AppSBonusSetting();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String id = request.getParameter("id");
			SBonusTarget sbt = ast.getSBonusTargetById(id);
			
			if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
				AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
				SBonusConfig sbc = appSBonusConfig.getPeriodConfig(sbt.getYear().toString(), DateUtil.getCurrentDateString(), 4);
				if(sbc == null) {
					request.setAttribute("result", "更新失败,目前不在积分目标设置周期内!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			
			
			Map map = MapUtil.changeStringKeyLow(request.getParameterMap());
			
			SBonusTarget localSbt = ast.getSBonusTarget(request.getParameter("toOrganId"), request.getParameter("productName"), request.getParameter("spec"), request.getParameter("year"), sbt.getId());
			if(localSbt != null) {
				if(!localSbt.getFromOrganId().equals(sbt.getFromOrganId())) {
					request.setAttribute("result", "修改失败,其他机构已设置过该产品的目标信息!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			
			MapUtil.mapToObject(map, sbt);
			
			sbt.setModifiedDate(new Date());
			Organ o = ao.getOrganByID(sbt.getToOrganId());
			double bonusPoint = 1;
			if (o!=null) {
				SBonusSetting sbs = ass.getSBonusSetting(sbt.getProductName(), sbt.getSpec(), o.getOrganModel().equals(DealerType.BKD.getValue())?DealerType.BKD.getValue():DealerType.BKR.getValue());
				if (sbs!=null) {
					bonusPoint = ArithDouble.mul(sbt.getTargetAmount(), sbs.getBonusPoint());
				}
			}
			sbt.setBonusPoint(bonusPoint);
			ast.updSBonusTarget(sbt);
			
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("updresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
