package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSuitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		AppSuit ap = new AppSuit();

		try {

			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Suit p = new Suit();
			p.setId(MakeCode.getExcIDByRandomTableName("suit", 2, "SU"));
			p.setCid(cid);
			p.setObjsort(RequestTool.getInt(request, "objsort"));
			p.setCname(request.getParameter("cname"));
			p.setSuitcontent(Integer.valueOf(request.getParameter("suitcontent")));
			p.setSuitway(Integer.valueOf(request.getParameter("suitway")));
			p.setSuittools(request.getParameter("suittools"));
			p.setSuitstatus(request.getParameter("suitstatus"));
			String suitdate = request.getParameter("suitdate");
			p.setSuitdate(DateUtil.StringToDate(suitdate));
			p.setMemo(request.getParameter("memo"));
			p.setIsdeal(0);
			p.setDealway(0);
			p.setDealuser(Integer.valueOf(0));
			p.setDealcontent("");
			p.setDealfinal("");
			p.setMakeorganid(users.getMakeorganid());
			p.setMakedeptid(users.getMakedeptid());
			p.setMakeid(userid);
			p.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			
			ap.addSuit(p);
			
			AppApproveFlowService apfs = new AppApproveFlowService();
			List detaillist = apfs.getFlowDetail("SU");
			
		    apfs.referApproveFlow(detaillist, p.getId().toString());

			DBUserLog.addUserLog(userid,5, "会员/积分管理>>新增抱怨投诉,编号：" + p.getId());

			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
