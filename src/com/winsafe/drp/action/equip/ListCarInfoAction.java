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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListCarInfoAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);

		try {

			String Condition = "   ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleIndent" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" EquipDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			 
			Object obj[] = DbUtil.setDynamicPager(request, "Equip as e ",
					whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppCustomer ac = new AppCustomer();
			AppEquip asl = new AppEquip();
			List pils = asl.getEquip(request,pagesize, whereSql);
			
			ArrayList also = new ArrayList();
			Equip o = null;
			for (int i = 0; i < pils.size(); i++) {
				EquipForm sof = new EquipForm();
				o = (Equip) pils.get(i);
				sof.setId(o.getId());
				 
				sof.setCid(o.getCid());
				sof.setCname(ac.getCustomer(o.getCid()).getCname());
				sof.setAddr(o.getAddr());
				sof.setCarbrand(o.getCarbrand());
				sof.setClinkman(o.getClinkman());
				sof.setEquipdatename(DateUtil.formatDate(o.getEquipdate()));
				sof.setEratotalsum(o.getEratotalsum());
				sof.setMotorman(o.getMotorman());
				sof.setMotormanname(au.getUsersByid(o.getMotorman())
						.getRealname());
				sof.setPiece(o.getPiece());
				sof.setTel(o.getTel());
				sof.setTransportmodename(Internation.getStringByKeyPosition(
						"Transportmode", request, o.getTransportmode(),
						"global.sys.SystemResource"));
				 
				also.add(sof);
			}

			 
		 
			request.setAttribute("also", also);
			 

			//DBUserLog.addUserLog(userid, "列表配送单");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
