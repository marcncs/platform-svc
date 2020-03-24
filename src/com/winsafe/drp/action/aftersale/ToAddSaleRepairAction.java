package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddSaleRepairAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {

			
			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
////				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
			
//			String warehourseselect = getWarehourseselect(wls, null);
			request.setAttribute("alw", alw);
//			request.setAttribute("warehourseselect", warehourseselect);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
	
	 private String getWarehourseselect(List whlist, Long whid){
		  StringBuffer sb = new StringBuffer();
		  sb.append("<select name=\"warehouseid\">");
		  for (int i = 0; i < whlist.size(); i++) {
				Object[] o = (Object[]) whlist.get(i);
				String selected = "";			
				if ( whid !=null && Long.valueOf(o[0].toString()) == whid ){
					selected = "selected";
				}
				sb.append("<option value=\"").append(
						Long.valueOf(o[0].toString())).append("\" ").append(selected).append(">").append(
						o[1].toString()).append("</option>");
			}
		  sb.append("</select>");
		  return sb.toString();
	  }

}
