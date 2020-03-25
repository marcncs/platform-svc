package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DeleteSuggestInspectAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		try {
			
			AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
			AppSuggestInspect asi = new AppSuggestInspect();
			if(ids.indexOf("on") > 1){
				ids = ids.substring(0,ids.length()-3);
			}
			String whereSql = " where si.id in ("+ids+")";
			List<SuggestInspect> list = asi.getSiByIds(whereSql);
			for (SuggestInspect si : list) {
				if(si.getIsOut()==1){
					continue;
				}
				if("2000001".equals(si.getTypeId())){
					String sql=" where si.mergeId ="+si.getId();
					List<SuggestInspect> siList = asi.getSiByIds(sql);
					for (SuggestInspect suggestInspect : siList) {
						suggestInspect.setIsMerge(0);
						suggestInspect.setMergeId(null);
						asi.updateSi(suggestInspect);
					}
					//删除合并表
					asi.deleteSi(si);
					//删除合并表详情
					asid.deleteBySiid(si.getSiid());
				}else{
					asid.deleteSuggestInspectByids(si.getId().toString());
					asi.deleteSi(si);
				}
			}
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "删除拣货建议单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	

}
