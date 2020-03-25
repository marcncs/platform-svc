package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncomeIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ImportSysData;
import com.winsafe.drp.util.fileListener.UFIDA.ResXmlBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditPurchaseIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		super.initdata(request);try{
			String piid = request.getParameter("PIID");
			AppPurchaseIncome api = new AppPurchaseIncome();
			AppFUnit af = new AppFUnit();
			AppPurchaseIncomeIdcode apidcode = new AppPurchaseIncomeIdcode();
			PurchaseIncome pi = api.getPurchaseIncomeByID(piid);

			if (pi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppPurchaseIncomeDetail apid = new AppPurchaseIncomeDetail();
			List<PurchaseIncomeDetail> pils = apid.getPurchaseIncomeDetailByPbId2(piid);
			for (PurchaseIncomeDetail pid : pils ) {

				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
				double q2 = apidcode.getQuantitySumByPiidProductid(pid.getProductid(), piid);
				if (q1 != q2) {
					request.setAttribute("result", pid.getProductname()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			
			
			List<PurchaseIncomeIdcode> idcodelist = apidcode.getPurchaseIncomeIdcodeByPiid(piid, 1);
			
			addIdcode(pi, idcodelist);

			api.updIsAudit(piid, userid, 1);
			
			List<PurchaseIncomeIdcode> idlist = apidcode.getPurchaseIncomeIdcodeByPiid(piid);
			addProductStockpile(idlist, pi.getWarehouseid());
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 7,"入库>>采购入库>>复核采购入库,编号：" + piid);
			ResXmlBean resXmlBean=new ResXmlBean();
			resXmlBean.setCgeneralhid(api.getPurchaseIncomeNccodeByID(piid));
			resXmlBean
					.setState(ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("xml_state_success"));
			resXmlBean
					.setDetail(ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("success"));
			
			return mapping.findForward("audit");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<PurchaseIncomeIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (PurchaseIncomeIdcode idcode : idlist ) {
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

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getPiid(), "采购-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	
	private void addIdcode(PurchaseIncome pi, List<PurchaseIncomeIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (PurchaseIncomeIdcode wi : idcodelist) {
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
			ic.setBillid(wi.getPiid());
			ic.setIdbilltype(1);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid(pi.getProvideid());
			ic.setProvidename(pi.getProvidename());
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}

}
