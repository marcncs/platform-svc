package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdOLinkManAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppOlinkMan appLinkMan = new AppOlinkMan();

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Olinkman ol =appLinkMan.getOlinkmanByID(id);
			Olinkman oldol = (Olinkman)BeanUtils.cloneBean(ol);
			
			ol.setName(RequestTool.getString(request, "name"));
			ol.setSex(RequestTool.getInt(request, "sex"));
			ol.setIdcard(RequestTool.getString(request, "idcard"));
			ol.setBirthday(RequestTool.getDate(request, "birthday"));
			ol.setDepartment(RequestTool.getString(request, "department"));
			ol.setDuty(RequestTool.getString(request, "duty"));
			ol.setOfficetel(RequestTool.getString(request, "officetel"));
			ol.setMobile(RequestTool.getString(request, "mobile"));
			ol.setHometel(RequestTool.getString(request, "hometel"));
			ol.setEmail(RequestTool.getString(request, "email"));
			ol.setQq(RequestTool.getString(request, "qq"));
			ol.setMsn(RequestTool.getString(request, "msn"));
			ol.setAddr(RequestTool.getString(request, "addr"));
			ol.setIsmain(RequestTool.getInt(request, "ismain"));

			appLinkMan.updOlinkman(ol);

//			AppCAddr ca = new AppCAddr();
//			if(linkman.getAddr()!=null&&!linkman.getAddr().equals("")){
//			Long caid= Long.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",0,""));
//			ca.addCAddrNoExists(caid, linkman.getCid(), StringFilters.CommaToDot(linkman.getAddr()));
//			}
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改机构联系人"+ol.getId(), oldol,ol);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
