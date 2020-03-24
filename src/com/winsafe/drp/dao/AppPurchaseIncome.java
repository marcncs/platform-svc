package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ReplyInfo;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseIncome {

	private static final Logger log = Logger.getLogger(AppPurchaseIncome.class);

	public List searchPurchaseIncome(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select pi from PurchaseIncome as pi,WarehouseVisit as wv "
				+ pWhereClause + " order by pi.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getPurchaseIncomeObject(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from PurchaseIncome as pi " + pWhereClause
				+ " order by pi.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(pi.totalsum) from PurchaseIncome as pi "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void updPurchaseIncome(PurchaseIncome pi) throws Exception {
		EntityManager.update(pi);
	}

	public List getPurchaseIncomeTotal(String pWhereClause) throws Exception {
		String sql = "select pi.id,pi.providename,pi.makedate from PurchaseIncome as pi "
				+ pWhereClause + " order by pi.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	//
	public void addPurchaseIncome(Object purchaseincome) throws Exception {
		EntityManager.save(purchaseincome);

	}

	public PurchaseIncome getPurchaseIncomeByID(String id) throws Exception {
		String sql = " from PurchaseIncome where id='" + id + "'";
		return (PurchaseIncome) EntityManager.find(sql);
	}

	public String getPurchaseIncomeNccodeByID(String id) throws Exception {
		String result = "";
		PurchaseIncome purchaseIncome = new PurchaseIncome();
		purchaseIncome = this.getPurchaseIncomeByID(id);
		result = purchaseIncome.getNccode();
		return result;
	}

	public PurchaseIncome getPurchaseIncomeByNCcode(String nccode)
			throws Exception {
		String sql = " from PurchaseIncome where nccode='" + nccode + "'";
		return (PurchaseIncome) EntityManager.find(sql);
	}

	public String getIdByNCcode(String nccode) throws Exception {
		PurchaseIncome temp = this.getPurchaseIncomeByNCcode(nccode);
		return temp.getId();
	}

	public void updIsRefer(String id) throws Exception {

		String sql = "update purchase_income set isrefer=1 where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delPurchaseIncome(String id) throws Exception {

		String sql = "delete from purchase_income where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void delPurchaseIncomeByNCcode(String nccode) throws Exception {

		String sql = "delete from purchase_income where NCcode='" + nccode
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update purchase_income set approvestatus=" + isapprove
				+ " where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsComplete(String id, Long userid) throws Exception {

		String sql = "update purchase_income set iscomplete=1,completeid="
				+ userid + ",completedate='" + DateUtil.getCurrentDateString()
				+ "' where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List waitApprovePurchaseIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pi.id,pi.warehouseid,pi.poid,pi.providename,pi.incomedate,pi.isrefer,pia.actid,pia.approve from PurchaseIncome as pi,PurchaseIncomeApprove as pia "
				+ pWhereClause + " order by pia.approve, pi.incomedate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}

	public int findCountBySLID(Long slid) throws Exception {
		int f = 0;
		String sql = "select count(*) from ProductIncome where iscomplete = 0 and slid="
				+ slid;
		if (slid > 0) {
			f = EntityManager.getRecordCount(sql);
		}
		return f;
	}

	public List getPurchaseIncomeForPurchaseInvoice(int pagesize,
			String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		List ls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pid.id,pid.piid,pid.productid,pid.productname,pid.specmode,pid.unitid,pi.batch,pid.unitprice,pid.quantity,pid.subsum from PurchaseIncomeDetail as pid,PurchaseIncome as pi, Product as p "
				+ pWhereClause + " order by pid.id desc ";

		ls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return ls;
	}

	public void updIsAudit(String ppid, int userid, int audit) throws Exception {
		String sql = "update purchase_income set isaudit=" + audit
				+ ",auditid=" + userid + ",auditdate=getdate() where id='"
				+ ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsAuditByNccode(String nccode, int userid, int audit)
			throws Exception {
		String sql = "update purchase_income set isaudit=" + audit
				+ ",auditid=" + userid + ",auditdate=getdate() where Nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsTally(String ppid, int userid, Integer tally)
			throws Exception {
		String sql = "update purchase_income set istally=" + tally
				+ ",tallyid=" + userid + ",tallydate=getdate() where id='"
				+ ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List selectPurchaseIncome(String whereSql) throws Exception {
		List ls = null;
		String sql = "select pid.id,pi.incomedate,pi.warehouseid,pid.productid,pid.productname,pid.specmode,pid.unitid,pid.quantity from PurchaseIncome as pi,PurchaseIncomeDetail as pid where pi.id=pid.piid and pid.issettlement=0 "
				+ whereSql;
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public List getPurchaseProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select p.makeorganid,pd.productid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity, sum(pd.subsum) as subsum "
				+ " from Purchase_Income_Detail pd, Purchase_Income p "
				+ whereSql
				+ " group by p.makeorganid,pd.productid,pd.productname, pd.specmode, pd.unitid ";
		return PageQuery
				.jdbcSqlserverQuery(request, "productid", hql, pagesize);
	}

	public List getPurchaseProductTotal(String whereSql) throws Exception {
		String hql = "select p.makeorganid,pd.productid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity, sum(pd.subsum) as subsum "
				+ " from Purchase_Income_Detail pd, Purchase_Income p "
				+ whereSql
				+ " group by p.makeorganid,pd.productid,pd.productname, pd.specmode, pd.unitid ";
		return EntityManager.jdbcquery(hql);
	}

	public List getTotal(String whereSql) throws Exception {
		String hql = "select sum(pd.quantity) as quantity,sum(factquantity) as factquantity, sum(pd.subsum) as subsum "
				+ " from Purchase_Income_Detail pd, Purchase_Income p "
				+ whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public double getTotalBillSum(String whereSql) throws Exception {
		String hql = "select sum(totalsum) as totalsum from PurchaseIncome pb "
				+ whereSql;
		return EntityManager.getdoubleSum(hql);
	}

	public List getPurchaseBill(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql = " from PurchaseIncome as pb " + whereSql
				+ " order by pb.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getPurchaseBill(String whereSql) {
		String hql = " from PurchaseIncome as pb " + whereSql
				+ " order by pb.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	public List<PurchaseIncome> searchPurchaseIncome(String whereSql) {
		String hql = "select pi from PurchaseIncome as pi,WarehouseVisit as wv "
				+ whereSql + " order by pi.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	/**
	 * 根据nccode使单据作废
	 * 
	 * @param nccode用友给出的id
	 * @param psnname批单人
	 */
	public ReplyInfo updateblankoutByNCcode(String nccode, String psnname)
			throws Exception {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		PurchaseIncome temp = new PurchaseIncome();
		temp = this.getPurchaseIncomeByNCcode(nccode);
		// 如果查不到该条记录则返回报错xml
		if (temp == null) {
			flag = false;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("none_record");

		} else {
			// 判断该单据是否已记帐
			if (temp.getIstally() == 1) {
				flag = false;
				info = "单据已记帐";
				return replyInfo;
			} else {// 如果该条记录已经复核过则取消复核
				HibernateUtil.currentTransaction();
				// 删除主表记录
				this.delPurchaseIncomeByNCcode(nccode);
				AppPurchaseIncomeDetail app = new AppPurchaseIncomeDetail();
				AppPurchaseIncomeIdcode apidcode = new AppPurchaseIncomeIdcode();
				// 删除detail表中记录
				app.delPurchaseIncomeDetailByNCcode(nccode);
				// 删除idcode表中记录
				apidcode.delPurchaseIncomeIDcodeByNCcode(nccode);
				HibernateUtil.commitTransaction();
				flag = true;
				info = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("success");
			}
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	/**
	 * 检验该单据是否已经复核过
	 * 
	 * @param nccode用友给出的编号
	 * @return result1为已经复核过0为未复核
	 */
	public boolean checkIsAudit(String nccode) {
		boolean result = false;
		String sql = " from PurchaseIncome where NCcode='" + nccode + "'";
		PurchaseIncome temp = new PurchaseIncome();
		temp = (PurchaseIncome) EntityManager.find(sql);
		if (temp.getIsaudit() == 0) {
			result = true;
		}
		return result;

	}

	/**
	 * 入库PurchaseIncome保存的方式
	 * 
	 * @param purchaseIncome需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo savePurchaseIncome(PurchaseIncome purchaseIncome) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppPurchaseIncome appPurchaseIncome = new AppPurchaseIncome();
		try {
			HibernateUtil.currentTransaction();
			appPurchaseIncome.addPurchaseIncome(purchaseIncome);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------保存成功------" + flag);
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------PurchaseIncome保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	/**
	 * 入库PurchaseIncomeDetail保存的方式
	 * 
	 * @param purchaseIncomeDetail需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo savePurchaseIncomeDetail(
			PurchaseIncomeDetail purchaseIncomeDetail) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppPurchaseIncomeDetail appPurchaseIncomeDetail = new AppPurchaseIncomeDetail();
		try {
			HibernateUtil.currentTransaction();
			appPurchaseIncomeDetail
					.addPurchaseIncomeDetail(purchaseIncomeDetail);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------PurchaseIncomeDetail保存-----成功");
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------PurchaseIncomeDetail保存------" + e.toString());

		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	/**
	 * 检货采购入库
	 * 
	 * @param purchaseIncomeId产成品ID
	 * @param userId根据psname得到的制单人信息
	 * @return result检货结果
	 */
	public static String auditPurchaseIncome(String purchaseIncomeId,
			String userId) {
		String result = "success";
		AppFUnit appFUnit = new AppFUnit();
		AppPurchaseIncome appPurchaseIncome = new AppPurchaseIncome();
		AppPurchaseIncomeIdcode appPurchaseIncomeIdcode = new AppPurchaseIncomeIdcode();
		AppPurchaseIncomeDetail appPurchaseIncomeDetail = new AppPurchaseIncomeDetail();
		PurchaseIncome purchaseIncome = new PurchaseIncome();
		List<PurchaseIncomeDetail> purchaseIncomeDetails = new ArrayList<PurchaseIncomeDetail>();
		List<PurchaseIncomeIdcode> purchaseIncomeIdcodes = new ArrayList<PurchaseIncomeIdcode>();
		try {
			purchaseIncome = appPurchaseIncome
					.getPurchaseIncomeByID(purchaseIncomeId);

			// 判断该单据是否已经复核过
			if (purchaseIncome.getIsaudit() == 1) {
				result = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("have_auditted");
				return result;
			}
			purchaseIncomeDetails = appPurchaseIncomeDetail
					.getPurchaseIncomeDetailByPbId2(purchaseIncomeId);
			for (PurchaseIncomeDetail tempPID : purchaseIncomeDetails) {
				double q1 = appFUnit.getQuantity(tempPID.getProductid(),
						tempPID.getUnitid(), tempPID.getQuantity());
				double q2 = appFUnit.getPkgQuantityInProductIncomeIdcode(
						tempPID.getProductid(), purchaseIncomeId);
				// 判断单据色数量是否相同
				if (q1 != q2) {
					result = ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("out_of_quantity");
					return result;
				}
			}
			purchaseIncomeIdcodes = appPurchaseIncomeIdcode
					.getPurchaseIncomeIdcodeByPiid(purchaseIncomeId, 1);

			addIdcode(purchaseIncome, purchaseIncomeIdcodes);

			appPurchaseIncome.updIsAudit(purchaseIncomeId, Integer
					.valueOf(userId), 1);

			List<PurchaseIncomeIdcode> idlist = appPurchaseIncomeIdcode
					.getPurchaseIncomeIdcodeByPiid(purchaseIncomeId);
			addProductStockpile(idlist, purchaseIncome.getWarehouseid());

			DBUserLog.addUserLog(Integer.valueOf(userId), 7,
					"入库>>采购入库>>复核采购入库,编号：" + purchaseIncomeId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------复核时查找出错------" + e.toString());
		}

		return result;
	}

	private static void addProductStockpile(List<PurchaseIncomeIdcode> idlist,
			String warehouseid) throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (PurchaseIncomeIdcode idcode : idlist) {
			ps = new ProductStockpile();

			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_stockpile", 0, "")));
			ps.setProductid(idcode.getProductid());

			ps
					.setCountunit(ap.getProductByID(ps.getProductid())
							.getCountunit());
			ps.setBatch(idcode.getBatch());
			ps.setProducedate(idcode.getProducedate());
			ps.setVad(idcode.getValidate());
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(idcode.getWarehousebit());
			ps.setMakedate(DateUtil.getCurrentDate());
			aps.addProductByPurchaseIncome(ps);

			aps.inProductStockpile(ps.getProductid(), idcode.getUnitid(), ps
					.getBatch(), idcode.getQuantity(), ps.getWarehouseid(), ps
					.getWarehousebit(), idcode.getPiid(), "采购-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps
					.getBatch());
		}
	}

	private static void addIdcode(PurchaseIncome pi,
			List<PurchaseIncomeIdcode> idcodelist) throws Exception {
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (PurchaseIncomeIdcode wi : idcodelist) {
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
			ic
					.setQuantity(af.getQuantity(ic.getProductid(), ic
							.getUnitid(), 1));
			ic.setFquantity(ic.getQuantity());
			ic.setPackquantity(wi.getPackquantity());
			ic.setIsuse(1);
			ic.setIsout(0);
			ic.setBillid(wi.getPiid());
			ic.setIdbilltype(1);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());
			ic.setProvideid(pi.getProvideid());
			ic.setProvidename(pi.getProvidename());
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic
					.getWarehouseid(), ic.getWarehousebit(), 1, 0);
		}
	}
}
