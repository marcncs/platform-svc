package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.winsafe.drp.dao.AppPact;
import com.winsafe.drp.dao.PactForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPactAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		AppPact ap = new AppPact();
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		int pagesize = 5;

		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
			String[] tablename = { "Pact" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			// String blur = DbUtil.getBlur(map, tmpMap, "Name"); 
			whereSql = whereSql + " p.cid='" + cid + "' and p.userid=" + userid
					+ " and"; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "Pact as p",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			List usList = ap.getPact(pagesize, whereSql, tmpPgInfo);
			ArrayList packkist = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				PactForm pa = new PactForm();
				Object[] ob = (Object[]) usList.get(t);
				pa.setId(Integer.valueOf(ob[0].toString()));
				pa.setPactcode(ob[1].toString());
				pa.setPacttype(
						Integer.valueOf(ob[2].toString()));
				pa.setCdeputy(ob[4].toString());
				pa.setPdeputy(ob[7].toString());
				pa.setSigndate((Date)ob[5]);
				pa.setSignaddr(ob[8].toString());

				packkist.add(pa);

			}
			request.getSession().setAttribute("cid", strCid);
			request.setAttribute("usList", packkist);

			DBUserLog.addUserLog(userid, 5, "合同>>列表合同");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
