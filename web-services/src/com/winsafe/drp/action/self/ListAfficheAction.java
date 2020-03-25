package com.winsafe.drp.action.self;

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

import com.winsafe.drp.dao.AfficheForm;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppAfficheBrowse;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListAfficheAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		AppAffiche aa = new AppAffiche();
		AppUsers au = new AppUsers();
		AppAfficheBrowse aab = new AppAfficheBrowse();
		int pagesize = 10;
		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
			String Condition = " a.id=ab.afficheid and ab.userid="+userid+" ";
			String[] tablename = { "Affiche","AfficheBrowse" };

			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, "AfficheContent"); 
			whereSql = whereSql + timeCondition + blur +Condition; 

			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = DbUtil.setPager(request, "Affiche as a,AfficheBrowse as ab ", whereSql,
					pagesize);
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			
			String affichetitle = "";
			String affichecontent ="";
			List aaList = aa.getListAffiche(pagesize, whereSql, tmpPgInfo);
			ArrayList afficheList = new ArrayList();
			for (int t = 0; t < aaList.size(); t++) {
				AfficheForm af = new AfficheForm();
				Object[] ob = (Object[]) aaList.get(t);
				af.setId(Integer.valueOf(ob[0].toString()));
				af.setBrowsestatus(aab.getAfficheBrowse(af.getId(), userid).getIsbrowse().toString());
				affichetitle = String.valueOf(ob[1]);
				af.setAffichetitle(affichetitle.length()>15?affichetitle.substring(0,13)+"...":affichetitle);
				affichecontent=String.valueOf(ob[2]);
				af.setAffichecontent(affichecontent.length()>20?affichecontent.substring(0,17)+"...":affichecontent);
				af.setAffichetype(Internation.getStringByKeyPosition(
						"AfficheType", request, Integer.parseInt(ob[3]
								.toString()), "global.sys.SystemResource"));
				af.setPonderance(Internation.getStringByKeyPosition(
						"Ponderance", request, Integer.parseInt(ob[4]
								.toString()), "global.sys.SystemResource"));
				af.setPublishdate(ob[5].toString());
				af.setPublishmanname(au.getUsersByid(Integer.valueOf(ob[6].toString())).getRealname());
				afficheList.add(af);

			}
			request.setAttribute("aaList", afficheList);

		    DBUserLog.addUserLog(userid,0,"查看公告列表");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("list");
	}

}
