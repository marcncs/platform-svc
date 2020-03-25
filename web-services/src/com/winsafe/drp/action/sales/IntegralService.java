package com.winsafe.drp.action.sales;

import java.util.Date;
import java.util.List;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppIntegralDetail;
import com.winsafe.drp.dao.AppIntegralI;
import com.winsafe.drp.dao.AppIntegralO;
import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPaymentMode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.AppSendTime;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.IntegralDetail;
import com.winsafe.drp.dao.IntegralI;
import com.winsafe.drp.dao.IntegralO;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralRule;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.ObjIntegral;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.PaymentMode;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.SendTime;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author jerry2009-3-3 新增客户积分,机构积分处理类
 */
public class IntegralService {

	private AppProductIntegral api = new AppProductIntegral();

	private AppIntegralI aii = new AppIntegralI();

	private AppIntegralO aio = new AppIntegralO();
	
	private AppIntegralDetail aid = new AppIntegralDetail();
	
	private AppCustomer ac = new AppCustomer();
	
	private Customer c= new Customer();
	
	private AppOrgan ao = new AppOrgan();
	
	private Organ o = new Organ();

	/** 单据编号 */
	private String billno;

	/** 机构编号 */
	private String morganid;
	
    /**配送机构*/
	private String eorganid;

	/** 会员编号 */
	private String cid;

	/** 付款方式 */
	private Integer paymentmode;

	/** 送货日期 */
	private Date consignmentdate;

	/** 订货方式编号 电话订购:1,网上订购:2,门店自购:3 */
	private int bookid;

	/** 产品基本积分 */
	private double baseIntegral = 0d;

	/**
	 * 销售单新增客户积分,机构积
	 * 
	 * @param so
	 *            销售单
	 * @param saleOrderDetail
	 *            销售单详情列表
	 */
	public void SaleOrderIntegral(SaleOrder so, List saleOrderDetail)
			throws Exception {
		if (saleOrderDetail == null) {
			return;
		}

		
		AppProduct ap = new AppProduct();
		for (int i = 0; i < saleOrderDetail.size(); i++) {
			SaleOrderDetail sod = (SaleOrderDetail) saleOrderDetail.get(i);
			
			if (ap.getProductByID(sod.getProductid()).getWise() == 0) {
				baseIntegral += sod.getQuantity()
						* getProductIntegral(sod.getProductid(), sod
								.getUnitid().intValue(),
								sod.getSalesort() == null ? 0 : sod
										.getSalesort());
			}
		}
		
		if (baseIntegral <= 0) {
			return;
		}
		// System.out.println("=======产品基本积分======="+baseIntegral);

		
		billno = so.getId();
		morganid = so.getMakeorganid();
		eorganid = so.getEquiporganid();
		cid = so.getCid();
		paymentmode = so.getPaymentmode();
		consignmentdate = so.getConsignmentdate();
		bookid = 1;
		
		c = ac.getCustomer(cid);
		o = ao.getOrganByID(eorganid);
		
		deal();
	}


