package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;


/**
 * 直接过账：
 * 直接将管家婆订单生成检货出库单，未复核状态
 * @author Andy.liu
 */
public class PostSuggestInspectAction extends BaseAction {
	AppSuggestInspect asi = new AppSuggestInspect();
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	StockAlterMoveService sams = new StockAlterMoveService();
	AppStockAlterMove asam = new AppStockAlterMove();
	AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
	
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String ids = request.getParameter("ids");
			if(ids.indexOf("on") > 1){
				ids = ids.substring(0,ids.length()-3);
			}
			String siSql = " where si.id in (" + ids + ") ";
			//获取要被过账的管家婆订单
			List<SuggestInspect> list = asi.getSiByIds(siSql);
			for (SuggestInspect si : list) {
				//生成订购单
				String returnStr = sams.produceStockAlterMove(request, si);
				if(returnStr != null){
					request.setAttribute("result", returnStr);
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
				//复核该订单
				StockAlterMove  sam = asam.getStockAlterMoveByNCcode(si.getSiid());
				List<StockAlterMoveDetail> samdList = asamd.getStockAlterMoveDetailBySamID(sam.getId());
				//添加tt和tb
				sams.addTakeBill(sam, samdList, users);
				//复核状态
				sam.setAuditdate(DateUtil.getCurrentDate());
				sam.setAuditid(userid);
				sam.setIsaudit(1);
				sam.setIsmove(0);
				asam.updstockAlterMove(sam);
				//消息通知
				String[] param = new String[] { "name", "applytime", "billno" };
				String[] values = new String[] { sam.getOlinkman(), DateUtil.formatDate(sam.getMovedate()), sam.getId() };
				MsgService ms = new MsgService(param, values, users, 7);
			 	ms.addmag(1, sam.getOtel());
			}
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid,3, "拣货建议单>>直接过账");
			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
