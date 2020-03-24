package com.winsafe.drp.keyretailer.action;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportSBonusTargetAction extends BaseAction {
	private AppBaseResource appBr = new AppBaseResource();
	private AppSBonusAccount asa = new AppSBonusAccount();
	private AppOrgan ao = new AppOrgan();
	private AppSBonusSetting ass = new AppSBonusSetting();
	private AppProduct ap = new AppProduct();
	private AppSBonusTarget ast = new AppSBonusTarget();
	
	private static Logger logger = Logger.getLogger(ImportSBonusTargetAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int CCount = 0, ECount = 0;
		Organ o = null;
		Set<String> notExistsPro = new HashSet<String>();
		Map<String,Product> existsPro = new HashMap<String, Product>();
		Map<String,SBonusConfig> bonusConfigMap = new HashMap<String, SBonusConfig>();
		AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		Workbook wb = null;
		try {
			/*SBonusConfig sbc = SBonusService.getCurrentSBonusConfigByType(4);
			if(sbc == null) {
				request.setAttribute("result", "导入失败,目前不在积分目标设置周期内!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}*/
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
				wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				
				if(sheet.getRows() <= 1) {
					request.setAttribute("result", "上传文件处理失败，未从文件中读取到数据");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				String header = sheet.getCell(0, 0).getContents()==null ? null : sheet.getCell(0, 0).getContents().trim();
				if(StringUtil.isEmpty(header) || !header.trim().equals("发货客户代码*") || sheet.getColumns() < 2) {
					request.setAttribute("result", "上传文件处理失败，请确认是否使用了正确的模板");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
//				Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
				
				AppSTransferRelation astr = new AppSTransferRelation();
//				Set<String> visitOrganSet;
				Set<String>  relation= astr.getAllRelationSet();
				
				for (int i = 1; i < row; i++) {
					double bonusPoint = 0;
					String fromorganid = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
					if(StringUtil.isEmpty(fromorganid)){
						errMsg.append("第[" + (i+1) + "]行发货客户代码不能为空<br />");
						ECount++;
						continue;
					} 
					
					if(OrganType.Dealer.getValue().equals(users.getOrganType()) && !fromorganid.equals(users.getMakeorganid())) {
						errMsg.append("第[" + (i+1) + "]行当前用户没有发货客户的管辖权限<br />");
						ECount++;
						continue;
					}
					
					o = ao.getOrganByID(fromorganid);
					if (o==null) {
						errMsg.append("第[" + (i+1) + "]行发货客户代码不存在<br />");
						ECount++;
						continue;
					}
					
					String toorganid = sheet.getCell(2, i).getContents()==null ? null : sheet.getCell(2, i).getContents().trim();
					if(StringUtil.isEmpty(toorganid)){
						errMsg.append("第[" + (i+1) + "]行进货机构代码不能为空<br />");
						ECount++;
						continue;
					} 
					if (fromorganid.equals(toorganid)) {
						errMsg.append("第[" + (i+1) + "]行发货客户代码和进货机构编号相同<br />");
						ECount++;
						continue;
					}
					
					if(!relation.contains(fromorganid+"_"+toorganid)) {
						errMsg.append("第[" + (i+1) + "]行机构间不存在进货关系<br />");
						ECount++;
						continue;
					}
					
					Organ toOrgan = ao.getOrganByID(toorganid);
					
					if (toOrgan==null) {
						errMsg.append("第[" + (i+1) + "]行行进货机构代码不存在<br />");
						ECount++;
						continue;
					}
					
					SBonusAccount  sba= asa.getSBonusAccountByOrganId(toorganid);
					if (sba==null) {
						errMsg.append("第[" + (i+1) + "]行进货机构不在积分范围内<br />");
						ECount++;
						continue;
					}
					
					String year = sheet.getCell(4, i).getContents()==null ? null : sheet.getCell(4, i).getContents().trim();
					if(StringUtil.isEmpty(year)){
						errMsg.append("第[" + (i+1) + "]行年度不能为空<br />");
						ECount++;
						continue;
					} 
					
					if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
						if(!bonusConfigMap.containsKey(year)) {
							SBonusConfig sbc = appSBonusConfig.getPeriodConfig(year, DateUtil.getCurrentDateString(), 4);
							if(sbc == null) {
								errMsg.append("第[" + (i+1) + "]行设置失败,目前不在积分目标设置周期内<br />");
								ECount++;
							} else {
								bonusConfigMap.put(year, sbc);
							}
						}
					}
					
					/*String productid = sheet.getCell(5, i).getContents()==null ? null : sheet.getCell(5, i).getContents().trim();
					if(StringUtil.isEmpty(productid)){
						errMsg.append("第[" + (i+1) + "]行产品代码不能为空<br />");
						ECount++;
						continue;
					} 
					
					Product p = ap.getProductByID(productid); 
					if(p==null) {
						errMsg.append("第[" + (i+1) + "]行产品代码不存在<br />");
						ECount++;
						continue;
					}*/
					
					String productname = sheet.getCell(5, i).getContents()==null ? null : sheet.getCell(5, i).getContents().trim();
					if(StringUtil.isEmpty(productname)){
						errMsg.append("第[" + (i+1) + "]行产品名称不能为空<br />");
						ECount++;
						continue;
					} 
					
					String productspec = sheet.getCell(6, i).getContents()==null ? null : sheet.getCell(6, i).getContents().trim();
					if(StringUtil.isEmpty(productspec)){
						errMsg.append("第[" + (i+1) + "]行产品规格不能为空<br />");
						ECount++;
						continue;
					} 
					
					if(notExistsPro.contains(productname+productspec)) {
						errMsg.append("第[" + (i+1) + "]行名称为["+productname+"]规格为["+productspec+"]的产品信息不存在<br />");
						ECount++;
						continue;
					}
					
					if(!existsPro.containsKey(productname+productspec)) {
						Product product = ap.getProductByNameAndSpec(productname, productspec);
						if(product != null) {
							existsPro.put(productname+productspec, product);
						} else {
							notExistsPro.add(productname+productspec);
							errMsg.append("第[" + (i+1) + "]行名称为["+productname+"]规格为["+productspec+"]的产品信息不存在<br />");
							ECount++;
							continue;
						}
					}
					
					Product product = existsPro.get(productname+productspec);
					
					/*String productcounitid = sheet.getCell(8, i).getContents()==null ? null : sheet.getCell(8, i).getContents().trim();
					if(StringUtil.isEmpty(productcounitid)){
						errMsg.append("第[" + (i+1) + "]行产品计量单位代码不能为空<br />");
						ECount++;
						continue;
					} 
					
					String productcounitname = sheet.getCell(9, i).getContents()==null ? null : sheet.getCell(9, i).getContents().trim();
					if(StringUtil.isEmpty(productcounitname)){
						errMsg.append("第[" + (i+1) + "]行产品计量单位名称不能为空<br />");
						ECount++;
						continue;
					} */
					
					String amount = sheet.getCell(7, i).getContents()==null ? null : sheet.getCell(7, i).getContents().trim();
					if(StringUtil.isEmpty(amount)){
						errMsg.append("第[" + (i+1) + "]行目标数量不能为空<br />");
						ECount++;
						continue;
					} 
					/*if(!p.getProductname().equals(productname) || 
						!p.getSpecmode().equals(productspec)||
						!p.getCountunit().equals(productcounitid)||
						!countUnitMap.get(p.getCountunit().toString()).equals(productcounitname)){
						errMsg.append("第[" + (i+1) + "]行产品信息在系统中不存在，请仔细核对！<br />");
						ECount++;
						continue;
					}*/
					SBonusTarget sbt = new SBonusTarget();
					sbt.setAccountId(sba.getAccountId());
					Calendar now = Calendar.getInstance();
					int month = now.get(Calendar.MONTH) + 1;
					SBonusSetting sbs = ass.getSBonusSetting(productname, productspec,Integer.parseInt(year),month, o.getOrganModel().equals(DealerType.BKD.getValue())?DealerType.BKD.getValue():DealerType.BKR.getValue());
					if (sbs!=null) {
						bonusPoint = new BigDecimal(ArithDouble.mul(Double.valueOf(amount), ArithDouble.div(sbs.getBonusPoint(), sbs.getAmount()))).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					} else {
						errMsg.append("第[" + (i+1) + "]行产品对应的积分设置信息在系统中不存在<br />");
						ECount++;
						continue;
					}
					sbt.setBonusPoint(bonusPoint);
					sbt.setCountUnit(product.getCountunit());
					sbt.setFromOrganId(fromorganid);
					sbt.setToOrganId(toorganid);
					sbt.setModifiedDate(new Date());
					sbt.setProductName(productname);
					sbt.setSpec(productspec);
					sbt.setYear(Integer.parseInt(year));
					sbt.setTargetAmount(Double.parseDouble(amount));
					sbt.setIsSupported(1);
					//已经存在
					SBonusTarget localSbt = ast.getSBonusTarget(sbt.getToOrganId(), sbt.getProductName(), sbt.getSpec(), sbt.getYear().toString());
					if(localSbt != null) {
						if(!localSbt.getFromOrganId().equals(sbt.getFromOrganId())) {
							errMsg.append("第[" + (i+1) + "]行产品对应的目标设置信息其他机构已设置过<br />");
							ECount++;
							continue;
						}
						// 更新此处3个变量
						localSbt.setModifiedDate(new Date());
						localSbt.setTargetAmount(Double.parseDouble(amount));
						localSbt.setBonusPoint(bonusPoint);
						ast.updSBonusTarget(localSbt);
					} else {
						ast.addSBonusTarget(sbt);
					}
					CCount++;
				}
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "上传积分目标完成,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!" + errMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("error occurred when processing xls file", e);
			request.setAttribute("result", "处理文件时发生异常");
			throw e;
		} finally {
			wb.close();
		}
	}
}




