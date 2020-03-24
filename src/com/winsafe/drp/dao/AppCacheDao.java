package com.winsafe.drp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.report.ExportIdcodeScanRateAction;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.cache.CacheBean;
import com.winsafe.drp.util.cache.CacheController;
import com.winsafe.drp.util.cache.ICache;

/**
 * 用完请即时销毁对象
 * @author huangxy
 * @date Dec 7, 2012 1:43:00 PM
 * @version v1.0
 */
public class AppCacheDao {
	private Logger logger = Logger.getLogger(AppCacheDao.class);

	private CacheController aCacheController=new CacheController();
	private AppTakeTicketDetailBatchBit attd = new AppTakeTicketDetailBatchBit();
	private AppProduct ap = new AppProduct();
	private AppWarehouse aw = new AppWarehouse();
	private AppOrgan ao = new AppOrgan();
	private AppFUnit af = new AppFUnit();

	public Product getProduct(String id){
		String key="Product"+id;
		return (Product)aCacheController.getBean(key, id, new ICache() {
			public Product getNew(Object id) {
				try {
					return ap.getProductByID((String)id);
				} catch (Exception e) {
					logger.error("", e);
				}
				return null;
			}

			public boolean equals(Object id,CacheBean cacheBean){
				if(cacheBean==null ){
					return false;
				}else{
					Product object = (Product)cacheBean.getObject();
					if(object==null 
							|| !object.getId().equals(id)){
						return false;
					}
				}
				return true;
			}
		});
	}
	
