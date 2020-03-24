package com.winsafe.drp.action.users;

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
			String oidArray  = request.getParameter("oid");
			String uid = request.getParameter("uid");
			String array = "";
			//删除管辖仓库
			if(!StringUtil.isEmpty(oidArray)){
				array = oidArray.replaceAll(",", "','");
				array = "'" + array + "'";
			}
			
			appRuleUserWH.delRuleWhByWid(array, uid, true);
			//删除管辖机构
			app.delUserVisitByUserid(uid);
			
			request.setAttribute("result", "databases.del.success");
			
			DBUserLog.addUserLog(userid, "系统管理", "用户管理>>删除管辖权限,机构编号:"+oidArray);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
