package com.winsafe.drp.keyretailer.action.phone;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusTargetDetailAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusTargetDetailAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	private DecimalFormat amountFormat = new DecimalFormat("###,##0.00");
	private AppBaseResource abr = new AppBaseResource();
	
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
				detailList = appSBonusTarget.getSBonusTargetDetail(year, loginUsers.getMakeorganid(),organId);
			}
			if ("2".equals(type)) {
				detailList = appSBonusTarget.getMySBonusTargetDetail(year, loginUsers.getMakeorganid(),organId);
			}
//			System.out.println("size=" + detailList.size());
			Map<String,BonusTargetJson> btjMap = new HashMap<String, BonusTargetJson>();
//			List<BonusTargetDetailJson> bdjList = null;
			
			if(detailList != null && detailList.size() > 0) {
				Map<Integer, String> countUnitMap = abr.getBaseResourceMap("CountUnit");
//				bdjList = new ArrayList<BonusTargetDetailJson>();
				for(Map<String, String> map : detailList) {
					String unitName = countUnitMap.get(Integer.valueOf(map.get("countunit")));
					BonusTargetDetailJson bonusJson = new BonusTargetDetailJson();
					MapUtil.mapToObject(map, bonusJson);
					if(!StringUtil.isEmpty(bonusJson.getAmount())) {
						bonusJson.setAmount(amountFormat.format(new BigDecimal(bonusJson.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP))+unitName);
					}
					if(!StringUtil.isEmpty(bonusJson.getBonusPoint())) {
						bonusJson.setBonusPoint(decimalFormat.format(new BigDecimal(bonusJson.getBonusPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
					} else { 
						bonusJson.setBonusPoint("0");
					}
					if(btjMap.containsKey(map.get("organid"))) {
						BonusTargetJson btj = btjMap.get(map.get("organid"));
						btj.getDetails().add(bonusJson);
					} else {
						BonusTargetJson btj = new BonusTargetJson();
						MapUtil.mapToObject(map, btj);
						if(StringUtil.isEmpty(btj.getCurBonusPoint())) {
							btj.setCurBonusPoint("0");
						}
						List<BonusTargetDetailJson> bdjList = new ArrayList<BonusTargetDetailJson>();
						bdjList.add(bonusJson);
						btj.setDetails(bdjList);
						btjMap.put(map.get("organid"), btj);
					}
				}
			}
			
			if (btjMap==null || btjMap.size()==0) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "本年度没有积分累积，请选择其他年度");
				return null;
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, btjMap.values()
					,loginUsers.getUserid(),"APP","积分明细查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public class BonusTargetJson {
		private String year;
		private String organName;
		private String organId;
		private String curBonusPoint;
		private List<BonusTargetDetailJson> details;
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
		public List<BonusTargetDetailJson> getDetails() {
			return details;
		}
		public void setDetails(List<BonusTargetDetailJson> details) {
			this.details = details;
		}
		public String getCurBonusPoint() {
			return curBonusPoint;
		}
		public void setCurBonusPoint(String curBonusPoint) {
			this.curBonusPoint = curBonusPoint;
		}
		
	}

	public class BonusTargetDetailJson {
		//TT单据号
		private String amount;
		private String productName;
		private String spec;
		private String bonusPoint;
		private String curAmount;
		private String proCurBonusPoint;
		
	
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getBonusPoint() {
			return bonusPoint;
		}
		public void setBonusPoint(String bonusPoint) {
			this.bonusPoint = bonusPoint;
		}
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
		public String getCurAmount() {
			return curAmount;
		}
		public void setCurAmount(String curAmount) {
			this.curAmount = curAmount;
		}
		public String getProCurBonusPoint() {
			return proCurBonusPoint;
		}
		public void setProCurBonusPoint(String proCurBonusPoint) {
			this.proCurBonusPoint = proCurBonusPoint;
		}
		
	}
	
}
