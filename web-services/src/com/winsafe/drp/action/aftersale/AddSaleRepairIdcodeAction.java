package com.winsafe.drp.action.aftersale;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppSaleRepairIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.SaleRepairIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class AddSaleRepairIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
	
		String productid = request.getParameter("productid");
		String batch = "";
		String[] idcode = request.getParameterValues("idcode");
		String piid = request.getParameter("piid");

		AppSaleRepairIdcode app = new AppSaleRepairIdcode();
		AppIdcode appidcode = new AppIdcode();
		try {
			
			List idlist = Arrays.asList(idcode);
			if ( idlist.size() != (new HashSet(idlist)).size() ){
			     request.setAttribute("result", "databases.record.doubleidcode");
			     return new ActionForward("/sys/lockrecord.jsp");
			}
//			for ( int i =0; i < idcode.length; i ++){
//				Idcode ic = appidcode.getIdcodeById(productid, idcode[i]);
//				if ( ic == null || ic.getIsuse() == 1){
//				    request.setAttribute("result", "databases.record.idcode.nosale");
//				    return new ActionForward("/sys/lockrecord.jsp");
//				}
//			}
			app.delSaleRepairIdcodeByPidBatch(productid, batch);
			SaleRepairIdcode pii = null;
			Idcode ic = null;
			for ( int i =0; i < idcode.length; i ++){
				pii = new SaleRepairIdcode();
				pii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
							"sale_trades_idcode", 0, "")));
				pii.setSrid(piid);
				pii.setProductid(productid);
				pii.setIdcode(idcode[i]);
				app.addSaleRepairIdcode(pii);
				
				
//				ic = new Idcode();
//				ic.setId(Long.valueOf(mc.getExcIDByRandomTableName(
//						"idcode", 0, "")));
//				ic.setBatch(pii.getBatch());
//				ic.setIdcode(pii.getIdcode());
//				ic.setProductid(pii.getProductid());
//				ic.setIsuse(0);
//				appidcode.addIdcode(ic);
			}
	

			request.setAttribute("result", "databases.add.success");			

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}
}
