package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.MsgService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34 www.winsafe.cn
 */
public class RatifyOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String id = request.getParameter("id");
			AppOrganTrades appAMA = new AppOrganTrades();
			OrganTrades ot = appAMA.getOrganTradesByID(id);
			
			int strid[] = RequestTool.getInts(request, "detailid");
			double canquantity[] = RequestTool.getDoubles(request, "canquantity");
			
			AppOrganTradesDetail  appOtd = new AppOrganTradesDetail();
			for(int i = 0 ; i < strid.length ;i++){
				OrganTradesDetail amad = appOtd.getOrganTradesDetailByID(strid[i]);
				amad.setCanquantity(canquantity[i]);
				appOtd.update(amad);
			}
			ot.setInwarehouseid(request.getParameter("inwarehouseid"));
			ot.setIsratify(1);
			ot.setRatifyid(userid);
			ot.setRatifydate(DateUtil.getCurrentDate());
			appAMA.update(ot);
			
			
			UsersService us = new UsersService();
			Users u = us.getUsersByid(ot.getMakeid());
			String[] param = new String[]{"name","applytime","billno"};
			String[] values = new String[]{u.getRealname(), DateUtil.formatDate(ot.getMakedate()), ot.getId()};
			MsgService ms = new MsgService(param, values, users, 15);
			ms.addmag(1,u.getMobile());	

			request.setAttribute("result", "databases.refer.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>批准渠道换货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
