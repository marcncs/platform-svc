package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.util.DBUserLog;

/**
 * 主要功能:删除用户仓库权限
 *
 */
public class DelRuleUserWHAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//dao层相关的类
		AppUserVisit app = new AppUserVisit();
		AppRuleUserWH appRuleUserWH = new AppRuleUserWH();
		//初始化
		initdata(request);
		try {
			//修改可访问机构批量删除
			String uidArray  = request.getParameter("uids");
			String wid = request.getParameter("WID");
			String array = "";
			//删除管辖仓库
			if(!StringUtil.isEmpty(uidArray)){
//				array = uidArray.replaceAll(",", "','");
//				array = "'" + array + "'";
				appRuleUserWH.delRuleWhByUid(uidArray, wid, true);
				//删除管辖机构
				app.delUserVisitByUserids(uidArray);
			}
			
			request.setAttribute("result", "databases.del.success");
			
			DBUserLog.addUserLog(userid, "系统管理", "机构设置>>列表管辖用户>>删除用户:"+array);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
