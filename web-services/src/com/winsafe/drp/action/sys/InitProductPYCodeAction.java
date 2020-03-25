package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.hbm.util.PYCode;

public class InitProductPYCodeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		try{
			AppProduct ap = new AppProduct();
			List ls = ap.getProduct(null);
			
			String pid="",pname="",pycode="";
			for(int i=0;i<ls.size();i++){
				Object[] o = (Object[])ls.get(i);
				pid = o[0].toString();
				pname = o[1].toString();
				pycode = PYCode.getSampCode(pname);
				ap.updProductPYCode(pid, pycode);
			}
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("add");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}

}
