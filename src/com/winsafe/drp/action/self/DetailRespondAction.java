package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Respond;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:34:02
 * www.winsafe.cn
 */
public class DetailRespondAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Integer id = RequestTool.getInt(request, "ID");
			AppRespond appRespond = new AppRespond();
			Respond respond =appRespond.findByID(id);
			request.setAttribute("respond", respond);
			return mapping.findForward("detail");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
