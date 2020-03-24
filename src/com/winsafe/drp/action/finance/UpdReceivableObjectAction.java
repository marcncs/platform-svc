package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.util.DBUserLog;

public class UpdReceivableObjectAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			AppReceivableObject ar = new AppReceivableObject();
			ReceivableObject r = new ReceivableObject();
			r.setId(Integer.valueOf(request.getParameter("id")));
			r.setOid(request.getParameter("oid"));
			r
					.setObjectsort(Integer.valueOf(request
							.getParameter("objectsort")));

			String payerid = request.getParameter("cid");
			if (payerid == null || payerid.equals("null") || payerid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			r.setPayer(payerid);

			 StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(r.getPayer()).append(",");
				
				if ( r.getObjectsort() == 1 ){
					AppCustomer ac = new AppCustomer();
					Customer c = ac.getCustomer(r.getOid());
					if ( c != null ){
						keyscontent.append(c.getMobile()).append(",").append(c.getOfficetel()).append(",");
					}
				}
				if ( r.getObjectsort() == 2){
					AppProvider ap = new AppProvider();
					Provider p = ap.getProviderByID(r.getOid());
					if ( p != null ){
						keyscontent.append(p.getMobile()).append(",");
					}
				}
				r.setKeyscontent(keyscontent.toString());

			ar.updReceivableObject(r);
			request.setAttribute("result", "databases.upd.success");
			ReceivableObject oldr = (ReceivableObject)BeanUtils.cloneBean(r);
			DBUserLog.addUserLog(userid, 9, "付款管理>>修改应收款对象,编号:"+r.getId(),oldr,r);

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
