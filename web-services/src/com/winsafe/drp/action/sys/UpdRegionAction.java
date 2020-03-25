package com.winsafe.drp.action.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;


public class UpdRegionAction  extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String acode = request.getParameter("acode");
		String sortname=request.getParameter("sortname");
		try{
			AppRegion aa = new AppRegion();
			
			Region ps = aa.getRegionById(acode);
			Region oldps = (Region)BeanUtils.cloneBean(ps);
			
			Region  re=  aa.getRegionBySortName(sortname.trim());
			
			if(re!=null &&!re.getSortname().equals(oldps.getSortname()) ){
				request.setAttribute("result",sortname+"已经存在不能重复添加!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			ps.setSortname(sortname.trim());
			EntityManager.update(ps);
			DBUserLog.addUserLog(request, "编号:"+acode, oldps, ps);
			request.setAttribute("result","databases.upd.success");
			return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return null;
	}

}

