package com.winsafe.drp.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AfficheBrowse;
import com.winsafe.drp.dao.AppAfficheBrowse;
import com.winsafe.drp.dao.AppServiceExecute;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.AppWorkReportApprove;
import com.winsafe.drp.dao.ServiceExecute;
import com.winsafe.drp.dao.TaskExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WorkReportApprove;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : ivan
 * @version : 2009-8-12 下午04:10:27 www.winsafe.cn
 */
public class SaveReferInfoAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String ReferType = (String) request.getSession().getAttribute(
					"ReferType");
			Integer id = Integer.valueOf(request.getSession().getAttribute("ID").toString());
			String speedstr = request.getParameter("speedstr");
			String[] userlist = speedstr.split(",");

			if (ReferType.equalsIgnoreCase("WorkReport")) {
				saveWorkReport(userid, userlist, id);
			} else if (ReferType.equalsIgnoreCase("ServiceAgreement")) {
				saveServiceAgreement(userid, userlist, id);
			} else if (ReferType.equalsIgnoreCase("Task")) {
				saveTaskExecute(userid, userlist, id);
			} else if (ReferType.equalsIgnoreCase("Affiche")) {
				saveAffiche(userid, userlist, id);
			}
			request.setAttribute("result", "databases.refer.success");
			
			request.getSession().setAttribute("ReferType", null);
			request.getSession().setAttribute("ID", null);
			request.getSession().setAttribute("ID", null);
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void saveAffiche(Integer userid, String[] userlist, Integer id)
			throws NumberFormatException, Exception {
		AppAfficheBrowse appab = new AppAfficheBrowse();
		///
		appab.delAfficheBrowse(id);
		AfficheBrowse ab = null;
		for (int i = 0; i < userlist.length; i++) {
			if (!userlist[i].toString().equals(userid.toString())) {
				ab = new AfficheBrowse();
				ab.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"affiche_browse", 0, "")));
				ab.setAfficheid(id);
				ab.setUserid(Integer.valueOf(userlist[i]));
				ab.setIsbrowse(0);
				appab.addAfficheBrowse(ab);
			}

		}
		
		ab = new AfficheBrowse();
		ab.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"affiche_browse", 0, "")));
		ab.setAfficheid(id);
		ab.setUserid(userid);
		ab.setIsbrowse(0);
		appab.addAfficheBrowse(ab);


	}

	private void saveServiceAgreement(Integer userid, String[] userlist,
			Integer id) throws NumberFormatException, Exception {
		AppServiceExecute atpe = new AppServiceExecute();
		atpe.delServiceExecuteBySAID(id);
		ServiceExecute tpe = null;
		for (int i = 0; i < userlist.length; i++) {
			if (!userlist[i].toString().equals(userid.toString())) {
				 tpe = new ServiceExecute();
				tpe.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"service_execute", 0, "")));
				tpe.setSaid(id);
				tpe.setUserid(Integer.valueOf(userlist[i]));
				tpe.setIsaffirm(0);
				atpe.addExecute(tpe);
			}
		}
		 tpe = new ServiceExecute();
		tpe.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"service_execute", 0, "")));
		tpe.setSaid(id);
		tpe.setUserid(userid);
		tpe.setIsaffirm(Integer.valueOf(1));
		atpe.addExecute(tpe);
		

	}

	public void saveTaskExecute(Integer userid, String[] userlist, Integer id)
			throws Exception {
		AppTaskExecute appTaskExecute = new AppTaskExecute();
		appTaskExecute.delTaskPlanExecuteByTPID(id);
		TaskExecute taskExecute = null;
		for (int i = 0; i < userlist.length; i++) {
			if (!userlist[i].toString().equals(userid.toString())) {
				taskExecute = new TaskExecute();
				taskExecute.setId(Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("task_execute", 0, "")));
				taskExecute.setTpid(id);
				taskExecute.setIsaffirm(0);
				taskExecute.setUserid(Integer.valueOf(userlist[i]));
				taskExecute.setIsover(0);
				taskExecute.setOverdate(null);
				appTaskExecute.addExecute(taskExecute);

			}
		}
		taskExecute = new TaskExecute();
		taskExecute.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"task_execute", 0, "")));
		taskExecute.setTpid(id);
		taskExecute.setIsaffirm(1);
		taskExecute.setUserid(userid);
		taskExecute.setIsover(0);
		taskExecute.setOverdate(null);
		appTaskExecute.addExecute(taskExecute);
		

		
	}

	public void saveWorkReport(Integer userid, String[] userlist, Integer id)
			throws Exception {

		AppWorkReportApprove appwra = new AppWorkReportApprove();
		WorkReportApprove wra = null;
		appwra.delWorkReportApproveByReportID(id);
		for (int i = 0; i < userlist.length; i++) {
			if (!userlist[i].toString().equals(userid.toString())) {
				wra = new WorkReportApprove();
				wra.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"work_report_approve", 0, "")));
				wra.setReportid(id);
				wra.setApproveid(Integer.valueOf(userlist[i].toString()));
				wra.setApprove(0);
				appwra.addWorkReportApprove(wra);
			}
		}
		
		//
		wra = new WorkReportApprove();
		wra.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"work_report_approve", 0, "")));
		wra.setReportid(id);
		wra.setApproveid(userid);
		wra.setApprove(1);
		appwra.addWorkReportApprove(wra);

	}

}
