package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.PlinkmanForm;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.ProviderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdProviderAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String pid = request.getParameter("PID");
    try{
      Provider p = new Provider();
      AppCountryArea aca = new AppCountryArea();
      AppProvider ap = new AppProvider();
      p = ap.getProviderByID(pid);

      ProviderForm pf = new ProviderForm();
      pf.setPid(p.getPid());
      pf.setPname(p.getPname());
      pf.setVocationname(Internation.getSelectTagByKeyAllDBDef("Vocation", "vocation",p.getVocation()));
      pf.setTaxcode(p.getTaxcode());
      pf.setCorporation(p.getCorporation());
      pf.setBankaccount(p.getBankaccount());
      pf.setBankname(p.getBankname());
      pf.setGenrename(Internation.getSelectTagByKeyAllDBDef("Genre","genre",p.getGenre()));
      pf.setTel(p.getTel());
      pf.setFax(p.getFax());
      pf.setMobile(p.getMobile());
      pf.setEmail(p.getEmail());
      pf.setPostcode(p.getPostcode());
      pf.setAddr(p.getAddr());
      pf.setAbcsortname(Internation.getSelectTagByKeyAll("AbcSort", request,
              "abcsort",p.getAbcsort().toString(), null));
      pf.setPrompt(p.getPrompt());
      pf.setTaxrate(p.getTaxrate());
      pf.setPaycondition(p.getPaycondition());
      pf.setHomepage(p.getHomepage());
//      pf.setRegion(p.getRegion());
//      if(p.getRegion()>0){
//      pf.setRegionname(aca.getAreaByID(Long.valueOf(p.getRegion())).getAreaname());
//      }else{
//      pf.setRegionname("");
//      }
      pf.setProvince(p.getProvince());
      if(p.getProvince()>0){
      pf.setProvincename(aca.getAreaByID(Integer.valueOf(p.getProvince())).getAreaname());
      }else{
      pf.setProvincename("");
      }
      pf.setCity(p.getCity());
      if(p.getCity()>0){
      pf.setCityname(aca.getAreaByID(Integer.valueOf(p.getCity())).getAreaname());
      }else{
      pf.setCityname("");
      }
      pf.setAreas(p.getAreas());
      if(p.getAreas()>0){
      pf.setAreasname(aca.getAreaByID(Integer.valueOf(p.getAreas())).getAreaname());
      }else{
      pf.setAreasname("");
      }
       
      pf.setRemark(p.getRemark());
      
      AppPlinkman applinkman = new AppPlinkman();
		List links = applinkman.getLinkmanByPid(pid);
		List linkmanlist = new ArrayList();
		for(Iterator it = links.iterator(); it.hasNext(); ){
			Plinkman lm = (Plinkman)it.next();
			PlinkmanForm f = new PlinkmanForm();
			f.setId(lm.getId());
			f.setName(lm.getName());
			f.setSexname(Internation.getSelectTagByKeyAll("Sex",request,"sex",String.valueOf(lm.getSex()),null));
//			f.setWedlockname(Internation.getSelectTagByKeyAll("Wedlock",request,"wedlock",String.valueOf(lm.getWedlock()),null));
			f.setStrbirthday(DateUtil.formatDate(lm.getBirthday()));
			f.setIdcard(lm.getIdcard());
			f.setDepartment(lm.getDepartment());
			f.setDuty(lm.getDuty());
			f.setOfficetel(lm.getOfficetel());
			f.setMobile(lm.getMobile());
			f.setHometel(lm.getHometel());
			f.setEmail(lm.getEmail());
			f.setQq(lm.getQq());
			f.setMsn(lm.getMsn());
//			f.setAddr(lm.getAddr());
			f.setIsmainname(Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",String.valueOf(lm.getIsmain()),null));
			linkmanlist.add(f);
		}
		if ( linkmanlist.size() == 0 ){
			linkmanlist = null;
		}
      
      List list0 = aca.getProvince();
      ArrayList cals = new ArrayList();

		for (int i = 0; i < list0.size(); i++) {
			CountryArea ca = new CountryArea();
			Object ob[] = (Object[]) list0.get(i);
			ca.setId(Integer.valueOf(ob[0].toString()));
			ca.setAreaname(ob[1].toString());
			ca.setParentid(Integer.valueOf(ob[2].toString()));
			ca.setRank(Integer.valueOf(ob[3].toString()));
			cals.add(ca);
		}


      request.setAttribute("cals", cals);
      request.setAttribute("pf",pf);
      request.setAttribute("linkmanlist", linkmanlist);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
