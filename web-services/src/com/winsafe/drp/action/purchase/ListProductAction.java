package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//dao层所需类
		AppProduct ap = new AppProduct();
		//初始化
		int pagesize = 15;
		initdata(request);
		try {
			//psid产品类别
			String pid = request.getParameter("OtherKey");
			//获取查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			//查询条件
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			//类别查询模糊查询
			StringBuffer buf = new StringBuffer();
			if (pid != null && pid.length() > 0) {
				buf.append("(");
				buf.append("psid like '" + pid + "%')");
				whereSql = whereSql + buf;
			}
			//关键字查询条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "ProductName",
					"SpecMode", "PYCode", "NCcode","mCode","regCertCode","specCode","regCertCodeFixed");

			whereSql = whereSql + blur;
			whereSql = DbUtil.getWhereSql(whereSql);
			//获取产品列表
			List apls = ap.getProduct(request, pagesize, whereSql);
			//记录操作日志
			DBUserLog.addUserLog(userid, "产品管理", "列表商品资料");
			//返回页面值
			request.setAttribute("alapls", apls);
			request.setAttribute("pid", pid);
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("UseFlag", request.getParameter("UseFlag"));
			request.setAttribute("OtherKey", request.getParameter("OtherKey"));

			return mapping.findForward("listproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
