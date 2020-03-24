package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCAddr;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.ValidateLinkman;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringFilters;

public class AddLinkManAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidateLinkman linkman = (ValidateLinkman) form;
		AppLinkMan appLinkMan = new AppLinkMan();

		try {

			
			initdata(request);

			appLinkMan.addLinkman(linkman, userid);

			AppCAddr ca = new AppCAddr();
			if(linkman.getAddr()!=null&&!linkman.getAddr().equals("")){
			Integer caid= Integer.valueOf(MakeCode.getExcIDByRandomTableName("c_addr",0,""));
			ca.addCAddrNoExists(caid, linkman.getCid(), StringFilters.CommaToDot(linkman.getAddr()));
			}
			
			request.setAttribute("gourl", "../sales/toAddLinkManAction.do");
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,5, "会员/积分管理>>新增联系人,编号："+linkman.getId());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
