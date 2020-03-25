package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.hbm.util.MakeCode;


/**
 * 呼叫中心获取computeid
 * @author jelli
 * 2009-3-24
 */
public class AjaxComputeIdAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {			
		
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"computeid", 0, ""));
			String computeid = MakeCode.getCurrentDateString()+MakeCode.getFormatNums(id, 6);
			JSONObject json = new JSONObject();			
			json.put("computeid", computeid);	
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
		    
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
