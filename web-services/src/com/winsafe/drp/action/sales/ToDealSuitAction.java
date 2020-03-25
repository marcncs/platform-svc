package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.hbm.util.Internation;

public class ToDealSuitAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
	    initdata(request);
		try{
			String sid = request.getParameter("SID");
			AppSuit ast = new AppSuit(); 
			Suit s = ast.getSuitByID(sid);
			if(s.getIsdeal()==1){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", "databases.audit.success");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			String dealwayselect = Internation.getSelectTagByKeyAllDB(
					"DealWay", "dealway", false);

			request.setAttribute("sid", sid);
			request.setAttribute("dealwayselect", dealwayselect);
			return mapping.findForward("todeal");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
		      ////HibernateUtil.closeSession();
	    }
		return new ActionForward(mapping.getInput());
	}

}
