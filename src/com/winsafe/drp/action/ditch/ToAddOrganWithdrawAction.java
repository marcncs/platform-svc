package com.winsafe.drp.action.ditch;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:13:03 www.winsafe.cn
 */
public class ToAddOrganWithdrawAction extends BaseAction {
	private AppWarehouse appw = new AppWarehouse();
	private AppOlinkMan al = new AppOlinkMan();
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
		//页面初始化时，默认机构和仓库
		UserVisit userVisit = appUserVisit.findVisitOrgan(userid, users.getMakeorganid());
		if(userVisit != null){
			Olinkman linkman = al.getMainLinkmanByCid(users.getMakeorganid());
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("plinkman", linkman.getName());
			if (!StringUtil.isEmpty(linkman.getMobile())) {
				request.setAttribute("Tel", linkman.getMobile());
			}else {
				request.setAttribute("Tel", linkman.getOfficetel());
			}
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
