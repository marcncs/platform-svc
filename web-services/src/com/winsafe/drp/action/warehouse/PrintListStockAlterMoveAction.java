package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try{

			String Condition = " sm.makeorganid='" + users.getMakeorganid()
					+ "' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppStockAlterMove asa = new AppStockAlterMove();

			List<StockAlterMove> sals = asa.getStockAlterMove(whereSql);
			List<StockAlterMoveForm> als = new ArrayList<StockAlterMoveForm>();
			for (int i = 0; i < sals.size(); i++) {
				StockAlterMoveForm saf = new StockAlterMoveForm();
				StockAlterMove o = (StockAlterMove) sals.get(i);
				saf.setId(o.getId());
//				saf.setMovedate(o.getMovedate());
				saf.setMakeorganidname(o.getMakeorganidname());
				saf.setOutwarehouseid(o.getOutwarehouseid());
				saf.setReceiveorganidname(o.getReceiveorganidname());
				saf.setIsaudit(o.getIsaudit());
				saf.setIscomplete(o.getIscomplete());
				saf.setIsshipment(o.getIsshipment());
				saf.setIsblankout(o.getIsblankout());

				saf.setMakeid(o.getMakeid());

				als.add(saf);
			}

			request.setAttribute("als", als);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>打印订购单!");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
