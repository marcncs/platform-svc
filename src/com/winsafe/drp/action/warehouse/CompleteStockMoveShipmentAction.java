package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class CompleteStockMoveShipmentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();
		String id = request.getParameter("SMID");
		super.initdata(request);try{
			AppStockMove asm = new AppStockMove();
			AppOrgan ao = new AppOrgan();
			AppStockMoveDetail asmd = new AppStockMoveDetail();
			StockMove sm = asm.getStockMoveByID(id);
			if (sm.getIsaudit()==0) { 
				String result = "databases.record.noauditnoshipment";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			AppWarehouseVisit awv = new AppWarehouseVisit();
//			int w=awv.findWarehouseByUseridWid(sm.getOutwarehouseid(), userid);

//			if(w==0){
//	          	 String result = "databases.record.nopurview";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
//	           }
			
			
			 
			if(sm.getIsshipment()==1){
				 
				String result = "databases.record.already";
				request.setAttribute("result",result);
				return mapping.findForward("lock");
			}

			List pils = asmd.getStockMoveDetailBySmIDNew(id);
			

			AppShipmentBill appsb = new AppShipmentBill();
			ShipmentBill bill = appsb.getShipmentBillByID(sm.getId());
			if (bill != null) {
				return mapping.findForward("audit");
			}

			ShipmentBill sb = new ShipmentBill();
			String sbid = sm.getId();
			sb.setId(sbid);
//			sb.setSoid(sbid);
//			sb.setSaledept(sm.getMakedeptid());
//			sb.setSaleid(sm.getMakeid());
			sb.setCid(sm.getInorganid());
			sb.setCname(ao.getOrganByID(sm.getInorganid()).getOrganname());
			sb.setCmobile("");
			sb.setLinkman("");
			sb.setTel("");
			sb.setReceiveaddr("");
			sb.setRequiredate(sm.getMovedate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(sm.getTransportmode());
			sb.setTransportnum("");
			sb.setTotalsum(0d);
			sb.setRemark(sm.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(sm.getMakeorganid());
			sb.setMakedeptid(sm.getMakedeptid());
			sb.setMakeid(sm.getMakeid());
			sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(sm.getKeyscontent());
			
			StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(sb.getId()).append(",").append(sb.getCname()).append(",")
		      .append(sb.getCmobile()).append(",").append(sb.getLinkman()).append(",")
		      .append(sb.getTel()).append(",");

			StockMoveDetail pid = null;
			// ReceivableDetail rvd = null;
			// AppReceivableDetail apprvd = new AppReceivableDetail();

			ShipmentBillDetail sbd = null;
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
			boolean hasquantiry = false;
			for (int i = 0; i < pils.size(); i++) {
				pid = (StockMoveDetail) pils.get(i);
				
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
//					sbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//							"shipment_bill_detail", 0, "")));
					sbd.setSbid(sbid);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(sm.getOutwarehouseid());
//					sbd.setUnitid(Long.valueOf(pid.getUnitid()));
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(0d);
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(0d);
					appsbd.addShipmentBillDetail(sbd);
					hasquantiry = true;
					
					 keyscontent.append(sbd.getProductid()).append(",")
				        .append(sbd.getProductname()).append(",")
				        .append(sbd.getSpecmode()).append(",");
				}
			}
			if (hasquantiry) {
				sb.setKeyscontent(keyscontent.toString());
				appsb.addShipmentBill(sb);
			}
			
		//	asm.updStockMoveIsShipment(id,1,userid);
			
		      request.setAttribute("result", "databases.add.success");
//		      DBUserLog.addUserLog(userid,"库存调拨发货");

			return mapping.findForward("move");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
