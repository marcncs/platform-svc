package com.winsafe.drp.action.report;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.report.pojo.SalesConsumHistory;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.FUnitUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

/**
 * 库存预警报表
 * 
 * @Title: AlarmByProductStockpileAllAction.java
 * @author: WEILI
 * @CreateTime: 2013-04-25
 * @version: 1.0
 */
public class UploadSalesConsumHistoryAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(UploadSalesConsumHistoryAction.class);
	private AppSalesConsumHistory appSalesConsumHistory = new AppSalesConsumHistory();
	private OrganService organService = new OrganService();
	private AppProduct appProduct = new AppProduct();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppFUnit appFUnit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
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
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				
				Scanner s = null;
				
				Map<String, Organ> organs = new HashMap<String, Organ>();
				Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
				Set<String> notExistsMaterialCodes = new HashSet<String>();
				Map<String, String> warehouseIds = new HashMap<String, String>();
				
				List<Organ> organlist = organService.getOrganIdAndNames();
				for(Organ organ : organlist) {
					organs.put(organ.getOecode(), organ);
				}
				Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						// 月份
						String yearMonth = sheet.getCell(0, i).getContents() == null ? null
								: sheet.getCell(0, i).getContents().trim();
						// 月份判断
						if (StringUtil.isEmpty(yearMonth)) {
							errMsg.append("第[" + (i + 1) + "]行月份不能为空<br />");
							hasError = true;
							continue;
						}
						if (!isValidateDate(yearMonth)) {
							errMsg.append("第[" + (i + 1) + "]行月份格式不正确<br />");
							hasError = true;
							continue;
						}
						
						// 机构内部编码
						String oecode = sheet.getCell(1, i).getContents() == null ? null
								: sheet.getCell(1, i).getContents().trim();
						// 机构内部编码判断
						if (StringUtil.isEmpty(oecode)) {
							errMsg.append("第[" + (i + 1) + "]行机构内部编码不能为空<br />");
							hasError = true;
							continue;
						}
						
						if(!organs.containsKey(oecode)) {
							errMsg.append("第[" + (i + 1) + "]行机构内部编码为"+oecode+"的机构不存在<br />");
							hasError = true;
							continue;
						}

						// 物料号
						String mcode = sheet.getCell(2, i).getContents() == null ? null
								: sheet.getCell(2, i).getContents().trim();
						// 物料号判断
						if (StringUtil.isEmpty(mcode)) {
							errMsg.append("第[" + (i + 1) + "]行物料号不能为空<br />");
							hasError = true;
							continue;
						}
						
						if(!isMaterialCodeExists(mcode, existMaterialCodes, notExistsMaterialCodes)) {
							errMsg.append("第[" + (i + 1) + "]行物料号为"+mcode+"的产品不存在<br />");
							hasError = true;
							continue;
						}
						
						// 单位
						String unit = sheet.getCell(3, i).getContents() == null ? null
								: sheet.getCell(3, i).getContents().trim();
						// 单位判断
						if (StringUtil.isEmpty(unit)) {
							errMsg.append("第[" + (i + 1) + "]行单位不能为空<br />");
							hasError = true;
							continue;
						}
						
						//销售数量		
						String salesVolumeStr = sheet.getCell(4, i).getContents() == null ? null
								: sheet.getCell(4, i).getContents().trim();
						Double salesVolume = getDouble(salesVolumeStr);
						if (salesVolume == null) {
							errMsg.append("第[" + (i + 1) + "]行销售数量格式不正确<br />");
							hasError = true;
							continue;
						}
						
						//销售金额
						String salesValueStr = sheet.getCell(5, i).getContents() == null ? null
								: sheet.getCell(5, i).getContents().trim();
						
						Double salesValue = getDouble(salesValueStr);
						if (salesValue == null) {
							errMsg.append("第[" + (i + 1) + "]行销售金额格式不正确<br />");
							hasError = true;
							continue;
						}
						
						//消耗数量
						String consumVolumeStr = sheet.getCell(6, i).getContents() == null ? null
								: sheet.getCell(6, i).getContents().trim();
						Double consumVolume = getDouble(consumVolumeStr);
						if (consumVolume == null) {
							errMsg.append("第[" + (i + 1) + "]行消耗数量格式不正确<br />");
							hasError = true;
							continue;
						}
						
						//其他消耗数量
						String otherConsumVolumeStr = sheet.getCell(7, i).getContents() == null ? null
								: sheet.getCell(7, i).getContents().trim();
						
						Double otherConsumVolume = getDouble(otherConsumVolumeStr);
						if (otherConsumVolume == null) {
							errMsg.append("第[" + (i + 1) + "]行其他消耗数量格式不正确<br />");
							hasError = true;
							continue;
						}
						
						
						if(salesVolume == 0 && salesValue == 0 && consumVolume == 0 && otherConsumVolume == 0) {
							errMsg.append("第[" + (i + 1) + "]行数量均为0<br />");
							continue;
						}
						
						Product product = existMaterialCodes.get(mcode);
						if("件".equals(unit)) {
							
							if(FUnitUtil.checkRate(product.getId(),Constants.DEFAULT_UNIT_ID,product.getSunit(), funitMap)){
								if(salesVolume != 0d) {
									salesVolume = FUnitUtil.changeUnit(product.getId(), Constants.DEFAULT_UNIT_ID, salesVolume, product.getSunit(),funitMap);
								}
								if(consumVolume != 0d) {
									consumVolume = FUnitUtil.changeUnit(product.getId(), Constants.DEFAULT_UNIT_ID, consumVolume, product.getSunit(),funitMap);
								}
								if(otherConsumVolume != 0d) {
									otherConsumVolume = FUnitUtil.changeUnit(product.getId(), Constants.DEFAULT_UNIT_ID, otherConsumVolume, product.getSunit(),funitMap);
								}
							}
						} else {
							if(product.getBoxquantity() != 0) {
								if(salesVolume != 0d) {
									salesVolume = ArithDouble.div(salesVolume, product.getBoxquantity());
								}
								if(consumVolume != 0d) {
									consumVolume = ArithDouble.div(consumVolume, product.getBoxquantity());
								}
								if(otherConsumVolume != 0d) {
									otherConsumVolume = ArithDouble.div(otherConsumVolume, product.getBoxquantity());
								}
							}
						}
						
						Integer month = Integer.parseInt(yearMonth.substring(4, 6));
						Integer year = Integer.parseInt(yearMonth.substring(0, 4));
						SalesConsumHistory sch = appSalesConsumHistory.getSalesConsumHistory(year,month,organs.get(oecode).getId(),existMaterialCodes.get(mcode).getId(), "1");
						if(sch != null) {
							sch.setSalesVolume(ArithDouble.add(sch.getSalesVolume(), salesVolume));
							sch.setConsumVolume(ArithDouble.add(sch.getConsumVolume(), consumVolume));
							sch.setOtherConsumVolume(ArithDouble.add(sch.getOtherConsumVolume(), otherConsumVolume));
							if(sch.getSalesValue() == null) {
								sch.setSalesValue(salesValue);
							} else {
								sch.setSalesValue(ArithDouble.add(sch.getSalesValue(), salesValue));
							}
							appSalesConsumHistory.updSalesConsumHistory(sch);
						} else {
							if(!warehouseIds.containsKey(oecode)) {
								Warehouse warehouse = appWarehouse.getAvaiableWarehouseByOID(organs.get(oecode).getId());
								warehouseIds.put(oecode, warehouse.getId());
							}
							
							sch = new SalesConsumHistory();
							sch.setMakeDate(DateUtil.getCurrentDate());
							//设置年月
							sch.setMonth(month);
							sch.setYear(year);
							sch.setOrganId(organs.get(oecode).getId());
							sch.setProductId(existMaterialCodes.get(mcode).getId());
							sch.setWarehouseId(warehouseIds.get(oecode));
							
							sch.setSalesVolume(salesVolume);
							sch.setSalesValue(salesValue);
							sch.setConsumVolume(consumVolume);
							sch.setConsumValue(0d);
							sch.setOtherConsumVolume(otherConsumVolume);
							sch.setHasInvoice(1);
							appSalesConsumHistory.addSalesConsumHistory(sch);
						}	
						
						HibernateUtil.commitTransaction();
						CCount++;
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败:"+e.getMessage()+"<br />");
						logger.error("",e);
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传销售消耗历史数据成功,本次总共添加 :" + (SCount) + "条! 成功:"
					+ CCount + "条! 失败：" + (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg
						+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>"
						+ "<div >" + errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			DBUserLog.addUserLog(request, "上传");
			return mapping.findForward("success");
		} catch (Exception e) {
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			logger.error("异常出现在第 " + CCount + "行",e);
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
	
	public boolean isMaterialCodeExists(String materialCode,Map<String, Product> existMaterialCodes, Set<String> notExistsMaterialCodes ) {
		if(existMaterialCodes.containsKey(materialCode)) {
			return true;
		} else if(notExistsMaterialCodes.contains(materialCode)){
			return false;
		} else {
			try {
				Product procuct = appProduct.getByMCode(materialCode);
				existMaterialCodes.put(materialCode, procuct);
				return true;
			} catch (NotExistException e) {
				notExistsMaterialCodes.add(materialCode);
				return false;
			}
		}
	}

	private Double getDouble(String valueStr) {
		try {
			return Double.parseDouble(valueStr);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isValidateDate(String value) {
		try {
			if(value.length() < 6) {
				return false;
			}
			Integer.parseInt(value.substring(0, 4));
			int month = Integer.parseInt(value.substring(4, 6));
		    if(month > 0 && month <13) {
		    	return true;
		    }
		    return false;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		String value = "201508";
		System.out.println(value.substring(0, 4));
		System.out.println(value.substring(4, 6));
//		System.out.println(isValidateDate("201512"));
	}
}
