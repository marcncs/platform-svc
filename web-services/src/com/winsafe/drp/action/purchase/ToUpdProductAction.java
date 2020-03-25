package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.service.ProductService;
import com.winsafe.hbm.util.Internation;

public class ToUpdProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductService productService = new ProductService();
		String strid = request.getParameter("ID");
		String id = strid;
		try {
			initdata(request);
			AppProduct ap = new AppProduct();
			AppFUnit afu = new AppFUnit();
			AppProductStruct appProductStruct = new AppProductStruct();
			AppIdcode appIdcode = new AppIdcode();
			
			Product p = ap.getProductByID(id);
			
			//检查是否有权限修改 
			if(p.getProductType() == null 
					|| !productService.canModify(p.getProductType(), users.getUserid())) {
				request.setAttribute("result", "您当前无权限修改此类型的产品!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			String funitid = Internation.getSelectTagByKeyAllDB("CountUnit","funitid", false);

			ProductStruct ps = appProductStruct.getProductStructById(p.getPsid());

			List fuls = afu.getFUnitByProductIDNoIsMain(id);
			//是否可更新包装大小(判断是否有码出库,如果出库则不允许修改)
			int canupdate = 1;
			if(appIdcode.IsExistIdcodeByPid(p.getId())){
				canupdate = 0;
			}
			request.setAttribute("afls", fuls);
			request.setAttribute("p", p);
			request.setAttribute("funitid", funitid);
			request.setAttribute("psidname", ps != null ? ps.getSortname(): "");
			request.setAttribute("canupdate", canupdate);
			request.setAttribute("productTypes", productService.getProductTypes(users.getUserid()));

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
