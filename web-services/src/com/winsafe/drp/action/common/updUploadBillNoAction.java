package com.winsafe.drp.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.drp.action.BaseAction;

//import com.winsafe.drp.dao.DealUploadIdcode;
import com.winsafe.drp.dao.AppDrawShipmentBillIdcode;
import com.winsafe.drp.dao.AppOrganTradesPIdcode;
import com.winsafe.drp.dao.AppOrganTradesTIdcode;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.AppPurchaseTradesIdcode;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.AppSupplySaleMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWithdrawIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;


/**
 * @author : Qhonge
 * @version : 2010-2-24 下午03:35:48
 * www.winsafe.cn
 */
public class updUploadBillNoAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			
			String billSort = request.getParameter("billsort");
			String billno = request.getParameter("billno");
			String trueBillno = request.getParameter("truebillno");
			
			//for (int i = 0; i < billno.length; i++) {
				UpdateBillNo(Integer.parseInt(billSort), billno,trueBillno);
			//}
				request.setAttribute("result", "databases.add.success");
				//操作日志
				DBUserLog.addUserLog(userid, 20, "修改上传单据,编号:"+billno+"改为:"+trueBillno);// 日志
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "修改单号失败");
			//return new ActionForward("/sys/lockrecord2.jsp");
		}
		
		return mapping.findForward("success");
	}

	private void  UpdateBillNo(int billosrt,String billno,String truebillno) throws Exception{			
		switch ( billosrt ){
		case 0 : 
			{
				AppPurchaseIncomeIdcode t_apii = new AppPurchaseIncomeIdcode();
				t_apii.updPurchaseIncomeIdcodeBillNo(billno,truebillno);
				break;
			}
		case 1 :
			{
				AppTakeTicketIdcode t_tti = new AppTakeTicketIdcode();
				t_tti.updTakeTicketIdcodeBillNo(billno,truebillno);		
				break;

			}
		case 2 :
			{
				 AppStockCheckIdcode t_sci = new AppStockCheckIdcode();
				t_sci.updStockCheckIdcodeBillNo(billno,truebillno);		
				break;
			}
		case 3 :
			{
				AppProductIncomeIdcode t_pii = new AppProductIncomeIdcode();
				t_pii.updProductIncomeIdcodeBillNo(billno, truebillno); 
				break;

			}
		case 4 : 
			{
				 AppStockAlterMoveIdcode t_sami = new AppStockAlterMoveIdcode();
				t_sami.updStockAlterMoveIdcodeBillNo(billno, truebillno); 
			    break;

			}
		case 5 :
			{
				AppStockMoveIdcode t_smi = new AppStockMoveIdcode();
				t_smi.updStockMoveIdcodeBillNo(billno, truebillno); 
				break;

			}
		case 6 :
			{ AppSupplySaleMoveIdcode t_ssmi = new AppSupplySaleMoveIdcode();
			  t_ssmi.updSupplySaleMoveIdcodeBillNo(billno, truebillno);
			  break;

			}
		case 7 : 
			{		
				AppProductInterconvertIdcode t_piii = new AppProductInterconvertIdcode();
				t_piii.updSupplySaleMoveIdcodeBillNo(billno, truebillno); 
				break;

			}
		case 8 :
			{	
				AppOrganWithdrawIdcode t_owi = new AppOrganWithdrawIdcode();
				t_owi.updOrganWithdrawIdcodeBillNo(billno, truebillno); 
			    break;
			}
		case 9 : 
			{	 AppOrganTradesPIdcode t_otpi = new AppOrganTradesPIdcode();
			      t_otpi.updOrganTradesPIdcodeBillNo(billno, truebillno);
			     break;
			}
		case 10 :
			{ 
				AppOrganTradesTIdcode t_otti = new AppOrganTradesTIdcode();
			    t_otti.updOrganTradesTIdcodeBillNo(billno, truebillno);
			    break;
			}
		case 11 :
			{		 
				AppOtherShipmentBillIdcode t_osbi = new AppOtherShipmentBillIdcode();
			    t_osbi.updOtherShipmentBillIdcodeBillNo(billno, truebillno);
			   break;
			}
		case 12 :
			{
				AppOtherIncomeIdcode t_oii = new AppOtherIncomeIdcode(); 
				t_oii.updOtherIncomeIdcodeBillNo(billno, truebillno);
				break;
			}
		case 13 : 
			{
				AppPurchaseTradesIdcode t_pti = new AppPurchaseTradesIdcode(); 
				t_pti.updPurchaseTradesIdcodeBillNo(billno, truebillno);
				break;
			}
		case 14 : 
			{ 
				AppWithdrawIdcode t_wi = new AppWithdrawIdcode();
			    t_wi.updWithdrawIdcodeBillNo(billno, truebillno);
				break;
			}
		case 15 :
			{	 
				AppSaleTradesIdcode t_sti = new AppSaleTradesIdcode();			
			    t_sti.updSaleTradesIdcodeBillNo(billno, truebillno);
				break;
			}
		case 16: 
			{  
				AppDrawShipmentBillIdcode t_dsi = new AppDrawShipmentBillIdcode();	
				t_dsi.updDrawShipmentBillIdcodeBillNo(billno, truebillno);
			    break;  		
			}
		}
	}
	

}
