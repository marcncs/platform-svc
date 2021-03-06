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
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBillDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintHarmShipmentBillAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/HarmShipmentBill.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppHarmShipmentBill aps = new AppHarmShipmentBill();
			AppUsers au = new AppUsers();
			HarmShipmentBill sb = aps.getHarmShipmentBillByID(id);				
			OrganService os = new OrganService();
			WarehouseService ws = new WarehouseService();
			
			
			Map parameters = new HashMap();			
			parameters.put("billtype", "领用出库单");
			parameters.put("warehouse", ws.getWarehouseName(sb.getWarehouseid()));
			parameters.put("harmdate", DateUtil.formatDate(sb.getHarmdate()));
			parameters.put("id", sb.getId());
			
			parameters.put("remark", sb.getRemark());
			
			parameters.put("makeidname", au.getUsersByID(sb.getMakeid()).getRealname());
			parameters.put("makeorgan", os.getOrganName(sb.getMakeorganid()));
			parameters.put("makedate", DateUtil.formatDate(sb.getMakedate()));
			
			if ( sb.getPrinttimes() == null || sb.getPrinttimes() == 0 ){
				sb.setPrinttimes(1);
			}
			parameters.put("printtime", sb.getPrinttimes());
			parameters.put("printman", users.getRealname());

			sb.setPrinttimes(sb.getPrinttimes()+1);
			aps.updHarmShipmentBill(sb);

			AppHarmShipmentBillDetail apbd = new AppHarmShipmentBillDetail();
			List<HarmShipmentBillDetail> pbls = apbd.getHarmShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();		

			for (HarmShipmentBillDetail ttd :  pbls) {
				HarmShipmentBillDetailForm sldf = new HarmShipmentBillDetailForm();		
				
				sldf.setProductname(ttd.getProductid()+"  "+ttd.getProductname()+"  "+ttd.getSpecmode());			
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", 
						ttd.getUnitid()));
				sldf.setBatch(ttd.getBatch()==null?"":ttd.getBatch());
				sldf.setQuantity(ttd.getQuantity());	

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
