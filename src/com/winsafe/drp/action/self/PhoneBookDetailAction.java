package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBook;

public class PhoneBookDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strid = request.getParameter("ID");
		Integer id = Integer.valueOf(strid);
		try {
			PhoneBook pb = new PhoneBook();
			AppPhoneBook apb = new AppPhoneBook();
			pb = apb.getPhoneBookByID(id);

			AppPhoneBookSort apbs = new AppPhoneBookSort();

			String sort = apbs.getPhoneBookSortByID(
					Integer.valueOf(pb.getSortid().toString())).getSortname();

			request.setAttribute("sex", pb.getSex());
			request.setAttribute("sort", sort);

			request.setAttribute("pb", pb);

			return mapping.findForward("phonebookdetail");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
