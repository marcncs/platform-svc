package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHap;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListHapAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		request.getSession().setAttribute("cid", strCid);

		int pagesize = 5;
		initdata(request);
		try {
			
			
			String Condition = " h.cid='" + cid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Hap" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			// "RegistDate"); 
			// String blur = DbUtil.getBlur(map, tmpMap, "RegieName");
			// 
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppHap appHap = new AppHap();
			List usList = appHap.searchHap(request,pagesize, whereSql);
//			ArrayList hList = new ArrayList();
//			for (int t = 0; t < usList.size(); t++) {
//				HapForm hf = new HapForm();
//				// customer=(Customer)usList.get(t);
//				Object[] ob = (Object[]) usList.get(t);
//				hf.setId(Integer.valueOf(ob[0].toString()));
//				hf.setCidname(ac.getCustomer(String.valueOf(ob[1])).getCname());
//				hf.setHapcontentname(Internation.getStringByKeyPositionDB(
//						"HapContent", Integer.valueOf(ob[2].toString())));
//				hf.setHapkindname(Internation.getStringByKeyPositionDB(
//						"HapKind", Integer.valueOf(ob[3].toString())));
//				hf.setHapstatusname(Internation.getStringByKeyPositionDB(
//						"HapStatus", Integer.valueOf(ob[4].toString())));
//				hf.setIntend(Double.valueOf(ob[5].toString()));
//				hf.setHapbegin(String.valueOf(ob[6]).substring(0, 10));
//				hf.setHapend(String.valueOf(ob[7]).substring(0, 10));
//				hf.setMakeidname(au.getUsersByid(
//						Integer.valueOf(ob[9].toString())).getRealname());
//
//				hList.add(hf);
//
//			}
			String hapcontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "HapContent", true);
			String hapkindselect = Internation.getSelectTagByKeyAllDB(
					"HapKind", "HapKind", true);
			String hapstatusselect = Internation.getSelectTagByKeyAllDB(
					"HapStatus", "HapStatus", true);

			request.setAttribute("hapcontentselect", hapcontentselect);
			request.setAttribute("hapkindselect", hapkindselect);
			request.setAttribute("hapstatusselect", hapstatusselect);

			request.setAttribute("hList", usList);

			DBUserLog.addUserLog(userid, 5,"销售机会>>列表销售机会");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
