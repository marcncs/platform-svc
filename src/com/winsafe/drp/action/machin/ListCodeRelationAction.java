package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCovertErrorLog;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CovertCodeBean;

/**
 * @author : ryan
 * @version : 2014-10-20 下午02:29:31
 * www.winsafe.cn
 */
public class ListCodeRelationAction extends BaseAction {

	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize= 20;
		try {
			Map paraMap = new HashMap(request.getParameterMap());
			if(!StringUtil.isEmpty((String)paraMap.get("search"))
					&& (!StringUtil.isEmpty((String)paraMap.get("primaryCode"))
					|| !StringUtil.isEmpty((String)paraMap.get("cartonCode"))
					|| !StringUtil.isEmpty((String)paraMap.get("covertCode")))) {
				List<Map<String,String>> lista = appPrimaryCode.getCodeRelation(request, pagesize, paraMap,userid);
				request.setAttribute("list", lista);
			}
			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

}
