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
import com.winsafe.drp.dao.AppWebIndentDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WebIndentDetail;
import com.winsafe.drp.dao.WebIndentDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ExcPutWebIndentDetailAction extends Action {
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
				visitorgan = " and so.equiporganid in(" + users.getVisitorgan()+ ")";
			}
			String Condition = " so.id=sod.woid and so.isendcase=1 and so.isblankout=0 "
					+ visitorgan;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "WebIndent"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String brur = DbUtil.getOrBlur(map, tmpMap, "CName", "CID",
					"ProductID", "ProductName", "SpecMode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			

			AppWebIndentDetail asod = new AppWebIndentDetail();
			double totalsum = asod.getTotalSubum(whereSql);

			List sodls = asod.getWebIndentDetail(whereSql);
			ArrayList alsod = new ArrayList();
			Double subsum = 0.00;
			for (int d = 0; d < sodls.size(); d++) {
				WebIndentDetailForm sodf = new WebIndentDetailForm();
				Object[] o = (Object[])sodls.get(d);
				String cname = (String)o[0];
				WebIndentDetail od = (WebIndentDetail) o[1];
				sodf.setCname(cname);
				sodf.setWoid(od.getWoid());
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
			String makeorganid = request.getParameter("EquipOrganID");
			if ( makeorganid != null && !makeorganid.equals("") ){
				request.setAttribute("EquipOrganID", ao.getOrganByID(makeorganid).getOrganname());
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
