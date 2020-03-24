package com.winsafe.drp.action.sales;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProject;
import com.winsafe.drp.dao.Project;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddProjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		AppProject ap = new AppProject();

		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", "databases.add.success");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Project p = new Project();
			p.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("hap", 0,
					"")));
			p.setCid(cid);
			p.setPcontent(Integer.valueOf(request.getParameter("pcontent")));
			p.setPstatus(Integer.valueOf(request.getParameter("pstatus")));
			p.setAmount(Double.valueOf(request.getParameter("amount")));
			String pbegin = request.getParameter("pbegin").replace('-', '/');
			if (pbegin != null && !pbegin.equals("")) {
				p.setPbegin(new Date(pbegin));
			}
			String pend = request.getParameter("pend").replace('-', '/');
			if (pend != null && !pend.equals("")) {
				p.setPend(new Date(pend));
			}
			p.setMemo(request.getParameter("memo"));
//			p.setMakeid(userid);
			p.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));

			 ap.addProject(p);
			
//			DBUserLog.addUserLog(userid, "新增销售机会,编号：" + p.getId());

			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		}catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
