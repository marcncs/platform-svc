package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.CollectionUtils;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class DownloadWarehouseAction  extends Action{
	private Logger logger = Logger.getLogger(DownloadWarehouseAction.class);
	
	private AppRuleUserWH aruw = new AppRuleUserWH();
	
	private AppWarehouse aw = new AppWarehouse();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String type = request.getParameter("Type");
		String username = request.getParameter("Username");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		List<Map<String, String>> list = null;
		//type[1:管辖仓库 2:业务往来仓库 3:可转仓仓库 4:产成品入库仓库]
		if ("1".equals(type)) {
			//管辖仓库
			list = getRuleUserWH(users, scannerNo);
		} else if ("2".equals(type)) {
			//业务往来仓库
			list = getWarehouseVisit(users);
		} else if ("3".equals(type)) {
			//可转仓仓库,只能是自己管辖的仓库,并且为同一机构下
			list = getJsonList(getRuleUserWH(users));
		} else if ("4".equals(type)) {
			//产成品入库仓库
			
		}else if ("5".equals(type)) {
			//可退回机构的仓库
			list = getPlantWarehouse();
		} else {
			//do nothing
		}    
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],仓库下载",true);
		return null;
	}
	
	public List getRuleUserWH(Users users,String scannerNo) throws Exception{
		List<Map<String, String>> list = null;
		//用户验证方式
		List userList = null;
		if(Constants.SCANNNER_VALIDATE_USER){
			userList = getRuleUserWH(users);
		}
		//采集器IMEI验证方式
		List IMEIList = null;
		if(Constants.SCANNNER_VALIDATE_IMEI){
			IMEIList = getWHByScanner(scannerNo);
		}
		//如果用户验证与采集器验证同时为true,则取交集,否则取并集
		if(Constants.SCANNNER_VALIDATE_USER && Constants.SCANNNER_VALIDATE_IMEI){
			Collection<Warehouse> collection = CollectionUtils.intersection(userList, IMEIList);
			list =  getJsonList(new ArrayList<Warehouse>(collection));
		}else {
			Collection<Warehouse> collection = CollectionUtils.union(userList, IMEIList);
			list =  getJsonList(new ArrayList<Warehouse>(collection));
		}
		
		return list;
	}
	
	
	/**
	 * 可退回机构的仓库
	 * Create Time 2014-10-21 上午10:37:02 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getPlantWarehouse() throws Exception{
		List<Warehouse> warehouseList = aw.getWarehouseByOrganProperty(1);
		return getJsonList(warehouseList);
	}
	/**
	 * 获取业务往来仓库
	 * Create Time 2014-10-21 上午10:29:49 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getWarehouseVisit(Users users) throws Exception{
		
		List<Warehouse> warehouseList = aw.getEnableWarehouseByVisit(users.getUserid());
		
		return getJsonList(warehouseList);
	}
	
	/**
	 * 获取管辖仓库
	 * Create Time 2014-10-21 上午10:29:49 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public List<Warehouse> getRuleUserWH(Users users) throws Exception{
		List<Warehouse> warehouseList  = aruw.queryRuleUserWh(users.getUserid());
		return warehouseList;
	}
	/**
	 * 获取管辖仓库对应的采集器
	 * Create Time 2014-10-23 上午09:32:24 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public List<Warehouse> getWHByScanner(String scannerNo) throws Exception{
		List<Warehouse> warehouseList  = aruw.queryWhByScanner(scannerNo);
		return warehouseList;
	}
	/**
	 * List转化
	 * Create Time 2014-10-21 上午10:18:22 
	 * @param list
	 * @return
	 */
	public List<Map<String, String>> getJsonList(List<Warehouse> list){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for(Warehouse w : list){
			Map<String, String> data = new HashMap<String, String>();
			data.put("organID", w.getMakeorganid());
			data.put("warehouseID", w.getId());
			data.put("warehouseName", w.getWarehousename());
			data.put("customWHID", w.getNccode());
			result.add(data);
		}
		return result;
	}
}