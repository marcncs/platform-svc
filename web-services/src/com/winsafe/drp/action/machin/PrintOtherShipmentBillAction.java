package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.Internation;

public class PrintOtherShipmentBillAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/OtherShipmentBill.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppOtherShipmentBillAll aps = new AppOtherShipmentBillAll();
			AppUsers au = new AppUsers();
			OtherShipmentBillAll sb = aps.getOtherShipmentBillByID(id);				
			OrganService os = new OrganService();
			WarehouseService ws = new WarehouseService();
			
			
			Map parameters = new HashMap();			
			parameters.put("billtype", "盘亏清单");
			parameters.put("warehouse", ws.getWarehouseName(sb.getWarehouseid()));
			parameters.put("shipmentsort", HtmlSelect.getNameByOrder(request, "ShipmentSort", sb.getShipmentsort()));
			parameters.put("billno", sb.getBillno());
			
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
			aps.updOtherShipmentBill(sb);

			AppOtherShipmentBillDAll apbd = new AppOtherShipmentBillDAll();
			List<OtherShipmentBillDAll> pbls = apbd.getOtherShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();		
			long orders = 1l;
			for (OtherShipmentBillDAll ttd :  pbls) {
				OtherShipmentBillDetailForm sldf = new OtherShipmentBillDetailForm();		
				sldf.setId(orders);
				sldf.setProductname(ttd.getProductid()+"  "+ttd.getProductname()+"  "+ttd.getSpecmode());			
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
						Integer.parseInt(ttd.getUnitid().toString())));
				sldf.setBatch(ttd.getBatch());
				sldf.setQuantity(ttd.getQuantity());	
				orders ++;
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
