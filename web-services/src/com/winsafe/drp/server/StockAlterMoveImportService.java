package com.winsafe.drp.server;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.BillImportService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.erp.pojo.BillImportConfig;
import com.winsafe.erp.pojo.ImportData;
import com.winsafe.erp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class StockAlterMoveImportService extends BillImportService {
	private static final Logger logger = Logger.getLogger(StockAlterMoveImportService.class);
	
	AppUsers au = new AppUsers();
	private AppOrgan ao = new AppOrgan();
	private AppWarehouse aw = new AppWarehouse();
	private AppStockAlterMove asm = new AppStockAlterMove();
	private AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
	private AppProduct ap = new AppProduct();
	private AppOlinkMan appOlinkMan = new AppOlinkMan();
	private AppFUnit afu = new AppFUnit();
	private AppBaseResource abr = new AppBaseResource();
	private AppOrganWithdraw aow = new AppOrganWithdraw();
	private AppOrganWithdrawDetail aowd = new AppOrganWithdrawDetail();
	
	private Map<String, Product> productMap = new HashMap<String, Product>();
	private Map<String, Organ> organMap = new HashMap<String, Organ>();
	private Map<String, Warehouse> warehouseMap = new HashMap<String, Warehouse>();
	private Map<String, Integer> unitMap = new HashMap<String, Integer>();
	
	protected Map<String, Warehouse> existWarehouses = new HashMap<String, Warehouse>();
	protected Set<String> notExistsWarehouses = new HashSet<String>();
	protected Map<String, Organ> existOrgans = new HashMap<String, Organ>();
	protected Set<String> notExistsOrgans = new HashSet<String>();
	protected Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	protected Set<String> notExistsMaterialCodes = new HashSet<String>();
	
	protected Set<String> existsSam = new HashSet<String>();
	protected Set<String> notExistsSam = new HashSet<String>();
	
	protected Set<String> existsOw = new HashSet<String>();
	protected Set<String> notExistsOw = new HashSet<String>();
	
	public StockAlterMoveImportService() {
		super();
	}

	public StockAlterMoveImportService(Organ organ, Warehouse warehouse,
			String templateNo, UsersBean users) {
		super();
		this.organ = organ;
		this.warehouse = warehouse;
		this.templateNo = templateNo;
		this.users = users;
	}

	protected List<BillImportConfig> getReuiredColumns() throws Exception{
		
		//从数据库中获得Excel对应的列
		String organId = "00000001";
		String templateNo = "1";
		//获取单据导入配置信息
		billConfigs = abic.getAllBillImportConfig(organId, templateNo);
		//获取文件中表头和数据所在行号
		for(BillImportConfig billConfig : billConfigs) {
			if(billConfig.getFieldName().equals("dataRowNo")) {
				dataRowNo = Integer.parseInt(billConfig.getDefaultValue()) - 1;
			} 
			if(billConfig.getFieldName().equals("titleRowNo")) {
				titleRowNo = Integer.parseInt(billConfig.getDefaultValue()) - 1;
			} 
		}
		//获取产品代码配置信息
//		productConfigs = apic.getProductConfigByOrganId(organId);
		
//		OUT_WAREHOUSE_NCCODE_LABEL = abic.getBillImportConfigByID(organId, templateNo, "OUT_WAREHOUSE_NCCODE").getColumnName();
//		PRODUCT_NCCODE_LABEL = abic.getBillImportConfigByID(organId, templateNo, "PRODUCT_NCCODE").getColumnName();
//		QUANTITY_LABEL = abic.getBillImportConfigByID(organId, templateNo, "QUANTITY").getColumnName();
//		SHIP_TO_LABEL = abic.getBillImportConfigByID(organId, templateNo, "SHIP_TO_ORGAN_NCCODE").getColumnName();
//		SOLD_TO_LABEL = abic.getBillImportConfigByID(organId, templateNo, "SOLD_TO_ORGAN_NCCODE").getColumnName();
//		UNIT_LABEL = abic.getBillImportConfigByID(organId, templateNo, "UNIT").getColumnName();
//		BILL_NCCODE_LABEL = abic.getBillImportConfigByID(organId, templateNo, "BILL_NCCODE").getColumnName();
//		
//		List<String> list = new ArrayList<String>();
//		list.add(OUT_WAREHOUSE_NCCODE_LABEL);
//		list.add(PRODUCT_NCCODE_LABEL);
//		list.add(QUANTITY_LABEL);
//		list.add(SHIP_TO_LABEL);
//		list.add(SOLD_TO_LABEL);
//		list.add(UNIT_LABEL);
//		list.add(BILL_NCCODE_LABEL);
		return billConfigs;
	}
	
	/**
	 * 验证机构是否存在仓库
	 * Create Time 2014-10-20 上午11:23:26
	 * @param organId
	 * @author Ryan.xi
	 */
	public boolean isWarehousExists(String makeOrganId) {
		if(existWarehouses.containsKey(makeOrganId)) {
			return true;
		} else if(notExistsWarehouses.contains(makeOrganId)){
			return false;
		} else {
			try {
				Warehouse warehouse = aw.getAvaiableWarehouseByOID(makeOrganId);
				existWarehouses.put(makeOrganId, warehouse);
				return true;
			} catch (NotExistException e) {
				notExistsWarehouses.add(makeOrganId);
				return false;
			}
		}
	}
	
	/**
	 * 验证机构是否存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param organId
	 * @author Ryan.xi
	 */
	public boolean isOrganExists(String parentId, String organName) {
		if(existOrgans.containsKey(parentId+organName)) {
			return true;
		} else if(notExistsOrgans.contains(parentId+organName)){
			return false;
		} else {
			try {
				Organ organ = ao.getOrganByNameAndParentId(parentId, organName);
				existOrgans.put(parentId+organName, organ);
				return true;
			} catch (NotExistException e) {
				notExistsOrgans.add(parentId+organName);
				return false;
			}
		}
	}
	
	/**
	 * 验证物料码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 */
	public boolean isMaterialCodeExists(String materialCode) {
		if(existMaterialCodes.containsKey(materialCode)) {
			return true;
		} else if(notExistsMaterialCodes.contains(materialCode)){
			return false;
		} else {
			try {
				Product procuct = ap.getByMCode(materialCode);
				existMaterialCodes.put(materialCode, procuct);
				return true;
			} catch (NotExistException e) {
				notExistsMaterialCodes.add(materialCode);
				return false;
			}
		}
	}
	
	/**
	 * 验证字符串是否为有效的日期格式
	 * Create Time 2014-10-20 上午11:23:26
	 * @param date
	 * @author Ryan.xi
	 */
	public boolean isValidDate(String date, String partten) { 
	      boolean convertSuccess=true; 
	      if(StringUtil.isEmpty(date)) {
	    	  return false;
	      }
	      SimpleDateFormat format = new SimpleDateFormat(partten); 
	       try { 
	          format.setLenient(false); 
	          format.parse(date); 
	       } catch (Exception e) { 
	           convertSuccess=false; 
	       } 
	       return convertSuccess; 
	}
	
	protected String dealData(List<Map<String, String>> dataList, Integer userId) throws Exception{
		//验证文件是否存在数据
		if(dataList == null || dataList.size() < 1){
			return "未从文件中读取到数据";
		}
		
		boolean hasError = false;
		Map<String,FUnit>  funitMap = afu.getAllMap(); //获取所有的单位换算信息
		
		StringBuilder errorMsg = new StringBuilder();
		
		
		Map<String, StockAlterMove> sams = new HashMap<String, StockAlterMove>();
		Map<String, StockAlterMoveDetail> samds = new HashMap<String, StockAlterMoveDetail>();
		
		Map<String, OrganWithdraw> ows = new HashMap<String, OrganWithdraw>();
		Map<String, OrganWithdrawDetail> owds = new HashMap<String, OrganWithdrawDetail>();
		
		Map<String, String> map = null;
		for(int i = 0; i < dataList.size(); i++){
			map = dataList.get(i);
			ImportData importData = new ImportData();
			
			MapUtil.mapToObject(map, importData);
//			判断是否是退货单
//			if(!StringUtil.isEmpty(importData.getIsReturn()) && "Y".equals(importData.getIsReturn().trim())) {
//				continue;
//			}
			
			String lineNumber = "第" + (i+1+dataRowNo) + "行："; 
			
			//判断单据日期格式是否正确
			if(!StringUtil.isEmpty(importData.getMoveDate())) {
				if(!isValidDate(importData.getMoveDate(), "yyyy-MM-dd")) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10010, "单据", "yyyy-MM-dd")).append("<br>");
					hasError = true;
					continue;
				} else {
					map.put("movedate", importData.getMoveDate() + " 00:00:00");
				}
			}
			
			//验证出货机构名称是否为空
			if(!StringUtil.isEmpty(importData.getOutOrganName())) {
				//如果不为空，判断出货机构名称是否与所选机构相同
				if(!importData.getOutOrganName().trim().equals(organ.getOrganname())) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.E10012).append("<br>");
					hasError = true;
					continue;
				}
