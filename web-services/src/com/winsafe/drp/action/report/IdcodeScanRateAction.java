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
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.RuleUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

/**
 * 出库条码扫描率
* @Title: IdcodeScanRateAction.java
* @author: wenping 
* @CreateTime: Oct 25, 2012 1:12:58 PM
* @version:
 */
public class IdcodeScanRateAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	      
		super.initdata(request);
		int pageSize=15;
		try {
			
			String[] tablename = { "TakeTicket","TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			//制单日期
			String timeCondition = getTimeCondition("tt.makedate");
			String Condition="  ttd.ttid=tt.id and tt.isaudit=1  and (inwarehouseid in (select wv.wid from  warehouse_visit as wv where wv.userid="+userid+") or inwarehouseid in (select ruw.warehouse_Id from rule_user_wh ruw where ruw.user_Id = "
			+ userid + " and activeFlag = 1))";
			
			String blur =DbUtil.getOrBlur(map, tmpMap, "billno","tt.id","tt.nccode");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			String orderSql = request.getParameter("orderbySql");
			String orderSqlName = request.getParameter("orderbySqlShowName");
			
			AppTakeTicket att  =new AppTakeTicket();
			List  ttlist = att.getIdcodeScanRateList(request, pageSize, whereSql, orderSql);
			
			Map tempMap =new HashMap(); 
			String ttid,proid,ncpid,outwid,outorgan,inwid,inorgan;
			String boxnum,scatternum,quantity;
			AppTakeTicketDetailBatchBit  attd = new AppTakeTicketDetailBatchBit();
			AppProduct ap = new AppProduct();
			AppWarehouse aw = new AppWarehouse();
			AppOrgan ao =new AppOrgan();
			AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
			Warehouse w;
			Organ o;
			Warehouse inw;
			Organ ino;
			double totalquantity,ttdbbrealquantity;
			Product  p;
			AppFUnit af  =new AppFUnit();
			for(int i=0;i<ttlist.size();i++){
				tempMap =(HashMap)ttlist.get(i);
				ttid = (String)tempMap.get("id");
				proid= (String)tempMap.get("productid");
				
				boxnum=attd.getTotalBoxSumByttidAndpid(ttid, proid);
				scatternum=attd.getTotalScatterSumByttidAndpid(ttid, proid);
				tempMap.put("boxnum", boxnum);
				tempMap.put("scatternum", scatternum);
				
				p =ap.getProductByID(proid);
				ncpid=p.getNccode();
				tempMap.put("pnccode", ncpid);
				
				tempMap.put("pname", p.getProductname());
				tempMap.put("pspecmode", p.getSpecmode());
				
				outwid=  (String)tempMap.get("warehouseid");
				w= aw.getWarehouseByID(outwid);
				outorgan = w.getMakeorganid();
				tempMap.put("warehouseid", w.getWarehousename());
				o = ao.getOrganByID(outorgan);
				tempMap.put("outorganname",o.getOrganname());
				
				inwid = (String) tempMap.get("inwarehouseid");
				inw = aw.getWarehouseByID(inwid);
				inorgan = inw.getMakeorganid();
				ino = ao.getOrganByID(inorgan);
				tempMap.put("inorganname",ino.getOrganname());
				
				quantity =atti.getTotalkgquantityByttidAndpid(ttid, proid);
				if(quantity==null || StringUtil.isEmpty(quantity)){
					totalquantity = 0d;
				}else{
					totalquantity = Double.valueOf(quantity);
				}
				//箱到kg转换数
				double xtsQuantity =af.getXQuantity(proid, 2);
    			//散到kg转换数
    			double stsQuantity =af.getXQuantity(proid, p.getScatterunitid());
    			//得到整箱数
				int q = ((Double)ArithDouble.div(totalquantity, xtsQuantity)).intValue();
				tempMap.put("idcodeboxnum", q);
				//得到散数
				double tqu = ArithDouble.sub(totalquantity, ArithDouble.mul(xtsQuantity, (double)q));
				tempMap.put("idcodescatternum", ((Double)ArithDouble.div(tqu, stsQuantity)).intValue());
				
				String tempquantity = attd.getTotalrealquantityByttidAndpid(ttid, proid);
				if(tempquantity==null || StringUtil.isEmpty(tempquantity)){
					tempMap.put("rate","");
				}else{
					ttdbbrealquantity = Double.valueOf(tempquantity);
					if(ttdbbrealquantity==0){
						tempMap.put("rate", "");
					}else{
						double kgquantity = af.getXQuantity(proid, p.getScatterunitid());
						ttdbbrealquantity=ArithDouble.mul(ttdbbrealquantity, kgquantity);
						double rate = ArithDouble.div(totalquantity, ttdbbrealquantity, 2);
						String srate = Double.toString(ArithDouble.mul(rate, 100))+"%";
						tempMap.put("rate",srate);
					}
				}
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
			DBUserLog.addUserLog(userid,  10,"报表分析>>出库>>扫描率");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("id", "单据号");
		  orderColumnMap.put("warehouseid", "发货机构");
		  orderColumnMap.put("productid", "产品内部编号");
		  orderColumnMap.put("inwarehouseid", "收货机构");
	}
	
}
