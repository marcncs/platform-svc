package com.winsafe.drp.keyretailer.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.mapping.Array;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

public class QueryIdcodeUploadLog  extends BaseAction
{
	private Logger logger = Logger.getLogger(QueryIdcodeUploadLog.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AppIdcodeUpload aiu = new AppIdcodeUpload();
		AppOrgan apo = new AppOrgan();
		AppUsers appUser = new AppUsers();
		List<IdcodeUpload> list = new ArrayList<IdcodeUpload>();
		try
		{
			String billsort = request.getParameter("billSort");
			String username = request.getParameter("Username");
			
			String fromdate = request.getParameter("fromDate");
			String todate = request.getParameter("toDate");
			
			Users user = appUser.getUsers(username);
			
			//fromdate,todate没有填写，只能查当天的数据
			//只有fromdate，todate，只查那一天的数据
			if(StringUtil.isEmpty(fromdate) && StringUtil.isEmpty(todate)){
				fromdate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy-MM-dd");
				todate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy-MM-dd");
			}else if(StringUtil.isEmpty(fromdate) && !StringUtil.isEmpty(todate)){
				todate = fromdate;
			}else if(!StringUtil.isEmpty(fromdate) && StringUtil.isEmpty(todate)){
				fromdate = todate;
			}
			//查找数据
			if(!StringUtil.isEmpty(billsort)){
				String[] bsorts = billsort.split(",");
				for(String bsort : bsorts){
					List<IdcodeUpload> tmp = aiu.queryIdcodeUpload(bsort, 100, user.getUserid(), fromdate, todate); 
					list.addAll(tmp);
				}
			}else {
				list = aiu.queryIdcodeUpload(billsort, 100, user.getUserid(), fromdate, todate); 
			}
			List<Map<String, String>> result = new ArrayList<Map<String,String>>(); 
			for(IdcodeUpload iu : list)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("billNo", ""); 
				map.put("billSort", iu.getBillsort()+"");
				map.put("uploadDate", DateUtil.formatDateTime(iu.getMakedate()));
				map.put("successNumber", iu.getValinum()+"");
				map.put("failNumber", iu.getFailnum()+"");
				map.put("failFile", iu.getFailfilepath());
				if(iu.getWarnnum()==null) {
					map.put("warnNumber", "0");
				} else {
					map.put("warnNumber", iu.getWarnnum()+"");
				} 
				/*if(iu.getFailnum()!=null&&iu.getFailnum()==0 &&iu.getWarnnum()!=null&&iu.getWarnnum()==0) {
					map.put("failFile", "");
				} else {
					map.put("failFile", iu.getFailfilepath());
				}*/
				
				if(iu.getFromorganid()==null) {
					map.put("fromOrganId", "");
					map.put("fromOrganName", "");
				} else {
					map.put("fromOrganId", iu.getFromorganid());
					Organ o = apo.getOrganByID(iu.getFromorganid());
					map.put("fromOrganName", o==null?"":o.getOrganname());
				}
				
				if(iu.getToorganid()==null) {
					map.put("toOrganId", "");
					map.put("toOrganName", "");
				} else {
					map.put("toOrganId", iu.getToorganid());
					Organ o = apo.getOrganByID(iu.getToorganid());
					map.put("toOrganName", o==null?"":o.getOrganname());
				}
				
				result.add(map);
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, result);
		}
		catch (Exception e) 
		{
			logger.error("",e);
		}
		return null;
	}
	
}