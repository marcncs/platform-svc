package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.util.DBUserLog;

/**
 * 主要功能:删除业务往来权限
 * @author RichieYu
 *
 */
public class DelOrganVisitAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//dao层相关的类
		AppOrganVisit app = new AppOrganVisit();
		AppWarehouseVisit appWV = new AppWarehouseVisit();
		//初始化
		initdata(request);
		try {
			//修改可访问机构批量删除
			String oidArray = request.getParameter("oid");
			String uid = request.getParameter("uid");
			String array = "";
			//删除管辖仓库
			if(!StringUtil.isEmpty(oidArray)){
				array = oidArray.replaceAll(",", "','");
				array = "'" + array + "'";
			}
			//删除业务往来仓库
			appWV.delByOid(array, uid, true);
			//删除业务往来机构
			String whereStr = " where ov.userid=" + uid + " and ov.visitorgan in  (" + array + ")" ;
			app.delOrganVisit(whereStr);
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, "系统管理", "用户管理>>删除业务往来权限,机构编号:"+oidArray);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
