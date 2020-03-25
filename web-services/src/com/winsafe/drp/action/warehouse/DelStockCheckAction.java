package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelStockCheckAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{

			String scid = request.getParameter("ID");
			AppStockCheck asb = new AppStockCheck();
			StockCheck sb = asb.getStockCheckByID(scid);
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppStockCheckDetail ascd = new AppStockCheckDetail();

			AppProductStockpile aps = new AppProductStockpile();
			List<StockCheckDetail> ls = ascd.getStockCheckDetailBySmID(scid);
			for (StockCheckDetail scd : ls) {
				aps.IsLock(scd.getProductid(), scd.getBatch(), sb
						.getWarehouseid(), scd.getWarehousebit(), 0);
			}
			
			if ( sb.getIsbar() == 1 ){
				AppStockCheckIdcode appsci = new AppStockCheckIdcode();
				appsci.delStockCheckIdcodeByScid(scid);
			}
				
			ascd.delStockCheckDetailBySmID(scid);
			asb.delStockCheck(scid);
			

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>删除库存盘点,编号:" + scid, sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
