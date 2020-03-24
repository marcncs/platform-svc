package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppStuffShipmentBill;
import com.winsafe.drp.dao.AppStuffShipmentBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StuffShipmentBill;
import com.winsafe.drp.dao.StuffShipmentBillDetailForm;
import com.winsafe.drp.dao.StuffShipmentBillForm;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ToApproveStuffShipmentBillAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    String actid = request.getParameter("actid");
    
    super.initdata(request);try{
    	
      if(DbUtil.judgeApproveStatusToApprover("StuffShipmentBill", id)){
    	  String result = "databases.record.isapprove";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecord.jsp");
      }
      if(DbUtil.judgeApproveStatusToApproverVeto("StuffShipmentBill", id)){
    	  String result = "databases.record.isveto";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecord.jsp");
      }
      AppStuffShipmentBill assb = new AppStuffShipmentBill();
      StuffShipmentBill ssb = new StuffShipmentBill();
      AppUsers au = new AppUsers();
      AppDept ad= new AppDept();
      AppWarehouse aw = new AppWarehouse();
      ssb = assb.getStuffShipmentBillByID(id);
      StuffShipmentBillForm ssbf= new StuffShipmentBillForm();
      
      ssbf.setId(id);
//      ssbf.setWarehouseidname(aw.getWarehouseByID(ssb.getWarehouseid()).getWarehousename());
      ssbf.setShipmentsortname(Internation.getStringByKeyPositionDB("StuffShipmentSort",
             ssb.getShipmentsort()));
//      ssbf.setShipmentdeptname(ad.getDeptByID(ssb.getShipmentdept()).getDeptname());
      ssbf.setRequiredate(ssb.getRequiredate().toString().substring(0,10));
      ssbf.setRemark(ssb.getRemark());
      ssbf.setIsrefername(Internation.getStringByKeyPosition("IsRefer",
              request,
              ssb.getIsrefer(), "global.sys.SystemResource"));
      ssbf.setApprovestatusname(Internation.getStringByKeyPosition("ApproveStatus",
            request,
            ssb.getApprovestatus(), "global.sys.SystemResource"));
      ssbf.setApprovedate(String.valueOf(ssb.getApprovedate()));
      ssbf.setIsaudit(ssb.getIsaudit());
      ssbf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
              request,
              ssb.getIsaudit(), "global.sys.SystemResource"));

        if(ssb.getAuditid()>0){
//      	 ssbf.setAuditidname(au.getUsersByid(ssb.getAuditid()).getRealname());
        }else{
      	  ssbf.setAuditidname("");
        }
        ssbf.setAuditdate(ssb.getAuditdate());
//        ssbf.setMakeidname(au.getUsersByid(ssb.getMakeid()).getRealname());
        ssbf.setMakedate(ssb.getMakedate());
        
      AppStuffShipmentBillDetail assld = new AppStuffShipmentBillDetail();
      List sals = assld.getStuffShipmentBillDetailBySbID(id);
      ArrayList als = new ArrayList();

      for(int i=0;i<sals.size();i++){
          StuffShipmentBillDetailForm ssbdf = new StuffShipmentBillDetailForm();
          Object[] o = (Object[])sals.get(i);
          ssbdf.setProductid(o[2].toString());
          ssbdf.setProductname(o[3].toString());
          ssbdf.setSpecmode(o[4].toString());
          //padf.setUnitid(Integer.valueOf(o[3].toString()));
          ssbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",            
              Integer.parseInt(o[5].toString())));
          ssbdf.setBatch(o[6].toString());
          ssbdf.setUnitprice(Double.valueOf(o[7].toString()));
          ssbdf.setQuantity(Double.valueOf(o[8].toString()));
          ssbdf.setSubsum(Double.valueOf(o[9].toString()));
          als.add(ssbdf);
        }
      
      String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus", request,
            "ApproveStatus", "1", null);
      String stractid=Internation.getStringByKeyPositionDB("ActID",
              Integer.valueOf(actid));

      request.setAttribute("approvestatus",approvestatus);
      request.setAttribute("actid", actid);
      request.setAttribute("stractid", stractid);
      request.setAttribute("ssid",id);
      request.setAttribute("als",als);
      request.setAttribute("ssbf",ssbf);

      return mapping.findForward("toapprove");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
