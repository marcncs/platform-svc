package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.OrganScan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdOrganScanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			AppOrganScan ad = new AppOrganScan();
			OrganScan os = ad.getOrganScanByID(id);
			OrganScan oldOrganScan = (OrganScan)BeanUtils.cloneBean(os);
			os.setIsscan(RequestTool.getInt(request, "isscan"));
			ad.updOrganScan(os);

			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11,"机构设置>>修改入库扫描设置,编号:"+id, oldOrganScan, os);
			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
				request.setAttribute("result", "databases.upd.fail");
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
