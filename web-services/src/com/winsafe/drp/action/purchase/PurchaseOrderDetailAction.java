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
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseOrderDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppPurchaseOrder apb = new AppPurchaseOrder();
			PurchaseOrder pb = new PurchaseOrder();
			AppUsers au = new AppUsers();
			AppCustomer apv = new AppCustomer();
			AppDept ad = new AppDept();
			pb = apb.getPurchaseOrderByID(id);
			PurchaseOrderForm pbf = new PurchaseOrderForm();
			pbf.setId(id);
			pbf.setPpid(pb.getPpid());
			pbf.setProvidename(apv.getCustomer(pb.getPid()).getCname());
			pbf.setPlinkman(pb.getPlinkman());
			pbf.setTel(pb.getTel());
			pbf.setBatch(pb.getBatch());
			pbf.setPurchasedeptname(ad.getDeptByID(pb.getPurchasedept())
					.getDeptname());
			pbf.setPurchaseidname(au.getUsersByid(pb.getPurchaseid())
					.getRealname());
			pbf.setPaymentmodename(Internation.getStringByPayPositionDB(pb.getPaymentmode()));
			pbf.setTotalsum(pb.getTotalsum());
			pbf.setReceivedate(pb.getReceivedate().toString());
			pbf.setReceiveaddr(pb.getReceiveaddr());
			pbf.setIsrefername(Internation.getStringByKeyPosition("IsRefer",
					request, pb.getIsrefer(), "global.sys.SystemResource"));
			pbf.setApprovestatusname(Internation.getStringByKeyPosition(
					"ApproveStatus", request, Integer.parseInt(pb
							.getApprovestatus().toString()),
					"global.sys.SystemResource"));
			pbf.setApprovedate(DateUtil.formatDate(pb.getApprovedate()));
			pbf.setMakeidname(au.getUsersByid(pb.getMakeid()).getRealname());
			pbf.setMakedate(DateUtil.formatDate(pb.getMakedate()));
			pbf.setRemark(pb.getRemark());
			pbf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, pb.getIsendcase(), "global.sys.SystemResource"));
			if ( pb.getEndcaseid() != null ){
				pbf.setEndcaseidname(au.getUsersByid(pb.getEndcaseid()).getRealname());
			}
			pbf.setEndcasedate(DateUtil.formatDate(pb.getEndcasedate()));
			pbf.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, pb.getIsblankout(), "global.sys.SystemResource"));
			if ( pb.getBlankoutid() != null ){
				pbf.setBlankoutidname(au.getUsersByid(pb.getBlankoutid()).getRealname());
			}			
			pbf.setBlankoutdate(DateUtil.formatDate(pb.getBlankoutdate()));

			AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
			List padls = apbd.getPurchaseOrderDetailByPoID(id);
			ArrayList als = new ArrayList();
			AppProduct ap = new AppProduct();

			PurchaseOrderDetail pod = null;
			for (int i = 0; i < padls.size(); i++) {
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

//			AppApproveFlowLog apba = new AppApproveFlowLog();
//			List aprv = apba.getApproveFlowLog(id);
//			ArrayList rvls = new ArrayList();
//			for (int r = 0; r < aprv.size(); r++) {
//				ApproveFlowLog afl = (ApproveFlowLog) aprv.get(r);
//				PurchaseBillApproveForm pbaf = new PurchaseBillApproveForm();
//				pbaf.setApproveidname(au.getUsersByID(afl.getApproveid())
//						.getRealname());
//				pbaf.setActidname(Internation.getStringByKeyPositionDB("ActID",
//						afl.getActid()));
//				pbaf.setApprovecontent(afl.getApprovecontent());
//				pbaf.setApprovename(Internation.getStringByKeyPosition(
//						"SubApproveStatus", request, afl.getApprove(),
//						"global.sys.SystemResource"));
//				pbaf.setApprovedate(afl.getApprovedate());
//				rvls.add(pbaf);
//			}
//
//			request.setAttribute("rvls", rvls);
			request.setAttribute("als", als);
			request.setAttribute("pbf", pbf);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "采购订单详情");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
