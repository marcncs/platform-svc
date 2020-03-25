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
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PurchaseIncomeDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = "pw.id=pwd.piid and  pw.istally=1 " + visitorgan+ " ";

			String[] tablename = { "PurchaseIncome", "PurchaseIncomeDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("pw.MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppPurchaseIncomeDetail asod=new AppPurchaseIncomeDetail(); 
			List sumobj = asod.getTotalSubum(whereSql);
		     
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        ArrayList alsod=new ArrayList();
	        AppWarehouse aw = new AppWarehouse();
	        Double totalsum=0.00;
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
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
	        	sodf.setUnitprice(pbd.getUnitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setSubsum(pbd.getSubsum());
	        	totalsum += sodf.getSubsum();
	        	alsod.add(sodf);
	        }
		       
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("alsod", alsod);
			  
			double allsum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allsum = Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
			}
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
		      
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>入库>>列表采购入库明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
