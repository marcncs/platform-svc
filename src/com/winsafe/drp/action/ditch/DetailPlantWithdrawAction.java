package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06 www.winsafe.cn
 */
public class DetailPlantWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			String isshow = request.getParameter("isshow");
			AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
			AppFUnit  appFUnit = new AppFUnit();
			AppProduct appProduct = new AppProduct();
			
			List<OrganWithdrawDetail> listAmad = appAMAD.getOrganWithdrawDetailByOwid(id);
			
			for(OrganWithdrawDetail detail : listAmad){
				if(detail == null) continue;
				Product product = appProduct.getProductByID(detail.getProductid());
				if(product == null) continue;
				detail.setCountunit(product.getCountunit());
				// 转化数量为计量单位的数量
				Double quantity = appFUnit.getQuantity(detail.getProductid(), detail.getUnitid(), detail.getQuantity());
				quantity = quantity * product.getBoxquantity();
				detail.setcUnitQuantity(quantity);
			}
			
			
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ama = appAMA.getOrganWithdrawByID(id);

			UsersBean users = UserManager.getUser(request);
			request.setAttribute("organid", users.getMakeorganid());
			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);
			request.setAttribute("isshow", isshow);
			
			request.setAttribute("type", request.getParameter("type"));
			
			return mapping.findForward("detail");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
