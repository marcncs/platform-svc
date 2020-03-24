package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryAreaForm;
import com.winsafe.hbm.util.Internation;

public class ToAddCountryAreaAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String rankselect =Internation.getSelectTagByKeyAll("Rank",request,"rank",false,null );
			AppCountryArea aca = new AppCountryArea();
			List cals = aca.getAllCountryArea();
			ArrayList als=new ArrayList();
			
			for(int i=0;i<cals.size();i++){
				CountryAreaForm caf = new CountryAreaForm();
				Object[] o = (Object[]) cals.get(i);
				caf.setId(Long.valueOf(o[0].toString()));
				caf.setAreaname(o[1].toString());
				caf.setRank(Integer.valueOf(o[3].toString()));
				als.add(caf);
			}
			
			request.setAttribute("als",als);
			request.setAttribute("rankselect",rankselect);
			return mapping.findForward("toadd");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}

}
