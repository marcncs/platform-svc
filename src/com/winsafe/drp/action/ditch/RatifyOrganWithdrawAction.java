package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
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
public class RatifyOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			AppOrganWithdrawDetail appowd = new AppOrganWithdrawDetail();
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);

			String inwarehouseid = request.getParameter("inwarehouseid");
			int[] detailid = RequestTool.getInts(request, "detailid");
			double[] unitprice = RequestTool.getDoubles(request, "unitprice");
			double[] ratifyquantity = RequestTool.getDoubles(request, "ratifyquantity");
			double totalsum=0.00;
			for (int i=0; i<detailid.length; i++){
				appowd.updRatifyQuantity(detailid[i], ratifyquantity[i], unitprice[i]);
				totalsum+=unitprice[i]*ratifyquantity[i];
			}
			
			ow.setInwarehouseid(inwarehouseid);
			ow.setTotalsum(totalsum);
			ow.setIsratify(1);
			ow.setRatifyid(userid);
			ow.setRatifydate(DateUtil.getCurrentDate());
			appAMA.update(ow);
			
			
			UsersService us = new UsersService();
			Users u = us.getUsersByid(ow.getMakeid());
			String[] param = new String[]{"name","applytime","billno"};
			String[] values = new String[]{u.getRealname(), DateUtil.formatDate(ow.getMakedate()), ow.getId()};
			MsgService ms = new MsgService(param, values, users, 13);
			ms.addmag(1,u.getMobile());	

			request.setAttribute("result", "databases.refer.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>批准渠道退货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
