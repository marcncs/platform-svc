package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppLoanOutDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.LoanOut;
import com.winsafe.drp.dao.LoanOutDetail;
import com.winsafe.drp.dao.LoanOutDetailForm;
import com.winsafe.drp.dao.LoanOutForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class LoanOutProductListAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		try {

			AppLoanOut aso = new AppLoanOut();
			AppUsers au = new AppUsers();
			AppWarehouse aw = new AppWarehouse();
			LoanOut so = aso.getLoanOutByID(id);

			LoanOutForm sof = new LoanOutForm();
			sof.setId(so.getId());
			sof.setUid(so.getUid());
			sof.setUname(so.getUname());
			sof.setReceiveman(so.getReceiveman());
			sof.setTel(so.getTel());
			sof.setSaledept(so.getSaledept());
			sof.setSaleid(so.getSaleid());
			sof
					.setConsignmentdate(DateUtil.formatDate(so
							.getConsignmentdate()));
			sof.setTransportmodename(Internation.getSelectTagByKeyAllDBDef(
					"TransportMode", "transportmode", so.getTransportmode()));
			sof.setTransitname(Internation.getSelectTagByKeyAllDBDef("Transit",
					"transit", so.getTransit()));
			sof.setTransportaddr(so.getTransportaddr());
			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());

			AppLoanOutDetail asld = new AppLoanOutDetail();
			List slls = asld.getLoanOutDetailByLoid(id);
			ArrayList als = new ArrayList();
			LoanOutDetail sod = null;

			for (int i = 0; i < slls.size(); i++) {
				sod = (LoanOutDetail) slls.get(i);
				LoanOutDetailForm sodf = new LoanOutDetailForm();
				sodf.setProductid(sod.getProductid());
				sodf.setProductname(sod.getProductname());
				sodf.setSpecmode(sod.getSpecmode());
				sodf.setWarehouseid(sod.getWarehouseid());
//				sodf.setWarehouseidname(aw.getWarehouseByID(
//						sod.getWarehouseid()).getWarehousename());
				sodf.setUnitid(sod.getUnitid());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(sod.getUnitid()
								.toString())));
				sodf.setUnitprice(sod.getUnitprice());
				sodf.setBackquantity(sod.getBackquantity());
				sodf.setQuantity(sod.getQuantity());
				sodf.setBatch(sod.getBatch());
				sodf.setCost(sod.getCost());
				sodf.setSubsum(sod.getSubsum());
				als.add(sodf);
			}

			AppDept ad = new AppDept();
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

			List uls = au.getIDAndLoginName();
			ArrayList userlist = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				userlist.add(ubs);
			}

			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
//			request.setAttribute("aldept", aldept);
			request.setAttribute("userlist", userlist);
			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
