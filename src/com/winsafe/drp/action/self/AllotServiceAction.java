package com.winsafe.drp.action.self;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.AppServiceExecute;
import com.winsafe.drp.dao.ServiceExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AllotServiceAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String strsaid = request.getParameter("said");
		Integer said = Integer.valueOf(strsaid);
		String strspeed = request.getParameter("speedstr");
		int count = Integer.parseInt(request.getParameter("uscount"));

		try {
			AppServiceExecute atpe = new AppServiceExecute();
			atpe.delServiceExecuteBySAID(said);

			StringTokenizer st = new StringTokenizer(strspeed, ",");
			AppServiceAgreement asa = new AppServiceAgreement();

			for (int p = 0; p < count; p++) {
				Integer newUserid = Integer.valueOf(st.nextToken().trim());
				
				if(!userid.equals(newUserid)){
					ServiceExecute tpe = new ServiceExecute();
					tpe.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"service_execute", 0, "")));
					tpe.setSaid(said);
					tpe.setUserid(newUserid);
					tpe.setIsaffirm(Integer.valueOf(0));
					atpe.addExecute(tpe);
				}
				
			}
			
			ServiceExecute tpe = new ServiceExecute();
			tpe.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"service_execute", 0, "")));
			tpe.setSaid(said);
			tpe.setUserid(userid);
			tpe.setIsaffirm(Integer.valueOf(0));
			atpe.addExecute(tpe);
			
			
			asa.updIsAllot(said);
			request.setAttribute("result", "databases.add.success");

			
			DBUserLog.addUserLog(userid,0, "分配任务,编号："+said);

			return mapping.findForward("allot");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
