package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.hbm.util.cache.PaymentModeCache;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class PaymentModeService {
	private PaymentModeCache cache = new PaymentModeCache();
	private AppPaymentMode appo = new AppPaymentMode();
	
	public List getPaymentMode(int pagesize, String whereSql, SimplePageInfo tmpPgInfo) throws Exception{
		return appo.getPaymentMode(pagesize, whereSql, tmpPgInfo);
	}
	
	public void addPaymentMode(PaymentMode pm) throws Exception {		
		appo.addPaymentMode(pm);		
		modifyCache();
	}	

	public void updPaymentMode(PaymentMode pm)throws Exception {		
		appo.updPaymentMode(pm);
		modifyCache();
	}
	
	public List getAllPaymentMode()throws Exception{
		List list = cache.getAllPaymentMode();
		if ( list == null ){
			list = appo.getAllPaymentMode();
			cache.putPaymentModeList(list);
		}
		return list;
	}
	
	public PaymentMode getPaymentModeByID(int id)throws Exception{
		PaymentMode o = cache.getPaymentMode(id);
		if ( o == null ){
			o = appo.getPaymentModeByID(id);
			modifyCache();
		}
		return o;
	}
	
	public PaymentMode getPaymentModeByID(Integer id)throws Exception{
		PaymentMode o = cache.getPaymentMode(id);
		if ( o == null ){
			o = appo.getPaymentModeByID(id);
			modifyCache();
		}
		return o;
	}
	
	
	public String getPaymentModeName(int id) throws Exception{
		PaymentMode o = getPaymentModeByID(id);
		if ( o != null ){
			return o.getPaymentname();
		}
		return "";
	}

	public void putUsersList(List list){

	}
	 private void modifyCache() throws Exception {
		//cache.modifyPaymentMode(appo.getAllPaymentMode());
	}
	
	
}

