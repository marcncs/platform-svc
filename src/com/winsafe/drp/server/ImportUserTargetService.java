package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganTarget;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionTarget;
import com.winsafe.drp.dao.UserTarget;
import com.winsafe.drp.dao.Users;
import com.winsafe.hbm.entity.HibernateUtil;

public class ImportUserTargetService {
	public String errorMsg="";
	//主管
	public Integer[] dealImportUserTarget(FormFile usersfile,String type) throws Exception{
		AppUserTarget apput = new AppUserTarget();
		AppUsers appu = new AppUsers();
		List<String> dateList = new ArrayList<String>();
		Integer[] result = new Integer[2];
		result[0]=0;
		result[1]=0;
		
		Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
		Sheet sheet = wb.getSheet(0);
		int row = sheet.getRows();
		Cell[] cells = null;
		
		UserTarget ut = null;
		String usefuldate = null;
		Users u = null;
		Double importtarget = null;
		Double chmantarget = null;
		Double chbabytarget = null;
		
		//首先组织有效日期
		Cell[] dateCells = sheet.getRow(0);
		String dateVal = "";
		if(dateCells!=null && dateCells.length>2){
			for(int d=2;d<dateCells.length;d=d+3){
				dateVal = dateCells[d].getContents().substring(0,4) + dateCells[d].getContents().substring(5);
				dateList.add(dateVal);
			}
		}
		AppBaseResource appbr =  new AppBaseResource();
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(type));
		UserTarget douUt = null;
		
