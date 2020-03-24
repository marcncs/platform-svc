package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AlterMoveApplyDetailForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.Internation;

public class ToTransStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{
			AppAlterMoveApply asm = new AppAlterMoveApply();
		      AlterMoveApply sm = asm.getAlterMoveApplyByID(id);


		      AppAlterMoveApplyDetail asmd = new AppAlterMoveApplyDetail();
		      List smdls = asmd.getAlterMoveApplyDetailNoTransByAmID(id);
		      ArrayList als = new ArrayList();

		      for(int i=0;i<smdls.size();i++){
		    	  AlterMoveApplyDetailForm smdf = new AlterMoveApplyDetailForm();
		    	  AlterMoveApplyDetail o = (AlterMoveApplyDetail)smdls.get(i);
		    	  smdf.setId(o.getId());
		        smdf.setProductid(o.getProductid());
		        smdf.setProductname(o.getProductname());
		        smdf.setSpecmode(o.getSpecmode());
		        smdf.setUnitid(o.getUnitid());
		        smdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",         
		            o.getUnitid()));
		        smdf.setUnitprice(o.getUnitprice());
		        smdf.setQuantity(o.getQuantity());
		        smdf.setCanquantity(o.getCanquantity());
		        smdf.setAlreadyquantity(o.getAlreadyquantity());
		        smdf.setSubsum(o.getSubsum());
		        als.add(smdf);
		      }
		      
		      
			
			
			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);
				icls.add(ic);
			}
			request.setAttribute("icls", icls);

			request.setAttribute("als", als);
			request.setAttribute("smf", sm);
			DBUserLog.addUserLog(userid,4, "渠道管理>>订购申请转订购,编号："+id);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
