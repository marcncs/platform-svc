package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.sap.action.ListPrintJobAction;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.pojo.CartonCode;

public class ImportTtidForIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ImportTtidForIdcodeAction.class);
	AppTakeTicket att = new AppTakeTicket();
	protected AppIdcode appidcode = new AppIdcode();
	protected AppTakeTicketIdcode appTakeTicketIdcode = new AppTakeTicketIdcode();
	protected AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
	protected AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	protected AppStockMove appStockMove = new AppStockMove();
	protected AppOrganWithdraw appOrganWithdraw = new AppOrganWithdraw();
	protected AppTakeTicket appTakeTicket = new AppTakeTicket();
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	private Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
	private Set<String> notExistsMaterialCodes = new HashSet<String>();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 初始化
		initdata(request);
		// 机构编号
		String organId = request.getParameter("organid");
		// 仓库编号
		String warehouseId = request.getParameter("outwarehouseid");
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

				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						
						// 获取单据号
						String ttid = sheet.getCell(0, i).getContents() == null ? null : sheet.getCell(0, i).getContents().trim();

						// 判断单据号是否为空
						if (StringUtil.isEmpty(ttid)) {
							errMsg.append("第[" + (i + 1) + "]行单据号不能为空，导入失败<br />");
							continue;
						}

						// 获取条码
						String idcode = sheet.getCell(1, i).getContents() == null ? null : sheet.getCell(1, i).getContents().trim();
						// 条码判断
						if (StringUtil.isEmpty(idcode)) {
							errMsg.append("第[" + (i + 1) + "]行条码不能为空，导入失败<br />");
							continue;
						}
						CartonCode cartonCode = null;
						// 获取条码长度
						if (idcode.length() != 20) {
							cartonCode = appCartonCode.getByOutPin(idcode);
						} else {
							cartonCode = appCartonCode.getByCartonCode(idcode);
						}
						
						// 判断箱码表中是否存在
						if (cartonCode == null) {
							errMsg.append("第[" + (i + 1) + "]行条码不存在，导入失败<br />");
							continue;
						}
						// 条码是否存在idcode表中
						Idcode idcode2 = appidcode.getIdcodeById(idcode);
						if (idcode2 == null) {
							errMsg.append("第[" + (i + 1) + "]行条码不存在，导入失败<br />");
							continue;
						}
						// 单据是否存在判断
						TakeTicket takeTicket = appTakeTicket.getTakeTicketById(ttid);
						if (takeTicket == null) {
							errMsg.append("第[" + (i + 1) + "]行单据不存在，导入失败<br />");
							continue;
						}

						// 单据是否复核判断
						if (takeTicket.getIsaudit() != 1) {
							errMsg.append("第[" + (i + 1) + "]行单据尚未复核，导入失败<br />");
							continue;
						}

						//选择的导入机构与该单据中的入库仓库不一致
						if (!takeTicket.getInwarehouseid().equals(warehouseId)) {
							errMsg.append("第[" + (i + 1) + "]行实际导入的仓库与单据中的入库仓库不一致，导入失败<br />");
							continue;
						}
						
						// 如果导入的条码对应的产品已经满了或者没有
						Integer countIdcode = appTakeTicketIdcode.getCodeQuantity(ttid, cartonCode.getProductID());
						TakeTicketDetail takeTicketDetail = appTakeTicketDetail.getDetailByTtidAndPid(ttid, cartonCode.getProductID());
						if(takeTicketDetail == null) {
							errMsg.append("第[" + (i + 1) + "]行条码与单据中的物料号不符<br />");
							continue;
						}
						if (takeTicketDetail.getRealQuantity() == 0 ) {
							errMsg.append("第[" + (i + 1) + "]行单据中无该条码对应产品，导入失败<br />");
							continue;
						}
						
						if (Double.valueOf(countIdcode) >= takeTicketDetail.getRealQuantity()) {
							errMsg.append("第[" + (i + 1) + "]行产品中的码数量已经达到签收数量，导入失败<br />");
							continue;
						}

						// 判断该单号是否已经存在该条码
						TakeTicketIdcode takeTicketIdcode = appTakeTicketIdcode.getTakeTicketIdcodeByttidAndidcode(ttid, idcode);
						if (takeTicketIdcode != null) {
							errMsg.append("第[" + (i + 1) + "]行单据条码已经存在，导入失败<br />");
							continue;
						}

						//判断条码是否在发货仓库(只记录错误信息,不报错)
						if (!takeTicket.getWarehouseid().equals(idcode2.getWarehouseid())) {
							//手工条码上传与采集器条码上传的单据类型不一致，添加错误日志时使用统一的类型
							Integer bSort = 0;
							if(takeTicket.getBsort() == 1) {
								bSort = 4;
							} else if(takeTicket.getBsort() == 2) {
								bSort = 5;
							} else {
								if(takeTicket.getBillno().startsWith("OW")) {
									bSort = 8;
								} else {
									bSort = 20;
								}
							}
							String errorMsg = UploadErrorMsg.getError(UploadErrorMsg.E00005, idcode);
							// 保存条码的特殊信息日志
							appIdcodeUploadLog.addIdcodeUploadLogs(errorMsg, takeTicket.getBillno(), String.valueOf(users.getUserid()), bSort,users.getMakeorganid(), takeTicket.getWarehouseid(), idcode);
						}
						
						
						// sql语句
						StringBuffer stringBuffer = new StringBuffer();
						// 增加一条记录到ttid表中
						stringBuffer.append("\r\n insert into TAKE_TICKET_IDCODE  ");
						stringBuffer.append("\r\n (id,ttid,PRODUCTID,isidcode,warehousebit,batch,unitid,quantity,idcode,makedate,producedate,packquantity) ");
						stringBuffer.append("\r\n select TTidcode_ID_OLD_SEQ.nextval,'" + ttid + "' ,cc.product_id,1,'000' ,pj.batch_number,2,1,'" + idcode + "',sysdate,'"+idcode2.getProducedate()+"','"+idcode2.getPackquantity()+"' ");
						stringBuffer.append("\r\n from CARTON_CODE cc,PRINT_JOB pj ");
						stringBuffer.append("\r\n where cc.CARTON_CODE='" + idcode + "' and cc.PRINT_JOB_ID=pj.print_job_id ");
						EntityManager.updateOrdelete(stringBuffer.toString());
						
						
						// 在stock_alter_move_idcode表中叶增加一条记录
						stringBuffer = new StringBuffer();
						stringBuffer.append("\r\n insert into " + Constants.TT_IDCODE_TABLE[takeTicket.getBsort()]);
						stringBuffer.append("\r\n (id,"+Constants.TT_MAIN_COLUMN[takeTicket.getBsort()]+",PRODUCTID,isidcode,warehousebit,batch,unitid,quantity,idcode,makedate,producedate,packquantity) ");
						stringBuffer.append("\r\n select STOCK_ALTER_MOVE_DETAIL_SEQ.nextval,'" + takeTicket.getBillno() + "' ,cc.product_id,1,'000' ,pj.batch_number,2,1,'" + idcode + "',sysdate,'"+idcode2.getProducedate()+"','"+idcode2.getPackquantity()+"' ");
						stringBuffer.append("\r\n from CARTON_CODE cc,PRINT_JOB pj ");
						stringBuffer.append("\r\n where cc.CARTON_CODE='" + idcode + "' and cc.PRINT_JOB_ID=pj.print_job_id ");
						EntityManager.updateOrdelete(stringBuffer.toString());
						
						// 如果该码在该单据的出库仓库中，并且单据已经签收，则更新条码
						boolean isComplete = false;
						if(takeTicket.getBsort() == 1) {
							StockAlterMove stockAlterMove = appStockAlterMove.getStockAlterMoveByID(takeTicket.getBillno());
							if(stockAlterMove.getIscomplete() == 1) {
								isComplete = true;
							}
						} else if(takeTicket.getBsort() == 2) {
							StockMove stockMove = appStockMove.getStockMoveByID(takeTicket.getBillno());
							if(stockMove.getIscomplete() == 1) {
								isComplete = true;
							}
						} else if(takeTicket.getBsort() == 7) {
							OrganWithdraw organWithdraw = appOrganWithdraw.getOrganWithdrawByID(takeTicket.getBillno());
							if(organWithdraw.getIscomplete() == 1) {
								isComplete = true;
							}
						}
						
						if (takeTicket.getWarehouseid().equals(idcode2.getWarehouseid()) && isComplete) {
							// 更新idcode,标志目前码所在仓库。
							String sql = " Update  idcode  set WAREHOUSEID='" + warehouseId + "' ,WAREHOUSEBIT='000' where idcode ='" + idcode + "' ";
							EntityManager.updateOrdelete(sql);
						}
						
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
			String resultMsg = "上传条码资料成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败：" + (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >" + errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			logger.error("文件上传失败", e);
			return new ActionForward("/sys/lockrecord2.jsp");
		} finally {
			existMaterialCodes.clear();
			notExistsMaterialCodes.clear();
		}
	}
}