package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganInvoice;
import com.winsafe.drp.dao.AppOrganInvoiceDetail;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.OrganInvoice;
import com.winsafe.drp.dao.OrganInvoiceDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00 www.winsafe.cn
 */
public class AddOrganInvoiceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			OrganInvoice oi = new OrganInvoice();
			AppOrganInvoice appAMA = new AppOrganInvoice();
			AppOrganInvoiceDetail appAMAD = new AppOrganInvoiceDetail();
			BeanCopy bc = new BeanCopy();

			bc.copy(oi, request);
			Integer oiid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_invoice", 0, ""));
			oi.setId(oiid);
			oi.setMakeid(userid);
			oi.setMakeorganid(users.getMakeorganid());
			oi.setMakedeptid(users.getMakedeptid());
			oi.setMakedate(DateUtil.getCurrentDate());

			String strpoid[] = request.getParameterValues("poid");
			String strmakedate[] = request.getParameterValues("makedate");
			String strsubsum[] = request.getParameterValues("subsum");
			String billnotype[] = request.getParameterValues("type");

			OrganInvoiceDetail oid = null;
			AppStockAlterMove appsam = new AppStockAlterMove();
			AppSupplySaleMove appssm = new AppSupplySaleMove();
			if (strpoid != null) {
				for (int i = 0; i < strpoid.length; i++) {
					oid = new OrganInvoiceDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("organ_invoice_detail",
									0, ""));
					oid.setId(id);
					oid.setPiid(oiid);
					oid.setPoid(strpoid[i]);
					oid.setSubsum(Double.valueOf(strsubsum[i]));
					oid.setMakedate(DateUtil.StringToDate(strmakedate[i]));
					appAMAD.save(oid);

					if (oi.getInorout().intValue() == 0) {
						if (billnotype[i].equals("1")) {
							appsam.updIsMakeTicket(strpoid[i], 1);
						} else if (billnotype[i].equals("2")) {
							appssm.updIsMakeTicket(strpoid[i], 1);
						} else {
							appssm.updIsReceiveTicket(strpoid[i], 2);
						}
					} else {
						if (billnotype[i].equals("1")) {
							appsam.updIsReceiveTicket(strpoid[i], 1);
						} else if (billnotype[i].equals("2")) {
							appssm.updIsMakeTicket(strpoid[i], 1);
						} else if (billnotype[i].equals("3")) {
							appssm.updIsReceiveTicket(strpoid[i], 1);
						} else {
							appssm.updIsReceiveTicket(strpoid[i], 3);
						}
					}
				}
			}

			appAMA.save(oi);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>添加渠道发票,编号：" + oiid);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
