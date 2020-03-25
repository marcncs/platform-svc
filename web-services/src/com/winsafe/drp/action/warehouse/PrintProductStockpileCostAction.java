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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintProductStockpileCostAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String keyword = request.getParameter("KeyWord");
		WarehouseService aw = new WarehouseService();
		super.initdata(request);try{
			String strCondition = " ps.warehouseid=wv.wid and wv.userid="
					+ userid + " and (ps.stockpile+ps.prepareout)!=0 and p.id=ps.productid ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpileAll", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
					"PYCode", "SpecMode");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppFUnit af = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();
			List pls = aps.getStockStat(whereSql);
			List alp = new ArrayList();
			 double totalstockpile=0.00;
		      double totalcost=0.00;
		      double totalsum=0.00;

			for (int i = 0; i < pls.size(); i++) {
		    	  ProductStockpileForm psf = new ProductStockpileForm();
		          Object[] o = (Object[])pls.get(i);
		          ProductStockpileAll ps = (ProductStockpileAll)o[0];
		          Product p = (Product)o[1];
		          psf.setId(ps.getId());
		          psf.setProductid(ps.getProductid());
		          psf.setPsproductname(p.getProductname());
		          psf.setPsspecmode(p.getSpecmode());
		          psf.setCountunit(p.getSunit());
		          psf.setBatch(ps.getBatch());
		          psf.setAllstockpile(af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getStockpile()+ps.getPrepareout()));
		          psf.setWarehouseid(ps.getWarehouseid());
		          psf.setWarehourseidname(aw.getWarehouseName(ps.getWarehouseid()));
		          psf.setCost(ps.getCost()*af.getXQuantity(ps.getProductid(), p.getSunit()));
		          psf.setTotalcost(psf.getAllstockpile()*psf.getCost());
		          
		          totalstockpile +=psf.getAllstockpile();
		          totalcost += psf.getCost();
		          totalsum += psf.getTotalcost();
		          alp.add(psf);
		       }
			 request.setAttribute("totalstockpile", totalstockpile);
		      request.setAttribute("totalcost", totalcost);
		      request.setAttribute("totalsum", totalsum);
		      
		      List alllist = aps.getStockStat(whereSql);
		      double allstockpile=0.00;
		      double allcost=0.00;
		      double allsum=0.00;
		      for(int i=0;i<alllist.size();i++){
			        Object[] o = (Object[])alllist.get(i);
			        ProductStockpileAll ps = (ProductStockpileAll)o[0];
			        Product p = (Product)o[1];
			        double sp = af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getStockpile()+ps.getPrepareout());
			        double c = ps.getCost()*af.getXQuantity(ps.getProductid(), p.getSunit());
			        allstockpile += sp;
			        allcost += c;
			        allsum += sp*c;
		      }
		      request.setAttribute("allstockpile", allstockpile);
		      request.setAttribute("allcost", allcost);
		      request.setAttribute("allsum", allsum);

			

			List wls = aw.getEnableWarehouseByVisit(userid);
			String brandselect = Internation.getSelectTagByKeyAllDB("Brand",
					"Brand", true);
			request.setAttribute("brandselect", brandselect);

			request.setAttribute("alw", wls);
			request.setAttribute("keyword", keyword);
			request.setAttribute("alp", alp);
			
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
