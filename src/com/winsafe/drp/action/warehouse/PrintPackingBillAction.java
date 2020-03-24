package com.winsafe.drp.action.warehouse;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.util.DBUserLog;

public class PrintPackingBillAction extends BaseAction {
	AppTakeBill atb = new AppTakeBill();
	AppTakeTicketDetail attd = new AppTakeTicketDetail();
	AppSuggestInspect asi = new AppSuggestInspect();
	AppStockAlterMove asam = new AppStockAlterMove();
	DecimalFormat format = new DecimalFormat("0000");
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String ttid = request.getParameter("ttid");
			String type = request.getParameter("type");
			String count = request.getParameter("count");
			String index = request.getParameter("index");
			TakeBill tb = atb.getTakeBillByID(ttid);
			if(!StringUtil.isEmpty(count))
				count = format.format(Integer.valueOf(count));
			if(tb.getBsort()!=1){
				request.setAttribute("result", "对不起，只有渠道订购单才能打印装箱单！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			if(tb.getIsaudit()==0){
				request.setAttribute("result", "对不起，该订购单未复核！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			if(type.equals("1")){
				request.setAttribute("ttid", ttid);
				
				return mapping.findForward("toprint");
			}
			List<Map> siids = asam.getSiidByTtid(ttid);
			List<TakeTicketDetail> pils = attd.getTakeTicketDetailByBillno(ttid);
			request.setAttribute("type", type);
			request.setAttribute("tb", tb);
		    request.setAttribute("alpi", pils);
		    request.setAttribute("siids", siids);
		    request.setAttribute("count", count);
			request.setAttribute("index", index);
			DBUserLog.addUserLog(userid, 7, "仓库管理>>打印装箱单");
			return mapping.findForward("print");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
