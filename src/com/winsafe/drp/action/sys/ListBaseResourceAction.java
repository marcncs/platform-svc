package com.winsafe.drp.action.sys;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListBaseResourceAction extends Action {

	private Set<String> tagNames = new HashSet<String>();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String tagName = request.getParameter("TagName");
			String tagTemp = tagName;

			if ("BusyTime".equals(tagName)) {
				tagName = "BusyFromTime' or br.tagname= 'BusyToTime";
				request.setAttribute("isTime", "true");
			}
			// Map map = new HashMap(request.getParameterMap());
			// Map tmpMap = EntityManager.scatterMap(map);
			// String[] tablename={"BaseResource"};
			// String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			//
			// //whereSql = whereSql ;
			// whereSql = DbUtil.getWhereSql(whereSql);
			// Object obj[] = (DbUtil.setDynamicPager(request,
			// "ApproveFlow as af ",
			// whereSql,
			// pagesize,"subCondition"));
			// SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0];
			// whereSql = (String)obj[1];
			AppBaseResource abr = new AppBaseResource();

			List apls = abr.getBaseResource(tagName);
			// ArrayList alpl = new ArrayList();
			// for(int i=0;i<apls.size();i++){
			// BaseResource aff = new BaseResource();
			// Object[] o = (Object[])apls.get(i);
			// aff.setId(Long.valueOf(o[0].toString()));
			// aff.setTagname(String.valueOf(o[1]));
			// aff.setTagsubkey(Integer.valueOf(o[2].toString()));
			// aff.setTagsubvalue(String.valueOf(o[3].toString()));
			//	        
			// alpl.add(aff);
			// }
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, "系统管理", "资料设置>>列表资源");
			request.setAttribute("alpl", apls);
			request.setAttribute("tagName", tagName);

			if (tagNames.size() == 0) {
				initTagNames();
			}
			if (tagNames.contains(tagTemp)) {
				if ("BusyTime".equals(tagTemp)) {
					request.setAttribute("istime", "true");
				}
				return mapping.findForward("list3");
			}

			if (tagName.equals("UploadIdcodeFlag")) {
				return mapping.findForward("list2");
			} else {
				return mapping.findForward("list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	private void initTagNames() {
		tagNames.add("PreGeneratedPCodeCnt");
		tagNames.add("BusyTime");
		tagNames.add("CartonLabelCount");
		tagNames.add("BusyTimeDelay");
		tagNames.add("IdleTimeDelay");
		tagNames.add("TenDigitsPCodeCount");
	}
}
