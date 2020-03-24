package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairDetailForm;
import com.winsafe.drp.dao.SaleRepairForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class SaleRepairDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppSaleRepair aso = new AppSaleRepair();
			AppUsers au = new AppUsers();
			AppWarehouse aw = new AppWarehouse();
			SaleRepair so = aso.getSaleRepairByID(id);
			SaleRepairForm sof = new SaleRepairForm();

			sof.setId(id);
			sof.setCid(so.getCid());
			sof.setCname(so.getCname());
			sof.setClinkman(so.getClinkman());
			sof.setTel(so.getTel());
//			sof.setWarehouseinidname(aw.getWarehouseByID(so.getWarehouseinid())
//					.getWarehousename());
//			sof.setWarehouseoutidname(aw.getWarehouseByID(so.getWarehouseoutid())
//					.getWarehousename());
			sof.setTradesdate(DateUtil.formatDate(so.getTradesdate()));
			sof.setTotalsum(so.getTotalsum());
//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setMakedate(DateUtil.formatDate(so.getMakedate()));
			sof.setRemark(so.getRemark());
			sof.setIsaudit(so.getIsaudit());
			sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsaudit(), "global.sys.SystemResource"));

			if (so.getAuditid() !=null && so.getAuditid() > 0) {
//				sof.setAuditidname(au.getUsersByid(so.getAuditid())
//						.getRealname());
			} else {
				sof.setAuditidname("");
			}			
			sof.setAuditdate(DateUtil.formatDate(so.getAuditdate()));
			sof.setIsblankout(so.getIsblankout());
			sof.setIsblankoutname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsblankout(), "global.sys.SystemResource"));
			if (so.getBlankoutid() != null && so.getBlankoutid() != null) {
//				sof.setIsauditname(au.getUsersByid(so.getBlankoutid())
//						.getRealname());
			}
			sof.setBlankoutdate(DateUtil.formatDate(so.getBlankoutdate()));
			sof.setIsbacktrack(so.getIsbacktrack());
			sof.setIsbacktrackname(Internation.getStringByKeyPosition("YesOrNo",
					request, so.getIsbacktrack(), "global.sys.SystemResource"));
			if (so.getBacktrackid() != null && so.getBacktrackid() > 0) {
//				sof.setBacktrackidname(au.getUsersByid(so.getBacktrackid())
//						.getRealname());
			} else {
				sof.setBacktrackidname("");
			}	
			sof.setBacktrackdate(DateUtil.formatDate(so.getBacktrackdate()));

			AppSaleRepairDetail asld = new AppSaleRepairDetail();
			List sals = asld.getSaleRepairDetailBySrid(id);
			ArrayList als = new ArrayList();
			AppProduct ap = new AppProduct();

			SaleRepairDetail wd = null;
			SaleRepairDetailForm sldf = null;
			for (int i = 0; i < sals.size(); i++) {
				wd = (SaleRepairDetail) sals.get(i);
				sldf = new SaleRepairDetailForm();
				sldf.setId(wd.getId());
				sldf.setProductid(wd.getProductid());
				sldf.setProductname(wd.getProductname());
				sldf.setSpecmode(wd.getSpecmode());
				sldf.setBatch(wd.getBatch());
				// padf.setUnitid(Integer.valueOf(o[3].toString()));
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", wd.getUnitid()));
				sldf.setBatch(wd.getBatch());
				sldf.setQuantity(wd.getQuantity());				
				sldf.setUnitprice(wd.getUnitprice());
				sldf.setSubsum(wd.getSubsum());
				als.add(sldf);
			}

			request.setAttribute("als", als);
			request.setAttribute("sof", sof);
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "销售返修详细");
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