	/**
	 * 积分换购单处理积分
	 * 
	 * @param io
	 *            积分换购单
	 * @throws Exception
	 */
	public void IntegralOrderDealIntegral(IntegralOrder io) throws Exception {
		if (io == null) {
			return;
		}

		
		billno = io.getId();
		morganid = io.getMakeorganid();
		eorganid = io.getEquiporganid();
		cid = io.getCid();
//		paymentmode = io.getPaymentmode();
//		consignmentdate = io.getConsignmentdate();
		bookid = 1;
		baseIntegral = io.getIntegralsum();
		
		c = ac.getCustomer(cid);
		o = ao.getOrganByID(eorganid);
		
		addSaleIntegral(baseIntegral,9);
		addChangeIntegral(baseIntegral);
		
	}

	
	private void deal() throws Exception {

		double buyIntegral = baseIntegral;
		addSaleIntegral(buyIntegral,1);
		
		
		double pi = 0d;
		//System.out.println("----------"+c.getParentid());
		if (c.getParentid()!= null && !c.getParentid().equals("")) {
			addCommendIntegral(c.getParentid());
		}
		
		
		AppIntegralRule air = new AppIntegralRule();
		IntegralRule ir = air.getIntegralRuleByID(bookid);
		double orderintegral = baseIntegral * ir.getIrate();
		if(orderintegral!=0){
		addSaleIntegral(orderintegral,3);
		}
		
		
		AppPaymentMode apm = new AppPaymentMode();
		PaymentMode pm = apm.getPaymentModeByID(paymentmode);
		double paymentintegral = baseIntegral * pm.getIntegralrate();
		if(paymentintegral!=0){
			addSaleIntegral(paymentintegral,4);
		}
		
		
		int sendtime = DateUtil.getTimeDifference(
				DateUtil.getCurrentDateTime(), DateUtil.formatDateTime(consignmentdate));
		AppSendTime ast = new AppSendTime();
		SendTime st = ast.getSendTimeBySETime(sendtime);
		double sendrate = 0d;
		if(st!=null){
			sendrate = st.getIntegralrate();
		}
		double sendtimeintegral = baseIntegral * sendrate;
		if(sendtimeintegral!=0){
		addSaleIntegral(sendtimeintegral,5);
		}
		
		
		AppMemberGrade amg = new AppMemberGrade();
		MemberGrade mg = amg.getMemberGradeByID(c.getRate());
		double rateintegral = baseIntegral * mg.getIntegralrate();
		if(rateintegral!=0){
		addSaleIntegral(rateintegral,6);
		}
		
		
		BuyIntegral(buyIntegral+pi+orderintegral+paymentintegral+sendtimeintegral+rateintegral);
	}

