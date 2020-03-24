package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectPlinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		String strpid = request.getParameter("pid");
		if (strpid == null) {
			strpid = (String) request.getSession().getAttribute("pid");
		}
		String pid = strpid;
		request.getSession().setAttribute("pid", strpid);
		super.initdata(request);try{
			String Condition = " pl.pid='" + pid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Plinkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "Name","NCcode");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

//			Object obj[] = (DbUtil.setPager(request, "Plinkman as pl",
//					whereSql, pagesize));
//			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
//			whereSql = (String) obj[1];

			AppPlinkman al = new AppPlinkman();
			List pls = al.getPlinkManByPId(request, pagesize, whereSql);
//			ArrayList sls = new ArrayList();
//
//			for (int i = 0; i < pls.size(); i++) {
//				PlinkmanForm lf = new PlinkmanForm();
//				Plinkman o = (Plinkman) pls.get(i);
//				lf.setId(o.getId());
//				lf.setName(o.getName());
//				lf.setSexname(Internation.getStringByKeyPosition("Sex",
//						request, o.getSex(), "global.sys.SystemResource"));
//				lf.setOfficetel(o.getOfficetel());
//
//				sls.add(lf);
//			}

			request.setAttribute("sls", pls);
			return mapping.findForward("selectlinkman");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
