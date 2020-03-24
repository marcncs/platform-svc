package com.winsafe.drp.action.machin;

import java.util.List;
import java.util.Set;

import jstels.database.i;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.dao.OtherIncomeDetailAll;
import com.winsafe.drp.dao.OtherIncomeDetailForm;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

public class AddOtherIncomeService {
	private Logger logger = Logger.getLogger(AddOtherIncomeAction.class);

	public AddOtherIncomeService() {

	}

	// public void addOtherIncome(String organId, String warehouseId, int
	// incomeSort, String billNo, String remark,
	// UsersBean users, String isAccount, String[] productIds, String[]
	// productNames, String[] specmodes,
	// int[] unitIds, String[] batchs, double[] quantitys) {
	//
	// try {
	// OtherIncomeAll oi = new OtherIncomeAll();
	// String oiid = MakeCode.getExcIDByRandomTableName("other_income_all", 2,
	// "QR");
	// oi.setId(oiid);
	// oi.setOrganid(organId);
	// oi.setWarehouseid(warehouseId);
	// oi.setIncomesort(incomeSort);
	// oi.setBillno(billNo);
	// oi.setRemark(remark);
	// oi.setIsaudit(0);
	// oi.setAuditid(0);
	// oi.setMakeorganid(users.getMakeorganid());
	// oi.setMakedeptid(users.getMakedeptid());
	// oi.setMakeid(users.getUserid());
	// oi.setMakedate(DateUtil.getCurrentDate());
	// oi.setKeyscontent(oi.getId() + "," + "," + oi.getBillno() + "," +
	// oi.getRemark());
	//
	// String isaccount = isAccount;
	// // 不记账
	// if (isaccount == null) {
	// oi.setIsaccount(0);
	// } else {
	// // 记账
	// oi.setIsaccount(1);
	// }
	//
	// String productid[] = productIds;
	// String productname[] = productNames;
	// String specmode[] = specmodes;
	// int unitid[] = unitIds;
	// String batch[] = batchs;
	// double quantity[] = quantitys;
	//
	// AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
	// WarehouseBitDafService wbds = new
	// WarehouseBitDafService("other_income_idcode_all", "oiid", oi
	// .getWarehouseid());
	//
	// for (int i = 0; i < productid.length; i++) {
	// OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
	// oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("other_income_detail_all",
	// 0, "")));
	// oid.setOiid(oiid);
	// oid.setProductid(productid[i]);
	// oid.setProductname(productname[i]);
	// oid.setSpecmode(specmode[i]);
	// oid.setUnitid(unitid[i]);
	// oid.setBatch(batch[i]);
	// oid.setQuantity(quantity[i]);
	// aoid.addOtherIncomeDetail(oid);
	// // 插入idcode
	// wbds.add(oi.getId(), oid.getProductid(), oid.getUnitid(),
	// oid.getQuantity(), oid.getBatch());
	// }
	//
	// AppOtherIncomeAll aoi = new AppOtherIncomeAll();
	// aoi.addOtherIncomeAll(oi);
	// } catch (Exception e) {
	// logger.error("AddOtherIncomeService add Other Income error:", e);
	// }
	//
	// }

	public void addOtherIncome(String organId, String warehouseId, int incomeSort, String billNo, String remark,
			UsersBean users, String isAccount, List<OtherIncomeDetailForm> list) {

		try {
			OtherIncomeAll oi = new OtherIncomeAll();
			String oiid = MakeCode.getExcIDByRandomTableName("other_income_all", 2, "QR");
			oi.setId(oiid);
			oi.setOrganid(organId);
			oi.setWarehouseid(warehouseId);
			oi.setIncomesort(incomeSort);
			oi.setBillno(billNo);
			oi.setRemark(remark);
			oi.setIsaudit(0);
			oi.setAuditid(0);
			oi.setMakeorganid(users.getMakeorganid());
			oi.setMakedeptid(users.getMakedeptid());
			oi.setMakeid(users.getUserid());
			oi.setMakedate(DateUtil.getCurrentDate());
			oi.setKeyscontent(oi.getId() + "," + "," + oi.getBillno() + "," + oi.getRemark());

			String isaccount = isAccount;
			// 不记账
			if (isaccount == null) {
				oi.setIsaccount(0);
			} else {
				// 记账
				oi.setIsaccount(1);
			}

			AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_income_idcode_all", "oiid", oi
					.getWarehouseid());

			for (int i = 0; i < list.size(); i++) {
				OtherIncomeDetailForm otherIncomeDetailForm = list.get(i);
				OtherIncomeDetailAll oid = new OtherIncomeDetailAll();
				oid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("other_income_detail_all", 0, "")));
				oid.setOiid(oiid);
				oid.setProductid(otherIncomeDetailForm.getProductid());
				oid.setProductname(otherIncomeDetailForm.getProductname());
				oid.setSpecmode(otherIncomeDetailForm.getSpecmode());
				oid.setUnitid(otherIncomeDetailForm.getUnitid());
				oid.setBatch(otherIncomeDetailForm.getBatch());
				oid.setQuantity(otherIncomeDetailForm.getQuantity());
				aoid.addOtherIncomeDetail(oid);
				// 插入idcode
				wbds.add(oi.getId(), oid.getProductid(), oid.getUnitid(), oid.getQuantity(), oid.getBatch());
			}

			AppOtherIncomeAll aoi = new AppOtherIncomeAll();
			aoi.addOtherIncomeAll(oi);
		} catch (Exception e) {
			logger.error("AddOtherIncomeService add Other Income error:", e);
		}

	}

}
