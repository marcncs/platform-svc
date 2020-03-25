package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdPurchaseTradesAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		AppPurchaseTrades asl = new AppPurchaseTrades();
		try {
			String id = request.getParameter("id");
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			PurchaseTrades dp = asl.getPurchaseTradesByID(id);
			PurchaseTrades olddp = (PurchaseTrades)BeanUtils.cloneBean(dp);
			dp.setProvideid(cid);
			dp.setProvidename(request.getParameter("cname"));
			dp.setPlinkman(request.getParameter("clinkman"));
			dp.setTel(request.getParameter("tel"));
			dp.setWarehouseinid(request.getParameter("warehouseinid"));
			dp.setWarehouseoutid(request.getParameter("warehouseoutid"));
			dp.setRemark(request.getParameter("remark"));
			dp.setTradesdate(RequestTool.getDate(request, "tradesdate"));	
			dp.setKeyscontent(dp.getId()+","+dp.getProvideid()+","+dp.getProvidename()+","+dp.getPlinkman()+","+dp.getTel());
			

			AppPurchaseTradesDetail asld = new AppPurchaseTradesDetail();
			asld.delPurchaseTradesDetailByPtid(id);

			PurchaseTradesDetail sod = null;
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			WarehouseBitDafService wbds = new WarehouseBitDafService("purchase_trades_idcode","ptid",dp.getWarehouseinid());
			wbds.del(dp.getId(), productid);
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
			asl.updPurchaseTrades(dp);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 2,"采购换货>>修改采购换货单,编号:"+id,olddp, dp);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
