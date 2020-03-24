package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.OrganProductForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JsonUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : ivan
 * @version : 2009-8-25 下午12:24:53 www.winsafe.cn
 */
public class SelectOrganProductPriceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//DAO所需类
		AppProduct ap = new AppProduct();
		AppOrganProduct appOrganProduct = new AppOrganProduct();
		AppFUnit appFUnit = new AppFUnit();
		
		//初始化
		int pagesize = 15;
		super.initdata(request);
		
		try {
			String organid = request.getParameter("OID");
			String Condition = " op.organid='" + organid
					+ "' and op.productid = p.id and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			String productname = (String)map.remove("ProductName");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur2(map, tmpMap, "p.id", "ProductName",
					"PYCode", "SpecMode", "p.nccode", "p.mCode");

			whereSql = whereSql + leftblur + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			if(!StringUtil.isEmpty(productname)) {
				whereSql += " and p.productname = '" + productname + "'";
			}

			List<Product> list = appOrganProduct.getOrganProductObj(request,pagesize, whereSql);
			List sls = new ArrayList();
			for (Product p : list) {
				OrganProductForm pf = new OrganProductForm();
				pf.setProductid(p.getId());
				pf.setProductname(p.getProductname());
				pf.setSpecmode(p.getSpecmode());
				pf.setPackSizeName(p.getPackSizeName());
				// 默认单位只能选择件
//				pf.setUnitid(p.getSunit());
//				pf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", p.getSunit()));
				pf.setUnitid(Constants.DEFAULT_UNIT_ID);
				pf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit", Constants.DEFAULT_UNIT_ID));
				pf.setPrice(0d);
				pf.setNccode(p.getmCode());
				//设置产品的计量单位
				pf.setCountUnitName(HtmlSelect.getResourceName(request,"CountUnit", p.getCountunit()));
				
				//获取产品的所有单位
				List<FUnit> unitList = appFUnit.getFUnitByProductID(p.getId());
				List<Map> uMapList = new ArrayList<Map>();
				for(FUnit fUnit : unitList){
					Map uMap = new HashMap<String, String>();
					uMap.put("unitId", fUnit.getFunitid());
					uMap.put("xQuantity", fUnit.getXquantity());
					uMapList.add(uMap);
				}
				//增加计量单位
				Map uLMap = new HashMap<String, String>();
				uLMap.put("unitId", 0);
				uLMap.put("xQuantity", p.getBoxquantity());
				uMapList.add(uLMap);
				
				pf.setUnitList(uMapList.toString());
				sls.add(pf);
			}
			request.setAttribute("OID", organid);
			request.setAttribute("list", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
