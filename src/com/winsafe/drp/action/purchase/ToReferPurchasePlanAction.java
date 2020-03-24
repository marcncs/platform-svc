package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlow;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.ApproveFlowForm;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.hbm.util.DbUtil;

public class ToReferPurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ppid = request.getParameter("PPID");

		try {
			if(DbUtil.judgeApproveStatusToRefer("PurchasePlan", ppid)){
		       	 String result = "databases.record.approvestatus";
		            request.setAttribute("result", result);
		            return new ActionForward("/sys/lockrecordclose.jsp");
		        }
		    	
		    	AppPurchasePlan app = new AppPurchasePlan();
		    	PurchasePlan pp = new PurchasePlan();
		    	pp = app.getPurchasePlanByID(ppid);
		    	
		    	if(pp.getIsratify()==0){
		          	 String result = "databases.record.noratifynorefer";
		               request.setAttribute("result", result);
		               return new ActionForward("/sys/lockrecordclose.jsp");
		           }

		      AppApproveFlow aaf = new AppApproveFlow();
		      List uls = aaf.getApproveFlow();
		      ArrayList apls = new ArrayList();
		      for(int i=0;i<uls.size();i++){
		        ApproveFlowForm aff = new ApproveFlowForm();
		        Object[] o = (Object[])uls.get(i);
		        aff.setId(o[0].toString());
		        aff.setFlowname(String.valueOf(o[1]));
		        apls.add(aff);
		      }


			request.setAttribute("ppid", ppid);
			request.setAttribute("apls", apls);

			return mapping.findForward("toselect");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
