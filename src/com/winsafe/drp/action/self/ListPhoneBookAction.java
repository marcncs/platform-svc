package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBook;
import com.winsafe.drp.dao.PhoneBookForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPhoneBookAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		int pagesize = 5;
		initdata(request);
		String pbsid = request.getParameter("PbsID");
		String id = request.getParameter("ID");
		String keyword = request.getParameter("KeyWord");
		try {
			String userCondition = " userid=" + userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PhoneBook" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();
			if (pbsid != null && pbsid.length() > 0) {
				buf.append("(");
				buf.append("sortid = '" + pbsid + "')");
				buf.append(" and ");
				whereSql = whereSql + buf;
			}

			String blur = DbUtil.getBlur(map, tmpMap, " Name");
			whereSql = whereSql + blur + userCondition;

			whereSql = DbUtil.getWhereSql(whereSql);
			AppPhoneBookSort apbs = new AppPhoneBookSort();
			AppPhoneBook apb = new AppPhoneBook();
			List<PhoneBook> pbsls = apb.searchPhoneBook(request, pagesize,
					whereSql);
			List<PhoneBookForm> list = new ArrayList<PhoneBookForm>();
			PhoneBookForm pbf = null;
			for (PhoneBook phoneBook : pbsls) {
				pbf = new PhoneBookForm();
				pbf.setId(phoneBook.getId());
				pbf.setName(phoneBook.getName());
				pbf.setSex(phoneBook.getSex());
				pbf.setPhone(phoneBook.getPhone());
				pbf.setMobile(phoneBook.getMobile());
				String sortname="";
				try {
					sortname=apbs.getPhoneBookSortByID(phoneBook.getSortid()).getSortname();
				} catch (Exception e) {
				}
				pbf.setSortname(sortname);
				list.add(pbf);

			}
			request.setAttribute("keyword", keyword);
			request.setAttribute("id", id);
			request.setAttribute("alpbsls", list);

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表电话本");
			return mapping.findForward("listphonebook");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
