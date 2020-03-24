package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppEquipDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddEquipAction extends BaseAction {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();
    String cid = request.getParameter("cid");
    String bids =request.getParameter("bids");


		super.initdata(request);try{

			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.add.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String cname = request.getParameter("cname");
			String clinkman = request.getParameter("clinkman");

			Integer transportmode = Integer.valueOf(request
					.getParameter("transportmode"));
			Integer transit = Integer.valueOf(request.getParameter("transit"));
			String addr = request.getParameter("transportaddr");
			String tel = request.getParameter("tel");
			String carbrand = request.getParameter("carbrand");
			String piece = request.getParameter("piece");
			String equipdate = request.getParameter("equipdate");

			String transportnum = request.getParameter("transportnum");
			String rushsum = request.getParameter("rushsum");
			String rushdesc = request.getParameter("rushdesc");
			double totalsum = 0.00;

			
			String strsbid[] = request.getParameterValues("sbid");
			String strerasum[] = request.getParameterValues("erasum");
			String strpaymentmode[] = request.getParameterValues("paymentmode");
			String strinvmsg[] = request.getParameterValues("invmsg");
			String strbillsum[] = request.getParameterValues("billsum");

			Double erasum=0.00;

			Equip so = new Equip();
			String soid = MakeCode.getExcIDByRandomTableName("equip", 2, "");
			so.setObjectsort(RequestTool.getInt(request, "objectsort"));
			so.setId(soid);
			so.setCid(cid);
			so.setCname(cname);
			so.setEquipdate(DateUtil.StringToDate(equipdate));
			so.setTransportmode(transportmode);
			so.setTransportnum(transportnum);
			so.setAddr(addr);
			so.setClinkman(clinkman);
			so.setTel(tel);
			so.setCarbrand(carbrand);
			so.setMotorman(RequestTool.getInt(request, "motorman"));
			so.setPiece(DataValidate.IsDouble(piece) ? Double.valueOf(piece)
					: Double.valueOf(0.00));
			so.setRushsum(DataValidate.IsDouble(rushsum) ? Double
					.valueOf(rushsum) : Double.valueOf(0.00));
			so.setRushdesc(rushdesc);
			so.setTransit(transit);
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setMakeid(userid);
			so.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			
			StringBuffer keyscontent = new StringBuffer();
			AppCustomer ac = new AppCustomer();
			Customer customer = ac.getCustomer(so.getCid());
		      keyscontent.append(so.getId()).append(",").append(so.getCname()).append(",")
		      .append(customer!=null?customer.getMobile():"").append(",").append(so.getClinkman()).append(",")
		      .append(so.getTel()).append(",");

			AppEquip aq = new AppEquip();
			AppEquipDetail asld = new AppEquipDetail();
			for (int i = 0; i < strsbid.length; i++) {

//				if (DataValidate.IsDouble(strerasum[i])) {
//					erasum = Double.valueOf(strerasum[i]);
//				} else {
//					erasum = Double.valueOf(0.00);
//				}

				totalsum += erasum;
				EquipDetail sod = new EquipDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"equip_detail", 0, "")));
				sod.setEid(soid);
				sod.setSbid(strsbid[i]);
				sod.setPaymentmode(Integer.valueOf(strpaymentmode[i]));
				sod.setInvmsg(Integer.valueOf(strinvmsg[i]));
				sod.setBillsum(DataValidate.IsDouble(strbillsum[i]) ? Double
						.valueOf(strbillsum[i]) : 0d);
				sod.setErasum(erasum);
				asld.addEquipDetail(sod);
				keyscontent.append(sod.getSbid()).append(",");
			}
			so.setEratotalsum(totalsum - so.getRushsum());
			so.setKeyscontent(keyscontent.toString());
			aq.addEquip(so);
			AppShipmentBill apsb = new AppShipmentBill();
			apsb.updIsTransByBids(bids);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 8, "新增配送单,编号:"+soid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
