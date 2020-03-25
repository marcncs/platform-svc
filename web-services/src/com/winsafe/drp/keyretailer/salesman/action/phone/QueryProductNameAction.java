package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class QueryProductNameAction extends Action {
	
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		//下载所有的可用产品
		List<Map<String,String>> list = getProducts();
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],产品名称下载",false);
		return null;
	}
	
	public List<Map<String,String>> getProducts() throws Exception{
		
		List<Map<String,String>> list = appProduct.getProductNameByUserFlag(1);
		for(Map<String,String> map : list) {
			map.put("productName", map.get("productname"));
			map.remove("productname");
		}
		return list;
		
	}
}
