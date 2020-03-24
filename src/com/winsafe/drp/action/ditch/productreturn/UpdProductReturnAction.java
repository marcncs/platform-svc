package com.winsafe.drp.action.ditch.productreturn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06
 * www.winsafe.cn
 */
public class UpdProductReturnAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ama = appAMA.getOrganWithdrawByID(id);
			AppWarehouse appWarehouse = new AppWarehouse();
			String warehouseName = appWarehouse.getWarehouseNameById(ama.getWarehouseid());
			
			if(ama.getIsaudit()==1){
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			List<OrganWithdrawDetail> listAmad = appAMAD.getOrganWithdrawDetailByOwid(id);
			
			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);
			request.setAttribute("warehouseName", warehouseName);
			
			return mapping.findForward("upd");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return super.execute(mapping, form, request, response);
	}
}
