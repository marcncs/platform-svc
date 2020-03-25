package com.winsafe.drp.action.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppCustomerMatchOrder;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.CustomerMatchOrder;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.hbm.entity.HibernateUtil;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * Create Time 2011-10-8 上午11:20:21 
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportCustomerMatchOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				AppOrgan appo = new AppOrgan();
				AppCustomerMatchOrder appcmo = new AppCustomerMatchOrder();
				boolean isExist = false;
				for (int i = 1; i < row; i++) {
					//总数加1
					SCount ++;
					//初始化
					isExist = false;
					try {
						//管家婆名称
						String siname = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
						//判断机构名称是否为空
						if(StringUtil.isEmpty(siname)){
							errMsg.append("第[" + (i+1) + "]行管家婆名称不能为空<br />");
							continue;
						}
						
						//产品信息
						String productline = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
						
						//公司名称
						String organName = sheet.getCell(2, i).getContents()==null ? null : sheet.getCell(2, i).getContents().trim();
						//判断上级机构名称是否为空
						if(StringUtil.isEmpty(organName)){
							errMsg.append("第[" + (i+1) + "]行公司名称不能为空<br />");
							continue;
						}
						
						//判断该机构是否存在
						Organ organ = appo.getOrganByOrganName(organName);
						if(organ==null){
							errMsg.append("第[" + (i+1) + "]行公司不已存在<br />");
							continue;
						}
						
						//是否VIP
						Integer customerlevel = null;
						String strVip = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
						//判断vip是否为空
						if(!StringUtil.isEmpty(strVip)){
							//是vip
							if("vip".equalsIgnoreCase(strVip)){
								customerlevel = 1;
							}else{
								customerlevel = 0;
							}
						}
						
						//拣货顺序
						Integer matchorder = sheet.getCell(4, i).getContents()==null ? null : Integer.parseInt(sheet.getCell(4, i).getContents().trim());
						//出货顺序
						String outorder = sheet.getCell(5, i).getContents()==null ? null : sheet.getCell(5, i).getContents().trim();
						//备注
						String remark = sheet.getCell(6, i).getContents()==null ? null : sheet.getCell(6, i).getContents().trim();
						
						//判断该管家婆是否存在
						CustomerMatchOrder cmo = appcmo.getCmoBySiName(siname);
						//如果存在则更新
						if(cmo==null){
							cmo = new CustomerMatchOrder();
						}else{
							isExist = true;
						}
						
						//管家婆名称设置
						cmo.setSiname(siname);
						//产品线设置
						cmo.setProductline(productline);
						//公司id设置
						cmo.setOrganid(organ.getId());
						//公司名称设置
						cmo.setOrganname(organ.getOrganname());
						//是否vip设置
						cmo.setCustomerlevel(customerlevel);
						//拣货顺序设置
						cmo.setMatchorder(matchorder);
						//出货顺序设置
						cmo.setOutorder(outorder);
						//备注设置
						cmo.setRemark(remark);
						
						//保存或更新
						if(isExist){
							//更新
							appcmo.updCustomerMatchOrder(cmo);
						}else{
							//新增
							appcmo.AddCustomerMatchOrder(cmo);
						}
				    	//有效数量加1
						CCount++;
						HibernateUtil.commitTransaction();
					}catch (Exception e) {
						errMsg.append("第[" + (i+1) + "]行导入失败<br />");
						e.printStackTrace();
						HibernateUtil.rollbackTransaction();
					}
					 
				}
				wb.close();

			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传配货顺序资料成功,本次总共添加 :"+(SCount)+"条! 成功:"+CCount+"条! 失败："+(SCount-CCount)+"条!";
			if (SCount-CCount > 0) {
				resultMsg = resultMsg
						+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>"
						+ "<div >"
						+ 	errMsg.toString()
						+ "</div>";
			}
			
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			System.out.println("异常出现在第 " + CCount + "行");
			return new ActionForward("/sys/lockrecord2.jsp");

		}

	}
}




