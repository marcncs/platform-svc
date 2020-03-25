package com.winsafe.drp.action.users;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.dao.Member;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;


public class ExportMemberAction
    extends BaseAction {
	private static Logger logger = Logger.getLogger(ExportMemberAction.class);
	private AppMember appMember=new AppMember();

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	 initdata(request);
    try {
      String Condition = "";
      Map map=new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Member"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
                "CreateDate"); 
      String blur = DbUtil.getOrBlur(map, tmpMap, "loginname"); 
      whereSql = whereSql + timeCondition + blur +Condition ; 
      whereSql = DbUtil.getWhereSql(whereSql); 
        
      List ulls = appMember.getAllMember(whereSql);
      
      if ( ulls.size() > Constants.EXECL_MAXCOUNT ){
    	  request.setAttribute("result", "当前记录数超过"+Constants.EXECL_MAXCOUNT+"条，请重新查询！");
    	  return new ActionForward("/sys/lockrecord2.jsp");
      }
     
      String fname = "Members" + "_" + DateUtil.getCurrentDateTime();
      OutputStream os = response.getOutputStream();
      response.reset();
      response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");
      response.setContentType("application/msexcel");
      testWrite(request, ulls, os);
      DBUserLog.addUserLog(userid, 11, "导出注册会员");
    }
    catch (Exception e) {
    	logger.error("",e);
    }
    return null;
  }
  
  public  void testWrite(HttpServletRequest request, List list, OutputStream os) throws Exception{
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);
		
		int snum = 1;
		int rowssize = 50000;
		snum = list.size()/rowssize;
		if ( list.size()%rowssize > 0 ){
			snum ++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j=0; j<snum; j ++ ){
			sheets[j] = workbook.createSheet("sheet"+j, j);
			sheets[j].setRowView(0, false);
			sheets[j].getSettings().setDefaultColumnWidth(20); 
			
			int currentnum = (j+1)*rowssize;
			if ( currentnum >= list.size() ){
				currentnum = list.size();
			}
			int start = j * rowssize;	
			
			sheets[j].mergeCells(0, start, 5, start);
			sheets[j].addCell(new Label(0, start, "注册用户",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "注册日期:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			sheets[j].addCell(new Label(2, start+1, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+2, "编号",wcfFC));
			sheets[j].addCell(new Label(1, start+2, "注册手机",wcfFC));
			sheets[j].addCell(new Label(2, start+2, "注册时间",wcfFC));
			sheets[j].addCell(new Label(3, start+2, "上次登录时间",wcfFC));
			sheets[j].addCell(new Label(4, start+2, "登陆次数",wcfFC));
			for (int i=start; i<currentnum; i++ ){
				int row = i - start + 3;
				Member ul = (Member)list.get(i);			
				sheets[j].addCell(new Label(0, row, ul.getId()+""));
	            sheets[j].addCell(new Label(1, row, ul.getLoginname()));
	            sheets[j].addCell(new Label(2, row, DateUtil.formatDateTime(ul.getCreatedate())));
	            sheets[j].addCell(new Label(3, row, DateUtil.formatDateTime(ul.getLastlogin())));
	            sheets[j].addCell(new Label(4, row, ul.getLogintimes()+""));
			}
		}
		workbook.write();
        workbook.close();
        os.close();
	}
}
