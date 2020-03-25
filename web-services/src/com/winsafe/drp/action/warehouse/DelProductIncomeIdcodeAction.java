package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeIdcode;

public class DelProductIncomeIdcodeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{

			AppProductIncomeIdcode asb = new AppProductIncomeIdcode();
			AppProductIncomeDetail apid = new AppProductIncomeDetail();
			String ids = request.getParameter("ids");			
			String[] id = ids.split(",");			
			if ( id != null ){
				for (int i=0; i<id.length; i++ ){
					ProductIncomeIdcode pii = asb.getProductIncomeIdcodeById(Long.valueOf(id[i]));
					List<ProductIncomeDetail> pids = (List<ProductIncomeDetail>) apid.getProductIncomeDetailByPiidPid(pii.getPiid(), pii.getProductid());
					if(null != pids && pids.size() > 0)
					{
						for(ProductIncomeDetail pid : pids)
						{
							Double funit = new AppFUnit().getXQuantity(pid.getProductid(), 2);
							if(null != funit){
								pid.setQuantity(pid.getQuantity() - funit.doubleValue());
								pid.setFactquantity(pid.getFactquantity() - funit.doubleValue());
							}
							apid.updProductIncomeDetail(pid);
							break;
						}
					}
					asb.delProductIncomeIdcodeById(Long.valueOf(id[i]));
				}
			}


			request.setAttribute("result", "databases.del.success");
//			UsersBean users = UserManager.getUser(request);
//			DBUserLog.addUserLog(users.getUserid(), 7, "入库>>产成品入库>>删除条码,条码:"
//					+ sb.getIdcode(), sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
