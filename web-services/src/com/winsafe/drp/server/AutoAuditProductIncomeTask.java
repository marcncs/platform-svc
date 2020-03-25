package com.winsafe.drp.server;

import java.util.List;
import java.util.TimerTask;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.SystemResource;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * Project:bright->Class:AutoAuditProductIncomeTask.java
 * <p style="font-size:16px;">
 * Description：自动复核产成品入库单据的任务
 * </p>
 * Create Time Oct 10, 2011 8:40:45 AM
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class AutoAuditProductIncomeTask extends TimerTask
{
	private AppFUnit af = new AppFUnit();

	private AppProductIncome api = null;

	//单据条形码服务类
	private AppProductIncomeIdcode apidcode = null;

	@Override
	public void run()
	{
		try
		{
			HibernateUtil.currentSession(true);
			DBUserLog.addUserLog(1, "开始自动复核产成品入库单据...");
			api = new AppProductIncome();
			String preDayStr = Dateutil.getPreDayStr("yyyyMMdd");
			List<ProductIncome> pils = api.getNoAuditProductIncome("where pi.id like 'PE" + preDayStr + "%' and pi.isaudit = 0 and  pi.isAutoCreate=1");
//			List<ProductIncome> pils = api.getNoAuditProductIncome("where pi.id like 'PE20120716%' and pi.isaudit = 0 and  pi.isAutoCreate=1");
			if (pils.size() > 0)
			{
				apidcode = new AppProductIncomeIdcode();
				for (ProductIncome pi : pils)
				{
					DBUserLog.addUserLog(1, "找到要复核的单据:" + pi.getId() + ",并开始复核...");
					//获取所有指定单据编号的条形码
					List<ProductIncomeIdcode> idcodelist = apidcode.getProductIncomeIdcodeByPiid(pi.getId(), 1);
					
					//记录条码
					addIdcode(pi, idcodelist);

					//设置单据已复核
					api.updIsAudit(pi.getId(), 1, 1);

					List<ProductIncomeIdcode> idlist = apidcode.getProductIncomeIdcodeByPiid(pi.getId());
					//修改库存量
					addProductStockpile(idlist, pi.getWarehouseid());
					DBUserLog.addUserLog(1, "单据:" + pi.getId() + "复核完成");
				}
			}
			DBUserLog.addUserLog(1, "自动复核产成品入库单据结束");
			HibernateUtil.commitTransaction();
		}
		catch(Exception e)
		{
			HibernateUtil.rollbackTransaction();
			try {
				DBUserLog.addUserLog(1, "自动复核产成品入库单据失败，失败原因：" + e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 增加产品库存量 Create Time: Oct 9, 2011 2:50:46 PM
	 * @param idlist 跟单据关联的条码集合
	 * @param warehouseid 仓库Id
	 * @throws Exception
	 * @author dufazuo
	 */
	private void addProductStockpile(List<ProductIncomeIdcode> idlist, String warehouseid) throws Exception
	{
		AppSystemResource appSR= new AppSystemResource();
		//判断入库单复核时是否操作库存
		if(!appSR.isTrue("systemParam", "productIncome")){
			return;
		}
		
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
			//设置产品检验状态为合格
			ps.setVerifyStatus(1);
			//新增库存记录（如果要新增的库存记录已经存在则不作新增操作，否则新增，注意，新增时先将库存量设置为零，因为下面的更新操作会加库存量）
			aps.addProductByPurchaseIncome2(ps);
			//更新库存量
			aps.inProductStockpileTotalQuantity(ps.getProductid(), idcode.getUnitid(), ps.getBatch(), idcode.getQuantity(), ps.getWarehouseid(), idcode
					.getWarehousebit(), idcode.getPiid(), "产成品-入库");
			//更新库存中产品的成本
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}

	/**
	 * 记录单据的条码 Create Time: Oct 9, 2011 2:55:06 PM
	 * @param pi 单据
	 * @param idcodelist 单据的条码
	 * @throws Exception
	 * @author dufazuo
	 */
	private void addIdcode(ProductIncome pi, List<ProductIncomeIdcode> idcodelist) throws Exception
	{
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
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
			ic.setQuantity(wi.getQuantity());
			ic.setFquantity(wi.getPackquantity());
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
			//记录条码（如果要记录的条形码已经存在则不作更新处理，否则新增一条记录）
			appidcode.addIdcode(ic);
			//更新（如果上面没有做新增操作，则更新数据库中相应的记录）
			appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic.getWarehouseid(), ic.getWarehousebit(), 1, 0);
		}
	}

}
