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

import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesDetail;
import com.winsafe.drp.dao.SaleTradesDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintSaleTradesAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/SaleTrades.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppSaleTrades aps = new AppSaleTrades();
			AppUsers au = new AppUsers();
			SaleTrades sb = aps.getSaleTradesByID(id);
			WarehouseService aw = new WarehouseService();
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
			
			
			parameters.put("billtype", "Retailed SWAP 零售换货单");
			parameters.put("titleid", "零售换货单编号");
			parameters.put("id", sb.getId());
			parameters.put("tradesdate", DateUtil
					.formatDate(sb.getTradesdate()));
			parameters.put("titlesort", "换货类型");
			parameters.put("sortname", HtmlSelect.getNameByOrder(request, "TradesSort", sb.getTradessort()));
			parameters.put("makedate", DateUtil
					.formatDate(sb.getMakedate()));
			parameters.put("billno", "");
			parameters.put("warehousename", aw.getWarehouseName(sb.getWarehouseinid()));
			parameters.put("outwarehousename", aw.getWarehouseName(sb.getWarehouseid()));
			
			
			parameters.put("cid", "客户编号："+sb.getCid());
			parameters.put("cname", "客户名称： " + sb.getCname());
			parameters.put("caddr", "地址："+sb.getSendaddr());
			parameters.put("receiveman", "联系人："+sb.getClinkman());
			parameters.put("tel", "联系电话："+sb.getTel());
			parameters.put("pmobile","");
			parameters.put("printc","");
			
			parameters.put("remark", sb.getRemark());
			
			parameters.put("makename", au.getUsersByID(sb.getMakeid())
					.getRealname());
			parameters.put("makeorgan",  os.getOrganName(sb.getMakeorganid()) );
			if (sb.getPrinttimes() == null || sb.getPrinttimes() == 0) {
				sb.setPrinttimes(1);
			}

			sb.setPrinttimes(sb.getPrinttimes() + 1);
			aps.updSaleTrades(sb);

			AppSaleTradesDetail apbd = new AppSaleTradesDetail();
			List<SaleTradesDetail> pbls = apbd.getSaleTradesDetailByStid(id);
			List<SaleTradesDetailForm> als = new ArrayList<SaleTradesDetailForm>();
			for (SaleTradesDetail ttd : pbls) {
				SaleTradesDetailForm sldf = new SaleTradesDetailForm();
				sldf.setProductid(ttd.getProductid());
				sldf.setProductname(ttd.getProductname() + " | " + ttd.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", ttd.getUnitid()));
				sldf.setOldbatch(ttd.getOldbatch()==null?"":ttd.getOldbatch());
				sldf.setBatch(ttd.getBatch()==null?"":ttd.getBatch());
				sldf.setQuantity(ttd.getQuantity());
				sldf.setTakequantity(ttd.getTakequantity());
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
			DBUserLog.addUserLog(userid, 4, "打印零售换货");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
