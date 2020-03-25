package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.dao.SuitForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToApproveSuitAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String fid = request.getParameter("fid");
        String actid = request.getParameter("actid");
        
//        Long userid = users.getUserid();
        AppUsers au = new AppUsers();
        AppOrgan ao = new AppOrgan();
        Suit p = new Suit(); 
        AppSuit ap = new AppSuit();

        try {
             
             p = ap.getSuitByID(id);
             SuitForm pf = new SuitForm();
             pf.setId(p.getId());
             pf.setCidname(p.getCname());
//             pf.setSuitcontentname(Internation.getStringByKeyPositionDB("HapContent", p.getSuitcontent()));
//             pf.setSuitwayname(Internation.getStringByKeyPositionDB("SuitWay",p.getSuitway()));
             pf.setSuittools(p.getSuittools());
             pf.setSuitstatus(p.getSuitstatus());
             pf.setSuitdate(DateUtil.formatDateTime(p.getSuitdate()));
             pf.setMemo(p.getMemo());
             pf.setIsdeal(p.getIsdeal());
//             pf.setIsdealname(Internation.getStringByKeyPosition("YesOrNo",request,p.getIsdeal(),"global.sys.SystemResource" ));
             pf.setDealwayname(Internation.getStringByKeyPositionDB("DealWay", p.getDealway()));
             if(p.getDealuser()>0){
             pf.setDealusername(au.getUsersByid(p.getDealuser()).getRealname());
             }else{
            	 pf.setDealusername("");
             }
             pf.setDealdate(DateUtil.formatDateTime(p.getDealdate()));
             pf.setDealcontent(p.getDealcontent());
             pf.setDealfinal(p.getDealfinal());
             pf.setMakeorganidname(ao.getOrganByID(p.getMakeorganid()).getOrganname());
             pf.setMakeidname(au.getUsersByid(p.getMakeid()).getRealname());
             pf.setMakedate(String.valueOf(p.getMakedate()));
//             pf.setApprovestatus(p.getApprovestatus());
//             pf.setApprovestatusname(Internation.getStringByKeyPosition("ApproveStatus",
//            		 request,p.getApprovestatus(),"global.sys.SystemResource" ));
            
             String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus2", request,
                     "ApproveStatus", null, null);
             String stractid=Internation.getStringByKeyPositionDB("ActID",
                       Integer.valueOf(actid));
             
 			

 			request.setAttribute("approvestatus", approvestatus);
 			request.setAttribute("aflid", fid);
 			request.setAttribute("stractid", stractid);
            request.setAttribute("pf", pf);

//            DBUserLog.addUserLog(userid,"抱怨投诉详情,编号："+id); 
            return mapping.findForward("toapprove");

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return mapping.getInputForward();
    }

}
