package com.winsafe.drp.server;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.hbm.util.DateUtil;

public class ChkUserTargetService {
	//主管检查
	public String dealChkUserTarget(HttpServletRequest request,String type) throws Exception{
		AppUserTarget apput = new AppUserTarget();
		String uid =request.getParameter("uid");
		String usefuldate = request.getParameter("usefuldate");
		String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate.toString(), "yyyy-MM"),"yyyyMM");
		String tid = request.getParameter("tid");
		boolean flag = false;
		if(tid!=null && !"".equals(tid)){
			flag = apput.chkUserTargetUpd(Integer.parseInt(uid), targetdate,Integer.parseInt(tid),type);
		}else{
			flag = apput.chkUserTarget(Integer.parseInt(uid), targetdate,type);
		}
		
		String result = "0";
		if(flag){
			result = "1";
		}
		return result;
	}
	//机构指标检查
	public String dealChkOrganTarget(HttpServletRequest request,String type) throws Exception{
		AppOrganTarget apput = new AppOrganTarget();
		String oid =request.getParameter("oid");
		String usefuldate = request.getParameter("usefuldate");
		String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate.toString(), "yyyy-MM"),"yyyyMM");
		String tid = request.getParameter("tid");
		boolean flag = false;
		if(tid!=null && !"".equals(tid)){
			flag = apput.chkOrganTargetUpd(oid, targetdate,Integer.parseInt(tid),type);
		}else{
			flag = apput.chkOrganTarget(oid, targetdate,type);
		}
		
		String result = "0";
		if(flag){
			result = "1";
		}
		
		return result;
	}
	//办事处、大区
	public String dealChkRegionTarget(HttpServletRequest request,String type) throws Exception{
		AppRegionTarget apprt = new AppRegionTarget();
		String regionid="";
		String usefuldate = request.getParameter("usefuldate");
		if("4".equals(type)){
			regionid = request.getParameter("bsrid");
		}else{
			regionid = request.getParameter("dqrid");
		}
		String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate.toString(), "yyyy-MM"),"yyyyMM");
		String tid = request.getParameter("tid");
		boolean flag = false;
		if(tid!=null && !"".equals(tid)){
			flag = apprt.chkRegionTargetUpd(Integer.parseInt(regionid), targetdate,Integer.parseInt(tid),type);
		}else{
			flag = apprt.chkRegionTarget(Integer.parseInt(regionid), targetdate,type);
		}
		
		String result = "0";
		if(flag){
			result = "1";
		}
		
		return result;
	}
}
