package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class StockMoveWarehouseTotalAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "StockMove" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			String Condition = "sm.id = smd.smid and (iscomplete=1 and isblankout=0  "
				 + " )  "+
					//+ visitorgan + ")"+ 
					" and (sm.inwarehouseid in (select wid from Warehouse_Visit where userid="+userid+") "+
					" or sm.outwarehouseid in (select wid from Warehouse_Visit where userid="+userid+")) ";
			String blur =getKeyWordCondition("MakeOrganID","InOrganID","ID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppStockMove aso = new AppStockMove();
			List pils = aso.getStockMoveWarehouseTotal(request, pagesize, whereSql);
//			Double allsum = aso.getStockTotalSum(whereSql)==null?0.00:aso.getStockTotalSum(whereSql);
			
			Map tempMap =new HashMap(); 
	        Integer unitid,totalboxnum=0;
	        Double quantity=0.0,totalscatternum=0.0;
	        AppProduct ap =new AppProduct();
	        AppFUnit af =new AppFUnit();
	        Product p;
	        String pid;
	        for(int i=0;i<pils.size();i++){
	        	tempMap =(HashMap)pils.get(i);
	        	unitid =Integer.parseInt((String)tempMap.get("unitid"));
	        	//兼容先前单据
	        	if(unitid==18 || unitid==2){
	        		quantity =Double.parseDouble( (String)tempMap.get("quantity"));
	        		pid=(String)tempMap.get("productid");
	        		p =ap.getProductByID(pid);
	        		tempMap.put("unitid", p.getScatterunitid());
	        		
	        		quantity = af.getQuantity(pid, unitid, quantity);
	        		
	        		//箱到kg转换数
	    			double xtsQuantity =af.getXQuantity(pid, 2);
	    			//散到kg转换数
	    			double stsQuantity =af.getXQuantity(pid, p.getScatterunitid());
	    			
	    			//得到整箱数
					int q = ((Double)ArithDouble.div(quantity, xtsQuantity)).intValue();
					tempMap.put("boxnum", Integer.toString(q));
					//得到散数
					double tqu = ArithDouble.sub(quantity, ArithDouble.mul(xtsQuantity, (double)q));
					tempMap.put("scatternum", Double.toString(ArithDouble.div(tqu, stsQuantity)));
	    			
	        	}
	        	totalboxnum+= (Integer.parseInt((String)tempMap.get("boxnum")));
	        	totalscatternum = ArithDouble.add(totalscatternum, Double.valueOf((String)tempMap.get("scatternum")));
	        }
			
//			Integer alltoalboxnum=aso.getStockAlterTotalBoxSum(whereSql);
//	        Double alltotalscatternum=aso.getStockAlterTotalScatterSum(whereSql);
	        	
	        request.setAttribute("alltoalboxnum", totalboxnum);
	        request.setAttribute("alltotalscatternum", totalscatternum);
//			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);


			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("InOrganID", request.getParameter("InOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表转仓按仓库汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
