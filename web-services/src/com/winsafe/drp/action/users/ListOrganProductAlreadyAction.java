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
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganProductAlreadyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 50; 
	super.initdata(request);
	String oid = request.getParameter("OID");
	if(oid ==null){
		oid=(String) request.getSession().getAttribute("sjoid");	
	}
	request.getSession().setAttribute("sjoid", oid);
	try {
		String condition = " op.organid='"+oid+"' and p.id=op.productid ";
		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"OrganProduct","Product"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
        String blur = DbUtil.getOrBlur2(map, tmpMap, "p.id","p.productname","p.pycode","p.mCode"); 
        whereSql = whereSql +leftblur + blur + condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
        
		AppOrganProduct aop=new AppOrganProduct();
		List menuls=aop.getOrganProductAlready(request, pagesize,whereSql);

		
		request.setAttribute("opls",menuls);
		 request.setAttribute("OID", oid);
		
		DBUserLog.addUserLog(userid, 11, "机构设置>>列表已经营商品");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
