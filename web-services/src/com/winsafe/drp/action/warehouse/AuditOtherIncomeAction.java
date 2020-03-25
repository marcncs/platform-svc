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
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ImportSysData;
import com.winsafe.drp.util.fileListener.UFIDA.ResXmlBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditOtherIncomeAction extends BaseAction {
	private AppFUnit af = new AppFUnit();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String omid = request.getParameter("ID");

		super.initdata(request);try{
			AppOtherIncome asm = new AppOtherIncome();
			AppOtherIncomeIdcode appmi = new AppOtherIncomeIdcode();
			OtherIncome sm = asm.getOtherIncomeByID(omid);


			
			if (sm.getIsaudit() == 1) { 
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppOtherIncomeDetail apid = new AppOtherIncomeDetail();
			List<OtherIncomeDetail> pils = apid.getOtherIncomeDetailByOiid(omid);
//			for (OtherIncomeDetail pid : pils) {
//
//				double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
//				double q2 = appmi.getQuantitySumByoiidProductid(pid.getProductid(), omid, pid.getBatch());
//				if (q1 != q2) {
//					request.setAttribute("result", "产品:"+pid.getProductname()+" 批次:"+pid.getBatch()+"数量不匹配,不能复核!");
//					return new ActionForward("/sys/lockrecordclose2.jsp");
//				}
//			}
//
//			List<OtherIncomeIdcode> alllist = appmi.getOtherIncomeIdcodeByoiid(omid);
//			List<OtherIncomeIdcode> idcodelist = appmi.getOtherIncomeIdcodeByoiid(omid, 1);
//			
//			addIdcode(sm, idcodelist);
//			addProductStockpile(alllist, sm.getWarehouseid());
			
			AppProductStockpile aps = new AppProductStockpile();	
			AppProductStockpileAll apsa = new AppProductStockpileAll();	
			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			for (OtherIncomeDetail pid : pils ) {
				ps = new ProductStockpile();
				
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(pid.getProductid());
				
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(pid.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(sm.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);

				if(sm.getIsaccount()!=null && sm.getIsaccount()==1){
					//记台账
					aps.inProductStockpile(pid.getProductid(),pid.getUnitid(),pid.getBatch(),pid.getQuantity(), sm.getWarehouseid(), ps.getWarehousebit(), sm.getId(), "复核盘盈单-入库");
				}else{
					aps.inProductStockpileWithOutAccount(pid.getProductid(),pid.getUnitid(),pid.getBatch(),pid.getQuantity(), sm.getWarehouseid(), ps.getWarehousebit(), sm.getId(), "复核盘盈单-入库");
				}
				//				ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
			}

			
			asm.updIsAudit(omid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request,"编号：" + omid);
			ResXmlBean resXmlBean=new ResXmlBean();
			resXmlBean.setCgeneralhid(asm.getOtherIncomeNccodeByID(omid));
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
			

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void addProductStockpile(List<OtherIncomeIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (OtherIncomeIdcode idcode : idlist ) {
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

			
			aps.inProductStockpile(ps.getProductid(),idcode.getUnitid(),ps.getBatch(),idcode.getQuantity(), ps.getWarehouseid(), ps.getWarehousebit(), idcode.getOiid(), "复核盘盈单-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	
	private void addIdcode(OtherIncome pi, List<OtherIncomeIdcode> idcodelist) throws Exception{		
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		for (OtherIncomeIdcode wi : idcodelist) {
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
			ic.setBillid(wi.getOiid());
			ic.setIdbilltype(7);
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
	
	
}
