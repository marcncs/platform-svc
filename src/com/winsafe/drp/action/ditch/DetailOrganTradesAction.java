package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:32:35 www.winsafe.cn
 */
public class DetailOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			String isshow = request.getParameter("isshow");
			AppOrganTrades appOt = new AppOrganTrades();
			OrganTrades ot = appOt.getOrganTradesByID(id);
			AppOrganTradesDetail appOtd = new AppOrganTradesDetail();
			List<OrganTradesDetail> list = appOtd
					.getOrganTradesDetailByotid(id);
			request.setAttribute("ot", ot);
			request.setAttribute("list", list);
			request.setAttribute("isshow", isshow);
			return mapping.findForward("detail");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
