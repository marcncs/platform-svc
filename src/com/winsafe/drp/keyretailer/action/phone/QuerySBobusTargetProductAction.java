package com.winsafe.drp.keyretailer.action.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;


public class QuerySBobusTargetProductAction extends BaseAction {
	
	private AppSBonusTarget asbt = new AppSBonusTarget();
	private AppBaseResource abr = new AppBaseResource();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String year = request.getParameter("year");
		String organId = request.getParameter("organId");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		
		List<Map<String,String>> proData = asbt.getProductList(year, users.getMakeorganid(), organId);
		
		List<ProductJson> list = getProductJsonList(proData);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"BCS_RI","目标设置产品下载",false);
		return null;
	}
	
	/**
	 * 转化为JSON数据
	 */
	public List<ProductJson> getProductJsonList(List<Map<String, String>> proData) throws Exception{
		Map<Integer, String> countUnitMap = abr.getBaseResourceMap("CountUnit");
		List<ProductJson> result = new ArrayList<ProductJson>();
		if(proData != null && proData.size()>0){
			for(int i=0 ; i<proData.size() ; i++){
				Map<String, String> map = proData.get(i);
				ProductJson pJson = new ProductJson();
				MapUtil.mapToObject(map, pJson);
				if(!StringUtil.isEmpty(pJson.getCountUnit())) {
					pJson.setCountUnit(countUnitMap.get(Integer.parseInt(pJson.getCountUnit())));
				}
				result.add(pJson);
			}
		}
		return result;
	}
	
	/**
	 * 用于封装json数据
	 */
	public class ProductJson{
		private String productName;
		private String spec;
		private String countUnit;
		private String amount;
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
		public String getCountUnit() {
			return countUnit;
		}
		public void setCountUnit(String countUnit) {
			this.countUnit = countUnit;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
	}
}
