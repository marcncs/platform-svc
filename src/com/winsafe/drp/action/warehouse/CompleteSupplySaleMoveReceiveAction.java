package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.AppSupplySaleMoveIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleMoveIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CompleteSupplySaleMoveReceiveAction extends BaseAction {
	private AppFUnit af = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String omid = request.getParameter("ID");

		super.initdata(request);try{
			AppSupplySaleMove asm = new AppSupplySaleMove();
			AppSupplySaleMoveIdcode appmi = new AppSupplySaleMoveIdcode();
			SupplySaleMove sm = asm.getSupplySaleMoveByID(omid);

			
			if (sm.getIsshipment() == 0) {
				request.setAttribute("result", "databases.record.noshipment");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (sm.getIsblankout() == 1) {
				request.setAttribute("result", "datebases.record.isblankout");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			if (sm.getIscomplete() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppSupplySaleMoveDetail apid = new AppSupplySaleMoveDetail();
			List<SupplySaleMoveDetail> pils = apid.getSupplySaleMoveBySSMID(omid);
			for (SupplySaleMoveDetail pid : pils) {

				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
				double q2 = appmi.getQuantitySumByssmidProductid(pid.getProductid(), omid);
				if (q1 != q2) {
					request.setAttribute("result", pid.getProductname()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			
			List<SupplySaleMoveIdcode> alllist = appmi.getSupplySaleMoveIdcodeByssmid(omid);
			List<SupplySaleMoveIdcode> idcodelist = appmi.getSupplySaleMoveIdcodeByssmid(omid, 1);
			
			
						
			asm.updSupplySaleMoveIsComplete(omid, 1, userid);
			
			addIdcode(sm, idcodelist);
			
			addProductStockpile(alllist, sm.getInwarehouseid());
			addPayable(sm, users);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7,"入库>>订购入库>>签收代销单,编号：" + omid);
//			return new ActionForward(
//					"/warehouse/receiveTallySupplySaleMoveAction.do?OMID="
//							+ omid);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<SupplySaleMoveIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (SupplySaleMoveIdcode idcode : idlist ) {
			ps = new ProductStockpile();
			
			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_stockpile", 0, "")));
			ps.setProductid(idcode.getProductid());
			
			ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
			ps.setBatch(idcode.getBatch());
			ps.setProducedate(idcode.getProducedate());
			ps.setVad(idcode.getValidate());				
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(idcode.getWarehousebit());
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			aps.addProductByPurchaseIncome(ps);

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getSsmid(), "代销签收-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	
	private void addIdcode(SupplySaleMove pi, List<SupplySaleMoveIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		for (SupplySaleMoveIdcode wi : idcodelist) {
			ic = new Idcode();				
			ic.setIdcode(wi.getIdcode());
			ic.setProductid(wi.getProductid());		
			ic.setProductname(ap.getProductNameByID(ic.getProductid()));
			ic.setBatch(wi.getBatch());
			ic.setProducedate(wi.getProducedate());
			ic.setVad(wi.getValidate());
			ic.setLcode(wi.getLcode());
			ic.setStartno(wi.getStartno());
			ic.setEndno(wi.getEndno());
			ic.setUnitid(wi.getUnitid());
			ic.setQuantity(af.getQuantity(ic.getProductid(), ic.getUnitid(), 1));
			ic.setFquantity(ic.getQuantity());
			ic.setPackquantity(wi.getPackquantity());				
			ic.setIsuse(1);				
			ic.setIsout(0);
			ic.setBillid(wi.getSsmid());
			ic.setIdbilltype(5);
			ic.setMakeorganid(pi.getInorganid());
			ic.setWarehouseid(pi.getInwarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}
	
	
	private void addPayable(SupplySaleMove pi, UsersBean users) throws Exception{

		OrganService ao = new OrganService();
		Organ organ = ao.getOrganByID(pi.getSupplyorganid());
		AppPayableObject apo = new AppPayableObject();
		PayableObject po = new PayableObject();
		po.setOid(pi.getSupplyorganid());
		po.setObjectsort(0);
		po.setPayee(organ.getOrganname());
		po.setMakeorganid(users.getMakeorganid());
		po.setMakedeptid(users.getMakedeptid());
		po.setMakeid(users.getUserid());
		po.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		po.setKeyscontent(po.getOid()+","+po.getPayee());
		apo.noExistsToAdd(po);					
		
		
		AppPayable ap = new AppPayable();
		Payable p = new Payable();
		p.setId(MakeCode.getExcIDByRandomTableName("payable",2,""));
		p.setPoid(po.getOid());
    	p.setPayablesum(pi.getStotalsum());
    	p.setPaymode(pi.getPaymentmode());
    	p.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), organ.getPrompt()));
    	p.setBillno(pi.getId());
    	p.setPayabledescribe(pi.getId()+"签收代销单生成应付款");
    	p.setAlreadysum(0.00d);
    	p.setIsclose(0);
    	p.setMakeorganid(users.getMakeorganid());
    	p.setMakedeptid(users.getMakedeptid());
    	p.setMakeid(users.getUserid());
    	p.setMakedate(DateUtil.getCurrentDate());	    	
    	
    	
    	ap.addPayable(p);
	}

}
