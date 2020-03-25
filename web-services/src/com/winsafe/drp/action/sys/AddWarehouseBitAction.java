package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddWarehouseBitAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String wbid=request.getParameter("wbid");
		String wid = (String) request.getSession().getAttribute("sjwid");
        try {

        	AppWarehouse aba=new AppWarehouse();
        	WarehouseBit wbit = aba.getWarehouseBitByWidWbid(wid, wbid);
        	if ( wbit != null ){
        		request.setAttribute("result", wbid+"仓位已经存在！");
				return new ActionForward("/sys/lockrecord2.jsp");
        	}
        	WarehouseBit wb=new WarehouseBit();
        	wb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_bit",0,"")));
        	wb.setWid(wid);
        	wb.setWbid(wbid);
        	
        	aba.addWarehouseBit(wb);

              request.setAttribute("result", "databases.add.success");
              UsersBean users = UserManager.getUser(request);
              Integer userid = users.getUserid();
              DBUserLog.addUserLog(userid, 11, "仓库设置>>新增仓位,编号:"+wbid);
           	 return mapping.findForward("addresult");
        	
        } catch (Exception e) {
          	 e.printStackTrace();
           } finally {
           }
		return mapping.getInputForward();
	}

}
