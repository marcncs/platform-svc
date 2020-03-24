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
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.SaleOrderTotal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SaleOrderTotalAction extends Action {
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
				visitorgan = "or d.makeorganid in(" + users.getVisitorgan()
						+ ") ";
			}
			String Condition = " (d.makeid=" + userid + " " + visitorgan
					+ " )  ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewSaleTotal" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"makedate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppSaleOrder aso = new AppSaleOrder();
			List pils = aso.getSaleTotal(request, pagesize, whereSql);
			List sumobj = aso.getTotalSum(whereSql);
			double totalqt = 0.00;
			double totalsum = 0.00;
			double totalcost = 0.00;
			double totalgain= 0.00;
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[]) pils.get(i);
				SaleOrderTotal sodf = new SaleOrderTotal();
				sodf.setProductid(String.valueOf(o[0]));
				sodf.setProductname(String.valueOf(o[1]));
				sodf.setSpecmode(String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[3].toString())));
				sodf.setQuantity(Double.valueOf(o[4].toString()));
				sodf.setSubsum(Double.valueOf(o[5].toString()));
				sodf.setCost(Double.valueOf(o[6].toString()));
				sodf.setGain(Double.valueOf(o[7].toString()));
				totalqt +=sodf.getQuantity();
				totalsum += sodf.getSubsum();
				totalcost += sodf.getCost();
				totalgain += sodf.getGain();
				str.add(sodf);
			}
			request.setAttribute("totalqt", totalqt);
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("totalcost", DataFormat.currencyFormat(totalcost));
			request.setAttribute("totalgain", DataFormat.currencyFormat(totalgain));
			request.setAttribute("str", str);

			double allsum = 0;
			double allqt = 0;
			double allcost = 0;
			double allgain = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allqt = Double.parseDouble(String.valueOf(ob[0] == null ? "0": ob[0]));
				allsum = Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
				allcost = Double.parseDouble(String.valueOf(ob[2] == null ? "0" : ob[2]));
				allgain = Double.parseDouble(String.valueOf(ob[3] == null ? "0": ob[3]));
			}
			request.setAttribute("allqt", allqt);
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			request.setAttribute("allcost", DataFormat.currencyFormat(allcost));
			request.setAttribute("allgain", DataFormat.currencyFormat(allgain));


		

			AppOrgan ao = new AppOrgan();
			List ols = ao.getAllOrgan();
			ArrayList alos = new ArrayList();

			for (int o = 0; o < ols.size(); o++) {
				OrganForm ub = new OrganForm();
				Organ of = (Organ) ols.get(o);
				ub.setId(of.getId());
				ub.setOrganname(of.getOrganname());
				alos.add(ub);
			}

			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("als", als);
			request.setAttribute("alos", alos);
			
			return mapping.findForward("saleordertotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
