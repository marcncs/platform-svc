package com.winsafe.drp.action.self;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSortVisit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class VisitDocSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		try {
			Integer dsid = Integer.valueOf(request.getParameter("dsid"));
			String strspeed = request.getParameter("speedstr");
			int count = Integer.parseInt(request.getParameter("uscount"));
			AppDocSort app = new AppDocSort();
			
			app.delDocSortVisitByDsid(dsid);

			StringTokenizer st = new StringTokenizer(strspeed, ",");

			for (int p = 0; p < count; p++) {
				DocSortVisit wv = new DocSortVisit();
				wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"doc_sort_visit", 0, "")));
				wv.setDsid(dsid);
				wv.setUserid(Integer.valueOf(st.nextToken().trim()));
				app.addDocSortVisit(wv);

			}

			request.setAttribute("result", "databases.add.success");

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>许可文件类型，仓库编号：" + dsid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
