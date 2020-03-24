package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.StockCheck;

public class StockCheckDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("SCID");
		super.initdata(request);try{
			AppStockCheck asc = new AppStockCheck();
			StockCheck sc = asc.getStockCheckByID(id);
			
			
			AppStockCheckDetail asmd = new AppStockCheckDetail();
			List spils = asmd.getStockCheckDetailBySmID(id);
			
			request.setAttribute("als", spils);
			request.setAttribute("scf", sc);
			
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
