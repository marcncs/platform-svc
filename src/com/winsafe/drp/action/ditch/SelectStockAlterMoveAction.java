package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.BillNoFrom;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-27 下午04:19:49 www.winsafe.cn
 */
public class SelectStockAlterMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize = 10;
		try {
			String organid = request.getParameter("organid");
			String inorout = request.getParameter("inorout");
			String billnotype = request.getParameter("billnotype");
			String Condition = "";
			// 收入
			if (inorout.equals("0")) {
				if (billnotype.equals("0")) {
					Condition = " sm.makeorganid='" + users.getMakeorganid()
							+ "' and sm.receiveorganid = '" + organid
							+ "'  and sm.ismaketicket = 0"; //and sm.isshipment=1 
				} else {
					Condition = "(makeorganid='"
							+ users.getMakeorganid()
							+ "'  and supplyorganid='"
							+ organid
							+ "' and ismaketicket=0 )" // and isshipment=1
							+ " or (supplyorganid='"
							+  users.getMakeorganid()
							+ "' and inorganid='"
							+ organid
							+ "' and  ismaketicket = 1 and isreceiveticket=1 ) ";

				}

			} else {
				if (billnotype.equals("0")) {
					Condition = " sm.receiveorganid='" + users.getMakeorganid()
							+ "' and sm.makeorganid='" + organid
							+ "' and sm.iscomplete=1 and sm.ismaketicket = 1 "
							+ "and sm.isreceiveticket=0";
				} else {

					Condition = "(supplyorganid='" + users.getMakeorganid()
							+ "'  and makeorganid='" + organid
							+ "' and ismaketicket = 1 and isreceiveticket=0 ) or "
							+ "(supplyorganid='" + organid
							+ "'  and inorganid='" + users.getMakeorganid()
							+ "' and iscomplete=1 and isreceiveticket=2 )";
				}

			}

			List list = null;
			if (billnotype.equals("0")) {
				list = getStockAlterMove(request, pagesize, Condition);
			} else {
				list = getSupplySaleMove(request, pagesize, Condition);
			}

			request.setAttribute("list", list);
			return mapping.findForward("select");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private List getStockAlterMove(HttpServletRequest request, int pagesize,
			String Condition) throws Exception {
		AppStockAlterMove asa = new AppStockAlterMove();
		String[] tablename = { "StockAlterMove" };
		String whereSql = getWhereSql2(tablename);
		String timeCondition = getTimeCondition("MakeDate");
		String blur = getKeyWordCondition("ID");
		whereSql = whereSql + timeCondition + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<StockAlterMove> list = asa.getStockAlterMove(request, pagesize,
				whereSql);

		List<BillNoFrom> billlist = new ArrayList<BillNoFrom>();

		for (StockAlterMove sam : list) {
			BillNoFrom bFrom = new BillNoFrom();
			bFrom.setId(sam.getId());
			bFrom.setMakedate(sam.getMakedate());
			bFrom.setTotalsum(sam.getTotalsum());
			bFrom.setType(1);
			billlist.add(bFrom);
		}

		return billlist;

	}

	private List getSupplySaleMove(HttpServletRequest request, int pagesize,
			String Condition) throws Exception {
		AppSupplySaleMove appSSM = new AppSupplySaleMove();
		String[] tablename = { "SupplySaleMove" };
		String whereSql = getWhereSql2(tablename);
		String timeCondition = getTimeCondition("MakeDate");
		String blur = getKeyWordCondition("ID");
		whereSql = whereSql + timeCondition + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<SupplySaleMove> list = appSSM.getSupplySaleMoveAll(request,
				pagesize, whereSql);
		List<BillNoFrom> billlist = new ArrayList<BillNoFrom>();

		for (SupplySaleMove sam : list) {
			BillNoFrom bFrom = new BillNoFrom();
			bFrom.setId(sam.getId());
			bFrom.setMakedate(sam.getMakedate());
			bFrom.setTotalsum(sam.getTotalsum());
			if(sam.getMakeorganid().equals(users.getMakeorganid())){
				bFrom.setType(2);
			}else{
				if(sam.getInorganid().equals(users.getMakeorganid())){
					bFrom.setType(4);
				}else{
					bFrom.setType(3);
					
				}
			}

			billlist.add(bFrom);
		}

		return billlist;

	}

}
