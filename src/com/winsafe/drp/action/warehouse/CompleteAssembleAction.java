package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CompleteAssembleAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		//Connection conn = null;
		super.initdata(request);try{
			String osid = request.getParameter("OSID");
			AppAssemble asb = new AppAssemble();
			Assemble sb = asb.getAssembleByID(osid);

			if (sb.getIsaudit() == 0) {
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
//			if (sb.getIscomplete() == 1) {
//				String result = "databases.record.already";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
			
			AppTakeService appts = new AppTakeService();		
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(osid);		
						
			if ( appts.isNotAuditTakeTicket(ticketlist) ){
				String result = "databases.record.taketicket.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
		
			
			AppAssembleDetail aspb = new AppAssembleDetail();
			List spbls = aspb.getAssembleDetailByAid(osid); 
				
			
			AppOtherIncome appoi = new AppOtherIncome();
			OtherIncome tt = new OtherIncome();
			String ttid = MakeCode.getExcIDByRandomTableName("other_income", 2,
					"OI");
			tt.setId(ttid);
			tt.setIncomesort(1);
			//tt.setBillno(sb.getId());
//			tt.setWarehouseid(sb.getWarehouseinid());
//			tt.setIncomedept(sb.getAdept());
//			tt.setIncomedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			tt.setRemark("组装单生成其它组装入库单");
			tt.setIsaudit(0);
//			tt.setMakeid(userid);
			tt.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			appoi.addOtherIncome(tt);

			AppOtherIncomeDetail appttd = new AppOtherIncomeDetail();
			OtherIncomeDetail ttd = null;			
			for (int t = 0; t < spbls.size(); t++) {
				AssembleDetail pid = (AssembleDetail) spbls.get(t);
				
				ttd = new OtherIncomeDetail();
//				ttd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"other_income_detail", 0, "")));
				ttd.setOiid(ttid);
				ttd.setProductid(pid.getProductid());
				ttd.setProductname(pid.getProductname());
				ttd.setSpecmode(pid.getSpecmode());
				ttd.setUnitid(Integer.valueOf(pid.getUnitid().toString()));
//				ttd.setBatch(sb.getBatch());
				ttd.setUnitprice(0d);
				ttd.setQuantity(pid.getQuantity());
				ttd.setSubsum(0d);
				appttd.addOtherIncomeDetail(ttd);				
			}

//			asb.updIsComplete(osid, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");

//			DBUserLog.addUserLog(userid, "完工组装");
			
			return mapping.findForward("audit");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
