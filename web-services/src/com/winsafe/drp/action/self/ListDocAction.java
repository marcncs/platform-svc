package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDoc;
import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DocForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListDocAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);

		int pagesize = 5;
		String pbsid = request.getParameter("PbsID");
		String id = request.getParameter("ID");
		String keyword = request.getParameter("KeyWord");
		AppUsers au = new AppUsers();
		try {
			//String userCondition = " exists ( select id from DocSortVisit as v where pb.sortid=v.dsid and v.userid="+userid+")";
			String userCondition = " 1=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Doc" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();
			if (pbsid != null && pbsid.length() > 0) {
				buf.append("(");
				buf.append("sortid = '" + pbsid + "')");
				buf.append(" and ");
				whereSql = whereSql + buf; 
			}
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, " Describe","Realpathname");
			whereSql = whereSql + timeCondition + blur + userCondition;

			whereSql = DbUtil.getWhereSql(whereSql); 

			AppDocSort apbs = new AppDocSort();
			AppDoc apb = new AppDoc();
			List pbsls = apb.searchDoc(request, pagesize, whereSql);
			ArrayList alpbsls = new ArrayList();
			for (int p = 0; p < pbsls.size(); p++) {
				DocForm pbf = new DocForm();
				Object[] o = (Object[]) pbsls.get(p);
				pbf.setId(Integer.valueOf(o[0].toString()));
				pbf.setSortname(apbs.getDocSortByID(
						Integer.valueOf(o[1].toString())).getSortname());
				pbf.setRealpathname(o[2].toString());
				pbf.setDescribe(o[3].toString());
				pbf.setMakedate(o[4].toString());
				pbf.setMakeidname(au.getUsersByid(
						Integer.valueOf(o[5].toString())).getRealname());
				alpbsls.add(pbf);
			}

			request.setAttribute("keyword", keyword);
			request.setAttribute("id", id);
			request.setAttribute("alpbsls", alpbsls);

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表文档");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
