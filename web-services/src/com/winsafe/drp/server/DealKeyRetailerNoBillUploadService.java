package com.winsafe.drp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.metadata.ErrorType; 
import com.winsafe.drp.server.DealNoBillUploadService.DetailBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.IdcodeUploadListener;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonCode;

public class DealKeyRetailerNoBillUploadService {
	
	private static Logger logger = Logger.getLogger(DealKeyRetailerNoBillUploadService.class);
	protected static String file_encode = "utf-8";
	protected static int defatult_delay = 3;
	
	protected List<Idcode> idcodeList = new ArrayList<Idcode>();
	protected Map<String,Idcode> idcodeMap = new HashMap<String,Idcode>();
	protected Set<String> orgtanIds = new HashSet<String>();
	protected Set<String> productIds = new HashSet<String>();
	protected String userId;
	protected Map<String,Organ> organMap = new HashMap<String, Organ>();
	protected Map<String,Warehouse> warehouseMap = new HashMap<String, Warehouse>();
	protected Map<String,Product> productMap = new HashMap<String, Product>();
	
	protected UsersBean users;
	
	protected static String MEMO_TT = "检货小票-出货";
	protected static String MEMO_SAM_RECEIVE = "发货单签收-入库";
	protected static String MEMO_SM_RECEIVE = "转仓签收-出货";
	protected static String MEMO_OW_RECEIVE = "退货签收-入库";
	protected static String MEMO_PW_RECEIVE = "退回工厂签收-入库";
	
	protected String filepath;
	protected String fileaddress;
	protected int iuid;
	protected String failAddress;
	protected String username;
	
	
	protected String makeorganid=null;
	protected String makeorganname=null;
	protected Integer userid = null;
	protected Integer makedeptid= null;
	
	protected int totalNum = 0; 
	protected int failNum = 0;
	//统计警告数量
	protected int warnNum = 0;
	// 出库机构
	public  String sfromorganid = null;
	// 入库机构
	public String stoorganid = null;
	//错误的类型
//	public static final String ETYPE = "E00005";
	
	private UploadIdcodeBean uploadIdcodeBean = null;
	
	protected UploadCodeRuleService crs = new UploadCodeRuleService();
	protected AppIdcodeUpload appiu = new AppIdcodeUpload();
	protected AppUsers au  = new AppUsers();
	protected OrganService os = new OrganService();
	protected AppIdcode ai = new AppIdcode();
	protected AppFUnit appFunit = new AppFUnit();
	protected AppProductStockpile aps = new AppProductStockpile();
	protected AppProduct ap = new AppProduct();
	protected AppWarehouse aw = new AppWarehouse();
	protected AppOrgan ao = new AppOrgan();
	protected AppTakeTicketIdcode att =new AppTakeTicketIdcode();
	protected AppOlinkMan appOlinkMan = new AppOlinkMan();
	protected AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	protected AppCartonCode appCartonCode = new AppCartonCode();
	
	//保存仓库信息的Map
	protected Map<String,Warehouse> whMap = new HashMap<String, Warehouse>();
	//保存仓库对应机构ID的Map
	protected Map<String,String> wh_OrganIDMap = new HashMap<String, String>();
	//保存仓库对应机构的Map
	protected Map<String,Organ> wh_OrganMap = new HashMap<String, Organ>();
	// 存放错误信息
//	protected Map<Integer,ErrorBean> errorMap = new TreeMap<Integer, ErrorBean>();
	protected List<ErrorBean> errorList = new ArrayList<ErrorBean>();
	//条码集合
	protected Set<String> idcodes = new HashSet<String>();
	//物流码前缀集合
	protected Set<String> pids = new HashSet<String>();
	//仓库ID集合
	protected Set<String> whids = new HashSet<String>();
	//管辖仓库
	protected Map<String,RuleUserWh> ruwMap  = new HashMap<String, RuleUserWh>();
	//业务往来仓库
	protected Map<String,WarehouseVisit> wvMap  = new HashMap<String, WarehouseVisit>();
	//机构联系人
	protected Map<String,Olinkman> lmMap  = new HashMap<String, Olinkman>();
	//工厂仓库权限
	protected Map<String,Warehouse> plantWMap  = new HashMap<String, Warehouse>();
	//警告条码
	protected Set<String> warnCode = new HashSet<String>();
	//扫描日期
	protected Map<String,String> scanDateMap  = new HashMap<String, String>();
	
