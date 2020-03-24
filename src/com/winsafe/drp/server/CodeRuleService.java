package com.winsafe.drp.server;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppCodeRule;
import com.winsafe.drp.dao.CodeRule;
import com.winsafe.drp.dao.CodeRuleUpload;
import com.winsafe.drp.dao.CodeUnit;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.hbm.util.cache.CodeRuleCache;
import com.winsafe.hbm.util.cache.CodeRuleUploadCache;

public class CodeRuleService
{
	private AppCodeRule appcr = new AppCodeRule();

	private CodeRuleCache crcache = new CodeRuleCache();

	private CodeRuleUploadCache uploadcache = new CodeRuleUploadCache();
	
	private Map<Integer,CodeRuleUpload> mapCodeRuleUpload = new HashMap<Integer, CodeRuleUpload>();

	public List getCodeUnitList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception
	{
		return appcr.getCodeUnitList(request, pagesize, pWhereClause);
	}

	public List getCodeUnitList() throws Exception
	{
		return appcr.getCodeUnitList();
	}
	
	/**
	 * 判断标识位是否在定义中
	 * @param f
	 * @return
	 * @throws Exception 
	 */
	public boolean checkCodeUnit(String f) throws Exception{
		List<CodeUnit> list=getCodeUnitList();
		for(CodeUnit cu:list){
			if(cu.getUcode().equals(f)){
				return true;
			}
		}
		return false;
	}

	public List getCodeRuleByUcode(String ucode) throws Exception
	{
		List list = crcache.getCodeRuleByUcode(ucode);
		if (list == null)
		{
			list = appcr.getCodeRuleByUcode(ucode);
			modifyCache();
		}
		return list;
	}

	public int getCodeRuleLengthByUcode(String ucode) throws Exception
	{
		List<CodeRule> list = getCodeRuleByUcode(ucode);
		int crlength = 0;
		if (list != null && !list.isEmpty())
		{
			for (CodeRule cr : list)
			{
				crlength += cr.getLno();
			}
		}
		return crlength;
	}

	public CodeRule getCodeRuleByUcodeCrp(String ucode, int crproperty) throws IdcodeException
	{
		CodeRule cr = crcache.getCodeRuleByUcodeCrp(ucode, crproperty);
		if (cr == null)
		{
			cr = appcr.getCodeRuleByUcodeCrp(ucode, crproperty);
			modifyCache();
		}
		return cr;
	}

	public void addCodeRule(CodeRule cu) throws Exception
	{
		appcr.addCodeRule(cu);
		modifyCache();
	}

	public void updCodeRule(CodeRule cu) throws Exception
	{
		appcr.updCodeRule(cu);
		modifyCache();
	}

	public void delCodeRuleByID(String ucode) throws Exception
	{
		appcr.delCodeRuleByID(ucode);
		modifyCache();
	}

