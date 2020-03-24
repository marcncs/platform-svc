package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.fileListener.UFIDA.ReplyInfo;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductIncome
{
	private static final Logger log = Logger.getLogger(AppProductIncome.class);

	public List getProductIncome(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception
	{
		String sql = "select pi from ProductIncome as pi,WarehouseVisit as wv " + pWhereClause + " order by pi.id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	

	public void updProductIncomeByID(ProductIncome pi) throws Exception
	{
		EntityManager.update(pi);

	}

	//
	public void addProductIncome(Object productincome) throws Exception
	{

		EntityManager.save3(productincome);

	}

	public ProductIncome getProductIncomeByID(String id) throws Exception
	{
		ProductIncome pi = null;
		String sql = " from ProductIncome where id='" + id + "'";
		pi = (ProductIncome) EntityManager.find(sql);
		return pi;
	}

	public String getProductIncomeNccodeByID(String id) throws Exception
	{
		String result = "";
		ProductIncome productIncome = new ProductIncome();
		productIncome = this.getProductIncomeByID(id);
		result = productIncome.getNccode();
		return result;
	}

	public ProductIncome getProductIncomeByNCcode(String nccode) throws Exception
	{
		String sql = " from ProductIncome where nccode='" + nccode + "'";
		return (ProductIncome) EntityManager.find(sql);
	}

	public void delProductIncome(String id) throws Exception
	{

		String sql = "delete from product_income where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsRefer(String id) throws Exception
	{

		String sql = "update product_income set isrefer=1 where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updIsApprove(String id, int isapprove) throws Exception
	{
		String sql = "update product_income set approvestatus=" + isapprove + " where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsComplete(String id, Long userid) throws Exception
	{

		String sql = "update product_income set iscomplete=1,completeid=" + userid + ",completedate='" + DateUtil.getCurrentDateTime() + "' where id ='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public List waitApproveProductIncome(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception
	{
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pi.id,pi.warehouseid,pi.producebatch,pi.incomedept,pi.incomedate,pia.actid,pia.approve from ProductIncome as pi,ProductIncomeApprove as pia "
				+ pWhereClause + " order by pia.approve, pi.incomedate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}

	public int findCountBySLID(Long slid) throws Exception
	{
		int f = 0;
		String sql = "select count(*) from ProductIncome where iscomplete = 0 and slid=" + slid;
		if (slid > 0)
		{
			f = EntityManager.getRecordCount(sql);
		}
		return f;
	}

	public void updIsAudit(String ppid, int userid, Integer audit) throws Exception
	{
		String sql = "update product_income set isaudit=" + audit + ",auditid=" + userid + ",auditdate=to_date('" + DateUtil.getCurrentDateTime() + "','yyyy-mm-dd hh24:mi:ss') where id='"
				+ ppid + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	public void cancelAudit(String ppid, int userid, Integer audit) throws Exception
	{
		String sql = "update product_income set isaudit=" + audit + ",auditid=" + userid + ",auditdate=null  where id='"
				+ ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List<ProductIncome> getProductIncome(String whereSql)
	{
		String sql = "select p from ProductIncome as p,RuleUserWh as r " + whereSql + " order by p.id desc ";
		return EntityManager.getAllByHql(sql);
	
	}
	/**
	 * 查询获取当日为复核产成品入库单据
	 * @param whereSql
	 * @return
	 */
	public List<ProductIncome> getNoAuditProductIncome(String whereSql)
	{
		String sql = "select pi from ProductIncome as pi " + whereSql + " order by pi.id desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 根据仓库编号查询未复核的产成品入库单
	 * @param whereSql
	 * @return
	 */
	public List<ProductIncome> getProductIncomeByWid(String whereSql)
	{
		String sql = "select pi from ProductIncome as pi " + whereSql + " order by pi.id desc ";
		return EntityManager.getAllByHql(sql);
	}

	public void delProductIncomeByNCcode(String nccode) throws Exception
	{

		String sql = "delete from product_income where nccode='" + nccode + "'";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 检验该单据是否已经复核过
	 * @param nccode用友给出的编号
	 * @return result1为已经复核过0为未复核
	 */
	public boolean checkIsAudit(String nccode)
	{
		boolean result = false;
		String sql = " from ProductIncome where NCcode='" + nccode + "'";
		ProductIncome temp = new ProductIncome();
		temp = (ProductIncome) EntityManager.find(sql);
		if (temp.getIsaudit() == 0)
		{
			result = true;
		}
		return result;

	}

	// 产成品入库

	/**
	 * 入库ProductIncome保存的方式
	 * @param productIncome需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo saveProductIncome(ProductIncome productIncome)
	{
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppProductIncome appProductIncome = new AppProductIncome();
		try
		{
			HibernateUtil.currentTransaction();
			appProductIncome.addProductIncome(productIncome);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------保存成功------" + flag);
		}
		catch(Exception e)
		{

			e.printStackTrace();
			info = e.toString();
			log.error("------ProductIncome保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	/**
	 * 入库ProductIncomeDetail保存的方式
	 * @param productIncomeDetail需要保存的实体对象
	 * @return replyInfo是否该次保存顺利执行及相关信息
	 */
	public static ReplyInfo saveProductIncomeDetail(ProductIncomeDetail productIncomeDetail)
	{
		ReplyInfo replyInfo = new ReplyInfo();
		boolean flag = false;
		String info = "";
		AppProductIncomeDetail appProductIncomeDetail = new AppProductIncomeDetail();
		try
		{
			HibernateUtil.currentTransaction();
			appProductIncomeDetail.addProductIncomeDetail(productIncomeDetail);
			HibernateUtil.commitTransaction();
			flag = true;
			log.info("------ProductIncomeDetail保存-----成功");
		}
		catch(Exception e)
		{

			e.printStackTrace();
			info = e.toString();
			log.error("------ProductIncomeDetail保存------" + e.toString());
		}
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

	/**
	 * 产成品检货入库
	 * @param productIncomeId产成品ID
	 * @param userId根据psname得到的制单人信息
	 * @return result检货结果
	 */
	public static String auditProductIncome(String productIncomeId, String userId)
	{
		HibernateUtil.currentTransaction();
		String result = "success";
		AppFUnit appFUnit = new AppFUnit();
		AppProductIncome appProductIncome = new AppProductIncome();
		AppProductIncomeIdcode appProductIncomeIdcode = new AppProductIncomeIdcode();
		AppProductIncomeDetail appProductIncomeDetail = new AppProductIncomeDetail();
		ProductIncome productIncome = new ProductIncome();
		List<ProductIncomeDetail> productIncomeDetails = new ArrayList<ProductIncomeDetail>();
		List<ProductIncomeIdcode> productIncomeIdcodes = new ArrayList<ProductIncomeIdcode>();
		try
		{
			productIncome = appProductIncome.getProductIncomeByID(productIncomeId);

			if (productIncome.getIsaudit() == 1)
			{
				result = ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("have_auditted");
				return result;
			}
			productIncomeDetails = appProductIncomeDetail.getProductIncomeDetailByPbId(productIncomeId);
			for (ProductIncomeDetail tempPID : productIncomeDetails)
			{
				double q1 = appFUnit.getQuantity(tempPID.getProductid(), tempPID.getUnitid(), tempPID.getQuantity());
				double q2 = appFUnit.getPkgQuantityInProductIncomeIdcode(tempPID.getProductid(), productIncomeId);
				if (q1 != q2)
				{
					result = ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("out_of_quantity");
					return result;
				}
			}
			productIncomeIdcodes = appProductIncomeIdcode.getProductIncomeIdcodeByPiid(productIncomeId, 1);

			addIdcode(productIncome, productIncomeIdcodes);

			appProductIncome.updIsAudit(productIncomeId, Integer.valueOf(userId), 1);

			List<ProductIncomeIdcode> idlist = appProductIncomeIdcode.getProductIncomeIdcodeByPiid(productIncomeId);
			addProductStockpile(idlist, productIncome.getWarehouseid());
			DBUserLog.addUserLog(Integer.valueOf(userId), 7, "入库>>产成品入库>>复核产成品入库,编号：" + productIncomeId);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("------复核时查找产成品出错------" + e.toString());
		}
		HibernateUtil.commitTransaction();
		return result;
	}

	public static void addIdcode(ProductIncome pi, List<ProductIncomeIdcode> idcodelist) throws Exception
	{
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		AppFUnit af = new AppFUnit();
		Idcode ic = null;
		for (ProductIncomeIdcode wi : idcodelist)
		{
			ic = new Idcode();
			ic.setIdcode(wi.getIdcode());
			ic.setProductid(wi.getProductid());
			ic.setProductname(ap.getProductNameByID(ic.getProductid()));
			ic.setBatch(wi.getBatch());
			ic.setProducedate(wi.getProducedate());
			ic.setVad(wi.getVad());
			ic.setLcode(wi.getLcode());
			ic.setStartno(wi.getStartno());
			ic.setEndno(wi.getEndno());
			ic.setUnitid(wi.getUnitid());
			ic.setQuantity(af.getQuantity(ic.getProductid(), ic.getUnitid(), 1));
			ic.setFquantity(ic.getQuantity());
			ic.setPackquantity(wi.getPackquantity());
			ic.setIsuse(1);
			ic.setIsout(0);
			ic.setBillid(wi.getPiid());
			ic.setIdbilltype(2);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setMakedate(wi.getMakedate());
			appidcode.addIdcode(ic);
			appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic.getWarehouseid(), ic.getWarehousebit(), 1, 0);
		}
	}

	public static void addProductStockpile(List<ProductIncomeIdcode> idlist, String warehouseid) throws Exception
	{
		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (ProductIncomeIdcode idcode : idlist)
		{
			ps = new ProductStockpile();

			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			ps.setProductid(idcode.getProductid());

			ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
			ps.setBatch(idcode.getBatch());
			ps.setProducedate(idcode.getProducedate());
			ps.setVad(idcode.getVad());
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(idcode.getWarehousebit());
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			aps.addProductByPurchaseIncome(ps);
			// RichieYu 20100505----更新库存数量
			aps.inProductStockpileTotalQuantity(ps.getProductid(), idcode.getUnitid(), ps.getBatch(), idcode.getPackquantity() * idcode.getQuantity(), ps
					.getWarehouseid(), ps.getWarehousebit(), idcode.getPiid(), "产成品-入库");
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}

	/**
	 * 根据nccode使单据作废
	 * @param nccode用友给出的id
	 * @param psnname批单人
	 */
	public ReplyInfo updateblankoutByNCcode(String nccode, String psnname) throws Exception
	{
		ReplyInfo replyInfo = new ReplyInfo();
		HibernateUtil.currentTransaction();
		boolean flag = false;
		String info = "";
		ProductIncome temp = new ProductIncome();
		temp = this.getProductIncomeByNCcode(nccode);
		// 如果查不到该条记录则返回报错xml
		if (temp == null)
		{
			flag = false;
			info = ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("none_record");

		}
		else
		{
			// 判断该单据是否已记帐
			// 如果该条记录已经复核过则取消复核
			//if (this.checkIsAudit(nccode)) {
			if (false)
			{
				/*
				 * AppProductIncomeIdcode apidcode = new AppProductIncomeIdcode(); AppProductIncomeDetail apid = new AppProductIncomeDetail(); AppIdcode
				 * appidcode = new AppIdcode(); AppProductStockpile aps = new AppProductStockpile(); ProductIncome productIncome = new ProductIncome();
				 * productIncome = this.getProductIncomeByNCcode(nccode); // String batch=purchaseIncome.get // 取消复核 this.updIsAudit(nccode,
				 * Integer.valueOf(psnname), 0); // 删除主表记录 this.delProductIncomeByNCcode(nccode); List<ProductIncomeIdcode> idcodelist = apidcode
				 * .getProductIncomeIdcodeByPiid(temp.getId(), 1); for (ProductIncomeIdcode ic : idcodelist) { appidcode.delIdcodeByid(ic.getIdcode()); }
				 * List<ProductIncomeIdcode> piilist = apidcode .getProductIncomeIdcodeByPiid(temp.getId()); for (ProductIncomeIdcode pii : piilist) { //
				 * RichieYu -----20100505 // 退还实际总数量 aps.returninProductStockpileTotalQuantity(pii .getProductid(), pii.getUnitid(), pii.getBatch(),
				 * pii.getPackquantity() * pii.getQuantity(), temp .getWarehouseid(), pii.getWarehousebit(), temp.getId(), "取消产成品入库-出货"); } // 删除idcode表记录
				 * apidcode.delProductIncomeIDcodeByNCcode(nccode); // 删除detail表记录 apid.delProductIncomeDetailByNCcode(nccode);
				 */
				flag = false;
				info = ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("can_not_cancel_audit");
			}
			else
			{
				this.delProductIncomeByNCcode(nccode);
				AppProductIncomeIdcode apidcode = new AppProductIncomeIdcode();
				AppProductIncomeDetail apid = new AppProductIncomeDetail();
				// 删除idcode表记录
				apidcode.delProductIncomeIDcodeByNCcode(nccode);
				// 删除detail表记录
				apid.delProductIncomeDetailByNCcode(nccode);
			}
			flag = true;
			info = ResourceBundle.getBundle("com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat").getString("success");

		}
		HibernateUtil.commitTransaction();
		replyInfo.setSaveFlag(flag);
		replyInfo.setErrorInfo(info);
		return replyInfo;
	}

}
