package com.winsafe.drp.action.sys;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSON;
import com.winsafe.drp.dao.AppRegionManage;
import com.winsafe.drp.dao.RegionItemInfo;

/**
 * @author Tao.sun
 * 加载区域管理中的 区域树结点数据
 */
public class LoadRegionTreeAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppRegionManage arm = new AppRegionManage();
		List<RegionItemInfo> list = arm.getAllRegionItem();
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.print(JSON.toJSONString(list));
		} finally {
			out.close();
		}
		return null;
	}

}
