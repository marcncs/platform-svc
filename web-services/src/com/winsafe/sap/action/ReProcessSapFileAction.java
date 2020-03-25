package com.winsafe.sap.action;

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
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.metadata.SapFileType;

public class ReProcessSapFileAction extends BaseAction{
private static Logger logger = Logger.getLogger(ReProcessSapFileAction.class);
	
	private AppSapUploadLog appUploadSAPlog = new AppSapUploadLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		String menuType = request.getParameter("menuType");
		initdata(request,menuType);
		Integer uploadId = Integer.valueOf(request.getParameter("uploadId"));
		try {
			appUploadSAPlog.reProcessFile(uploadId);
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "UploadSAPLog" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap," makeDate");
			whereSql = whereSql + timeCondition;
			
			
	/*		String condition = "  makeOrganId in ( select makeorganid from Warehouse where id in " +
			"(select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "+ userid +" and activeFlag = 1))";
			
			String fileType = (String)map.get("reProFileType");
			if(fileType.equals(SapFileType.DELIVERY.getDatabaseValue())
					|| fileType.equals(SapFileType.INVOICE.getDatabaseValue())) {
				whereSql = whereSql + "(fileType = "+SapFileType.DELIVERY.getDatabaseValue()+ " or fileType = "+SapFileType.INVOICE.getDatabaseValue() + ") and ";
				request.setAttribute("category", "delivery");
			} else {
				whereSql = whereSql + "fileType != "+SapFileType.DELIVERY.getDatabaseValue() +" and fileType != "+SapFileType.INVOICE.getDatabaseValue() +" and ";
			}
			if(fileType.equals(SapFileType.SAPCODE.getDatabaseValue())){
				whereSql = whereSql +condition +"and fileType = "+SapFileType.SAPCODE.getDatabaseValue();
			}*/
			
			if(menuType.equals("1")){
				whereSql = whereSql + "(fileType = "+SapFileType.DELIVERY.getDatabaseValue()+ " or fileType = "+SapFileType.INVOICE.getDatabaseValue() + ") and ";
				request.setAttribute("category", "delivery");
			}else{
				whereSql = whereSql + "fileType != "+SapFileType.DELIVERY.getDatabaseValue() +" and fileType != "+SapFileType.INVOICE.getDatabaseValue() +" and fileType != "+SapFileType.SAPCODE.getDatabaseValue() +
				" and ";
			}
			
			whereSql = DbUtil.getWhereSql(whereSql);
			
			
			List sapUploadLogs = appUploadSAPlog.getSapUploadLog(request, pagesize, whereSql);
						
			request.setAttribute("sapUploadLogs", sapUploadLogs);
		} catch (Exception e) {
			request.setAttribute("result", "databases.del.fail");
			e.printStackTrace();
		}
		DBUserLog.addUserLog(request, "编号：" + uploadId);
		if(menuType.equals("1")){
			return mapping.findForward("listsapdelivery");
		}else{
			return mapping.findForward("listsapupload");
		}
	}
}
