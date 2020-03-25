package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TransStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aaid = request.getParameter("aaid");

		super.initdata(request);
		try{
			
			AppOrgan ao = new AppOrgan();
			StockAlterMove sm = new StockAlterMove();
			String smid = MakeCode.getExcIDByRandomTableName(
					"stock_alter_move", 2, "OM");
			sm.setId(smid);
			sm.setMovedate(DateUtil.StringToDate(request
					.getParameter("movedate")));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setInvmsg(Integer.valueOf(request.getParameter("invmsg")));
			sm.setTickettitle(request.getParameter("tickettitle"));
			sm.setPaymentmode(Integer.valueOf(request
					.getParameter("paymentmode")));
			sm.setTransportmode(Integer.valueOf(request
					.getParameter("transportmode")));
			sm.setOlinkman(request.getParameter("olinkman"));
			sm.setOtel(request.getParameter("otel"));
			sm.setTransportaddr(request.getParameter("transportaddr"));
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakeorganidname(ao.getOrganByID(users.getMakeorganid())
					.getOrganname());
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			sm.setIsaudit(0);
			sm.setIsshipment(0);
			sm.setIstally(0);
			sm.setIsblankout(0);
			sm.setReceiveorganid(request.getParameter("receiveorganid"));
			sm.setReceiveorganidname(ao.getOrganByID(sm.getReceiveorganid())
					.getOrganname());
			sm.setIscomplete(0);
			sm.setReceiveid(0);
			sm.setReceivedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
			.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
			sm.setKeyscontent(keyscontent.toString());
			AppStockAlterMove asm = new AppStockAlterMove();

			
			String[] strpid = request.getParameterValues("pid");
			String[] strdetaidid = request.getParameterValues("detailid");
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] stroperatorquantity = request
					.getParameterValues("operatorquantity");

			Integer unitid;
			Long detailid;
			Double operatorquantity, unitprice, totalsum = 0.00;
			String productid, productname, specmode;
			AppAlterMoveApplyDetail aamd = new AppAlterMoveApplyDetail();

			
			List podlist = aamd.getAlterMoveApplyDetailByAmID(aaid);
			AlterMoveApplyDetail pod = null;
			int totalquantity = 0;
			int totalalreadyquantity = 0;
			for (int n = 0; n < podlist.size(); n++) {
				pod = (AlterMoveApplyDetail) podlist.get(n);
				totalquantity += pod.getCanquantity();
				totalalreadyquantity += pod.getAlreadyquantity();
			}

			AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();

			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				detailid = Long.valueOf(strdetaidid[j]);
				productid = strproductid[j];
				productname = strproductname[j];
				specmode = strspecmode[j];
				unitid = Integer.valueOf(strunitid[j]);
				if (DataValidate.IsDouble(strunitprice[j])) {
					unitprice = Double.valueOf(strunitprice[j]);
				} else {
					unitprice = Double.valueOf(0.00);
				}

				if (DataValidate.IsDouble(stroperatorquantity[i])) {
					operatorquantity = Double.valueOf(stroperatorquantity[i]);
				} else {
					operatorquantity = Double.valueOf(0.00);
				}


				if(operatorquantity>0.00){
				
				StockAlterMoveDetail smd = new StockAlterMoveDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_alter_move_detail", 0, "")));
				smd.setSamid(smid);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				smd.setBatch("");
				smd.setUnitid(unitid);
				smd.setUnitprice(unitprice);
				smd.setQuantity(operatorquantity);
				smd.setTakequantity(0d);
				smd.setSubsum(unitprice * operatorquantity);
				totalsum += smd.getSubsum();
				totalalreadyquantity += operatorquantity;
				asmd.addStockAlterMoveDetail(smd);
				}

				
				aamd.updAlreadyQuantity(detailid, operatorquantity);

			}

			sm.setTotalsum(totalsum);
			sm.setTakestatus(0);
			asm.addStockAlterMove(sm);

			AppAlterMoveApply apb = new AppAlterMoveApply();

			if (totalquantity == totalalreadyquantity) {
				
				apb.updIsTrans(aaid, 1);
			}

			request.setAttribute("result", "databases.input.success");
			DBUserLog.addUserLog(userid,4, "渠道管理>>确认订购申请转订购单。编号：" + aaid);

			return mapping.findForward("input");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
