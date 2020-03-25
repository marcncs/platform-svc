package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class StockAlterMoveDetailAction extends BaseAction{
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
			String Condition = "pw.id=pwd.samid and (pw.iscomplete=1 and pw.isblankout=0 "
				 + " )  "+
				//+ visitorgan + " )  "+ 
			" and (pw.inwarehouseid in (select wid from WarehouseVisit where userid="+userid+") "+
			" or pw.outwarehouseid in (select wid from WarehouseVisit where userid="+userid+")) ";
			String[] tablename = { "StockAlterMove", "StockAlterMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppStockAlterMoveDetail asod=new AppStockAlterMoveDetail(); 
//			List sumobj = asod.getTotalSubum(whereSql);		     
			List boxnumandscatternum =asod.getTotalBoxnumAndScatternum(whereSql);
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        ArrayList alsod=new ArrayList();
//	        Double totalsum=0.00;
	        int toalboxnum=0;
	        Double totalscatternum=0.0;
	        Double quantity=0.0;
	        AppProduct ap =new AppProduct();
	        AppFUnit af =new AppFUnit();
	        Product p;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String morganname = (String)o[1];
	        	String rorganname = (String)o[3];
	        	String makedate = DateUtil.formatDateTime((Date)o[4]);
	        	StockAlterMoveDetail pbd = (StockAlterMoveDetail)o[5];
	        	sodf.setOname(morganname);
	        	sodf.setSoname(rorganname);
	        	sodf.setMakedate(makedate);
	        	sodf.setBillid(pbd.getSamid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	//兼容老单据
	        	if(pbd.getUnitid()==18){
	        		p =ap.getProductByID(pbd.getProductid());
	        		sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", p.getScatterunitid()));
//		        	sodf.setUnitprice(pbd.getUnitprice());
//		        	sodf.setQuantity(pbd.getQuantity());
	        		
	        		//箱到kg转换数
	    			double xtsQuantity =af.getXQuantity(pbd.getProductid(), 2);
	    			//散到kg转换数
	    			double stsQuantity =af.getXQuantity(pbd.getProductid(), p.getScatterunitid());
	    			//得到整箱数
					int q = ((Double)ArithDouble.div(pbd.getQuantity(), xtsQuantity)).intValue();
		        	sodf.setBoxnum(q);
		        	//得到散数
					double tqu = ArithDouble.sub(quantity, ArithDouble.mul(xtsQuantity, (double)q));
		        	sodf.setScatternum(ArithDouble.div(tqu, stsQuantity));
	        	}else if( pbd.getUnitid()==2){
	        		p =ap.getProductByID(pbd.getProductid());
	        		sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", p.getScatterunitid()));
	        		sodf.setBoxnum(((Double)pbd.getQuantity()).intValue());
		        	sodf.setScatternum(0d);
	        	}else{
	        		sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
		        	sodf.setBoxnum(pbd.getBoxnum());
		        	sodf.setScatternum(pbd.getScatternum());
	        	}
	        	
//	        	sodf.setSubsum(pbd.getSubsum());
	        	//sodf.setFrate(aop.getOrganPriceiiByOidPidUid((String)o[2],pbd.getProductid(),pbd.getUnitid()).getFrate());
//	        	totalsum += sodf.getSubsum();
	        	toalboxnum+=sodf.getBoxnum();
	        	totalscatternum=ArithDouble.add(totalscatternum, sodf.getScatternum());
	        	alsod.add(sodf);
	        }
		       
//			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
	        request.setAttribute("toalboxnum", toalboxnum);
	        request.setAttribute("totalscatternum", totalscatternum);
			request.setAttribute("alsod", alsod);
			  
//			double allsum = 0;
			int alltoalboxnum=0;
	        Double alltotalscatternum=0.0;
//			if (sumobj != null) {
//				Object[] ob = (Object[]) sumobj.get(0);
//				allsum = Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
//			}
//			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
	        if (boxnumandscatternum != null) {
				Object[] ob = (Object[]) boxnumandscatternum.get(0);
				alltoalboxnum = Integer.parseInt((String.valueOf(ob[0] == null ? "0": ob[0])));
				alltotalscatternum=Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
			}
			
			request.setAttribute("alltoalboxnum", alltoalboxnum);
	        request.setAttribute("alltotalscatternum", alltotalscatternum); 

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
		
			request.setAttribute("alos", alos);
			request.setAttribute("als", als);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("ReceiveOrganID", request.getParameter("ReceiveOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("OutWarehouseID", request.getParameter("OutWarehouseID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>渠道>>列表订购明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
