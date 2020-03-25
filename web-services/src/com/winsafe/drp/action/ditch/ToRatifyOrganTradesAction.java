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

/**
 * @author : jerry
 * @version : 2009-8-31 下午04:32:28 www.winsafe.cn
 */
public class ToRatifyOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppOrganTrades appAMA = new AppOrganTrades();
			OrganTrades ot = appAMA.getOrganTradesByID(id);
			if (ot.getIsaudit() == 0) {
				request.setAttribute("result", "该单据未复核!不能进行此操作!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			AppOrganTradesDetail appAMAD = new AppOrganTradesDetail();
			List listAmad = appAMAD.getOrganTradesDetailByotid(id);

			request.setAttribute("list", listAmad);

			request.setAttribute("ot", ot);
			return mapping.findForward("toratify");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
