package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;

/**
 * @author : jerry
 * @version : 2009-9-4 下午03:09:40
 * www.winsafe.cn
 */
public class ToUploadProduceReportAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppWarehouse awh=new AppWarehouse();
		List<Warehouse> warehouses=new ArrayList();
		warehouses=awh.getWarehouseByProperty("3");
		request.setAttribute("warehouses", warehouses);
		request.setAttribute("type", request.getParameter("type"));
		if(request.getParameter("type").equals("1")){
			return mapping.findForward("upload");
		}else{
			return mapping.findForward("preupload");
		}
	}
}
