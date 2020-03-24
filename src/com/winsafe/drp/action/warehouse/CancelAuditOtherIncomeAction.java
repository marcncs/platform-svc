package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CancelAuditOtherIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		super.initdata(request);try{
			AppOtherIncome asm = new AppOtherIncome();
			OtherIncome sm = asm.getOtherIncomeByID(smid);

			
			if (sm.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
//			AppOtherIncomeIdcode appmi = new AppOtherIncomeIdcode();
//			List<OtherIncomeIdcode> idcodelist = appmi.getOtherIncomeIdcodeByoiid(smid, 1);
//			AppIdcode appidcode = new AppIdcode();
//			for ( OtherIncomeIdcode ic : idcodelist ){
//				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
//					request.setAttribute("result", "databases.recode.idcodehasuse");
//					return new ActionForward("/sys/lockrecordclose.jsp");
//				}
//			}
//
//
//			List<OtherIncomeIdcode> alllist = appmi.getOtherIncomeIdcodeByoiid(smid);
//			
//			returnProductStockpile(alllist, sm.getWarehouseid());
//			
//			asm.updIsAudit(smid, userid, 0);
//						
//			for ( OtherIncomeIdcode ic : idcodelist ){
//				appidcode.updIsUse(ic.getIdcode(), 0);
//			}

			AppOtherIncomeDetail apid = new AppOtherIncomeDetail();
			List<OtherIncomeDetail> pils = apid.getOtherIncomeDetailByOiid(sm.getId());
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

				aps.returninProductStockpile(pid.getProductid(), pid.getUnitid(), pid.getBatch(), pid.getQuantity(),
						sm.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, sm.getId(), "取消复核盘盈单出库");
			}
			asm.updIsNotAudit(smid);
			

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"库存盘点>>取消复核盘盈单,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<OtherIncomeIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (OtherIncomeIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getOiid(), "取消复核盘盈单出库");
		}
	}

}
