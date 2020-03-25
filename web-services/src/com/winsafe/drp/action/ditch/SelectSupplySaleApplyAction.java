package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectSupplySaleApplyAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String findCondition = " ssa.isratify=1 and ssa.istrans=0 and ssa.outorganid='"+users.getMakeorganid()+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SupplySaleApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,	" MoveDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppSupplySaleApply app = new AppSupplySaleApply();
			List<SupplySaleApply> pals = app.getSupplySaleApplyAll(request,pagesize, whereSql);
			List<SupplySaleApplyForm> list = new ArrayList<SupplySaleApplyForm>();
			for (SupplySaleApply ssa : pals) {
				SupplySaleApplyForm ssaf = new SupplySaleApplyForm();
				ssaf.setId(ssa.getId());
				ssaf.setMovedate(ssa.getMovedate());
				ssaf.setOutorganid(ssa.getOutorganid());
				ssaf.setInorganid(ssa.getInorganid());
				ssaf.setMakeid(ssa.getMakeid());
				list.add(ssaf);
			}

			request.setAttribute("list", list);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
