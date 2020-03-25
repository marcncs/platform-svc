package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.MakeCode;

public class CompleteAllStockAlterMoveReceiveAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			AppStockAlterMove asm = new AppStockAlterMove();
			AppStockAlterMoveIdcode appmi = new AppStockAlterMoveIdcode();

			String Condition = " where  sm.isshipment=1 and sm.isblankout=0  and sm.iscomplete=0  and sm.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where wv.activeFlag=1 and wv.userId="
					+ userid + ") ";
//			Map map = new HashMap(request.getParameterMap());
//			Map tmpMap = EntityManager.scatterMap(map);
//			String[] tablename = { "StockAlterMove" };
//			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
//			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MoveDate");
//			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
//			whereSql = whereSql + timeCondition + blur + Condition;
//			whereSql = DbUtil.getWhereSql(whereSql);

			AppStockAlterMove asa = new AppStockAlterMove();
			// 得到登录用户所有未签收的单据
			List<StockAlterMove> sals = asa.getStockAlterMove(Condition);

			int i=0;
			for (StockAlterMove sam : sals) {
				i++;
				List<StockAlterMoveIdcode> idcodelist = appmi.getStockAlterMoveIdcodeBysamid(sam.getId(), 1);

				AppTakeTicket att = new AppTakeTicket();
				List<TakeTicket> tts = att.getTakeTicketByBillno(sam.getId());
				TakeTicket tt = tts.get(0);

				AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
				List<TakeTicketDetailBatchBit> batchBitList = appTakeTicketDetailBatchBit.getBatchBitByTTID(tt.getId());

				asm.updStockAlterMoveIsComplete(sam.getId(), 1, userid);

				addIdcode(sam, idcodelist);

				addProductStockpile(batchBitList, sam.getInwarehouseid(), sam.getId());
				
				if(i%50==0){
					HibernateUtil.commitTransaction();
				}

			}

			request.setAttribute("result", "databases.audit.success");
			// DBUserLog.addUserLog(userid, 7, "入库>>订购入库>>签收订购入库,编号：" + omid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

	private void addProductStockpile(List<TakeTicketDetailBatchBit> batchBitList, String warehouseid, String omid)
			throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (TakeTicketDetailBatchBit ttdbd : batchBitList) {
			ps = new ProductStockpile();

			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			ps.setProductid(ttdbd.getProductid());

			ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());

			// 如果批次长度为12位 就截取前9位作为批次保存
			if (ttdbd.getBatch() != null && !ttdbd.getBatch().equals("") && ttdbd.getBatch().length() == 12) {
				String batch = ttdbd.getBatch().substring(0, 9);
				ps.setBatch(batch);
			} else {
				// 否则按原批次存储
				ps.setBatch(ttdbd.getBatch());
			}

			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(ttdbd.getWarehouseBit());
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			ps.setVerifyStatus(1);
			aps.addProductByPurchaseIncome(ps);

			aps.inProductStockpile(ps.getProductid(), ttdbd.getUnitid(), ps.getBatch(), ttdbd.getRealQuantity(), ps
					.getWarehouseid(), ps.getWarehousebit(), omid, "订购入库-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}

	private void addIdcode(StockAlterMove pi, List<StockAlterMoveIdcode> idcodelist) throws Exception {
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		String idcodeid = "";
		for (StockAlterMoveIdcode wi : idcodelist) {
			// 如果是托条码
			if (wi.getUnitid() == 17) {
				// 托中最后一条条码的后几位顺序码
				int lastidocde = Integer.parseInt(wi.getEndno().substring(4, 13));
				// 托中第一条条码的后几位顺序码
				int firstidocde = Integer.parseInt(wi.getStartno().substring(4, 13));
				// 条码前四位
				String idcodeprefix = wi.getEndno().substring(0, 4);
				// 托条码中的箱数量
				int n = lastidocde - firstidocde + 1;
				for (int k = n; k > 0; k--) {
					idcodeid = idcodeprefix + String.format("%09d", lastidocde);
					lastidocde--;

					Idcode idcode = appidcode.getIdcodeById(idcodeid);

					appidcode.updIsUse(idcode.getIdcode(), pi.getReceiveorganid(), pi.getInwarehouseid(), idcode
							.getWarehousebit(), 1, 0);
				}
			} else {
				ic = new Idcode();
				ic.setIdcode(wi.getIdcode());
				ic.setProductid(wi.getProductid());
				ic.setProductname(ap.getProductNameByID(ic.getProductid()));
				ic.setBatch(wi.getBatch());
				ic.setProducedate(wi.getProducedate());
				ic.setVad(wi.getValidate());
				ic.setLcode(wi.getLcode());
				ic.setStartno(wi.getStartno());
				ic.setEndno(wi.getEndno());
				ic.setUnitid(wi.getUnitid());
				ic.setQuantity(1d);
				ic.setFquantity(wi.getPackquantity());
				ic.setPackquantity(wi.getPackquantity());
				ic.setIsuse(1);
				ic.setIsout(0);
				ic.setBillid(wi.getSamid());
				ic.setIdbilltype(3);
				ic.setMakeorganid(pi.getReceiveorganid());
				ic.setWarehouseid(pi.getInwarehouseid());
				ic.setWarehousebit(wi.getWarehousebit());
				ic.setProvideid("");
				ic.setProvidename("");
				ic.setMakedate(wi.getMakedate());
				appidcode.addIdcode2(ic);
				appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic.getWarehouseid(), ic.getWarehousebit(), 1, 0);
			}
		}
	}

}
