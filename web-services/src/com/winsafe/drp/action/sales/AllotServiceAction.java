package com.winsafe.drp.action.sales;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.AppServiceExecute;
import com.winsafe.drp.dao.ServiceExecute;

public class AllotServiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strsaid = request.getParameter("said");
		Long said = Long.valueOf(strsaid);
		String strspeed = request.getParameter("speedstr");
		int count = Integer.parseInt(request.getParameter("uscount"));

		try {
			AppServiceExecute atpe = new AppServiceExecute();
//			atpe.delServiceExecuteBySAID(said);

			StringTokenizer st = new StringTokenizer(strspeed, ",");
			AppServiceAgreement asa = new AppServiceAgreement();

			for (int p = 0; p < count; p++) {
				ServiceExecute tpe = new ServiceExecute();
//				tpe.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"service_execute", 0, "")));
//				tpe.setSaid(said);
//				tpe.setUserid(Long.valueOf(st.nextToken().trim()));
				tpe.setIsaffirm(Integer.valueOf(0));
				// paa.setApprovedate(null);

				atpe.addExecute(tpe);
//				asa.updIsAllot(said);
			}
			
			request.setAttribute("result", "databases.add.success");

			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "分配任务");

			return mapping.findForward("allot");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
