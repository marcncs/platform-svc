package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

/**
 * 拣货建议单生成IS订购单
 * @author Andy.liu
 *
 */
public class ProduceStockAlterMoveAction extends Action {
	AppWarehouse aw = new AppWarehouse();
	AppOrgan ao = new AppOrgan();
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	AppStockAlterMove asam = new AppStockAlterMove();
	AppProduct ap = new AppProduct();
	AppStockAlterMoveDetail asamd =new AppStockAlterMoveDetail();
	AppSuggestInspect asi = new AppSuggestInspect();
	
	StockAlterMoveService sams = new StockAlterMoveService();
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			AppSuggestInspect asi = new AppSuggestInspect();
			if(ids.indexOf("on") > 1){
				ids = ids.substring(0,ids.length()-3);
			}
			String whereSql = " where si.id in ("+ids+")";
			List<SuggestInspect> list = asi.getSiByIds(whereSql);
			/*
			 * 遍历单据集合，生成IS订购单
			 * 
			 */
			for (SuggestInspect si : list) {
				String returnStr =sams.produceStockAlterMove(request,si);
				if(!StringUtil.isEmpty(returnStr)){
					 request.setAttribute("result", returnStr);
					 return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			request.setAttribute("result", "databases.samout.success");
			DBUserLog.addUserLog(userid, 11, "拣货建议单生成IS订购单");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
	
}
