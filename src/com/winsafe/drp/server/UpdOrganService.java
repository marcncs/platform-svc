package com.winsafe.drp.server;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.ExcelUtil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.OrganUploadLog;
import com.winsafe.sap.util.FileUploadUtil;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class UpdOrganService {
	
	private static Logger logger = Logger.getLogger(UpdOrganService.class);
	
	AppOrgan appo = new AppOrgan();
	AppMakeConf appmc = new AppMakeConf();
	AppOlinkMan appLinkMan = new AppOlinkMan();
	AppWarehouse appWarehouse = new AppWarehouse();
	private AppCountryArea appca = new AppCountryArea();
	private OrganUploadLog uploadLog = null;
	
	private AppUsers appusers = new AppUsers();
	
	Map<String, CountryArea> countryAreas = new HashMap<String, CountryArea>();
	Map<String, Organ> organMap = new HashMap<String, Organ>();
	Map<String, Warehouse> warehouseMap = new HashMap<String, Warehouse>();
	Map<String, Integer> counts = new HashMap<String, Integer>();

	public void execute(OrganUploadLog uploadLog, File file)
			throws Exception {
		this.uploadLog = uploadLog;
		
		try {
				
				Workbook wb = WorkbookFactory.create(new FileInputStream(file));
				Sheet sheet = wb.getSheetAt(0);
				int rownum = sheet.getLastRowNum()+1;
				
				File tempFile = File.createTempFile("tempExcel", ".xls");
				
				XSSFWorkbook wwb = new XSSFWorkbook(new FileInputStream(file));
				XSSFSheet wsheet = wwb.getSheetAt(0);
				Row row = null;
				// 初始化数据
				initData(sheet);
				
				for (int i = 1; i < rownum; i++) {
					Row wrow = wsheet.getRow(i);
					row = sheet.getRow(i);
					if(updOrgan(row, wrow)) {
						HibernateUtil.commitTransaction();
					} else {
						HibernateUtil.rollbackTransaction();
					}
					if(updWarehouse(row, wrow)) {
						HibernateUtil.commitTransaction();
					} else {
						HibernateUtil.rollbackTransaction();
					}
				}
				Row wrow = wsheet.createRow(rownum);
				wrow.createCell(17).setCellValue("上传机构资料成功,本次成功更新机构 :"+(counts.get("organSuccessCount")!=null?counts.get("organSuccessCount"):0)+"条! 失败:"+(counts.get("organErrorCount")!=null?counts.get("organErrorCount"):0)+"条! 成功更新仓库:"+(counts.get("warehouseSuccessCount")!=null?counts.get("warehouseSuccessCount"):0)+"条! 失败："+(counts.get("warehouseErrorCount")!=null?counts.get("warehouseErrorCount"):0)+"条!");
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
	
	private boolean updWarehouse(Row row, Row wrow) {
		try {
			String warehouseId = ExcelUtil.getValue(row.getCell(9));
			if(!StringUtil.isEmpty(warehouseId)){
				Warehouse warehouse = warehouseMap.get(warehouseId);
				if(warehouse == null) {
					addCount("warehouseErrorCount", counts, 1);
					wrow.createCell(18).setCellValue("编号为"+warehouseId+"的仓库不存在");
					return false;
				}
				
				//更新仓库名称
				String warehouseName = ExcelUtil.getValue(row.getCell(10));
				if(!StringUtil.isEmpty(warehouseName) && !warehouse.getWarehousename().equals(warehouseName)) {
					warehouse.setWarehousename(warehouseName);
				}
				
				//更新机构省市区
				String province = ExcelUtil.getValue(row.getCell(11));
				String city = ExcelUtil.getValue(row.getCell(12));
				String area = ExcelUtil.getValue(row.getCell(13));
				if(!StringUtil.isEmpty(province)
						&& StringUtil.isEmpty(city)
						&& StringUtil.isEmpty(area)) {
					if("内蒙古自治区东".equals(province)) {
						province = "内蒙古自治区(东)";
					} else if("内蒙古自治区西".equals(province)) {
						province = "内蒙古自治区(西)";
					} else if(province.indexOf("省直辖县级行政区划") != -1) {
						province = "省直辖县级行政区划";
					}
					CountryArea op = countryAreas.get(province);
					if(op == null) {
						op = appca.getProvinceByName(province);
					}
					if(op == null){
						addCount("organErrorCount", counts, 1);
						wrow.createCell(18).setCellValue("省 ["+province+"] 不存在");
						return false;
					} else {
						countryAreas.put(province, op);
					}
					
					CountryArea oc = null;
					if(!StringUtil.isEmpty(city)) {
						oc = countryAreas.get(province+city);
						if(oc == null) {
							oc = appca.getCountryAreaByAreaNameAndParentId(city, op.getId());
						}
						if(oc == null){
							addCount("organErrorCount", counts, 1);
							String error = "区域信息错误：["+province+"] 不存在城市 [" + city +"]";
							wrow.createCell(18).setCellValue(error);
							return false;
						} else {
							countryAreas.put(province+city, oc);
						}
					} 
					CountryArea oa = null;
					if(oc != null) {
						
						if(!StringUtil.isEmpty(area)) {
							oa = countryAreas.get(province+city+area);
							if(oa == null) {
								oa = appca.getCountryAreaByAreaNameAndParentId(area, oc.getId());
							}
							if(oa == null){
								addCount("organErrorCount", counts, 1);
								String error = "区域信息错误：["+city+"] 不存在区县 [ " + area +"]";
								wrow.createCell(18).setCellValue(error);
								return false;
							} else {
								countryAreas.put(province+city+area, oa);
							}
						}
					}
					warehouse.setProvince(op.getId());
					warehouse.setCity(oc.getId());
					warehouse.setAreas(oa.getId());
				}
				
				String warehouseAddress = ExcelUtil.getValue(row.getCell(14));
				if(!StringUtil.isEmpty(warehouseAddress)){
					warehouse.setWarehouseaddr(warehouseAddress);
				}
				//更新机构联系人
				//默认联系人
				String olName = ExcelUtil.getValue(row.getCell(15));
				if(!StringUtil.isEmpty(olName)){
					warehouse.setUsername(olName);
				}
				String olTel = ExcelUtil.getValue(row.getCell(16));
				if(!StringUtil.isEmpty(olTel)){
					warehouse.setWarehousetel(olTel);
				}
				appWarehouse.updWarehouse(warehouse);
				//联系电话
//				HibernateUtil.commitTransaction();
				addCount("warehouseSuccessCount", counts, 1);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			addCount("warehouseErrorCount", counts, 1);
			logger.error("",e);
//			HibernateUtil.rollbackTransaction();
			wrow.createCell(18).setCellValue("更新仓库失败:"+e.getMessage());
			return false;
		}
	}

	private boolean updOrgan(Row row, Row wrow) throws Exception {
		try {
			String organId = ExcelUtil.getValue(row.getCell(0));
			if(StringUtil.isEmpty(organId)){
				addCount("organErrorCount", counts, 1);
				wrow.createCell(17).setCellValue("机构编号不能为空");
				return false;
			}
			//检查机构是否存在
			Organ organ = organMap.get(organId);
			if(organ == null) {
				addCount("organErrorCount", counts, 1);
				wrow.createCell(17).setCellValue("编号为"+organId+"的机构不存在");
				return false;
			}
			
			//机构类型更新
			String organType = ExcelUtil.getValue(row.getCell(1));
			if(!StringUtil.isEmpty(organType)) {
				DealerType type = DealerType.parseByName(organType);
				if(type != null && !type.getValue().equals(organ.getOrganModel())) {
					if(DealerType.PD.getValue().equals(type.getValue())
							|| DealerType.PD.getValue().equals(organ.getOrganModel()) 
							|| OrganType.Plant.getValue().equals(organ.getOrganType())){
						addCount("organErrorCount", counts, 1);
						wrow.createCell(17).setCellValue("工厂,TD与其他经销商机构类型不能互转");
						return false;
					} else {
						organ.setOrganModel(type.getValue());
					}
				}
			}
			
			//更新机构名称
			String organName = ExcelUtil.getValue(row.getCell(2));
			if(!StringUtil.isEmpty(organName) && !organ.getOrganname().equals(organName)) {
				organ.setOrganname(organName);
			}
			
			//更新机构省市区
			String province = ExcelUtil.getValue(row.getCell(3));
			String city = ExcelUtil.getValue(row.getCell(4));
			String area = ExcelUtil.getValue(row.getCell(5));
			if(!StringUtil.isEmpty(province)
					&& !StringUtil.isEmpty(city)
					&& !StringUtil.isEmpty(area)) {
				if("内蒙古自治区东".equals(province)) {
					province = "内蒙古自治区(东)";
				} else if("内蒙古自治区西".equals(province)) {
					province = "内蒙古自治区(西)";
				} else if(province.indexOf("省直辖县级行政区划") != -1) {
					province = "省直辖县级行政区划";
				}
				CountryArea op = countryAreas.get(province);
				if(op == null) {
					op = appca.getProvinceByName(province);
				}
				if(op == null){
					addCount("organErrorCount", counts, 1);
					wrow.createCell(17).setCellValue("省 ["+province+"] 不存在");
					return false;
				} else {
					countryAreas.put(province, op);
				}
				
				CountryArea oc = null;
				if(!StringUtil.isEmpty(city)) {
					oc = countryAreas.get(province+city);
					if(oc == null) {
						oc = appca.getCountryAreaByAreaNameAndParentId(city, op.getId());
					}
					if(oc == null){
						addCount("organErrorCount", counts, 1);
						String error = "区域信息错误：["+province+"] 不存在城市 [" + city +"]";
						wrow.createCell(17).setCellValue(error);
						return false;
					} else {
						countryAreas.put(province+city, oc);
					}
				} 
				CountryArea oa = null;
				if(oc != null) {
					
					if(!StringUtil.isEmpty(area)) {
						oa = countryAreas.get(province+city+area);
						if(oa == null) {
							oa = appca.getCountryAreaByAreaNameAndParentId(area, oc.getId());
						}
						if(oa == null){
							addCount("organErrorCount", counts, 1);
							String error = "区域信息错误：["+city+"] 不存在区县 [ " + area +"]";
							wrow.createCell(17).setCellValue(error);
							return false;
						} else {
							countryAreas.put(province+city+area, oa);
						}
					}
				}
				organ.setProvince(op.getId());
				organ.setCity(oc.getId());
				organ.setAreas(oa.getId());
			}
			
			String organAddress = ExcelUtil.getValue(row.getCell(6));
			if(!StringUtil.isEmpty(organAddress)){
				organ.setOaddr(organAddress);
			}
			
			//默认联系人
			String olName = ExcelUtil.getValue(row.getCell(7));
			//联系电话
			String olTel = ExcelUtil.getValue(row.getCell(8));
			//更新机构联系人
			if(!StringUtil.isEmpty(olTel) || !StringUtil.isEmpty(olName)) {
				//更新联系人
				Olinkman oLinkman = appLinkMan.findOlinkmanByCidAndMobile(organ.getId(), organ.getOmobile());
				if(oLinkman == null) {
					oLinkman = appLinkMan.findOlinkmanByCid(organ.getId());
				}
				//登陆用户
				Users users = null;
				if(organ.getIsKeyRetailer() !=null && organ.getIsKeyRetailer() == 1) {
					users = appusers.getUsersByMobile(organ.getOmobile());
				}
				//更新手机号 
				if(!StringUtil.isEmpty(olTel) && !olTel.equals(organ.getOmobile())) {
					if(appo.isUsersExists(olTel, organ.getId())) {
						addCount("organErrorCount", counts, 1);
						wrow.createCell(17).setCellValue("已存在手机号为["+olTel+"]的客户");
						return false;
					}
					if(oLinkman != null) {
						oLinkman.setMobile(olTel);
					} else {
						addOlinkMan(organ, olName, olTel);
					}
					if(users != null) {
						users.setMobile(olTel);
					}
					organ.setOmobile(olTel);
				}
				
				if(!StringUtil.isEmpty(olName)) {
					if(oLinkman != null) {
						oLinkman.setName(olName);
					}
					if(users != null) {
						users.setRealname(olName);
					}
				}
				
				if(oLinkman != null) {
					appLinkMan.updOlinkman(oLinkman);
				}
				if(users != null) {
					appusers.updUsers(users);
				}
			}
			
			/*if(!StringUtil.isEmpty(olTel)){
				//当前号码不一致时更新
				if(!olTel.equals(organ.getOmobile())) {
					if(appo.isUsersExists(olTel, organ.getId())) {
						addCount("organErrorCount", counts, 1);
						wrow.createCell(17).setCellValue("已存在手机号为["+olTel+"]的客户");
						return false;
					}
					//更新联系人
					Olinkman oLinkman = appLinkMan.findOlinkmanByCidAndMobile(organ.getId(), organ.getOmobile());
					if(oLinkman != null) {
						oLinkman.setMobile(olTel);
						if(!StringUtil.isEmpty(olName)&&!olName.equals(oLinkman.getName())) {
							oLinkman.setName(olName);
						}
						appLinkMan.updOlinkman(oLinkman);
					} else {
						addOlinkMan(organ,olName,olTel);
					}
					//更新登陆用户
					if(organ.getIsKeyRetailer() !=null && organ.getIsKeyRetailer() == 1) {
						Users users = appusers.getUsersByMobile(organ.getOmobile());
						if(users != null) {
							users.setMobile(olTel);
							if(!StringUtil.isEmpty(olName)&&!olName.equals(users.getRealname())) {
								users.setRealname(olName);
							}
							appusers.updUsers(users);
						}
					}
				}
				organ.setOmobile(olTel);
			}*/
			appo.updOrgan(organ);
//			HibernateUtil.commitTransaction();
			addCount("organSuccessCount", counts, 1);
			return true;
		} catch (Exception e) {
			addCount("organErrorCount", counts, 1);
			logger.error("",e);
//			HibernateUtil.rollbackTransaction();
			wrow.createCell(17).setCellValue("更新机构失败:"+e.getMessage());
			return false;
		}
	}

	private void addOlinkMan(Organ organ, String olName, String olTel) throws Exception {
		Olinkman oLinkman = new Olinkman();
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
		appLinkMan.addOlinkman(oLinkman);
	}

	private void initData(Sheet sheet) throws Exception {
		int rownum = sheet.getLastRowNum()+1;
		int oidCount = 0;
		int widCount = 0;
		List<Organ> organList = new ArrayList<Organ>();
		List<Warehouse> warehouseList = new ArrayList<Warehouse>();
		StringBuffer oids = new StringBuffer();
		StringBuffer wids = new StringBuffer();
		for (int i = 1; i < rownum; i++) {
			Row row = sheet.getRow(i);
			String organId = ExcelUtil.getValue(row.getCell(0));
			if(!StringUtil.isEmpty(organId)){
				oids.append(",'"+organId+"'");
				oidCount++;
				if(oidCount%1000 == 0) {
					organList.addAll(appo.getOrganByIds(oids.substring(1)));
					oids = new StringBuffer();
				}
			}
			
			String warehouseId = ExcelUtil.getValue(row.getCell(8));
			if(!StringUtil.isEmpty(warehouseId)){
				wids.append(",'"+warehouseId+"'");
				widCount++;
				if(widCount%1000 == 0) {
					warehouseList.addAll(appWarehouse.getWarehouseByIds(wids.substring(1)));
					wids = new StringBuffer();
				}
			}
			
		}
		if(oids.length() > 0) {
			organList.addAll(appo.getOrganByIds(oids.substring(1)));
		}
		if(wids.length() > 0) {
			warehouseList.addAll(appWarehouse.getWarehouseByIds(wids.substring(1)));
		}
		for(Organ organ : organList) {
			organMap.put(organ.getId(), organ);
		}
		for(Warehouse warehouse : warehouseList) {
			warehouseMap.put(warehouse.getId(), warehouse);
		}
	}

	private void addCount(String key, Map<String, Integer> counts, int i) {
		Integer count = counts.get(key);
		if(count != null) {
			counts.put(key, count + i);
		} else {
			counts.put(key, i);
		}
	}
	
}




