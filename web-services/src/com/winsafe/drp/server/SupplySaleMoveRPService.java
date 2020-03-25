package com.winsafe.drp.server;

import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-9-3 下午04:58:32 
 * www.winsafe.cn 
 */
public class SupplySaleMoveRPService {

	private String billno;
	
	private AppSupplySaleMove appssm = new AppSupplySaleMove();
	private AppReceivableObject appro = new AppReceivableObject();
	private AppReceivable ar = new AppReceivable();
	private OrganService ao = new OrganService();
	
	public SupplySaleMoveRPService(String billno){
		this.billno = billno;
	}
	
	public void addReceivablePayable() throws Exception{
		SupplySaleMove ssm = appssm.getSupplySaleMoveByID(billno);
		addOneToTwoReceivable(ssm);
		addTwoToThreeReceivable(ssm);
		addTwoToOnePayable(ssm);
	}
	
	
	
	private void addOneToTwoReceivable(SupplySaleMove ssm) throws Exception{
		Organ organ = ao.getOrganByID(ssm.getSupplyorganid());
		ReceivableObject  ro = new ReceivableObject();
		ro.setOid(ssm.getSupplyorganid());
		ro.setObjectsort(0);
		ro.setPayer(organ.getOrganname());
		ro.setMakeorganid(ssm.getMakeorganid());
		ro.setMakedeptid(ssm.getMakedeptid());
		ro.setMakeid(ssm.getMakeid());
		ro.setMakedate(DateUtil.getCurrentDate());
		ro.setKeyscontent(ro.getOid()+","+ro.getPayer()+","+organ.getOmobile());
		appro.addReceivableObjectIsNoExist(ro);
		
		Receivable r = new Receivable();
		r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
		r.setRoid(ro.getOid());
		r.setReceivablesum(ssm.getTotalsum());
		r.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), organ.getPrompt()));
		r.setPaymentmode(ssm.getPaymentmode());
		r.setBillno(billno);
		r.setReceivabledescribe(billno + "代销送货单生成应收款");
		r.setAlreadysum(0.00d);
		r.setIsclose(0);
		r.setMakeorganid(ro.getMakeorganid());
		r.setMakedeptid(ro.getMakedeptid());
		r.setMakeid(ro.getMakeid());
		r.setMakedate(DateUtil.getCurrentDate());
		ar.addReceivable(r);
	}
	
	private void addTwoToThreeReceivable(SupplySaleMove ssm) throws Exception{
		Organ organ = ao.getOrganByID(ssm.getInorganid());
		ReceivableObject ro = new ReceivableObject();
		ro.setOid(ssm.getInorganid());
		ro.setObjectsort(0);
		ro.setPayer(organ.getOrganname());
		ro.setMakeorganid(ssm.getSupplyorganid());
		ro.setMakedeptid(0);
		ro.setMakeid(0);
		ro.setMakedate(DateUtil.getCurrentDate());
		ro.setKeyscontent(ro.getOid()+","+ro.getPayer()+","+organ.getOmobile());
		appro.addReceivableObjectIsNoExist(ro);
		
		Receivable r = new Receivable();
		r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
		r.setRoid(ro.getOid());
		r.setReceivablesum(ssm.getStotalsum());
		r.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), organ.getPrompt()));
		r.setPaymentmode(ssm.getPaymentmode());
		r.setBillno(billno);
		r.setReceivabledescribe(billno + "代销送货单生成应收款");
		r.setAlreadysum(0.00d);
		r.setIsclose(0);
		r.setMakeorganid(ro.getMakeorganid());
		r.setMakedeptid(ro.getMakedeptid());
		r.setMakeid(ro.getMakeid());
		r.setMakedate(DateUtil.getCurrentDate());
		ar.addReceivable(r);
	}
	
	private void addTwoToOnePayable(SupplySaleMove ssm) throws Exception{
		Organ organ = ao.getOrganByID(ssm.getMakeorganid());
		AppPayableObject apo = new AppPayableObject();
		PayableObject po = new PayableObject();
		po.setOid(ssm.getMakeorganid());
		po.setObjectsort(0);
		po.setPayee(ssm.getMakeorganidname());
		po.setMakeorganid(ssm.getSupplyorganid());
		po.setMakedeptid(0);
		po.setMakeid(0);
		po.setMakedate(DateUtil.getCurrentDate());
		po.setKeyscontent(po.getOid()+","+po.getPayee()+","+organ.getOmobile());
		apo.noExistsToAdd(po);	
		
		AppPayable ap = new AppPayable();
		Payable p = new Payable();
		p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
		p.setPoid(po.getOid());
    	p.setPayablesum(ssm.getTotalsum());
    	p.setPaymode(ssm.getPaymentmode());
    	p.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), organ.getPrompt()));
    	p.setBillno(billno);
    	p.setPayabledescribe(billno+"代销送货单生成应付款");
    	p.setAlreadysum(0.00d);
    	p.setIsclose(0);
    	p.setMakeorganid(po.getMakeorganid());
    	p.setMakedeptid(po.getMakedeptid());
    	p.setMakeid(po.getMakeid());
    	p.setMakedate(DateUtil.getCurrentDate());	    	
    	ap.addPayable(p);
	}
}
