package com.winsafe.drp.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 
 * @Title: StockMoveImportService.java
 * @Description: 转仓Excel文件解析
 * @author: wenping
 * @CreateTime: Apr 24, 2012 1:50:26 PM
 * @version:
 */
public class StockMoveImportService {

	AppOrgan ao = new AppOrgan();
	AppWarehouse aw = new AppWarehouse();
	AppStockMove asm = new AppStockMove();

	AppStockMoveDetail asmd = new AppStockMoveDetail();
	AppProduct ap = new AppProduct();
	AppOlinkMan appOlinkMan = new AppOlinkMan();

	public StockMoveImportService() {

	}

	public String dealFile(String filepath, UsersBean users) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Workbook wb = null;
		Date now = new Date();
		String outwarehouseid = "00007";// 固定：
		StringBuilder errorMsg = new StringBuilder();
		int rowCount = 0;
		int successCount = 0;
		try {
			wb = Workbook.getWorkbook(new File(filepath));
			Sheet sheet = wb.getSheet(0);
			rowCount = sheet.getRows();

			StockMove sm = null;
			Warehouse warehouse = null;
			Product p = null;
			Organ organ = null;
			String nccode2;// 配送单号
			String nccode;// 外部单号
			String lastNccode = null;// 上一次的外部单号
			Date orderDate;// 订单日期
			String receiveWarehouseNCcode;// 客户编号
			String productNCcode;// 商品编号
			double quantity;// 实出数量
			String busNo;// 车号
			String busWay;// 线路
			int state = 0;

			for (int i = 1; i < rowCount; i++) {
				try {
					/*
					 * 初始化
					 */
					nccode2 = null;
					nccode = null;
					orderDate = null;
					receiveWarehouseNCcode = null;
					productNCcode = null;
					quantity = 0;
					busNo = null;
					busWay = null;

					/*
					 * 读取xls
					 */
					try {
						nccode2 = getCellValue(sheet, 0, i);// 配送单号
						nccode = getCellValue(sheet, 1, i);// 外部单号
						String date = getCellValue(sheet, 2, i);
						if (date.length() == 8) {
							orderDate = dateFormat2.parse(getCellValue(sheet, 2, i));// 订单日期
						} else {
							orderDate = dateFormat.parse(getCellValue(sheet, 2, i));// 订单日期
						}
						getCellValue(sheet, 3, i);// 要求送货日期
						receiveWarehouseNCcode = getCellValue(sheet, 4, i);// 客户编号
						getCellValue(sheet, 5, i);// 明细行号
						productNCcode = getCellValue(sheet, 6, i);// 商品编号
						getCellValue(sheet, 7, i);// 订单数量
						quantity = Double.parseDouble(getCellValue(sheet, 8, i));// 实出数量
						// 如果订单数量不为整箱
						if (quantity % 6 > 0) {
//							errorMsg.append("第" + i + "行 产品不为整箱出库<br>");
//							continue;
							quantity = (int)quantity / 6 + 1;
						} else {
							quantity = quantity / 6;
						}
						busNo = getCellValue(sheet, 9, i);// 车号
						busWay = getCellValue(sheet, 10, i);// 线路

						// 外部单号、商品编号不能等于空
						if (StringUtil.isEmpty(nccode) || StringUtil.isEmpty(productNCcode)) {
							throw new Exception();
						}

						/*
						 * if (!nccode.equals(lastNccode)) { // 外部单号不同的情况 try {
						 * warehouse = aw.findByNCcode(receiveWarehouseNCcode);
						 * } catch (NotExistException e) { errorMsg.append("第" +
						 * i + "行 发货客户不存在,客户编号：" + receiveWarehouseNCcode +
						 * "<br>"); continue; }
						 * 
						 * organ = ao.getOrganByID(warehouse.getMakeorganid());
						 * if (organ == null) { errorMsg.append("第" + i +
						 * "行 发货客户机构不存在" + warehouse.getMakeorganid() + "<br>");
						 * continue; }
						 * 
						 * } else if (state == 3) { // 外部单号相同的情况
						 * errorMsg.append("第" + i + "行 已复核不能修改<br>"); continue;
						 * }
						 */

						try {
							p = ap.getByNCcode(productNCcode);
						} catch (NotExistException e) {
							errorMsg.append("第" + i + "行 产品不存在" + productNCcode + "<br>");
							continue;
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorMsg.append("第" + i + "行 格式不正确<br>");
						continue;
					}

					if (!nccode.equals(lastNccode)) {
						// 与上次外部单号不同的情况
						lastNccode = nccode;
						
						try {
							warehouse = aw.findByNCcode(receiveWarehouseNCcode);
						} catch (NotExistException e) {
							errorMsg.append("第" + i + "行 发货客户不存在,客户编号：" + receiveWarehouseNCcode + "<br>");
							continue;
						}

						organ = ao.getOrganByID(warehouse.getMakeorganid());
						if (organ == null) {
							errorMsg.append("第" + i + "行 发货客户机构不存在" + warehouse.getMakeorganid() + "<br>");
							continue;
						}

						sm = AppStockMove.getStockMoveByNCcode(nccode);
						if (sm == null) {
							// 新增转仓单
							//state = 1;
							sm = new StockMove();
							sm.setNccode2(nccode2);// 配送单号
							sm.setNccode(nccode);// 外部单号

							String smid = MakeCode.getExcIDByRandomTableName("stock_move", 2, "SM");
							sm.setId(smid);
							sm.setMovedate(orderDate);// 订单日期
							sm.setOutwarehouseid(outwarehouseid);
							Olinkman olinkman = appOlinkMan.getMainLinkmanByCid(organ.getId());
							if (olinkman != null) {
								sm.setOlinkman(olinkman.getName());
								sm.setOtel(olinkman.getOfficetel());
							} else {
								sm.setOlinkman("");
								sm.setOtel("");
							}
							sm.setTransportmode(null);// 发运方式
							sm.setTransportaddr(warehouse.getWarehouseaddr());// 收货地址

							sm.setMakedate(now);
							if (users != null) {
								sm.setMakeid(users.getUserid());
								sm.setMakeorganid(users.getMakeorganid());
								sm.setMakedeptid(users.getMakedeptid());
								// sm.setOutwarehouseid(aw.getWarehouseByOID(users.getMakeorganid()).getId());
							}

							sm.setInwarehouseid(warehouse.getId());
							sm.setInorganid(organ.getId());
							sm.setMovecause(null);// 转仓原因
							sm.setRemark(null);// 备注
							sm.setIsaudit(0);
							sm.setIsshipment(0);
							sm.setIsblankout(0);
							sm.setIscomplete(0);
							sm.setBusNo(busNo);// 车号
							sm.setBusWay(busWay);// 线路

							StringBuffer keyscontent = new StringBuffer();
							keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",").append(sm.getOtel());
							sm.setTotalsum(0d);
							sm.setTakestatus(0);
							sm.setKeyscontent(keyscontent.toString());
							asm.addStockMove(sm);
						}
						else if (sm!=null && (sm.getIsaudit() == null || sm.getIsaudit() == 0)) {
							// 更新转仓单
							//state = 2;
							sm.setNccode2(nccode2);// 配送单号
							sm.setNccode(nccode);// 外部单号
							sm.setMovedate(orderDate);// 订单日期
							sm.setOutwarehouseid(outwarehouseid);
							Olinkman olinkman = appOlinkMan.getMainLinkmanByCid(organ.getId());
							if (olinkman != null) {
								sm.setOlinkman(olinkman.getName());
								sm.setOtel(olinkman.getOfficetel());
							}
							sm.setTransportaddr(warehouse.getWarehouseaddr());// 收货地址

							if (users != null) {
								sm.setMakeid(users.getUserid());
								sm.setMakeorganid(users.getMakeorganid());
								sm.setMakedeptid(users.getMakedeptid());
								sm.setMakedate(now);
							}
							sm.setInwarehouseid(warehouse.getId());
							sm.setInorganid(organ.getId());
							sm.setBusNo(busNo);// 车号
							sm.setBusWay(busWay);// 线路

							StringBuffer keyscontent = new StringBuffer();
							keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",").append(sm.getOtel());
							sm.setKeyscontent(keyscontent.toString());

							asm.updstockMove(sm);
						} else {
							// state = 3;
							errorMsg.append("第" + i + "行 已复核不能修改<br>");
							continue;
						}

					}

					// 新增转仓单明细
					addSmd(state, sm, p, quantity);
					HibernateUtil.commitTransaction();

					successCount++;
				} catch (Exception e) {
					e.printStackTrace();
					errorMsg.append("第" + i + "行 导入失败<br>");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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

	@SuppressWarnings("unchecked")
	private void addSmd(int state, StockMove sm, Product p, double quantity) throws NumberFormatException, Exception {
		StockMoveDetail smd = null;
//		if (state == 1) {
//			// 新增
//			addSmd(sm, p, quantity);
//		} else if (state == 2) {
			List<StockMoveDetail> list = asmd.getStockMoveDetailByPiidPid(sm.getId(), p.getId());
			if (list.size() == 0) {
				// 新增
				addSmd(sm, p, quantity);
			} else {
				// 更新
				smd = list.get(0);
				smd.setProductid(p.getId());
				smd.setProductname(p.getProductname());
				smd.setSpecmode(p.getSpecmode());

				smd.setUnitid(2);// 固定：箱
				// 2011-12-13 richie.yu 修改单据同步单位默认为罐
				// smd.setUnitid(7);
				smd.setBatch("");
				smd.setQuantity(quantity);
				smd.setTakequantity(0d);

				asmd.updstockMove(smd);
			}
//		}
	}

	private void addSmd(StockMove sm, Product p, double quantity) throws NumberFormatException, Exception {
		StockMoveDetail smd = new StockMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_move_detail", 0, "")));
		smd.setSmid(sm.getId());
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());

		smd.setUnitid(2);// 固定：箱
		// 2011-12-13 richie.yu 修改单据同步单位默认为罐
		// smd.setUnitid(7);
		smd.setBatch("");
		smd.setQuantity(quantity);
		smd.setTakequantity(0d);
		asmd.addStockMoveDetail(smd);
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
