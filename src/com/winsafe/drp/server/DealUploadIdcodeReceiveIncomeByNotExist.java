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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceiveIncomeIdcode;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ReceiveIncome;
import com.winsafe.drp.dao.ReceiveIncomeDetail;
import com.winsafe.drp.dao.ReceiveIncomeIdcode;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadIdcodeUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 经销商入库签收条码上传
* @Title: DealUploadIdcodeReceiveIncome.java
* @author: wenping 
* @CreateTime: Jan 9, 2013 4:20:26 PM
* @version:
 */
public class DealUploadIdcodeReceiveIncomeByNotExist {

	private String filepath;
	private String fileaddress;
	private int iuid;
	private String failAddress;
	private String username;
	
	private AppIdcodeUpload appiu = new AppIdcodeUpload();
	private CodeRuleService crs = new CodeRuleService();
	private AppIdcode ai = new AppIdcode();
	private AppICode al = new AppICode();
	private AppProduct ap = new AppProduct();
	private AppReceiveIncomeIdcode aii =new AppReceiveIncomeIdcode();
	private AppProductStockpile aps = new AppProductStockpile();
	private AppWarehouse aw = new AppWarehouse();
	private AppTakeTicketIdcode att =new AppTakeTicketIdcode();
	private AppFUnit af = new AppFUnit();
	
	private int boxNum = 0;
	private int scatterNum = 0;
	
	private String riid;
	
	private int totalNum = 0;
	private int failNum = 0;
	private Integer userid = null;
	
	private String batch;
	
	private Map<Integer,ErrorBean> errorMap = new TreeMap<Integer, ErrorBean>();
	
	//保存仓库信息的Map
	private Map<String,Warehouse> whMap = new HashMap<String, Warehouse>();
	//保存产品信息的Map
	private Map<String,Product> productMap = new HashMap<String, Product>();
	private Map<String,Product> lcodeMap = new HashMap<String, Product>();
	
	private Map<String,RuleUserWh> ruwMap  = new HashMap<String, RuleUserWh>();
	//保存系统中不存在的散码
	private Set<String> notExistIdcodes = new HashSet<String>();
	
	public DealUploadIdcodeReceiveIncomeByNotExist(){}

