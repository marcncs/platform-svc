package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSaleTradesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			SaleTrades dp = new SaleTrades();
			String id = MakeCode.getExcIDByRandomTableName("sale_trades", 2, "ST");
			dp.setId(id);
			dp.setCid(cid);
			dp.setCname(request.getParameter("cname"));
			dp.setCmobile(request.getParameter("cmobile"));
			dp.setClinkman(request.getParameter("decideman"));
			dp.setTel(request.getParameter("tel"));
			dp.setWarehouseinid(request.getParameter("warehouseinid"));
			dp.setWarehouseid(request.getParameter("warehouseid"));
			dp.setSendaddr(request.getParameter("sendaddr"));
			dp.setRemark(request.getParameter("remark"));
			dp.setTradesdate(RequestTool.getDate(request, "tradesdate"));
			dp.setTradessort(RequestTool.getInt(request, "tradessort"));
			dp.setMakeorganid(users.getMakeorganid());
			dp.setMakedeptid(users.getMakedeptid());
			dp.setMakeid(userid);
			dp.setMakedate(DateUtil.getCurrentDate());
			dp.setIsaudit(0);
			dp.setAuditid(0);
			dp.setIsblankout(0);
			dp.setIsendcase(0);
			dp.setTradessort(0);
			
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			//String strbatch[] = request.getParameterValues("batch");
			int unitid[] = RequestTool.getInts(request,"unitid");
			//String warehouseid[] = request.getParameterValues("warehouseid");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			//String stroldbatch[] = request.getParameterValues("oldbatch");

			
			AppSaleTradesDetail asld = new AppSaleTradesDetail();
			SaleTradesDetail sod = null;
			WarehouseBitDafService wbds = new WarehouseBitDafService("sale_trades_idcode","stid",dp.getWarehouseinid());

			for (int i = 0; i < productid.length; i++) {
				 sod = new SaleTradesDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_trades_detail", 0, "")));
				sod.setStid(id);
				sod.setProductid(productid[i]);
				sod.setProductname(productname[i]);
				sod.setSpecmode(specmode[i]);
				//sod.setBatch(batch);
				sod.setUnitid(unitid[i]);
				sod.setQuantity(quantity[i]);
				//sod.setWarehouseid(warehouseid[i]);
				//sod.setOldbatch(oldbatch);
				sod.setTakequantity(0d);				
				asld.addSaleTradesDetail(sod);
				wbds.add(dp.getId(), sod.getProductid(), sod.getUnitid(), sod.getQuantity());
			}
			AppSaleTrades asl = new AppSaleTrades();
			asl.addSaleTrades(dp);

			request.setAttribute("result","databases.add.success");
			DBUserLog.addUserLog(userid,6, "销售换货>>新增销售换货,编号："+id);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
