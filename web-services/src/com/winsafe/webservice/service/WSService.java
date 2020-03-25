package com.winsafe.webservice.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.winsafe.drp.action.assistant.FwIdcodeService;
import com.winsafe.drp.action.phone.QueryFWIdcodeAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.XmlUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.webservice.client.ldap.LDAPServices;
import com.winsafe.webservice.pojo.QueryFWIdcodeResponse;
import com.winsafe.webservice.pojo.Result;

public class WSService {
	private Logger logger = Logger.getLogger(QueryFWIdcodeAction.class);
	
	public String queryFWIdcode(String fwidcode, String ipString, int queryMode) {
		boolean showResult = false;
		QueryFWIdcodeResponse result = new QueryFWIdcodeResponse();
		try {
			FwIdcodeService fis = new FwIdcodeService();
//			String ipString = aq.getIpAddr(request);
			Map<String, Object> queryMap = fis.fwIdcodeQuery(fwidcode.trim().toUpperCase(), queryMode, showResult, null, ipString);
			PrimaryCode pc = null;
			if (queryMap.get("primaryCode") != null) {
				pc = (PrimaryCode) queryMap.get("primaryCode");
			}

			if (pc != null && pc.getId() != null) {
				String fwnidcode = fis.getFwidcode(fwidcode.trim());
				result = getMapDate(queryMap, fwnidcode);
				result.setReturnCode(Constants.CODE_SUCCESS);
				result.setReturnMsg(Constants.CODE_SUCCESS_MSG);
//				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, null, "手机", "IMEI:[" + imeinumber + "]", false);
			} else {
				result = getMapDate(queryMap, fwidcode.trim());
				result.setReturnCode(Constants.CODE_NO_DATA);
				result.setReturnMsg(Constants.CODE_NO_DATA_MSG_SEC);
//				ResponseUtil.writeJsonMsg(response, Constants.CODE_NO_DATA, Constants.CODE_NO_DATA_MSG_SEC, list, null, "手机", "IMEI:[" + imeinumber + "]", false);
			}
		} catch (Exception e) {
			result.setReturnCode(Constants.CODE_LOGIN_FAIL);
			result.setReturnCode("系统异常");
			logger.error("", e);
		}
		return XmlUtil.objectToXml(QueryFWIdcodeResponse.class, result);
	}
	
	/**
	 * 转化数据为Map形式
	 */
	private QueryFWIdcodeResponse getMapDate(Map<String, Object> queryMap, String fwidcode) throws Exception {
//		List<Map> list = new ArrayList<Map>();
//		Map<String, Object> map = new HashMap<String, Object>();
		QueryFWIdcodeResponse queryResponse = new QueryFWIdcodeResponse();
		PrimaryCode pc = null;
		PrintJob printJob = null;
		Product product = null;
		Query query = null;

		if (queryMap.get("primaryCode") != null) {
			pc = (PrimaryCode) queryMap.get("primaryCode");
		}

		if (pc != null && pc.getId() != null) {
			// 主要数据
			queryResponse.setFwIdcode(fwidcode);
//			map.put("fwIdcode", fwidcode);
			if (queryMap.get("query") != null) {
				query = (Query) queryMap.get("query");
			}
			if (query != null) {
				queryResponse.setCount(query.getQueryNum().toString());
//				map.put("count", query.getQueryNum());
			} else {
				queryResponse.setCount("");
//				map.put("count", "");
			}

			if (queryMap.get("product") != null) {
				product = (Product) queryMap.get("product");
			}

			if (product != null) {
				queryResponse.setSpecMode(product.getSpecmode());
//				map.put("specMode", product.getSpecmode());
			} else {
//				map.put("specMode", "");
				queryResponse.setSpecMode("");
			}

			if (queryMap.get("printJob") != null) {
				printJob = (PrintJob) queryMap.get("printJob");
			}
			if (printJob != null && printJob.getPrintJobId() != -1) {
				queryResponse.setpName(product.getProductname());
				queryResponse.setProduceDate(Dateutil.formatDate(DateUtil.formatStrDate(printJob.getProductionDate())));
				queryResponse.setBatch(printJob.getBatchNumber());
//				map.put("pName", product.getProductname());
//				map.put("produceDate", Dateutil.formatDate(DateUtil.formatStrDate(printJob.getProductionDate())));
//				map.put("batch", printJob.getBatchNumber());
			} else {
				queryResponse.setpName("");
				queryResponse.setProduceDate("");
				queryResponse.setBatch("");
			}
//			list.add(map);
		} else {
			queryResponse.setFwIdcode("");
//			map.put("fwIdcode", fwidcode);
		}
//		return map;
		return queryResponse; 
	}

	public boolean authenticate(String userName, String password) throws Exception {
		return LDAPServices.authenticateUserByAD(userName, password);
	}
}
