package com.winsafe.drp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.winsafe.erp.pojo.BillImportConfig;
import com.winsafe.hbm.util.StringUtil;

public class ReadExcelUtil {
	
	private static final Logger logger = Logger.getLogger(ReadExcelUtil.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	/**
	 * 根据路径获得Workbook
	 * @param path excel文件路径
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static Workbook getWorkbook(String path) throws InvalidFormatException, IOException{
		return WorkbookFactory.create(new File(path));
	}
	/**
	 * 获得表头的值
	 * @param sheet
	 * @param titleRowNo
	 * @return
	 */
	public static List<String> getTitle(Sheet sheet, Integer titleRowNo){
		List<String> list = new ArrayList<String>();
		if(sheet == null || sheet.getLastRowNum() <= 0){
			return list;
		}
		
		Row row = sheet.getRow(titleRowNo);
		if(row == null || row.getLastCellNum() <= 0){
			return list;
		}
		
		for(Cell cell: row){
			list.add(getStringVal(cell));
		}
		
		return list;
	}
	
	/**
	 * 获得sheet的行数
	 * @param sheet
	 * @return
	 */
	public static int getRowCounts(Sheet sheet){
		int count = 0;
		if(sheet != null && sheet.getLastRowNum() >= 0 && sheet.getPhysicalNumberOfRows() > 0){
			count = sheet.getLastRowNum() + 1;
		}
		return count;
	}
	
	/**
	 * 获得cell的值
	 * @param cell
	 * @return
	 */
	public static String getStringVal(Cell cell) {
		String result = "";
		if (cell == null) {
			return result;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			result = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			result = null;
			break;
		case Cell.CELL_TYPE_FORMULA:
			Workbook wb = cell.getSheet().getWorkbook();
			CreationHelper crateHelper = wb.getCreationHelper();
			FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
			result = getStringVal(evaluator.evaluateInCell(cell));
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				result = format.format(cell.getDateCellValue());
			} else {
				result = NumberToTextConverter.toText(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getRichStringCellValue().getString();
			break;
		default:
			result = "";
		}
		return result.trim();
	}
	
	public static void main(String arg[]) throws InvalidFormatException, IOException{
		Workbook wb = getWorkbook("C:\\Users\\ryan.ruan\\Desktop\\HK11 SI List.xlsx");
		Sheet sheet = wb.getSheetAt(0);
		/*int rCount = getRowCounts(sheet);*/
		List<String> columnlist = new ArrayList<String>();
		columnlist.add("Delivery");
		columnlist.add("Ship-to");
		columnlist.add("Material");
		columnlist.add("Delivery quantity");
		columnlist.add("Sold-to pt");
		columnlist.add("SU");
		columnlist.add("ShPt");
//		List list = getDataRowsWithRequiredColumns(sheet, columnlist);
//		System.out.println(list.size());
	}
	
	/**
	 * 获得需要的列的数据
	 * @param sheet
	 * @param columns 单据导入配置信息
	 * @param titleRowNo 表头所在行号
	 * @param dataRowNo 数据起始行号
	 * @return
	 */
	public static List<Map<String, String>> getDataRowsWithRequiredColumns(
			String filePath, Map<String, BillImportConfig> colAndConfig, Integer titleRowNo,
			Integer dataRowNo) {
		
		InputStream is = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		try {
			Workbook wb = null;
			
			if(filePath == null){
				return list;
			} else if (filePath.trim().toLowerCase().endsWith(".xls")){
				is = new FileInputStream(filePath.trim());
				wb = new HSSFWorkbook(is);
			} else if (filePath.trim().toLowerCase().endsWith(".xlsx")){
				is = new FileInputStream(filePath.trim());
				wb = new XSSFWorkbook(is);
			} else {
				return list;
			}
			
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet == null){
				return list;
			}
			if(colAndConfig == null || colAndConfig.size() <= 0){
				return list;
			}
			
			List<String> titleList = getTitle(sheet, titleRowNo);
			if(titleList == null || titleList.size() <= 0){
				return list;
			}
			
				
			Map<Integer, String> columnIndexMap = new HashMap<Integer, String>();
			for(int i=0; i < titleList.size(); i++){
				if(colAndConfig.containsKey(titleList.get(i))){
					columnIndexMap.put(i, titleList.get(i));
				} 
			}
			
			//没有找到合适的列
			if(columnIndexMap.size() <= 0){
				return list;
			}
			
			Map<String, String> defaultValues = new HashMap<String, String>();
			for(String colName : colAndConfig.keySet()) {
				if(!titleList.contains(colName)) {
					BillImportConfig config = colAndConfig.get(colName);
					if(!StringUtil.isEmpty(config.getDefaultValue())) {
						defaultValues.put(config.getFieldName(), config.getDefaultValue());
					}
				}
			}
			
			titleList.clear();
			
			Map<String, String> map = null;
			for(int i = dataRowNo ; i <= sheet.getLastRowNum() ; i++) {
				Row row = sheet.getRow(i);
				map = new HashMap<String, String>();
				for(Cell cell: row){
					if(columnIndexMap.containsKey(cell.getColumnIndex())){
						BillImportConfig config = colAndConfig.get(columnIndexMap.get(cell.getColumnIndex()));
						map.put(config.getFieldName().toLowerCase(), getStringVal(cell));
					}
				}
				map.putAll(defaultValues);
				list.add(map);
				map = null;
			}
			
		} catch (Exception e) {
			logger.error("解析excel文档时发生异常",e);
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("文件关闭失败",e);
				}
			}
		}
		return list;
	}
}
