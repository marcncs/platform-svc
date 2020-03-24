package com.winsafe.drp.action.users;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ImportCustomerAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			InputStream is = new FileInputStream(
					"E:/Tomcat/Tomcat6.0-ss4c/webapps/ss4c/users/updcustomer.xls");
			Workbook wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(0);
			int row = sheet.getRows();
			int col = sheet.getColumns();

			for (int i = 1; i < row; i++) {
				System.out.println("" + sheet.getCell(0, i).getContents()
						+ "," + sheet.getCell(1, i).getContents() + ","
						+ sheet.getCell(2, i).getContents() + ","
						+ sheet.getCell(3, i).getContents() + ","
						+ sheet.getCell(4, i).getContents() + ","
						+ sheet.getCell(5, i).getContents() + ","
						+ sheet.getCell(6, i).getContents() + ","
						+ sheet.getCell(7, i).getContents() + ","
						+ sheet.getCell(8, i).getContents() + ","
						+ sheet.getCell(9, i).getContents());

				// System.out.println("---"+sheet.getCell(0,i).getContents());
				// System.out.println("---"+sheet.getCell(1,i).getContents());
				// System.out.println("---"+sheet.getCell(2,i).getContents());
				// System.out.println("---"+sheet.getCell(3,i).getContents());
				// System.out.println("---"+sheet.getCell(4,i).getContents());
				// System.out.println("---"+sheet.getCell(5,i).getContents());
				// System.out.println("---"+sheet.getCell(6,i).getContents());
				// System.out.println("---"+sheet.getCell(7,i).getContents());
				// System.out.println("---"+sheet.getCell(8,i).getContents());
				// System.out.println("---"+sheet.getCell(9,i).getContents());

			}

			// for (int i =0; i < row; i ++ )
			// {
			// AppProduct ap = new AppProduct();
			// AppProductPrice apr = new AppProductPrice();
			//					
			// for ( int j = 5; j < 6; j ++ ){
			// ProductPrice pr = new ProductPrice();
			// pr.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_price",0,"")));
			// //System.out.println("---a--"+sheet.getCell(0,i).getContents());
			// //System.out.println("---b--"+sheet.getCell(1,i).getContents());
			// pr.setProductid(sheet.getCell(0,i).getContents());
			// pr.setUnitid(ap.getProductByID(pr.getProductid()).getCountunit());
			// pr.setPolicyid(6l);
			// pr.setUnitprice(Double.valueOf(sheet.getCell(2,i).getContents()));
			// //apr.addProductPrice(pr);
			// System.out.println(pr.getId()+","+pr.getProductid()+","+pr.getUnitid()+","+pr.getPolicyid()+","+pr.getUnitprice());
			//						
			// //System.out.println("++aa="+sheet.getCell(j, i).getContents());
			// }
			//					
			// }

			/*
			 * 
			 * 
			 * for (int i =0; i < row; i ++ ) {
			 * 
			 * AppProduct ap = new AppProduct(); AppProductPrice apr = new
			 * AppProductPrice(); String pid=""; Integer countid =0; Integer
			 * plic=6; Double up=0.00;
			 * 
			 * //String[] datas = new String[col]; String pname=sheet.getCell(0,
			 * i).getContents(); List pls = ap.getProductByName(pid); //
			 * System.out.println("----"+pls.size()); if(pls.size()>0){ for(int
			 * p=0;p<pls.size();p++){ Product pn = (Product)pls.get(p);
			 * pid=pn.getId(); //String productname=pn.getProductname();
			 * countid=pn.getCountunit(); //
			 * System.out.println("pid==="+pn.getId()); //
			 * System.out.println("pname==="+pn.getProductname());
			 * 
			 * 
			 * //System.out.println("PID=="+sheet.getCell(0, i).getContents());
			 * for ( int j = 1; j < col; j ++ ){
			 * 
			 * //datas[j] = sheet.getCell(1, i).getContents(); if(j==1){ plic=6; }
			 * if(j==2){ plic=1; } if(j==3){ plic=3; } if(j==4){ plic=5; }
			 * if(sheet.getCell(j, i).getContents()!=null){ up =
			 * Double.valueOf(sheet.getCell(j, i).getContents()); }
			 * //System.out.println(plic+"++aa="+sheet.getCell(j,
			 * i).getContents()); ProductPrice pr = new ProductPrice();
			 * pr.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_price",0,"")));
			 * pr.setProductid(pid); pr.setUnitid(countid);
			 * pr.setPolicyid(Integer.valueOf(plic)); pr.setUnitprice(up);
			 * apr.addProductPrice(pr);
			 * System.out.println(pr.getId()+",'"+pr.getProductid()+"',"+pr.getUnitid()+","+pr.getPolicyid()+","+pr.getUnitprice()); }
			 * //System.out.println("-------------------"); } } }
			 */

			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
