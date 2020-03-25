package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.ResponseUtil;

public class QueryIdcodeAction  extends Action{
	private Logger logger = Logger.getLogger(QueryIdcodeAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AppUsers au = new AppUsers();
		String barcode = request.getParameter("Barcode");
		String username = request.getParameter("username");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		if(users==null){
			return null;
		}

//		String boxIdcode=getBoxIdcode(barcode);
//		if(StringUtil.isEmpty(boxIdcode)){
//			ResponseUtil.writeReturnMsg(response, "1","没有查到对应的箱条码");
//		}
//		
//		ResponseUtil.writeReturnMsg(response, "0",boxIdcode);
//		
		StringBuilder outString=new StringBuilder();
		Set<String> set = getIdcode(barcode);
		for(String str:set){
			outString.append(str);
		}
		
		ResponseUtil.write(response, outString.toString());
		
		return null;
	}
	
	private Set<String> getIdcode(String idcode) throws Exception
	{
		Set<String> idcodeSet=new LinkedHashSet<String>();
		AppUploadProduceReport aupr = new AppUploadProduceReport();
		AppIdcode appIdcode = new AppIdcode();
		
		if(idcode!=null){
			idcode=idcode.trim();
			idcode=idcode.toUpperCase();
		}
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			return null;
		}
		
		//第1位：代表产品品牌(1为国内产品、2为国际等)；
		String f=idcode.substring(0,1);
		if("1".equals(f) || "3".equals(f)){
			//国内产品(散)

			UploadProduceReport upr = aupr.getUploadProduceReportByUnitNo(idcode);
			if(upr!=null){
				String boxIdcode= upr.getBoxCode();
				idcodeSet.add(boxIdcode+",箱;");
				List<UploadProduceReport> list=aupr.getUploadProduceReportByUnitnocode(boxIdcode);
				for(UploadProduceReport urp:list){
					idcodeSet.add(urp.getProCode()+",散;");
				}
			}
			idcodeSet.add(idcode+",散;");
			return idcodeSet;
		}else if("C".equals(f)){
			//国内产品(箱)
			idcodeSet.add(idcode+",箱;");
			List<UploadProduceReport> list=aupr.getUploadProduceReportByUnitnocode(idcode);
			for(UploadProduceReport urp:list){
				idcodeSet.add(urp.getProCode()+",散;");
			}
			return idcodeSet;
		}else if("2".equals(f)){
			//国际产品

			if(idcode.length()!=16){
				return idcodeSet;
			}

			String boxIdcode=idcode.substring(0,13)+"000";
			if(idcode.endsWith("000")){
				//(箱)
			}else{
				//(散)
			}
			idcodeSet.add(boxIdcode+",箱;");
			
			List<Idcode> list=appIdcode.queryLikeIdcode(idcode.substring(0,13));
			for(Idcode ic:list){
				if(!boxIdcode.equals(ic.getIdcode())){
					idcodeSet.add(ic.getIdcode()+",散;");
				}
			}
			
			return idcodeSet;
		}else{
			return idcodeSet;
		}
	}
	
	/**
	 * 根据散条码获取箱条码
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	private String getBoxIdcode(String idcode) throws Exception
	{
		if(idcode!=null){
			idcode=idcode.trim();
			idcode=idcode.toUpperCase();
		}
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			return null;
		}
		
		//第1位：代表产品品牌(1为国内产品、2为国际等)；
		String f=idcode.substring(0,1);
		if("1".equals(f)){
			//国内产品(散)

			AppUploadProduceReport aupr = new AppUploadProduceReport();
			UploadProduceReport upr = aupr.getUploadProduceReportByUnitNo(idcode);
			if(upr!=null){
				return upr.getBoxCode();
			}
			return null;
		}else if("C".equals(f)){
			//国内产品(箱)

			return null;
		}else if("2".equals(f)){
			//国际产品

			if(idcode.length()!=16){
				return null;
			}
			
			if(idcode.endsWith("000")){
				return null;
			}
			
			return idcode.substring(0,13)+"000";
		}else{
			return null;
		}
	}
	
	private String getIdcodeType(String idcode){
		if(idcode!=null){
			idcode=idcode.trim();
			idcode=idcode.toUpperCase();
		}
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			return "";
		}
		
		//第1位：代表产品品牌(1为国内产品、2为国际等)；
		String f=idcode.substring(0,1);
		if("1".equals(f)){
			//国内产品(散)

			return "散";
		}else if("C".equals(f)){
			//国内产品(箱)

			return "箱";
		}else if("2".equals(f)){
			//国际产品

			if(idcode.length()!=16){
				return "";
			}
			
			if(idcode.endsWith("000")){
				return "箱";
			}
			
			return "散";
		}else{
			return "";
		}
	}
}