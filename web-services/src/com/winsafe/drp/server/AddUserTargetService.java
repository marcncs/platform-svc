package com.winsafe.drp.server;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.OrganTarget;
import com.winsafe.drp.dao.RegionTarget;
import com.winsafe.drp.dao.UserTarget;
import com.winsafe.hbm.util.DateUtil;

public class AddUserTargetService {
	//主管新增
	public void dealAddUserTarget(HttpServletRequest request,String targetType) throws Exception{
		AppBaseResource appbr =  new AppBaseResource();
		UserTarget ut = new UserTarget();
		//用户编号
		ut.setUserid(Integer.parseInt(request.getParameter("uid")));
		//有效日期
		String usefuldate = request.getParameter("usefuldate");
		String targetdate = DateUtil.formatDate(DateUtil.StringToDate(usefuldate,"yyyy-MM"),"yyyyMM");
		ut.setTargetdate(targetdate);
		//进口粉指标
		double importtarget = (request.getParameter("importtarget")==null||"".equals(request.getParameter("importtarget")))?0
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
		
		ut.setTargettype(targetType);
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(targetType));
		if(br!=null){
			ut.setTargettypename(br.getTagsubvalue());
		}
		
		AppUserTarget apput = new AppUserTarget();
		apput.insertUserTarget(ut);
	}
	//经销商、网点新增
	public void dealAddOrganTarget(HttpServletRequest request,String targetType) throws Exception{
		AppBaseResource appbr =  new AppBaseResource();
		OrganTarget ot = new OrganTarget();
		//机构编号
		ot.setOrganid(request.getParameter("oid"));
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
		
		ot.setTargettype(targetType);
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(targetType));
		if(br!=null){
			ot.setTargettypename(br.getTagsubvalue());
		}
		
		AppOrganTarget appot = new AppOrganTarget();
		appot.insertOrganTarget(ot);
	}
	//办事处、大区新增
	public void dealAddRegionTarget(HttpServletRequest request,String targetType) throws Exception{
		AppBaseResource appbr =  new AppBaseResource();
		RegionTarget rt = new RegionTarget();
		//机构编号
		//办事处
		if("4".equals(targetType)){
			rt.setRegionid(Integer.parseInt(request.getParameter("bsrid")));
		}
		//大区
		else{
			rt.setRegionid(Integer.parseInt(request.getParameter("dqrid")));
		}
		
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
		
		rt.setTargettype(targetType);
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(targetType));
		if(br!=null){
			rt.setTargettypename(br.getTagsubvalue());
		}
		
		AppRegionTarget appot = new AppRegionTarget();
		appot.insertRegionTarget(rt);
	}
}
