package com.winsafe.hbm.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppScanner; 
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PurviewFilter implements Filter { 

	private static Logger logger = Logger.getLogger(PurviewFilter.class);
	//忽略权限验证的url
	private static Set<String> noPermitUrlSet = new HashSet<String>();
	//对外接口的验证
	private static Set<String> interfaceUrlSet = new HashSet<String>();
	private static Set<String> phoneInterfaceUrlSet = new HashSet<String>();
	private static Set<String> otherInterfaceUrlSet = new HashSet<String>();
	
	private static AppScanner appScanner = new AppScanner();
	
	private static AppOrgan appOrgan = new AppOrgan();

	static {
		initPermitSetUrl();
		initInterfaceSetUrl();
		phoneInitInterfaceSetUrl();
		initOtherInterfaceSetUrl();
	}
	
	private static final String NOPURVIEW = "/nopurview.do";

	private static final String NOLOGIN = "/forwardIndexAction.do";

	private static final String REQUEST_ERROR = "/requestErrorAction.do";

	protected FilterConfig filterConfig;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest hreq = (HttpServletRequest) request;
			String value = hreq.getServletPath();
			hreq.setCharacterEncoding("UTF-8");
			if(value.endsWith(".do")){
				logger.debug(value);
				System.out.println(value);
			}
			if(hreq.getSession().getAttribute("localeLanguage") == null) {
				String lang = hreq.getLocale().getLanguage().toString();
				hreq.getSession().setAttribute("localeLanguage", lang);
			}
			if (checkValidata(hreq)) {
				logger.error("您发送请求中的参数中含有非法字符");
				hreq.getRequestDispatcher(REQUEST_ERROR).forward(request, response);
				return;
			} 
			if(value.indexOf("RTCIWebServicePort") != -1
					|| value.indexOf("AuthServicePort") != -1) { 
				chain.doFilter(request, response);
				return;
			}
			if(value.equals("/qr") || value.equals("/elabel") || value.equals("/cm")) {
				chain.doFilter(request, response);
				return;
			} 
			int index = value.indexOf(".");
			if (index == -1) {
				hreq.getRequestDispatcher(NOLOGIN).forward(request,response);
				return;
			}
			String suffix = value.substring(index + 1, value.length());
			//不作验证
			if(!suffix.equals("do") || noPermitUrlSet.contains(value)){
				chain.doFilter(request, response);
				return;
			}
			//外部接口验证
			if(interfaceUrlSet.contains(value)){
				//接口方式验证
				if(!checkInterfaceUrlMethod(hreq)){
					ResponseUtil.writeJsonMsg((HttpServletResponse)response, Constants.CODE_METHOD_ERROR, Constants.CODE_METHOD_ERROR_MSG,"");
					return;
				}
				if(!noPermitUrlSet.contains(value)) {
					//用户验证
					if(Constants.SCANNNER_VALIDATE_USER){
						String errorMsg = checkInterfacePwd(hreq);
						if(!StringUtil.isEmpty(errorMsg)){
							ResponseUtil.writeJsonMsg((HttpServletResponse)response,Constants.CODE_LOGIN_FAIL, errorMsg,"");
							return;
						}
					}
				}
				//采集器IMEI验证
				if(Constants.SCANNNER_VALIDATE_IMEI){
					if(!checkInterfaceIMEI(hreq)){
						ResponseUtil.writeJsonMsg((HttpServletResponse)response, Constants.CODE_LOGIN_FAIL, Constants.CODE_SCANNER_FAIL_MSG,"");
						return;
					}
				}
				chain.doFilter(request, response);
				return;
			}
			//手机接口验证
			if(phoneInterfaceUrlSet.contains(value)){
				//提交方式验证
				if(!checkInterfaceUrlMethod(hreq)){
					ResponseUtil.writeJsonMsg((HttpServletResponse)response, Constants.CODE_METHOD_ERROR, Constants.CODE_METHOD_ERROR_MSG,"");
					return;
				}
				if(!noPermitUrlSet.contains(value)) {
					//用户验证
					if(Constants.PHONE_VALIDATE_USER){
						String errorMsg = checkPhoneInterfacePwd(hreq);
						if(!StringUtil.isEmpty(errorMsg)){
							ResponseUtil.writeJsonMsg((HttpServletResponse)response,Constants.CODE_LOGIN_FAIL, errorMsg,"");
							return;
						}
					}
				}
				chain.doFilter(request, response);
				return;
			}
			//其他外部接口验证
			if(otherInterfaceUrlSet.contains(value)){
				//接口方式验证
				if(!checkInterfaceUrlMethod(hreq)){
					ResponseUtil.writeJsonMsg((HttpServletResponse)response, Constants.CODE_METHOD_ERROR, Constants.CODE_METHOD_ERROR_MSG,"");
					return;
				}
				chain.doFilter(request, response);
				return;
			}
			//进行session用户与权限验证
			if (!noPermitUrlSet.contains(value) || !interfaceUrlSet.contains(value) || !phoneInterfaceUrlSet.contains(value)) {
				UsersBean user = null;
				user = UserManager.getUser(hreq);
				if (user == null) {
					hreq.getRequestDispatcher(NOLOGIN).forward(request,response);
					return;
				}
				Integer userid = user.getUserid();
				String tmpPath = hreq.getServletPath();

				if (UserManager.isPermit(tmpPath, userid)) {
					chain.doFilter(request, response);
				} else {
					logger.error("no pass and go filter.do");
					hreq.getRequestDispatcher(NOPURVIEW).forward(request, response);
				}
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}
	/**
	 * 安全验证(sql注入)规则
	 */
	protected static boolean sqlValidate(String str, ServletRequest request) {
		str = str.toLowerCase();// 统一转为小写
//		if (str.length() >= 100) {
//			return false;
//		} 
//		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|chr|mid|master|truncate|"
//				+ "char|declare|sitename|net user|xp_cmdshell|like'|and|exec|execute|insert|create|drop|"
//				+ "table|from|grant|use|group_concat|column_name|"
//				+ "information_schema.columns|table_schema|union|where|order|by|"
//				+ "chr|mid|master|truncate|char|declare|or|;|--|+|like|//|#|"
//				+ "\"|&|\'|<|>|0x0d|0x0a|0x08|http://|://|</script>|<script>|alert(|%27|alert";// 过滤掉的sql关键字，可以手动添加
		String badStr = "insert|delete|update|drop|truncate";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) != -1) {
				logger.debug("invalid str:"+str+","+badStrs[i]);
				return true; 
			}
		}

		/*HttpServletRequest hreq = (HttpServletRequest) request;
		Map map = hreq.getParameterMap();
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next().toString();
			if (key.length() >= 100) {
				return false;
			}
			if (key.indexOf("<script>alert") >= 0) {
				return false;
			}
		}*/
		return false;
	}
	/**
	 * 验证安全(sql注入)规则
	 */
	private static boolean checkValidata(HttpServletRequest hreq){
		/*Enumeration params = hreq.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			// 得到参数名
			String name = params.nextElement().toString();
			if(name.toLowerCase().indexOf("password") != -1) {
				continue;
			}
			// 得到参数对应值
			String[] sqlvalue = hreq.getParameterValues(name);

			for (int i = 0; i < sqlvalue.length; i++) {
				sql = sql + sqlvalue[i];
			}
		}
		if(sqlValidate(sql, hreq)){
			return true;
		}else {
			return false;
		}*/
		return false;
	}
	/**
	 * 验证接口方式
	 */
	private boolean checkInterfaceUrlMethod(HttpServletRequest request) throws Exception{
		//验证接口方式
		if(!request.getMethod().equals("POST")){
			return false;
		}
		return true;
	}
	/**
	 * 验证接口用户名与密码
	 */
	private String checkInterfacePwd(HttpServletRequest request) throws Exception{
		String result = "";
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		UsersService usServices = new UsersService(); 
		UsersBean usersBean = usServices.CheckUsersNamePassword(username, password);
		if(	usersBean == null ){
			return Constants.CODE_LOGIN_FAIL_MSG;
		}
		// 判断用户是否可用
		if( usersBean.getStatus() != 1){
			return "用户不可用!";
		}
		// 判断用户所属机构是否撤销
		Organ organ = appOrgan.getOrganByID_Isrepeal(usersBean.getMakeorganid());
		if(organ == null){
			return "用户不可用!";
		}
		return result ;
		
	}
	
	/**
	 * 验证手机接口用户名与密码
	 */
	private String checkPhoneInterfacePwd(HttpServletRequest request) throws Exception{
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		UsersService usServices = new UsersService(); 
		UsersBean usersBean = usServices.CheckUsersNamePassword(username, password);
		String result = "";
		if(	usersBean == null ){
			return Constants.CODE_LOGIN_FAIL_MSG;
		}
		// 判断用户是否可用
		if( usersBean.getStatus() != 1){
			return "用户不可用!";
		}
		// 判断用户所属机构是否撤销
		Organ organ = appOrgan.getOrganByID_Isrepeal(usersBean.getMakeorganid());
		if(organ == null){
			return "用户不可用!";
		}
		return result ;
	}
	
	/**
	 * 验证接口用户名与密码
	 */
	private boolean checkInterfaceIMEI(HttpServletRequest request) throws Exception{
		//IMEI验证方式
		boolean IMEIFlag = true;
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//验证IMEI号
		if(appScanner.findScannerByIMEI(scannerNo) != null){
			IMEIFlag = true;
		}else {
			IMEIFlag = false;
		}
		return  IMEIFlag;
		
	}
	
	/**
	 * 初始化操作
	 */
	private static void initPermitSetUrl() {
		// 语言设置
		noPermitUrlSet.add("/sys/internationalAction.do");
		// 验证码
		noPermitUrlSet.add("/checkNumber.do");
		// 用户登陆
		noPermitUrlSet.add("/sys/userLoginAction.do");
		// 系统初始化
		noPermitUrlSet.add("/initsysAction.do");
		// 更新有效期文件
		noPermitUrlSet.add("/sys/updateUserValAction.do");

		noPermitUrlSet.add("/sales/ajaxFunitAction.do");
		noPermitUrlSet.add("/sys/toSelectOrderColumnAction.do");
		noPermitUrlSet.add("/sys/selectOrderColumnAction.do");
		
		noPermitUrlSet.add("/scanner/checkVersionAction.do");
		//产品验证查询
		noPermitUrlSet.add("/phone/queryFWIdcodeAction.do");
		//官网产品验证查询
		noPermitUrlSet.add("/sys/webQueryFWIdcodeAction.do");
		//公告
		noPermitUrlSet.add("/phone/queryAfficheAction.do");
		//投诉
		noPermitUrlSet.add("/phone/addSuggestionAction.do");
		
		//手机用户注册
		noPermitUrlSet.add("/phone/registerAction.do");
		//发送手机验证码
		noPermitUrlSet.add("/phone/checkMessageAction.do");
		//手机用户找回密码
		noPermitUrlSet.add("/phone/getPwdBackAction.do");
		//手机会员登录
		noPermitUrlSet.add("/phone/memberLoginAction.do");
		noPermitUrlSet.add("/checkVersionAction.do");
		
		noPermitUrlSet.add("/SAP_Interface/SAPData.do");
		noPermitUrlSet.add("/phone/queryNewPassword.do");
		noPermitUrlSet.add("/salesman/queryNewPassword.do"); 
		
		noPermitUrlSet.add("/salesman/checkVersionAction.do"); 
		noPermitUrlSet.add("/phone/checkVersionAction.do"); 
		
		noPermitUrlSet.add("/phone/queryCountryAreaAction.do"); 
		noPermitUrlSet.add("/salesman/queryCountryAreaAction.do"); 
		
		noPermitUrlSet.add("/users/updSelfPwdAction.do"); 
		noPermitUrlSet.add("/users/initSelfPwdAction.do"); 
		
		noPermitUrlSet.add("/common/ajaxGetIdentifyCodeAction.do"); 
		
		noPermitUrlSet.add("/sales/listAreasAction.do"); 
		noPermitUrlSet.add("/users/addUsersApplyAction.do"); 
		noPermitUrlSet.add("/users/ajaxCheckUsersAction.do"); 
		noPermitUrlSet.add("/phone/registerNewUser.do"); 
		
		noPermitUrlSet.add("/salesman/userLoginAction.do"); 
		noPermitUrlSet.add("/phone/userLoginAction.do"); 
		noPermitUrlSet.add("/phone/loginAction.do"); 
		
		noPermitUrlSet.add("/machin/uploadProduceFileAction.do"); 
		noPermitUrlSet.add("/machin/loginAction.do");  
		
		noPermitUrlSet.add("/assistant/trace.do");   
		
//		noPermitUrlSet.add("/scanner/downloadNoBonusProductIdAction.do");
		//手机APP隐私声明更新验证
		noPermitUrlSet.add("/phone/checkPrivacyPolicyAction.do");
		noPermitUrlSet.add("/sys/indexAction.do");
		noPermitUrlSet.add("/secure/aad");
		noPermitUrlSet.add("/sys/initDefaultAction.do");
		
		noPermitUrlSet.add("/report/consumeDetailReportAction.do");
		noPermitUrlSet.add("/warehouse/listTakeBillAction.do");
		noPermitUrlSet.add("/warehouse/addStockAlterMoveAction.do");
		
	}
	
	/**
	 * 初始化操作 
	 */
	private static void initInterfaceSetUrl() {
		// 采集器接口
		//登陆
		interfaceUrlSet.add("/scanner/loginAction.do");
		//机构信息
		interfaceUrlSet.add("/scanner/downloadOrganAction.do");
		//仓库信息
		interfaceUrlSet.add("/scanner/downloadWarehouseAction.do");
		//产品信息
		interfaceUrlSet.add("/scanner/downloadProductAction.do");
		//获取上传日志
		interfaceUrlSet.add("/scanner/downloadIdcodeUploadLog.do");
		//修改密码
		interfaceUrlSet.add("/scanner/updatePasswordAction.do");
		//单据下载
		interfaceUrlSet.add("/scanner/downloadTakeTicketAction.do");
		//单位下载
		interfaceUrlSet.add("/scanner/downloadUnitAction.do");
		//数据上传
		interfaceUrlSet.add("/scanner/uploadIdcodeAction.do");
		//盘库文件上传
		interfaceUrlSet.add("/scanner/uploadStockCheckAction.do");
		//条码盘库单据下载
		interfaceUrlSet.add("/scanner/downloadStockCheckAction.do");
		//APP更新
		interfaceUrlSet.add("/scanner/checkVersionAction.do");
		//不能积分兑换的产品列表
		interfaceUrlSet.add("/scanner/downloadNoBonusProductIdAction.do"); 
		
		//分装厂码替换相关接口 
		interfaceUrlSet.add("/scanner/tollingLoginAction.do"); 
		interfaceUrlSet.add("/scanner/uploadProduceFileAction.do"); 
		
		
//		interfaceUrlSet.add("/warehouse/txtPutTakeTicketAction.do");
//		interfaceUrlSet.add("/common/uploadIdcodeAction.do");
//		interfaceUrlSet.add("/warehouse/txtPutProductIncomeAction.do");
//		interfaceUrlSet.add("/warehouse/getCheckBillIdcodeAction.do");
//		interfaceUrlSet.add("/warehouse/checkBillAction.do");
//		interfaceUrlSet.add("/warehouse/pickBillAction.do");
//		interfaceUrlSet.add("/downloadProductAction.do");
//		interfaceUrlSet.add("/downloadWarehouseAction.do");
//		interfaceUrlSet.add("/uploadIdcodeTimelyAction.do");
//		interfaceUrlSet.add("/common/updateScannerAction.do");
//		interfaceUrlSet.add("/downloadIdcodeUploadLogToScannerAction.do");
//		interfaceUrlSet.add("/scanner/updateScannerAction.do");
//		interfaceUrlSet.add("/scanner/customerManagerAction.do");
//		interfaceUrlSet.add("/scanner/toUploadReceiveIncomeIdcodeAction.do");
//		interfaceUrlSet.add("/scanner/queryIdcodeAction.do");
	}

	/**
	 * 手机接口初始化操作
	 */
	private static void phoneInitInterfaceSetUrl() {
		// 手机接口
		//登陆
		phoneInterfaceUrlSet.add("/phone/loginAction.do");
		//产品追溯查询
		phoneInterfaceUrlSet.add("/phone/queryWlmIdcodeAction.do");
		phoneInterfaceUrlSet.add("/phone/applyWlmIdcodeAction.do");
		
		phoneInterfaceUrlSet.add("/phone/uploadIdcodeAction.do");
		phoneInterfaceUrlSet.add("/phone/userLoginAction.do");
		phoneInterfaceUrlSet.add("/phone/queryBillReceiveAction.do");
		
		phoneInterfaceUrlSet.add("/phone/queryBillReceiveIdcodeAction.do");
		phoneInterfaceUrlSet.add("/phone/delBillReceiveIdcodeAction.do");
		
		phoneInterfaceUrlSet.add("/phone/completeBillReceiveAction.do");
		phoneInterfaceUrlSet.add("/phone/queryBonusAction.do");
		phoneInterfaceUrlSet.add("/phone/addOrganAction.do");
		phoneInterfaceUrlSet.add("/phone/queryOrganAction.do");
		phoneInterfaceUrlSet.add("/phone/queryBonusDetailAction.do");
		phoneInterfaceUrlSet.add("/phone/queryBonusTargetAction.do");
		phoneInterfaceUrlSet.add("/phone/queryProductAction.do");
		phoneInterfaceUrlSet.add("/phone/queryIdcodeUploadLog.do");
		phoneInterfaceUrlSet.add("/phone/queryBonusTargetDetailAction.do");
		phoneInterfaceUrlSet.add("/phone/queryOrganAfficheAction.do");
		phoneInterfaceUrlSet.add("/phone/updPasswordAction.do");
		phoneInterfaceUrlSet.add("/phone/queryNotReadOrganAfficheAction.do");
		phoneInterfaceUrlSet.add("/phone/dealReadOrganAfficheAction.do");
		phoneInterfaceUrlSet.add("/phone/updMobileAction.do");
		phoneInterfaceUrlSet.add("/phone/countBillReceiveAction.do");
		phoneInterfaceUrlSet.add("/phone/dealbackproiftAction.do");
		phoneInterfaceUrlSet.add("/phone/uploadUserImgAction.do");
		phoneInterfaceUrlSet.add("/phone/getIdentifyCodeAction.do");
		phoneInterfaceUrlSet.add("/phone/updAfficeToReadAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryCodeTraceAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryRecAndDisSumAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryRecAndDisSumDetailAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryCustomerSupportAction.do"); 
		phoneInterfaceUrlSet.add("/phone/updCustomerSupportAction.do"); 
		phoneInterfaceUrlSet.add("/phone/addOrUpdSBobusTargetAction.do"); 
		phoneInterfaceUrlSet.add("/phone/updOrganAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryCustomerAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryCustomerDetailAction.do"); 
		phoneInterfaceUrlSet.add("/phone/querySBobusTargetProductAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryBonusConfirmAction.do"); 
		phoneInterfaceUrlSet.add("/phone/updBonusConfirmAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/userLoginAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryOrganAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryBonusAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryBonusDetailAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryRecAndDisSumAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryRecAndDisSumDetailAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryCustomerInfoAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryRebateAction.do");  
		phoneInterfaceUrlSet.add("/salesman/updMobileAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/updPasswordAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/QueryOrganToAuditAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/queryOrganAfficheAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryNotReadOrganAfficheAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/updAfficeToReadAction.do");  
		phoneInterfaceUrlSet.add("/salesman/auditCustomerAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/queryCustomerToAuditAction.do");  
		phoneInterfaceUrlSet.add("/salesman/queryTransferOrganToAddAction.do");  
		phoneInterfaceUrlSet.add("/salesman/addTransferRelationAction.do");  
		phoneInterfaceUrlSet.add("/salesman/queryCustomerInfoFromApiAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/queryProductAction.do");  
		phoneInterfaceUrlSet.add("/salesman/getIdentifyCodeAction.do");  
		phoneInterfaceUrlSet.add("/salesman/queryUsersAction.do");  
		phoneInterfaceUrlSet.add("/salesman/queryBonusListAction.do");  
		phoneInterfaceUrlSet.add("/salesman/queryCustomerToAuditCountAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/queryBonusProductAction.do");  
		
		phoneInterfaceUrlSet.add("/salesman/queryRecAndDisSumProductDetailAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryRecAndDisSumProductDetailAction.do"); 
		
		phoneInterfaceUrlSet.add("/salesman/queryProductNameAction.do"); 
		phoneInterfaceUrlSet.add("/phone/queryProductNameAction.do");
		phoneInterfaceUrlSet.add("/phone/querySBobusTargetYearAction.do"); 
		
		phoneInterfaceUrlSet.add("/salesman/queryStockMoveToAuditAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/auditStockMoveAction.do");
		phoneInterfaceUrlSet.add("/salesman/queryStockMoveToAuditCountAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryAreasAction.do"); 
		
		phoneInterfaceUrlSet.add("/phone/registerNewUser.do"); 
		phoneInterfaceUrlSet.add("/salesman/auditUserAction.do");
		phoneInterfaceUrlSet.add("/salesman/queryUsersToAuditAction.do"); 
		phoneInterfaceUrlSet.add("/salesman/queryUserOrganAction.do"); 
	} 
	/**
	 * 其他接口初始化操作
	 */
	private static void initOtherInterfaceSetUrl() {
		//暗码上传
		otherInterfaceUrlSet.add("/machin/uploadCovertCodeAction.do");
	}
	
	private boolean isValidMobile(String  mobile) {
		if(mobile.length() != 11 || !mobile.matches("[0-9]*")) {
			return false;
		}
		return true;
	}
}
