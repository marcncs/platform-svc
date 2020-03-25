package com.winsafe.drp.action.users;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppRegionUsers;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.RegionUsers;
import com.winsafe.drp.dao.UsersUploadForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.MakeCode;

public class ImportOrganRegionAction extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int CCount = 0, ECount = 0;
		String errorMsg = "";
		long start=System.currentTimeMillis();
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile usersfile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (usersfile != null && !usersfile.equals("")) {
				if (usersfile.getContentType() != null) {
					if (usersfile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(usersfile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				
				 List<Organ>  nateList=new ArrayList<Organ>();
				 List<Olinkman>  olinkedmanList=new ArrayList<Olinkman>();
				 List<Organ>  agencyList=new  ArrayList<Organ>();
				 
				AppOrgan apporgan = new AppOrgan();
				AppRegion appregion=new AppRegion();
				AppRegionUsers appregionusers=new AppRegionUsers();
				AppOlinkMan appolinnkman=new AppOlinkMan();
				
				Region bigRe=null;
				Region officeRe=null;
				for (int i = 2; i < row; i++) {
					Organ organNate=null;
					Organ organagency=null;
					Olinkman  olinkman=null;
					
					   //主管id信息
					   String userid=null;
					   //大区信息
					   String bigRegionId=null;
					   String bigRegionName=null;
					   //办事处信息
					   Long officeId=null;
					   String officeName=null;
					   
					   //解析大区信息
					String bigregion=sheet.getCell(0, i).getContents();
					if(bigregion==null || bigregion.equals("")){
						errorMsg += "<br/>第[" + (i + 1) + "]行：大区信息不能为空";
						ECount++;
						continue;
					}
					   //解析办事处
					String officeregion=sheet.getCell(1, i).getContents();
					if(officeregion==null || officeregion.equals("")){
						errorMsg += "<br/>第[" + (i + 1) + "]行：办事处信息不能为空";
						ECount++;
						continue;
					}
					bigRe=appregion.getRegionBySortNameBigRegion(bigregion);
					officeRe=appregion.getRegionBySortName(officeregion);
					
					if(bigRe==null){
						errorMsg += "<br/>第[" + (i + 1) + "]行：该大区在区域信息中未配置!请核查";
						ECount++;
						continue;
					}
					if(officeRe==null){
						errorMsg += "<br/>第[" + (i + 1) + "]行：该办事处在区域信息中未配置!请核查";
						ECount++;
						continue;
					}
						   //大区信息
					bigRegionId=String.valueOf(bigRe.getId());
					bigRegionName=bigRe.getSortname();
					
						  //办事处信息
					officeId=officeRe.getId();
					officeName=officeRe.getSortname();
					
					     //经销商编号
					   String  agencyid=sheet.getCell(2, i).getContents();
						if (agencyid == null || "".equals(agencyid)) {
							errorMsg += "<br/>第[" + (i + 1) + "]行：经销商编号不能为空!";
							ECount++;
							continue;
						}
						//经销商名称
					   String  agencyname=sheet.getCell(3, i).getContents();
//						if (agencyname == null || "".equals(agencyname)) {
//							errorMsg += "<br/>第[" + (i + 1) + "]行：经销商名称不能为空！";
//							ECount++;
//							continue;
//						}
					  // 网点编号
					String natenodeid = sheet.getCell(4, i).getContents();
					if (natenodeid == null || "".equals(natenodeid)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行：网点编号不能为空!";
						ECount++;
						continue;
					}
					       //网点名称
					String natenodename = sheet.getCell(5, i).getContents();
					
//					if (natenodename == null || "".equals(natenodename)) {
//						errorMsg += "<br/>第[" + (i + 1) + "]行：网点名称不能为空！";
//						ECount++;
//						continue;
//					}
					//-----------验证网点是否存在---------------------
				 //	organNate= apporgan.getByOecode(natenodeid);
					
					 List  nList=apporgan.getAllOrganByOecode(natenodeid);
				    if(nList==null || nList.isEmpty() ){
				    	errorMsg += "<br/>第[" + (i + 1) + "]行：网点信息不存在!";
						ECount++;
						continue;
				    }
				    
				  //-----------验证经销商是否存在---------------------
					List  agList=apporgan.getAllOrganByOecode(agencyid);
				    if(agList==null || agList.isEmpty()){
				    	errorMsg += "<br/>第[" + (i + 1) + "]行：经销商信息不存在!";
						ECount++;
						continue;
				    }
				    //地址
				    String adress=sheet.getCell(6, i).getContents();
				    //联系人
				    String linkedman=sheet.getCell(7, i).getContents();
				    if(linkedman==null|| linkedman.length()==0){
				    	linkedman="默认联系人";
				    }
				   // 联系电话
				    String telephone =sheet.getCell(8, i).getContents();
				    //主管编号
				    String userlogin= sheet.getCell(9, i).getContents();
				    //主管姓名
				    String username= sheet.getCell(10, i).getContents();
				    
				    RegionUsers  ru=  appregionusers.getRegionUsersByUserLogin(userlogin);
				    if(ru!=null){
				    	   userid=ru.getUserid();
				    	   if(ru.getRid()!=officeId){
				    			errorMsg += "<br/>第[" + (i + 1) + "]行：主管的区域信息与页面不一致!";
								ECount++;
								continue;
                           }
				    }
				    if(ru==null ){
				    	errorMsg += "<br/>第[" + (i + 1) + "]行：主管信息有误 !请核查";
       					ECount++;
						continue;
				    }
				    //--------------------整理网点信息------------------------------------
				  
				for(int j=0;j<nList.size();j++){
					    organNate=(Organ) nList.get(j);
					
					    organNate.setSalemanId(userid);
					    organNate.setBigRegionId(Integer.valueOf(bigRegionId));
					    organNate.setBigRegionName(bigRegionName);
					    organNate.setOfficeId(officeId.intValue());
					    organNate.setOfficeName(officeName);
     				 	//apporgan.updOrgan(organNate);
					    nateList.add(organNate);
					    
					    //--------------------整理联系人信息---------------------------------
					    appolinnkman.delOlinkmanByCid(organNate.getId());
					 
					    olinkman=new Olinkman();
					    olinkman.setId(Integer.valueOf(MakeCode .getExcIDByRandomTableName("olinkman", 0, "")));
					    olinkman.setCid(organNate.getId());
					    olinkman.setName(linkedman);
					    olinkman.setSex(2); //性别默认值
					    olinkman.setOfficetel(telephone);
					    olinkman.setAddr(adress);
					    olinkman.setIsmain(1);
					    
					    olinkedmanList.add(olinkman);
					    
					    organNate=null;
				}
			  	    //--------------------整理经销商信息------------------------------------//  
				    
				    for(int z=0;z<agList.size();z++){
				    	organagency=(Organ) agList.get(z);
				    	
				    	organagency.setBigRegionId(Integer.valueOf(bigRegionId));
						organagency.setBigRegionName(bigRegionName);
						organagency.setOfficeId(officeId.intValue());
						organagency.setOfficeName(officeName);
					  //  apporgan.updOrgan(organagency);
						agencyList.add(organagency);
						organagency=null;
				    }
					CCount++;
				}
				
			//--------------jdbc批量更新网点操作--------------------------------
				String sqlnate=null ;
				StringBuffer natesbff=new StringBuffer();
				 for(int i=0;i<nateList.size();i++){
					 Organ o=(Organ) nateList.get(i);
					 sqlnate="update organ  set  salemanid='"+o.getSalemanId()+"', bigregionid='"+o.getBigRegionId()+"', bigregionname='"+o.getBigRegionName()+"', officeid='"+o.getOfficeId()+"' ,officename='"+o.getOfficeName()+"'  where oecode='"+o.getOecode()+"' ;" ;
					 natesbff.append(sqlnate);
					 sqlnate=null;
					//每5000条与数据库交互一次
						if(i % Constants.DB_BULK_SIZE == 0){
							 apporgan.updOrganByjdbc(natesbff.toString());
						}
				 }
				 if(natesbff.length() >0){
					 apporgan.updOrganByjdbc(natesbff.toString());
				 }
			//--------------jdbc批量更新联系人操作--------------------------------
					String sqlolinkedman=null ;
					StringBuffer olinkedmansbff=new StringBuffer();
					 for(int i=0;i<olinkedmanList.size();i++){
						 Olinkman ol=(Olinkman) olinkedmanList.get(i);
						 sqlolinkedman=" insert  into olinkman(id,cid,name,sex,officetel,addr,ismain) (select max(id)+1, '"+ol.getCid()+"',  '"+ol.getName()+"' ,2, '"+ol.getOfficetel()+"' , '"+ol.getAddr()+"', 1 from olinkman); " ;
						 olinkedmansbff.append(sqlolinkedman);
						 sqlolinkedman=null;
						//每5000条与数据库交互一次
							if(i % Constants.DB_BULK_SIZE == 0){
								appolinnkman.updOlinkmanByjdbc(olinkedmansbff.toString());
							}
					 }
					 if(olinkedmansbff.length()>0){
						 appolinnkman.updOlinkmanByjdbc(olinkedmansbff.toString());
					  }
		          //--------------jdbc批量更新经销商操作--------------------------------
						String sqlagency=null ;
						StringBuffer agencysbff=new StringBuffer();
						 for(int i=0;i<agencyList.size();i++){
							 Organ agency=(Organ) agencyList.get(i);
							 sqlnate="update organ  set  bigregionid='"+agency.getBigRegionId()+"', bigregionname='"+agency.getBigRegionName()+"', officeid='"+agency.getOfficeId()+"' ,officename='"+agency.getOfficeName()+"'  where oecode='"+agency.getOecode()+"' ;" ;
							 natesbff.append(sqlnate);
							 sqlnate=null;
								//每5000条与数据库交互一次
								if(i % Constants.DB_BULK_SIZE == 0){
									 apporgan.updOrganByjdbc(agencysbff.toString());
								}
						 }
						 if(agencysbff.length() >0){
							apporgan.updOrganByjdbc(agencysbff.toString()); 
						 }
						 
				wb.close();
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			
			long end=System.currentTimeMillis();
			System.out.println(end-start);
			System.out.println((end-start)/(1000*60)+"分钟");
			
			
			request.setAttribute("result", "上传用户资料完成,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!" + errorMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}

	}

}
