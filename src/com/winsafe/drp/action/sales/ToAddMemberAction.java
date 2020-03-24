package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode;

public class ToAddMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			AppUsers au = new AppUsers();
			
			initdata(request);

			String isreceivemsg = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "isreceivemsg", "1", null);

			CountryAreaService aca = new CountryAreaService();
			List list = aca.getProvinceObj();

			

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}

			AppMemberGrade amg = new AppMemberGrade();
			List ls = amg.getEnableMemberGradeByVisit(userid);
			ArrayList amgls = new ArrayList();
			for (int m = 0; m < ls.size(); m++) {
				MemberGrade mg = new MemberGrade();
				Object[] og = (Object[]) ls.get(m);
				mg.setId(Integer.valueOf(og[0].toString()));
				mg.setGradename(String.valueOf(og[1]));
				amgls.add(mg);
			}

			request.setAttribute("tel", request.getParameter("tel"));
			request.setAttribute("officetel", request
							.getParameter("officetel"));
			request.setAttribute("op", request.getParameter("op"));

			request.setAttribute("isreceivemsg", isreceivemsg);

			request.setAttribute("als", als);
			request.setAttribute("cals", list);
			request.setAttribute("amgls", amgls);

			AppMakeConf amc = new AppMakeConf();
			MakeConf mc = amc.getMakeConfByID("customer");
			String cid = "";
			String isread = "";
			if (mc.getRunmode() == 1) {
				cid = MakeCode.getExcIDByRandomTableName("customer", 4, "");
				isread = "readonly";
			}

			request.setAttribute("mc", mc);
			request.setAttribute("cid", cid);
			request.setAttribute("isread", isread);

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
