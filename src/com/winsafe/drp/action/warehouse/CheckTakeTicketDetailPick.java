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
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.util.JsonUtil;

public class CheckTakeTicketDetailPick extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TakeTicket tt = (TakeTicket)request.getSession().getAttribute("sof");
			String prefix = tt.getBillno().substring(0, 2);
			if("OW".equals(prefix)){
				JSONObject json = new JSONObject();
				json.put("result", 1);
				JsonUtil.setJsonObj(response, json);
			}
			Boolean checkflag = getPickFlag(tt);
			if(checkflag == false){
				JSONObject json = new JSONObject();
				json.put("result", 0);
				JsonUtil.setJsonObj(response, json);
			}
			if(checkflag == true){
				//判断是否已验货
				if(tt.getIsChecked() == null || tt.getIsChecked() != 2){
					JSONObject json = new JSONObject();
					json.put("result", 2);
					JsonUtil.setJsonObj(response, json);
				}else {
					JSONObject json = new JSONObject();
					json.put("result", 1);
					JsonUtil.setJsonObj(response, json);
				}
			}
			
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("result", 0);
			JsonUtil.setJsonObj(response, json);
		}
		return null;
	}
	
	/**
	 * 判断TT明细是否有一个检货
	 * @param tt
	 * @return
	 * @throws Exception 
	 */
	private Boolean getPickFlag(TakeTicket tt) throws Exception{
		AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
		List<TakeTicketDetail> details = appTakeTicketDetail.getTakeTicketDetailByTtid(tt.getId());
		for (TakeTicketDetail takeTicketDetail : details) {
			if(takeTicketDetail.getIsPicked() != 1){
				return false;
			}
		}
		return true;
	}
}
