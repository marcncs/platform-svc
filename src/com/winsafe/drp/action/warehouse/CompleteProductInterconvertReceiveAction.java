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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvertIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CompleteProductInterconvertReceiveAction extends BaseAction {
	private AppFUnit af = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("SMID");

		super.initdata(request);try{
			AppProductInterconvert asm = new AppProductInterconvert();
			AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();
			AppProductInterconvertIdcode appmi = new AppProductInterconvertIdcode();
			ProductInterconvert sm = asm.getProductInterconvertByID(smid);

			
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
			
			
			List<ProductInterconvertDetail> pils = asmd.getProductInterconvertDetailBySamID(smid);
			
			for (ProductInterconvertDetail pid : pils) {

				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
				double q2 = appmi.getQuantitySumByPiidProductid(pid.getProductid(), smid, pid.getBatch());
				if (q1 != q2) {
					request.setAttribute("result", "产品:"+pid.getProductname()+" 批次:"+pid.getBatch()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			List<ProductInterconvertIdcode> alllist = appmi.getProductInterconvertIdcodeByPiid(smid);
			List<ProductInterconvertIdcode> idcodelist = appmi.getProductInterconvertIdcodeByPiid(smid, 1);
			
			
						
			asm.updProductInterconvertIsComplete(smid, 1, userid);
			
			addProductStockpile(alllist, sm.getInwarehouseid());
			addIdcode(sm, idcodelist);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 7,"入库>>商品互转签收>>签收,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<ProductInterconvertIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (ProductInterconvertIdcode idcode : idlist ) {
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

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getPiid(), "商品互转签收-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	
	private void addIdcode(ProductInterconvert pi, List<ProductInterconvertIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		for (ProductInterconvertIdcode wi : idcodelist) {
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
			ic.setIdbilltype(6);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getInwarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}

}
