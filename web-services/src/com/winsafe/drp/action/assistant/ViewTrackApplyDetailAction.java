package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.AppWlmIdcodeLog;
import com.winsafe.drp.dao.FleeProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WlmIdcodeLog;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import common.Logger;

public class ViewTrackApplyDetailAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ViewTrackApplyDetailAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			//获得查询码
			String cartonCode = request.getParameter("ID").toUpperCase().trim();
			//查询
			WlmIdcodeService wis = new WlmIdcodeService(true);
			Map<String,Object> map = wis.execute(cartonCode,users);
			
			//用于显示
			//查询码为小码时，显示小码
			if (map.get("viewPc") != null) {
				request.setAttribute("viewPc", map.get("viewPc"));
			}
			//显示产品信息
			if (map.get("p") != null) {
				request.setAttribute("p", map.get("p"));
			}
			//显示printjob信息
			if (map.get("pg") != null) {
				request.setAttribute("pg", map.get("pg"));
			}
			//显示箱码
			if (map.get("existString") != null) {
				request.setAttribute("existString", map.get("existString"));
			}
			//显示箱码信息
			if (map.get("cc") != null) {
				request.setAttribute("cc", map.get("cc"));
			}
			//显示小码信息
			if (map.get("pc") != null) {
				request.setAttribute("pc", map.get("pc"));
			}
			//物流信息
			if (map.get("lttall") != null) {
				request.setAttribute("lttall", map.get("lttall"));
			}
			//显示提示信息
			if (map.get("prompt") != null) {
				request.setAttribute("prompt", map.get("prompt"));
			}
			//显示物流信息
			if (map.get("wlmessage") != null) {
				request.setAttribute("wlmessage", map.get("wlmessage"));
			}
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
		} 
		return new ActionForward(mapping.getInput());
	}
}

