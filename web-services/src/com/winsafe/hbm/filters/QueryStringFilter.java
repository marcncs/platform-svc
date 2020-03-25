package com.winsafe.hbm.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.winsafe.utils.StringUtils;

public class QueryStringFilter implements Filter {
	
	private static Set<String> urlSet = new HashSet<String>();
	
	static {
		initUrl();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private static void initUrl() {
		urlSet.add("/fwmApplyController/list.do");
		urlSet.add("/dashboard");
		urlSet.add("/fwmApplyController/listConfirm.do");
		urlSet.add("/fwmApplyController/listExport.do");
		urlSet.add("/page/public/messages/Messages.properties");
		urlSet.add("/page/public/messages/Messages_zh.properties");
		urlSet.add("/queryFlowController/queryflowts.do");
		urlSet.add("/takeTicketController/list.do");
		urlSet.add("/microCertificateController/list.do");
		urlSet.add("/fwmApplyController/ajaxExportFwmApply.do");
		urlSet.add("/queryFlowController/queryflowts.do");
		urlSet.add("/microCertificateController/list.do");
		urlSet.add("/microCertificateController/listExport.do");
		urlSet.add("/microCertificateController/listCert.do");
		urlSet.add("/microCertificateController/listConfirm.do");
		urlSet.add("/roleController/list.do");
		urlSet.add("/microCertificateController/previewMicroCert.do");
		urlSet.add("/wwReportController/checkHistory.do");
		urlSet.add("/userController/list.do");
		urlSet.add("/queryFlowController/queryFlowReport.do");
		urlSet.add("/stockAlterMoveController/toListStockAlterMoveReceive.do");
		urlSet.add("/wwReportController/fakeAlarm.do");
		urlSet.add("/productStockpileController/toListProductStockpile.do");
		urlSet.add("/productIncomeController/toListAuditedProductIncome.do");
		
		urlSet.add("/productController/list.do");
		urlSet.add("/scannerController/list.do");
		urlSet.add("/queryFlowController/flowlist.do");
		urlSet.add("/auditManagementController/list.do");
		urlSet.add("/idcodeUploadController/toListIdcodeUploadLog.do");
		urlSet.add("/inventoryAccountingController/toListInventoryAccounting.do");
		
		urlSet.add("/productIncomeController/toListProductIncome.do");
		urlSet.add("/productJobController/toListProcessOrder.do");
		
		urlSet.add("/labelDesignController/list.do");
		urlSet.add("/labelDesignController/list.do");
		urlSet.add("/otherShipmentController/list.do");
		urlSet.add("/productBrandController/list.do");
		urlSet.add("/productJobController/uploadlist.do");
		urlSet.add("/fwmQueryLogController/reportlist.do");
		
		urlSet.add("/fwmQueryLogController/jahwaechart.do");
		urlSet.add("/moduleController/list.do");
		urlSet.add("/companyController/list.do");
		urlSet.add("/sapUploadController/toListSapUploadLog.do");
		urlSet.add("/invoiceDetailController/list.do");
		urlSet.add("/invoiceUploadController/list.do");
		urlSet.add("/labelIDHController/toLabelIDHList.do");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String url = request.getServletPath() ;
		
		String params = request.getQueryString();
		//locale=
		if(request.getMethod().equals("GET")
				&& urlSet.contains(url)
//				&& !StringUtils.isEmpty(params)
				){
			request.getRequestDispatcher("error.jsp").forward(request,response);  
		} 
		filterChain.doFilter(request, response);  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
