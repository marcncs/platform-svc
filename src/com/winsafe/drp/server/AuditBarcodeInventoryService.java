package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryDAll;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.AppBarcodeInventoryIdcode;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventoryDAll;
import com.winsafe.drp.dao.BarcodeInventoryDetail;
import com.winsafe.drp.dao.BarcodeInventoryIdcode;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditBarcodeInventoryService {
	
	private AppProductStockpile appps = new AppProductStockpile();
	private AppProductStockpileAll apppsa = new AppProductStockpileAll();
	private Logger logger = Logger.getLogger(AuditBarcodeInventoryService.class);
	private AppOtherShipmentBillAll apos = new AppOtherShipmentBillAll();
	private AppOtherIncomeAll aoi = new AppOtherIncomeAll();
	private AppOtherShipmentBillDAll apobd = new AppOtherShipmentBillDAll();
	private AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
	private AppBarcodeInventoryDAll abida = new AppBarcodeInventoryDAll();
	private AppProduct appProduct = new AppProduct();
	private AppBarcodeInventoryIdcode abii = new AppBarcodeInventoryIdcode();
	private AppIdcode ai = new AppIdcode();
	private AppFUnit af = new AppFUnit();
	private AppBarcodeInventory abi = new AppBarcodeInventory();
	private Properties sysPro = null;
	private int inventoryCheckStrategy = 1;
	
	/**
	 * 初始化要处理的任务
	 */
	public void init() {
		try {
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			inventoryCheckStrategy = Integer.parseInt(sysPro.getProperty("inventoryCheckStrategy"));
		} catch (Exception e) {
			logger.error("加载配置文件system.properties出错", e);
		}
	}
	
	public void auditBarcodeInventory(BarcodeInventory bi, UsersBean users) throws Exception {
		init();
		if(inventoryCheckStrategy == 1) {
			clearNotUploadedInventory(bi);
		} 
		
		boolean addothershipmentFlag = false;
		boolean addotherincomeFlag = false;
		
		// 对在idcode表中的数据操作.
		List<BarcodeInventoryIdcode> bidcode = abii.getBarcodeInventoryIdcodeByOsid(bi.getId());
		ai.updIdcodeWarehouse(bi.getWarehouseid());
		for (BarcodeInventoryIdcode idcode : bidcode) {
			// 是本仓库的数据, 进行清除.
			ai.updIdcode(idcode.getIdcode());
			// 按箱条码,对idcode表进行标志(标志当前仓库,并把NCLtono 记录盘库的单据号)
			ai.updWidAndNclByIdcode(bi.getWarehouseid(), bi.getId(), idcode.getIdcode());
		}

		// 创建其他出库单
		OtherShipmentBillAll osb = otherShipment(bi);
		// 创建其他入库单
		OtherIncomeAll oi = otherIncome(bi);
		// 对库存进行操作. 更新库存 (按产品,批号,盘库的数量,为新的库存.)
		// 获取该单号的详细情况
		AppBarcodeInventoryDetail abid = new AppBarcodeInventoryDetail();
		List<Map> bardetaiList = abid.getQuantityById(bi.getId());
		List<BarcodeInventoryDetail> labid = transforList(bardetaiList);

		// 在库存中增加该单号详细情况
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (BarcodeInventoryDetail ttd : labid) {
			ps = new ProductStockpile();
			ps.setId(Long.valueOf(MakeCode
					.getExcIDByRandomTableName("product_stockpile", 0, "")));
			ps.setProductid(ttd.getProductid());
			// 设置最小单位
			Product p = ap.getProductByID(ttd.getProductid());
			ps.setCountunit(p.getSunit());
			ps.setBatch(ttd.getBatch());
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(bi.getWarehouseid());
			ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			appps.addProductByPurchaseIncome2(ps);
			// 增加盘库台帐记录. ( 增加或减少的数量)
			// 获取库存并判断是增加操作还是减少操作
			// 由于product_stockpile和product_stockpile_all表中保存的数据是最小单位
			Double total = 0.0;
			total = apppsa.getStockpileByWidBatchProductid(ps.getWarehouseid(), ps.getBatch(),
					ps.getProductid(), ps.getWarehousebit());
			Double xquantity = af.getXQuantity(ps.getProductid(), ttd.getUnitid());
			if (ttd.getQuantity() * xquantity - total > 0) {
				addotherincomeFlag = true;
				WarehouseBitDafService wbds = new WarehouseBitDafService(
						"other_income_idcode_all", "oiid", oi.getWarehouseid());
				otherIncomeDetail(oi.getId(), ttd.getProductid(), ttd.getSpecmode(), (ttd
						.getQuantity()
						* xquantity - total)
						/ xquantity, ttd.getBatch(), oi, ttd.getProductname(), wbds);
				// 其他入库操作
				appps.inProductStockpile(ttd.getProductid(), p.getSunit(), ttd.getBatch(), (ttd
						.getQuantity()
						* xquantity - total), oi.getWarehouseid(), ps.getWarehousebit(), oi
						.getId(), "盘盈入库");

				// 条码盘点入库操作
				/*
				 * appps.inbarcodeInventoryStockpile(ttd.getProductid(),
				 * ttd.getUnitid(), ttd .getBatch(), ttd.getQuantity(),
				 * bi.getWarehouseid(), ps .getWarehousebit(), bi.getId(),
				 * total, "盘库-条码盘库");
				 */

			} else if (ttd.getQuantity() * xquantity - total < 0) {
				addothershipmentFlag = true;
				WarehouseBitDafService wbds = new WarehouseBitDafService(
						"other_shipment_bill_idcode", "osid", osb.getWarehouseid());
				otherShipmentDetail(osb.getId(), ttd.getProductid(), ttd.getSpecmode(),
						(total - ttd.getQuantity() * xquantity) / xquantity, ttd.getBatch(),
						ttd.getProductname(), wbds, osb);

				// 其他出库操作
				appps.outProductStockpile(ttd.getProductid(), p.getSunit(), ttd.getBatch(),
						(total - ttd.getQuantity() * xquantity), osb.getWarehouseid(), ps
								.getWarehousebit(), osb.getId(), "盘亏出库");

				// 条码盘点出库操作
				/*
				 * appps.outbarcodeInventoryStockpile(ttd.getProductid(),
				 * ttd.getUnitid(), ttd .getBatch(), ttd.getQuantity(),
				 * bi.getWarehouseid(), ps .getWarehousebit(), bi.getId(),
				 * total, "盘库-条码盘库");
				 */

			}
			// 在addBarcodeInventoryDAll表中增加记录
			addBarcodeInventoryDAll(bi.getId(), ttd.getProductid(), ttd.getProductname(),
					ttd.getSpecmode(), ttd.getUnitid(), ttd.getBatch(), total / xquantity, ttd
							.getQuantity(), total / xquantity - ttd.getQuantity());
		}

		// TODO:新增其他入库单
		if (addotherincomeFlag) {
			aoi.addOtherIncomeAll(oi);
		}

		// TODO:新增其他出库单
		if (addothershipmentFlag) {
			apos.addOtherShipmentBill(osb);
		}

		// 修改复核标志
//		abi.updIsAudit(bi.getId(), users.getUserid(), 1);
		bi.setIsaudit(1);
		bi.setAuditid(users.getUserid());
		bi.setAuditdate(DateUtil.getCurrentDate());
		abi.updBarcodeInventory(bi);
	}
	
	private List transforList(List<Map> list) {
		List psList = new ArrayList();
		for (Map map : list) {
			BarcodeInventoryDetail bDetail = new BarcodeInventoryDetail();
			bDetail.setProductid(map.get("productid").toString());
			bDetail.setBatch(map.get("batch").toString());
			bDetail.setProductname(map.get("productname").toString());
			bDetail.setSpecmode(map.get("specmode").toString());
			bDetail.setQuantity(Double.valueOf(map.get("quantity").toString()));
			bDetail.setUnitid(Integer.valueOf(map.get("unitid").toString()));
			bDetail.setSpecmode(map.get("specmode").toString());
			bDetail.setOsid(map.get("specmode").toString());
			psList.add(bDetail);
		}
		return psList;
	}

	private void clearNotUploadedInventory(BarcodeInventory bi) throws Exception {
		boolean addothershipmentFlag = false;
		boolean addotherincomeFlag = false;
		List<ProductStockpileAll> stockpiles = abida.getNotUploadedInventory(bi);
		if(stockpiles != null && stockpiles.size() > 0) {
			// 创建其他出库单
			OtherShipmentBillAll osb = otherShipment(bi);
			// 创建其他入库单
			OtherIncomeAll oi = otherIncome(bi);
			for(ProductStockpileAll psa : stockpiles) {
				Product product = appProduct.loadProductById(psa.getProductid());
				Double xQuantity = af.getXQuantity(product.getId(), Constants.DEFAULT_UNIT_ID);
				if(psa.getStockpile() > 0) { //其他出库
					addothershipmentFlag = true;
					WarehouseBitDafService wbds = new WarehouseBitDafService(
							"other_shipment_bill_idcode", "osid", osb.getWarehouseid());
					otherShipmentDetail(osb.getId(), psa.getProductid(), product.getSpecmode(),
							ArithDouble.div(psa.getStockpile(), xQuantity), psa.getBatch(),
							product.getProductname(), wbds, osb);

					// 其他出库操作
					appps.outProductStockpile(psa.getProductid(), product.getSunit(), psa.getBatch(),
							psa.getStockpile(), osb.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, osb.getId(), "盘亏出库");
				} else { //其他入库
					addotherincomeFlag = true;
					WarehouseBitDafService wbds = new WarehouseBitDafService(
							"other_income_idcode_all", "oiid", oi.getWarehouseid());
					otherIncomeDetail(oi.getId(), psa.getProductid(), product.getSpecmode(), Math.abs(ArithDouble.div(psa.getStockpile(), xQuantity)), psa.getBatch(), oi, product.getProductname(), wbds);
					// 其他入库操作
					appps.inProductStockpile(psa.getProductid(), product.getSunit(), psa.getBatch(), Math.abs(psa.getStockpile()), oi.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, oi
							.getId(), "盘盈入库");
				}
				// 在addBarcodeInventoryDAll表中增加记录
				addBarcodeInventoryDAll(bi.getId(), psa.getProductid(), product.getProductname(),
						product.getSpecmode(), Constants.DEFAULT_UNIT_ID, psa.getBatch(), ArithDouble.div(psa.getStockpile(), xQuantity), 0d
								, ArithDouble.sub(ArithDouble.div(psa.getStockpile(), xQuantity),0d));
				//删除库存
				apppsa.delProductStockpileAll(psa);
			}
			
			// TODO:新增其他入库单
			if (addotherincomeFlag) {
				aoi.addOtherIncomeAll(oi);
			}

			// TODO:新增其他出库单
			if (addothershipmentFlag) {
				apos.addOtherShipmentBill(osb);
			}
		}
	}
	
	private void addBarcodeInventoryDAll(String osid, String productid, String productname,
			String specmode, Integer unitid, String batch, Double unitprice, Double quantity,
			Double takequantity) throws Exception {
		BarcodeInventoryDAll bida = new BarcodeInventoryDAll();
		bida.setOsid(osid);
		bida.setProductid(productid);
		bida.setProductname(productname);
		bida.setSpecmode(specmode);
		bida.setUnitid(unitid);
		bida.setBatch(batch);
		bida.setUnitprice(unitprice);
		bida.setQuantity(quantity);
		bida.setTakequantity(takequantity);
		abida.addBarcodeInventoryDAll(bida);
	}
	
	private void otherIncomeDetail(String oiid, String productid, String specmode, Double quantity,
			String batch, OtherIncomeAll oi, String productname, WarehouseBitDafService wbds)
			throws NumberFormatException, Exception {
		OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
		oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("other_income_detail_all", 0,
				"")));
		oid.setOiid(oiid);
		oid.setProductid(productid);
		oid.setSpecmode(specmode);
		oid.setProductname(productname);
		oid.setUnitid(Constants.DEFAULT_UNIT_ID);
		oid.setBatch(batch);
		oid.setQuantity(Math.abs(quantity));
		aoid.addOtherIncomeDetail(oid);
		wbds
				.add(oi.getId(), oid.getProductid(), oid.getUnitid(), oid.getQuantity(), oid
						.getBatch());
	}
	
	private void otherShipmentDetail(String osid, String productid, String specmode,
			Double quantity, String batch, String productname, WarehouseBitDafService wbds,
			OtherShipmentBillAll osb) throws NumberFormatException, Exception {
		OtherShipmentBillDAll osbd = new OtherShipmentBillDAll();
		osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("other_shipment_bill_detail",
				0, "")));
		osbd.setOsid(osid);
		osbd.setProductid(productid);
		osbd.setSpecmode(specmode);
		osbd.setProductname(productname);
		// 单位默认为包
		osbd.setUnitid(Constants.DEFAULT_UNIT_ID);
		osbd.setBatch(batch);
		osbd.setUnitprice(0.00);
		osbd.setQuantity(Math.abs(quantity));
		osbd.setSubsum(0.00);
		osbd.setTakequantity(0d);
		apobd.addOtherShipmentBillDetail(osbd);
		wbds.add(osb.getId(), osbd.getProductid(), osbd.getUnitid(), osbd.getQuantity(), osbd
				.getBatch());
	}
	
	private OtherShipmentBillAll otherShipment(BarcodeInventory bi) throws Exception {
		OtherShipmentBillAll osb = new OtherShipmentBillAll();
		String osid = MakeCode.getExcIDByRandomTableName("other_shipment_bill_all", 2, "QC");
		osb.setId(osid);
		osb.setOrganid(bi.getOrganid());
		osb.setWarehouseid(bi.getWarehouseid());
		// 设置出库类别
		osb.setShipmentsort(1);
		osb.setRequiredate(DateUtil.getCurrentDate());
		// 标记是否符合
		osb.setIsaudit(1);
		osb.setAuditid(0);
		osb.setAuditdate(DateUtil.getCurrentDate());
		osb.setMakeorganid(bi.getMakeorganid());
		osb.setMakeid(bi.getMakeid());
		osb.setMakedate(DateUtil.getCurrentDate());
		osb.setIsblankout(0);
		osb.setBlankoutid(0);
		osb.setIsendcase(0);
		osb.setEndcaseid(0);
		osb.setTakestaus(0);
		osb.setIsaccount(1);
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(osb.getId()).append(",").append(osb.getBillno()).append(",").append(
				osb.getRemark()).append(",");
		WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode",
				"osid", osb.getWarehouseid());
		osb.setKeyscontent(keyscontent.toString());
		return osb;
	}
	
	private OtherIncomeAll otherIncome(BarcodeInventory bi) throws Exception {
		OtherIncomeAll oi = new OtherIncomeAll();
		String oiid = MakeCode.getExcIDByRandomTableName("other_income_all", 2, "QR");
		oi.setId(oiid);
		oi.setOrganid(bi.getOrganid());
		oi.setWarehouseid(bi.getWarehouseid());
		oi.setIncomesort(0);
		oi.setIsaudit(1);
		oi.setAuditid(0);
		oi.setAuditdate(DateUtil.getCurrentDate());
		oi.setMakeorganid(bi.getMakeorganid());
		oi.setMakeid(bi.getMakeid());
		oi.setMakedate(DateUtil.getCurrentDate());
		oi.setKeyscontent(oi.getId() + "," + "," + oi.getBillno() + "," + oi.getRemark());
		// 记账
		oi.setIsaccount(1);
		AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
		WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode_all", "oiid",
				oi.getWarehouseid());
		return oi;
	}

}
