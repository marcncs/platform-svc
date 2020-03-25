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
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusListAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusListAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year");
			String organModel = request.getParameter("organModel");
			String organId = request.getParameter("organId");
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			String condition = SBonusService.getWhereCondition(loginUsers);
			
			List<Map<String, String>> sBonusAppraise = appSBonusAppraise.getBonusList(year, organId, condition);
			
			List<BonusJson> bonusJsonList = getBonusJsonList(sBonusAppraise);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bonusJsonList
					,loginUsers.getUserid(),"APP","积分查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private List<BonusJson> getBonusJsonList(
			List<Map<String, String>> sBonusAppraise) throws Exception {
		List<BonusJson> bonusJsonList = new ArrayList<BonusJson>();
		for(Map<String, String> data : sBonusAppraise) {
			BonusJson bonusJson = new BonusJson();
			MapUtil.mapToObject(data, bonusJson);
			if(!StringUtil.isEmpty(bonusJson.getFinalPoint())) {
				bonusJson.setFinalPoint(decimalFormat.format(new BigDecimal(bonusJson.getFinalPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getCurPoint())) {
				bonusJson.setCurPoint(decimalFormat.format(new BigDecimal(bonusJson.getCurPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getTargetPoint())) {
				bonusJson.setTargetPoint(decimalFormat.format(new BigDecimal(bonusJson.getTargetPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
			}
			if(!StringUtil.isEmpty(bonusJson.getAppraise())) {
				bonusJson.setAppraise(bonusJson.getAppraise());
			}
			if(StringUtil.isEmpty(bonusJson.getIsSupported())) {
				bonusJson.setIsSupported("0");
			}
			bonusJsonList.add(bonusJson);
		}
		return bonusJsonList;
	}

	public class BonusJson {
		//TT单据号
		private String organId;
		private String organName;
		private String fromOrganId;
		private String fromOrganName;
		private String year;
		private String targetPoint;
		private String curPoint;
		private String targetAmount;
		private String curAmount;
		private String appraise;
		private String finalPoint;
		private String isSupported;
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
		public String getTargetAmount() {
			return targetAmount;
		}
		public void setTargetAmount(String targetAmount) {
			this.targetAmount = targetAmount;
		}
		public String getCurAmount() {
			return curAmount;
		}
		public void setCurAmount(String curAmount) {
			this.curAmount = curAmount;
		}
		public String getAppraise() {
			return appraise;
		}
		public void setAppraise(String appraise) {
			this.appraise = appraise;
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
		public String getFromOrganId() {
			return fromOrganId;
		}
		public void setFromOrganId(String fromOrganId) {
			this.fromOrganId = fromOrganId;
		}
		public String getFromOrganName() {
			return fromOrganName;
		}
		public void setFromOrganName(String fromOrganName) {
			this.fromOrganName = fromOrganName;
		}
	}
	
}
