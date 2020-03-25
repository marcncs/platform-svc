package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.ProviderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectProvideAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		String keyword = request.getParameter("KeyWord");
		try {
			String visitorgan = "";
			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
				visitorgan = " or p.makeorganid in("+users.getVisitorgan()+")";
			}

	    	String Condition = " (p.makeid="+userid+" "+visitorgan+") and p.isdel=0 ";
	    	
	    	//String Condition =" p.isdel=0 ";
	      Map map = new HashMap(request.getParameterMap());
	      Map tmpMap = EntityManager.scatterMap(map);
	      //String sql = "select * from provide ";
	      String[] tablename={"Provider"};
	      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

	      String blur = DbUtil.getOrBlur(map, tmpMap, "PName","Mobile");

	      whereSql = whereSql + blur + Condition;
	      whereSql = DbUtil.getWhereSql(whereSql); 

	      Object obj[] = (DbUtil.setDynamicPager(request, "Provider as p", whereSql,
						pagesize,"spsubCondition"));
				SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
				whereSql = (String) obj[1];
	      
	      AppProvider ap = new AppProvider();
	      List pls = ap.getProvider(request,pagesize,whereSql);
	      ArrayList sls = new ArrayList();

	      for(int i=0;i<pls.size();i++){
	        ProviderForm pf = new ProviderForm();
	        Provider o=(Provider)pls.get(i);
	        pf.setPid(o.getPid());
	        pf.setPname(o.getPname());
	        pf.setMobile(o.getMobile());
	        pf.setTel(o.getTel());
	        
	        sls.add(pf);
	      }

	      request.setAttribute("sls",sls);
			return mapping.findForward("selectprovide");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
