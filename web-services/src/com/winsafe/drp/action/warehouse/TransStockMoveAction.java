package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TransStockMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aaid = request.getParameter("aaid");

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			AppMoveApply appMa = new AppMoveApply();
			MoveApply ma = appMa.getMoveApplyByID(aaid);
			StockMove sm = new StockMove();
			String smid = MakeCode.getExcIDByRandomTableName(
					"stock_move", 2, "SM");
			sm.setId(smid);
			sm.setMovedate(ma.getMovedate());
			sm.setOutwarehouseid(request
					.getParameter("outwarehouseid"));
			sm.setInorganid(ma.getMakeorganid());
			sm.setInwarehouseid(ma.getInwarehouseid());
			sm.setTransportmode(ma.getTransportmode());
			sm.setTransportaddr(ma.getTransportaddr());
			
			sm.setOlinkman(ma.getOlinkman());
			sm.setOtel(ma.getOtel());
			sm.setMakeorganid(users.getMakeorganid());
			
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			sm.setMovecause(ma.getMovecause());
			sm.setRemark(ma.getRemark());
			sm.setIsaudit(0);
			sm.setAuditid(Integer.valueOf(0));
			sm.setIsshipment(0);
			sm.setShipmentid(Integer.valueOf(0));
			sm.setIsblankout(0);
			

			sm.setIscomplete(0);

			sm.setReceivedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));

			AppStockMove asm = new AppStockMove();

			
			String[] strpid = request.getParameterValues("pid");
			String[] strdetaidid = request.getParameterValues("detailid");
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] stroperatorquantity = request
					.getParameterValues("operatorquantity");

			Integer unitid;
			Integer detailid;
			Double operatorquantity, unitprice, totalsum = 0.00, subsum;
			String productid, productname, specmode, batch;
			AppMoveApplyDetail aamd = new AppMoveApplyDetail();

			
			List podlist = aamd.getMoveApplyDetailByAmID(aaid);
			MoveApplyDetail pod = null;
			int totalquantity = 0;
			int totalalreadyquantity = 0;
			for (int n = 0; n < podlist.size(); n++) {
				pod = (MoveApplyDetail) podlist.get(n);
				totalquantity += pod.getCanquantity();
				totalalreadyquantity += pod.getAlreadyquantity();
			}

			AppStockMoveDetail asmd = new AppStockMoveDetail();

			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				detailid = Integer.valueOf(strdetaidid[j]);
				productid = strproductid[j];
				productname = strproductname[j];
				specmode = strspecmode[j];
				unitid = Integer.valueOf(strunitid[j]);
			

				if (DataValidate.IsDouble(stroperatorquantity[i])) {
					operatorquantity = Double.valueOf(stroperatorquantity[i]);
				} else {
					operatorquantity = Double.valueOf(0.00);
				}


				if(operatorquantity>0.00){
				
				StockMoveDetail smd = new StockMoveDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_move_detail", 0, "")));
				smd.setSmid(smid);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				smd.setBatch("");
				smd.setUnitid(unitid);
				smd.setQuantity(operatorquantity);
				smd.setTakequantity(0d);
				smd.setCost(0d);
				totalalreadyquantity += operatorquantity;

				asmd.addStockMoveDetail(smd);
				}

				
				aamd.updAlreadyQuantity(detailid, operatorquantity);

			}

			sm.setTotalsum(totalsum);
			sm.setTakestatus(0);
			asm.addStockMove(sm);

			AppMoveApply apb = new AppMoveApply();

			if (totalquantity == totalalreadyquantity) {
				
				apb.updIsTrans(aaid, 1);
			}

			request.setAttribute("result", "databases.input.success");
			//DBUserLog.addUserLog(userid, "确认转仓申请转转仓单。编号：" + aaid);

			return mapping.findForward("input");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
