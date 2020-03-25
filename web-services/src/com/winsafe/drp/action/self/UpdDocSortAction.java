package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDocSort;
import com.winsafe.drp.dao.DocSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdDocSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strid = request.getParameter("id");
		Integer id = Integer.valueOf(strid);
		String sortname = request.getParameter("sortname");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			AppDocSort apbs = new AppDocSort();
			if(apbs.getDocSortByName(sortname,userid)){
				request.setAttribute("result", "修改失败!该文件类型已经存在!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			
			DocSort ds = apbs.getDocSortByID(id);
			
			DocSort oldds = (DocSort) BeanUtils.cloneBean(ds);
			apbs.updDocSort(id, sortname);
			
			request.setAttribute("result", "databases.upd.success");
	
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改文件柜,编号：" + id, oldds, ds);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
