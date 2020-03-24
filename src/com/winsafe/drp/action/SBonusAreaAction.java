package com.winsafe.drp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.keyretailer.dao.AppSBonusArea;

/**
 *@author jelli
 *@company www.winsafe.cn
 * 2009-6-6
 */
public class SBonusAreaAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String parentid = request.getParameter("id");
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		String sortname = "";
		try {
			sb.append("<tree>");
			AppSBonusArea aps = new AppSBonusArea();
			List list = aps.getChild(parentid);
			for (int i = 0; i < list.size(); i++) {
				RegionItemInfo ps = (RegionItemInfo) list.get(i);
//				if (ps.getStructcode().length() == (parentid.length() + 2)) {					
					sortname = ps.getName();
					if (aps.hasChild(ps.getId())) {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
								ps.getId()).append(
								"')\" src=\"../sBonusAreaAction.do?id=")
								.append(ps.getId())
								.append("\"></tree>");
					} else {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
								ps.getId()).append("')\" />");
					}
//				}
			}
			sb.append("</tree>");
			out.print(sb.toString());
		} finally {
			out.close();
		}

		return null;
	}

}
