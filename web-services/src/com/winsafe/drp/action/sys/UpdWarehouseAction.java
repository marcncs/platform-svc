package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

public class UpdWarehouseAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String id = request.getParameter("id");
			WarehouseService aw = new WarehouseService();
			Warehouse w = aw.getWarehouseByID(id);
			Warehouse oldw = (Warehouse)BeanUtils.cloneBean(w);
			w.setWarehousename(request.getParameter("warehousename"));
			w.setDept(RequestTool.getInt(request, "dept"));
			w.setUsername(RequestTool.getString(request, "username"));
			w.setWarehousetel(request.getParameter("warehousetel"));
			w.setWarehouseproperty(Integer.valueOf(request
					.getParameter("warehouseproperty")));
			w.setProvince(RequestTool.getInt(request, "province"));
			w.setCity(RequestTool.getInt(request, "city"));
			w.setAreas(RequestTool.getInt(request, "areas"));
			w.setWarehouseaddr(request.getParameter("warehouseaddr"));
			w.setUseflag(Integer.valueOf(request.getParameter("useflag")));
			w.setRemark(request.getParameter("remark"));
			w.setIsautoreceive(RequestTool.getInt(request, "isautoreceive"));
			//设置是否负库存
			w.setIsMinusStock(RequestTool.getInt(request, "isMinusStock"));
			w.setShortname(request.getParameter("shortname"));
			w.setNccode(request.getParameter("nccode"));
			
			//设置上限
			w.setHighNumber(request.getParameter("highNumber"));
			//设置下限
			w.setBelowNumber(request.getParameter("belowNumber"));
			w.setModificationTime(DateUtil.getCurrentDate());
			
			aw.updWarehouse(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request, "修改编号:"+w.getId(),oldw, w);
			return mapping.findForward("updResult");
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
