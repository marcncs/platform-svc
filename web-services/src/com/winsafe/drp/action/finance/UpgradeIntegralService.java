package com.winsafe.drp.action.finance;

import java.util.List;

import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppMemberGradeRule;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppWebIndent;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.MemberGradeRule;

/**
 * 计算会员晋级类
 * @author jelli
 * 2009-3-6
 */
public class UpgradeIntegralService {
	
	/**
	 * 计算会员晋级
	 * @param customerid 会员编号
	 * @param currentsum 会员当前消费金额
	 * @throws Exception
	 */
	public void upgrade(String customerid, double currentsum) throws Exception{
		AppCustomer ac = new AppCustomer();
		Customer c = ac.getCustomer(customerid);
		if ( c == null ){
			return;
		}
		
		if ( c.getRate() == 0 ){
			return;
		}
		
		
		AppSaleOrder appso = new AppSaleOrder();		
		AppPeddleOrder apppo = new AppPeddleOrder();
		AppWebIndent appwi = new AppWebIndent();
		int size = appso.getSaleOrderEndcase(customerid) 
			+ apppo.getPeddleOrderByCid(customerid)
			+ appwi.getWebIndentEndcase(customerid);
		
		AppMemberGradeRule appmgr = new AppMemberGradeRule();
		List mgrlist = appmgr.getAllMemberGradeRule();
		
		AppMemberGrade appmg = new AppMemberGrade();		
		
		
		if ( size == 1 ){
			for( int i=0; i<mgrlist.size(); i++ ){
				MemberGradeRule mgr = (MemberGradeRule)mgrlist.get(i);
				
				if ( currentsum >= mgr.getStartprice().doubleValue() 
						&& currentsum < mgr.getEndprice().doubleValue() ){
					c.setRate(mgr.getMgid().intValue());
					c.setPolicyid(appmg.getMemberGradeByID(mgr.getMgid()).getPolicyid());
					ac.updateCustomer(c);
					
					break;
				}
			}
		}else{
			AppCIntegral appci = new AppCIntegral();
			double totalsum = appci.getUpgradeIntegralByCID(customerid);
			//System.out.println("=============总积分="+totalsum);
			for( int i=0; i<mgrlist.size(); i++ ){
				MemberGradeRule mgr = (MemberGradeRule)mgrlist.get(i);
				
				if ( totalsum >= mgr.getStartintegral().doubleValue() 
						&& totalsum < mgr.getEndintegral().doubleValue() ){
					
					if ( c.getRate().intValue() == mgr.getMgid().intValue() ){
						break;
					}
					c.setRate(mgr.getMgid().intValue());
					c.setPolicyid(appmg.getMemberGradeByID(mgr.getMgid()).getPolicyid());
					ac.updateCustomer(c);
					
					break;
				}
			}
		}
	}


}
