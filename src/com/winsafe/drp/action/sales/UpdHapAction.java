package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHap;
import com.winsafe.drp.dao.Hap;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdHapAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		initdata(request);

		AppHap ah = new AppHap();
		try {

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Hap h = ah.getHapByID(Integer.valueOf(request.getParameter("id")));
			Hap oldh = (Hap) BeanUtils.cloneBean(h);
			h.setObjsort(Integer.valueOf(request.getParameter("objsort")));
			h.setCid(cid);
			h.setCname(request.getParameter("cname"));
			h.setHaptitle(request.getParameter("haptitle"));
			h.setHapcontent(Integer.valueOf(request.getParameter("hapcontent")));
			h.setHapkind(Integer.valueOf(request.getParameter("hapkind")));
			h.setHapstatus(Integer.valueOf(request.getParameter("hapstatus")));
			h.setHapsrc(Integer.valueOf(request.getParameter("hapsrc")));
			h.setIntend(Double.valueOf(request.getParameter("intend")));
			h.setIntenddate(DateUtil.StringToDate(request.getParameter("intenddate")));
			h.setHapbegin(DateUtil.StringToDate(request.getParameter("hapbegin")));
			h.setHapend(DateUtil.StringToDate(request.getParameter("hapend")));
			h.setMemo(request.getParameter("memo"));
			h.setMakeorganid(users.getMakeorganid());
			h.setMakedeptid(users.getMakedeptid());
			h.setMakeid(userid);
			h.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			ah.updHap(h);
			
			DBUserLog.addUserLog(userid,5,"会员/积分管理>>修改销售机会,编号：" + h.getId(),oldh,h);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return mapping.getInputForward();
	}

}
