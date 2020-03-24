package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.OrganScan;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteStockMoveReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    String smid = request.getParameter("SMID");

		super.initdata(request);try{
			AppStockMove asm = new AppStockMove();
			StockMove sm = asm.getStockMoveByID(smid);

			
			if(sm.getIscomplete()==0){
	               request.setAttribute("result", "databases.record.already");
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
			
			//如果签收机构是需要扫描的情况 ---update by wenping start 2013-01-08
			AppOrganScan appos = new AppOrganScan();
			OrganScan os = appos.getOrganScanByOidScb(sm.getInorganid(), "SM");
			if(os!=null && os.getIsscan()==1){
				request.setAttribute("result", "databases.add.notcancelreceive");
				return mapping.findForward("operateresult");
			}
			// ---update by wenping end
			
			if (!sm.getInorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppStockMoveIdcode appmi = new AppStockMoveIdcode();
			List<StockMoveIdcode> idcodelist = appmi.getStockMoveIdcodeBysmid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( StockMoveIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			List<StockMoveIdcode> alllist = appmi.getStockMoveIdcodeBysmid(smid);
			
			AppTakeTicket  att =new AppTakeTicket();
			List<TakeTicket> tts=	att.getTakeTicketByBillno(smid);
			TakeTicket tt = tts.get(0);
			AppTakeTicketDetailBatchBit appTakeTicketDetailBatchBit = new AppTakeTicketDetailBatchBit(); 
			List<TakeTicketDetailBatchBit> batchBitList = appTakeTicketDetailBatchBit.getBatchBitByTTID(tt.getId());
			
			
			returnProductStockpile(batchBitList, sm);
			
			asm.updStockMoveIsComplete(smid, 0, userid);
			String idcodeid="";						
			for ( StockMoveIdcode ic : idcodelist ){
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
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>转仓签 收>>取消转仓签收,编号：" + smid);

			return mapping.findForward("movein");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<TakeTicketDetailBatchBit> batchBitList, StockMove sm) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		String batch ="";
		for (TakeTicketDetailBatchBit ttdbb : batchBitList ) {
			
			//如果批次长度为12位  就截取前9位作为批次保存
			if(ttdbb.getBatch()!=null && !ttdbb.getBatch().equals("") &&ttdbb.getBatch().length()==12 ){
				
				batch =ttdbb.getBatch().substring(0, 9);
			}else{
				//否则按原批次
				batch = ttdbb.getBatch();
			}
			
			aps.returninProductStockpile(ttdbb.getProductid(), ttdbb.getUnitid(), batch, ttdbb.getRealQuantity(),
					sm.getInwarehouseid(), ttdbb.getWarehouseBit(), sm.getId(), "取消转仓签收");
		}
	}

}
