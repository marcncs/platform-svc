package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganTarget;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionTarget;
import com.winsafe.drp.dao.UserTarget;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class UserTargetService {
	//主管销售指标、主管有效网点指标
	@SuppressWarnings("unchecked")
	public List dealUser(HttpServletRequest request,String type) throws Exception{
		 AppUserTarget appUt=new AppUserTarget();
	     int pagesize = 10;
	     List<UserTarget> utList = null;
	     try {
	    	 String condition = " where u.userid=ut.userid and ut.targettype='" + type + "' and ";
            Map map=new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
           
            String blur = DbUtil.getOrBlur(map, tmpMap, "RealName", "loginname"); 
           
            String whereSql = condition + blur ; 
            whereSql = DbUtil.getWhereSql(whereSql); 
           
            //查询
            List list = appUt.getUserTargetList(request, pagesize, whereSql);
            utList = new ArrayList<UserTarget>();
            if(list!=null && !list.isEmpty()){
            	Object[] oo = null;
            	UserTarget ut = null;
            	Users u = null;
            	for(int i=0;i<list.size();i++){
            		oo = (Object[]) list.get(i);
            		u = (Users) oo[0];
            		ut = (UserTarget) oo[1];
            		if(u!=null){
            			//存放对象编号
            			ut.setObjcode(u.getLoginname());
            			//存放对象名称
            			ut.setObjname(u.getRealname());
            		}
            		if(ut.getTargetdate()!=null)
            			//存放有效日期
            			ut.setUsefuldate(ut.getTargetdate().substring(0, 4) + "-" + ut.getTargetdate().substring(4));
            		utList.add(ut);
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return utList;
	}
	
	//经销商指标、网点指标
	@SuppressWarnings("unchecked")
	public List dealOrgan(HttpServletRequest request,String type) throws Exception{
		 AppOrganTarget appOt=new AppOrganTarget();
	     int pagesize = 10;

	     List<OrganTarget> otList = null;
	     try {
	        	String condition = " where o.id=ot.organid and ot.targettype='" + type + "' and ";
	            Map map=new HashMap(request.getParameterMap());
	            Map tmpMap = EntityManager.scatterMap(map);
	           
	            String blur = DbUtil.getOrBlur(map, tmpMap, "organname","oecode"); 
	          
	            String whereSql = condition + blur ; 
	            whereSql = DbUtil.getWhereSql(whereSql); 
	           
	            
	            List list = appOt.getOrganTargetList(request, pagesize, whereSql);
	            otList = new ArrayList<OrganTarget>();
	            if(list!=null && !list.isEmpty()){
	            	Object[] oo = null;
	            	OrganTarget ot = null;
	            	Organ o = null;
	            	for(int i=0;i<list.size();i++){
	            		oo = (Object[]) list.get(i);
	            		o = (Organ) oo[0];
	            		ot = (OrganTarget) oo[1];
	            		if(o!=null){
	            			//存放对象编号
	            			ot.setObjcode(o.getOecode());
	            			//存放对象名称
	            			ot.setObjname(o.getOrganname());
	            		}
	            		if(ot.getTargetdate()!=null)
	            			//存放有效日期
	            			ot.setUsefuldate(ot.getTargetdate().substring(0, 4) + "-" + ot.getTargetdate().substring(4));
	            		otList.add(ot);
	            	}
	            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return otList;
	}
	
	//办事处指标、大区指标
	@SuppressWarnings("unchecked")
	public List dealRegion(HttpServletRequest request,String type) throws Exception{
		AppRegionTarget apprt = new AppRegionTarget();
		 int pagesize = 10;
		 List<RegionTarget> rtList = null;
		 try {
	        	String condition = " where r.id=rt.regionid and rt.targettype='" + type + "' and ";
	            Map map=new HashMap(request.getParameterMap());
	            Map tmpMap = EntityManager.scatterMap(map);
	           
	            String blur = DbUtil.getOrBlur(map, tmpMap, "sortname","r.regioncode"); 
	          
	            String whereSql = condition + blur ; 
	            whereSql = DbUtil.getWhereSql(whereSql); 
	           
	            
	            List list = apprt.getRegionTargetList(request, pagesize, whereSql);
	            rtList = new ArrayList<RegionTarget>();
	            if(list!=null && !list.isEmpty()){
	            	Object[] oo = null;
	            	RegionTarget rt = null;
	            	Region r = null;
	            	for(int i=0;i<list.size();i++){
	            		oo = (Object[]) list.get(i);
	            		r = (Region) oo[0];
	            		rt = (RegionTarget) oo[1];
	            		if(r!=null){
	            			//存放对象编号
	            			rt.setObjcode(r.getRegioncode());
	            			//存放对象名称
	            			rt.setObjname(r.getSortname());
	            		}
	            		if(rt.getTargetdate()!=null)
	            			//存放有效日期
	            			rt.setUsefuldate(rt.getTargetdate().substring(0, 4) + "-" + rt.getTargetdate().substring(4));
	            		rtList.add(rt);
	            	}
	            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtList;
		
	}
}
