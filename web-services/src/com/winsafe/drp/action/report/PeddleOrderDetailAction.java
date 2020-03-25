package com.winsafe.drp.action.report;

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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrderDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class PeddleOrderDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
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

			Object obj[] = DbUtil.setDynamicPager(request, "PeddleOrder as so, PeddleOrderDetail as sod ",
					whereSql, pagesize, "PeddleOrderReport");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];


			AppPeddleOrderDetail asod = new AppPeddleOrderDetail();
			double totalsum = asod.getTotalSubum(whereSql);
			
			List sodls = asod.getPeddleOrderDetail(pagesize, whereSql, tmpPgInfo);
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
