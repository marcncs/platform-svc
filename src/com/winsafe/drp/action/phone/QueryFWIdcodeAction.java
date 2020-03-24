package com.winsafe.drp.action.phone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.winsafe.drp.action.assistant.FwIdcodeService;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.metadata.LinkMode;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppPrintLog;
import com.winsafe.sap.dao.AppUploadProduceLog;
import com.winsafe.sap.pojo.CartonSeqLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.pojo.UploadProduceLog;

public class QueryFWIdcodeAction extends Action {
	private Logger logger = Logger.getLogger(QueryFWIdcodeAction.class);
	private AppQuery aq = new AppQuery();
	private AppOrgan appOrgan = new AppOrgan();
	private AppPrintLog appPrintLog = new AppPrintLog();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8"); 
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String fwidcode = request.getParameter("FWIdcode"); // 防伪码
		int query_mode = Constants.CASE_QUERY_MODE_APP;
		boolean showResult = false;

		try {
			FwIdcodeService fis = new FwIdcodeService();
			String ipString = aq.getIpAddr(request);
			Map<String, Object> queryMap = fis.fwIdcodeQuery(fwidcode.trim().toUpperCase(), query_mode, showResult, request, ipString);
			PrimaryCode pc = null;
			if (queryMap.get("primaryCode") != null) {
				pc = (PrimaryCode) queryMap.get("primaryCode");
			}

			if (pc != null && pc.getId() != null) {
				String fwnidcode = fis.getFwidcode(fwidcode.trim());
				Map list = getMapDate(queryMap, response, fwnidcode);
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list, null, "手机", "IMEI:[" + imeinumber + "]", false);
			} else {
				Map list = getMapDate(queryMap, response, fwidcode.trim());
				ResponseUtil.writeJsonMsg(response, Constants.CODE_NO_DATA, Constants.CODE_NO_DATA_MSG_SEC, list, null, "手机", "IMEI:[" + imeinumber + "]", false);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 转化数据为Map形式
	 */
	private Map getMapDate(Map<String, Object> queryMap, HttpServletResponse response, String fwidcode) throws Exception {
		List<Map> list = new ArrayList<Map>(); 
		Map<String, Object> map = new HashMap<String, Object>();
		PrimaryCode pc = null; 
		PrintJob printJob = null;
		Product product = null;
		Query query = null; 
		CartonSeqLog cartonSeqLog = null;
		//检验单位
		map.put("qcUser", Constants.DEFAULT_INSPECT_INST);

		if (queryMap.get("primaryCode") != null) {
			pc = (PrimaryCode) queryMap.get("primaryCode");
		}

		if (pc != null && pc.getId() != null) {
			//查询条码 
			map.put("fwIdcode", fwidcode);
			if (queryMap.get("query") != null) {
				query = (Query) queryMap.get("query");
			}
			//查询次数
			if (query != null) {
				map.put("count", query.getQueryNum());
			} else {
				map.put("count", "");
			}

			//设置产品名称,农药名称，登记证持有人
			if (queryMap.get("product") != null) {
				product = (Product) queryMap.get("product");
			}
			if (product != null) {
				map.put("pName", product.getProductname());
				map.put("standardName", product.getStandardName());
				map.put("regCertUser", product.getRegCertUser());
				map.put("specMode", product.getSpecmode());
			} else {
				map.put("pName", "");
				map.put("standardName", "");
				map.put("regCertUser", "");
				map.put("specMode", "");
			}
			
			//批次号,检验时间
			if (queryMap.get("printJob") != null) {
				printJob = (PrintJob) queryMap.get("printJob");
			}
			if (queryMap.get("cartonSeqLog") != null) {
				cartonSeqLog = (CartonSeqLog) queryMap.get("cartonSeqLog");
			}
			Organ organ = null;
			if (printJob != null && printJob.getPrintJobId() != -1) {
				organ = appOrgan.getByOecode(printJob.getPlantCode());
				map.put("batch", printJob.getBatchNumber());
				map.put("produceDate", DateUtil.formatDate(DateUtil.formatStrDate(printJob.getProductionDate())));
				if(printJob.getPrintingDate() != null) {
					map.put("qcDate",getInspectionDate(printJob.getPrintingDate()));
				} else {
					//若打印任务中的打印日期为空,取打印历史中的日期
					Date date = appPrintLog.getMinPrintDateByJobID(printJob.getPrintJobId().toString());
				    if(date != null) {
				    	map.put("qcDate",getInspectionDate(date));
				    } else {
				    	map.put("qcDate",getInspectionDate(printJob.getCreateDate()));
				    }
					
				}
			} else if(cartonSeqLog != null){
				organ = appOrgan.getOrganByID(cartonSeqLog.getOrganId());
				map.put("batch", cartonSeqLog.getBatch());
				map.put("produceDate",DateUtil.formatDate(cartonSeqLog.getProductionDate()));
				if(cartonSeqLog.getInspectionDate() != null) {
					map.put("qcDate", getInspectionDate(cartonSeqLog.getInspectionDate()));
				} else {
					map.put("qcDate", getInspectionDate(cartonSeqLog.getMakeDate()));
				}
				
			} else {
				map.put("batch", "");
				map.put("produceDate","");
				map.put("qcDate", "");
			}
			//分装厂的条码显示各分装厂的名称
			if(organ != null
					&& OrganType.Plant.getValue().equals(organ.getOrganType())
					&& PlantType.Toller.getValue().equals(organ.getOrganModel())) {
				map.put("qcUser", organ.getOrganname());
			}
			//设置杭州工厂后关联检验日期
			/*if(organ != null
					&& OrganType.Plant.getValue().equals(organ.getOrganType())
					&& PlantType.Hangzhou.getValue().equals(organ.getOrganModel())
					&& printJob != null
					&& product != null
					&& LinkMode.After.getValue().equals(product.getLinkMode())) {
				AppUploadProduceLog appUpl = new AppUploadProduceLog();
				UploadProduceLog log = appUpl.getUploadProduceLogByPrintJobId(printJob.getPrintJobId());
				if(log != null) {
					map.put("qcDate", DateUtil.formatDate(log.getMakeDate(), "yyyy-MM"));
				}
			}*/
			list.add(map);
		} else {
			map.put("fwIdcode", fwidcode);
		}
		return map;
	}

	private String getInspectionDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		//25号之后打印的产品检验时间显示下个月
		if(day >= 25) {
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
		}
		return DateUtil.formatDate(date,"yyyy-MM");
	}

}
