package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddStockCheckAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			StockCheck sc = new StockCheck();
			String scid = MakeCode.getExcIDByRandomTableName("stock_check", 2,
					"SC");
			sc.setId(scid);
			sc.setWarehouseid(request.getParameter("warehouseid"));
			sc.setWarehousebit(request.getParameter("warehousebit"));
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

			
			String[] dwarehousebit = request.getParameterValues("dwarehousebit");
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double reckonquantity[] = RequestTool.getDoubles(request, "reckonquantity");
			double checkquantity[] = RequestTool.getDoubles(request, "checkquantity");

			
			AppProductStockpile aps = new AppProductStockpile();
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			for (int i = 0; i < productid.length; i++) {
				StockCheckDetail scd = new StockCheckDetail();
				scd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_check_detail", 0, "")));
				scd.setScid(scid);
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
