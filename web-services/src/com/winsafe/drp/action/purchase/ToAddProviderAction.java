package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode;

public class ToAddProviderAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
      String vocationselect = Internation.getSelectTagByKeyAllDB("Vocation","vocation", false);
      
      String abcsortselect = Internation.getSelectTagByKeyAll("AbcSort", request,
          "abcsort", false, null);
      
      String genreselect =Internation.getSelectTagByKeyAllDB("Genre", 
          "genre", false);
      
      String mac = "泛洋力创科技有限公司";
      //String mac ="";
      //String mac = "5RA5V9TV";
      //String mac = "PVF804Z32203UN";
		AppProvider ap = new AppProvider();
		//AppUsers au = new AppUsers();
		//int cc = ap.getProvideCount("");
//		if(cc>15){
//			if(!mac.equals(MACAddress.getMACAddress())){
//			   String result = "sys.noregedit";
//             request.setAttribute("result", result);
//             return new ActionForward("/sys/lockrecord.jsp");
//		    }
//		}
//		
//	    if(UserLimit.userLimit()==1){
//	    	String result = "sys.userlimit";
//          request.setAttribute("result", result);
//          return new ActionForward("/sys/lockrecord.jsp");
//	    }
		
		AppMakeConf amc = new AppMakeConf();
		MakeConf mc = amc.getMakeConfByID("provide");
		String pid = "";
		String isread = "";
		if(mc.getRunmode()==1){
			pid = MakeCode.getExcIDByRandomTableName("provide",4,"");
			isread = "readonly";
		}
		
		request.setAttribute("mc", mc);
		request.setAttribute("pid", pid);
		request.setAttribute("isread", isread);
      
      AppCountryArea aca = new AppCountryArea();
      List list = aca.getProvince();

      ArrayList cals = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			CountryArea ca = new CountryArea();
			Object ob[] = (Object[]) list.get(i);
			ca.setId(Integer.valueOf(ob[0].toString()));
			ca.setAreaname(ob[1].toString());
			ca.setParentid(Integer.valueOf(ob[2].toString()));
			ca.setRank(Integer.valueOf(ob[3].toString()));
			cals.add(ca);
		}

      request.setAttribute("cals", cals);
      request.setAttribute("vocationselect",vocationselect);
      request.setAttribute("abcsortselect",abcsortselect);
      request.setAttribute("genreselect",genreselect);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
