package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOlinkmanAction extends BaseAction {

	private AppOlinkMan al = new AppOlinkMan(); 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		super.initdata(request);
		String oid = request.getParameter("OID");
//		if (oid == null) {
//			oid = (String) request.getSession().getAttribute("sjoid");
//		}
//		request.getSession().setAttribute("sjoid", oid);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);

		try {
			String[] tablename = { "Olinkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "Name", "OfficeTel", "Mobile"); 
			whereSql = whereSql + blur + timeCondition + " cid='" + oid+ "' and ";
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			
			List usList = al.searchOlinkman(request, whereSql);
			
			request.setAttribute("usList", usList);
			request.setAttribute("OID", oid);

			DBUserLog.addUserLog(userid, "系统管理", "机构资料联系人" + oid);
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