	private void modifyCache()
	{
		try
		{
			crcache.modifyCodeRule(appcr.getAllCodeRule());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getUcode(String idcode) throws IdcodeException
	{
		return idcode.substring(0, 1);
	}

	public String getLcode(String idcode) throws IdcodeException
	{
		return getCodeProperty(idcode, 0);
	}

	public String getStartNo(String idcode) throws IdcodeException
	{
		return idcode;
		//return getLcode(idcode) + getCodeProperty(idcode, 2);
	}

	public String getEndNo(String idcode) throws IdcodeException
	{
		return idcode;
		
//		String lcode = getLcode(idcode);
//		String fromNo = getCodeProperty(idcode, 2);
//		String toUnitNo = getCodeProperty(idcode, 3);
//		try
//		{
//			if (toUnitNo == null || toUnitNo.equals(""))
//			{
//				return lcode + fromNo;
//			}
//			else
//			{
//				String fromNoF = fromNo.substring(0, fromNo.length() - toUnitNo.length());
//				int intFromNoF = Integer.valueOf(fromNoF);
//				int intFromNoL = Integer.valueOf(fromNo.substring(fromNo.length() - toUnitNo.length()));
//				int intToUnitNo = Integer.valueOf(toUnitNo);
//
//				if (intFromNoL <= intToUnitNo)
//				{
//					return lcode + fromNoF + toUnitNo;
//				}
//				else
//				{
//					return lcode + getFormatNums(intFromNoF + 1, fromNoF.length()) + toUnitNo;
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			throw new IdcodeException("CodeRuleService getEndNo error:" + e.getMessage());
//		}
	}

	private String getFormatNums(Integer id, int length)
	{
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMinimumIntegerDigits(length);
		df.setGroupingUsed(false);
		return df.format(id);
	}

	public String getProduceDate(String idcode) throws IdcodeException
	{
		//return DateUtil.StringToStr(getCodeProperty(idcode, 1));
		return getCodeProperty(idcode, 1);
	}

//	public String getBatch(String idcode) throws IdcodeException
//	{
//		String batch = getCodeProperty(idcode, 5);
//		//20100519-----RichieYu----取出批次号的"#"
//		batch = batch.replace("#", "");
//		return batch;
//	}

	public double getStandQuantity(String idcode) throws IdcodeException
	{
		String q = getCodeProperty(idcode, 6);
		try
		{
			if (q == null || "".equals(q))
			{
				return 0.00;
			}
			else
			{
				return Double.valueOf(q);
			}
		}
		catch(Exception e)
		{
			throw new IdcodeException("CodeRuleService getStandQuantity error:" + e.getMessage());
		}
	}

	/**
	 * 主要功能:根据条码得到数量(结束码-起始码)
	 * @param idcode
	 * @return
	 * @throws IdcodeException
	 */
	public double getRealQuantity(String idcode) throws IdcodeException
	{
		try
		{
			int startNo = Integer.valueOf(this.getStartNo(idcode));
			int endNo = Integer.valueOf(this.getEndNo(idcode));
			return endNo - startNo + 1;
		}
		catch(Exception e)
		{
			throw new IdcodeException("CodeRuleService getRealQuantity error:" + e.getMessage());
		}
	}

	/**
	 * 主要功能:自动填补条码最后5位包装数量
	 * @param idcode 原条码
	 * @return 填补后的条码
	 * @throws Exception
	 */
	public String addQuantityCode(String idcode) throws Exception
	{
		String ucode = getUcode(idcode);
		//检查长度是否和系统长度相同,相同情况直接返回
		if (idcode.length() == getCodeRuleLengthByUcode(ucode))
		{
			return idcode;
		}
		Integer quantity = (int) getRealQuantity(idcode + "00000");
		String zeroQuantity = "00000";
		zeroQuantity = zeroQuantity.substring(0, zeroQuantity.length() - quantity.toString().length()) + quantity;
		return idcode + zeroQuantity;
	}

	private String getCodeProperty(String idcode, int pp) throws IdcodeException
	{
		if (idcode == null || "".equals(idcode))
		{
			return "";
		}
		try
		{
			//-----RichieYu-----中农多一位条形码
			//TODO设置多一位
			//			String ucode = getUcode(idcode);
			//			if ( idcode.length() != getCodeRuleLengthByUcode(ucode)){
			//				throw new IdcodeException("getCode error:ucode="+ucode+" length is error");
			//			}
			String ucode = "B";
			CodeRule cru = getCodeRuleByUcodeCrp(ucode, pp);
			if (cru == null)
			{
				throw new IdcodeException("getCode error:ucode=" + ucode + " can't finded crproperty=" + pp);
			}
//			System.out.println("===========getStartno======>" + cru.getStartno() + "==getLno==" + cru.getLno());
			if (cru.getStartno() == 0 || cru.getLno() == 0)
			{
				return "";
			}
			//-----RichieYu-----中农新增产品状态字段.故起始位置+1

			return idcode.substring(cru.getStartno() - 1, cru.getStartno() - 1 + cru.getLno());
			//return idcode.substring(0,2);

		}
		catch(Exception e)
		{
			throw new IdcodeException("CodeRuleService getCodeProperty error:" + e.getMessage());
		}
	}

	//-------------------CodeRuleUpload---------------------
	public List getAllCodeRuleUploadList() throws Exception
	{
		List list = uploadcache.getAllCodeRuleUpload();
		if (list == null)
		{
			list = appcr.getAllCodeRuleUploadList();
			modifyUploadCache();
		}
		return list;
	}

	private void modifyMapCodeRuleUpload(){
		try
		{
			List codeRuleUploadList = appcr.getAllCodeRuleUploadList();
			if(codeRuleUploadList!=null){
				CodeRuleUpload objCru = null;
				for(int i=0;i<codeRuleUploadList.size();i++){
					objCru = (CodeRuleUpload) codeRuleUploadList.get(i);
					if(objCru!=null && objCru.getCruproperty()!=null){
						mapCodeRuleUpload.put(objCru.getCruproperty(), objCru);
					}
				}
				//uploadcache.modifyCodeRuleUpload(codeRuleUploadList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int getCodeRuleUploadLength() throws Exception
	{
		
		
		if(mapCodeRuleUpload.isEmpty()){
			modifyMapCodeRuleUpload();
		}
		Set<Integer> set = mapCodeRuleUpload.keySet();
		int crulength = 0;
		if(set != null && set.size()>0){
			for(Integer crproperty : set){
				crulength += mapCodeRuleUpload.get(crproperty).getLno();
			}
		}
		return crulength;
		
//		List<CodeRuleUpload> list = getAllCodeRuleUploadList();
//		int crulength = 0;
//		if (list != null && !list.isEmpty())
//		{
//			for (CodeRuleUpload cru : list)
//			{
//				crulength += cru.getLno();
//			}
//		}
//		return crulength;
	}

	public CodeRuleUpload getCodeRuleUploadByCrp(int crproperty) throws Exception
	{
		
		CodeRuleUpload cru = null;
		if(mapCodeRuleUpload.get(crproperty) == null){
			modifyMapCodeRuleUpload();
		}
		cru = mapCodeRuleUpload.get(crproperty);
		if(cru==null){
			cru = appcr.getCodeRuleUploadByCrp(crproperty);
		}
		return cru;
//		CodeRuleUpload cru = uploadcache.getCodeRuleUploadByCrp(crproperty);
//		if (cru == null)
//		{
//			cru = appcr.getCodeRuleUploadByCrp(crproperty);
//			modifyUploadCache();
//		}
//		return cru;
	}

	public void updCodeRuleUpload(CodeRuleUpload cu) throws Exception
	{
		appcr.updCodeRuleUpload(cu);
		modifyUploadCache();
	}

	//获取单号
	public String getBillNo(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 0);
	}

	public String setBillNo(String idcode, String billno) throws IdcodeException
	{
		return setUploadProperty(idcode, 0, billno);
	}

	//获取仓库编号
	public String getWarehouseId(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 1);
	}
	
	//获取生产日期
	public String getProductDate(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 2);
	}
	
	//获取批次
	public String getBatch(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 3);
	}
	
	//获取产品编号
	public String getProductId(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 4);
	}
	
