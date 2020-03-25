package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdSaleRepairAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
	
		AppSaleRepair asl = new AppSaleRepair();
		try {
			String id = request.getParameter("id");
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			SaleRepair dp = asl.getSaleRepairByID(id);
			//
			
			String cname = request.getParameter("cname");
			String linkman = request.getParameter("clinkman");
			String tel = request.getParameter("tel");
			String warehouseinid = request.getParameter("warehouseinid");
			String warehouseoutid = request.getParameter("warehouseoutid");
			String tradesdate = request.getParameter("tradesdate");
			String remark = request.getParameter("remark");
			String strtotalsum = request.getParameter("totalsum");


			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strbatch[] = request.getParameterValues("batch");
			String strunitid[] = request.getParameterValues("unitid");
			String strquantity[] = request.getParameterValues("quantity");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strsubsum[] = request.getParameterValues("subsum");
	
			String productid;
			String productname, specmode, batch;
			Integer unitid;
			Double totalsum,unitprice, subsum, quantity;
			
			if (DataValidate.IsDouble(strtotalsum)) {
				totalsum = Double.valueOf(strtotalsum);
			} else {
				totalsum = Double.valueOf(0.00);
			}

			dp.setCid(cid);
			dp.setCname(cname);
			dp.setClinkman(linkman);
			dp.setTel(tel);
			dp.setWarehouseinid(Long.parseLong(warehouseinid));
			dp.setWarehouseoutid(Long.parseLong(warehouseoutid));
			dp.setRemark(remark);
			dp.setTradesdate(DateUtil.StringToDate(tradesdate));			
			dp.setTotalsum(totalsum);

			AppSaleRepairDetail asld = new AppSaleRepairDetail();
			 asld.delSaleRepairDetailBySrid(id);

			SaleRepairDetail sod = null;
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				batch = "";
				unitid = Integer.valueOf(strunitid[i]);				
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(strsubsum[i])) {
					subsum = Double.valueOf(strsubsum[i]);
				} else {
					subsum = Double.valueOf(0.00);
				}
				if (DataValidate.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0);
				}	
			

				sod = new SaleRepairDetail();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"sale_repair_detail", 0, "")));
				sod.setSrid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setBatch(batch);
				sod.setUnitid(unitid);
				sod.setQuantity(quantity);
				sod.setUnitprice(unitprice);
				sod.setSubsum(subsum);
				
				asld.addSaleRepairDetail(sod);
			}
			asl.updSaleRepair(dp);


			request.setAttribute("result", "databases.upd.success");
//			DBUserLog.addUserLog(userid, "修改销售返修");

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
