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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
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
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadIdcodeUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 无单一级经销商出库
* @Title: DealNoBillStockAlterMove.java
* @author: wenping 
* @CreateTime: Jan 16, 2013 2:07:36 PM
* @version:
 */
public class DealNoBillStockAlterMoveByFail {

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
	private AppTakeTicketIdcode att =new AppTakeTicketIdcode();
	private AppProductStockpile aps = new AppProductStockpile();
	private AppWarehouse aw = new AppWarehouse();
	private AppOrgan ao = new AppOrgan();
	private AppUsers au  = new AppUsers();
	private OrganService os = new OrganService();
	private AppFUnit af = new AppFUnit();
	private AppOlinkMan aom = new AppOlinkMan();
	
	private int boxNum = 0;
	private int scatterNum = 0;
	
	private String omid;
	private String ttid;
	
	private int totalNum = 0;
	private int failNum = 0;
	private String batch;
	private int unitid;
	private String outwarehouseid;
	//入库仓库
	private String inwarehouseid;
	
	private String makeorganid=null;
	private String makeorganname=null;
	private Integer userid = null;
	private Integer makedeptid= null;
	private Integer issplit=0;
//	private Integer flagA=0;
	private Integer flagB=0;
	
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
	
	public DealNoBillStockAlterMoveByFail(){}

	public DealNoBillStockAlterMoveByFail(String filepath, String fileaddress, int iuid,String username)
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
//			flagA = UploadIdcodeUtil.getBaseResourceFlag(1);
			flagB = UploadIdcodeUtil.getBaseResourceFlag(2);
			wvMap = UploadIdcodeUtil.getWVAuthority(userid);
			ruwMap = UploadIdcodeUtil.getRUWAuthority(userid);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			Set<String> idcodes = dealTxtFile(idcodeBeanList);
			
			//得到数据库中存在的条码集合
			Map<String,Idcode> existIdcodes = bulkQueryByInIdcode(idcodes);
			
//			//更新UploadIdcodeBean中的Icode信息
//			List<ICode> icodes = updUploadIdcodeBeanInfo(idcodeBeanList,existIdcodes);
			
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
			
			Set<String> idcodes = new HashSet<String>();
			//物流码前缀集合
			Set<String> pids = new HashSet<String>();
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
				//得到用户名
				String username = crs.getUserName(uploadidcode);
				// 调出仓库编号
				String outwarehouseid =crs.getOutWarehouseId(uploadidcode);
				
				bean.setIdcode(idcode);
				bean.setInwarehouseid(inwarehouseid);
				bean.setLcode(lcode);
				bean.setScanType(scanType);
				bean.setScanFlag(scanFlag);
				bean.setFileLineNo(lineNo++);
				bean.setUsername(username);
				bean.setOutwarehouseid(outwarehouseid);
				
				//扫描类型为D 代表已删除条码
				if("D".equals(scanType)){
					continue;
				}
				/*//用户不匹配
				if(!this.username.equals(username)){
					continue;
				}*/
				
				//记录上传总数量
				totalNum++;
				
				if(idcode==null || (idcode.length()!=13 && idcode.length()!=16)){
					addErrorInfo(bean,idcode,"E00100:条码格式不正确");
					continue;
				}
				
				if(Constants.SCAN_TYPE_OM.equals(scanType)){
					//保存有效条码
					idcodes.add(idcode);
					//保存入库仓库
					whids.add(inwarehouseid);
					//保存出库仓库
					whids.add(outwarehouseid);
					
					//保存产品物流码前缀
					pids.add(lcode);
				}
				
