package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesDetail;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdSaleTradesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		super.initdata(request);
		AppSaleTrades aso = new AppSaleTrades();

		try {
			SaleTrades dp = aso.getSaleTradesByID(id);
			SaleTrades olds = (SaleTrades) BeanUtils.cloneBean(dp);

			if (dp.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			dp.setCid(cid);
			dp.setCname(request.getParameter("cname"));
			dp.setClinkman(request.getParameter("clinkman"));
			dp.setCmobile(request.getParameter("cmobile"));
			dp.setTel(request.getParameter("tel"));
			dp.setWarehouseinid(request.getParameter("warehouseinid"));
			dp.setWarehouseid(request.getParameter("warehouseid"));
			dp.setTradesdate(RequestTool.getDate(request, "tradesdate"));
			dp.setRemark(request.getParameter("remark"));
			dp.setTradessort(RequestTool.getInt(request, "tradessort"));

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			//String warehouseid[] = request.getParameterValues("warehouseid");

			AppSaleTradesDetail asld = new AppSaleTradesDetail();
			asld.delSaleTradesDetailByRID(id);
			WarehouseBitDafService wbds = new WarehouseBitDafService("sale_trades_idcode","stid",dp.getWarehouseinid());
			wbds.del(dp.getId(), productid);
			for (int i = 0; i < productid.length; i++) {				
				SaleTradesDetail sod = new SaleTradesDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_trades_detail", 0, "")));
				sod.setStid(id);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				sod.setUnitid(unitid[i]);
				sod.setQuantity(quantity[i]);
				//sod.setWarehouseid(warehouseid[i]);
				sod.setTakequantity(0d);
				asld.addSaleTradesDetail(sod);
				wbds.add(dp.getId(), sod.getProductid(), sod.getUnitid(), sod.getQuantity());
			}

			aso.updSaleTrades(dp);


			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 6,"销售换货>>修改销售换货,编号："+id,olds,dp);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
