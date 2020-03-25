package com.winsafe.drp.action.purchase;

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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlanDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PurchasePlanAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/PurchasePlan.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppPurchasePlan app = new AppPurchasePlan();
			UsersService au = new UsersService();
			DeptService ad = new DeptService();
			PurchasePlan pp = app.getPurchasePlanByID(id);
			Map parameters = new HashMap();
			parameters.put("billtype", "Purchase Plan 采购计划");
			OrganService os = new OrganService();
			
			Organ organ = os.getOrganByID(pp.getMakeorganid());
			parameters.put("title","");
			parameters.put("organtitle",organ.getOrganname());
			
			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);
			parameters.put("printb", "");
			parameters.put("printc", "");
			parameters.put("billno",pp.getId());
			parameters.put("plandept", ad.getDeptName(pp.getPlandept()));
			parameters.put("plandate", String.valueOf(pp.getPlandate())
					.substring(0, 10));
			Users user = au.getUsersByid(pp.getPlanid());
			parameters.put("planname", user.getRealname());

			parameters.put("tel",user.getOfficetel()+"/"+user.getMobile());
			parameters.put("fax",organ.getOfax());
			parameters.put("email",user.getEmail());
		
			
			AppPurchasePlanDetail appd = new AppPurchasePlanDetail();
			List sals = appd.getPurchasePlanDetailByPaID(id);
			List<PurchasePlanDetailForm> als = new ArrayList<PurchasePlanDetailForm>();
			double totalsum=0.00;
			for (int i = 0; i < sals.size(); i++) {
				PurchasePlanDetailForm ppdf = new PurchasePlanDetailForm();
				PurchasePlanDetail o = (PurchasePlanDetail) sals.get(i);
				ppdf.setProductid(o.getProductid());
				ppdf.setProductname(o.getProductname() + " | " + o.getSpecmode());
				String unitname = HtmlSelect.getResourceName(request,
						"CountUnit", o.getUnitid());
				ppdf.setUnitname(unitname);
				ppdf.setRequiredate(DateUtil.formatDate(o.getRequiredate()));
				ppdf.setAdvicedate(DateUtil.formatDate(o.getAdvicedate()));
				ppdf.setUnitprice(o.getUnitprice());
				ppdf.setQuantity(o.getQuantity());
				ppdf.setSubsum(0.00);
				totalsum += ppdf.getSubsum();
				als.add(ppdf);
			}
			parameters.put("totalsum", totalsum);
			String numRMB=NumToChRMB.numToRMB(String.valueOf(totalsum));
			
			parameters.put("totalsumcapital", numRMB);
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
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,2,"打印采购计划");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
