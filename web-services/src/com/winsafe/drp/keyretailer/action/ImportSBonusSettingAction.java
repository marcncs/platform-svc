package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.HashSet;
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

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class ImportSBonusSettingAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ImportSBonusSettingAction.class);
	private SBonusService sBonusService = new SBonusService();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 初始化
		initdata(request);
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
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				Map<String, Product> existsProduct = new HashMap<String, Product>();
				Set<String> notExistsProduct = new HashSet<String>();
				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						// 获取截止年份
						String year = sheet.getCell(0, i).getContents() == null ? null : sheet
								.getCell(0, i).getContents().trim();
						if (StringUtil.isEmpty(year)) {
							errMsg.append("第[" + (i + 1) + "]行截止年份不能为空<br />");
							continue;
						}
						if (!NumberUtil.isNumberic(year)) {
							errMsg.append("第[" + (i + 1) + "]行截止年份格式不正确<br />");
							continue;
						}
						// 获取截止月份
						String month = sheet.getCell(1, i).getContents() == null ? null : sheet
								.getCell(1, i).getContents().trim();
						if (StringUtil.isEmpty(month)) {
							errMsg.append("第[" + (i + 1) + "]行截止月份不能为空<br />");
							continue;
						}
						if (!NumberUtil.isNumberic(month)) {
							errMsg.append("第[" + (i + 1) + "]行截止月份格式不正确<br />");
							continue;
						}
						// 获取产品名称
						String proName = sheet.getCell(2, i).getContents() == null ? null : sheet
								.getCell(2, i).getContents().trim();
						
						if (StringUtil.isEmpty(proName)) {
							errMsg.append("第[" + (i + 1) + "]行产品名称不能为空<br />");
							continue;
						}
						// 获取规格
						String spec = sheet.getCell(3, i).getContents() == null ? null : sheet
								.getCell(3, i).getContents().trim();
						
						if (StringUtil.isEmpty(spec)) {
							errMsg.append("第[" + (i + 1) + "]行规格不能为空<br />");
							continue;
						}
						
						if(notExistsProduct.contains(proName+spec)) {
							errMsg.append("第[" + (i + 1) + "]行产品名为"+proName+"规格为"+spec+"不能为空<br />");
							continue;
						}
						
						if(!existsProduct.containsKey(proName+spec)) {
							Product p = appProduct.getProductByNameAndSpec(proName, spec);
							if(p == null) {
								notExistsProduct.add(proName+spec);
								errMsg.append("第[" + (i + 1) + "]行产品名为"+proName+"规格为"+spec+"不能为空<br />");
								continue;
							} else {
								existsProduct.put(proName+spec, p);
							}
						}
						
						// 获取积分
						String bonus = sheet.getCell(4, i).getContents() == null ? null : sheet
								.getCell(4, i).getContents().trim();
						if (StringUtil.isEmpty(bonus)) {
							errMsg.append("第[" + (i + 1) + "]行积分不能为空<br />");
							continue;
						} 
						// 获取计量单位数量
						String amount = sheet.getCell(5, i).getContents() == null ? null : sheet
								.getCell(5, i).getContents().trim();
						if (StringUtil.isEmpty(amount)) {
							errMsg.append("第[" + (i + 1) + "]行计量单位数量不能为空<br />");
							continue;
						} 
						// 获取账户类型
						String type = sheet.getCell(6, i).getContents() == null ? null : sheet
								.getCell(6, i).getContents().trim();
						if (StringUtil.isEmpty(type)) {
							errMsg.append("第[" + (i + 1) + "]行账户类型不能为空<br />");
							continue;
						} 
						
						SBonusSetting sbs = new SBonusSetting();
						if("BKD".equals(type)) {
							sbs.setAccountType(2);
						} else if("BKR".equals(type)){
							sbs.setAccountType(3);
						} else {
							errMsg.append("第[" + (i + 1) + "]行账户类型格式不正确<br />");
							continue;
						}
						sbs.setActiveFlag(1);
						sbs.setAmount(Double.valueOf(amount));
						sbs.setBonusPoint(Double.valueOf(bonus));
						Product product = existsProduct.get(proName+spec);
						sbs.setCountUnit(product.getCountunit());
						sbs.setModifiedDate(DateUtil.getCurrentDate());
						sbs.setMonth(Integer.parseInt(month));
						sbs.setProductName(proName);
						sbs.setSpec(spec);
						sbs.setYear(Integer.parseInt(year));
						
						if(sBonusService.isBonusAlreadyExists(sbs)) {
							errMsg.append("第[" + (i + 1) + "]行新增失败,该日期范围内已存在相关配置<br />");
							continue;
						}
						
						sBonusService.addSBonusSetting(sbs);
						
						HibernateUtil.commitTransaction();
						CCount++;
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败<br />");
						logger.error("第[" + (i + 1) + "]行导入失败", e);
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传积分设置成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败："
					+ (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >"
						+ errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("文件上传失败", e);
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		} 
	}
	
}
