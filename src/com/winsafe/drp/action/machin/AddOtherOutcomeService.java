package com.winsafe.drp.action.machin;

import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.OtherShipmentBillDAllForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddOtherOutcomeService {

	private Logger logger = Logger.getLogger(AddOtherOutcomeService.class);
	private AppOtherShipmentBillDAll apobd = new AppOtherShipmentBillDAll();;

	public AddOtherOutcomeService() {
	}

	public void addOtherOutcome(String organId, String warehouseId, int incomeSort, String billNo, String remark,
			UsersBean users, String isAccount, List<OtherShipmentBillDAllForm> list) {

		try {
			OtherShipmentBillAll osb = new OtherShipmentBillAll();
			String osid = MakeCode.getExcIDByRandomTableName("other_shipment_bill_all", 2, "QC");
			osb.setId(osid);
			osb.setOrganid(organId);
			osb.setWarehouseid(warehouseId);
			osb.setShipmentsort(incomeSort);
			osb.setBillno(billNo);
			osb.setRemark(remark);
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

			String isaccount = isAccount;
			// 不记账
			if (isaccount == null) {
				osb.setIsaccount(0);
			} else {
				// 记账
				osb.setIsaccount(1);
			}

			AppOtherIncomeDetailAll aoid = new AppOtherIncomeDetailAll();
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode", "osid", osb
					.getWarehouseid());
			for (int i = 0; i < list.size(); i++) {
				OtherShipmentBillDAllForm otherShipmentBillDAllForm = list.get(i);
				OtherShipmentBillDAll osbd = new OtherShipmentBillDAll();
				osbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("other_shipment_bill_detail", 0, "")));
				osbd.setOsid(osid);
				osbd.setProductid(otherShipmentBillDAllForm.getProductid());
				osbd.setProductname(otherShipmentBillDAllForm.getProductname());
				osbd.setSpecmode(otherShipmentBillDAllForm.getSpecmode());
				osbd.setUnitid(otherShipmentBillDAllForm.getCountUnit());
				osbd.setBatch(otherShipmentBillDAllForm.getBatch());
				osbd.setQuantity(otherShipmentBillDAllForm.getQuantity());
				apobd.addOtherShipmentBillDetail(osbd);
				// 插入idcode
				wbds.add(osb.getId(), osbd.getProductid(), osbd.getUnitid(), osbd.getQuantity(), osbd.getBatch());
			}

			AppOtherShipmentBillAll apos = new AppOtherShipmentBillAll();
			apos.addOtherShipmentBill(osb);
		} catch (Exception e) {
			logger.error("AddOtherIncomeService add Other Income error:", e);
		}

	}
}
