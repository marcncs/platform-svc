package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
* 库存预警报表
* @Title: AlarmByProductStockpileAllAction.java
* @author: WEILI 
* @CreateTime: 2013-04-25
* @version: 1.0
 */
public class AlarmByProductStockpileAllAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		AppWarehouse aw = new AppWarehouse();
		AppProductStockpileAll apsa = new AppProductStockpileAll();
		
		//获取页面检索条件中的参数
		String outOrganID = request.getParameter("MakeOrganID");
		String outWarehouseID = request.getParameter("WarehouseID");
		map = request.getParameterMap();
		tmpMap = EntityManager.scatterMap(map);
		String blur = DbUtil.getOrBlur(map, tmpMap, "w.warehousename", "w.id");
		
		String whereSql = " where 1=1 and ";
		whereSql += blur;
		if(null != outOrganID && !"".equals(outOrganID)){
			whereSql += " w.makeorganid = '"+outOrganID+"' and ";
		}else{
			whereSql += " w.makeorganid = '"+users.getMakeorganid()+"' and ";
		}
		if(null != outWarehouseID && !"".equals(outWarehouseID)){
			whereSql += " w.id = '"+outWarehouseID+"' and";
		}
		whereSql = DbUtil.getWhereSql(whereSql); 
		
		//根据当前用户的所属机构查询所有仓库
		List<Warehouse> warehouseList = aw.getWarehouse(request, pageSize, whereSql);
		//循环仓库集合
		for (Warehouse warehouse : warehouseList) {
			//根据仓库ID查询所有库存量
			double stockpileNumber = apsa.getProductStockpileAllByWID(warehouse.getId());
			warehouse.setStockpile(stockpileNumber);
			/**
			 * 判断库存状态
			 */
			if(null == warehouse.getHighNumber() || "".equals(warehouse.getHighNumber())){
				warehouse.setStockpilestruts(0);
				continue;
			}
			if(null == warehouse.getBelowNumber() || "".equals(warehouse.getBelowNumber())){
				warehouse.setStockpilestruts(0);
				continue;
			}
			//如果库存大于上限
			if(stockpileNumber > Double.valueOf(warehouse.getHighNumber()).doubleValue()){
				warehouse.setStockpilestruts(2);
			}
			//如果库存大于下限小于上限
			if(stockpileNumber > Double.valueOf(warehouse.getBelowNumber()).doubleValue() && 
					stockpileNumber < Double.valueOf(warehouse.getHighNumber()).doubleValue()){
				warehouse.setStockpilestruts(0);
			}
			//如果库存小于下限
			if(stockpileNumber < Double.valueOf(warehouse.getBelowNumber()).doubleValue()){
				warehouse.setStockpilestruts(1);
			}
		}
		//获取检索条件中的库存状态并判断集合中的数据
		List<Warehouse> wList = new ArrayList<Warehouse>();
		String stockpilestruts = request.getParameter("sstruts");
		if(null != stockpilestruts && !"".equals(stockpilestruts)){
			if(!"-1".equals(stockpilestruts)){
				for (Warehouse warehouse : warehouseList) {
					//如果页面传值的库存状态与集合中的数据相等
					if(stockpilestruts.equals(String.valueOf(warehouse.getStockpilestruts()))){
						wList.add(warehouse);
					}
				}
			}else{
				wList = warehouseList;
			}
		}else{
			wList = warehouseList;
		}
		
		request.setAttribute("warehouseList", wList);
		
		request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
		request.setAttribute("OName", request.getParameter("OName"));
		request.setAttribute("KeyWord", request.getParameter("KeyWord"));
		request.setAttribute("sstruts", request.getParameter("sstruts"));
		DBUserLog.addUserLog(userid,  7,"仓库管理>>库存预警报表");
		return mapping.findForward("success");
	}
}
