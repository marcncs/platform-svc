package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectOrganLinkmanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strcid = request.getParameter("cid");
		
		if (strcid == null ) {
			strcid = (String) request.getSession().getAttribute("olcid");
		}
		request.getSession().setAttribute("olcid", strcid);
		super.initdata(request);
		String str=request.getParameter("allSelect");
		String name=request.getParameter("KeyWord");
		
		if(str!=null&&str!=""&&str.equals("1")){
//			if(name)
//			String wheresql="where"
			String 	wheresql="";
			AppOlinkMan al = new AppOlinkMan();
			if(name!=""&&name!=null)
			 	wheresql="where name like '%"+name+"%' or mobile  like '"+name+"%' or officetel  like '%"+name+"%'";
			List pls = al.searchOlinkman(request, wheresql);
			request.setAttribute("sls", pls);
			return mapping.findForward("success");
		}else{
			try{
				String Condition="";
				if(strcid!=null){
					Condition = " cid='" + strcid + "' ";
					if(name!=""&&name!=null)
						Condition=Condition+" and name like '%"+name+"%' or mobile  like '"+name+"%' or officetel  like '%"+name+"%'";
				}else{
					if(name!=""&&name!=null)
						Condition=Condition+" name like '%"+name+"%' or mobile  like '"+name+"%' or officetel  like '%"+name+"%'";
				}
				
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "Olinkman" };
				String whereSql = EntityManager.getTmpWhereSql(map, tablename);

				String blur = DbUtil.getOrBlur(map, tmpMap, "Name","Mobile","OfficeTel","Addr");

				whereSql = whereSql + blur + Condition;
				whereSql = DbUtil.getWhereSql(whereSql); 

				AppOlinkMan al = new AppOlinkMan();
				List pls = al.searchOlinkman(request, whereSql);

				request.setAttribute("sls", pls);
				return mapping.findForward("success");
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		

		return new ActionForward(mapping.getInput());
	}
}
