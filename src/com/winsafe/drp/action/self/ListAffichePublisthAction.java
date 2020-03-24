package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AfficheOrView;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListAffichePublisthAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		AppAffiche appAffiche = new AppAffiche();

		int pagesize = 10;
		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
//			String Condition = " a.id=ab.afficheid and ab.userid="+userid+" ";
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Affiche","AfficheBrowse" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			
			String blur = DbUtil.getBlur(map, tmpMap, "AfficheContent"); 
//			whereSql = whereSql+timeCondition +blur+ Condition; 
			whereSql = whereSql+timeCondition +blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			
//			List list = appAffiche.searchAffiche(request, pagesize, whereSql);
//			
//			
//			List listview = new ArrayList();
//			for (int i = 0 ; i < list.size() ; i++) {
//				Object[] obj = (Object[]) list.get(i);
//				AfficheOrView afficheOrView = new AfficheOrView();
//				afficheOrView.setId(Integer.valueOf(obj[0].toString()));
//				afficheOrView.setAffichetitle(obj[1].toString());
//				afficheOrView.setAffichecontent(obj[3].toString());
//				afficheOrView.setMakeid(Integer.valueOf(obj[2].toString()));
//				afficheOrView.setAffichetype(Integer.valueOf(obj[4].toString()));
//				afficheOrView.setMakedate((Date)obj[5]);
//				afficheOrView.setIsbrowse(Integer.valueOf(obj[6].toString()));
//				listview.add(afficheOrView);
//			}
//			
			List listview = appAffiche.getAffiche(request, pagesize, whereSql);
			request.setAttribute("aaList", listview);
		    DBUserLog.addUserLog(request,"列表");//日志 
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("list");
	}

}
