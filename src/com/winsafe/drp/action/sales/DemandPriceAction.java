package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.AppDemandPriceDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DemandPrice;
import com.winsafe.drp.dao.DemandPriceDetailForm;
import com.winsafe.drp.dao.DemandPriceForm;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.Internation;

public class DemandPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Long id = Long.valueOf(request.getParameter("ID"));
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/DemandPrice.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppDemandPrice asl = new AppDemandPrice();
			DemandPrice so = new DemandPrice();
			AppUsers au = new AppUsers();
			AppCustomer ac = new AppCustomer();
			so = asl.getDemandPriceByID(id);
			DemandPriceForm sof = new DemandPriceForm();

			sof.setId(id);
			sof.setCid(so.getCid());
			sof.setCidname(ac.getCustomer(so.getCid()).getCname());
			sof.setLinkman(so.getLinkman());
			sof.setTel(so.getTel());
			sof.setDemandname(so.getDemandname());
			sof.setMakedate(String.valueOf(so.getMakedate()));
//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());

			Map parameters = new HashMap();
			parameters.put("title", Internation.getStringByKeyPosition(
					"PrinterName", request, 0, "global.sys.SystemResource"));
			parameters.put("billtype", Internation.getStringByKeyPosition(
					"PrinterName", request, 12, "global.sys.SystemResource"));
			parameters.put("id", id.toString());
			parameters.put("cid", sof.getCid());
			parameters.put("cidname", sof.getCidname());
			parameters.put("linkman", sof.getLinkman());
			parameters.put("tel", sof.getTel());
			parameters.put("demandname", sof.getDemandname());
			parameters.put("makedate", sof.getMakedate().toString().substring(
					0, 10));
			parameters.put("makeidname", sof.getMakeidname());
			parameters.put("remark", sof.getRemark());
			parameters.put("totalsum", sof.getTotalsum());
			parameters.put("totalsumcapital", DataValidate.arabiaToChinese(sof
					.getTotalsum().toString()));

			AppDemandPriceDetail asld = new AppDemandPriceDetail();
			List sals = asld.getDemandPriceDetailByDpID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				DemandPriceDetailForm sodf = new DemandPriceDetailForm();
				Object[] o = (Object[]) sals.get(i);
				sodf.setProductid(o[2].toString());
				sodf.setProductname(o[3].toString());
				sodf.setSpecmode(o[4].toString());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(o[5].toString())));
				sodf.setUnitprice(Double.valueOf(o[6].toString()));
				sodf.setQuantity(Double.valueOf(o[7].toString()));
				sodf.setSubsum(Double.valueOf(o[8].toString()));
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
			// return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