	/**
	 * 计算产品原始积分
	 * 
	 * @param pid
	 *            产品编号
	 * @param unitid
	 *            产品单位编号
	 * @param salesort
	 *            销售方式
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

	
	private void BuyIntegral(double integral) throws Exception {
		//会员加
		IntegralI ii = new IntegralI();
		ii.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_i", 0, "")));
		ii.setOsort(1);
		ii.setOid(cid);
		ii.setOname(c.getCname());
		ii.setOmobile(c.getMobile());
		ii.setBillno(billno);
		ii.setIsort(1);
		ii.setRincome(integral);
		ii.setAincome(0d);
		ii.setMakedate(DateUtil.getCurrentDate());
		ii.setEquiporganid(eorganid);
		ii.setOrganid(morganid);
		aii.addIntegralI(ii);
		aid.updIidByBillno(billno, 1, ii.getId());

		
		IntegralO io = new IntegralO();
		io.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_o", 0, "")));
		io.setOsort(0);
		io.setOid(eorganid);
		io.setOname(o.getOrganname());
		io.setOmobile(o.getOmobile());
		io.setBillno(billno);
		io.setIsort(1);
		io.setRout(integral);
		io.setAout(0d);
		io.setMakedate(DateUtil.getCurrentDate());
		io.setEquiporganid(eorganid);
		io.setOrganid(Constants.ORGANID);
		aio.addIntegralO(io);
		aid.updIidByBillno(billno, 0, io.getId());
	}
	
	private void addSaleIntegral(double integral,int isort) throws Exception {
		//会员加
		IntegralDetail idc = new IntegralDetail();
		idc.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_detail", 0, "")));
		idc.setOsort(1);
		idc.setOid(cid);
		idc.setOname(c.getCname());
		idc.setOmobile(c.getMobile());
		idc.setBillno(billno);
		idc.setIsort(isort);
		idc.setIntegral(integral);
		idc.setMakedate(DateUtil.getCurrentDate());
		idc.setEquiporganid(eorganid);
		idc.setOrganid(morganid);

		aid.addIntegralDetail(idc);

		//机构减
		IntegralDetail ido = new IntegralDetail();
		ido.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_detail", 0, "")));
		ido.setOsort(0);
		ido.setOid(cid);
		ido.setOname(c.getCname());
		ido.setOmobile(c.getMobile());
		ido.setBillno(billno);
		ido.setIsort(isort);
		ido.setIntegral(integral);
		ido.setMakedate(DateUtil.getCurrentDate());
		ido.setEquiporganid(eorganid);
		ido.setOrganid(morganid);

		aid.addIntegralDetail(ido);
		
	}

	/**
	 * 新增推荐积分
	 * 
	 * @param parentid
	 *            推荐客户编号
	 */
	private double addCommendIntegral(String parentid) throws Exception {
		Customer sp = ac.getCustomer(parentid);
		double addint = baseIntegral * 0.5;
		
		ObjIntegral ci = new ObjIntegral();
		ci.setOid(sp.getCid());
		ci.setOsort(1);
		ci.setOname(sp.getCname());
		ci.setOmobile(sp.getMobile());
		ci.setOrganid(sp.getMakeorganid());
		ci.setKeyscontent(sp.getCid()+","+sp.getCname()+","+sp.getMobile());
		AppObjIntegral aoi = new AppObjIntegral();
		aoi.addObjIntegralIsNoExist(ci);		
		
		//收入
		IntegralI ii = new IntegralI();
		ii.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_i", 0, "")));
		ii.setOsort(1);
		ii.setOid(ci.getOid());
		ii.setOname(ci.getOname());
		ii.setOmobile(ci.getOmobile());
		ii.setBillno(billno);
		ii.setIsort(7);
		ii.setRincome(addint);
		ii.setAincome(0d);
		ii.setMakedate(DateUtil.getCurrentDate());
		ii.setEquiporganid(eorganid);
		ii.setOrganid(morganid);
		aii.addIntegralI(ii);
		
		//会员明细加
		IntegralDetail idc = new IntegralDetail();
		idc.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_detail", 0, "")));
		idc.setIid(ii.getId());
		idc.setOsort(1);
		idc.setOid(parentid);
		idc.setOname(sp.getCname());
		idc.setOmobile(sp.getMobile());
		idc.setBillno(billno);
		idc.setIsort(2);
		idc.setIntegral(addint);
		idc.setMakedate(DateUtil.getCurrentDate());
		idc.setEquiporganid(eorganid);
		idc.setOrganid(morganid);
		aid.addIntegralDetail(idc);
		
		//机构支出
		IntegralO io = new IntegralO();
		io.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_o", 0, "")));
		io.setOsort(0);
		io.setOid(eorganid);
		io.setOname(o.getOrganname());
		io.setOmobile(o.getOmobile());
		io.setBillno(billno);
		io.setIsort(7);
		io.setRout(addint);
		io.setAout(0d);
		io.setMakedate(DateUtil.getCurrentDate());
		io.setEquiporganid(eorganid);
		io.setOrganid(Constants.ORGANID);
		aio.addIntegralO(io);

		//机构明细减
		IntegralDetail ido = new IntegralDetail();
		ido.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_detail", 0, "")));
		ido.setIid(io.getId());
		ido.setOsort(0);
		ido.setOid(parentid);
		ido.setOname(sp.getCname());
		ido.setOmobile(sp.getMobile());
		ido.setBillno(billno);
		ido.setIsort(2);
		ido.setIntegral(addint);
		ido.setMakedate(DateUtil.getCurrentDate());
		ido.setEquiporganid(eorganid);
		ido.setOrganid(morganid);
		aid.addIntegralDetail(ido);
		
		return addint ;

	}

	

	/**
	 * 新增兑换积分
	 * 
	 * @throws Exception
	 */
	private void addChangeIntegral(double integral) throws Exception {
//		机构加
		IntegralI ii = new IntegralI();
		ii.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_i", 0, "")));
		ii.setOsort(0);
		ii.setOid(eorganid);
		ii.setOname(o.getOrganname());
		ii.setOmobile(o.getOmobile());
		ii.setBillno(billno);
		ii.setIsort(2);
		ii.setRincome(integral);
		ii.setAincome(integral);
		ii.setMakedate(DateUtil.getCurrentDate());
		ii.setEquiporganid(eorganid);
		ii.setOrganid(Constants.ORGANID);
		aii.addIntegralI(ii);
		aid.updIidByBillno(billno, 1, ii.getId());

		
		IntegralO io = new IntegralO();
		io.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"integral_o", 0, "")));
		io.setOsort(1);
		io.setOid(c.getCid());
		io.setOname(c.getCname());
		io.setOmobile(c.getMobile());
		io.setBillno(billno);
		io.setIsort(2);
		io.setRout(integral);
		io.setAout(integral);
		io.setMakedate(DateUtil.getCurrentDate());
		io.setEquiporganid(eorganid);
		io.setOrganid(morganid);
		aio.addIntegralO(io);
		aid.updIidByBillno(billno, 0, io.getId());
	}

	

}
