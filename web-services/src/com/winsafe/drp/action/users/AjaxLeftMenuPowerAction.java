package com.winsafe.drp.action.users;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.LeftMenuBean;


public class AjaxLeftMenuPowerAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer leftmenuid = Integer.valueOf(request.getParameter("id"));
		Integer roleid = Integer.valueOf(request.getParameter("roleid"));
		try {
			
			AppLeftMenu applm = new AppLeftMenu();
			
			List secondlist = applm.getLeftMenuByParentid(leftmenuid, 2, roleid);
			JSONArray  menulist = new JSONArray();
			for (int i=0; i<secondlist.size(); i++ ){
				LeftMenuBean lm = (LeftMenuBean)secondlist.get(i);
				JSONObject json = new JSONObject();		
				
				
				List thirdlist = applm.getLeftMenuByParentid(lm.getId(), 3, roleid);				
				JSONArray  tarray = new JSONArray();
				if ( thirdlist.size() > 0 ){
					lm.setHassonmenu(1);
					for (int j=0; j<thirdlist.size(); j ++){
						LeftMenuBean tlm = (LeftMenuBean)thirdlist.get(j);						
						
						
						JSONArray  oarray = applm.getOperateByLmid(tlm.getId(), roleid);						
						JSONObject thirdmenu = new JSONObject();		
						thirdmenu.put("thirdmenu", tlm);
						thirdmenu.put("operatelist", oarray);
						tarray.put(thirdmenu);
					}
				}else{
					
					JSONArray  oarray = applm.getOperateByLmid(lm.getId(), roleid);								
					json.put("operatelist", oarray);
					
				}
				json.put("secondmenu", lm);
				json.put("sunmenu", tarray);
				menulist.put(json);
			}

			
			JSONObject json = new JSONObject();			
			json.put("menulist", menulist);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

		    
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
