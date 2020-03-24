package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentDetail;
import com.winsafe.drp.dao.SaleIndentDetailForm;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/SaleOrder.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppSaleIndent asb = new AppSaleIndent();
			SaleIndent so = asb.getSaleIndentByID(id);
			AppUsers au = new AppUsers();

			OrganService os = new OrganService();
			Map<String, Object> parameters = new HashMap<String, Object>();
			Organ organ = os.getOrganByID(so.getMakeorganid());
			parameters.put("organtitle", organ.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);

			parameters.put("billtype", "Sales Order 零售订单");
			parameters.put("id", id);
			parameters.put("consignmentdate", DateUtil.formatDate(so
					.getConsignmentdate()));
			parameters.put("billno", so.getCustomerbillid());
			Integer transit = so.getTransit()==null?0:so.getTransit();
			parameters.put("transitname", HtmlSelect.getResourceName(request,"Transit", transit));
			PaymentModeService pms = new PaymentModeService();
			parameters.put("paymentmodename", pms.getPaymentModeName(so
					.getPaymentmode()));
			String transportmode = HtmlSelect.getNameByOrder(request,
					"TransportMode", so.getTransportmode());
			parameters.put("transportmodename", transportmode);

			parameters.put("cid", "客户编号：" + so.getCid());
			parameters.put("cname", "客户名称：" + so.getCname());
			parameters.put("receiveman", "收货人：" + so.getReceiveman());
			parameters.put("tel", "联系电话：" + so.getTel());
			parameters.put("caddr", "地址：" + so.getTransportaddr());

			parameters.put("pmobile", "");
			parameters.put("printc", "");
			parameters.put("remark", so.getRemark());

			parameters.put("makedate", DateUtil.formatDate(so.getMakedate()));
			parameters.put("makename", au.getUsersByid(so.getMakeid())
					.getRealname());
			parameters.put("totalsum", so.getTotalsum());
			parameters.put("totalsumcapital", DataValidate.arabiaToChinese(so
					.getTotalsum().toString()));

			AppSaleIndentDetail asbd = new AppSaleIndentDetail();
			List<SaleIndentDetail> sbls = asbd.getSaleIndentDetailBySIID(id);
			List<SaleIndentDetailForm> als = new ArrayList<SaleIndentDetailForm>();

			for (SaleIndentDetail ard : sbls) {
				SaleIndentDetailForm sldf = new SaleIndentDetailForm();
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | "
						+ ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setDiscount(ard.getDiscount());
				sldf.setTaxrate(ard.getTaxrate());
				sldf.setSubsum(ard.getSubsum());
				als.add(sldf);
			}

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(als));

			{
				byte[] bytes = JasperExportManager.exportReportToPdf(print);
				if (bytes != null && bytes.length > 0) {
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					ServletOutputStream ouputStream = response
							.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}
			}
			initdata(request);
			DBUserLog.addUserLog(userid, 4, "打印零售订单");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
