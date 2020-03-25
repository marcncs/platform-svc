package com.winsafe.drp.keyretailer.action.phone;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;


public class DelBillReceiveIdcodeAction extends BaseAction {
	private Logger logger = Logger.getLogger(DelBillReceiveIdcodeAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private AppIdcode appIdcode = new AppIdcode();
	private AppStockAlterMoveIdcode asami = new AppStockAlterMoveIdcode();
	
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String scannerno = request.getParameter("IMEI_number"); 
			String billNo = request.getParameter("billNo"); 
			String idcodes = request.getParameter("idcodes"); 
			// 判断用户是否存在
			Users loginUsers = appUsers.getUsers(username);
			
			if(loginUsers != null){
				String[] idcodeArray = idcodes.split(",");
				StringBuffer idcodeString = new StringBuffer();
				for(String idcode : idcodeArray) {
					idcodeString.append(",'"+idcode+"'");
				}
				if(idcodeString.length() > 0) {
					asami.delStockAlterMoveIdcodeByIdcodes(billNo,idcodeString.substring(1));
					appIdcode.updIsUseOutByIdcodes(idcodeString.substring(1), 1, 0); 
				}
			}
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, loginUsers.getUserid(),"APP","删除条码",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
