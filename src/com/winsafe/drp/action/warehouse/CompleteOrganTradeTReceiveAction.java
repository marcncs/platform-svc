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
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.AppOrganTradesTIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.dao.OrganTradesTIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CompleteOrganTradeTReceiveAction extends BaseAction {
	private AppFUnit af = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");

		super.initdata(request);try{
			AppOrganTrades asm = new AppOrganTrades();
			AppOrganTradesDetail asmd = new AppOrganTradesDetail();
			AppOrganTradesTIdcode appmi = new AppOrganTradesTIdcode();
			OrganTrades sm = asm.getOrganTradesByID(smid);


			
			if (sm.getIsblankout() == 1) {
				request.setAttribute("result", "datebases.record.isblankout");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			if (sm.getIsreceive() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			List<OrganTradesDetail> pils = asmd.getOrganTradesDetailByotid(smid);
			for (OrganTradesDetail pid : pils) {

				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getCanquantity());
				double q2 = appmi.getQuantitySumByotidProductid(pid.getProductid(), smid, pid.getBatch());
				if (q1 != q2) {
					request.setAttribute("result", "产品:"+pid.getProductname()+" 批次:"+pid.getBatch()+"数量不匹配,不能复核!");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			List<OrganTradesTIdcode> alllist = appmi.getOrganTradesTIdcodeByotid(smid);
			List<OrganTradesTIdcode> idcodelist = appmi.getOrganTradesTIdcodeByotid(smid, 1);
			
			addProductStockpile(alllist, sm.getOutwarehouseid());
						
			sm.setIsreceive(1);
			sm.setReceiveid(userid);
			sm.setReceivedate(DateUtil.getCurrentDate());
			asm.update(sm);
			
			addIdcode(sm, idcodelist);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 7,"入库>>渠道换货换方签收>>签收渠道换货单,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<OrganTradesTIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (OrganTradesTIdcode idcode : idlist ) {
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

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),idcode.getQuantity(), 
					ps.getWarehouseid(), ps.getWarehousebit(), idcode.getOtid(), "渠道换货换方签收-入库");
		}
	}
	
	
	private void addIdcode(OrganTrades pi, List<OrganTradesTIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		for (OrganTradesTIdcode wi : idcodelist) {
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
			ic.setBillid(wi.getOtid());
			ic.setIdbilltype(8);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getOutwarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());				
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(),ic.getMakeorganid(),ic.getWarehouseid(),ic.getWarehousebit(), 1, 0);
		}
	}

}
