package com.winsafe.drp.action.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;

public class UpdateRegionItemAction extends DispatchAction {
	private static Map<Integer, String> regionType = new HashMap<Integer, String>();
	
	static {
		regionType.put(-1, "大区");
		regionType.put(0, "省");
		regionType.put(1, "地区");
		regionType.put(2, "小区");
	}

	public ActionForward toPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		String id = request.getParameter("xid");
		RegionItemInfo info = arm.getRegionById(id);
		request.setAttribute("info", info);
		request.setAttribute("rankName", regionType.get(info.getRank()));
		return mapping.findForward("page");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		int id = Integer.parseInt(request.getParameter("id"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		int rank = Integer.parseInt(request.getParameter("rank"));
		String name = request.getParameter("name");
		RegionItemInfo info = new RegionItemInfo();
		info.setId(id);
		info.setAreaId(id);
		info.setName(name);
		info.setpId(pid);
		info.setRank(rank);
		info.setMdate(new Date());
		RegionItemInfo r = arm.getRegionByPidName(info);
		if (r != null) {
			request.setAttribute("result",name+" 已经存在!");
			return new ActionForward("/sys/lockrecordclose2.jsp");
		}
		
		arm.updateRegion(info);
		request.setAttribute("result","databases.upd.success");
		return mapping.findForward("success");
	}
}
