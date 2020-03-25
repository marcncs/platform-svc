package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TallyPurchaseWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);


		try{
			String pwid = request.getParameter("PWID");
			AppPurchaseWithdraw asam = new AppPurchaseWithdraw(); 
			AppPurchaseWithdrawDetail asamd = new AppPurchaseWithdrawDetail();
			PurchaseWithdraw pw = asam.getPurchaseWithdrawByID(pwid);

			List pils = asamd.getPurchaseWithdrawDetailByPWID(pwid);


			AppPayableObject apo = new AppPayableObject();
			PayableObject po = new PayableObject();
			po.setOid(pw.getPid());
			po.setObjectsort(2);
			po.setPayee(pw.getPname());
			//po.setTotalpayablesum(Double.valueOf(0.00));
			//po.setAlreadypayablesum(Double.valueOf(0.00));
			po.setMakeorganid(users.getMakeorganid());
			po.setMakedeptid(users.getMakedeptid());
//			po.setMakeid(userid);
			po.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			po.setKeyscontent(pw.getPid()+","+pw.getPname()+","+pw.getTel());
			
			apo.noExistsToAdd(po);
			

			String poid = apo.getPayableObjectByOSPID(2, pw.getPid(),pw.getMakeorganid()).getOid();
			
			
			
			AppPayable ap = new AppPayable();
			Payable p = new Payable();
			p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
			p.setPoid(poid);
	    	p.setPayablesum(-pw.getTotalsum());
	    	p.setPaymode(0);
	    	p.setBillno(pwid);
	    	p.setPayabledescribe(pwid+"采购退货单生成负数应付款");
	    	p.setAlreadysum(0.00d);
	    	p.setMakeorganid(users.getMakeorganid());
	    	p.setMakedeptid(users.getMakedeptid());
//	    	p.setMakeid(userid);
	    	p.setMakedate(DateUtil.getCurrentDate());
	    	p.setIsclose(0);
	    	

	    	//String addp =apo.addPayableSum(poid, p.getPayablesum());
	    	ap.addPayable(p);
			
		      request.setAttribute("result", "databases.audit.success");
//		      DBUserLog.addUserLog(userid,"记账采购退货"); 
			return mapping.findForward("tally");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
