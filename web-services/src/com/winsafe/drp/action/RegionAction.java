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

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.Region;

public class RegionAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String parentid = request.getParameter("id");
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		String sortname = "";
		try {
			sb.append("<tree>");
			AppRegion aps = new AppRegion();
			List list = aps.getChild(parentid);
			for (int i = 0; i < list.size(); i++) {
				Region ps = (Region) list.get(i);
				if (ps.getRegioncode().length() == (parentid.length() + 2)) {
					sortname = ps.getSortname();
					if (hasChild(ps.getRegioncode(), list)) {
						sb.append("<tree text=\"").append(sortname).append("\" action=\"javascript:show('").append(
								ps.getRegioncode()).append("')\" src=\"../regionAction.do?id=").append(ps.getRegioncode())
								.append("\"></tree>");
					} else {
						sb.append("<tree text=\"").append(sortname).append("\" action=\"javascript:show('").append(
								ps.getRegioncode()).append("')\" />");
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
	public boolean hasChild(String code, List<Region> list) {
		int childlength = code.length() + 2;
		for (Region ps : list) {
			if (ps.getRegioncode().length() == childlength) {
				if (code.equals(ps.getRegioncode().subSequence(0, ps.getRegioncode().length() - 2))) {
					return true;
				}
			}
		}
		return false;
	}

}

