package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidateCheckImport;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportCheckTableAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    ValidateCheckImport vct = (ValidateCheckImport) form;
    super.initdata(request);try{
    	
    	FormFile documentFile = (FormFile) vct.getDoc();
    	boolean bool = false;
		if (documentFile != null && !documentFile.equals("")) {

			if (documentFile.getContentType() != null) {
				if (documentFile.getFileName().indexOf("xls") >= 0) {
					bool = true;
				}
			}
		}
		
		if (bool) {
			Workbook wb = Workbook.getWorkbook(documentFile.getInputStream());
			Sheet sheet = wb.getSheet(0);
			int row = sheet.getRows();
			String warehouseid = sheet.getCell(1, 0).getContents().trim();
			String warehousebit = sheet.getCell(5, 0).getContents().trim();
			
			AppStockCheck asc = new AppStockCheck();
	    	StockCheck sc = new StockCheck();
			String scid = MakeCode.getExcIDByRandomTableName("stock_check", 2,"SC");
			sc.setId(scid);
			sc.setWarehouseid(warehouseid);
			sc.setWarehousebit(warehousebit);
			sc.setMemo("");
			sc.setMakeorganid(users.getMakeorganid());
			sc.setMakedeptid(users.getMakedeptid());
			sc.setMakeid(userid);
			sc.setMakedate(DateUtil.getCurrentDate());
			sc.setIsaudit(0);
			sc.setAuditid(0);
			sc.setIscreate(0);
			sc.setIsbar(0);
			sc.setKeyscontent(sc.getId()+","+sc.getMemo());
			asc.addStockCheck(sc);
			
			AppProductStockpile aps = new AppProductStockpile();
			AppStockCheckDetail ascd = new AppStockCheckDetail();
			 AppFUnit af = new AppFUnit();
			for ( int i=2; i<row; i++ ){
				StockCheckDetail scd = new StockCheckDetail();
				scd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_check_detail", 0, "")));
				scd.setScid(scid);				
				scd.setProductid(sheet.getCell(0, i).getContents());
				scd.setProductname(sheet.getCell(1, i).getContents());
				scd.setSpecmode(sheet.getCell(2, i).getContents());
				scd.setUnitid(DataFormat.strToInt(sheet.getCell(3, i).getContents()));
				scd.setWarehousebit(sheet.getCell(5, i).getContents());
				scd.setBatch(sheet.getCell(6, i).getContents());
				double cost = ProductCostService.getCost(warehouseid, scd.getProductid(), scd.getBatch());
				scd.setUnitprice(cost*af.getXQuantity(scd.getProductid(), scd.getUnitid()));
				scd.setReckonquantity(DataFormat.strToDouble(sheet.getCell(7, i).getContents()));
				scd.setCheckquantity(DataFormat.strToDouble(sheet.getCell(8, i).getContents()));
				ascd.addStockCheckDetail(scd);
				
				aps.IsLock(scd.getProductid(), scd.getBatch(), sc.getWarehouseid(), scd.getWarehousebit(),1);
			}
			wb.close();
		}else {
			request.setAttribute("result", "上传文件失败,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		request.setAttribute("result", "databases.add.success");
		return mapping.findForward("success");
    	


    }catch(Exception e){
      e.printStackTrace();
      request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
      return new ActionForward("/sys/lockrecord2.jsp");
    }
  }

}
