package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListDeptAction extends BaseAction {
	private AppDept ad=new AppDept();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 15; 
	super.initdata(request);
	String oid = request.getParameter("OID");
//	if (oid == null) {//System.out.println("=======1========>"+oid);
//		oid = (String) request.getSession().getAttribute("sjoid");
//	}
//	//System.out.println("=======2========>"+oid);
//	request.getSession().setAttribute("sjoid", oid);
	try {
		String condition = " d.oid='"+oid+"' ";
		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Dept"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getBlur(map, tmpMap, "DeptName"); 
        whereSql = whereSql + blur +condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 

		List menuls=ad.getDept(request, pagesize,whereSql);
		
		
		request.setAttribute("dpt",menuls);
		request.setAttribute("OID", oid);
	
		//DBUserLog.addUserLog(userid,"列表部门");
		return mapping.findForward("listdept");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
