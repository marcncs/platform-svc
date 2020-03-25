package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.drp.util.ResponseUtil;

/*
 * 用来ajax方法来查询条码数量
 */
public class AjaxCheckTakeTicketIdcodeAction extends BaseAction{
	private static Logger logger = Logger.getLogger(AjaxCheckTakeTicketIdcodeAction.class);
	
	private AppTakeTicketDetail appTtd = new AppTakeTicketDetail();
	private AppTakeTicketIdcode appTti = new AppTakeTicketIdcode();
	private AppFUnit appFunit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer errorMsg = new StringBuffer();
		// 初始化
		initdata(request);
		// 单据编号
		String ttid = request.getParameter("ttid");
		//更新确认数量
		String[] pids = request.getParameterValues("pid");
		String[] quantitys = request.getParameterValues("realQuantity");
		// 以json数据返回
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnCode", "0");
		jsonObject.put("returnMsg", "");
		try{
			//查询明细
			List<TakeTicketDetail>  ttdList = appTtd.getDetailByReadOnly(ttid);
			updRealQuantity(ttdList, pids, quantitys);
			//查询条码的数量(最小单位)
			Map<String, Double>  ttidcodeMap = appTti.getTtidcodePackQuantity(ttid);
			//比较数量是否一致
			for(TakeTicketDetail ttd : ttdList){
				String pid = ttd.getProductid();
				String pName = ttd.getProductname();
				// 详情数量(最小单位)
				Double quantity = appFunit.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getRealQuantity());
				// 条码数量(最小单位)
				Double idcodeQuantity = ttidcodeMap.get(pid);
				if(quantity == null || idcodeQuantity == null || !quantity.equals(idcodeQuantity)){
					errorMsg.append("产品[ " + pid + " " + pName +" ]确认数量与条码数量不一致。  \r\n");
				}
			}
			// 返回错误信息
			if(errorMsg.length() > 0){
				jsonObject = new JSONObject(); 
				jsonObject.put("returnCode", "-1");
				jsonObject.put("returnMsg", errorMsg.toString());
			}
		}catch (Exception e) {
			logger.error("", e);
		}finally{
			ResponseUtil.write(response, jsonObject.toString());
		}
		
		return null;
	}
	
	/**
	 * 更新确认数量(但不同步到数据库)
	 */
	private void updRealQuantity(List<TakeTicketDetail> ttds,String[] pids ,String[] quantitys) throws Exception{
		int length = pids.length;
		for(int i=0 ; i<length ; i++ ){
			String pid = pids[i];
			Double realQuantity = NumberUtil.getDouble(quantitys[i]);
			for(TakeTicketDetail ttd : ttds){
				if(ttd.getProductid().equals(pid)){
					ttd.setRealQuantity(realQuantity);
					break;
				}
			}
		}
	}

}
