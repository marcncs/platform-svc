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
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class StockMoveProductTotalAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
//			String visitorgan = "";
//			if (users.getVisitorgan() != null
//					&& users.getVisitorgan().length() > 0) {
//				visitorgan = getAndVisitOrgan("makeorganid");
//			}
			String Condition = " pw.id=pwd.smid and  (pw.iscomplete=1 and pw.isblankout=0 " 
				 + " )  "+
				//+ visitorgan+ " ) "+ 
			" and (pw.inwarehouseid in (select wid from WarehouseVisit where userid="+userid+") "+
			" or pw.outwarehouseid in (select wid from WarehouseVisit where userid="+userid+")) ";
			String[] tablename = { "StockMove", "StockMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			String blur = getKeyWordCondition("MoveDate","MakeOrganID","OutWarehouseID","ProductID","ProductName","InOrganID","InWarehouseID");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockMoveDetail asod=new AppStockMoveDetail(); 
//			Double sumobj = asod.getTotalSubum(whereSql);		     
	        List list = asod.getProductTotalReport(request,pagesize, whereSql.replaceAll("WarehouseVisit", "Warehouse_Visit"));
	        
	        Map tempMap =new HashMap(); 
	        Integer unitid,alltotalboxnum=0;
	        Double quantity=0.0,alltotalscatternum=0.0;
	        AppProduct ap =new AppProduct();
	        AppFUnit af =new AppFUnit();
	        Product p;
	        String pid;
	        for(int i=0;i<list.size();i++){
	        	tempMap =(HashMap)list.get(i);
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
	        	alltotalboxnum+= (Integer.parseInt((String)tempMap.get("boxnum")));
	        	alltotalscatternum = ArithDouble.add(alltotalscatternum, Double.valueOf((String)tempMap.get("scatternum")));
	        }
	        
	        request.setAttribute("alltotalboxnum", alltotalboxnum);
	        request.setAttribute("alltotalscatternum", alltotalscatternum);
		       
			request.setAttribute("list", list);

//			request.setAttribute("allqt", sumobj);
			
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("OutWarehouseID", request.getParameter("OutWarehouseID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("InOrganID", request.getParameter("InOrganID"));
			request.setAttribute("InWarehouseID", request.getParameter("InWarehouseID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表转仓按产品汇总");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
