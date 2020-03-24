package com.winsafe.sap.action;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ESAPIUtil; 
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppInvoice;
import com.winsafe.sap.pojo.Invoice;

public class ExcPutInvoiceAction extends BaseAction {
	
	private AppInvoice appInvoice = new AppInvoice();
	private AppOrgan appOrgan = new AppOrgan();
	private AppProduct appProduct = new AppProduct();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Invoice" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String timeCondition = getTimeCondition(map, tmpMap," invoiceDate");
			whereSql = whereSql + timeCondition;
			whereSql = getJoinSql(map, whereSql);
			whereSql = DbUtil.getWhereSql(whereSql);
			
			List<Invoice> invoices = appInvoice.getInvoices(whereSql);

			if (invoices.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ListInvoice.xls");
			response.setContentType("application/msexcel");
			writeXls(invoices, os, request);
			os.flush();
			os.close();
			DBUserLog.addUserLog(request,"列表");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJoinSql(Map map, String whereSql) {
		StringBuffer sb = new StringBuffer();
		String partnName = (String)map.get("partnName");
		if(!StringUtil.isEmpty(partnName)) {
			sb.append(" ,Organ o ");
			whereSql = whereSql + " i.partnSold = o.oecode and o.organname = '" + partnName.trim() + "' and ";
		}
		String productname = (String)map.get("productname");
		String packagesize = (String)map.get("packagesize");
		if(!StringUtil.isEmpty(productname) || !StringUtil.isEmpty(packagesize)) {
			sb.append(" ,Product p ");
			if(!StringUtil.isEmpty(productname)) {
				whereSql = whereSql + " i.materialCode = p.mCode and p.productname = '" + productname.trim() + "' and ";
			}
			if(!StringUtil.isEmpty(packagesize)) {
				whereSql = whereSql + " i.materialCode = p.mCode and p.specmode = '" + packagesize.trim() + "' and ";
			}
		}
		return sb.append(whereSql).toString();
	}
	
	private String getTimeCondition(Map map, Map tmpMap, String field) {
		StringBuffer buf = new StringBuffer();
		if(!StringUtil.isEmpty((String)map.get("BeginDate"))) {
			buf.append(field).append(" >='").append(((String)map.get("BeginDate")).replace("-", "")).append("' and ");
		}
		if(!StringUtil.isEmpty((String)map.get("EndDate"))) {
			buf.append(field).append(" <='").append(((String)map.get("EndDate")).replace("-", "")).append("' and ");
		}
		return buf.toString();
	}
	
	public void writeXls(List<Invoice> invoices, OutputStream os,
			HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		String lmenuName = AppLeftMenu.getMenuNameByUrl(request.getServletPath());
		int snum = 1;
		snum = invoices.size() / 50000;
		if (invoices.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= invoices.size()) {
				currentnum = invoices.size();
			}
			int start = j * 50000;

			sheets[j].mergeCells(0, start, 11, start);
			sheets[j].addCell(new Label(0, start, lmenuName,wchT));
			sheets[j].addCell(new Label(0, start+1, "发票编号:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("invoiceNumber")));
			sheets[j].addCell(new Label(2, start+1, "送货单号:",seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("deliveryNumber")));
			sheets[j].addCell(new Label(4, start+1, "发票日期:",seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "客户编号:",seachT));
			sheets[j].addCell(new Label(1, start+2, request.getParameter("partnSold")));
			sheets[j].addCell(new Label(2, start+2, "客户名称:",seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("soldtoparty")));
			sheets[j].addCell(new Label(4, start+2, "物料号:",seachT));
			sheets[j].addCell(new Label(5, start+2, request.getParameter("materialCode")));
			
			sheets[j].addCell(new Label(0, start+3, "产品名称:",seachT));
			sheets[j].addCell(new Label(1, start+3, request.getParameter("productname")));
			sheets[j].addCell(new Label(2, start+3, "规格:",seachT));
			sheets[j].addCell(new Label(3, start+3, request.getParameter("packagesize")));
			sheets[j].addCell(new Label(4, start+3, "批号:",seachT));
			sheets[j].addCell(new Label(5, start+3, request.getParameter("batchNumber")));
			          
			sheets[j].addCell(new Label(0, start+4, "发票编号"));
			sheets[j].addCell(new Label(1, start+4, "发票日期 "));
			sheets[j].addCell(new Label(2, start+4, "客户编号"));
			sheets[j].addCell(new Label(3, start+4, "客户名称"));
			sheets[j].addCell(new Label(4, start+4, "送货单号"));
			sheets[j].addCell(new Label(5, start+4, "发票行号"));
			sheets[j].addCell(new Label(6, start+4, "物料号"));
			sheets[j].addCell(new Label(7, start+4, "产品"));
			sheets[j].addCell(new Label(8, start+4, "规格"));
			sheets[j].addCell(new Label(9, start+4, "批号"));
			sheets[j].addCell(new Label(10, start+4, "数量"));
			sheets[j].addCell(new Label(11, start+4, "金额"));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i - start + 5;
				Invoice p = invoices.get(i);
				sheets[j].addCell(new Label(0, row, p.getInvoiceNumber()));
				sheets[j].addCell(new Label(1, row, p.getInvoiceDate()));
				sheets[j].addCell(new Label(2, row, p.getPartnSold()));
				try {
					Organ organ = appOrgan.getByOecode(p.getPartnSold());
					sheets[j].addCell(new Label(3, row, ESAPIUtil.decodeForHTML(organ.getOrganname())));
				} catch (Exception e) {
					
				}
				sheets[j].addCell(new Label(4, row, p.getDeliveryNumber()));
				sheets[j].addCell(new Label(5, row, p.getInvoiceLineItem()));
				sheets[j].addCell(new Label(6, row, p.getMaterialCode()));
				try {
					Product product = appProduct.getByMCode(p.getMaterialCode());
					sheets[j].addCell(new Label(7, row, product.getProductname()));
					sheets[j].addCell(new Label(8, row, product.getSpecmode()));
				} catch (Exception e) {
					
				}
				sheets[j].addCell(new Label(9, row, p.getBatchNumber()));
				if(p.getInvoiceQty() != null) {
					sheets[j].addCell(new Label(10, row, p.getInvoiceQty().toString()));
				}
				sheets[j].addCell(new Label(11, row, p.getNetVal()));
				
			}

		}
		workbook.write();
		workbook.close();
		os.close();
	}
	
}
