package com.winsafe.sap.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppInvoice;
import com.winsafe.sap.pojo.Invoice;

public class ListInvoicesAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListInvoicesAction.class);
	
	private AppInvoice appInvoice = new AppInvoice();
	private AppOrgan appOrgan = new AppOrgan();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "Invoice" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = getTimeCondition(map, tmpMap," invoiceDate");
		whereSql = whereSql + timeCondition;
		whereSql = getJoinSql(map, whereSql);
		whereSql = DbUtil.getWhereSql(whereSql);
				
		
		List<Invoice> invoices = appInvoice.getInvoices(request, pagesize, whereSql);
		
		if(invoices != null) {
			for(Invoice invoice: invoices) {
				try {
					Organ organ = appOrgan.getByOecode(invoice.getPartnSold());
					if(organ != null) {
						invoice.setPartnName(organ.getOrganname());
					}
					Product product = appProduct.getByMCode(invoice.getMaterialCode());
					invoice.setProName(product.getProductname());
					invoice.setPackSize(product.getSpecmode());
				} catch (Exception e) {
					logger.debug("error when get invoice details info");
				}
			}
		}
		
		request.setAttribute("invoices", invoices);
		DBUserLog.addUserLog(request, "查看");
		return mapping.findForward("list");
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
		
}
