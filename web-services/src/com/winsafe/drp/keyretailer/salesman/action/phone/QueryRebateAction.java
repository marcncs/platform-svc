package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryRebateAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryRebateAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	private AppSBonusConfig asc = new AppSBonusConfig();
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try { 
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year");
			String organId = request.getParameter("organId");
			String isConfirmed = request.getParameter("isConfirmed");
			String organModel = request.getParameter("organType");
			String areaId = request.getParameter("areaId");
			
			SBonusConfig sc = asc.getSBonusConfigByYearAndType(Integer.valueOf(year), 3);
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			String condition = SBonusService.getWhereCondition(loginUsers);
			
			List<Map<String, String>> sBonusAppraise = appSBonusAppraise.getRebate(year, organId, condition, isConfirmed,organModel,areaId);
			
			List<BonusJson> bonusJsonList = getBonusJsonList(sBonusAppraise, sc);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bonusJsonList
					,loginUsers.getUserid(),"APP","积分查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private List<BonusJson> getBonusJsonList(
			List<Map<String, String>> sBonusAppraise, SBonusConfig sc) throws Exception { 
		List<BonusJson> bonusJsonList = new ArrayList<BonusJson>();
		if(sc == null) {
			return bonusJsonList;
		}
		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		DecimalFormat rebateFormat = new DecimalFormat("###,##0.0");
		DecimalFormat rateFormat = new DecimalFormat("##0.00");
		double ratio = Double.valueOf(sc.getValue());
		for(Map<String, String> data : sBonusAppraise) {
			BonusJson bonusJson = new BonusJson();
			MapUtil.mapToObject(data, bonusJson);
			
			double curPoint = 0d;
			double targetPoint = 0d;
			
			//是否达标
			if(!StringUtil.isEmpty(bonusJson.getCurPoint()) 
					&& !StringUtil.isEmpty(bonusJson.getCurPoint())) {
				curPoint = Double.valueOf(bonusJson.getCurPoint());
				targetPoint = Double.valueOf(bonusJson.getTargetPoint());
				if(targetPoint != 0 && (curPoint/targetPoint) >= ratio) {
					bonusJson.setIsReached("1");
				} else {
					bonusJson.setIsReached("0");
				}
			} else {
				bonusJson.setIsReached("0");
			}
			
			if(!StringUtil.isEmpty(bonusJson.getFinalPoint())) {
				bonusJson.setFinalPoint(rebateFormat.format(new BigDecimal(bonusJson.getFinalPoint()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getCurPoint())) {
				bonusJson.setCurPoint(decimalFormat.format(new BigDecimal(bonusJson.getCurPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getTargetPoint())) {
				bonusJson.setTargetPoint(decimalFormat.format(new BigDecimal(bonusJson.getTargetPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getAppraise())) {
				bonusJson.setAppraise(rateFormat.format(Double.valueOf(bonusJson.getAppraise()))); 
			} 
			
			if(StringUtil.isEmpty(bonusJson.getIsSupported())) {
				bonusJson.setIsSupported("0");
			}
			bonusJson.setAppraise(bonusJson.getAppraise());
			if(StringUtil.isEmpty(bonusJson.getIsConfirmed())) {
				bonusJson.setIsConfirmed("0");
			}
			
			bonusJsonList.add(bonusJson);
		}
		return bonusJsonList;
	}

	public class BonusJson {
		//TT单据号
		private String organId;
		private String organName;
		private String organType;
		private String year;
		private String targetPoint;
		private String curPoint;
		private String appraise;
		private String finalPoint;
		private String isConfirmed;
		private String isSupported;
		private String isReached;
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
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getAppraise() {
			return appraise;
		}
		public void setAppraise(String appraise) {
			this.appraise = appraise;
		}
		public String getIsConfirmed() {
			return isConfirmed;
		}
		public void setIsConfirmed(String isConfirmed) {
			this.isConfirmed = isConfirmed;
		}
		public String getTargetPoint() {
			return targetPoint;
		}
		public void setTargetPoint(String targetPoint) {
			this.targetPoint = targetPoint;
		}
		public String getCurPoint() {
			return curPoint;
		}
		public void setCurPoint(String curPoint) {
			this.curPoint = curPoint;
		}
		public String getFinalPoint() {
			return finalPoint;
		}
		public void setFinalPoint(String finalPoint) {
			this.finalPoint = finalPoint;
		}
		public String getIsSupported() {
			return isSupported;
		}
		public void setIsSupported(String isSupported) {
			this.isSupported = isSupported;
		}
		public String getIsReached() {
			return isReached;
		}
		public void setIsReached(String isReached) {
			this.isReached = isReached;
		}
		public String getOrganType() {
			return organType;
		}
		public void setOrganType(String organType) {
			this.organType = organType;
		}
		
	}
	
}
