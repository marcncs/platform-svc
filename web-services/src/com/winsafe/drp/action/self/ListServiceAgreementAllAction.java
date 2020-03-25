package com.winsafe.drp.action.self;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.ServiceAgreementView;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

//import java.text.SimpleDateFormat;

public class ListServiceAgreementAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		initdata(request);
		int pagesize = 10;
		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();
			String Condition = " sa.id = se.said and se.userid = " + userid ;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ServiceAgreement","ServiceExecute" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil
					.getTimeCondition(map, tmpMap, "SDate"); 
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
		
			AppServiceAgreement asa = new AppServiceAgreement();
			List usList = asa.searchServiceAgreement(request,pagesize,
					whereSql);
			
			ServiceAgreementView sab = null;
			
			List<ServiceAgreementView> list = new ArrayList<ServiceAgreementView>();
			
			
			for(int i = 0; i < usList.size(); i++){
				Object[] obj = (Object[]) usList.get(i);
				sab = new ServiceAgreementView();
				sab.setId(Integer.valueOf(obj[0].toString()));
				sab.setObjsort(Integer.valueOf(obj[1].toString()));
				sab.setCid(obj[2].toString());
				sab.setCname(obj[3].toString());
				sab.setScontent(Integer.valueOf(obj[4].toString()));
				sab.setSstatus(Integer.valueOf(obj[5].toString()));
				sab.setRank(Integer.valueOf(obj[6].toString()));
				sab.setSdate((Date)obj[7]);
				sab.setMakeorganid(obj[8].toString());
				sab.setMakeid(Integer.valueOf(obj[9].toString()));
				sab.setIsaffirm(Integer.valueOf(obj[10].toString()));
				list.add(sab);
			}
			
			request.setAttribute("hList", list);
			DBUserLog.addUserLog(userid,0,"我的办公桌>>列表服务预约");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
