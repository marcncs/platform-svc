package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.dao.ReckoningForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListReckoningAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		String uid = request.getParameter("uid");
		if (uid == null) {
			uid = (String) request.getSession().getAttribute("uid");
		}
		request.getSession().setAttribute("uid", uid);
		try {

			String Condition = " r.uid ='" + uid + "' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Reckoning" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql +timeCondition+ Condition ;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppReckoning apa = new AppReckoning();
			AppUsers au = new AppUsers();

			List pbls = apa.getReckoning(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();

			for (int i = 0; i < pbls.size(); i++) {
				ReckoningForm lf = new ReckoningForm();
				Reckoning o = (Reckoning) pbls.get(i);
				lf.setId(o.getId());
				lf.setUidname(au.getUsersByid(o.getUid()).getRealname());
				lf.setLoandate(String.valueOf(o.getLoandate()));
				lf.setLoansum(o.getLoansum());
				lf.setBacksum(o.getBacksum());
				lf.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
				lf.setMakedate(String.valueOf(o.getMakedate()));
				lf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
						request, o.getIsaudit(), "global.sys.SystemResource"));

				alpl.add(lf);
			}

			request.setAttribute("alpl", alpl);

			 DBUserLog.addUserLog(userid,9,"个人借支>>列表还款清算");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
