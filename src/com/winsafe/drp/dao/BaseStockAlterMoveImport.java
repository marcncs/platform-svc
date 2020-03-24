package com.winsafe.drp.dao;

import java.io.File;
import java.io.IOException;

import com.winsafe.common.util.StringUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class BaseStockAlterMoveImport {

	
	public static void genWorkBString( String filepath ) throws Exception{
		Workbook wb  = Workbook.getWorkbook(new File(filepath));
		Sheet sheet = wb.getSheet(0);
		int rowCount = sheet.getRows();
		int num=1;
		String styleNo=getCellValue(sheet,    1, num++);// 样式编号
		String styleName=getCellValue(sheet,  1, num++);// 样式名称
		String organName=getCellValue(sheet,  1, num++); // 公司全名
		String organId=getCellValue(sheet ,   1, num++); // 公司编号
		String  olinkmanvalue=getCellValue(sheet,1,num++); //联系人
		String address=getCellValue(sheet,1,num++); //地址
		String otel=getCellValue(sheet,1,num++); //电话
		String zipcode=getCellValue(sheet,1,num++); //邮编
		String taxNo=getCellValue(sheet,1,num++); //税号
		String bank=getCellValue(sheet,1,num++); //开户银行
		String bankno=getCellValue(sheet,1,num++); //公司账号
		String organremake=getCellValue(sheet,1,num++); //公司备注
		String makeorganman=getCellValue(sheet,1,num++); //制单人
		String makedate=getCellValue(sheet,1,num++); //日期
		String nccode=getCellValue(sheet,1,num++); //单据编号
		String operateman=getCellValue(sheet,1,num++); //经手人
		String receiveorganname=getCellValue(sheet,1,num++); //往来单位名称
		String receivewarehousename=getCellValue(sheet,1,num++); //往来单位仓库名称
		String explain=getCellValue(sheet,1,num++); //说明
		String remake=getCellValue(sheet,1,num++); //摘要
		String waybill=getCellValue(sheet,1,num++); //运单号
		String gdnum=getCellValue(sheet,1,num++); //物件数量
		String onjtel=getCellValue(sheet,1,num++); //承运单位及电话
	    String AA=getCellValue(sheet,1,num++); //包装方式
		String oecode=getCellValue(sheet,1,num++); //客户编号
		String BB=getCellValue(sheet,1,num++); //邮 编
		String CC=getCellValue(sheet,1,num++); //地址电话
		String DD=getCellValue(sheet,1,num++); //联 系 人
		String EE=getCellValue(sheet,1,num++); //信誉额度
		String FF=getCellValue(sheet,1,num++); //税 号
		String GG=getCellValue(sheet,1,num++); //开户行及帐号
		String HH=getCellValue(sheet,1,num++); //备 注
		String JJ=getCellValue(sheet,1,num++); //行业
		String KK=getCellValue(sheet,1,num++); //传真
		
		
	}
	
	private static  String getCellValue(Sheet sheet, int col, int row) {
		Cell cell = sheet.getCell(col, row);
		if (cell != null) {
			String value = cell.getContents();
			if (!StringUtil.isEmpty(value)) {
				return value.trim();
			}
		}
		return null;
	}
	
}
