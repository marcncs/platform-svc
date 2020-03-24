package com.winsafe.drp.action.report;

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

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WithdrawDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class WithdrawTotalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = "or w.makeorganid in(" + users.getVisitorgan()+ ")" ;
			}
			String Condition = " (w.makeid=" + userid + " " + visitorgan+ " ) and wd.wid=w.id and w.isaudit=1 and w.isblankout=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Withdraw", "WithdrawDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "ProductID", "ProductName", "SpecMode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			List str = new ArrayList();

			AppWithdrawDetail asod = new AppWithdrawDetail();
			List pils = asod.getWithdrawTotalReport(request, pagesize, whereSql);
			List sumobj = asod.getTotalSubum(whereSql);
			double totalqt = 0.00;
			double totalsum = 0.00;
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[])pils.get(i);
				WithdrawDetailForm sodf = new WithdrawDetailForm();
				sodf.setProductid(String.valueOf(o[0]));
				sodf.setProductname(String.valueOf(o[1]));
				sodf.setSpecmode(String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[3].toString())));
				sodf.setQuantity(Double.valueOf(o[4].toString()));
				sodf.setSubsum(Double.valueOf(o[5].toString()));
				totalqt += sodf.getQuantity();
				totalsum += sodf.getSubsum();
				str.add(sodf);
			}
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			
			request.setAttribute("str", str);
			
			double allqt = 0;
			double allsum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allqt = Double.parseDouble(String.valueOf(ob[0] == null ? "0": ob[0]));
				allsum = Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
			}
			request.setAttribute("allqt", allqt);
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));

			String paymentmodeselect = Internation.getSelectPayByAllDB(
					"paymentmode", true);
			String withdrawsortselect = Internation.getSelectTagByKeyAllDB(
					"WithdrawSort", "WithdrawSort", true);
			
			

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());
			

			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("als", als);
			request.setAttribute("alos", alos);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("withdrawsortselect", withdrawsortselect);
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10, "报表分析>>零售>>列表零售退货汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
