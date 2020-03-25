package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.JsonUtil;

public class SetTakeTicketScannerNoAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		try {
			String ttid=request.getParameter("ttid");
			String scannerNo=request.getParameter("scannerNo");
			AppTakeTicket appTakeTicket = new AppTakeTicket();
			TakeTicket takeTicket = appTakeTicket.getTakeTicketById(ttid);
			//修改单据中的采集器编号
			takeTicket.setScannerNo(scannerNo);
			json.put("result", 1);
			json.put("msg", "单据["+ttid+"]已指定采集器[" + scannerNo + "]");
			
		} catch (Exception e) {
			json.put("result", 0);
			json.put("msg", "单据指定采集器失败!");
		}
		finally{
			JsonUtil.setJsonObj(response, json);
		}
		return null;
	}
}
