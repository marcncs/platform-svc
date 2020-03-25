package com.winsafe.drp.action.ditch;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:13:03 www.winsafe.cn
 */
public class ToAddPlantWithdrawAction extends BaseAction {
	private AppWarehouse appw = new AppWarehouse();
	private AppUserVisit appUserVisit = new AppUserVisit();
	private AppOrgan os = new AppOrgan();
	private  AppRuleUserWH aru = new AppRuleUserWH();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String makeorganid = users.getMakeorganid();
		Organ og = os.getOrganByID(makeorganid);
		String parentOrganid =og.getParentid() ;
		if(parentOrganid.equals("0")){
			parentOrganid = makeorganid;
		}
		request.setAttribute("makeorganid", makeorganid);
		//页面初始化时,判断用户自己的机构有没有管辖,如果有默认加载机构和仓库
		UserVisit userVisit = appUserVisit.findVisitOrgan(userid, users.getMakeorganid());
		if(userVisit != null){
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("oname", users.getMakeorganname());
			List warehouseList  = aru.queryRuleUserWh(users.getUserid(),users.getMakeorganid());
			if (warehouseList != null && warehouseList.size()>0) {
				Warehouse warehouse = (Warehouse)warehouseList.get(0);
				request.setAttribute("WarehouseID", warehouse.getId());
				request.setAttribute("wname", warehouse.getWarehousename());
			}
		}
		return mapping.findForward("add");
	}
}
