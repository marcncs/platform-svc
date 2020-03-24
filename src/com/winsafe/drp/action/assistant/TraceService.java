package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winsafe.control.pojo.TraceJson;
import com.winsafe.control.pojo.TraceJsonDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.dao.QueryResult;
import com.winsafe.drp.metadata.ProductType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintLog;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CartonSeqLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.util.SapConfig;
import common.Logger;
/*******************************************************************************************  
 * 处理追溯页面查询
 * 农药名称：取产品资料中 农药标准名称 字段
 * 登记证持有人：取产品资料中的 登记证持有人 字段
 * 产品生产批次:
 * 				1.	有箱码的小码，取打印任务中的批次号字段
 *				2.	无箱码的小码，需后台激活，取激活时填写的批次号
 * 产品检验单位:杭州工厂的条码显示 拜耳作物科学(中国)有限公司 ，分装厂的条码显示各分装厂的名称
 * 产品检验时间:
 * 				1.	杭州工厂的条码，取打印任务中的打印日期
 *				3.	分装厂有箱码的小码，取打印任务中的打印日期，即生成打印任务的日期
 *				4.	分装厂无箱码的小码，需后台激活，取激活时填写的检验日期				
 * @author: ryan.xi	  
 * @date：2014-10-15  
 * @version 1.0  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-10-15   ryan.xi                               
 *******************************************************************************************  
 */  
public class TraceService {
	private static Logger logger = Logger.getLogger(TraceService.class);
	private AppProduct ap = new AppProduct();
	private AppPrimaryCode apc = new AppPrimaryCode();
	private AppCartonCode acc = new AppCartonCode();
	private AppQuery appQuery = new AppQuery();
	private PrimaryCode pCode = null;
	private AppOrgan appOrgan = new AppOrgan();
	private AppPrintLog appPrintLog = new AppPrintLog();
	/**
	 * 追溯查询
	 * 
	 * @param queryCode
	 * @param ipString
	 * @param batch
	 * @return
	 */
	public QueryResult execute(String queryCode, String ipString, boolean isMannual) {
		QueryResult queryResult = null;
		try {
			//如果查询码为空返回空值
			if(StringUtil.isEmpty(queryCode)) {
				return queryResult;
			}
			String cartonCode = "";
			if(isMannual) {
				//手工输入21位验证码查询
				cartonCode = getCartonCodeByVCode(queryCode);
			} else {
				cartonCode = getCartonCode(queryCode);
			}
			//如果箱码为空返回空值
			if(StringUtil.isEmpty(cartonCode)) {
				//通用码或还未更新箱码的后关联码
				if(isCommonCode(pCode) || isAfterLinkCode(pCode)) {
					queryResult = getQueryResultByPrintJobId(queryCode, pCode.getPrintJobId());
					
				} else if(pCode != null 
						&& !StringUtil.isEmpty(pCode.getCartonCode())
						&& pCode.getIsUsed() == 1) {
					//分装厂无箱码的小码
					queryResult = getQueryResultForNoCartonPrimaryCode(queryCode);
				} 
			} else {
				//通过20位箱码获取追溯信息
				queryResult = getQueryResultByCartonCode(queryCode, cartonCode);
				
			}
			if(queryResult != null) {
				//通过箱码查询流向
				if(!StringUtil.isEmpty(cartonCode)) {
					List<Map<String,String>> flowList = appQuery.getFlowByCartonCode(cartonCode);
					if(flowList!= null && flowList.size() > 0) {
						Map<String,String> flowMap = flowList.get(0);
						queryResult.setFlow(flowMap.get("organname"));
					}
				}
				addQuery(queryResult, ipString);
			} else {
				queryResult = new QueryResult(queryCode, false);
				addQuery(queryResult, ipString);
			}
			//格式化检验日期
			formatInspectionDate(queryResult);
			//设置暗码信息
			if(pCode != null) {
				queryResult.setCovertCode(pCode.getCovertCode());
				queryResult.setPrimaryCode(pCode.getPrimaryCode());
			}
			queryResult.setCartonCode(cartonCode);
		} catch (Exception e) {
			logger.error("",e);
		}
		return queryResult;
	}
	
