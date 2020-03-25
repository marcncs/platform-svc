package com.winsafe.drp.action.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.UsersService;

public class AjaxOrganWarehouseAction extends Action {

	public static List<Warehouse> warehouses = new ArrayList();
	public static List<Organ> ogs = new ArrayList();
	public static AppOrgan apo = new AppOrgan();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//清除静态变量的机构集合
		ogs.clear();
		UsersBean users = UserManager.getUser(request);
		String oid = request.getParameter("oid");
		AppWarehouse aw = new AppWarehouse();
		try {
			//如果在页面上取不到机构编号则用登录的用户的机构编号
			if ("".equals(oid)) {
				oid = users.getMakeorganid();
			}
			//通过oid找到根节点的机构
			List<Organ> tempogs = new ArrayList();
			Organ tempog = apo.getOrganByID(oid);
			tempogs.add(tempog);
			//递归遍历并将根节点下的所有机构对象放入ogs集合
			getAll(tempogs);
			JSONArray list = new JSONArray();
			List<Warehouse> warehouses = new ArrayList();
			//通过已取得的机构集合找到仓库集合
		
			for (int i = 0; i < ogs.size(); i++) {
				Organ temporg = ogs.get(i);
				List<Warehouse> whs = new ArrayList();
				whs = aw.getWarehouseListByOID(temporg.getId());
				for (int j = 0; j < whs.size(); j++) {
					Warehouse warehouse=whs.get(j);
					list.put(warehouse);
					}
			}
			JSONObject json = new JSONObject();
			json.put("userslist", list);
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 递归方法取机构及机构下的所有子机构
	 * @param organ 根机构
	 * @throws Exception
	 */
	public static void getAll(List<Organ> organ) throws Exception {
		for (int i = 0; i < organ.size(); i++) {
			Organ tempog = organ.get(i);
			ogs.add(tempog);
			List tempogs = apo.getTreeByCate(tempog.getId());
			if (tempogs != null) {
				getAll(tempogs);
			} else {
				continue;
			}
		}

	}
}
