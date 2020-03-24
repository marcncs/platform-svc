package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TaskForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ReceiptTaskAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppUsers au = new AppUsers();

		try {
			String condition = "t.id=te.tpid and te.userid=" + userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Task", "TaskExecute" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getBlur(map, tmpMap, " and t.tptitle");
			whereSql = whereSql + condition + blur; 
			whereSql = DbUtil.getWhereSql(whereSql);
			Object obj[] = (DbUtil.setPager(request,
					"Task as t,TaskExecute as te", whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppTask atp = new AppTask();
			AppTaskExecute ate = new AppTaskExecute();
			List rtls = atp.listReceiptTask(pagesize, whereSql, tmpPgInfo);
			ArrayList alrt = new ArrayList();
			String tptitle = "";
			for (int i = 0; i < rtls.size(); i++) {
				TaskForm tpf = new TaskForm();
				Object[] o = (Object[]) rtls.get(i);
				tpf.setId(Integer.valueOf(o[0].toString()));
				tptitle = String.valueOf(o[1]);
				tpf.setTptitle(tptitle.length() > 15 ? tptitle.substring(0, 13)
						+ "..." : tptitle);
				tpf.setBegindate((Date) o[2]);
				tpf.setEnddate((Date) o[3]);
				tpf.setStatusname(Internation.getStringByKeyPosition(
						"TaskPlanStatus", request, Integer.parseInt(o[4]
								.toString()), "global.sys.SystemResource"));
				tpf.setUsername(au.getUsersByID(
						Integer.valueOf(o[6].toString())).getRealname());
				tpf.setIsaffirm(ate.getTaskExecute(tpf.getId(), userid)
						.getIsaffirm());

				alrt.add(tpf);
			}

			request.setAttribute("alrt", alrt);

			DBUserLog.addUserLog(userid,0,"我的办公桌>>列表收到任务");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
