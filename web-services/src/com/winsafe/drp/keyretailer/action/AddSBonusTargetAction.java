package com.winsafe.drp.keyretailer.action;


import java.util.Calendar;
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
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;

public class AddSBonusTargetAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSBonusTargetAction.class);
	
	private AppSBonusTarget ast = new AppSBonusTarget();
	private AppSBonusAccount asa = new AppSBonusAccount();
	private AppOrgan ao = new AppOrgan();
	private AppSBonusSetting ass = new AppSBonusSetting();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			SBonusTarget sbt = new SBonusTarget();
			//将Map中对应的值赋值给实例
			Map map = MapUtil.changeStringKeyLow(request.getParameterMap());
			MapUtil.mapToObject(map, sbt);
			
			if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
				AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
				SBonusConfig sbc = appSBonusConfig.getPeriodConfig(sbt.getYear().toString(), DateUtil.getCurrentDateString(), 4);
				if(sbc == null) {
					request.setAttribute("result", "新增失败,目前不在积分目标设置周期内!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			
			
			SBonusAccount  sba= asa.getSBonusAccountByOrganId(sbt.getToOrganId());
			if (sba==null) {
				request.setAttribute("result", "新增失败,目标机构不是银河会员,不在积分范围内!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			sbt.setAccountId(sba.getAccountId());
			sbt.setModifiedDate(new Date());
			Organ o = ao.getOrganByID(sbt.getToOrganId());
			double bonusPoint = 0;
			if (o!=null) {
				SBonusSetting sbs = ass.getSBonusSetting(sbt.getProductName(), sbt.getSpec(), o.getOrganModel().equals(DealerType.BKD.getValue())?DealerType.BKD.getValue():DealerType.BKR.getValue());
				if (sbs!=null) {
					bonusPoint = sbt.getTargetAmount()*sbs.getBonusPoint();
				} else {
					request.setAttribute("result", "新增失败,未找到产品对应的积分设置信息!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			sbt.setBonusPoint(bonusPoint);
			SBonusTarget localSbt = ast.getSBonusTarget(sbt.getToOrganId(), sbt.getProductName(), sbt.getSpec(), sbt.getYear().toString());
			if(localSbt != null) {
				request.setAttribute("result", "新增失败,该产品已经配置过!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			ast.addSBonusTarget(sbt);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		System.out.println(year);
	}
	

}
