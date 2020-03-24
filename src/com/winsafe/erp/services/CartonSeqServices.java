package com.winsafe.erp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set; 
import java.util.TreeMap;

import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonSeq;
import com.winsafe.sap.pojo.CartonSeqLog;

public class CartonSeqServices {

	private AppCartonSeq appCartonSeq = new AppCartonSeq();
	/**
	 * 获取可用的箱码序号范围
	 * @param seqList
	 * @return
	 */
	public List<String[]> getUsableSeqRangeList(List<String> seqList) {
		List<String[]> rangeList = new ArrayList<String[]>();
		if(seqList.size() == 0) {
			return rangeList;
		}
		long start = Long.valueOf(seqList.get(0));
		int index = 0;
		for(int i = 1;i < seqList.size(); i++) {
			long current =  Long.valueOf(seqList.get(i));
			if(current - start + index == i) {
				continue;
			} else {
				
				rangeList.add(new String[]{CartonSeq.getSeqWithPrefix(String.valueOf(start)),seqList.get(i-1)});
				start = Long.valueOf(seqList.get(i));
				index=i;
			}
		}
		rangeList.add(new String[]{CartonSeq.getSeqWithPrefix(String.valueOf(start)),seqList.get(seqList.size()-1)});
		return rangeList;
	}
	
	/**
	 * 获取可用的箱码序号范围字符串
	 * @param seqList
	 * @return
	 */
	public String getUsableSeqRangeString(List<String> seqList) {
		List<String[]> seqRangeList = getUsableSeqRangeList(seqList);
		StringBuffer range = new StringBuffer();
		for(String[] seqRange : seqRangeList) {
			range.append(","+Long.valueOf(seqRange[0])+"~"+Long.valueOf(seqRange[1]));
		}
		if(range.length() > 0) {
			return range.substring(1);
		}
		return range.toString();
	}
	
	public String getSeqRangeString(long[] startSeq, long[] endSeq) {
		StringBuffer range = new StringBuffer();
		for(int i = 0;i<startSeq.length;i++) {
			range.append(","+startSeq[i]+"~"+endSeq[i]);
		}
		if(range.length() > 0) {
			return range.substring(1);
		}
		return range.toString();
	}
	
	public static void main(String[] args) {
//		AppCartonSeq appCs = new AppCartonSeq();
//		List<String> seqList = appCs.getUsableSeqByProductId("033870");
//		CartonSeqServices css = new CartonSeqServices();
//		List<String[]> list = css.getSeqRanges(seqList);
//		System.out.println("11234561001300000010500331278154".substring(21,24));
	}

