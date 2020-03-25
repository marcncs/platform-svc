package com.winsafe.drp.action.assistant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CartonSeqLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;

public class FwIdcodeService {
	private Logger logger = Logger.getLogger(FwIdcodeService.class);
	private AppPrimaryCode apc = new AppPrimaryCode();
	private AppCartonCode acc = new AppCartonCode();
	private AppProduct ap = new AppProduct();
	private AppQuery aq = new AppQuery();
	private PrimaryCode pc = new PrimaryCode();
	private CartonCode cc = new CartonCode();
	private PrintJob pj = new PrintJob();
	private String fwidcode = "";
	private String querycode = "";
	private Map<String, Object> queryMap = new HashMap<String, Object>();

	// 小码查询
	public Map<String, Object> fwIdcodeQuery(String primaryCode, int query_mode, boolean showResult, HttpServletRequest request, String ipString) throws Exception {
		try {
			querycode = primaryCode;
			fwidcode = getFwidcode(primaryCode);

			// 进行小包装码查询
			if (!StringUtil.isEmpty(fwidcode)) {
				if(primaryCode.length() == Constants.PRIMARY_CODE_II_I) {
					pc = getCartonCodeByPrimarySuffix(primaryCode);
				} else {
					pc = apc.getPrimaryCodeByPCode(fwidcode);
				}
				if (pc != null 
						&& pc.getIsUsed() == 1) {
					// 显示查询出的结果
					if (!StringUtil.isEmpty(pc.getCartonCode())) {
						cc = acc.getByCartonCode(pc.getCartonCode());
						if (cc != null) {
							queryMap.put("cartonCode", cc);
						}
					}

/*
					//从小码表中得到产品信息
					if (pc.getPrintJobId() != null) {
						pj = apj.getPrintJobByID(pc.getPrintJobId());
						if (showResult) {
							request.setAttribute("pj", pj);
						}
						if (pj != null && !StringUtil.isEmpty(pj.getProductId())) {
							pd = ap.getProductByID(pj.getProductId());
							if (showResult) {
								request.setAttribute("pd", pd);
							}
						}
					}
*/	


					// 先从箱码中获取产品信息，获取不到从小码表中获取产品信息
					Map<String, Object> productMap = ap.getProductInformation(cc, pc, null);
					if (productMap.get("product") != null) {
						queryMap.put("product", (Product) productMap.get("product"));
					}
					
					if (productMap.get("cartonSeqLog") != null) {
						queryMap.put("cartonSeqLog", (CartonSeqLog) productMap.get("cartonSeqLog"));
					}

					if (productMap.get("printJob") != null) {
						queryMap.put("printJob", (PrintJob) productMap.get("printJob"));
					}

					// 增加查询记录，第一个true是判断是否为第一次查询，第二个true为是否是正确查询
					queryRecord(query_mode, request, showResult, ipString, true, true);
					queryMap.put("primaryCode", pc);
					queryMap.put("prompt", "r");
				} else {

					// 增加查询记录
					queryRecord(query_mode, request, showResult, ipString, false, false);
					// 提示暂未获取到序列号
					queryMap.put("prompt", "fn");
				}
			} else {
				// 增加查询记录
				queryRecord(query_mode, request, showResult, ipString, false, false);
				// 提示暂未获取到序列号
				queryMap.put("prompt", "fn");
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("", e);
		}
		return queryMap;
	}

	private PrimaryCode getCartonCodeByPrimarySuffix(String idCode) throws Exception {
		List<Map<String, String>> pcMapList = apc.getCartonCodeByVCode(idCode);
		if(pcMapList != null && pcMapList.size() > 0) {
			Map<String, String> pcMap = pcMapList.get(0);
			pc = new PrimaryCode();
			pc.setPrimaryCode(pcMap.get("primarycode"));
			pc.setCartonCode(pcMap.get("cartoncode"));
			pc.setCovertCode(pcMap.get("convertcode"));
			if(!StringUtil.isEmpty(pcMap.get("printjobid"))) {
				pc.setPrintJobId(Integer.valueOf(pcMap.get("printjobid")));
			}
			if(!StringUtil.isEmpty(pcMap.get("isused"))) {
				pc.setIsUsed(Integer.valueOf(pcMap.get("isused")));
			} else {
				pc.setIsUsed(0);
			}
			return pc;
		}
		return null;
	}
	
	// 增加记录
	public void queryRecord(int query_mode, HttpServletRequest request, boolean showResult, String ipString, boolean chktrue, boolean rightquery) {
		// 判断该码是否已经查询过
		int query_num = 1;
		try {
			boolean existQuery = false;
			Query q = new Query();
			if (rightquery) {
				q = aq.getQueryByID(fwidcode);
			} else {
				q = aq.getQueryByID(querycode);
			}
			if (q != null) {
				existQuery = true;
				query_num = q.getQueryNum();
				// 如果该小码中已存在第一次查询日期
				// 显示第一次查询日期
				if (existQuery) {
					if (pc != null) {
						queryMap.put("firstTime", Dateutil.formatDateTime(pc.getFirstTime()));
					}
				}
			}
			// 单查询为页面查询时，不增加记录
			if (query_mode != 1) {
				q = new Query();
				//q.setId(MakeCode.getExcIDByRandomTableName("query", 0, ""));
				if (rightquery) {
					q.setProNumber(fwidcode);
				} else {
					q.setProNumber(querycode);
				}
				q.setFindDt(new Date());
				// 设置查询模式
				q.setFindType(String.valueOf(query_mode));
//				switch (query_mode) {
//				case Constants.CASE_QUERY_MODE_WEB:
//					q.setFindType(Constants.QUERY_MODE_WEB);
//					break;
//				case Constants.CASE_QUERY_MODE_APP:
//					q.setFindType(Constants.QUERY_MODE_APP);
//					break;
//				case Constants.CASE_QUERY_MODE_MOBILE:
//					q.setFindType(Constants.QUERY_MODE_MOBILE);
//					break;
//				case Constants.CASE_QUERY_MODE_OFFICEWEB:
//					q.setFindType(Constants.QUERY_MODE_OFFICEWEB);
//					break;
//				}
				if (chktrue) {
					q.setChkTrue(1);
				} else {
					q.setChkTrue(0);
				}
				q.setTelNumber(ipString);
				q.setProductid(pj.getProductId());
				// 如果该码已经查过
				if (existQuery) {
					q.setIsFirstQuery(0);
					q.setQueryNum(query_num + 1);
				} else {
					q.setQueryNum(query_num);
					q.setIsFirstQuery(1);
					// 设置小码第一次查询时间
					if (pc != null) {
						apc.updFirstSearch(Dateutil.formatDateTime(new Date()), pc.getPrimaryCode());
						queryMap.put("firstTime", Dateutil.formatDateTime(new Date()));
					}
				}
				aq.addQuery(q);
				// 设置所有的pro_number（查询次数）为最新的
				//aq.updateQueryByPid(q.getQueryNum(), q.getProNumber());
			}
			queryMap.put("query", q);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public String getFwidcode(String primaryCode) {
		primaryCode = ESAPI.encoder().decodeForHTML(primaryCode);
		String fwString = primaryCode;
		// 获取小包装码
		fwString = fwString.toUpperCase().trim();
		if(fwString.indexOf("/QR/") != -1) {
			fwString = fwString.substring(primaryCode.lastIndexOf("/") + 1);
		} else if (fwString.contains("HTTP")) {
			fwString = fwString.substring(primaryCode.lastIndexOf("?") + 1);
		}
		switch (fwString.trim().length()) {
		case Constants.PRIMARY_CODE_V_I:
			break;
		case Constants.PRIMARY_CODE_V_II:
			break;
		case Constants.PRIMARY_CODE_III_II:
			break; 
		case Constants.PRIMARY_CODE_V_III:
			fwString = fwString.substring(Constants.PRIMARY_BEGIN_INDEX);
			break;
		case Constants.PRIMARY_CODE_V_IV:
			fwString = fwString.substring(Constants.PRIMARY_BEGIN_INDEX);
			break;
		}

		return fwString;
	}
}
