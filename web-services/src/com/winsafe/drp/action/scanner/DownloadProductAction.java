package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class DownloadProductAction extends Action {
	
	private AppOrganProduct aop = new AppOrganProduct();
	private AppFUnit afun = new AppFUnit();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		List<ProductJson> list = null;
//		//下载当前用户下管辖内机构的可经营产品
//		//list = getRuleOrganProduct(users.getUserid());
//		List ruleList = aop.getRuleOrganProduct(users.getUserid());
//		List visitList = aop.getVisitOrganProduct(users.getUserid());
//		List result = unitList(ruleList, visitList);
//		list = getJsonList(result);
		//下载所有的可用产品
		list = getProductJsonList(getProducts());
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],产品下载",true);
		return null;
	}
	
	public List<Product> getProducts() throws Exception{
		
		return appProduct.getProductByUserFlag(1);
		
	}
	/**
	 * 合并两个集合
	 */
	private List unitList(List ruleList,List visitList){
		// 存放所有的集合
		Map<String, Object[]> result = new HashMap<String, Object[]>();
		
		for(int i=0 ; i<ruleList.size() ; i++){
			Object[] objs = (Object[]) ruleList.get(i);
			OrganProduct op = (OrganProduct) objs[0];
			Product p = (Product) objs[1];
			String key = op.getOrganid() + "_" + op.getProductid();
			if(!result.keySet().contains(key)){
				result.put(key, objs);
			}
		}
		for(int j=0 ; j < visitList.size() ; j++){
			Object[] objs = (Object[]) visitList.get(j);
			OrganProduct op = (OrganProduct) objs[0];
			Product p = (Product) objs[1];
			String key = op.getOrganid() + "_" + op.getProductid();
			if(!result.keySet().contains(key)){
				result.put(key, objs);
			}
		}
		
		return new ArrayList(result.values());
	}
	
	public List<ProductJson> getVisitOrganProduct(int userid) throws Exception{
		List list = aop.getVisitOrganProduct(userid);
		return getJsonList(list);
		
	}
	
	/**
	 * 转化为JSON数据
	 */
	private List<ProductJson> getJsonList(List list) throws Exception{
		List<ProductJson> result = new ArrayList<ProductJson>();
		if(list != null && list.size()>0){
			for(int i=0 ; i<list.size() ; i++){
				Object[] objs = (Object[]) list.get(i);
				OrganProduct op = (OrganProduct) objs[0];
				Product p = (Product) objs[1];
				ProductJson pJson = new ProductJson();
				pJson.setOrganID(op.getOrganid());
				pJson.setlCode(p.getmCode());
				pJson.setProductID(p.getId());
				pJson.setProductName(p.getProductname());
				pJson.setmCode(p.getmCode());
				pJson.setSpecMode(p.getSpecmode());
				pJson.setSunit(p.getSunit());
				//获取比例单位
				List<FUnit> funitList =  afun.getFUnitByProductID(p.getId());
				List<FUnitJson> fJsons = new ArrayList<FUnitJson>();
				for(FUnit fUnit : funitList){
					FUnitJson fJson = new FUnitJson();
					fJson.setUnitID(fUnit.getFunitid());
					fJson.setRate(fUnit.getXquantity());
					fJsons.add(fJson); 
				}
				pJson.setPackageSize(fJsons);
				result.add(pJson);
			}
		}
		return result;
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
				pJson.setOrganID("");
				pJson.setlCode(p.getmCode());
				pJson.setProductID(p.getId());
				pJson.setProductName(p.getProductname());
				pJson.setmCode(p.getmCode());
				pJson.setSpecMode(p.getSpecmode());
				pJson.setSunit(p.getSunit());
				//获取比例单位
				List<FUnit> funitList =  afun.getFUnitByProductID(p.getId());
				List<FUnitJson> fJsons = new ArrayList<FUnitJson>();
				for(FUnit fUnit : funitList){
					FUnitJson fJson = new FUnitJson();
					fJson.setUnitID(fUnit.getFunitid());
					fJson.setRate(fUnit.getXquantity());
					fJsons.add(fJson); 
				}
				pJson.setPackageSize(fJsons);
				result.add(pJson);
			}
		}
		return result;
	}
	
	/**
	 * 获取管辖机构下的可经营产品
	 */
	public List<ProductJson> getRuleOrganProduct(int userid) throws Exception{
		//查找可用的用户管辖机构内的产品
		List list = aop.getRuleOrganProduct(userid);
		return getJsonList(list);
	}
	/**
	 * 用于封装json数据
	 */
	public class ProductJson{
		private String organID;
		private String lCode;
		private String productID;
		private String productName;
		private String mCode;
		private String specMode;
		private Integer sunit;
		private  List<FUnitJson> packageSize;
		
		public String getOrganID() {
			return organID;
		}
		public void setOrganID(String organID) {
			this.organID = organID;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
				public List<FUnitJson> getPackageSize() {
			return packageSize;
		}
		public void setPackageSize(List<FUnitJson> packageSize) {
			this.packageSize = packageSize;
		}
		public String getProductID() {
			return productID;
		}
		public void setProductID(String productID) {
			this.productID = productID;
		}
		public String getmCode() {
			return mCode;
		}
		public void setmCode(String mCode) {
			this.mCode = mCode;
		}
		public String getSpecMode() {
			return specMode;
		}
		public void setSpecMode(String specMode) {
			this.specMode = specMode;
		}
		public String getlCode() {
			return lCode;
		}
		public void setlCode(String lCode) {
			this.lCode = lCode;
		}
		public Integer getSunit() {
			return sunit;
		}
		public void setSunit(Integer sunit) {
			this.sunit = sunit;
		}
		
		
	}
	public class FUnitJson{
		private Integer unitID;
		private Double rate;
		public Integer getUnitID() {
			return unitID;
		}
		public void setUnitID(Integer unitID) {
			this.unitID = unitID;
		}
		public Double getRate() {
			return rate;
		}
		public void setRate(Double rate) {
			this.rate = rate;
		}
	}
	
}
