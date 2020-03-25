package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.hbm.entity.HibernateUtil;

public class ToAssignAreasAction extends Action{
	  public ActionForward execute(ActionMapping mapping, ActionForm form,
              HttpServletRequest request,
              HttpServletResponse response) throws Exception {
		  String uid=request.getParameter("uid");
		  AppUsers au=new AppUsers();
		  try{
			  HibernateUtil.currentSession();
			  
			  List list=au.getAllAreas();
			  List ulist=au.getAllUserAreas(Integer.valueOf(uid));
			  
			  ArrayList clist=new ArrayList();
//			  for(int i=0;i<list.size();i++){
//				  Object ob[]=(Object[])list.get(i);
//				  CountryArea ca=new CountryArea();
//				  ca.setId(Integer.valueOf(ob[0].toString()));
//				  ca.setAreaname(ob[1].toString());
//				  ca.setParentid(Integer.valueOf(ob[2].toString()));
//				  ca.setRank(Integer.valueOf(ob[3].toString()));
//				  clist.add(ca);
//			  }
//			  
//			  ArrayList ualist=new ArrayList();
//			  for(int i=0;i<list.size();i++){
//				  Object ob[]=(Object[])list.get(i);
//				  UserArea ua=new UserArea();
//				  ua.setId(Integer.valueOf(ob[0].toString()));
//				  ua.setAreaid(Integer.valueOf(ob[2].toString()));
//				  ua.setUserid(Integer.valueOf(ob[1].toString()));
//				  ualist.add(ua);
//			  }
			  request.setAttribute("uid",uid);
			  request.setAttribute("list",list);
			  request.setAttribute("ulist",ulist);
			  
			  return mapping.findForward("toassign");
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  //HibernateUtil.closeSession();
		  }
		  
		  return mapping.findForward("toassign");
	  }
}
