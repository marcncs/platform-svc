package com.winsafe.drp.action.users;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;


public class AjaxOrganWarehouseAction extends Action {
	private  AppRuleUserWH aru = new AppRuleUserWH();
	private static Logger logger = Logger.getLogger(AjaxOrganWarehouseAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String oid =request.getParameter("oid");
		try {
			String type = request.getParameter("type");
			List<Warehouse> warehouseList = new ArrayList<Warehouse>();
			if(DbUtil.isDealer(users)) {//渠道用户
				if("rw".equals(type)){
					//获取管辖仓库（设置仓库）rule_user_wh
					warehouseList = aru.queryRuleUserWh(users.getUserid(),oid);
				}else if("vw".equals(type)){
					//获取业务往来仓库（许可）warehouse_visit

					AppWarehouse aw = new AppWarehouse();
					warehouseList = aw.getEnableWarehouseByvisit(users.getUserid(),oid);
				}else{
		//			if ( "".equals(oid) ){
		//				oid = users.getMakeorganid();
		//			}
					WarehouseService applm = new WarehouseService();
					warehouseList = applm.getCanUseWarehouseByOid(oid);
				}
			} else {
				WarehouseService applm = new WarehouseService();
				warehouseList = applm.getCanUseWarehouseByOid(oid);
			}
			

			if(warehouseList==null)return null;
			JSONArray deptlist = new JSONArray();
			for (Warehouse w : warehouseList ){
				deptlist.put(w);
			}
			
			JSONObject json = new JSONObject();			
			json.put("warehouselist", deptlist);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
