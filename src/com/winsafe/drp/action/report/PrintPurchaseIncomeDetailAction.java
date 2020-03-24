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
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintPurchaseIncomeDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid") ;
			}
			String Condition = "pw.id=pwd.piid and  pw.istally=1 " + visitorgan+ "  ";

			String[] tablename = { "PurchaseIncome", "PurchaseIncomeDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition =getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppPurchaseIncomeDetail asod=new AppPurchaseIncomeDetail(); 
		     
	        List sodls = asod.getDetailReport(whereSql);
	        List<DetailReportForm> list=new ArrayList<DetailReportForm>();
	        AppWarehouse aw = new AppWarehouse();
	        double totalsum = 0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String pid = (String)o[0];
	        	String pname = (String)o[1];
	        	String wid = (String)o[2];
	        	String makedate = DateUtil.formatDateTime((Date)o[3]);
	        	PurchaseIncomeDetail pbd = (PurchaseIncomeDetail)o[4];
	        	sodf.setMakedate(makedate);
	        	sodf.setPid(pid);
	        	sodf.setOname(pname);
	        	sodf.setSoname(aw.getWarehouseByID(wid).getWarehousename());
	        	sodf.setBillid(pbd.getPiid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    pbd.getUnitid()));
	        	sodf.setUnitprice(pbd.getUnitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setSubsum(pbd.getSubsum());
	        	totalsum+=sodf.getSubsum();
	        	list.add(sodf);
	        }
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印采购入库明细");
	        request.setAttribute("list", list);
	        request.setAttribute("totalsum", totalsum);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
