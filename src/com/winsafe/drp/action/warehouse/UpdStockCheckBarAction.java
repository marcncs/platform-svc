package com.winsafe.drp.action.warehouse;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
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

public class UpdStockCheckBarAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);
		try{
			
			String id = request.getParameter("ID");
			AppStockCheck asa = new AppStockCheck();
			StockCheck sc = asa.getStockCheckByID(id);
			
			
			sc.setMemo(request.getParameter("memo"));			
			sc.setKeyscontent(sc.getId()+","+sc.getMemo());
			
			String[] scdid = request.getParameterValues("scdid");
			String[] dwarehousebit = request.getParameterValues("dwarehousebit");
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double reckonquantity[] = RequestTool.getDoubles(request, "reckonquantity");
			double checkquantity[] = RequestTool.getDoubles(request, "checkquantity");		
			
			
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			AppStockCheckIdcode appsci = new AppStockCheckIdcode();
			
			List<StockCheckDetail> scdlist = ascd.getStockCheckDetailBySmID(id);
			
			if ( scdid == null ){
				ascd.delStockCheckDetailBySmID(id);		
				appsci.delStockCheckIdcodeByScid(id);
			}else{
				List idlist  = Arrays.asList(scdid);
				for ( StockCheckDetail scd : scdlist ){
					if ( !idlist.contains(scd.getId().toString()) ){
						appsci.delStockCheckIdcodeByPid(id, scd.getProductid(),scd.getWarehousebit(),
								scd.getBatch());
						ascd.delStockCheckDetailByID(scd.getId());
					}
				}
			}
			
				
			for (int i = 0; i < productid.length; i++) {
				if ( scdid!=null && scdid[i]!=null && !"".equals(scdid[i]) ){
					continue;
				}
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
				
				appsci.copyIdcodeToStockCheckIdcode(scd.getScid(), sc.getWarehouseid(), 
						scd.getWarehousebit(), scd.getBatch(), scd.getProductid());
				//aps.IsLock(scd.getProductid(), scd.getBatch(), sc.getWarehouseid(), scd.getWarehousebit(),1);
			}

			
			asa.upStockCheck(sc);
			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 7, "库存盘点>>修改条码产品盘点单,编号:"+id);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
