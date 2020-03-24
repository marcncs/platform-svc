package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.LinkmanForm;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.Internation;

public class UpdLinkManAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    //logger.info("============into UpdLinkManAction============");
    Integer id=Integer.valueOf(request.getParameter("id"));
    //String cid=(String)request.getSession().getAttribute("cid");
    
    initdata(request);
    AppLinkMan appLinkman=new AppLinkMan();
    AppCustomer appCustomer=new AppCustomer();
    Linkman l=new Linkman();
   
    try{
       HibernateUtil.currentSession();
       l=appLinkman.getLinkman(id,userid);
       Customer customer=appCustomer.getCustomer(l.getCid());
       
       LinkmanForm lf = new LinkmanForm();
       lf.setId(l.getId());
       lf.setCid(l.getCid());
       lf.setCidname(customer.getCname());
       lf.setName(l.getName());
       lf.setSexname(Internation.getSelectTagByKeyAll("Sex",request,"sex",String.valueOf(l.getSex()),null ));
       lf.setIdcard(l.getIdcard());
       lf.setBirthday(l.getBirthday());
       lf.setDepartment(l.getDepartment());
       lf.setDuty(l.getDuty());
       lf.setOfficetel(l.getOfficetel());
       lf.setMobile(l.getMobile());
       lf.setHometel(l.getHometel());
       lf.setEmail(l.getEmail());
       lf.setQq(l.getQq());
       lf.setMsn(l.getMsn());
       lf.setAddr(l.getAddr());
       lf.setIsmainname(Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",String.valueOf(l.getIsmain()),null ));
       
       

       String cname = customer.getCname();
       request.setAttribute("cname", cname);
       request.setAttribute("linkman",lf);
       request.getSession().setAttribute("id",id);
       
       
       return mapping.findForward("edit");
    }
    catch(Exception e){
       e.printStackTrace();
    }
    finally {
      //HibernateUtil.closeSession();
    }

    return mapping.getInputForward();
  }

}
