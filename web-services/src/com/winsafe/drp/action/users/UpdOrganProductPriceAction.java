package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.RequestTool;

public class UpdOrganProductPriceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		try {
			int[] opid = RequestTool.getInts(request, "id");
			double[] unitprice = RequestTool.getDoubles(request, "unitprice");
			double[] frate = RequestTool.getDoubles(request, "frate");
			AppOrganPriceii appod = new AppOrganPriceii();
			
			for(int i=0; i<opid.length; i++ ){
				appod.updOrganPriceiiPrice(opid[i], unitprice[i],frate[i]/100);				
			}

			request.setAttribute("result", "databases.upd.success");			
			//DBUserLog.addUserLog(users.getUserid(), 11, "修改产品订购价格,机构编号"+users.getMakeorganid());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

}
