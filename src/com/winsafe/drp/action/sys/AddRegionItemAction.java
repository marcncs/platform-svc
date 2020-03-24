package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;

public class AddRegionItemAction extends DispatchAction {
	private static Map<Integer, String> regionType = new HashMap<Integer, String>();
	
	static {
		regionType.put(-2, "ALL");
		regionType.put(-1, "大区");
		regionType.put(0, "省");
		regionType.put(1, "地区");
		regionType.put(2, "小区");
	}

	public ActionForward addRegionPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		int rank = Integer.parseInt(request.getParameter("rank"));
		String pid = request.getParameter("xid");
		RegionItemInfo pinfo = null;
		if (rank == -2) {
			pinfo = new RegionItemInfo();
			pinfo.setId(-1);
			pinfo.setAreaId(-1);
			pinfo.setName("All");
			pinfo.setRank(-2);
		} else {
			pinfo = arm.getRegionById(pid);
		}
		
		request.setAttribute("info", pinfo);
		request.setAttribute("rank", pinfo.getRank());
		request.setAttribute("rankName", regionType.get(pinfo.getRank()+1)); // 获取下级区域类型名称

		return mapping.findForward("page");
		
	}
	
	public ActionForward addRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		AppRegionManage arm = new AppRegionManage();
		int rank = Integer.parseInt(request.getParameter("rank"));
		int pid = Integer.parseInt(request.getParameter("parentid"));
		String areaName = "";
		RegionItemInfo info = new RegionItemInfo();
		info.setpId(pid);
		info.setRank(rank+1);
		areaName = request.getParameter("name").trim();
		info.setName(areaName);
		List r = arm.query(info);
		if (r != null && r.size()>0) {
			request.setAttribute("result",areaName+" 已经存在不能重复添加!");
			return new ActionForward("/sys/lockrecordclose2.jsp");
		}
		arm.addRegion(info);
		request.setAttribute("result","databases.add.success");
		return mapping.findForward("success");
	}
	
}
