package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportTakeBillAction extends BaseAction {
	private AppTakeTicketIdcode app = new AppTakeTicketIdcode();
	private String lcode;
	AppTakeTicket att = new AppTakeTicket();
	protected AppIdcode appidcode = new AppIdcode();
	private TakeTicket tt = null;
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		// TTid编号
		String productid = request.getParameter("prid");
		String ttid = request.getParameter("billid");
		
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

				Scanner s = null;
				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						// 获取发货单编号
						String id = sheet.getCell(0, i).getContents() == null ? null : sheet
								.getCell(0, i).getContents().trim();
						// 发货单编号判断
						if (StringUtil.isEmpty(id)) {
							errMsg.append("第[" + (i + 1) + "]行发货单编号不能为空<br />");
							continue;
						}
						tt = att.getTakeTicket(id);
						
						if (tt == null) {
							errMsg.append("第[" + (i + 1) + "]行单据不存在<br />");
							continue;
						}
						if(!tt.getId().equals(ttid)) {
							errMsg.append("单据编号不一致,请确认模板中单据号是否正确<br />");
							continue;
						}
						// 该发货单是否已作废判断
						if (tt.getIsblankout() == 1) {
							errMsg.append("第[" + (i + 1) + "]行发货单编号已作废<br />");
							continue;
						}
						// 该发货单是否已复核判断
						if (tt.getIsaudit() == 1) {
							errMsg.append("第[" + (i + 1) + "]行发货单编号已复核<br />");
							continue;
						}

						// 获取条码
						String idcode = sheet.getCell(1, i).getContents() == null ? null : sheet
								.getCell(1, i).getContents().trim();
						// 判断采集器型号是否为空
						if (StringUtil.isEmpty(idcode)) {
							errMsg.append("第[" + (i + 1) + "]行条码不能为空<br />");
							continue;
						}

						// 条码格式判断
//						if (idcode.length() != 20) {
//							errMsg.append("第[" + (i + 1) + "]行条码格式不正确<br />");
//							continue;
//						}
						// 通过条码新增service进行条码的检查及新增
						String errorString = dealIdcode(tt.getId(), idcode, productid, userid + "", users
								.getMakeorganid());
						if (isSuccess(errorString)) {
							// 每行数据手动提交事务,防止事务事件过长
//							errMsg.append("第[" + (i + 1) + "]行条码更新成功<br />");
							HibernateUtil.commitTransaction();
							CCount++;
						}else {
							errMsg.append("第[" + (i + 1) + "]行"+errorString+"<br />");
						}
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败<br />");
						e.printStackTrace();
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传采集器资料成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败："
					+ (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >"
						+ errMsg.toString() + "</div>";
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

	public String dealIdcode(String billid, String idcode, String productid, String userid,
			String organId) throws Exception {

		
		Nidcode aNidcode = new Nidcode();
		// 验证条码的正确性
		String errorMsg = validateIdcode(idcode, tt, productid, aNidcode);
		if (isSuccess(errorMsg)) {
			// 更新条码状态为不可用
			appidcode.updIsUse(idcode, 0);
			// 增加条码
			TakeTicketIdcode pi = new TakeTicketIdcode();
//			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
			pi.setTtid(billid);
			pi.setProductid(aNidcode.productid);
			pi.setIsidcode(1);
			// pi.setWarehousebit(aNidcode.warehousebit);
			pi.setBatch(aNidcode.batch);
			pi.setProducedate(aNidcode.productDate);
			pi.setVad("");
			pi.setUnitid(aNidcode.unitid);
			pi.setQuantity(1d);
			pi.setPackquantity(aNidcode.packQuantity);
			pi.setIdcode(idcode);
			pi.setLcode(lcode);
			pi.setStartno(aNidcode.startno);
			pi.setEndno(aNidcode.endno);
			pi.setIssplit(aNidcode.issplit);
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			app.addTakeTicketIdcode(pi);
		}
		return errorMsg;
	}

	private String validateIdcode(String ic, TakeTicket tt, String productid, Nidcode aNidcode)
			throws Exception {
		// 记录特殊的错误信息(只记录,不报错)
		String errorMsg = null;
		// 判断条码数量是否超过产品数量
		if (att.isIdcodeQtyGreatOrEqualThanProductQty(tt.getId(), productid)) {
//			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00016));
			return UploadErrorMsg.E00016;
		}
		// 判断条码是否存在
		Idcode idcode = appidcode.getIdcodeByCode(ic);
		if (idcode == null) {
//			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00003, ic));
			return UploadErrorMsg.getError(UploadErrorMsg.E00003, ic);
		}
		// 判断条码的产品是否符合条件
		if (!idcode.getProductid().equals(productid)) {
//			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00108, ic));
			return UploadErrorMsg.getError(UploadErrorMsg.E00108, ic);
		}
		// 判断条码是否可用
		if (idcode.getIsuse() != null && idcode.getIsuse() == 0
				&& idcode.getWarehouseid().equals(tt.getWarehouseid())) {
//			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00004, ic));
			return UploadErrorMsg.getError(UploadErrorMsg.E00004, ic);
		}
		TakeTicketIdcode tti = app.getTakeTicketIdcodeByidcode(productid, tt.getId(), ic);
		if (tti != null) {
//			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00006, ic));
			return UploadErrorMsg.getError(UploadErrorMsg.E00006, ic);
		}
		// 判断条码是否在当前仓库(只记录错误信息,不报错)
		if (!idcode.getWarehouseid().equals(tt.getWarehouseid())) {
			errorMsg = UploadErrorMsg.getError(UploadErrorMsg.E00005, ic);
			//手工条码上传与采集器条码上传的单据类型不一致，添加错误日志时使用统一的类型
			Integer bSort = 0;
			if(tt.getBsort() == 1) {
				bSort = 4;
			} else if(tt.getBsort() == 2) {
				bSort = 5;
			} else {
				if(tt.getBillno().startsWith("OW")) {
					bSort = 8;
				} else {
					bSort = 20;
				}
			}
			// 保存条码的特殊信息日志
			appIdcodeUploadLog.addIdcodeUploadLogs(errorMsg, tt.getBillno(), userid.toString(), bSort,users.getMakeorganid(), tt.getWarehouseid(), idcode.getIdcode());
		}
		aNidcode.batch = idcode.getBatch();
		aNidcode.productid = idcode.getProductid();
		aNidcode.productDate = idcode.getProducedate();
		aNidcode.warehousebit = idcode.getWarehousebit();
		aNidcode.unitid = idcode.getUnitid();
		aNidcode.packQuantity = idcode.getPackquantity();
		aNidcode.startno = idcode.getStartno();
		aNidcode.endno = idcode.getEndno();
		aNidcode.warehousebit = idcode.getWarehousebit();

		return "条码更新成功";

	}

	public class Nidcode {
		public String batch;
		public String productid;
		public String productDate;
		public String warehousebit = "000";
		public int issplit = 0;
		public Integer unitid;
		public String scanFlag;
		public Double packQuantity = 1d;
		public String startno;
		public String endno;
		public String warehouseid;
		public Product product;
	}
	
	public  boolean isSuccess(String errorMsg) {
		if ("条码更新成功".equals(errorMsg)) {
			return true;
		}
		return false;
	}
}