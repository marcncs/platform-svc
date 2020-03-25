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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.SaleOrderDetailForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String cid = request.getParameter("cid");
		if ( cid == null ){
			cid = (String)request.getSession().getAttribute("cid");
		}
		request.getSession().setAttribute("cid", cid);

		super.initdata(request);try{
			String strCondition = " s.cid='" + cid
					+ "' and s.salesort=1 and s.isblankout=0 and s.id=d.soid and d.productid=p.id ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleOrder", "SaleOrderDetail","Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);


			//String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			 String blur = DbUtil.getOrBlur(map, tmpMap, "p.productname", "p.specmode");
		      String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql + blur + leftblur+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setDynamicPager(request,
					"SaleOrder as s,SaleOrderDetail as d,Product as p", whereSql, pagesize,
					"subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppSaleOrderDetail app = new AppSaleOrderDetail();
			List pls = app.getSettlementSaleOrderDetail(pagesize, whereSql,
					tmpPgInfo);
			ArrayList sls = new ArrayList();

			AppWarehouse aw = new AppWarehouse();
			SaleOrderDetail sod = null;
			for (int i = 0; i < pls.size(); i++) {
				sod = (SaleOrderDetail) pls.get(i);
				SaleOrderDetailForm ppf = new SaleOrderDetailForm();
//				ppf.setId(sod.getId());
				ppf.setProductid(sod.getProductid());
				ppf.setProductname(sod.getProductname());
				ppf.setSpecmode(sod.getSpecmode());
				ppf.setWarehouseid(sod.getWarehouseid());
				Warehouse w = aw.getWarehouseByID(sod.getWarehouseid());
				if (w != null) {
					ppf.setWarehouseidname(w.getWarehousename());
				}
				ppf.setUnitid(sod.getUnitid());
				ppf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(sod.getUnitid()
								.toString())));
				ppf.setBatch(sod.getBatch());
				ppf.setUnitprice(sod.getUnitprice());
				ppf.setQuantity(sod.getQuantity());
				ppf.setDiscount(sod.getDiscount());
				ppf.setTaxrate(sod.getTaxrate());
				ppf.setSubsum(sod.getSubsum());
				sls.add(ppf);
			}
			
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			
			String brandselect=Internation.getSelectTagByKeyAllDB("Brand","Brand",true );
				  
				request.setAttribute("brandselect",brandselect);
				request.setAttribute("uls", uls);

			request.setAttribute("uls", uls);
			request.setAttribute("cid", cid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
