package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.ValidateLinkman;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdLinkManExecuteAction extends BaseAction {
	Logger logger = Logger.getLogger(UpdLinkManExecuteAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidateLinkman linkman = (ValidateLinkman) form;
		Integer id = Integer.valueOf(request.getParameter("id"));
		
		initdata(request);

		AppLinkMan appLinkman = new AppLinkMan();
		Linkman lm = appLinkman.getLinkmanByID(id);
		Linkman oldlm = (Linkman) BeanUtils.cloneBean(lm);
		try {
			lm.setName(linkman.getName());
			lm.setSex(Integer.valueOf(linkman.getSex()));
			lm.setIdcard(linkman.getIdcard());
			lm.setBirthday(DateUtil.StringToDate(linkman.getBirthday()));
			lm.setDepartment(linkman.getDepartment());
			lm.setDuty(linkman.getDuty());
			lm.setOfficetel(linkman.getOfficetel());
			lm.setMobile(linkman.getMobile());
			lm.setHometel(linkman.getHometel());
			lm.setEmail(linkman.getEmail());
			lm.setQq(linkman.getQq());
			lm.setMsn(linkman.getMsn());
			lm.setAddr(linkman.getAddr());
			lm.setIsmain(Integer.valueOf(linkman.getIsmain()));

			appLinkman.updLinkman(lm);

			
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,5,"会员/积分管理>>修改客户联系人,编号："+id,lm,lm); 
			//con.commit();
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			////
			//  ConnectionEntityManager.close(con);
		}

		return mapping.getInputForward();
	}

}