		for (int i = 2; i < row; i++) {
			
			try {
				
				//判断当前用户是否存在
				String loginname = sheet.getCell(0, i).getContents();
				if(loginname==null || "".equals(loginname)){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行：用户编号不能为空！";
					continue;
				}
				u = appu.getUsers(loginname);
				if(u==null){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行："+loginname+"用户不存在！";
					continue;
				}
				//组建数据
				cells = sheet.getRow(i);
				int cellindex = 0;
				if(cells!=null && cells.length>0){
					for(int j=2;j<cells.length&&j<dateCells.length;j=j+3){
						//判断当前主管是否存在目标信息
						cellindex = j;
						usefuldate = dateList.get((cellindex-2)/3);
						douUt = apput.getUserTarget(u.getUserid(), usefuldate,type);
						if(douUt!=null){
							apput.delUserTargetById(douUt.getId());
						}
						
						//插入数据
						ut =  new UserTarget();
						//进口粉指标
						importtarget = ((sheet.getCell(cellindex, i).getContents())==null||"".equals(sheet.getCell(cellindex, i).getContents()))?0
								:Double.parseDouble(sheet.getCell(cellindex, i).getContents());
						ut.setImporttarget(importtarget);
						//国产成人粉指标
						chmantarget = ((sheet.getCell((cellindex+1),i).getContents())==null||"".equals(sheet.getCell((cellindex+1),i).getContents()))?0
								:Double.parseDouble(sheet.getCell((cellindex+1),i).getContents());
						ut.setChmantarget(chmantarget);
						//国产婴儿粉指标
						chbabytarget = ((sheet.getCell((cellindex+2), i).getContents())==null||"".equals(sheet.getCell((cellindex+2), i).getContents()))?0
								:Double.parseDouble(sheet.getCell((cellindex+2), i).getContents());
						ut.setChbabytarget(chbabytarget);
						//合计
						double totaltarget = importtarget + chmantarget + chbabytarget;
						ut.setTotaltarget(totaltarget);
						ut.setUserid(u.getUserid());
						ut.setTargetdate(usefuldate);
						ut.setTargettype(type);
						if(br!=null){
							ut.setTargettypename(br.getTagsubvalue());
						}
						
						apput.insertUserTarget(ut);
						j=cellindex;
					}
				}
				
				result[1]++;
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				result[0]++;
				HibernateUtil.rollbackTransaction();
			}
		}
		wb.close();
		return result;
	}
	
	//机构
	public Integer[] dealImportOrganTarget(FormFile usersfile,String type) throws Exception{
		AppOrganTarget appot = new AppOrganTarget();
		AppOrgan appo = new AppOrgan();
		List<String> dateList = new ArrayList<String>();
		Integer[] result = new Integer[2];
		result[0]=0;
		result[1]=0;
		
		Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
		Sheet sheet = wb.getSheet(0);
		int row = sheet.getRows();
		Cell[] cells = null;
		
		OrganTarget ot = null;
		String usefuldate = null;
		Organ o = null;
		List<Organ> listOrgan = null;
		
		Double importtarget = null;
		Double chmantarget = null;
		Double chbabytarget = null;
		
		//首先组织有效日期
		Cell[] dateCells = sheet.getRow(0);
		String dateVal = "";
		if(dateCells!=null && dateCells.length>2){
			for(int d=2;d<dateCells.length;d=d+3){
				dateVal = dateCells[d].getContents().substring(0,4) + dateCells[d].getContents().substring(5);
				dateList.add(dateVal);
			}
		}
		AppBaseResource appbr =  new AppBaseResource();
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(type));
		OrganTarget douOt = null;
		String typeCondition = "";
		String typeName = "";
		//"2"为经销商（经销商organtype为2）,"3"为网点（网点organtype为0）
		if("2".equals(type)){
			typeCondition += " and organtype=2";
			typeName = "经销商";
		}else{
			typeCondition += " and organtype=0";
			typeName = "网点";
		}
		for (int i = 2; i < row; i++) {
			
			try {
				//判断当前用户是否存在
				String oecode = sheet.getCell(0, i).getContents();
				//o = appo.getByOecode(oecode);
				if(oecode==null || "".equals(oecode)){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行：机构编号不能为空！";
					continue;
				}
				listOrgan = appo.getAllOrganByOecodeAndOrganType(oecode, typeCondition);
				if(listOrgan==null || listOrgan.isEmpty()){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行："+ typeName + oecode+"机构不存在！";
					continue;
				}
				//组建数据
				cells = sheet.getRow(i);
				int cellindex = 0;
				if(cells!=null && cells.length>0){
					for(int j=2;j<cells.length&&j<dateCells.length;j=j+3){
						//判断当前主管是否存在目标信息
						cellindex = j;
						usefuldate = dateList.get((cellindex-2)/3);
						
						//循环取出每一个organ
						for(int k=0;k<listOrgan.size();k++){
							o = listOrgan.get(k);
							douOt = appot.getOrganTarget(o.getId(), usefuldate,type);
							if(douOt!=null){
								appot.delOrganTargetById(douOt.getId());
							}
							
							//插入数据
							ot =  new OrganTarget();
							//进口粉指标
							importtarget = ((sheet.getCell(cellindex, i).getContents())==null||"".equals(sheet.getCell(cellindex, i).getContents()))?0
									:Double.parseDouble(sheet.getCell(cellindex, i).getContents());
							ot.setImporttarget(importtarget);
							//国产成人粉指标
							chmantarget = ((sheet.getCell((cellindex+1),i).getContents())==null||"".equals(sheet.getCell((cellindex+1),i).getContents()))?0
									:Double.parseDouble(sheet.getCell((cellindex+1),i).getContents());
							ot.setChmantarget(chmantarget);
							//国产婴儿粉指标
							chbabytarget = ((sheet.getCell((cellindex+2), i).getContents())==null||"".equals(sheet.getCell((cellindex+2), i).getContents()))?0
									:Double.parseDouble(sheet.getCell((cellindex+2), i).getContents());
							ot.setChbabytarget(chbabytarget);
							//合计
							double totaltarget = importtarget + chmantarget + chbabytarget;
							ot.setTotaltarget(totaltarget);
							
							ot.setOrganid(o.getId());
							ot.setTargetdate(usefuldate);
							ot.setTargettype(type);
							if(br!=null){
								ot.setTargettypename(br.getTagsubvalue());
							}
							appot.insertOrganTarget(ot);
						}
						
						j=cellindex;
					}
				}
				result[1]++;
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				result[0]++;
				HibernateUtil.rollbackTransaction();
			}
		}
		wb.close();
		return result;
	}
	//办事处、大区
	//机构
	public Integer[] dealImportRegionTarget(FormFile usersfile,String type) throws Exception{
		AppRegionTarget apprt = new AppRegionTarget();
		AppRegion appr = new AppRegion();
		List<String> dateList = new ArrayList<String>();
		Integer[] result = new Integer[2];
		result[0]=0;
		result[1]=0;
		
		Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
		Sheet sheet = wb.getSheet(0);
		int row = sheet.getRows();
		Cell[] cells = null;
		
		RegionTarget rt = null;
		String usefuldate = null;
		Region r = null;
		Double importtarget = null;
		Double chmantarget = null;
		Double chbabytarget = null;
		
		//首先组织有效日期
		Cell[] dateCells = sheet.getRow(0);
		String dateVal = "";
		if(dateCells!=null && dateCells.length>2){
			for(int d=2;d<dateCells.length;d=d+3){
				dateVal = dateCells[d].getContents().substring(0,4) + dateCells[d].getContents().substring(5);
				dateList.add(dateVal);
			}
		}
		AppBaseResource appbr =  new AppBaseResource();
		//查询targettypename
		BaseResource br = appbr.getBaseResourceValue("TargetType",Integer.parseInt(type));
		RegionTarget douRt = null;
		String typeCondition = "";
		String typeName = "";
		//"4"为办事处（办事处typeid为2）,"5"为大区（大区typeid为1）
		if("4".equals(type)){
			typeCondition += " and typeid='2'";
			typeName = "办事处";
		}else{
			typeCondition += " and typeid='1'";
			typeName = "大区";
		}
		for (int i = 2; i < row; i++) {
			
			try {
				
				//判断当前用户是否存在
				String regioncode = sheet.getCell(0, i).getContents();
				if(regioncode==null || "".equals(regioncode)){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行：区域编号不能为空！";
					continue;
				}
				r = appr.getRegionByCodeAndTypeId(regioncode, typeCondition);
				if(r==null){
					result[0]++;
					errorMsg += "<br/>第[" + (i + 1) + "]行："+ typeName +regioncode+"区域不存在！";
					continue;
				}
				//组建数据
				cells = sheet.getRow(i);
				int cellindex = 0;
				if(cells!=null && cells.length>0){
					for(int j=2;j<cells.length&&j<dateCells.length;j=j+3){
						//判断当前主管是否存在目标信息
						cellindex = j;
						usefuldate = dateList.get((cellindex-2)/3);
						douRt = apprt.getRegionTarget(Integer.valueOf(r.getId()+""), usefuldate,type);
						if(douRt!=null){
							apprt.delRegionTargetById(douRt.getId());
						}
						
						//插入数据
						rt =  new RegionTarget();
						//进口粉指标
						importtarget = ((sheet.getCell(cellindex, i).getContents())==null||"".equals(sheet.getCell(cellindex, i).getContents()))?0
								:Double.parseDouble(sheet.getCell(cellindex, i).getContents());
						rt.setImporttarget(importtarget);
						//国产成人粉指标
						chmantarget = ((sheet.getCell((cellindex+1),i).getContents())==null||"".equals(sheet.getCell((cellindex+1),i).getContents()))?0
								:Double.parseDouble(sheet.getCell((cellindex+1),i).getContents());
						rt.setChmantarget(chmantarget);
						//国产婴儿粉指标
						chbabytarget = ((sheet.getCell((cellindex+2), i).getContents())==null||"".equals(sheet.getCell((cellindex+2), i).getContents()))?0
								:Double.parseDouble(sheet.getCell((cellindex+2), i).getContents());
						rt.setChbabytarget(chbabytarget);
						//合计
						double totaltarget = importtarget + chmantarget + chbabytarget;
						rt.setTotaltarget(totaltarget);
						
						rt.setRegionid(Integer.valueOf(r.getId()+""));
						rt.setTargetdate(usefuldate);
						rt.setTargettype(type);
						if(br!=null){
							rt.setTargettypename(br.getTagsubvalue());
						}
						apprt.insertRegionTarget(rt);
						j=cellindex;
					}
				}
				result[1]++;
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				result[0]++;
				HibernateUtil.rollbackTransaction();
			}
		}
		wb.close();
		return result;
	}
}
