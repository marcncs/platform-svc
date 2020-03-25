package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.server.OrganWithdrawService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * Update By WenPing 2012-6-20 复核退货单
 */
public class AuditPlantWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppOrganWithdraw appAMA = new AppOrganWithdraw();
		AppOrganWithdrawDetail appowd = new AppOrganWithdrawDetail();
		OrganWithdrawService service = new OrganWithdrawService();
		
		try {
			String id = request.getParameter("ID");
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);
			// 单据是否存在
			if(ow == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (ow.getIsaudit() == 1)
			{
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是作废
			if (ow.getIsblankout() == 1)
			{
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			List<OrganWithdrawDetail> listowd = appowd.getOrganWithdrawDetailByOwid(id);
			//判断库存
			StringBuffer errorMsg = new StringBuffer();
			if(!service.checkStock(ow, listowd,errorMsg)){
				request.setAttribute("result", errorMsg.toString() + "<br/>不能复核!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			// 设置复核
			ow.setIsaudit(1);
			ow.setAuditid(userid);
			ow.setAuditdate(DateUtil.getCurrentDate());
			// 设置批准
			ow.setIsratify(1);
			ow.setRatifyid(userid);
			ow.setRatifydate(DateUtil.getCurrentDate());
			// 设置确认
			ow.setIsaffirm(1);
			ow.setAffirmid(userid);
			ow.setAffirmdate(DateUtil.getCurrentDate());
			appAMA.update(ow);
			
			// 生成检货出库单
			service.addTakeTicket(ow, listowd, users);
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, "渠道管理", "渠道管理>>复核渠道退货,编号：" + id);

			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
