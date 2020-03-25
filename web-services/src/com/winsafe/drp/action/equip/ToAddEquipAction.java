package com.winsafe.drp.action.equip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.Car;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddEquipAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);

    try{
 
		
		AppCar ac=new AppCar();
		String makeorganid = users.getMakeorganid();
		String Condition = " c.makeorganid = '" + makeorganid + "' ";
		List<Car> cls= ac.getAllCars(Condition);
		
		request.setAttribute("cls", cls);
    	 
      return mapping.findForward("success");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
