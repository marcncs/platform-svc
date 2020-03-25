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
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.ValidateLinkman;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListLinkManAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		AppLinkMan al = new AppLinkMan(); 
		String cid = strCid;
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		int pagesize = 5;
		  initdata(request);
		try {
						
			
			String[] tablename = { "Linkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"Birthday"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "Name", "OfficeTel", "Mobile"); 
			whereSql = whereSql + blur + timeCondition + " l.cid='" + cid
					+ "' and ";// l.userid="+userid+" and"; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			// --------------保持查询条件--------------------------------------
			String keyword = request.getParameter("KeyWord");
			if (keyword == null) {
				keyword = "";
			}
			request.setAttribute("keyword", keyword);
			// --------------保持查询条件End--------------------------------------

			List usList = al.searchLinkMan(request,pagesize, whereSql);
			ArrayList linkManList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				ValidateLinkman linkman = new ValidateLinkman();
				Linkman o = (Linkman) usList.get(t);
				linkman.setId(o.getId());
				linkman.setCid(o.getCid());
				linkman.setName(o.getName());
				linkman.setSex(Internation.getStringByKeyPosition("Sex",
						request, o.getSex(),
						"global.sys.SystemResource"));
				linkman.setIdcard(o.getIdcard());
				//linkman.setBirthday(o.getBirthday());
				linkman.setDepartment(o.getDepartment());
				linkman.setDuty(o.getDuty());
				linkman.setOfficetel(o.getOfficetel());
				linkman.setMobile(o.getMobile());
				linkman.setHometel(o.getHometel());
				linkman.setEmail(o.getEmail());
				linkman.setMsn(o.getMsn());
				linkman.setAddr(o.getAddr());
				linkman.setIsmain(Internation.getStringByKeyPosition("IsMain",
						request, o.getIsmain(),
						"global.sys.SystemResource"));
				// linkman.setUserid(Integer.valueOf(ob[15].toString()));
				linkManList.add(linkman);

			}
			request.getSession().setAttribute("cid", strCid);
			request.setAttribute("usList", linkManList);

			DBUserLog.addUserLog(userid, 5, "会员资料>>列表会员联系人");
			return mapping.findForward("linkManList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
