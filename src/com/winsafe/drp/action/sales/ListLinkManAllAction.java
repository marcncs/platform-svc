package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.LinkmanForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListLinkManAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AppLinkMan al = new AppLinkMan();
		int pagesize = 10;
		  initdata(request);
		try {
			
			String[] tablename = { "Linkman" };
			String whereSql = getWhereSql2(tablename); 
			String timeCondition = getTimeCondition("Birthday"); 
			String blur = getKeyWordCondition("Name", "OfficeTel", "Mobile"); 
			whereSql = whereSql + blur + timeCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			List usList = al.searchLinkMan(request,pagesize, whereSql);
			ArrayList linkManList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				LinkmanForm linkman = new LinkmanForm();
				Linkman o = (Linkman) usList.get(t);
				linkman.setId(o.getId());
				linkman.setCid(o.getCid());
				linkman.setName(o.getName());
				linkman.setSexname(Internation.getStringByKeyPosition("Sex",
						request, o.getSex(),
						"global.sys.SystemResource"));
				linkman.setIdcard(o.getIdcard());
				linkman.setDepartment(o.getDepartment());
				linkman.setDuty(o.getDuty());
				linkman.setOfficetel(o.getOfficetel());
				linkman.setMobile(o.getMobile());
				linkman.setHometel(o.getHometel());
				linkman.setEmail(o.getEmail());
				linkman.setMsn(o.getMsn());
				linkman.setAddr(o.getAddr());
				linkman.setIsmainname(Internation.getStringByKeyPosition("IsMain",
						request, o.getIsmain(),
						"global.sys.SystemResource"));
				linkManList.add(linkman);

			}
			String sexselect = Internation.getSelectTagByKeyAll("Sex", request,
					"Sex", true, null);

			request.setAttribute("sexselect", sexselect);
			request.setAttribute("usList", linkManList);

			DBUserLog.addUserLog(userid,5,"会员/积分管理>>列表客户联系人");
			return mapping.findForward("linkManList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
