package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
public class AjaxGetWarehouseAction  extends BaseAction {
	Logger logger = Logger.getLogger(AjaxGetWarehouseAction.class);
	private AppWarehouse aw = new AppWarehouse();
	private  AppRuleUserWH aru = new AppRuleUserWH();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String ln = request.getParameter("ln");
		String type = request.getParameter("type");
		Warehouse w = null;
		try {
			if(DbUtil.isDealer(users)) {//渠道用户
				if(type != null && "rw".equals(type)){
					List<Warehouse> warehouseList = aru.queryRuleUserWh(users.getUserid(),ln);
					if(warehouseList != null && warehouseList.size() > 0){
						w = warehouseList.get(0);
					}
				}else {
					//获得可用的仓库
					List warehouseList = new ArrayList();
					if (!StringUtil.isEmpty(ln)) {
						warehouseList = aw.getCanUseWarehouseByOid(ln);
						if (warehouseList.size()>0) {
							w = (Warehouse)warehouseList.get(0);
						}
					}
				}
			} else {
				//获得可用的仓库
				List warehouseList = new ArrayList();
				if (!StringUtil.isEmpty(ln)) {
					warehouseList = aw.getCanUseWarehouseByOid(ln);
					if (warehouseList.size()>0) {
						w = (Warehouse)warehouseList.get(0);
					}
				}
			}
			
			JSONObject json = new JSONObject();
			if (w != null) {
				json.put("w", w);
			}else {
				json.put("w", "");
			}
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			logger.debug(out);
			out.write(json.toString());
			out.close();
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

}

