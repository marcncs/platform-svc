package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppScannerUser;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.BatchQuantity;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ScannerUser;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class ProductBatchQuantityAction extends Action {
	AppFUnit af = new AppFUnit();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		Integer unitid = Integer.parseInt(request.getParameter("unitid"));
		
		TakeTicket sof = (TakeTicket) request.getSession().getAttribute("sof");
		
		String productid = request.getParameter("productid");
		request.setAttribute("productid", productid);
		request.setAttribute("unitid", unitid);
		
		AppProduct ap =new AppProduct();
		
		Product p =ap.getProductByID(productid);
		request.setAttribute("sunitid", p.getCountunit());
		
		//箱到最小单位转化率
//		Double Xnum = af.getXQuantity(productid, 2);
		Double Xnum = 0d;
		request.setAttribute("Xnum", Xnum);
		
//		String unitid = request.getParameter("unitid");
//		String warehouseid = request.getParameter("warehouseid");
//		String ttdid = request.getParameter("ttdid");
		// String quantity = request.getParameter("quantity");
		List<TakeTicketDetailBatchBit> ttdbbs = new ArrayList<TakeTicketDetailBatchBit>();
		//检货分配库存数量
		if("1".equals(type)){
			// 初始化库位库存		
			ttdbbs = initBatchBit(sof, productid);
		}else{
			AppTakeTicketDetailBatchBit attdbb = new AppTakeTicketDetailBatchBit();
			ttdbbs = attdbb.getBatchBitByTTIDPID(sof.getId(), productid);
		}	
//		request.setAttribute("unitid", unitid);
		
		// initBatchBit(sof, productid);

		AppTakeTicket att = new AppTakeTicket();
		try {
			String productName = att.getProductName(productid);
//			List<BatchQuantity> bqs = att.getBatchQuantity(warehouseid,productid);
			AppTakeTicketDetail attd = new AppTakeTicketDetail();
			double bquantity = attd.getTakeTicketProductTotalBoxQuantity(sof.getId(), productid);
			double squantity = attd.getTakeTicketProductTotalScatterQuantity(sof.getId(), productid);
			// String ttid = att.getTtid(billno);
			// request.setAttribute("ttid", ttid);
			request.setAttribute("productName", productName);
			//库存不足或产品未检验,无法检货
			if(ttdbbs.size() == 0){
				request.setAttribute("errormsg", "库存不足或产品未检验,无法完成检货.请检查库存后重试!");
			}
			//将ttdbbs转化为按月显示的批次
			ttdbbs = changeMonthPs(ttdbbs);
			
			request.setAttribute("ttdbbs", ttdbbs);
			//request.getSession().setAttribute("ttdbbs", ttdbbs);
			request.setAttribute("totalboxQuantity", bquantity);
			request.setAttribute("totalscatterQuantity", squantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		if("1".equals(type)){
			return mapping.findForward("calculate");
		}else{
			return mapping.findForward("calculateNoQuantity");
		}
		//return mapping.findForward("calculate");
	}
	
	

	/**
	 * 初始化检货用的库位库存
	 * 
	 * @param tt
	 * @param ttdid
	 * @param productId
	 * @param warehouseId
	 * @return
	 * @throws Exception
	 */
	/*
	private List<TakeTicketDetailBatchBit> initStockpile(TakeTicket tt,
			String ttdid, String productId, String warehouseId)
			throws Exception {
		AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
		// 页面显示用的库位库存列表
		List<TakeTicketDetailBatchBit> returnList = new ArrayList<TakeTicketDetailBatchBit>();
		AppProductStockpile appProductStockpile = new AppProductStockpile();
		List<ProductStockpile> stockList = appProductStockpile
				.getStockPileByPIDWID(productId, warehouseId);
		for (ProductStockpile productStockpile : stockList) {
			TakeTicketDetailBatchBit takeTicketDetailBatchBit = new TakeTicketDetailBatchBit();
			takeTicketDetailBatchBit.setTtid(tt.getId());
			takeTicketDetailBatchBit.setTtdid(Integer.valueOf(ttdid));
			takeTicketDetailBatchBit.setBatch(productStockpile.getBatch());
			takeTicketDetailBatchBit.setProductid(productId);
			takeTicketDetailBatchBit.setProductname(new AppProduct()
					.getProductNameByID(productId));
			takeTicketDetailBatchBit.setWarehouseBit(productStockpile
					.getWarehousebit());
			takeTicketDetailBatchBit.setQuantity(productStockpile
					.getStockpile());

			if (productStockpile.getStockpile() != 0) {
				returnList.add(takeTicketDetailBatchBit);
			}

		}
		return returnList;
	}
*/
	/**
	 * 初始化库位库存.并进行预出库
	 * 
	 * @param tt
	 *            检货小票ID
	 * @param pid
	 *            产品ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected static List<TakeTicketDetailBatchBit> initBatchBit(TakeTicket tt,String pid) throws Exception {
		AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
		AppProductStockpile appProductStockpile = new AppProductStockpile();
		AppFUnit af = new AppFUnit();
		// 页面显示用的库位库存列表
		List<TakeTicketDetailBatchBit> returnList = new ArrayList<TakeTicketDetailBatchBit>();
		AppProduct ap =new AppProduct();
		Product p ;
		// 取得对应的TTD
		List<TakeTicketDetail> ttds = appTakeTicketDetail.getTakeTicketDetailByTtidPid(tt.getId(), pid);
		for (TakeTicketDetail takeTicketDetail : ttds) {
			
			p =ap.getProductByID(takeTicketDetail.getProductid());
			//箱到最小单位转换率
			double boxTransfrom = 0d;
			//散到最小单位转换率
			double scatterTransfrom = 1d;
			
			//箱转化为最小单位
			double xtsQuantity = ArithDouble.mul(takeTicketDetail.getBoxnum(),boxTransfrom) ;
			//散转化为最小单位
			double stsQuantity = ArithDouble.mul(takeTicketDetail.getScatternum(),scatterTransfrom);
			//订购的数量(最小单位数)
			double needQuantity =  ArithDouble.add(xtsQuantity, stsQuantity);
			
			double needQuantityTemp = needQuantity;
			
			// 找到对应的库位库存  按照批次升序排列
			List<ProductStockpile> stockpiles = appProductStockpile.getStockPileByPIDWID(takeTicketDetail.getProductid(), tt.getWarehouseid());
			//将库存转化为份库存
			
			for (ProductStockpile productStockpile : stockpiles) {
				// 检查库存是否为0,0的不进行初始化库位
				if (productStockpile.getStockpile() == null || productStockpile.getStockpile() <= 0) {
					continue;
				}
				
				TakeTicketDetailBatchBit batchBit = new TakeTicketDetailBatchBit();
				batchBit.setTtid(tt.getId());
				batchBit.setTtdid(takeTicketDetail.getId());
				batchBit.setProductid(takeTicketDetail.getProductid());
				batchBit.setProductname(takeTicketDetail.getProductname());
				batchBit.setBatch(productStockpile.getBatch());
				batchBit.setWarehouseBit(productStockpile.getWarehousebit());
				batchBit.setStockQuantity(productStockpile.getStockpile());
				
				//箱到最小单位转化率
//				Double boxq = af.getXQuantity(takeTicketDetail.getProductid(), 2);
				Double boxq = boxTransfrom;
				//散到最小单位转化率
				Double scaq = scatterTransfrom;
				//得到库存整箱数
				int q = 0;
				batchBit.setStockboxnum(q);
				//得到库存散数
				double tqu = productStockpile.getStockpile();
				batchBit.setStockscatternum(tqu);

				batchBit.setUnitid(p.getScatterunitid());
				batchBit.setXtsQuantity(boxq);
				batchBit.setStsQuantity(scaq);
	
				//当库存数量大于订购数量时
				if (productStockpile.getStockpile() >= needQuantityTemp) {
					if(needQuantity==needQuantityTemp){
						batchBit.setBoxnum(takeTicketDetail.getBoxnum());
						batchBit.setScatternum(takeTicketDetail.getScatternum());
					}else{
						q = 0;
						batchBit.setBoxnum(q);
						
						tqu = ArithDouble.sub(needQuantityTemp, ArithDouble.mul(boxq, (double)q));
						batchBit.setScatternum(ArithDouble.div(tqu, scaq));
					}
					
					needQuantityTemp = 0;
				} else {// 如果库存不足需求量,设置最大库存，并取下一个库位库存,需求量扣减
					batchBit.setBoxnum(0);
					batchBit.setScatternum(ArithDouble.div( ArithDouble.sub(productStockpile.getStockpile(), ArithDouble.mul(boxq, (double)q)), scaq));
					
					needQuantityTemp = ArithDouble.sub(needQuantityTemp, productStockpile.getStockpile()) ;
				}

				returnList.add(batchBit);

			}
			//判断是否全部完全分配
			if(needQuantityTemp > 0){//若还有需要分配数量,即数量未分配完
				//删除不符合的对象
				for (int i = 0; i < returnList.size(); i++) {
					TakeTicketDetailBatchBit batchBit = returnList.get(i);
					if(batchBit.getTtdid().equals(takeTicketDetail.getId())){
						returnList.remove(batchBit);
						i--;
					}
				} 
			}
		}
		return returnList;
	}
	/**
	 * 将日库存转化为月库存
	 * Create Time 2014-4-16 上午10:29:37 
	 * @param dayPsList
	 * @return
	 * @author lipeng
	 */
	protected static List<TakeTicketDetailBatchBit> changeMonthPs(List<TakeTicketDetailBatchBit> dayTtdbbsList) throws Exception{
		/**
		 * 日库存批次为yyyymmdd
		 * 月库存批次为yyyymm
		 * 将日库存汇总为月库存
		 */
		List<TakeTicketDetailBatchBit> monthTtdbbs = new ArrayList<TakeTicketDetailBatchBit>();
		Map<String, TakeTicketDetailBatchBit> map = new TreeMap<String, TakeTicketDetailBatchBit>();
		for(TakeTicketDetailBatchBit dayTtdbbs : dayTtdbbsList){
			if(dayTtdbbs.getBatch() == null || "null".equals(dayTtdbbs.getBatch())){
				continue;
			}
			//获取月批次
			String monthBatch = "";
			monthBatch = dayTtdbbs.getBatch().substring(0, 6);
			String key = "";  //产品+批次
			key = dayTtdbbs.getProductid()+"_"+monthBatch;
			if(!map.containsKey(key)){
				TakeTicketDetailBatchBit monthBatchBit = new  TakeTicketDetailBatchBit();
				BeanUtils.copyProperties(monthBatchBit, dayTtdbbs);
				monthBatchBit.setBatch(monthBatch);
				map.put(key, monthBatchBit);
			}else {
				//box数量
				Integer boxNum = dayTtdbbs.getBoxnum() == null?0:dayTtdbbs.getBoxnum();
				//库存box数量
				Integer stockBoxnum = dayTtdbbs.getStockboxnum() == null?0:dayTtdbbs.getStockboxnum();
				//scatter数量
				double scatternum = dayTtdbbs.getScatternum() == null?0:dayTtdbbs.getScatternum();
				//库存scatter数量
				double stockScatternum = dayTtdbbs.getStockscatternum() == null?0:dayTtdbbs.getStockscatternum();
				//数量
				double quantity = dayTtdbbs.getQuantity()==null?0:dayTtdbbs.getQuantity();
				//库存数量
				double stockQuantity = dayTtdbbs.getStockQuantity() == null?0:dayTtdbbs.getStockQuantity();
				TakeTicketDetailBatchBit monthBatchBit  = map.get(key);
				//增加相关数量
				monthBatchBit.setBoxnum(monthBatchBit.getBoxnum()+boxNum);
				monthBatchBit.setStockboxnum(monthBatchBit.getStockboxnum()+stockBoxnum);
				monthBatchBit.setScatternum(monthBatchBit.getScatternum() + scatternum);
				monthBatchBit.setStockscatternum(monthBatchBit.getStockscatternum() + stockScatternum);
				monthBatchBit.setQuantity(monthBatchBit.getQuantity() + quantity);
				monthBatchBit.setStockQuantity(monthBatchBit.getStockQuantity() + stockQuantity);
			}
		}
		monthTtdbbs =  new ArrayList<TakeTicketDetailBatchBit>(map.values()) ;
		
		return monthTtdbbs;
	}
}
