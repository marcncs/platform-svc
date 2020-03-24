package com.winsafe.drp.action.machin;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ResXmlBean;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditBlueIncomeAction extends BaseAction {
	private AppFUnit af = new AppFUnit();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String omid = request.getParameter("ID");

		super.initdata(request);
		try {
			AppOtherIncomeAll asm = new AppOtherIncomeAll();
			AppOtherIncomeIdcode appmi = new AppOtherIncomeIdcode();
			OtherIncomeAll sm = asm.getOtherIncomeByID(omid);

			if (sm.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherIncomeDetailAll apid = new AppOtherIncomeDetailAll();
			List<OtherIncomeDetailAll> pils = apid.getOtherIncomeDetailByOiid(omid);

			AppProductStockpile aps = new AppProductStockpile();
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			for (OtherIncomeDetailAll pid : pils) {
				ps = new ProductStockpile();

				ps.setId(Long.valueOf(MakeCode
						.getExcIDByRandomTableName("product_stockpile", 0, "")));
				ps.setProductid(pid.getProductid());
				Product p = ap.getProductByID(pid.getProductid());
				ps.setCountunit(p.getSunit());
				ps.setBatch(pid.getBatch());
				ps.setProducedate("");
				ps.setVad("");
				ps.setWarehouseid(sm.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);
				Double quantity = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid
						.getQuantity());
				
				if (sm.getIsaccount() != null && sm.getIsaccount() == 1) {
					// 记台账
					aps.inProductStockpile(pid.getProductid(), p.getSunit(), pid.getBatch(),
							quantity, sm.getWarehouseid(), ps.getWarehousebit(), sm.getId(),
							"入库-蓝冲入库");
				} else {
					aps.inProductStockpileWithOutAccount(pid.getProductid(), p.getSunit(), pid
							.getBatch(), quantity, sm.getWarehouseid(), ps
							.getWarehousebit(), sm.getId(), "入库-蓝冲入库");
				}
				// ProductCostService.updateCost(warehouseid, ps.getProductid(),
				// ps.getBatch());
			}

			asm.updIsAudit(omid, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + omid);
			ResXmlBean resXmlBean = new ResXmlBean();
			resXmlBean.setCgeneralhid(asm.getOtherIncomeNccodeByID(omid));
			resXmlBean.setState(ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString(
					"xml_state_success"));
			resXmlBean.setDetail(ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("success"));

			HibernateUtil.commitTransaction();
			return mapping.findForward("success");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

}
