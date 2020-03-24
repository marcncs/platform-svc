package com.winsafe.drp.action.assistant;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppViewWlmIdcode;
import com.winsafe.drp.dao.RuleUserWh;
import com.winsafe.drp.dao.ViewWlmIdcode;
import com.winsafe.drp.dao.ViewWlmIdcodeForm;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
/**
 * 导出物流码溯源查询
 * WEI.LI
 * ADD 2013-07-26 13:58
 */
public class ExcPutViewIdcodeRetrospectAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			//拼接SQL
			String whereSql = " where ";
			//获取产品ID
			String proudctId = request.getParameter("ProductID");
			//获取批次
			String batch = request.getParameter("batch");
			//获取生产日期
			String produceDate = request.getParameter("produceDate");
			//如果三个参数都不为空的情况，再查询
			if(null != proudctId && !"".equals(proudctId)){
				whereSql += " productid = '"+proudctId+"' ";
				if(null != batch && !"".equals(batch)){
					whereSql += " and batch = '"+batch+"' ";
				}
				if(null != produceDate && !"".equals(produceDate)){
					whereSql += " and producedate = '"+produceDate+"' ";
				}
			//否则，将不查询数据
			}else{
				whereSql += " null between startno and endno ";
				//默认初始化日期
				//produceDate = DateUtil.getCurrentDateString();
			}
			//初始化查询方法和返回集合
			AppRuleUserWH aru = new AppRuleUserWH();
			List<ViewWlmIdcodeForm> vmiList = new ArrayList<ViewWlmIdcodeForm>();
			//执行查询语句
			List pils = new AppViewWlmIdcode().getViewWlmIdcodeByWhereGroup(whereSql);
			//循环集合过滤非登录用户的业务范围的数据
			for (int i = 0; i < pils.size(); i++) {
				Map p = (Map) pils.get(i);
				ViewWlmIdcodeForm vwi = new ViewWlmIdcodeForm();
				vwi.setId(p.get("id").toString());
				vwi.setWarehouseid(p.get("warehouseid").toString());
				vwi.setCid(p.get("cid").toString());
				vwi.setCname(p.get("cname").toString());
				vwi.setProvince(Integer.valueOf(p.get("province").toString()));
				vwi.setNccode(p.get("nccode").toString());
				vwi.setProductname(p.get("productname").toString());
				vwi.setSpecmode(p.get("specmode").toString());
				vwi.setBillname(p.get("billname").toString());
				vwi.setMakedate(p.get("makedate").toString());
				vwi.setProducedate(p.get("producedate")==null?"":p.get("producedate").toString());
				vwi.setBatch(p.get("batch").toString());
				vwi.setPackquantity(Double.valueOf(p.get("packquantity").toString()));
				RuleUserWh ruw = aru.getRuleByWH(vwi.getWarehouseid(), users.getUserid());
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
			write(request, vmiList, os);
			DBUserLog.addUserLog(userid, 1, "导出物流码查询结果");//日志 
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public  void write(HttpServletRequest request, List<ViewWlmIdcodeForm> list, OutputStream os) throws Exception{
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
			
			sheets[j].mergeCells(0, start, 14, start);
			sheets[j].addCell(new Label(0, start, "物流码溯源查询  ",wchT));
			sheets[j].addCell(new Label(0, start+1, "产品：", seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("ProductName")));	
			sheets[j].addCell(new Label(2, start+1, "批次:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("batch")));
			sheets[j].addCell(new Label(4, start+1, "生产日期:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("produceDate")));
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));
			
			sheets[j].addCell(new Label(0, start+3, "仓库编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "仓库名称",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "单据号",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "单据类型",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "客户代码",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "内部编码",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "客户名称",wcfFC));
			sheets[j].addCell(new Label(7, start+3, "省份",wcfFC));
			sheets[j].addCell(new Label(8, start+3, "产品内部编号",wcfFC));
			sheets[j].addCell(new Label(9, start+3, "产品名称",wcfFC));
			sheets[j].addCell(new Label(10, start+3, "规格",wcfFC));
			sheets[j].addCell(new Label(11, start+3, "制单日期",wcfFC));
			sheets[j].addCell(new Label(12, start+3, "生产日期",wcfFC));
			sheets[j].addCell(new Label(13, start+3, "批次",wcfFC));
			sheets[j].addCell(new Label(14, start+3, "包装数量",wcfFC));
			
			for (int i=start; i<currentnum; i++ ){
				int row = i - start + 4;
				ViewWlmIdcodeForm ul = (ViewWlmIdcodeForm)list.get(i);			
				sheets[j].addCell(new Label(0, row, ul.getWarehouseid()));
	            sheets[j].addCell(new Label(1, row, ws.getWarehouseName(ul.getWarehouseid())));
	            sheets[j].addCell(new Label(2, row, ul.getId()));
	            sheets[j].addCell(new Label(3, row, ul.getBillname()));
	            sheets[j].addCell(new Label(4, row, ul.getCid()));
	            sheets[j].addCell(new Label(5, row, ul.getSyncode()));
	            sheets[j].addCell(new Label(6, row, ul.getCname()));
	            sheets[j].addCell(new Label(7, row, cas.getCountryAreaName(ul.getProvince())));
	            sheets[j].addCell(new Label(8, row, ul.getNccode()));
	            sheets[j].addCell(new Label(9, row, ul.getProductname()));
	            sheets[j].addCell(new Label(10, row, ul.getSpecmode()));
	            sheets[j].addCell(new Label(11, row, ul.getMakedate()));
	            sheets[j].addCell(new Label(12, row, ul.getProducedate()));
	            sheets[j].addCell(new Label(13, row, ul.getBatch()));
	            sheets[j].addCell(new Label(14, row, ul.getPackquantity().toString()));
			}
		}
		
		workbook.write();
        workbook.close();
        os.close();
	}
}
