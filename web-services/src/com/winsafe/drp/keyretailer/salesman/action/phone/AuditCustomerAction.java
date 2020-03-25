package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.keyretailer.pojo.UserCustomer;
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.keyretailer.service.OrganService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.AfficheService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

import net.sf.json.JSONObject;

public class AuditCustomerAction extends Action{
//	private static Logger logger = Logger.getLogger(AuditCustomerAction.class);
	AppSalesMan appSalesMan = new AppSalesMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		AppOrgan appOrgan = new AppOrgan();
		AppOlinkMan appOlinkMan = new AppOlinkMan();
		StringBuffer updContent = new StringBuffer();
		String username = request.getParameter("Username");
		String organId = request.getParameter("organId");
		String organName = request.getParameter("organName");
		String oaddr = request.getParameter("oaddr");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String isAudit = request.getParameter("isAudit");
		String oppOrganIds = request.getParameter("oppOrganId");
		String userId = request.getParameter("userId");
		String organModel = request.getParameter("organType");
		String validateLocalId = request.getParameter("validateLocalId");
		String operName = request.getParameter("operName");
		String companyName = request.getParameter("companyName");
		String startDate = request.getParameter("startDate");
		String status = request.getParameter("status");
		String creditNo = request.getParameter("creditNo");
		String province = request.getParameter("Province");
		String city = request.getParameter("City");
		String county = request.getParameter("County");
		boolean isChanged = false;
		try {
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			Organ organ = appOrgan.getOrganByID(organId);
			if(organ.getValidatestatus() != null 
					&& !ValidateStatus.NOT_AUDITED.getValue().equals(organ.getValidatestatus())) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,该机构已审核过");
				return null;
			}
			
			if(!StringUtil.isEmpty(organName) 
					&& !organ.getOrganname().equals(organName)) {
				updContent.append(",机构名称由["+organ.getOrganname()+"]改为["+organName+"]");
				organ.setOrganname(organName);
				isChanged = true;
			}
			
			if(!StringUtil.isEmpty(oaddr) 
					&& !organ.getOaddr().equals(oaddr)) {
				updContent.append(",机构地址由["+organ.getOaddr()+"]改为["+oaddr+"]");
				organ.setOaddr(oaddr);
				isChanged = true;
			}
			
			Olinkman linkMan = appOlinkMan.getOlinkmanByMobile(organ.getOmobile());
			if(!StringUtil.isEmpty(name) 
					&& linkMan != null && !linkMan.getName().equals(name)) {
				updContent.append(",负责人姓名由["+linkMan.getName()+"]改为["+name+"]");
				linkMan.setName(name);
				isChanged = true;
			} 
			
