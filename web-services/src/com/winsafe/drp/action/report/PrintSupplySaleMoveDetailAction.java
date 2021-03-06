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
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.SSMDetailReportForm;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintSupplySaleMoveDetailAction extends BaseAction{
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
			String Condition = "pw.id=pwd.ssmid and (pw.iscomplete=1 and pw.isblankout=0 " + visitorgan+ " )  ";
			String[] tablename = { "SupplySaleMove", "SupplySaleMoveDetail"  };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MoveDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMove asod=new AppSupplySaleMove(); 
	        List sodls = asod.getDetailReport(whereSql);
	        ArrayList alsod=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	SSMDetailReportForm sodf = new SSMDetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String morganname = (String)o[0];
	        	String rorganname = (String)o[1];
	        	String sorganname = (String)o[2];
	        	String makedate = DateUtil.formatDateTime((Date)o[3]);
	        	SupplySaleMoveDetail pbd = (SupplySaleMoveDetail)o[4];
	        	sodf.setOname(morganname);
	        	sodf.setRname(rorganname);
	        	sodf.setSname(sorganname);
	        	sodf.setMakedate(makedate);
	        	sodf.setBillid(pbd.getSsmid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", pbd.getUnitid()));
	        	sodf.setPunitprice(pbd.getPunitprice());
	        	sodf.setSunitprice(pbd.getSunitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setPsubsum(pbd.getPsubsum());
	        	sodf.setSsubsum(pbd.getSsubsum());
	        	totalsum += sodf.getSsubsum();
	        	alsod.add(sodf);
	        }
		       
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("alsod", alsod);



	      return mapping.findForward("toprint");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
