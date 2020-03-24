package com.winsafe.drp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppMoveCanuseOrgan;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.MoveCanuseOrgan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;

public class UploadIdcodeUtil {
	private static AppOlinkMan aom = new AppOlinkMan();
	/**
	 * 获取系统资源
	* @param key
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jul 23, 2012 4:00:04 PM
	 */
	public static Integer getBaseResourceFlag(Integer key) throws Exception{
		AppBaseResource abr = new AppBaseResource();
		BaseResource bar=abr.getBaseResourceValue("UploadIdcodeFlag", key);
		Integer intFlag=bar.getIsuse();
		return intFlag;
	}
	
	/**
	 * 仓库权限验证
	 */
	public static Map<String,RuleUserWh> getRUWAuthority(Integer userid) {
		AppRuleUserWH auw = new AppRuleUserWH();
		RuleUserWh existObject=new RuleUserWh();
		Map<String,RuleUserWh> ruwMap  = new HashMap<String, RuleUserWh>();
		List<String>  list  = 	auw.getWarehouseIdByUserid(userid);
		for(String  wid : list){
			ruwMap.put(wid, existObject);
		}
		return ruwMap;
	}
	
	/**
	 * 仓库权限验证
	* @param inwarehouseid
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 23, 2013 4:49:33 PM
	 */
	public static Map<String,MoveCanuseOrgan> getMCOAuthority(Integer userid) {
		AppMoveCanuseOrgan amco = new AppMoveCanuseOrgan();
		Map<String,MoveCanuseOrgan> mcoMap  = new HashMap<String, MoveCanuseOrgan>();
		List<MoveCanuseOrgan> list = amco.getMoveCanuseOrganByUserid(userid);
		for(MoveCanuseOrgan mco : list){
			mcoMap.put(mco.getOid(), mco);
		}
		return mcoMap;
	}
	
	/**
	 * 仓库权限验证
	* @param inwarehouseid
	 */
	public static Map<String,WarehouseVisit> getWVAuthority(Integer userid){
		AppWarehouseVisit awv = new AppWarehouseVisit();
		Map<String,WarehouseVisit> wvMap  = new HashMap<String, WarehouseVisit>();
		List<WarehouseVisit> list= awv.getWarehouseVisitByUID(userid);
		for(WarehouseVisit wv : list){
			wvMap.put(wv.getWid(), wv);
		}
		return wvMap;
	}
	
	public static Map<String, Warehouse> getPlantWarehouse() throws Exception{
		Map<String, Warehouse> resultMap = new HashMap<String, Warehouse>();
		AppWarehouse aw = new AppWarehouse();
		List<Warehouse> warehouseList = aw.getWarehouseByOrganProperty(1);
		for(Warehouse warehouse : warehouseList){
			resultMap.put(warehouse.getId(), warehouse);
		}
		return resultMap;
	}
	
	
	
	/**
	 * 获取联系人信息
	* @param warehouseid
	* @return
	* @author wenping   
	* @CreateTime Jan 29, 2013 10:37:55 AM
	 */
	public static Object[] getLinkMan(Map<String,Olinkman> lmMap,Map<String,String> wh_OrganIDMap, String warehouseid){
		String linkman = null;
		String tel = null;
		Olinkman ol = lmMap.get(warehouseid);
		if(ol==null){
			ol = aom.getMainLinkmanByCid(wh_OrganIDMap.get(warehouseid));
			if(ol!=null){
				lmMap.put(warehouseid, ol);
				
				linkman = ol.getName();
				tel = ol.getMobile();
				if(tel==null){
					tel = ol.getOfficetel();
				}
			}
		}else{
			linkman = ol.getName();
			tel = ol.getMobile();
			if(tel==null){
				tel = ol.getOfficetel();
			}
		}
		
		Object[] arr  = new Object[3];
		arr[0] = linkman;
		arr[1] = tel;
		arr[2] = lmMap;
		
		return arr;
	}
}
