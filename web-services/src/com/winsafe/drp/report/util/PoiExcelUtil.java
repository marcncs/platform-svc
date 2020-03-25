package com.winsafe.drp.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelUtil {
	/**
	 * 获取Excel
	 * Create Time Feb 20, 2012 11:09:06 AM 
	 * @param templateFileName
	 * @return
	 */
	public static Workbook getWorkbook(String templateFileName){
		File sourcefile = new File( templateFileName);
		return getWorkbook(sourcefile);
	}
	/**
	 * 获取Excel 
	 * @return
	 */
	public static Workbook getWorkbook(File sourcefile)
	{
		Workbook wb = null;
		InputStream is = null;
		try
		{
			is = new FileInputStream(sourcefile);

			try
			{
				wb = new XSSFWorkbook(is);
			}
			catch(Exception e)
			{
				if(is!=null){
					try{
						is.close();
					}catch (Exception e2) {
					}
				}
				is = new FileInputStream(sourcefile);
				wb = new HSSFWorkbook(is);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(is!=null){
				try{
					is.close();
				}catch (Exception e) {
				}
			}
		}

		return wb;
	}
	
	/**
	 * 获取单元格的值,保留公式
	 * Create Time Apr 16, 2012 1:36:39 PM 
	 * @param srcCell
	 * @return
	 * @author huangxy
	 */
	public static Object getCellValueKeepFormula(Cell srcCell)
	{
		if(srcCell==null)return null;
		int srcCellType = srcCell.getCellType();
		switch (srcCellType)
		{
			case Cell.CELL_TYPE_NUMERIC:
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(srcCell))
				{
					return srcCell.getDateCellValue();
				}
				else
				{
					return srcCell.getNumericCellValue();
				}
			case Cell.CELL_TYPE_STRING:
				if(srcCell.getRichStringCellValue()!=null){
					return srcCell.getRichStringCellValue().getString();
				}
				return null;
			case Cell.CELL_TYPE_BLANK:
				return null;
			case Cell.CELL_TYPE_BOOLEAN:
				return srcCell.getBooleanCellValue();
			case Cell.CELL_TYPE_ERROR:
				return srcCell.getErrorCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return srcCell.getCellFormula();
			default:
				return null;
		}
	}
	
	
	/**
	 * 获取单元格的值,有公式就取它的值
	 * Create Time 2013-2-4 上午11:47:26 
	 * @param srcCell
	 * @return
	 * @author lipeng
	 */
	public static Object getCellRealValue(Cell srcCell)
	{
		if(srcCell==null)return null;
		int srcCellType = srcCell.getCellType();
		switch (srcCellType)
		{
		case Cell.CELL_TYPE_NUMERIC:
			if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(srcCell))
			{
				return srcCell.getDateCellValue();
			}
			else
			{
				return srcCell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_STRING:
			if(srcCell.getRichStringCellValue()!=null){
				return srcCell.getRichStringCellValue().getString();
			}
			return null;
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return srcCell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return srcCell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			try{
				return srcCell.getStringCellValue();
			}catch (IllegalStateException e) {
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(srcCell))
				{
					return srcCell.getDateCellValue();
				}
				else
				{
					return srcCell.getNumericCellValue();
				}
			}
		default:
			return null;
		}
	}
	/**
	 * 创建行
	 * Create Time Feb 17, 2012 10:05:47 AM 
	 * @param sheet
	 * @param sample
	 * @param rowIndex
	 * @return
	 * @author huangxy
	 */
	public static XSSFRow createRow(XSSFSheet sheet,XSSFRow sample,int rowIndex){
		XSSFRow newRow=sheet.getRow(rowIndex);
		if(newRow==null){
			newRow=sheet.createRow(rowIndex);
		}
		if(sample!=null && sample.getRowStyle()!=null){
			newRow.setRowStyle(sample.getRowStyle());
		}
		return newRow;
	}
	/**
	 * 创建单元格
	 * Create Time Feb 17, 2012 10:05:40 AM 
	 * @param row
	 * @param cellSample
	 * @param colIndex
	 * @return
	 * @author huangxy
	 */
	public static XSSFCell createCell(XSSFRow row,XSSFCell cellSample,int colIndex){
		XSSFCell cell=row.getCell(colIndex);
		if(cell==null){
			cell = row.createCell(colIndex);
		}
		if(cellSample!=null && cellSample.getCellStyle()!=null){
			cell.setCellStyle(cellSample.getCellStyle());
		}
		return cell;
	}
	
	public static void setCell(Row row,int colIndex,Object value){
		if(value==null)return;
			Cell cell = createCell(row,colIndex);
		if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Date){
			cell.setCellValue((Date)value);
		}else if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}else if(value instanceof BigDecimal){
			cell.setCellValue(((BigDecimal) value).intValue());
		}
	}
	
	public static void setCell(Row row,int colIndex,Object value,CellStyle style){
		if(value==null)return;
		Cell cell = createCell(row,colIndex);
		if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Date){
			cell.setCellValue((Date)value);
		}else if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}else if(value instanceof BigDecimal){
			cell.setCellValue(((BigDecimal) value).intValue());
		}
		if(style != null){
			cell.setCellStyle(style);
		}
	}
	
	/**
	 * 创建单元格
	 * Create Time Feb 17, 2012 3:35:53 PM 
	 * @param row
	 * @param colIndex
	 * @return
	 * @author huangxy
	 */
	public static Cell createCell(Row row,int colIndex){
		Cell cell=row.getCell(colIndex);
		if(cell==null){
			cell = row.createCell(colIndex);
		}
		return cell;
	}
	
	public static void setCellFormula(Row row,int colIndex,String value){
		if(value==null)return;
		Cell oldCell=row.getCell(colIndex);
		Cell cell;
		if(oldCell==null){
			cell = row.createCell(colIndex);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(value);
		}else{
			CellStyle cellStyle=oldCell.getCellStyle();
			cell = row.createCell(colIndex);
			cell.setCellStyle(cellStyle);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(value);
		}
	}
	
	

	public static void setCellFormulaByCellStyle(Row row,int colIndex,String value,CellStyle percentCellStyle){
		if(value==null)return;
		Cell oldCell=row.getCell(colIndex);
		Cell cell;
		if(oldCell==null){
			cell = row.createCell(colIndex);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellStyle(percentCellStyle);
			cell.setCellFormula(value);
		}else{
			CellStyle cellStyle=oldCell.getCellStyle();
			cell = row.createCell(colIndex);
			cell.setCellStyle(cellStyle);
			cell.setCellType(Cell.CELL_TYPE_FORMULA);
			cell.setCellFormula(value);
		}
	}
	
	public static File saveWorkbook(Workbook wb,String filePath)
	{
		FileOutputStream fos=null;
		File targetFile=null;
		try
		{
			//文件输出流，用于将写到Workbook中的数据保存到模板文件中
			fos = new FileOutputStream(filePath);

			//关闭文件输出流
			wb.write(fos);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
		return targetFile;
	}
	
	/**
	 * 获取单元格的位置标示符，如getPos(0,9)=A10
	 * Create Time Feb 16, 2012 4:38:47 PM 
	 * @param col 列号(0...)
	 * @param row 行号(0...)
	 * @return
	 * @author huangxy
	 */
	public static String getPos(int col,int row){
		StringBuilder sb = new StringBuilder();
		int t=26;
		int i;
		int m;
		do{
			i=col/t;
			m=col%t;
			if(sb.length()==0){
				sb.append(String.valueOf((char)(65+m)));
			}else{
				sb.append(String.valueOf((char)(64+m)));
			}
			col=i;
		}while(i>t);

		if(i!=0){
			sb.append(String.valueOf((char)(64+i)));
		}

		return sb.reverse().toString()+(row+1);
	}
	
}
