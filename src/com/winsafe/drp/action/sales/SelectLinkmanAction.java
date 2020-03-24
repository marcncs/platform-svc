package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectLinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		String strcid = request.getParameter("cid");
		if (strcid == null) {
			strcid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strcid;
		request.getSession().setAttribute("cid", strcid);
		// String keyword = request.getParameter("KeyWord");
		try {
			
			String Condition = " l.cid='" + cid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Linkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getBlur(map, tmpMap, "Name");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Linkman as l", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppLinkMan al = new AppLinkMan();
//			List pls = al.searchLinkmanNew(pagesize, whereSql,
//					tmpPgInfo);
			ArrayList sls = new ArrayList();

//			for (int i = 0; i < pls.size(); i++) {
//				LinkmanForm lf = new LinkmanForm();
//				Linkman lk = (Linkman) pls.get(i);
//				// Object[] o=(Object[])pls.get(i);
//				lf.setId(lk.getId());
//				lf.setName(lk.getName());
//				lf.setSexname(Internation.getStringByKeyPosition("Sex",
//						request, lk.getSex(), "global.sys.SystemResource"));
//				lf.setOfficetel(lk.getOfficetel());
//				lf.setMobile(lk.getMobile());
//				lf.setDepartment(lk.getDepartment());
//				lf.setDuty(lk.getDuty());
//				lf.setAddr(lk.getAddr());
//				lf.setTransit(lk.getTransit());
//				sls.add(lf);
//			}

			request.setAttribute("sls", sls);
			return mapping.findForward("selectlinkman");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
