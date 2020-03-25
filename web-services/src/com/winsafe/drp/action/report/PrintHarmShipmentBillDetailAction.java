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
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintHarmShipmentBillDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pw.id=pwd.hsid and  pw.isendcase=1 and pw.isblankout=0 " + visitorgan+ " ";

			String[] tablename = { "HarmShipmentBill", "HarmShipmentBillDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppHarmShipmentBillDetail asod=new AppHarmShipmentBillDetail(); 
	        List sodls = asod.getDetailReport(whereSql);
	        AppWarehouse aw = new AppWarehouse();
	        ArrayList list=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String wid = (String)o[0];
	        	String makedate = DateUtil.formatDateTime((Date)o[1]);
	        	HarmShipmentBillDetail pbd = (HarmShipmentBillDetail)o[2];
				sodf.setMakedate(makedate);
	        	sodf.setOname(aw.getWarehouseByID(wid).getWarehousename());
	        	sodf.setBillid(pbd.getHsid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
	        	sodf.setQuantity(pbd.getQuantity());
	        	totalsum += sodf.getQuantity();
	        	list.add(sodf);
	        }
	        DBUserLog.addUserLog(userid, 10,"报表分析>>打印报损明细");
	        request.setAttribute("totalqt", totalsum);
			request.setAttribute("list", list);
			return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}
