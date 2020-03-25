package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:13:03
 * www.winsafe.cn
 */
public class ToAddSupplySaleApplyAction extends BaseAction {

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String makeorganid = users.getMakeorganid();
		AppInvoiceConf aic = new AppInvoiceConf();
		List<InvoiceConf> uls = aic.getAllInvoiceConf();
		

		List<InvoiceConf> icls = new ArrayList<InvoiceConf>();
		for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);
				icls.add(ic);
		}	
		request.setAttribute("icls", icls);
		AppOrgan os = new AppOrgan();
		Organ og = os.getOrganByID(makeorganid);
		String parentOrganid =og.getParentid() ;
		if(parentOrganid.equals("0")){
			parentOrganid = makeorganid;
		}
		request.setAttribute("parentOrganid", parentOrganid);
		request.setAttribute("makeorganid", makeorganid);
		return mapping.findForward("add");
	}
}
