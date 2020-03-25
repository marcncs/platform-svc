package com.winsafe.drp.server;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 库存导入文件处理
* @Title: ProductStockPileImportService.java
* @author: wenping 
* @CreateTime: Jul 3, 2012 10:54:21 AM
* @version:
 */
public class ProductStockPileImportService {
	private Logger logger = Logger.getLogger(ProductStockPileImportService.class);

	AppWarehouse aw = new AppWarehouse();
	AppProduct ap = new AppProduct();
	AppProductStockpile aps = new AppProductStockpile();
//	AppProductStockpileAll apsa = new AppProductStockpileAll();

	public ProductStockPileImportService() {
	}

	public String dealFile(String filepath) throws Exception {

		Workbook wb = null;
		StringBuilder errorMsg = new StringBuilder();
		int rowCount = 0;
		int successCount = 0;
		try {
			wb = Workbook.getWorkbook(new File(filepath));
			Sheet sheet = wb.getSheet(0);
			rowCount = sheet.getRows();

//			ProductStockpileAll psa = null;
			ProductStockpile ps =null;
			String  warehouseID = null;
			String nccode = null;
			String  productID = null;
			String batch = null;
			Double stockpile =0.0;
			Double prepareout =0.0;
			int unitid;
			Warehouse w =null;
			Product p = null;
			

			for (int i = 1; i < rowCount; i++) {
				try {

					 //读取xls
					try {
						warehouseID = getCellValue(sheet, 0, i);// 仓库ID 
						nccode = getCellValue(sheet, 1, i);// 产品内部编号
						batch = getCellValue(sheet, 2, i);//批次
						stockpile = Double.parseDouble(getCellValue(sheet, 3, i));//可用库存
						prepareout = Double.parseDouble(getCellValue(sheet, 4, i));// 预出库
						unitid= Integer.parseInt(getCellValue(sheet, 5, i));//导入数量的单位
						
						w= aw.getWarehouseByID(warehouseID);
						if(w==null){
							errorMsg.append("第" + i + "行 仓库不存在<br>");
							continue;
						}
						
						List<Product> pList = ap.getProductByNccode(nccode);
						if(pList.size()==0){
							errorMsg.append("第" + i + "行 产品不存在<br>");
							continue;
						}else if(pList.size()>=2){
							errorMsg.append("第" + i + "行 产品内部编号重复<br>");
							continue;
						}
						p = pList.get(0);
						productID = p.getId();
//						if(p==null){
//							errorMsg.append("第" + i + "行 产品不存在<br>");
//							continue;
//						}
					} catch (Exception e) {
						e.printStackTrace();
						errorMsg.append("第" + i + "行 数据格式不正确<br>");
						continue;
					}
					
					ps = aps.getProductStockpileByPidWid(productID, warehouseID, "000", batch);
					if(ps!=null&&(ps.getStockpile()!=0||ps.getPrepareout()!=0)){
						errorMsg.append("第" + i + "行 该批次的同种产品已存在当前仓库中<br>");
						continue;
					}else{
						//新增库存记录
						ps=new ProductStockpile();
//						psa =new ProductStockpileAll();
						
						ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
						ps.setProductid(productID);
						ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
						ps.setBatch(batch);
						ps.setProducedate("");
						ps.setVad("");
						ps.setWarehouseid(warehouseID);
						ps.setWarehousebit("000");
						ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
						//新增库存记录
						aps.addProductByPurchaseIncome2(ps);						
						//更新库存量
						String Info = aps.inProductStockpileTotalQuantityImport(ps.getProductid(),unitid,ps.getBatch(), stockpile,prepareout, ps.getWarehouseid(), ps.getWarehousebit(), "", "库存文件导入");
						if(Info!=null){
							errorMsg.append("第" + i + "行 " +Info+"<br>");
							continue;
						}
						//更新库存中产品的成本
						ProductCostService.updateCost(warehouseID, ps.getProductid(), ps.getBatch());
					}

					HibernateUtil.commitTransaction();

					successCount++;
				} catch (Exception e) {
					logger.error("库存导入", e);
					errorMsg.append("第" + i + "行 导入失败<br>");
				}
			}

		} catch (Exception e) {
			logger.error("库存导入", e);
			if (successCount > 0) {
				errorMsg.append("文件读取错误<br>");
				String t = "总数量：" + (rowCount - 1) + "，成功数量：" + successCount + "，失败数量：" + (rowCount - successCount - 1) + "<br>";
				return t + "<font color='red'>" + errorMsg.toString() + "</font>";
			}
			errorMsg.append("文件读取错误<br>");
			return "<font color='red'>" + errorMsg.toString() + "</font>";
		} finally {
			if (wb != null) {
				// 关闭xls文件
				wb.close();
			}
		}
		String t = "总数量：" + (rowCount - 1) + "，成功数量：" + successCount + "，失败数量：" + (rowCount - successCount - 1) + "<br>";
		return t + "<font color='red'>" + errorMsg.toString() + "</font>";
	}

	private String getCellValue(Sheet sheet, int col, int row) {
		Cell cell = sheet.getCell(col, row);
		if (cell != null) {
			String value = cell.getContents();
			if (!StringUtil.isEmpty(value)) {
				return value.trim();
			}
		}
		return null;
	}
}
