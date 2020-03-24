package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;

public class ToAddStockMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToAddStockMoveAction.class);
	private AppWarehouse appw = new AppWarehouse();
	private AppOlinkMan aom = new AppOlinkMan();
	private Olinkman olinkman = new Olinkman();
	private AppUserVisit appUserVisit = new AppUserVisit();
	private  AppRuleUserWH aru = new AppRuleUserWH();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try {
			// UsersBean users = UserManager.getUser(request);
			// AppOrgan ao = new AppOrgan();
			// String organid = users.getMakeorganid();
			// String organname = ao.getOrganByID(users.getMakeorganid())
			// .getOrganname();
			// request.setAttribute("organid", organid);
			// request.setAttribute("organname", organname);
			// 页面初始化时，默认机构和仓库
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
				olinkman = aom.getMainLinkmanByCid(users.getMakeorganid());
				request.setAttribute("olinkman", olinkman.getName());
				if (!StringUtil.isEmpty(olinkman.getMobile())) {
					request.setAttribute("otel", olinkman.getMobile());
				}else {
					request.setAttribute("otel", olinkman.getOfficetel());
				}
			}
			return mapping.findForward("toadd");
		} catch (Exception e) {
			logger.error("",e);
		}
		return new ActionForward(mapping.getInput());
	}
}
