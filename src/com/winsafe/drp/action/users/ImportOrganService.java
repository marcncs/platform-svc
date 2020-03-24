package com.winsafe.drp.action.users;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet; 
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
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
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.metadata.YesOrNo;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportOrganService extends BaseAction {
	
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

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		String currentOrganName = null;
		Map<String, CountryArea> countryAreas = new HashMap<String, CountryArea>();
		
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount =0,SCount=0;
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {

				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				boolean hasError = false;
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet("Sheet1");
				int row = sheet.getRows();
				int startRowNo = 1;
				int endRowNo = 1; 
				Map<String, Integer> counts = new HashMap<String, Integer>();
				
				if(sheet.getRows() <= 1) {
					request.setAttribute("result", "上传文件处理失败，未从文件中读取到数据");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				String header = sheet.getCell(0, 0).getContents()==null ? null : sheet.getCell(0, 0).getContents().trim();
				if(StringUtil.isEmpty(header) || !header.trim().equals("机构类型") || sheet.getColumns() < 19) {
					request.setAttribute("result", "上传文件处理失败，请确认是否使用了正确的模板");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				
				File tempFile = File.createTempFile("tempExcel", ".xls");
				WritableWorkbook wwb = Workbook.createWorkbook(tempFile, wb);
				WritableSheet wsheet = wwb.getSheet(0);
				
				
//				WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
//				WritableSheet wsheet = wwb.getSheet(0);
				
				// 保存报错信息
				
				for (int i = 1; i < row; i++) {
					String organName = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
					if(StringUtil.isEmpty(organName)){
//						wsheet.addCell(new Label(19, i, "机构名称不能为空"));
						errMsg.append("第[" + i + "]行机构名称不能为空<br />");
						
						hasError = true;
					} 
				}
				
				if(!hasError) {
					for (int i = 1; i <= row; i++) {
						String organName = "";
						if(i != row) {
							organName = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
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
					wsheet.addCell(new Label(19, row, "上传机构资料成功,本次成功添加机构 :"+(counts.get("organSuccessCount")!=null?counts.get("organSuccessCount"):0)+"条! 失败:"+(counts.get("organErrorCount")!=null?counts.get("organErrorCount"):0)+"条! 成功添加仓库:"+(counts.get("warehouseSuccessCount")!=null?counts.get("warehouseSuccessCount"):0)+"条! 失败："+(counts.get("warehouseErrorCount")!=null?counts.get("warehouseErrorCount"):0)+"条!"));
				} else {
//					wsheet.addCell(new Label(19, row, "处理失败 :机构内部编码不能为空"));
					request.setAttribute("result", "文件上传失败,原因如下:<br /> "+errMsg.toString());
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				wb.close();
				wwb.write();
				wwb.close();
				try {
					OutputStream os = response.getOutputStream();
					response.reset();
					response.setHeader("content-disposition",
							"attachment; filename=ImportOrgan.xls");
					response.setContentType("application/msexcel");
					FileInputStream fis = new FileInputStream(tempFile);
					byte [] b = new byte[1024];
					int i = 0;
					while ( (i = fis.read(b)) > 0 )
					{
						os.write(b, 0, i);
					}
					fis.close();
					os.flush();
					os.close();
					if(tempFile.exists()) {
						tempFile.delete();
					}
				} catch (Exception e) {
					logger.error("error occurred when push xls file to client", e);
				} finally {
					response.flushBuffer();
				}
				DBUserLog.addUserLog(request, "");
			} 
			
		} catch (Exception e) {
			logger.error("error occurred when processing xls file", e);
			request.setAttribute("result", "处理文件时发生异常");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		return null;
	}
	
	private boolean addOrganWithWarehouse(Sheet sheet, int startRowNo, int endRowNo, Map<String, CountryArea> countryAreas, WritableSheet wsheet, Map<String, Integer> counts) {
		List<Warehouse> warehouses = new ArrayList<Warehouse>();
		List<WarehouseBit> warehouseBits = new ArrayList<WarehouseBit>();
		Integer organTypeValue = null;
		Integer modelTypeValue = null;
		boolean isOrganAlreadyExists = false;
		int i = startRowNo;
		Organ organ = null;
		Olinkman oLinkman = new Olinkman();
		Organ parentOrgan = null;
		
		try {
			//机构内部编码
			String organOeCode = sheet.getCell(2, i).getContents()==null ? null : sheet.getCell(2, i).getContents().trim();
			
			//机构名称
			String organName = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
			//判断机构名称是否为空
			if(StringUtil.isEmpty(organName)){
				addCount("organErrorCount", counts, 1);
				wsheet.addCell(new Label(19, i, "机构名称不能为空"));
				return true;
			}
			
			
			
			//上机构名称
			String parentName = sheet.getCell(4, i).getContents()==null ? null : sheet.getCell(4, i).getContents().trim();
			//判断上级机构名称是否为空
			if(StringUtil.isEmpty(parentName)){
				addCount("organErrorCount", counts, 1);
				wsheet.addCell(new Label(19, i, "上级机构名称不能为空"));
				return true;
			}
			
			//判断上级机构是否存在
//			parentOrgan = appo.getOrganByOrganName(parentName);
			
			String whereSql = " where o.organname='"+ parentName+ "' and o.organType=2 and o.organModel=1 and o.isrepeal = 0 ";
			List<Organ> organs = appo.getOrganByWhere(whereSql);
			if(organs==null || organs.size() < 1){
				addCount("organErrorCount", counts, 1);
				String error = "上级机构不存在";
				wsheet.addCell(new Label(19, i, error));
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
					organ = appo.getOrganByNameAndParentId(organName, parentOrgan.getId());
					if(organ != null) {
						isOrganAlreadyExists = true;
					}
				} catch (Exception e) {
					isOrganAlreadyExists = false;
				}
				
			}
			
			//是否是上级机构的管辖权限
			UserVisit userVisit = auv.findVisitOrganByUidAndOid(userid, parentOrgan.getId());
			if(userVisit == null) {
				addCount("organErrorCount", counts, 1);
				String error = "当前用户没有上级机构 "+parentOrgan.getOrganname()+" 的管辖权限";
				wsheet.addCell(new Label(19, i, error));
				return true;
			}
			
			if(!isOrganAlreadyExists) {
				//机构类型
				String organType = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
				if(StringUtil.isEmpty(organType)){
					addCount("organErrorCount", counts, 1);
					wsheet.addCell(new Label(19, i, "机构类型不能为空"));
					return true;
				}
				
				//机构类别
				String organModel = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
				if(StringUtil.isEmpty(organModel)){
					addCount("organErrorCount", counts, 1);
					wsheet.addCell(new Label(19, i, "机构类别不能为空"));
					return true;
				}
				
				OrganType orgType = OrganType.parseByName(organType);
				if(orgType == null) {
					addCount("organErrorCount", counts, 1);
					wsheet.addCell(new Label(19, i, "机构类型选择错误"));
					return true;
				} 
				organTypeValue = orgType.getValue();
				if(orgType == OrganType.Plant) {
					PlantType plantType = PlantType.parseByName(organModel);
					if(plantType == null) {
						addCount("organErrorCount", counts, 1);
						wsheet.addCell(new Label(19, i, "工厂类别选择错误"));
						return true;
					}
					modelTypeValue = plantType.getValue();
				} else {
					DealerType dealerType = DealerType.parseByName(organModel);
					if(dealerType == null) {
						addCount("organErrorCount", counts, 1);
						wsheet.addCell(new Label(19, i, "经销商类别选择错误"));
						return true;
					}
					modelTypeValue = dealerType.getValue();
				}
				
				if(OrganType.Dealer.getValue().equals(parentOrgan.getOrganType()) && DealerType.PD.getValue().equals(parentOrgan.getOrganModel())) {
					if(OrganType.Plant.getValue().equals(organTypeValue) 
							|| (OrganType.Dealer.getValue().equals(organTypeValue) && DealerType.PD.getValue().equals(modelTypeValue))) {
						addCount("organErrorCount", counts, 1);
						wsheet.addCell(new Label(19, i, "经销商类别选择错误,PD只能导入BKD或BKR的信息"));
						return true;
					}
				}
				
				//判断省市区
				String organProvince = sheet.getCell(5, i).getContents()==null?null:sheet.getCell(5, i).getContents().trim();
				if(StringUtil.isEmpty(organProvince)){
					
					wsheet.addCell(new Label(19, i, "机构所属省份不能为空"));
					return true;
				}
				CountryArea op = countryAreas.get(organProvince);
				if(op == null) {
					op = appca.getProvinceByName(organProvince);
				}
				if(op == null){
					addCount("organErrorCount", counts, 1);
					wsheet.addCell(new Label(19, i, "省 ["+organProvince+"] 不存在"));
					return true;
				} else {
					countryAreas.put(organProvince, op);
				}
				String organCity = sheet.getCell(6, i).getContents()==null?null:sheet.getCell(6, i).getContents().trim();
				CountryArea oc = null;
				if(!StringUtil.isEmpty(organCity)) {
					oc = countryAreas.get(organProvince+organCity);
					if(oc == null) {
						oc = appca.getCountryAreaByAreaNameAndParentId(organCity, op.getId());
					}
					if(oc == null){
						addCount("organErrorCount", counts, 1);
						String error = "区域信息错误：["+organProvince+"] 不存在城市 [" + organCity +"]";
						wsheet.addCell(new Label(19, i, error));
						return true;
					} else {
						countryAreas.put(organProvince+organCity, oc);
					}
				} else {
					addCount("organErrorCount", counts, 1);
					wsheet.addCell(new Label(19, i, "机构所属市不能为空"));
					return true;
				}
				CountryArea oa = null;
				if(oc != null) {
					String organArea = sheet.getCell(7, i).getContents()==null?null:sheet.getCell(7, i).getContents().trim();
					if(!StringUtil.isEmpty(organArea)) {
						oa = countryAreas.get(organProvince+organCity+organArea);
						if(oa == null) {
							oa = appca.getCountryAreaByAreaNameAndParentId(organArea, oc.getId());
						}
						if(oa == null){
							addCount("organErrorCount", counts, 1);
							String error = "区域信息错误：["+organCity+"] 不存在区县 [ " + organArea +"]";
							wsheet.addCell(new Label(19, i, error));
							return true;
						} else {
							countryAreas.put(organProvince+organCity+organArea, oa);
						}
					}
				}
				
				String organAddress = sheet.getCell(8, i).getContents()==null?null:sheet.getCell(8, i).getContents().trim();
				if(StringUtil.isEmpty(organAddress)){
					addCount("organErrorCount", counts, 1);
					String error = "机构地址不能为空";
					wsheet.addCell(new Label(19, i, error));
					return true;
				}
				
				//默认联系人
				String olName = sheet.getCell(9, i).getContents()==null?null:sheet.getCell(9, i).getContents().trim();
				//联系电话
				String olTel = sheet.getCell(10, i).getContents()==null?null:sheet.getCell(10, i).getContents().trim();
				//判断联系人是否为空 如果为空则使用默认联系人
				if(StringUtil.isEmpty(olName)){
					addCount("organErrorCount", counts, 1);
					String error = "机构联系人不能为空";
					wsheet.addCell(new Label(19, i, error));
					return true;
				}
				//判断联系人是否为空 如果为空则取默认联系电话
				if(StringUtil.isEmpty(olTel)){
					addCount("organErrorCount", counts, 1);
					String error = "机构联系电话不能为空";
					wsheet.addCell(new Label(19, i, error));
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
				
				organ.setProvince(op.getId());
				organ.setCity(oc != null ? oc.getId() : 1);
				organ.setAreas(oa != null ? oa.getId() : 1);
				if(organ.getOrganType() == OrganType.Dealer.getValue() 
						&& organ.getOrganModel() != DealerType.PD.getValue()) {
					organ.setValidatestatus(YesOrNo.NO.getValue());
				}
							
				oLinkman.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("olinkman", 0, "")));
				//姓名
				oLinkman.setName(olName);
				//电话
				oLinkman.setMobile(olTel);
				oLinkman.setCid(organ.getId());
				oLinkman.setMakeorganid(users.getMakeorganid());
				oLinkman.setMakedeptid(users.getMakedeptid());
				oLinkman.setMakeid(userid);
				oLinkman.setMakedate(DateUtil.getCurrentDate());
				oLinkman.setIsmain(YesOrNo.NO.getValue());
			} else {
				wsheet.addCell(new Label(20, i, "机构已存在"));
				addCount("organErrorCount", counts, 1);
			}
			Set<String> warehouseNameSet = new HashSet<String>();
			while(i < endRowNo) {
				//仓库名称
				String warehouseName = sheet.getCell(11, i).getContents()==null ? null : sheet.getCell(11, i).getContents().trim();
				if(StringUtil.isEmpty(warehouseName)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库名称不能为空";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				}
				
				if(warehouseNameSet.contains(warehouseName)) {
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库名称重复";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				} else {
					warehouseNameSet.add(warehouseName);
				}
				
				//仓库内部编码
				String warehouseNcCode = sheet.getCell(12, i).getContents()==null ? null : sheet.getCell(12, i).getContents().trim();
				
				if(isOrganAlreadyExists) {
					if(appWarehouse.isWarehousExists(organ.getId(), warehouseName, warehouseNcCode)) {
						wsheet.addCell(new Label(19, i, "仓库已存在"));
						addCount("warehouseErrorCount", counts, 1);
						i++;
						continue;
					}
				}
				
				//检查省市区信息
				String wProvince = sheet.getCell(13, i).getContents()==null ? null : sheet.getCell(13, i).getContents().trim();
				if(StringUtil.isEmpty(wProvince)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库所属省份不能为空";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				}
				CountryArea wp = countryAreas.get(wProvince);
				if(wp == null) {
					wp = appca.getProvinceByName(wProvince);
				}
				if(wp == null){
					addCount("warehouseErrorCount", counts, 1);
					String error = "省 ["+wProvince+"] 不存在";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				} else {
					countryAreas.put(wProvince, wp);
				}
				String wCity = sheet.getCell(14, i).getContents()==null ? null : sheet.getCell(14, i).getContents().trim();
				CountryArea wc = null;
				if(!StringUtil.isEmpty(wCity)) {
					wc = countryAreas.get(wProvince+wCity);
					if(wc == null) {
						wc = appca.getCountryAreaByAreaNameAndParentId(wCity, wp.getId());
					}
					if(wc == null){
						addCount("warehouseErrorCount", counts, 1);
						String error = "区域信息错误：["+wProvince+"] 不存在城市  [" + wCity + "]";
						wsheet.addCell(new Label(19, i, error));
						i++;
						continue;
					} else {
						countryAreas.put(wProvince+wCity, wc);
					}
				} else {
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库所属市不能为空";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				}
				CountryArea wa = null;
				if(wc != null) {
					String wArea = sheet.getCell(15, i).getContents()==null ? null : sheet.getCell(15, i).getContents().trim();
					if(!StringUtil.isEmpty(wArea)) {
						wa = countryAreas.get(wProvince+wCity+wArea);
						if(wa == null) {
							wa = appca.getCountryAreaByAreaNameAndParentId(wArea, wc.getId());
						}
						if(wa == null){
							addCount("warehouseErrorCount", counts, 1);
							String error = "区域信息错误：["+wCity+"] 不存在区县 [" + wArea + "]";
							wsheet.addCell(new Label(19, i, error));
							i++;
							continue;
						} else {
							countryAreas.put(wProvince+wCity+wArea, wa);
						}
					}
				}
				
				
				//仓库地址
				String warehouseAddr = sheet.getCell(16, i).getContents()==null ? null : sheet.getCell(16, i).getContents().trim();
				if(StringUtil.isEmpty(warehouseAddr)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库地址不能为空";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				}
				
				//仓库联系人名称
				String warehouselName = sheet.getCell(17, i).getContents()==null ? null : sheet.getCell(17, i).getContents().trim();
				if(StringUtil.isEmpty(warehouselName)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库联系人不能为空";
					wsheet.addCell(new Label(19, i, error));
					i++;
					continue;
				}
				
				//仓库联系电话
				String warehouselTel = sheet.getCell(18, i).getContents()==null ? null : sheet.getCell(18, i).getContents().trim();
				if(StringUtil.isEmpty(warehouselTel)){
					addCount("warehouseErrorCount", counts, 1);
					String error = "仓库联系电话不能为空";
					wsheet.addCell(new Label(19, i, error));
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
					warehouse.setIsMinusStock(YesOrNo.NO.getValue());
				} else {
					warehouse.setIsautoreceive(YesOrNo.YES.getValue());
					warehouse.setIsMinusStock(YesOrNo.YES.getValue());
				}
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
				wsheet.addCell(new Label(19, i, "添加时发生异常,请检查系统中是否已存在该机构,若不存在请联系管理员" + e.getMessage()));
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
	
}




