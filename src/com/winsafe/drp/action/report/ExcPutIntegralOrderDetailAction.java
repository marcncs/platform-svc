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

import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
import com.winsafe.drp.dao.IntegralOrderDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ExcPutIntegralOrderDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = "or io.makeorganid in(" + users.getVisitorgan()+ ")" ;
			}
			String Condition = " (io.makeid=" + userid + " " + visitorgan+ " ) and io.id=iod.ioid and io.isendcase=1 and io.isblankout=0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "IntegralOrder", "IntegralOrderDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "CID", "CName", "ProductID",
					"ProductName", "SpecMode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralOrderDetail asod = new AppIntegralOrderDetail();
			List sumobj = asod.getTotalSubum(whereSql);
			
			List sodls = asod.getIntegralOrderDetail(whereSql);
			ArrayList alsod = new ArrayList();
			Double totalsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				IntegralOrderDetailForm sodf = new IntegralOrderDetailForm();
				Object[] o = (Object[])sodls.get(d);
				IntegralOrder io= (IntegralOrder)o[0];
				IntegralOrderDetail od = (IntegralOrderDetail)o[1];
				sodf.setCname(io.getCname());
				sodf.setCmobile(io.getCmobile());
				sodf.setIoid(od.getIoid());
				sodf.setProductid(od.getProductid());
				sodf.setProductname(od.getProductname());
				sodf.setSpecmode(od.getSpecmode());
				sodf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", od.getUnitid()));
				sodf.setIntegralprice(od.getIntegralprice());
				sodf.setQuantity(od.getQuantity());
				sodf.setSubsum(od.getSubsum());
				totalsum += sodf.getSubsum();
				alsod.add(sodf);
			}
				
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("alsod", alsod);
			
			double allsum = 0;
			if (sumobj != null) {
				Object[] ob = (Object[]) sumobj.get(0);
				allsum = Double.parseDouble(String.valueOf(ob[1] == null ? "0": ob[1]));
			}
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			
			

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
