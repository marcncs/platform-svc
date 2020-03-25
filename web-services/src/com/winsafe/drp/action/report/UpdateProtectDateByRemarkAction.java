package com.winsafe.drp.action.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductStockpile;

public class UpdateProtectDateByRemarkAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String id = request.getParameter("id");
			String remark = request.getParameter("remark");
			AppProductStockpile aps = new AppProductStockpile();
			ProductStockpile ps = aps.getProductStockpileByID(Long.valueOf(id));
			if(null != ps){
				ps.setRemark(remark);
				aps.updProductStockpile(ps);
				request.setAttribute("result", "databases.add.success");
			}else{
				request.setAttribute("result", "databases.add.fail");
			}
			return mapping.findForward("updResult");
	}
}
