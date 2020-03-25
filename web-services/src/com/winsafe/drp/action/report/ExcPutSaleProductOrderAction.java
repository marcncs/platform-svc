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
import com.winsafe.drp.dao.SaleOrderTotal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutSaleProductOrderAction extends Action {
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
			List pils = aso.getProductOrder(whereSql, ordertype);
			
			for (int i = 0; i < pils.size(); i++) {
				Object[] o = (Object[]) pils.get(i);
				SaleOrderTotal sodf = new SaleOrderTotal();
				int order = i +1;
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
			String makeorganid = request.getParameter("makeorganid");
			if ( makeorganid != null && !makeorganid.equals("") ){
				request.setAttribute("makeorganid", ao.getOrganByID(makeorganid).getOrganname());
			}
			String equiporganid = request.getParameter("equiporganid");
			if ( equiporganid != null && !equiporganid.equals("") ){
				request.setAttribute("equiporganid", ao.getOrganByID(equiporganid).getOrganname());
			}
			String billtype = request.getParameter("billtype");
			String billtypename = "所有";
			if ( billtype != null && !billtype.equals("") ){
				if ( billtype.equals("SO") ){
					billtypename = "销售单";
				}else if ( billtype.equals("PD") ){
					billtypename = "零售单";
				}else if ( billtype.equals("WI") ){
					billtypename = "网站订单";
				}else if ( billtype.equals("VO") ){
					billtypename = "行业销售单";
				}
			}
			
			
			request.setAttribute("begindate", request.getParameter("BeginDate"));
			request.setAttribute("enddate", request.getParameter("EndDate"));
			request.setAttribute("billtypename", billtypename);
			
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
