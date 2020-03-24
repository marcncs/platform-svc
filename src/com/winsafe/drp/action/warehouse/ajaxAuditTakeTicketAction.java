package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;

/*
 * 用来ajax方法来判断是否出库数量大于库存数量
 */
public class ajaxAuditTakeTicketAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
		String id = request.getParameter("id");
		//声明所需的dao
		AppTakeTicketDetail apid = new AppTakeTicketDetail();
		AppFUnit af = new AppFUnit();
		AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
		AppProductStockpile appps = new AppProductStockpile();
		AppTakeTicket apb = new AppTakeTicket(); 
		AppWarehouse aw=new AppWarehouse();
		//最后返回的对象
		JSONObject json = new JSONObject();
		//得到TT明细
		List<TakeTicketIdcode> alllist = apidcode.getTakeTicketIdcodeByttid(id);	
		TakeTicket tt = apb.getTakeTicketById(id);
		Warehouse warehouse=aw.getWarehouseByID(tt.getWarehouseid());
		//仓库属性3为产成品入库仓库
		String warehouseType=String.valueOf(warehouse.getWarehouseproperty()!=null?warehouse.getWarehouseproperty():0);
		//单据类型1为订购、2为转仓
		String tttype=String.valueOf(tt.getBsort());
		
		

	    
	    ///////////////////////

		//如果没有出现出库数量大于库存数量则state为空
		json.put("id", id);
		json.put("state", "");
		json.put("warehouseType", warehouseType);
		json.put("tttype", tttype);
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out2 = response.getWriter();
	    out2.print(json.toString());
	    if(1==1){
	    return null;
	    }
	    ///////////////////////
		
		
		
		for ( TakeTicketIdcode tti : alllist) {
			double q1 = af.getPkgQuantityInTakeTicket(tti.getProductid(), tti.getTtid());
			AppProduct appProduct = new AppProduct();
			Product product = appProduct.getProductByID(tti.getProductid());
			String batch = "";
			//判断当前产品是否需要批号处理
			if(product.getIsbatch() == 1){
				batch = tti.getBatch();
			}
			double q2 = appps.getStockpileByPidWid(tti.getProductid(), tt.getWarehouseid(), tti.getWarehousebit(),batch);
			//判断出库数量是否大于库存数量
			if (q1 > q2) {
				//当负出库时json对象记录该单据的id和该单据的状态state
				json.put("id", id);
				json.put("warehouseType", warehouseType);
				json.put("tttype", tttype);
				json.put("state", "overQuantity");
				response.setContentType("text/html; charset=UTF-8");
			    response.setHeader("Cache-Control", "no-cache");
			    PrintWriter out = response.getWriter();
			    out.print(json.toString());
			    return null;
			}
			
		}
		//如果没有出现出库数量大于库存数量则state为空
		json.put("id", id);
		json.put("state", "");
		json.put("warehouseType", warehouseType);
		json.put("tttype", tttype);
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
		
		
		
		return null;
	}

}
