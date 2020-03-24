package com.winsafe.erp.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;  

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.services.CartonSeqServices;


public class AjaxGetAvailableRange extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppCartonSeq appCs = new AppCartonSeq();
		CartonSeqServices css = new CartonSeqServices();
		String productId =request.getParameter("productId");
		//获取当前可用范围
		List<String> seqList = appCs.getUsableSeqForActivationByProductId(productId);
		String range =css. getUsableSeqRangeString(seqList);
		String partialActivatedSeq  =css. getNotFullyActivatedCartSeq(productId);
		
		JSONObject json = new JSONObject();			
		json.put("range", range);	
		json.put("seqs", partialActivatedSeq);
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
	    out.close();
		return null;
	}

}
