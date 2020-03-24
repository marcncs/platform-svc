package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustOIntegral;
import com.winsafe.drp.dao.AdjustOIntegralDetail;
import com.winsafe.drp.dao.AdjustOIntegralDetailForm;
import com.winsafe.drp.dao.AppAdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegralDetail;
import com.winsafe.drp.dao.AppOIntegral;

public class ToUpdAdjustOIntegralAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		try {
			AppAdjustOIntegral appr = new AppAdjustOIntegral();
			AdjustOIntegral r = appr.getAdjustOIntegralByID(id);
			
			if (r.getIsaudit() == 1 ) {
				String result = "databases.record.isapprovenooperator";
		        request.setAttribute("result",result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppAdjustOIntegralDetail arf = new AppAdjustOIntegralDetail();
			AppOIntegral aoi = new AppOIntegral();
			List list = arf.getDetailByAocid(id);
			List rflist = new ArrayList();
			for ( int i=0; i<list.size(); i++ ){
				AdjustOIntegralDetail a = (AdjustOIntegralDetail)list.get(i);
				AdjustOIntegralDetailForm af = new AdjustOIntegralDetailForm();
				af.setId(a.getId());
				af.setOid(a.getOid());
				af.setOname(a.getOname());
				af.setAdjustintegral(a.getAdjustintegral());
				af.setIntegral(aoi.getOIntegralByOID(af.getOid()));
				rflist.add(af);
			}
			
			

			request.setAttribute("r", r);
			request.setAttribute("rflist", rflist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("toupd");
	}

}
