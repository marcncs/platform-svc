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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectStuffProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String strwid=request.getParameter("wid");
	    if(strwid==null){
	      strwid=(String)request.getSession().getAttribute("wid");
	    }
	    request.getSession().setAttribute("wid",strwid);
		Long wid = Long.valueOf(strwid);

		super.initdata(request);try{
			String strCondition = "(pp.propertycode=2 or pp.propertycode=5) and ps.productid=pp.productid and ps.stockpile>0 and ps.warehouseid=" + wid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			//String sql = "select * from product where " + strCondition;
			String[] tablename = { "ProductStockpile","ProductProperty" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			whereSql = whereSql + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setDynamicPager(request, "ProductStockpile as ps,ProductProperty as pp ",
					whereSql, pagesize, "subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppProductStockpile aps = new AppProductStockpile();
			AppWarehouse aw = new AppWarehouse();
//			List pls = aps.getProductStockpileForStuff(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

//			for (int i = 0; i < pls.size(); i++) {
//				ProductStockpileForm psf = new ProductStockpileForm();
//				Object[] o = (Object[]) pls.get(i);
//				psf.setId(Long.valueOf(o[0].toString()));
//				psf.setProductid(o[1].toString());
//				psf.setPsproductname(o[2].toString());
//				psf.setPsspecmode(o[3].toString());
//				psf.setCountunit(Integer.parseInt(o[4].toString()));
//				psf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", 
//						Integer.parseInt(o[4].toString())));
//				psf.setBatch(o[5].toString());
//				psf.setBarcode(o[6].toString());
////				psf.setWarehourseidname(aw.getWarehouseByID(Long.valueOf(o[7].toString())).getWarehousename());
//				psf.setStockpile(Double.valueOf(o[8].toString()));
//				sls.add(psf);
//			}
					
				  AppProductStruct appProductStruct = new AppProductStruct();
					List uls = appProductStruct.getProductStructCanUse();
			
				  request.setAttribute("uls",uls);
			request.setAttribute("wid", wid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
