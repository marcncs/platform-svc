package com.winsafe.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.metadata.SapFileType;

public class TollerChangeLogAction extends BaseAction{
	private static Logger logger = Logger.getLogger(TollerChangeLogAction.class);
	
	AppSapUploadLog appSapUploadLog = new AppSapUploadLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		initdata(request);
		String condition = "  makeOrganId in ( select makeorganid from Warehouse where id in " +
				"(select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "+ userid +" and activeFlag = 1))";
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String id = (String)map.get("id");
		if(!StringUtil.isEmpty(id)) {
			try {
				Integer.parseInt(id);
			} catch (Exception e) {
				map.put("id", 0);
			}
		}
		String[] tablename = { "UploadSAPLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
		whereSql = whereSql + timeCondition;
		// 发货日志，与发票日志放在一起
		//whereSql = whereSql + "fileType != "+SapFileType.DELIVERY.getDatabaseValue();
		whereSql = whereSql + "fileType = "+SapFileType.TollerChange.getDatabaseValue() +" and "+condition+" and ";
		String blur = DbUtil.getOrBlur2(map, tmpMap, "fileName" );
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List sapUploadLogs = appSapUploadLog.getSapUploadLog(request, pagesize, whereSql);
		
		request.setAttribute("sapUploadLogs", sapUploadLogs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
