package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPicture;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.ProductPicture;
import com.winsafe.drp.dao.ProductPictureForm;
import com.winsafe.drp.util.DBUserLog;

public class ListProductPictureAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Long id = Long.valueOf(request.getParameter("ID"));
			AppOrganProduct aop = new AppOrganProduct();
			OrganProduct op = aop.getOrganProductByID(id);

			AppProductPicture abr = new AppProductPicture();
			AppProduct ap = new AppProduct();

			List apls = abr.getProductPictureByProductID(op.getProductid());
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				ProductPictureForm ppf = new ProductPictureForm();
				ProductPicture o = (ProductPicture) apls.get(i);

				ppf.setId(o.getId());
				ppf.setProductid(o.getProductid());
				ppf.setProductidname(ap.getProductByID(o.getProductid())
						.getProductname());
				ppf.setPictureurl(o.getPictureurl());

				alpl.add(ppf);
			}
			DBUserLog.addUserLog(userid, 4, "渠道管理>>列表销售商品图片");
			request.setAttribute("alpl", alpl);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
