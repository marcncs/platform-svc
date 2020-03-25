package com.winsafe.drp.action.machin;

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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetailForm;
import com.winsafe.drp.dao.OtherIncomeForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.entity.HibernateUtil;

public class ImportOtherIncomeProductAction extends BaseAction {
	private Logger logger = Logger.getLogger(ImportOtherIncomeProductAction.class);
	private AppProduct appProduct = new AppProduct();
	private Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	private Set<String> notExistsMaterialCodes = new HashSet<String>();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 初始化
		initdata(request);
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
		
		// 数据初始化
		Integer incomeSort = Integer.parseInt(request.getParameter("incomeSort"));
		String billNo = null;
		String remark = null;
		String organId = request.getParameter("organid");
		String warehouseId = request.getParameter("outwarehouseid");
		String isAccount = "1";
		//用于判断数据是否重复
		Set<String> set = new HashSet<String>();

		// 数据用对象保存
		List<OtherIncomeDetailForm> list = new ArrayList<OtherIncomeDetailForm>();
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

				for (int i = 1; i < row; i++) {
					// 控制列
					int j = 0;
					SCount++;
					try {
						// 获取产品id
						String productid = sheet.getCell(j, i).getContents() == null ? null : sheet.getCell(j, i)
								.getContents().trim();

						// 判断产品id是否为空
						if (StringUtil.isEmpty(productid)) {
							errMsg.append("第[" + (i + 1) + "]行产品编号不能为空，导入失败<br />");
							continue;
						}
						// 判断是否存在该产品
						Product product = appProduct.getProductByID(productid);
						if (product == null) {
							errMsg.append("第[" + (i + 1) + "]行没有该产品，导入失败<br />");
							continue;
						}
						// 判断是否允许该产品导入
						// Product product =
						// appOrganProduct.getOrganProductByPid(organId,
						// productid);
						// if (product == null) {
						// errMsg.append("第[" + (i + 1) +
						// "]行没有该产品使用权，导入失败<br />");
						// continue;
						// }
						//
						j++;
						// 获取批号
						String batch = sheet.getCell(j, i).getContents() == null ? null : sheet.getCell(j, i)
								.getContents().trim();
						// 批号判断
						if (StringUtil.isEmpty(batch)) {
							errMsg.append("第[" + (i + 1) + "]行批号不能为空，导入失败<br />");
							continue;
						}

						j++;
						// 获取数量
						String quantity = sheet.getCell(j, i).getContents() == null ? null : sheet.getCell(j, i)
								.getContents().trim();
						// 批号判断
						if (StringUtil.isEmpty(quantity)) {
							errMsg.append("第[" + (i + 1) + "]行数量不能为空，导入失败<br />");
							continue;
						}

						if (!NumberUtil.isNumberic(quantity)) {
							errMsg.append("第[" + (i + 1) + "]行数量格式错误，导入失败<br />");
							continue;
						}
						
						//判断是否存在相同的数据
						if (set.contains(productid+batch)) {
							errMsg.append("第[" + (i + 1) + "]行数据重复，导入失败<br />");
							continue;
						}else {
							set.add(productid+batch);
						}
						
						
						// 打造数据
						OtherIncomeDetailForm otherIncomeDetailForm = new OtherIncomeDetailForm();
						otherIncomeDetailForm.setProductid(productid);
						otherIncomeDetailForm.setProductname(product.getProductname());
						otherIncomeDetailForm.setSpecmode(product.getSpecmode());
						otherIncomeDetailForm.setBatch(batch);
						otherIncomeDetailForm.setQuantity(Double.valueOf(quantity));
						otherIncomeDetailForm.setUnitid(Constants.DEFAULT_UNIT_ID);
						list.add(otherIncomeDetailForm);

						CCount++;
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败<br />");
						logger.error("import into otherincome error:", e);
						HibernateUtil.rollbackTransaction();
					}
				}
				try {
					// 增加其他入库单
					// 获取数据
					if (list.size() > 0) {

						AddOtherIncomeService addOtherIncomeService = new AddOtherIncomeService();
						addOtherIncomeService.addOtherIncome(organId, warehouseId, incomeSort, billNo, remark, users,
								isAccount, list);
						HibernateUtil.commitTransaction();
					}

				} catch (Exception e) {
					HibernateUtil.rollbackTransaction();
					logger.error("", e);
					request.setAttribute("result", "上传文件失败,请重试");
					return new ActionForward("/sys/lockrecord2.jsp");
				}

				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传条码资料成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败：" + (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >" + errMsg.toString()
						+ "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			System.out.println("异常出现在第 " + CCount + "行");
			return new ActionForward("/sys/lockrecord2.jsp");
		} finally {
			existMaterialCodes.clear();
			notExistsMaterialCodes.clear();
		}
	}
}