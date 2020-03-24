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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganProductForSelectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 50; 
	super.initdata(request);
	String oid = request.getParameter("OID");
	if ( oid == null ){
		oid=(String) request.getSession().getAttribute("sjoid");
	}
	request.getSession().setAttribute("sjoid", oid);
	OrganService os = new OrganService();
	try {

//		String parentid = os.getOrganByID(oid).getParentid();
//		String Condition = " op.productid=p.id and op.organid='"+parentid+"' and not exists (select o.id from OrganProduct as o "+
//					" where p.id=o.productid and o.organid='"+oid+"') "; 
//		if ( "0".equals(parentid) || "".equals(parentid) ){
//			Condition = " not exists (select o.id from OrganProduct as o where p.id=o.productid and o.organid='"+oid+"') ";
//		}
		String Condition = " not exists (select o.id from OrganProduct as o where p.id=o.productid and o.organid='"+oid+"') ";
		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Product"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "p.PSID");
        String blur = DbUtil.getOrBlur(map, tmpMap, "p.ID","p.ProductName","p.PYCode"); 
        whereSql = whereSql + leftblur + blur + Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 

       
		AppProduct ap=new AppProduct();
		List menuls = null;
		//if ( "0".equals(parentid) || "".equals(parentid) ){
			menuls =ap.getProductNotInOrganProduct(request, pagesize,whereSql);
//		}else{
//			menuls=ap.getOrganProductProduct(request, pagesize,whereSql);
//		}
		

		AppProductStruct appProductStruct = new AppProductStruct();
		List uls = appProductStruct.getProductStructCanUse();
		request.setAttribute("uls", uls);
		
		request.setAttribute("opls",menuls);
	
		DBUserLog.addUserLog(userid,11,"机构设置>>列表机构选择产品");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
