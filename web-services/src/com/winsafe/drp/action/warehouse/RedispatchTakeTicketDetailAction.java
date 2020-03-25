
package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.JsonUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedispatchTakeTicketDetailAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//当前TT小票
		TakeTicket tt = (TakeTicket) request.getSession().getAttribute("sof");
		if(tt.getIsaudit() == 1){
			return null;
		}
		//待捡配列表
		List<TakeTicketDetailBatchBit> batchBitList = new ArrayList<TakeTicketDetailBatchBit>();
		//待捡配列表的数量
		List<String> bqs = new ArrayList<String>();
		//要改变检货状态的ttdid
		Set<Integer> ttdids = new HashSet<Integer>();
		//是否是全部检货标示位
		String allid = request.getParameter("allId");
		//产品ID
		String pid = request.getParameter("productid");
		AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
		List<TakeTicketDetail> ttds = appTakeTicketDetail.getTakeTicketDetailByTtidPid(tt.getId(), pid);
		/*
		//全部检货
		if ("1".equals(allid)) {
			//初始化检货列表
			List<TakeTicketDetailBatchBit> ttdbbList = new ArrayList<TakeTicketDetailBatchBit>();
			String[] proid = request.getParameterValues("allselectProductID");
			for (String prid : proid) {
				//判断是否已经检货
				if(!isExistInBatchBit(tt.getId(),prid)){//如果不存在
					List<TakeTicketDetailBatchBit> ttdbballs = ProductBatchQuantityAction.initBatchBit(tt, prid);
					ttdbbList.addAll(ttdbballs);
				}else{
					continue;
				}
			}
			batchBitList = ttdbbList;
			//初始化检货列表数量
			for (int i = 0; i < batchBitList.size(); i++) {
				bqs.add(batchBitList.get(i).getQuantity().toString());
				ttdids.add(batchBitList.get(i).getTtdid());
			}
			*/
		//部分检货
//		}else{
			//batchBitList = (List)request.getAttribute("ttdbbs");
			//判断是否检货
			//判断是否已经检货
//			if(!isExistInBatchBit(tt.getId(),pid)){//如果不存在
//				batchBitList = ProductBatchQuantityAction.initBatchBit(tt,pid);
////				for(String item : boxnum) {
////					String[] bq = item.split(",");
////					for (int i = 0; i < bq.length; i++) {
////						bqs.add(bq[i]);
////					}
////				}
//				
//				ttdids.add(batchBitList.get(0).getTtdid());
//			}else{
//				batchBitList = new ArrayList<TakeTicketDetailBatchBit>();
//			}
			
