package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.util.DBUserLog;

public class DelPayableObjectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			
			 String id = request.getParameter("ID");
		    AppPayableObject apo = new AppPayableObject();
		    AppPayable ap = new AppPayable();
			
//		    PayableObject po = apo.getPayableObjectByID(id);
		      
//		      if (po.getTotalpayablesum() > 0||po.getAlreadypayablesum()>0) { 
//		          String result = "databases.record.lock";
//		          request.setAttribute("result", result);
//		          return new ActionForward("/sys/lockrecordclose.jsp");
//		        }

			apo.delPayableObject(id);
			ap.delPayableByPOID(id);

		      request.setAttribute("result", "databases.del.success");


		      DBUserLog.addUserLog(userid,9, "付款管理>>删除应付款对象,编号:"+id); 
		      
			return mapping.findForward("del");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
