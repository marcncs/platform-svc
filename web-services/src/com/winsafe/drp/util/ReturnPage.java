package com.winsafe.drp.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

public class ReturnPage {
	/**
	 * 获取返回ActionForward
	 * @param request
	 * @param returnCode 0为正常
	 * @param resultMsg 需要显示的消息内容
	 * @return
	 */
	public static ActionForward getReturn(HttpServletRequest request,
			String returnCode, String resultMsg) {
		return getReturn(request,returnCode,resultMsg,true);
	}
	
	/**
	 * 获取返回ActionForward
	 * @param request
	 * @param returnCode 0为正常
	 * @param resultMsg 需要显示的消息内容
	 * @return
	 */
	public static ActionForward getReturn(HttpServletRequest request,
			String returnCode, String resultMsg,boolean isRefrash) {
		request.setAttribute("isRefrash", isRefrash);
		request.setAttribute("returnCode", returnCode);
		request.setAttribute("resultMsg", resultMsg);
		return new ActionForward("/sys/operatorresult.jsp");
	}
}
