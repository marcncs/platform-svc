package com.winsafe.drp.keyretailer.action.phone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.CodeTraceService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;


public class QueryCodeTraceAction extends BaseAction {
	private Logger logger = Logger.getLogger(QueryCodeTraceAction.class);
	
	private AppUsers appUsers = new AppUsers();
	private DecimalFormat decimalFormat = new DecimalFormat("###,###");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String username = request.getParameter("Username"); //登陆名
			String idcode = request.getParameter("idcode");
//			String organId = request.getParameter("organId");
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			CodeTraceService cts = new CodeTraceService();
			TraceJson traceJson = new TraceJson();
			traceJson.setIdcode(idcode);
			
			//查询条码信息
			Map<String,String> codeInfo = cts.getCodeInfo(idcode);
			
			if(codeInfo != null) {
				//查询流向记录
				List<Map<String,String>> codeFlowList = cts.getCodeFlows(codeInfo.get("cartoncode")); 
				if(codeFlowList.size() == 0) {
					//查看条码是否在当前仓库
					String organId = cts.getIdcodeBelongedInOrganId(codeInfo.get("cartoncode"));
					if(!loginUsers.getMakeorganid().equals(organId)) {
						traceJson.setStatus("4");
					} else {
						setTraceInfo(traceJson, codeInfo);
						traceJson.setStatus("3");
					}
				} else {
					//查看是否由用户所在机构发出过
					boolean hasAuthority = false;
					for(Map<String, String> flow : codeFlowList) {
						if(loginUsers.getMakeorganid().equals(flow.get("outoid")) 
								|| loginUsers.getMakeorganid().equals(flow.get("inoid"))) {
							hasAuthority = true;
							break;
						}
					}
					if(hasAuthority) {
						traceJson.setStatus("1");
						setTraceInfo(traceJson, codeInfo);
						setTraceDetail(traceJson, codeFlowList, loginUsers);
					} else {
						traceJson.setStatus("4");
					}
					
				}
			} else {
				traceJson.setStatus("2");
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, traceJson
					,loginUsers.getUserid(),"APP_RI","流向查询",true);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private void setTraceDetail(TraceJson traceJson,
			List<Map<String, String>> codeFlowList, UsersBean loginUsers) { 
		List<TraceDetailJson> traceDetail = new ArrayList<TraceDetailJson>();
		Map<String, String> bonusPointMap = new HashMap<String, String>();
		for(Map<String, String> flow : codeFlowList) {
			if(!loginUsers.getMakeorganid().equals(flow.get("outoid")) 
					&& !loginUsers.getMakeorganid().equals(flow.get("inoid"))) {
				continue;
			}
			TraceDetailJson tdj = new TraceDetailJson();
			tdj.setInOrganName(flow.get("inoname"));
			tdj.setOutOrganName(flow.get("outoname"));
			tdj.setOutDate(flow.get("outdate"));
			if(!StringUtil.isEmpty(flow.get("samindate"))) {
				tdj.setInDate(flow.get("samindate"));
				if(!StringUtil.isEmpty(flow.get("outbonuspoint"))) {
					tdj.setOutBonusPoint(decimalFormat.format(Double.valueOf(flow.get("outbonuspoint"))));
					bonusPointMap.put("outbonuspoint", tdj.getOutBonusPoint());
				}
				if(!StringUtil.isEmpty(flow.get("inbonuspoint"))) {
					tdj.setInBonusPoint(decimalFormat.format(Double.valueOf(flow.get("inbonuspoint"))));
					bonusPointMap.put("inbonuspoint", tdj.getInBonusPoint());
				}
			} else if(!StringUtil.isEmpty(flow.get("owindate"))) {
				tdj.setInDate(flow.get("owindate"));
				if(!StringUtil.isEmpty(bonusPointMap.get("outbonuspoint"))) {
					tdj.setOutBonusPoint("-"+bonusPointMap.get("outbonuspoint"));
				}
				if(!StringUtil.isEmpty(bonusPointMap.get("inbonuspoint"))) {
					tdj.setInBonusPoint("-"+bonusPointMap.get("inbonuspoint"));
				}
			}
			tdj.setType(flow.get("type"));
			traceDetail.add(tdj);
		}
		traceJson.getTraceInfo().setTraceDetail(traceDetail);
	}

