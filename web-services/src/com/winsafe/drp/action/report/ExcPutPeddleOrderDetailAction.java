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
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrderDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutPeddleOrderDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = "or so.makeorganid in(" + users.getVisitorgan()+ ") or so.equiporganid in(" + users.getVisitorgan()+ ")";
			}
			String Condition = " (so.makeid=" + userid + " " + visitorgan+ " ) and so.id=sod.poid and  so.isblankout=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PeddleOrder", "PeddleOrderDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql  + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPeddleOrderDetail asod = new AppPeddleOrderDetail();
			double totalsum = asod.getTotalSubum(whereSql);
			
			List sodls = asod.getPeddleOrderDetail(whereSql);
			ArrayList alsod = new ArrayList();
			Double subsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				PeddleOrderDetailForm sodf = new PeddleOrderDetailForm();
				Object[] o = (Object[])sodls.get(d);
				String cname = (String)o[0];
				PeddleOrderDetail od = (PeddleOrderDetail) o[1];
				sodf.setCname(cname);
				sodf.setPoid(od.getPoid());
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
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("subsum", DataFormat.currencyFormat(subsum));
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
