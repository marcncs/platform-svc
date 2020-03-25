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
import com.winsafe.sap.dao.AppCovertErrorLog;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CovertCodeBean;

/**
 * @author : ryan
 * @version : 2014-10-20 下午02:29:31
 * www.winsafe.cn
 */
public class ListUploadProduceReportAction extends BaseAction {

	AppCovertErrorLog appCovertErrorLog = new AppCovertErrorLog();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize= 20;
		try {
			Map paraMap = new HashMap(request.getParameterMap());
			if(!StringUtil.isEmpty((String)paraMap.get("search"))) {
				List<CovertCodeBean> covertCodes = new ArrayList<CovertCodeBean>();
				List<Map> lista = appCovertErrorLog.getCovertCodes(request, pagesize, paraMap);
				for(Map map : lista){
					CovertCodeBean bean = new CovertCodeBean();
					//将Map中对应的值赋值给实例
					MapUtil.mapToObject(map, bean);
					if(bean.getErrorType() == -3) {
						bean.setBatchNumber(null);
						bean.setProductId(null);
						bean.setProductName(null);
						bean.setTdCode(null);
					}
					covertCodes.add(bean);
				}
				request.setAttribute("list", covertCodes);
			}
			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

}
