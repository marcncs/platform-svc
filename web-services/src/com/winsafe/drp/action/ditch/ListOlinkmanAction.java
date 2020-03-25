package com.winsafe.drp.action.ditch;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.hbm.util.DbUtil;

public class ListOlinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String oid = request.getParameter("OID");
		AppOlinkMan al = new AppOlinkMan(); 

		super.initdata(request);
		try{
			String[] tablename = { "Olinkman" };
			String whereSql = getWhereSql2(tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap,"ID", "Name", "OfficeTel", "Mobile"); 
			whereSql = whereSql + blur + timeCondition + " cid='" + oid+ "' and ";
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			List usList = al.searchOlinkman(request, whereSql);
			request.setAttribute("usList", usList);
			request.setAttribute("OID", oid);

			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
