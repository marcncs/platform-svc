package com.winsafe.drp.keyretailer.action.phone;

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
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class QueryCustomerSupportAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryCustomerSupportAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String organId = request.getParameter("organId");
			String year = request.getParameter("year");
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			List<Map<String,String>> dateList = appSBonusAppraise.getCustomerSupportAction(loginUsers.getMakeorganid(), organId, year);
			
			List<CustomerSupportJson> list = getCustomerSupportJsonList(dateList);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
					,loginUsers.getUserid(),"APP_RI","下级客户业务支持度评价",false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "失败,系统异常");
		}
		return null;
	}

	private List<CustomerSupportJson> getCustomerSupportJsonList(
			List<Map<String, String>> dateList) throws Exception { 
		if(dateList.size() == 0) {
			return null;
		}
		DecimalFormat rateFormat = new DecimalFormat("##.00");
		List<CustomerSupportJson> list = new ArrayList<CustomerSupportJson>();
		for(Map<String, String> data : dateList) {
			CustomerSupportJson radsj = new CustomerSupportJson();
			MapUtil.mapToObject(data, radsj);
			double curPoint = 0d;
			double targetPoint = 0d;
			if(!StringUtil.isEmpty(radsj.getCurPoint())) {
				curPoint = Double.valueOf(radsj.getCurPoint());
				radsj.setCurPoint(decimalFormat.format(curPoint));
			} else {
				radsj.setCurPoint("0");
			}
			if(!StringUtil.isEmpty(radsj.getTargetPoint())) {
				targetPoint = Double.valueOf(radsj.getTargetPoint());
				radsj.setTargetPoint(decimalFormat.format(targetPoint));
			}
			if(StringUtil.isEmpty(radsj.getIsSupported()) || "0".equals(radsj.getIsSupported())) {
				radsj.setIsSupported("3");
			}
			if(targetPoint != 0 && curPoint != 0) {
				radsj.setCompleteRate(rateFormat.format(ArithDouble.div2(curPoint, targetPoint, 4)*100)+"%");
			} else {
				radsj.setCompleteRate("0%");
			}
			list.add(radsj);
		}
		return list;
	}

	public class CustomerSupportJson {
		private String organId;
		private String organName;
		private String curPoint;
		private String targetPoint;
		private String isSupported;
		private String completeRate;
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getCurPoint() {
			return curPoint;
		}
		public void setCurPoint(String curPoint) {
			this.curPoint = curPoint;
		}
		public String getTargetPoint() {
			return targetPoint;
		}
		public void setTargetPoint(String targetPoint) {
			this.targetPoint = targetPoint;
		}
		public String getIsSupported() {
			return isSupported;
		}
		public void setIsSupported(String isSupported) {
			this.isSupported = isSupported;
		}
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getCompleteRate() {
			return completeRate;
		}
		public void setCompleteRate(String completeRate) {
			this.completeRate = completeRate;
		}
	}
	
}
