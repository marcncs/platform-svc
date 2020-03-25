package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBillDetailForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			String realpath = "/WEB-INF/reports/ShipmentBill.jasper";
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath(realpath);
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppShipmentBill aps = new AppShipmentBill();
			ShipmentBill sb = aps.getShipmentBillByID(id);

			OrganService ao = new OrganService();
			Organ o = ao.getOrganByID(users.getMakeorganid());

			String billtype = "Settlement Note 结算单";

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");

			String path = "";
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);
			parameters.put("billtype", billtype);
			parameters.put("billno", sb.getId());
			parameters.put("makedate", DateUtil.formatDate(DateUtil
					.getCurrentDate()));
			parameters.put("id", sb.getId());

			WarehouseService ws = new WarehouseService();
			Warehouse wh = ws.getWarehouseByID(sb.getInwarehouseid());
			if (wh != null) {
				parameters.put("wconsignee", "");
				parameters.put("wcompany", "收货仓库：" + wh.getWarehousename());
				parameters.put("waddr", "地址：" + wh.getWarehouseaddr());
				parameters.put("wtel", "联系人：" + wh.getWarehousename());
				parameters.put("wmobile", "电话：" + wh.getWarehousetel());
			} else {
				parameters.put("wconsignee", "");
				parameters.put("wcompany", "");
				parameters.put("waddr", "");
				parameters.put("wtel", "");
				parameters.put("wmobile", "");
			}

			parameters.put("consignee", sb.getLinkman());
			parameters.put("company", "单位：" + sb.getCname());
			parameters.put("paddr", "地址：" + sb.getReceiveaddr());
			parameters.put("ptel", "电话：" + sb.getTel());
			parameters.put("pmobile", "手机：" + sb.getCmobile());

			parameters.put("printc", "");

			if (sb.getPrinttimes() == null || sb.getPrinttimes() == 0) {
				sb.setPrinttimes(1);
			}
			sb.setPrinttimes(sb.getPrinttimes() + 1);
			aps.updShipmentBill(sb);

			AppShipmentBillDetail apbd = new AppShipmentBillDetail();
			List<ShipmentBillDetail> pbls = apbd
					.getShipmentBillDetailBySbID(id);
			List<ShipmentBillDetailForm> als = new ArrayList<ShipmentBillDetailForm>();
			double totalsum = 0.00;
			for (ShipmentBillDetail ttd : pbls) {
				ShipmentBillDetailForm sldf = new ShipmentBillDetailForm();
				sldf.setProductid(ttd.getProductid());
				sldf.setProductname(ttd.getProductname() + " | "
						+ ttd.getSpecmode());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(ttd.getUnitid()
								.toString())));
				sldf.setUnitprice(ttd.getUnitprice());
				sldf.setQuantity(ttd.getQuantity());
				sldf.setSubsum(ttd.getSubsum());
				totalsum += sldf.getSubsum() == null ? 0 : sldf.getSubsum();
				als.add(sldf);
			}
			parameters.put("totalsum", totalsum);
			parameters.put("totalsumcapital", NumToChRMB.numToRMB(String
					.valueOf(totalsum)));
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
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 8, "打印结算单");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
