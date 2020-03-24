package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.exception.CustomException;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.server.AddTakeTicketIdcodeiiService;
import com.winsafe.drp.util.JsonUtil;
import com.winsafe.hbm.entity.HibernateUtil;
/**
 * 批量插入条码
* @Title: MultiInsertIdcodeAction.java
* @author: wenping 
* @CreateTime: Jan 7, 2013 2:36:32 PM
* @version:
 */
public class MultiInsertIdcodeAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject json = new JSONObject();
		//条码新增service
		AddTakeTicketIdcodeiiService idcodeiiService = new AddTakeTicketIdcodeiiService();
		String idcodes = request.getParameter("result");
//		String idcodes = (String)request.getSession().getAttribute("resultVal");
		String billid = request.getParameter("billid");
		String productid = request.getParameter("productid");

		String[] idcodess = idcodes.split("\n");
		// System.out.println(idcodess.length);

		StringBuilder msgList = new StringBuilder();
		// System.out.println(msgList.length());
		// System.out.println(msgList==null);
		String result = "";
		for (int i = 0; i < idcodess.length; i++) {
			String idcode = idcodess[i];
			try {
				// request.setAttribute("idcode",);
				// request.setAttribute("msgList",msgList);
				// request.setAttribute("billid",billid);
				// request.setAttribute("productid",productid);
				// new AddTakeTicketIdcodeiiAction().execute(mapping, form,
				// request, response);

				idcodeiiService.dealIdcode(billid, idcode,productid, userid+"", users.getMakeorganid());

			} catch (IdcodeException e) {
				result = "条码不符合规则，请检查条码！";
				msgList.append(idcode + "[" + result + "]<br>");
			} catch (CustomException e) {
				result = e.getMessage();
				msgList.append(idcode + "[" + result + "]<br>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (msgList.length() == 0) {
//			request.setAttribute("result", "批量增加成功");
//			return new ActionForward("/sys/operatorclose3.jsp");
			json.put("result", 0);
			JsonUtil.setJsonObj(response, json);
		} else {
			HibernateUtil.rollbackTransaction();
//			request.setAttribute("result", msgList.toString());
//			return new ActionForward("/sys/lockrecordclose4.jsp");
			json.put("result", 1);
			JsonUtil.setJsonObj(response, json);
			request.getSession().setAttribute("result", msgList.toString());
		}
		
		return null;
	}
}
