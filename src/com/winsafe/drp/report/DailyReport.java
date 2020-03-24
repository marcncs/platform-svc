package com.winsafe.drp.report;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.winsafe.common.exception.CustomException;
import com.winsafe.drp.dao.DailyProductOut;
import com.winsafe.drp.report.dao.AppDailyReport;
import com.winsafe.drp.report.util.PoiExcelUtil;
import com.winsafe.drp.report.util.ReportUtil;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 日报
 * Project:is->Class:DailyReport.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time 2013-12-20 上午11:28:44 
 * @author <a href='peng.li@winsafe.cn'>lipeng</a>
 * @version 0.8
 */
public class DailyReport {
	/**生成日期 */
	private Date date;
	/**报表文件名*/
	public static final String FILENAME="Daily_Report";
	
	private static Object alock=new Object();
	
	/**冻结坐标轴*/
	private static Integer FREEZE_X=2;
	private static Integer FREEZE_Y=12;
	/**表头*/
	private static String[] firstRowCells = new String[]
	    {"No","办事处名字","办事处经理名字","经销商编号","经销商名字"
		,"主管编号","主管名字","网点编号","网点名字","产品编号","产品名称","价格"};
	
	
	/**表格起始行**/
	private static Integer first_row = 1;
	
	private static final int CELL_0=0;
	private static final int CELL_1=1;
	private static final int CELL_2=2;
	private static final int CELL_3=3;
	private static final int CELL_4=4;
	private static final int CELL_5=5;
	private static final int CELL_6=6;
	private static final int CELL_7=7;
	private static final int CELL_8=8;
	private static final int CELL_9=9;
	private static final int CELL_10=10;
	private static final int CELL_11=11;
	private static final int CELL_12=12;
	private static final int CELL_13=13;
	private static final int CELL_14=14;
	private static final int CELL_15=15;
	
	/**单元格样式*/
	private  CellStyle dateCellStyle ;
	private CellStyle titleCellStyle;
	private  DataFormat dateDataFormat;
	
	/**工作簿路径 */
	private String filepath;
	
	/**月报App**/
	AppDailyReport appdr = new AppDailyReport();
	/**封装月报信息**/
	Map<String,LinkedHashMap<String,DailyProductOut>> dpoInfo = new LinkedHashMap<String, LinkedHashMap<String,DailyProductOut>>();
	/**封装主管产品信息***/
	Map<String,DailyProductOut> saleInfo = new LinkedHashMap<String, DailyProductOut>();
	/**封装主管信息**/
	Map<String,Integer[]> salemanInfo = new LinkedHashMap<String, Integer[]>();
	/**封装办事处信息***/
	Map<String,Integer[]> regionInfo = new LinkedHashMap<String, Integer[]>();
	/**封装主管名称信息***/
	Map<String,String> salemanMap = new HashMap<String, String>();
	/**封装办事处信息**/
	Map<String,String> regionMap = new HashMap<String, String>();
	/**
	 * 生成报表
	 * Create Time 2013-12-20 下午01:35:32 
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public String generate() throws Exception{
		synchronized (alock){
			Workbook wb = null;
			date = DateUtil.getCurrentDate();
			Date tempDate = date;
			try{
				DBUserLog.addUserLog(1, "生成报表开始");
				//获取工作簿
				//wb=getDailyReport(tempDate);
				wb=getDailyReport(DateUtil.addDay2Date(-1,tempDate));
				
				//组建基础
				buildDate(DateUtil.addDay2Date(-1,tempDate));
				
				//写数据
				writeData(wb,DateUtil.addDay2Date(-1,tempDate));
				
				//保存工作薄
				PoiExcelUtil.saveWorkbook(wb, filepath);
				
				DBUserLog.addUserLog(1, "生成报表结束");
			}catch (Exception e) {
				e.printStackTrace();
				DBUserLog.addUserLog(1, "生成报表失败");
			}
		}
		return filepath;
	}
	/**
	 * 获取日报
	 * Create Time 2013-12-20 下午02:29:57 
	 * @param date
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	private Workbook getDailyReport(Date date) throws Exception{
		Workbook wb;
		String fileName = FILENAME + DateUtil.formatDate(date,"yyyyMMdd") + ".xlsx";
		
		filepath=ReportUtil.getReportDir()+fileName;
		//如果文件不存在就创建文件
		File file=new File(filepath);
		if(file.exists()){
			file.delete();
		}
		//重新生成基础数据
		wb = genBaseReport(date);
		
		/*if(!file.exists()){
			//重新生成基础数据
			wb = genBaseReport(date);
		}else{
			wb = PoiExcelUtil.getWorkbook(filepath);
		}*/
		
