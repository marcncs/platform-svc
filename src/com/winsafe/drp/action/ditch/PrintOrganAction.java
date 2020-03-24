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
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.DbUtil;

public class PrintOrganAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	UsersBean users = UserManager.getUser(request);
	super.initdata(request);
	try {
		Map<String,Object> param = new HashMap<>();
		OrganService os = new OrganService();
		String Condition = " o.sysid like :osysid "; 
		param.put("osysid ", os.getOrganByID(users.getMakeorganid()).getSysid()+"%");
		Map map=new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"Organ"};
        String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param); 

        String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "ID","OrganName","OMobile","OECode"); 
        whereSql = whereSql + blur +Condition ; 
        whereSql = DbUtil.getWhereSql(whereSql); 
        
        AppOrgan ao = new AppOrgan();
        AppOIntegral aoi = new AppOIntegral();
        AppCountryArea aca = new AppCountryArea();

		List menuls=ao.getOrgan(whereSql);
		List dpt=new ArrayList();
		String province = "";
		String city = "";
		String areas = "";
		for(int i=0;i<menuls.size();i++){
			OrganForm rmf=new OrganForm();
			Organ o=(Organ)menuls.get(i);
			rmf.setId(o.getId());
			rmf.setOecode(o.getOecode());
			rmf.setOrganname(o.getOrganname());
			rmf.setIsrepeal(o.getIsrepeal());			
			if (o.getProvince()>0) {
				CountryArea c = aca.getAreaByID(o.getProvince());
				province = c==null?"": c.getAreaname();
			}
			if (o.getCity() > 0) {
				CountryArea c = aca.getAreaByID(o.getCity());
				city = c==null?"": c.getAreaname();
			}
			if (o.getAreas() > 0) {
				CountryArea c = aca.getAreaByID(o.getAreas());
				areas = c==null?"": c.getAreaname();
			}
			rmf.setProvincename(province);
			rmf.setCityname(city);
			rmf.setAreasname(areas);
			rmf.setParentid(o.getParentid());
			if ( !o.getParentid().equals("0")){
				rmf.setParentidname(ao.getOrganByID(o.getParentid()).getOrganname());
			}
			rmf.setRank(o.getRank());
			rmf.setIntegral(aoi.getOIntegralByOID(o.getId()));
			dpt.add(rmf);
		}
		
		List list0 = aca.getProvince();
		ArrayList cals = new ArrayList();
		for (int a = 0; a < list0.size(); a++) {
			CountryArea ca = new CountryArea();
			Object ob[] = (Object[]) list0.get(a);
			ca.setId(Integer.valueOf(ob[0].toString()));
			ca.setAreaname(ob[1].toString());
			ca.setParentid(Integer.valueOf(ob[2].toString()));
			ca.setRank(Integer.valueOf(ob[3].toString()));
			cals.add(ca);
		}
		request.setAttribute("cals",cals);
		request.setAttribute("dpt",dpt);
		
		return mapping.findForward("toprint");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
