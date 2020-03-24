package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;

public class ToAddStockAlterMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToAddStockAlterMoveAction.class);
	
	private AppWarehouse appw = new AppWarehouse();
	private AppUserVisit appUserVisit = new AppUserVisit();
	private  AppRuleUserWH aru = new AppRuleUserWH();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			initdata(request);
			
			try {
				
				OrganService os = new OrganService();
				
				//页面初始化时,判断用户自己的机构有没有管辖,如果有默认加载机构和仓库
				UserVisit userVisit = appUserVisit.findVisitOrgan(userid, users.getMakeorganid());
				
				Organ o = os.getOrganByID(users.getMakeorganid());  //取发货机构ID
				
				if(userVisit != null){
					request.setAttribute("MakeOrganID", users.getMakeorganid());
					request.setAttribute("oname", users.getMakeorganname());
					request.setAttribute("oModel", o.getOrganModel());
					List warehouseList  = aru.queryRuleUserWh(users.getUserid(),users.getMakeorganid());
					if (warehouseList != null && warehouseList.size()>0) {
						Warehouse warehouse = (Warehouse)warehouseList.get(0);
						request.setAttribute("WarehouseID", warehouse.getId());
						request.setAttribute("wname", warehouse.getWarehousename());
					}
				}
				return mapping.findForward("success");
			} catch (Exception e) {
				logger.error("",e);
			}
			return new ActionForward(mapping.getInput());
	}
}
