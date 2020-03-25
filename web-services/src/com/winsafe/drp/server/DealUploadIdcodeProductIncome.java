package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry
 * @version 2009-8-17 下午04:36:33 www.winsafe.cn
 */
public class DealUploadIdcodeProductIncome extends DealUploadIdcode
{
	private AppProductIncome apppi = new AppProductIncome();

	private AppProductIncomeDetail apppd = new AppProductIncomeDetail();

	private AppProductIncomeIdcode app = new AppProductIncomeIdcode();
	
	AppFUnit af = new AppFUnit();
	//新增无参构造方法是为了实时上传可以调用它
	public DealUploadIdcodeProductIncome(){}

	public DealUploadIdcodeProductIncome(String filepath, String fileaddress, int iuid)
	{
		super(filepath, fileaddress, iuid);
	}

	public String addIdcode(String uploadidcode)
	{
		String t_ret = null;
		try
		{
			//判断并去掉采集器上传的条码的bom
			uploadidcode = dealuploadIdcode(uploadidcode);
			//解析获取条形码
			String idcode = crs.getIdcode(uploadidcode);
			//扫描类型
			
			//判断上传上来的条形码是否已经存在
			
			String warehouseId = crs.getWarehouseId(uploadidcode);
			ProductIncome pi = getCurrentUsableProductIncome(warehouseId);
			//如果单据不存在，则新建单据并关联上传的条码
			if(null == pi)
			{
				HibernateUtil.currentSession(true);
				//生成新单据
				pi = genProductIncomeByUploadIdcode(uploadidcode);
				//生成单据详情
				genProductIncomeDetailByUploadIdcode(pi, uploadidcode);
				//让单据和条码关联起来
				genProductIncomeIdcodeByUploadIdcode(pi, uploadidcode);
				HibernateUtil.commitTransaction();
			}
			else
			{
				//判断上传的条形码是否已经存在于当前单据当中
				t_ret = isExist(uploadidcode, pi.getId(), idcode);
				if(null != t_ret)
					return t_ret;
				//在当前单据上追加条码信息
//				String productid = appicode.getICodeByLcode(crs.getProductId(uploadidcode)).getProductid();
				String productid = crs.getProductId(uploadidcode);
				//根据产品编号获取产品信息
				AppProduct ap = new AppProduct();
				Product p = ap.getProductEntityByICode(productid);
				if(null != p)
				{
					productid = p.getId();
				}
				HibernateUtil.currentSession(true);
				//判断产品是否已经存在,如果产品不存在，则新增单据产品 //131022 ADD 并根据批次判断 LW
				if(!isContains(pi, productid, uploadidcode))
				{
					genProductIncomeDetailByUploadIdcode(pi, uploadidcode);
				}
				else
				{
					//如果单据中的产品信息已经存在，则加相应产品的数量等信息
					addProductQuantityWithIdcode(pi.getId(), productid, Double.valueOf(crs.getProductCount(uploadidcode)), crs.getBatch(uploadidcode));
				}
				genProductIncomeIdcodeByUploadIdcode(pi, uploadidcode);
				HibernateUtil.commitTransaction();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
			t_ret = "条码不符合规则";
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return t_ret;
	}
	
	/**
	 * 判断上传的条形码是否已经存在于当前单据中
	 * Create Time: Oct 10, 2011 3:29:29 PM 
	 * @param billno 单据编号
	 * @param idcode 条形码
	 * @return
	 * @throws Exception
	 * @author dufazuo
	 */
	private String isExist(String uploadidcode,String billno, String idcode) throws Exception
	{
//		String productid = appicode.getICodeByLcode(crs.getProductId(uploadidcode)).getProductid();
//		if (app.getProductIncomeIdcodeByidcode(productid, billno, idcode) != null)
//		{
//			writeTxt(idcode + "[条码已经存在当前列表中]");
//			return "条码已经存在当前列表中";
//		}
		if (app.getProductIncomeIdcodeByIdcode(idcode) != null)
		{
			writeTxt("E00037:"+idcode + "[条码已经存在当前列表中]");
			return "条码已经存在当前列表中";
		}
		
		return null;
	}

	/**
	 * 获取当前可用的产成品入库单据
	 * Create Time: Oct 10, 2011 2:49:52 PM 
	 * @return
	 * @author dufazuo
	 */
	public ProductIncome getCurrentUsableProductIncome(String warehouseId)
	{
		//获取系统当前日期时间（YYYYMMDD格式）
		String currentDateStr = Dateutil.getCurrentDateTimeString().substring(0, 8);
		
		AppProductIncome api = new AppProductIncome();
		List<ProductIncome> pils = api.getProductIncomeByWid("where pi.id like 'PE" + currentDateStr + "%' and warehouseid = '" + warehouseId + "' and pi.isaudit = 0 and  pi.isAutoCreate=1");
		//单据不存在
		if(pils.size() > 0)
		{
			return pils.get(0);
		}
		return null;
	}
	
	/**
	 * 根据采集器上传的条码生成新的入库单据
	 * Create Time: Oct 9, 2011 11:49:11 AM 
	 * @param uploadidcode 采集器上传的条码
	 * @return ProductIncome 新生成的单据
	 * @throws Exception
	 * @author dufazuo
	 */
	private ProductIncome genProductIncomeByUploadIdcode(String uploadidcode) throws Exception
	{
		ProductIncome pi = new ProductIncome();
		
		String piid = MakeCode.getExcIDByRandomTableName("product_income", 2, "PE");
		pi.setId(piid);
		pi.setWarehouseid(crs.getWarehouseId(uploadidcode));
		pi.setIncomedate(Dateutil.getCurrentDate());
		pi.setIncomesort(0);
		pi.setIsaudit(0);
		pi.setMakeorganid("00000002");
		pi.setMakedeptid(0);
		pi.setMakeid(1);
		pi.setIsAutoCreate(1);
		pi.setMakedate(DateUtil.getCurrentDate());
		pi.setKeyscontent(pi.getId());
		
		AppProductIncome api = new AppProductIncome();
		api.addProductIncome(pi);
		
		return pi;
	}
	
	/**
	 * 根据单据和采集器上传的条码生成新的入库产品
	 * Create Time: Oct 9, 2011 11:50:42 AM 
	 * @param pi 单据
	 * @param uploadidcode 采集器上传的条码
	 * @return 新生成的入库产品
	 * @throws Exception
	 * @author dufazuo
	 */
	private ProductIncomeDetail genProductIncomeDetailByUploadIdcode(ProductIncome pi, String uploadidcode) throws Exception
	{
		//生成单据明细
		AppProductIncomeDetail apid = new AppProductIncomeDetail();
		ProductIncomeDetail pid = new ProductIncomeDetail();
		pid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_income_detail", 0, "")));
		pid.setPiid(pi.getId());
		pid.setProductid(crs.getProductId(uploadidcode));
		pid.setUnitid(2);
		pid.setQuantity(Double.valueOf(crs.getProductCount(uploadidcode)));
		//根据产品编号获取产品信息
		AppProduct ap = new AppProduct();
		Product p = ap.getProductEntityByICode(pid.getProductid());
		if(null != p)
		{
			pid.setProductid(p.getId());
			pid.setProductname(p.getProductname());
			pid.setSpecmode(p.getSpecmode());
			if(null != p.getCost())
			{
				pid.setCostprice(p.getCost());
				pid.setCostsum(Double.valueOf(crs.getProductCount(uploadidcode)) * p.getCost());
			}
			else
			{
				pid.setCostprice(0d);
				pid.setCostsum(0d);
			}
		}
		pid.setFactquantity(Double.valueOf(crs.getProductCount(uploadidcode)));
		pid.setConfirmQuantity(Double.valueOf(crs.getProductCount(uploadidcode)));
		pid.setMakedate(DateUtil.getCurrentDate());
		pid.setBatch(crs.getBatch(uploadidcode));

		apid.addProductIncomeDetail(pid);
		
		return pid;
	}
	
	/**
	 * 根据单据和采集器上传的条码生成新的条码
	 * Create Time: Oct 9, 2011 12:00:35 PM 
	 * @param pi 单据
	 * @param uploadidcode 采集器上传的条码
	 * @return 跟入库产品关联的条码
	 * @throws Exception
	 * @author dufazuo
	 */
	private ProductIncomeIdcode genProductIncomeIdcodeByUploadIdcode(ProductIncome pi, String uploadidcode) throws Exception
	{
		//让单据跟条码对应起来
		ProductIncomeIdcode pii = new ProductIncomeIdcode();
		pii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_income_idcode", 0, "")));
		pii.setPiid(pi.getId());
		pii.setIdcode(crs.getIdcode(uploadidcode));
		pii.setStartno(crs.getStartNo(crs.getIdcode(uploadidcode)));
		pii.setEndno(crs.getEndNo(crs.getIdcode(uploadidcode)));
//		pii.setProductid(appicode.getICodeByLcode(crs.getProductId(uploadidcode)).getProductid());
		pii.setProductid(crs.getProductId(uploadidcode));
		//根据产品编号获取产品信息
		AppProduct ap = new AppProduct();
		Product p = ap.getProductEntityByICode(pii.getProductid());
		if(null != p)
		{
			pii.setProductid(p.getId());
		}
		pii.setProducedate(DateUtil.formatDate(DateUtil.formatStrDate(crs.getProductDate(uploadidcode))));
		pii.setIsidcode(1);
		//根据仓库编号获取仓库的仓位
		AppWarehouse aw = new AppWarehouse();
		WarehouseBit wb = new WarehouseBit();
		if(aw.getWarehouseBitByWid(crs.getWarehouseId(uploadidcode)).size() > 0)
		{
			wb = (WarehouseBit)aw.getWarehouseBitByWid(crs.getWarehouseId(uploadidcode)).get(0);
		}
		pii.setWarehousebit(wb.getWbid());
		pii.setBatch(crs.getBatch(uploadidcode));
		pii.setUnitid(2);
//		pii.setQuantity(Double.valueOf(crs.getProductCount(uploadidcode)));
		pii.setQuantity(1d);
		//包装数量
		pii.setPackquantity(af.getXQuantity(pii.getProductid(), pii.getUnitid()));
		pii.setLcode(crs.getProductId(uploadidcode));
		pii.setMakedate(DateUtil.getCurrentDate());
		
		AppProductIncomeIdcode apii = new AppProductIncomeIdcode();
		apii.addProductIncomeIdcode(pii);

		return pii;
	}
	
	/**
	 * 给单据产品加数量
	 * Create Time: Oct 10, 2011 5:36:38 PM 
	 * @param piid 单据编号
	 * @param productid 产品编号
	 * @param quantity 产品数量
	 * @throws Exception
	 * @author dufazuo
	 */
	private void addProductQuantityWithIdcode(String piid, String productid, double quantity, String batch) throws Exception
	{
		ProductIncomeDetail pid = apppd.getPIDetailByPiidPid(piid, productid, batch);
		if(null != pid)
		{
			pid.setQuantity(pid.getQuantity() + quantity);
			pid.setFactquantity(pid.getFactquantity() + quantity);
			pid.setConfirmQuantity(pid.getConfirmQuantity() + quantity);
			if(null != pid.getCostprice())
			{
				pid.setCostsum(pid.getCostprice() + quantity * pid.getCostprice());
			}
			else
			{
				pid.setCostsum(0d);
			}
			apppd.updProductIncomeDetail(pid);
		}
	}
	
	/**
	 * 判断条码对应的产品是否已经在单据中存在
	 * Create Time: Oct 9, 2011 12:02:19 PM 
	 * @param pi 单据
	 * @param productId 上传的条码中对应的产品
	 * @return 
	 * @throws Exception
	 * @author dufazuo
	 */
	private boolean isContains(ProductIncome pi, String productId, String uploadidcode) throws Exception
	{
		AppProductIncomeDetail apid = new AppProductIncomeDetail();
		List<ProductIncomeDetail> pids = apid.getProductIncomeDetailByPbId(pi.getId());
		if(pids.size() > 0)
		{
			for(ProductIncomeDetail pid : pids)
			{
				if((null != pid.getProductid() && pid.getProductid().equals(productId)) 
						&& (null != pid.getBatch() && pid.getBatch().equals(crs.getBatch(uploadidcode))))
				{
					return true;
				}
			}
		}
		return false;
	}

}