		return wb;
	}
	/**
	 * 创建日报,并设置基本数据
	 * Create Time 2013-12-20 下午02:35:44 
	 * @param date
	 * @return
	 * @author lipeng
	 */
	private Workbook genBaseReport(Date date){
		Workbook wb = null;
		//创建工作薄
		wb = new XSSFWorkbook();
		//初始化相关样式
		initStyle(wb);
		//创建sheet
		Sheet sheet = wb.createSheet();
		//设置窗口冻结
		sheet.createFreezePane(FREEZE_Y, FREEZE_X);
		
		Row row = sheet.createRow(first_row);
		//设置表头
		for(int j=0 ; j<firstRowCells.length ; j++){
			//设置列宽自动
			PoiExcelUtil.setCell(row, j, firstRowCells[j] ,titleCellStyle);
		}
		
		Date firstDate = DateUtil.StringToDate(DateUtil.getMonthFirstDay());
		//Date lastDate = Dateutil.StringToDate(DateUtil.getMonthLastDay()); 
		Date lastDate =  date;
		Integer dayCount = Dateutil.getDiffDays(firstDate, lastDate) + 1;
		for(int i=0 ; i<dayCount ; i++){
			//设置列宽自动
			PoiExcelUtil.setCell(row, FREEZE_Y+i, DateUtil.formatDateTimeWeek2(Dateutil.addDay2Date(i, firstDate)),dateCellStyle);
		}
		
		//设置列宽自适应
//		for(int m=0 ; m<(firstRowCells.length+dayCount);m++){
//			sheet.autoSizeColumn(m,false);
//		}
		return wb;
	}
	/**
	 * 初始化样式 
	 * Create Time 2013-12-20 下午02:59:49 
	 * @author lipeng
	 */
	private void initStyle(Workbook wb){
		//初始化日期样式
		dateDataFormat = wb.createDataFormat();
		dateCellStyle =  wb.createCellStyle();
		dateCellStyle.setDataFormat(dateDataFormat.getFormat("MM月dd日"));
		dateCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		dateCellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		dateCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		dateCellStyle.setWrapText(true);
		
		titleCellStyle = wb.createCellStyle();
		titleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		titleCellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	}
	
	/**
	 * 写数据
	 * Create Time Apr 16, 2012 12:09:42 PM 
	 * @author huangxy
	 * @throws CustomException 
	 */
	private void writeData(Workbook wb,Date date) throws Exception{
		Sheet sheet;
		//创建sheet
		sheet = wb.getSheetAt(0);
		//写入基础数据
		try {
			setCell(sheet,dpoInfo);
			//写入公式
			setProductSum(sheet,dpoInfo,date);
		} catch (Exception e) {
			e.printStackTrace();
			DBUserLog.addUserLog(1, "写入数据失败");
		}
	}
	/**
	 * 组建基础数据
	 */
	private void buildDate(Date tempdate) {
		
		List<DailyProductOut> dpoList = appdr.queryDailyReportInfo(DateUtil.formatDate(tempdate,"yyyy-MM"));
		if(dpoList!=null && !dpoList.isEmpty()){
			LinkedHashMap<String,DailyProductOut> dailyInfo = null;
			LinkedHashMap<String,DailyProductOut> oldDailyInfo = null;
			DailyProductOut dpo = null;
			//循环遍历每一条数据
			//for(DailyProductOut dpo : dpoList){
			for(int i=0;i<dpoList.size();i++){
				dpo = dpoList.get(i);
				//map中每条记录数key(saleid_productid)
				String key = dpo.getRegionid() + "_" +dpo.getSalemanid() + "_" + dpo.getProductid();
				//如果存在则追加天销售量
				if(dpoInfo.containsKey(key)){
					oldDailyInfo = dpoInfo.get(key);
					oldDailyInfo.put(DateUtil.formatDate(dpo.getMakedate(),"yyyyMMdd"), dpo);
					//dpoInfo.put(key, oldDailyInfo);
				}else{
					//不存在新建
					dailyInfo = new LinkedHashMap<String, DailyProductOut>();
					dailyInfo.put(DateUtil.formatDate(dpo.getMakedate(),"yyyyMMdd"), dpo);
					dpoInfo.put(key, dailyInfo);
				}
				
				/**
				 * 组织主管信息
				 */
				String manKey = dpo.getSalemanid();
				if(!salemanMap.containsKey(manKey)){
					salemanMap.put(manKey, dpo.getSalemanname());
				}
				/**
				 * 组织办事处信息
				 */
				String reKey = dpo.getRegionid()+"";
				if(!regionMap.containsKey(reKey)){
					regionMap.put(reKey, dpo.getRegionname()+"_" + dpo.getRegionuname());
				}
			}
		}
		
	}
	
	/**
	 * 设置单元格
	 * Create Time Apr 17, 2012 4:24:27 PM 
	 * @param sheet
	 * @param dateColIndex 日期列索引
	 * @param tempMap 要写入的数据
	 * @param rowIndex 行索引
	 * @param colIndex tempMap中key对应的列的索引
	 * @author huangxy
	 */
	private void setCell(Sheet sheet,Map<String,LinkedHashMap<String,DailyProductOut>> dpoInfoMap){
		Cell cell = null;
		Row rowTemp = null;
		//创建基本信息标志
		boolean flag = false;
		//no列递增
		int No = 0;
		//行标志
		int rowIndex = first_row + 1;
		//日报列标志
		//int colIndex = 12;
		DailyProductOut dailyDpo = null;
		//循环遍历dpoInfoMap
		Iterator<String> dpoIter = dpoInfoMap.keySet().iterator();
		LinkedHashMap<String,DailyProductOut> dailyInfoMap = null; 
		while(dpoIter.hasNext()){
			String dpoKey = dpoIter.next();
			dailyInfoMap = dpoInfoMap.get(dpoKey);
			//基本信息置为true
			flag = true;
			//no列置为1
			No++;
			//设置类标志
			//colIndex = 12;
			
			/**
			 * 组织主管汇总信息开始位置和结束位置
			 */
			Integer[] salemanNum = null;
			Integer[] regionNum = null;
			String salemanKey = dpoKey.substring(0,dpoKey.lastIndexOf("_"));
			//如果存在取出数据相加
			if(!salemanInfo.containsKey(salemanKey)){
				salemanNum = new Integer[2];
				salemanNum[0]= rowIndex;
				salemanNum[1]= rowIndex;
				salemanInfo.put(salemanKey, salemanNum);
			}else{
				salemanNum = salemanInfo.get(salemanKey);
				salemanNum[1] = rowIndex;
			}
			/**
			 * 组织办事处信息开始位置和结束位置
			 */
			String regionKey = salemanKey.substring(0,salemanKey.lastIndexOf("_"));
			if(!regionInfo.containsKey(regionKey)){
				regionNum = new Integer[2];
				regionNum[0] = rowIndex;
				regionNum[1] = rowIndex;
				regionInfo.put(regionKey, regionNum);
			}else{
				regionNum = regionInfo.get(regionKey);
				regionNum[1] = rowIndex;
			}
			
			
			//循环遍历dailyInfoMap
			Iterator<String> dailyItera = dailyInfoMap.keySet().iterator();
			while(dailyItera.hasNext()){
				String dailyKey = dailyItera.next();
				dailyDpo = dailyInfoMap.get(dailyKey);
				//第一次循环添加ext基本信息
				if(flag){
					//首先创建一行，然后rowIndex自增
					rowTemp=sheet.createRow(rowIndex++);
					//创建NO列
					PoiExcelUtil.setCell(rowTemp,CELL_0,No);
					//创建办事处名字
					PoiExcelUtil.setCell(rowTemp,CELL_1,dailyDpo.getRegionname());
					//创建办事处经理名字
					PoiExcelUtil.setCell(rowTemp,CELL_2,dailyDpo.getRegionuname());
					//创建经销商编号
					PoiExcelUtil.setCell(rowTemp,CELL_3,dailyDpo.getOutorganoecode());
					//创建经销商名字
					PoiExcelUtil.setCell(rowTemp,CELL_4,dailyDpo.getOutorganname());
					//创建主管编号
					PoiExcelUtil.setCell(rowTemp,CELL_5,dailyDpo.getSalemanid());
					//创建主管名字
					PoiExcelUtil.setCell(rowTemp,CELL_6,dailyDpo.getSalemanname());
					//创建网点编号
					PoiExcelUtil.setCell(rowTemp,CELL_7,dailyDpo.getInorganoecode());
					//创建网点名字
					PoiExcelUtil.setCell(rowTemp,CELL_8,dailyDpo.getInorganname());
					//创建产品编号
					PoiExcelUtil.setCell(rowTemp,CELL_9,dailyDpo.getProductid());
					//创建产品名称
					PoiExcelUtil.setCell(rowTemp,CELL_10,dailyDpo.getProductname());
					//创建产品价格
					PoiExcelUtil.setCell(rowTemp,CELL_11,dailyDpo.getPrice()==null?0.0:dailyDpo.getPrice());
					
					//设置信息置为false
					flag = false;
				}
				
				//一次填充每天销售量
				String everydate = DateUtil.formatDate(dailyDpo.getMakedate(),"dd");
				
				//填充每天价格
				PoiExcelUtil.setCell(rowTemp,(Integer.parseInt(everydate)+firstRowCells.length-1),dailyDpo.getPrice());
				
			}
			
		}
	}
	/**
	 * 写入公式
	 * 
	 */
	private void setProductSum(Sheet sheet,Map<String,LinkedHashMap<String,DailyProductOut>> dpoInfoMap,Date tempDate) {
		if(salemanInfo!=null && !salemanInfo.isEmpty()){
			boolean salemanFalg = true;
			boolean regionFalg = true;
			Cell cell = null;
			//sheet的最后行坐标
			Integer lastRow = sheet.getLastRowNum();
			//取得日报坐标轴开始位置与结束位置
			Date firstDate = DateUtil.StringToDate(DateUtil.getMonthFirstDay());
			Date lastDate =  tempDate;
			Integer dayCount = Dateutil.getDiffDays(firstDate, lastDate) + 1;
			//取得每一个主管开始于结束行信息
			Iterator<String> salemanIter = salemanInfo.keySet().iterator();
			while(salemanIter.hasNext()){
				//创建一行，最后行坐标加1
				Row row = sheet.createRow(++lastRow);
				
				String salenamKey = salemanIter.next();
				Integer[] rowNum = salemanInfo.get(salenamKey);
				
				//写入主管统计：
				if(salemanFalg){
					PoiExcelUtil.setCell(row,CELL_9,"主管统计：");
					salemanFalg = false;
				}
				
				//写入主管编号
				PoiExcelUtil.setCell(row,CELL_10,salenamKey.substring(salenamKey.lastIndexOf("_")+1));
				//写入主管名称
				PoiExcelUtil.setCell(row,CELL_11,salemanMap.get(salenamKey.substring(salenamKey.lastIndexOf("_")+1)));
				
				//循环坐标轴（每一天）
				for(int i=0;i<dayCount;i++){
					//汇总
					PoiExcelUtil.setCellFormula(row, (firstRowCells.length+i), "SUM(" + PoiExcelUtil.getPos((firstRowCells.length+i), rowNum[0])+ ":" + PoiExcelUtil.getPos((firstRowCells.length+i), rowNum[1]) + ")");
					//PoiExcelUtil.setCellFormula(row, (firstRowCells.length+i), "SUM(" + dailyRowCells[i] + rowNum[0] + ":" + dailyRowCells[i] + rowNum[1] + ")");
				}
			}
			
			//取得每一个办事处信息开始行与结束行
			Iterator<String> regionIter = regionInfo.keySet().iterator();
			while(regionIter.hasNext()){
				//创建一行，最后行坐标加1
				Row row = sheet.createRow(++lastRow);
				
				String regionKey = regionIter.next();
				Integer[] regionNum = regionInfo.get(regionKey);
				//写入办事处统计：
				if(regionFalg){
					PoiExcelUtil.setCell(row,CELL_9,"办事处统计：");
					regionFalg = false;
				}
				
				//写入办事处名称
				PoiExcelUtil.setCell(row,CELL_10,regionMap.get(regionKey).substring(0,regionMap.get(regionKey).lastIndexOf("_")));
				//写入办事处人名称
				PoiExcelUtil.setCell(row,CELL_11,regionMap.get(regionKey).substring(regionMap.get(regionKey).lastIndexOf("_")+1));
				//循环坐标轴（每一天）
				for(int i=0;i<dayCount;i++){
					//汇总
					PoiExcelUtil.setCellFormula(row, (firstRowCells.length+i), "SUM(" + PoiExcelUtil.getPos((firstRowCells.length+i), regionNum[0])+ ":" + PoiExcelUtil.getPos((firstRowCells.length+i), regionNum[1]) + ")");
					//PoiExcelUtil.setCellFormula(row, (firstRowCells.length+i), "SUM(" + dailyRowCells[i] + regionNum[0] + ":" + dailyRowCells[i] + regionNum[1] + ")");
				}
			}
			
		}
	}
}
