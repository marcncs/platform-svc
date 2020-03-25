package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.PrintJob;

public class ListProductStockpileAllAction extends BaseAction {
	
	private AppStockPileAgeing aspa = new AppStockPileAgeing();
	private AppFUnit af = new AppFUnit();
	private AppProductStockpileAll aps = new AppProductStockpileAll();
	private WarehouseService aw = new WarehouseService();
	private AppPrintJob apj = new AppPrintJob();
	
	

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int pagesize = 15; 
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		try {
			String organid = request.getParameter("MakeOrganID");
			String wnameString = request.getParameter("wname");
			String keyword = request.getParameter("KeyWord");
			
			String strCondition = " ps.stockpile <> 0";
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductStockpileAll", "WarehouseVisit",
					"Product" };
			String whereSql = EntityManager.getTmpWhereSql2ForSql(map, tablename, param);
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "p.id", "p.ProductName",
					"p.PYCode", "p.SpecMode", "p.nccode","ps.Batch");
			if (!com.winsafe.hbm.util.StringUtil.isEmpty(blur)) {
				blur = blur.substring(0,blur.indexOf(")"));
				blur = blur +" or p.mCode like ? ) and ";
				param.put("p.mCode", "%"+keyword+"%");
			}
			String leftblur = DbUtil.getBlurLeftForSql(map, tmpMap, "PSID", param);
			// 权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition += " and ps.warehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?) ";
				param.put("wv.user_Id", userid);
			} else {
				Condition += " and "+DbUtil.getWhereCondition(users, "o");
			}
			String organSql = "";
			if (!StringUtil.isEmpty(organid) && StringUtil.isEmpty(wnameString)) {
				organSql = " and  w.makeorganid=? ";
				param.put("w.makeorganid", organid);
			}
			whereSql = whereSql + blur + leftblur + strCondition + Condition+organSql;
			whereSql = DbUtil.getWhereSql(whereSql);

//			List pls = aps.getStockStat(request, pagesize, whereSql, "");
			List<Map<String,String>> pls = aps.getStockStatList(request, pagesize, whereSql, users, param);
			// 所有的库龄集合
			List<StockPileAgeing> spa = aspa.getStockPileAgeing();
			
			List alp = new ArrayList();
			for (int i = 0; i < pls.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				PrintJob pj = new PrintJob();
				Map<String,String> psMap = pls.get(i); 
				
				MapUtil.mapToObject(psMap, psf); 
				psf.setId(Long.valueOf(psMap.get("id"))); 
				psf.setCountunit(Constants.DEFAULT_UNIT_ID);
				Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
				if (xquantity != 0) {
					psf.setAllstockpile((psf.getStockpile() + psf.getPrepareout())/xquantity);
				}else {
					psf.setAllstockpile(0.0);
				}
				//库龄
				pj = apj.getPrintJobByBatAMc(psf.getNccode(), psf.getBatch());
				if (pj != null) {
					psf.setExpiryDate(pj.getExpiryDate());
					psf.setProductionDate(pj.getProductionDate());
				}else {
					psf.setExpiryDate(null);
					psf.setProductionDate(null);
				}
				String color = getColorByBatch(getStockPileAgeing(DateUtil.formatDate(psf.getMakedate())), spa);
				//颜色
				psf.setTagColor(color);
				alp.add(psf);
			} 

			double totalQuanity = 0;
			double totalBoxQuantity = 0;
			double totalScatterQuantity = 0;
			// 统计总数 
//			for (int i = 0; i < pls.size(); i++) {
//				Object[] o = (Object[]) pls.get(i);
//				ProductStockpileAll ps = (ProductStockpileAll) o[0];
//				totalQuanity = ArithDouble.add(totalQuanity, ArithDouble.add(ps
//						.getStockpile(), ps.getPrepareout()));
//			}
			// 改版统计总数
			for (int i = 0; i < alp.size(); i++) {
//				Object[] o = (Object[]) pls.get(i);
				ProductStockpileForm ps = (ProductStockpileForm) alp.get(i);
				Double xquantityf = af.getXQuantity(ps.getProductid(), ps.getCountunit());
				if (xquantityf != 0 ) {
					totalQuanity = ArithDouble.add(totalQuanity, ArithDouble.div(ArithDouble.add(ps
							.getStockpile(), ps.getPrepareout()),xquantityf));
				}else {
					totalQuanity = ArithDouble.add(totalQuanity, 0.0);
				}
			}
			
			List wls = aw.getEnableWarehouseByVisit(userid);
			String brandselect = Internation.getSelectTagByKeyAllDB("Brand",
					"Brand", true);
			request.setAttribute("brandselect", brandselect);
			request.setAttribute("alw", wls);
			request.setAttribute("alp", alp);
			request.setAttribute("totalQuanity", totalQuanity);
			request.setAttribute("totalBoxQuantity", totalBoxQuantity);
			request.setAttribute("totalScatterQuantity", totalScatterQuantity);
			DBUserLog.addUserLog(request, "列表");

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private int getStockPileAgeing(String batchDate) {
		String minYMD = batchDate;
		String maxYMD = DateUtil.getCurrentDateString();
		return DateUtil.mulDate2Date(minYMD, maxYMD);
	}
	
	private String getColorByBatch(int day, List<StockPileAgeing> spas) {

		for (StockPileAgeing spa : spas) {
			if (spa.getTagMinValue() <= day && spa.getTagMaxValue() >= day) {
				return spa.getTagColor();
			}
		}
		return null;

	}

}
