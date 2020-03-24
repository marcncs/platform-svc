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
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.ApproveFlowForm;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.hbm.util.DbUtil;

public class ToReferPurchaseOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pbid = request.getParameter("PBID");

		try {
			if(DbUtil.judgeApproveStatusToRefer("PurchaseOrder", pbid)){
		       	 String result = "databases.record.approvestatus";
		            request.setAttribute("result", result);
		            return new ActionForward("/sys/lockrecordclose.jsp");
		        }
		    	
		    	AppPurchaseOrder apb = new AppPurchaseOrder();
		    	PurchaseOrder pb = new PurchaseOrder();
		    	pb = apb.getPurchaseOrderByID(pbid);
		    	
		    	if(pb.getIsblankout()==1){
		          	 String result = "databases.record.blankoutnooperator";
		               request.setAttribute("result", result);
		               return new ActionForward("/sys/lockrecordclose.jsp");
		           }
		    	
		    	if(pb.getIsrefer()==1){
		          	 String result = "databases.record.already";
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
		        aff.setFlowname(o[1].toString());
		        apls.add(aff);
		      }


			request.setAttribute("pbid", pbid);
			request.setAttribute("apls", apls);

			return mapping.findForward("toselect");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
