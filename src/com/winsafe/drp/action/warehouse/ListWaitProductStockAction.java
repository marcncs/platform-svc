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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListWaitProductStockAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Double> boxUnitMap = new HashMap<String, Double>();
		Map<String, Double> scatterUnitMap = new HashMap<String, Double>();

		AppTakeTicket att = new AppTakeTicket();

		Map<String, Product> pMap = new HashMap<String, Product>();
		AppProduct ap = new AppProduct();

		String isShowAssistQuantity = request.getParameter("isShowAssistQuantity");
		// String isShowStatus= request.getParameter("isShowStatus");
		
		  String orderSql = request.getParameter("orderbySql");
		  String orderSqlName = request.getParameter("orderbySqlShowName");

		int pagesize = 20;
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String keyword = request.getParameter("KeyWord");
		String MakeOrganID = request.getParameter("MakeOrganID");
		WarehouseService aw = new WarehouseService();
		super.initdata(request);
		try {
			// String strCondition =" p.id=ps.productid ";
			String strCondition = "  tt.warehouseid = wv.wid and wv.userid=" + userid + "";
			// " ps.warehouseid=wv.wid and wv.userid="+userid+" and (ps.stockpile+ps.prepareout)!=0 and p.id=ps.productid ";//
			// and (ps.stockpile!=0 or ps.prepareout>0) ";
			if (MakeOrganID != null && !"".equals(MakeOrganID)) {
				String ss = getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
				strCondition += " and tt.warehouseid in(" + ss + ") ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpileAll", "WarehouseVisit", "Product" };
			// String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String whereSql = " where ";
			if (!StringUtil.isEmpty(request.getParameter("ProductID"))) {
				whereSql += "  ttb.productid ='" + request.getParameter("ProductID") + "' and ";
			}
			if (!StringUtil.isEmpty(request.getParameter("WarehouseID"))) {
				whereSql += "  tt.warehouseid ='" + request.getParameter("WarehouseID") + "' and ";
			}
			// String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName", "PYCode", "SpecMode", "p.nccode");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppFUnit af = new AppFUnit();
			AppProductStockpileAll aps = new AppProductStockpileAll();

			// //得到有预出库的仓库集合pls，按产品汇总后，不分批次
			// List pls = aps.getStockStatNoBatch(request, pagesize, whereSql);
			//      

			// //得到所有
			// List all = aps.getStockStat(whereSql);

			List alp = new ArrayList();

			// for(int i=0;i<pls.size();i++){
			// Map p = (Map) pls.get(i);

			// psf.setCountunit(Integer.parseInt(p.get("countunit").toString()));

			// List ttbList =
			// att.getWaitProductStock(p.get("warehouseid").toString(),p.get("productid").toString());
			List ttbList = att.getWaitProductStock(request, pagesize, whereSql,orderSql);

			for (int j = 0; j < ttbList.size(); j++) {
				Map p = (Map) ttbList.get(j);

				ProductStockpileForm psf = new ProductStockpileForm();

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(p.get("warehousename").toString());
				psf.setSortName(p.get("sortname").toString());
				psf.setNccode(p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());

				Double quantity = Double.valueOf(p.get("quantity").toString());
				Double boxQuantity = Double.valueOf(p.get("boxquantity").toString());
				Double scatterQuantity = Double.valueOf(p.get("scatterquantity").toString());

				// 每小包装kg数
				Double scaq = scatterUnitMap.get(p.get("productid").toString());
				if (scaq == null) {

					Product product = pMap.get(p.get("productid").toString());
					if (product == null) {
						product = ap.getProductByID(p.get("productid").toString());
						if (product == null) {
							// 说明产品不存在
							product = new Product();
						}
						pMap.put(p.get("productid").toString(), product);
					}

					scaq = af.getXQuantity(p.get("productid").toString(), product.getScatterunitid());
					scatterUnitMap.put(p.get("productid").toString(), scaq);
				}

				// 预定出库量
				psf.setPrepareout(ArithDouble.mul(quantity, scaq));
				psf.setXnum(boxQuantity);
				psf.setScatterNum(scatterQuantity);
				// 对应单据日期
				psf.setDate(p.get("makedate").toString().substring(0, 10));
				// 对应单据号
				psf.setBillno(p.get("ttid").toString());

				alp.add(psf);

			}
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
	      	
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);

			// }

			// long totalQuanity = 0;
			// long totalBoxQuantity = 0;
			// long totalScatterQuantity = 0;
			// //统计总数
			// for(int i=0;i<all.size();i++){
			//    	  
			// Object[] o = (Object[])all.get(i);
			// ProductStockpileAll ps = (ProductStockpileAll)o[0];
			// Product p = (Product)o[1];
			//          
			// totalQuanity+=ps.getStockpile()+ps.getPrepareout();
			//          
			//          
			// //是否显示辅助数量
			// if(isShowAssistQuantity!=null){
			// //每箱多少kg
			// Double Xquantity = boxUnitMap.get(ps.getProductid());
			// if(Xquantity == null){
			// Xquantity = af.getXQuantity(ps.getProductid(), 2);
			// boxUnitMap.put(ps.getProductid(), Xquantity);
			// }
			//              
			// //每小包装kg数
			// Double scaq = scatterUnitMap.get(ps.getProductid());
			// if(scaq == null){
			// scaq = af.getXQuantity(ps.getProductid(), p.getScatterunitid());
			// scatterUnitMap.put(ps.getProductid(), scaq);
			// }
			//          	
			// Double bn = ArithDouble.div(ArithDouble.add(ps.getStockpile(),
			// ps.getPrepareout()), Xquantity);
			// //计算总箱数
			// totalBoxQuantity+= bn;
			//          	
			// //计算总EA数
			// Double other = ArithDouble.sub(ArithDouble.add(ps.getStockpile(),
			// ps.getPrepareout()), ArithDouble.mul(Xquantity, bn));
			// totalScatterQuantity+=ArithDouble.div(other, scaq);
			//          	
			// }
			// }
			//
			//      
			//
			// List wls = aw.getEnableWarehouseByVisit(userid);

			String brandselect = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
			request.setAttribute("brandselect", brandselect);

			// request.setAttribute("isShowStatus", isShowStatus);
			request.setAttribute("isShowAssistQuantity", isShowAssistQuantity);
			// request.setAttribute("alw",wls);
			request.setAttribute("keyword", keyword);
			request.setAttribute("alp", alp);

			// request.setAttribute("totalQuanity", totalQuanity);
			// request.setAttribute("totalBoxQuantity", totalBoxQuantity);
			// request.setAttribute("totalScatterQuantity",
			// totalScatterQuantity);
			// System.out.println(totalQuanity + "--" + totalBoxQuantity + "--"
			// + totalScatterQuantity);

			DBUserLog.addUserLog(userid, 7, "列表待客库存统计");

			request.setAttribute("KeyWordLeft", request.getParameter("KeyWordLeft"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("Batch", request.getParameter("Batch"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("warehouseid", "仓库编号");
		  orderColumnMap.put("makedate", "单据日期");
		  orderColumnMap.put("nccode", "产品内部编号");
	}
}
