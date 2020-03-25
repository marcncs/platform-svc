package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.dao.OrganTarget;
import com.winsafe.drp.dao.RegionTarget;
import com.winsafe.drp.dao.UserTarget;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class UpdUserTargetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			//编号
			String tid = request.getParameter("tid");
			String targettype = request.getParameter("TargetType");
			//主管销售指标、主管有效网点指标信息
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)||"1".equals(targettype)){
        		AppUserTarget apput = new AppUserTarget();
        		UserTarget ut = null;
        		ut = apput.getUserTargetById(Integer.parseInt(tid));
    			if(ut!=null){
    				//有效日期
    				String usefuldate = request.getParameter("usefuldate");
    				String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate,"yyyy-MM"),"yyyyMM");
    				ut.setTargetdate(targetdate);
    				//进口粉指标
    				double importtarget = (request.getParameter("importtarget")==null || "".equals(request.getParameter("importtarget")))?0
    						:Double.parseDouble(request.getParameter("importtarget"));
    				ut.setImporttarget(importtarget);
    				//国产成人粉指标
    				double chmantarget = (request.getParameter("chmantarget")==null || "".equals(request.getParameter("chmantarget")))?0
    						:Double.parseDouble(request.getParameter("chmantarget"));
    				ut.setChmantarget(chmantarget);
    				//国产婴儿粉指标
    				double chbabytarget = (request.getParameter("chbabytarget")==null || "".equals(request.getParameter("chbabytarget")))?0
    						:Double.parseDouble(request.getParameter("chbabytarget"));
    				ut.setChbabytarget(chbabytarget);
    				
    				//合计
    				double totaltarget = importtarget + chmantarget + chbabytarget;
    				ut.setTotaltarget(totaltarget);
    			}
    			apput.updUserTarget(ut);
        	}
        	//经销商指标、网点指标检查
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		AppOrganTarget appot = new AppOrganTarget();
        		OrganTarget ot = appot.getOrganTargetById(Integer.parseInt(tid));
    			if(ot!=null){
    				//有效日期
    				String usefuldate = request.getParameter("usefuldate");
    				String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate,"yyyy-MM"),"yyyyMM");
    				ot.setTargetdate(targetdate);
    				//进口粉指标
    				double importtarget = (request.getParameter("importtarget")==null || "".equals(request.getParameter("importtarget")))?0
    						:Double.parseDouble(request.getParameter("importtarget"));
    				ot.setImporttarget(importtarget);
    				//国产成人粉指标
    				double chmantarget = (request.getParameter("chmantarget")==null || "".equals(request.getParameter("chmantarget")))?0
    						:Double.parseDouble(request.getParameter("chmantarget"));
    				ot.setChmantarget(chmantarget);
    				//国产婴儿粉指标
    				double chbabytarget = (request.getParameter("chbabytarget")==null || "".equals(request.getParameter("chbabytarget")))?0
    						:Double.parseDouble(request.getParameter("chbabytarget"));
    				ot.setChbabytarget(chbabytarget);
    				
    				//合计
    				double totaltarget = importtarget + chmantarget + chbabytarget;
    				ot.setTotaltarget(totaltarget);
    			}
    			appot.updOrganTarget(ot);
        	}
        	//办事处、大区
        	else if("4".equals(targettype)||"5".equals(targettype)){
        		AppRegionTarget apprt = new AppRegionTarget();
        		RegionTarget rt = apprt.getRegionTargetById(Integer.parseInt(tid));
    			if(rt!=null){
    				//有效日期
    				String usefuldate = request.getParameter("usefuldate");
    				String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate,"yyyy-MM"),"yyyyMM");
    				rt.setTargetdate(targetdate);
    				//进口粉指标
    				double importtarget = (request.getParameter("importtarget")==null || "".equals(request.getParameter("importtarget")))?0
    						:Double.parseDouble(request.getParameter("importtarget"));
    				rt.setImporttarget(importtarget);
    				//国产成人粉指标
    				double chmantarget = (request.getParameter("chmantarget")==null || "".equals(request.getParameter("chmantarget")))?0
    						:Double.parseDouble(request.getParameter("chmantarget"));
    				rt.setChmantarget(chmantarget);
    				//国产婴儿粉指标
    				double chbabytarget = (request.getParameter("chbabytarget")==null || "".equals(request.getParameter("chbabytarget")))?0
    						:Double.parseDouble(request.getParameter("chbabytarget"));
    				rt.setChbabytarget(chbabytarget);
    				
    				//合计
    				double totaltarget = importtarget + chmantarget + chbabytarget;
    				rt.setTotaltarget(totaltarget);
    			}
    			apprt.updRegionTarget(rt);
        	}
        	
			DBUserLog.addUserLog( 11, "对象目标设置>>对象主管目标");
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
		}

		return mapping.getInputForward();
	}
}