	/**
	 * 工厂仓库权限验证
	 */
	protected boolean validatePlantAuthority(String warehouseid) throws Exception {
		Warehouse  w= plantWMap.get(warehouseid);
		if(w==null){
			return false;
		}
		return true;
	}
	/**
	 * 业务往来仓库权限验证
	 */
	protected boolean validateWVAuthority(String warehouseid) throws Exception {
		WarehouseVisit  wv= wvMap.get(warehouseid);
		if(wv==null){
			return false;
		}
		return true;
	}
	/**
	 * 管辖仓库权限验证
	 */
	protected boolean validateRUWAuthority(String warehouseid) {
		RuleUserWh ruw = ruwMap.get(warehouseid);
		if(ruw==null){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 更新条码信息
	 * @param organId 
	 */
	protected void updIdcode(String whid, List<TakeTicketIdcode> ttiList, String organId) throws Exception {
		Warehouse w = warehouseMap.get(organId);
		StringBuilder sb= new StringBuilder();
		int idcodeNum = 0;
		for (TakeTicketIdcode idcode : ttiList){
			sb.append(",'"+idcode.getIdcode()+"'");
			idcodeNum ++ ;
			//每5000条与数据库交互一次
			if(idcodeNum % Constants.DB_BULK_SIZE == 0){
				ai.bulkUpdIdcode(w.getMakeorganid(), whid, sb.toString().substring(1));
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0){
			ai.bulkUpdIdcode(w.getMakeorganid(), whid, sb.toString().substring(1));
		}
	}
	
	/**
	 * 获取机构联系人
	 */
	protected Olinkman getOlinkman(String oid) throws Exception{
		Olinkman olinkman = lmMap.get(oid);
		if(olinkman == null){
			List<Olinkman>  list = appOlinkMan.getOlinkmanByCid(oid);
			if(list != null && list.size()>0){
				olinkman = list.get(0);
			}
			lmMap.put(oid, olinkman);
		}
		return olinkman;
	}
	
	
	
	/**
	 * 从数据库中查询文件中的有效条码
	 */
	
	protected Map<String, Idcode> bulkQueryByInIdcode(Set<String> idcodes) {
		Map<String,Idcode> idcodeMap = new HashMap<String, Idcode>();
		if(idcodes != null && idcodes.size() > 0){
			int idcodeNum = 0;
			List<Idcode> idcodeList = new ArrayList<Idcode>();
			StringBuffer idcodeString = new StringBuffer();
			for(String idcode:idcodes){
				idcodeString.append(",'" + idcode +"'");
				idcodeNum ++ ;
				//每5000条与数据库交互一次
				if(idcodeNum % Constants.DB_BULK_SIZE == 0){
					List<Idcode> idcodesTemp = ai.queryByInIdcode(idcodeString.toString().substring(1));
					idcodeList.addAll(idcodesTemp);
					idcodeString.delete(0, idcodeString.length());
				}
			}
			
			if(idcodeString.length()>0){
				List<Idcode> idcodesTemp = ai.queryByInIdcode(idcodeString.toString().substring(1));
				idcodeList.addAll(idcodesTemp);
			}
			
			//构造存在数据库中条码的集合
			for(Idcode ic:idcodeList){
				idcodeMap.put(ic.getIdcode(), ic);
			}
		}
		return idcodeMap;
	}
	/**
	 * 查询仓库对应的机构信息
	* @param whids
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 17, 2013 3:17:09 PM
	 */
	protected void bulkQueryByInOrgan(List<Warehouse> whList) throws Exception {
		if(whList==null){
			return;
		}
		int num = 0;
		StringBuilder sb = new StringBuilder();
		List<Organ> oList = new ArrayList<Organ>();
		for(Warehouse wh:whList){
			sb.append(",'"+wh.getMakeorganid()+"'");
			num++;
			//每5000条与数据库交互一次
			if(num % Constants.DB_BULK_SIZE == 0){
				List<Organ> osTemp = ao.queryByInOrgan(sb.toString().substring(1));
				oList.addAll(osTemp);
				sb.delete(0, sb.length());
			}
		}
		if(sb.length()>0){
			List<Organ> osTemp = ao.queryByInOrgan(sb.toString().substring(1));
			oList.addAll(osTemp);
		}
		
		Map<String,Organ> oid_OrganMap = new HashMap<String, Organ>();
		for(Organ o : oList){
			oid_OrganMap.put(o.getId(), o);
		}
		
		for(Map.Entry<String, String> entry : wh_OrganIDMap.entrySet()){
			wh_OrganMap.put(entry.getKey(), oid_OrganMap.get(entry.getValue()));
		}
	}
	
	/**
	 * 封装产品相关的Map信息
	 */
	protected void generateInfoMap(Set<String> pids) throws Exception {
		if(pids == null){
			return;
		}
		int num=0;
		StringBuilder pidString = new StringBuilder();
		//productList
		List<Product> products = new ArrayList<Product>();
		
		for(String pid : pids){
			pidString.append(",'" + pid +"'");
			num++;
			//每5000条与数据库交互一次
			if(num % Constants.DB_BULK_SIZE == 0){
				List<Product> productsTemp = ap.queryByInProduct(pidString.toString().substring(1));
				products.addAll(productsTemp);
				pidString.delete(0, pidString.length());
			}
		}
		if(pidString.length()>0){
			List<Product> productsTemp = ap.queryByInProduct(pidString.toString().substring(1));
			products.addAll(productsTemp);
		}
		//封装产品对应的产品集合
		for(Product p : products){
			productMap.put(p.getId(), p);
		}
	}
	
	/**
	 * 生成TTIdcode
	 */
	protected void genTakeTicketIdcode(Idcode ic,String ttid) throws Exception, IdcodeException
	{
	    TakeTicketIdcode tti = new TakeTicketIdcode();
//		tti.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		tti.setTtid(ttid);
		tti.setIsidcode(1);
		tti.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		tti.setProductid(ic.getProductid());
		tti.setBatch(ic.getBatch());
		tti.setProducedate(ic.getProducedate());
		tti.setVad(ic.getVad());
		tti.setUnitid(Constants.DEFAULT_UNIT_ID);
		tti.setQuantity(1d);
		tti.setPackquantity(ic.getPackquantity());
		tti.setIdcode(ic.getIdcode());
		tti.setLcode(ic.getLcode());
		tti.setStartno(ic.getStartno());
		tti.setEndno(ic.getEndno());
		tti.setIssplit(0);
		tti.setMakedate(DateUtil.StringToDatetime(scanDateMap.get(ic.getIdcode())));
		EntityManager.save(tti);
	}
	
	/**
	 * 生成检货出库TT单详情
	* @param product
	* @author    
	 * @throws Exception 
	* @CreateTime Jan 18, 2013 3:38:58 PM
	 */
	protected void generateTTDetail(Product product,String ttid,Double quantity,Integer unitid) throws Exception {
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setProductid(product.getId());
		ttd.setProductname(product.getProductname());
		ttd.setSpecmode(product.getSpecmode());
		//产品单位
		ttd.setUnitid(unitid);
		ttd.setUnitprice(0d);
		//物料号
		ttd.setNccode(product.getmCode());
		ttd.setQuantity(quantity);
		//确认数量
		ttd.setRealQuantity(quantity);
		ttd.setBoxnum(0);
		ttd.setScatternum(0d);
		ttd.setTtid(ttid);
		ttd.setIsPicked(1);
		EntityManager.save(ttd);
	}
	/**
	 * 封装错误信息,添加一个警告的错误类型
	 */
//	protected void addErrorInfo(Integer lineNo,String msg,String type) {
//		ErrorBean eb= new ErrorBean();
//		eb.setType(type);
//		eb.setInfo(msg);
//		eb.setErrCode(msg.split(":")[0]);
//		if(uploadIdcodeBean != null) {
//			eb.setIdcode(uploadIdcodeBean.getIdcode());
//		}
//		errorMap.put(lineNo, eb);
//	}
	/**
	 * 封装错误信息,添加一个警告的错误类型
	 */
//	protected void addErrorInfo(Integer lineNo,String msg) {
//		ErrorBean eb= new ErrorBean();
//		eb.setInfo(msg);
//		eb.setErrCode(msg.split(":")[0]);
//		if(uploadIdcodeBean != null) {
//			eb.setIdcode(uploadIdcodeBean.getIdcode());
//		}
//		errorMap.put(lineNo, eb);
//	}
	
	protected void addErrorInfo(Integer lineNo,String msg,ErrorType errType, String proName, String specMode) {
		ErrorBean eb= new ErrorBean();
		eb.setLineNo(lineNo);
		eb.setErrType(errType);
		eb.setProName(proName);
		eb.setSpecMode(specMode);
		eb.setInfo(msg);
		eb.setErrCode(msg.split(":")[0]);
		if(uploadIdcodeBean != null) {
			eb.setIdcode(uploadIdcodeBean.getIdcode());
		}
		errorList.add(eb);
	}
	/**
	 * 封装错误信息
	 */
	protected ArrayList<String> generateErrorInfo(List<ErrorBean> errorList) {
		ArrayList<String> errorMsgList = new ArrayList<String>();
		String destFile = filepath + failAddress;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, false),file_encode));
			
			for(ErrorBean bean : errorList){
				
				String str = "第" + bean.getLineNo() +"行";
				if(bean.getErrType() == ErrorType.WARNING) {
					warnNum++;	
				} else if(bean.getErrType() == ErrorType.FAIL) {
					failNum++;
				}
//				if(bean.getIdcode()!=null){
//					str += "  "+ bean.getIdcode();
//				}
				str+=bean.getProName()+","+bean.getSpecMode()+","+bean.getErrType().getName()+","+"[" + bean.getInfo()+"]";
				bean.setInfo(str);
				//写入文件
				out.write(str);
				out.write("\r\n");
				errorMsgList.add(str);
			}
			addfailMsgToDB(errorList);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
		//errorList.add("成功 "+(totalNum-failNum)+" 条,失败"+failNum+"条");
		return errorMsgList;
	}
	
