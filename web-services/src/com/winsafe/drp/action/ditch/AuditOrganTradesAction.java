package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:35:13 www.winsafe.cn
 */
public class AuditOrganTradesAction extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganTrades appAMA = new AppOrganTrades();
			OrganTrades ow = appAMA.getOrganTradesByID(id);
			AppOrganTradesDetail appowd = new AppOrganTradesDetail();

			List<OrganTradesDetail> listowd = appowd
					.getOrganTradesDetailByotid(id);
			for (OrganTradesDetail owd : listowd) {
				AppProductStockpileAll appps = new AppProductStockpileAll();
				AppFUnit appfu = new AppFUnit();

				double q = appfu.getQuantity(owd.getProductid(), owd
						.getUnitid(), owd.getQuantity());
				double stock = appps.getProductStockpileAllByPIDWID(owd
						.getProductid(), ow.getOutwarehouseid());
				if (q > stock) {
					request.setAttribute("result", "产品：" + owd.getProductname()
							+ " 库存量不足! 不能进行换货!请检查单据! ");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}

			ow.setIsaudit(1);
			ow.setAuditid(userid);
			ow.setAuditdate(DateUtil.getCurrentDate());
			appAMA.update(ow);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>复核渠道换货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
