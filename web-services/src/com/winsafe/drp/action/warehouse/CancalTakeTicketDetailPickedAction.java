package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.util.JsonUtil;

public class CancalTakeTicketDetailPickedAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TakeTicket tt = (TakeTicket)request.getSession().getAttribute("sof");
			//已复核
			if(tt.getIsaudit() == 1){
				JSONObject json = new JSONObject();
				json.put("result", 0);
				JsonUtil.setJsonObj(response, json);
				return null;
			}
			//已检货
			if(tt.getIsChecked() == 1 || tt.getIsChecked() == 2){
				JSONObject json = new JSONObject();
				json.put("result", 0);
				JsonUtil.setJsonObj(response, json);
				return null;
			}
			String pid = request.getParameter("productid");
			String whid = request.getParameter("warehouseid");
			String ttdid = request.getParameter("ttdid");
			String quantity = request.getParameter("quantity");
			AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
			AppProductStockpile appProductStockpile = new AppProductStockpile();
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			List<TakeTicketDetailBatchBit> batchBits = appTakeTicketDetailBatchBit.getBatchBitByTTDID(Integer.valueOf(ttdid));
			//得到库位库存表
			//返还预出库
			for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : batchBits) {
				//还原预出库库存				
				appProductStockpile.freeStockpile(takeTicketDetailBatchBit.getProductid(),takeTicketDetailBatchBit.getUnitid() ,tt.getWarehouseid(),takeTicketDetailBatchBit.getWarehouseBit(),takeTicketDetailBatchBit.getBatch(),takeTicketDetailBatchBit.getQuantity());
				apsa.freeStockpile(takeTicketDetailBatchBit.getProductid(),takeTicketDetailBatchBit.getUnitid() , tt.getWarehouseid(),takeTicketDetailBatchBit.getBatch(),takeTicketDetailBatchBit.getQuantity());
			
				appTakeTicketDetailBatchBit.deleteTakeTicketDetailBatchBit(takeTicketDetailBatchBit);
			}
			AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
			TakeTicketDetail takeTicketDetail = appTakeTicketDetail.getTakeTicketDetailByID(Integer.valueOf(ttdid));
			takeTicketDetail.setIsPicked(0);
			appTakeTicketDetail.updTakeTicketDetail(takeTicketDetail);
			JSONObject json = new JSONObject();
			json.put("result", 1);
			JsonUtil.setJsonObj(response, json);
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("result", 0);
			JsonUtil.setJsonObj(response, json);
		}
		// TODO Auto-generated method stub
		return null;
	}
}
