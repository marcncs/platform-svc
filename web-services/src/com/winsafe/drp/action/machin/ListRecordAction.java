package com.winsafe.drp.action.machin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author: jerry
 * @version:2009-10-13 下午05:44:30
 * @copyright:www.winsafe.cn
 */
public class ListRecordAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize=20;
		try {
			initdata(request);
	    	String Condition = "";
	    	String fileName = request.getParameter("FileName");
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "UploadPrLog"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "FileName","FilePath");
			whereSql = whereSql + blur+ timeCondition + Condition; 
			if(!StringUtil.isEmpty(fileName)) {
				whereSql = whereSql + " filename = '" +fileName+ "'";
			}
			whereSql = DbUtil.getWhereSql(whereSql); 
			

			RecordDao appA=new  RecordDao();
			List lista = appA.getAllRecord(request, whereSql, pageSize);
			
			request.setAttribute("list", lista);
			DBUserLog.addUserLog(request,"[列表]");
			return mapping.findForward("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
