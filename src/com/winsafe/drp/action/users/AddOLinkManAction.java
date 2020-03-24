package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.MakeCode;

public class AddOLinkManAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppOlinkMan appLinkMan = new AppOlinkMan();

		try {

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Olinkman ol = new Olinkman();
			BeanCopy bc = new BeanCopy();
			bc.copy(ol, request);
			ol.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("olinkman", 0, "")));

			appLinkMan.addOlinkman(ol);

//			AppCAddr ca = new AppCAddr();
//			if(linkman.getAddr()!=null&&!linkman.getAddr().equals("")){
//			Long caid= Long.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",0,""));
//			ca.addCAddrNoExists(caid, linkman.getCid(), StringFilters.CommaToDot(linkman.getAddr()));
//			}
			
			request.setAttribute("gourl", "../sales/toAddLinkManAction.do");
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "新增机构联系人"+ol.getId());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
