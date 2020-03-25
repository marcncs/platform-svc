package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesTIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesTIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteOrganTradeTReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    String smid = request.getParameter("ID");

		super.initdata(request);try{
			AppOrganTrades asm = new AppOrganTrades();
			OrganTrades sm = asm.getOrganTradesByID(smid);

			
			if(sm.getIsreceive()==0){
	               request.setAttribute("result", "databases.record.already");
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
			
			if (!sm.getMakeorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppOrganTradesTIdcode appmi = new AppOrganTradesTIdcode();
			List<OrganTradesTIdcode> idcodelist = appmi.getOrganTradesTIdcodeByotid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( OrganTradesTIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			List<OrganTradesTIdcode> alllist = appmi.getOrganTradesTIdcodeByotid(smid);
			
			returnProductStockpile(alllist, sm.getOutwarehouseid());
			
			sm.setIsreceive(0);
			sm.setReceiveid(null);
			sm.setReceivedate(null);
						
			for ( OrganTradesTIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>渠道换货换方签收>>取消签收渠道换货换方签收,编号：" + smid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<OrganTradesTIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (OrganTradesTIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getOtid(), "取消渠道换货换方签收");
		}
	}

}
