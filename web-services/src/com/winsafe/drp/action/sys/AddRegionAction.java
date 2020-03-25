package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;


public class AddRegionAction  extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String strparentid = request.getParameter("parentid");
		String sortname=request.getParameter("sortname");
		String typeid=request.getParameter("typeid");
		String typename=null;
		
		try{
			AppRegion aa = new AppRegion();
			//获取父区域的name
		    Region parent= aa.getRegionById(strparentid);
		    //获取子区域的code
			String acode=aa.getAcodeByParent(strparentid);
			
			Region  re=  aa.getRegionBySortName(sortname.trim());
			if(re!=null){
				request.setAttribute("result",sortname+"已经存在不能重复添加!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			Region as= new Region();
			as.setRegioncode(acode);
			as.setSortname(sortname.trim());
			as.setParentid(strparentid);
			as.setParentname(parent.getSortname());
			as.setTypeid(typeid);
			if("1".equals(typeid)){
				typename="大区";
			}
			if("2".equals(typeid)){
				typename="办事处";
			}
			as.setTypename(typename);
			EntityManager.save(as);    
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(request, "编号:"+acode);
			request.setAttribute("result","databases.add.success");
			return mapping.findForward("success");

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}

