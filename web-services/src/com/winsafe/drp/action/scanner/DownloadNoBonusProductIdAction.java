package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class DownloadNoBonusProductIdAction extends Action {
	
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		List<Map<String,String>> list = null;
		list = getProductJsonList(getNoBonusProductIds());
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],产品下载",true);
		return null;
	}
	
	public Set<String> getNoBonusProductIds() throws Exception{
		return appProduct.getNoBonusProductIdSet();
	}
	
	/**
	 * 转化为JSON数据
	 */
	public List<Map<String,String>> getProductJsonList(Set<String> list) throws Exception{
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		if(list != null && list.size()>0){
			for(String productId : list){ 
				Map<String,String> map =new HashMap<String,String>();
				map.put("productId", productId);
				result.add(map);
			}
		}
		return result;
	}
	
}
