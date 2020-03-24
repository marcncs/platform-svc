package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;

public class RegionDetailAction extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
//		RegionItemInfo info = new RegionItemInfo();
//		String keyword = request.getParameter("keyword");
		String pid = request.getParameter("pid");
		if("-1".equals(pid)) {
			request.setAttribute("rank", -1);
		} else {
			RegionItemInfo ri = arm.getRegionById(pid);
			if(ri != null) {
				request.setAttribute("rank", ri.getRank());
			} else {
				request.setAttribute("rank", -1);
			}
		}
//		if (StringUtil.isEmpty(keyword)) {
//			keyword = null;
//		}
//		if (!StringUtil.isEmpty(pid)) {
//			info.setpId(Integer.parseInt(pid));
//		}
//		List list = arm.getAllRegionDetail(request, info, keyword);
//		request.setAttribute("list", list);
		request.setAttribute("pid", pid);
//		request.setAttribute("keyword", keyword);
		return mapping.findForward("page");
	}

}