				//保存到beanList中
				idcodeBeanList.add(bean);
				
			}
			
			//查询相关的产品信息
			List<ICode> icodes = bulkQueryByInProduct(pids);
			//封装产品相关的Map信息
			generateInfoMap(icodes);
			
			//查询相关的仓库信息
			List<Warehouse> whList = bulkQueryByInWarehouse(whids);
			
			//查询仓库对应机构的信息
			bulkQueryByInOrgan(whList);
			
			return idcodes;
			
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
	/*private List<ICode> updUploadIdcodeBeanInfo(List<UploadIdcodeBean> idcodeBeanList, Map<String, Idcode> existIdcodes) throws Exception {
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
	}*/


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
					List<ICode> icodesTemp = al.queryByInIcodeWithlcode(pidString.toString().substring(1));
					icodes.addAll(icodesTemp);
					pidString.delete(0, pidString.length());
				}
			}
			
			if(pidString.length()>0){
				List<ICode> icodesTemp = al.queryByInIcodeWithlcode(pidString.toString().substring(1));
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
	* @param resultMap
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 10:18:18 AM
	 */
	@SuppressWarnings("unchecked")
	private void dealResultMap(Map<String, Map<String, List<UploadIdcodeBean>>> resultMap,Map<String,Idcode> existIdcodes) throws Exception {
		
		Set<String> idcodeSet = new HashSet<String>();
		
		for(Map.Entry<String, Map<String, List<UploadIdcodeBean>>> entry : resultMap.entrySet()){
			//得到出入库仓库id
			String out_in_whid = entry.getKey();
			String[] arr = out_in_whid.split("_");
			//出库仓库
			outwarehouseid = arr[0];
			//入库仓库
			inwarehouseid = arr[1];
			
			try {
				/*//验证出库仓库
				boolean isOutExist = validateWarehouse(outwarehouseid);
				//验证出库仓库权限
				boolean hasRUWAuth = validateRUWAuthority(outwarehouseid);
				
				//验证入库仓库
				boolean isInExist = validateWarehouse(inwarehouseid);
				//验证入库仓库权限
				boolean hasWVAuth = validateWVAuthority(inwarehouseid);
				*/
				boolean isOutExist = validateWarehouse(outwarehouseid);
				boolean isInExist = validateWarehouse(inwarehouseid);
				
				HibernateUtil.currentSession(true);
				if(isOutExist && isInExist){
					//生成订购单据
					omid = generateBill();
					//生成检货单据TB
					generateTBBill();
					//生成TT单据
					generateTTBill();
				}
				
				//得到仓库下的所有产品
				Map<String, List<UploadIdcodeBean>> pMap = entry.getValue();
				//单据详情是否存在
				boolean hasDetail = false;
				
				//循环产品信息
				for(Map.Entry<String, List<UploadIdcodeBean>> pentry : pMap.entrySet()){
					//得到产品物流码前缀
					String pid = pentry.getKey();
//					//验证产品
//					boolean ispExist = validateProduct(pid);
					
					//得到产品对应的所有条码
					List<UploadIdcodeBean> beanList = pentry.getValue();
					
					boxNum = 0;
					scatterNum = 0;
					
					//循环产品下的所有条码信息
					for(UploadIdcodeBean bean: beanList){
						
						String idcode = bean.getIdcode();
						
						if(!isOutExist){
							addErrorInfo(bean,null,"E00101:出库仓库不存在");
							continue;
						}
						
						/*if(!hasRUWAuth){
							addErrorInfo(bean,null,"E00102:用户("+username+")对出库仓库(id:"+outwarehouseid+")无管辖权限");
							continue;
						}*/
						
						if(!isInExist){
							addErrorInfo(bean,null,"E00103:入库仓库不存在");
							continue;
						}
						
						/*if(!hasWVAuth){
							addErrorInfo(bean,null,"E00104:用户("+username+")对入库仓库(id:"+inwarehouseid+")没有业务往来权限");
							continue;
						}*/
						
//						if(!ispExist){
//							addErrorInfo(bean,null,"E00105:对应产品不存在");
//							continue;
//						}
						
						//验证扫描类型
						boolean ispass = validateScanType(bean);
						if(!ispass){
							continue;
						}
						
						//验证条码重复
						boolean isSuccess = idcodeSet.add(idcode);
						if(!isSuccess){
							addErrorInfo(bean,idcode,"E00106:当前文件中该条码重复扫描");
							continue;
						}
						
						//校验条码
						ispass = validateIdcode(bean,existIdcodes);
						if(!ispass){
							continue;
						}
					}
					
					
					if(boxNum>0 || scatterNum>0){
						//生成单据详情
						if (lcodeMap.get(pid)==null){
							hasDetail = false;
						}else{
							generateDetail(lcodeMap.get(pid));
							//生成TT单据详情
							generateTTDetail(lcodeMap.get(pid));
							
							hasDetail = true;
						}
					}
				}
				
				//如果单据下有详情，则提交事务
				if(hasDetail){
					List<TakeTicketIdcode> ttiList = att.getTakeTicketIdcodeByttid(ttid);
					//自动复核单据
					autoAuditBill(outwarehouseid,ttiList);
					//复制条码
					saveStockAlterMoveIdcode(ttiList);
					//自动签收单据
					autoReceiveBill(inwarehouseid,ttiList);
					//更改条码信息
					updIdcode(inwarehouseid,ttiList);
					
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
	 * 入库仓库权限验证
	* @param inwarehouseid
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 23, 2013 4:49:33 PM
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
	* @param outwarehouseid
	* @return
	* @author wenping   
	* @CreateTime Jan 23, 2013 4:49:58 PM
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
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 14, 2013 2:21:15 PM
	 */
	/*private boolean validateProduct(String pid) throws Exception {
		Product p = lcodeMap.get(pid);
		if(p==null){
			return false;
		}
		unitid = p.getScatterunitid();
		return true;
	}*/
	
	/**
	 * 验证产品
	* @param ic
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 21, 2013 11:58:03 AM
	 */
	private boolean validateProduct(Idcode ic,UploadIdcodeBean bean) throws Exception {
		Product p = productMap.get(ic.getProductid());
		if(p==null){
			p = ap.getProductByID(ic.getProductid());
			if(p==null){
				addErrorInfo(bean,bean.getIdcode(),"E00139:对应的产品不存在");
				return false;
			}
			productMap.put(ic.getProductid(), p);
		}
		return true;
	}
	
	/**
	 * 验证产品是否与条码匹配
	* @param bean
	* @param ic
	* @author wenping   
	* @CreateTime Jan 21, 2013 4:57:32 PM
	 */
	private boolean validateMatch(UploadIdcodeBean bean, Idcode ic) {
		if(bean.getLcode()!=null && !bean.getLcode().isEmpty()){
			if("13".equals(ic.getLcode()) || "14".equals(ic.getLcode()) || "15".equals(ic.getLcode())){
				if("13".equals(ic.getLcode())){
					if("13".equals(bean.getLcode()) || "213".equals(bean.getLcode())){
						return true;
					}else{
						if(!bean.getLcode().equals(ic.getLcode())){
							addErrorInfo(bean,bean.getIdcode(),"E00137:该条码与当前产品不匹配");
							return false;
						}
					}
				}else if("14".equals(ic.getLcode())){
					if("14".equals(bean.getLcode()) || "214".equals(bean.getLcode())){
						return true;
					}else{
						if(!bean.getLcode().equals(ic.getLcode())){
							addErrorInfo(bean,bean.getIdcode(),"E00137:该条码与当前产品不匹配");
							return false;
						}
					}
				}else if("15".equals(ic.getLcode())){
					if("15".equals(bean.getLcode()) || "215".equals(bean.getLcode())){
						return true;
					}else{
						if(!bean.getLcode().equals(ic.getLcode())){
							addErrorInfo(bean,bean.getIdcode(),"E00137:该条码与当前产品不匹配");
							return false;
						}
					}
				}else{
					//暂无逻辑
				}
			}else{
				if(ic.getLcode().trim().length() == 2){
					if(!ic.getLcode().equals(bean.getLcode().substring(bean.getLcode().length() - 2, bean.getLcode().length()))){
						addErrorInfo(bean,bean.getIdcode(),"E00137:该条码与当前产品不匹配");
						return false;
					}
				}else{
					//if(!bean.getLcode().equals(ic.getLcode())){
					//	addErrorInfo(bean,bean.getIdcode(),"E00137:该条码与当前产品不匹配");
					//	return false;
					//}
				}
			}
			return true;
		}
		addErrorInfo(bean,bean.getIdcode(),"E00138:该条码与当前产品不匹配");
		return false;
	}

	
	/**
	 * 复核单据
	* @param warehouseid
	* @param ttiList
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 18, 2013 3:53:26 PM
	 */
	private void autoAuditBill(String warehouseid,List<TakeTicketIdcode> ttiList) throws Exception {
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
			aps.outProductStockpile(productid, productMap.get(productid).getCountunit(), batch, quantity, warehouseid, Constants.WAREHOUSE_BIT_DEFAULT,
					ttid, "检货小票-出货");
		}
	}

/**
 * 签收单据
* @author wenping   
 * @throws Exception 
* @CreateTime Jan 11, 2013 10:36:21 AM
 */
	private void autoReceiveBill(String warehouseid,List<TakeTicketIdcode> ttiList) throws Exception {
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
			aps.inProductStockpileTotalQuantity(ps.getProductid(),ps.getCountunit(),ps.getBatch(), quantity, ps.getWarehouseid(), ps.getWarehousebit(), ttid, "订购入库-入库");
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
* @author wenping   
 * @throws Exception 
 * @throws NumberFormatException 
* @CreateTime Jan 10, 2013 5:12:44 PM
 */
	private void generateDetail(Product p) throws NumberFormatException, Exception {
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(omid);
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
		// 产品单位取product中的小包装单位
		smd.setUnitid(p.getScatterunitid());
		smd.setUnitprice(0d);
		// 共有多少小包装数量
		double quantity = ArithDouble.add(boxNum * p.getBoxquantity(), Double.valueOf(scatterNum + ""));
		smd.setQuantity(quantity);
		// 箱数
		smd.setBoxnum(boxNum);
		// 散数
		smd.setScatternum(Double.valueOf(scatterNum + ""));
		smd.setTakequantity(0d);
		smd.setSubsum(0d);

		EntityManager.save3(smd);
	}
	
	/**
	 * 生成检货出库TT单详情
	* @param product
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 18, 2013 3:38:58 PM
	 */
	private void generateTTDetail(Product product) throws Exception {
		TakeTicketDetail ttd = new TakeTicketDetail();
		ttd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_detail", 0, "")));
		ttd.setProductid(product.getId());
		ttd.setProductname(product.getProductname());
		ttd.setSpecmode(product.getSpecmode());
		ttd.setUnitid(product.getScatterunitid());
		ttd.setUnitprice(0d);
		double quantity = ArithDouble.add(boxNum * product.getBoxquantity(), Double.valueOf(scatterNum + ""));
		ttd.setQuantity(quantity);
		ttd.setBoxnum(boxNum);
		ttd.setScatternum(Double.valueOf(scatterNum + ""));
		ttd.setTtid(ttid);
		ttd.setIsPicked(1);
		EntityManager.save3(ttd);
	}

	/**
	 * 生成订购单
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 10, 2013 4:43:46 PM
	 */
	@SuppressWarnings("unchecked")
	private String generateBill() throws Exception {
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
		sm.setMovedate(Dateutil.getCurrentDate());
		sm.setOutwarehouseid(outwarehouseid);
		sm.setIsmaketicket(0);
		sm.setIsreceiveticket(0);
		sm.setMakeorganid(makeorganid);
		sm.setMakeorganidname(makeorganname);
		sm.setMakedeptid(makedeptid);
		sm.setMakeid(userid);
		sm.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		sm.setInwarehouseid(inwarehouseid);
		sm.setIsaudit(1);
		sm.setAuditid(userid);
		sm.setAuditdate(Dateutil.getCurrentDate());
		
		sm.setOlinkman(linkman);
		sm.setOtel(tel);
		
		sm.setIsshipment(1);
		sm.setShipmentid(userid);
		sm.setShipmentdate(Dateutil.getCurrentDate());
		sm.setIstally(0);
		sm.setIsblankout(0);
		sm.setTakestatus(1);
		sm.setReceiveorganid(receiveorganid);
		sm.setReceiveorganidname(receiveorganidname);
		sm.setIscomplete(1);
		sm.setReceiveid(userid);
		sm.setReceivedate(Dateutil.getCurrentDate());
		
		StringBuffer keyscontent = new StringBuffer();
		keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
		.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
		sm.setTakestatus(0);
		sm.setKeyscontent(keyscontent.toString());
		
		EntityManager.save3(sm);
		
		return smid;
	}

	/**
	 * 生成检货TB单据
	* @param outwarehouseid
	* @param inwarehouseid
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 17, 2013 10:15:04 AM
	 */
	@SuppressWarnings("unchecked")
	private void generateTBBill() throws Exception {
		Organ o = wh_OrganMap.get(outwarehouseid);
		String oid = null;
		String oname = null;
		if(o!=null){
			oid = o.getId();
			oname = o.getOrganname();
		}
		
		String linkman = null;
		String tel = null;
		
		Object[] arr = UploadIdcodeUtil.getLinkMan(lmMap, wh_OrganIDMap, inwarehouseid);
		linkman = (String)arr[0];
		tel =  (String)arr[1];
		lmMap = (Map)arr[2];
		
		
		TakeBill tb = new TakeBill();
		tb.setId(omid);
		tb.setBsort(1);
		tb.setEquiporganid(oid);
		tb.setInwarehouseid(inwarehouseid);
		tb.setIsaudit(1);
		tb.setAuditid(userid);
		tb.setAuditdate(Dateutil.getCurrentDate());
		tb.setIsblankout(0);
		tb.setIsread(0);
		tb.setMakeid(userid);
		tb.setMakedate(Dateutil.getCurrentDate());
		tb.setMakeorganid(makeorganid);
		tb.setOid(oid);
		tb.setOname(oname);
		tb.setSenddate(Dateutil.getCurrentDate());
		tb.setRlinkman(linkman);
		tb.setTel(tel);
		
		EntityManager.save3(tb);
	}
	
	/**
	 * 生成检货小票TT
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 18, 2013 8:48:58 AM
	 */
	private void generateTTBill() throws Exception {
		String otoid = null;
		String otoname = null;
		Organ oto = wh_OrganMap.get(inwarehouseid);
		if(oto!=null){
			otoid= oto.getId();
			otoname = oto.getOrganname();
		}
		//生成TT 单
		TakeTicket tt = new TakeTicket();
		ttid = MakeCode.getExcIDByRandomTableName("take_ticket", 2, "TT");
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
		EntityManager.save3(tt);
	}
	
	/**
	 * 验证扫描类型
	* @param bean
	* @author wenping   
	* @CreateTime Jan 10, 2013 11:41:16 AM
	 */
	private boolean validateScanType(UploadIdcodeBean bean) {
		if(!Constants.SCAN_TYPE_OM.equals(bean.getScanType())){
			addErrorInfo(bean,null,"E00107:扫描类型错误");
			return false;
		}
		return true;
	}
	
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
			addErrorInfo(bean,idcode,"E00108:条码格式不正确");
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
		if(Constants.CODEUNIT_B.equals(bean.getScanFlag()) ){
			//箱的 情况
			if(ic == null){
				//系统不存在该条码
				if(flagB==1){//如果允许上传不存在系统中的条码(由系统变量值确定)
					ic=addToIdcode(outwarehouseid,bean,bean.getIdcode());
				}else{
					addErrorInfo(bean,bean.getIdcode(),"E00109:该条码不在系统中");
					return false;
				}
			}else{
				
				//验证产品
				boolean isExist = validateProduct(ic,bean);
				if(!isExist){
					return false;
				}
				
				//验证产品是否匹配
				boolean isMatch = validateMatch(bean,ic);
				if(!isMatch){
					return false;
				}
				
				//验证条码
				boolean ispass = validateIdcodeAvaliable(ic,bean);
				if(!ispass){
					return false;
				}
				ic.setIsuse(0);
				EntityManager.update2(ic);	
			}
			
			unitid = 2;
			boxNum++;
			
		}else if(Constants.CODEUNIT_Z.equals(bean.getScanFlag())){
			//散装的情况
			if(ic!=null){
				//散装存在时
				
				//验证产品
				boolean isExist = validateProduct(ic,bean);
				if(!isExist){
					return false;
				}
				
				//验证产品是否匹配
				boolean isMatch = validateMatch(bean,ic);
				if(!isMatch){
					return false;
				}
				
				//验证条码
				boolean ispass = validateIdcodeAvaliable(ic,bean);
				if(!ispass){
					return false;
				}
				ic.setIsuse(0);
				EntityManager.update2(ic);	
				
				unitid = ic.getUnitid();
			}else{
				//散不存在
				AppUploadProduceReport  appupr = new AppUploadProduceReport();
				//获取对应的箱条码
				UploadProduceReport aUploadProduceReport=appupr.getUploadProduceReportByUnitNo(bean.getIdcode());
				if(aUploadProduceReport==null){
					addErrorInfo(bean,bean.getIdcode(),"E00110:该条码不在系统中");
					return false;
				}else{
					String boxIdcode=aUploadProduceReport.getBoxCode();
					//验证条码是否已存在当前单据(,单号,条码)
					boolean isExist  = isExist(1,boxIdcode,bean);
					if(isExist){
						return false;
					}
					
					ic = ai.getIdcodeById(boxIdcode);
					if(ic==null){
						//所在箱条码不存在时
						if(flagB==1){
							issplit= 1;
							//将箱条码保存到Idcode
							ic = addToIdcode(outwarehouseid,bean,boxIdcode);
							//将散码保存到Idcode中
							ic = addToIdcodeZ(outwarehouseid,bean,ic.getBatch());
						}else{
							addErrorInfo(bean,bean.getIdcode(),"E00111:所在箱码("+boxIdcode+")不在系统中");
							return false;
						}
					}else{
						
						//验证产品
						isExist = validateProduct(ic,bean);
						if(!isExist){
							return false;
						}
						
						//验证产品是否匹配
						boolean isMatch = validateMatch(bean,ic);
						if(!isMatch){
							return false;
						}
						
						//验证条码
						boolean ispass = validateIdcodeZ(ic,bean);
						if(!ispass){
							return false;
						}
						
						unitid = productMap.get(ic.getProductid()).getScatterunitid();
						issplit= 1;
						//修改Idcode -->拆箱
						ic.setPackquantity(ic.getPackquantity()-af.getXQuantity(ic.getProductid(), unitid));
						EntityManager.update2(ic);	
						
						//将散码保存到Idcode中
						ic = addToIdcodeZ(outwarehouseid,bean,ic.getBatch());
					}
				}
			}
			
			scatterNum++;
		}else{
			addErrorInfo(bean,null,"E00112:不正确的扫描标识位");
			return false;
		}
		
		if(ic.getBatch()!=null && ic.getBatch().length()>=9){
			batch = ic.getBatch().substring(0, 9);
		}else{
			batch = ic.getBatch();
		}
		
		//保存条码信息
		genTakeTicketIdcode(ic);

		return true;
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
//		String idcode  = bean.getIdcode();
//		//取得条码后三位(000为箱,非000为散装)
//		String unitType = idcode.substring(idcode.length()-3, idcode.length());
		if(Constants.CODEUNIT_B.equals(bean.getScanFlag())){
			if(ic==null){
				addErrorInfo(bean,bean.getIdcode(),"E00113:该条码不在系统中");
				return false;
			}else{
				//验证产品
				boolean isExist = validateProduct(ic,bean);
				if(!isExist){
					return false;
				}
				
				//验证产品是否匹配
				boolean isMatch = validateMatch(bean,ic);
				if(!isMatch){
					return false;
				}
				
				//验证条码
				boolean ispass = validateIdcodeAvaliable(ic,bean);
				if(!ispass){
					return false;
				}
				ic.setIsuse(0);
				EntityManager.update2(ic);	
			}
			
			unitid = 2;
			boxNum++;
		}else if(Constants.CODEUNIT_Z.equals(bean.getScanFlag())){
			//散装的情况
			if(ic!=null){
				//散码存在的情况
				//验证产品
				boolean isExist = validateProduct(ic,bean);
				if(!isExist){
					return false;
				}
				
				//验证产品是否匹配
				boolean isMatch = validateMatch(bean,ic);
				if(!isMatch){
					return false;
				}
				
				//验证条码
				boolean ispass = validateIdcodeAvaliable(ic,bean);
				if(!ispass){
					return false;
				}
				ic.setIsuse(0);
				EntityManager.update2(ic);	
				
				unitid = ic.getUnitid();
			}else{
				//得到所在箱码
				String boxidcode = bean.getIdcode().substring(0, bean.getIdcode().length()-3)+"000";
				//验证条码是否已存在当前单据(,单号,条码)
				boolean isExist  = isExist(1,boxidcode,bean);
				if(isExist){
					return false;
				}
				ic = ai.getIdcodeById(boxidcode);
				if(ic==null){
					addErrorInfo(bean,bean.getIdcode(),"E00114:所在箱码("+boxidcode+")不在系统中");
					return false;
				}else{
					//验证产品
					isExist = validateProduct(ic,bean);
					if(!isExist){
						return false;
					}
					
					//验证产品是否匹配
					boolean isMatch = validateMatch(bean,ic);
					if(!isMatch){
						return false;
					}
					
					//验证所在箱条码
					boolean ispass = validateIdcodeZ(ic,bean);
					if(!ispass){
						return false;
					}
					
					unitid = productMap.get(ic.getProductid()).getScatterunitid();
					issplit= 1;
					//修改Idcode -->拆箱
					ic.setPackquantity(ic.getPackquantity()-af.getXQuantity(ic.getProductid(), unitid));
					EntityManager.update2(ic);	
					
					//将散码保存到Idcode中
					ic = addToIdcodeZ(outwarehouseid,bean,ic.getBatch());
				}
			}
			
			scatterNum++;
		}else{
			addErrorInfo(bean,null,"E00179:不正确的扫描标识位");
			return false;
		}
		
		batch = ic.getBatch();
		
		//保存条码信息
		genTakeTicketIdcode(ic);
		
		return true;
	}
	
	/**
	 * 插入条码表
	* @param ic
	* @author wenping   
	 * @throws Exception 
	 * @throws NumberFormatException 
	* @CreateTime Jan 10, 2013 3:51:37 PM
	 */
	/*private void generateReceiveIncomeIdcode(Idcode ic) throws NumberFormatException, Exception {

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
	}*/

	/**
	 * 校验条码
	* @param ic
	* @author wenping   
	* @CreateTime Jan 10, 2013 3:02:20 PM
	 */
/*	private boolean validateIdcodeAvaliable(Idcode ic,UploadIdcodeBean bean) {
		//如果条码是已出库状态，则通过
		if(ic.getIsuse()==0 && ic.getIsout()==1){
			return true;
		}else{
			addErrorInfo(bean,ic.getIdcode(),"该条码未曾出库");
		}
		return false;
	}*/

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
		
		errorList.add("成功 "+(totalNum-failNum)+" 条,失败"+failNum+"条");
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
	 * 获取用户相关信息
	* @param username
	* @throws Exception
	* @author wenping   
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
	* @param key
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jul 23, 2012 4:00:04 PM
	 */
	/*private Integer getBaseResourceFlag(Integer key) throws Exception{
		AppBaseResource abr = new AppBaseResource();
		BaseResource bar=abr.getBaseResourceValue("UploadIdcodeFlag", key);
		Integer intFlag=bar.getIsuse();
		return intFlag;
	}*/
	
	/**
	 * 保存条码到Idcode表,用于箱条码
	 */
	private Idcode addToIdcode(String warehouseId,UploadIdcodeBean bean,String idcode) throws Exception{
        Warehouse w = whMap.get(warehouseId);
        Product p =lcodeMap.get(bean.getLcode());
        double boxQuantity=0;
        int useFlag=0;
        if(issplit==1){
        	useFlag = 1;
        	boxQuantity = af.getXQuantity(p.getId(), 2)-af.getXQuantity(p.getId(), unitid);
        }else{
        	boxQuantity=af.getXQuantity(p.getId(), 2);
        }
        
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
		ic.setPackquantity(boxQuantity);
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
	/**
	 * 保存条码到Idcode表,用于散码
	 */
	private Idcode addToIdcodeZ(String warehouseId,UploadIdcodeBean bean,String batch) throws Exception{
        Warehouse w = whMap.get(warehouseId);
        Product p =lcodeMap.get(bean.getLcode());
        double boxQuantity = af.getXQuantity(p.getId(), unitid);
        
        Idcode ic = new Idcode();
		ic.setIdcode(bean.getIdcode());
		ic.setProductid(p.getId());
		ic.setProductname(p.getProductname());
		ic.setBatch(batch);
		ic.setLcode(bean.getLcode());
		ic.setStartno(bean.getIdcode());
		ic.setEndno(bean.getIdcode());
		ic.setUnitid(unitid);
		ic.setQuantity(1d);
		ic.setFquantity(boxQuantity);
		ic.setPackquantity(boxQuantity);
	    ic.setIsuse(0);
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
	
	
	/**
	 * 验证条码是否在当前仓库 是否可用 是否已出库
	* @param ic
	* @return
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jul 13, 2012 3:58:25 PM
	 */
	private boolean validateIdcodeAvaliable(Idcode ic,UploadIdcodeBean bean) throws Exception{
		/*if(!ic.getWarehouseid().equals(outwarehouseid)){
			addErrorInfo(bean,bean.getIdcode(),"E00115:该条码不在当前仓库中");
			return false;
		}else */
		if(ic.getPackquantity().doubleValue()!=ic.getFquantity().doubleValue()){
			addErrorInfo(bean,bean.getIdcode(),"E00116:该条码不可用(因为该箱已被拆散码出库)");
			return false;
		}else if(ic.getIsout()==1){
			addErrorInfo(bean,bean.getIdcode(),"E00117:该条码已出库");
			return false;
		}else if(ic.getIsuse()==0){
			addErrorInfo(bean,bean.getIdcode(),"E00118:该条码已被其它单据使用");
			return false;
		}
		return true;
	}
	
	/**
	 * 验证散码所在箱码
	* @param ic
	* @param bean
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 18, 2013 1:19:47 PM
	 */
	private boolean validateIdcodeZ(Idcode ic,UploadIdcodeBean bean) throws Exception{
		if(!ic.getWarehouseid().equals(outwarehouseid)){
			addErrorInfo(bean,bean.getIdcode(),"E00119:该条码不在当前仓库中");
			return false;
		}else if(ic.getPackquantity()- af.getXQuantity(ic.getProductid(), unitid)<0){
			addErrorInfo(bean,bean.getIdcode(),"E00120:该条码不可用，因为该条码所在箱码("+ic.getIdcode()+")已没有任何散码可供使用)");
			return false;
		}else if(ic.getIsout()==1){
			addErrorInfo(bean,bean.getIdcode(),"E00121:该条码不可用，因为该条码所在的箱码("+ic.getIdcode()+")已出库");
			return false;
		}else if(ic.getIsuse()==0){
			addErrorInfo(bean,bean.getIdcode(),"E00122:该条码不可用，因为该条码所在的箱码("+ic.getIdcode()+")已被其它单据使用");
			return false;
		}
		return true;
	}
	
	/**
	 * 生成TTIdcode
	* @author wenping   
	* @CreateTime Jan 17, 2013 5:35:09 PM
	 */
	private void genTakeTicketIdcode(Idcode ic) throws Exception, IdcodeException
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
		tti.setIssplit(issplit);
		tti.setMakedate(DateUtil.getCurrentDate());
		tti.setOrganfromid("out");
		EntityManager.save3(tti);
	}
	
	/**
	 * 保存StockAlterMoveIdcode
	* @param tti
	* @author wenping   
	* @CreateTime Jan 18, 2013 4:30:50 PM
	 */
	private void saveStockAlterMoveIdcode(List<TakeTicketIdcode> idlist) throws Exception {
		for ( TakeTicketIdcode tti : idlist ){
			StockAlterMoveIdcode idcode = new StockAlterMoveIdcode();
			BeanUtils.copyProperties(idcode, tti);
//			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_idcode", 0, "")));
			idcode.setSamid(omid);
			idcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			idcode.setNccode("out");
			EntityManager.save3(idcode);
		}
	}

	/**
	 * 验证条形码是否存在在当前单据中
	 */
	private boolean isExist(int flag,String currentIdcode,UploadIdcodeBean bean) throws Exception
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
	}
}
