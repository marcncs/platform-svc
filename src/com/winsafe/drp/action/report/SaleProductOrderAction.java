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
import com.winsafe.drp.dao.SaleOrderTotal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.PaginationFacility;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SaleProductOrderAction extends Action {
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
			
			String ordertype = request.getParameter("ordertype");
			if( ordertype == null || ordertype.equals("") ){
				ordertype = "q";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewSaleProductTotal" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"makedate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppSaleOrder aso = new AppSaleOrder();
			List pils = aso.getProductOrder(request, pagesize, whereSql, ordertype);
			
			SimplePageInfo tmpPgIf=(SimplePageInfo)request.getAttribute(PaginationFacility.PAGINATION_ELE_NAME);
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[]) pils.get(i);
				SaleOrderTotal sodf = new SaleOrderTotal();
				int order = i +1 + (tmpPgIf.getCurrentPageNo()-1)*pagesize;
				sodf.setId(""+order);
				sodf.setProductid(String.valueOf(o[0]));
				sodf.setProductname(String.valueOf(o[1]));
				sodf.setSpecmode(o[2]==null?"":String.valueOf(o[2]));
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.valueOf(o[3].toString())));
				sodf.setQuantity(o[4]==null?0:Double.valueOf(o[4].toString()));
				sodf.setSubsum(o[5]==null?0:Double.valueOf(o[5].toString()));
				
				str.add(sodf);
			}
			
			request.setAttribute("str", str);

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("als", als);
			request.setAttribute("alos", alos);
			request.setAttribute("makeorganid", request.getParameter("makeorganid"));
			request.setAttribute("equiporganid", request.getParameter("equiporganid"));
			request.setAttribute("billtype", request.getParameter("billtype"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("productname", request.getParameter("productname"));
			request.setAttribute("ordertype", ordertype);
			
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
