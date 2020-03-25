package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String cname = request.getParameter("cname");
			String decideman = request.getParameter("decideman");
			String cmobile = request.getParameter("cmobile");
			String decidemantel = request.getParameter("decidemantel");
			String warehouseid = request.getParameter("warehouseid");
			String withdrawcause = request.getParameter("withdrawcause");
			String billno = request.getParameter("billno");
			String strpaymentmode = request.getParameter("paymentmode");
			String strwithdrawsort = request.getParameter("withdrawsort");
			double totalsum=0.00;

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			//String strbatch[] = request.getParameterValues("batch");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double taxunitprice[] = RequestTool.getDoubles(request,"taxunitprice");
			double quantity[] = RequestTool.getDoubles(request,"quantity");
			double discount[] = RequestTool.getDoubles(request,"discount");
			double taxrate[] = RequestTool.getDoubles(request,"taxrate");
			String productid;
			String productname, specmode, batch;
			Double  subsum;

			Withdraw dp = new Withdraw();
			String id = MakeCode.getExcIDByRandomTableName("withdraw", 2, "WD");
			dp.setId(id);
			dp.setCid(cid);
			dp.setCname(cname);
			dp.setClinkman(decideman);
			dp.setCmobile(cmobile);
			dp.setTel(decidemantel);
			dp.setWarehouseid(warehouseid);
			dp.setWithdrawcause(withdrawcause);			
			dp.setBillno(billno);
			dp.setPaymentmode(Integer.parseInt(strpaymentmode));
			dp.setWithdrawsort(Integer.parseInt(strwithdrawsort));
			dp.setPrinttimes(0);
			dp.setMakeorganid(users.getMakeorganid());
			dp.setMakedeptid(users.getMakedeptid());
			dp.setMakeid(userid);
			dp.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			dp.setIsaudit(0);
			dp.setAuditid(0);
			dp.setIsblankout(Integer.valueOf(0));
			
			StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(dp.getId()).append(",").append(dp.getCname()).append(",")
		      .append(dp.getCmobile()).append(",").append(dp.getClinkman()).append(",")
		      .append(dp.getTel()).append(",").append(dp.getBillno()).append(",");

			AppWithdraw asl = new AppWithdraw();
			AppWithdrawDetail asld = new AppWithdrawDetail();
			WarehouseBitDafService wbds = new WarehouseBitDafService("withdraw_idcode","wid",dp.getWarehouseid());

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				batch = "";

		        subsum = DataFormat.countPrice(quantity[i], taxunitprice[i], discount[i], taxrate[i]);
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
//				sod.setIssettlement(0);				
				asld.addWithdrawDetail(sod);
				wbds.add(dp.getId(), sod.getProductid(), sod.getUnitid(), sod.getQuantity());

			}
			dp.setTotalsum(totalsum);
			dp.setKeyscontent(keyscontent.toString());
			asl.addWithdraw(dp);


			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 6,"零售退货>>新增销售退货,编号:"+id);

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
