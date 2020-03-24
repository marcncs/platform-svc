package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustCIntegral;
import com.winsafe.drp.dao.AdjustCIntegralDetail;
import com.winsafe.drp.dao.AdjustCIntegralDetailForm;
import com.winsafe.drp.dao.AppAdjustCIntegral;
import com.winsafe.drp.dao.AppAdjustCIntegralDetail;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToUpdAdjustCIntegralAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		String id = request.getParameter("id");
		try {
			AppAdjustCIntegral appr = new AppAdjustCIntegral();
			AdjustCIntegral r = appr.getAdjustCIntegralByID(id);
			
			if (r.getIsaudit() == 1 ) {
				String result = "databases.record.isapprovenooperator";
		        request.setAttribute("result",result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppAdjustCIntegralDetail arf = new AppAdjustCIntegralDetail();
			AppCIntegral aci = new AppCIntegral();
			//cu.setIntegral(aci.getCIntegralByCID(c.getCid()));
			List list = arf.getDetailByAccid(id);
			List rflist = new ArrayList();
			for ( int i=0; i<list.size(); i++ ){
				AdjustCIntegralDetail a = (AdjustCIntegralDetail)list.get(i);
				AdjustCIntegralDetailForm af = new AdjustCIntegralDetailForm();
				af.setId(a.getId());
				af.setCid(a.getCid());
				af.setCname(a.getCname());
				af.setCmobile(a.getCmobile());
				af.setAdjustintegral(a.getAdjustintegral());
				af.setIntegral(aci.getCIntegralByCID(a.getCid()));
				af.setOid(a.getOid());
				rflist.add(af);
			}
			
			
			 AppOrgan ao = new AppOrgan();
			 List ols = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("ols", ols);
			request.setAttribute("r", r);
			request.setAttribute("rflist", rflist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("toupd");
	}

}
