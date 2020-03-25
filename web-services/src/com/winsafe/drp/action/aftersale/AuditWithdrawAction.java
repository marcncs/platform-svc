package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.AppWithdrawIdcode;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.dao.WithdrawDetail;
import com.winsafe.drp.dao.WithdrawIdcode;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		try {
			String piid = request.getParameter("ID");
			AppWithdraw api = new AppWithdraw();
			AppFUnit af = new AppFUnit();
			AppWithdrawIdcode apidcode = new AppWithdrawIdcode();
			Withdraw pi = api.getWithdrawByID(piid);

			if (pi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if ( pi.getIsblankout() == 1 ){
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppWithdrawDetail apid = new AppWithdrawDetail();
			List<WithdrawDetail> pils = apid.getWithdrawDetailByWID(piid);
			for (WithdrawDetail pid : pils ) {
				
				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
				double q2 = apidcode.getQuantitySumBywidProductid(pid.getProductid(), piid);
				if (q1 != q2) {
					request.setAttribute("result", pid.getProductname()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			
			
			List<WithdrawIdcode> idcodelist = apidcode.getWithdrawIdcodeBywid(piid, 1);
			
			addIdcode(pi, idcodelist);
			
			api.updIsAudit(piid, userid, 1);
			
			List<WithdrawIdcode> idlist = apidcode.getWithdrawIdcodeBywid(piid);			
			addProductStockpile(idlist, pi.getWarehouseid());
			
			
			addReceivable(pi, users);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6,"销售退货>>复核销售退货,编号：" + piid);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<WithdrawIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (WithdrawIdcode idcode : idlist ) {
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
			ps.setMakedate(DateUtil.getCurrentDate());
			aps.addProductByPurchaseIncome(ps);

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),
					idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getWid(), "复核销售退货-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	
	private void addIdcode(Withdraw pi, List<WithdrawIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (WithdrawIdcode wi : idcodelist) {
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
			ic.setBillid(wi.getWid());
			ic.setIdbilltype(9);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}
	
	private void addReceivable(Withdraw sb, UsersBean users) throws Exception {	
		AppCustomer ac = new AppCustomer();
		Customer c = ac.getCustomer(sb.getCid());
		
		AppReceivableObject appro = new AppReceivableObject();
		ReceivableObject ro = new ReceivableObject();
		ro.setOid(sb.getCid());
		ro.setObjectsort(1);
		ro.setPayer(sb.getCname());
		ro.setMakeorganid(users.getMakeorganid());
		ro.setMakedeptid(users.getMakedeptid());
		ro.setMakeid(users.getUserid());
		ro.setMakedate(DateUtil.getCurrentDate());
		ro.setKeyscontent(ro.getOid()+","+ro.getPayer()+","+sb.getCmobile());
		appro.addReceivableObjectIsNoExist(ro);

		
		AppReceivable ar = new AppReceivable();
		Receivable r = new Receivable();
		r.setId(MakeCode.getExcIDByRandomTableName("receivable",2, ""));
		r.setRoid(ro.getOid());
		r.setReceivablesum(-sb.getTotalsum());
		r.setAwakedate(DateUtil.calculatedays(DateUtil.getCurrentDate(), c.getPrompt()));
		r.setPaymentmode(0);
		r.setBillno(sb.getId());
		r.setReceivabledescribe(sb.getId() + "复核销售退货单生成负的应收款");
		r.setAlreadysum(0.00d);
		r.setIsclose(0);
		r.setMakeorganid(users.getMakeorganid());
		r.setMakedeptid(users.getMakedeptid());
		r.setMakeid(sb.getMakeid());
		r.setMakedate(DateUtil.getCurrentDate());
		ar.addReceivable(r);
	}

}
