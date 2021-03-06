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
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutVocationOrderDetailAction extends Action {
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
				visitorgan = "or so.makeorganid in(" + users.getVisitorgan()+ ") or so.equiporganid in(" + users.getVisitorgan()+ ")";
			}
			String Condition = " (so.makeid=" + userid + " " + visitorgan+ " ) and so.id=sod.soid and so.isendcase=1 and so.isblankout=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "VocationOrder", "VocationOrderDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "CName", "CMobile",
					"ProductID", "ProductName", "SpecMode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppVocationOrderDetail asod = new AppVocationOrderDetail();
			List pils = asod.getVocationOrderDetail(whereSql);
			double totalsum = asod.getTotalSubum(whereSql);
				ArrayList alsod = new ArrayList();
				Double subsum = 0.00;
				for (int d = 0; d < pils.size(); d++) {
					Object[] o = (Object[])pils.get(d);
					SaleOrderDetailForm sodf = new SaleOrderDetailForm();
					String cname = (String) o[0];
					VocationOrderDetail od = (VocationOrderDetail) o[1];
					sodf.setCname(cname);
					sodf.setSoid(od.getSoid());
					sodf.setProductid(od.getProductid());
					sodf.setProductname(od.getProductname());
					sodf.setSpecmode(od.getSpecmode());
					sodf.setUnitidname(Internation.getStringByKeyPositionDB(
							"CountUnit", od.getUnitid().intValue()));
					sodf.setUnitprice(od.getTaxunitprice());
					sodf.setQuantity(od.getQuantity());
					sodf.setSubsum(od.getSubsum());
					subsum += sodf.getSubsum();
					alsod.add(sodf);
				}
				
			request.setAttribute("subsum", DataFormat.currencyFormat(subsum));
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("alsod", alsod);


			

			AppOrgan ao = new AppOrgan();
			AppUsers au = new AppUsers();
			String makeorganid = request.getParameter("MakeOrganID");
			if ( makeorganid != null && !makeorganid.equals("") ){
				request.setAttribute("MakeOrganID", ao.getOrganByID(makeorganid).getOrganname());
			}
			String MakeID = request.getParameter("MakeID");
			if ( MakeID != null && !MakeID.equals("") ){
				request.setAttribute("MakeID", au.getUsersByid(Integer.valueOf(MakeID)).getRealname());
			}
			
			request.setAttribute("begindate", request.getParameter("BeginDate"));
			request.setAttribute("enddate", request.getParameter("EndDate"));
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
