package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppScannerUser;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.ScannerUser;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;

public class TakeTicketDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		String id = request.getParameter("ID");
		initdata(request);
		AppTakeTicket aso = new AppTakeTicket();
		AppProduct ap = new AppProduct();
		AppTakeTicketDetail asld = new AppTakeTicketDetail();
		
		try{
			
			TakeTicket so = aso.getTakeTicketById(id);
			if ( so == null ){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			asld.updTakeReadStatusById(id);
			List<TakeTicketDetail> sals = asld.getTakeTicketDetailByTtid(id);
			
			for(TakeTicketDetail  ttd :  sals){				
				  ttd.setNccode(ap.getProductByID(ttd.getProductid()).getmCode());
			}
			
			//退货
			if (so.getBillno().startsWith("OW"))
			{
				request.setAttribute("flag", "OW");
			}else if(so.getBillno().startsWith("PW")){
				request.setAttribute("flag", "PW");
			} else {
				request.setAttribute("flag", "OTHER");
			}
			
			//输出采集器编号
			AppScannerUser appScannerUser = new AppScannerUser();
			List<ScannerUser>  scannerUsers = appScannerUser.getScanners();
			request.setAttribute("scannerUsers", scannerUsers);
			
			
            request.setAttribute("IsRead", so.getIsread());
			request.setAttribute("als", sals);
			request.setAttribute("sof", so);
			request.setAttribute("type", request.getParameter("type"));
			
			//saveToken(request);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
