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
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.Internation;

public class PrintSaleOrderAction extends BaseAction {

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

			AppSaleOrder asl = new AppSaleOrder();
			AppUsers au = new AppUsers();
			SaleOrder so = asl.getSaleOrderByID(id);

			OrganService os = new OrganService();
			Map<String, Object> parameters = new HashMap<String, Object>();
			Organ organ = os.getOrganByID(so.getMakeorganid());
			parameters.put("title", "");
			parameters.put("organtitle", organ.getOrganname());
			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);

			parameters.put("billtype", "Retail Order/Note 零售单");
			parameters.put("id", id);
			parameters.put("consignmentdate", DateUtil.formatDate(so
					.getConsignmentdate()));
			parameters.put("billno", so.getCustomerbillid());
			parameters.put("transitname", HtmlSelect.getResourceName(request,
					"Transit", so.getTransit()));
			PaymentModeService pms = new PaymentModeService();
			parameters.put("paymentmodename", pms.getPaymentModeName(so
					.getPaymentmode()));
			String transportmode = HtmlSelect.getNameByOrder(request,
					"TransportMode", so.getTransportmode());
			parameters.put("transportmodename", transportmode);

			parameters.put("cid", "客户编号：" + so.getCid());
			parameters.put("cname", "客户名称：" + so.getCname());
			parameters.put("receiveman", "收货人：" + so.getReceiveman());
			parameters.put("tel", "联系电话：" + so.getReceivetel());
			parameters.put("caddr", "地址：" + so.getTransportaddr());

			parameters.put("pmobile", "");
			parameters.put("printc","");
			parameters.put("remark", so.getRemark());

			parameters.put("makedate", DateUtil.formatDate(so.getMakedate()));
			parameters.put("makename", au.getUsersByid(so.getMakeid())
					.getRealname());
			parameters.put("totalsum", so.getTotalsum());
			parameters.put("totalsumcapital", DataValidate.arabiaToChinese(so
					.getTotalsum().toString()));

			AppSaleOrderDetail asld = new AppSaleOrderDetail();
			List<SaleOrderDetail> sals = asld.getSaleOrderDetailBySoid(id);
			List<SaleOrderDetailForm> als = new ArrayList<SaleOrderDetailForm>();
			for (SaleOrderDetail o : sals) {
				SaleOrderDetailForm sodf = new SaleOrderDetailForm();
				sodf.setProductid(o.getProductid());
				sodf.setProductname(o.getProductname() + " | "
						+ o.getSpecmode());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", o.getUnitid().intValue()));
				sodf.setUnitprice(o.getUnitprice());
				sodf.setQuantity(o.getQuantity());
				sodf.setDiscount(o.getDiscount());
				sodf.setTaxrate(o.getTaxrate());
				sodf.setSubsum(o.getSubsum());
				als.add(sodf);
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
			DBUserLog.addUserLog(userid, 4, "打印零售单");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
