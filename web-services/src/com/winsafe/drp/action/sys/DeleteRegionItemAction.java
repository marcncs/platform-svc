package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;

public class DeleteRegionItemAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		int rank = Integer.parseInt(request.getParameter("rank"));
		int areaId = Integer.parseInt(request.getParameter("areaId"));
		if (id == -1) {
			request.setAttribute("result", "不能删除顶级区域!");
			return new ActionForward("/sys/lockrecordclose2.jsp");
		}
		AppRegionManage arm = new AppRegionManage();
		RegionItemInfo info = new RegionItemInfo();
		info.setId(id);
		info.setRank(rank);
		info.setAreaId(areaId);
		arm.deleteRegionRec(info);
		request.setAttribute("result","databases.del.success");
		return mapping.findForward("success");
	}

}
