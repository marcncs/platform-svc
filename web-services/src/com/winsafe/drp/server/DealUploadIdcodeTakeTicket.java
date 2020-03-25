package com.winsafe.drp.server;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadIdcode;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.pojo.CartonCode;

/**
 * 出库条码解析
* @Title: DealUploadIdcodeTakeTicket.java
 */
public class DealUploadIdcodeTakeTicket extends DealUploadIdcode
{
	private Logger logger = Logger.getLogger(DealUploadIdcodeTakeTicket.class);
	
	protected UploadCodeRuleService crs = new UploadCodeRuleService();
	
	private AppCartonCode appCartonCode = new AppCartonCode();
	
	public DealUploadIdcodeTakeTicket()
	{
	}
	public DealUploadIdcodeTakeTicket(String filepath, String fileaddress, int iuid)
	{
		super(filepath, fileaddress, iuid);
	}

	@SuppressWarnings("unchecked")
	public String addIdcode(String uploadidcode)
	{
		
		String t_ret = null;
		String billNo = null;
		UploadIdcode bean = null;
		try
		{
			JSONObject  uploadJsonObj = JSONObject.fromString(uploadidcode) ;
			//获取上传对像bean
			bean = crs.getOMUploadIdcode(uploadJsonObj);
			// 对于24位的箱码处理,先去箱码表中查找到对应的箱码
			if(bean.getIdcode().length() == Constants.CARTON_OLD_LENGTH){
				CartonCode cartonCode = appCartonCode.getByOutPin(bean.getIdcode());
				if(cartonCode != null){
					bean.setOutMpinCode(bean.getIdcode());
					bean.setIdcode(cartonCode.getCartonCode());
				}
			}
			setUploadIdcode(bean);
			//判断扫描标识,扫描只有箱
			if(!Constants.CODEUNIT_B.equals(bean.getScanFlag()) ){
				throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00014));
			}
			//判断扫描类型
			if(Constants.SCAN_TYPE_OM.equals(bean.getScanType()) 
					|| Constants.SCAN_TYPE_SM.equals(bean.getScanType())
					|| Constants.SCAN_TYPE_DB.equals(bean.getScanType())
					|| Constants.SCAN_TYPE_OW.equals(bean.getScanType())
					|| Constants.SCAN_TYPE_PW.equals(bean.getScanType())
				){
			
			}else {
				throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00010));
			}
			//验证单据是否正确
			TakeTicket tt = getTakeTicket(bean.getBillNo());
			billNo = tt.getBillno();
			validateTakeTicket(uploadJsonObj.toString(),tt.getBillno(),tt);
			//验证条形码
			t_ret = validateIdcode(bean);
			//验证产品与条码是否一致
			if(!StringUtil.isEmpty(bean.getProductId())){
				Product p = getProduct(bean.getProductId());
				if( p != null ){
					Idcode ic = getIdcode(bean.getIdcode());
					if(!p.getId().equals(ic.getProductid())){
						throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00009,
								tt.getBillno(),bean.getProductId()));
					}
				}
			}
			//验证条码的产品正确性
			validateTakeTicketProduct(bean);
			//验证条形码是否存在在当前单据中
			isExist(bean,tt.getBillno());
			//生成条码
			genTakeTicketIdcode(bean,0);
			//更新条码状态为不可用
			appidcode.updIsUse(bean.getIdcode(), 0);
			//每行记录提交事务
			HibernateUtil.commitTransaction();
		}catch(IdcodeException e){
			// 错误信息事务回滚
			HibernateUtil.rollbackTransaction();
			t_ret = e.getMessage();
		}catch(Exception e){
			// 错误信息事务回滚
			HibernateUtil.rollbackTransaction();
			logger.error("",e);
			t_ret = UploadErrorMsg.getError(UploadErrorMsg.E00013);
		}
		if(billNo != null) {
			bean.setBillNo(billNo);
		}
		if(!StringUtil.isEmpty(t_ret)){
			if(bean != null) {
				addErrorInfo(bean.getIdcode(), t_ret);
			} else {
				addErrorInfo("", t_ret);
			}
		}
		//返回验证错误信息
		return t_ret;
	}
	/**
	 * 验证单据中的产品与条码是否相符
	 * Create Time 2014-12-25 下午05:00:01 
	 */
	private void validateTakeTicketProduct(UploadIdcode bean) throws Exception
	{
		List list = appttd.getTakeTicketDetailByTtidPid(bean.getBillNo(), bean.getProductId());
		if(list == null || list.size()<=0){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00108,bean.getIdcode()));
		}
	}
	
	/**
	 * 让上传的条码和单据关联起来
	 * @param billno
	 * @throws Exception
	 * @throws IdcodeException
	 */
	private void genTakeTicketIdcode(UploadIdcode bean,int issplit) throws Exception, IdcodeException
	{
	    TakeTicketIdcode tti = new TakeTicketIdcode();
//		tti.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
		tti.setTtid(bean.getBillNo());
		tti.setIsidcode(1);
		tti.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
		tti.setProductid(bean.getProductId());
		tti.setBatch(bean.getBatch());
		tti.setProducedate(bean.getProDate());
		tti.setVad("");
		tti.setUnitid(bean.getUnitid());
		tti.setQuantity(1d);
		tti.setPackquantity(bean.getPackQuantity());
		tti.setIdcode(bean.getIdcode());
		tti.setLcode(bean.getLcode());
		tti.setStartno(bean.getIdcode());
		tti.setEndno(bean.getIdcode());
		tti.setIssplit(issplit);
		tti.setMakedate(DateUtil.getCurrentDate());
		apptti.addTakeTicketIdcode(tti);
	}

	/**
	 * 验证单据 Create Time: Oct 11, 2011 4:03:35 PM
	 * @param uploadidcode
	 * @return
	 * @author dufazuo
	 * @throws Exception
	 */
	private void validateTakeTicket(String uploadidcode,String billno,TakeTicket tt) throws Exception
	{
		//判断单据状态是否正确
		//单号不存在或非TT开头的条码或单号不存在
		if ( tt == null)
		{
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00001, billno));
		}
		//判断对应的单据状态(已复核的不能进行操作)
		if (tt.getIsaudit() == 1)
		{
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00002, tt.getBillno()));
		}
		//判断对应的单据状态(已作废的不能进行操作)
		if(tt.getIsblankout() == 1){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00007, tt.getBillno()));
		}
	}

	/**
	 * 验证条形码是否存在在当前单据中
	 */
	private void isExist(UploadIdcode bean,String billId) throws Exception
	{
		String billno = bean.getBillNo();
		String idcode = bean.getIdcode();
		if(takeTicketIdcodeSet.contains(billno + "_" + idcode))
		{
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00006, idcode));
		}
		TakeTicketIdcode tti = apptti.getTakeTicketIdcodeByttidAndIdcode(billno, idcode);
		if(tti != null){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00006, idcode));
		}
		takeTicketIdcodeSet.add(bean.getBillNo() + "_" + bean.getIdcode());
	}
	
	/**
	 * 验证条码
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	private String validateIdcode(UploadIdcode bean) throws Exception
	{
		// 记录特殊的错误信息(只记录,不报错)
		String errorMsg = null;
		String idcode = bean.getIdcode();
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00003,bean.getOutMpinCode()));
		}
		Idcode ic = getIdcode(bean.getIdcode());
		// 条码不存在
		if(ic == null){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00003,idcode));
		}
		//条码不可用(并且条码的当前仓库与出库仓库相同)
		if(ic.getIsuse() == 0 && ic.getWarehouseid().equals(bean.getOutWarehouseId())){
			throw new IdcodeException(UploadErrorMsg.getError(UploadErrorMsg.E00004,idcode));
		}
		// 条码不在当前仓库中,只记录,不报错
		if(!ic.getWarehouseid().equals(bean.getOutWarehouseId())){
			errorMsg = UploadErrorMsg.getError(UploadErrorMsg.E00005,idcode);
		}
		//bean中的产品为空时,根据条码设置产品ID
		bean.setProductId(ic.getProductid());
		//设置条码的最小包装数量
		bean.setPackQuantity(ic.getPackquantity());
		//设置条码的batch
		bean.setBatch(ic.getBatch());
		//设置条码的单位 
		bean.setUnitid(ic.getUnitid());
		bean.setProDate(ic.getProducedate());
		return errorMsg;
	}
}
