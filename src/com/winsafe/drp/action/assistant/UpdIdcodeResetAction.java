package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.IdcodeResetDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;

public class UpdIdcodeResetAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();
      String id = request.getParameter("id");
      AppIdcodeReset ar = new AppIdcodeReset();
      IdcodeReset ir = ar.getIdcodeResetById(id);    
      ir.setMemo(request.getParameter("memo"));
     
      
      
      String strproductid[] = request.getParameterValues("productid");
      String strproductname[] = request.getParameterValues("productname");
      String strspecmode[] = request.getParameterValues("specmode");
      String strquantity[] = request.getParameterValues("quantity");
      AppIdcodeResetDetail aprp = new AppIdcodeResetDetail();
      aprp.delIdcodeResetDetailByIrid(id);
      IdcodeResetDetail ird = null;
      for (int j = 0; j < strproductid.length; j++) {
    	  ird = new IdcodeResetDetail();
    	  ird.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("idcode_reset_detail",0,"")));
    	  ird.setIrid(ir.getId());
    	  ird.setProductid(strproductid[j]);
    	  ird.setProductname(strproductname[j]);
    	  ird.setSpecmode(strspecmode[j]);
    	  ird.setQuantity(DataValidate.IsDouble(strquantity[j])?Double.valueOf(strquantity[j]):0);
    	  aprp.addIdcodeResetDetail(ird);    	 
      }      
      ar.updIdcodeReset(ir);

      request.setAttribute("result", "databases.upd.success");
//      DBUserLog.addUserLog(userid,"修改序号重置");//日志 

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
