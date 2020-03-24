package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdStockCheckAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppStockCheck asc = new AppStockCheck();
			StockCheck sc = asc.getStockCheckByID(id);
			StockCheck oldsc = (StockCheck)BeanUtils.cloneBean(sc);
			sc.setWarehouseid(request.getParameter("warehouseid"));
			sc.setWarehousebit(request.getParameter("warehousebit"));
			sc.setMemo(request.getParameter("memo"));
			sc.setKeyscontent(sc.getId()+","+sc.getMemo());

			
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			AppProductStockpile aps = new AppProductStockpile();
			AppStockCheckIdcode appsci = new AppStockCheckIdcode();
			

			List<StockCheckDetail> ls = ascd.getStockCheckDetailBySmID(id);			
			for (StockCheckDetail scd : ls) {
				aps.IsLock(scd.getProductid(), scd.getBatch(),
						sc.getWarehouseid(), scd.getWarehousebit(), 0);
			}

			
			String[] dwarehousebit = request.getParameterValues("dwarehousebit");
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double reckonquantity[] = RequestTool.getDoubles(request, "reckonquantity");
			double checkquantity[] = RequestTool.getDoubles(request, "checkquantity");			

			ascd.delStockCheckDetailBySmID(id);		
			appsci.delStockCheckIdcodeByScid(id);

			for (int i = 0; i < productid.length; i++) {
				StockCheckDetail scd = new StockCheckDetail();
				scd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_check_detail", 0, "")));
				scd.setScid(sc.getId());
				scd.setWarehousebit(dwarehousebit[i]);
				scd.setProductid(productid[i]);
				scd.setProductname(productname[i]);
				scd.setSpecmode(specmode[i]);
				scd.setUnitid(unitid[i]);
				scd.setBatch(batch[i]);
				scd.setUnitprice(unitprice[i]);
				scd.setReckonquantity(reckonquantity[i]);
				scd.setCheckquantity(checkquantity[i]);
				ascd.addStockCheckDetail(scd);
				
				
				if ( sc.getIsbar() == 1 ){					
					appsci.copyIdcodeToStockCheckIdcode(scd.getScid(), sc.getWarehouseid(), 
							scd.getWarehousebit(), scd.getBatch(), scd.getProductid());
				}

				
				aps.IsLock(scd.getProductid(), scd.getBatch(), sc.getWarehouseid(), scd.getWarehousebit(),1);
			}
			asc.upStockCheck(sc);
			request.setAttribute("result", "databases.upd.success");

		      DBUserLog.addUserLog(userid,7, "库存盘点>>修改库存盘点,编号:"+id, oldsc, sc); 
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
