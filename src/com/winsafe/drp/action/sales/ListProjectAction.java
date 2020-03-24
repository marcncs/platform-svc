package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

//import java.text.SimpleDateFormat;

public class ListProjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		request.getSession().setAttribute("cid", strCid);
		AppCustomer ac = new AppCustomer();
		AppUsers au = new AppUsers();

		int pagesize = 5;		
		try {
			
//			Long userid = usersBean.getUserid();
			String Condition = " p.cid='" + cid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Project" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			// "RegistDate"); 
			// String blur = DbUtil.getBlur(map, tmpMap, "RegieName");
			// 
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, "Project as p", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

//			List usList = AppProject.getProject(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
//			for (int t = 0; t < usList.size(); t++) {
//				ProjectForm hf = new ProjectForm();
//				// customer=(Customer)usList.get(t);
//				Object[] ob = (Object[]) usList.get(t);
//				hf.setId(Long.valueOf(ob[0].toString()));
//				hf.setCidname(ac.getCustomer(String.valueOf(ob[1])).getCname());
//				hf.setPcontentname(Internation.getStringByKeyPositionDB(
//						"HapContent", Integer.valueOf(ob[2].toString())));
//				hf.setPstatusname(Internation.getStringByKeyPositionDB(
//						"PStatus", Integer.valueOf(ob[3].toString())));
//				hf.setAmount(Double.valueOf(ob[4].toString()));
//				hf.setPbegin(String.valueOf(ob[5]).substring(0, 10));
//				hf.setPend(String.valueOf(ob[6]).substring(0, 10));
//				hf.setMakeidname(au.getUsersByid(Long.valueOf(ob[8].toString()))
//						.getRealname());
//
//				hList.add(hf);
//			}
			String pcontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "PContent", true);
			String pstatusselect = Internation.getSelectTagByKeyAllDB(
					"PStatus", "PStatus", true);

			request.setAttribute("pcontentselect", pcontentselect);
			request.setAttribute("pstatusselect", pstatusselect);

			request.setAttribute("hList", hList);

//			DBUserLog.addUserLog(userid, "列表项目");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
