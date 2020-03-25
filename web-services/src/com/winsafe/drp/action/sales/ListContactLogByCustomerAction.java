package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.ContactLogForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListContactLogByCustomerAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
        String strCid = request.getParameter("Cid");
        if (strCid == null) {
            strCid = (String) request.getSession().getAttribute("cid");
        }
        String cid = strCid;
        AppContactLog appContactLog = new AppContactLog();
        AppCustomer ac = new AppCustomer();
        int pagesize = 5;
        initdata(request);
        try {
            
            
            
            Map map = new HashMap(request.getParameterMap());
            Map tmpMap = EntityManager.scatterMap(map);
            String[] tablename={"ContactLog"};
            String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
            String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
                    "ContactDate"); 
            String blur = DbUtil.getBlur(map, tmpMap, "ContactContent"); 
            whereSql = whereSql + timeCondition + blur + " c.cid='" + cid +
                       "' "; 
            whereSql = DbUtil.getWhereSql(whereSql); 

           // Object obj[] = DbUtil.setPager(request, "ContactLog as c", whereSql,pagesize);
            Object obj[] = DbUtil.setDynamicPager(request, "ContactLog as c", whereSql,pagesize,"subCondition");
            SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
            whereSql = (String) obj[1];

            List usList = appContactLog.searchContactLog(pagesize, whereSql,
                    tmpPgInfo);
            ArrayList contactList = new ArrayList();          
            for (int t = 0; t < usList.size(); t++) {
                ContactLogForm clf = new ContactLogForm();
                Object[] ob = (Object[]) usList.get(t);
                clf.setId(Integer.valueOf(ob[0].toString()));
                clf.setCid(ob[1].toString());
                clf.setCidname(ac.getCustomer(clf.getCid()).getCname());
                clf.setLinkman(String.valueOf(ob[7]));
                clf.setContactdate(String.valueOf(ob[2]).substring(0,10));
                clf.setContactmodename(Internation.getStringByKeyPosition("ContactMode",
                        request,
                        Integer.parseInt(ob[3].toString()), "global.sys.SystemResource"));
                clf.setContactpropertyname(Internation.getStringByKeyPosition("ContactProperty",
                        request,
                        Integer.parseInt(ob[4].toString()), "global.sys.SystemResource"));
                clf.setNextcontact(String.valueOf(ob[8]).substring(0,10));
                clf.setUserid(Integer.valueOf(ob[9].toString()));

                contactList.add(clf);

            }
            request.getSession().setAttribute("cid", strCid);
            request.setAttribute("usList", contactList);
            DBUserLog.addUserLog(userid,"列表联系记录"); 
            return mapping.findForward("contactList");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
