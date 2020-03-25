package com.winsafe.drp.action.sales;

import java.util.List;

import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.AppSendTime;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.IntegralRule;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.SendTime;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry2009-3-3
 * 新增客户积分,机构积分处理类
 */
public class PeddleIntegralDealService {

	private AppProductIntegral api = new AppProductIntegral();
	private AppCIntegralDeal acid = new AppCIntegralDeal();
	private AppOIntegralDeal aoid = new AppOIntegralDeal();

	private String billno;
	private String organid;
	private String cid;
	private double baseIntegral = 0d;
	
	private PeddleOrder po;
	private List peddleOrderDetail;
	
	/**
	 * 构造方法
	 * @param so 销售单
	 * @param peddleOrderDetail 销售单详情
	 */
	public PeddleIntegralDealService(PeddleOrder po, List peddleOrderDetail){
		this.po = po;
		this.peddleOrderDetail = peddleOrderDetail;
	}
	
	
	public void dealIntegral() throws Exception {
		if ( peddleOrderDetail == null ){
			return;
		}
		
		
		AppProduct ap = new AppProduct();
		for ( int i=0; i<peddleOrderDetail.size(); i++ ){
			PeddleOrderDetail sod = (PeddleOrderDetail)peddleOrderDetail.get(i);
			
			if ( ap.getProductByID(sod.getProductid()).getWise() == 0 ){
				baseIntegral += sod.getQuantity() * getProductIntegral(sod.getProductid(), sod.getUnitid().intValue(), sod.getSalesort());
			}
		}
		
		if ( baseIntegral <= 0 ){
			return;
		}
		
		
		
		billno = po.getId();
		organid = po.getEquiporganid();
		cid = po.getCid();
		
		AppCustomer ac = new AppCustomer();
		Customer c = ac.getCustomer(cid);
		
		
		addSaleIntegral();
		
		addCommendIntegral(c.getParentid());
		
		addBookIntegral();
		
		addPaymentIntegral(po.getPaymentmode());
		
		//addSendTimeIntegral(DateUtil.formatDateTime(po.getConsignmentdate()));
		
		addMemberGradeIntegral(Long.valueOf(c.getRate()));
	}

	/**
	 * 计算产品原始积分
	 * @param pid 产品编号
	 * @param unitid 产品单位编号
	 * @param salesort 销售方式
	 */
	private double getProductIntegral(String pid, Integer unitid,
			Integer salesort) {
		try {
			ProductIntegral pi = api.getProductIntegralByPIDUIDSID(pid, unitid,
					salesort);
			if (pi != null) {
				return pi.getIntegral() * pi.getIntegralrate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
	}

	
	private void addSaleIntegral() throws Exception {
		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(cid);
		integral.setIsort(1);
		integral.setDealintegral(baseIntegral);
		integral.setCompleteintegral(baseIntegral);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(1);
		ontegral.setDealintegral(-baseIntegral);
		ontegral.setCompleteintegral(-baseIntegral);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		ontegral.setIssettlement(0);
		
		aoid.addOIntegralDeal(ontegral);
		
	}

	/**
	 * 新增推荐积分
	 * @param parentid 推荐客户编号
	 */
	private void addCommendIntegral(String parentid) throws Exception {		
		
		if ( parentid == null || parentid.equals("") ){
			return;
		}
		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(parentid);
		integral.setIsort(2);
		integral.setDealintegral(baseIntegral * 0.5);
		integral.setCompleteintegral(baseIntegral * 0.5);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(2);
		ontegral.setDealintegral(-baseIntegral * 0.5);
		ontegral.setCompleteintegral(-baseIntegral * 0.5);
		ontegral.setIssettlement(0);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		aoid.addOIntegralDeal(ontegral);
		
	}

	
	private void addBookIntegral() throws Exception {
		AppIntegralRule air = new AppIntegralRule();
		IntegralRule ir = new IntegralRule();// air.getIntegralRuleByID(3l);
		double orderintegral = baseIntegral * ir.getIrate();
		if ( orderintegral == 0 ){
			return;
		}

		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(cid);
		integral.setIsort(3);
		integral.setDealintegral(orderintegral);
		integral.setCompleteintegral(orderintegral);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(3);
		ontegral.setDealintegral(-orderintegral);
		ontegral.setCompleteintegral(-orderintegral);
		ontegral.setIssettlement(0);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		aoid.addOIntegralDeal(ontegral);
		
	}

	/**
	 * 新增付款方式积分
	 * @param paymentmode 付款方式
	 * @throws Exception
	 */
	private void addPaymentIntegral(Integer paymentmode) throws Exception {
		AppPaymentMode apm = new AppPaymentMode();
		PaymentMode pm = apm.getPaymentModeByID(paymentmode);
		double paymentintegral = baseIntegral * pm.getIntegralrate();
		if ( paymentintegral == 0 ){
			return;
		}

		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(cid);
		integral.setIsort(4);
		integral.setDealintegral(paymentintegral);
		integral.setCompleteintegral(paymentintegral);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(4);
		ontegral.setDealintegral(-paymentintegral);
		ontegral.setCompleteintegral(-paymentintegral);
		ontegral.setIssettlement(0);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		aoid.addOIntegralDeal(ontegral);
		
	}

	/**
	 * 新增送货时间积分
	 * @param consignmentdate 交货日期
	 * @throws Exception
	 */
	private void addSendTimeIntegral(String consignmentdate) throws Exception {
		int sendtime = DateUtil.getTimeDifference(DateUtil.getCurrentDateTime(), consignmentdate);
		AppSendTime ast = new AppSendTime();
		SendTime st = ast.getSendTimeBySETime(sendtime);
		double sendtimeintegral = baseIntegral * st.getIntegralrate();
		if ( sendtimeintegral == 0 ){
			return;
		}

		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(cid);
		integral.setIsort(5);
		integral.setDealintegral(sendtimeintegral);
		integral.setCompleteintegral(sendtimeintegral);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(5);
		ontegral.setDealintegral(-sendtimeintegral);
		ontegral.setCompleteintegral(-sendtimeintegral);
		ontegral.setIssettlement(0);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		aoid.addOIntegralDeal(ontegral);
		
	}
	/**
	 * 新增会员级别积分
	 * @param rate 会员级别
	 * @throws Exception
	 */
	private void addMemberGradeIntegral(Long rate) throws Exception {
		AppMemberGrade amg = new AppMemberGrade();
		MemberGrade mg =new MemberGrade();//amg.getMemberGradeByID(rate);
		double rateintegral = baseIntegral * mg.getIntegralrate();
		if ( rateintegral == 0 ){
			return;
		}

		CIntegralDeal integral = new CIntegralDeal();
		integral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"c_integral_deal", 0, "")));
		integral.setBillno(billno);
		integral.setOrganid(organid);
		integral.setCid(cid);
		integral.setIsort(6);
		integral.setDealintegral(rateintegral);
		integral.setCompleteintegral(rateintegral);
		integral.setIssettlement(0);
		integral.setMakedate(DateUtil.getCurrentDate());
		acid.addCIntegralDeal(integral);

