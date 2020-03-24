package com.winsafe.drp.action.users;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveCanuseOrgan;
import com.winsafe.drp.dao.MoveCanuseOrgan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.RequestTool;

/**
 * 转仓授权设置
* @Title: SetMoveOrganDateAction.java
* @author: wenping 
* @CreateTime: Jan 7, 2013 9:34:03 AM
* @version:
 */
public class SetMoveOrganDateAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		String opids = request.getParameter("opids");
		
		Date beginDate = RequestTool.getDate(request, "begindate");
		Date endDate = RequestTool.getDate(request, "enddate");
		
		if(beginDate.after(endDate)){
			request.setAttribute("result", "结束日期不能大于开始日期");
			return new ActionForward("/users/lockrecordclose.jsp");
		}
		
		String[] arr = opids.split(",");
		for(int i=0;i<arr.length;i++){
			MoveCanuseOrgan mco = aop.getMoveCanuseOrganByID(Long.parseLong(arr[i]));
			mco.setBegindate(beginDate);
			mco.setEnddate(endDate);
			EntityManager.update2(mco);
		}
		request.setAttribute("result", "授权成功");
		return new ActionForward("/sys/operatorclose3.jsp");
	}
}
