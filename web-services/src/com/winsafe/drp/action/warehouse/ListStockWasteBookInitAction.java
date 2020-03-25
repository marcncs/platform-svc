package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListStockWasteBookInitAction extends BaseAction {
	Logger logger = Logger.getLogger(ListStockWasteBookInitAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 10;
		initdata(request);
		AppStockWasteBook aswb = new AppStockWasteBook();
		AppFUnit af = new AppFUnit();
		try {
			Map<String, Object> param = new LinkedHashMap<String, Object>();
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "StockWasteBook" };
			String whereSql = EntityManager.getTmpWhereSql2ForSql(map, tablename, param);

			String leftblur = DbUtil.getBlurLeftForSql(map, tmpMap, "p.PSID", param);
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap, " RecordDate", param);
			String condition = "";
			if(DbUtil.isDealer(users)) {
				condition += " s.warehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?) and ";
				param.put("wv.user_Id", userid);
			} else { 
				condition += DbUtil.getWhereCondition(users, "o")+" and ";
			}
			whereSql = whereSql + leftblur + timeCondition + condition ;
			
			//#start modified by ryan.xi 20150610
			if(StringUtil.isEmpty((String)map.get("WarehouseID")) && !StringUtil.isEmpty((String)map.get("MakeOrganID"))) {
				whereSql += " w.makeorganid = ? ";
				param.put("w.makeorganid", map.get("MakeOrganID"));
			}
			//#end modified by ryan.xi 20150610
			
			whereSql = DbUtil.getWhereSql(whereSql);
			List<Map<String,String>> sals = aswb.getStockWastBookList(request, pagesize, whereSql,users, param);
			logger.debug("库存统计单位、数量转换");
			for (Map<String,String> map2 : sals) {
				//单位
				Integer cu =  Integer.valueOf(map2.get("countunit"));
				//期初数量
				Double cfq =  Double.valueOf(map2.get("cyclefirstquantity"));
				//收入数量
				Double ciq =  Double.valueOf(map2.get("cycleinquantity"));
				//发出数量
				Double coq =  Double.valueOf(map2.get("cycleoutquantity"));
				//结存数量
				Double cbq =  Double.valueOf(map2.get("cyclebalancequantity"));
				
				//产品id
				String pid = (String) map2.get("productid");
				map2.put("countunit", String.valueOf(Constants.DEFAULT_UNIT_ID));
				Double xquantity = af.getXQuantity(pid, Integer.valueOf(map2.get("countunit")));
				if (xquantity != 0) {
					map2.put("cyclefirstquantity", String.valueOf(cfq/xquantity));
					map2.put("cycleinquantity", String.valueOf(ciq/xquantity));
					map2.put("cycleoutquantity", String.valueOf(coq/xquantity));
					map2.put("cyclebalancequantity", String.valueOf(cbq/xquantity));
				}else {
					map2.put("cyclefirstquantity", "0");
					map2.put("cycleinquantity", "0");
					map2.put("cycleoutquantity", "0");
					map2.put("cyclebalancequantity", "0");
				}
			}
			
			DBUserLog.addUserLog(request, "列表");
			request.setAttribute("als", sals);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
