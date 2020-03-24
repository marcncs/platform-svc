package com.winsafe.drp.action.equip;

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
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class CarDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String carbrand = request.getParameter("brand");
		carbrand = new String(carbrand.getBytes("ISO-8859-1"), "utf-8");
		super.initdata(request);
		int pagesize = 10;
		try {
			String Condition = " e.carbrand='"+carbrand+"' and (e.makeid="+userid+" "+this.getOrVisitOrgan("e.makeorganid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Equip" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" EquipDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "KeysContent");
			whereSql = whereSql + blur +timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			 

			AppEquip asl = new AppEquip();
			List pils = asl.getEquip(request, pagesize, whereSql);

			AppUsers au = new AppUsers();
			List als = new ArrayList();
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
			request.setAttribute("carbrand", carbrand);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
