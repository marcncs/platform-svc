package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.Dept;
import com.winsafe.drp.dao.DeptForm;
import com.winsafe.drp.dao.UserVisit;

public class SelectDeptToVisitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		Integer vu = (Integer) request.getSession().getAttribute("visituser");

	    try{
	      AppDept ad = new AppDept();
	      List dls = ad.getAllDept();
	      
	      ArrayList auls = new ArrayList();
	      for(int i=0;i<dls.size();i++){
	        DeptForm df = new DeptForm();
	        Dept o = (Dept)dls.get(i);
	        df.setId(o.getId());
	        df.setDeptname(o.getDeptname());

	        auls.add(df);
	      }

	      AppUserVisit atpe = new AppUserVisit();
	      UserVisit alrd = atpe.getUserVisitByUserID(vu);
	      
	      ArrayList alls = new ArrayList();
	      if(alrd!=null){
	      
	      String struvd = alrd.getVisitdept();
	      StringTokenizer stvd = new StringTokenizer(struvd, ",");

	      while (stvd.hasMoreTokens()){
	        DeptForm alub = new DeptForm();
	        String ntuid = stvd.nextToken();
	        
	        alub.setId(Integer.valueOf(ntuid));
	        alub.setDeptname(ad.getDeptByID(Integer.valueOf(ntuid)).getDeptname());
	        alls.add(alub);
	      }
	      
	      }

	      request.setAttribute("alls",alls);
	      request.setAttribute("auls",auls);

	      return mapping.findForward("toselect");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    return new ActionForward(mapping.getInput());
	}
}
