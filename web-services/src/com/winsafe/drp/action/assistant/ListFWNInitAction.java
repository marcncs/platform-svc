package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.util.Constants;
import com.winsafe.sap.pojo.PrimaryCode;
import common.Logger;

public class ListFWNInitAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListFWNInitAction.class);
	private AppQuery aq = new AppQuery();

	List<PrimaryCode> lpc = new ArrayList<PrimaryCode>();
	String primaryCode = "";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		primaryCode = request.getParameter("KeyWord");
		int query_mode = Constants.CASE_QUERY_MODE_WEB;
		boolean showResult = true;
		try {
			if (primaryCode != null) {
				FwIdcodeService fis = new FwIdcodeService();
				String ipString = aq.getIpAddr(request);
				Map<String, Object> queryMap = fis.fwIdcodeQuery(primaryCode.trim().toUpperCase(),query_mode,showResult,request,ipString);
				if (queryMap.get("cartonCode") != null) {
					request.setAttribute("cc", queryMap.get("cartonCode"));
				}
				if (queryMap.get("printJob") != null) {
					request.setAttribute("pj", queryMap.get("printJob"));
				}
				if (queryMap.get("product") != null) {
					request.setAttribute("pd", queryMap.get("product"));
				}
				if (queryMap.get("primaryCode") != null) {
					request.setAttribute("p", queryMap.get("primaryCode"));
				}
				if (queryMap.get("prompt") != null) {
					request.setAttribute("prompt", queryMap.get("prompt"));
				}
				if (queryMap.get("firstTime") != null) {
					request.setAttribute("firstTime", queryMap.get("firstTime"));
				}
				if (queryMap.get("query") != null) {
					request.setAttribute("q", queryMap.get("query"));
				}
			}
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
		} 
		return new ActionForward(mapping.getInput());
	}
}