//		}
		String[] monthBatch = (String[])request.getParameterValues("monthBatch");
		String[] boxnum = (String[])request.getParameterValues("boxnum");
		String[] scatternum = (String[])request.getParameterValues("scatternum");
		//初始化库位
		int count = monthBatch.length;
		for(int i=0 ; i<count ; i++){
			String mBatch = monthBatch[i];
			int bnum = Integer.parseInt(boxnum[i]);
			Double snum = Double.valueOf(scatternum[i]);
			if(bnum > 0 || snum >0){
				List<TakeTicketDetailBatchBit> monthBatchBitList = initBatchBit(tt, ttds.get(0).getId(),pid, mBatch, bnum, snum);
				batchBitList.addAll(monthBatchBitList);			
			}
		}
		//更新检货小票明细为检货状态
		if(batchBitList.size() > 0){
			
			ttdids.add(batchBitList.get(0).getTtdid());
			
			AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
			AppProductStockpileAll appProductStockpileAll = new AppProductStockpileAll();
			AppProductStockpile appProductStockpile = new AppProductStockpile();
			AppProduct ap =new AppProduct();
			Product p;
			//已复核不能修改
			if(tt.getAuditid() != null){
				return null;
			}
			
			for (int i = 0; i < batchBitList.size(); i++) {
//				int bnum = Integer.parseInt(boxnum[i]);
//				Double snum = Double.valueOf(scatternum[i]);
					TakeTicketDetailBatchBit takeTicketDetailBatchBit = batchBitList.get(i);
					p =ap.getProductByID(takeTicketDetailBatchBit.getProductid());
//					//以小包装为单位的数量
//					takeTicketDetailBatchBit.setQuantity(ArithDouble.add(0*bnum, snum));
//					takeTicketDetailBatchBit.setBoxnum(bnum);
//					takeTicketDetailBatchBit.setScatternum(snum);
					//保存分配的库位库存
					appTakeTicketDetailBatchBit.addTakeTicketDetailBatchBit(takeTicketDetailBatchBit);
					//预出库
					appProductStockpileAll.prepareOut(takeTicketDetailBatchBit.getProductid(),takeTicketDetailBatchBit.getUnitid() ,tt.getWarehouseid(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getQuantity());
					appProductStockpile.prepareOut(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getUnitid() ,tt.getWarehouseid(), takeTicketDetailBatchBit.getWarehouseBit(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getQuantity());
			}
			//更新检货小票明细为已检货
			for (Integer ttdid : ttdids) {
				TakeTicketDetail takeTicketDetail = appTakeTicketDetail.getTakeTicketDetailByID(ttdid); 
				takeTicketDetail.setIsPicked(1);
				appTakeTicketDetail.updTakeTicketDetail(takeTicketDetail);
			}
		}else{
			JSONObject json = new JSONObject();			
			json.put("result", "0");				
			JsonUtil.setJsonObj(response, json);
		}
		
		//AppTakeTicketDetail attd = new AppTakeTicketDetail();
		//attd.updateBatchQuantity(tt.getId(), pid, bqs);
		
//		for(String[] bq : bqs) {
//			System.out.println(bq[0] + "," + bq[1]);
//		}
		return null;
	}
	
	/**
	 * 判断是否检货
	 * @param ttid 小票id
	 * @param pid 产品id
	 * @return 是否存在标识符
	 * @throws Exception 
	 */
	protected static boolean isExistInBatchBit(String ttid, String pid) throws Exception{
		AppTakeTicketDetail appTakeTicketDetail = new AppTakeTicketDetail();
		//TT下不会有重复的产品记录
		List<TakeTicketDetail> ttdetails = appTakeTicketDetail.getTakeTicketDetailByTtidPid(ttid, pid);
		if(ttdetails.get(0).getIsPicked() != null && ttdetails.get(0).getIsPicked() == 1){//已存在,已检货
			return true;
		}
		return false;
	}
	
	/**
	 * 根据月份初始化库位库存.并进行预出库
	 */
	@SuppressWarnings("unchecked")
	protected static List<TakeTicketDetailBatchBit> initBatchBit(TakeTicket tt,Integer ttdid,String pid,String monthBatch,Integer checkBoxNum,Double checkscatterNum) throws Exception {
		AppProductStockpile appProductStockpile = new AppProductStockpile();
//		AppFUnit af = new AppFUnit();
		// 页面显示用的库位库存列表
		List<TakeTicketDetailBatchBit> returnList = new ArrayList<TakeTicketDetailBatchBit>();
		AppProduct ap =new AppProduct();
		Product p ;
			p =ap.getProductByID(pid);
			//箱到最小单位转换率
			double boxTransfrom = 0d;
			//散到最小单位转换率
			double scatterTransfrom = 1d;
			//箱转化为最小单位
			double xtsQuantity = ArithDouble.mul(checkBoxNum,boxTransfrom) ;
			//散转化为最小单位
			double stsQuantity = ArithDouble.mul(checkscatterNum,scatterTransfrom);
			//订购的数量(最小单位数)
			double needQuantity =  ArithDouble.add(xtsQuantity, stsQuantity);
			
			double needQuantityTemp = needQuantity;
			
			// 找到对应的批次库位库存  按照批次升序排列
			List<ProductStockpile> stockpiles = appProductStockpile.getMonthPSByPIDWIDBatch(pid, tt.getWarehouseid(),monthBatch);
			//将库存转化为份库存
			for (ProductStockpile productStockpile : stockpiles) {
				// 检查库存是否为0,0的不进行初始化库位
				if (productStockpile.getStockpile() <= 0) {
					continue;
				}
				
				TakeTicketDetailBatchBit batchBit = new TakeTicketDetailBatchBit();
				batchBit.setTtid(tt.getId());
				batchBit.setTtdid(ttdid);
				batchBit.setProductid(pid);
				batchBit.setProductname(p.getProductname());
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
						batchBit.setBoxnum(checkBoxNum);
						batchBit.setScatternum(checkscatterNum);
						batchBit.setQuantity(checkscatterNum);
					}else{
						q = 0;
						batchBit.setBoxnum(q);
						
						tqu = ArithDouble.sub(needQuantityTemp, ArithDouble.mul(boxq, (double)q));
						batchBit.setScatternum(ArithDouble.div(tqu, scaq));
						batchBit.setQuantity(ArithDouble.div(tqu, scaq));
					}
					
					needQuantityTemp = 0;
				} else {// 如果库存不足需求量,设置最大库存，并取下一个库位库存,需求量扣减
					batchBit.setBoxnum(0);
					batchBit.setScatternum(ArithDouble.div( ArithDouble.sub(productStockpile.getStockpile(), ArithDouble.mul(boxq, (double)q)), scaq));
					batchBit.setQuantity(ArithDouble.div( ArithDouble.sub(productStockpile.getStockpile(), ArithDouble.mul(boxq, (double)q)), scaq));
					
					needQuantityTemp = ArithDouble.sub(needQuantityTemp, productStockpile.getStockpile()) ;
				}

				returnList.add(batchBit);

			}
			//判断是否全部完全分配
			if(needQuantityTemp > 0){//若还有需要分配数量,即数量未分配完
				//删除不符合的对象
				for (int i = 0; i < returnList.size(); i++) {
					TakeTicketDetailBatchBit batchBit = returnList.get(i);
					if(batchBit.getTtdid().equals(ttdid)){
						returnList.remove(batchBit);
						i--;
					}
				} 
			}
		return returnList;
	}
}