package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.LargessForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;


public class ListLargessAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		AppCustomer ac = new AppCustomer();
		AppUsers au = new AppUsers();

		int pagesize = 5;
		  initdata(request);
		try {
			
			
			String Condition = " l.makeid like '" + userid + "%' ";
			// String Condition = " l.cid='"+cid+"'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Largess" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"RegistDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, "LargessDescribe"); 
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " Largess as l", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			AppLargess appl = new AppLargess();
			List usList = appl.getLargess(request,pagesize, whereSql);
			ArrayList hList = new ArrayList();
			String largessdescribe = "";
			for (int t = 0; t < usList.size(); t++) {
				LargessForm lf = new LargessForm();
				// customer=(Customer)usList.get(t);
				Object[] ob = (Object[]) usList.get(t);
				lf.setId(Integer.valueOf(ob[0].toString()));
				lf.setCid(String.valueOf(ob[1]));
				lf.setCidname(ac.getCustomer(String.valueOf(ob[1])).getCname());
				largessdescribe = String.valueOf(ob[2]);
				lf
						.setLargessdescribe(largessdescribe.length() > 15 ? largessdescribe
								.substring(0, 13)
								+ "..."
								: largessdescribe);
				lf.setLfee(Double.valueOf(ob[3].toString()));
				lf.setLdate(String.valueOf(ob[4]).substring(0, 10));
				lf.setMemo(String.valueOf(ob[5]));
				lf.setMakeidname(au.getUsersByid(Integer.valueOf(ob[6].toString()))
						.getRealname());
				hList.add(lf);
			}

			request.setAttribute("hList", hList);

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

			DBUserLog.addUserLog(userid, 5,"赠品>>列表赠品");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
