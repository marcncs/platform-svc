package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganProductForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : ivan
 * @version : 2009-8-25 下午12:24:53 www.winsafe.cn
 */
public class SelectMemberProductPriceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String cid = request.getParameter("cid");
			String organid = users.getMakeorganid();
			String Condition = " op.organid='" + organid + "' and op.productid = p.id and p.useflag=1 and p.wise=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename={"Product"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id","ProductName","PYCode","SpecMode");
			
			whereSql = whereSql +leftblur+ blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
		      
			AppProduct ap = new AppProduct();
			AppOrganProduct appOrganProduct = new AppOrganProduct();
			
			List<Product> list = appOrganProduct.getOrganProductObj(request,
					pagesize, whereSql);
			List sls = new ArrayList();
			for( Product p : list ){
				OrganProductForm pf = new OrganProductForm();
				pf.setProductid(p.getId());
				pf.setProductname(p.getProductname());
				pf.setSpecmode(p.getSpecmode());
				pf.setUnitid(p.getSunit());
				pf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", p.getSunit()));
				pf.setPrice(ap.getProductPriceByOidPidCid(organid, p.getId(),
						p.getSunit(), cid));
				sls.add(pf);
			}
			request.setAttribute("cid", cid);
			request.setAttribute("oid", organid);
			request.setAttribute("list", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
