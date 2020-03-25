package com.winsafe.drp.keyretailer.action.phone;

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
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusTargetAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusTargetAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	
	private AppSBonusTarget appSBonusTarget = new AppSBonusTarget();
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String year = request.getParameter("year");
			String organId = request.getParameter("organId");
			// type 1 ：人家给当前机构设置   2： 当前机构给别人设置的  默认1
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)) {
				type = "1";
			}
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			List<Map<String, String>> detailList = null;
			if ("1".equals(type)) {
				detailList = appSBonusTarget.getSBonusTarget(year, loginUsers.getMakeorganid(),organId);
			} 
			if ("2".equals(type)) {
				detailList = appSBonusTarget.getMySBonusTarget(year, loginUsers.getMakeorganid(),organId);
			}
			System.out.println("size=" + detailList.size());
			List<BonusTargetJson> bdjList = null;
			if(detailList != null && detailList.size() > 0) {
				bdjList = new ArrayList<BonusTargetJson>();
				for(Map<String, String> map : detailList) {
					bdjList.add(getBonusDetailJson(map, loginUsers));
				}
			}
			
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bdjList
					,loginUsers.getUserid(),"APP","积分明细查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private BonusTargetJson getBonusDetailJson(Map<String, String> map,
			UsersBean loginUsers) throws Exception { 
		BonusTargetJson bonusJson = new BonusTargetJson();
		MapUtil.mapToObject(map, bonusJson);
		if(StringUtil.isEmpty(bonusJson.getCurBonusPoint())) {
			bonusJson.setCurBonusPoint("0");
		} else {
			bonusJson.setCurBonusPoint(decimalFormat.format(Double.valueOf(bonusJson.getCurBonusPoint())));
		}
		String bonuspointStr = bonusJson.getBonuspoint();
		String bonuspointTemp = decimalFormat.format(new BigDecimal(Double.valueOf(bonuspointStr)).setScale(0, BigDecimal.ROUND_HALF_UP));
		if (bonuspointTemp.contains(".")) {
			bonusJson.setBonuspoint(bonuspointTemp.substring(0,bonuspointTemp.indexOf(".")));
		} else {
			bonusJson.setBonuspoint(bonuspointTemp);
		}
		return bonusJson;
	}
	

	public class BonusTargetJson {
		//TT单据号
		private String year;
		private String organName;
		private String organId;
		private String bonuspoint;
		private String curBonusPoint;
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getBonuspoint() {
			return bonuspoint;
		}
		public void setBonuspoint(String bonuspoint) {
			this.bonuspoint = bonuspoint;
		}
		public String getCurBonusPoint() {
			return curBonusPoint;
		}
		public void setCurBonusPoint(String curBonusPoint) {
			this.curBonusPoint = curBonusPoint;
		}
	}
	
}