	private void addfailMsgToDB(Collection<ErrorBean> errorBeans) throws Exception {
		IdcodeUpload idcodeUpload = IdcodeUploadListener.getIdcodeUpload(iuid);
		String uploadWarehouse = null;
		if(uploadIdcodeBean != null) {
			uploadWarehouse = uploadIdcodeBean.getOutwarehouseid();
		}
		if(idcodeUpload != null) {
			appIdcodeUploadLog.addIdcodeUploadLogs(errorBeans ,idcodeUpload, uploadWarehouse, null);
		}
	}
	/**
	 * 获取条码
	 */
	protected Idcode getIdcode(String idcode) throws Exception{
		Idcode ic = idcodeMap.get(idcode);
		if(ic == null){
			ic = ai.getIdcodeByCode(idcode);
			idcodeMap.put(idcode, ic);
		}
		return ic;
	}
	/**
	 * 判断并去掉采集器上传的条码的bom
	 */
	protected String dealuploadIdcode(String uploadIdcode) {
		byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] buffBytes;
		try {
			buffBytes = uploadIdcode.getBytes(file_encode);
			if (buffBytes[0] == bom[0] && buffBytes[1] == bom[1] && buffBytes[2] == bom[2]) {
				uploadIdcode = new String(buffBytes, 3, buffBytes.length - 3, file_encode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uploadIdcode;
	}
	/**
	 * 获取用户相关信息
	* @param username
	* @throws Exception
	* @author    
	* @CreateTime Jan 17, 2013 3:54:06 PM
	 */
	protected void getUserInfo(String username) throws Exception {
		Users u = au.getUsers(username);
		if(u!=null){
			makeorganid = u.getMakeorganid();
			userid = u.getUserid();
			makedeptid= u.getMakedeptid();
			makeorganname = os.getOrganName(makeorganid);
		}
	}
	
	
	/**
	 * 更新条码上传日志 成功失败数量
	* @author    
	* @CreateTime Jan 11, 2013 9:58:50 AM
	 */
	protected void updLogNum() throws Exception{
	   appiu.updNum(iuid, 2, totalNum-failNum-warnNum, failNum,warnNum,failAddress,sfromorganid,stoorganid);
	}
	/**
	 * 根据条码汇总批次数量
	 * @return
	 */
	protected Map<String, Double> collectIdcode(List<TakeTicketIdcode> ttiList){
		Map<String, Double> idcodeBatchMap = new HashMap<String, Double>();
		for(TakeTicketIdcode tti : ttiList){
			if(tti == null) {
				continue;
			}
			String pid = tti.getProductid();
			String batch = tti.getBatch();
			String key = pid + "_" + batch;
			if(StringUtil.isEmpty(batch)){
				continue;
			}
			Double quantity = idcodeBatchMap.get(key);
			if(quantity == null){
				idcodeBatchMap.put(key, tti.getQuantity());
			}else {
				idcodeBatchMap.put(key, quantity + tti.getQuantity());
			}
		}
		return idcodeBatchMap;
	}
	
	
	/**
	 * map中的数量增加
	 */
	protected void countMapAdd(Map<String, Double> map,String key,Double value){
		Double val = map.get(key);
		if(val == null){
			val = 0d;
		}
		if(map.keySet().contains(key)){
			map.put(key, map.get(key)+value);
		}else {
			map.put(key, value);
		}
	}
	
	
	/**
	 * 获取产品
	 */
	protected Product getProduct(String pid) throws Exception{
		Product product = productMap.get(pid);
		if(product == null){
			product = ap.getProductByID(pid);
			productMap.put(pid, product);
		}
		return product;
	}
	
	protected List<Product> bulkQueryProduct(Set<String> productIds) throws Exception {
		List<Product> whList = new ArrayList<Product>();
		if(productIds != null && productIds.size() > 0){
			int num = 0;
			StringBuilder whidString = new StringBuilder();
			for(String whid:productIds){
				whidString.append(",'" + whid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<Product> whsTemp = ap.queryByInProduct(whidString.substring(1));
					whList.addAll(whsTemp);
					whidString.delete(0, whidString.length());
				}
			}
			
			if(whidString.length()>0){
				List<Product> whsTemp = ap.queryByInProduct(whidString.substring(1));
				whList.addAll(whsTemp);
			}

			//构造存在数据库中的仓库集合
			for(Product wh : whList){
				productMap.put(wh.getId(), wh);
			}
		}
		
		return whList;
		
	}
	

	protected Set<String> dealTxtFile(List<UploadIdcodeBean> idcodeBeanList,String scanType) throws Exception{
		// 条码文件
		File file = new File(filepath + fileaddress);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), file_encode));
			String uploadidcode;
			
