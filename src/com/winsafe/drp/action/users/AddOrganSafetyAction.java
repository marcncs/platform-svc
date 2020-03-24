package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.OrganSafetyIntercalate;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddOrganSafetyAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

    AppOrganSafetyIntercalate aws = new AppOrganSafetyIntercalate();

    try {
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
      
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      String oid = (String) request.getSession().getAttribute("sjoid");
      
      String[] strproductid = request.getParameterValues("productid");
      String[] strsafetyl = request.getParameterValues("safetyl");
      String[] strsafetyh = request.getParameterValues("safetyh");
      
      for(int i=0;i<strproductid.length;i++){
      
      OrganSafetyIntercalate ws = new OrganSafetyIntercalate();
      ws.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_safety_intercalate",0,"")));
      ws.setOrganid(oid);
      ws.setProductid(strproductid[i]);
      ws.setSafetyl(Double.valueOf(strsafetyl[i]));
      ws.setSafetyh(Double.valueOf(strsafetyh[i]));


      aws.addOrganSafetyIntercalate(ws);
      
      }
      
      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,11,"机构设置>>新增产品报警,机构编号:"+oid);

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();

    }
    finally {

    }

    return mapping.getInputForward();
  }

}
