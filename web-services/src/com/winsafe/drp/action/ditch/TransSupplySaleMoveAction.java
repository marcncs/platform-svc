package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TransSupplySaleMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aaid = request.getParameter("aaid");
		
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{

			AppOrgan ao = new AppOrgan();
			SupplySaleMove sm = new SupplySaleMove();
			String smid = MakeCode.getExcIDByRandomTableName(
					"supply_sale_move", 2, "DM");
			sm.setId(smid);
			AppSupplySaleApply appSsa = new AppSupplySaleApply();
			
			SupplySaleApply ssa = appSsa.getSupplySaleApplyByID(aaid);
			
			sm.setMovedate(ssa.getMovedate());

			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			
			sm.setInvmsg(ssa.getInvmsg());
			sm.setTickettitle(ssa.getTickettitle());
			sm.setPaymentmode(ssa.getPaymentmode());
			sm.setTransportmode(ssa.getTransportmode());
			sm.setTransportaddr(ssa.getTransportaddr());
			sm.setOlinkman(ssa.getOlinkman());
			sm.setSupplyorganid(ssa.getMakeorganid());
			sm.setOtel(ssa.getOtel());
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakeorganidname(ao.getOrganByID(users.getMakeorganid())
					.getOrganname());
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			sm.setInwarehouseid(ssa.getInwarehouseid());

			sm.setMovecause(ssa.getMovecause());
			sm.setRemark(ssa.getRemark());
			sm.setIsaudit(0);
			sm.setAuditid(0);
			sm.setIsshipment(0);
			sm.setShipmentid(0);
			sm.setIstally(0);
			sm.setTallyid(0);
			sm.setIsblankout(0);
			sm.setInorganid(ssa.getInorganid());
			sm.setReceiveorganid(ssa.getInorganid());
			sm.setReceiveorganidname(ao.getOrganByID(ssa.getInorganid())
					.getOrganname());
			sm.setIscomplete(0);
			sm.setReceiveid(0);

			AppSupplySaleMove asm = new AppSupplySaleMove();

			
			String[] strpid = request.getParameterValues("pid");
			String[] strdetaidid = request.getParameterValues("detailid");
			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname = request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strpunitprice = request.getParameterValues("punitprice");
			String[] strsunitprice = request.getParameterValues("sunitprice");
			String[] stroperatorquantity = request
					.getParameterValues("operatorquantity");

			Integer unitid;
			Integer detailid;
			Double operatorquantity, punitprice, sunitprice, totalsum = 0.00, psubsum, ssubsum;
			double stotalsum=0.00;
			String productid, productname, specmode, batch;
			AppSupplySaleApplyDetail aamd = new AppSupplySaleApplyDetail();

			
			List podlist = aamd.getSupplySaleAplyBySSID(aaid);
			SupplySaleApplyDetail pod = null;
			int totalquantity = 0;
			int totalalreadyquantity = 0;
			for (int n = 0; n < podlist.size(); n++) {
				pod = (SupplySaleApplyDetail) podlist.get(n);
				totalquantity += pod.getCanquantity();
				totalalreadyquantity += pod.getAlreadyquantity();
			}

			AppSupplySaleMoveDetail asmd = new AppSupplySaleMoveDetail();

			for (int i = 0; i < strpid.length; i++) {
				int j = Integer.parseInt(strpid[i]);
				detailid = Integer.valueOf(strdetaidid[j]);
				productid = strproductid[j];
				productname = strproductname[j];
				specmode = strspecmode[j];
				unitid = Integer.valueOf(strunitid[j]);

				if (DataValidate.IsDouble(strpunitprice[j])) {
					punitprice = Double.valueOf(strpunitprice[j]);
				} else {
					punitprice = Double.valueOf(0.00);
				}

				if (DataValidate.IsDouble(strsunitprice[j])) {
					sunitprice = Double.valueOf(strsunitprice[j]);
				} else {
					sunitprice = Double.valueOf(0.00);
				}

				if (DataValidate.IsDouble(stroperatorquantity[i])) {
					operatorquantity = Double.valueOf(stroperatorquantity[i]);
				} else {
					operatorquantity = Double.valueOf(0.00);
				}

				if (operatorquantity > 0.00) {

					SupplySaleMoveDetail smd = new SupplySaleMoveDetail();
					smd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"supply_sale_move_detail", 0, "")));
					smd.setSsmid(smid);
					smd.setProductid(productid);
					smd.setProductname(productname);
					smd.setSpecmode(specmode);
					smd.setBatch("");
					smd.setUnitid(unitid);
					smd.setSunitprice(sunitprice);
					smd.setPunitprice(punitprice);
					smd.setQuantity(operatorquantity);
					smd.setTakequantity(0d);
					smd.setSsubsum(sunitprice * operatorquantity);
					smd.setPsubsum(punitprice * operatorquantity);
					totalsum += smd.getPsubsum();
					stotalsum += smd.getSsubsum();
					totalalreadyquantity += operatorquantity;

					asmd.save(smd);
				}

				
				aamd.updAlreadyQuantity(detailid, operatorquantity);

			}

			sm.setTotalsum(totalsum);
			sm.setStotalsum(stotalsum);
			sm.setTakestatus(0);
			sm.setKeyscontent(sm.getId()+","+sm.getMakeorganidname()+","+sm.getOlinkman()+","+sm.getOtel());
			asm.save(sm);

			AppSupplySaleApply apb = new AppSupplySaleApply();

			if (totalquantity == totalalreadyquantity) {
				
				apb.IsTrans(aaid, 1);
			}

			request.setAttribute("result", "databases.input.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>确认代销申请转渠道代销。编号：" + aaid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
