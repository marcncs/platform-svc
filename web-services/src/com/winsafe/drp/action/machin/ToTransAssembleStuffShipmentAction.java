package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToTransAssembleStuffShipmentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String aid = request.getParameter("id");
			AppAssemble aa = new AppAssemble();
			Assemble a = aa.getAssembleByID(aid);
			if (a.getIsaudit() == 0) {
				String result = "databases.record.noauditnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (a.getIsendcase() == 1) {
				String result = "databases.record.iscomplete";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppAssembleDetail appad = new AppAssembleDetail();
			AppProductStockpile aps = new AppProductStockpile();
//			List adlist = appad.getAssembleDetailBySIIDNotAlready(aid);

			List vls = new ArrayList();
//			for (int i = 0; i < adlist.size(); i++) {
//				AssembleDetailForm sldf = new AssembleDetailForm();
//				AssembleDetail ard = (AssembleDetail) adlist.get(i);
//				sldf.setProductid(ard.getProductid());
//				sldf.setProductcode(ard.getProductcode());
//				sldf.setProductname(ard.getProductname());
//				sldf.setSpecmode(ard.getSpecmode());
//				sldf.setUnitid(ard.getUnitid());
//				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
//						"CountUnit", ard.getUnitid(),request));
//				//sldf.setUnitprice(ard.getUnitprice());
//				sldf.setQuantity(ard.getQuantity());
//				sldf.setStockpile(aps.getSumByProductID(ard.getProductid()));
//				sldf.setAlreadyquantity(ard.getAlreadyquantity());
//				//sldf.setSubsum(ard.getSubsum());
//				sldf.setId(ard.getId());
//				vls.add(sldf);
//			}
//			
			
//			WarehouseService aw = new WarehouseService();
//			List wls = aw.getEnableWarehouseByVisit(userid, getLan(request));
//			
//			DeptService ad = new DeptService();
//			List dls = ad.getAllDept(getLan(request));
//			
//
//			String shipmentsortselect = Internation.getSelectTagByKeyAllDB(
//					"StuffShipmentSort","shipmentsort",request,false);
//
//			request.setAttribute("shipmentsortselect", shipmentsortselect);
//			request.setAttribute("vls", vls);
//			request.setAttribute("alw", wls);
//			request.setAttribute("aldept", dls);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
