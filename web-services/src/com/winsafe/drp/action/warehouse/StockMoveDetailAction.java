package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.util.DBUserLog;

public class StockMoveDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		AppStockMove asm = new AppStockMove();
		AppStockMoveDetail asmd = new AppStockMoveDetail();
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();

		try {
			
			String id = request.getParameter("ID");
			StockMove sm = asm.getStockMoveByID(id);
			List<StockMoveDetail> spils = asmd.getStockMoveDetailBySmIDNew(id);
			
			for(StockMoveDetail detail : spils){
				if(detail == null) continue;
				Product product = appProduct.getProductByID(detail.getProductid());
				if(product == null) continue;
				detail.setCountunit(product.getCountunit());
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(detail.getProductid(), detail.getUnitid(), detail.getQuantity());
				quantity = quantity * product.getBoxquantity();
				detail.setcUnitQuantity(quantity);
			}

			request.setAttribute("smid", id);
			request.setAttribute("als", spils);
			request.setAttribute("smf", sm);
			request.setAttribute("type", request.getParameter("type"));
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
