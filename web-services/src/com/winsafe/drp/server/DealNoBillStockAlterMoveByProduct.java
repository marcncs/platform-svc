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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveIdcode;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.drp.util.UploadIdcodeUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 无单一级经销商出库
* @Title: DealNoBillStockAlterMove.java
* @version:
 */
public class DealNoBillStockAlterMoveByProduct {
	
	
	private static Logger logger = Logger.getLogger(DealNoBillStockAlterMoveByProduct.class);

	private String filepath;
	private String fileaddress;
	private int iuid;
	private String failAddress;
	private String username;
	
	// dao层
	private AppIdcodeUpload appiu = new AppIdcodeUpload();
	private UploadCodeRuleService crs = new UploadCodeRuleService();
	private AppIdcode ai = new AppIdcode();
	private AppICode al = new AppICode();
	private AppProduct ap = new AppProduct();
	private AppTakeTicketIdcode att =new AppTakeTicketIdcode();
	private AppProductStockpile aps = new AppProductStockpile();
	private AppWarehouse aw = new AppWarehouse();
	private AppOrgan ao = new AppOrgan();
	private AppUsers au  = new AppUsers();
	private OrganService os = new OrganService();
	
	private int totalNum = 0;
	private int failNum = 0;
	private String batch;
	private int unitid;
	
	private String makeorganid=null;
	private String makeorganname=null;
	private Integer userid = null;
	private Integer makedeptid= null;
	
	//条码集合
	private Set<String> idcodes = new HashSet<String>();
	private Map<String,Idcode> idcodeMap = new HashMap<String, Idcode>();
	//物流码前缀集合
	private Set<String> pids = new HashSet<String>();
	//仓库集合
	private Set<String> whids = new HashSet<String>();
	
	/**
	 * 存放错误信息
	 */
	private Map<Integer,ErrorBean> errorMap = new TreeMap<Integer, ErrorBean>();
	
	//保存仓库信息的Map
	private Map<String,Warehouse> whMap = new HashMap<String, Warehouse>();
	//保存产品信息的Map
	private Map<String,Product> productMap = new HashMap<String, Product>();
	private Map<String,Product> lcodeMap = new HashMap<String, Product>();
	//保存仓库对应机构ID的Map
	private Map<String,String> wh_OrganIDMap = new HashMap<String, String>();
	//保存仓库对应机构的Map
	private Map<String,Organ> wh_OrganMap = new HashMap<String, Organ>();
	
	private Map<String,RuleUserWh> ruwMap  = new HashMap<String, RuleUserWh>();
	private Map<String,WarehouseVisit> wvMap  = new HashMap<String, WarehouseVisit>();
	
	private Map<String,Olinkman> lmMap  = new HashMap<String, Olinkman>();
	
	public DealNoBillStockAlterMoveByProduct(){}

	public DealNoBillStockAlterMoveByProduct(String filepath, String fileaddress, int iuid,String username)
	{
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}
	
