package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppUserGrade;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.UserGrade;
import com.winsafe.drp.dao.UserGradeForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class MemberGradeDetailAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	Integer id = Integer.valueOf(request.getParameter("ID"));
        UsersBean users = UserManager.getUser(request);
        Integer userid = users.getUserid();
        AppUsers au = new AppUsers();

        AppMemberGrade aw = new AppMemberGrade();

        try {
        	 MemberGrade  w = aw.getMemberGradeByID(id);
             

             AppUserGrade asla = new AppUserGrade();
             List slrv = asla.getUserGradeMGID(id);
             ArrayList rvls = new ArrayList();
             for(int r=0;r<slrv.size();r++){
            	 UserGradeForm wvf = new UserGradeForm();
            	 UserGrade or=(UserGrade)slrv.get(r);
               //wvf.setWidname(aw.getWarehouseByID(Long.valueOf(or[1].toString())).getWarehousename());
               wvf.setUserid(or.getUserid());
              wvf.setUseridname(au.getUsersByid(or.getUserid()).getRealname());
               
               rvls.add(wvf);
             }

             request.setAttribute("rvls",rvls);
            request.setAttribute("w", w);

            //DBUserLog.addUserLog(userid,"仓库详情,编号："+id);
            return mapping.findForward("detail");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HibernateUtil.closeSession();
        }

        return mapping.getInputForward();
    }

}
