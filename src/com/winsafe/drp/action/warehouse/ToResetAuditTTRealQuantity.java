package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.ArithDouble;

public class ToResetAuditTTRealQuantity extends BaseAction {
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Integer userid = users.getUserid();
		String ttid = request.getParameter("ttid");
		String warehouseId = new AppTakeTicket().getTakeTicketById(ttid).getWarehouseid();
		super.initdata(request);
		try {
			AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit();
			AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
			AppFUnit af =new AppFUnit();
			//得到捡货初始化好的库位库存表
			List<TakeTicketDetailBatchBit> ttdbbs = appTakeTicketDetailBatchBit.getBatchBitByTTID(ttid);
			//根据条码数量初始化实出数
			//initRealQuantityList(ttdbbs,warehouseId);
			//初始化实出数量等于计划订单数量 0316----RichieYu
			initRealQuantityListByOM(ttdbbs,warehouseId);
			int totalBoxQuantity = getTotalBoxQuantityByBatchBit(ttdbbs);
			Double totalScatterQuantity = getTotalScatterQuantityByBatchBit(ttdbbs);
			request.setAttribute("totalBoxQuantity", totalBoxQuantity);
			request.setAttribute("totalScatterQuantity", totalScatterQuantity.intValue());
			
			for(TakeTicketDetailBatchBit tb : ttdbbs){
				Double Xnum = af.getXQuantity(tb.getProductid(), 2);
				tb.setXnum(Xnum);
				
				Product product = new AppProduct().getProductByID(tb.getProductid());
				if(null != product){
					tb.setProductnccode(product.getNccode());
				}
			}
			//将日库存转化为月库存
			ttdbbs = ProductBatchQuantityAction.changeMonthPs(ttdbbs);
			for(TakeTicketDetailBatchBit ttdbb : ttdbbs){
				ttdbb.setRealboxnum(ttdbb.getBoxnum());
				ttdbb.setRealscatternum(ttdbb.getScatternum());
				ttdbb.setRealQuantity(ttdbb.getQuantity());
			}
			
			//取消使用session
			request.setAttribute("realttdbbs", ttdbbs);
			request.setAttribute("ttid", ttid);
			double idcodeQuantity=0.00;
			List<TakeTicketIdcode> idcodelist = apidcode.getTakeTicketIdcodeByttid(ttid, 1);
			for(TakeTicketIdcode tti : idcodelist){
				//Double Xnum = af.getXQuantity(tti.getProductid(), 2);
				//idcodeQuantity+=tti.getPackquantity()/Xnum;
//				idcodeQuantity+=tti.getPackquantity();
				idcodeQuantity=ArithDouble.add(idcodeQuantity, tti.getPackquantity());//kg
			}
			request.setAttribute("idcodesize", idcodeQuantity);
			//request.getSession().setAttribute("realttdbbs", ttdbbs);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	/**
	 * 初始化实出数
	 * @param ttdbbs 
	 * @param warehouseId 仓库id
	 * @throws Exception
	 */
	protected static void initRealQuantityList(List<TakeTicketDetailBatchBit> ttdbbs,String warehouseId) throws Exception{
		AppProductStockpile appProductStockpile = new AppProductStockpile();
		AppTakeTicketIdcode appTakeTicketIdcode = new AppTakeTicketIdcode();
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : ttdbbs) {
			ProductStockpile productStockpile = appProductStockpile.getProductStockpileByPidWid(takeTicketDetailBatchBit.getProductid(), warehouseId, takeTicketDetailBatchBit.getWarehouseBit(), takeTicketDetailBatchBit.getBatch());
			if(productStockpile == null || productStockpile.getStockpile() < 0){
				continue;
			}
			takeTicketDetailBatchBit.setStockQuantity(productStockpile.getStockpile() + takeTicketDetailBatchBit.getQuantity());
			double barcodeQuantity = appTakeTicketIdcode.getQuantitySumByttidProductidBatchBit(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getTtid(), takeTicketDetailBatchBit.getBatch(), takeTicketDetailBatchBit.getWarehouseBit());
			takeTicketDetailBatchBit.setBarcodeQuantity(barcodeQuantity);
			if(takeTicketDetailBatchBit.getRealQuantity() == null){
				takeTicketDetailBatchBit.setRealQuantity(takeTicketDetailBatchBit.getQuantity());
			}
			
		}
	}
	
	protected static void initRealQuantityListByOM(List<TakeTicketDetailBatchBit> ttdbbs,String warehouseId) throws Exception{
		AppProductStockpile appProductStockpile = new AppProductStockpile();
		AppFUnit af = new AppFUnit();
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : ttdbbs) {
			ProductStockpile productStockpile = appProductStockpile.getProductStockpileByPidWid(takeTicketDetailBatchBit.getProductid(), warehouseId, takeTicketDetailBatchBit.getWarehouseBit(), takeTicketDetailBatchBit.getBatch());
			if(productStockpile == null || productStockpile.getStockpile() < 0){
				continue;
			}
			//散到最小单位的转化率
			double qq = 1d;
			takeTicketDetailBatchBit.setStockQuantity(ArithDouble.add(productStockpile.getStockpile(), ArithDouble.mul(takeTicketDetailBatchBit.getQuantity(), qq)));
			
			//每箱支数
			Double boxq = 0d;
			//每小包装转化为支数
			Double scaq = 1d;
			
			//得到整箱数
			int q = 0;
			takeTicketDetailBatchBit.setStockboxnum(q);
			//得到散数
			double tqu = ArithDouble.sub(takeTicketDetailBatchBit.getStockQuantity(), ArithDouble.mul(boxq, (double)q));
			takeTicketDetailBatchBit.setStockscatternum(ArithDouble.div(tqu, scaq));
			
			takeTicketDetailBatchBit.setRealboxnum(takeTicketDetailBatchBit.getBoxnum());
			takeTicketDetailBatchBit.setRealscatternum(takeTicketDetailBatchBit.getScatternum());
		}
	}
	
	private int getTotalBoxQuantityByBatchBit(List<TakeTicketDetailBatchBit> ttdbbs) throws Exception{
		int totalBoxQuantity = 0;
//		AppFUnit af = new AppFUnit();
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : ttdbbs) {
//			double qq = af.getXQuantity(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getUnitid());
			totalBoxQuantity += takeTicketDetailBatchBit.getBoxnum();
		}
		return totalBoxQuantity;
	}
	
	private double getTotalScatterQuantityByBatchBit(List<TakeTicketDetailBatchBit> ttdbbs) throws Exception{
		double totalScatterQuantity = 0;
//		AppFUnit af = new AppFUnit();
		for (TakeTicketDetailBatchBit takeTicketDetailBatchBit : ttdbbs) {
//			double qq = af.getXQuantity(takeTicketDetailBatchBit.getProductid(), takeTicketDetailBatchBit.getUnitid());
			totalScatterQuantity =ArithDouble.add(totalScatterQuantity, takeTicketDetailBatchBit.getScatternum()) ;
		}
		return totalScatterQuantity;
	}
}
