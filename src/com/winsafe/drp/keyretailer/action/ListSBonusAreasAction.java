package com.winsafe.drp.keyretailer.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.hbm.util.StringUtil;

public class ListSBonusAreasAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusSettingAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		AppRegionManage arm = new AppRegionManage();
		RegionItemInfo info = new RegionItemInfo();
		String keyword = request.getParameter("keyword");
		String pid = request.getParameter("pid");
		if (StringUtil.isEmpty(keyword)) {
			keyword = null;
		}
		if (!StringUtil.isEmpty(pid)) {
			info.setpId(Integer.parseInt(pid));
		}
		List list = arm.getAllRegionDetail(request, info, keyword, pagesize);
		request.setAttribute("list", list);
		request.setAttribute("pid", pid);
		request.setAttribute("keyword", keyword);
		return mapping.findForward("list");
	}
		
}
