package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.TaskView;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListTaskAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		int pagesize = 10;

		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
			String Condition = " t.id=te.tpid and te.userid=" + userid;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Task","TaskExecute" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ConclusionDate");
			
			// whereSql = whereSql + timeCondition + blur + Condition; 
			String blur = DbUtil.getBlur(map, tmpMap, "TPTitle"); 
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppTask at = new AppTask();


			List list = at.searchTask(request, pagesize, whereSql);
			
			TaskView taskView =null;
			List<TaskView> listview = new ArrayList<TaskView>();
			for (int i = 0 ; i < list.size() ; i++) {
				Object[] obj = (Object[]) list.get(i);
				taskView= new TaskView();
				taskView.setId(Integer.valueOf(obj[0].toString()));
				taskView.setObjsort(Integer.valueOf(obj[1].toString()));
				taskView.setCname(obj[2].toString());
				taskView.setTptitle(obj[3].toString());
				taskView.setConclusiondate((Date)obj[4]);
				taskView.setPriority(Integer.valueOf(obj[5].toString()));
				taskView.setStatus(Integer.valueOf(obj[6].toString()));
				taskView.setOverstatus(Integer.valueOf(obj[7].toString()));
				taskView.setMakeorganid(obj[8].toString());
				taskView.setMakeid(Integer.valueOf(obj[9].toString()));
				taskView.setIsaffirm(Integer.valueOf(obj[10].toString()));
				listview.add(taskView);
			}
			

			request.setAttribute("tpfls", listview);

			DBUserLog.addUserLog(userid, 0,"我的办公桌>>列表任务");
			return mapping.findForward("tolist");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}