	public DealUploadIdcodeReceiveIncomeByNotExist(String filepath, String fileaddress, int iuid,String username)
	{
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<String> deal() throws Exception {
		System.out.println(new Date().toLocaleString());
		try {
			//获取用户信息
			getUserInfo(this.username);
			ruwMap = UploadIdcodeUtil.getRUWAuthority(userid);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			Set<String> idcodes = dealTxtFile(idcodeBeanList);
			
			//得到数据库中存在的条码集合
			Map<String,Idcode> existIdcodes = bulkQueryByInIdcode(idcodes);
			
			//补充系统中不存在的条码信息
			addNotExistIdcodes(idcodes,existIdcodes);
			
			//更新UploadIdcodeBean中的Icode信息
			List<ICode> icodes = updUploadIdcodeBeanInfo(idcodeBeanList,existIdcodes);
			
			//封装产品相关的Map信息
			generateInfoMap(icodes);
			
			//对上传后的所有条码进行分类整理
			Map<String,Map<String,List<UploadIdcodeBean>>> resultMap = sortUploadIdcodeBean(idcodeBeanList);
			
			//处理分类后的map
			dealResultMap(resultMap,existIdcodes);
			
			//封装错误信息
			return generateErrorInfo(errorMap);
			
		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * 处理上传文件
	* @return
	* @author wenping   
	* @CreateTime Jan 9, 2013 5:03:39 PM
	 */
	private Set<String> dealTxtFile(List<UploadIdcodeBean> idcodeBeanList) {
		// 条码文件
		File file = new File(filepath + fileaddress);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String uploadidcode;
			updIsDeal();
			
			//List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			
			Set<String> idcodes = new HashSet<String>();
//			//物流码前缀集合
//			Set<String> pids = new HashSet<String>();
			//仓库集合
			Set<String> whids = new HashSet<String>();
			int lineNo = 1;
			// 通过循环得到该TXT文档的所有内容
			while ((uploadidcode = br.readLine()) != null) {
				
				//首先去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				
				UploadIdcodeBean bean = new UploadIdcodeBean();
				
				// 取得扫描类型
				String scanType = crs.getScanType(uploadidcode);	
				// 取得条形码编号
				String idcode = crs.getIdcode(uploadidcode);
				// 调入仓库编号
				 String inwarehouseid = crs.getWarehouseId(uploadidcode);
				 //产品物流码前缀
				 String lcode =crs.getProductId(uploadidcode);
				//获取扫描标识位
				String scanFlag=crs.getScanSign(uploadidcode);
				//得到数量
//				int quantity =Integer.parseInt(crs.getProductCount(uploadidcode));
				//得到用户名
				String username = crs.getUserName(uploadidcode);
				idcode=idcode.toUpperCase();
				bean.setIdcode(idcode);
				bean.setInwarehouseid(inwarehouseid);
				bean.setLcode(lcode);
//				bean.setQuantity(quantity);
				bean.setScanType(scanType);
				bean.setScanFlag(scanFlag);
				bean.setFileLineNo(lineNo++);
				bean.setUsername(username);
				
				//扫描类型为D 代表已删除条码
				if("D".equals(scanType)){
					continue;
				}
				//用户不匹配
				/*if(!this.username.equals(username)){
					continue;
				}*/
				
				//记录上传总数量
				totalNum++;
				
				if(idcode==null || (idcode.length()!=13 && idcode.length()!=16)){
					addErrorInfo(bean,idcode,"E00089:条码格式不正确");
					continue;
				}
				
//				if(idcode!=null && (idcode.length()==13 || idcode.length()==16)){
//					if(Constants.SCAN_TYPE_RI.equals(scanType) && this.username.equals(username)){
//						idcodes.add(idcode);
//					}
//				}
				
				if(Constants.SCAN_TYPE_RI.equals(scanType)){
					//保存有效条码
					idcodes.add(idcode);
//					//保存产品物流码前缀
//					pids.add(lcode);
					//保存入库仓库
					whids.add(inwarehouseid);
				}
				
				//保存到beanList中
				idcodeBeanList.add(bean);
				
//				//只保留与当前用户匹配的条码
//				if(this.username.equals(username)){
//					//保存到beanList中
//					idcodeBeanList.add(bean);
//				}
			}
			
//			//查询相关的产品信息
//			List<ICode> icodes = bulkQueryByInProduct(pids);
			
			//查询相关的仓库信息
			bulkQueryByInWarehouse(whids);
			
			return idcodes;
			
			/*//得到数据库中存在的条码集合
			Map<String,Idcode> existIdcodes = bulkQueryByInIdcode(idcodes);
			
			//对上传后的所有条码进行分类整理
			Map<String,Map<String,List<UploadIdcodeBean>>> resultMap = sortUploadIdcodeBean(idcodeBeanList);
			
			//处理分类后的map
			dealResultMap(resultMap,existIdcodes);
			
			//封装错误信息
			return generateErrorInfo(errorMap);*/
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 更新UploadIdcodeBean的Icode信息
	* @param idcodeBeanList
	* @param existIdcodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 16, 2013 10:39:07 AM
	 */
	private List<ICode> updUploadIdcodeBeanInfo(List<UploadIdcodeBean> idcodeBeanList, Map<String, Idcode> existIdcodes) throws Exception {
		Set<String> pids = new HashSet<String>();
		for(Map.Entry<String, Idcode> entry : existIdcodes.entrySet()){
			Idcode ic = entry.getValue();
			pids.add(ic.getProductid());
		}
		
		List<ICode> icodes =  bulkQueryByInProduct(pids);
		//封装pid对应的Icode信息
		Map<String,String> pid_icodeMap = new HashMap<String, String>();
		for(ICode i : icodes){
			pid_icodeMap.put(i.getProductid(), i.getLcode());
		}
		
		//封装Idcode对应的Icode信息
		Map<String,String> idcode_icodeMap = new HashMap<String, String>();
		for(Map.Entry<String, Idcode> entry : existIdcodes.entrySet()){
			Idcode ic = entry.getValue();
			idcode_icodeMap.put(entry.getKey(), pid_icodeMap.get(ic.getProductid()));
		}
		
		//更新UploadIdcodeBean
		for(UploadIdcodeBean bean : idcodeBeanList){
			bean.setLcode(idcode_icodeMap.get(bean.getIdcode()));
		}
		
		return icodes;
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
	private void bulkQueryByInWarehouse(Set<String> whids) {
		if(whids != null && whids.size() > 0){
			List<Warehouse> whList = new ArrayList<Warehouse>();
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
			}
		}
	}

	/**
	 * 得到相关产品信息
	* @param pids
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 14, 2013 11:52:15 AM
	 */
	private List<ICode> bulkQueryByInProduct(Set<String> pids) throws Exception {
		//IcodeList
		List<ICode> icodes = new ArrayList<ICode>();
		if(pids != null && pids.size() > 0){
			StringBuilder pidString = new StringBuilder();
			int num = 0;
			for(String pid:pids){
				pidString.append(",'" + pid +"'");
				num++;
				//每5000条与数据库交互一次
				if(num % Constants.DB_BULK_SIZE == 0){
					List<ICode> icodesTemp = al.queryByInIcode(pidString.toString().substring(1));
					icodes.addAll(icodesTemp);
					pidString.delete(0, pidString.length());
				}
			}
			
			if(pidString.length()>0){
				List<ICode> icodesTemp = al.queryByInIcode(pidString.toString().substring(1));
				icodes.addAll(icodesTemp);
			}
		}
		
		return icodes;
	}
	
	/**
	 * 封装产品相关的Map信息
	* @param icodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 15, 2013 1:30:26 PM
	 */
	private void generateInfoMap(List<ICode> icodes) throws Exception {
		if(icodes==null){
			return;
		}
		int num=0;
		StringBuilder pidString = new StringBuilder();
		//productList
		List<Product> products = new ArrayList<Product>();
		
		for(ICode i : icodes){
			pidString.append(",'" + i.getProductid() +"'");
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
		//封装物流码前缀对应的产品集合
		for(ICode i : icodes){
			lcodeMap.put(i.getLcode(), productMap.get(i.getProductid()));
		}
	}

	/**
	 * 对上传后的所有条码进行分类整理
	* @param idcodeBeanList
	* @author wenping   
	* @CreateTime Jan 10, 2013 9:29:41 AM
	 */
	private Map<String,Map<String,List<UploadIdcodeBean>>> sortUploadIdcodeBean(List<UploadIdcodeBean> idcodeBeanList) {

		Map<String,Map<String,List<UploadIdcodeBean>>> whMap = new LinkedHashMap<String,Map<String,List<UploadIdcodeBean>>>();
		for(UploadIdcodeBean bean : idcodeBeanList){
			
			//得到仓库对应的Map
			Map<String,List<UploadIdcodeBean>> pidMap = whMap.get(bean.getInwarehouseid());
			
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
			whMap.put(bean.getInwarehouseid(), pidMap);
		}
		
		return whMap;
	}
	
	/**
	 * 处理分类后的Map 
	* @param resultMap
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 10:18:18 AM
	 */
	private void dealResultMap(Map<String, Map<String, List<UploadIdcodeBean>>> resultMap,Map<String,Idcode> existIdcodes) throws Exception {
		
		Set<String> idcodeSet = new HashSet<String>();
		
		for(Map.Entry<String, Map<String, List<UploadIdcodeBean>>> entry : resultMap.entrySet()){
			//得到仓库id
			String whid = entry.getKey();
			
			try {
				//验证仓库
				boolean isExist = validateWarehouse(whid);
				
				//验证仓库权限
				boolean hasRUWAuth = validateRUWAuthority(whid);
				
				HibernateUtil.currentSession(true);
				if(isExist){
					//生成单据
					riid = generateBill(whid);
				}
				
				//得到仓库下的所有产品
				Map<String, List<UploadIdcodeBean>> pMap = entry.getValue();
				//单据详情是否存在
				boolean hasDetail = false;
				
				//循环产品信息
				for(Map.Entry<String, List<UploadIdcodeBean>> pentry : pMap.entrySet()){
					//得到产品物流码前缀
					String pid = pentry.getKey();
					//验证产品
					boolean ispExist = validateProduct(pid);
					
					//得到产品对应的所有条码
					List<UploadIdcodeBean> beanList = pentry.getValue();
					
					boxNum = 0;
					scatterNum = 0;
					
					//循环产品下的所有条码信息
					for(UploadIdcodeBean bean: beanList){
						
						String idcode = bean.getIdcode();
						
						//验证扫描类型
						boolean ispass = validateScanType(bean);
						if(!ispass){
							continue;
						}
						
						if(!existIdcodes.containsKey(idcode)){
							addErrorInfo(bean,idcode,"E00090:该条码不在系统中");
							continue;
						}
						
						if(!isExist){
							addErrorInfo(bean,null,"E00091:入库仓库不存在");
							continue;
						}
						
						if(!hasRUWAuth){
							Warehouse warehouse = new AppWarehouse().getWarehouseByID(whid);
							if(null != warehouse){
								addErrorInfo(bean,null,"E00092:用户("+username+")对当前入库仓库(名称:"+warehouse.getWarehousename()+")无管辖权限");
							}else{
								addErrorInfo(bean,null,"E00092:用户("+username+")对当前入库仓库(id:"+whid+")无管辖权限");
							}
							continue;
						}
						
						if(!ispExist){
							addErrorInfo(bean,null,"E00093:对应产品不存在");
							continue;
						}
						
						//验证条码重复
						boolean isSuccess = idcodeSet.add(idcode);
						if(!isSuccess){
							addErrorInfo(bean,idcode,"E00094:当前文件中该条码重复扫描");
							continue;
						}
						
						/*//匹配用户
						ispass = matchUser(bean);
						if(!ispass){
							continue;
						}*/
						
						/*//验证条码规则
						if(bean.getIdcode().length()!=13 && bean.getIdcode().length()!=16){
							ErrorBean eb=new ErrorBean();
							eb.setIdcode(bean.getIdcode());
							eb.setInfo("条码格式不正确");
							errorMap.put(bean.getFileLineNo(), eb);
							continue;
						}*/
						
						//校验条码
						ispass = validateIdcode(bean,existIdcodes);
						if(!ispass){
							continue;
						}
						
					}
					
					
					if(boxNum>0 || scatterNum>0){
						//生成单据详情
						generateDetail(lcodeMap.get(pid));
						hasDetail = true;
					}
				}
				
				//如果单据下有详情，则提交事务
				if(hasDetail){
					List<ReceiveIncomeIdcode> riiList = aii.getReceiveIncomeIdcodeByPiid(riid);
					//自动复核单据
					autoAuditBill(whid,riiList);
					//更改条码信息
					updIdcode(whid,riiList);
					HibernateUtil.commitTransaction();
				}else{
					HibernateUtil.rollbackTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
				HibernateUtil.rollbackTransaction();
				throw e;
			}
			
		}
		
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
	 * 获取用户相关信息
	* @param username
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 17, 2013 3:54:06 PM
	 */
	private void getUserInfo(String username) throws Exception {
		AppUsers au  = new AppUsers();
		Users u = au.getUsers(username);
		if(u!=null){
			userid = u.getUserid();
		}
	}
	
	
	/**
	 * 权限验证
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 23, 2013 4:49:58 PM
	 */
	private boolean validateRUWAuthority(String warehouseid) throws Exception {
		RuleUserWh ruw = 	ruwMap.get(warehouseid);
		if(ruw==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证产品
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 14, 2013 2:21:15 PM
	 */
	private boolean validateProduct(String pid) throws Exception {
		Product p = lcodeMap.get(pid);
		if(p==null){
			return false;
		}
		return true;
	}

/**
 * 复核单据
* @author wenping   
 * @throws Exception 
* @CreateTime Jan 11, 2013 10:36:21 AM
 */
	private void autoAuditBill(String warehouseid,List<ReceiveIncomeIdcode> riiList) throws Exception {
		ProductStockpile ps = null;
		Map<String,Double> psMap = new HashMap<String, Double>();
		//整理ReceiveIncomeIdcode
		for (ReceiveIncomeIdcode idcode : riiList){
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
			ps.setCountunit(p.getCountunit());
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
			aps.inProductStockpileTotalQuantity(ps.getProductid(),ps.getCountunit(),ps.getBatch(), quantity, ps.getWarehouseid(), ps.getWarehousebit(), riid, "经销商签收入库-入库");
			//更新库存中产品的成本
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps.getBatch());
		}
	}
	
	/**
	 * 更新条码信息
	* @param whid
	* @param riiList
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 11, 2013 11:31:03 AM
	 */
	private void updIdcode(String whid, List<ReceiveIncomeIdcode> riiList) throws Exception {
		Warehouse w = whMap.get(whid);
		StringBuilder sb= new StringBuilder();
		int idcodeNum = 0;
		for (ReceiveIncomeIdcode idcode : riiList){
			//ai.updIsUse(idcode.getIdcode(), w.getMakeorganid(), whid, idcode.getWarehousebit(), 1, 0);
			sb.append(",'"+idcode.getIdcode()+"'");
			idcodeNum ++ ;
			//每5000条与数据库交互一次
			if(idcodeNum % Constants.DB_BULK_SIZE == 0){
				//ai.bulkUpdIdcode(w.getMakeorganid(), whid, sb.toString().substring(1));
				ai.bulkUpdIdcode2(w.getMakeorganid(), whid, sb.toString().substring(1));
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0){
			//ai.bulkUpdIdcode(w.getMakeorganid(), whid, sb.toString().substring(1));
			ai.bulkUpdIdcode2(w.getMakeorganid(), whid, sb.toString().substring(1));
		}
	}

/**
 * 生成签收入库单详情
* @param riid
* @author wenping   
 * @throws Exception 
 * @throws NumberFormatException 
* @CreateTime Jan 10, 2013 5:12:44 PM
 */
	private void generateDetail(Product p) throws NumberFormatException, Exception {
		ReceiveIncomeDetail rid = new ReceiveIncomeDetail();
		rid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("receive_income_detail", 0, "")));
		rid.setPiid(riid);
		rid.setProductid(p.getId());
		rid.setProductname(p.getProductname());
		rid.setSpecmode(p.getSpecmode());
		rid.setUnitid(p.getScatterunitid());
		rid.setBoxNum(boxNum);
		rid.setScatterNum(scatterNum);
		rid.setCostprice(0d);
		rid.setCostsum(0d);
		rid.setMakedate(DateUtil.getCurrentDate());
		
		EntityManager.save3(rid);
	}

	/**
	 * 生成签收入库单
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 10, 2013 4:43:46 PM
	 */
	private String generateBill(String whid) throws Exception {
		AppUsers au  = new AppUsers();
		Users u = au.getUsers(this.username);
		String makeorganid=null;
		Integer userid = null;
		if(u!=null){
			makeorganid = u.getMakeorganid();
			userid = u.getUserid();
		}

        ReceiveIncome ri = new ReceiveIncome();
		String riid = MakeCode.getExcIDByRandomTableName("receive_income", 2, "RI");
		ri.setId(riid);
		ri.setWarehouseid(whid);
		ri.setIncomedate(Dateutil.getCurrentDate());
		ri.setIncomesort(0);
		ri.setIsaudit(1);
		ri.setAuditid(userid);
		ri.setAuditdate(Dateutil.getCurrentDate());
		ri.setMakeorganid(makeorganid);
		ri.setMakedeptid(0);
		ri.setMakeid(1);
		ri.setIsAutoCreate(1);
		ri.setMakedate(DateUtil.getCurrentDate());
		ri.setKeyscontent(ri.getId());
		
		EntityManager.save3(ri);
		
		return riid;
	}

	/**
	 * 验证扫描类型
	* @param bean
	* @author wenping   
	* @CreateTime Jan 10, 2013 11:41:16 AM
	 */
	private boolean validateScanType(UploadIdcodeBean bean) {
//		if("D".equals(bean.getScanType())){
//			return false;
//		}else 
			
		if(!Constants.SCAN_TYPE_RI.equals(bean.getScanType())){
			addErrorInfo(bean,null,"E00095:扫描类型错误");
			return false;
		}
		return true;
	}
	
	/**
	 * 匹配用户
	* @param bean
	* @author wenping   
	* @CreateTime Jan 10, 2013 2:00:08 PM
	 */
	/*private boolean matchUser(UploadIdcodeBean bean) {
		if(!this.username.equals(bean.getUsername())){
			ErrorBean eb= new ErrorBean();
			eb.setInfo("条码扫描用户与当前文件上传用户不匹配");
			errorMap.put(bean.getFileLineNo(), eb);
			return false;
		}
		return true;
	}*/
	
	/***
	 * 校验条码
	* @param bean
	* @param existIdcodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 2:22:48 PM
	 */
	private boolean validateIdcode(UploadIdcodeBean bean, Map<String, Idcode> existIdcodes) throws Exception {
		String idcode = bean.getIdcode();
		//条码类型   第1位：代表产品品牌(1为国内产品、2为国际)
		String f=idcode.substring(0,1);
		if("1".equals(f) || "C".equals(f) || "3".equals(f)){
			//国内产品
			return validateIdcode4N(existIdcodes.get(idcode),bean);
		}else if("2".equals(f)){
			//国际产品
			return validateIdcode4W(existIdcodes.get(idcode),bean);
		}else{
			addErrorInfo(bean,idcode,"E00096:条码格式不正确");
			return false;
		}
		
	}

	/**
	 * 封装错误信息
	* @param bean
	* @param idcode
	* @param msg
	* @author wenping   
	* @CreateTime Jan 14, 2013 10:02:26 AM
	 */
	private void addErrorInfo(UploadIdcodeBean bean,String idcode,String msg) {
		ErrorBean eb= new ErrorBean();
		eb.setIdcode(idcode);
		eb.setInfo(msg);
		errorMap.put(bean.getFileLineNo(), eb);
	}
	

	/**
	 * 验证国产粉
	* @param ic
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 10, 2013 2:48:54 PM
	 */
	private boolean validateIdcode4N(Idcode ic,UploadIdcodeBean bean) throws Exception
	{
		boolean ispass  = validateIdcodeAvaliable(ic,bean);
		//验证未通过
		if(!ispass){
			return ispass;
		}
		
		//判断条码是否被占用（只考虑经销商，网点暂时不考虑）
		if(ic.getDealerreceive()!=null && !"".equals(ic.getDealerreceive())){
			addErrorInfo(bean,ic.getIdcode(),"E00098:该条码已在仓库 ["+ic.getWarehouseid()+"]入库");
			return false;
		}
		
		if(Constants.CODEUNIT_B.equals(bean.getScanFlag()) ){
			//箱的 情况
			boxNum++;
			
		}else if(Constants.CODEUNIT_Z.equals(bean.getScanFlag())){
			//散装的情况
			
			//----20140117----------add-------S
			//如果是系统中不存在的码
			if(notExistIdcodes.contains(bean.getIdcode())){
				//散不存在
				AppUploadProduceReport  appupr = new AppUploadProduceReport();
				//获取对应的箱条码
				UploadProduceReport aUploadProduceReport=appupr.getUploadProduceReportByUnitNo(bean.getIdcode());
				if(aUploadProduceReport==null){
					//只将散码加入到条码中
					/*addErrorInfo(bean,bean.getIdcode(),"E00110:该条码不在系统中");
					return false;*/
				}else{
					String boxIdcode=aUploadProduceReport.getBoxCode();
					//验证条码是否已存在当前单据(,单号,条码)
					//boolean isExist  = isExist(1,boxIdcode,bean);
					Idcode parentIc = null;
					parentIc = ai.getIdcodeById(boxIdcode);
					//所在箱条码不存在时
					if(parentIc==null){
						//将箱条码保存到Idcode
						parentIc = addToIdcode(ic.getUnitid(),bean.getInwarehouseid(),bean,boxIdcode);
					}
					//所在箱码存在时
					else{
						//首先判断DR是否有值（即是否有经销商入库）
						if(parentIc.getDealerreceive()!=null && !"".equals(parentIc.getDealerreceive())){
							addErrorInfo(bean,ic.getIdcode(),"E00099:该条码的箱码已在仓库 ["+parentIc.getWarehouseid()+"]入库");
							//删除之前添加进去的条码
							ai.delIdcodeByid(ic.getIdcode());
							return false;
						}
						//判断该箱码是否还可以再拆箱
						if(parentIc.getPackquantity()-af.getXQuantity(ic.getProductid(), ic.getUnitid())<0){
							addErrorInfo(bean,ic.getIdcode(),"E00099:该条码的箱码数量不足已不可继续拆箱");
							//删除之前添加进去的条码
							ai.delIdcodeByid(ic.getIdcode());
							return false;
						}
						Integer unitid = productMap.get(ic.getProductid()).getScatterunitid();
						//修改Idcode -->拆箱
						parentIc.setPackquantity(parentIc.getPackquantity()-af.getXQuantity(parentIc.getProductid(), unitid));
						EntityManager.update2(parentIc);	
					}
				}
			}
			
			//----20140117----------add-------E
			
			
			scatterNum++;
		}else{
			addErrorInfo(bean,null,"E00097:不正确的扫描标识位");
			return false;
		}
		
		if(ic.getBatch()!=null && ic.getBatch().length()>=9){
			batch = ic.getBatch().substring(0, 9);
		}else{
			batch = ic.getBatch();
		}
		
		//保存条码信息
		generateReceiveIncomeIdcode(ic);

		return ispass;
		
	}

	/**
	 * 验证国际粉
	* @param idcode
	* @param bean
	* @return
	* @author wenping   
	 * @throws Exception 
	 * @throws NumberFormatException 
	* @CreateTime Jan 11, 2013 9:14:54 AM
	 */
	private boolean validateIdcode4W(Idcode ic, UploadIdcodeBean bean) throws NumberFormatException, Exception {
		
		boolean ispass  = validateIdcodeAvaliable(ic,bean);
		//验证未通过
		if(!ispass){
			return ispass;
		}
		
		//判断条码是否被占用（只考虑经销商，网点暂时不考虑）
		if(ic.getDealerreceive()!=null && !"".equals(ic.getDealerreceive())){
			addErrorInfo(bean,ic.getIdcode(),"E00098:该条码已在仓库 ["+ic.getWarehouseid()+"]入库");
			return false;
		}
		

//		String idcode  = ic.getIdcode();
//		//取得条码后三位(000为箱,非000为散装)
//		String unitType = idcode.substring(idcode.length()-3, idcode.length());
		if(Constants.CODEUNIT_B.equals(bean.getScanFlag())){
			boxNum++;
		}else if(Constants.CODEUNIT_Z.equals(bean.getScanFlag())){
			//散装的情况
			//----20140117----------add-------S
			//如果是系统中不存在的码
			if(notExistIdcodes.contains(bean.getIdcode())){
				//散不存在
				String boxIdcode=bean.getIdcode().substring(0,bean.getIdcode().length()-3)+"000";
				//验证条码是否已存在当前单据(,单号,条码)
				//boolean isExist  = isExist(1,boxIdcode,bean);
				Idcode parentIc = null;
				parentIc = ai.getIdcodeById(boxIdcode);
				//所在箱条码不存在时
				if(parentIc==null){
					//将箱条码保存到Idcode
					parentIc = addToIdcode(ic.getUnitid(),bean.getInwarehouseid(),bean,boxIdcode);
				}
				//所在箱码存在时
				else{
					//首先判断DR是否有值（即是否有经销商入库）
					if(parentIc.getDealerreceive()!=null && !"".equals(parentIc.getDealerreceive())){
						addErrorInfo(bean,ic.getIdcode(),"E00099:该条码的箱码已在仓库 ["+parentIc.getWarehouseid()+"]入库");
						//删除之前添加进去的条码
						ai.delIdcodeByid(ic.getIdcode());
						return false;
					}
					//判断该箱码是否还可以再拆箱
					if(parentIc.getPackquantity()-af.getXQuantity(ic.getProductid(), ic.getUnitid())<0){
						addErrorInfo(bean,ic.getIdcode(),"E00099:该条码的箱码数量不足已不可继续拆箱");
						//删除之前添加进去的条码
						ai.delIdcodeByid(ic.getIdcode());
						return false;
					}
					Integer unitid = productMap.get(ic.getProductid()).getScatterunitid();
					//修改Idcode -->拆箱
					parentIc.setPackquantity(parentIc.getPackquantity()-af.getXQuantity(parentIc.getProductid(), unitid));
					EntityManager.update2(parentIc);	
				}
				
			}
			
			//----20140117----------add-------E
			scatterNum++;
		}else{
			addErrorInfo(bean,null,"E00178:不正确的扫描标识位");
			return false;
		}
		
		batch = ic.getBatch();
		
		//保存条码信息
		generateReceiveIncomeIdcode(ic);
		
		return ispass;
	}
	
	/**
	 * 插入条码表
	* @param ic
	* @author wenping   
	 * @throws Exception 
	 * @throws NumberFormatException 
	* @CreateTime Jan 10, 2013 3:51:37 PM
	 */
	private void generateReceiveIncomeIdcode(Idcode ic) throws NumberFormatException, Exception {

		ReceiveIncomeIdcode rii = new ReceiveIncomeIdcode();
		rii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("receive_income_idcode", 0, "")));
		rii.setPiid(riid);
		rii.setIdcode(ic.getIdcode());
		rii.setStartno(ic.getIdcode());
		rii.setEndno(ic.getIdcode());
		rii.setProductid(ic.getProductid());
		rii.setProducedate(ic.getProducedate());
		rii.setIsidcode(1);
		rii.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		rii.setBatch(batch);
		rii.setUnitid(ic.getUnitid());
		rii.setQuantity(1d);
		//包装数量
		rii.setPackquantity(ic.getPackquantity());
		rii.setLcode(ic.getLcode());
		rii.setMakedate(DateUtil.getCurrentDate());
		
		EntityManager.save3(rii);
	}

	/**
	 * 校验条码
	* @param ic
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 3:02:20 PM
	 */
	private boolean validateIdcodeAvaliable(Idcode ic,UploadIdcodeBean bean) throws Exception {
		//如果条码是已出库状态，则通过
		if(ic.getIsuse()==0 && ic.getIsout()==1){
			return true;
		}else if(ic.getWarehouseid().equals(bean.getInwarehouseid())){
			Organ organ = new AppOrgan().getOrganByWarehouseid(bean.getInwarehouseid());
			if(null != organ){
				addErrorInfo(bean,ic.getIdcode(),"E00098:该条码已在当前 "+organ.getOrganname()+" 仓库中");
			}else{
				addErrorInfo(bean,ic.getIdcode(),"E00098:该条码已在当前仓库中");
			}
		}else{
			if(Constants.CODEUNIT_B.equals(bean.getScanFlag()) ){
				//箱的 情况
				if(ic == null){
					addErrorInfo(bean,bean.getIdcode(),"E00133:该条码不在系统中");
					return false;
				}
				
				if(ic.getPackquantity().doubleValue()!=ic.getFquantity().doubleValue()){
					addErrorInfo(bean,bean.getIdcode(),"E00143:该条码不可用(因为该箱已被拆散码出库)");
					return false;
				}
			}
			return true;
			/*Organ organ = new AppOrgan().getOrganByWarehouseid(ic.getWarehouseid());
			if(null != organ){
				addErrorInfo(bean,ic.getIdcode(),"E00099:该条码未从 "+organ.getOrganname()+" 出库");
			}else{
				addErrorInfo(bean,ic.getIdcode(),"E00099:该条码未曾出库");
			}*/
			
		}
		return false;
	}

	/**
	 * 保存条码上传日志信息
	* @author wenping   
	* @CreateTime Jan 11, 2013 9:59:34 AM
	 */
	private void updIsDeal() {
		try {
			HibernateUtil.currentSession(true);
			appiu.updIsDeal(iuid, 1);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		}
	}
	
	/**
	 * 封装错误信息
	* @param errorMap
	* @author wenping   
	* @CreateTime Jan 11, 2013 9:34:05 AM
	 */
	@SuppressWarnings("deprecation")
	private ArrayList<String> generateErrorInfo(Map<Integer, ErrorBean> errorMap) {

		ArrayList<String> errorList = new ArrayList<String>();
		if(!errorMap.isEmpty()){
			for(Map.Entry<Integer, ErrorBean> entry : errorMap.entrySet()){
				failNum++;
				
				String str = "第" + entry.getKey() +"行";
				ErrorBean bean = entry.getValue();
				if(bean.getIdcode()!=null){
					str += "  "+ bean.getIdcode();
				}
				str+="[" + bean.getInfo()+"]";
				
				//写入文件
				writeTxt(str);
				//错误信息列表
				errorList.add(str);
			}
		}
		
		//更新条码上传日志
		updLogNum();
		
//		errorList.add("成功 "+(totalNum-failNum)+" 条,失败"+failNum+"条");
		System.out.println(new Date().toLocaleString());
		return errorList;
	}
	
	/**
	 * 更新条码上传日志 成功失败数量
	* @author wenping   
	* @CreateTime Jan 11, 2013 9:58:50 AM
	 */
	private void updLogNum() {
		try {
			HibernateUtil.currentSession(true);
			appiu.updNum(iuid, 2, totalNum-failNum, failNum, failAddress);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		} 
	}
	
	/**
	 * 写错误文件
	* @param str
	* @author wenping   
	* @CreateTime Jan 11, 2013 9:39:35 AM
	 */
	private void writeTxt(String str) {
		BufferedWriter out = null;
		String destFile = filepath + failAddress;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFile, true)));
			out.write(str);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
	 * 
	 * @param idcodes 所有条码信息
	 * @param existIdcodes 数据库中存在的条码信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addNotExistIdcodes(Set<String> idcodes,Map<String,Idcode> existIdcodes) throws Exception{
		if(idcodes!=null && !idcodes.isEmpty()){
			String strIdcode = "";
			Idcode objIdcode = null;
			String strLcode = "";
			Integer funit = 0;
			String strwhere = "";
			String pid = "";
			String pname = "";
			double boxQuantity=0;
			List plist = null;
			Map m = null;
			//编写批次
			String batch = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMMdd") + "X999";
			//循环遍历所有条码信息
			Iterator<String> iter = idcodes.iterator();
			while(iter.hasNext()){
				strIdcode = iter.next();
				//判断数据库中是否存在该条码信息
				//如果不存在则插入到idcode表中
				if(!existIdcodes.containsKey(strIdcode)){
					objIdcode = new Idcode();
					//获取物流码前缀(国内箱码以“C” 开头，)
					if(strIdcode.startsWith("C") || strIdcode.startsWith("c")){
						strLcode = strIdcode.substring(1, 4);
						strwhere = " and f.funitid=2 and i.lcode='" +  strLcode + "'";
					}
					//国际箱码以“2”开头且已“000”结尾
					else if(strIdcode.startsWith("2") && strIdcode.endsWith("000")){
						strLcode = strIdcode.substring(0, 3);
						strwhere = " and f.funitid=2 and i.lcode='" +  strLcode + "'";
					}
					//散码情况
					else{
						strLcode = strIdcode.substring(0, 3);
						strwhere = " and f.funitid<>2 and f.funitid<>17 and i.lcode='" +  strLcode + "'";
						//将系统中不存在的散条码加入到集合中
						notExistIdcodes.add(strIdcode);
					}
					//查询产品信息
					
					plist = ap.getProductUnitById(strwhere);
					if(plist!=null && !plist.isEmpty()){
						m = (Map) plist.get(0);
						pid = String.valueOf(m.get("id"));
						pname = String.valueOf(m.get("productname"));
						funit = Integer.valueOf(String.valueOf(m.get("funitid")));
						boxQuantity = Double.valueOf(String.valueOf(m.get("xquantity")));
					}
					
					objIdcode.setIdcode(strIdcode);
					objIdcode.setBatch(batch);
					objIdcode.setProductid(pid);
					objIdcode.setProductname(pname);
					objIdcode.setLcode(strLcode);
					objIdcode.setStartno(strIdcode);
					objIdcode.setEndno(strIdcode);
					
					objIdcode.setIsuse(1);
					objIdcode.setIsout(0);
					objIdcode.setIdbilltype(0);
					objIdcode.setWarehousebit("000");
					objIdcode.setMakedate(DateUtil.getCurrentDate());
					
					objIdcode.setUnitid(funit);
					objIdcode.setFquantity(boxQuantity);
					objIdcode.setPackquantity(boxQuantity);
					objIdcode.setPcolumn(Integer.parseInt(batch.subSequence(4, 6).toString()));
					objIdcode.setWarehouseid("xxxxx");
					//保存idcode
					EntityManager.save3(objIdcode);
					//将条码信息加入到existIdcodes中
					existIdcodes.put(strIdcode, objIdcode);
				}
			}
		}
	}
	
	/**
	 * 验证条形码是否存在在当前单据中
	 */
	/*private boolean isExist(int flag,String currentIdcode,UploadIdcodeBean bean) throws Exception
	{
		//判断是否已经存在(单号,条码)
		if (att.getTakeTicketIdcodeByttidAndidcode(ttid, currentIdcode) != null)
		{
			if(flag==0){
				addErrorInfo(bean,bean.getIdcode(),"E00123:条码已经存在当前列表中");
				return true;
			}else if(flag==1){
				addErrorInfo(bean,bean.getIdcode(),"E00124:该条码的箱码("+currentIdcode+")已经存在当前列表中");
				return true;
			}
		}
		return false;
	}*/
	
	/**
	 * 保存条码到Idcode表,用于箱条码
	 */
	private Idcode addToIdcode(int unitid, String warehouseId,UploadIdcodeBean bean,String idcode) throws Exception{
        Warehouse w = whMap.get(warehouseId);
        Product p =lcodeMap.get(bean.getLcode());
        double boxQuantity=0;
        double packQuantity=0;
        int useFlag=0;
        boxQuantity=af.getXQuantity(p.getId(), 2);
        packQuantity = boxQuantity-af.getXQuantity(p.getId(), unitid);
        /*if(issplit==1){
        	useFlag = 1;
        	packQuantity = af.getXQuantity(p.getId(), 2)-af.getXQuantity(p.getId(), unitid);
        }else{
        	boxQuantity=af.getXQuantity(p.getId(), 2);
        }*/
        
        Idcode ic = new Idcode();
		ic.setIdcode(idcode);
		ic.setProductid(p.getId());
		ic.setProductname(p.getProductname());
		ic.setBatch("");
		ic.setLcode(bean.getLcode());
		ic.setStartno(idcode);
		ic.setEndno(idcode);
		ic.setUnitid(2);
		ic.setQuantity(1d);
		ic.setFquantity(boxQuantity);
		ic.setPackquantity(packQuantity);
	    ic.setIsuse(useFlag);
		ic.setIsout(0);
		ic.setIdbilltype(0);
		ic.setMakeorganid(w.getMakeorganid());
		ic.setWarehouseid(warehouseId);
		ic.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		ic.setMakedate(DateUtil.getCurrentDate());
		ic.setPcolumn(Integer.parseInt(DateUtil.getCurrentDateString().substring(5, 7)));
		EntityManager.save3(ic);
		
		return ic;
	}
}
