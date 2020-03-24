package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.IntegralRule;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.BaseResourceService;
import com.winsafe.drp.util.DBUserLog;

public class UpdIntegralRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			AppIntegralRule air = new AppIntegralRule();
			IntegralRule w = air.getIntegralRuleByID(id);
			IntegralRule oldw = (IntegralRule)BeanUtils.cloneBean(w);
			
			Integer rkeyid = Integer.valueOf(request.getParameter("rkeyid"));
			BaseResourceService appBr = new BaseResourceService();
			
			BaseResource bResource =appBr.getBaseResourceValue("RKey",rkeyid);
			bResource.setTagsubvalue(request.getParameter("rkey"));

			appBr.updBaseResource(bResource);
			
			w.setRkey(rkeyid);
			
			w.setIrate(Double.valueOf(request.getParameter("irate")));
			air.updIntegralRule(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>修改积分规则,编号:"+id,oldw,w);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
