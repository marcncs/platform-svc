package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.dao.WithdrawDetail;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		AppWithdraw aso = new AppWithdraw();
		super.initdata(request);
		try {
			Withdraw dp = aso.getWithdrawByID(id);

			Withdraw olds = (Withdraw) BeanUtils.cloneBean(dp);

			if (dp.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String cname = request.getParameter("cname");
			String decideman = request.getParameter("decideman");
			String cmobile = request.getParameter("cmobile");
			String tel = request.getParameter("decidemantel");
			String warehouseid = request.getParameter("warehouseid");
			String withdrawcause = request.getParameter("withdrawcause");
			// String strtotalsum = request.getParameter("totalsum");
			String billno = request.getParameter("billno");
			String strpaymentmode = request.getParameter("paymentmode");
			String strwithdrawsort = request.getParameter("withdrawsort");
			double totalsum = 0.00;

			dp.setCid(cid);
			dp.setCname(cname);
			dp.setCmobile(cmobile);
			dp.setClinkman(decideman);
			dp.setTel(tel);
			dp.setWarehouseid(warehouseid);
			dp.setWithdrawcause(withdrawcause);
			dp.setBillno(billno);
			dp.setPaymentmode(Integer.parseInt(strpaymentmode));
			dp.setWithdrawsort(Integer.parseInt(strwithdrawsort));

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String strbatch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double taxunitprice[] = RequestTool.getDoubles(request,
					"taxunitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double discount[] = RequestTool.getDoubles(request, "discount");
			double taxrate[] = RequestTool.getDoubles(request, "taxrate");
			// String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			String productname, specmode, batch;
			Double subsum;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(dp.getId()).append(",").append(dp.getCname())
					.append(",").append(dp.getCmobile()).append(",").append(
							dp.getClinkman()).append(",").append(dp.getTel())
					.append(",").append(dp.getBillno()).append(",");

			AppWithdrawDetail asld = new AppWithdrawDetail();
			asld.delWithdrawDetailByWID(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("withdraw_idcode","wid",dp.getWarehouseid());
			wbds.del(dp.getId(), strproductid);
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				batch = "";

				subsum = DataFormat.countPrice(quantity[i], taxunitprice[i],
						discount[i], taxrate[i]);
				totalsum += subsum;

				WithdrawDetail sod = new WithdrawDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"withdraw_detail", 0, "")));
				sod.setWid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setBatch(batch);
				sod.setUnitid(unitid[i]);
				sod.setUnitprice(unitprice[i]);
				sod.setTaxunitprice(taxunitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setDiscount(discount[i]);
				sod.setTaxrate(taxrate[i]);
				sod.setSubsum(subsum);
				// sod.setIssettlement(0);
				asld.addWithdrawDetail(sod);
				wbds.add(dp.getId(), sod.getProductid(), sod.getUnitid(), sod.getQuantity());

			}
			dp.setTotalsum(totalsum);
			dp.setKeyscontent(keyscontent.toString());
			aso.updWithdraw(dp);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 6, "销售退货>>修改销售退货,编号：" + id, olds, dp);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
