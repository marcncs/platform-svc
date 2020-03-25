package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;

public class ReplaceTTidcodeAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		//上传上来的TT编号
		Map<String, List<TakeTicketIdcode>> notExistTT = (HashMap<String, List<TakeTicketIdcode>>)request.getSession().getAttribute("notExistTTList");
		String checkRightid = request.getParameter("checkRightid");
		//上传上来的所有TT条码
		List<TakeTicketIdcode> notExistTTidcodes = notExistTT.get(checkRightid);
		//系统中现有的TT对象
		String checkLeftid = request.getParameter("checkLeftid");
		
		AppTakeTicketIdcode appTakeTicketIdcode = new AppTakeTicketIdcode();
		for (TakeTicketIdcode takeTicketIdcode : notExistTTidcodes) {
		    
		    //takeTicketIdcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		    takeTicketIdcode.setTtid(checkLeftid);
		    //更新条码
		    HibernateUtil.currentTransaction();
		    appTakeTicketIdcode.updTakeTicketIdcode(takeTicketIdcode);
		    HibernateUtil.commitTransaction();
		    JSONObject json = new JSONObject();			
		    json.put("result", "success");	
		    response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
		}
		
		
		return null;
	}
}
