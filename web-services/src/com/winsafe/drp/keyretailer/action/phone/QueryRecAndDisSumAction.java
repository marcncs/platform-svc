package com.winsafe.drp.keyretailer.action.phone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryRecAndDisSumAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryRecAndDisSumAction.class);
	
	private AppUsers appUsers = new AppUsers();
//	private DecimalFormat decimalFormat = new DecimalFormat("###,###");
	private DecimalFormat amountFormat = new DecimalFormat("###,##0.00");
	private AppTakeTicket appTakeTicket = new AppTakeTicket();
	private AppBaseResource abr = new AppBaseResource();
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			//type 1.收货2.发货
			String type = request.getParameter("type");
			
			List<Map<String,String>> dateList = new ArrayList<Map<String,String>>();
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			if("1".equals(type)) {
				if(DealerType.BKD.getValue().equals(loginUsers.getOrganModel())) {
					dateList = appTakeTicket.getReceiveSummary(request, loginUsers.getMakeorganid());
				} else {
					dateList = appTakeTicket.getReceiveSummary(request, loginUsers.getMakeorganid());
				}
			} else {
				dateList = appTakeTicket.getDispatchSummary(request, loginUsers.getMakeorganid());
			}
			
			Collection<SummaryJson> list = getReceiveAndDispatchSummaryJsonList(dateList);
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
					,loginUsers.getUserid(),"APP_RI","流向查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private Collection<SummaryJson> getReceiveAndDispatchSummaryJsonList(
			List<Map<String, String>> dateList) throws Exception { 
		if(dateList.size() == 0) {
			return null;
		}
		Map<Integer, String> countUnitMap = abr.getBaseResourceMap("CountUnit");
		
		Map<String, SummaryJson> summaryMap = new TreeMap<String, SummaryJson>();
		
		for(Map<String, String> data : dateList) {
			String key = data.get("organname")+data.get("organid")+data.get("yearmonth");
			if(summaryMap.containsKey(key)) {
				SummaryDetailJson sdj = getSummaryDetailJson(data, countUnitMap);
				summaryMap.get(key).getDetail().add(sdj);
			} else {
				SummaryJson radsj = new SummaryJson();
				MapUtil.mapToObject(data, radsj);
				SummaryDetailJson sdj = getSummaryDetailJson(data, countUnitMap);
				List<SummaryDetailJson> detail = new ArrayList<SummaryDetailJson>();
				detail.add(sdj);
				radsj.setDetail(detail);
				summaryMap.put(key, radsj);
			}
		}
		return summaryMap.values();
	}
	
	
	private SummaryDetailJson getSummaryDetailJson(Map<String, String> data, Map<Integer, String> countUnitMap) throws Exception {
		SummaryDetailJson sdj = new SummaryDetailJson();
		MapUtil.mapToObject(data, sdj);
		if(!StringUtil.isEmpty(sdj.getQuantity())) {
			sdj.setQuantity(amountFormat.format(Double.valueOf(sdj.getQuantity()))+countUnitMap.get(Integer.valueOf(data.get("countunit"))));
		}
		return sdj;
	}
	
	public class SummaryJson {
		private String yearMonth;
		private String organId;
		private String organName;
		List<SummaryDetailJson> detail;
		public String getYearMonth() {
			return yearMonth;
		}
		public void setYearMonth(String yearMonth) {
			this.yearMonth = yearMonth;
		}
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
		public List<SummaryDetailJson> getDetail() {
			return detail;
		}
		public void setDetail(List<SummaryDetailJson> detail) {
			this.detail = detail;
		}
		
	}
	

	public class SummaryDetailJson {
		private String productName;
		private String quantity;
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
	}
	
}
