package com.winsafe.drp.action.sales;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.SampleBillDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.hbm.util.DateUtil;

public class ReceiveSampleBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String sbid = request.getParameter("SBID");
			AppSampleBill asb = new AppSampleBill();
			AppSampleBillDetail asbd = new AppSampleBillDetail();
			
			SampleBill sb = asb.findByID(sbid);	
			String wid = sb.getWarehouseID();
			AppProductStockpile  aps = new AppProductStockpile();
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			List<SampleBillDetail> sls = asbd.findBySbid(sbid) ;
			
	
			
			if(sb.getIsaudit() == 0){
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			 for(int i=0;i<sls.size();i++){
				 SampleBillDetail t_sbd = (SampleBillDetail)sls.get(i);
				 aps.adjustProductStockpile(t_sbd.getProductid(), "+",
						 t_sbd.getQuantity(), wid);	
				 apsa.adjustProductStockpileAll(t_sbd.getProductid(), "+",
						 t_sbd.getQuantity(), wid);	
			 }	
			sb.setRecycleid(userid);
			sb.setActualrecycle(DateUtil.getCurrentDate());
			sb.setIsrecycle(1);
			asb.update(sb);

			request.setAttribute("result", "databases.audit.success");
			//DBUserLog.addUserLog(userid, "回收样品记录,编号：" + sbid);

			return mapping.findForward("receive");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
