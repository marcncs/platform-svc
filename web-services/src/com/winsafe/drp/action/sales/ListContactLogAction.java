package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.ContactLogForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;

public class ListContactLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppContactLog appContactLog = new AppContactLog();
		int pagesize = 10;
		Integer objsorttype = RequestTool.getInt(request, "objsort");
		initdata(request);
		try {
			

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ContactLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"NextContact"); 
			String blur = DbUtil.getBlur(map, tmpMap, "ContactContent"); 
			whereSql = whereSql + timeCondition + blur + " c.makeid = "
					+ userid + " and  c.objsort = " + objsorttype + " and"; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			List usList = appContactLog.searchContactLog(request, pagesize,
					whereSql);
			ArrayList contactList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				ContactLogForm clf = new ContactLogForm();
				Object[] ob = (Object[]) usList.get(t);
				clf.setId(Integer.valueOf(ob[0].toString()));
				clf.setObjsort(Integer.valueOf(ob[1].toString()));
				clf.setCidname(ob[2].toString());
				clf.setContactdate(String.valueOf(ob[3]).substring(0, 10));
				clf.setContactmode(Integer.valueOf(ob[4].toString()));
				clf.setContactproperty(Integer.valueOf(ob[5].toString()));
				clf.setLinkman(String.valueOf(ob[6]));
				clf.setNextcontact(String.valueOf(ob[7]).substring(0, 10));
				clf.setMakeorganid(ob[8].toString());
				clf.setUserid(Integer.valueOf(ob[9].toString()));
				contactList.add(clf);

			}
			request.setAttribute("usList", contactList);
			request.setAttribute("objsort", objsorttype);
			DBUserLog.addUserLog(userid, 5, "会员/积分管理 >>列表商务联系");
			return mapping.findForward("contactList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
