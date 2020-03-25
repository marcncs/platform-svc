package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetailForm;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.hbm.util.Internation;

public class ToTransPurchanseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppPurchaseBill apb = new AppPurchaseBill();
			AppUsers au = new AppUsers();
//			AppProvider apv = new AppProvider();
			PurchaseBill pb = apb.getPurchaseBillByID(id);
			PurchaseBillForm pbf = new PurchaseBillForm();
			pbf.setId(id);
			pbf.setPpid(pb.getPpid());
			pbf.setPid(pb.getPid());
			pbf.setPname(pb.getPname());
			pbf.setPlinkman(pb.getPlinkman());
			pbf.setTel(pb.getTel());
			pbf.setPurchasedept(pb.getPurchasedept());
			pbf.setPurchaseid(pb.getPurchaseid());
			pbf.setTotalsum(pb.getTotalsum());
			pbf.setReceivedate(pb.getReceivedate());
			pbf.setReceiveaddr(pb.getReceiveaddr());
			pbf.setPaymode(pb.getPaymode());
//			pbf.setPaymodename(Internation.getSelectTagByKeyAll("PayMode",
//					request, "paymode", String.valueOf(pb.getPaymode()), null));
			pbf.setMakeidname(au.getUsersByid(pb.getMakeid()).getRealname());
			pbf.setMakedate(pb.getMakedate());
			pbf.setRemark(pb.getRemark());
			pbf.setIstransferadsum(pb.getIstransferadsum());
//			pbf.setIstransferadsumname(Internation.getStringByKeyPosition(
//					"YesOrNo", request, pb.getIstransferadsum(),
//					"global.sys.SystemResource"));

			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();
			List padls = apbd.getPurchaseBillDetailByPbIDNoTrans(id);
			ArrayList als = new ArrayList();
//			System.out.println(id+"----"+padls.size());
			for (int i = 0; i < padls.size(); i++) {
				PurchaseBillDetail pbd = (PurchaseBillDetail) padls.get(i);
				PurchaseBillDetailForm pbdf = new PurchaseBillDetailForm();
				pbdf.setId(pbd.getId());
				pbdf.setProductid(pbd.getProductid());
				pbdf.setProductname(pbd.getProductname());
				pbdf.setSpecmode(pbd.getSpecmode());
				pbdf.setUnitid(pbd.getUnitid());
				pbdf.setUnitname(Internation.getStringByKeyPositionDB(
						"CountUnit", pbd.getUnitid()));
				pbdf.setRequiredate(pbd.getRequiredate());
				pbdf.setUnitprice(pbd.getUnitprice());
				pbdf.setQuantity(pbd.getQuantity());
				pbdf.setIncomequantity(pbd.getIncomequantity());
				pbdf.setSubsum(pbd.getSubsum());
				als.add(pbdf);
			}

//			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}

//			AppDept ad = new AppDept();
//			List aldept = ad.getDeptByOID(users.getMakeorganid());
			

//			List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
//			String isbatch = Constants.IS_BATCH;
//			String curdate = "";
//			if (!isbatch.equals("readonly")) {
//				curdate = DateUtil.getCurrentDateString().replace("-", "");
//			}

//			request.setAttribute("curdate", curdate);
//			request.setAttribute("isbatch", isbatch);
//			request.setAttribute("aldept", aldept);
//			request.setAttribute("auls", auls);
//			request.setAttribute("alw", alw);
			request.setAttribute("als", als);
			request.setAttribute("pbf", pbf);
			//DBUserLog.addUserLog(userid, "采购订单转采购入库");
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
