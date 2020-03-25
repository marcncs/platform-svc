package com.winsafe.drp.action.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppScannerDownload;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ScannerDownload;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.MakeCode;

public class DownloadUnitAction  extends Action {
	private AppBaseResource appBS = new AppBaseResource();
	private AppScannerDownload appDownload = new AppScannerDownload();
	private AppUsers appUsers = new AppUsers();
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		String username = request.getParameter("Username"); //登陆名
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		Users loginUsers = appUsers.getUsers(username);
		List<Map<String, String>> result = getCountUnit();
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, result
				,loginUsers.getUserid(),"采集器","IMEI:[" + scannerNo + "],单位下载",true);
		// 更新采集器下载数据的md5
		//根据用户名称查询用户信息
		Users users = appUsers.getUsers(username);
		updScannerDownloadMd5(users, scannerNo);
		
		return null;
	}
	/**
	 * 更新采集器md5的值
	 */
	private void updScannerDownloadMd5(Users users,String scannerNo) throws Exception{
		String baseDataMd5 = appDownload.getBaseDataMd5(users, scannerNo);
		ScannerDownload scannerDownload = appDownload.getByIMEI(scannerNo);
		if(scannerDownload == null){
			scannerDownload = new ScannerDownload();
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("scannerdownload", 0, ""));
			scannerDownload.setId(id);
			scannerDownload.setImei(scannerNo);
			scannerDownload.setDownloadMd5(baseDataMd5);
			appDownload.save(scannerDownload);
		}else {
			scannerDownload.setDownloadMd5(baseDataMd5);
			appDownload.update(scannerDownload);
		}
	}
	
	public List<Map<String, String>> getCountUnit() throws Exception{
		List<BaseResource> list = appBS.getIsUseBaseResource("CountUnit");
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for(BaseResource br : list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("unitID", br.getTagsubkey()+"");
			map.put("unitName", br.getTagsubvalue());
			result.add(map);
		}
		return result; 
	}
}
