package com.winsafe.drp.server;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.ExcelUtil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.OrganUploadLog;
import com.winsafe.sap.util.FileUploadUtil;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportOrganService {
	
	private static Logger logger = Logger.getLogger(ImportOrganService.class);
	
	OrganService app = new OrganService();
	AppOrganScan appos = new AppOrganScan();
	AppOrgan appo = new AppOrgan();
	AppWarehouseVisit awv = new AppWarehouseVisit();
	AppOrganProduct aop = new AppOrganProduct();
	AppUserVisit auv = new AppUserVisit();
	AppMakeConf appmc = new AppMakeConf();
	AppOlinkMan appLinkMan = new AppOlinkMan();
	AppWarehouse appWarehouse = new AppWarehouse();
	private AppCountryArea appca = new AppCountryArea();
	private AppOrganVisit aov = new AppOrganVisit();
	private OrganUploadLog uploadLog = null;
	
	private AppSBonusAccount acc = new AppSBonusAccount(); 
	private AppSTransferRelation appstr = new AppSTransferRelation();

	public void execute(OrganUploadLog uploadLog, File file)
			throws Exception {
		this.uploadLog = uploadLog;
		String currentOrganName = null;
		Map<String, CountryArea> countryAreas = new HashMap<String, CountryArea>();
		
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount =0,SCount=0;
		try {
				boolean hasError = false;
				
				Workbook wb = WorkbookFactory.create(new FileInputStream(file));
				Sheet sheet = wb.getSheetAt(0);
				int rownum = sheet.getLastRowNum()+1;
				int startRowNo = 1;
				int endRowNo = 1; 
				Map<String, Integer> counts = new HashMap<String, Integer>();
				
				File tempFile = File.createTempFile("tempExcel", ".xls");
				
				
				XSSFWorkbook wwb = new XSSFWorkbook(new FileInputStream(file));
				XSSFSheet wsheet = wwb.getSheetAt(0);
				Row row = null;
				// 保存报错信息
				
				for (int i = 1; i <= rownum; i++) {
					row = sheet.getRow(i);
					String organName = "";
					if(i != rownum) {
						organName = ExcelUtil.getValue(row.getCell(3));
					}
					if(currentOrganName == null || organName.equals(currentOrganName)) {
						currentOrganName = organName;
						endRowNo++;
					} else {
						try {
							i--;
							SCount++;
							hasError = addOrganWithWarehouse(sheet, startRowNo, endRowNo, countryAreas, wsheet, counts);
							currentOrganName = null;
							startRowNo = endRowNo;
							if(!hasError) {
								CCount++;
								HibernateUtil.commitTransaction();
							} 
						} catch (Exception e) {
							errMsg.append("第[" + i + "]行导入失败<br />");
							e.printStackTrace();
							HibernateUtil.rollbackTransaction();
						}
						
					}
				}
				Row wrow = wsheet.createRow(rownum);
				wrow.createCell(21).setCellValue("上传机构资料成功,本次成功添加机构 :"+(counts.get("organSuccessCount")!=null?counts.get("organSuccessCount"):0)+"条! 失败:"+(counts.get("organErrorCount")!=null?counts.get("organErrorCount"):0)+"条! 成功添加仓库:"+(counts.get("warehouseSuccessCount")!=null?counts.get("warehouseSuccessCount"):0)+"条! 失败："+(counts.get("warehouseErrorCount")!=null?counts.get("warehouseErrorCount"):0)+"条!");
//				wb.close();
				wwb.write(new FileOutputStream(tempFile));
//				wwb.close();
				try {
					String uploadPath = (String)JProperties.loadProperties("upr.properties",JProperties.BY_CLASSLOADER).get("organLogFilePath"); 
//					String uploadPath = "/upload/organ/log/";
					String saveFileName = uploadLog.getFileName()+"_"+uploadLog.getId()+"_log.xlsx";
					FileUploadUtil.saveUplodedFile(new FileInputStream(tempFile), uploadPath, saveFileName);
					uploadLog.setLogFilePath(uploadPath+saveFileName);
					uploadLog.setErrorCount((counts.get("organErrorCount")!=null?counts.get("organErrorCount"):0)+(counts.get("warehouseErrorCount")!=null?counts.get("warehouseErrorCount"):0));
					uploadLog.setTotalCount((counts.get("organSuccessCount")!=null?counts.get("organSuccessCount"):0)+(counts.get("organErrorCount")!=null?counts.get("organErrorCount"):0)+(counts.get("warehouseSuccessCount")!=null?counts.get("warehouseSuccessCount"):0)+(counts.get("warehouseErrorCount")!=null?counts.get("warehouseErrorCount"):0));
					
				} catch (Exception e) {
					logger.error("error occurred when push xls file to client", e);
				}
		} catch (Exception e) {
			logger.error("error occurred when processing xls file", e);
			throw e;
		}
	}
	
	private boolean addOrganWithWarehouse(Sheet sheet, int startRowNo, int endRowNo, Map<String, CountryArea> countryAreas, XSSFSheet wsheet, Map<String, Integer> counts) {
		List<Warehouse> warehouses = new ArrayList<Warehouse>();
		List<WarehouseBit> warehouseBits = new ArrayList<WarehouseBit>();
		Integer organTypeValue = null;
		Integer modelTypeValue = null;
		Integer isKeyRetailer = null; // 是否关键零售商
		boolean isOrganAlreadyExists = false;
		boolean isNeedAudit = true;//是否需要审核
		int i = startRowNo;
		Organ organ = null;
		Olinkman oLinkman = new Olinkman();
		Organ parentOrgan = null;
		Row row = sheet.getRow(i);
		Row wrow = wsheet.getRow(i);

		try {
			//机构内部编码
			String organOeCode = ExcelUtil.getValue(row.getCell(2));
			
			//机构名称
			String organName = ExcelUtil.getValue(row.getCell(3));
			//判断机构名称是否为空
			if(StringUtil.isEmpty(organName)){
				addCount("organErrorCount", counts, 1);
				wrow.createCell(21).setCellValue("机构名称不能为空");
				return true;
			}
			
			
			
			//上机构名称
			String parentName = ExcelUtil.getValue(row.getCell(4));
			//判断上级机构名称是否为空
			if(StringUtil.isEmpty(parentName)){
				addCount("organErrorCount", counts, 1);
//				wsheet.addCell(new Label(19, i, "上级机构名称不能为空"));
				wrow.createCell(21).setCellValue("上级机构名称不能为空");
				return true;
			}
			
			//判断上级机构是否存在
//			parentOrgan = appo.getOrganByOrganName(parentName);
			
			String organModel = ExcelUtil.getValue(row.getCell(1));
			String organModelValue = "1"; 
			String organTypeCon = "2"; 
			if("BKD".equals(organModel)) {
				organModelValue = "1"; 
			} else if("TD".equals(organModel)){
				organTypeCon = "1"; 
				organModelValue = "1,2,3";
			} else {
				organModelValue = "1,2";
			}
			String whereSql = " where o.isrepeal = 0 and o.organname='"+ parentName+ "' and o.organType="+organTypeCon+" and o.organModel in ("+organModelValue+")";
			List<Organ> organs = appo.getOrganByWhere(whereSql);
			if(organs==null || organs.size() < 1){
				addCount("organErrorCount", counts, 1);
				String error = "上级机构不存在";
//				wsheet.addCell(new Label(19, i, error));
				wrow.createCell(21).setCellValue(error);
				return true;
			} else {
				parentOrgan = organs.get(0);
			}
			
			//判断机构是否存在
			if(!StringUtil.isEmpty(organOeCode)){
				organ = appo.getByOecodeAndParentId(organOeCode, parentOrgan.getId());
			}
			if(organ!=null){
				isOrganAlreadyExists = true;
			} else {
				try {
					organ = appo.getOrganByNameAndParentId(parentOrgan.getId(),organName);
					if(organ != null) {
						isOrganAlreadyExists = true;
					}
				} catch (Exception e) {
					isOrganAlreadyExists = false;
				}
				
			}
			
			//是否是上级机构的管辖权限
			UserVisit userVisit = auv.findVisitOrganByUidAndOid(uploadLog.getMakeId(), parentOrgan.getId());
			if(userVisit == null) {
				addCount("organErrorCount", counts, 1);
				String error = "当前用户没有上级机构 "+parentOrgan.getOrganname()+" 的管辖权限";
//				wsheet.addCell(new Label(19, i, error));
				wrow.createCell(21).setCellValue(error);
				return true;
			}
			
			if(!isOrganAlreadyExists) {
				//机构类型
				String organType = ExcelUtil.getValue(row.getCell(0));
				if(StringUtil.isEmpty(organType)){
					addCount("organErrorCount", counts, 1);
//					wsheet.addCell(new Label(19, i, "机构类型不能为空"));
					wrow.createCell(21).setCellValue("机构类型不能为空");
					return true;
				}
				
				//机构类别
//				String organModel = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
				if(StringUtil.isEmpty(organModel)){
					addCount("organErrorCount", counts, 1);
//					wsheet.addCell(new Label(19, i, "机构类别不能为空"));
					wrow.createCell(21).setCellValue("机构类别不能为空");
					return true;
				}
				
				OrganType orgType = OrganType.parseByName(organType);
				if(orgType == null) {
					addCount("organErrorCount", counts, 1);
//					wsheet.addCell(new Label(19, i, "机构类型选择错误"));
					wrow.createCell(21).setCellValue("机构类型选择错误");
					return true;
				} 
				organTypeValue = orgType.getValue();
				if(orgType == OrganType.Plant) {
					PlantType plantType = PlantType.parseByName(organModel);
					if(plantType == null) {
						addCount("organErrorCount", counts, 1);
//						wsheet.addCell(new Label(19, i, "工厂类别选择错误"));
						wrow.createCell(21).setCellValue("工厂类别选择错误");
						return true;
					}
					modelTypeValue = plantType.getValue();
				} else {
					DealerType dealerType = DealerType.parseByName(organModel);
					if(dealerType == null) {
						addCount("organErrorCount", counts, 1);
//						wsheet.addCell(new Label(19, i, "经销商类别选择错误"));
						wrow.createCell(21).setCellValue("经销商类别选择错误");
						return true;
					}
					modelTypeValue = dealerType.getValue();
					
					// 判断是否关键零售商
					if (organTypeValue.equals(OrganType.Dealer.getValue()) && !modelTypeValue.equals(DealerType.PD.getValue())) {
						String keyR = ExcelUtil.getValue(row.getCell(19));
						
						isKeyRetailer = this.parseKeyRetailer(keyR);
					}
				}
				
				String needAudit = ExcelUtil.getValue(row.getCell(20));
				if("是".equals(needAudit)) {
					isNeedAudit = false;
				}
				
				
				if(OrganType.Dealer.getValue().equals(parentOrgan.getOrganType()) && DealerType.PD.getValue().equals(parentOrgan.getOrganModel())) {
					if(OrganType.Plant.getValue().equals(organTypeValue) 
							|| (OrganType.Dealer.getValue().equals(organTypeValue) && DealerType.PD.getValue().equals(modelTypeValue))) {
						addCount("organErrorCount", counts, 1);
//						wsheet.addCell(new Label(19, i, "经销商类别选择错误,PD只能导入BKD或BKR的信息"));
						wrow.createCell(21).setCellValue("经销商类别选择错误,PD只能导入BKD或BKR的信息");
						return true;
					}
				}
				
				//判断省市区
				String organProvince = ExcelUtil.getValue(row.getCell(5));
				if(StringUtil.isEmpty(organProvince)){
					
//					wsheet.addCell(new Label(19, i, "机构所属省份不能为空"));
					wrow.createCell(21).setCellValue("机构所属省份不能为空");
					return true;
				}
				if("内蒙古自治区东".equals(organProvince)) {
					organProvince = "内蒙古自治区(东)";
				} else if("内蒙古自治区西".equals(organProvince)) {
					organProvince = "内蒙古自治区(西)";
				} else if(organProvince.indexOf("省直辖县级行政区划") != -1) {
					organProvince = "省直辖县级行政区划";
				}
				CountryArea op = countryAreas.get(organProvince);
				if(op == null) {
					op = appca.getProvinceByName(organProvince);
				}
				if(op == null){
					addCount("organErrorCount", counts, 1);
//					wsheet.addCell(new Label(19, i, "省 ["+organProvince+"] 不存在"));
					wrow.createCell(21).setCellValue("省 ["+organProvince+"] 不存在");
					return true;
				} else {
					countryAreas.put(organProvince, op);
				}
				String organCity = ExcelUtil.getValue(row.getCell(6));
				CountryArea oc = null;
				if(!StringUtil.isEmpty(organCity)) {
					oc = countryAreas.get(organProvince+organCity);
					if(oc == null) {
						oc = appca.getCountryAreaByAreaNameAndParentId(organCity, op.getId());
					}
					if(oc == null){
						addCount("organErrorCount", counts, 1);
						String error = "区域信息错误：["+organProvince+"] 不存在城市 [" + organCity +"]";
//						wsheet.addCell(new Label(19, i, error));
						wrow.createCell(21).setCellValue(error);
						return true;
					} else {
						countryAreas.put(organProvince+organCity, oc);
					}
				} else {
					addCount("organErrorCount", counts, 1);
//					wsheet.addCell(new Label(19, i, "机构所属市不能为空"));
					wrow.createCell(21).setCellValue("机构所属市不能为空");
					return true;
				}
				CountryArea oa = null;
				if(oc != null) {
					String organArea = ExcelUtil.getValue(row.getCell(7));
					if(!StringUtil.isEmpty(organArea)) {
						oa = countryAreas.get(organProvince+organCity+organArea);
						if(oa == null) {
							oa = appca.getCountryAreaByAreaNameAndParentId(organArea, oc.getId());
						}
						if(oa == null){
							addCount("organErrorCount", counts, 1);
							String error = "区域信息错误：["+organCity+"] 不存在区县 [ " + organArea +"]";
//							wsheet.addCell(new Label(19, i, error));
							wrow.createCell(21).setCellValue(error);
							return true;
						} else {
							countryAreas.put(organProvince+organCity+organArea, oa);
						}
					}
				}
				
				String organAddress = ExcelUtil.getValue(row.getCell(8));
				if(StringUtil.isEmpty(organAddress)){
					addCount("organErrorCount", counts, 1);
					String error = "机构地址不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					return true;
				}
				
				//默认联系人
				String olName = ExcelUtil.getValue(row.getCell(9));
				//联系电话
				String olTel = ExcelUtil.getValue(row.getCell(10));
				//判断联系人是否为空 如果为空则使用默认联系人
				if(StringUtil.isEmpty(olName)){
					addCount("organErrorCount", counts, 1);
					String error = "机构联系人不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					return true;
				}
				//判断联系人是否为空 如果为空则取默认联系电话
				if(StringUtil.isEmpty(olTel)){
					addCount("organErrorCount", counts, 1);
					String error = "机构联系电话不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					return true;
				}
				
				if(appo.isUsersExists(olTel, null)) {
					addCount("organErrorCount", counts, 1);
					String error = "已存在手机号为["+olTel+"]的客户";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					return true;
				}
				
				String sysid = app.getOcodeByParent(parentOrgan.getSysid());
				String id = MakeCode.getExcIDByRandomTableName("organ", 4, "");
				organ = new Organ();
				//编号
				organ.setId(id.trim());
				//系统编号
				organ.setSysid(sysid);
				//内部编号
				if(!StringUtil.isEmpty(organOeCode)){
					organ.setOecode(organOeCode);
				} else {
					organ.setOecode(id);
				}
				
				//机构名称
				organ.setOrganname(organName);
				organ.setRate(1);
				organ.setPrompt(1);
				organ.setPprompt(1);
				organ.setIsrepeal(0);
				organ.setCity(1);
				organ.setProvince(1);
				organ.setAreas(1);
				organ.setOaddr(organAddress);
				//上级机构id
				organ.setParentid(parentOrgan.getId());
				organ.setRank(parentOrgan.getRank()==null?null:parentOrgan.getRank()+ 1);
				organ.setLogo("");
				organ.setOrganType(organTypeValue);
				organ.setOrganModel(modelTypeValue);
				organ.setIsKeyRetailer(isKeyRetailer);
				organ.setOmobile(olTel);
				
				organ.setProvince(op.getId());
				organ.setCity(oc != null ? oc.getId() : 1);
				organ.setAreas(oa != null ? oa.getId() : 1);
				if(organ.getOrganType() == OrganType.Dealer.getValue() 
						&& organ.getOrganModel() != DealerType.PD.getValue()) {
					if(isNeedAudit) {
						organ.setValidatestatus(YesOrNo.NO.getValue());
					} else {
						organ.setValidatestatus(YesOrNo.YES.getValue());
					}
					
				}
				//创建时间
				organ.setCreationTime(DateUtil.getCurrentDate());
							
				oLinkman.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("olinkman", 0, "")));
				//姓名
				oLinkman.setName(olName);
				//电话
				oLinkman.setMobile(olTel);
				oLinkman.setCid(organ.getId());
				oLinkman.setMakeorganid(uploadLog.getMakeOrganId());
				oLinkman.setMakeid(uploadLog.getMakeId());
				oLinkman.setMakedate(DateUtil.getCurrentDate());
				oLinkman.setIsmain(YesOrNo.NO.getValue());
			} else {
//				wsheet.addCell(new Label(21, i, "机构已存在"));
				wrow.createCell(21).setCellValue("机构已存在");
				addCount("organErrorCount", counts, 1);
			}
			Set<String> warehouseNameSet = new HashSet<String>();
			while(i < endRowNo) {
				//仓库名称
				String warehouseName = ExcelUtil.getValue(row.getCell(11));
				if(StringUtil.isEmpty(warehouseName)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库名称不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				
				if(warehouseNameSet.contains(warehouseName)) {
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库名称重复";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				} else {
					warehouseNameSet.add(warehouseName);
				}
				
				//仓库内部编码
				String warehouseNcCode = ExcelUtil.getValue(row.getCell(12));
				
				if(isOrganAlreadyExists) {
					if(appWarehouse.isWarehousExists(organ.getId(), warehouseName, warehouseNcCode)) {
//						wsheet.addCell(new Label(19, i, "仓库已存在"));
						wrow.createCell(21).setCellValue("仓库已存在");
						addCount("warehouseErrorCount", counts, 1);
						i++;
						continue;
					}
				}
				
				//检查省市区信息
				String wProvince = ExcelUtil.getValue(row.getCell(13));
				if(StringUtil.isEmpty(wProvince)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库所属省份不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				if("内蒙古自治区东".equals(wProvince)) {
					wProvince = "内蒙古自治区(东)";
				} else if("内蒙古自治区西".equals(wProvince)) {
					wProvince = "内蒙古自治区(西)";
				} else if(wProvince.indexOf("省直辖县级行政区划") != -1) {
					wProvince = "省直辖县级行政区划";
				}
				CountryArea wp = countryAreas.get(wProvince);
				if(wp == null) {
					wp = appca.getProvinceByName(wProvince);
				}
				if(wp == null){
					addCount("warehouseErrorCount", counts, 1);
					String error = "省 ["+wProvince+"] 不存在";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				} else {
					countryAreas.put(wProvince, wp);
				}
				String wCity = ExcelUtil.getValue(row.getCell(14));
				CountryArea wc = null;
				if(!StringUtil.isEmpty(wCity)) {
					wc = countryAreas.get(wProvince+wCity);
					if(wc == null) {
						wc = appca.getCountryAreaByAreaNameAndParentId(wCity, wp.getId());
					}
					if(wc == null){
						addCount("warehouseErrorCount", counts, 1);
						String error = "区域信息错误：["+wProvince+"] 不存在城市  [" + wCity + "]";
//						wsheet.addCell(new Label(19, i, error));
						wrow.createCell(21).setCellValue(error);
						i++;
						continue;
					} else {
						countryAreas.put(wProvince+wCity, wc);
					}
				} else {
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库所属市不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				CountryArea wa = null;
				if(wc != null) {
					String wArea = ExcelUtil.getValue(row.getCell(15));
					if(!StringUtil.isEmpty(wArea)) {
						wa = countryAreas.get(wProvince+wCity+wArea);
						if(wa == null) {
							wa = appca.getCountryAreaByAreaNameAndParentId(wArea, wc.getId());
						}
						if(wa == null){
							addCount("warehouseErrorCount", counts, 1);
							String error = "区域信息错误：["+wCity+"] 不存在区县 [" + wArea + "]";
//							wsheet.addCell(new Label(19, i, error));
							wrow.createCell(21).setCellValue(error);
							i++;
							continue;
						} else {
							countryAreas.put(wProvince+wCity+wArea, wa);
						}
					}
				}
				
				
				//仓库地址
				String warehouseAddr = ExcelUtil.getValue(row.getCell(16));
				if(StringUtil.isEmpty(warehouseAddr)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库地址不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				
				//仓库联系人名称
				String warehouselName = ExcelUtil.getValue(row.getCell(17));
				if(StringUtil.isEmpty(warehouselName)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库联系人不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				
				//仓库联系电话
				String warehouselTel = ExcelUtil.getValue(row.getCell(18));
				if(StringUtil.isEmpty(warehouselTel)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库联系电话不能为空";
//					wsheet.addCell(new Label(19, i, error));
					wrow.createCell(21).setCellValue(error);
					i++;
					continue;
				}
				//默认仓库
				Warehouse warehouse = new Warehouse();
				warehouse.setId(MakeCode.getExcIDByRandomTableName("warehouse", 1, ""));
				//仓库名默认与机构名相同 + "默认仓库"
				warehouse.setWarehousename(warehouseName);
				//仓库位置
				warehouse.setWarehouseaddr(warehouseAddr);
				//仓库联系人
				warehouse.setUsername(warehouselName);
				//相关的组织关联
				warehouse.setMakeorganid(organ.getId());
				//设置自动签收仓库
				if(organ.getOrganType() == OrganType.Dealer.getValue() 
						&& organ.getOrganModel() == DealerType.PD.getValue()) {
					warehouse.setIsautoreceive(YesOrNo.NO.getValue());
				} else {
					warehouse.setIsautoreceive(YesOrNo.YES.getValue());
				}
				warehouse.setIsMinusStock(YesOrNo.NO.getValue());
				//设置可用
				warehouse.setUseflag(YesOrNo.YES.getValue());
				if(!StringUtil.isEmpty(warehouseNcCode)){
					warehouse.setNccode(warehouseNcCode);
				} else {
					warehouse.setNccode(warehouse.getId());
				}
				warehouse.setWarehouseproperty(0);
				
				warehouse.setWarehousetel(warehouselTel);
				warehouse.setUsername(warehouselName);
				warehouse.setUserid(0);
				warehouse.setProvince(wp.getId());
				warehouse.setCity(wc != null ? wc.getId() : 1);
				warehouse.setAreas(wa != null ? wa.getId() : 1);
				warehouse.setCreationTime(DateUtil.getCurrentDate());
				warehouses.add(warehouse);
				
				//默认仓库的库位
				WarehouseBit bit = new WarehouseBit();
				bit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_bit", 1, "")));
				bit.setWid(warehouse.getId());
				//默认库位000
				bit.setWbid("000");
				warehouseBits.add(bit);
				i++;
			}
			if(warehouses.size() == 0 && isOrganAlreadyExists) {
				return true;
			}
			if(!isOrganAlreadyExists) {
				addOrganAndWarehouse(organ, parentOrgan, oLinkman, warehouses, warehouseBits);
				this.processKeyRetailer(organ);
				addCount("organSuccessCount", counts, 1);
				addCount("warehouseSuccessCount", counts, warehouses.size());
			} else {
				addWarehouse(organ, parentOrgan, oLinkman, warehouses, warehouseBits);
				addCount("warehouseSuccessCount", counts, warehouses.size());
			}
		} catch (Exception e) {
			logger.error("添加第"+i+"行时发生异常", e);
			try {
				addCount("warehouseErrorCount", counts, 1);
//				wsheet.addCell(new Label(19, i, "添加时发生异常,请检查系统中是否已存在该机构,若不存在请联系管理员" + e.getMessage()));
				wrow.createCell(21).setCellValue("添加时发生异常,请检查系统中是否已存在该机构,若不存在请联系管理员" + e.getMessage());
			} catch (Exception e1) {
				logger.error("修改excel文档时发生异常", e1);
			} 
			return true;
		}
		
		
		return false;
	}

	private void addCount(String key, Map<String, Integer> counts, int i) {
		Integer count = counts.get(key);
		if(count != null) {
			counts.put(key, count + i);
		} else {
			counts.put(key, i);
		}
	}

	private void addWarehouse(Organ organ, Organ parentOrgan,
			Olinkman oLinkman, List<Warehouse> warehouses,
			List<WarehouseBit> warehouseBits) throws Exception {
		for(Warehouse warehouse : warehouses) {
			appWarehouse.addWarehouse(warehouse);
		}
		for(WarehouseBit warehouseBit : warehouseBits) {
			appWarehouse.addWarehouseBit(warehouseBit);
		}
		// 增加用户(管辖上级机构)的业务往来权限
		List<UserVisit> userVisitList  = auv.queryByOrganId(parentOrgan.getId());
		for(UserVisit userVisit : userVisitList){
			addWarehouseVisit(userVisit.getUserid(), organ.getId());
		}
		
	}

	private void addWarehouseVisit(Integer userId,String oid) throws Exception {
		//增加业务往来仓库
		awv.addVWByOid(oid, userId+"");
		//更新make_conf
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}

	private void addOrganAndWarehouse(Organ organ, Organ parentOrgan,
		Olinkman oLinkman, List<Warehouse> warehouses,
		List<WarehouseBit> warehouseBits) throws Exception {
		appo.AddOrgan(organ);
		appLinkMan.addOlinkman(oLinkman);
		//机构上架所有产品
		aop.addAllProductToOrganProduct(organ.getId());
		//更新make_conf
		appmc.updMakeConf("organ_product","organ_product");
		for(Warehouse warehouse : warehouses) {
			appWarehouse.addWarehouse(warehouse);
		}
		for(WarehouseBit warehouseBit : warehouseBits) {
			appWarehouse.addWarehouseBit(warehouseBit);
		}
		// 增加用户(管辖上级机构)的业务往来权限
		List<UserVisit> userVisitList  = auv.queryByOrganId(parentOrgan.getId());
		for(UserVisit userVisit : userVisitList){
			addOrganVisit(userVisit.getUserid(), organ.getId());
		}
		//添加进货关系
		if(OrganType.Dealer.getValue().equals(organ.getOrganType())
				&& !DealerType.PD.getValue().equals(organ.getOrganModel())) {
			addSTransferRelation(organ.getId(), parentOrgan.getId());
		}
		
	}

	private void addSTransferRelation(String organId, String oppOrganId) throws Exception {
		STransferRelation s = new STransferRelation();
		s.setModifieDate(new Date());
		s.setOrganizationId(organId);
		s.setOppOrganId(oppOrganId);
		appstr.addSTransferRelation(s);
	}

	/**
	 * 新增业务往来权限
	 * @throws Exception 
	 */
	private void addOrganVisit(Integer userId,String oid) throws Exception{
		OrganVisit organVisit = new OrganVisit();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
		organVisit.setId(id);
		organVisit.setUserid(Integer.valueOf(userId));
		organVisit.setVisitorgan(oid);
		aov.SaveOrganVisit(organVisit);
		//增加业务往来仓库
		awv.addVWByOid(oid, userId+"");
		//更新make_conf
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
	
	/**
	 * 转换， 1:是关键经销商，0:否
	 * @param v
	 * @return
	 */
	private Integer parseKeyRetailer(String v) {
		Integer ret = null;
		if ("是".equals(v)) {
			ret = 1;
		} else if ("否".equals(v)) {
			ret = 0;
		}
		return ret;
	}
	
	/**
	 * 如果为关键经销商，创建积分账户、用户
	 * @param o
	 * @throws Exception
	 */
	private void processKeyRetailer(Organ o) throws Exception {
		if (o.getIsKeyRetailer() != null && o.getIsKeyRetailer() == 1) {
			String str = "";
			if (o.getOrganModel() == DealerType.BKD.getValue()) {
				str = "BKD" + o.getId();
			} else {
				str = "BKR" + o.getId();
			}
			SBonusAccount sac = acc.getByOrganId(o.getId());
			if (sac == null) {
				SBonusAccount s = new SBonusAccount();
				s.setName(str);
				s.setCompanyName(o.getOrganname());
				s.setMobile(o.getOmobile());
				s.setOrganId(o.getId());
				s.setAreas(o.getAreas());
				s.setProvince(o.getProvince());
				s.setCity(o.getCity());
				s.setAddress(o.getOaddr());
				acc.addSBonusAccount(s);
			} 
			/*List list = appusers.getUsersByMakeOrganId(o.getId());
			if (list == null || list.size() == 0) {
				Users user = new Users();
				Integer uid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
				user.setUserid(uid);
				user.setLoginname(str);
				user.setPassword(Encrypt.getSecret("a123456", 1));
				user.setApprovepwd(Encrypt.getSecret("aaaa", 1));
				user.setStatus(1);
				user.setMakeorganid(o.getId());
				user.setMobile(o.getOmobile());
				appusers.InsertUsers(user);
			}*/ 
		}
	}
	
}




