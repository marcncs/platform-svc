package com.winsafe.drp.action.report;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.FUnitUtil;
import com.winsafe.hbm.util.StringUtil;

/**
* 库存预警报表
* @Title: AlarmByProductStockpileAllAction.java
* @author: WEILI 
* @CreateTime: 2013-04-25
* @version: 1.0
 */
public class ListSalesConsumHistoryAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(ListSalesConsumHistoryAction.class);
	private AppSalesConsumHistory appSalesConsumHistory = new AppSalesConsumHistory();
	private AppFUnit appFUnit = new AppFUnit();
	private DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
	private AppBaseResource appBr = new AppBaseResource();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		
		//获取页面检索条件中的参数
		Map paramMap = new HashMap(request.getParameterMap());
		List<Map<String,String>> schList = appSalesConsumHistory.getAllSalesConsumHistory(request, pageSize, paramMap, users);
		
		if(schList != null) {
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Map<String,String> schMap : schList) {
				double salesVolume = getDouble((String)schMap.get("salesvolume"));
				double consumVolume = getDouble((String)schMap.get("consumvolume"));
				double otherConsum = getDouble((String)schMap.get("otherconsum"));
				double price = getDouble((String)schMap.get("price"));
				String productId = (String)schMap.get("pid");
				Integer pSunit = Integer.parseInt((String)schMap.get("psunit"));
				Double boxQuantity = Double.parseDouble((String)schMap.get("boxquantity"));
				Integer countUnit = Integer.parseInt((String)schMap.get("countunit"));
				//转换成件pSunit
				if(StringUtil.isEmpty((String)paramMap.get("countByUnit"))) {
					//检查单位是否可以正常转化,如不能则不转化
					if(FUnitUtil.checkRate(productId,pSunit,Constants.DEFAULT_UNIT_ID, funitMap)){
						if(salesVolume != 0d) {
							salesVolume = FUnitUtil.changeUnit(productId, pSunit, salesVolume, Constants.DEFAULT_UNIT_ID,funitMap);
							schMap.put("salesvolume", decimalFormat.format(salesVolume));
						}
						if(consumVolume != 0d) {
							consumVolume = FUnitUtil.changeUnit(productId, pSunit, consumVolume, Constants.DEFAULT_UNIT_ID,funitMap);
							schMap.put("consumvolume", decimalFormat.format(consumVolume));
						}
						if(otherConsum != 0d) {
							otherConsum = FUnitUtil.changeUnit(productId, pSunit, otherConsum, Constants.DEFAULT_UNIT_ID,funitMap);
							schMap.put("otherconsum", decimalFormat.format(otherConsum));
						}
						
						if(price != 0d) {
							price = FUnitUtil.changeUnit(productId, Constants.DEFAULT_UNIT_ID, ArithDouble.mul(price, boxQuantity), pSunit ,funitMap);
							schMap.put("price", decimalFormat.format(price));
						}
						
						schMap.put("unit", countUnitMap.get(Constants.DEFAULT_UNIT_ID));
					}
				} else {
					//转换成计量单位
					if(boxQuantity != 0d) {
						if(salesVolume != 0d) {
							salesVolume = ArithDouble.mul(salesVolume, boxQuantity);
							schMap.put("salesvolume", decimalFormat.format(salesVolume));
						}
						if(consumVolume != 0d) {
							consumVolume = ArithDouble.mul(consumVolume, boxQuantity);
							schMap.put("consumvolume", decimalFormat.format(consumVolume));
						}
						if(otherConsum != 0d) {
							otherConsum = ArithDouble.mul(otherConsum, boxQuantity);
							schMap.put("otherconsum", decimalFormat.format(otherConsum));
						}
//						if(price != 0d) {
//							price = ArithDouble.div(price, boxQuantity,2);
//							schMap.put("price", decimalFormat.format(price));
//						}
						schMap.put("unit", countUnitMap.get(countUnit));
					}
				}
			}
		}
				
		request.setAttribute("schList", schList);
		
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
	
	private double getDouble(String value) {
		if(!StringUtil.isEmpty(value)) {
			return Double.parseDouble(value);
		} 
		return 0d;
	}
}
