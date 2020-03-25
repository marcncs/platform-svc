package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;

public class PayableDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);super.initdata(request);try{
			AppPayable apa = new AppPayable();
			Payable pa = new Payable();
//			AppUsers au = new AppUsers();

			pa = apa.getPayableByID(id);
			PayableForm paf = new PayableForm();
			paf.setId(id);
			paf.setPoid(pa.getPoid());
			paf.setPayablesum(pa.getPayablesum());
			paf.setBillno(pa.getBillno());
			paf.setPayabledescribe(pa.getPayabledescribe());
			paf.setMakeorganid(pa.getMakeorganid());
			paf.setMakedeptid(pa.getMakedeptid());
			paf.setMakeid(pa.getMakeid());
//			paf.setMakeidname(au.getUsersByid(Long.valueOf(pa.getMakeid()))
//					.getRealname());
			paf.setMakedate(pa.getMakedate().toString());
			paf.setPaymode(pa.getPaymode());
			paf.setAwakedate(pa.getAwakedate());
//			paf.setPaymodename(Internation.getStringByKeyPosition("PayMode",
//		            request,
//		            pa.getPaymode(), "global.sys.SystemResource"));
			paf.setAlreadysum(pa.getAlreadysum());
			paf.setIsclose(pa.getIsclose());
//			paf.setIsclosename(Internation.getStringByKeyPosition("YesOrNo",
//		            request,
//		            pa.getIsclose(), "global.sys.SystemResource"));

			request.setAttribute("paf", paf);
//			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"应付款详情"); 
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
