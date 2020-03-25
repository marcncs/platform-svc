package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.StockCheckDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintStockCheckEmptyAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/StockCheckEmpty.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppStockCheck asb = new AppStockCheck();
			StockCheck sc = asb.getStockCheckByID(id);
			
			
			AppUsers au = new AppUsers();
			WarehouseService ws = new WarehouseService();
			OrganService os = new OrganService();
			
			

			Map parameters = new HashMap();
			parameters.put("title", "");
			parameters.put("billtype", "盘点单");
			parameters.put("id", id);
			parameters.put("warehousebit", sc.getWarehousebit());
			parameters.put("warehouse", ws.getWarehouseName(sc.getWarehouseid()));
			
			parameters.put("makeorgan", os.getOrganName(sc.getMakeorganid()));
			parameters.put("makeidname", au.getUsersByID(sc.getMakeid()).getRealname());
			parameters.put("makedate", DateUtil.formatDate(sc.getMakedate()));
			parameters.put("remark", sc.getMemo());
			
			if ( sc.getPrinttimes() == null || sc.getPrinttimes() == 0 ){
				sc.setPrinttimes(1);
			}
			parameters.put("printtime", sc.getPrinttimes());
			parameters.put("printman", users.getRealname());
			
			sc.setPrinttimes(sc.getPrinttimes()+1);
			asb.upStockCheck(sc);

			AppStockCheckDetail asbd = new AppStockCheckDetail();
			List sbls = asbd.getStockCheckDetailBySmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < sbls.size(); i++) {
				StockCheckDetailForm sldf = new StockCheckDetailForm();
				StockCheckDetail o = (StockCheckDetail) sbls.get(i);
				sldf.setProductname(o.getProductid()+" "+ o.getProductname()+""+o.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit",
						o.getUnitid()));
				sldf.setWarehousebit(o.getWarehousebit());
				sldf.setBatch(o.getBatch());
				sldf.setReckonquantity(o.getReckonquantity());
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
			//return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
