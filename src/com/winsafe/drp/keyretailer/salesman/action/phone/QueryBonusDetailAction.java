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
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusDetailAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusDetailAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year");
			String organId = request.getParameter("organId");
			String fromOrganId = request.getParameter("fromOrganId");
			String productName = request.getParameter("productName");
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			List<Map<String, String>> sBonusAppraise = appSBonusAppraise.getSBonusAppraiseDetail(year, organId, fromOrganId,productName);
			
			List<BonusJson> bonusJsonList = getBonusJsonList(sBonusAppraise);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bonusJsonList
					,loginUsers.getUserid(),"APP","积分查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private List<BonusJson> getBonusJsonList(List<Map<String, String>> sBonusAppraise) throws Exception {
		DecimalFormat pointFormat = new DecimalFormat("###,###");
		DecimalFormat ammountFormat = new DecimalFormat("###,##0.00");
		List<BonusJson> bonusJsonList = new ArrayList<BonusJson>();
		for(Map<String, String> data : sBonusAppraise) { 
			BonusJson bonusJson = new BonusJson();
			MapUtil.mapToObject(data, bonusJson);
			if(!StringUtil.isEmpty(bonusJson.getCurAmount())) {
				bonusJson.setCurAmount(ammountFormat.format(new BigDecimal(bonusJson.getCurAmount())));
			}
			if(!StringUtil.isEmpty(bonusJson.getTargetAmount())) {
				bonusJson.setTargetAmount(ammountFormat.format(new BigDecimal(bonusJson.getTargetAmount())));
			} 
			if(!StringUtil.isEmpty(bonusJson.getCurPoint())) {
				bonusJson.setCurPoint(pointFormat.format(new BigDecimal(bonusJson.getCurPoint())));
			}
			if(!StringUtil.isEmpty(bonusJson.getTargetPoint())) {
				bonusJson.setTargetPoint(pointFormat.format(new BigDecimal(bonusJson.getTargetPoint())));
			}
			if(!StringUtil.isEmpty(bonusJson.getAmountRate())) {
				bonusJson.setAmountRate(bonusJson.getAmountRate()+"%");
			} 
			if(!StringUtil.isEmpty(bonusJson.getPointRate())) {
				bonusJson.setPointRate(bonusJson.getPointRate()+"%");
			} 
			bonusJsonList.add(bonusJson);
		}
		return bonusJsonList;
	}

	public class BonusJson {
		private String targetPoint;
		private String curPoint;
		private String targetAmount;
		private String curAmount;
		private String productName;
		private String spec;
		private String amountRate;
		private String pointRate;
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
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
		public String getAmountRate() {
			return amountRate;
		}
		public void setAmountRate(String amountRate) {
			this.amountRate = amountRate;
		}
		public String getPointRate() {
			return pointRate;
		}
		public void setPointRate(String pointRate) {
			this.pointRate = pointRate;
		}
	}
	
}
