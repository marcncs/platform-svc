package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganScanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String oid = request.getParameter("OID");
		if (oid == null) {
			oid = (String) request.getSession().getAttribute("sjoid");
		}
		request.getSession().setAttribute("sjoid", oid);
		AppOrganScan al = new AppOrganScan(); 
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);

		try {
			String condition = " os.organid='" + oid+ "' and os.scb=sc.id ";
			String[] tablename = {"OrganScan" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "Name", "OfficeTel", "Mobile"); 
			whereSql = whereSql + blur + timeCondition + condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			
			List usList = al.searchOrganScan(request, whereSql);
			
			request.setAttribute("oslist", usList);

			//DBUserLog.addUserLog(userid, "列表客户联系人");
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
