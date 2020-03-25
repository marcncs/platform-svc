package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawIdcode;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteOrganWithdrawReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		super.initdata(request);try{
			AppOrganWithdraw asm = new AppOrganWithdraw();
			OrganWithdraw sm = asm.getOrganWithdrawByID(smid);

			
			if (sm.getIscomplete() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppReceivable appr = new AppReceivable();
			List<Receivable> rlist = appr.getReceivableByBillno(sm.getId());
			
			for ( Receivable r : rlist ){
				if ( r.getAlreadysum() > 0 ){
					request.setAttribute("result", "对不起，该单据已经收款不能取消！");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}		
			
			AppOrganWithdrawIdcode appmi = new AppOrganWithdrawIdcode();
			List<OrganWithdrawIdcode> idcodelist = appmi.getOrganWithdrawIdcodeByowid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( OrganWithdrawIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}


			List<OrganWithdrawIdcode> alllist = appmi.getOrganWithdrawIdcodeByowid(smid);
			
			AppTakeTicket  att =new AppTakeTicket();
			List<TakeTicket> tts=	att.getTakeTicketByBillno(smid);
			TakeTicket tt = tts.get(0);
			AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit(); 
			List<TakeTicketDetailBatchBit> batchBitList = appTakeTicketDetailBatchBit.getBatchBitByTTID(tt.getId());
			
			returnProductStockpile(batchBitList, sm);
			
			asm.updOrganWithdrawIsReceive(smid, 0, userid);
			String idcodeid="";					
			for ( OrganWithdrawIdcode ic : idcodelist ){
//				appidcode.updIsUse(ic.getIdcode(), 0);
				if(ic.getUnitid()==17){//托码
					//托中最后一条条码的后几位顺序码
					int lastidocde = Integer.parseInt(ic.getEndno().substring(4,13));
					//托中第一条条码的后几位顺序码
					int firstidocde = Integer.parseInt(ic.getStartno().substring(4,13));
					//条码前四位
					String idcodeprefix = ic.getEndno().substring(0,4);
					
					//托条码中的箱数量
					int n =lastidocde-firstidocde+1;
					for(int k =n; k>0;k--){
						idcodeid =idcodeprefix +String.format("%09d", lastidocde) ;
						lastidocde--;
						
						appidcode.updIsUse(idcodeid, 0);	
					}
				}else{
				       appidcode.updIsUse(ic.getIdcode(), 0);
				}
			}
			
			for ( Receivable r : rlist ){				
				appr.delReceivable(r.getId());
			}

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>取消签收渠道退货,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<TakeTicketDetailBatchBit> batchBitList, OrganWithdraw ow) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
//		AppProduct ap = new  AppProduct();
		for (TakeTicketDetailBatchBit ttdbb : batchBitList ) {
			
			aps.returninProductStockpile(ttdbb.getProductid(), 2, ttdbb.getBatch(), ttdbb.getRealQuantity(),
					ow.getInwarehouseid(), ttdbb.getWarehouseBit(), ow.getId(), "取消渠道退货签收-出库");
		}
	}

}
