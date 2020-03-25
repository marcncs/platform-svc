package com.winsafe.drp.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockWasteBook;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppDupontCodeUploadLog;

public class ImportSapIdcodeServices {
	
	private AppDupontCodeUploadLog adcul = new AppDupontCodeUploadLog();
	private AppTakeTicket appTt = new AppTakeTicket();
	private AppStockAlterMove appSam = new AppStockAlterMove();
	private AppTakeTicketIdcode appTti = new AppTakeTicketIdcode();
	private AppStockAlterMoveIdcode appSami = new AppStockAlterMoveIdcode();
	private AppTakeTicketDetail appTtd = new AppTakeTicketDetail();
	private AppStockAlterMoveDetail appSamd = new AppStockAlterMoveDetail();
	private AppProduct appProduct = new AppProduct();
	private AppOrgan appOrgan = new AppOrgan();
	
	//出库台账
	private AppStockWasteBook appswb = new AppStockWasteBook();
	private AppFUnit appFunit = new AppFUnit();
	private AppProductStockpile apsp = new AppProductStockpile();
	
	public final Integer DB_IN_SIZE = 999;

	public void dealFile(String ttid, InputStream is, String fileName) throws Exception {
		TakeTicket tt = appTt.getTakeTicketById(ttid);
		StockAlterMove sam = appSam.getStockAlterMoveByID(tt.getBillno());
		checkBillStatus(tt, sam);
		
		//K：物料号，V：箱数量
		Map<String, Double> codeMap = new HashMap<String, Double>();
		Set<String> codeSet = new HashSet<String>();
		readFile(codeMap, codeSet, is, fileName, sam);
		
		checkIsCartonCodeExists(codeSet);
		delBillIdcodes(tt);
		addBillIdcodes(tt, codeSet);
		updBillDetail(tt, codeMap);
		updBillDate(tt, sam);
		
		updStockWasteBook(ttid);
	}
	
	/**
	 * 条码数量转换为计量单位数量
	 * 
	 * @param ttid
	 * @throws Exception
	 */
	private void updStockWasteBook(String ttid) throws Exception{
		appswb.deleteStockWasteBookByBillcode(ttid);
		
		List<TakeTicketIdcode> ttiList = appTti.getTakeTicketIdcodeByttid(ttid);
		//TT对象
		TakeTicket tt = appTt.getTakeTicketById(ttid);
		//得到TT明细
		List<TakeTicketDetail> ttds = appTtd.getTakeTicketDetailByTtid(ttid);
		//新增出库台账
		outProductStockpileTTD(ttds, ttiList, tt.getWarehouseid());
	}
	
