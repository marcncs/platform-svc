package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddCountryAreaAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		MakeCode mc = new MakeCode();
		String areaname=request.getParameter("areaname");
		String strparentid = request.getParameter("parentid");
		Integer parentid=0;
		if(strparentid==null||strparentid.equals("")){
			parentid = 0;
		}else{
			parentid = Integer.valueOf(strparentid);
		}
		String strrank = request.getParameter("rank");
		Integer rank = Integer.valueOf(strrank)+1;

	    try{
	    	CountryArea ca= new CountryArea();
	    	ca.setId(Integer.valueOf(mc.getExcIDByRandomTableName("country_area",0,"")));
	    	ca.setAreaname(areaname);
	    	ca.setParentid(parentid);
	    	ca.setRank(rank);
	    	
	    	AppCountryArea caa=new AppCountryArea();
	    	caa.addCountryArea(ca);
	    	
	        request.setAttribute("result", "databases.add.success");
	        UsersBean users = UserManager.getUser(request);
	        Integer userid = users.getUserid();
	        DBUserLog.addUserLog(userid,"新增地区");
	        
	        return mapping.findForward("addresult");
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }finally {
	        //
	        //  ConnectionEntityManager.close(conn);
	      }
	    
		return new ActionForward(mapping.getInput());
	}

}
