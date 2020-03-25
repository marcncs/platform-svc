package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganRole;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.OrganRole;
import com.winsafe.drp.dao.OrganRoleForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganRoleForOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	int pagesize = 20; 
	super.initdata(request);
	String organid = request.getParameter("OrganID");
	if(organid ==null){
		organid=(String) request.getSession().getAttribute("organid");	
	}
	request.getSession().setAttribute("organid", organid);
	try {

		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"OrganRole"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

        String blur = DbUtil.getOrBlur(map, tmpMap, "RName"); 
        whereSql = whereSql + blur;
        whereSql = DbUtil.getWhereSql(whereSql); 

        AppOrganRole aor = new AppOrganRole(); 

		List menuls=aor.searchOrganRole(request, pagesize,whereSql);
		
		ArrayList als = new ArrayList();
		AppRole ar = new AppRole();
		
		for(int i=0;i<menuls.size();i++){
			OrganRoleForm  orf = new OrganRoleForm();
			OrganRole o=(OrganRole)menuls.get(i);
			orf.setId(o.getId());
			orf.setOname(o.getOname());
			orf.setOrganid(o.getOrganid());
			orf.setRname(o.getRname());
			orf.setRoleid(o.getRoleid());
			orf.setRoledesc(ar.getRoleById(o.getRoleid())==null?"":ar.getRoleById(o.getRoleid()).getDescribes());
			als.add(orf);
		}

		request.setAttribute("opls",als);
		request.setAttribute("OrganID", organid);
		
		DBUserLog.addUserLog(userid, 11, "机构管理>>列表机构拥有角色");
		return mapping.findForward("list");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
