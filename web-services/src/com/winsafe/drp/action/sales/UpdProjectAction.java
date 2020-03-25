package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProject;
import com.winsafe.drp.dao.Project;
import com.winsafe.hbm.util.DateUtil;

public class UpdProjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// String cid = (String) request.getSession().getAttribute("cid");
		
//		Long userid = users.getUserid();

		AppProject ah = new AppProject();
		// Session session = null;
		// //Connection con = null;
		try {
			// 
			// //con = session.connection();
			// //con.setAutoCommit(false);

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", "databases.upd.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Project h = new Project();
			h.setId(Long.valueOf(request.getParameter("id")));
			h.setCid(cid);
			h.setPcontent(Integer.valueOf(request.getParameter("pcontent")));
			h.setPstatus(Integer.valueOf(request.getParameter("pstatus")));
			h.setAmount(Double.valueOf(request.getParameter("amount")));
			String pbegin = request.getParameter("pbegin").replace('-', '/');

			String pend = request.getParameter("pend").replace('-', '/');

			h.setMemo(request.getParameter("memo"));
//			h.setMakeid(userid);
			h.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			String result = "";
//			String upd = ah.updProject(h, pbegin, pend);
//			if (upd.equals("s")) {
//				result = "databases.upd.success";
//			} else {
//				result = "databases.upd.fail";
//			}
//
//			DBUserLog.addUserLog(userid, "修改项目,编号：" + h.getId());
			// //con.commit();
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 
			// ConnectionEntityManager.close(con);

		}

		return mapping.getInputForward();
	}

}
