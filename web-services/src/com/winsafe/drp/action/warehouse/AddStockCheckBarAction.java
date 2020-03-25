package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddStockCheckBarAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String warehouseid = request.getParameter("warehouseid");
			String warehousebit = request.getParameter("warehousebit");
			String psid = request.getParameter("psid");
			String productid = request.getParameter("productid");
			String iszero = request.getParameter("iszero");
			StringBuffer sql = new StringBuffer();
			sql.append(" where  p.isidcode=1 and ps.productid=p.id and ps.warehouseid='"+warehouseid+"' ");
			if ( warehousebit != null && !"".equals(warehousebit) ){
				sql.append(" and ps.warehousebit='"+warehousebit+"' ");
			}
			if ( psid != null && !"".equals(psid) ){
				sql.append(" and p.psid like '"+psid+"%' ");
			}
			if ( productid != null && !"".equals(productid) ){
				sql.append(" and p.id='"+productid+"' ");
			}
			if ( iszero != null && "0".equals(iszero) ){
				sql.append(" and ps.stockpile=0 ");
			}
			if ( iszero != null && "1".equals(iszero) ){
				sql.append(" and ps.stockpile>0 ");
			}

			
			AppProductStockpile aps = new AppProductStockpile();
			List list = aps.getCheckStockpileEmpty(sql.toString());

			StockCheck sc = new StockCheck();
			String scid = MakeCode.getExcIDByRandomTableName("stock_check", 2,
					"SC");
			sc.setId(scid);
			sc.setWarehouseid(warehouseid);
			sc.setWarehousebit(warehousebit);
			sc.setMemo(request.getParameter("memo"));
			sc.setMakeorganid(users.getMakeorganid());
			sc.setMakedeptid(users.getMakedeptid());
			sc.setMakeid(userid);
			sc.setMakedate(DateUtil.getCurrentDate());
			sc.setIsaudit(0);
			sc.setAuditid(0);
			sc.setIscreate(0);
			sc.setIsbar(RequestTool.getInt(request, "isbar"));
			sc.setKeyscontent(sc.getId()+","+sc.getMemo());
			
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			AppStockCheckIdcode appsci = new AppStockCheckIdcode();
			for (int i = 0; i < list.size(); i++) {
				Object[] ppobj = (Object[]) list.get(i);
				ProductStockpile ps = (ProductStockpile) ppobj[0];
				Product o = (Product) ppobj[1];				
				
				StockCheckDetail scd = new StockCheckDetail();
				scd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_check_detail", 0, "")));
				scd.setScid(scid);
				scd.setWarehousebit(ps.getWarehousebit());
				scd.setProductid(o.getId());
				scd.setProductname(o.getProductname());
				scd.setSpecmode(o.getSpecmode());
				scd.setUnitid(o.getCountunit());
				scd.setBatch(ps.getBatch());
				scd.setUnitprice(ProductCostService.getCost(warehouseid, scd.getProductid(), scd.getBatch()));
				scd.setReckonquantity(ps.getStockpile());
				scd.setCheckquantity(0.00);
				ascd.addStockCheckDetail(scd);
				
				
				if ( sc.getIsbar() == 1 ){					
					appsci.copyIdcodeToStockCheckIdcode(scd.getScid(), sc.getWarehouseid(), 
							scd.getWarehousebit(), scd.getBatch(), scd.getProductid());
				}

				
				aps.IsLock(scd.getProductid(), scd.getBatch(), sc.getWarehouseid(), scd.getWarehousebit(),1);
			}

			AppStockCheck asa = new AppStockCheck();
			asa.addStockCheck(sc);
			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 7, "库存盘点>>新增库存盘点单,编号:"+scid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
