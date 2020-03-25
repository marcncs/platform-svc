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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddIdcodeResetAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      IdcodeReset ir = new IdcodeReset();
      ir.setId(MakeCode.getExcIDByRandomTableName(
          "idcode_reset",2,"IR"));
      ir.setMemo(request.getParameter("memo"));
      ir.setMakeorganid(users.getMakeorganid());
//      ir.setMakedeptid(users.getMakedeptid());
//      ir.setMakeid(userid);
      ir.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
      ir.setIsaudit(0);
      ir.setAuditid(0l);
      
      
      String strproductid[] = request.getParameterValues("productid");
      String strproductname[] = request.getParameterValues("productname");
      String strspecmode[] = request.getParameterValues("specmode");
      String strquantity[] = request.getParameterValues("quantity");
      AppIdcodeResetDetail aprp = new AppIdcodeResetDetail();
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
      AppIdcodeReset ar = new AppIdcodeReset();
      ar.addIdcodeReset(ir);

      request.setAttribute("result", "databases.add.success");
//      DBUserLog.addUserLog(userid,"新增序号重置");//日志 

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
