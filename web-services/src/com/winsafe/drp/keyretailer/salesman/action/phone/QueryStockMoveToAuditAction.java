package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class QueryStockMoveToAuditAction extends Action{
	
	private AppSalesMan appSalesMan = new AppSalesMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username");
		// 判断用户是否存在
		UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
		String billNo = request.getParameter("billNo");
		
		List<Map<String, String>> list = new ArrayList<>();
		
		if(UserType.CM.getValue().equals(loginUsers.getUserType())) {
			//查看是否有审批权限
			boolean firstAuditRole = false;
			boolean secondAuditRole = false;
			AppRole appRole = new AppRole();
			List<Role> roles = appRole.getRolesByUserid(loginUsers.getUserid());
			for(Role role : roles) {
				if("转仓审批一".equals(role.getRolename())) {
					firstAuditRole = true;
				}
				if("转仓审批二".equals(role.getRolename())) {
					secondAuditRole = true;
				}
			}
			if(firstAuditRole || secondAuditRole) {
				list = appSalesMan.getStockMoveToAuditByCM(billNo, firstAuditRole, secondAuditRole);
			}
		} else {
			list = appSalesMan.getStockMoveToAudit(loginUsers.getUserid(), billNo);
		}
		
		
		Collection<StockMoveJson> ciList = getStockMoveJsonList(list);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ciList
				,loginUsers.getUserid(),"APP_RI","转仓单审核",true);
		return null;
	}
	
	private Collection<StockMoveJson> getStockMoveJsonList(
			List<Map<String, String>> list) throws Exception { 
		Map<String,StockMoveJson> map = new TreeMap<String, StockMoveJson>();
		for(Map<String, String> data : list) {
			if(map.containsKey(data.get("billno"))) {
				ProductDetail pd = new ProductDetail();
				MapUtil.mapToObject(data, pd);
				pd.setUnitName("箱");
				map.get(data.get("billno")).getProDetails().add(pd);
			} else {
				StockMoveJson smj = new StockMoveJson();
				MapUtil.mapToObject(data, smj);
				if(StringUtil.isEmpty(StringUtil.removeNull(smj.getStatus()))) {
					smj.setStatus("0");
				}
				StockMoveConfirmStatus status = StockMoveConfirmStatus.parseByValue(Integer.valueOf(smj.getStatus()));
				if(status != null) {
					smj.setStatus(status.getName());
				} else {
					smj.setStatus(StockMoveConfirmStatus.NOT_AUDITED.getName());
				}
				if("1".equals(smj.getType())) {
					smj.setType("买卖");
				} else if("2".equals(smj.getType())){
					smj.setType("借换");
				} else if("3".equals(smj.getType())) {
					smj.setType("平台交易");
				}
				List<ProductDetail>  proDetails = new ArrayList<ProductDetail>();
				smj.setProDetails(proDetails);
				ProductDetail pd = new ProductDetail();
				MapUtil.mapToObject(data, pd);
				pd.setUnitName("箱");
				proDetails.add(pd);
				map.put(smj.getBillNo(), smj);
			}
		}
		return map.values();
	}

	public class StockMoveJson {
		private String billNo;
		private String fromOrgan;
		private String fromWarehouse;
		private String toOrgan;
		private String toWarehouse;
		private String status;
		private String type;
		private String reason;
		private List<ProductDetail>  proDetails;
		public String getBillNo() {
			return billNo;
		}
		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}
		public String getFromOrgan() {
			return fromOrgan;
		}
		public void setFromOrgan(String fromOrgan) {
			this.fromOrgan = fromOrgan;
		}
		public String getFromWarehouse() {
			return fromWarehouse;
		}
		public void setFromWarehouse(String fromWarehouse) {
			this.fromWarehouse = fromWarehouse;
		}
		public String getToOrgan() {
			return toOrgan;
		}
		public void setToOrgan(String toOrgan) {
			this.toOrgan = toOrgan;
		}
		public String getToWarehouse() {
			return toWarehouse;
		}
		public void setToWarehouse(String toWarehouse) {
			this.toWarehouse = toWarehouse;
		}
		public List<ProductDetail> getProDetails() {
			return proDetails;
		}
		public void setProDetails(List<ProductDetail> proDetails) {
			this.proDetails = proDetails;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
	}
	
	public class ProductDetail {
		private String productId;
		private String productName;
		private String spec;
		private String quantity;
		private String unitName;
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
		
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getUnitName() {
			return unitName;
		}
		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}
		
	}
}

