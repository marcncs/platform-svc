package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry
 * @version 2009-8-17 下午04:36:33 www.winsafe.cn
 */
public class DealUploadIdcodeStockAlterMove extends DealUploadIdcode
{
	private AppStockAlterMove apppi = new AppStockAlterMove();

	private AppStockAlterMoveDetail apppd = new AppStockAlterMoveDetail();

	private AppStockAlterMoveIdcode app = new AppStockAlterMoveIdcode();

	//单据编号
	private String billno;

	//条形码
	private String idcode;
	
	//产品编号
	private String productid;

	private StockAlterMove p;
	
	private List<StockAlterMoveDetail> ttdlist;
	
	public DealUploadIdcodeStockAlterMove()
	{
		
	}

	public DealUploadIdcodeStockAlterMove(String filepath, String fileaddress, int iuid)
	{
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode)
	{
		String t_ret = null;
		try
		{

			billno = crs.getBillNo(uploadidcode);

			idcode = crs.getIdcode(uploadidcode);
			
			productid = appicode.getICodeByLcode(crs.getLcode(idcode)).getProductid();
			
			p = apppi.getStockAlterMoveByID(billno);
			
			ttdlist = apppd.getStockAlterMoveDetailByPiidPid(billno, productid);
			
			//扫描类型
			
			
			//验证单据编号
			t_ret = validateBillno(uploadidcode, billno);
			if (null != t_ret)
			{
				return t_ret;
			}
			//验证条形码是否存在在当前单据中
			t_ret = isExits();
			if (null != t_ret)
			{
				return t_ret;
			}
			//验证条形码是否存在
			
			//生成订购入库条形码
			genStockAlterMoveIdcode(uploadidcode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			t_ret = "条码不符合规则";
		}
		return t_ret;
	}

	/**
	 * 生成订购入库条形码
	 * Create Time: Oct 12, 2011 1:27:33 PM 
	 * @param uploadidcode
	 * @throws Exception
	 * @throws IdcodeException
	 * @author dufazuo
	 */
	private void genStockAlterMoveIdcode(String uploadidcode) throws Exception, IdcodeException
	{
		StockAlterMoveIdcode pi = new StockAlterMoveIdcode();
//		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_idcode", 0, "")));
		pi.setSamid(billno);
		pi.setProductid(productid);
		pi.setIsidcode(1);
		pi.setWarehousebit(crs.getWarehouseBit(uploadidcode));
		pi.setBatch(crs.getBatch(idcode));
		pi.setProducedate(DateUtil.formatDate(DateUtil.formatStrDate(crs.getProductDate(uploadidcode))));
		pi.setValidate("");
		pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
		pi.setQuantity(Double.valueOf(crs.getProductCount(uploadidcode)));
		pi.setPackquantity(1d);
		pi.setIdcode(idcode);
		pi.setLcode(crs.getLcode(idcode));
		pi.setStartno(crs.getStartNo(idcode));
		pi.setEndno(crs.getEndNo(idcode));
		pi.setMakedate(DateUtil.getCurrentDate());
		app.addStockAlterMoveIdcode(pi);
	}

	/**
	 * 
	 * Create Time: Oct 12, 2011 1:23:27 PM 
	 * @param uploadidcode
	 * @throws Exception
	 * @author dufazuo
	 */
	private String isExits() throws Exception
	{
		if (app.getStockAlterMoveIdcodeByidcode(productid, billno, idcode) != null)
		{
			writeTxt(idcode + "[条码已经存在当前列表中]");
			return "条码已经存在当前列表中";
		}
		return null;
	}

	/**
	 * 验证单据 Create Time: Oct 12, 2011 1:09:20 PM
	 * @param uploadidcode
	 * @param t_ret
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author dufazuo
	 */
	private String validateBillno(String uploadidcode, String billno) throws Exception
	{
		if (billno.equals("") || billno.indexOf("OM") == -1)
		{
			writeTxt(uploadidcode + "[单据编号错误]");
			return "单据编号错误";
		}

		if (p == null)
		{
			writeTxt(uploadidcode + "[单据编号不存在]");
			return "单据编号不存在";
		}
		else
		{
			if (p.getIscomplete() == 1)
			{
				writeTxt(uploadidcode + "[单据已签收]");
				return "单据已签收";
			}
		}
		if (ttdlist == null || ttdlist.isEmpty())
		{
			writeTxt(uploadidcode + "[单据产品错误]");
			return "单据产品错误";
		}
		return null;
	}

}