	/**
	 * 根据检货详情及条码扣减库存
	 * @param ttdList
	 * @param ttiList
	 * @param outWarehouseid
	 * @throws Exception
	 */
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,List<TakeTicketIdcode> ttiList,String outWarehouseid) throws Exception {
		Map<String, Double> idcodeBatchMap = collectIdcode(ttiList);
		outProductStockpileTTD(ttdList, idcodeBatchMap, outWarehouseid);
	}
	
	private void outProductStockpileTTD(List<TakeTicketDetail> ttdList,Map<String, Double> idcodeBatchMap,String outWarehouseid) throws Exception {
		// 根据批次减出库仓库库存
		for (TakeTicketDetail ttd : ttdList) {
			//总数量
			Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
			Integer unitid = appProduct.getProductByID(ttd.getProductid()).getSunit();
			// 先扣减所有批次的库存
			for(String key : idcodeBatchMap.keySet()){
				String[] objs = key.split("_");
				String pid = objs[0];
				String batch = objs[1];
				if(pid.equals(ttd.getProductid())){
					// 批次的数量
					Double batchQuantity = idcodeBatchMap.get(key);
					if(StringUtil.isEmpty(batch)){
						batch = Constants.NO_BATCH;
					}
					batchQuantity = appFunit.getQuantity(pid, unitid, batchQuantity);
					appswb.addOutStockWasteBook(pid, batch, appFunit.getQuantity(pid, unitid, batchQuantity), outWarehouseid,
							"000", ttd.getTtid(), "检货小票-出货");// 台账
					quantity = quantity - batchQuantity;
				}
			}
			// 如果quantity不为0,则对应扣减特殊批次的库存
			if(quantity != 0){
				quantity = appFunit.getQuantity(ttd.getProductid(), unitid, quantity);
				appswb.addOutStockWasteBook(ttd.getProductid(), Constants.NO_BATCH, quantity, outWarehouseid,
						"000", ttd.getTtid(), "检货小票-出货");// 台账
			}
			
		}
	}
	
	/**
	 * 根据条码汇总批次数量
	 * Create Time 2014-11-18 下午03:35:11 
	 * @param ttiList
	 * @return
	 */
	private Map<String, Double> collectIdcode(List<TakeTicketIdcode> ttiList){
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(TakeTicketIdcode tti : ttiList){
			if(tti == null) {
				continue;
			}
			String pid = tti.getProductid();
			String batch = tti.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, tti.getPackquantity());
			}else {
				idcodeBatchMap.put(key, quantity + tti.getPackquantity());
			}
		}
		return idcodeBatchMap;
	}
	
	private void updBillDate(TakeTicket tt, StockAlterMove sam) throws Exception {
		Date date = DateUtil.getCurrentDate();
		tt.setAuditdate(date);
		appTt.updTakeTicket(tt);
		sam.setAuditdate(date);
		appSam.updstockAlterMove(sam);
	}

	private void updBillDetail(TakeTicket tt, Map<String, Double> codeMap) throws Exception {
		//K:产品编号V:产品
		Map<String, Product> proMap = getProductMap(codeMap.keySet());
		Map<String, TakeTicketDetail> ttdMap = getTakeTicketDetailMap(tt.getId());
		Map<String, StockAlterMoveDetail> samdMap = getStockAlterMoveDetailMap(tt.getBillno());
		for(String productId : ttdMap.keySet()) {
			Product pro = proMap.get(productId);
			TakeTicketDetail ttd = ttdMap.get(productId);
			StockAlterMoveDetail samd = samdMap.get(productId);
			if(pro == null) {
				//删除详情
				appTtd.delTakeTicketDetailById(ttd.getId());
				appSamd.delStockAlterMoveDetail(samd.getId().toString());
				continue;
			}
			if(codeMap.containsKey(pro.getmCode())) {
				//更新详情
				ttd.setQuantity(codeMap.get(pro.getmCode()));
				ttd.setRealQuantity(codeMap.get(pro.getmCode()));
				appTtd.updTakeTicketDetail(ttd);
				samd.setQuantity(codeMap.get(pro.getmCode()));
				samd.setTakequantity(codeMap.get(pro.getmCode()));
				appSamd.updstockAlterMove(samd);
			} else {
				//删除详情
				appTtd.delTakeTicketDetailById(ttd.getId());
				appSamd.delStockAlterMoveDetail(samd.getId().toString());
			}
			codeMap.remove(pro.getmCode());
		}
		if(codeMap.size() > 0) {
			//添加详情
			//K:产品物料号V:产品
			proMap = getProductMap(proMap.values());
			for(String mCode : codeMap.keySet()) {
				StockAlterMoveDetail samd = addStockAlterMoveDetail(tt.getBillno(), proMap.get(mCode), codeMap.get(mCode));
				addTakeTicketDetail(tt.getId(), samd);
			}
		}
		
	}

	private void addTakeTicketDetail(String ttid, StockAlterMoveDetail samd) throws Exception {
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setProductid(samd.getProductid());
		ttd.setProductname(samd.getProductname());
		ttd.setSpecmode(samd.getSpecmode());
		ttd.setUnitid(samd.getUnitid());
		ttd.setNccode(samd.getNccode());
		ttd.setBatch(samd.getBatch());
		ttd.setUnitprice(samd.getUnitprice());
		//数量
		ttd.setQuantity(samd.getQuantity());
		ttd.setRealQuantity(samd.getQuantity());
		//箱数
		ttd.setBoxnum(samd.getBoxnum());
		//散数
		ttd.setScatternum(samd.getScatternum());
		ttd.setTtid(ttid);
		ttd.setIsPicked(0);
		appTtd.addTakeTicketDetail(ttd);
	}

	private StockAlterMoveDetail addStockAlterMoveDetail(String samid, Product pro, Double quantity) throws Exception {
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(samid);
		smd.setProductid(pro.getId());
		smd.setProductname(pro.getProductname());
		smd.setSpecmode(pro.getSpecmode());
		//单位
		smd.setUnitid(Constants.DEFAULT_UNIT_ID);
		//订购不选择批次
		smd.setBatch("");
		//设置内部编号 
		smd.setNccode(pro.getmCode());
		smd.setUnitprice(0d);
		smd.setQuantity(quantity);
		smd.setTakequantity(quantity);
		smd.setSubsum(0d);
		smd.setMakedate(DateUtil.getCurrentDate());
		appSamd.addStockAlterMoveDetail(smd);
		return smd;
	}

	private Map<String, Product> getProductMap(Collection<Product> pros) {
		Map<String, Product> proMap = new HashMap<String, Product>();
		for(Product pro : pros) {
			proMap.put(pro.getmCode(), pro);
		}
		return proMap;
	}

	private Map<String, StockAlterMoveDetail> getStockAlterMoveDetailMap(String billno) throws Exception {
		List<StockAlterMoveDetail> samdList = appSamd.getStockAlterMoveDetailBySamID(billno);
		Map<String, StockAlterMoveDetail> samdMap = new HashMap<>();
		for(StockAlterMoveDetail ttd : samdList) {
			samdMap.put(ttd.getProductid(), ttd);
		}
		return samdMap;
	}

	private Map<String, TakeTicketDetail> getTakeTicketDetailMap(String id) throws Exception {
		List<TakeTicketDetail> ttdList = appTtd.getTakeTicketDetailByTtid(id);
		Map<String, TakeTicketDetail> ttdMap = new HashMap<>();
		for(TakeTicketDetail ttd : ttdList) {
			ttdMap.put(ttd.getProductid(), ttd);
		}
		return ttdMap;
	}

	private void addBillIdcodes(TakeTicket tt, Set<String> codeSet) throws Exception {
		List<String> sqls = new ArrayList<String>();
		StringBuffer cartonCodes = new StringBuffer();
		int count = 0;
		for(String cartonCode : codeSet) {
			cartonCodes.append(",'"+cartonCode+"'");
			count++;
			if(count >= DB_IN_SIZE) { 
				sqls.add(getAddTakeTicketIdcodeSql(tt.getId(), cartonCodes.substring(1)));
				sqls.add(getStockAlterMoveIdcodeSql(tt.getBillno(), cartonCodes.substring(1)));
				cartonCodes = new StringBuffer();
				count = 0;
			}
		}
		if(cartonCodes.length() > 0) {
			sqls.add(getAddTakeTicketIdcodeSql(tt.getId(), cartonCodes.substring(1)));
			sqls.add(getStockAlterMoveIdcodeSql(tt.getBillno(), cartonCodes.substring(1)));
		}
		for(String sql : sqls) {
			appTti.updBySql(sql);
		}
		
	}

	private void delBillIdcodes(TakeTicket tt) throws Exception {
		appTti.delTakeTicketIdcodeByTiid(tt.getId());
		appSami.delStockAlterMoveIdcodeByPiid(tt.getBillno());
	}

	private void readFile(Map<String, Double> codeMap, Set<String> codeSet, InputStream is, String fileName, StockAlterMove sam) throws Exception {
		Workbook wb = null;
		if(fileName.toLowerCase().endsWith(".xlsx")) {
			wb = new XSSFWorkbook(is); 
		} else {
			wb = new HSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		String billNo = null;
		
		//读取文件
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {  
			Row row = sheet.getRow(rowNum);  
			if(billNo == null) {
				billNo = row.getCell(1).getStringCellValue();
				billNo = Constants.ZERO_PREFIX[10-billNo.length()]+billNo;
				if(!billNo.equals(sam.getNccode())) {
					throw new Exception("发货单号"+billNo+"与所选的发货单不符");
				}
			}
			
			String mCode = row.getCell(3).getStringCellValue();
			String idcode = row.getCell(2).getStringCellValue();
			System.out.println(mCode);
			System.out.println(idcode);
			idcode = Constants.ZERO_PREFIX[20-idcode.length()]+idcode;
			mCode = Constants.ZERO_PREFIX[8-mCode.length()]+mCode;
			
			if(!codeSet.add(idcode)) {
				throw new Exception("第"+(rowNum+1)+"行:文件中存在重复条码"+idcode);
			}
			if(codeMap.containsKey(mCode)) {
				codeMap.put(mCode, codeMap.get(mCode)+1);
			} else {
				codeMap.put(mCode, 1d);
			}
		}
	}

	private void checkBillStatus(TakeTicket tt, StockAlterMove sam) throws Exception {
		if(sam.getIscomplete() == 1) {
			throw new Exception("该单据已签收,无法重新导入条码");
		}
		Organ outOrgan = appOrgan.getOrganByID(tt.getOid());
		if(!outOrgan.getOrganType().equals(OrganType.Plant.getValue())) {
			throw new Exception("只有工厂的出库单才能使用该功能");
		}
	}

	private Map<String, Product> getProductMap(Set<String> mCodeSet) {
		StringBuffer mCodes = new StringBuffer();
		for(String mCode : mCodeSet) {
			mCodes.append(",'"+mCode+"'");
		}
		List<Product> proList = appProduct.getProductByMCodes(mCodes.substring(1));
		Map<String, Product> proMap = new HashMap<String, Product>();
		for(Product pro : proList) {
			proMap.put(pro.getId(), pro);
		}
		return proMap;
	}

	private String getStockAlterMoveIdcodeSql(String samid, String codes) {
		String sql = "INSERT INTO STOCK_ALTER_MOVE_IDCODE (ID, SAMID, PRODUCTID, ISIDCODE, WAREHOUSEBIT, BATCH, PRODUCEDATE, VAD, UNITID, QUANTITY, PACKQUANTITY, LCODE, STARTNO, ENDNO, IDCODE, MAKEDATE) "
				+ "select seq_stock_alter_move_idcode.nextval,'"+samid+"',PRODUCTID, '1', '000', batch, PRODUCEDATE, VAD, UNITID, QUANTITY, PACKQUANTITY, LCODE, STARTNO, ENDNO, IDCODE, SYSDATE "
				+ "FROM IDCODE where IDCODE in ("+codes+")";
		return sql;
	}
	
	private String getAddTakeTicketIdcodeSql(String ttid, String codes) {
		String sql = "INSERT INTO TAKE_TICKET_IDCODE (ID, TTID, PRODUCTID, ISIDCODE, WAREHOUSEBIT, BATCH, PRODUCEDATE, VAD, UNITID, QUANTITY, PACKQUANTITY, LCODE, STARTNO, ENDNO, IDCODE, ISSPLIT, MAKEDATE) "
				+ "select seq_take_ticket_idcode.nextval,'"+ttid+"',PRODUCTID, '1', '000', batch, PRODUCEDATE, VAD, UNITID, QUANTITY, PACKQUANTITY, LCODE, STARTNO, ENDNO, IDCODE, 0, SYSDATE "
				+ "FROM IDCODE where IDCODE in ("+codes+")";
		return sql;
	}

	private void checkIsCartonCodeExists(Set<String> codeSet) throws Exception {
		Set<String> fileCodeSet = new HashSet<>();
		fileCodeSet.addAll(codeSet);
		List<String> sqls = new ArrayList<String>();
		StringBuffer cartonCodes = new StringBuffer();
		int count = 0;
		for(String cartonCode : codeSet) {
			cartonCodes.append(",'"+cartonCode+"'");
			count++;
			if(count >= DB_IN_SIZE) { 
				sqls.add(getCheckCartonCodeSql(cartonCodes.substring(1)));
				cartonCodes = new StringBuffer();
				count = 0;
			}
		}
		if(cartonCodes.length() > 0) {
			sqls.add(getCheckCartonCodeSql(cartonCodes.substring(1)));
		}
		Set<String> existsCode = new HashSet<String>();
		for(String sql : sqls) {
			List<Map<String,String>> result = adcul.executeSql(sql);
			for(Map<String,String> map : result) {
				existsCode.add(map.get("cartoncode"));
			}
		}
		fileCodeSet.removeAll(existsCode);
		if(fileCodeSet.size() > 0) {
			StringBuffer errMsg = new StringBuffer("以下条码系统中不存在：<br>");
			for(String code : fileCodeSet) {
				errMsg.append(code).append("<br>");
			}
			throw new Exception(errMsg.toString());
		}
	}
	
	private String getCheckCartonCodeSql(String codes) {
		String sql = "select CARTON_CODE cartoncode from CARTON_CODE where CARTON_CODE in ("+codes+")";
		return sql;
	}

}
