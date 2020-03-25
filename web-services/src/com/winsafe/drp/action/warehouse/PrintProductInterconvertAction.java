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
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvertDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintProductInterconvertAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/ProductInterconvert.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppProductInterconvert asb = new AppProductInterconvert();
			ProductInterconvert ar = asb.getProductInterconvertByID(id);
			AppUsers au = new AppUsers();
			OrganService os = new OrganService();
			AppWarehouse aw = new AppWarehouse();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			Organ o = os.getOrganByID(ar.getMakeorganid());
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);
			 
			parameters.put("billtype", "Product Conversion 产品互转单");
			parameters.put("id", id);
			parameters.put("movedate", DateUtil.formatDate(ar.getMakedate()));

			String warehouse = aw.getWarehouseByID(ar.getInwarehouseid()) == null ? ""
					: aw.getWarehouseByID(ar.getInwarehouseid()).getWarehousename();
			parameters.put("inwarehouseid", warehouse);
			String outwarehouse = aw.getWarehouseByID(ar.getOutwarehouseid()) == null ? ""
					: aw.getWarehouseByID(ar.getOutwarehouseid()).getWarehousename();
			parameters.put("outwarehouseid", outwarehouse);
			
			parameters.put("movecause", ar.getMovecause());


			parameters.put("makeorgan", os.getOrganName(ar.getMakeorganid()));
			String makename = au.getUsersByID(ar.getMakeid()).getRealname();
			parameters.put("makeidname", makename);
			parameters.put("makedate", DateUtil.formatDate(ar.getMakedate()));
			parameters.put("remark", ar.getRemark());
			
			if (ar.getPrinttimes() == null || ar.getPrinttimes() == 0) {
				ar.setPrinttimes(1);
			}
			parameters.put("printtime", ar.getPrinttimes());
			parameters.put("printman", users.getRealname());
			parameters.put("printb", "");
			
			ar.setPrinttimes(ar.getPrinttimes() + 1);
			asb.updstockAlterMove(ar);
			

			AppProductInterconvertDetail asbd = new AppProductInterconvertDetail();
			List<ProductInterconvertDetail> sbls = asbd.getProductInterconvertDetailBySamID(id);
			List<ProductInterconvertDetailForm> als = new ArrayList<ProductInterconvertDetailForm>();
			ProductInterconvertDetailForm sldf=null;
			for (ProductInterconvertDetail ard:sbls) {
				sldf = new ProductInterconvertDetailForm();
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | " + ard.getSpecmode());
				sldf.setBatch(ard.getBatch());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setQuantity(ard.getQuantity());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
