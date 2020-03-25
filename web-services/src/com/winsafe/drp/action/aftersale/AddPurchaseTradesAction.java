package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPurchaseTradesAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			
			PurchaseTrades dp = new PurchaseTrades();
			String id = MakeCode.getExcIDByRandomTableName("purchase_trades",
					2, "PT");
			dp.setId(id);
			dp.setProvideid(cid);
			dp.setProvidename(request.getParameter("cname"));
			dp.setPlinkman(request.getParameter("clinkman"));
			dp.setTel(request.getParameter("tel"));
			dp.setWarehouseinid(request.getParameter("warehouseinid"));
			dp.setWarehouseoutid(request.getParameter("warehouseoutid"));
			dp.setRemark(request.getParameter("remark"));
			dp.setTradesdate(RequestTool.getDate(request, "tradesdate"));
			dp.setNewbatch("0");
			dp.setMakeid(userid);
			dp.setMakeorganid(users.getMakeorganid());
			dp.setMakedeptid(users.getMakedeptid());
			dp.setMakedate(DateUtil.getCurrentDate());
			dp.setIsaudit(0);
			dp.setAuditid(0);
			dp.setIsreceive(0);
			dp.setIsblankout(0);
			dp.setBlankoutid(0);
			dp.setTakestatus(0);
			dp.setKeyscontent(dp.getId()+","+dp.getProvideid()+","+dp.getProvidename()+","+dp.getPlinkman()+","+dp.getTel());

			AppPurchaseTrades asl = new AppPurchaseTrades();
			AppPurchaseTradesDetail asld = new AppPurchaseTradesDetail();
			PurchaseTradesDetail sod = null;
			
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_trades_idcode","ptid",dp.getWarehouseinid());

			if (productid != null) {
				PurchaseTradesDetail sods[] = new PurchaseTradesDetail[productid.length];
				for (int i = 0; i < productid.length; i++) {
					sod = new PurchaseTradesDetail();
					sod.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"purchase_trades_detail", 0, "")));
					sod.setPtid(id);
					sod.setProductid(productid[i]);
					sod.setProductname(productname[i]);
					sod.setSpecmode(specmode[i]);
					sod.setBatch("");
					sod.setUnitid(unitid[i]);
					sod.setQuantity(quantity[i]);
					sod.setUnitprice(unitprice[i]);
					sod.setTakequantity(0d);
					sod.setSubsum(sod.getUnitprice()*sod.getQuantity());
					sods[i] = sod;
					wbds.add(dp.getId(), sod.getProductid(), sod.getUnitid(), sod.getQuantity());
				}

				asld.addPurchaseTradesDetail(sods);
			}			
			asl.addPurchaseTrades(dp);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,2, "采购换货>>新增采购换货单,编号:"+id);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
