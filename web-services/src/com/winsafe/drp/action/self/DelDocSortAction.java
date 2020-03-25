package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDoc;
import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-7-27 下午05:30:14 www.winsafe.cn
 */
public class DelDocSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppDocSort appDocSort = new AppDocSort();
			AppDoc appDoc = new AppDoc();

			int count = appDoc.isCount(id);
			if (count > 0) {
				request.setAttribute("result", "文件类型中存在文件!该文件类型不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除文件柜,编号：" + id);
			DocSort ds = appDocSort.getDocSortByID(id);
			if(!ds.getUserid().equals(userid)){
				request.setAttribute("result", "删除失败!您没有权限删除此文件类型!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			appDocSort.delDocSort(id);

			request.setAttribute("result", "databases.del.success");

			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
