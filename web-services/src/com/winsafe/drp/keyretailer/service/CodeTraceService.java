package com.winsafe.drp.keyretailer.service;

import java.util.List;
import java.util.Map;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;

public class CodeTraceService {
	
	private AppCartonCode acc = new AppCartonCode();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppIdcode appIdcode = new AppIdcode();

	public Map<String, String> getCodeInfo(String code) throws Exception {
		if(code.length() == Constants.CARTON_CODE_V_I) { //20位箱码
			return appPrintJob.getPrintJobByCartonCode(code);
		} else if(code.length() == Constants.CARTON_CODE_V_II) { //58位二维码
			String cartonCode = code.substring(Constants.CARTON_BEGIN_INDEX);
			return appPrintJob.getPrintJobByCartonCode(cartonCode);
		} else if(code.length() == Constants.PRIMARY_CODE_V_I || code.length() == Constants.PRIMARY_CODE_V_II) { //10,13位小码
			return appPrintJob.getPrintJobByCode(null,code,null);
		} else if(code.length() == Constants.PRIMARY_CODE_V_III || code.length() == Constants.PRIMARY_CODE_V_IV) {//50,53位二维码
			String primaryCode = code.substring(Constants.PRIMARY_BEGIN_INDEX);
			return appPrintJob.getPrintJobByCode(null,primaryCode,null);
		} else if(code.length() == Constants.MPIN_CODE_V_II) {//24位二维码
			CartonCode cc = acc.getByOutAndIn(code);
			if(cc != null) {
				return appPrintJob.getPrintJobByCartonCode(cc.getCartonCode());
			}
		} else if(code.length() == Constants.COVERT_CODE_V_II || code.length() == Constants.COVERT_CODE_X_I) {//12,11位暗码
			return appPrintJob.getPrintJobByCode(null,null,code);
		}
		return null;
	}

	public List<Map<String, String>> getCodeFlows(String idcode) throws Exception { 
		return appIdcode.getCodeFlows(idcode);
	}

	public String getIdcodeBelongedInOrganId(String cartonCode) {
		return appIdcode.getIdcodeBelongedInOrganId(cartonCode);
	}
}
