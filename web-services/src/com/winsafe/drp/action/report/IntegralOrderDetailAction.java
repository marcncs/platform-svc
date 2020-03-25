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
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class IntegralOrderDetailAction extends Action {
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

			Object obj[] = DbUtil.setDynamicPager(request, "IntegralOrder as io, IntegralOrderDetail as iod ",
					whereSql, pagesize, "IntegralOrderReport");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppIntegralOrderDetail asod = new AppIntegralOrderDetail();
			List sumobj = asod.getTotalSubum(whereSql);
			
			List sodls = asod.getIntegralOrderDetail(pagesize, whereSql, tmpPgInfo);
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
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", od.getUnitid().intValue()));
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
			List alos = ao.getOrganToDown(users.getMakeorganid());
			

			AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("als", als);
			request.setAttribute("alos", alos);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("CName", request.getParameter("CName"));
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
