package com.winsafe.drp.action.common;




/**
 * @author : ivan
 * @version : 2009-8-22 下午02:49:30
 * www.winsafe.cn
 */
public class ExcelWriter {

//	
//	 private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;
//
//	 
//	 private static String NUMBER_FORMAT = "#,##0.00";
//
//	 
//	 private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"
//
//	 private OutputStream out = null;
//
//	 private HSSFWorkbook workbook = null;
//
//	 private HSSFSheet sheet = null;
//
//	 private HSSFRow row = null;
//
//	 public ExcelWriter() {
//	 }
//
//	 /**
//	  * 初始化Excel
//	  * 
//	  */
//	 public ExcelWriter(OutputStream out) {
//	  this.out = out;
//	  this.workbook = new HSSFWorkbook();
//	  this.sheet = workbook.createSheet();
//	 }
//
//	 /**
//	  * 导出Excel文件
//	  * 
//	  * @throws IOException
//	  */
//	 public void export() throws FileNotFoundException, IOException {
//	  try {
//	   workbook.write(out);
//	   out.flush();
//	   out.close();
//	  } catch (FileNotFoundException e) {
//	   throw new IOException(" 生成导出Excel文件出错! ", e);
//	  } catch (IOException e) {
//	   throw new IOException(" 写入Excel文件出错! ", e);
//	  }
//
//	 }
//
//	 /**
//	  * 增加一行
//	  * 
//	  * @param index
//	  *            行号
//	  */
//	 public void createRow(int index) {
//	  this.row = this.sheet.createRow(index);
//	 }
//
//	 /**
//	  * 获取单元格的值
//	  * 
//	  * @param index
//	  *            列号
//	  */
//	 public String getCell(int index) {
//	  HSSFCell cell = this.row.getCell((short) index);
//	  String strExcelCell = "";
//	  if (cell != null) { // add this condition
//	   // judge
//	   switch (cell.getCellType()) {
//	   case HSSFCell.CELL_TYPE_FORMULA:
//	    strExcelCell = "FORMULA ";
//	    break;
//	   case HSSFCell.CELL_TYPE_NUMERIC: {
//	    strExcelCell = String.valueOf(cell.getNumericCellValue());
//	   }
//	    break;
//	   case HSSFCell.CELL_TYPE_STRING:
//	    strExcelCell = cell.getStringCellValue();
//	    break;
//	   case HSSFCell.CELL_TYPE_BLANK:
//	    strExcelCell = "";
//	    break;
//	   default:
//	    strExcelCell = "";
//	    break;
//	   }
//	  }
//	  return strExcelCell;
//	 }
//
//	 /**
//	  * 设置单元格
//	  * 
//	  * @param index
//	  *            列号
//	  * @param value
//	  *            单元格填充值
//	  */
//	 public void setCell(int index, int value) {
//	  HSSFCell cell = this.row.createCell((short) index);
//	  cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//	  cell.setCellValue(value);
//	 }
//
//	 /**
//	  * 设置单元格
//	  * 
//	  * @param index
//	  *            列号
//	  * @param value
//	  *            单元格填充值
//	  */
//	 public void setCell(int index, double value) {
//	  HSSFCell cell = this.row.createCell((short) index);
//	  cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//	  cell.setCellValue(value);
//	  HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
//	  HSSFDataFormat format = workbook.createDataFormat();
//	  cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
//	  cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
//	 }
//
//	 /**
//	  * 设置单元格
//	  * 
//	  * @param index
//	  *            列号
//	  * @param value
//	  *            单元格填充值
//	  */
//	 public void setCell(int index, String value) {
//	  HSSFCell cell = this.row.createCell((short) index);
//	  cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//	  cell.setEncoding(XLS_ENCODING);
//	  cell.setCellValue(value);
//	 }
//
//	 /**
//	  * 设置单元格
//	  * 
//	  * @param index
//	  *            列号
//	  * @param value
//	  *            单元格填充值
//	  */
//	 public void setCell(int index, Calendar value) {
//	  HSSFCell cell = this.row.createCell((short) index);
//	  cell.setEncoding(XLS_ENCODING);
//	  cell.setCellValue(value.getTime());
//	  HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
//	  cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
//	  cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
//	 }
//
//	 public static void main(String[] args) {
//	  System.out.println(" 开始导出Excel文件 ");
//
//	  File f = new File("C:\\qt.xls");
//	  ExcelWriter e = new ExcelWriter();
//
//	  try {
//	   e = new ExcelWriter(new FileOutputStream(f));
//	  } catch (FileNotFoundException e1) {
//	   e1.printStackTrace();
//	  }
//
//	  e.createRow(0);
//	  e.setCell(0, "试题编码 ");
//	  e.setCell(1, "题型");
//	  e.setCell(2, "分值");
//	  e.setCell(3, "难度");
//	  e.setCell(4, "级别");
//	  e.setCell(5, "知识点");
//
//	  e.createRow(1);
//	  e.setCell(0, "t1");
//	  e.setCell(1, 1);
//	  e.setCell(2, 3.0);
//	  e.setCell(3, 1);
//	  e.setCell(4, "重要");
//	  e.setCell(5, "专业");
//
//	  try {
//	   e.export();
//	   System.out.println(" 导出Excel文件[成功] ");
//	  } catch (IOException ex) {
//	   System.out.println(" 导出Excel文件[失败] ");
//	   ex.printStackTrace();
//	  }
//	 }

}