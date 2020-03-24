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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncomeDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintPurchaseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/PurchaseIncome.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppPurchaseIncome aps = new AppPurchaseIncome();
			WarehouseService ws = new WarehouseService();
			OrganService os = new OrganService();
			PurchaseIncome tt = aps.getPurchaseIncomeByID(id);			

			Map<String ,Object> parameters = new HashMap<String ,Object>();		
			Organ o = os.getOrganByID(tt.getMakeorganid());
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);
			
			parameters.put("billtype", "Purchase Income 采购入库单");
			parameters.put("id", tt.getId());
			parameters.put("incomedate", DateUtil.formatDate(tt.getIncomedate()));
			parameters.put("billno", tt.getPoid());
			UsersService us = new UsersService();
			Users u = us.getUsersByid(tt.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel()+"/"+u.getMobile());	
			parameters.put("warehousename", ws.getWarehouseName(tt.getWarehouseid()));	
			
			
			AppProvider appP = new AppProvider();
			Provider p = appP.getProviderByID(tt.getProvideid());
			parameters.put("pid", tt.getProvideid());
			parameters.put("pname", tt.getProvidename());
			parameters.put("paddr", p.getAddr() == null ? "" : p.getAddr());
			parameters.put("plinkman", tt.getPlinkman());
			parameters.put("ptel", tt.getTel()+"/"+p.getMobile());
			parameters.put("pmobile", "");
			
			
			parameters.put("printc", "");
			AppUsers au = new AppUsers();
			parameters.put("makeorgan", os.getOrganName(tt.getMakeorganid()));
			parameters.put("makename", au.getUsersByid(tt.getMakeid()).getRealname());
			parameters.put("makedate", DateUtil.formatDateTime(tt.getMakedate()));
			parameters.put("remark", tt.getRemark());
			
			if ( tt.getPrinttimes() == null || tt.getPrinttimes() == 0 ){
				tt.setPrinttimes(1);
			}
			
			tt.setPrinttimes(tt.getPrinttimes()+1);
			aps.updPurchaseIncome(tt);
			
		
			

			AppPurchaseIncomeDetail apbd = new AppPurchaseIncomeDetail();
			List<PurchaseIncomeDetail> pbls = apbd.getPurchaseIncomeDetailByPbId2(id);
			List<PurchaseIncomeDetailForm> als = new ArrayList<PurchaseIncomeDetailForm>();		
			for (PurchaseIncomeDetail ttd :  pbls) {
				PurchaseIncomeDetailForm sldf = new PurchaseIncomeDetailForm();	
				sldf.setProductid(ttd.getProductid());
				sldf.setProductname(ttd.getProductname()+" | "+ttd.getSpecmode());			
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", 
						ttd.getUnitid()));
				sldf.setUnitprice(ttd.getUnitprice());
				sldf.setQuantity(ttd.getQuantity());	
				sldf.setSubsum(ttd.getSubsum());
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
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 7, "打印采购入库!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
