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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.StockMoveDetail;

/**
 * 验证条码数量与订单数量是否一致  转仓
 */
public class AjaxQuantityCompleteStockMoveAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//声明所需的DAO
		boolean flag = true;
		AppFUnit af = new AppFUnit();
		AppStockMoveDetail asamd = new AppStockMoveDetail();
		AppStockMoveIdcode apidcode = new AppStockMoveIdcode();
		//最后返回的对象
		JSONObject json = new JSONObject();
		//转仓单ID
		String smid = request.getParameter("id");
		//得到明细
		List<StockMoveDetail> pils = asamd.getStockMoveDetailBySmIDNew(smid);
		//循环判断明细产品数量是否符合
		for (StockMoveDetail samd : pils){
			//订单数量
			double q1 = af.getQuantity(samd.getProductid(), samd.getUnitid(), samd.getQuantity());
			//扫描数量
			double q2 = apidcode.getQuantitySumBysmidProductid2(samd.getProductid(), smid);
			//订单数量与扫描数量不一致
			if (q1 != q2){
				flag = false;
				break;
			}
		}
		if(!flag){
			json.put("state", "1");
		}else{
			json.put("state", "0");
		}
		json.put("id", smid);
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
		return null;
	}
}
