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
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditStockCheckAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
		super.initdata(request);try{
			String scid = request.getParameter("SCID");
			AppStockCheck asc = new AppStockCheck(); 

			StockCheck sc = asc.getStockCheckByID(scid);

			if(sc.getIsaudit()==0){
	               request.setAttribute("result", "databases.record.already");
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			if( sc.getIscreate() == 1 ){
	               request.setAttribute("result", "该单据已经完成，不能取消！");
	               return new ActionForward("/sys/lockrecordclose2.jsp");
	           }
			
			
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			List<StockCheckDetail> ls = ascd.getStockCheckDetailBySmID(scid);
			
			AppProductStockpile aps = new AppProductStockpile();
			for (StockCheckDetail scd : ls) {
				aps.IsLock(scd.getProductid(), scd.getBatch(),
						sc.getWarehouseid(), scd.getWarehousebit(), 1);
			}
			
			 asc.updIsAudit(scid, userid, 0);
			
		      request.setAttribute("result", "databases.cancel.success");
		      DBUserLog.addUserLog(userid,7, "库存盘点>>取消复核商品盘点单,编号:"+scid);
			return mapping.findForward("noaudit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
