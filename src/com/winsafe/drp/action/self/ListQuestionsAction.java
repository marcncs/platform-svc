package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppQuestions;
import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Questions;
import com.winsafe.drp.dao.QuestionsForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:43:18
 * www.winsafe.cn
 */
public class ListQuestionsAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = 10;
		initdata(request);
		try{
			String Condition = " q.makeorganid = '"+users.getMakeorganid()+"'";
			
			String[] tablename = { "Questions"};
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			
			String blur = getKeyWordCondition("Content"); 
			whereSql = whereSql+timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppQuestions appQuestions = new AppQuestions();
			List<Questions> list  = appQuestions.findAll(request, whereSql, pageSize);
			List<QuestionsForm> listF = new ArrayList<QuestionsForm>();
			AppRespond appRespond = new AppRespond();
			for (Questions q : list) {
				QuestionsForm qf = new QuestionsForm();
				qf.setId(q.getId());
				qf.setContent(q.getContent());
				qf.setMakedate(q.getMakedate());
				qf.setMakedeptid(q.getMakedeptid());
				qf.setMakeid(q.getMakeid());
				qf.setMakeorganid(q.getMakeorganid());
				qf.setTitle(q.getTitle());
				qf.setNumber(appRespond.getNumber(q.getId()));
				listF.add(qf);
			}
			
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表常见问题  ");
			request.setAttribute("list", listF);
			return mapping.findForward("list");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		return super.execute(mapping, form, request, response);
	}
	
	

}
