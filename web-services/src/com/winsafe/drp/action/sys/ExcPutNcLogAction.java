package com.winsafe.drp.action.sys;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUserLog;
import com.winsafe.drp.dao.UserLog;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;


public class ExcPutNcLogAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	 initdata(request);
    try {
     
      String[] tablename={"UserLog"};
      String whereSql =getWhereSql2(tablename); 
      String timeCondition = getTimeCondition("LogTime"); 
      String blur = getKeyWordCondition("Detail"); 
      whereSql = whereSql + blur + timeCondition; 

      whereSql = DbUtil.getWhereSql(whereSql); 

      AppUserLog aul = new AppUserLog();
      if("".equals(whereSql)){
    	  whereSql+=" where modeltype=13";
      }
      else{
    	  whereSql+=" and modeltype=13";
      }
      List ulls = aul.getUserLog(whereSql);
      
      if ( ulls.size() > Constants.EXECL_MAXCOUNT ){
    	  request.setAttribute("result", "当前记录数超过"+Constants.EXECL_MAXCOUNT+"条，请重新查询！");
    	  return new ActionForward("/sys/lockrecord2.jsp");
      }
     
      String fname = "userlog";
      OutputStream os = response.getOutputStream();
      response.reset();
      response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");
      response.setContentType("application/msexcel");
      testWrite(request, ulls, os);
      DBUserLog.addUserLog(userid, 11, "导出操作日志");
     
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public  void testWrite(HttpServletRequest request, List list, OutputStream os) throws Exception{
	  	UsersService us = new UsersService();
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
			sheets[j].addCell(new Label(0, start, "操作日志",wchT));
			
			sheets[j].addCell(new Label(0, start+1, "操作用户:", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("UserName")));
			sheets[j].addCell(new Label(2, start+1, "日志日期:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginDate")+"-"+request.getParameter("EndDate")));
			
			sheets[j].addCell(new Label(0, start+2, "功能模块:", seachT));
			String IsStr = HtmlSelect.getNameByOrder(request, "ModelType", getInt("ModelType"));
			sheets[j].addCell(new Label(1, start+2, IsStr));		
			sheets[j].addCell(new Label(2, start+2, "关键字:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getParameter("KeyWord")));
			
			sheets[j].addCell(new Label(0, start+3, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+3, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+3, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+3, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+3, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+3, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+4, "操作用户",wcfFC));
			sheets[j].addCell(new Label(1, start+4, "日期",wcfFC));
			sheets[j].addCell(new Label(2, start+4, "模块",wcfFC));
			sheets[j].addCell(new Label(3, start+4, "内容",wcfFC));
			sheets[j].addCell(new Label(4, start+4, "详情",wcfFC));
			for (int i=start; i<currentnum; i++ ){
				int row = i - start + 5;
				UserLog ul = (UserLog)list.get(i);			
				sheets[j].addCell(new Label(0, row, us.getUsersName(ul.getUserid())));
	            sheets[j].addCell(new Label(1, row, ul.getLogtime().toString()));
	            sheets[j].addCell(new Label(2, row, ul.getModeltype()==null?"":HtmlSelect.getNameByOrder(request, "ModelType", ul.getModeltype())));
	            sheets[j].addCell(new Label(3, row, ul.getDetail()));
	            sheets[j].addCell(new Label(4, row, ul.getModifycontent()));
			}
		}
		
		
		workbook.write();
        workbook.close();
        os.close();
	}
}
