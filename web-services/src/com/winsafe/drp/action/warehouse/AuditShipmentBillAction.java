package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.SupplySaleMoveRPService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String sbid = request.getParameter("SBID");
			AppShipmentBill asb = new AppShipmentBill();
			ShipmentBill sb = asb.getShipmentBillByID(sbid);

//			if (sb.getIstrans() == 0) {
//				request.setAttribute("result", "datebases.record.nottrans");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			if ( sb.getBsort()== 0 || sb.getBsort()== 1 ){
				
				addReceivable(sb, users);
			}
			
			if ( sb.getBsort() == 3 ){
				SupplySaleMoveRPService sss = new SupplySaleMoveRPService(sb.getId());
				sss.addReceivablePayable();
			}
			
			if ( sb.getBsort() == 7 || sb.getBsort() == 10 ){
				addPayable(sb, users);
			}
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 8, "复核送货清单,编号：" + sbid);
			
			asb.updIsAudit(sbid, userid, 1);
			
//			if(sbid.startsWith("OM")){
//				return new ActionForward("/warehouse/tallyStockAlterMoveAction.do?OMID="
//						+ sbid);
//			}else if(sbid.startsWith("PW")){
//				return new ActionForward("/aftersale/tallyPurchaseWithdrawAction.do?PWID="+sbid);
//			}
			
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addReceivable(ShipmentBill sb, UsersBean users) throws Exception {
		int prompt = 0;
		if ( sb.getObjectsort().intValue() == 0 ){
			OrganService ao = new OrganService();
			Organ o = ao.getOrganByID(sb.getCid());
			prompt = o.getPrompt();
		}else if ( sb.getObjectsort().intValue() == 1 ){
			AppCustomer ac= new AppCustomer();
			Customer c = ac.getCustomer(sb.getCid());
			prompt = c.getPrompt();
		}else if ( sb.getObjectsort().intValue() == 2 ){
			AppProvider ap= new AppProvider();
			Provider p = ap.getProviderByID(sb.getCid());
			prompt = p.getPrompt();
		}

		AppReceivableObject appro = new AppReceivableObject();
		ReceivableObject ro = new ReceivableObject();
		ro.setOid(sb.getCid());
		ro.setObjectsort(sb.getObjectsort());
		ro.setPayer(sb.getCname());
		ro.setMakeorganid(users.getMakeorganid());
		ro.setMakedeptid(users.getMakedeptid());
		ro.setMakeid(users.getUserid());
		ro.setMakedate(DateUtil.StringToDatetime(DateUtil
				.getCurrentDateTime()));
		ro.setKeyscontent(sb.getCid()+","+sb.getCname()+","+sb.getCmobile());
		appro.addReceivableObjectIsNoExist(ro);

		
		AppReceivable ar = new AppReceivable();
		Receivable r = new Receivable();
		r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
		r.setRoid(sb.getCid());
		r.setReceivablesum(sb.getTotalsum());
		r.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), prompt));
		r.setPaymentmode(sb.getPaymentmode());
		r.setBillno(sb.getId());
		r.setReceivabledescribe(sb.getId() + "送货单生成应收款");
		r.setAlreadysum(0.00d);
		r.setIsclose(0);
		r.setMakeorganid(users.getMakeorganid());
		r.setMakedeptid(users.getMakedeptid());
		r.setMakeid(sb.getMakeid());
		r.setMakedate(DateUtil.StringToDatetime(DateUtil
				.getCurrentDateTime()));
		ar.addReceivable(r);
	}
	
	private void addPayable(ShipmentBill sb, UsersBean users) throws Exception{
		
		int objectsort=0;
		int prompt = 0;
		String describe = "";
		if ( sb.getBsort() ==7 ){
			OrganService ao = new OrganService();
			Organ organ = ao.getOrganByID(sb.getCid());
			objectsort=0;
			prompt = organ.getPrompt();
			describe="渠道退货送货单生成负的应付款";
		}else if ( sb.getBsort() == 10 ){
			AppProvider ap = new AppProvider();
			Provider provider = ap.getProviderByID(sb.getCid());
			objectsort=2;
			prompt = provider.getPrompt();
			describe="采购退货送货单生成负的应付款";
		}
		
		AppPayableObject apo = new AppPayableObject();
		PayableObject po = new PayableObject();
		po.setOid(sb.getCid());
		po.setObjectsort(objectsort);
		po.setPayee(sb.getCname());
		po.setMakeorganid(users.getMakeorganid());
		po.setMakedeptid(users.getMakedeptid());
		po.setMakeid(users.getUserid());
		po.setMakedate(DateUtil.getCurrentDate());
		po.setKeyscontent(po.getOid()+","+po.getPayee()+","+sb.getTel());
		apo.noExistsToAdd(po);	
		
		AppPayable ap = new AppPayable();
		Payable p = new Payable();
		p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
		p.setPoid(po.getOid());
    	p.setPayablesum(-sb.getTotalsum());
    	p.setPaymode(sb.getPaymentmode());
    	p.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), prompt));
    	p.setBillno(sb.getId());
    	p.setPayabledescribe(sb.getId()+describe);
    	p.setAlreadysum(0.00d);
    	p.setIsclose(0);
    	p.setMakeorganid(po.getMakeorganid());
    	p.setMakedeptid(po.getMakedeptid());
    	p.setMakeid(po.getMakeid());
    	p.setMakedate(DateUtil.getCurrentDate());	    	
    	ap.addPayable(p);
	}

}