	private void setTraceInfo(TraceJson traceJson, Map<String, String> codeInfo) throws Exception { 
		TraceInfoJson tij = new TraceInfoJson();
		MapUtil.mapToObject(codeInfo, tij);
		if(!StringUtil.isEmpty(tij.getProduceDate())) {
			Date produceDate = DateUtil.StringToDate(tij.getProduceDate(), "yyyyMMdd");
			tij.setProduceDate(DateUtil.formatDate(produceDate)); 
		}
		if(!StringUtil.isEmpty(tij.getExpiryDate())) {
			Date produceDate = DateUtil.StringToDate(tij.getExpiryDate(), "yyyyMMdd");
			tij.setExpiryDate(DateUtil.formatDate(produceDate)); 
		}
		if(!StringUtil.isEmpty(tij.getPackDate())) {
			Date packDate = DateUtil.StringToDate(tij.getPackDate(), "yyyyMMdd");
			tij.setPackDate(DateUtil.formatDate(packDate)); 
		}
		traceJson.setTraceInfo(tij);
	}

	public class TraceJson {
		private String idcode;
		//1.正常,2.无条码信息,3无物流信息,4.无权限
		private String status;
		private TraceInfoJson traceInfo;
		public String getIdcode() {
			return idcode;
		}
		public void setIdcode(String idcode) {
			this.idcode = idcode;
		}
		public TraceInfoJson getTraceInfo() {
			return traceInfo;
		}
		public void setTraceInfo(TraceInfoJson traceInfo) {
			this.traceInfo = traceInfo;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}

	public class TraceInfoJson {
		private String productName;
		private String produceDate;
		private String expiryDate;
		private String batch;
		private String cartonCode;
		private String packDate;
		private String spec;
		private List<TraceDetailJson> traceDetail;
		
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProduceDate() {
			return produceDate;
		}
		public void setProduceDate(String produceDate) {
			this.produceDate = produceDate;
		}
		public String getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(String expiryDate) {
			this.expiryDate = expiryDate;
		}
		public String getBatch() {
			return batch;
		}
		public void setBatch(String batch) {
			this.batch = batch;
		}
		public String getCartonCode() {
			return cartonCode;
		}
		public void setCartonCode(String cartonCode) {
			this.cartonCode = cartonCode;
		}
		public List<TraceDetailJson> getTraceDetail() {
			return traceDetail;
		}
		public void setTraceDetail(List<TraceDetailJson> traceDetail) {
			this.traceDetail = traceDetail;
		}
		public String getPackDate() {
			return packDate;
		}
		public void setPackDate(String packDate) {
			this.packDate = packDate;
		}
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
	}
	
	public class TraceDetailJson {
		private String outOrganName;
		private String outDate;
		private String inOrganName;
		private String inDate;
		private String outBonusPoint;
		private String inBonusPoint;
		private String type;
		public String getOutOrganName() {
			return outOrganName;
		}
		public void setOutOrganName(String outOrganName) {
			this.outOrganName = outOrganName;
		}
		public String getOutDate() {
			return outDate;
		}
		public void setOutDate(String outDate) {
			this.outDate = outDate;
		}
		public String getInOrganName() {
			return inOrganName;
		}
		public void setInOrganName(String inOrganName) {
			this.inOrganName = inOrganName;
		}
		public String getInDate() {
			return inDate;
		}
		public void setInDate(String inDate) {
			this.inDate = inDate;
		}
		public String getOutBonusPoint() {
			return outBonusPoint;
		}
		public void setOutBonusPoint(String outBonusPoint) {
			this.outBonusPoint = outBonusPoint;
		}
		public String getInBonusPoint() {
			return inBonusPoint;
		}
		public void setInBonusPoint(String inBonusPoint) {
			this.inBonusPoint = inBonusPoint;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
}
