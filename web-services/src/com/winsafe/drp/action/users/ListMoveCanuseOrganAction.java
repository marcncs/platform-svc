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
import com.winsafe.drp.dao.AppMoveCanuseOrgan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListMoveCanuseOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 20; 
	super.initdata(request);
	String uid = request.getParameter("UIDI");
	if(uid ==null){
		uid=(String) request.getSession().getAttribute("uidi");	
	}
	request.getSession().setAttribute("uidi", uid);
	try {
		//String condition = " op.organid='"+uid+"' and p.id=op.productid ";
		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"MoveCanuseOrgan"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getOrBlur(map, tmpMap, "OName"); 
        whereSql = whereSql + blur;// + condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 

		AppMoveCanuseOrgan aop=new AppMoveCanuseOrgan();
		List menuls=aop.getMoveCanuseOrgan(request, pagesize,whereSql);

		request.setAttribute("opls",menuls);
		request.setAttribute("UIDI", uid);
		request.setAttribute("UID", uid);
		
		DBUserLog.addUserLog(userid, 11, "用户设置>>列表转仓机构");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