	/**
	 * 判断范围是否有效,起始号必须小于等于结束号
	 * @param startSeq
	 * @param endSeq
	 * @return
	 */
	public boolean isSeqRangeLegal(long[] startSeq, long[] endSeq) {
		for(int i=0; i<startSeq.length;i++) {
			if(startSeq[i] > endSeq[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断各范围之间是否有交集
	 * @param startSeq
	 * @param endSeq
	 * @return
	 */
	public boolean isSeqRangeIsolated(long[] startSeq, long[] endSeq) {
		Map<Long,Long[]> map = new TreeMap<Long, Long[]>();
		for(int i=0; i<startSeq.length;i++) {
			map.put(startSeq[i], new Long[]{startSeq[i], endSeq[i]});
		}
		Long endSeqNo = null;
		for(Long key : map.keySet()) {
			if(endSeqNo == null) {
				endSeqNo = map.get(key)[1];
			} else {
				if(map.get(key)[0] <= endSeqNo) {
					return false;
				} else {
					endSeqNo = map.get(key)[1];
				}
			}
		}
		return true;
	}

	/**
	 * 为打印任务关联箱码序号
	 * @param productPlan
	 * @param startSeq
	 * @param endSeq
	 * @return
	 * @throws Exception
	 */
	public int updateProductPlanSeq(int printJobId, String productId, long[] startSeq,
			long[] endSeq) throws Exception {
		AppCartonCode appCartonCode  = new AppCartonCode();
		//箱码列表
		List<String> codes = appCartonCode.getCodeListByPrintJobId(printJobId);
		List<String> sqlList = new ArrayList<String>();
		List<String> seqList = new ArrayList<String>();
		for(int i=0;i<startSeq.length;i++) {
			for(long seq=startSeq[i];seq<=endSeq[i];seq++) {
				seqList.add(CartonSeq.getSeqWithPrefix(String.valueOf(seq)));
			}
		}
		for(int i=0;i<codes.size();i++) {
			sqlList.add(getUpdCartonSeqSql(codes.get(i),productId,seqList.get(i)));
		}
		return appCartonSeq.executeBatchWithResult(sqlList);
	}

	private String getUpdCartonSeqSql(String cartonCode, String productId, String cartonSeq) {
		return "update CARTON_SEQ SET CARTONCODE = '"+cartonCode+"', STATUS="+CartonSeqStatus.LOCKED.getValue()+" where productId = '"+productId+"' and seq = '"+cartonSeq+"' and STATUS="+CartonSeqStatus.NOT_USED.getValue();
	}

	/**
	 * 所选范围的数量是否足够
	 * @param startSeq
	 * @param endSeq
	 * @param boxnum
	 * @return
	 */
	public boolean isSeqCountEnough(long[] startSeq, long[] endSeq,
			Integer boxnum) {
		int selectQty = 0;
		for(int i=0; i<startSeq.length;i++) {
			selectQty+=(endSeq[i]-startSeq[i]+1);
		}
		return boxnum == selectQty;
	}

	/**
	 * 更新序号时获取不可用的序号
	 * @param productId
	 * @param newSeqSet
	 * @param newSeqs
	 * @param oldCartonCodes
	 * @return
	 */
	public Set<String> getNotAvailableSeq(String productId, Set<String> newSeqSet,
			String newSeqs, String oldCartonCodes) {
		AppCartonSeq appCartonSeq = new AppCartonSeq();
		List<String> availableSeq = appCartonSeq.getAvailableSeqList(productId, newSeqs, oldCartonCodes);
		newSeqSet.removeAll(availableSeq);
		return newSeqSet; 
	}

	public int updateSelectedSeq(String productId, List<String[]> newSeqListToUpdate, String cartonCodesString) throws Exception {
		appCartonSeq.releaseCartonSeq(cartonCodesString);
		List<String> sqlList = new ArrayList<String>();
		for(String[] seqAndCarton : newSeqListToUpdate) {
			sqlList.add(getUpdCartonSeqSql(seqAndCarton[1],productId,seqAndCarton[0]));
		}
		return appCartonSeq.executeBatchWithResult(sqlList);
	}

	public boolean isSeqInAvailableRange(long[] startSeq, long[] endSeq,
			String availableRange) {
		List<Range> aRangeList = new ArrayList<Range>();
		String aRanges[] = availableRange.split(",");
		for(String aRange : aRanges) {
			aRangeList.add(new Range(Long.valueOf(aRange.split("~")[0]), Long.valueOf(aRange.split("~")[1])));
		}
		for(int i=0;i<startSeq.length;i++) {
			boolean isInAvailableRange = false;
			for(Range range : aRangeList) {
				if(range.getStart() <= startSeq[i] && 
						range.getEnd() >= endSeq[i]) {
					isInAvailableRange = true;
					break;
				}
			}
			if(isInAvailableRange) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	private class Range {
		private long start;
		private long end;
		public Range(long start, long end) {
			super();
			this.start = start;
			this.end = end;
		}
		public long getStart() {
			return start;
		}
		public long getEnd() {
			return end;
		}
		
	}

	public String validateRange(long[] startSeq, long[] endSeq, String range) {
		if(StringUtil.isEmpty(range)) {
			return "msg.tolling.cartonseqactivate.2";
		}
		if(!isSeqRangeLegal(startSeq, endSeq)) {
			return "msg.tolling.cartonseqactivate.3"; 
		}
		
		if(!isSeqRangeIsolated(startSeq, endSeq)) {
			return "msg.tolling.cartonseqactivate.4";
		}
		
		if(!isSeqInAvailableRange(startSeq, endSeq, range)) {
			return "msg.tolling.cartonseqactivate.5";
		}
		return null;
	}
	
	public String validateRange(long[] startSeq, long[] endSeq, String range, int cartonQty) {
		String resultMsg = validateRange(startSeq, endSeq, range);
		if(!StringUtil.isEmpty(resultMsg)) {
			return resultMsg;
		}
		if(!isSeqCountEnough(startSeq, endSeq, cartonQty)) {
			return "msg.tolling.cartonseqactivate.6";
		}
		return null;
	}

	public boolean activateCartonSeq(String productId, long[] startSeq,
			long[] endSeq, Integer logId, long[] startPSeq, long[] endPSeq, double quantity) throws Exception {
		long totalCartonSeqQuantity = 0;
		long totalPrimaryCodeQuantity = 0;
		List<String> cartonSeqSqlList = new ArrayList<String>();
		List<String> primaryCodeSqlList = new ArrayList<String>();
		
		List<String> cartonSeqPartialSqlList = new ArrayList<String>();
		List<String> primaryCodePartialSqlList = new ArrayList<String>();
		List<String> updCartonSeqStatusSqlList = new ArrayList<String>();
		for(int i=0;i<startSeq.length;i++) {
			totalCartonSeqQuantity += endSeq[i] - startSeq[i] + 1;
			totalPrimaryCodeQuantity += (endSeq[i] - startSeq[i] + 1) * (endPSeq[i] - startPSeq[i] + 1);
			String startSeqStr = CartonSeq.getSeqWithPrefix(String.valueOf(startSeq[i]));
			String endSeqStr = CartonSeq.getSeqWithPrefix(String.valueOf(endSeq[i]));
			if(endPSeq[i] - startPSeq[i] + 1 == quantity) {
				cartonSeqSqlList.add(getUpdCartonSeqToUsedSql(productId,startSeqStr, endSeqStr));
				primaryCodeSqlList.add(getUpdPrimaryCodeToUsedSql(productId,startSeqStr, endSeqStr, logId));
			} else {
				String startPSeqStr = CartonSeq.getPrimaryCodeSeqWithPrefix(String.valueOf(startPSeq[i]));
				String endPSeqStr = CartonSeq.getPrimaryCodeSeqWithPrefix(String.valueOf(endPSeq[i]));
				cartonSeqPartialSqlList.add(getUpdCartonSeqToActivatingSql(productId,startSeqStr, endSeqStr));
				primaryCodePartialSqlList.add(getUpdPrimaryCodeToUsedSql(productId,startSeqStr, endSeqStr, logId, startPSeqStr, endPSeqStr));
				updCartonSeqStatusSqlList.add(getUpdCartonSeqFromActivatingToUsedSql(productId,startSeqStr, endSeqStr));
			}
			
			/*for(long seq=startSeq[i];seq<=endSeq[i];seq++) {
				seqList.add(CartonSeq.getSeqWithPrefix(String.valueOf(seq)));
			}*/
		}
		/*for(String seq : seqList) {
			cartonSeqSqlList.add(getUpdCartonSeqSql(productId,seq));
			primaryCodeSqlList.add(getUpdPrimaryCodeSql(productId,seq));
		}*/
		totalCartonSeqQuantity -= appCartonSeq.executeBatchWithResult(cartonSeqSqlList);
		totalPrimaryCodeQuantity -= appCartonSeq.executeBatchWithResult(primaryCodeSqlList);
		totalCartonSeqQuantity -= appCartonSeq.executeBatchWithResult(cartonSeqPartialSqlList);
		totalPrimaryCodeQuantity -= appCartonSeq.executeBatchWithResult(primaryCodePartialSqlList);
		appCartonSeq.executeBatchWithResult(updCartonSeqStatusSqlList);
		return totalCartonSeqQuantity == 0 && totalPrimaryCodeQuantity == 0;
	}

	private String getUpdCartonSeqFromActivatingToUsedSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ cs set status = "+CartonSeqStatus.USED.getValue()+" where not EXISTS (" +
				"select PRIMARY_CODE from PRIMARY_CODE where CARTON_CODE = cs.productid||cs.seq and IS_USED = 0) " +
				"and status = "+CartonSeqStatus.ACTIVATING.getValue()+" and productid='"+productId+"' and seq >='"+startSeqStr+"' and seq <='"+endSeqStr+"'";
	}

	private String getUpdPrimaryCodeToUsedSql(String productId,
			String startSeqStr, String endSeqStr, Integer logId,
			String startPSeqStr, String endPSeqStr) {
		return "update PRIMARY_CODE set IS_USED = "+YesOrNo.YES.getValue()+", UPLOAD_PR_ID="+logId+" where CARTON_CODE >='"+productId+startSeqStr+"' and CARTON_CODE <= '"+productId+endSeqStr+"' and substr(PRIMARY_CODE, 22, 3) >='"+startPSeqStr+"' and substr(PRIMARY_CODE, 22, 3) <='"+endPSeqStr+"' and IS_USED = "+YesOrNo.NO.getValue();
	}

	private String getUpdCartonSeqToActivatingSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ SET STATUS="+CartonSeqStatus.ACTIVATING.getValue()+" where productId = '"+productId+"' and seq >= '"+startSeqStr+"' and seq <= '"+endSeqStr+"' and STATUS in ("+CartonSeqStatus.NOT_USED.getValue()+","+CartonSeqStatus.ACTIVATING.getValue()+")";
	}

	private String getUpdPrimaryCodeToUsedSql(String productId,
			String startSeqStr, String endSeqStr, Integer logId) { 
		return "update PRIMARY_CODE set IS_USED = "+YesOrNo.YES.getValue()+", UPLOAD_PR_ID="+logId+" where CARTON_CODE >='"+productId+startSeqStr+"' and CARTON_CODE <= '"+productId+endSeqStr+"' and IS_USED = "+YesOrNo.NO.getValue();
	}

	private String getUpdCartonSeqToUsedSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ SET STATUS="+CartonSeqStatus.USED.getValue()+" where productId = '"+productId+"' and seq >= '"+startSeqStr+"' and seq <= '"+endSeqStr+"' and STATUS="+CartonSeqStatus.NOT_USED.getValue();
	}

	private String getUpdPrimaryCodeSql(String productId, String seq) {
		return "update PRIMARY_CODE set IS_USED = "+YesOrNo.YES.getValue()+" where CARTON_CODE='"+productId+seq+"' and IS_USED = "+YesOrNo.NO.getValue();
	}

	private String getUpdCartonSeqSql(String productId, String cartonSeq) {
		return "update CARTON_SEQ SET STATUS="+CartonSeqStatus.USED.getValue()+" where productId = '"+productId+"' and seq = '"+cartonSeq+"' and STATUS="+CartonSeqStatus.NOT_USED.getValue();
	}

	public CartonSeqLog addCartonSeqLog(String organId, String productId, String range,
			Integer userId, String batch, String productionDate, String packingDate, String inspectionDate, String inspectionInstitution) throws Exception {
		CartonSeqLog csl = new CartonSeqLog();
		csl.setMakeDate(DateUtil.getCurrentDate());
		csl.setMakeId(userId);
		csl.setOrganId(organId);
		csl.setProductId(productId);
		csl.setRange(range);
		csl.setBatch(batch);
		csl.setInspectionInstitution(inspectionInstitution);
		if(!StringUtil.isEmpty(productionDate)) {
			csl.setProductionDate(DateUtil.formatStrDate(productionDate));
		}
		if(!StringUtil.isEmpty(packingDate)) {
			csl.setPackingDate(DateUtil.formatStrDate(packingDate));
		}
		if(!StringUtil.isEmpty(inspectionDate)) {
			csl.setInspectionDate(DateUtil.formatStrDate(inspectionDate));
		}
		appCartonSeq.addCartonSeqLog(csl);
		return csl;
	}

	public void releaseCartonSeqByPlanId(long planId) throws Exception {
		appCartonSeq.releaseCartonSeqByPlanId(planId);
		appCartonSeq.releaseReplacedCodeByPlanId(planId);
	}
	

	public void closeCartonSeq(long planId, Integer printJobId) throws Exception {
		appCartonSeq.updPrimaryCodeCartonCode(planId, printJobId);
		appCartonSeq.closeCartonSeq(planId);
		appCartonSeq.updReplaceCode(planId, printJobId);
		appCartonSeq.delReplaceCode(planId);
	}
	
	public void closeCartonSeq(Integer printJobId) throws Exception {
		appCartonSeq.updPrimaryCodeCartonCode(printJobId);
		appCartonSeq.closeCartonSeq(printJobId);
	}

	public int addReplaceCodes(List<String> addReplaceSqlList) throws Exception {
		return appCartonSeq.executeBatchWithResult(addReplaceSqlList);
	}

	public int updCartonSeq(String sql) throws Exception {
		return appCartonSeq.executeUpdateWithResult(sql);
	}

	public String validatePrimaryCodeRange(long[] startSeq, long[] endSeq,
			double quantity) throws Exception {
		if(!isSeqRangeLegal(startSeq, endSeq)) {
			return "msg.tolling.cartonseqactivate.3"; 
		}
		if(!isPrimaryCodeSeqRangeLegal(startSeq, endSeq, quantity)) {
			return "msg.tolling.cartonseqactivate.7"; 
		}
		return null;
	}

	private boolean isPrimaryCodeSeqRangeLegal(long[] startSeq, long[] endSeq, double quantity) {
		for(int i=0; i<startSeq.length;i++) {
			if(endSeq[i] > quantity) {
				return false;
			}
		}
		return true;
	}

	public String getNotFullyActivatedCartSeq(
			String productId) throws Exception {
		List<Map<String, String>> list = appCartonSeq.getNotFullyActivatedCartSeq(productId);
		if(list.size() >0) {
			StringBuffer seqs = new StringBuffer();
			for(Map<String, String> map : list) {
				seqs.append(", "+map.get("cartonseq")+"("+map.get("startpseq")+"~"+map.get("endpseq")+")");
			}
			return seqs.substring(2);
		}
		return "";
	}
	
	
}
