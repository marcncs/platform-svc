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

import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.server.OrganService;

/**
 * 处理机构树
 *@author jelli
 *@company www.winsafe.cn
 * 2009-6-6
 */
public class OrganStrutsAction extends Action {

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
			OrganService organs = new OrganService();
			List list = organs.getTreeByCate(parentid);
			for (int i = 0; i < list.size(); i++) {
				Organ organ = (Organ) list.get(i);
					sortname = organ.getOrganname();
					
					if (hasChild(organ.getId())) {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
										organ.getId()).append(
								"')\" src=\"../organStrutsAction.do?id=")
								.append(organ.getId())
								.append("\"></tree>");
					} else {
						sb.append("<tree text=\"").append(sortname).append(
								"\" action=\"javascript:show('").append(
										organ.getId()).append("')\" />");
					}
			}
			sb.append("</tree>");
			out.print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

		return null;
	}

	public boolean hasChild(String code) throws Exception {
		OrganService organs = new OrganService();
		return organs.getTreeByCate(code).isEmpty()?false:true;
	}

}
