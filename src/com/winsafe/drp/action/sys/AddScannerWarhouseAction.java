package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ScannerWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.StringUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 新增  修改  仓库配置采集器
* @Title: AddScannerWarhouseAction.java
* @Description: TODO
* @author: wenping 
* @CreateTime: Jul 16, 2012 8:41:57 AM
* @version:
 */
public class AddScannerWarhouseAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String scannerid= request.getParameter("scannerid");
		String warhouseid = (request.getParameter("outwarehouseid") == "") ? request.getParameter("warehouseid")  : request.getParameter("outwarehouseid") ;
		String orgName = request.getParameter("orgname");
		AppScannerWarehouse asw = new AppScannerWarehouse();
		ScannerWarehouse scannerwarehouse= new ScannerWarehouse();
		Warehouse wh = new Warehouse();
		AppWarehouse awh = new AppWarehouse();
		try {
			wh = awh.getWhById(warhouseid);
			scannerwarehouse.setScannerid(scannerid);
			scannerwarehouse.setWarehouseid(warhouseid);
			scannerwarehouse.setOrgName(orgName);
			if(!com.winsafe.hbm.util.StringUtil.isEmpty(warhouseid)){
				scannerwarehouse.setWareHouseName(wh.getWarehousename());
			}
			if(request.getParameter("type").equals("ADD")){
				//新增配置
//				scannerwarehouse.setId(Integer.toString(asw.getMaxScannerWarehouseId()));
				scannerwarehouse.setId(MakeCode.getExcIDByRandomTableName("scanner_warehouse", 0, ""));
				asw.InsertScannerWarehouse(scannerwarehouse);
				request.setAttribute("result","databases.add.success");
				DBUserLog.addUserLog(request, "仓库编号："+warhouseid+"采集器编号"+scannerid);
			}else{
				//修改
					String swid= request.getParameter("swid");
					asw.updScannerWarehouse(Integer.parseInt(swid), scannerid, warhouseid,scannerwarehouse.getOrgName(),scannerwarehouse.getWareHouseName());
				request.setAttribute("result", "databases.upd.success");
				DBUserLog.addUserLog(userid, "采集器仓库设置", "采集器设置>>采集器仓库设置>>修改[仓库编号:"+warhouseid+"采集器编号"+scannerid+"]");
			}
			List scanwarhouses = asw.getScannersByWh(warhouseid);
			AppWarehouse aw = new AppWarehouse();
//			String wname= aw.getWarehouseByID(warhouseid).getWarehousename();
			request.setAttribute("scanwarhouses", scanwarhouses);
//			request.setAttribute("wname", wname);

			
			
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
