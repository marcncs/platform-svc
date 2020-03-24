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
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintStockAlterMoveReceiveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/StockAlterMove.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(reportSource);

			AppStockAlterMove aps = new AppStockAlterMove();
			StockAlterMove sb = aps.getStockAlterMoveByID(id);
			WarehouseService aw = new WarehouseService();

			AppInvoiceConf aic = new AppInvoiceConf();

			String billtype = "Order List Receive 订购签收单";

			Map<String, Object> parameters = new HashMap<String, Object>();
			OrganService os = new OrganService();
			Organ o = os.getOrganByID(sb.getMakeorganid());
			Organ organ = os.getOrganByID(sb.getReceiveorganid());
			Organ makeo = os.getOrganByID(users.getMakeorganid());
			parameters.put("organtitle", makeo.getOrganname());
			parameters.put("title","");
			String path = "";
			parameters.put("img", path);
			parameters.put("isshow", false);

			parameters.put("billtype", billtype);
			parameters.put("billno", sb.getId());
			parameters.put("movedate", DateUtil.formatDate(sb.getMovedate()));

			UsersService us = new UsersService();
			Users u = us.getUsersByid(sb.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel() + "/" + u.getMobile());
			parameters.put("fax", o.getOfax());
			parameters.put("email", u.getEmail());
			Warehouse wh = aw.getWarehouseByID(sb.getInwarehouseid());
			parameters.put("inwarehouseidname", wh.getWarehousename());
			parameters.put("outwarehouseidname", aw.getWarehouseName(sb
					.getOutwarehouseid()));
			parameters.put("remark", sb.getRemark());
			parameters.put("movecause", sb.getMovecause());

			AppOlinkMan appO = new AppOlinkMan();
			Organ makeorgan = os.getOrganByID(sb.getMakeorganid());
			List<Olinkman> list = appO.getOlinkmanByCid(makeorgan.getId());
			Olinkman olinkman = null;
			if(list != null && list.size()>0){
				olinkman = list.get(0);
				parameters.put("pid", makeorgan.getId());
				parameters.put("pname", makeorgan.getOrganname());
				parameters.put("paddr", makeorgan.getOaddr());
				parameters.put("plinkman", olinkman.getName());
				parameters.put("ptel", olinkman.getOfficetel());
				parameters.put("pmobile", olinkman.getMobile());
			}
			
			
			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());

			list = appO.getOlinkmanByCid(organ.getId());
			parameters.put("oname", organ.getOrganname());
			parameters.put("oaddr", sb.getTransportaddr());
			if(olinkman != null){
				parameters.put("olinkman", olinkman.getName());
				parameters.put("otel", olinkman.getOfficetel());
				parameters.put("omobile", olinkman.getMobile());
			}

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
				sldf.setProductname(ttd.getProductname() + " | "
						+ ttd.getSpecmode());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(ttd.getUnitid()
								.toString())));
				sldf.setUnitprice(ttd.getUnitprice());
				sldf.setQuantity(ttd.getQuantity());
				sldf.setSubsum(ttd.getSubsum());
				als.add(sldf);
			}
			parameters.put("totalsum", sb.getTotalsum());
			parameters.put("totalsumcapital", NumToChRMB.numToRMB(NumberUtil.removeNull(sb
					.getTotalsum()).toString()));
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
			DBUserLog.addUserLog(userid, 4, "打印渠道订购签收!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
