package com.winsafe.hbm.util.cache;

import java.util.List;

import com.winsafe.hbm.util.cache.CacheManager;
import com.winsafe.drp.dao.PaymentMode;


public class PaymentModeCache {
	private CacheManager cm;
	private String CACHE_TAG = "paymentmodelist";
	
	public PaymentModeCache(){
		cm = CacheManager.getInstance("PaymentMode");
	}
	
	public PaymentMode getPaymentMode(int id){	
		List<PaymentMode> list = getAllPaymentMode();
		if ( list == null ){
			return null;
		}
		for ( PaymentMode d : list ){
			if ( d.getId().intValue() == id ){
				return d;
			}
		}
		return null;
	}
	
	
	public void putPaymentModeList(List list){
		cm.put(CACHE_TAG, list);
	}
	
	public void removePaymentModeList(){
		cm.remove(CACHE_TAG);
	}
	
	public List getAllPaymentMode(){
		return (List)cm.get(CACHE_TAG);
	}
	
	
	public void modifyPaymentMode(List list){
		removePaymentModeList();
		putPaymentModeList(list);
	}
}
