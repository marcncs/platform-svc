package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CancelAuditBlueIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		super.initdata(request);try{
			AppOtherIncomeAll asm = new AppOtherIncomeAll();
			OtherIncomeAll sm = asm.getOtherIncomeByID(smid);

			
			if (sm.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			

			AppOtherIncomeDetailAll apid = new AppOtherIncomeDetailAll();
			List<OtherIncomeDetailAll> pils = apid.getOtherIncomeDetailByOiid(sm.getId());
			AppProductStockpile aps = new AppProductStockpile();	
			AppProductStockpileAll apsa = new AppProductStockpileAll();	
			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			for (OtherIncomeDetailAll pid : pils ) {
				ps = new ProductStockpile();
				
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(pid.getProductid());
				
				ps.setCountunit(pid.getUnitid());
				ps.setBatch(pid.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(sm.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);

				aps.returninProductStockpile(pid.getProductid(), pid.getUnitid(), pid.getBatch(), pid.getQuantity(),
						sm.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, sm.getId(), "取消复核蓝冲入库单");
			}
			asm.updIsNotAudit(smid);
			

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(request, "编号：" + smid);
			DBUserLog.addUserLog(userid, 7,"出入库管理>>取消复核蓝冲入库单,编号：" + smid);
			HibernateUtil.commitTransaction();
			return mapping.findForward("success");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
