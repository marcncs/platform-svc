package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class QueryProductAction extends Action {
	
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		List<ProductJson> list = null;
		//下载所有的可用产品
		list = getProductJsonList(getProducts());
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],产品下载",false);
		return null;
	}
	
	public List<Product> getProducts() throws Exception{
		
		return appProduct.getProductByUserFlag(1);
		
	}
	
	
	/**
	 * 转化为JSON数据
	 */
	public List<ProductJson> getProductJsonList(List<Product> list) throws Exception{
		List<ProductJson> result = new ArrayList<ProductJson>();
		if(list != null && list.size()>0){
			for(int i=0 ; i<list.size() ; i++){
				Product p = list.get(i);
				ProductJson pJson = new ProductJson();
				pJson.setMcode(p.getmCode());
				pJson.setProductId(p.getId());
				pJson.setProductName(p.getProductname());
				pJson.setSpecMode(p.getSpecmode());
				result.add(pJson);
			}
		}
		return result;
	}
	
	/**
	 * 用于封装json数据
	 */
	public class ProductJson{
		private String productId;
		private String productName;
		private String mcode;
		private String specMode;
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
		public String getMcode() {
			return mcode;
		}
		public void setMcode(String mcode) {
			this.mcode = mcode;
		}
		public String getSpecMode() {
			return specMode;
		}
		public void setSpecMode(String specMode) {
			this.specMode = specMode;
		}
	}
}
