package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class UpdProductStockpileAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Long id = Long.valueOf(request.getParameter("id"));
    Double stockpile = Double.valueOf(request.getParameter("stockpile"));
    Double prepareout = Double.valueOf(request.getParameter("prepareout"));

    super.initdata(request);try{
      AppProductStockpile aps = new AppProductStockpile();
      //AppUsers au = new AppUsers();
       aps.setPrepareByID(id,stockpile ,prepareout);

		request.setAttribute("result", "databases.upd.success");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		//DBUserLog.addUserLog(userid,"调整预定出库量,编号："+id); 
		
      return mapping.findForward("updresult");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
