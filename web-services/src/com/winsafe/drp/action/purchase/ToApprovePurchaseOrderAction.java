package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrderDetailForm;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ToApprovePurchaseOrderAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    String actid = request.getParameter("actid");
    String pbaid = request.getParameter("pbaid");

    try {
    	if(DbUtil.judgeApproveStatusToApprover("PurchaseOrder", id)){
        	  String result = "databases.record.isapprove";
    			request.setAttribute("result", result);
    			return new ActionForward("/sys/lockrecord.jsp");
          }
          if(DbUtil.judgeApproveStatusToApproverVeto("PurchaseOrder", id)){
        	  String result = "databases.record.isveto";
    			request.setAttribute("result", result);
    			return new ActionForward("/sys/lockrecord.jsp");
          }
      AppPurchaseOrder apb = new AppPurchaseOrder();
      PurchaseOrder pb = new PurchaseOrder();
      AppUsers au = new AppUsers();
      AppCustomer apv = new AppCustomer();
      AppDept ad = new AppDept();
      pb = apb.getPurchaseOrderByID(id);
      PurchaseOrderForm pbf= new PurchaseOrderForm();
      pbf.setId(id);
      pbf.setPpid(pb.getPpid());
      pbf.setProvidename(apv.getCustomer(pb.getPid()).getCname());
      pbf.setPlinkman(pb.getPlinkman());
      pbf.setTel(pb.getTel());
      pbf.setPurchasedeptname(ad.getDeptByID(pb.getPurchasedept()).getDeptname());
      pbf.setPurchaseidname(au.getUsersByid(pb.getPurchaseid()).getRealname());
      pbf.setPaymentmodename(Internation.getStringByKeyPosition("PaymentMode",
              request,
              pb.getPaymentmode(), "global.sys.SystemResource"));
      pbf.setTotalsum(pb.getTotalsum());
      pbf.setBatch(pb.getBatch());
      pbf.setReceivedate(DateUtil.formatDate(pb.getReceivedate()));
      pbf.setReceiveaddr(pb.getReceiveaddr());
      pbf.setIsrefername(Internation.getStringByKeyPosition("IsRefer",
            request,
            pb.getIsrefer(), "global.sys.SystemResource"));
      pbf.setApprovestatusname(Internation.getStringByKeyPosition("ApproveStatus",
            request,
            Integer.parseInt(pb.getApprovestatus().toString()), "global.sys.SystemResource"));
      pbf.setApprovedate(DateUtil.formatDate(pb.getApprovedate()));
      pbf.setMakeidname(au.getUsersByid(pb.getMakeid()).getRealname());
      pbf.setMakedate(DateUtil.formatDate(pb.getMakedate()));
      pbf.setRemark(pb.getRemark());
        
      
      AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
      List padls = apbd.getPurchaseOrderDetailByPoID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();
      
      PurchaseOrderDetail pod = null;
      for(int i=0;i<padls.size();i++){        
        pod = (PurchaseOrderDetail) padls.get(i);
		PurchaseOrderDetailForm pbdf = new PurchaseOrderDetailForm();
		pbdf.setProductid(pod.getProductid());
		pbdf.setProductname(pod.getProductname());
		pbdf.setSpecmode(pod.getSpecmode());
		// padf.setUnitid(Integer.valueOf(o[3].toString()));
		pbdf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", pod.getUnitid()));
		pbdf.setUnitprice(pod.getUnitprice());
		pbdf.setQuantity(pod.getQuantity());
		pbdf.setIncomequantity(pod.getIncomequantity());
		pbdf.setSubsum(pod.getSubsum());
		als.add(pbdf);
      }
      String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus2", request,
            "ApproveStatus", null, null);
      String stractid=Internation.getStringByKeyPositionDB("ActID",
              Integer.valueOf(actid));

      request.setAttribute("approvestatus",approvestatus);
      request.setAttribute("pbid",id);
      request.setAttribute("pbaid",pbaid);
      request.setAttribute("actid", actid);
      request.setAttribute("stractid", stractid);
      request.setAttribute("als",als);
      request.setAttribute("pbf",pbf);

      return mapping.findForward("toapprove");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
