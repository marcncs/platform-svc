package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

/**
 * 验证条码数量与订单数量是否一致
 */
public class AjaxQuantityCompleteAllStockAlterMoveAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		//声明所需的DAO
		boolean flag = true;
		AppFUnit af = new AppFUnit();
		AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
		AppStockAlterMoveIdcode apidcode = new AppStockAlterMoveIdcode();
		//最后返回的对象
		JSONObject json = new JSONObject();
		
		String Condition = " where sm.isshipment=1 and sm.isblankout=0 and sm.iscomplete=0  and sm.inwarehouseid in (select wv.warehouseId from  RuleUserWh as wv where wv.activeFlag=1 and wv.userId="+userid+") ";
//		Map map = new HashMap(request.getParameterMap());
//		Map tmpMap = EntityManager.scatterMap(map);
//		String[] tablename = { "StockAlterMove" };
//		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
//		String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"MoveDate");
//		String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
//		whereSql = whereSql +  blur + Condition; 
//		whereSql = DbUtil.getWhereSql(whereSql); 			
		
		AppStockAlterMove asa = new AppStockAlterMove();
		//得到登录用户所有未签收的单据
		List<StockAlterMove> sals = asa.getStockAlterMove(Condition);
		
		for(StockAlterMove sam : sals){
			//得到明细
			List<StockAlterMoveDetail> pils = asamd.getStockAlterMoveDetailBySamID(sam.getId());
			//循环判断明细产品数量是否符合
			for (StockAlterMoveDetail samd : pils){
				//订单数量
				double q1 = af.getQuantity(samd.getProductid(), samd.getUnitid(), samd.getQuantity());
				//扫描数量
				double q2 = apidcode.getQuantitySumBysamidProductid2(samd.getProductid(), sam.getId());
				//订单数量与扫描数量不一致
				if (q1 != q2){
					flag = false;
					break;
				}
			}
			
			if(!flag){
				json.put("msg", sam.getId());
				break;
			}
		}
		
		
		if(!flag){
			json.put("state", "1");
		}else{
			json.put("state", "0");
		}
		//json.put("id", samid);
		response.setContentType("text/html; charset=UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter out = response.getWriter();
	    out.print(json.toString());
		return null;
	}
}
