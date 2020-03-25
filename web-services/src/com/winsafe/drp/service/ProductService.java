package com.winsafe.drp.service;

import java.util.ArrayList;
import java.util.List; 

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.metadata.ProductType;

public class ProductService {
	
	private AppRole appRole = new AppRole();
	
	public boolean canModify(int productType, int userId) {
		String roleName=null;
		if(ProductType.HZ.getValue() == productType) {
			roleName="产品资料管理";
		} else if(ProductType.TOLLING.getValue() == productType) {
			roleName="产品资料管理-Tolling";
		} else if(ProductType.ES.getValue() == productType) {
			roleName="产品资料管理-ES";
		}
		return appRole.hasRole(userId, roleName);
	}

	public List<ProductType> getProductTypes(Integer userId) {
		String names = "'产品资料管理','产品资料管理-Tolling','产品资料管理-ES'";
		List<String> roleNames = appRole.getProductRoleNames(userId, names);
		return getProductTypes(roleNames);
	}

	private List<ProductType> getProductTypes(List<String> roleNames) {
		List<ProductType> types = new ArrayList<ProductType>();
		for(String roleName : roleNames) {
			if("产品资料管理".equals(roleName)) {
				types.add(ProductType.HZ);
			} else if("产品资料管理-Tolling".equals(roleName)) {
				types.add(ProductType.TOLLING);
			} else if("产品资料管理-ES".equals(roleName)) {
				types.add(ProductType.ES);
			} 
		}
		return types;
	}
}