	//获取扫描标识位
	public String getScanSign(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 5);
	}
	
	//从采集条码中解析出条形码
	public String getIdcode(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 6);
	}
	
	//获取扫描类型
	public String getScanType(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 7);
	}

	//获取扫描日期
	public String getScanDate(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 8);
	}

	//获取产品数量
	public String getProductCount(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 9);
	}

	//获取采集器编号
	public String getScannerNo(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 10);
	}
	
	//获取发货仓库编号
	public String getOutWarehouseId(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 12);
	}
	
	//获取用户名
	public String getUserName(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 11);
	}
	
	public String getWarehouseBit(String idcode) throws IdcodeException
	{
		return getUploadProperty(idcode, 2);
	}

	/**
	 * 检查条码得到相应的数据字段
	 * @param idcode 条码
	 * @param pp 位数
	 * @return 返回对应的数据
	 * @throws IdcodeException
	 */
	private String getUploadProperty(String idcode, int pp) throws IdcodeException
	{
		if (idcode == null || "".equals(idcode))
		{
			return "";
		}
		try
		{
			//检查数据库配置好的条码字段
			//兼容旧采集器
			if (idcode.length() != 101 && idcode.length() != getCodeRuleUploadLength())
			{
				throw new IdcodeException("getUploadProperty error: the length is error");
			}
			CodeRuleUpload cru = getCodeRuleUploadByCrp(pp);
			if (cru == null)
			{
				throw new IdcodeException("getUploadProperty error: can't finded cruproperty=" + pp);
			}
			if (cru.getStartno() == 0 || cru.getLno() == 0)
			{
				return "";
			}
			return idcode.substring(cru.getStartno() - 1, cru.getStartno() - 1 + cru.getLno()).trim();

		}
		catch(Exception e)
		{
			throw new IdcodeException("CodeRuleService getUploadProperty error:" + e.getMessage());
		}
	}

	private String setUploadProperty(String idcode, int pp, String value) throws IdcodeException
	{
		if (idcode == null || "".equals(idcode))
		{
			return "";
		}
		try
		{
			//兼容旧采集器
			if (idcode.length() != 101 && idcode.length() != getCodeRuleUploadLength())
			{
				throw new IdcodeException("getUploadProperty error: the length is error");
			}
			CodeRuleUpload cru = getCodeRuleUploadByCrp(pp);
			if (cru == null)
			{
				throw new IdcodeException("getUploadProperty error: can't finded cruproperty=" + pp);
			}
			//System.out.println("===========getStartno======>"+cru.getStartno()+"==getLno=="+cru.getLno());
			if (cru.getStartno() == 0 || cru.getLno() == 0)
			{
				return idcode;
			}
			if (value.length() != cru.getStartno() - 1 + cru.getLno())
			{
				throw new IdcodeException("set billNo error: set value length error");
			}
			String regex = idcode.substring(cru.getStartno() - 1, cru.getStartno() - 1 + cru.getLno());

			return idcode.substring(0, cru.getStartno() - 1) + value
					+ idcode.substring(cru.getStartno() + cru.getLno() - 1, idcode.length() - cru.getStartno() - cru.getLno() + 1);

		}
		catch(Exception e)
		{
			throw new IdcodeException("CodeRuleService getUploadProperty error:" + e.getMessage());
		}
	}

	private void modifyUploadCache()
	{
		try
		{
			uploadcache.modifyCodeRuleUpload(appcr.getAllCodeRuleUploadList());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//------------------CodeRuleUpload---------------------------
}
