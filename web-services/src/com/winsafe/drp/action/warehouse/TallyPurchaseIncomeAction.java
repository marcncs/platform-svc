package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TallyPurchaseIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String piid = request.getParameter("PIID");
			AppPurchaseIncome api = new AppPurchaseIncome();
			PurchaseIncome pi = api.getPurchaseIncomeByID(piid);
			
			if (pi.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			addPayable(pi, users);

		    api.updIsTally(piid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7, "入库>>采购入库>>记账采购入库,编号：" + piid);
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void addPayable(PurchaseIncome pi, UsersBean users) throws Exception{

		AppPayableObject apo = new AppPayableObject();
		PayableObject po = new PayableObject();
		po.setOid(pi.getProvideid());
		po.setObjectsort(2);
		po.setPayee(pi.getProvidename());
		po.setMakeorganid(users.getMakeorganid());
		po.setMakedeptid(users.getMakedeptid());
		po.setMakeid(users.getUserid());
		po.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		po.setKeyscontent(pi.getProvideid()+","+pi.getProvidename()+","+pi.getTel());
		apo.noExistsToAdd(po);					
		
		
		AppPayable ap = new AppPayable();
		Payable p = new Payable();
		p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
		p.setPoid(pi.getProvideid());
    	p.setPayablesum(pi.getTotalsum());
    	p.setPaymode(pi.getPaymode());
    	p.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), pi.getPrompt()));
    	p.setBillno(pi.getId());
    	p.setPayabledescribe(pi.getId()+"采购入库单生成应付款");
    	p.setAlreadysum(0.00d);
    	p.setIsclose(0);
    	p.setMakeorganid(users.getMakeorganid());
    	p.setMakedeptid(users.getMakedeptid());
    	p.setMakeid(users.getUserid());
    	p.setMakedate(DateUtil.getCurrentDate());	    	
    	
    	
    	ap.addPayable(p);
	}

}
