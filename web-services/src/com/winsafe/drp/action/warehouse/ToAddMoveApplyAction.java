package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.Properties;

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
import com.winsafe.drp.util.JProperties;

public class ToAddMoveApplyAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToAddMoveApplyAction.class);
	private OrganService os = new OrganService();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppUserVisit appUserVisit = new AppUserVisit();
	private  AppRuleUserWH aru = new AppRuleUserWH();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Organ organ = os.getOrganByID(users.getMakeorganid());
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("MakeOrganName", users.getMakeorganname());
			request.setAttribute("organType", organ.getOrganType());
			request.setAttribute("organRank", organ.getRank());
			
			//页面初始化时,判断用户自己的机构有没有管辖,如果有默认加载机构和仓库
			UserVisit userVisit = appUserVisit.findVisitOrgan(userid, users.getMakeorganid());
			if(userVisit != null){
				List warehouseList  = aru.queryRuleUserWh(users.getUserid(),users.getMakeorganid());
				if (warehouseList != null && warehouseList.size()>0) {
					Warehouse warehouse = (Warehouse)warehouseList.get(0);
					request.setAttribute("outwarehouseid", warehouse.getId());
					request.setAttribute("wname", warehouse.getWarehousename());
				}
			}
			
			Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			String moveApplyOrganId = pro.getProperty("moveApplyOrganId")!=null?pro.getProperty("moveApplyOrganId"):"1";
			
			request.setAttribute("moveApplyOrganId", moveApplyOrganId);
			
			return mapping.findForward("toadd");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