	public ArrayList<String> deal() throws Exception {
		try {
			//获取用户信息
			getUserInfo(this.username);
			wvMap = UploadIdcodeUtil.getWVAuthority(userid);
			ruwMap = UploadIdcodeUtil.getRUWAuthority(userid);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			dealTxtFile(idcodeBeanList);
			//缓存相关数据
			cacheData();
			//对上传后的产品进行分类整理
			Map<String,Map<String,Double>> productMap = getProductDetail(idcodeBeanList);
			//对上传后的所有条码进行分类整理
			Map<String,Map<String,List<UploadIdcodeBean>>> idcodeMap = sortUploadIdcodeBean(idcodeBeanList);
			//处理分类后的map
			dealResultMap(productMap,idcodeMap);
			//封装错误信息
			return generateErrorInfo(errorMap);
			
		} catch (Exception e) {
			appiu.updNum(iuid, 2, 0, totalNum, failAddress);
			HibernateUtil.commitTransaction();
			throw e;
		}
	}
	private Map<String,Map<String,Double>> getProductDetail(List<UploadIdcodeBean> idcodeBeanList){
		Map<String,Map<String,Double>> productMap = new HashMap<String, Map<String,Double>>();
		for(UploadIdcodeBean bean : idcodeBeanList){
			String warehousekey = bean.getOutwarehouseid()+"_"+bean.getInwarehouseid();
			Map<String,Double> pidMap = productMap.get(warehousekey);
			//如果仓库对应的Map不存在 
			if(pidMap == null){
				pidMap = new HashMap<String, Double>();
				pidMap.put(bean.getLcode(), bean.getSum());
			}
		}
		return productMap;
	}
	/**
	 * 处理上传文件
	* @return
	 */
	private Set<String> dealTxtFile(List<UploadIdcodeBean> idcodeBeanList) {
		// 条码文件
		File file = new File(filepath + fileaddress);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String uploadidcode;
			
			int lineNo = 1;
			// 通过循环得到该TXT文档的所有内容
			while ((uploadidcode = br.readLine()) != null) {
				//首先去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				// 获取无单上传对像
				JSONObject  uploadJsonObj = JSONObject.fromString(uploadidcode) ;
				UploadIdcodeBean bean = crs.getNoBillSamUploadIdcode(uploadJsonObj);
				//设置行号
				bean.setFileLineNo(lineNo++);
				//扫描类型为D 代表已删除条码
				if("D".equals(bean.getScanType())){
					continue;
				}
				//记录上传总数量
				totalNum++;
				//用户不匹配
				
				//判断条码格式
				if(StringUtil.isEmpty(bean.getIdcode())){
					addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00107, bean.getIdcode()));
					continue;
				}
				if(Constants.SCAN_TYPE_OM_NOBILL.equals(bean.getScanType())){
					//保存有效条码 
					idcodes.add(bean.getIdcode());
					//保存入库仓库
					whids.add(bean.getInwarehouseid());
					//保存出库仓库
					whids.add(bean.getOutwarehouseid());
					//保存产品物流码前缀
					pids.add(bean.getLcode());
				}
				//保存到beanList中
				idcodeBeanList.add(bean);
			}
			return idcodes;
		}catch (Exception e) {
			logger.error("",e);
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
		return null;
	}
	/**
	 * 缓存数据
	 */
	private void cacheData() throws Exception{
		//封装产品相关的Map信息
		generateInfoMap(pids);
		//查询相关的仓库信息
		List<Warehouse> whList = bulkQueryByInWarehouse(whids);
		//查询仓库对应机构的信息
		bulkQueryByInOrgan(whList);
		//得到数据库中存在的条码集合
		//idcodeMap = bulkQueryByInIdcode(idcodes);
	}
	
	/**
	 * 从数据库中查询文件中的有效条码
	* @param idcodes
	* @return
	* @author wenping   
	* @CreateTime Jan 10, 2013 1:34:47 PM
	 */
	private Map<String, Idcode> bulkQueryByInIdcode(Set<String> idcodes) {
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
	 * 得到相关仓库信息
	* @param whids
	* @author wenping   
	* @CreateTime Jan 14, 2013 11:52:10 AM
	 */
	private List<Warehouse> bulkQueryByInWarehouse(Set<String> whids) {
		List<Warehouse> whList = new ArrayList<Warehouse>();
		if(whids != null && whids.size() > 0){
			int num = 0;
			StringBuilder whidString = new StringBuilder();
			for(String whid:whids){
				whidString.append(",'" + whid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<Warehouse> whsTemp = aw.queryByInWarehouse(whidString.toString().substring(1));
					whList.addAll(whsTemp);
					whidString.delete(0, whidString.length());
				}
			}
			
			if(whidString.length()>0){
				List<Warehouse> whsTemp = aw.queryByInWarehouse(whidString.toString().substring(1));
				whList.addAll(whsTemp);
			}

			//构造存在数据库中的仓库集合
			for(Warehouse wh : whList){
				whMap.put(wh.getId(), wh);
				wh_OrganIDMap.put(wh.getId(), wh.getMakeorganid());
			}
		}
		
		return whList;
	}
	

	/**
	 * 查询仓库对应的机构信息
	* @param whids
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 17, 2013 3:17:09 PM
	 */
	private void bulkQueryByInOrgan(List<Warehouse> whList) throws Exception {
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
	* @param icodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 15, 2013 1:30:26 PM
	 */
	private void generateInfoMap(Set<String> pids) throws Exception {
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
	 * 对上传后的所有条码进行分类整理
	* @param idcodeBeanList
	* @CreateTime Jan 10, 2013 9:29:41 AM
	 */
	private Map<String,Map<String,List<UploadIdcodeBean>>> sortUploadIdcodeBean(List<UploadIdcodeBean> idcodeBeanList) {

		Map<String,Map<String,List<UploadIdcodeBean>>> whMap = new LinkedHashMap<String,Map<String,List<UploadIdcodeBean>>>();
		for(UploadIdcodeBean bean : idcodeBeanList){
			
			String warehousekey = bean.getOutwarehouseid()+"_"+bean.getInwarehouseid();
			//得到出入库仓库对应的Map
			Map<String,List<UploadIdcodeBean>> pidMap = whMap.get(warehousekey);
			
			//如果仓库对应的Map不存在 
			if(pidMap == null){
				pidMap = new LinkedHashMap<String,List<UploadIdcodeBean>>();
				//条码bean集合
				List<UploadIdcodeBean> beanList = new ArrayList<UploadIdcodeBean>();
				beanList.add(bean);
				pidMap.put(bean.getLcode(), beanList);
			}else{
				//仓库对应的Map存在 
				List<UploadIdcodeBean> pListBean = pidMap.get(bean.getLcode());
				if( pListBean==null){
					pListBean= new ArrayList<UploadIdcodeBean>();
				}
				pListBean.add(bean);
				//将更改后的条码bean集合放入Map中
				pidMap.put(bean.getLcode(), pListBean);
			}
			
			//将更改后的仓库对应的map放回Map中
			whMap.put(warehousekey, pidMap);
		}
		
		return whMap;
	}
	
	/**
	 * 处理分类后的Map 
	* @param idcodeMap
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 10:18:18 AM
	 */
	@SuppressWarnings("unchecked")
	private void dealResultMap(Map<String,Map<String,Double>> billDetailMap,Map<String, Map<String, List<UploadIdcodeBean>>> idcodeMap) throws Exception {
		
		// 根据productMap产生单据
		for(String warehouseKey : billDetailMap.keySet()){
			//存放条码集合
			Set<String> idcodeSet = new HashSet<String>();
			// outw_inw
			String[] arr = warehouseKey.split("_");
			//出库仓库
			String outwarehouseid = arr[0];
			//入库仓库
			String inwarehouseid = arr[1];
			
			//验证出库仓库
			boolean isOutExist = validateWarehouse(outwarehouseid);
			//验证出库仓库权限
			boolean hasRUWAuth = validateRUWAuthority(outwarehouseid);
			//验证入库仓库
			boolean isInExist = validateWarehouse(inwarehouseid);
			//验证入库仓库权限
			boolean hasWVAuth = validateWVAuthority(inwarehouseid);
			// 
			if(!(isOutExist && isInExist)){
				
			}
			
			
			//生成订购单据
			String omid = generateBill(outwarehouseid,inwarehouseid);
			//生成TT单据
			String ttid = generateTTBill(outwarehouseid,inwarehouseid,omid);
			
			Map<String,Double> productDetailMap = billDetailMap.get(warehouseKey);
			for(String pid : productDetailMap.keySet()){
				//生成单据详情
				generateDetail(productMap.get(pid),omid,new Double(productDetailMap.get(pid)));
				//生成TT单据详情
				generateTTDetail(productMap.get(pid),ttid,new Double(productDetailMap.get(pid)));
				//生成码记录
				// 根据单据号获取对应的条码
				Map<String, List<UploadIdcodeBean>> productIdcodeMap = idcodeMap.get(warehouseKey);
				if(productIdcodeMap != null){
					List<UploadIdcodeBean> idcodeBeans = productIdcodeMap.get(pid);
					for(UploadIdcodeBean idcodeBean : idcodeBeans){
						//判断权限
						boolean isAuth = checkAuthority(idcodeBean, isOutExist, hasRUWAuth, isInExist, hasWVAuth);
						if(!isAuth){
							continue;
						}
						//验证条码重复
						String idcode = idcodeBean.getIdcode();
						boolean isSuccess = idcodeSet.add(idcode);
						if(!isSuccess){
							addErrorInfo(idcodeBean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00106,idcode));
							continue;
						}
						//校验条码
						boolean ispass = validateIdcode(idcodeBean);
						if(!ispass){
							continue;
						}
						Idcode ic = getIdcode(idcodeBean.getIdcode());
						genTakeTicketIdcode(ic, ttid);
						
					}
				}
				
				List<TakeTicketIdcode> ttiList = att.getTakeTicketIdcodeByttid(ttid);
				//自动复核单据
				autoAuditBill(outwarehouseid,ttiList,ttid);
				//复制条码
				saveStockAlterMoveIdcode(ttiList,omid);
				//自动签收单据
				autoReceiveBill(inwarehouseid,ttiList,ttid);
				//更改条码信息
				updIdcode(inwarehouseid,ttiList);
				//更新条码上传日志
				updLogNum();
			}
			
		}
		
		
		
		
		
		
		
		for(Map.Entry<String, Map<String, List<UploadIdcodeBean>>> entry : idcodeMap.entrySet()){
			Set<String> idcodeSet = new HashSet<String>();
			//得到出入库仓库id的key
			String out_in_whid = entry.getKey();
			String[] arr = out_in_whid.split("_");
			//出库仓库
			String outwarehouseid = arr[0];
			//入库仓库
			String inwarehouseid = arr[1];
			try {
				//验证出库仓库
				boolean isOutExist = validateWarehouse(outwarehouseid);
				//验证出库仓库权限
				boolean hasRUWAuth = validateRUWAuthority(outwarehouseid);
				//验证入库仓库
				boolean isInExist = validateWarehouse(inwarehouseid);
				//验证入库仓库权限
				boolean hasWVAuth = validateWVAuthority(inwarehouseid);
				
				if(isOutExist && isInExist){
					//生成订购单据
					String omid = generateBill(outwarehouseid,inwarehouseid);
					//生成TT单据
					String ttid = generateTTBill(outwarehouseid,inwarehouseid,omid);
					//得到仓库下的所有产品
					Map<String, List<UploadIdcodeBean>> pMap = entry.getValue();
					//单据详情是否存在
					boolean hasDetail = false;
					
					//循环产品信息
					for(Map.Entry<String, List<UploadIdcodeBean>> pentry : pMap.entrySet()){
						//得到产品物流码前缀
						String pid = pentry.getKey();
//						//验证产品
//						boolean ispExist = validateProduct(pid);
						
						//得到产品对应的所有条码
						List<UploadIdcodeBean> beanList = pentry.getValue();
						
						Double quantity = 0d;
						
						//循环产品下的所有条码信息
						for(UploadIdcodeBean bean: beanList){
							//判断权限
							boolean isAuth = checkAuthority(bean, isOutExist, hasRUWAuth, isInExist, hasWVAuth);
							if(!isAuth){
								continue;
							}
							//验证条码重复
							String idcode = bean.getIdcode();
							boolean isSuccess = idcodeSet.add(idcode);
							if(!isSuccess){
								addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00106,idcode));
								continue;
							}
							//校验条码
							boolean ispass = validateIdcode(bean);
							if(!ispass){
								continue;
							}
							Idcode ic = getIdcode(bean.getIdcode());
							genTakeTicketIdcode(ic, ttid);
							quantity += ic.getPackquantity() ;
						}
						if(quantity > 0){
							//生成单据详情
							generateDetail(productMap.get(pid),omid,quantity);
							//生成TT单据详情
							generateTTDetail(productMap.get(pid),ttid,quantity);
							hasDetail = true;	
						}
					}
					//如果单据下有详情，则提交事务
					if(hasDetail){
						List<TakeTicketIdcode> ttiList = att.getTakeTicketIdcodeByttid(ttid);
						//自动复核单据
						autoAuditBill(outwarehouseid,ttiList,ttid);
						//复制条码
						saveStockAlterMoveIdcode(ttiList,omid);
						//自动签收单据
						autoReceiveBill(inwarehouseid,ttiList,ttid);
						//更改条码信息
						updIdcode(inwarehouseid,ttiList);
						//更新条码上传日志
						updLogNum();
					}else{
						HibernateUtil.rollbackTransaction();
					}
				}
			} catch (Exception e) {
				logger.error("", e);
				HibernateUtil.rollbackTransaction();
				throw e;
			}
		}
	}

	/**
	 * 验证权限
	 */
	private boolean checkAuthority(UploadIdcodeBean bean,boolean isOutExist,boolean hasRUWAuth,boolean isInExist,boolean hasWVAuth) throws Exception{
		if(!isOutExist){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00101, bean.getOutwarehouseid()));
			return false;
		}
		
		if(!hasRUWAuth){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00102, username,bean.getOutwarehouseid()));
			return false;
		}
		
		if(!isInExist){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00103,bean.getInwarehouseid()));
			return false;
		}
		
		if(!hasWVAuth){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00104,username,bean.getInwarehouseid()));
			return false;
		}
		return true;
	}

	/**
	 * 验证仓库
	* @param whid
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 11, 2013 11:37:16 AM
	 */
	private boolean validateWarehouse(String whid) throws Exception {
		Warehouse w = whMap.get(whid);
		if(w ==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 入库仓库权限验证
	 */
	private boolean validateWVAuthority(String inwarehouseid) throws Exception {
		WarehouseVisit  wv= wvMap.get(inwarehouseid);
		if(wv==null){
			return false;
		}
		return true;
	}

	/**
	 * 出库仓库权限验证
	 */
	private boolean validateRUWAuthority(String outwarehouseid) {
		RuleUserWh ruw = 	ruwMap.get(outwarehouseid);
		if(ruw==null){
			return false;
		}
		return true;
	}
	/**
	 * 验证产品
	 */
	private boolean validateProduct(Idcode ic,UploadIdcodeBean bean) throws Exception {
		Product p = productMap.get(ic.getProductid());
		if(p==null){
			p = ap.getProductByID(ic.getProductid());
			if(p==null){
				addErrorInfo(bean.getFileLineNo(),"E00139:对应的产品不存在");
				return false;
			}
			productMap.put(ic.getProductid(), p);
		}
		return true;
	}
	
	/**
	 * 复核单据
	 */
	private void autoAuditBill(String warehouseid,List<TakeTicketIdcode> ttiList,String ttid) throws Exception {
		AppProductStockpile  aps = new AppProductStockpile();
		Map<String,Double> psMap = new HashMap<String, Double>();
		//整理TakeTicketIdcode
		for (TakeTicketIdcode idcode : ttiList){
			String key = idcode.getProductid()+"_"+idcode.getBatch();
			Double quantity = psMap.get(key);
			if(quantity==null){
				psMap.put(key, idcode.getPackquantity());
			}else{
				quantity+=idcode.getPackquantity();
				psMap.put(key, quantity);
			}
		}
		// 循环扣减库存
		for (Map.Entry<String, Double> entry : psMap.entrySet()) {
			String[] arr = entry.getKey().split("_");
			double quantity = entry.getValue();

			String productid = arr[0];
			String batch = "";
			if (arr.length > 1) {
				batch = arr[1];
			}
			aps.outProductStockpile(productid, productMap.get(productid).getSunit(), batch, quantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT,
					ttid, "检货小票-出货");
		}
	}

	/**
	 * 签收单据
	 */
	private void autoReceiveBill(String warehouseid,List<TakeTicketIdcode> ttiList,String ttid) throws Exception {
		ProductStockpile ps = null;
		Map<String,Double> psMap = new HashMap<String, Double>();
		//整理TakeTicketIdcode
		for (TakeTicketIdcode idcode : ttiList){
			String key = idcode.getProductid()+"_"+idcode.getBatch();
			Double quantity = psMap.get(key);
			if(quantity==null){
				psMap.put(key, idcode.getPackquantity());
			}else{
				quantity+=idcode.getPackquantity();
				psMap.put(key, quantity);
			}
		}
		
		//循环加库存
		for(Map.Entry<String,Double> entry : psMap.entrySet()){
			ps = new ProductStockpile();
			String[] arr = entry.getKey().split("_");
			double quantity = entry.getValue();
			
			String productid = arr[0];
			String batch ="";
			if(arr.length>1){
				batch = arr[1];
			}
			Product p = productMap.get(productid);
			
			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile", 0, "")));
			ps.setProductid(productid);
			ps.setCountunit(p.getSunit());
			ps.setBatch(batch);
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			//设置产品检验状态为合格
			ps.setVerifyStatus(1);
			//新增库存记录（如果要新增的库存记录已经存在则不作新增操作，否则新增，注意，新增时先将库存量设置为零，因为下面的更新操作会加库存量）
			aps.addProductByPurchaseIncome2(ps);
			//更新库存量
			aps.inProductStockpileTotalQuantity(ps.getProductid(),ps.getCountunit(),ps.getBatch(), quantity, ps.getWarehouseid(), ps.getWarehousebit(), ttid, "订购入库-入库");
		}
	}
	
	/**
	 * 更新条码信息
	* @param whid
	* @param riiList
	* @author    
	 * @throws Exception 
	* @CreateTime Jan 11, 2013 11:31:03 AM
	 */
	private void updIdcode(String whid, List<TakeTicketIdcode> ttiList) throws Exception {
		Warehouse w = whMap.get(whid);
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
 * 生成订购单详情
* @param riid
* @author    
 * @throws Exception 
 * @throws NumberFormatException 
* @CreateTime Jan 10, 2013 5:12:44 PM
 */
	private void generateDetail(Product p,String omid,Double quantity) throws NumberFormatException, Exception {
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(omid);
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
		// 产品单位取product中的小包装单位
		smd.setUnitid(p.getSunit());
		smd.setUnitprice(0d);
		smd.setQuantity(quantity);
		// 箱数
		smd.setBoxnum(0);
		// 散数
		smd.setScatternum(0d);
		smd.setTakequantity(0d);
		smd.setSubsum(0d);

		EntityManager.save(smd);
	}
	
	/**
	 * 生成检货出库TT单详情
	* @param product
	* @author    
	 * @throws Exception 
	* @CreateTime Jan 18, 2013 3:38:58 PM
	 */
	private void generateTTDetail(Product product,String ttid,Double quantity) throws Exception {
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setProductid(product.getId());
		ttd.setProductname(product.getProductname());
		ttd.setSpecmode(product.getSpecmode());
		ttd.setUnitid(product.getSunit());
		ttd.setUnitprice(0d);
		ttd.setQuantity(quantity);
		ttd.setBoxnum(0);
		ttd.setScatternum(0d);
		ttd.setTtid(ttid);
		ttd.setIsPicked(1);
		EntityManager.save(ttd);
	}

	/**
	 * 生成订购单
	* @param whid
	* @return
	* @throws Exception
	* @author    
	* @CreateTime Jan 10, 2013 4:43:46 PM
	 */
	@SuppressWarnings("unchecked")
	private String generateBill(String outwarehouseid,String inwarehouseid) throws Exception {
		Organ inOrgan = wh_OrganMap.get(inwarehouseid);
		String receiveorganid = null;
		String receiveorganidname = null;
		if(inOrgan!=null){
			receiveorganid = inOrgan.getId();
			receiveorganidname= inOrgan.getOrganname();
		}
		
		String linkman = null;
		String tel = null;
		
		Object[] arr = UploadIdcodeUtil.getLinkMan(lmMap, wh_OrganIDMap, inwarehouseid);
		linkman = (String)arr[0];
		tel =  (String)arr[1];
		lmMap = (Map)arr[2];
		
		StockAlterMove sm = new StockAlterMove();
		String smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM");
		sm.setId(smid);
		sm.setMovedate(Dateutil.addSecond2Date(3, Dateutil.getCurrentDate()));
		sm.setOutwarehouseid(outwarehouseid);
		sm.setIsmaketicket(0);
		sm.setIsreceiveticket(0);
		sm.setMakeorganid(makeorganid);
		sm.setMakeorganidname(makeorganname);
		sm.setMakedeptid(makedeptid);
		sm.setMakeid(userid);
		sm.setMakedate(Dateutil.addSecond2Date(3,Dateutil.getCurrentDate()));
		sm.setInwarehouseid(inwarehouseid);
		sm.setIsaudit(1);
		sm.setAuditid(userid);
		sm.setAuditdate(Dateutil.addSecond2Date(3,Dateutil.getCurrentDate()));
		
		sm.setOlinkman(linkman);
		sm.setOtel(tel);
		
		sm.setIsshipment(1);
		sm.setShipmentid(userid);
		sm.setShipmentdate(Dateutil.addSecond2Date(3,Dateutil.getCurrentDate()));
		sm.setIstally(0);
		sm.setIsblankout(0);
		sm.setTakestatus(1);
		sm.setReceiveorganid(receiveorganid);
		sm.setReceiveorganidname(receiveorganidname);
		sm.setIscomplete(1);
		sm.setReceiveid(userid);
		sm.setReceivedate(Dateutil.addSecond2Date(3,Dateutil.getCurrentDate()));
		
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
		.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
		sm.setTakestatus(0);
		sm.setKeyscontent(keyscontent.toString());
		
		EntityManager.save(sm);
		
		return smid;
	}
	/**
	 * 生成检货小票TT
	* @author    
	 * @throws Exception 
	 */
	private String generateTTBill(String outwarehouseid,String inwarehouseid,String omid) throws Exception {
		String otoid = null;
		String otoname = null;
		Organ oto = wh_OrganMap.get(inwarehouseid);
		if(oto!=null){
			otoid= oto.getId();
			otoname = oto.getOrganname();
		}
		//生成TT 单
		TakeTicket tt = new TakeTicket();
		String ttid = MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT");
		tt.setId(ttid);
		tt.setBillno(omid);
		tt.setBsort(1);
		tt.setEquiporganid(wh_OrganIDMap.get(outwarehouseid));
		tt.setInwarehouseid(inwarehouseid);
		tt.setIsaudit(1);
		tt.setAuditid(userid);
		tt.setAuditdate(Dateutil.getCurrentDate());
		tt.setIsblankout(0);
		tt.setIsread(0);
		tt.setMakeid(userid);
		tt.setMakedate(Dateutil.getCurrentDate());
		tt.setWarehouseid(outwarehouseid);
		tt.setMakeorganid(makeorganid);
		tt.setOid(otoid);
		tt.setOname(otoname);
		tt.setPrinttimes(0);
		EntityManager.save(tt);
		return ttid;
	}
	
	/***
	 * 校验条码
	* @param bean
	* @param existIdcodes
	* @author    
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 2:22:48 PM
	 */
	private boolean validateIdcode(UploadIdcodeBean bean) throws Exception {
		String idcode = bean.getIdcode();
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00107, idcode));
			return false;
		}
		Idcode ic = getIdcode(bean.getIdcode());
		//条码不存在
		if(ic == null){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00003, idcode));
			return false;
		}
		//条码不可用
		if(ic.getIsuse() == 0){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00004, idcode));
			return false;
		}
		if(!ic.getWarehouseid().equals(bean.getOutwarehouseid())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00005, idcode));
			return false;
		}
		return true;
	}

	/**
	 * 封装错误信息
	* @param bean
	* @param idcode
	* @param msg
	 */
	private void addErrorInfo(Integer lineNo,String msg) {
		ErrorBean eb= new ErrorBean();
		eb.setInfo(msg);
		errorMap.put(lineNo, eb);
	}
	

	/**
	 * 封装错误信息
	* @param errorMap
	 */
	private ArrayList<String> generateErrorInfo(Map<Integer, ErrorBean> errorMap) {
		ArrayList<String> errorList = new ArrayList<String>();
		String destFile = filepath + failAddress;
		BufferedWriter out = null;
		try {
			if(!errorMap.isEmpty()){
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, true)));
				
				for(Map.Entry<Integer, ErrorBean> entry : errorMap.entrySet()){
					failNum++;
					
					String str = "第" + entry.getKey() +"行";
					ErrorBean bean = entry.getValue();
					if(bean.getIdcode()!=null){
						str += "  "+ bean.getIdcode();
					}
					str+="[" + bean.getInfo()+"]";
					//写入文件
					out.write(str);
					out.write("\r\n");
					
					//errorList.add(str);
				}
			}
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
		return errorList;
	}
	
	/**
	 * 更新条码上传日志 成功失败数量
	* @author    
	* @CreateTime Jan 11, 2013 9:58:50 AM
	 */
	private void updLogNum() throws Exception{
	   appiu.updNum(iuid, 2, totalNum-failNum, failNum, failAddress);
	}
	
	/**
	 * 获取用户相关信息
	* @param username
	* @throws Exception
	* @author    
	* @CreateTime Jan 17, 2013 3:54:06 PM
	 */
	private void getUserInfo(String username) throws Exception {
		Users u = au.getUsers(username);
		if(u!=null){
			makeorganid = u.getMakeorganid();
			userid = u.getUserid();
			makedeptid= u.getMakedeptid();
			makeorganname = os.getOrganName(makeorganid);
		}
	}
	
	/**
	 * 判断并去掉采集器上传的条码的bom
	 */
	private String dealuploadIdcode(String uploadIdcode) {
		byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] buffBytes;
		try {
			buffBytes = uploadIdcode.getBytes("utf-8");
			if (buffBytes[0] == bom[0] && buffBytes[1] == bom[1] && buffBytes[2] == bom[2]) {
				uploadIdcode = new String(buffBytes, 3, buffBytes.length - 3, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uploadIdcode;
	}
	
	/**
	 * 生成TTIdcode
	* @author    
	* @CreateTime Jan 17, 2013 5:35:09 PM
	 */
	private void genTakeTicketIdcode(Idcode ic,String ttid) throws Exception, IdcodeException
	{
	    TakeTicketIdcode tti = new TakeTicketIdcode();
//		tti.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		tti.setTtid(ttid);
		tti.setIsidcode(1);
		tti.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		tti.setProductid(ic.getProductid());
		tti.setBatch(batch);
		tti.setProducedate(ic.getProducedate());
		tti.setVad(ic.getVad());
		tti.setUnitid(unitid);
		tti.setQuantity(1d);
		tti.setPackquantity(ic.getPackquantity());
		tti.setIdcode(ic.getIdcode());
		tti.setLcode(ic.getLcode());
		tti.setStartno(ic.getStartno());
		tti.setEndno(ic.getEndno());
		tti.setIssplit(0);
		tti.setMakedate(DateUtil.getCurrentDate());
		EntityManager.save(tti);
	}
	
	/**
	 * 保存StockAlterMoveIdcode
	* @param tti
	* @author    
	* @CreateTime Jan 18, 2013 4:30:50 PM
	 */
	private void saveStockAlterMoveIdcode(List<TakeTicketIdcode> idlist,String omid) throws Exception {
		for ( TakeTicketIdcode tti : idlist ){
			StockAlterMoveIdcode idcode = new StockAlterMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
//			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_idcode", 0, "")));
			idcode.setSamid(omid);
			idcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			EntityManager.save(idcode);
		}
	}
	
	/**
	 * 获取条码
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	private Idcode getIdcode(String idcode) throws Exception{
		Idcode ic = idcodeMap.get(idcode);
		if(ic == null){
			ic = ai.getIdcodeByCode(idcode);
			idcodeMap.put(idcode, ic);
		}
		return ic;
	}
}
