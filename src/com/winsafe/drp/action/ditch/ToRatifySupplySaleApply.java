package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;

/**
 * @author : jerry
 * @version : 2009-8-31 下午04:32:28 www.winsafe.cn
 */
public class ToRatifySupplySaleApply extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			SupplySaleApply ama = appAMA.getSupplySaleApplyByID(id);
			if (ama.getIsaudit() == 0) {
				String result = "该单据未复核!不能进行此操作!";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			AppSupplySaleApplyDetail appAMAD = new AppSupplySaleApplyDetail();
			List listAmad = appAMAD.getSupplySaleAplyBySSID(id);

			request.setAttribute("list", listAmad);

			request.setAttribute("ama", ama);
			return mapping.findForward("toratify");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
