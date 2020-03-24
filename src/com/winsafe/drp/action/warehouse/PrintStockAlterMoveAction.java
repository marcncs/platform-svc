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
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/StockAlterMove.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppStockAlterMove aps = new AppStockAlterMove();
			StockAlterMove sb = aps.getStockAlterMoveByID(id);
			WarehouseService aw = new WarehouseService();
			

			AppInvoiceConf aic = new AppInvoiceConf();


			String billtype = "Order List 订购单";

			Map<String ,Object> parameters = new HashMap<String ,Object>();
			OrganService os = new OrganService();
			Organ o = os.getOrganByID(sb.getMakeorganid());
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");
			
			String path = "";
			if ("".equals(o.getLogo())) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);
			parameters.put("isshow", true);
			
			parameters.put("billtype", billtype);
			parameters.put("billno", sb.getId());
			parameters.put("movedate", DateUtil.formatDate(sb.getMovedate()));
			UsersService us = new UsersService();
			Users  u = us.getUsersByid(sb.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel()+"/"+u.getMobile());
			parameters.put("fax", o.getOfax());
			parameters.put("email",u.getEmail());
			Warehouse wh = aw.getWarehouseByID(sb.getInwarehouseid());
			parameters.put("inwarehouseidname", wh.getWarehousename());
			parameters.put("outwarehouseidname", aw.getWarehouseName(sb.getOutwarehouseid()));
			parameters.put("transportmode", Internation
					.getStringByKeyPositionDB("TransportMode", sb
							.getTransportmode()));
			parameters.put("remark", sb.getRemark());
			parameters.put("movecause", sb.getMovecause());
			parameters.put("paymentmodename", Internation
					.getStringByPayPositionDB(sb.getPaymentmode()));
			parameters.put("invmsg", aic.getInvoiceConfById(sb.getInvmsg())
					.getIvname());
			
			
			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(o.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("pid", o.getId());
			parameters.put("pname", o.getOrganname());
			parameters.put("paddr", o.getOaddr());
			parameters.put("plinkman",olinkman.getName());
			parameters.put("ptel", olinkman.getOfficetel());
			parameters.put("pmobile", olinkman.getMobile());
			
			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());
			
			Organ organ = os.getOrganByID(sb.getReceiveorganid());
			list = appO.getOlinkmanByCid(organ.getId());
			Olinkman olinkman2 = list.get(0);
			parameters.put("oname", organ.getOrganname());
			parameters.put("oaddr", sb.getTransportaddr());
			parameters.put("olinkman", olinkman2.getName());
			parameters.put("otel", olinkman2.getOfficetel());
			parameters.put("omobile", olinkman2.getMobile());
			
			
			parameters.put("printb", "");
			parameters.put("printc", "");
			
			

			if (sb.getPrinttimes() == null || sb.getPrinttimes() == 0) {
				sb.setPrinttimes(1);
			}
			parameters.put("printtimes", sb.getPrinttimes());

			sb.setPrinttimes(sb.getPrinttimes() + 1);

			AppStockAlterMoveDetail apbd = new AppStockAlterMoveDetail();
			List<StockAlterMoveDetail> pbls = apbd
					.getStockAlterMoveDetailBySamID(id);
			List<StockAlterMoveDetailForm> als = new ArrayList<StockAlterMoveDetailForm>();

			
			for (StockAlterMoveDetail ttd : pbls) {
				StockAlterMoveDetailForm sldf = new StockAlterMoveDetailForm();
				sldf.setProductid(ttd.getProductid());
				sldf.setProductname(ttd.getProductname()+" | "+ttd.getSpecmode());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(ttd.getUnitid()
								.toString())));
				sldf.setUnitprice(ttd.getUnitprice());
				sldf.setQuantity(ttd.getQuantity());
				sldf.setSubsum(ttd.getSubsum());
				als.add(sldf);
			}
			parameters.put("totalsum", sb.getTotalsum());
			parameters.put("totalsumcapital", NumToChRMB.numToRMB(sb
					.getTotalsum().toString()));
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
			DBUserLog.addUserLog(userid,4, "打印渠道订购!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
