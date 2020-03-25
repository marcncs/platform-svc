package com.winsafe.drp.action.aftersale;

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

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.dao.WithdrawDetail;
import com.winsafe.drp.dao.WithdrawDetailForm;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintSaleWithdrawAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/SaleWithdraw.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppWithdraw aps = new AppWithdraw();
			AppUsers au = new AppUsers();
			Withdraw sb = aps.getWithdrawByID(id);
			WarehouseService aw = new WarehouseService();
			PaymentModeService pms = new PaymentModeService();
			
			
			OrganService os = new OrganService();
			Map<String, Object> parameters = new HashMap<String, Object>();
			Organ organ = os.getOrganByID(sb.getMakeorganid());
			parameters.put("organtitle", organ.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);
			parameters.put("billtype", "Retailed Return 零售退货单");
			parameters.put("titleid", "零售退货单编号");
			parameters.put("id", sb.getId());
			parameters.put("makedate", DateUtil
					.formatDate(sb.getMakedate()));
			parameters.put("titlesort", "退货类型");
			parameters.put("sortname", HtmlSelect.getNameByOrder(request, "WithdrawSort", sb.getWithdrawsort()));
			parameters.put("billno",sb.getBillno());
			parameters.put("paymentmodename", pms.getPaymentModeName(sb.getPaymentmode()));
			parameters.put("warehousename", aw.getWarehouseName(sb.getWarehouseid()));
			parameters.put("out", "");
			parameters.put("outwarehousename", "");
			
			
			parameters.put("cid", "客户编号："+sb.getCid());
			parameters.put("cname", "客户名称："+sb.getCname());
			parameters.put("caddr", "联系人："+sb.getClinkman());
			parameters.put("receiveman", "联系电话："+sb.getTel());
			parameters.put("tel", "");
			parameters.put("pmobile","");
			parameters.put("printc","");
			
			parameters.put("remark", sb.getWithdrawcause());

			String numRMB = "";
			if (sb.getTotalsum() != null) {
				numRMB = NumToChRMB.numToRMB(sb.getTotalsum().toString());
			} else {
				numRMB = NumToChRMB.numToRMB("0");
			}
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", sb.getTotalsum());
			parameters.put("makename", au.getUsersByID(sb.getMakeid())
					.getRealname());
			parameters.put("makeorgan",  os.getOrganName(sb.getMakeorganid()) );
			
		
			if (sb.getPrinttimes() == null || sb.getPrinttimes() == 0) {
				sb.setPrinttimes(1);
			}

			sb.setPrinttimes(sb.getPrinttimes() + 1);
			aps.updWithdraw(sb);

			AppWithdrawDetail apbd = new AppWithdrawDetail();
			List<WithdrawDetail> pbls = apbd.getWithdrawDetailByWID(id);
			ArrayList als = new ArrayList();
			for (WithdrawDetail ttd : pbls) {
				
				WithdrawDetailForm sldf = new WithdrawDetailForm();
				sldf.setProductid(ttd.getProductid());
				sldf.setProductname(ttd.getProductname() + " | " + ttd.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", ttd.getUnitid()));
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
			DBUserLog.addUserLog(userid, 4, "打印零售退货");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
