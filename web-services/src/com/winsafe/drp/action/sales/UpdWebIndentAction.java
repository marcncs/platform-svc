package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWebIndent;
import com.winsafe.drp.dao.AppWebIndentDetail;
import com.winsafe.drp.dao.WebIndent;

public class UpdWebIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppWebIndent aso = new AppWebIndent();
			WebIndent so = aso.getWebIndentByID(soid);
			AppWebIndentDetail appsod = new AppWebIndentDetail();
			
			String[] wdid = request.getParameterValues("wdid");
			String[] wid = request.getParameterValues("warehouseid");
			for ( int i=0; i<wdid.length; i ++ ){
				if ( wid[i].equals("") ){
					request.setAttribute("result", "databases.approve.fail");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			so.setEquiporganid(request.getParameter("equiporganid"));
			aso.updWebIndent(so);
			for ( int i=0; i<wdid.length; i ++ ){
				appsod.updWarehourseId(Long.valueOf(wdid[i]), Long.valueOf(wid[i]));
			}
					

			request.setAttribute("result", "databases.upd.success");
//			DBUserLog.addUserLog(userid, "修改网站订单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
