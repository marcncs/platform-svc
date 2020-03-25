package com.winsafe.drp.action.warehouse;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.HtmlSelect;

public class OutPutCheckEmptyAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    initdata(request);

    try{
    	String warehouseid = request.getParameter("warehouseid");
		String warehousebit = request.getParameter("warehousebit");
		String psid = request.getParameter("psid");
		String productid = request.getParameter("productid");
		String iszero = request.getParameter("iszero");
		StringBuffer sql = new StringBuffer();
		sql.append(" where p.isidcode=0 and p.wise=0 and ps.productid=p.id and ps.warehouseid='"+warehouseid+"' ");
		if ( warehousebit != null && !"".equals(warehousebit) ){
			sql.append(" and ps.warehousebit='"+warehousebit+"' ");
		}
		if ( psid != null && !"".equals(psid) ){
			sql.append(" and p.psid like '"+psid+"%' ");
		}
		if ( productid != null && !"".equals(productid) ){
			sql.append(" and p.id='"+productid+"' ");
		}
		if ( iszero != null && "0".equals(iszero) ){
			sql.append(" and ps.stockpile=0 ");
		}
		if ( iszero != null && "1".equals(iszero) ){
			sql.append(" and ps.stockpile>0 ");
		}

      
      AppProductStockpile aps = new AppProductStockpile();
      List pls = aps.getCheckStockpileEmpty(sql.toString());
      
      if ( pls.size() > Constants.EXECL_MAXCOUNT ){
    	  request.setAttribute("result", "当前记录数超过"+Constants.EXECL_MAXCOUNT+"条，请重新查询！");
    	  return new ActionForward("/sys/lockrecord2.jsp");
      }
      
      String fname = java.net.URLEncoder.encode("盘点表","UTF-8");    
      OutputStream os = response.getOutputStream();    
      response.reset();
      response.setHeader("content-disposition", "attachment; filename=" + new String(fname.getBytes("UTF-8"),"GBK") + ".xls");    
      response.setContentType("application/msexcel;charset=GBK");    
      testWrite(request, pls, os, warehouseid, warehousebit);
      DBUserLog.addUserLog(userid, 7, "库存盘点>>导出盘点空表"); 

      return null;
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
  
  public  void testWrite(HttpServletRequest request, List list, OutputStream os, String wid, String wbit) throws Exception{
	  	AppFUnit af = new AppFUnit();
	  	WarehouseService ws = new WarehouseService();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);
		
	
		WritableSheet sheets = workbook.createSheet("盘点单", 0);
		sheets.setRowView(0, false);
		sheets.getSettings().setDefaultColumnWidth(15); 
		
		sheets.addCell(new Label(0, 0, "仓库编号"));
		sheets.addCell(new Label(1, 0, wid));
		sheets.addCell(new Label(2, 0, "仓库名称"));
		sheets.addCell(new Label(3, 0, ws.getWarehouseName(wid)));
		sheets.addCell(new Label(4, 0, "仓位"));
		sheets.addCell(new Label(5, 0, wbit));
		
		sheets.addCell(new Label(0, 1, "商品编号"));
		sheets.addCell(new Label(1, 1, "商品名称"));
		sheets.addCell(new Label(2, 1, "规格"));
		sheets.addCell(new Label(3, 1, "单位编号"));
		sheets.addCell(new Label(4, 1, "单位名称"));
		sheets.addCell(new Label(5, 1, "仓位"));
		sheets.addCell(new Label(6, 1, "批次"));
		sheets.addCell(new Label(7, 1, "账面数量"));
		sheets.addCell(new Label(8, 1, "盘点数量"));
		for (int i=0; i<list.size(); i++ ){
			Object[] o = (Object[])list.get(i);	
			ProductStockpile ps = (ProductStockpile)o[0];
			Product p = (Product)o[1];
			
			sheets.addCell(new Label(0, i+2, p.getId()));
			sheets.addCell(new Label(1, i+2, p.getProductname()));
            sheets.addCell(new Label(2, i+2, p.getSpecmode()));
            sheets.addCell(new Label(3, i+2, p.getSunit().toString()));
            sheets.addCell(new Label(4, i+2, HtmlSelect.getResourceName(request, "CountUnit", p.getSunit())));
            sheets.addCell(new Label(5, i+2, ps.getWarehousebit()));
            sheets.addCell(new Label(6, i+2, ps.getBatch()));
            sheets.addCell(new Number(7, i+2, af.getStockpileQuantity(p.getId(), p
					.getSunit(), ps.getStockpile()),QWCF));
            sheets.addCell(new Number(8, i+2, 0,QWCF));
		}
		
		workbook.write();
      workbook.close();
      os.close();
	}
}
