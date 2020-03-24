package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.List;
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
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOtherIncome {

	private static final Logger log = Logger.getLogger(AppOtherIncome.class);

	public List getOtherIncome(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from OtherIncome  " + pWhereClause
				+ " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(oid.subsum) from OtherIncome as oi, OtherIncomeDetail as oid "
				+ pWhereClause + " and oi.id=oid.oiid";
		return EntityManager.getdoubleSum(sql);
	}

	public void addOtherIncome(OtherIncome otherincome) throws Exception {
		EntityManager.save(otherincome);
	}

	public void updOtherIncome(OtherIncome otherincome) throws Exception {
		EntityManager.save(otherincome);
	}

	public void delOtherIncome(String id) throws Exception {
		String sql = "delete from Other_Income where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getOtherIncomeByBillno(String billno) throws Exception {
		String sql = "from OtherIncome where billno='" + billno + "'";
		return EntityManager.getAllByHql(sql);
	}

	public OtherIncome getOtherIncomeByID(String id) throws Exception {
		String sql = " from OtherIncome where id='" + id + "'";
		return (OtherIncome) EntityManager.find(sql);
	}

	public String getOtherIncomeNccodeByID(String id) throws Exception {
		String result = "";
		OtherIncome otherIncome = new OtherIncome();
		otherIncome = this.getOtherIncomeByID(id);
		result = otherIncome.getNccode();
		return result;
	}

	public void updIsAudit(String ppid, int userid, int audit) throws Exception {
		String sql = "update other_income set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate=to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	public void updIsNotAudit(String ppid) throws Exception {
		String sql = "update other_income set isaudit=0,auditid=null" +
				",auditdate=null where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List<OtherIncomeAll> getOtherIncome(String whereSql) {
		String hql = " from OtherIncomeAll  " + whereSql
				+ " order by makedate desc ";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 根据NCCODE删除记录
	 * 
	 * @param nccode用友给出的编号
	 */
	public void delOtherIncomeByNCcode(String nccode) throws Exception {

		String sql = "delete from other_income where NCcode='" + nccode + "'";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 检验该单据是否已经复核过
	 * 
	 * @param nccode用友给出的编号
	 * @return result1为已经复核过0为未复核
	 */
	public boolean checkIsAudit(String nccode) {
		boolean result = false;
		String sql = " from OtherIncome where NCcode='" + nccode + "'";
		OtherIncome temp = new OtherIncome();
		temp = (OtherIncome) EntityManager.find(sql);
		if (temp.getIsaudit() == 0) {
			result = true;
		}
		return result;

	}

	/**
	 * 入库OtherIncome保存的方式
	 * 
	 * @param otherIncome需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo saveOtherIncome(OtherIncome otherIncome) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppOtherIncome appOtherIncome = new AppOtherIncome();
		try {
			HibernateUtil.currentTransaction();
			appOtherIncome.addOtherIncome(otherIncome);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------保存成功------" + flag);
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------OtherIncome保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;

	}

	/**
	 * 入库OtherIncomeDetail保存的方式
	 * 
	 * @param otherIncomeDetail需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */

	public static ReplyInfo saveOtherIncomeDetail(
			OtherIncomeDetail otherIncomeDetail) {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppOtherIncomeDetail appOtherIncomeDetail = new AppOtherIncomeDetail();
		try {
			HibernateUtil.currentTransaction();
			appOtherIncomeDetail.addOtherIncomeDetail(otherIncomeDetail);
			HibernateUtil.commitTransaction();
			log.info("------OtherIncomeDetail保存-----成功");
		} catch (Exception e) {

			e.printStackTrace();
			info = e.toString();
			log.error("------OtherIncomeDetail保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;

	}

	/**
	 * 检货借入入库及其他入库
	 * 
	 * @param oiid产成品ID
	 * @param userId根据psname得到的制单人信息
	 * @return result检货结果
	 */
	public static String auditOtherIncome(String oiid, String userId) {
		String result = "success";
		AppFUnit appFUnit = new AppFUnit();
		AppOtherIncome appOtherIncome = new AppOtherIncome();
		AppOtherIncomeIdcode appOtherIncomeIdcode = new AppOtherIncomeIdcode();
		AppOtherIncomeDetail appOtherIncomeDetail = new AppOtherIncomeDetail();
		OtherIncome otherIncome = new OtherIncome();
		List<OtherIncomeDetail> otherIncomeDetails = new ArrayList<OtherIncomeDetail>();
		List<OtherIncomeIdcode> otherIncomeIdcodes = new ArrayList<OtherIncomeIdcode>();
		try {
			otherIncome = appOtherIncome.getOtherIncomeByID(oiid);

			if (otherIncome.getIsaudit() == 1) {
				result = ResourceBundle
						.getBundle(
								"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("have_auditted");
				return result;
			}
			otherIncomeDetails = appOtherIncomeDetail
					.getOtherIncomeDetailByOiid(oiid);
			for (OtherIncomeDetail tempOID : otherIncomeDetails) {
				double q1 = appFUnit.getQuantity(tempOID.getProductid(),
						tempOID.getUnitid(), tempOID.getQuantity());
				double q2 = appFUnit.getPkgQuantityInProductIncomeIdcode(
						tempOID.getProductid(), oiid);
				if (q1 != q2) {
					result = ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("out_of_quantity");
					return result;
				}
			}
			otherIncomeIdcodes = appOtherIncomeIdcode
					.getOtherIncomeIdcodeByoiid(oiid, 1);

			addIdcode(otherIncome, otherIncomeIdcodes);

			appOtherIncome.updIsAudit(oiid, Integer.valueOf(userId), 1);

			List<OtherIncomeIdcode> idlist = appOtherIncomeIdcode
					.getOtherIncomeIdcodeByoiid(oiid);
			addProductStockpile(idlist, otherIncome.getWarehouseid());

			DBUserLog.addUserLog(Integer.valueOf(userId), 7, "库存盘点>>复核盘盈单,编号："
					+ oiid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------复核时查找出错------" + e.toString());
		}

		return result;
	}

	private static void addProductStockpile(List<OtherIncomeIdcode> idlist,
			String warehouseid) throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (OtherIncomeIdcode idcode : idlist) {
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
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			aps.addProductByPurchaseIncome(ps);

			aps.inProductStockpile(ps.getProductid(), idcode.getUnitid(), ps
					.getBatch(), idcode.getQuantity(), ps.getWarehouseid(), ps
					.getWarehousebit(), idcode.getOiid(), "复核盘盈单-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps
					.getBatch());
		}
	}

	private static void addIdcode(OtherIncome pi,
			List<OtherIncomeIdcode> idcodelist) throws Exception {
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (OtherIncomeIdcode wi : idcodelist) {
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
			ic.setBillid(wi.getOiid());
			ic.setIdbilltype(7);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic
					.getWarehouseid(), ic.getWarehousebit(), 1, 0);
		}
	}

	public OtherIncome getOtherIncomeByNCcode(String nccode)
			throws Exception {
		String sql = " from OtherIncome where NCcode='" + nccode + "'";
		return (OtherIncome) EntityManager.find(sql);
	}

	/**
	 * 根据nccode使单据作废(入库)
	 * 
	 * @param nccode用友给出的id
	 * @param psnname批单人
	 */
	public ReplyInfo updateblankoutByNCcode(String nccode, String psnname)
			throws Exception {
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		OtherIncome temp = new OtherIncome();
		temp = this.getOtherIncomeByNCcode(nccode);
		// 如果查不到该条记录则返回报错xml
		if (temp == null) {
			flag = false;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("none_record");

		} else {
			// 判断该单据是否已记帐
			// 如果该条记录已经复核过则取消复核
			if (this.checkIsAudit(nccode)) {
				/*AppOtherIncomeIdcode aoidcode = new AppOtherIncomeIdcode();
				AppOtherIncomeDetail aoid=new AppOtherIncomeDetail();
				AppIdcode appidcode = new AppIdcode();
				AppProductStockpile aps = new AppProductStockpile();
				OtherIncome otherIncome = new OtherIncome();
				otherIncome = this.getOtherIncomeByNCcode(nccode);
				// String batch=purchaseIncome.get
				this.updIsAudit(nccode, Integer.valueOf(psnname), 0);
				this.delOtherIncomeByNCcode(nccode);
				List<OtherIncomeIdcode> idcodelist = aoidcode
						.getOtherIncomeIdcodeByoiid(temp.getId(), 1);
				for (OtherIncomeIdcode ic : idcodelist) {
					appidcode.delIdcodeByid(ic.getIdcode());
				}
				List<OtherIncomeIdcode> piilist = aoidcode
						.getOtherIncomeIdcodeByoiid(temp.getId());
				for (OtherIncomeIdcode pii : piilist) {
					// RichieYu -----20100505
					// 退还实际总数量
					aps.returninProductStockpileTotalQuantity(pii
							.getProductid(), pii.getUnitid(), pii.getBatch(),
							pii.getPackquantity() * pii.getQuantity(), temp
									.getWarehouseid(), pii.getWarehousebit(),
							temp.getId(), "取消其他入库-出货");
				}

				aoidcode.delOtherIncomeIDcodeByNCcode(nccode);
				aoid.delOtherIncomeDetailByNCcode(nccode);*/
				flag = false;
				info = ResourceBundle.getBundle(
						"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
						.getString("can_not_cancel_audit");
			} else {
				this.delOtherIncomeByNCcode(nccode);
				AppOtherIncomeIdcode aoidcode = new AppOtherIncomeIdcode();
				AppOtherIncomeDetail aoid=new AppOtherIncomeDetail();
				aoidcode.delOtherIncomeIDcodeByNCcode(nccode);
				aoid.delOtherIncomeDetailByNCcode(nccode);
			}
			flag = true;
			info = ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("success");

		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

}
