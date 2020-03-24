package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class RemoveSuggestInspectAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		try {
			
			AppSuggestInspect asi = new AppSuggestInspect();
			if(ids.indexOf("on") > 1){
				ids = ids.substring(0,ids.length()-3);
			}
			//asi.removeSuggestInspectByIds(ids);
			String whereSql=" where si.id in (" + ids+")";
			List<SuggestInspect> list =asi.getSiByIds(whereSql);
			for (SuggestInspect si : list) {
				if(si.getIsRemove().equals(1)){
					si.setIsRemove(0);
				}else{
					si.setIsRemove(1);
				}
				asi.updateSuggestInspect(si);
			}
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "排除拣货建议单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	

}
