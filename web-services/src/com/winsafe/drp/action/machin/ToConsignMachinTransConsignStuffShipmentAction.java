package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachinDetailForm;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.HtmlSelect;

public class ToConsignMachinTransConsignStuffShipmentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			String aid = request.getParameter("id");
			AppConsignMachin aa = new AppConsignMachin();
			ConsignMachin cm = aa.getConsignMachinByID(aid);
			if (cm.getIsaudit() == 0) {
				String result = "databases.record.noauditnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (cm.getIsendcase() == 1) {
				String result = "databases.record.iscomplete";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			ConsignMachinForm cmf = new ConsignMachinForm();
//			AppProvide ap = new AppProvide();
			cmf.setId(cm.getId());
			cmf.setPid(cm.getPid());
//			cmf.setPidname(ap.getProvideByID(cm.getPid()).getPname());
			cmf.setPlinkman(cm.getPlinkman());
			cmf.setTel(cm.getTel());
			
			AppConsignMachinDetail appad = new AppConsignMachinDetail();
			AppProductStockpile aps = new AppProductStockpile();
			List adlist = appad.getConsignMachinDetailBySIIDNotAlready(aid);

			List vls = new ArrayList();
			for (int i = 0; i < adlist.size(); i++) {
				ConsignMachinDetailForm sldf = new ConsignMachinDetailForm();
				ConsignMachinDetail ard = (ConsignMachinDetail) adlist.get(i);
				sldf.setProductid(ard.getProductid());
//				sldf.setProductcode(ard.getProductcode());
				sldf.setProductname(ard.getProductname());
				sldf.setSpecmode(ard.getSpecmode());
				sldf.setUnitid(ard.getUnitid());
				sldf.setUnitidname(HtmlSelect.getResourceName(request, 
						"CountUnit", ard.getUnitid()));
				//sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setStockpile(aps.getSumByProductID(ard.getProductid()));
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				//sldf.setSubsum(ard.getSubsum());
				sldf.setId(ard.getId());
				vls.add(sldf);
			}
			
			
			WarehouseService aw = new WarehouseService();
//			List wls = aw.getEnableWarehouseByVisit(userid, getLan(request));
			
//			AppDept ad = new AppDept();
//			List dls = ad.getDept();
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				Dept d = new Dept();
//				Object[] ob = (Object[]) dls.get(i);
//				d.setId(Long.valueOf(ob[0].toString()));
//				d.setDeptname(ob[1].toString());
//				aldept.add(d);
//			}

//			String shipmentsortselect = Internation.getSelectTagByKeyAllDB(
//					"ConsignShipmentSort","consignshipmentsort",request,false);

//			request.setAttribute("shipmentsortselect", shipmentsortselect);
//			request.setAttribute("vls", vls);
//			request.setAttribute("alw", wls);
			request.setAttribute("cmf", cmf);
//			request.setAttribute("aldept", aldept);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
