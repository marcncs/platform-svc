package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.ProductVlidateReportForm;
import com.winsafe.drp.server.ProductVlidataReportServer;
import common.Logger;

public class ProductValidateDetailAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ProductValidateDetailAction.class);
	
	private ProductVlidataReportServer pvServer = new ProductVlidataReportServer();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化参数
		super.initdata(request);
		// 初始化分页信息
		int pageSize = 10;
		// 查询条件
		String idcode = request.getParameter("idcode");
		
		List<ProductVlidateReportForm> list = pvServer.queryDetailReport(request, pageSize, idcode,users);
		
		request.setAttribute("list", list);
		return mapping.findForward("success");
	}
}
