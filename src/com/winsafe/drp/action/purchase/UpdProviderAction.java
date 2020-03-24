package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidateProvider;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdProviderAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    ValidateProvider vp = (ValidateProvider) form;
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    String pid=request.getParameter("pid");

    try {

      AppProvider ap = new AppProvider();
      
      Provider p = ap.getProviderByID(pid);
      Provider oldP = (Provider)BeanUtils.cloneBean(p);
//      BeanCopy bc = new BeanCopy();
//		bc.copy(p, request);
      
      
//      p.setPid(MakeCode.getExcIDByRandomTableName("provide",3,"P-"));
      p.setPname(vp.getPname());
      p.setVocation(vp.getVocation());
      p.setTaxcode(vp.getTaxcode());
      p.setCorporation(vp.getCorporation());
      p.setBankaccount(vp.getBankaccount());
      p.setBankname(vp.getBankname());
      p.setGenre(vp.getGenre());
      p.setTel(vp.getTel());
      p.setFax(vp.getFax());
      p.setMobile(vp.getMobile());
      p.setEmail(vp.getEmail());
      p.setPostcode(vp.getPostcode());
      p.setAddr(vp.getAddr());
      p.setAbcsort(vp.getAbcsort());
      p.setPrompt(vp.getPrompt());
      p.setTaxrate(vp.getTaxrate());
      p.setPaycondition(vp.getPaycondition());
      p.setHomepage(vp.getHomepage());
//      p.setRegion(vp.getRegion());
      p.setProvince(vp.getProvince());
      p.setCity(vp.getCity());
      p.setAreas(vp.getAreas());
      p.setIsactivate(0);
      p.setActivateid(0);
      p.setUpdateid(userid);
      p.setModifydate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
      p.setRemark(vp.getRemark());
      p.setIsdel(0);

      ap.updProvider(p);
      
      
      AppPlinkman applinkman = new AppPlinkman();
      Plinkman linkman = null;
      applinkman.delPlinkmanByPid(pid);
      for(int i=0; i<vp.getName().length; i++){
    	  if (!"".equals(vp.getName()[i])){
    		  linkman = new Plinkman();
    		  linkman.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("plinkman",0,"")));
    		  linkman.setPid(p.getPid());
    		  linkman.setName(vp.getName()[i]);
    		  linkman.setIdcard(vp.getIdcard()[i]);
    		  linkman.setSex(vp.getSex()[i]);
    		  linkman.setBirthday(DateUtil.StringToDate(vp.getBirthday()[i]));
    		  linkman.setDepartment(vp.getDepartment()[i]);
    		  linkman.setDuty(vp.getDuty()[i]);
    		  linkman.setOfficetel(vp.getLinkofficetel()[i]);
    		  linkman.setMobile(vp.getLinkmobile()[i]);
    		  linkman.setHometel(vp.getHometel()[i]);
    		  linkman.setEmail(vp.getLinkemail()[i]);
    		  linkman.setQq(vp.getQq()[i]);
    		  linkman.setMsn(vp.getMsn()[i]);
    		  linkman.setIsmain(vp.getIsmain()[i]); 
    		  linkman.setMakeorganid(users.getMakeorganid());
    		  linkman.setMakedeptid(users.getMakedeptid());
    		  linkman.setMakeid(userid);
    		  linkman.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
    		  applinkman.addPlinkman(linkman);
    	  }    	  
      }
      
      request.setAttribute("result", "databases.upd.success");
      DBUserLog.addUserLog(userid,2, "供应商资料>>修改供应商,编号："+p.getPid(), oldP, p);
      
      return mapping.findForward("success");
    }
    catch (Exception e) {
    	request.setAttribute("result", "databases.upd.fail");
      e.printStackTrace();
    }
    finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
