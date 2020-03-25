package com.winsafe.drp.action.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.WarehouseProdcutStockpileAll;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
 * 库存保质期预警报表导出
 * @Title: ExportProtectDateByProductStockpileAllAction.java
 * @author: WEILI	
 * @version:
 */
public class ExportProtectDateByProductStockpileAllAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream os = null;
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		AppProduct ap = new AppProduct();
		AppWarehouse aw = new AppWarehouse();
		AppProductStruct aps = new AppProductStruct();
		AppProductStockpile apsa = new AppProductStockpile();
		AppFUnit af  = new AppFUnit();
		List<WarehouseProdcutStockpileAll> wpsaList = new ArrayList<WarehouseProdcutStockpileAll>();
		//获取页面检索条件中的参数
		String productID = request.getParameter("ProductID");
		String outOrganID = request.getParameter("MakeOrganID");
		String keyWordLeft = request.getParameter("KeyWordLeft");
		String outWarehouseID = request.getParameter("WarehouseID");
		
		String beginDate = request.getParameter("BeginDate");
		String endDate = request.getParameter("EndDate");
		
		String isFZfunitid = request.getParameter("isFZfunitid");
		
		map = request.getParameterMap();
		tmpMap = EntityManager.scatterMap(map);
		String blur = DbUtil.getOrBlur(map, tmpMap, "w.warehousename", "w.id");
		
		String whereSql = " where 1=1 and ";
		whereSql += blur;
		if(null != outOrganID && !"".equals(outOrganID)){
			whereSql += " w.makeorganid = '"+outOrganID+"' and ";
		}else{
			whereSql += " w.makeorganid = '"+users.getMakeorganid()+"' and ";
		}
		if(null != outWarehouseID && !"".equals(outWarehouseID)){
			whereSql += " w.id = '"+outWarehouseID+"' and";
		}
		whereSql = DbUtil.getWhereSql(whereSql); 
		//根据当前用户的所属机构查询所有仓库
		List<Warehouse> warehouseList = aw.getWarehouseByNoPage(whereSql);
		
		String warehouseString = getWarehouseIDBYList(warehouseList);
		
		WarehouseProdcutStockpileAll wpsa = null;
		//获取产品预警期
		BaseResource br1 = new AppBaseResource().getBaseResourceValue("ProductAlermDate", 0);
		BaseResource br2 = new AppBaseResource().getBaseResourceValue("ProductAlermDate", 1);
		
		String pWhereSql = " where ps.productid = p.id and ps.warehouseid in ("+warehouseString+") ";
		if(null != keyWordLeft && !"".equals(keyWordLeft)){
			pWhereSql += " and p.psid = '"+keyWordLeft+"' ";
		}
		if(null != productID && !"".equals(productID)){
			pWhereSql += " and ps.productid = '"+productID+"' ";
		}
		
		//根据仓库ID查询所有库存量
		List<ProductStockpile> psList = apsa.getProductStockpileAllListByWIDNoPage(pWhereSql);
		for (ProductStockpile ps : psList) {
			wpsa = new WarehouseProdcutStockpileAll();
			Warehouse warehouse = aw.getWarehouseByID(ps.getWarehouseid());
			wpsa.setWarehouseId(warehouse.getId());
			wpsa.setWarehouseName(warehouse.getWarehousename());
			Product p = ap.getProductByID(ps.getProductid());
			if(null != p){
				wpsa.setProductId(p.getId());
				wpsa.setProductNccode(p.getNccode());
				wpsa.setProductName(p.getProductname());
				wpsa.setProductSpecmode(p.getSpecmode());
				wpsa.setProductProtectDate(p.getProtectDate());
				ProductStruct pst = aps.getProductStructById(p.getPsid());
				if(null != pst){
					wpsa.setProductStruct(pst.getSortname());
				}else{
					wpsa.setProductStruct("");
				}
			}else{
				wpsa.setProductNccode("");
				wpsa.setProductName("");
				wpsa.setProductSpecmode("");
			}
			wpsa.setPsid(ps.getId());
			wpsa.setRemark(ps.getRemark());
			wpsa.setProduceDate(ps.getProducedate());
			wpsa.setStockpile(ps.getStockpile() + ps.getPrepareout());
			wpsa.setUnitId(ps.getCountunit());
			//获取产品预警期
			BaseResource brByUnit = new AppBaseResource().getBaseResourceValue("CountUnit", wpsa.getUnitId());
			wpsa.setUnitidName(brByUnit.getTagsubvalue());
			//如果生产日期为空
			if(null == wpsa.getProduceDate() || "".equals(wpsa.getProduceDate())){
				wpsa.setDaoqiDate("");
				wpsa.setDaoqiDateByDay("");
				wpsa.setStockpilestruts(0);
			}else{
				wpsa.setDaoqiDate(DateUtil.formatDateByAdd(wpsa.getProduceDate(), 
						Integer.valueOf(wpsa.getProductProtectDate())));
				wpsa.setDaoqiDateByDay(String.valueOf(DateUtil.formatDateByDiffByTwoStringDate(
						DateUtil.getCurrentDateString(), wpsa.getDaoqiDate())));
				/**
				 * 判断库存状态
				 */
				//如果产品预警期-到期天数大于等于0
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) >= Integer.valueOf(br2.getTagsubvalue())){
					wpsa.setStockpilestruts(2);
				}
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) < Integer.valueOf(br1.getTagsubvalue())){
					wpsa.setStockpilestruts(0);
				}
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) > Integer.valueOf(br1.getTagsubvalue()) &&
						Integer.valueOf(wpsa.getDaoqiDateByDay()) < Integer.valueOf(br2.getTagsubvalue())){
					wpsa.setStockpilestruts(1);
				}
			}
			if("1".equals(isFZfunitid)){
				//箱到KG转换数
				double xtsQuantity = af.getXQuantity(wpsa.getProductId(), 2);
    			//散到KG转换数
    			double stsQuantity = af.getXQuantity(wpsa.getProductId(), p.getScatterunitid());
    			//得到整箱数
    			double b = ((Double)ArithDouble.div(wpsa.getStockpile(), xtsQuantity));
				//得到散数
				double tqu = ArithDouble.sub(wpsa.getStockpile(), ArithDouble.mul(xtsQuantity, (double)b));
				tqu = (Double)ArithDouble.div(tqu, stsQuantity);
				wpsa.setFuzhuBoxStockpile(b);
				wpsa.setFuzhuEAStockpile(tqu);
			}
			wpsaList.add(wpsa);
		}
		//获取检索条件中的库存状态并判断集合中的数据
		List<WarehouseProdcutStockpileAll> wList = new ArrayList<WarehouseProdcutStockpileAll>();
		String stockpilestruts = request.getParameter("sstruts");
		if(null != stockpilestruts && !"".equals(stockpilestruts)){
			if(!"-1".equals(stockpilestruts)){
				for (WarehouseProdcutStockpileAll wpsall : wpsaList) {
					//如果页面传值的库存状态与集合中的数据相等
					if(stockpilestruts.equals(String.valueOf(wpsall.getStockpilestruts()))){
						wList.add(wpsall);
					}
				}
			}else{
				wList = wpsaList;
			}
		}else{
			wList = wpsaList;
		}
		/*List<WarehouseProdcutStockpileAll> wpsList = new ArrayList<WarehouseProdcutStockpileAll>();
		if(null != beginDate && !"".equals(beginDate) && null != endDate && !"".equals(endDate)){
			for (WarehouseProdcutStockpileAll wpsall : wList) {
				//如果页面传值的产品类别与集合中的数据相等
				if(wpsall.getDaoqiDate().compareTo(beginDate) > 0 && 
						wpsall.getDaoqiDate().compareTo(endDate) < 0){
					wpsList.add(wpsall);
				}
			}
		}else{
			wpsList = wList;
		}*/

		if (wList.size() > Constants.EXECL_MAXCOUNT) {
			request.setAttribute("result", "当前记录数超过" + Constants.EXECL_MAXCOUNT + "条，请重新查询！");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition", "attachment; filename=ProtectDateByProductStockAll.xls");
		response.setContentType("application/msexcel");
		writeXls(wList, isFZfunitid, os, request);
		os.flush();
		return null;
	}
	
	public void writeXls(List<WarehouseProdcutStockpileAll> ttlist, String isFZfunitid, OutputStream os, HttpServletRequest request) throws NumberFormatException, Exception {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		OrganService organ = new OrganService();
		WarehouseService ws = new WarehouseService();
		int snum = 1;
		snum = ttlist.size() / 50000;
		if (ttlist.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];

		try {
			for (int j = 0; j < snum; j++) {
				sheets[j] = workbook.createSheet("sheet" + j, j);
				int currentnum = (j + 1) * 50000;
				if (currentnum >= ttlist.size()) {
					currentnum = ttlist.size();
				}
				int start = j * 50000;

				sheets[j].mergeCells(0, start, 12, start);
				sheets[j].addCell(new Label(0, start, "库存保质期预警报表", wchT));
				sheets[j].addCell(new Label(0, start + 1, "发货机构:", seachT));
				sheets[j].addCell(new Label(1, start + 1, ""));
				sheets[j].addCell(new Label(2, start + 1, "发货仓库:", seachT));
				sheets[j].addCell(new Label(3, start + 1, ""));
				sheets[j].addCell(new Label(4, start + 1, "关键字:", seachT));
				sheets[j].addCell(new Label(5, start + 1, ""));

				sheets[j].addCell(new Label(0, start + 3, "导出机构:", seachT));
				sheets[j].addCell(new Label(1, start + 3, request.getAttribute("porganname").toString()));
				sheets[j].addCell(new Label(2, start + 3, "导出人:", seachT));
				sheets[j].addCell(new Label(3, start + 3, request.getAttribute("pusername").toString()));
				sheets[j].addCell(new Label(4, start + 3, "导出时间:", seachT));
				sheets[j].addCell(new Label(5, start + 3, DateUtil.getCurrentDateTime()));

				sheets[j].addCell(new Label(0, start + 4, "仓库编号", wcfFC));
				sheets[j].addCell(new Label(1, start + 4, "仓库名称", wcfFC));
				sheets[j].addCell(new Label(2, start + 4, "产品类别", wcfFC));
				sheets[j].addCell(new Label(3, start + 4, "产品内部编号", wcfFC));
				sheets[j].addCell(new Label(4, start + 4, "产品名称", wcfFC));
				sheets[j].addCell(new Label(5, start + 4, "产品规格", wcfFC));
				sheets[j].addCell(new Label(6, start + 4, "生产日期", wcfFC));
				sheets[j].addCell(new Label(7, start + 4, "仓位", wcfFC));
				sheets[j].addCell(new Label(8, start + 4, "基本单位", wcfFC));
				sheets[j].addCell(new Label(9, start + 4, "实际库存", wcfFC));
				
				if("1".equals(isFZfunitid)){
					sheets[j].addCell(new Label(10, start + 4, "辅助单位(箱)", wcfFC));
					sheets[j].addCell(new Label(11, start + 4, "辅助单位(EA)", wcfFC));
					sheets[j].addCell(new Label(12, start + 4, "保质期(天)", wcfFC));
					sheets[j].addCell(new Label(13, start + 4, "到期日期", wcfFC));
					sheets[j].addCell(new Label(14, start + 4, "到/期天数", wcfFC));
					sheets[j].addCell(new Label(15, start + 4, "库龄状态", wcfFC));
					sheets[j].addCell(new Label(16, start + 4, "备注", wcfFC));
				}else{
					sheets[j].addCell(new Label(10, start + 4, "保质期(天)", wcfFC));
					sheets[j].addCell(new Label(11, start + 4, "到期日期", wcfFC));
					sheets[j].addCell(new Label(12, start + 4, "到/期天数", wcfFC));
					sheets[j].addCell(new Label(13, start + 4, "库龄状态", wcfFC));
					sheets[j].addCell(new Label(14, start + 4, "备注", wcfFC));
				}
				
				int row = 0;
				for (int i = start; i < currentnum; i++) {
					row = i - start + 5;
					WarehouseProdcutStockpileAll wpsa = ttlist.get(i);
					sheets[j].addCell(new Label(0, row, (String) wpsa.getWarehouseId()));
					sheets[j].addCell(new Label(1, row, (String) wpsa.getWarehouseName()));
					sheets[j].addCell(new Label(2, row, wpsa.getProductStruct()));
					sheets[j].addCell(new Label(3, row, wpsa.getProductNccode()));
					sheets[j].addCell(new Label(4, row, (String) wpsa.getProductName()));
					sheets[j].addCell(new Label(5, row, (String) wpsa.getProductSpecmode()));
					sheets[j].addCell(new Label(6, row, (String) wpsa.getProduceDate()));
					sheets[j].addCell(new Label(7, row, ""));
					sheets[j].addCell(new Label(8, row, wpsa.getUnitidName()));
					sheets[j].addCell(new Label(9, row, String.valueOf(wpsa.getStockpile())));
					
					if("1".equals(isFZfunitid)){
						sheets[j].addCell(new Label(10, row, String.valueOf(wpsa.getFuzhuBoxStockpile())));
						sheets[j].addCell(new Label(11, row, String.valueOf(wpsa.getFuzhuEAStockpile())));
						sheets[j].addCell(new Label(12, row, (String) wpsa.getProductProtectDate()));
						sheets[j].addCell(new Label(13, row, (String) wpsa.getDaoqiDate()));
						sheets[j].addCell(new Label(14, row, wpsa.getDaoqiDateByDay()));
						String status = "";
						if(wpsa.getStockpilestruts() == 0){
							status = "安全";
						}
						if(wpsa.getStockpilestruts() == 1){
							status = "预警";
						}
						if(wpsa.getStockpilestruts() == 2){
							status = "过期";
						}
						sheets[j].addCell(new Label(15, row, status));
						sheets[j].addCell(new Label(16, row, (String) wpsa.getRemark()));
					}else{
						sheets[j].addCell(new Label(10, row, (String) wpsa.getProductProtectDate()));
						sheets[j].addCell(new Label(11, row, (String) wpsa.getDaoqiDate()));
						sheets[j].addCell(new Label(12, row, wpsa.getDaoqiDateByDay()));
						String status = "";
						if(wpsa.getStockpilestruts() == 0){
							status = "安全";
						}
						if(wpsa.getStockpilestruts() == 1){
							status = "预警";
						}
						if(wpsa.getStockpilestruts() == 2){
							status = "过期";
						}
						sheets[j].addCell(new Label(13, row, status));
						sheets[j].addCell(new Label(14, row, (String) wpsa.getRemark()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
			if (os != null) {
				os.close();
			}
		}

	}
	
	public String getWarehouseIDBYList(List<Warehouse> warehouseList){
		StringBuffer sbtake = new StringBuffer();
		if(warehouseList.size() == 1){
			sbtake.append("'");
			sbtake.append(warehouseList.get(0).getId());
			sbtake.append("'");
		}else if(warehouseList.size() > 1){
			for (int i = 0; i < warehouseList.size(); i++) {
				if(i != warehouseList.size()-1){
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
					sbtake.append(",");
				}else{
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
				}
			}
		}else{
			//暂无逻辑
		}
		return sbtake.toString();
	}
}
