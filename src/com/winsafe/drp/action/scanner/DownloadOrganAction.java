package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.util.CollectionUtils;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.ResponseUtil;

/**
 * Project:dupontdgm->Class:DownloadOrganAction.java
 * 下载出入库机构信息
 * Create Time 2012/10/30
 * @author WeiLi
 * @version 0.1
 */
@SuppressWarnings("unchecked")
public class DownloadOrganAction extends Action{
	private Logger logger = Logger.getLogger(DownloadOrganAction.class);
	
	private AppOrgan appOrgan = new AppOrgan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String type = request.getParameter("Type");
		String username = request.getParameter("Username");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		//type[1:管辖机构 2:业务往来机构 3:可转仓机构 4:产成品入库机构]
		
		List<Map<String, String>> list = null;
		if ("1".equals(type)) {
			//管辖机构
			list = getUserVisit(users, scannerNo);
		} else if ("2".equals(type)) {
			//业务往来机构
			list=getOrganVisitByUser(users);
		} else if ("3".equals(type)) {
			//可转仓机构 (管辖机构)
			//用户验证方式
			list = getUserVisit(users, scannerNo);
		} else if ("4".equals(type)) {
			//产成品入库机构
			
		} else if ("5".equals(type)) {
			//可退回机构(工厂,用于PD退货给工厂)
			list=getPlant();
			
		} else {
			//do nothing
		}    
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],机构下载",true);
		return null;
	}
	
	public List getUserVisit(Users users,String scannerNo) throws Exception{
		List<Map<String, String>> list = null;
		List userList = null;
		if(Constants.SCANNNER_VALIDATE_USER){
			userList =getUserVisitByUser(users);
		}
		//采集器IMEI验证方式
		List IMEIList = null;
		if(Constants.SCANNNER_VALIDATE_IMEI){
			IMEIList=getUserVisitByScanner(users,scannerNo);
		}
		//如果用户验证与采集器验证同时为true,则取交集,否则取并集
		if(Constants.SCANNNER_VALIDATE_USER && Constants.SCANNNER_VALIDATE_IMEI){
			
			Collection<Organ> collection = CollectionUtils.intersection(userList, IMEIList);
			list =  getJsonList(new ArrayList<Organ>(collection));
			
		}else {
			
			Collection<Organ> collection = CollectionUtils.union(userList, IMEIList);
			list =  getJsonList(new ArrayList<Organ>(collection));
			
		}
		return list;
	}
	/**
	 * 根据users查询得到UserVisit机构信息
	 * @param userVisit
	 * @return
	 * @throws Exception
	 */
	private List<Organ> getUserVisitByUser(Users users) throws Exception{
		//通过UserId查询UserVisit
		String condition = " where o.isrepeal=0 and  o.id in (select visitorgan from UserVisit as uv where  uv.userid=" + users.getUserid() + ") ";
		List<Organ> list = appOrgan.getOrgan(condition);
		return list;
	}
	/**
	 * 根据采集器查询UserVisit机构信息
	 * @param users
	 * @return
	 * @throws Exception
	 */
	private List<Organ> getUserVisitByScanner(Users users,String scannerNo) throws Exception{
		//通过UserId查询UserVisit
		String condition = " where o.isrepeal=0 " +
				" and o.id in (select o.id from Organ o, Warehouse w,ScannerWarehouse sw,Scanner s where o.id=w.makeorganid and w.id=sw.warehouseid and sw.scannerid =s.id and s.status=1 and s.scannerImeiN='" + scannerNo +"' ) " ;
		List<Organ> list = appOrgan.getOrgan(condition);
		return list;
	}
	
	/**
	 * 根据USER查询Warehouse表
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getOrganVisitByUser(Users users) throws Exception{
		//通过UserId查询UserVisit
		//20170707,只能下载已经审核通过的BKD/BKR
		String condition = " where o.isrepeal=0 and o.validatestatus = "+ValidateStatus.PASSED.getValue()+" and o.id in (select visitorgan from OrganVisit as ov where  ov.userid=" + users.getUserid() + ") ";
		List<Organ> list = appOrgan.getOrgan(condition);
		return getJsonList(list);
	}
	public List<Map<String, String>> getPlant(){
		String condition = " where o.isrepeal=0 and o.organType = 1 ";
		List<Organ> list = appOrgan.getOrgan(condition);
		return getJsonList(list);
	}
	
	/**
	 * 将organ转化为Map
	 */
	private List<Map<String, String>> getJsonList(List<Organ> list){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for(Organ organ : list){
			Map<String, String> data = new HashMap<String, String>();
			data.put("organID", organ.getId());
			data.put("organName", ESAPIUtil.decodeForHTML(organ.getOrganname()));
			data.put("customOrgID", organ.getOecode());
			result.add(data);
		}
		return result;
	}
}

