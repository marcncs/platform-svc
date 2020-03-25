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
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class QueryBonusConfirmAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusConfirmAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppSBonusTarget asbt = new AppSBonusTarget(); 
	private AppSBonusConfig asc = new AppSBonusConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year");
			SBonusConfig sc = asc.getSBonusConfigByYearAndType(Integer.valueOf(year), 3);
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			List<Map<String,String>> dateList = asbt.getBonusConfirm(loginUsers.getMakeorganid(), year);
			
			List<BonusConfirmJson> list = getBonusConfirmJsonList(dateList, sc);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
					,loginUsers.getUserid(),"APP_RI","积分返利确认查询",false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "失败,系统异常");
		}
		return null;
	}

	private List<BonusConfirmJson> getBonusConfirmJsonList(
			List<Map<String, String>> dateList, SBonusConfig sc) throws Exception {  
		if(dateList.size() == 0) {
			return null;
		}
		double ratio = Double.valueOf(sc.getValue());
		List<BonusConfirmJson> list = new ArrayList<BonusConfirmJson>();
		
		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		DecimalFormat rebateFormat = new DecimalFormat("###,##0.0");
		
		for(Map<String, String> data : dateList) {
			BonusConfirmJson radsj = new BonusConfirmJson();
			MapUtil.mapToObject(data, radsj);
			double curPoint = 0d;
			double targetPoint = 0d;
			//是否达标
			if(!StringUtil.isEmpty(radsj.getCurPoint()) 
					&& !StringUtil.isEmpty(radsj.getTargetPoint())) {
				curPoint = Double.valueOf(radsj.getCurPoint());
				targetPoint = Double.valueOf(radsj.getTargetPoint());
				if(targetPoint != 0 && (curPoint/targetPoint) >= ratio) {
					radsj.setIsReached("1");
				} else {
					radsj.setIsReached("0");
				}
			} else {
				radsj.setIsReached("0");
			}
			if(!StringUtil.isEmpty(radsj.getCurPoint())) {
				radsj.setCurPoint(decimalFormat.format(curPoint));
			}
			if(!StringUtil.isEmpty(radsj.getTargetPoint())) {
				radsj.setTargetPoint(decimalFormat.format(targetPoint));
			}
			if(!StringUtil.isEmpty(radsj.getRebate())) {
				radsj.setRebate(rebateFormat.format(Double.valueOf(radsj.getRebate())));
			}
			if(StringUtil.isEmpty(radsj.getIsSupported())) {
				radsj.setIsSupported("0");
			}
			radsj.setRebateRate(radsj.getRebateRate());
			if(StringUtil.isEmpty(radsj.getIsConfirmed())) {
				radsj.setIsConfirmed("0");
			}
			list.add(radsj);
		}
		return list;
	}

	public class BonusConfirmJson {
		private String organId;
		private String organName;
		private String curPoint;
		private String targetPoint;
		private String rebate;
		private String rebateRate;
		private String isConfirmed;
		private String isReached;
		private String isSupported;
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
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getRebate() {
			return rebate;
		}
		public void setRebate(String rebate) {
			this.rebate = rebate;
		}
		public String getRebateRate() {
			return rebateRate;
		}
		public void setRebateRate(String rebateRate) {
			this.rebateRate = rebateRate;
		}
		public String getIsConfirmed() {
			return isConfirmed;
		}
		public void setIsConfirmed(String isConfirmed) {
			this.isConfirmed = isConfirmed;
		}
		public String getIsReached() {
			return isReached;
		}
		public void setIsReached(String isReached) {
			this.isReached = isReached;
		}
		public String getIsSupported() {
			return isSupported;
		}
		public void setIsSupported(String isSupported) {
			this.isSupported = isSupported;
		}
	}
	
	public static void main(String[] args) {
		DecimalFormat rebateFormat = new DecimalFormat("###.0");
		System.out.println(rebateFormat.format(0.80));
	}
}
