package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.service.ProductService;
import com.winsafe.drp.util.DBUserLog;

public class DelProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppProduct ap = new AppProduct();
			String id = request.getParameter("ID");
			Product p = ap.getProductByID(id);
			AppProductStockpile aps = new AppProductStockpile();
			ProductService ps = new ProductService();
			UsersBean users = UserManager.getUser(request);

			//检查是否有权限修改
			if(!ps.canModify(p.getProductType(), users.getUserid())) {
				request.setAttribute("result", "您当前无权限删除此类型的产品!");
				return new ActionForward("/sys/lockrecord2.jsp"); 
			}
			if (aps.getSumByProductID(id) > 0.00
					|| aps.getPoSumByProductID(id) > 0.00) {
				request.setAttribute("result","该产品已经使用，不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			AppOrganProduct apop = new AppOrganProduct();
			if ( apop.getCountByProductID(id) > 0 ){
				request.setAttribute("result","该产品已经使用，不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			AppStockWasteBook aswb = new AppStockWasteBook();
			if (aswb.getStockWasteBookByProductID(id) > 0) {
				request.setAttribute("result","该产品已经使用，不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}


			ap.delProduct(id);
			
			AppFUnit afu = new AppFUnit();
			afu.delFUnitByPID(id);

			request.setAttribute("result", "databases.del.success");
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "删除产品,编号：" + id, p);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
