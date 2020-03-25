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
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectAlterMoveApplyAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String findCondition = " ama.isblankout=0 and ama.isratify=1 and ama.istrans=0 and ama.outorganid='"+users.getMakeorganid()+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "AlterMoveApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,	" MoveDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppAlterMoveApply app = new AppAlterMoveApply();
			List pals = app.getAlterMoveApply(request,pagesize, whereSql);
			ArrayList alpa = new ArrayList();

			for (int i = 0; i < pals.size(); i++) {
				AlterMoveApplyForm pbf = new AlterMoveApplyForm();
				AlterMoveApply o = (AlterMoveApply) pals.get(i);
				pbf.setId(o.getId());
				pbf.setMovedate(o.getMovedate());
				pbf.setOutorganid(o.getOutorganid());
				pbf.setMakeorganid(o.getMakeorganid());

				pbf.setMakeid(o.getMakeid());

				alpa.add(pbf);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