//				errorMsg.append(lineNumber).append(UploadErrorMsg.E10011).append("<br>");
//				hasError = true;
//				continue;
			} 
			
			//验证单据编号是否为空
			if(StringUtil.isEmpty(importData.getNccode())) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.E10001).append("<br>");
				hasError = true;
				continue;
			}
			
			//验证收货机构名称是否为空
			if(StringUtil.isEmpty(importData.getReceiveorganidname())) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.E10002).append("<br>");
				hasError = true;
				continue;
			}
			//验证出货仓库名称是否为空
			if(!StringUtil.isEmpty(importData.getOutWarehouseName())) {
				//判断出货仓库是否与所选仓库相同
				if(!importData.getOutWarehouseName().trim().equals(warehouse.getWarehousename())) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.E10015).append("<br>");
					hasError = true;
					continue;
				}
//				errorMsg.append(lineNumber).append(UploadErrorMsg.E10016).append("<br>");
//				hasError = true;
//				continue;
			} 
			//验证产品编码是否为空
			if(StringUtil.isEmpty(importData.getProductCode())) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.E10003).append("<br>");
				hasError = true;
				continue;
			}
			//验证数量是否为空
			if((importData.getQuantity() == null || importData.getQuantity() == 0d)
					&& (importData.getCountQuantity() == null || importData.getCountQuantity() == 0d)) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.E10004).append("<br>");
				hasError = true;
				continue;
			}
			
			//验证收获机构是否存在
			if(!isOrganExists(organ.getId(), importData.getReceiveorganidname())) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10005, organ.getOrganname(), importData.getReceiveorganidname())).append("<br>");
				hasError = true;
				continue;
			}
			Organ receiveOrgan = existOrgans.get(organ.getId() + importData.getReceiveorganidname());
			//验证收获仓库是否存在
			if(!isWarehousExists(receiveOrgan.getId())) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10006, receiveOrgan.getOrganname())).append("<br>");
				hasError = true;
				continue;
			}
			
			Warehouse inWarehouse = existWarehouses.get(receiveOrgan.getId());
			
			
			//获取物料号
			String mCode = importData.getProductCode();
			if(productCodes.size() > 0) {
				mCode = productCodes.get(importData.getProductCode());
				if(StringUtil.isEmpty(mCode)) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10007, importData.getProductCode())).append("<br>");
					hasError = true;
					continue;
				}
			}
			
			if(!isMaterialCodeExists(mCode)) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10008, mCode)).append("<br>");
				hasError = true;
				continue;
			}
 			
			Product product = existMaterialCodes.get(mCode);
			
			//获取单据中产品数量
			Double quantity = 0d;
			
			if(importData.getQuantity() == null || importData.getQuantity() == 0d){
				quantity = importData.getCountQuantity();
				quantity = ArithDouble.div(quantity, product.getBoxquantity());
				//检查单位是否可以正常转化
				if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
					quantity = changeUnit(product.getId(), product.getSunit(), quantity, Constants.DEFAULT_UNIT_ID,funitMap);
				} else {
					errorMsg.append(lineNumber).append(UploadErrorMsg.E10009).append("<br>");
					hasError = true;
					continue;
				}
			} else {
				quantity = importData.getQuantity();
			}
			
			//判断出货数量是否为整数（件）
			if(!(Math.abs(quantity - Math.round(quantity)) <= 0.00000001)) {
				errorMsg.append(lineNumber).append(UploadErrorMsg.E10013).append("<br>");
				hasError = true;
				continue;
			}
			
			//判断单据是否已存在
			if(quantity > 0) {
				if(existsSam.contains(importData.getNccode())) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10017, organ.getOrganname(), importData.getNccode())).append("<br>");
					hasError = true;
					continue;
				}
				if(!notExistsSam.contains(importData.getNccode())){
					StockAlterMove sam = asm.getStockAlterMoveByOidAndNCcode(organ.getId(), importData.getNccode());
					if(sam != null) {
						existsSam.add(importData.getNccode());
						errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10017, organ.getOrganname(), importData.getNccode())).append("<br>");
						hasError = true;
						continue;
					} else {
						notExistsSam.contains(importData.getNccode());
					}
					
				}
			} else {
				if(existsOw.contains(importData.getNccode())) {
					errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10018, organ.getOrganname(), importData.getNccode())).append("<br>");
					hasError = true;
					continue;
				}
				if(!notExistsOw.contains(importData.getNccode())){
					OrganWithdraw ow = aow.getOrganWithdrawByOidAndNccode(organ.getId(), importData.getNccode());
					if(ow != null) {
						existsOw.add(importData.getNccode());
						errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10018, organ.getOrganname(), importData.getNccode())).append("<br>");
						hasError = true;
						continue;
					} else {
						notExistsOw.contains(importData.getNccode());
					}
					
				}
			}
			
			
			if(quantity < 0) {
				//退货单
				quantity = Math.abs(quantity);
				
				if(ows.containsKey(importData.getNccode())) {
					
					//检查一个单据是否有多个客户
					OrganWithdraw ow = ows.get(importData.getNccode());
					if(!ow.getPorganid().equals(receiveOrgan.getId())) {
						errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10019, importData.getNccode())).append("<br>");
						hasError = true;
						continue;
					}
					OrganWithdrawDetail owd = owds.get(importData.getNccode()+mCode);
					if(owd != null) {
						owd.setQuantity(owd.getQuantity() + quantity);
					} else {
//						OrganWithdraw ow = ows.get(importData.getNccode());
						OrganWithdrawDetail newOwd = newOwd(ow.getId(), product, quantity);
						owds.put(importData.getNccode()+mCode, newOwd);
					}
				} else {
					
					OrganWithdraw ow = newOw(map, inWarehouse, receiveOrgan);
					ows.put(importData.getNccode(), ow);
					
					OrganWithdrawDetail owd = newOwd(ow.getId(), product, quantity);
					owds.put(importData.getNccode()+mCode, owd);
				}
				
			} else {
				//发货单
//				if(quantity < 0) {
//					errorMsg.append(lineNumber).append(UploadErrorMsg.E10014).append("<br>");
//					hasError = true;
//					continue;
//				}
				if(sams.containsKey(importData.getNccode())) {
					//检查一个单据是否有多个客户
					StockAlterMove sam = sams.get(importData.getNccode());
					if(!sam.getReceiveorganid().equals(receiveOrgan.getId())) {
						errorMsg.append(lineNumber).append(UploadErrorMsg.getError(UploadErrorMsg.E10019, importData.getNccode())).append("<br>");
						hasError = true;
						continue;
					}
					StockAlterMoveDetail samd = samds.get(importData.getNccode()+mCode);
					if(samd != null) {
						samd.setQuantity(samd.getQuantity() + quantity);
					} else {
						StockAlterMoveDetail smd = newSamd(sam.getId(), product, quantity);
						samds.put(importData.getNccode()+mCode, smd);
					}
				} else {
					
					StockAlterMove sam = newSam(map, inWarehouse, receiveOrgan);
					sams.put(importData.getNccode(), sam);
					
					StockAlterMoveDetail smd = newSamd(sam.getId(), product, quantity);
					samds.put(importData.getNccode()+mCode, smd);
				}
			}
		}
		if(!hasError) {
			addStockAlterMoves(sams, samds);		
			addOrganWithdraws(ows, owds);		
		}
