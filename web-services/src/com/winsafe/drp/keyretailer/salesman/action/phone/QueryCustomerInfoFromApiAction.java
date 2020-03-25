package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryCustomerInfoFromApiAction extends Action {
	
	private AppSalesMan appSalesMan = new AppSalesMan();
	private AppCountryArea aca = new AppCountryArea();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsersBean loginUsers = null;
		try {
			AppUsers appUsers = new AppUsers();
			String username = request.getParameter("Username");
			String wideType = request.getParameter("WideType");
			String keyword = request.getParameter("Keyword");
			String province = request.getParameter("Province");
			String city = request.getParameter("City");
			String county = request.getParameter("County");
			String pageIndexStr = request.getParameter("PageIndex");
			int pageIndex = (pageIndexStr == null || pageIndexStr.trim().length() == 0) ? 1 : Integer.parseInt(pageIndexStr.trim());
			pageIndex = pageIndex > 0 ? pageIndex : 1;
			// 判断用户是否存在
			loginUsers = appUsers.getUsersBeanByLoginname(username);
			List<Map<String, String>> list = appSalesMan.getCustomerInfoFromLccal(keyword, wideType, province, city, county);
			CustomerInfo ci = new CustomerInfo();
			if (list == null || list.size() == 0) {
				// 接口参数初始化
				Properties sysProperties = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
				String apiUrl = sysProperties.getProperty("QichachaApiUrl");
				String appKey = sysProperties.getProperty("QichachaAppKey");
				String secretKey = sysProperties.getProperty("QichachaSecretKey");
				// 调用接口
				String timespan = String.valueOf((System.currentTimeMillis() / 1000)); //精确到秒的Unix时间戳
				String token = MD5BigFileUtil.md5(appKey + timespan + secretKey).toUpperCase();
				Map<String, String> params = new HashMap<String, String>();
		        params.put("key", appKey);
		        params.put("keyword", keyword);
		        params.put("pageSize", "20");
		        params.put("pageIndex", String.valueOf(pageIndex));
		        /*if ("0".equals(wideType)) {
			        params.put("type", "opername"); //法人名维度查询
		        } else {
			        params.put("type", "name"); //公司名维度查询
		        }*/
		        WfLogger.debug("provinceCode:"+province);
		        WfLogger.debug("cityCode:"+city);
		        if(!StringUtil.isEmpty(province) || !StringUtil.isEmpty(city)) {
					Map<String,String> areasMap = aca.getAreaMapByIDs(province,city);
		        	params.put("provinceCode", areasMap.get(province));
		        	params.put("cityCode", areasMap.get(city));
		        }
				String result = sendGet(apiUrl, params, token, timespan);
				//String result = "{\"OrderNumber\":\"ECI2018121209555567892407\",\"Paging\":{\"PageSize\":10,\"PageIndex\":1,\"TotalRecords\":3},\"Result\":[{\"KeyNo\":\"17b059e87440d0c7a70cd3e5837187f6\",\"Name\":\"寿光市炳祥果蔬有限公司\",\"OperName\":\"王炳祥A\",\"StartDate\":\"2013-08-27 00:00:00\",\"Status\":\"在营（开业）企业\",\"No\":\"370783200055937\",\"CreditCode\":\"91370783076984421H\"},{\"KeyNo\":\"17b059e87440d0c7a70cd3e5837187f6\",\"Name\":\"寿光市炳祥果蔬有限公司\",\"OperName\":\"王炳祥B\",\"StartDate\":\"2014-08-27 00:00:00\",\"Status\":\"在营（开业）企业\",\"No\":\"370783200055937\",\"CreditCode\":\"91370783076984421H\"},{\"KeyNo\":\"17b059e87440d0c7a70cd3e5837187f6\",\"Name\":\"寿光市炳祥果蔬有限公司\",\"OperName\":\"王炳祥C\",\"StartDate\":\"2015-08-27 00:00:00\",\"Status\":\"在营（开业）企业\",\"No\":\"370783200055937\",\"CreditCode\":\"91370783076984421H\"}],\"Status\":\"200\",\"Message\":\"查询成功\"}";
				JSONObject jsonobject = JSONObject.fromObject(result);
				String apiReturnStatus = jsonobject.getString("Status");
				if ("200".equals(apiReturnStatus)) {
					JSONArray jsonArray = jsonobject.getJSONArray("Result");
					JSONObject pageInfo = jsonobject.getJSONObject("Paging");
					ci = getCustomerInfoFromApi(jsonArray, pageInfo);
				}
			} else {
				ci = getCustomerInfoFromLccal(list, pageIndex);
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ci, loginUsers.getUserid(), "APP_RI", "客户信息匹配", true);
		} catch (Exception e) {
			WfLogger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null, loginUsers.getUserid(), "APP_RI", "客户信息匹配", true);
		}
		
		return null;
	}
    
	private CustomerInfo getCustomerInfoFromLccal(List<Map<String, String>> list, int pageIndex) throws Exception {
		int totalRecords = (list == null) ? 0 : list.size();
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setPageIndex(1);
		customerInfo.setTotalPage(1);
		customerInfo.setTotalRecords(totalRecords);
		List<CustomerDetailInfo> cdiList = new ArrayList<CustomerDetailInfo>();
		for (int i = 0; i < totalRecords; i++) {
			CustomerDetailInfo cdi = new CustomerDetailInfo();
			MapUtil.mapToObject(list.get(i), cdi);
			cdiList.add(cdi);
		}
		customerInfo.setCdiList(cdiList);
		return customerInfo;
	}
	
	private CustomerInfo getCustomerInfoFromApi(JSONArray jsonArray, JSONObject pageInfo) throws Exception {
		int totalPage = (pageInfo.getInt("TotalRecords") + pageInfo.getInt("PageSize") - 1) / pageInfo.getInt("PageSize");
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setPageIndex(pageInfo.getInt("PageIndex"));
		customerInfo.setTotalPage(totalPage);
		customerInfo.setTotalRecords(pageInfo.getInt("TotalRecords"));
		List<CustomerDetailInfo> cdiList = new ArrayList<CustomerDetailInfo>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = (JSONObject)jsonArray.get(i);
			CustomerDetailInfo cdi = new CustomerDetailInfo();
			cdi.setId("qichacha");
			cdi.setName(object.getString("Name"));
			cdi.setOperName(object.getString("OperName"));
			cdi.setStartDate(object.getString("StartDate"));
			cdi.setStatus(object.getString("Status"));
			cdi.setCreditNo(object.getString("No"));
			cdiList.add(cdi);
		}
		customerInfo.setCdiList(cdiList);
		return customerInfo;
	}
	
	public static String sendGet(String apiUrl, Map<String, String> param, String token, String timespan) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = "";
        try {
        	StringBuffer sb = new StringBuffer();
            String apiUrlName = apiUrl + "?" + urlencode(param);
            WfLogger.debug("---apiUrlName:"+apiUrlName);
            URL url = new URL(apiUrlName);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Token", token);
            conn.setRequestProperty("Timespan", timespan);
            conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
            conn.setUseCaches(false);
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
            WfLogger.debug("---result:"+rs);
        } catch (IOException e) {
        	WfLogger.error("", e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
	
	// 将map型转为请求参数型（get访问时使用）
    @SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (sb.toString().endsWith("&")) {
        	return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
        	return sb.toString();
        }
    }
	
	public class CustomerInfo {
		int pageIndex;
		int totalPage;
		int totalRecords;
		List<CustomerDetailInfo> cdiList;
		public int getPageIndex() {
			return pageIndex;
		}
		public void setPageIndex(int pageIndex) {
			this.pageIndex = pageIndex;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public List<CustomerDetailInfo> getCdiList() {
			return cdiList;
		}
		public void setCdiList(List<CustomerDetailInfo> cdiList) {
			this.cdiList = cdiList;
		}
		public int getTotalRecords() {
			return totalRecords;
		}
		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}
	}
    
	public class CustomerDetailInfo {
		private String id;
		private String name;
		private String operName;
		private String startDate;
		private String status;
		private String creditNo;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCreditNo() {
			return creditNo;
		}
		public void setCreditNo(String creditNo) {
			this.creditNo = creditNo;
		}
	}
	
	public static void main(String[] ags) throws Exception {
		String timespan = String.valueOf((System.currentTimeMillis()/1000));//精确到秒的Unix时间戳
		String token = MD5BigFileUtil.md5("20aac8991897402393ccf4983abaae57" + timespan + "E360220DE247183424082FA979D38190").toUpperCase();
		Map<String, String> params = new HashMap<String, String>();
        params.put("key", "20aac8991897402393ccf4983abaae57");
        params.put("keyword", "春福");
//        params.put("keyword", "371329600272657");
        params.put("provinceCode", "SD");
        params.put("cityCode", "13");
//        params.put("type", "name");
        /*if(!StringUtil.isEmpty(province)) {
        	params.put("provinceCode", province);
        }
        if(!StringUtil.isEmpty(city)) {
        	params.put("cityCode", city);
        }*/
        System.out.println(sendGet("http://api.qichacha.com/ECIV4/Search", params, token, timespan));
//        System.out.println(sendGet("http://api.qichacha.com/ECIV4/GetDetailsByName", params, token, timespan));
        
	}
}
