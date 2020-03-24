package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.AppServiceDetail;
import com.winsafe.drp.dao.ServiceAgreement;
import com.winsafe.drp.dao.ServiceAgreementForm;
import com.winsafe.drp.dao.ServiceDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdServiceAgreementAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        AppServiceAgreement asa = new AppServiceAgreement();
        UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
        ServiceAgreement h = new ServiceAgreement();
        try {

        	h = asa.getServiceAgreementByID(id);
        	if(!h.getMakeid().equals(userid)){
				request.setAttribute("result", "databases.del.nosuccess");
				return new ActionForward("/sys/lockrecord.jsp");
			}
        	
        	ServiceAgreementForm  saf= new ServiceAgreementForm();
        	saf.setObjsort(h.getObjsort());
        	saf.setId(h.getId());
        	saf.setCid(h.getCid());
        	saf.setCname(h.getCname());
        	saf.setLinkman(h.getLinkman());
        	saf.setTel(h.getTel());
        	saf.setScontent(h.getScontent());
        	saf.setSstatus(h.getSstatus());
        	saf.setRank(h.getRank());
        	saf.setSfee(h.getSfee());
        	saf.setSdate(String.valueOf(h.getSdate()));
        	saf.setQuestion(h.getQuestion());
        	saf.setMemo(h.getMemo());
        	
        	AppServiceDetail asld = new AppServiceDetail();
            List slls = asld.getServiceDetailBySAID(id);
            ArrayList als = new ArrayList();

            for(int i=0;i<slls.size();i++){
            	ServiceDetailForm sodf = new ServiceDetailForm();
              Object[] o = (Object[])slls.get(i);
              sodf.setProductid(String.valueOf(o[2]));
              sodf.setProductname(o[3].toString());
              sodf.setSpecmode(o[4].toString());
              sodf.setUnitid(Integer.valueOf(o[5].toString()));
              sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",                  
                  Integer.parseInt(o[5].toString())));
              sodf.setUnitprice(Double.valueOf(o[6].toString()));
              sodf.setQuantity(Double.valueOf(o[7].toString()));
              sodf.setSubsum(Double.valueOf(o[8].toString()));
              als.add(sodf);
            }
            
            request.setAttribute("als",als);
            request.setAttribute("saf", saf);
            return mapping.findForward("toupd");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HibernateUtil.closeSession();
        }

        return mapping.getInputForward();
    }

}
