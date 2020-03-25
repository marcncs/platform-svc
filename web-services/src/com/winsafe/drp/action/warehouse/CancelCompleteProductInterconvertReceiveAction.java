package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteProductInterconvertReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    String smid = request.getParameter("SMID");

		super.initdata(request);try{
			AppProductInterconvert asm = new AppProductInterconvert();
			ProductInterconvert sm = asm.getProductInterconvertByID(smid);

			
			if(sm.getIscomplete()==0){
	               request.setAttribute("result", "databases.record.already");
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
			
			if (!sm.getMakeorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppProductInterconvertIdcode appmi = new AppProductInterconvertIdcode();
			List<ProductInterconvertIdcode> idcodelist = appmi.getProductInterconvertIdcodeByPiid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( ProductInterconvertIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			List<ProductInterconvertIdcode> alllist = appmi.getProductInterconvertIdcodeByPiid(smid);
			
			returnProductStockpile(alllist, sm.getInwarehouseid());
			
			asm.updProductInterconvertIsComplete(smid, 0, userid);
						
			for ( ProductInterconvertIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>商品互转签 收>>取消签收,编号：" + smid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<ProductInterconvertIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (ProductInterconvertIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getPiid(), "取消商品互转签收");
		}
	}

}
