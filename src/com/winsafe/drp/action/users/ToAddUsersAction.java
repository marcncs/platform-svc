package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.UserLimit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.hbm.util.Internation;


public class ToAddUsersAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
    try{
    	/*UserLimit ul = new UserLimit();
    	if(ul.userLimit()==1){
          	 String result = "databases.users.userlimit";
               request.setAttribute("result", result);
               return new ActionForward("/sys/userlimitreturn.jsp");
           }	*/
    	
     String sexselect = Internation.getSelectTagByKeyAll("Sex",request,"sex",false,null );
    
     String status = Internation.getSelectTagByKeyAll("YesOrNo", request,
             "status", "1", null);
     String isCwid = Internation.getSelectTagByKeyAll("YesOrNo", request,
             "isCwid", "0", null);
     String islogin = Internation.getSelectTagByKeyAll("YesOrNo",request,"islogin","1",null);
     String iscall = Internation.getSelectTagByKeyAll("YesOrNo", request,
             "iscall", "0", null);
     
     AppCountryArea aca = new AppCountryArea();
     List list = aca.getProvince();

     ArrayList cals = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			CountryArea ca = new CountryArea();
			Object ob[] = (Object[]) list.get(i);
			ca.setId(Integer.valueOf(ob[0].toString()));
			ca.setAreaname(ob[1].toString());
			ca.setParentid(Integer.valueOf(ob[2].toString()));
			ca.setRank(Integer.valueOf(ob[3].toString()));
			cals.add(ca);
		}

     request.setAttribute("cals", cals);

     request.setAttribute("sexselect",sexselect);
     request.setAttribute("status", status);
     request.setAttribute("islogin",islogin);
     request.setAttribute("iscall",iscall);
     request.setAttribute("isCwid",isCwid);
     request.setAttribute("makeorganid",users.getMakeorganid());
     AppBaseResource appBaseResource = new AppBaseResource();
     List salesUserType = appBaseResource.getBaseResource("SalesUserType");
     request.setAttribute("salesUserType",salesUserType);
     request.setAttribute("ucategory",UserCategary.values());
      return mapping.findForward("toadd");
    }catch(Exception e){
    	e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
