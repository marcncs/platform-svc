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
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.metadata.BonusType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryBonusDetailAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryBonusDetailAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("#,###");
	private DecimalFormat ammountFormat = new DecimalFormat("#,##0.00");
	
	private AppSBonusDetail appSBonusDetail = new AppSBonusDetail();
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String fromDate = request.getParameter("fromDate");
			String organId = request.getParameter("organId"); 
			
			String year = fromDate.split("-")[0];
			String month = fromDate.split("-")[1];
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			List<Map<String,String>> detailList = appSBonusDetail.getSBonusDetailsBySql(year, month, loginUsers.getMakeorganid(),organId);
			List<BonusDetailJson> bdjList = getBonusDetailJsonList(detailList, loginUsers);
			
			if (bdjList==null || bdjList.size()==0) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "本月没有积分累积，请选择其他月份");
				return null;
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, bdjList
					,loginUsers.getUserid(),"APP","积分明细查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private List<BonusDetailJson> getBonusDetailJsonList(
			List<Map<String, String>> detailList, UsersBean loginUsers) throws Exception { 
		List<BonusDetailJson> bdjList = null;
		if(detailList != null && detailList.size() > 0) {
			AppBaseResource abr = new AppBaseResource();
			Map<Integer, String> countUnitMap = abr.getBaseResourceMap("CountUnit");
			bdjList = new ArrayList<BonusDetailJson>();
			for(Map<String,String> map : detailList) {
				BonusDetailJson bonusJson = new BonusDetailJson();
				MapUtil.mapToObject(map, bonusJson);
				if(!StringUtil.isEmpty(bonusJson.getBonusPoint())) {
					bonusJson.setBonusPoint(decimalFormat.format(new BigDecimal(bonusJson.getBonusPoint()).setScale(0, BigDecimal.ROUND_HALF_UP)));
				}
				if(!StringUtil.isEmpty(bonusJson.getAmount())) {
					bonusJson.setAmount(ammountFormat.format(new BigDecimal(bonusJson.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP))+countUnitMap.get(Integer.valueOf(map.get("countunit"))));
				}
				BonusType bonusType =BonusType.parseByValue(Integer.valueOf(bonusJson.getBonusType()));
				if(bonusType != null) {
					bonusJson.setBonusType(bonusType.getName());
				}
				bdjList.add(bonusJson);
			}
		}
		return bdjList;
	}

	public class BonusDetailJson {
		private String makeDate;
		private String organName;
		private String bonusType;
		private String productName;
		private String spec;
		private String bonusPoint;
		private String amount;
		
		public String getMakeDate() {
			return makeDate;
		}
		public void setMakeDate(String makeDate) {
			this.makeDate = makeDate;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getBonusType() {
			return bonusType;
		}
		public void setBonusType(String bonusType) {
			this.bonusType = bonusType;
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
		public String getBonusPoint() {
			return bonusPoint;
		}
		public void setBonusPoint(String bonusPoint) {
			this.bonusPoint = bonusPoint;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
	}
	
	public static void main(String[] args) {
		DecimalFormat amountFormat = new DecimalFormat("###");
		System.out.println(amountFormat.format(22342343.0));
		
	}
	
}
