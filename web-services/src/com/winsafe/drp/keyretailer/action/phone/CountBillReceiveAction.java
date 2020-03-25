package com.winsafe.drp.keyretailer.action.phone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Internation;


public class CountBillReceiveAction extends BaseAction {
	private Logger logger = Logger.getLogger(CountBillReceiveAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppStockAlterMove asam = new AppStockAlterMove();
	private DecimalFormat decimalFormat = new DecimalFormat("###0.00");
	private DecimalFormat quantityFormat = new DecimalFormat("###0");
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String scannerno = request.getParameter("IMEI_number"); 
			
			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			Map map = new HashMap();
			if(loginUsers != null){
				int count = asam.countStockAlterMoveReceive(request, loginUsers);
				map.put("count", String.valueOf(count));
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, map
					,loginUsers.getUserid(),"APP","收货确认数量",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * @param ttList
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param ttList
	 * @return
	 * @throws Exception
	 */
	private Collection<BillReceiveJson> getBillReceiveJsonList(List<Map<String, String>> ttList) throws Exception {
		Map<String, BillReceiveJson> jsonMap = new HashMap<String, BillReceiveJson>();
		for(Map<String, String> map : ttList) {
			if(jsonMap.containsKey(map.get("billno"))) {
				jsonMap.get(map.get("billno")).getDetails().add(getBillReceiveDetailJson(map));
			} else {
				BillReceiveJson sobj = new BillReceiveJson();
				MapUtil.mapToObject(map, sobj);
				
				
				List<BillReceiveDetailJson> sobjds = new ArrayList<BillReceiveDetailJson>();
				sobj.setDetails(sobjds);
				
				sobjds.add(getBillReceiveDetailJson(map));
				
				jsonMap.put(sobj.getBillNo(), sobj);
			}
		}
		return jsonMap.values();
	}
	
	
	private BillReceiveDetailJson getBillReceiveDetailJson(Map<String, String> map) throws Exception {
		BillReceiveDetailJson sobjd = new BillReceiveDetailJson();
		MapUtil.mapToObject(map, sobjd);
		//单位
		if(map.containsKey("unitid")) {
			sobjd.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", Integer.valueOf(map.get("unitid").toString())));
		}
		return sobjd;
	}

	public class BillReceiveJson {
		//TT单据号
		private String billNo;
		private String fromOrgName;
		private String toOrgName;
		private String billDate;
		private List<BillReceiveDetailJson> details;
		public String getBillNo() {
			return billNo;
		}
		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}
		public String getFromOrgName() {
			return fromOrgName;
		}
		public void setFromOrgName(String fromOrgName) {
			this.fromOrgName = fromOrgName;
		}
		public String getToOrgName() {
			return toOrgName;
		}
		public void setToOrgName(String toOrgName) {
			this.toOrgName = toOrgName;
		}
		public String getBillDate() {
			return billDate;
		}
		public void setBillDate(String billDate) {
			this.billDate = billDate;
		}
		public List<BillReceiveDetailJson> getDetails() {
			return details;
		}
		public void setDetails(List<BillReceiveDetailJson> details) {
			this.details = details;
		}
		
	}
	
	public class BillReceiveDetailJson {
		private String productId;
		private String productName;
		private String specMode;
		private String takeQuantity;
		private String mcode;
		private String unitname;
		
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getSpecMode() {
			return specMode;
		}
		public void setSpecMode(String specMode) {
			this.specMode = specMode;
		}
		public String getTakeQuantity() {
			return takeQuantity;
		}
		public void setTakeQuantity(String takeQuantity) {
			this.takeQuantity = takeQuantity;
		}
		public String getMcode() {
			return mcode;
		}
		public void setMcode(String mcode) {
			this.mcode = mcode;
		}
		public String getUnitname() {
			return unitname;
		}
		public void setUnitname(String unitname) {
			this.unitname = unitname;
		}
		
	
	}
}
