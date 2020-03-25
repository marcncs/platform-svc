package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectAndResetTTAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);
		AppTakeTicket appTakeTicket = new AppTakeTicket();
		//得到系统当前存在的TT(条码不全的,未复核的)
		List<TakeTicket> existTT = appTakeTicket.getNotAuditTT(users.getUserid());
		for (TakeTicket takeTicket : existTT) {
		    takeTicket.setProductNames();
		}
		//得到错误的TT编号
		Map<String, List<TakeTicketIdcode>> notExistTT = appTakeTicket.getNotExistTT();
		Set<Map.Entry<String, List<TakeTicketIdcode>>> ttSet = notExistTT.entrySet();
		List<TakeTicket> ttList = new ArrayList<TakeTicket>();
		//得到TT单号列表(用于页面显示)
		for (Entry<String, List<TakeTicketIdcode>> entry : ttSet) {
		    TakeTicket takeTicket = new TakeTicket();
		    //初始化TT的产品明细和数量
		    takeTicket.setId(entry.getKey());
		    takeTicket.setProductNames(entry.getValue());
		    ttList.add(takeTicket);
		}
		request.getSession().setAttribute("notExistTTList", notExistTT);
		request.getSession().setAttribute("existTT", existTT);
		request.getSession().setAttribute("notExistTT", ttList);
		return mapping.findForward("list");
		
	} 
}
