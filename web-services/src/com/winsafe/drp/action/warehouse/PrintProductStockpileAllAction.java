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
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintProductStockpileAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> param = new HashMap<>(); 
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String keyword = request.getParameter("KeyWord");
		String MakeOrganID = request.getParameter("MakeOrganID");
		WarehouseService aw = new WarehouseService();
		super.initdata(request);try{
			String strCondition = " ps.warehouseid=wv.wid and wv.userid=:userid and (abs(ps.stockpile)+abs(ps.prepareout))<>0 and p.id=ps.productid ";
			param.put("userid", userid);
			
			if ( MakeOrganID != null && !"".equals(MakeOrganID) ){
		    	 String ss = getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
		    	 strCondition += " and ps.warehouseid in("+ss+") ";
		      }
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpileAll", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);
			String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "p.id", "ProductName",
					"PYCode", "SpecMode");
			String leftblur = DbUtil.getBlurLeftForHql(map, tmpMap, param, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppFUnit af = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();
			List pls = aps.getStockStat(whereSql);
			List alp = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Object[] o = (Object[]) pls.get(i);
				ProductStockpileAll ps = (ProductStockpileAll) o[0];
				Product p = (Product) o[1];
				psf.setId(ps.getId());
				psf.setProductid(ps.getProductid());
				psf.setNccode(p.getNccode());
				psf.setPsproductname(p.getProductname());
				psf.setPsspecmode(p.getSpecmode());
				psf.setBatch(ps.getBatch());
				psf.setCountunit(p.getSunit());
				psf.setPrepareout(af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getPrepareout()));
			    psf.setStockpile(af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getStockpile()));
			    psf.setAllstockpile(af.getStockpileQuantity2(ps.getProductid(), p.getSunit(), ps.getStockpile()+ps.getPrepareout()));
				psf.setWarehouseid(ps.getWarehouseid());
				psf.setWarehourseidname(aw
						.getWarehouseName(ps.getWarehouseid()));
				alp.add(psf);
			}

			

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
	
	private String getWarehouseId(List wlist) {
		  StringBuffer sb = new StringBuffer();	  
		  for ( int i=0; i<wlist.size(); i++ ){
			  Warehouse w = (Warehouse)wlist.get(i);
			  if ( i==0 ){
				  sb.append("'").append(w.getId()).append("'");
			  }else{
				  sb.append(",'").append(w.getId()).append("'");
			  }		  
		  }
		  return sb.toString();
	  }
}
