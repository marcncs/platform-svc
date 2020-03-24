package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddTakeTicketIdcodeiAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppTakeTicketIdcode app = new AppTakeTicketIdcode();
		AppProductStockpile appps = new AppProductStockpile();
		super.initdata(request);try{
			
			String billid = request.getParameter("billid");
			String productid = request.getParameter("prid");
			String wid = request.getParameter("wid");
			String pbatch = request.getParameter("pbatch");
			int unitid = RequestTool.getInt(request, "unitid");
			String[] warehousebit = request.getParameterValues("warehousebit");
			String[] batch = request.getParameterValues("batch");
			String[] producedate = request.getParameterValues("producedate");
			double[] quantity = RequestTool.getDoubles(request, "quantity");

			for ( int i=0; i<warehousebit.length; i++ ){
				ProductStockpile ps = appps.getProductStockpileByPidWid(productid, wid, warehousebit[i], batch[i]);
				if ( ps == null || ps.getStockpile().doubleValue() < quantity[i]){
					request.setAttribute("result", warehousebit[i]+"仓位数量不够,不能出库!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			TakeTicketIdcode pi = null;
			app.delTakeTicketIdcodeByPid(productid, billid, pbatch);
			for ( int i=0; i<warehousebit.length; i++ ){
				pi = new TakeTicketIdcode();
//				pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
				pi.setTtid(billid);
				pi.setProductid(productid);
				pi.setIsidcode(0);
				pi.setWarehousebit(warehousebit[i]);
				pi.setBatch(batch[i]);
				pi.setProducedate(producedate[i]);
				pi.setVad("");
				pi.setUnitid(unitid);
				pi.setQuantity(quantity[i]);
				pi.setPackquantity(0d);
				pi.setMakedate(DateUtil.getCurrentDate());
				app.addTakeTicketIdcode(pi);
			}
			 request.setAttribute("result", "databases.add.success");
			 UsersBean users = UserManager.getUser(request);
			 DBUserLog.addUserLog(users.getUserid(),7, "检货出库>>检货小票>>分配仓位,产品编号:"+productid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
