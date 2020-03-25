package com.winsafe.drp.action.equip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppEquipDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.EquipDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelEquipAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			String soid = request.getParameter("SOID");
			AppEquip aso = new AppEquip();
			AppEquipDetail asld = new AppEquipDetail();
			AppShipmentBill appsb = new AppShipmentBill();
			List<EquipDetail> listed = asld.getEquipDetailByEID(soid);
			for ( EquipDetail ed : listed ){
				ShipmentBill sb = appsb.getShipmentBillByID(ed.getSbid());
				if ( sb != null && sb.getIsaudit() == 1 ){
					request.setAttribute("result", "datebases.record.istransnotdel");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			for ( EquipDetail ed : listed ){
				appsb.updIsTrans(0, ed.getSbid());
			}
			
			 aso.delEquip(soid);
			
			 asld.delEquipDetailByEID(soid);
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,8,"配送单>>删除配送单,编号："+soid ); 
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
