package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSort;
import com.winsafe.drp.dao.DocSortVisit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddDocSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sortname = request.getParameter("sortname");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		
		
		try {
			AppDocSort apbs = new AppDocSort();
			
			if(apbs.getDocSortByName(sortname,userid)){
				request.setAttribute("result", "添加失败!该文件类型已经存在!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			DocSort pbs = new DocSort();
			Integer dsid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"doc_sort", 0, ""));
			pbs.setId(dsid);
			pbs.setSortname(sortname);
			pbs.setUserid(userid);

			apbs.addDocSort(pbs);
			//DocSortVisit docSortVisit = new DocSortVisit();
			//docSortVisit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
			//		"doc_sort_visit", 0, "")));
			//docSortVisit.setDsid(dsid);
			//docSortVisit.setUserid(userid);
			
			//apbs.addDocSortVisit(docSortVisit);
			

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增文件柜,编号： " + pbs.getId());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
