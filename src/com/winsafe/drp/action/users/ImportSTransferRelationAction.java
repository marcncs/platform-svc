package com.winsafe.drp.action.users;


import java.util.Date;

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
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportSTransferRelationAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ImportSTransferRelationAction.class);
	private AppSTransferRelation asr = new AppSTransferRelation();
	private AppOrgan ao = new AppOrgan();
	private AppMakeConf appmc = new AppMakeConf();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int CCount = 0, ECount = 0;
		Organ o = null;
		STransferRelation sr = null;
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		Workbook wb = null;
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
				wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				
				if(sheet.getRows() <= 1) {
					request.setAttribute("result", "上传文件处理失败，未从文件中读取到数据");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				String header = sheet.getCell(0, 0).getContents()==null ? null : sheet.getCell(0, 0).getContents().trim();
				if(StringUtil.isEmpty(header) || !header.trim().equals("BKR机构编号*") || sheet.getColumns() < 2) {
					request.setAttribute("result", "上传文件处理失败，请确认是否使用了正确的模板");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				
				for (int i = 1; i < row; i++) {
					String sonid = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
					if(StringUtil.isEmpty(sonid)){
						errMsg.append("第[" + i + "]行BKR机构编号不能为空<br />");
						ECount++;
						continue;
					} 
					o = ao.getBKROrganByID(sonid);
					if (o==null) {
						errMsg.append("第[" + i + "]行BKR机构编号不存在<br />");
						ECount++;
						continue;
					}
					
					String parentid = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
					if(StringUtil.isEmpty(parentid)){
						errMsg.append("第[" + i + "]行BKD/PD进货机构编号不能为空<br />");
						ECount++;
						continue;
					} 
					if (sonid.equals(parentid)) {
						errMsg.append("第[" + i + "]行BKR机构编号和BKD/PD进货机构编号相同<br />");
						ECount++;
						continue;
					}
					o = ao.getBKDPDOrganByID(parentid);
					if (o==null) {
						errMsg.append("第[" + i + "]行BKD/PD进货机构编号不存在<br />");
						ECount++;
						continue;
					}
					sr = asr.queryby2(sonid, parentid);
					if (sr!=null) {
						errMsg.append("第[" + i + "]行的对应关系已存在<br />");
						ECount++;
						continue;
					}
					sr = new STransferRelation();
					sr.setModifieDate(new Date());
					sr.setOrganizationId(sonid);
					sr.setOppOrganId(parentid);
					sr.setVersion(1);
					asr.addSTransferRelation(sr);
					// 增加用户(管辖上级机构)的业务往来权限
					if(o.getOrganType() == 2 && o.getOrganModel() == 1) {
						addOrganVisit(parentid, sonid);
					}
					CCount++;
				}
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String msg = "上传进货机构关系完成,本次总共添加 :"
				+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
				+ "条!" + errMsg;
			request.setAttribute("result", msg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("error occurred when processing xls file", e);
			request.setAttribute("result", "处理文件时发生异常");
			throw e;
		} finally {
			wb.close();
		}
	}
	
	/**
	 * 新增业务往来权限
	 * @throws Exception 
	 */
	private void addOrganVisit(String parentid,String oid) throws Exception{
		//增加业务往来机构
		aov.addOrganVisit(oid, parentid);
		//增加业务往来仓库
		appWV.addWarehousVisit(oid, parentid);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
}




