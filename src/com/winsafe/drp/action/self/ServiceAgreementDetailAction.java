package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.AppServiceExecute;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ServiceAgreement;
import com.winsafe.drp.dao.ServiceAgreementForm;
import com.winsafe.drp.dao.ServiceExecuteForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.Internation;

public class ServiceAgreementDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		// Integer cid = Integer.valueOf((String)
		// request.getSession().getAttribute("cid"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppUsers au = new AppUsers();
		AppCustomer ac = new AppCustomer();
		ServiceAgreement p = new ServiceAgreement();

		AppServiceAgreement asa = new AppServiceAgreement();

		try {
			HibernateUtil.currentSession();

			p = asa.getServiceAgreementByID(id);
			ServiceAgreementForm pf = new ServiceAgreementForm();
			pf.setId(p.getId());
			pf.setCname(p.getCname());
			pf.setLinkman(p.getLinkman());
			pf.setTel(p.getTel());
			pf.setScontentname(Internation.getStringByKeyPositionDB(
					"HapContent", p.getScontent()));
			pf.setSstatusname(Internation.getStringByKeyPositionDB("SStatus", p
					.getSstatus()));
			pf.setRankname(Internation.getStringByKeyPositionDB("Rank", p
					.getRank()));
			pf.setSfee(p.getSfee());
			pf.setSdate(String.valueOf(p.getSdate()));
			pf.setQuestion(p.getQuestion());
			pf.setMemo(p.getMemo());
			pf.setIsallotname(Internation.getStringByKeyPosition("YesOrNo",
					request, p.getIsallot(), "global.sys.SystemResource"));
			pf.setMakeid(p.getMakeid());
			pf.setMakedate(String.valueOf(p.getMakedate()));
			pf.setMakeorganid(p.getMakeorganid());
			pf.setMakedeptid(p.getMakedeptid());
			AppServiceExecute asla = new AppServiceExecute();
			asla.updIsAffirmServiceExecute(id, userid);
			List slrv = asla.getExecuteIDByServiceID(id);
			ArrayList rvls = new ArrayList();
			for (int r = 0; r < slrv.size(); r++) {
				ServiceExecuteForm slaf = new ServiceExecuteForm();
				Object[] or = (Object[]) slrv.get(r);
				slaf.setIsaffirmname(Internation.getStringByKeyPosition(
						"YesOrNo", request, Integer.parseInt(or[3].toString()),
						"global.sys.SystemResource"));
				slaf.setUseridname(au.getUsersByid(
						Integer.valueOf(or[2].toString())).getRealname());
				rvls.add(slaf);
			}

			request.setAttribute("question", p.getQuestion().replace("\n", "<br>"));
			request.setAttribute("rvls", rvls);
			request.setAttribute("pf", pf);

			return mapping.findForward("detail");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
