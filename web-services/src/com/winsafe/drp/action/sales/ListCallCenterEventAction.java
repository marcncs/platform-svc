package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCallCenterEvent;
import com.winsafe.drp.dao.CallCenterEvent;
import com.winsafe.drp.dao.CallCenterEventForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListCallCenterEventAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 25;

		super.initdata(request);
		 
		try {

			AppCallCenterEvent appcc = new AppCallCenterEvent();
			String visitorgan = "";
//			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
//				visitorgan = "or c.makeorganid in("+users.getVisitorgan()+") ";
//			}
//			String Condition = " (c.userid="+userid+" "+visitorgan+" )";
			
			List list = appcc.getUserCallEventByMuid(userid);
			StringBuffer uid = new StringBuffer();
			for (int i=0; i<list.size(); i++ ){
				Integer s = (Integer)list.get(i);
				if ( i == 0 ){
					uid.append(s);
				}else{
					uid.append(",").append(s);
				}					
			}
			if ( !uid.toString().equals("") ){
				visitorgan = " or userid in("+uid.toString()+") ";
			}
			String Condition = " (userid="+userid + visitorgan +") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "CallCenterEvent" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" EventDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "CallNum","CalledNum","UserName","CustomerName");
			whereSql = whereSql + timeCondition + blur +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			

			
			 
			List pils = appcc.getCallCenterEvent(request, pagesize, whereSql);

			List cclist = new ArrayList();
			CallCenterEvent c = null;
			for (int i = 0; i < pils.size(); i++) {
				CallCenterEventForm cef = new CallCenterEventForm();
				c = (CallCenterEvent) pils.get(i);
				cef.setId(c.getId());				
				cef.setCallnum(c.getCallnum());
				cef.setCallednum(c.getCallednum());
				cef.setEventtype(c.getEventtype());
				cef.setEventdate(DateUtil.formatDateTime(c.getEventdate()));
				cef.setComputerid(c.getComputerid());
				cef.setSoundname(c.getSoundname());
				cef.setUserid(c.getUserid());
				cef.setUseridname(c.getUsername());
				cef.setOptype(c.getOptype());
				String soundfile = "javascript:void(0)";
				if ( !c.getComputerid().equals("") && c.getComputerid().length() >= 14){
					soundfile = "http://"+Internation.getStringByKeyPositionDB("CallCenter", 0)+"/"+
					c.getComputerid().substring(0,4)+"/"+
					c.getComputerid().substring(4,6)+"/"+
					c.getComputerid().substring(6,8)+"/"+
					c.getComputerid().substring(8,14)+"/"+
					c.getSoundname();
				}				
				cef.setSoundfile(soundfile);
				if ( c.getEventtype().intValue() == 0 ){
					cef.setCustomername(c.getCustomername());
				}else{
					cef.setCustomername(c.getUsername());
				}
				
				cclist.add(cef);
			}

			request.setAttribute("cclist", cclist);
			request.setAttribute("CID", request.getParameter("CID"));

			DBUserLog.addUserLog(userid, 12, "呼叫中心事件列表");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
