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
import com.winsafe.drp.dao.AppOrganTradesPIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesPIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteOrganTradePReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    String smid = request.getParameter("ID");

		super.initdata(request);try{
			AppOrganTrades asm = new AppOrganTrades();
			OrganTrades sm = asm.getOrganTradesByID(smid);

			
			if(sm.getPisreceive()==0){
	               request.setAttribute("result", "databases.record.already");
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
			
			if (!sm.getPorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppOrganTradesPIdcode appmi = new AppOrganTradesPIdcode();
			List<OrganTradesPIdcode> idcodelist = appmi.getOrganTradesPIdcodeByotid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( OrganTradesPIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			List<OrganTradesPIdcode> alllist = appmi.getOrganTradesPIdcodeByotid(smid);
			
			returnProductStockpile(alllist, sm.getInwarehouseid());
			
			sm.setPisreceive(0);
			sm.setPreceiveid(null);
			sm.setPreceivedate(null);
						
			for ( OrganTradesPIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>渠道换货供方签收>>取消签收渠道换货供方签收,编号：" + smid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<OrganTradesPIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (OrganTradesPIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getOtid(), "取消渠道换货供方签收");
		}
	}

}
