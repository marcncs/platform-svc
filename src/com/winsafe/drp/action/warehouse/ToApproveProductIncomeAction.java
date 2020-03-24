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
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetailForm;
import com.winsafe.drp.dao.ProductIncomeForm;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ToApproveProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		String actid = request.getParameter("actid");

		super.initdata(request);try{

			if (DbUtil.judgeApproveStatusToApprover("ProductIncome", id)) {
				String result = "databases.record.isapprove";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			if (DbUtil.judgeApproveStatusToApproverVeto("ProductIncome", id)) {
				String result = "databases.record.isveto";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			AppProductIncome api = new AppProductIncome();
			ProductIncome pi = new ProductIncome();
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			AppWarehouse aw = new AppWarehouse();
			pi = api.getProductIncomeByID(id);
			ProductIncomeForm pif = new ProductIncomeForm();

			pif.setId(id);
			pif.setHandwordcode(pi.getHandwordcode());
			pif.setWarehouseidname(aw.getWarehouseByID(pi.getWarehouseid())
					.getWarehousename());
//			pif.setProducebatch(pi.getProducebatch());
//			pif.setIncomedeptname(ad.getDeptByID(pi.getIncomedept())
//					.getDeptname());
			pif.setIncomedate(pi.getIncomedate().toString());
			pif.setRemark(pi.getRemark());
//			pif.setIsrefername(Internation.getStringByKeyPosition("IsRefer",
//					request, pi.getIsrefer(), "global.sys.SystemResource"));
//			pif.setApprovestatusname(Internation.getStringByKeyPosition(
//					"ApproveStatus", request, pi.getApprovestatus(),
//					"global.sys.SystemResource"));
//			pif.setApprovedate(pi.getApprovedate());
			pif.setIsaudit(pi.getIsaudit());
			pif.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, pi.getIsaudit(), "global.sys.SystemResource"));
			if (pi.getAuditid() > 0) {
				pif.setAuditidname(au.getUsersByid(pi.getAuditid())
						.getRealname());
			} else {
				pif.setAuditidname("");
			}
			pif.setAuditdate(pi.getAuditdate());
			pif.setMakeidname(au.getUsersByid(pi.getMakeid()).getRealname());
			pif.setMakedate(pi.getMakedate());

			AppProductIncomeDetail aspb = new AppProductIncomeDetail();
			List spils = aspb.getProductIncomeDetailByPbId(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < spils.size(); i++) {
				ProductIncomeDetailForm pidf = new ProductIncomeDetailForm();
				Object[] o = (Object[]) spils.get(i);
				pidf.setProductid(o[2].toString());
				pidf.setProductname(o[3].toString());
				pidf.setSpecmode(o[4].toString());
				pidf.setUnitid(Integer.valueOf(o[5].toString()));
				pidf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",
						Integer.parseInt(o[5].toString())));
				pidf.setQuantity(Double.valueOf(o[6].toString()));
				als.add(pidf);
			}

			String approvestatus = Internation.getSelectTagByKeyAll(
					"SubApproveStatus", request, "ApproveStatus", "1", null);
			String stractid=Internation.getStringByKeyPositionDB("ActID",
		              Integer.valueOf(actid));

			request.setAttribute("approvestatus", approvestatus);
			request.setAttribute("actid", actid);
		    request.setAttribute("stractid", stractid);
			request.setAttribute("piid", id);
			request.setAttribute("als", als);
			request.setAttribute("pif", pif);

			return mapping.findForward("toapprove");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
