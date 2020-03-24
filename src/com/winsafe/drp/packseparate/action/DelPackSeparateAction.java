package com.winsafe.drp.packseparate.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.util.DBUserLog;

public class DelPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelPackSeparateAction.class);
	private AppPackSeparate aps = new AppPackSeparate();
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try
		{
			String psid = request.getParameter("ID");
			PackSeparate ps = aps.getPackSeparateById(psid);
			// 单据是否存在
			if(ps == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			//已复核的情况
			if (ps.getIsAudit() == 1)
			{
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			apsd.delPackSeparateDetailByPsid(psid);
			aps.delPackSeparateById(psid);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "编号：" + psid, ps);

			return mapping.findForward("success");
		}
		catch(Exception e)
		{
			logger.error("删除分包单据发生异常",e);
			throw e;
		}
	}
	
}
