package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
* 出库条码扫描率按仓库汇总
* @Title: IdcodeScanRateByWarehouseAction.java
* @author: WEILI 
* @CreateTime: 2013-04-25
* @version: 1.0
 */
public class IdcodeScanRateByWarehouseAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		//初始化各查询类
		AppTakeTicketDetailBatchBit  attd = new AppTakeTicketDetailBatchBit();
		AppProduct ap = new AppProduct();
		AppWarehouse aw = new AppWarehouse();
		AppOrgan ao =new AppOrgan();
		AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
		AppTakeTicket att  =new AppTakeTicket();
		AppFUnit af  =new AppFUnit();
		try {
			//页面接收两个表中的属性值
			String[] tablename = { "TakeTicket","TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			//制单日期
			String timeCondition = getTimeCondition("tt.makedate");
			//String Condition="  ttd.ttid=tt.id and tt.isaudit=1  and (inwarehouseid in (select wv.wid from  warehouse_visit as wv where wv.userid="+userid+") or inwarehouseid in (select ruw.warehouse_Id from rule_user_wh ruw where ruw.user_Id = " + userid + " and activeFlag = 1))";
			String Condition="  ttd.ttid=tt.id and tt.isaudit=1 ";
			//绑定关键字
			String blur =DbUtil.getOrBlur(map, tmpMap, "billno","tt.id","tt.nccode");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			String orderSql = request.getParameter("orderbySql");
			String orderSqlName = request.getParameter("orderbySqlShowName");
			
			//查询集合
			List ttlist = att.getIdcodeScanRateListByWarehouse(request, pageSize, whereSql, orderSql);
			//通过MAP设置集合中的值
			Map tempMap =new HashMap(); 
			Map tempWarehouseMap =new HashMap(); 
			String outwid,outorgan,boxnum,scatternum;
			Warehouse w;
			Organ o;
			Product p;
			for(int i=0;i<ttlist.size();i++){
				tempMap =(HashMap)ttlist.get(i);
				
				boxnum = (String)tempMap.get("boxnum");
				scatternum = (String)tempMap.get("scatternum");
				tempMap.put("boxnum", boxnum);
				tempMap.put("scatternum", scatternum);
				
				outwid=  (String)tempMap.get("warehouseid");
				w= aw.getWarehouseByID(outwid);
				outorgan = w.getMakeorganid();
				tempMap.put("warehouseid", w.getWarehousename());
				o = ao.getOrganByID(outorgan);
				tempMap.put("outorganname",o.getOrganname());
				
				//根据仓库进行产品汇总并计算扫描箱散数量
				double countByBox = 0.0, countByScatternum = 0.0, totalquantityByWarehouse = 0.0;
				String productid, packquantity;
				List warehouseProductList = atti.getTotalkgquantityByWarehouseid(outwid, whereSql);
				for (int j = 0; j < warehouseProductList.size(); j++) {
					tempWarehouseMap = (HashMap)warehouseProductList.get(j);
					productid = (String)tempWarehouseMap.get("productid");
					p = ap.getProductByID(productid);
					packquantity = (String)tempWarehouseMap.get("packquantity");
					if(packquantity == null || StringUtil.isEmpty(packquantity)){
						totalquantityByWarehouse = 0d;
					}else{
						totalquantityByWarehouse = Double.valueOf(packquantity);
					}
					//箱到KG转换数
					double xtsQuantity = af.getXQuantity(productid, 2);
	    			//散到KG转换数
	    			double stsQuantity = af.getXQuantity(productid, p.getScatterunitid());
	    			//得到整箱数
					int q = ((Double)ArithDouble.div(totalquantityByWarehouse, xtsQuantity)).intValue();
					//得到散数
					double tqu = ArithDouble.sub(totalquantityByWarehouse, ArithDouble.mul(xtsQuantity, (double)q));
					tqu = ((Double)ArithDouble.div(tqu, stsQuantity)).intValue();
					countByBox += q;
					countByScatternum += tqu;
					totalquantityByWarehouse += totalquantityByWarehouse;
				}
				tempMap.put("idcodeboxnum", countByBox);
				tempMap.put("idcodescatternum", countByScatternum);
				
				//根据仓库进行产品汇总并计算扫描率
				if(totalquantityByWarehouse==0){
					tempMap.put("rate", "");
				}else{
					double rate = ArithDouble.div(countByBox, Double.valueOf(boxnum), 2);
					String srate = Double.toString(ArithDouble.mul(rate, 100))+"%";
					tempMap.put("rate",srate);
				}
				totalquantityByWarehouse = 0.0;
			}
			
			request.setAttribute("ttlist", ttlist);
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
			
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("OName", request.getParameter("OName"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid,  10,"报表分析>>出库>>扫描率按仓库汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("warehouseid", "发货仓库");
	}
}
