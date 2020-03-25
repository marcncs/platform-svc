package com.winsafe.drp.action.assistant;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.ViewWlmIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;


public class ExcPutViewWlmIdcodeAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  initdata(request);
    try {
		
		UploadProduceReport upr = null;
		String wlmidcode = request.getParameter("wlmidcode");
		String bzmidcode = request.getParameter("bzmidcode");
		
		String Condition = "";
		//判断散条码是否为空
		if(null != bzmidcode && !"".equals(bzmidcode)){
			AppUploadProduceReport aupr = new AppUploadProduceReport();
			upr = aupr.getUploadProduceReportByUnitNo(bzmidcode);
			//判断散条码的生产数据是否存在
			if(null != upr){
				//生产数据存在的话，从生产数据获取箱条码
				
				request.setAttribute("wlmidcode",upr.getBoxCode());
				Condition = " '"+upr.getBoxCode()+"' between startno and endno ";
			}else{
				//生产数据不存在
				
				//判断箱条码是否存在
				if(null != wlmidcode && !"".equals(wlmidcode)){
					request.setAttribute("bzmidcode","");
					Condition = " '"+wlmidcode+"' between startno and endno ";
				}else{
					String boxIdcode=getBoxIdcode(bzmidcode);
					if(StringUtil.isEmpty(boxIdcode)){
						Condition = " 'null' between startno and endno ";
						request.setAttribute("wlmidcode","");
					}else{
						request.setAttribute("wlmidcode",boxIdcode);
						Condition = " '"+boxIdcode+"' between startno and endno ";
					}
				}
			}
		}else{
			Condition = " '"+wlmidcode+"' between startno and endno ";
			request.setAttribute("wlmidcode",wlmidcode);
		}
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "ViewWlmIdcode" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		whereSql = whereSql + Condition; 
		whereSql = DbUtil.getWhereSql(whereSql); 
		List<ViewWlmIdcode> vmiList = new ArrayList<ViewWlmIdcode>();

		AppViewWlmIdcode asb = new AppViewWlmIdcode();
		List pils = asb.getViewWlmIdcode(whereSql);
		for (int i = 0; i < pils.size(); i++) {
			ViewWlmIdcode vwi = (ViewWlmIdcode) pils.get(i);
			RuleUserWh ruw = new AppRuleUserWH().getRuleByWH(vwi.getWarehouseid(), users.getUserid());
			if(null != ruw){
				vmiList.add(vwi);
			}
		}
      
      if ( vmiList.size() > Constants.EXECL_MAXCOUNT ){
    	  request.setAttribute("result", "当前记录数超过"+Constants.EXECL_MAXCOUNT+"条，请重新查询！");
    	  return new ActionForward("/sys/lockrecord2.jsp");
      }
     
      String fname = "wlmidcode";//excel文件名    
      OutputStream os = response.getOutputStream();//取得输出流    
      response.reset();//清空输出流    
      response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");//设定输出文件头    
      response.setContentType("application/msexcel");//定义输出类型    
      testWrite(request, vmiList, os);
      DBUserLog.addUserLog(userid, 1, "导出物流码查询结果");//日志 
     
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private String getBoxIdcode(String idcode) throws Exception
	{
		if(idcode!=null){
			idcode=idcode.trim();
			idcode=idcode.toUpperCase();
		}
		if(StringUtil.isEmpty(idcode) || idcode.length()<1){
			return null;
		}
		
		//第1位：代表产品品牌(1为国内产品、2为国际等)；
		String f=idcode.substring(0,1);
		if("1".equals(f) || "3".equals(f)){
			//国内产品(散)

			return null;
		}else if("C".equals(f)){
			//国内产品(箱)

			return null;
		}else if("2".equals(f)){
			//国际产品

			if(idcode.length()!=16){
				return null;
			}
			return idcode.substring(0,13)+"000";
		}else{
			return null;
		}
	}
  
  public  void testWrite(HttpServletRequest request, List list, OutputStream os) throws Exception{
	  	WarehouseService ws = new WarehouseService();
	  	CountryAreaService cas = new CountryAreaService();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);
		
		int snum = 1;
		int rowssize = 50000;
		snum = list.size()/rowssize;
		if ( list.size()%rowssize >= 0 ){
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
			
			sheets[j].mergeCells(0, start, 15, start);
			sheets[j].addCell(new Label(0, start, "物流码查询邮件  ",wchT));
			sheets[j].addCell(new Label(0, start+1, "散包装码：", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("bzmidcode")));	
			sheets[j].addCell(new Label(2, start+1, "物流码:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("wlmidcode")));
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));
			
			
			
			sheets[j].addCell(new Label(0, start+3, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "客户代码",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "内部编码",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "客户名称",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "省份",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "产品编号",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "产品名称",wcfFC));
			sheets[j].addCell(new Label(8, start+3, "规格",wcfFC));
			sheets[j].addCell(new Label(9, start+3, "单据类型",wcfFC));
			sheets[j].addCell(new Label(10, start+3, "制单日期",wcfFC));
			sheets[j].addCell(new Label(11, start+3, "生产日期",wcfFC));
			sheets[j].addCell(new Label(12, start+3, "批次",wcfFC));
			sheets[j].addCell(new Label(13, start+3, "起始物流码",wcfFC));
			sheets[j].addCell(new Label(14, start+3, "结束物流码",wcfFC));
			sheets[j].addCell(new Label(15, start+3, "包装数量",wcfFC));
			
			for (int i=start; i<currentnum; i++ ){
				int row = i - start + 4;
				ViewWlmIdcode ul = (ViewWlmIdcode)list.get(i);			
				sheets[j].addCell(new Label(0, row, ul.getWarehouseid()));
	            sheets[j].addCell(new Label(1, row, ws.getWarehouseName(ul.getWarehouseid())));
	            sheets[j].addCell(new Label(2, row, ul.getCid()));
	            sheets[j].addCell(new Label(3, row, ul.getSyncode()));
	            sheets[j].addCell(new Label(4, row, ul.getCname()));
	            sheets[j].addCell(new Label(5, row, cas.getCountryAreaName(ul.getProvince())));
	            sheets[j].addCell(new Label(6, row, ul.getProductid()));
	            sheets[j].addCell(new Label(7, row, ul.getProductname()));
	            sheets[j].addCell(new Label(8, row, ul.getSpecmode()));
	            sheets[j].addCell(new Label(9, row, ul.getBillname()));
	            sheets[j].addCell(new Label(10, row, DateUtil.formatDateTime(ul.getMakedate())));
	            sheets[j].addCell(new Label(11, row, ul.getProducedate()));
	            sheets[j].addCell(new Label(12, row, ul.getBatch()));
	            sheets[j].addCell(new Label(13, row, ul.getStartno()));
	            sheets[j].addCell(new Label(14, row, ul.getEndno()));
	            sheets[j].addCell(new Label(15, row, ul.getPackquantity().toString()));
			}
		}
		
		
		workbook.write();
        workbook.close();
        os.close();
	}
}
