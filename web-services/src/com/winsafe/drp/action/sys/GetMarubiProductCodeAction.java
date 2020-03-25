package com.winsafe.drp.action.sys;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppICode;

public class GetMarubiProductCodeAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String type = request.getParameter("type");
			if("1".equals(type)){
				String productid = request.getParameter("productid");
				String icode = new AppICode().getLCodeByPid(productid);
				if(icode.equals("")){
					icode = "0000";
				}
				response.setContentType("text/xml");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<strs>");
					out.print("<str0>");
						out.print(icode);
					out.print("</str0>");
				out.print("</strs>");
			}else{
				//暂无逻辑
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
