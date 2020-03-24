package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;

public class DelTransferRelationAction extends BaseAction {
	private AppSTransferRelation app = new AppSTransferRelation();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("ID");
			STransferRelation s = app.getSTransferRelationById(id);
			delOrganVisit(s.getOppOrganId(), s.getOrganizationId());
			app.delSTransferRelation(id);
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void delOrganVisit(String parentid,String oid) throws Exception {
		AppOrganVisit aov = new AppOrganVisit();
		AppWarehouseVisit appWV = new AppWarehouseVisit();
		//增加业务往来机构
		aov.delOrganVisit(oid, parentid);
		//增加业务往来仓库
		appWV.delWarehousVisit(oid, parentid);
	}

}
