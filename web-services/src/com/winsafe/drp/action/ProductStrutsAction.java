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

/**
 *@author jelli
 *@company www.winsafe.cn
 * 2009-6-6
 */
public class ProductStrutsAction extends Action {

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
			AppProductStruct aps = new AppProductStruct();
			List list = aps.getChild(parentid);
			for (int i = 0; i < list.size(); i++) {
				ProductStruct ps = (ProductStruct) list.get(i);
				if (ps.getStructcode().length() == (parentid.length() + 2)) {					
					sortname = ps.getSortname();
					if (hasChild(ps.getStructcode(), list)) {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
								ps.getStructcode()).append(
								"')\" src=\"../productStrutsAction.do?id=")
								.append(ps.getStructcode())
								.append("\"></tree>");
					} else {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
								ps.getStructcode()).append("')\" />");
					}
				}
			}
			sb.append("</tree>");
			out.print(sb.toString());
		} finally {
			out.close();
		}

		return null;
	}

	public boolean hasChild(String code, List<ProductStruct> list) {
		int childlength = code.length() + 2;
		for (ProductStruct ps : list) {
			if (ps.getStructcode().length() == childlength) {
				if (code.equals(ps.getStructcode().subSequence(0,
						ps.getStructcode().length() - 2))) {
					return true;
				}
			}
		}
		return false;
	}

}
