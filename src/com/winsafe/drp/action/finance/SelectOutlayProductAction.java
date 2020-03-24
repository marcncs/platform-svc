package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectOutlayProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		String keyword = request.getParameter("KeyWord");
		super.initdata(request);super.initdata(request);try{
			String Condition = " p.wise=2 and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from provide ";
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getBlur(map, tmpMap, "ProductName");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Product as p", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppProduct ap = new AppProduct();
//			List pls = ap.getProduct(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

//			for (int i = 0; i < pls.size(); i++) {
//				ProductForm uf = new ProductForm();
//				Object[] o = (Object[]) pls.get(i);
//				uf.setId(o[0].toString());
//				uf.setProductname(o[1].toString());
//				uf.setSpecmode(o[2].toString());
//				sls.add(uf);
//			}

			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
