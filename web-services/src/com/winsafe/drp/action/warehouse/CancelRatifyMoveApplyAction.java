package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelRatifyMoveApplyAction extends BaseAction {
	
	private AppStockMove asm = new AppStockMove();
	private AppStockMoveDetail asmd = new AppStockMoveDetail();
	private AppTakeTicket apb = new AppTakeTicket();
	private AppTakeTicketDetail apbd = new AppTakeTicketDetail();
	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String aaid = request.getParameter("AAID");
			AppMoveApply api = new AppMoveApply();
			MoveApply pi = api.getMoveApplyByID(aaid);

			if (pi.getIsratify() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			TakeTicket tt = apb.getTakeTicket(pi.getId());
			if (tt.getIsaudit() == 1) {
				String result = "databases.record.alreadycompletenocancel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			List<TakeTicketIdcode> idcodes =  atti.getTakeTicketIdcodeByttid(tt.getId());
			if(idcodes.size() > 0) {
				String result = "databases.record.alreadyinprocessnocancel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//删除机构间转仓单
			asmd.delStockMoveDetailBySmID(pi.getId());
			asm.delStockMove(pi.getId());
			//删除TT单
			apbd.delTakeTicketDetailByTtid(tt.getId());
			apb.delTakeTicketByBillNo(pi.getId());
			
			pi.setIsratify(0);
			pi.setRatifyid(null);
			pi.setRatifydate(null);
			api.updMoveApply(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid,"机构间转仓申请审核", "销售管理>>取消批准机构间转仓申请单,编号：" + aaid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