			int lineNo = 1;
			// 通过循环得到该TXT文档的所有内容
			while ((uploadidcode = br.readLine()) != null) {
				//首先去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				// 获取无单上传对像
				JSONObject  uploadJsonObj = JSONObject.fromString(uploadidcode) ;
//				UploadIdcodeBean bean = crs.getNoBillSamUploadIdcode(uploadJsonObj);
				UploadIdcodeBean bean = (UploadIdcodeBean)JSONObject.toBean(uploadJsonObj, UploadIdcodeBean.class);
				//设置行号
				bean.setFileLineNo(lineNo++);
				//扫描类型为D 代表已删除条码
				if("D".equals(bean.getScanType())){
					continue;
				}
				//记录上传总数量
				totalNum++;
				//用户不匹配
				// 对于24位的箱码处理,先去箱码表中查找到对应的箱码
				if(bean.getIdcode().length() == Constants.CARTON_OLD_LENGTH){
					CartonCode cartonCode = appCartonCode.getByOutPin(bean.getIdcode());
					if(cartonCode != null){
						bean.setOutMpinCode(bean.getIdcode());
						bean.setIdcode(cartonCode.getCartonCode());
					}
				}
				
				//判断条码格式
				if(StringUtil.isEmpty(bean.getIdcode())){
					addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00107, bean.getIdcode()),ErrorType.FAIL,"","");
					continue;
				}
				if(scanType.equals(bean.getScanType())){
					//保存有效条码 
					idcodes.add(bean.getIdcode());
					orgtanIds.add(bean.getFromOrganId());
					orgtanIds.add(bean.getToOrganId());
					userId = bean.getUserId();
					productIds.add(bean.getProductId());
					//保存入库仓库
//					whids.add(bean.getInwarehouseid());
					//保存出库仓库
//					whids.add(bean.getOutwarehouseid());
					//保存产品物流码前缀
//					pids.add(bean.getLcode());
				}
				//条码扫描日期
				scanDateMap.put(bean.getIdcode(), bean.getScanDate());
				//保存到beanList中
				idcodeBeanList.add(bean);
			}
			return idcodes;
		}catch (Exception e) {
			logger.error("",e);
			throw e;
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
	}
	
	protected List<Warehouse> bulkQueryByInWarehouse(Set<String> whids) {
		List<Warehouse> whList = new ArrayList<Warehouse>();
		if(whids != null && whids.size() > 0){
			int num = 0;
			StringBuilder whidString = new StringBuilder();
			for(String whid:whids){
				whidString.append(",'" + whid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<Warehouse> whsTemp = aw.queryByInOrganId(whidString.toString().substring(1));
					whList.addAll(whsTemp);
					whidString.delete(0, whidString.length());
				}
			}
			
			if(whidString.length()>0){
				List<Warehouse> whsTemp = aw.queryByInOrganId(whidString.toString().substring(1));
				whList.addAll(whsTemp);
			}

			//构造存在数据库中的仓库集合
			for(Warehouse wh : whList){
				warehouseMap.put(wh.getMakeorganid(), wh);
			}
		}
		
		return whList;
	}
	
	protected List<Organ> bulkQueryOrgan(Set<String> orgtanIds) {
		List<Organ> whList = new ArrayList<Organ>();
		if(orgtanIds != null && orgtanIds.size() > 0){
			int num = 0;
			StringBuilder whidString = new StringBuilder();
			for(String whid:orgtanIds){
				whidString.append(",'" + whid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<Organ> whsTemp = ao.getOrganByIds(whidString.substring(1));
					whList.addAll(whsTemp);
					whidString.delete(0, whidString.length());
				}
			}
			
			if(whidString.length()>0){
				List<Organ> whsTemp = ao.getOrganByIds(whidString.substring(1));
				whList.addAll(whsTemp);
			}

			//构造存在数据库中的仓库集合
			for(Organ wh : whList){
				organMap.put(wh.getId(), wh);
			}
		}
		
		return whList;
		
	}
	
	protected List<Idcode> bulkQueryIdcode(Set<String> idcodes) {
		List<Idcode> whList = new ArrayList<Idcode>();
		if(idcodes != null && idcodes.size() > 0){
			int num = 0;
			StringBuilder whidString = new StringBuilder();
			for(String whid:idcodes){
				whidString.append(",'" + whid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<Idcode> whsTemp = ai.queryByInIdcode(whidString.substring(1)); 
					whList.addAll(whsTemp);
					whidString.delete(0, whidString.length());
				}
			}
			
			if(whidString.length()>0){
				List<Idcode> whsTemp = ai.queryByInIdcode(whidString.substring(1)); 
				whList.addAll(whsTemp);
			}

			//构造存在数据库中的仓库集合
			for(Idcode wh : whList){
				idcodeMap.put(wh.getIdcode(), wh);
			}
		}
		
		return whList;
	}
	

	
	/**
	 * 验证仓库是否存在
	 */
	protected boolean validateWarehouse(String whid) throws Exception {
		Warehouse w = warehouseMap.get(whid);
		if(w ==null){
			return false;
		}
		return true;
	}
	
	protected boolean validateOrgan(String whid) throws Exception {
		Organ w = organMap.get(whid);
		if(w ==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 对上传后的所有条码进行分类整理
	* @param idcodeBeanList
	 */
	protected Map<String,List<UploadIdcodeBean>> sortUploadIdcodeBean(List<UploadIdcodeBean> idcodeBeanList) {
		Map<String,List<UploadIdcodeBean>> whMap = new LinkedHashMap<String,List<UploadIdcodeBean>>();
		for(UploadIdcodeBean bean : idcodeBeanList){
			
			String warehousekey = bean.getFromOrganId()+"_"+bean.getToOrganId();
			//得到出入库仓库对应的所有条码
			List<UploadIdcodeBean> idcodeList = whMap.get(warehousekey);
			
			//如果仓库对应的条码集合不存在 
			if(idcodeList == null){
				//条码bean集合
				idcodeList = new ArrayList<UploadIdcodeBean>();
				idcodeList.add(bean);
			}else{
				idcodeList.add(bean);
			}
			
			//将更改后的仓库对应的条码map放回Map中
			whMap.put(warehousekey, idcodeList);
		}
		return whMap;
	}
	

	/**
	 * 生成检货小票TT
	 */
	protected String generateTTBill(String fromOrganId,String toOrganId,String omid,Integer bSort) throws Exception {
		String otoid = null;
		String otoname = null;
		Organ inOrgan = organMap.get(toOrganId);
		Organ oto = organMap.get(fromOrganId);
		Warehouse outWarehouse = warehouseMap.get(fromOrganId);
		Warehouse inWarehouse = warehouseMap.get(toOrganId);
		if(oto!=null){
			otoid= oto.getId();
			otoname = oto.getOrganname();
		}
		
		String inOid = null;
		String inOname = null;
		if(inOrgan != null){
			inOid = inOrgan.getId();
			inOname = inOrgan.getOrganname();
		}
		
		String linkman = null;
		String tel = null;
		Olinkman olinkman = getOlinkman(inOid);
		if(olinkman != null){
			linkman = olinkman.getName();
			tel = olinkman.getMobile();
		}
		
		//生成TT单
		TakeTicket tt = new TakeTicket();
		String ttid = MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT");
		tt.setId(ttid);
		tt.setBillno(omid);
		tt.setBsort(bSort);
		tt.setInwarehouseid(inWarehouse.getId());
		tt.setIsaudit(1);
		tt.setAuditid(users.getUserid());
		tt.setAuditdate(Dateutil.getCurrentDate());
		tt.setIsblankout(0);
		tt.setIsread(0);
		tt.setMakeid(users.getUserid());
		tt.setMakedate(Dateutil.getCurrentDate());
		tt.setWarehouseid(outWarehouse.getId());
		tt.setMakeorganid(users.getMakeorganid());
		tt.setOid(otoid);
		tt.setOname(otoname);
		tt.setPrinttimes(0);
		tt.setInOid(inOid);
		tt.setInOname(inOname);
		tt.setRlinkman(linkman);
		tt.setTel(tel);
		tt.setIsNoBill(YesOrNo.YES.getValue());
		EntityManager.save(tt);
		return ttid;
	}
	
	/**
	 * 签收单据
	 */
	protected void autoReceiveBill(String warehouseid,List<TakeTicketIdcode> ttiList,Map<String, Double> productCountMap,String ttid,String memo) throws Exception {
		Map<String,Double> psMap = collectIdcode(ttiList);
		
		// 只获取有条码的产品数量
//		Map<String, Double> productCountMap = filterMap(productDetailMap,idcodeCountMap, ttiList);
		
		for(String pid : productCountMap.keySet()){
//			DetailBean detailBean = productDetailMap.get(pid);
			//总数量
			Double quantity = appFunit.getQuantity(pid, Constants.DEFAULT_UNIT_ID, productCountMap.get(pid));
			Integer unitid = getProduct(pid).getSunit();
			
			//循环加库存
			for(Map.Entry<String,Double> entry : psMap.entrySet()){
				String[] arr = entry.getKey().split("_");
				double batchQuantity = entry.getValue();
				// 换算为最小包装单位
				batchQuantity = appFunit.getQuantity(pid, Constants.DEFAULT_UNIT_ID, batchQuantity);
				
				String productid = arr[0];
				String batch ="";
				if(arr.length>1){
					batch = arr[1];
				}
				if(productid.equals(pid)){
					//更新库存量
					aps.inProductStockpileTotalQuantity(productid,unitid,batch, batchQuantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT, ttid, memo);
					quantity = quantity - batchQuantity;
				}
			}
			if(quantity != 0){
				//更新库存量
				aps.inProductStockpileTotalQuantity(pid,unitid,null, quantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT, ttid, memo);
			}
		}
	}
	

	/**
	 * 复核单据
	 */
	protected void autoAuditBill(String warehouseid,List<TakeTicketIdcode> ttiList,Map<String, Double> productCountMap,String ttid,String memo) throws Exception {
		AppProductStockpile  aps = new AppProductStockpile();
		Map<String,Double> psMap = collectIdcode(ttiList);
		// 只获取有条码的产品数量
//		Map<String, Double> productCountMap = filterMap(productDetailMap,idcodeCountMap, ttiList);
		// 扣减库存
		for(String pid : productCountMap.keySet()){
//			DetailBean detailBean = productDetailMap.get(pid);
			//总数量
			Double quantity = appFunit.getQuantity(pid, Constants.DEFAULT_UNIT_ID, productCountMap.get(pid));
			Integer unitid = getProduct(pid).getSunit();
			
			// 循环扣减库存
			for (Map.Entry<String, Double> entry : psMap.entrySet()) {
				String[] arr = entry.getKey().split("_");
				double batchQuantity = entry.getValue();
				// 换算为最小包装单位
				batchQuantity = appFunit.getQuantity(pid, Constants.DEFAULT_UNIT_ID, batchQuantity);
				
				String productid = arr[0];
				String batch = "";
				if (arr.length > 1) {
					batch = arr[1];
				}
				if(productid.equals(pid)){
					aps.outProductStockpile(pid, unitid, batch, batchQuantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT,
							ttid, "检货小票-出货");
					quantity = quantity - batchQuantity;
				}
			}
			if(quantity != 0){
				aps.outProductStockpile(pid, unitid, null, quantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT,
						ttid, "检货小票-出货");
			}
			
		}
	}
}