		OIntegralDeal ontegral = new OIntegralDeal();
		ontegral.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
				"o_integral_deal", 0, "")));
		ontegral.setBillno(billno);
		ontegral.setOid(organid);
		ontegral.setIsort(6);
		ontegral.setDealintegral(-rateintegral);
		ontegral.setCompleteintegral(-rateintegral);
		ontegral.setIssettlement(0);
		ontegral.setMakedate(DateUtil.getCurrentDate());
		aoid.addOIntegralDeal(ontegral);
		
	}

	
//	private void upgrade(String customerid) throws Exception{
//		AppCustomer ac = new AppCustomer();
//		Customer c = ac.getCustomer(customerid);
//		
//		AppSaleOrder appso = new AppSaleOrder();
//		String where = " where so.cid ='"+customerid+"' and so.isendcase=1 ";
//		List list = appso.getSaleOrderCustomer(where);
//		
//		AppMemberGradeRule appmgr = new AppMemberGradeRule();
//		List mgrlist = appmgr.getAllMemberGradeRule();
//		
//		AppMemberGrade appmg = new AppMemberGrade();
//		
//		
//		
//		if ( list == null ){
//			for( int i=0; i<mgrlist.size(); i++ ){
//				MemberGradeRule mgr = (MemberGradeRule)mgrlist.get(i);
//				if ( so.getTotalsum().doubleValue() >= mgr.getStartprice().doubleValue() 
//						&& so.getTotalsum().doubleValue() < mgr.getEndprice().doubleValue() ){
//					c.setRate(mgr.getMgid().intValue());
//					c.setPolicyid(appmg.getMemberGradeByID(mgr.getMgid()).getPolicyid());
//					ac.updateCustomer(c);
//					break;
//				}
//			}
//		}else{
//			AppCIntegral appci = new AppCIntegral();
//			double totalsum = appci.getCIntegralByCID(cid);
//			for( int i=0; i<mgrlist.size(); i++ ){
//				MemberGradeRule mgr = (MemberGradeRule)mgrlist.get(i);
//				if ( totalsum >= mgr.getStartintegral().doubleValue() 
//						&& totalsum < mgr.getEndintegral().doubleValue() ){
//					c.setRate(mgr.getMgid().intValue());
//					c.setPolicyid(appmg.getMemberGradeByID(mgr.getMgid()).getPolicyid());
//					ac.updateCustomer(c);
//					break;
//				}
//			}
//		}
//	}

}
