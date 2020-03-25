package com.winsafe.drp.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap; 
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.webservice.util.MySecureSocketFactory;


/**
 * HTTP工具类
 * 
 * @author lixiangyang
 * 
 */
public class HttpUtils {

	private static Logger logger = Logger.getLogger(HttpUtils.class);
	/**
	 * 定义编码格式 UTF-8
	 */
	public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

	/**
	 * 定义编码格式 GBK
	 */
	public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private static final String EMPTY = "";

	private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 25000;

	private static int socketTimeOut = 25000;

	private static int maxConnectionPerHost = 20;

	private static int maxTotalConnections = 20;

	private static HttpClient client;
	
	private static Properties sysPro = null;
	
	private static ProxyHost proxyHostCfg = null;
	
	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		client = new HttpClient(connectionManager);
		
		// 获取配置文件,配置代理
		try {
			logger.debug("设置代理");
			sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			String proxyHost = sysPro.getProperty("proxy_host");
			String proxyUser = sysPro.getProperty("proxy_user");
			String proxyPwd = sysPro.getProperty("proxy_pwd");
			//解码 
			if(!StringUtil.isEmpty(proxyPwd)
					&& !StringUtil.isEmpty(proxyUser)) {
				proxyPwd = Encrypt.getSecret(proxyPwd, 2);
				proxyUser = Encrypt.getSecret(proxyUser, 2);
			}
			Integer proxyPort = Integer.parseInt(sysPro.getProperty("proxy_port"));
			
			proxyHostCfg = new ProxyHost(proxyHost, proxyPort);
			client.getHostConfiguration().setProxyHost(proxyHostCfg);
			client.getParams().setAuthenticationPreemptive(true);
			if(!StringUtil.isEmpty(proxyPwd)
					&& !StringUtil.isEmpty(proxyUser)) {
				client.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUser, proxyPwd));
			}
			logger.debug("代理信息: " +proxyHost+proxyPort+proxyUser+proxyPwd);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * GET方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLGet(String url, Map<String, String> params, String enc) {
		addProxy();
		String response = EMPTY;
//		GetMethod getMethod = null;
//		StringBuffer strtTotalURL = new StringBuffer(EMPTY);
//
//		if (strtTotalURL.indexOf("?") == -1) {
//			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
//		} else {
//			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
//		}
//		logger.debug("GET请求URL = \n" + strtTotalURL.toString());

		try {
			AddressUtils autil = new AddressUtils();
			response = autil.getAddresses("ip=" + params.get("ip"), enc, url);
//			getMethod = new GetMethod(strtTotalURL.toString());
//			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
//			// 执行getMethod
//			int statusCode = client.executeMethod(getMethod);
//			if (statusCode == HttpStatus.SC_OK) {
//				response = getMethod.getResponseBodyAsString();
//			} else {
//				logger.debug("响应状态码 = " + getMethod.getStatusCode());
//			}
		} catch (UnsupportedEncodingException e) {
			logger.error("发生不支持编码异常:HttpUtils.URLGet()" + e.getLocalizedMessage());
			e.printStackTrace();
//		} finally {
//			if (getMethod != null) {
//				getMethod.releaseConnection();
//				getMethod = null;
//			}
		}

		return response;
	}

	/**
	 * 据Map生成URL字符串
	 * 
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	private static String getUrl(Map<String, String> map, String valueEnc) {

		if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (map.containsKey(key)) {
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				try {
					str = URLEncoder.encode(str, valueEnc);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}

		return (strURL);
	}
	
	/**
	 * GET方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLGetForSMS(String url, Map<String, String> params) {
//		Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
//		Protocol.registerProtocol("https", easyhttps);
		addProxy();
		setProtocol();
		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params));
		}
		logger.debug("GET请求URL = \n" + strtTotalURL.toString());

		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				logger.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}

		return response;
	}
	

	/**
	 * 据Map生成URL字符串
	 * 
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	private static String getUrl(Map<String, String> map) {

		if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (map.containsKey(key)) {
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}

		return (strURL);
	}
	
	public static boolean upload(String url, File file){
//		Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
//		Protocol.registerProtocol("https", easyhttps);
		long time = System.currentTimeMillis();
		
		logger.debug("--------start send file----------");
		logger.debug("--------file size = "+file.length()/1024+"KB----------");
		clearProxy();
		setProtocol();
        PostMethod filePost = new PostMethod(url);
        try {
            //通过以下方法可以模拟页面参数提交
//            filePost.setParameter("fileType", fileType.getValue().toString());
            
            Part[] parts = { new FilePart("uploadedFile", file) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            
            client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
            
            int status = client.executeMethod(filePost);
            
            logger.debug("--------spend time = "+(System.currentTimeMillis()-time)+"ms----------");
            logger.debug("--------end send file----------");
            
            if (status == HttpStatus.SC_OK) {
            	String result = new String(filePost.getResponseBody(),URL_PARAM_DECODECHARSET_UTF8);
            	logger.debug(result);
            	if(result.indexOf("-") != -1) {
            		return false;
            	}
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        	logger.error("上传文件发生异常", e);
        } finally {
            filePost.releaseConnection();
        }
        return false;
    }
	
	public static boolean uploadToCSSI(String url, String data){
		long time = System.currentTimeMillis();
		logger.debug("--------start uploadToCSSI----------");
		addProxy();
		setProtocol();
        PostMethod httppost = new PostMethod(url);
        try {
        	RequestEntity requestEntity = new StringRequestEntity(data,"text/plain","UTF-8");
			httppost.setRequestHeader("Content-Type", "application/json;charset=utf8");
			httppost.setRequestEntity(requestEntity);  
            client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
            int status = client.executeMethod(httppost);
            logger.debug("--------spend time = "+(System.currentTimeMillis()-time)+"ms----------");
            logger.debug("--------end uploadToCSSI file----------");
            
            if (status == HttpStatus.SC_OK) {
            	String result = new String(httppost.getResponseBody(),URL_PARAM_DECODECHARSET_UTF8);
            	logger.debug(result);
            	JSONObject jo = JSONObject.parseObject(result);
            	if(jo.containsKey("result")&&"aaa".equals(jo.getString("result"))) {
            		return true;
            	}
            }
        } catch (Exception e) {
        	logger.error("uploadToCSSI异常", e);
        } finally {
        	httppost.releaseConnection();
        }
        return false;
    }
	
	public static boolean authenticateUser(String url, String userName, String password, String apiKey){
		long time = System.currentTimeMillis();
		logger.debug("--------start authenticateUser----------");
		clearProxy();
		setProtocol();
        PostMethod httppost = new PostMethod(url);
        try {
        	
        	JSONObject jsonParam = new JSONObject();
            jsonParam.put("cwid", userName);
            jsonParam.put("password", password);
        	
        	RequestEntity requestEntity = new StringRequestEntity(jsonParam.toString(),"application/json","UTF-8");
        	httppost.setRequestHeader("x-api-key", apiKey);
			httppost.setRequestHeader("Content-Type", "application/json");
			httppost.setRequestEntity(requestEntity);  
            client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
            int status = client.executeMethod(httppost);
            logger.error("--------spend time = "+(System.currentTimeMillis()-time)+"ms----------");
            logger.error("--------end authenticateUser----------");
            logger.error("--------result status----------"+status+" "+ new String(httppost.getResponseBody(),URL_PARAM_DECODECHARSET_UTF8));
            if (status == HttpStatus.SC_OK) {
            	String result = new String(httppost.getResponseBody(),URL_PARAM_DECODECHARSET_UTF8);
            	logger.error(result);
            	if("true".equals(result)) {
            		return true;
            	}
            }
        } catch (Exception e) {
        	WfLogger.error("authenticateUser异常", e);
        	//logger.error("authenticateUser异常", e);
        } finally {
        	httppost.releaseConnection();
        }
        return false;
    }
	
	public static String getIdsFromGroup(String url, Map<String, String> params, String apiKey) {
		clearProxy();
		setProtocol();
		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params));
		}
		logger.debug("GET请求URL = \n" + strtTotalURL.toString());

		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("x-api-key", apiKey);
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				logger.error("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return response;
	}
	
	
	private static void addProxy() {
		client.getHostConfiguration().setProxyHost(proxyHostCfg);
	}
	
	private static void clearProxy() {
		client.getHostConfiguration().setProxyHost(null);
	}

	private static void setProtocol() {
		 //声明
       ProtocolSocketFactory fcty = new MySecureSocketFactory();
       //加入相关的https请求方式
       Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
	}
	
	public static void main(String[] args) {
		HttpUtils.URLGetForSMS("https://www.baidu.com", new HashMap<String,String>());
	}
	
}
