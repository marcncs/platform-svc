package com.winsafe.drp.action.warehouse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AmountInventory;
import com.winsafe.drp.dao.AmountInventoryDetail;
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.dao.AppAmountInventoryDetail;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;

public class DealProductStockDifference extends BaseAction {
	private Logger logger = Logger.getLogger(DealProductStockDifference.class);
	private AppFUnit af = new AppFUnit();
	private AppProduct ap = new AppProduct();
	private AppProductStockpile aps = new AppProductStockpile();
	private AppProductStockpileAll apsa = new AppProductStockpileAll();
	private AppOtherShipmentBillDAll apobd = new AppOtherShipmentBillDAll();
	private AppOtherShipmentBillAll apos = new AppOtherShipmentBillAll();
	private AppOtherIncomeAll aoi = new AppOtherIncomeAll();
	private AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		UsersBean users = UserManager.getUser(request);
		initdata(request);
		
		OtherShipmentBillAll osb = null;
		WarehouseBitDafService osbWbds = null;
		OtherIncomeAll oi = null;
		WarehouseBitDafService wbds = null;
		List<OtherShipmentBillDAll> osbd = new ArrayList<OtherShipmentBillDAll>();
		List<OtherIncomeDetailAll> oida = new ArrayList<OtherIncomeDetailAll>();
		Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);

		try {
			String warehouseidString = request.getParameter("WarehouseID");
			String[] productid = request.getParameterValues("productid");
			String[] specmode = request.getParameterValues("specmode");
			String[] productname = request.getParameterValues("productname");
			String[] realstock = request.getParameterValues("realstock");
			String[] resultstock = request.getParameterValues("resultstock");
			String[] remark = request.getParameterValues("remark");
			String[] stockpile = request.getParameterValues("stockpile");
			String[] batch = request.getParameterValues("batch");
			int unitid[] = RequestTool.getInts(request, "unitid");
			StringBuffer productIds = new StringBuffer();

			logger.debug("在AmountInventory中增加一条记录");
			AppAmountInventory aai = new AppAmountInventory();
			AmountInventory ai = new AmountInventory();
			String aiid = MakeCode.getExcIDByRandomTableName(
					"amount_inventory", 2, "AI");
			ai.setId(aiid);
			ai.setOrganid(request.getParameter("MakeOrganID"));
			ai.setWarehouseid(request.getParameter("WarehouseID"));
			// 设置billsort类型(无类型)
			ai.setShipmentsort(0);
			if ("1".equals(sysPro.getProperty("autoAuditAmountInventory"))) {
				ai.setIsaudit(1);
			} else {
				ai.setIsaudit(0);
			}
			ai.setAuditid(users.getUserid());
			ai.setMakeorganid(users.getMakeorganid());
			ai.setMakedeptid(users.getMakedeptid());
			ai.setMakeid(users.getUserid());
			ai.setMakedate(DateUtil.getCurrentDate());
			ai.setRequiredate(DateUtil.getCurrentDate());
			ai.setAuditdate(DateUtil.getCurrentDate());
			ai.setIsblankout(0);
			ai.setBlankoutid(0);
			ai.setIsendcase(0);
			ai.setEndcaseid(0);
			// 设置记账
			ai.setIsaccount(1);
			ai.setTakestaus(0);
			ai.setOrganid(request.getParameter("MakeOrganID"));
			logger.debug("增加AmountInventory单详情");
			AppAmountInventoryDetail aaid = new AppAmountInventoryDetail();
			for (int i = 0; i < productid.length; i++) {
				AmountInventoryDetail aid = new AmountInventoryDetail();
				aid.setOsid(aiid);
				aid.setProductid(productid[i]);
				aid.setProductname(productname[i]);
				aid.setSpecmode(specmode[i]);
				// aid.setUnitid(Constants.DEFAULT_UNIT_ID);
				aid.setUnitid(unitid[i]);
				// 设置当时库存量
				aid.setUnitprice(Double.valueOf(stockpile[i]));
				// 设置盘库时数量
				aid.setQuantity(Double.valueOf(realstock[i]));
				// 设置差异数量
				aid.setTakequantity(ArithDouble.sub(aid.getUnitprice(), aid.getQuantity()));
				// 设置备注
				aid.setRemark(remark[i]);
				aid.setBatch(batch[i]);
				aaid.addAmountInventoryDetail(aid);
				// 自动审核
				if ("1".equals(sysPro.getProperty("autoAuditAmountInventory"))) {
					if (Double.valueOf(resultstock[i]) < 0) {
						// 添加其他出库单
						if (osb == null) {
							osb = createOtherShipmentBill(ai, warehouseidString,request);
							osbWbds = new WarehouseBitDafService(
									"other_shipment_bill_idcode", "osid", osb
											.getWarehouseid());
							osbd.add(createOtherShipmentBillDAll(osb, aid, osbWbds));
						} else {
							osbd.add(createOtherShipmentBillDAll(osb, aid, osbWbds));
						}
					} else if(Double.valueOf(resultstock[i]) > 0){
						// 添加其他入库单
						if(oi == null) {
							oi = createOtherIncomeAll(ai, warehouseidString,request);
							wbds = new WarehouseBitDafService(
									"other_income_idcode_all", "oiid", oi
											.getWarehouseid());
							oida.add(createOtherIncomeDetailAll(oi, aid, wbds));
						} else {
							oida.add(createOtherIncomeDetailAll(oi, aid, wbds));
						}
					}
				}
				productIds.append(",'"+productid[i]+"'");
			}
			//盘点单个产品的数量时,将库存中的其他产品
			List<ProductStockpileAll> pss = apsa.getNotInProductStockpilePidsAndWid(productIds.substring(1), warehouseidString);
			if(pss != null && pss.size()>0) {
				productIds = new StringBuffer();
				for(ProductStockpileAll ps : pss) {
					productIds.append(",'"+ps.getProductid()+"'");
				}
				Map<String,FUnit> funitMap = af.getAllMapByPidsAndUnitId(productIds.substring(1), Constants.DEFAULT_UNIT_ID);
				for(ProductStockpileAll ps : pss) {
					Product product = ap.loadProductById(ps.getProductid());
					AmountInventoryDetail aid = new AmountInventoryDetail();
					aid.setOsid(aiid);
					aid.setProductid(product.getId());
					aid.setProductname(product.getProductname());
					aid.setSpecmode(product.getSpecmode());
					aid.setUnitid(Constants.DEFAULT_UNIT_ID);
					Double quantity = ArithDouble.div(ps.getStockpile(), funitMap.get(product.getId()).getXquantity());
					// 设置当时库存量
					aid.setUnitprice(quantity);
					// 设置盘库时数量
					aid.setQuantity(quantity);
					// 设置差异数量
					aid.setTakequantity(0d);
					// 设置备注
					aid.setBatch(ps.getBatch());
					aaid.addAmountInventoryDetail(aid);
				}
			}
			
			
			aai.addAmountInventory(ai);
			if ("1".equals(sysPro.getProperty("autoAuditAmountInventory"))) {
				// 其他出库操作
				if(osb != null) {
					auditOtherShipment(osb, osbd, request);
				}
				// 其他入库操作
				if(oi != null) {
					auditOtherIncome(oi, oida, request);
				}
			} else {
				addNotificationMail(ai);
			}
			
			request.setAttribute("result", "databases.inventory.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}

	private void addNotificationMail(AmountInventory ai) {
		AppUsers appUser = new AppUsers();
		List<Users> auditUsers = appUser.getUsersByOperate("审核","数量盘点");
		for(Users user : auditUsers) {
			if(!StringUtil.isEmpty(user.getEmail())) {
				addToAuditNotificationMail(user, ai.getId());
			}
		}
	}

	private void addToAuditNotificationMail(Users user, String billNo) {
		AppBatchCompleteMail abcm = new AppBatchCompleteMail();
		BatchCompleteMail mail = new BatchCompleteMail();
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		mail.setMailSender(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailFrom(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailTo(user.getEmail());//发送给谁
		mail.setMailSubject(mailPro.getProperty("ai_mail_subject"));//邮件标题
		mail.setCreateDate(DateUtil.getCurrentDate());
		mail.setMailType(MailType.AMOUNT_INVENTORY_AUDIT.getDbValue());
		String mailBody = MessageFormat.format(mailPro.getProperty("ai_mail_body"),billNo);
		mail.setMailBody(mailBody);//内容
		abcm.add(mail);
	}

	private void auditOtherIncome(OtherIncomeAll oi,
			List<OtherIncomeDetailAll> oida, HttpServletRequest request) throws Exception {
		ProductStockpile ps = null;
		for (OtherIncomeDetailAll pid : oida) {
			ps = new ProductStockpile();
		
			ps.setId(Long.valueOf(MakeCode
					.getExcIDByRandomTableName("product_stockpile",
							0, "")));
			ps.setProductid(pid.getProductid());
			// 设置最小单位
			Product p = ap.getProductByID(pid.getProductid());
			ps.setCountunit(p.getSunit());
			ps.setBatch(pid.getBatch());
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(oi.getWarehouseid());
			ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			aps.addProductByPurchaseIncome2(ps);
			Double quantity = af.getQuantity(pid.getProductid(),
					pid.getUnitid(), pid.getQuantity());
			if (oi.getIsaccount() != null && oi.getIsaccount() == 1) {
				// 记台账
				aps.inProductStockpile(pid.getProductid(), p
						.getSunit(), pid.getBatch(), quantity, oi
						.getWarehouseid(), ps.getWarehousebit(), oi
						.getId(), "盘盈入库");
			} else {
				aps.inProductStockpileWithOutAccount(pid
						.getProductid(), p.getSunit(), pid
						.getBatch(), quantity, oi.getWarehouseid(),
						ps.getWarehousebit(), oi.getId(), "盘盈入库");
			}
		}
		aoi.updIsAudit(oi.getId(), userid, 1);
		DBUserLog.addUserLog(request, "编号：" + oi.getId());
		
	}

	private OtherIncomeDetailAll createOtherIncomeDetailAll(OtherIncomeAll oi,
			AmountInventoryDetail aid, WarehouseBitDafService wbds) throws Exception {
		OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
		oid.setId(Integer.valueOf(MakeCode
				.getExcIDByRandomTableName(
						"other_income_detail_all", 0, "")));
		oid.setOiid(oi.getId());
		oid.setProductid(aid.getProductid());
		oid.setSpecmode(aid.getSpecmode());
		oid.setProductname(aid.getProductname());
		oid.setRemark(aid.getRemark());
		// oid.setUnitid(Constants.DEFAULT_UNIT_ID);
		oid.setUnitid(aid.getUnitid());
		oid.setBatch(aid.getBatch());
		oid.setQuantity(Math.abs(aid.getTakequantity()));
		aoid.addOtherIncomeDetail(oid);
		wbds.add(oi.getId(), oid.getProductid(), oid
						.getUnitid(), oid.getQuantity(), oid
						.getBatch());
		return oid;
	}

	private OtherIncomeAll createOtherIncomeAll(AmountInventory ai,
			String warehouseidString, HttpServletRequest request) throws Exception {
		OtherIncomeAll oi = new OtherIncomeAll();
		String oiid = MakeCode.getExcIDByRandomTableName(
				"other_income_all", 2, "QR");
		oi.setId(oiid);
		oi.setOrganid(ai.getOrganid());
		oi.setWarehouseid(warehouseidString);
		oi.setIncomesort(0);
		oi.setBillno(request.getParameter("billno"));
		oi.setIsaudit(0);
		oi.setAuditid(0);
		oi.setMakeorganid(users.getMakeorganid());
		oi.setMakedeptid(users.getMakedeptid());
		oi.setMakeid(userid);
		oi.setMakedate(DateUtil.getCurrentDate());
		oi.setKeyscontent(oi.getId() + "," + "," + oi.getBillno()
				+ "," + oi.getRemark());

		// 记账
		oi.setIsaccount(1);
		aoi.addOtherIncomeAll(oi);
		DBUserLog.addUserLog(request, "编号:" + oiid);
		return oi;
	}

	private void auditOtherShipment(OtherShipmentBillAll osb,
			List<OtherShipmentBillDAll> osbd, HttpServletRequest request) throws Exception {

		ProductStockpile ps = null;
		// 出库复核

		for (OtherShipmentBillDAll ttd : osbd) {
			ps = new ProductStockpile();
			ps.setId(Long.valueOf(MakeCode
					.getExcIDByRandomTableName("product_stockpile",
							0, "")));
			ps.setProductid(ttd.getProductid());
			// 设置最小单位
			Product p = ap.getProductByID(ttd.getProductid());
			ps.setCountunit(p.getSunit());
			ps.setBatch(ttd.getBatch());
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(osb.getWarehouseid());
			ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			aps.addProductByPurchaseIncome2(ps);
			Double quantity = af.getQuantity(ttd.getProductid(),
					ttd.getUnitid(), ttd.getQuantity());
			// 记台账
			// aps.outPrepareout(tt.getWarehouseid(),
			// Constants.WAREHOUSE_BIT_DEFAULT, ttd
			// .getProductid(), ttd.getUnitid(), ttd.getBatch(),
			// ttd.getQuantity(), tt.getId(), "其他出入库-其他出库", true);
			// apsa.outPrepareout(tt.getWarehouseid(),
			// ttd.getProductid(),
			// ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity());
			aps.outProductStockpile(ttd.getProductid(), p
					.getSunit(), ttd.getBatch(), quantity, osb
					.getWarehouseid(), ps.getWarehousebit(), osb
					.getId(), "盘亏出库");

		}

		apos.updIsAudit(osb.getId(), userid, 1);

		DBUserLog.addUserLog(request, "编号：" + osb.getId());
		
	}

	private OtherShipmentBillDAll createOtherShipmentBillDAll(
			OtherShipmentBillAll osb, AmountInventoryDetail aid, WarehouseBitDafService wbds) throws Exception {
		OtherShipmentBillDAll osbd = new OtherShipmentBillDAll();
		osbd.setId(Integer.valueOf(MakeCode
				.getExcIDByRandomTableName(
						"other_shipment_bill_detail", 0, "")));
		osbd.setOsid(osb.getId());
		osbd.setProductid(aid.getProductid());
		osbd.setSpecmode(aid.getSpecmode());
		osbd.setProductname(aid.getProductname());
			osbd.setRemark(aid.getRemark());
		// 单位默认为包
		// osbd.setUnitid(Constants.DEFAULT_UNIT_ID);
		osbd.setUnitid(aid.getUnitid());
		osbd.setBatch(aid.getBatch());
		osbd.setUnitprice(0.00);
		osbd.setQuantity(Math.abs(aid.getTakequantity()));
		osbd.setSubsum(0.00);
		osbd.setTakequantity(0d);
		apobd.addOtherShipmentBillDetail(osbd);
		// totalsum+=osbd.getSubsum();
		wbds.add(osb.getId(), osbd.getProductid(), osbd
				.getUnitid(), osbd.getQuantity(), osbd
				.getBatch());
		return osbd;
	}

	private OtherShipmentBillAll createOtherShipmentBill(AmountInventory ai, String warehouseidString, HttpServletRequest request) throws Exception {
		OtherShipmentBillAll osb = new OtherShipmentBillAll();
		String osid = MakeCode.getExcIDByRandomTableName(
				"other_shipment_bill_all", 2, "QC");
		osb.setId(osid);
		osb.setOrganid(ai.getOrganid());
		osb.setWarehouseid(warehouseidString);
		// 设置出库类别
		osb.setShipmentsort(1);
		osb.setRequiredate(DateUtil.getCurrentDate());
		// 标记是否符合
		osb.setIsaudit(0);
		osb.setAuditid(0);
		osb.setMakeorganid(users.getMakeorganid());
		osb.setMakedeptid(users.getMakedeptid());
		osb.setMakeid(users.getUserid());
		osb.setMakedate(DateUtil.getCurrentDate());
		osb.setIsblankout(0);
		osb.setBlankoutid(0);
		osb.setIsendcase(0);
		osb.setEndcaseid(0);
		osb.setTakestaus(0);
		osb.setIsaccount(1);
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(osb.getId()).append(",").append(osb.getBillno())
				.append(",").append(osb.getRemark()).append(",");
		WarehouseBitDafService wbds = new WarehouseBitDafService(
				"other_shipment_bill_idcode", "osid", osb.getWarehouseid());
		osb.setKeyscontent(keyscontent.toString());
		
		apos.addOtherShipmentBill(osb);
		DBUserLog.addUserLog(request, "编号:" + osid);
		return osb;
	}

}