	public Warehouse getWarehouse(String id){
		
		return (Warehouse)aCacheController.getBean("Warehouse", id, new ICache() {
			public Warehouse getNew(Object id) {
				try {
					return aw.getWarehouseByID((String)id);
				} catch (Exception e) {
					logger.error("", e);
				}
				return null;
			}

			public boolean equals(Object id,CacheBean cacheBean){
				if(cacheBean==null ){
					return false;
				}else{
					Warehouse object = (Warehouse)cacheBean.getObject();
					if(object==null 
							|| !object.getId().equals(id)){
						return false;
					}
				}
				return true;
			}
		});
	}
	public Organ getOrgan(String id){
		
		return (Organ)aCacheController.getBean("Organ", id, new ICache() {
			public Organ getNew(Object id) {
				try {
					return ao.getOrganByID((String)id);
				} catch (Exception e) {
					logger.error("", e);
				}
				return null;
			}

			public boolean equals(Object id,CacheBean cacheBean){
				if(cacheBean==null ){
					return false;
				}else{
					Organ object = (Organ)cacheBean.getObject();
					if(object==null 
							|| !object.getId().equals(id)){
						return false;
					}
				}
				return true;
			}
		});
	}
	public Double getXQuantity(final String productid,final int unitid){
		String key=productid+"_"+unitid;
		FUnit aFUnit= (FUnit)aCacheController.getBean(key, "", new ICache() {
			public FUnit getNew(Object id) {
				try {
					return af.getFUnit(productid, unitid);
				} catch (Exception e) {
					logger.error("", e);
				}
				return null;
			}

			public boolean equals(Object id,CacheBean cacheBean){
				if(cacheBean==null ){
					return false;
				}else{
					FUnit object = (FUnit)cacheBean.getObject();
					if(object==null ){
						return false;
					}
					if(!object.getProductid().equals(productid)){
						return false;
					}
					if(!object.getFunitid().equals(unitid)){
						return false;
					}
				}
				return true;
			}
		});
		return aFUnit.getXquantity();
	}
//	
//	@SuppressWarnings("unchecked")
//	public List<TakeTicketDetailBatchBit> getTTDBBList(final String ttid,final String productid){
//		final String key=ttid+"_"+productid;
//		return (List<TakeTicketDetailBatchBit>)aCacheController.getBean(key, key, new ICache() {
//			public List<TakeTicketDetailBatchBit> getNew(Object id) {
//				try {
//					return attd.getBatchBitByTTIDPID(ttid, productid);
//				} catch (Exception e) {
//					logger.error("", e);
//				}
//				return null;
//			}
//
//			public boolean equals(Object id,CacheBean cacheBean){
//				if(cacheBean==null ){
//					return false;
//				}else{
//					Object object = cacheBean.getObject();
//					if(object==null){
//						return false;
//					}
//					if(!key.equals(cacheBean.getKey())){
//						return false;
//					}
//				}
//				return true;
//			}
//		});
//	}
//	
//	public String getTotalBoxSum(final String ttid,final String productid){
//		List<TakeTicketDetailBatchBit> list=getTTDBBList(ttid,productid);
//		if(list==null || list.isEmpty()){
//			return "0";
//		}
//		int sum=0;
//		Integer realboxnum;
//		for(TakeTicketDetailBatchBit b:list){
//			realboxnum=b.getRealboxnum();
//			if(realboxnum==null){
//				realboxnum=0;
//			}
//			sum+=realboxnum;
//		}
//		return String.valueOf(sum);
//	}
//	
//	public String getTotalScatterSum(final String ttid,final String productid){
//		List<TakeTicketDetailBatchBit> list=getTTDBBList(ttid,productid);
//		if(list==null || list.isEmpty()){
//			return "0";
//		}
//		double sum=0;
//		Double realscatternum;
//		for(TakeTicketDetailBatchBit b:list){
//			realscatternum=b.getRealscatternum();
//			if(realscatternum==null){
//				realscatternum=0d;
//			}
//			sum=ArithDouble.add(sum, realscatternum);
//		}
//		return String.valueOf(sum);
//	}
//	
//	public String getTotalrealquantity(final String ttid,final String productid){
//		List<TakeTicketDetailBatchBit> list=getTTDBBList(ttid,productid);
//		if(list==null || list.isEmpty()){
//			return "0";
//		}
//		double sum=0;
//		Double realQuantity;
//		for(TakeTicketDetailBatchBit b:list){
//			realQuantity=b.getRealQuantity();
//			if(realQuantity==null){
//				realQuantity=0d;
//			}
//			sum=ArithDouble.add(sum, realQuantity);
//		}
//		return String.valueOf(sum);
//	}
//	
//	/**
//	 * 清除List的缓存
//	 * @param ttid
//	 * @param productid
//	 */
//	public void clearTTDBBList(final String ttid,final String productid){
//		final String key=ttid+"_"+productid;
//		aCacheController.clearCache(key);
//	}

	
	public String getTotalkgquantity(List<Map> list ,final String ttid,final String productid){
		if(list==null || list.isEmpty()){
			return "0";
		}
		String mapTtid;
		String mapProductid;
		String mapQuantity;
		for(Map map:list){
			mapTtid=(String)map.get("ttid");
			if(!StringUtil.isEmpty(mapTtid) && mapTtid.equals(ttid)){

				mapProductid=(String)map.get("productid");
				if(!StringUtil.isEmpty(mapProductid) && mapProductid.equals(productid)){
					mapQuantity =(String)map.get("packquantity");
					if(!StringUtil.isEmpty(mapQuantity)){
						return mapQuantity;
					}else{
						return "0";
					}
				}
			}
		}
		return "0";
	}
	
	public Map<String,String> getBatchbit(List<Map> list ,final String ttid,final String productid){
		Map<String,String> result=new HashMap<String,String>();
		result.put("realQuantity", "0");
		result.put("realscatternum", "0");
		result.put("realboxnum", "0");
		if(list==null || list.isEmpty()){
			return result;
		}
		String mapTtid;
		String mapProductid;
		String mapQuantity;
		for(Map map:list){
			mapTtid=(String)map.get("ttid");
			if(!StringUtil.isEmpty(mapTtid) && mapTtid.equals(ttid)){

				mapProductid=(String)map.get("productid");
				if(!StringUtil.isEmpty(mapProductid) && mapProductid.equals(productid)){
					result.put("realQuantity", (String)map.get("realquantity"));
					result.put("realscatternum", (String)map.get("realscatternum"));
					result.put("realboxnum",(String)map.get("realboxnum"));
					return result;
				}
			}
		}
		return result;
	}
	
}
