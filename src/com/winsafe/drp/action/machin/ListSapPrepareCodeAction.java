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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.pojo.PrepareCode;
import com.winsafe.erp.pojo.PrepareCodeForm;

public class ListSapPrepareCodeAction extends BaseAction {

	//AppCovertErrorLog appCovertErrorLog = new AppCovertErrorLog();
	AppPrepareCode appPrepareCode = new AppPrepareCode();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize= 20;
		String code = request.getParameter("dcode");
		if(StringUtil.isEmpty(code)){
			
			try {
				String Condition = " and  aaa.organId in ( select makeorganid from Warehouse where id in" +
						" (select ruw.warehouse_Id from Rule_User_Wh ruw where ruw.user_Id = "+ userid +" and activeFlag = 1))";
				
				Map paraMap = new HashMap(request.getParameterMap());
				if(!StringUtil.isEmpty((String)paraMap.get("search"))) {
					List<PrepareCodeForm> prepareCodes = new ArrayList<PrepareCodeForm>();
					List<Map> lista = appPrepareCode.getPrepareCodes(request, pagesize, paraMap,Condition);
					
					for(Map map : lista){
						PrepareCodeForm bean = new PrepareCodeForm();
						//将Map中对应的值赋值给实例
						MapUtil.mapToObject(map, bean);
//						bean.setModifiedDate(Dateutil.formatDatetime(Dateutil.formatDateTime(bean.getModifiedDate())));
						prepareCodes.add(bean);
					}
					request.setAttribute("list", prepareCodes);
					//已用数量
					Integer countAll = appPrepareCode.getPrepareCodeUsedAll(paraMap,Condition);
					Integer countUsed = appPrepareCode.getPrepareCodeUsedCount(paraMap,Condition);
					Integer countPur = countAll-countUsed;
					Integer countRelease = appPrepareCode.getPrepareCodeRelease(paraMap,Condition);
					
					request.setAttribute("countAll", countAll);
					request.setAttribute("countUsed", countUsed-countRelease);
					request.setAttribute("countPur", countPur);
					request.setAttribute("countRelease", countRelease);
				}else{
					request.setAttribute("countAll", 0);
					request.setAttribute("countUsed", 0);
					request.setAttribute("countPur", 0);
					request.setAttribute("countPur", 0);
					request.setAttribute("countRelease", 0);
				}
				DBUserLog.addUserLog(request,"列表");
				return mapping.findForward("list");
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			String whereSql = " where tcode = '"+code+"' or (code = '"+code+"' and tcode !='t')  order by code ";
			List<PrepareCode> prepareCodes = appPrepareCode.getPrepareCodeByWhere(whereSql);
			request.setAttribute("codes", prepareCodes);
			return mapping.findForward("detail");
		}
		
		return super.execute(mapping, form, request, response);
	}

}
