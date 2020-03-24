package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdAssembleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataValidate dv = new DataValidate();
		MakeCode mc = new MakeCode();
//		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		try {
			String id = request.getParameter("id");
			AppAssemble asl = new AppAssemble();
			Assemble dp = asl.getAssembleByID(id);
			Assemble olddp = (Assemble)BeanUtils.cloneBean(dp);

			String aproductid = request.getParameter("aproductid");
			if (aproductid == null || aproductid.equals("null")
					|| aproductid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String aproductname = request.getParameter("aproductname");
			String abatch = request.getParameter("abatch");
			String straquantity = request.getParameter("aquantity");
			String warehouseinid = request.getParameter("warehouseinid");
			String adept = request.getParameter("adept");
			String completeintenddate = request
					.getParameter("completeintenddate");
			String remark = request.getParameter("remark");

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strbatch[] = request.getParameterValues("batch");
			String strunitid[] = request.getParameterValues("unitid");
			String strwarehouseoutid[] = request
					.getParameterValues("warehouseoutid");
			String strquantity[] = request.getParameterValues("quantity");

			String productid, productname, specmode, batch;
			Integer unitid;
			Long warehouseoutid;
			Double aquantity, quantity;

			if (dv.IsInteger(straquantity)) {
				aquantity = Double.valueOf(straquantity);
			} else {
				aquantity = Double.valueOf(0);
			}

			dp.setAproductid(aproductid);
			dp.setAproductname(aproductname);
//			dp.setBatch(abatch);
//			dp.setAquantity(aquantity);
//			dp.setWarehouseinid(Long.valueOf(warehouseinid));
//			dp.setAdept(Long.valueOf(adept));
			dp.setCompleteintenddate(DateUtil.StringToDate(completeintenddate));
			dp.setRemark(remark);

			AppAssembleDetail asld = new AppAssembleDetail();
			asld.delAssembleDetailByAid(id);
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				batch = "";
				warehouseoutid = Long.valueOf(strwarehouseoutid[i]);
				unitid = Integer.valueOf(strunitid[i]);
				if (dv.IsInteger(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0);
				}

				AssembleDetail sod = new AssembleDetail();
//				sod.setId(Lonsle_detail", 0, "")));
				sod.setAid(dp.getId());
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
//				sod.setBatch(batch);
				sod.setUnitid(unitid);
//				sod.setWarehouseoutid(warehouseoutid);
				sod.setQuantity(quantity);

				asld.addAssembleDetail(sod);
			}
			asl.updAssemble(dp);

			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 3, "组装>>修改组装单,编号:"+id, olddp, dp);
			
			return mapping.findForward("updresult");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
		}

		return new ActionForward(mapping.getInput());
	}
}
