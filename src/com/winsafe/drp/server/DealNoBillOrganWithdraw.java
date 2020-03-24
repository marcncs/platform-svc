package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdrawIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcodeBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.drp.util.UploadIdcodeUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 无单退货
* @Title: DealNoBillOrganWithdraw.java
* @version:
 */
public class DealNoBillOrganWithdraw extends DealNoBillUploadService{
	
	private static Logger logger = Logger.getLogger(DealNoBillOrganWithdraw.class);
	
	public DealNoBillOrganWithdraw(){}

	public DealNoBillOrganWithdraw(String filepath, String fileaddress, int iuid,String username)
	{
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<String> deal() throws Exception {
		try {
			//获取用户信息
			getUserInfo(this.username);
			wvMap = UploadIdcodeUtil.getWVAuthority(userid);
			ruwMap = UploadIdcodeUtil.getRUWAuthority(userid);
			
			List<UploadIdcodeBean> idcodeBeanList = new ArrayList<UploadIdcodeBean>();
			//处理上传的文件
			dealTxtFile(idcodeBeanList,Constants.SCAN_TYPE_OW_NOBILL);
			//缓存相关数据
			cacheData();
			//对上传后的产品进行分类整理
			Map<String,Map<String,DetailBean>> productDetailMap = getProductDetail(idcodeBeanList);
			//对上传后的所有条码进行分类整理
			Map<String,List<UploadIdcodeBean>> idcodeMap = sortUploadIdcodeBean(idcodeBeanList);
			//处理分类后的map
			dealResultMap(productDetailMap,idcodeMap);
			//封装错误信息
			ArrayList<String> errorList = generateErrorInfo(errorMap);
			//更新条码上传日志
			updLogNum();
			return errorList;
		} catch (Exception e) {
			generateErrorInfo(errorMap);
			appiu.updNum(iuid, -1, 0, totalNum, failAddress);
			HibernateUtil.commitTransaction();
			throw e;
		}
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
	 * 处理分类后的Map 
	* @param resultMap
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 10:18:18 AM
	 */
	@SuppressWarnings("unchecked")
	private void dealResultMap(Map<String,Map<String,DetailBean>> productDetailMap,Map<String,List<UploadIdcodeBean>> idcodeMap) throws Exception {
		for(String out_in_whid : idcodeMap.keySet()){
			Set<String> idcodeSet = new HashSet<String>();
			//得到出入库仓库id的key
			String[] arr = out_in_whid.split("_");
			//出库仓库
			String outwarehouseid = arr[0];
			//入库仓库
			String inwarehouseid = arr[1];
			// 获取产品数量
			Map<String,DetailBean> productCountMap = productDetailMap.get(out_in_whid);
			// 存放实际的产品数量
			Map<String, Double> idcodeCountMap = new HashMap<String, Double>();
			
			try {
				//验证出库仓库
				boolean isOutExist = validateWarehouse(outwarehouseid);
				//验证出库仓库权限
				boolean hasRUWAuth = validateRUWAuthority(inwarehouseid);
				//验证入库仓库
				boolean isInExist = validateWarehouse(inwarehouseid);
				//验证入库仓库权限
				boolean hasWVAuth = validateWVAuthority(outwarehouseid);
				
				if(isOutExist && isInExist){
					//生成订购单据
					String owid = generateBill(outwarehouseid,inwarehouseid);
					//生成TT单据
					String ttid = generateTTBill(outwarehouseid,inwarehouseid,owid,7);
					// 增加条码
					for(UploadIdcodeBean bean: idcodeMap.get(out_in_whid)){
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
						//判断是否为入库机构					
						Idcode ic = getIdcode(bean.getIdcode());
						genTakeTicketIdcode(ic, ttid);
						// 统计每个产品的数量
						countMapAdd(idcodeCountMap, ic.getProductid(), ic.getPackquantity());
						//quantity += ic.getPackquantity() ;
					}
					// 增加详情
					for(String pid : idcodeCountMap.keySet()){
						// 根据接口上传的数量为准
						Double quantity = 0d;
						Integer unitid = Constants.DEFAULT_UNIT_ID;
						DetailBean detailBean = productCountMap.get(pid);
						// 如查计划数量不存在,则以条码数量为准
						if(detailBean == null){
							detailBean = new DetailBean();
							detailBean.setPid(pid);
							detailBean.setUnitid(unitid);
							detailBean.setQuantity(idcodeCountMap.get(pid));
						}
						if(detailBean.getQuantity() == null){
							detailBean.setQuantity(idcodeCountMap.get(pid));
						}
						quantity = detailBean.getQuantity();
						unitid = detailBean.getUnitid();
						//生成单据详情
						generateDetail(getProduct(pid),owid,quantity,unitid);
						//生成TT单据详情
						generateTTDetail(getProduct(pid),ttid,quantity,unitid);
						
					}
					// 依据实际正确的码进行库存扣减
					List<TakeTicketIdcode> ttiList = att.getTakeTicketIdcodeByttid(ttid);
					if(ttiList != null && ttiList.size() >0){
						//自动复核单据
						autoAuditBill(outwarehouseid,ttiList,productCountMap,idcodeCountMap,ttid,MEMO_TT);
						//复制条码
						addOrganWithdrawIdcode(ttiList,owid);
						//自动签收单据
						autoReceiveBill(inwarehouseid,ttiList,productCountMap,idcodeCountMap,owid,MEMO_OW_RECEIVE);
						//更改条码信息
						updIdcode(inwarehouseid,ttiList);
						HibernateUtil.commitTransaction();
					}else {
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
 * 生成退货单详情
* @param riid
 * @throws Exception 
 * @throws NumberFormatException 
* @CreateTime Jan 10, 2013 5:12:44 PM
 */
	private void generateDetail(Product p,String owid,Double quantity,Integer unitid) throws NumberFormatException, Exception {
		OrganWithdrawDetail owd = new OrganWithdrawDetail();
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_detail", 0, ""));
		owd.setId(id);
		owd.setOwid(owid);
		owd.setProductid(p.getId());
		owd.setProductname(p.getProductname());
		owd.setSpecmode(p.getSpecmode());
		// 产品单位取product中的小包装单位
		owd.setUnitid(unitid);
		owd.setUnitprice(0d);
		// 共有多少小包装数量
		owd.setQuantity(quantity);
		//物料号
		owd.setNccode(p.getmCode());
		owd.setRatifyquantity(quantity);
		//箱数
		owd.setBoxnum(0);
		//散数
		owd.setScatternum(0d);
		owd.setTakequantity(quantity);
		owd.setSubsum(0d);
		EntityManager.save(owd);
	}

	/**
	 * 生成退货单
	* @param whid
	* @return
	* @throws Exception
	* @author wenping   
	* @CreateTime Jan 10, 2013 4:43:46 PM
	 */
	@SuppressWarnings("unchecked")
	private String generateBill(String outwarehouseid,String inwarehouseid) throws Exception {
		Organ outOrgan = wh_OrganMap.get(outwarehouseid);
		String outorganid = null;
		String outorganidname = null;
		if(outOrgan!=null){
			outorganid=outOrgan.getId();
			outorganidname = outOrgan.getOrganname();
		}
		
		String linkman = null;
		String tel = null;
		Olinkman olinkman = getOlinkman(wh_OrganIDMap.get(inwarehouseid));
		if(olinkman != null){
			linkman = olinkman.getName();
			tel = olinkman.getMobile();
		}
		
		OrganWithdraw ow = new OrganWithdraw();
		String owid =  MakeCode.getExcIDByRandomTableName("organ_withdraw", 2, "OW");
		ow.setId(owid);
		ow.setPorganid(outorganid);
		ow.setPorganname(outorganidname);
		ow.setWarehouseid(outwarehouseid);
		ow.setReceiveorganid(wh_OrganIDMap.get(inwarehouseid));
		ow.setInwarehouseid(inwarehouseid);
		ow.setMakeid(userid);
		ow.setMakeorganid(makeorganid);
		ow.setMakedeptid(makedeptid);
		ow.setMakedate(Dateutil.addSecond2Date(defatult_delay, DateUtil.getCurrentDate()));
		ow.setIsaudit(1);
		ow.setAuditid(userid);
		ow.setAuditdate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		ow.setIscomplete(1);
		ow.setReceiveid(userid);
		ow.setReceivedate(Dateutil.addSecond2Date(defatult_delay,Dateutil.getCurrentDate()));
		ow.setIsblankout(0);
		ow.setBlankoutid(0);
		ow.setPrinttimes(0);
		ow.setTakestatus(1);
    	 // 设置批准
		ow.setIsratify(1);
		ow.setRatifyid(userid);
		ow.setRatifydate(Dateutil.addSecond2Date(3,DateUtil.getCurrentDate()));
		// 设置确认
		ow.setIsaffirm(1);
		ow.setAffirmid(userid);
		ow.setAffirmdate(Dateutil.addSecond2Date(3,DateUtil.getCurrentDate()));
		ow.setKeyscontent(ow.getId() + "," + ow.getPorganid() + "," + ow.getPorganname() + "," + ow.getPlinkman() + ","
				+ ow.getTel());
		ow.setPlinkman(linkman);
		ow.setTel(tel);
		
		EntityManager.save3(ow);
		
		return owid;
	}

	
	/***
	 * 校验条码
	* @param bean
	* @param existIdcodes
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 10, 2013 2:22:48 PM
	 */
	private boolean validateIdcode(UploadIdcodeBean bean) throws Exception {

		String idcode = bean.getIdcode();
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00107, bean.getOutMpinCode()));
			return false;
		}
		Idcode ic = getIdcode(bean.getIdcode());
		//条码不存在
		if(ic == null){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00003, idcode));
			return false;
		}
		//条码不可用
		if(ic.getIsuse() == 0 && ic.getWarehouseid().equals(bean.getOutwarehouseid())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00004, idcode));
			return false;
		}
		// 判断条码与产品是否相同
		if(ic.getProductid() == null || bean.getLcode() == null || !ic.getProductid().equals(bean.getLcode())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00108, idcode));
			return false;
		}
		// 判断条码是否在当前仓库
		if(!ic.getWarehouseid().equals(bean.getOutwarehouseid())){
			addErrorInfo(bean.getFileLineNo(),UploadErrorMsg.getError(UploadErrorMsg.E00005, idcode));
		}
		return true;
	
	}
	
	/**
	 * 保存OrganWithdrawIdcode
	* @param tti
	* @author wenping   
	* @CreateTime Jan 18, 2013 4:30:50 PM
	 */
	private void addOrganWithdrawIdcode(List<TakeTicketIdcode> idlist,String owid) throws Exception {
		for ( TakeTicketIdcode tti : idlist ){
			OrganWithdrawIdcode idcode = new OrganWithdrawIdcode();
			BeanUtils.copyProperties(idcode, tti);
			idcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_withdraw_idcode", 0, "")));
			idcode.setOwid(owid);
			idcode.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			EntityManager.save3(idcode);
		}
	}
}
