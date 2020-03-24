package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.dao.AssembleDetailForm;
import com.winsafe.drp.dao.AssembleForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdAssembleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		super.initdata(request);try{
			AppAssemble api = new AppAssemble();
			Assemble pi = new Assemble();
			AppWarehouse aw = new AppWarehouse();
			AppDept ad = new AppDept();

			pi = api.getAssembleByID(id);
			if (pi.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AssembleForm pif = new AssembleForm();
			pif.setId(id);
			pif.setAproductid(pi.getAproductid());
			pif.setAproductname(pi.getAproductname());
//			pif.setBatch(pi.getBatch());
			pif.setAquantity(pi.getAquantity());
//			pif.setWarehouseinid(pi.getWarehouseinid());
			pif.setAdept(pi.getAdept());
//			pif.setCompleteintenddate(DateUtil.formatDate(pi
//					.getCompleteintenddate()));
			pif.setRemark(pi.getRemark());

			
			AppAssembleDetail aspb = new AppAssembleDetail();
			List spils = aspb.getAssembleDetailByAid(id);
			ArrayList als = new ArrayList();

			AssembleDetail pid = null;
			for (int i = 0; i < spils.size(); i++) {
				pid = (AssembleDetail) spils.get(i);
				AssembleDetailForm pidf = new AssembleDetailForm();
				pidf.setId(pid.getId());
				pidf.setProductid(pid.getProductid());
				pidf.setProductname(pid.getProductname());
				pidf.setSpecmode(pid.getSpecmode());
//				pidf.setBatch(pid.getBatch());
				pidf.setUnitid(pid.getUnitid());
				pidf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", pid.getUnitid()));
//				pidf.setWarehouseoutid(pid.getWarehouseoutid());
//				pidf.setWarehouseoutidname(aw.getWarehouseByID(
//						pid.getWarehouseoutid()).getWarehousename());
				pidf.setQuantity(pid.getQuantity());
				als.add(pidf);
			}

//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
//
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

//			request.setAttribute("alw", alw);
//			request.setAttribute("aldept", aldept);
			request.setAttribute("als", als);
			request.setAttribute("pif", pif);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
