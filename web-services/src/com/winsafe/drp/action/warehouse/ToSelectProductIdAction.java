package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.a.a;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class ToSelectProductIdAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			String[] arr = request.getParameterValues("arr");

			String Condition = "  p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "ProductName", "PYCode", "SpecMode",
					"NCcode");

			whereSql = whereSql + leftblur + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct ap = new AppProduct();
			List pls = ap.getSelectProduct(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();
			AppBaseResource abr = new AppBaseResource();
			for (int i = 0; i < pls.size(); i++) {
				BaseResource br = new BaseResource();
				ProductForm pf = new ProductForm();
				Product o = (Product) pls.get(i);
				br = abr.getBaseResourceValue("CountUnit", o.getSunit());
				pf.setId(o.getId());
				pf.setProductname(o.getProductname());
				pf.setSpecmode(o.getSpecmode());
				// pf.setCountunit(o.getCountunit());
				pf.setCountunit(Constants.DEFAULT_UNIT_ID);
				pf.setSunitname(br.getTagsubvalue());
				pf.setCountunitname(HtmlSelect.getResourceName(request, "CountUnit", Constants.DEFAULT_UNIT_ID));
				pf.setNccode(o.getNccode());
				// pf.setBarcode(String.valueOf(o.getBarcode()));
				sls.add(pf);
			}

			request.setAttribute("sls", sls);
			request.setAttribute("KeyWordLeft", request.getParameter("KeyWordLeft"));
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
