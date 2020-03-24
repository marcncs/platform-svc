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
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.util.DBUserLog;

public class StockAlterMoveDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		
		try {
			String id = request.getParameter("ID");
			StockAlterMove sm = asm.getStockAlterMoveByID(id);
			List<StockAlterMoveDetail> spils = asmd.getUsefulStockAlterMoveDetailBySamID(id);
			//处理页面显示
			for(StockAlterMoveDetail detail : spils){
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
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
