package com.winsafe.drp.action.equip;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintCarDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String carbrand = request.getParameter("carbrand");
		carbrand = new String(carbrand.getBytes("ISO-8859-1"), "UTF-8");
		super.initdata(request);
		try {
			String Condition = " e.carbrand='"+carbrand+"' and (e.makeid="+userid+" "+this.getOrVisitOrgan("e.makeorganid")+") ";

			String[] tablename = { "Equip" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("EquipDate");
			String blur = getKeyWordCondition("ID", "KeysContent");
			whereSql = whereSql + blur +timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			 

			AppEquip asl = new AppEquip();
			List<Equip> pils = asl.getEquip(whereSql);

			AppUsers au = new AppUsers();
			List<EquipForm> als = new ArrayList<EquipForm>();
			for (int i = 0; i < pils.size(); i++) {
				EquipForm ef = new EquipForm();
				Equip o = (Equip) pils.get(i);
				ef.setId(o.getId());
				ef.setAddr(o.getAddr());
				ef.setCname(o.getCname());
				ef.setClinkman(o.getClinkman());
				ef.setEquipdatename(DateUtil.formatDate(o.getEquipdate()));
				ef.setEquipdate(o.getEquipdate());
				ef.setEratotalsum(o.getEratotalsum());
				ef.setMotormanname(au.getUsersByid(o.getMotorman())
						.getRealname());
				ef.setTel(o.getTel());
				 
				als.add(ef);
			}

			request.setAttribute("als", als);
			DBUserLog.addUserLog(userid, 8,"打印出车情况");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
