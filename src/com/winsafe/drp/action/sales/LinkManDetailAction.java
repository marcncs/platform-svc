package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.LinkmanForm;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.Internation;

public class LinkManDetailAction extends BaseAction {
   // Logger logger = Logger.getLogger(LinkManDetailAction.class);
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        //Integer cid = Integer.valueOf((String) request.getSession().getAttribute("cid"));
        
        initdata(request);
        AppLinkMan alm = new AppLinkMan();
        LinkmanForm ld = new LinkmanForm();
        try {
             HibernateUtil.currentSession();
             Linkman lk = alm.getLinkmanByID(id);
            //Customer customer = appCustomer.getCustomer(cid);

            ld.setName(lk.getName());
            ld.setSexname(Internation.getStringByKeyPosition("Sex",request,Integer.parseInt(lk.getSex().toString()),"global.sys.SystemResource" ));
            ld.setIdcard(lk.getIdcard());
            ld.setBirthday(lk.getBirthday());
            ld.setDepartment(lk.getDepartment());
            ld.setDuty(lk.getDuty());
            ld.setOfficetel(lk.getOfficetel());
            ld.setMobile(lk.getMobile());
            ld.setHometel(lk.getHometel());
            ld.setEmail(lk.getEmail());
            ld.setQq(lk.getQq());
            ld.setMsn(lk.getMsn());
            ld.setAddr(lk.getAddr());
            ld.setIsmainname(Internation.getStringByKeyPosition("YesOrNo",request,Integer.parseInt(lk.getIsmain().toString()),"global.sys.SystemResource" ));
            
           // String cname = customer.getCname();
            //request.setAttribute("cname", cname);
            request.setAttribute("ld", ld);
            request.getSession().setAttribute("id", id);

            //DBUserLog.addUserLog(userid,"客户联系人详情,编号："+id); 
            return mapping.findForward("info");

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return mapping.getInputForward();
    }

}