	private void formatInspectionDate(QueryResult queryResult) {
		if(!StringUtil.isEmpty(queryResult.getInspectionDate())) {
			Date date = null;
			if(queryResult.getInspectionDate().length() > 10) {
				date = DateUtil.StringToDatetime(queryResult.getInspectionDate());
			} else {
				date = DateUtil.StringToDate(queryResult.getInspectionDate());
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			//25号之后打印的产品检验时间显示下个月
			if(day >= 25) {
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				queryResult.setInspectionDate(DateUtil.formatDate(calendar.getTime()));
			}
			
		}
	}

	private QueryResult getQueryResultForNoCartonPrimaryCode(String queryCode) throws Exception {
		QueryResult queryResult = new QueryResult(queryCode, true);
		//登记证后六位
		String regCode = pCode.getPrimaryCode().substring(1, 7);
		//规格代码
		String specCode = pCode.getPrimaryCode().substring(8, 11);
		Product product = ap.getProductByRegCertAndSpecCode(regCode, specCode);
		if(product != null) {
			queryResult.setProductId(product.getId());
			queryResult.setProductName(product.getProductname());
			queryResult.setRegCertUser(product.getRegCertUser());
			queryResult.setStandardName(product.getStandardName());
			queryResult.setSpecMode(product.getSpecmode());
			queryResult.setProductType(product.getProductType().toString()); 
			queryResult.setRegcertCodeFixed(product.getRegCertCodeFixed());
//			queryResult.setValidResult(product.getValidResult());
			queryResult.setInspectionInstitution(product.getInspectionInstitution());
			queryResult.setMaterialCode(product.getmCode());
		}
		//批次号
		if(!StringUtil.isEmpty(pCode.getUploadPrId())) {
			AppCartonSeq appCartonSeq = new AppCartonSeq();
			CartonSeqLog log = appCartonSeq.getCartonSeqLogById(pCode.getUploadPrId());
			if(log != null) {
				Organ organ = appOrgan.getOrganByID(log.getOrganId());
				if(organ!= null) {
					queryResult.setPlantName(organ.getOrganname());
				}
				queryResult.setProductionDate(DateUtil.formatDate(log.getProductionDate()));
				queryResult.setBatch(StringUtil.removeNull(log.getBatch()));
				if(log.getInspectionDate() != null) {
					queryResult.setInspectionDate(DateUtil.formatDate(log.getInspectionDate()));
				} else {
					queryResult.setInspectionDate(DateUtil.formatDate(log.getMakeDate()));
				}
			}
		}
		return queryResult;
	}

	private QueryResult getQueryResultByCartonCode(String queryCode,
			String cartonCode) throws Exception {
		List<Map<String, String>> traceList = acc.getTraceInfo(cartonCode);
		QueryResult queryResult = getQueryResult(queryCode, traceList);
		return queryResult;
	}

	private QueryResult getQueryResult(String queryCode,
			List<Map<String, String>> traceList) throws Exception {
		QueryResult queryResult = null;
		if(traceList.size() > 0) {
			Map<String, String> result = traceList.get(0);
			queryResult = new QueryResult(queryCode, true);
			MapUtil.mapToObjectIgnoreCase(traceList.get(0), queryResult);
//			if(OrganType.Plant.getValue().toString().equals(result.get("organtype"))
//					&& PlantType.Toller.getValue().toString().equals(result.get("organmodel"))) {
//				queryResult.setInspectionInstitution(result.get("organname"));
//			}
			if(StringUtil.isEmpty(result.get("inspectiondate"))) {
				//若打印任务中的打印日期为空,取打印历史中的日期
			    Date date = appPrintLog.getMinPrintDateByJobID(result.get("printjobid"));
			    if(date != null) {
			    	queryResult.setInspectionDate(DateUtil.formatDate(date));
			    } else {
			    	queryResult.setInspectionDate(result.get("createdate"));
			    }
			}
			
			//产品类型是分装厂时
			if (String.valueOf(ProductType.TOLLING.getValue()).equals(result.get("producttype"))) {
				//分装日期 +产品批次
				queryResult.setBatch(result.get("packagingdate") + (result.get("materialbatchno") == null ? "" : result.get("materialbatchno")));
			}
		}
		return queryResult;
	}

	private QueryResult getQueryResultByPrintJobId(String queryCode, Integer printJobId) throws Exception {
		List<Map<String, String>> traceList = acc.getTraceInfoByPrintJobId(pCode.getPrintJobId());
		QueryResult queryResult = getQueryResult(queryCode, traceList);
		return queryResult;
	}

	//是否后关联
	private boolean isAfterLinkCode(PrimaryCode primaryCode) {
		return primaryCode != null 
				&& StringUtil.isEmpty(primaryCode.getCartonCode())
				&& primaryCode.getPrintJobId() != null
				&& primaryCode.getIsUsed() == 1;
	}

	private boolean isCommonCode(PrimaryCode primaryCode) {
		return primaryCode!=null 
				&& SapConfig.getSapConfig().getProperty("commonCode").equals(primaryCode.getCartonCode());
	}

	/**
	 * 根据21位验证码查询杭州工厂条码信息
	 * @param idCode
	 * @return
	 * @throws Exception 
	 */
	private String getCartonCodeByVCode(String idCode) throws Exception {
		// 根据21位验证码获取箱码信息
		List<Map<String,String>> pc  = apc.getCartonCodeByVCode(idCode);
		if(pc.size() > 0) {
			Map<String,String> map = pc.get(0);
			pCode = new PrimaryCode();
			MapUtil.mapToObject(map, pCode);
			String cartonCode = map.get("cartoncode");
			if(isNomalCartonCode(cartonCode)) {
				return cartonCode;
			}
		}
		return null;
	}

	private boolean isNomalCartonCode(String cartonCode) {
		return !StringUtil.isEmpty(cartonCode)
				&& cartonCode.length() == Constants.CARTON_CODE_V_I
					&& !cartonCode.equals(SapConfig.getSapConfig().getProperty("commonCode"));
	}

	private void addQuery(QueryResult queryResult, String ipString) {
		// 判断该码是否已经查询过
		try {
			//查看是否已查询过
			Query existsQuery = appQuery.getQueryByID(queryResult.getQueryCode());
			Query query = new Query();
			//query.setId(MakeCode.getExcIDByRandomTableName("query", 0, ""));
			query.setProNumber(queryResult.getQueryCode());
			query.setFindDt(new Date());
			query.setFindType(String.valueOf(Constants.CASE_QUERY_MODE_WEB));
			if (queryResult.isExist()) {
				query.setChkTrue(YesOrNo.YES.getValue());
			} else {
				query.setChkTrue(YesOrNo.NO.getValue());
			}
			query.setTelNumber(ipString);
			query.setProductid(queryResult.getProductId());
			
			// 如果该码已经查过
			if (existsQuery != null) {
				query.setIsFirstQuery(YesOrNo.NO.getValue());
				query.setQueryNum(existsQuery.getQueryNum() + 1);
				queryResult.setFirstQuery(DateUtil.formatDateTime(existsQuery.getFindDt()));
			} else {
				query.setQueryNum(1);
				query.setIsFirstQuery(YesOrNo.YES.getValue());
				// 设置小码第一次查询时间
				Date date = DateUtil.getCurrentDate();
				apc.updFirstSearch(Dateutil.formatDateTime(date), queryResult.getQueryCode());
				queryResult.setFirstQuery(DateUtil.formatDateTime(date));
			}
			appQuery.addQuery(query);
			// 设置所有的pro_number（查询次数）为最新的
			//appQuery.updateQueryByPid(query.getQueryNum(), query.getProNumber());
			queryResult.setQueryCount(query.getQueryNum());
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}

	public static void main(String[] args) {
//		Map<String,String> map  = new TraceService().execute("00540522966000000614");
//		System.out.println(map);
	}

	/**
	 * 判断码的位数是否复核要求
	 * 
	 * @author jason.huang
	 * @param idcode
	 * @return
	 */
	public boolean judgeCode(String idcode) {
		// 用来判断码位数是否正确 
		boolean flag = false;
		switch (idcode.length()) {
		case Constants.CARTON_CODE_V_I:
			flag = true;
			break;
		case Constants.CARTON_CODE_V_II:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_I:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_II:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_III:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_IV:
			flag = true;
			break;
		case Constants.MPIN_CODE_V_II:
			flag = true;
			break;
		case Constants.COVERT_CODE_V_II:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}

	public String getCartonCode(String idCode) {
		try {
			switch (idCode.length()) {
			case Constants.CARTON_CODE_V_I:
				// 得到箱码
				return idCode;
			case Constants.CARTON_CODE_V_II:
				// 对二维码解析,得到箱码
				return idCode.substring(Constants.CARTON_BEGIN_INDEX);
			case Constants.PRIMARY_CODE_V_I:
				return getCartonCodeByPrimaryCode(idCode);
			case Constants.PRIMARY_CODE_V_II:
				return getCartonCodeByPrimaryCode(idCode);
			case Constants.PRIMARY_CODE_III_II:
				return getCartonCodeByPrimaryCode(idCode);
			case Constants.PRIMARY_CODE_V_III:
				// 对50位小包装二维码解析,得到小码
				return getCartonCodeByPrimaryCode(idCode.substring(Constants.PRIMARY_BEGIN_INDEX));
			case Constants.PRIMARY_CODE_V_IV:
				// 对53位小包装二维码解析,得到小码
				return getCartonCodeByPrimaryCode(idCode.substring(Constants.PRIMARY_BEGIN_INDEX));
			case Constants.MPIN_CODE_V_II:
				// 得到原mpin码
				CartonCode cc = acc.getByOutAndIn(idCode);
				if (cc != null) {
					// 根据原mpin码得到箱码
					return cc.getCartonCode();
				} else {
					return "";
				}
			case Constants.COVERT_CODE_V_II:
				return getCartonCodeByCovertCode(idCode);
			case Constants.COVERT_CODE_X_I:
				// 得到暗码
				return getCartonCodeByCovertCode(idCode);
			default:
				return idCode;
			}
		} catch (Exception e) {
			logger.error("analyzingCode  error:", e);
		}
		return "";
	}

	private String getCartonCodeByCovertCode(String covertCode) {
		// 根据暗码得到小码
		pCode = apc.getPrimaryCodeByCovertCode(covertCode);
		// 判断该小码是否存在
		if (pCode != null) {
			if(isNomalCartonCode(pCode.getCartonCode())) {
				return pCode.getCartonCode();
			}
		} 
		return "";
	}

	private String getCartonCodeByPrimaryCode(String primaryCode) {
		pCode = apc.getPrimaryCodeByPCode(primaryCode);
		// 判断该小码是否存在,是否有关联打印任务
		if (pCode != null) {
			if(isNomalCartonCode(pCode.getCartonCode())) {
				return pCode.getCartonCode();
			}
		} 
		return ""; 
	}

	public String getPopularProductId(String productName) {
		return ap.getPopularProductId(productName);
	}

	public TraceJson getFlowInfo(QueryResult result) throws Exception {
		TraceJson json = new TraceJson(result);
		List<TraceJsonDetail> list = new ArrayList<>();
		json.setFlows(list);
		if(!StringUtil.isEmpty(result.getCartonCode())) {
			List<Map<String, String>> mapList = acc.getFlowByCartonCode(result.getCartonCode());
			if(mapList != null && mapList.size()>0) {
				for(Map<String, String> map : mapList) {
					TraceJsonDetail tjd = new TraceJsonDetail();
					MapUtil.mapToObjectIgnoreCase(map, tjd);
					list.add(tjd);
				}
			}
		} 
		return json;
	}
}
