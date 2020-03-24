package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdSuitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		initdata(request);
		AppSuit ah = new AppSuit();

		try {

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Suit s = ah.getSuitByID(request.getParameter("id"));
			Suit olds = (Suit) BeanUtils.cloneBean(s);
			s.setCid(cid);
			s.setCname(request.getParameter("cname"));
			s.setSuitcontent(Integer.valueOf(request
					.getParameter("suitcontent")));
			s.setSuitway(Integer.valueOf(request.getParameter("suitway")));
			s.setSuittools(request.getParameter("suittools"));
			s.setSuitstatus(request.getParameter("suitstatus"));
			String suitdate = request.getParameter("suitdate");
			s.setSuitdate(DateUtil.StringToDate(suitdate));
			s.setMemo(request.getParameter("memo"));

			ah.updSuit(s);

			DBUserLog.addUserLog(userid,5,"会员/积分管理>>修改抱怨投诉,编号：" + s.getId(),olds,s);

			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return mapping.getInputForward();
	}

}
