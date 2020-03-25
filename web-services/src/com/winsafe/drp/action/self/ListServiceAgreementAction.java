package com.winsafe.drp.action.self;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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


public class ListServiceAgreementAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		request.getSession().setAttribute("cid", strCid);
		AppCustomer ac = new AppCustomer();
		AppUsers au = new AppUsers();

		int pagesize = 5;
		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
			String Condition = " sa.cid='" + cid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ServiceAgreement" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			// "RegistDate"); 
			// String blur = DbUtil.getBlur(map, tmpMap, "RegieName");
			// 
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, "ServiceAgreement as sa",
					whereSql, pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			AppServiceAgreement asa = new AppServiceAgreement();
			List usList = asa.getServiceAgreement(pagesize,
					whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				ServiceAgreementForm hf = new ServiceAgreementForm();
				// customer=(Customer)usList.get(t);
				Object[] ob = (Object[]) usList.get(t);
				hf.setId(Integer.valueOf(ob[0].toString()));
				//hf.setCidname(ac.getCustomer(String.valueOf(ob[1])).getCname());
				hf.setScontentname(Internation.getStringByKeyPositionDB(
						"HapContent", Integer.valueOf(ob[4].toString())));
				hf.setSstatusname(Internation.getStringByKeyPositionDB(
						"SStatus", Integer.valueOf(ob[6].toString())));
				hf.setRankname(Internation.getStringByKeyPositionDB("Rank",
						Integer.valueOf(ob[7].toString())));
				hf.setSfee(Double.valueOf(ob[5].toString()));
				hf.setSdate(String.valueOf(ob[8]).substring(0, 10));
				hf.setIsallotname(Internation.getStringByKeyPosition("YesOrNo",
						request, Integer.parseInt(ob[11].toString()),
						"global.sys.SystemResource"));
				hf.setMakeidname(au.getUsersByid(Integer.valueOf(ob[12].toString()))
						.getRealname());

				hList.add(hf);
			}
			String scontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "SContent", true);
			String sstatusselect = Internation.getSelectTagByKeyAllDB(
					"SStatus", "SStatus", true);
			String rankselect = Internation.getSelectTagByKeyAllDB("Rank",
					"Rank", true);
			String isallotselect = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "IsAllot", true, null);

			request.setAttribute("scontentselect", scontentselect);
			request.setAttribute("sstatusselect", sstatusselect);
			request.setAttribute("rankselect", rankselect);
			request.setAttribute("isallotselect", isallotselect);

			request.setAttribute("hList", hList);

			DBUserLog.addUserLog(userid, 0,"我的办公桌>>列表服务预约");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
