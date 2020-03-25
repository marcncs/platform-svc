package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.Warehouse;
/**
 * 验证入库单详情数量与条码数量是否相等
* @Title: AjaxQuantityAuditProductIncomeAction.java
* @author: WenPing 
* @CreateTime: Aug 3, 2012 1:19:34 PM
* @version:  2.0
 */
public class AjaxQuantityAuditProductIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 声明所需的DAO
//		boolean flag = true;
		//单据条形码服务类
		AppProductIncomeIdcode apidcode = new AppProductIncomeIdcode();
		//入库单详情dao
//		AppProductIncomeDetail apid = new AppProductIncomeDetail();
//		AppProductIncome api = new AppProductIncome();
//		AppWarehouse aw = new AppWarehouse();
		// 最后返回的对象
		JSONObject json = new JSONObject();
		// 入库单ID
		String piid = request.getParameter("id");
		// 得到入库单详情
//		List<ProductIncomeDetail> pils = apid.getProductIncomeDetailByPbId(piid);
//		ProductIncome pi = api.getProductIncomeByID(piid);
//		Warehouse w  =aw.getWarehouseByID(pi.getWarehouseid());
		double idcodeQuantity =apidcode.getTotalQuantityByPiid( piid);
		
//		for (ProductIncomeDetail pid : pils) {
			//单据同种产品数量
//			double q1 = apid.getTotalQuantityByPiidProductid(pid.getProductid(),piid );
//			double q1 = af.getQuantity(pid.getProductid(), pid.getUnitid(), pid.getQuantity());
			// 条码数量
//			double q = apidcode.getTotalQuantityByPiidProductid(pid.getProductid(), piid);
//			idcodeQuantity+=q;
			// 订单数量与条码数量不一致
//			if (q1 != q2) {
//				flag = false;
//				break;
//			}
//		}
//		if (!flag) {
//			json.put("state", "1");
//		} else {
//			json.put("state", "0");
//		}
		 json.put("idcodeQuantity", idcodeQuantity);	
		json.put("id", piid);
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		return null;
	}
}