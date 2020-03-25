package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ReceiveTallyStockAlterMoveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();

		super.initdata(request);try{
			String omid = request.getParameter("OMID");
			AppStockAlterMove asam = new AppStockAlterMove(); 
			//AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
			StockAlterMove sam = asam.getStockAlterMoveByID(omid);

			//List ls = asamd.getStockAlterMoveDetailBySamID(omid);


			AppPayableObject apo = new AppPayableObject();
			PayableObject po = new PayableObject();
			po.setOid(sam.getMakeorganid());
			po.setObjectsort(0);
			po.setPayee(sam.getMakeorganidname());
			//po.setTotalpayablesum(Double.valueOf(0.00));
			//po.setAlreadypayablesum(Double.valueOf(0.00));
			po.setMakeorganid(users.getMakeorganid());
			po.setMakedeptid(users.getMakedeptid());
//			po.setMakeid(userid);
			po.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			po.setKeyscontent(sam.getMakeorganid()+","+sam.getMakeorganidname());
			apo.noExistsToAdd(po);
			

			String poid = sam.getMakeorganid();//apo.getPayableObjectByOSPID(2, sam.getMakeorganid(),users.getMakeorganid()).getOid();

			
			AppPayable ap = new AppPayable();
			Payable p = new Payable();
			p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
			p.setPoid(poid);
	    	p.setPayablesum(sam.getTotalsum());
	    	p.setPaymode(sam.getPaymentmode());
	    	p.setBillno(omid);
	    	p.setPayabledescribe(omid+"机构调拨生成应付款");
	    	p.setAlreadysum(0.00d);
	    	p.setMakeorganid(users.getMakeorganid());
	    	p.setMakedeptid(users.getMakedeptid());
//	    	p.setMakeid(userid);
	    	p.setMakedate(DateUtil.getCurrentDate());
	    	p.setIsclose(0);
	    	

	    	//String addp =apo.addPayableSum(poid, p.getPayablesum());
	    	ap.addPayable(p);
			
		      request.setAttribute("result", "databases.audit.success");
//		      DBUserLog.addUserLog(userid,"记账机构调拨,编号："+omid); 
			return mapping.findForward("tally");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
