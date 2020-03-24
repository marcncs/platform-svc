package com.winsafe.drp.keyretailer.action.phone;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	private DecimalFormat appraiseFormat = new DecimalFormat("#0.00");
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String period = request.getParameter("year");
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			Map<String,String> sBonusAppraise = appSBonusAppraise.getSBonusByOrganIdandYear(loginUsers.getMakeorganid(), period);
			
			BonusJson bonusJson = getBonusJson(sBonusAppraise, loginUsers, period);
			
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bonusJson
					,loginUsers.getUserid(),"APP","积分查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private BonusJson getBonusJson(Map<String, String> sBonusAppraise, UsersBean loginUsers, String period) throws Exception {
		BonusJson bonusJson = new BonusJson(); 
		if(sBonusAppraise != null) {
			MapUtil.mapToObject(sBonusAppraise, bonusJson);
			bonusJson.setOrganId(loginUsers.getMakeorganid());
			bonusJson.setOrganName(loginUsers.getMakeorganname());
			
			double actualPoint = 0d;
			double bonusPoint = 0d;
			double appraise = 0d;
			if(!StringUtil.isEmpty(bonusJson.getActualPoint())) {
				actualPoint = Double.valueOf(bonusJson.getActualPoint());
				bonusJson.setActualPoint(decimalFormat.format(new BigDecimal(actualPoint).setScale(1, BigDecimal.ROUND_HALF_UP)));
			} else {
				bonusJson.setActualPoint("0");
			}
			if(!StringUtil.isEmpty(bonusJson.getBonusTarget())) {
				bonusJson.setBonusTarget(decimalFormat.format(new BigDecimal(Double.valueOf(bonusJson.getBonusTarget())).setScale(0, BigDecimal.ROUND_HALF_UP)));
			} else {
				bonusJson.setBonusTarget("0");
			}
			
			if(!StringUtil.isEmpty(bonusJson.getBonusPoint())) {
				bonusPoint = Double.valueOf(bonusJson.getBonusPoint());
				bonusJson.setBonusPoint(decimalFormat.format(new BigDecimal(bonusPoint).setScale(0, BigDecimal.ROUND_HALF_UP)));
			} else {
				bonusJson.setBonusPoint("0");
			}
			
			if(bonusPoint != 0) {
				appraise = ArithDouble.div(actualPoint, bonusPoint,2);
			}
			bonusJson.setAppraise(appraiseFormat.format((appraise)));
			
			String backproift = "";
			if ("0".equals(bonusJson.getActualPoint())) {
				backproift = "0";
			} else {
				backproift = "1";
			}
			bonusJson.setBackprofit(backproift);
			bonusJson.setYear(period);
		}
		
		/*AppSBonusTarget app = new AppSBonusTarget();
		double str = app.getSBonusTargetTotalById(period, loginUsers.getMakeorganid());
		
		String bonusTargetTemp = decimalFormat.format(new BigDecimal(str).setScale(0, BigDecimal.ROUND_HALF_UP));
		if (bonusTargetTemp.contains(".")) {
			bonusJson.setBonusTarget(bonusTargetTemp.substring(0,bonusTargetTemp.indexOf(".")));
		} else {
			bonusJson.setBonusTarget(bonusTargetTemp);
		}*/
		
		return bonusJson;
	}

	public class BonusJson {
		//TT单据号
		private String organId;
		private String organName;
		private String accountId;
		private String year;
		private String bonusTarget;
		private String bonusPoint;
		private String appraise;
		private String actualPoint;
		private String backprofit;
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getBonusTarget() {
			return bonusTarget;
		}
		public void setBonusTarget(String bonusTarget) {
			this.bonusTarget = bonusTarget;
		}
		public String getBonusPoint() {
			return bonusPoint;
		}
		public void setBonusPoint(String bonusPoint) {
			this.bonusPoint = bonusPoint;
		}
		public String getAppraise() {
			return appraise;
		}
		public void setAppraise(String appraise) {
			this.appraise = appraise;
		}
		public String getActualPoint() {
			return actualPoint;
		}
		public void setActualPoint(String actualPoint) {
			this.actualPoint = actualPoint;
		}
		public String getBackprofit() {
			return backprofit;
		}
		public void setBackprofit(String backprofit) {
			this.backprofit = backprofit;
		}
		
	}
	
}
