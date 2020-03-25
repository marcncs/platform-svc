package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.dao.SuitForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

//import java.text.SimpleDateFormat;

public class ListSuitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		request.getSession().setAttribute("cid", strCid);
		AppUsers au = new AppUsers();

		int pagesize = 5;
		try {
			
			initdata(request);
			
			String visitorgan = "";
			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
				visitorgan = "or s.makeorganid in("+users.getVisitorgan()+") ";
			}

			String Condition = " (s.makeid="+userid+" "+visitorgan+" )";
			
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Suit" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "CID","CName");
			// 
			whereSql = whereSql + timeCondition + blur +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setPager(request, " Suit as s ", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppSuit apps = new AppSuit();
			AppOrgan ao = new AppOrgan();
			List usList = apps.getSuit(pagesize, whereSql, tmpPgInfo);
			ArrayList hList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				SuitForm hf = new SuitForm();
				Suit ob = (Suit) usList.get(t);
				hf.setId(ob.getId());
				hf.setCidname(ob.getCname());
				
				hf.setMakeorganidname(ao.getOrganByID(ob.getMakeorganid()).getOrganname());
				hf.setMakeidname(au.getUsersByid(ob.getMakeid())
						.getRealname());
				hf.setMakedate(DateUtil.formatDateTime(ob.getMakedate()));

				hList.add(hf);
			}
			String suitcontentselect = Internation.getSelectTagByKeyAllDB(
					"HapContent", "SuitContent", true);
			String suitwayselect = Internation.getSelectTagByKeyAllDB(
					"SuitWay", "SuitWay", true);
			String isdealselect = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "IsDeal", true, null);
			
			List ols = ao.getOrganToDown(users.getMakeorganid());
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			

			request.setAttribute("suitcontentselect", suitcontentselect);
			request.setAttribute("suitwayselect", suitwayselect);
			request.setAttribute("isdealselect", isdealselect);
			request.setAttribute("ols", ols);
			request.setAttribute("als", als);

			request.setAttribute("hList", hList);

			DBUserLog.addUserLog(userid, 5, "抱怨投诉>>列表投诉抱怨");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