//		for(int i = 1; i < dataList.size(); i++){
//			try{
//				map = dataList.get(i);
//				StockAlterMove sam = new StockAlterMove();
//				StockAlterMoveDetail samd = new StockAlterMoveDetail();
//				outWarehouseNccode = map.get(OUT_WAREHOUSE_NCCODE_LABEL);
//				productNccode = map.get(PRODUCT_NCCODE_LABEL);
//				quantity = map.get(QUANTITY_LABEL);
//				shipToOrganNccode = map.get(SHIP_TO_LABEL);
//				soldToOrganNccode = map.get(SOLD_TO_LABEL);
//				unit = map.get(UNIT_LABEL);
//				smNccode = map.get(BILL_NCCODE_LABEL);
//				//验证内部单号是否为空
//				if(smNccode == null || "".equals(smNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行内部单号不能为空<br>");
//					continue;
//				}
//				//验证产品内部编号是否为空
//				if(productNccode == null || "".equals(productNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行产品内部编号不能为空<br>");
//					continue;
//				}
//				//验证产品是否存在
//				if(!productMap.containsKey(productNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行产品不存在<br>");
//					continue;
//				}
//				//验证出库仓库是否为空
//				if(outWarehouseNccode == null || "".equals(outWarehouseNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行出库仓库不能为空<br>");
//					continue;
//				}
//				//验证出库仓库是否存在
//				if(!warehouseMap.containsKey(outWarehouseNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行出库仓库不存在<br>");
//					continue;
//				}
//				//验证Ship-To机构是否为空
//				if(shipToOrganNccode == null || "".equals(shipToOrganNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行Ship-To机构不能为空<br>");
//					continue;
//				}
//				//验证Ship-To机构是否存在
//				if(!organMap.containsKey(shipToOrganNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行Ship-To机构不存在<br>");
//					continue;
//				}
//				//验证Sold-To机构是否为空
//				if(soldToOrganNccode == null || "".equals(soldToOrganNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行Ship-To机构不能为空<br>");
//					continue;
//				}
//				//验证Sold-To机构是否存在
//				if(!organMap.containsKey(soldToOrganNccode)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行Ship-To机构不存在<br>");
//					continue;
//				}
//				
//				//验证数量是否为空
//				if(quantity == null || "".equals(quantity)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行数量不能为空<br>");
//					continue;
//				}
//				
//				//验证单位是否为空
//				if(unit == null || "".equals(unit)){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行单位不能为空<br>");
//					continue;
//				}
//				//PC-盒， SE-套
//				if(UnitEnum.getIsUnit(unit) == null){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行单位不正确<br>");
//					continue;
//				}
//				//单位是否存在
//				if(unitMap.get(UnitEnum.getIsUnit(unit)) == null){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行单位不存在<br>");
//					continue;
//				}
//				//判断产品是否有此单位的换算比例
//				FUnit fu1 = afu.getFUnit(productMap.get(productNccode).getId(), unitMap.get(UnitEnum.getIsUnit(unit)));
//				if(fu1 == null){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行产品不存在此单位的换算关系<br>");
//					continue;
//				}
//				//判断产品是否有配置箱的换算关系
//				FUnit fu2 = afu.getFUnit(productMap.get(productNccode).getId(), 2);
//				if(fu2 == null){
//					errorMsg.append("单据信息错误，第").append(i+1).append("行产品未配置箱的换算关系<br>");
//					continue;
//				}
//				sam = asm.getStockAlterMoveByNCcode(smNccode);
//				if(sam == null){
//					sam = new StockAlterMove();
//					Users user = au.getUsersByid(userId);
//					newSam(sam, smNccode, warehouseMap.get(outWarehouseNccode), organMap.get(shipToOrganNccode), organMap.get(soldToOrganNccode), user);
//					newSamd(sam.getId(), productMap.get(productNccode), Double.valueOf(quantity)*(fu1.getXquantity()/fu2.getXquantity()));
//				}
//				else if(sam.getIsblankout() == 0 && sam.getIsaudit() == 0){
//					samd = asmd.getStockAlterMoveDetailBySamIDAndPid(sam.getId(), productMap.get(productNccode).getId());
//					if(samd == null){
//						newSamd(sam.getId(), productMap.get(productNccode), Double.valueOf(quantity)*(fu1.getXquantity()/fu2.getXquantity()));
//					}
//					else{
//						errorMsg.append("单据信息错误，第").append(i+1).append("行该产品已在单据中，不能重复导入<br>");
//						continue;
//						/*updSamd(samd, Double.valueOf(quantity)*(fu1.getXquantity()/fu2.getXquantity()));*/
//					}
//				}
//				else{
//					errorMsg.append("单据信息错误，第").append(i+1).append("行单据已作废或关闭，无法更新<br>");
//					continue;
//				}
//				sam = null;
//				samd = null;
//				map = null;
//				HibernateUtil.commitTransaction();
//			}
//			catch(Exception e){
//				HibernateUtil.rollbackTransaction();
//				logger.error("", e);
//			}
//		}
		if(hasError) {
			return "导入失败 <br>" + errorMsg.toString();
		} else {
			return "导入完成 <br>";
		}
		
	}
	
	private void addOrganWithdraws(Map<String, OrganWithdraw> ows,
			Map<String, OrganWithdrawDetail> owds) throws Exception {
		for(OrganWithdraw ow : ows.values()) {
			aow.save(ow);
		}
		for(OrganWithdrawDetail owd : owds.values()) {
			aowd.save(owd);
		}
		
	}

	private OrganWithdraw newOw(Map<String, String> map, Warehouse inWarehouse,
			Organ returnOrgan) throws Exception {
		OrganWithdraw ow = new OrganWithdraw();
		MapUtil.mapToObject(map, ow);
		ow.setId(MakeCode.getExcIDByRandomTableName("organ_withdraw", 2, "OW"));
		ow.setPorganid(returnOrgan.getId());
		ow.setPorganname(returnOrgan.getOrganname());
		ow.setWarehouseid(inWarehouse.getId());
		ow.setPlinkman(warehouse.getUsername());
		ow.setTel(warehouse.getWarehousetel());
		ow.setInwarehouseid(warehouse.getId());
		ow.setReceiveorganid(organ.getId());
		ow.setMakeid(users.getUserid());
		ow.setMakeorganid(users.getMakeorganid());
		ow.setMakedeptid(users.getMakedeptid());
		ow.setMakedate(DateUtil.getCurrentDate());
		ow.setHasDetail(1);
		ow.setIsaudit(0);
		ow.setAuditid(0);
		ow.setIsratify(0);
		ow.setIsaffirm(0);
		ow.setIscomplete(0);
		ow.setReceiveid(0);
		ow.setIsblankout(0);
		ow.setBlankoutid(0);
		ow.setPrinttimes(0);
		ow.setTakestatus(0);
		ow.setWithdrawcause(ow.getRemark());
		ow.setRemark("");
		return ow;
	}

	private OrganWithdrawDetail newOwd(String owid, Product product,
			Double quantity) throws NumberFormatException, Exception {
		OrganWithdrawDetail owd = new OrganWithdrawDetail();
		owd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_detail", 0, "")));
		owd.setOwid(owid);
		owd.setProductid(product.getId());
		owd.setProductname(product.getProductname());
		owd.setSpecmode(product.getSpecmode());
		owd.setBatch("");
		owd.setUnitprice(0d);
		owd.setQuantity(quantity);
		owd.setTakequantity(0d);
		owd.setSubsum(0d);
		owd.setBoxnum(0);
		owd.setScatternum(0d);
		owd.setUnitid(Constants.DEFAULT_UNIT_ID);
		owd.setNccode(product.getmCode());
		return owd;
	}

	private void addStockAlterMoves(Map<String, StockAlterMove> sams,
			Map<String, StockAlterMoveDetail> samds) throws Exception {
		for(StockAlterMove sam : sams.values()) {
			asm.addStockAlterMove(sam);
		}
		for(StockAlterMoveDetail samd : samds.values()) {
			asmd.addStockAlterMoveDetail(samd);
		}
	}
	
	private StockAlterMove newSam(Map<String, String> map, Warehouse inWarehouse, Organ receiveOrgan) throws Exception{
		StockAlterMove sam = new StockAlterMove();
		MapUtil.mapToObject(map, sam);
		sam.setId(MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM"));
		//单据日期
		if(sam.getMovedate() == null) {
			sam.setMovedate(DateUtil.getCurrentDate());
		}
		sam.setOutwarehouseid(warehouse.getId());
		sam.setOlinkman(inWarehouse.getUsername());
		sam.setOtel(inWarehouse.getWarehousetel());
		sam.setTransportaddr(inWarehouse.getWarehouseaddr());//收货地址
		sam.setMakedate(DateUtil.getCurrentDate());
		if(users!=null){
			sam.setMakeid(users.getUserid());
			sam.setMakeorganid(users.getMakeorganid());
			sam.setMakedeptid(users.getMakedeptid());
			Organ o = ao.getOrganByID(users.getMakeorganid());
			sam.setMakeorganidname(o.getOrganname());
		}
		sam.setInwarehouseid(inWarehouse.getId());
		sam.setIsaudit(0);
		sam.setIsshipment(0);
		sam.setIsblankout(0);
		sam.setReceiveorganid(receiveOrgan.getId());
		sam.setReceiveorganidname(receiveOrgan.getOrganname());
		sam.setIscomplete(0);
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sam.getId()).append(",").append(sam.getOlinkman()).append(",").append(sam.getOtel()).append(",").append(sam.getMakeorganidname());
		sam.setTakestatus(0);
		sam.setKeyscontent(keyscontent.toString());
		sam.setOutOrganId(organ.getId());
		sam.setOutOrganName(organ.getOrganname());
		return sam;
	}
	

	private void newSam(StockAlterMove sm, String nccode, Warehouse outWarehouse, Organ shipToOrgan, Organ soldToOrgan, Users users) throws Exception{
		Warehouse inWarehouse = aw.getWarehouseByOID(shipToOrgan.getId());
		Warehouse soldToWarehouse = aw.getWarehouseByOID(soldToOrgan.getId());
		Organ outOrgan = ao.getOrganByID(outWarehouse.getMakeorganid());
		sm.setNccode(nccode);//外部单号
		sm.setId(MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM"));
		sm.setMovedate(DateUtil.getCurrentDate());//订单日期
		sm.setOutwarehouseid(outWarehouse.getId());
		sm.setInvmsg(null);//开票信息
		sm.setTickettitle(null);//发票抬头
		sm.setIsmaketicket(0);
		sm.setIsreceiveticket(0);
		Olinkman olinkman = appOlinkMan.getMainLinkmanByCid(shipToOrgan.getId());
		if(olinkman!=null){
			sm.setOlinkman(olinkman.getName());
			sm.setOtel(olinkman.getOfficetel());
		}else{
			sm.setOlinkman("");
			sm.setOtel("");
		}
		sm.setPaymentmode(null);//付款方式
		sm.setTransportmode(null);//发运方式
		sm.setTransportaddr(inWarehouse.getWarehouseaddr());//收货地址
		sm.setMakedate(DateUtil.getCurrentDate());
		if(users!=null){
			sm.setMakeid(users.getUserid());
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakedeptid(users.getMakedeptid());
			Organ o = ao.getOrganByID(users.getMakeorganid());
			sm.setMakeorganidname(o.getOrganname());
		}
		sm.setInwarehouseid(inWarehouse.getId());
		sm.setMovecause(null);//订购原因
		sm.setRemark(null);//备注
		sm.setIsaudit(0);
		sm.setIsshipment(0);
		sm.setIstally(0);
		sm.setIsblankout(0);
		sm.setReceiveorganid(shipToOrgan.getId());
		sm.setReceivedeptid(null);
		sm.setReceiveorganidname(shipToOrgan.getOrganname());
		sm.setIscomplete(0);
//		sm.setBusNo(busNo);//车号
//		sm.setBusWay(busWay);//线路
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",").append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
		sm.setTotalsum(0d);
		sm.setTakestatus(0);
		sm.setKeyscontent(keyscontent.toString());
		sm.setOutOrganId(outOrgan.getId());
		sm.setOutOrganName(outOrgan.getOrganname());
		//系统中没用到soldTo
//		sm.setSoldToOrganId(soldToOrgan.getId());
//		sm.setSoldToOrganName(soldToOrgan.getOrganname());
//		sm.setSoldToWarehouseId(soldToWarehouse.getId());
		asm.addStockAlterMove(sm);
		outOrgan = null;
		inWarehouse = null;
		soldToWarehouse = null;
	}
	
	private StockAlterMoveDetail newSamd(String samid, Product product, Double quantity) throws Exception{
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(samid);
		smd.setProductid(product.getId());
		smd.setProductname(product.getProductname());
		smd.setSpecmode(product.getSpecmode());
		smd.setNccode(product.getNccode());
		smd.setBatch("");
		smd.setUnitprice(0d);
		smd.setQuantity(quantity);
		smd.setTakequantity(0d);
		smd.setSubsum(0d);
		smd.setBoxnum(0);
		smd.setScatternum(0d);
		smd.setUnitid(Constants.DEFAULT_UNIT_ID);
		smd.setNccode(product.getmCode());
		return smd;
	}
	
	private void updSamd(StockAlterMoveDetail samd, Double quantity) throws Exception{
		samd.setQuantity(samd.getQuantity() + quantity);
		asmd.updstockAlterMove(samd);
	}
	
	private void init() throws Exception{
		
		//保存所有仓库
		List<Warehouse> wList = aw.getAllWarehousebean();
		for(Warehouse w: wList){
			warehouseMap.put(w.getNccode(), w);
		}
		wList.clear();
		
		//保存所有机构
		List<Organ> oList = ao.getAllOrgan();
		for(Organ o: oList){
			organMap.put(o.getOecode(), o);
		}
		oList.clear();
		
		//保存所有产品
		List<Product> pList = ap.getProductAll();
		for(Product p: pList){
			productMap.put(p.getmCode(), p);
		}
		pList.clear();
		
		//保存单位
		List<BaseResource> brList = abr.getBaseResource("CountUnit");
		for(BaseResource br: brList){
			unitMap.put(br.getTagsubvalue(), br.getTagsubkey());
		}
		brList.clear();
	}

	public String dealFile(String fileAddress, Object object, Object object2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}



enum UnitEnum{
	SET("SE", "套"), PC("PC", "盒"), PCE("PCE", "盒");
	
	private String sapUnit;
	private String isUnit;
	public String getSapUnit() {
		return sapUnit;
	}

	public void setSapUnit(String sapUnit) {
		this.sapUnit = sapUnit;
	}

	public String getIsUnit() {
		return isUnit;
	}

	public void setIsUnit(String isUnit) {
		this.isUnit = isUnit;
	}

	private UnitEnum(String sapUnit, String isUnit){
		this.sapUnit = sapUnit;
		this.isUnit = isUnit;
	}
	
	public static String getIsUnit(String sapUnit){
		for(UnitEnum ue: UnitEnum.values()){
			if(ue.sapUnit.equals(sapUnit)){
				return ue.isUnit;
			}
		}
		return null;
	}
}
