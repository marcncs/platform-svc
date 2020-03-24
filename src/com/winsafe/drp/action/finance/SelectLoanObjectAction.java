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
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.drp.dao.LoanObjectForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectLoanObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			//String Condition = " ro.makeid like '"+userid+"%' ";
			String Condition=" (lo.makeid="+userid+" " +getOrVisitOrgan("lo.makeorganid") +") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			//String sql = "select * from income_log as il where "+Condition;
			String[] tablename = { "LoanObject" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			//String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
			 String blur = DbUtil.getBlur(map, tmpMap, "UID"); 
			whereSql = whereSql + blur + Condition ; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppLoanObject aro = new AppLoanObject();
			AppUsers au = new AppUsers();
			// AppReckoning ai = new AppReckoning();

			List pbls = aro.getLoanObject(request,pagesize, whereSql);
			ArrayList alpl = new ArrayList();

			for (int i = 0; i < pbls.size(); i++) {
				LoanObjectForm rf = new LoanObjectForm();
				LoanObject o = (LoanObject) pbls.get(i);
				//id = Long.valueOf(o[0].toString());
				rf.setId(o.getId());
				rf.setUid(o.getUid());
				rf.setUidname(au.getUsersByid(o.getUid())
						.getRealname());

				alpl.add(rf);
			}

			request.setAttribute("alpl", alpl);

			return mapping.findForward("selectobject");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
