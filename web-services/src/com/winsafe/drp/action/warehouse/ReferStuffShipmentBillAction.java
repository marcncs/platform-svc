package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowDetail;
import com.winsafe.drp.dao.AppStuffShipmentBill;
import com.winsafe.drp.dao.AppStuffShipmentBillApprove;
import com.winsafe.drp.dao.StuffShipmentBillApprove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class ReferStuffShipmentBillAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    
    //Session 
    ////Connection 
    
    
    super.initdata(request);try{
    	String ssid = request.getParameter("ssid");

        String strspeed = request.getParameter("speedstr");
        int count = Integer.parseInt(request.getParameter("uscount"));
        
        AppStuffShipmentBillApprove asla = new AppStuffShipmentBillApprove();
        asla.delStuffShipmentBillApproveBySBID(ssid);

        StringTokenizer st = new StringTokenizer(strspeed, ",");

        for (int c = 0; c < count; c++) {

          AppApproveFlowDetail aaf= new AppApproveFlowDetail();
          List apls = aaf.getApproveFlowDetailByAFID(Long.valueOf(st.nextToken().trim()));
          
          for(int p=0;p<apls.size();p++){
          	Object[] o = (Object[])apls.get(p);
          	StuffShipmentBillApprove sla = new StuffShipmentBillApprove();
          	sla.setId(Long.valueOf(mc.getExcIDByRandomTableName("stuff_shipment_bill_approve",0,"")));
              sla.setSsid(ssid);
              sla.setApproveid(Long.valueOf(o[1].toString()));
              sla.setActid(Integer.valueOf(o[2].toString()));
              sla.setApprovecontent("");
              sla.setApprove(Integer.valueOf(0));
              
               asla.addStuffShipmentBillApprove(sla);
          }

        }
      AppStuffShipmentBill assb= new AppStuffShipmentBill();
       assb.updIsRefer(ssid);
 
      request.setAttribute("result", "databases.refer.success");
      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();
//      DBUserLog.addUserLog(userid,"提交材料出库单"); 
      
      return mapping.findForward("refer");
    }
    catch (Exception e) {
      
      e.printStackTrace();
    }
    finally {
      //
      
    }

    return new ActionForward(mapping.getInput());
  }
}
