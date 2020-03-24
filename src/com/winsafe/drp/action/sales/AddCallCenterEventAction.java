package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCallCenterEvent;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.CallCenterEvent;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddCallCenterEventAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String seatnum=""+userid;
			if ( userid < 10 ){
				seatnum = "00"+userid;
			}
			if ( userid > 10 && userid < 100 ) {
				seatnum = "0"+userid;
			}
			//主叫号码
			String callnum = request.getParameter("callnum");
			//被叫号码
			String callednum = request.getParameter("callednum");
			//事件类型  0来电 1去电
			Integer eventtype = Integer.valueOf(request.getParameter("et"));
			//呼叫中心操作类型
			String alarmtype = request.getParameter("at");
			//呼叫中心电脑编号
			String computerid = request.getParameter("cpid");
			String cpid2 = request.getParameter("cpid2");
			
			callnum = callnum.replace(",", "").trim();
			callednum = callednum.replace(",", "").trim();			
			CallCenterEvent c = new CallCenterEvent();
			c.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("call_center_event", 0, "")));
			c.setCallnum(callnum);
			c.setCallednum(callednum);
			c.setEventtype(eventtype);			
			c.setEventdate(DateUtil.getCurrentDate());
			c.setAlarmtype(alarmtype);
			c.setComputerid(computerid);
			c.setUserid(userid);
			c.setMakeorganid(users.getMakeorganid());
			c.setUsername(users.getRealname());
			c.setOptype(0);
			
			String soundname = "";
			Customer cus =null;
			AppCustomer ac = new AppCustomer();			
			if ( eventtype == 0 ){
				soundname= "B"+computerid.substring(8)+".wav";
				cus = ac.getCustomerByMobile(callnum);
				//seatnum = callednum;
			}else if ( eventtype == 1 ){
				soundname= "D1"+cpid2.substring(8)+".wav";
				cus = ac.getCustomerByMobile(callednum);
				//seatnum = callnum;
			}
			if ( cus != null ){
				c.setCustomername(cus.getCname());
				c.setCid(cus.getCid());
			}
			c.setSeatnum(seatnum);
			c.setSoundname(soundname);
			
			AppCallCenterEvent apcc = new AppCallCenterEvent();
			apcc.addCallCenterEvent(c);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 12, "新增呼叫中心事件,编号："+c.getId());
//			if ( eventtype == 0 ){	
//				return new ActionForward("/sales/toAddSaleOrderAction.do?telphone="+callnum);
//			}
			
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return null;
	}
}