			if(!StringUtil.isEmpty(mobile) 
					&& !mobile.equals(organ.getOmobile())) {
				if(appOrgan.isUsersExists(mobile, null)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "修改失败,已存在手机号为["+mobile+"]的客户");
					return null;
				}
				updContent.append(",手机由["+organ.getOmobile()+"]改为["+mobile+"]");
				organ.setOmobile(mobile);
				if(linkMan != null) {
					linkMan.setMobile(mobile);
				}
				isChanged = true;
			}
			
			if(!StringUtil.isEmpty(organModel)) {
				organ.setOrganModel(Integer.parseInt(organModel)); 
			}
			
			// 审批通过，添加审批匹配信息
			if (ValidateStatus.PASSED.getValue().equals(Integer.parseInt(isAudit))) {
				// 企查查信息匹配审批，信息保存至本地
				if ("qichacha".equals(validateLocalId)) {
					try {
						AppCountryArea aca = new AppCountryArea();
						Map<String,String> areasMap = aca.getAreaByIDs(province,city,county);
						Properties sysProperties = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
						String apiUrl = sysProperties.getProperty("QichachaApiUrl_Detail");
						String appKey = sysProperties.getProperty("QichachaAppKey");
						String secretKey = sysProperties.getProperty("QichachaSecretKey");
						String timespan = String.valueOf((System.currentTimeMillis() / 1000)); //精确到秒的Unix时间戳
						String token = MD5BigFileUtil.md5(appKey + timespan + secretKey).toUpperCase();
						Map<String, String> params = new HashMap<String, String>();
				        params.put("key", appKey);
				        params.put("keyword", creditNo);
				        String result = QueryCustomerInfoFromApiAction.sendGet(apiUrl, params, token, timespan);
				        JSONObject jsonobject = JSONObject.fromObject(result);
						String apiReturnStatus = jsonobject.getString("Status");
						if ("200".equals(apiReturnStatus)) {
							JSONObject detail = jsonobject.getJSONObject("Result");
							JSONObject contactInfo = detail.getJSONObject("ContactInfo");
							appSalesMan.addCustomerLocalInfo(detail, contactInfo, areasMap, organ);
							validateLocalId = detail.getString("KeyNo");
//							appSalesMan.addCustomerLocalInfo(detail, contactInfo);
						} else {
							validateLocalId = UUID.randomUUID().toString().replace("-", "");
							appSalesMan.addCustomerLocalInfo(validateLocalId, companyName, operName, startDate, status, creditNo);
						}
					} catch (Exception e) {
						WfLogger.error("获取匹配详情失败", e);
					}
					
				}
				organ.setValidateLoaclId(validateLocalId);
			}
			
			organ.setValidatedate(DateUtil.getCurrentDate());
			organ.setValidatestatus(Integer.parseInt(isAudit)); 
			organ.setValidateuserid(loginUsers.getUserid());
			appOrgan.updOrgan(organ);
			
			if(linkMan == null) {
				OrganService organService = new OrganService();
				organService.addOlinkman(loginUsers, organ, name, "");
			} else {
				appOlinkMan.updOlinkman(linkMan);
			}
			
			//新增进货关系
			if(!StringUtil.isEmpty(oppOrganIds) && !",".equals(oppOrganIds)) {
				AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
//				String condition = "'"+oppOrganIds.replace(",", "','")+"'";
//				logger.debug("-----oppOrganIds-------"+oppOrganIds);
//				appSTransferRelation.delSTransferRelation(organId, condition);
//				oppOrganIds = oppOrganIds.substring(1);
				String[] oppOrganIdArray = oppOrganIds.split(",");
				for(String oppOrganId : oppOrganIdArray) {
					if(StringUtil.isEmpty(oppOrganId)) {
						continue;
					}
					if(appSTransferRelation.isAlreadyExists(organId, oppOrganId)) {
						continue;
					}
					STransferRelation str = new STransferRelation();
					str.setModifieDate(DateUtil.getCurrentDate());
					str.setOppOrganId(oppOrganId);
					str.setOrganizationId(organId);
					appSTransferRelation.addSTransferRelation(str);
					Organ parentOrgan = appOrgan.getOrganByID(oppOrganId);
					
					// 增加PD用户(管辖上级机构)的业务往来权限
					if(parentOrgan.getOrganType() == 2 && parentOrgan.getOrganModel() == 1) {
						addOrganVisit(oppOrganId, organId);
					}
				}
			}
			
			if(DealerType.BKD.getValue().equals(organ.getOrganModel())
					&& !StringUtil.isEmpty(userId)) {
				AppUserCustomer appUserCustomer = new AppUserCustomer();
				UserCustomer uc = appUserCustomer.getUserCustomer(userId, organ.getId());
				if(uc == null) {
					appUserCustomer.delUserCustomerByOrganId(organ.getId());
					uc = new UserCustomer();
					uc.setMakeDate(DateUtil.getCurrentDate());
					uc.setOrganId(organ.getId());
					uc.setUserId(Integer.parseInt(userId));
					appUserCustomer.addUserCustomer(uc);
				}
			}
			
			if(isChanged) {
				AfficheService afficheService = new AfficheService();
				afficheService.addAuditResponseAffiche(organ, updContent.toString());
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, organ.getId()
					,loginUsers.getUserid(),"APP_RI","审核客户",true);
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "审核失败,系统异常");
			throw e;
		}
		
		return null;
	}
	
	/**
	 * 新增业务往来权限
	 * @throws Exception 
	 */
	private void addOrganVisit(String parentid,String oid) throws Exception{
		AppOrganVisit aov = new AppOrganVisit();
		AppWarehouseVisit appWV = new AppWarehouseVisit();
		AppMakeConf appmc = new AppMakeConf();
		//增加业务往来机构
		aov.addOrganVisit(oid, parentid);
		//增加业务往来仓库
		appWV.addWarehousVisit(oid, parentid);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
	
	public static void main(String[] args) {
		String result = "{\"JobId\":\"\",\"OrderNumber\":\"ECI2019022117595427155706\",\"Result\":{\"Partners\":[{\"StockName\":\"高秀梅\",\"StockType\":\"自然人股东\",\"StockPercent\":\"100%\",\"ShouldCapi\":\"100\",\"ShoudDate\":\"2016-12-30\",\"InvestType\":\"货币\",\"InvestName\":null,\"RealCapi\":null,\"CapiDate\":null}],\"Employees\":[{\"Name\":\"高秀梅\",\"Job\":\"执行董事\"},{\"Name\":\"高福和\",\"Job\":\"监事\"},{\"Name\":\"高波\",\"Job\":\"经理\"}],\"Branches\":[],\"ChangeRecords\":[],\"ContactInfo\":{\"WebSite\":null,\"PhoneNumber\":\"13563360845\",\"Email\":\"13563360845@163.com\"},\"Industry\":{\"IndustryCode\":\"A\",\"Industry\":\"农、林、牧、渔业\",\"SubIndustryCode\":\"05\",\"SubIndustry\":\"农、林、牧、渔专业及辅助性活动\",\"MiddleCategoryCode\":null,\"MiddleCategory\":null,\"SmallCategoryCode\":null,\"SmallCategory\":null},\"KeyNo\":\"ee6b5b74241e0cf977e251c6f5d43804\",\"Name\":\"利津县中古店农业开发有限责任公司\",\"No\":\"370522200013691\",\"BelongOrg\":\"利津县市场监督管理局\",\"OperName\":\"高秀梅\",\"StartDate\":\"2015-11-30 00:00:00\",\"EndDate\":\"\",\"Status\":\"在营（开业）企业\",\"Province\":\"SD\",\"UpdatedDate\":\"2019-02-04 15:08:50\",\"CreditCode\":\"91370522MA3C1CP780\",\"RegistCapi\":\"100万元人民币\",\"EconKind\":\"有限责任公司(自然人独资)\",\"Address\":\"山东省东营市利津县陈庄镇中古店一村140号\",\"Scope\":\"农业技术开发、咨询服务及转让,土地整理;农业机械服务,农用机械、农具、化肥、农药(不含危险化学品)、不再分装的包装种子零售;粮食作物、花卉、苗木、瓜果、蔬菜种植及销售,农产品加工及销售;园林绿化及技术服务,家禽、水产品淡水养殖及销售,农业休闲旅游。(依法须经批准的项目,经相关部门批准后方可开展经营活动)\",\"TermStart\":\"2015-11-30 00:00:00\",\"TeamEnd\":\"\",\"CheckDate\":\"2015-11-30 00:00:00\",\"OrgNo\":\"MA3C1CP7-8\",\"IsOnStock\":\"0\",\"StockNumber\":null,\"StockType\":null,\"OriginalName\":[],\"ImageUrl\":\"https://co-image.qichacha.com/CompanyImage/default.jpg\"},\"Status\":\"200\",\"Message\":\"查询成功\"}";
		JSONObject jsonobject = JSONObject.fromObject(result);
		String apiReturnStatus = jsonobject.getString("Status");
		if ("200".equals(apiReturnStatus)) {
			JSONObject detail = jsonobject.getJSONObject("Result");
			JSONObject contactInfo = detail.getJSONObject("ContactInfo");
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO CUSTOMER_LOCAL_INFO (ID, NAME, RE_CODE, OFFICE, LEG, C_DATE, STATUS, PROVINCE, TYPE, AMOUNT, ADDRESS, SCOPE, DATE_FROM, DATE_TO, ISSUE_DATE, TEL) ");
			sb.append("VALUES ('");
			sb.append(StringUtil.removeNull(detail.getString("KeyNo")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("Name")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("No")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("BelongOrg")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("OperName")));
			sb.append("',");
			sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
			sb.append(",'");
			sb.append(StringUtil.removeNull(detail.getString("Status")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("Province")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("EconKind")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("RegistCapi")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("Address")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("Scope")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("TermStart")));
			sb.append("','");
			sb.append(StringUtil.removeNull(detail.getString("TeamEnd")));
			sb.append("',");
			sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
			sb.append(",'");
			sb.append(StringUtil.removeNull(contactInfo.getString("PhoneNumber")));
			sb.append("')");
			System.out.println(sb.toString());
		} 
	}
}

