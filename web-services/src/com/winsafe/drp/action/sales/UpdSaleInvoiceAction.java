package com.winsafe.drp.action.sales;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.AppSaleInvoiceDetail;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.dao.SaleInvoiceDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdSaleInvoiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Integer id = RequestTool.getInt(request,"id");
			AppSaleInvoice asi = new AppSaleInvoice();
			SaleInvoice si = asi.getSaleInvoiceByID(id);
			SaleInvoice oldso = (SaleInvoice) BeanUtils.cloneBean(si);

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			si.setId(id);
			si.setInvoicecode(request.getParameter("invoicecode"));
			si.setInvoicetype(Integer.valueOf(request
					.getParameter("invoicetype")));
			String makeinvoicedate = request.getParameter("makeinvoicedate");
			String invoicedate = request.getParameter("invoicedate");
			String tmpmakeinvoicedate = makeinvoicedate.replace('-', '/');
			if (tmpmakeinvoicedate != null
					&& tmpmakeinvoicedate.trim().length() > 0) {
				si.setMakeinvoicedate(new Date(tmpmakeinvoicedate));
			}
			String tmpinvoicedate = invoicedate.replace('-', '/');
			if (tmpinvoicedate != null && tmpinvoicedate.trim().length() > 0) {
				si.setInvoicedate(new Date(tmpinvoicedate));
			}
			si.setInvoicesum(Double.valueOf(request.getParameter("totalsum")));
			si.setCid(cid);
			si.setMemo(request.getParameter("memo"));
			si.setInvoicetitle(request.getParameter("invoicetitle"));
			si.setSendaddr(request.getParameter("sendaddr"));
			si.setUpdateid(userid);
			si.setLastdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			// si.setMakeinvoicedate(makeinvoicedate)

			// Double totalsum;
			
			String strsoid[] = request.getParameterValues("soid");
			double subsum[] = RequestTool.getDoubles(request, "subsum");
			String strmakedate[] = request.getParameterValues("makedate");

			String soid;

			AppSaleInvoiceDetail asld = new AppSaleInvoiceDetail();
			asld.delSaleInvoiceBySIID(id);

			// if(strsoid.length<=0){
			// String result = "databases.add.fail";
			// request.setAttribute("result", "databases.upd.success");
			// return new ActionForward("/sys/lockrecordclose.jsp");
			// }
			if ( strsoid != null ){
				for (int i = 0; i < strsoid.length; i++) {
					soid = strsoid[i];
	
					SaleInvoiceDetail sid = new SaleInvoiceDetail();
					sid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"sale_invoice_detail", 0, "")));
					sid.setSiid(id);
					sid.setSoid(soid);
					sid.setSubsum(subsum[i]);
					sid.setMakedate(new Date(strmakedate[i].replace('-', '/')));
	
					asld.addSaleInvoiceDetail(sid);	
				}
			}

			asi.updSaleInvoiceByID(si);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 6,"销售发票>>修改销售发票,编号："+id,oldso,si);

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
