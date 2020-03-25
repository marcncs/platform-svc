package com.winsafe.drp.server;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry
 * @version 2009-8-27 下午03:59:09 www.winsafe.cn
 */
public class TakeTicketDetailService
{
	private AppProductStockpileAll appps = new AppProductStockpileAll();

	private AppFUnit appfun = new AppFUnit();

	private TakeTicket tt;

	private String wid;

	private String productid;

	private String productname;

	private String specmode;

	private int unitid;

	private double unitprice;

	public TakeTicketDetailService(TakeTicket tt, String wid, String productid, String productname, String specmode, int unitid, double unitprice)
	{
		this.tt = tt;
		this.wid = wid;
		this.productid = productid;
		this.productname = productname;
		this.specmode = specmode;
		this.unitid = unitid;
		this.unitprice = unitprice;
	}

	public void addBatchDetail(double curQuantity) throws Exception
	{
		ProductStockpileAll bq = appps.getBatchQuantityByPidWid(productid, wid);
		int psquantity = 0;
		if (bq != null)
		{
			psquantity = appfun.getStockpileQuantity(productid, unitid, bq.getStockpile());
			//System.out.println("===========psquantity======>"+psquantity);
			//System.out.println("===========curQuantity======>"+curQuantity);
			//按批次大小，数量自动分配。
			//
			if (psquantity < curQuantity)
			{
				add(bq.getBatch(), psquantity);
				prepareOut(bq.getBatch(), psquantity + 0.00);
				addBatchDetail(curQuantity - psquantity);
			}
			else
			{
				add(bq.getBatch(), curQuantity);
				prepareOut(bq.getBatch(), curQuantity);
			}
		}
	}

	public void addDetail(double curQuantity) throws Exception
	{
		ProductStockpileAll bq = appps.getBatchQuantityByPidWid(productid, wid);
		int psquantity = 0;
		if (bq != null)
		{
			psquantity = appfun.getStockpileQuantity(productid, unitid, bq.getStockpile());
			//System.out.println("===========psquantity======>"+psquantity);
			//System.out.println("===========curQuantity======>"+curQuantity);
			//按批次大小，数量自动分配。
			//
			if (psquantity < curQuantity)
			{
				add(bq.getBatch(), psquantity);
				prepareOut(bq.getBatch(), psquantity + 0.00);
				addBatchDetail(curQuantity - psquantity);
			}
			else
			{
				add(bq.getBatch(), curQuantity);
				prepareOut(bq.getBatch(), curQuantity);
			}
		}
	}

	private void prepareOut(String batch, Double quantity) throws Exception
	{
		appps.prepareOut(productid, unitid, wid, batch, quantity);
	}

	private void add(String batch, double quantity) throws Exception
	{
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setProductid(productid);
		ttd.setProductname(productname);
		ttd.setSpecmode(specmode);
		ttd.setUnitid(unitid);
		ttd.setBatch(batch);
		ttd.setUnitprice(unitprice);
		ttd.setQuantity(quantity);
		ttd.setTtid(tt.getId());
		tt.getTtdetails().add(ttd);

	}
}
