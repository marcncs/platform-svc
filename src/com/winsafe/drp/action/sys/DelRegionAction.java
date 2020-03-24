package com.winsafe.drp.action.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionArea;
import com.winsafe.drp.util.DBUserLog;


public class DelRegionAction  extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String dcode = request.getParameter("acode");
		try{
			if ( dcode.equals("1") ){
				request.setAttribute("result", "不能删除顶级区域!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			AppRegion aa = new AppRegion();
			AppRegionArea appRegionArea = new AppRegionArea();
			aa.del(dcode);
			appRegionArea.delByRegionCodeId(dcode);
			DBUserLog.addUserLog(request, "编号:"+dcode);	
			request.setAttribute("result","databases.del.success");
			return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}

