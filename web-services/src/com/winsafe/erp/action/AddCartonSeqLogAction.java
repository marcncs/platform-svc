package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.util.Constants; 
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonSeq;
import com.winsafe.sap.pojo.CartonSeqLog;

public class AddCartonSeqLogAction extends BaseAction {

	private static Logger logger = Logger.getLogger(AddCartonSeqLogAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		CartonSeqServices css = new CartonSeqServices();
		try {
			String organid = request.getParameter("organid");
			String productId = request.getParameter("ProductID");
			String range = request.getParameter("range");
			String batch = request.getParameter("batch");
			
			String productionDate = request.getParameter("productionDate");
			String packingDate = request.getParameter("packingDate");
			String inspectionDate = request.getParameter("inspectionDate");
			String inspectionInstitution = request.getParameter("inspectionInstitution");
			
			long startSeq[] = RequestTool.getLongs(request, "startSeq");
			long endSeq[] = RequestTool.getLongs(request, "endSeq");
			long startPSeq[] = RequestTool.getLongs(request, "startPSeq");
			long endPSeq[] = RequestTool.getLongs(request, "endPSeq");
			
			String resultMsg = css.validateRange(startSeq, endSeq, range);
			if(!StringUtil.isEmpty(resultMsg)) {
				request.setAttribute("result", resultMsg);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppFUnit appFUnit = new AppFUnit();
			double quantity = appFUnit.getXQuantity(productId, Constants.DEFAULT_UNIT_ID);
			
			resultMsg = css.validatePrimaryCodeRange(startPSeq, endPSeq, quantity);
			if(!StringUtil.isEmpty(resultMsg)) {
				request.setAttribute("result", resultMsg);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			//新增日志
			String activeRange = css.getSeqRangeString(startSeq, endSeq);
			CartonSeqLog log = css.addCartonSeqLog(organid, productId, activeRange, userid, batch, productionDate, packingDate, inspectionDate, inspectionInstitution);
			//激活小包装码
			if(!css.activateCartonSeq(productId, startSeq, endSeq, log.getId(), startPSeq, endPSeq, quantity)) {
				HibernateUtil.rollbackTransaction(); 
				request.setAttribute("result", "msg.tolling.cartonseqactivate.1");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(request, "编号："+log.getId());
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}
	
	public static void main(String[] args) throws Exception {
		long startSeq[] = {4,5,200};
		long endSeq[] = {4,199,200};
		long startPSeq[] = {55,1,1};
		long endPSeq[] = {288,288,209};
		double quantity = 288;
		String productId = "014612";
		Integer logid = 12;
		activateCartonSeq(productId, startSeq, endSeq, logid, startPSeq, endPSeq, quantity);
	}
	
	public static boolean activateCartonSeq(String productId, long[] startSeq,
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
//		totalCartonSeqQuantity -= appCartonSeq.executeBatchWithResult(cartonSeqSqlList);
//		totalPrimaryCodeQuantity -= appCartonSeq.executeBatchWithResult(primaryCodeSqlList);
//		totalCartonSeqQuantity -= appCartonSeq.executeBatchWithResult(cartonSeqPartialSqlList);
//		totalPrimaryCodeQuantity -= appCartonSeq.executeBatchWithResult(primaryCodePartialSqlList);
//		appCartonSeq.executeBatchWithResult(updCartonSeqStatusSqlList);
		return totalCartonSeqQuantity == 0 && totalPrimaryCodeQuantity == 0;
	}
	
	private static String getUpdCartonSeqToActivatingSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ SET STATUS="+CartonSeqStatus.ACTIVATING.getValue()+" where productId = '"+productId+"' and seq >= '"+startSeqStr+"' and seq <= '"+endSeqStr+"' and STATUS in ("+CartonSeqStatus.NOT_USED.getValue()+","+CartonSeqStatus.ACTIVATING.getValue()+")";
	}

	private static String getUpdPrimaryCodeToUsedSql(String productId,
			String startSeqStr, String endSeqStr, Integer logId) { 
		return "update PRIMARY_CODE set IS_USED = "+YesOrNo.YES.getValue()+", UPLOAD_PR_ID="+logId+" where CARTON_CODE >='"+productId+startSeqStr+"' and CARTON_CODE <= '"+productId+endSeqStr+"' and IS_USED = "+YesOrNo.NO.getValue();
	}

	private static String getUpdCartonSeqToUsedSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ SET STATUS="+CartonSeqStatus.USED.getValue()+" where productId = '"+productId+"' and seq >= '"+startSeqStr+"' and seq <= '"+endSeqStr+"' and STATUS="+CartonSeqStatus.NOT_USED.getValue();
	}
	
	private static String getUpdCartonSeqFromActivatingToUsedSql(String productId,
			String startSeqStr, String endSeqStr) {
		return "update CARTON_SEQ cs set status = "+CartonSeqStatus.USED.getValue()+" where not EXISTS (" +
				"select PRIMARY_CODE from PRIMARY_CODE where CARTON_CODE = cs.productid||cs.seq and IS_USED = 0) " +
				"and status = "+CartonSeqStatus.ACTIVATING.getValue()+" and productid='"+productId+"' and seq >='"+startSeqStr+"' and seq <='"+endSeqStr+"'";
	}

	private static String getUpdPrimaryCodeToUsedSql(String productId,
			String startSeqStr, String endSeqStr, Integer logId,
			String startPSeqStr, String endPSeqStr) {
		return "update PRIMARY_CODE set IS_USED = "+YesOrNo.YES.getValue()+", UPLOAD_PR_ID="+logId+" where CARTON_CODE >='"+productId+startSeqStr+"' and CARTON_CODE <= '"+productId+endSeqStr+"' and substr(PRIMARY_CODE, 22, 3) >='"+startPSeqStr+"' and substr(PRIMARY_CODE, 22, 3) <='"+endPSeqStr+"' and IS_USED = "+YesOrNo.NO.getValue();
	}


}
