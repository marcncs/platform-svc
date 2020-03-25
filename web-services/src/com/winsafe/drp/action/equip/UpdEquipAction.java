package com.winsafe.drp.action.equip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppEquipDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdEquipAction extends Action {


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    String cid = request.getParameter("cid");

		try {

			if (cid == null || cid.equals("null") || cid.equals("")) {
				request.setAttribute("result", "databases.upd.fail");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String id = request.getParameter("id");
			double totalsum = 0.00;			

			AppEquip aq = new AppEquip();
			Equip so = aq.getEquipByID(id);
			Equip oldso = (Equip)BeanUtils.cloneBean(so);
			so.setObjectsort(RequestTool.getInt(request, "objectsort"));
			so.setCid(cid);
			so.setCname(request.getParameter("cname"));
			so.setEquipdate(RequestTool.getDate(request, "equipdate"));
			so.setTransportmode(RequestTool.getInt(request, "transportmode"));
			so.setTransportnum(request.getParameter("transportnum"));
			so.setAddr(request.getParameter("transportaddr"));
			so.setClinkman(request.getParameter("clinkman"));
			so.setTel(request.getParameter("tel"));
			so.setCarbrand(request.getParameter("carbrand"));
			so.setMotorman(RequestTool.getInt(request, "motorman"));
			so.setPiece(RequestTool.getDouble(request, "piece"));
			//so.setRushsum(RequestTool.getDouble(request, "rushsum"));
			//so.setRushdesc(request.getParameter("rushdesc"));
			so.setTransit(RequestTool.getInt(request, "transit"));
			so.setMakeorganid(users.getMakeorganid());
			so.setMakedeptid(users.getMakedeptid());
			so.setMakeid(userid);
			so.setMakedate(DateUtil.getCurrentDate());
			

			AppShipmentBill aps = new AppShipmentBill();
			String strsbid[] = request.getParameterValues("sbid");
			int paymentmode[] = RequestTool.getInts(request, "paymentmode");
			int invmsg[] = RequestTool.getInts(request, "invmsg");
			double billsum[] = RequestTool.getDoubles(request, "billsum");

			
			AppEquipDetail asld = new AppEquipDetail();
			asld.delEquipDetailByEID(id);
			for (int i = 0; i < strsbid.length; i++) {				
				EquipDetail sod = new EquipDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"equip_detail", 0, "")));
				sod.setEid(id);
				sod.setSbid(strsbid[i]);
				sod.setPaymentmode(paymentmode[i]);
				sod.setInvmsg(invmsg[i]);
				sod.setBillsum(billsum[i]);
				sod.setErasum(0.00);
				asld.addEquipDetail(sod);
				totalsum += sod.getErasum();
				aps.updIsTrans(1, strsbid[i]);
			}
			so.setKeyscontent("");
			aq.updEquip(so);


			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 8, "修改配送单,编号："+id, oldso, so);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
