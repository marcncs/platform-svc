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
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.MoveApplyDetailForm;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;
import com.winsafe.hbm.util.StringUtil;

public class PrintMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/MoveApply.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppMoveApply asb = new AppMoveApply();
			MoveApply ar = asb.getMoveApplyByID(id);
			OrganService os = new OrganService();
			WarehouseService aw = new WarehouseService();
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			
			Organ o = os.getOrganByID(ar.getMakeorganid());
			Organ makeo = os.getOrganByID(users.getMakeorganid());
			parameters.put("organtitle", makeo.getOrganname());
			parameters.put("title", "");
			
			String path = "";
			if (StringUtil.isEmpty(o.getLogo())) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			
			
			
			parameters.put("billtype", "Location Transfer Request 转仓申请单");
			parameters.put("billno", id);
			parameters.put("movedate", DateUtil.formatDate(ar.getMovedate()));
			Warehouse wh = aw.getWarehouseByID(ar.getInwarehouseid());
			parameters.put("inwarehouseidname", wh.getWarehousename());
			UsersService us = new UsersService();
			Users  u = us.getUsersByid(ar.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel()+"/"+u.getMobile());
			parameters.put("fax", o.getOfax());
			parameters.put("email", u.getEmail());
//			String transportmode = HtmlSelect.getNameByOrder(request,
//					"TransportMode", ar.getTransportmode());
//			parameters.put("transportmode", transportmode);
			
			AppOlinkMan appO = new AppOlinkMan();
			Organ outo = os.getOrganByID(ar.getOutorganid());
			List<Olinkman> list = appO.getOlinkmanByCid(ar.getOutorganid());
			Olinkman outlinkman = list.get(0);
			parameters.put("pid", ar.getOutorganid());
			parameters.put("pname", outo.getOrganname());
			parameters.put("paddr", outo.getOaddr());
			parameters.put("plinkman", outlinkman.getName());
			parameters.put("ptel", outlinkman.getOfficetel());
			parameters.put("pmobile", outlinkman.getMobile());
			
			
			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());
			
			
			list = appO.getOlinkmanByCid(o.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("oname", o.getOrganname());
			parameters.put("oaddr", ar.getTransportaddr());
			parameters.put("olinkman", olinkman.getName());
			parameters.put("otel", olinkman.getOfficetel());
			parameters.put("omobile", olinkman.getMobile());
			
			
			parameters.put("printb", "");
			parameters.put("printc", "");
			
			
			parameters.put("movecause", ar.getMovecause());

			String numRMB = "";
			if (ar.getTotalsum() != null) {
				numRMB = NumToChRMB.numToRMB(ar.getTotalsum().toString());
			} else {
				numRMB = NumToChRMB.numToRMB("0");
			}
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", ar.getTotalsum() == null ? 0.00 : ar
					.getTotalsum());
			parameters.put("remark", ar.getRemark());
			
			UsersBean users = UserManager.getUser(request);
			if (ar.getPrinttimes() == null || ar.getPrinttimes() == 0) {
				ar.setPrinttimes(1);
			}
			ar.setPrinttimes(ar.getPrinttimes() + 1);
			asb.updMoveApply(ar);

			AppMoveApplyDetail asbd = new AppMoveApplyDetail();
			List<MoveApplyDetail> sbls = asbd.getMoveApplyDetailByAmID(id);
			List<MoveApplyDetailForm> als = new ArrayList<MoveApplyDetailForm>();

			for (MoveApplyDetail ard  : sbls) {
				MoveApplyDetailForm sldf = new MoveApplyDetailForm();
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | " + ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				sldf.setSubsum(ard.getSubsum());
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
			DBUserLog.addUserLog(userid,4, "打印转仓申请");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
