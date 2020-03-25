package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ServiceAgreementForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ReceiptServiceAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppUsers au = new AppUsers();

		try {
			String condition = "sa.id=se.said and se.userid=" + userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql =
			// "select * from task_plan as tp ,task_plan_execute as tpe where "+condition;
			String[] tablename = { "ServiceAgreement", "ServiceExecute" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" SDate");
			whereSql = whereSql + condition + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql);
			Object obj[] = (DbUtil.setPager(request,
					"ServiceAgreement as sa,ServiceExecute as se", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppServiceAgreement atp = new AppServiceAgreement();
			AppCustomer ac = new AppCustomer();
			List rtls = atp.listReceiptService(pagesize, whereSql, tmpPgInfo);
			ArrayList alrt = new ArrayList();

			for (int i = 0; i < rtls.size(); i++) {
				ServiceAgreementForm saf = new ServiceAgreementForm();
				Object[] ob = (Object[]) rtls.get(i);
				saf.setId(Integer.valueOf(ob[0].toString()));
				saf.setCid(ob[1].toString());
				saf.setCname(ac.getCustomer(ob[2].toString()).getCname());
				saf.setScontentname(Internation.getStringByKeyPositionDB(
						"HapContent", Integer.valueOf(ob[4].toString())));
				saf.setSstatusname(Internation.getStringByKeyPositionDB(
						"SStatus", Integer.valueOf(ob[6].toString())));
				saf.setRankname(Internation.getStringByKeyPositionDB("Rank",
						Integer.valueOf(ob[7].toString())));
				saf.setSfee(Double.valueOf(ob[5].toString()));
				saf.setSdate(String.valueOf(ob[8]).substring(0, 10));
				// saf.setIsallotname(Internation.getStringByKeyPosition("YesOrNo",request,Integer.parseInt(ob[11].toString()),
				// "global.sys.SystemResource"));
				saf.setMakeidname(au.getUsersByid(
						Integer.valueOf(ob[9].toString())).getRealname());
				saf.setIsaffirm(Integer.valueOf(String.valueOf(ob[11])));
				alrt.add(saf);
			}

			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			request.setAttribute("als", als);

			String scontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "SContent", true);
			String sstatusselect = Internation.getSelectTagByKeyAllDB(
					"SStatus", "SStatus", true);
			String rankselect = Internation.getSelectTagByKeyAllDB("Rank",
					"Rank", true);

			request.setAttribute("scontentselect", scontentselect);
			request.setAttribute("sstatusselect", sstatusselect);
			request.setAttribute("rankselect", rankselect);
			request.setAttribute("alrt", alrt);

			DBUserLog.addUserLog(userid,0,"我的办公桌>>查收服务预约");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
