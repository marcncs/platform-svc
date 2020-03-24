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
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class ProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/ProductIncome.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppProductIncome api = new AppProductIncome();
			ProductIncome sb = new ProductIncome();
			sb = api.getProductIncomeByID(id);
			
			AppUsers au = new AppUsers();
			OrganService os = new OrganService();
			WarehouseService ws = new WarehouseService();


			Map<String ,Object> parameters = new HashMap<String ,Object>();		
			Organ o = os.getOrganByID(sb.getMakeorganid());
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.gif");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);
			
			parameters.put("billtype", "产成品入库");
			
			parameters.put("id", id);
			parameters.put("billno", sb.getHandwordcode());
			String sortname =HtmlSelect.getNameByOrder(request,"ProductIncomeSort",
		            sb.getIncomesort());
			parameters.put("incomesort", sortname);
			parameters.put("warehousename", ws.getWarehouseName(sb.getWarehouseid()));
			parameters.put("incomedate", DateUtil.formatDate(sb.getIncomedate()));
			
			
			parameters.put("pid","");
			parameters.put("pname", "");
			parameters.put("paddr", "");
			parameters.put("plinkman", "");
			parameters.put("ptel", "");
			parameters.put("pmobile", "");
			
			parameters.put("makeorgan", os.getOrganName(sb.getMakeorganid()));
			parameters.put("makename", au.getUsersByid(sb.getMakeid()).getRealname());
			parameters.put("makedate", DateUtil.formatDateTime(sb.getMakedate()));
			parameters.put("remark", sb.getRemark());
			
			parameters.put("printc", "");

			AppProductIncomeDetail asbd = new AppProductIncomeDetail();
			List<ProductIncomeDetail> sbls = asbd.getProductIncomeDetailByPbId(id);
			List<ProductIncomeDetailForm> als = new ArrayList<ProductIncomeDetailForm>();
			double totalsum = 0.00;
			for (ProductIncomeDetail pid:sbls) {
				ProductIncomeDetailForm sldf = new ProductIncomeDetailForm();
				sldf.setProductid(pid.getProductid());
				sldf.setProductname(pid.getProductname()+"|"+pid.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", 
						pid.getUnitid()));
				sldf.setCostprice(pid.getCostprice());
				sldf.setQuantity(pid.getQuantity());
				sldf.setCostsum(pid.getCostsum());
				totalsum+=sldf.getCostsum();
				als.add(sldf);
			}
			
			
			String numRMB  = NumToChRMB.numToRMB(String.valueOf(totalsum));
			
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", totalsum);

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
			DBUserLog.addUserLog(userid, 7, "打印产成品入库!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
