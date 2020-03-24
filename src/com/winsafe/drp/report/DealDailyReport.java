package com.winsafe.drp.report;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.winsafe.common.exception.CustomException;
import com.winsafe.common.util.FileUtil;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.report.dao.AppDailyReport;
import com.winsafe.drp.report.pojo.DailyReport;
import com.winsafe.drp.report.util.PoiExcelUtil;
import com.winsafe.drp.report.util.ReportUtil;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;

public class DealDailyReport{
	/**生成日期 */
	private Date date;
	/**报表文件名*/
	public static final String FILENAME="Daily_Report";
	
	private static Object alock=new Object();
	/**表头*/
	private static String[] firstRowCells = new String[]
	    {"大区","办事处","办事处经理","主管编号","主管名字","经销商编号","经销商","网点编号","网点","产品品类","产品编号","产品名",
		"当月指标","累计金额","累计达成率","预计达成率","当前日均销量","剩余天数目标金额","同比","环比"};
	/**表格起始行**/
	private static Integer first_row = 0;
	
	//与上表头字段对应
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
	private static final int CELL_16=16;
	private static final int CELL_17=17;
	private static final int CELL_18=18;
	private static final int CELL_19=19;
	
	/**存储当前月份有效天数列**/
	private static final int CELL_EFFECTIVE=61;
	/**存放大区进口指标**/
	private static final int CELL_REGIONPIMPORTTARGET=62;
	/**存放大区国产成人指标**/
	private static final int CELL_REGIONPCHMANTARGET=63;
	/**存放大区国产婴儿指标**/
	private static final int CELL_REGIONPCHBABYTARGET=64;
	/**存放大区总指标**/
	private static final int CELL_REGIONPTOTALTARGET=65;
	/**存放办事处进口指标**/
	private static final int CELL_REGIONIMPORTTARGET=66;
	/**存放办事处国产成人指标**/
	private static final int CELL_REGIONCHMANTARGET=67;
	/**存放办事处国产婴儿指标**/
	private static final int CELL_REGIONCHBABYTARGET=68;
	/**存放办事处总指标**/
	private static final int CELL_REGIONTOTALTARGET=69;
	/**存放经销商进口指标**/
	private static final int CELL_DEALERIMPORTTARGET=70;
	/**存放经销商国产成人指标**/
	private static final int CELL_DEALERCHMANTARGET=71;
	/**存放经销商国产婴儿指标**/
	private static final int CELL_DEALERCHBABYTARGET=72;
	/**存放经销商总指标**/
	private static final int CELL_DEALERTOTALTARGET=73;
	/**存放门店进口指标**/
	private static final int CELL_FRONTIMPORTTARGET=74;
	/**存放门店国产成人指标**/
	private static final int CELL_FRONTCHMANTARGET=75;
	/**存放门店国产婴儿指标**/
	private static final int CELL_FRONTCHBABYTARGET=76;
	
	/**存放门店总指标**/
	private static final int CELL_FRONTTOTALTARGET=77;
	/**存放主管进口指标**/
	private static final int CELL_SALEMANIMPORTTARGET=78;
	/**存放主管国产成人指标**/
	private static final int CELL_SALEMANCHMANTARGET=79;
	/**存放主管国产婴儿指标**/
	private static final int CELL_SALEMANCHBABYTARGET=80;
	/**存放主管总指标**/
	private static final int CELL_SALEMANTOTALTARGET=81;
	/**存放主管进口网点数**/
	private static final int CELL_SALEMANIMPORTCOUNT=82;
	/**存放门主管国产成人网点数**/
	private static final int CELL_SALEMANCHMANCOUNT=83;
	/**存放主管国产婴儿网点数**/
	private static final int CELL_SALEMANCHBABYCOUNT=84;
	/**存放主管总网点数**/
	private static final int CELL_SALEMANTOTALCOUNTT=85;
	
	
	/**工作簿路径 */
	private String filepath;
	private static String tempfilepath="D:\\daily_report.xlsm";
	/**日报App**/
	AppDailyReport appdr = new AppDailyReport();
	AppRegion appr = new AppRegion();
	/**封装日报信息**/
	Map<String,LinkedHashMap<String,DailyReport>> dailyReportMap = new LinkedHashMap<String, LinkedHashMap<String,DailyReport>>();
	/**当月天数**/
	private Integer currMonthDays = 0;
	private Integer currDays = 0;
	/**单元格样式**/
	private  DataFormat dateDataFormat;
	private CellStyle percentCellStyle;
	private CellStyle dateCellStyle ;
	private CellStyle textCellStyle;
	
	/**品类信息**/
	private static String IMPORT_SORTNAME="进口奶粉";
	private static String CHMAN_SORTNAME="国产成人粉";
	private static String CHBABY_SORTNAME="国产婴儿粉";
	
	/**冻结坐标轴*/
	private static Integer FREEZE_X=1;
	private static Integer FREEZE_Y=0;
	/**大区与办事处文件夹名称**/
	private static String bigRegion="日报\\大区";
	private static String region="日报\\办事处";
	
	/**存放所有报表路径信息***/
	public static List<String> filepathlist = new LinkedList<String>();
	
	/**
	 * 循环大区与办事处生成报表
	 */
	/*public void createDailyReport() throws Exception{
		//首先取出所有大区和办事处信息
		List<Region> regionList = appr.getAllRegion();
		if(regionList!=null && !regionList.isEmpty()){
			//Region r = null;
			for(Region r : regionList){
				//区域类型不存在则跳过（入光明总仓）
				if(r.getTypeid()==null || "".equals(r.getTypeid())){
					continue;
				}
				//首先清空全局变量信息
				filepath="";
				dailyReportMap.clear();
				//将返回的excel路径写入到全局变量中
				String filePath = generate(r);
				filepathlist.add(filePath);
			}
		}
	}*/
	//指定大区或办事处生成报表
	public boolean createDailyReport(Region r,Date currdate) throws Exception{
		boolean rsMsg = true;
		//如果传过来的信息有误则返回
		if(r==null || currdate==null){
			rsMsg = false;
			return rsMsg;
		}
		
		try {
			//首先清空全局变量信息
			filepath="";
			dailyReportMap.clear();
			//将返回的excel路径写入到全局变量中
			String filePath = generate(r,currdate);
			filepathlist.add(filePath);
		} catch (Exception e) {
			rsMsg = false;
			e.printStackTrace();
		}
		
		return rsMsg;
	}
	
	
	/**
	 * 生成报表 
	 * @return
	 * @throws Exception
	 */
	public String generate(Region r,Date currdate) throws Exception{
		synchronized (alock){
			try {
				Workbook wb = null;
				date = currdate;
				//date = DateUtil.getCurrentDate();
				//date = DateUtil.StringToDate("2013-12-29");
				//取日期为当前日期的前一天
				Date tempDate = DateUtil.addDay2Date(-1,date);
				DBUserLog.addUserLog(1, "生成产品日报报表开始");
				//获取工作簿
				wb=getDailyReport(tempDate,r);
				//组建基础
				buildDate(tempDate,r);
				//写数据
				writeData(wb,tempDate);
				
				//保存工作薄
				PoiExcelUtil.saveWorkbook(wb, filepath);
				
				DBUserLog.addUserLog(1, "生成产品日报报表结束");
			} catch (Exception e) {
				e.printStackTrace();
				DBUserLog.addUserLog(1, "生成产品日报报表失败");
			}
		}
		return filepath;
	}
	
	/**
	 * 获取日报
	 */
	private Workbook getDailyReport(Date date,Region r) throws Exception{
		Workbook wb;
		String fileName = FILENAME + "_" + r.getSortname() + "_" + DateUtil.formatDate(date,"yyyyMMdd") + ".xlsm";
		String filedirpath = ReportUtil.getReportDir();
		//大区文件夹
		if("1".equals(r.getTypeid())){
			filedirpath += bigRegion;
		}
		//办事处文件夹
		else if("2".equals(r.getTypeid())){
			filedirpath += region;
		}
		filedirpath += "\\" + r.getSortname() + "\\" + DateUtil.formatDate(date,"yyyyMM");
		filepath=filedirpath + "\\"+fileName;
		//首先判断文件夹是否存在，不存在新建
		File filedir = new File(filedirpath);
		if(!filedir.exists()){
			filedir.mkdirs();
		}
		//在判断文件是否存在，若存在删除原有文件，重新生成新文件
		File file=new File(filepath);
		if(file.exists()){
			file.delete();
		}
		//复制文件
		FileUtil.copyFile(tempfilepath, filepath);
		
		wb = genBaseReport(date,filepath);
		
		return wb;
	}
	
	/**
	 * 创建日报,并设置基本数据
	 */
	private Workbook genBaseReport(Date date,String filepath){
		//Workbook wb = null;
		//创建工作薄
		//wb = new XSSFWorkbook();
		Workbook wb = PoiExcelUtil.getWorkbook(filepath);
		//初始化相关样式
		initStyle(wb);
		//创建sheet
		wb.createSheet();
		return wb;
	}
	
	/**
	 * 初始化样式 
	 */
	private void initStyle(Workbook wb){
		dateDataFormat = wb.createDataFormat();
		/*//日期标题样式
		dateCellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		dateCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		dateCellStyle.setWrapText(true);*/
		//标题样式
		Font f =  wb.createFont();
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(f);
		//文本样式
		textCellStyle = wb.createCellStyle();
		textCellStyle.setDataFormat(dateDataFormat.getFormat("@"));
		
		//百分比样式
		percentCellStyle = wb.createCellStyle();
		percentCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
	}
	
	/**
	 * 组织数据
	 * @param tempdate
	 * @throws Exception
	 */
	private void buildDate(Date tempdate,Region r) throws Exception{
		if(r==null)
			return;
		String condition = " and dealdate<='" + DateUtil.formatDate(tempdate,"yyyyMMdd") + "'";
		//大区条件
		if("1".equals(r.getTypeid())){
			condition += " and regionpcode=" + r.getRegioncode();
		}
		//办事处条件
		else if("2".equals(r.getTypeid())){
			condition += " and regioncode=" + r.getRegioncode();
		}
		List<DailyReport> drList = appdr.getAllDailyReportByDealdate(DateUtil.formatDate(tempdate,"yyyyMM"),condition);
		if(drList!=null && !drList.isEmpty()){
			LinkedHashMap<String,DailyReport> newDailyInfo = null;
			LinkedHashMap<String,DailyReport> oldDailyInfo = null;
			//循环遍历每一条数据
			for(DailyReport dr : drList){
				/**
				 * 组织日报数据信息
				 * key : 大区_办事处_主管_经销商_网点_产品品类_产品
				 * value : DailyProductOut
				 */
				String key = dr.getRegionpid()+ "_" + dr.getRegionid() + "_" + dr.getSalemanid() + "_" + dr.getInparentorganid()
							+ "_" + dr.getInorganid() + "_" + dr.getProductStruct() + "_" + dr.getProductid();
				//如果存在则追加天销售量
				if(dailyReportMap.containsKey(key)){
					oldDailyInfo = dailyReportMap.get(key);
					oldDailyInfo.put(dr.getDealdate(), dr);
				}else{
					//不存在新建
					newDailyInfo = new LinkedHashMap<String, DailyReport>();
					newDailyInfo.put(dr.getDealdate(), dr);
					dailyReportMap.put(key, newDailyInfo);
				}
			}
		}
	}
	
	/**
	 * 写数据
	 * Create Time Apr 16, 2012 12:09:42 PM 
	 * @author huangxy
	 * @throws CustomException 
	 */
	private void writeData(Workbook wb,Date tempDate) throws Exception{
		Sheet sheet;
		//创建sheet
		sheet = wb.getSheetAt(0);
		//写入基础数据
		try {
			setCell(sheet,tempDate);
			//写入公式
			//setProductSum(sheet,dpoInfo,date);
		} catch (Exception e) {
			e.printStackTrace();
			DBUserLog.addUserLog(1, "写入数据失败");
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
	private void setCell(Sheet sheet,Date tempDate){
		/**
		 * 创建标题
		 */
		//设置窗口冻结
		sheet.createFreezePane(FREEZE_Y, FREEZE_X);
		Row row = sheet.createRow(first_row);
		//设置表头
		for(int j=0 ; j<firstRowCells.length ; j++){
			//设置列宽自动
			PoiExcelUtil.setCell(row, j, firstRowCells[j],dateCellStyle);
		}
		Date firstDate = DateUtil.StringToDate(DateUtil.getMonthFirstDay());
		Date lastDate = Dateutil.StringToDate(DateUtil.getMonthLastDay()); 
		//Date firstDate = DateUtil.StringToDate("2013-12-01");
		//Date lastDate = Dateutil.StringToDate("2013-12-31"); 
		Integer dayCount = Dateutil.getDiffDays(firstDate, lastDate) + 1;
		currMonthDays = dayCount;
		currDays = Dateutil.getDiffDays(firstDate, tempDate) + 1;
		
		for(int i=0 ; i<dayCount ; i++){
			//设置列宽自动
			PoiExcelUtil.setCell(row, firstRowCells.length+i,DateUtil.formatDate(Dateutil.addDay2Date(i, firstDate),"MM月dd日"),dateCellStyle);
		}
		//创建当前月份总天数列
		PoiExcelUtil.setCell(row, CELL_EFFECTIVE, currMonthDays);
		
		//隐藏
		sheet.setColumnHidden(CELL_EFFECTIVE, true);
		sheet.setColumnHidden(CELL_REGIONPIMPORTTARGET, true);
		sheet.setColumnHidden(CELL_REGIONPCHMANTARGET, true);
		sheet.setColumnHidden(CELL_REGIONPCHBABYTARGET, true);
		sheet.setColumnHidden(CELL_REGIONPTOTALTARGET, true);
		sheet.setColumnHidden(CELL_REGIONIMPORTTARGET, true);
		sheet.setColumnHidden(CELL_REGIONCHMANTARGET, true);
		sheet.setColumnHidden(CELL_REGIONCHBABYTARGET, true);
		sheet.setColumnHidden(CELL_REGIONTOTALTARGET, true);
		sheet.setColumnHidden(CELL_DEALERIMPORTTARGET, true);
		sheet.setColumnHidden(CELL_DEALERCHMANTARGET, true);
		sheet.setColumnHidden(CELL_DEALERCHBABYTARGET, true);
		sheet.setColumnHidden(CELL_DEALERTOTALTARGET, true);
		sheet.setColumnHidden(CELL_FRONTIMPORTTARGET, true);
		sheet.setColumnHidden(CELL_FRONTCHMANTARGET, true);
		sheet.setColumnHidden(CELL_FRONTCHBABYTARGET, true);
		sheet.setColumnHidden(CELL_FRONTTOTALTARGET, true);
		sheet.setColumnHidden(CELL_SALEMANIMPORTTARGET, true);
		sheet.setColumnHidden(CELL_SALEMANCHMANTARGET, true);
		sheet.setColumnHidden(CELL_SALEMANCHBABYTARGET, true);
		sheet.setColumnHidden(CELL_SALEMANTOTALTARGET, true);
		sheet.setColumnHidden(CELL_SALEMANIMPORTCOUNT, true);
		sheet.setColumnHidden(CELL_SALEMANCHMANCOUNT, true);
		sheet.setColumnHidden(CELL_SALEMANCHBABYCOUNT, true);
		sheet.setColumnHidden(CELL_SALEMANTOTALCOUNTT, true);
		
		/**
		 * 创建数据区
		 */
		Row rowTemp = null;
		//创建基本信息标志
		boolean flag = false;
		//行标志
		int rowIndex = first_row + 1;
		//日报列标志
		DailyReport dailyReport = null;
		//循环遍历dpoInfoMap
		Iterator<String> drIter = dailyReportMap.keySet().iterator();
		LinkedHashMap<String,DailyReport> drMap = null; 
		while(drIter.hasNext()){
			String key = drIter.next();
			drMap = dailyReportMap.get(key);
			//基本信息置为true
			flag = true;
			//循环遍历dailyReportMap
			Iterator<String> dailyItera = drMap.keySet().iterator();
			while(dailyItera.hasNext()){
				String dailyKey = dailyItera.next();
				dailyReport = drMap.get(dailyKey);
				//第一次循环添加ext基本信息
				if(flag){
					//首先创建一行，然后rowIndex自增
					rowTemp=sheet.createRow(rowIndex);
					//创建大区
					PoiExcelUtil.setCell(rowTemp,CELL_0,dailyReport.getRegionpname());
					//创建办事处名字
					PoiExcelUtil.setCell(rowTemp,CELL_1,dailyReport.getRegionname());
					//创建办事处经理名字
					PoiExcelUtil.setCell(rowTemp,CELL_2,dailyReport.getRegionuname());
					//创建主管编号
					PoiExcelUtil.setCell(rowTemp,CELL_3,dailyReport.getSalemancode(),textCellStyle);
					//创建主管名字
					PoiExcelUtil.setCell(rowTemp,CELL_4,dailyReport.getSalemanname());
					//创建经销商编号
					PoiExcelUtil.setCell(rowTemp,CELL_5,dailyReport.getInparentorganoecode(),textCellStyle);
					//创建经销商名字
					PoiExcelUtil.setCell(rowTemp,CELL_6,dailyReport.getInparentorganname());
					
					//创建网点编号
					PoiExcelUtil.setCell(rowTemp,CELL_7,dailyReport.getInorganoecode(),textCellStyle);
					//创建网点名字
					PoiExcelUtil.setCell(rowTemp,CELL_8,dailyReport.getInorganname());
					//创建产品品类
					PoiExcelUtil.setCell(rowTemp,CELL_9,dailyReport.getProductStruct());
					//创建产品编号
					PoiExcelUtil.setCell(rowTemp,CELL_10,dailyReport.getProductid(),textCellStyle);
					//创建产品名称
					PoiExcelUtil.setCell(rowTemp,CELL_11,dailyReport.getProductname());
					
					//创建网点当月指标
					//进口粉指标
					if(IMPORT_SORTNAME.equals(dailyReport.getProductStruct().trim())){
						PoiExcelUtil.setCell(rowTemp,CELL_12,dailyReport.getFrontimporttarget());
					}
					//国产成人粉
					else if(CHMAN_SORTNAME.equals(dailyReport.getProductStruct().trim())){
						PoiExcelUtil.setCell(rowTemp,CELL_12,dailyReport.getFrontchmantarget());
					}
					//国产婴儿粉
					else{
						PoiExcelUtil.setCell(rowTemp,CELL_12,dailyReport.getFrontchbabytarget());
					}
					
					/*//创建累计金额
					PoiExcelUtil.setCellFormula(rowTemp, CELL_13, "SUM(" + PoiExcelUtil.getPos(firstRowCells.length, rowIndex)+ ":" + PoiExcelUtil.getPos(firstRowCells.length+currMonthDays-1,rowIndex) + ")");*/
					//PoiExcelUtil.setCell(rowTemp,CELL_13,1);
					//创建累计达成率
					PoiExcelUtil.setCellFormula(rowTemp, CELL_14, "IF(" + PoiExcelUtil.getPos(CELL_12,rowIndex) + "=0,0," + PoiExcelUtil.getPos(CELL_13,rowIndex) + "/" + PoiExcelUtil.getPos(CELL_12,rowIndex) + ")");
					//设置百分比样式percentCellStyle
					rowTemp.getCell(CELL_14).setCellStyle(percentCellStyle);
					
					//预计达成率
					PoiExcelUtil.setCellFormula(rowTemp, CELL_15, "IF(" + PoiExcelUtil.getPos(CELL_12,rowIndex) + "=0,0," + PoiExcelUtil.getPos(CELL_13,rowIndex) + "/" + PoiExcelUtil.getPos(CELL_EFFECTIVE, rowIndex)
							+ "*" + PoiExcelUtil.getPos(CELL_EFFECTIVE, first_row)+ "/" + PoiExcelUtil.getPos(CELL_12,rowIndex) + ")");
					//设置百分比样式percentCellStyle
					rowTemp.getCell(CELL_15).setCellStyle(percentCellStyle);
					
					//创建当前日均销售量
					PoiExcelUtil.setCellFormula(rowTemp, CELL_16, PoiExcelUtil.getPos(CELL_13,rowIndex) + "/" 
							+  PoiExcelUtil.getPos(CELL_EFFECTIVE, rowIndex));
					//创建剩余天数目标金额
					PoiExcelUtil.setCellFormula(rowTemp, CELL_17, "IF((" + PoiExcelUtil.getPos(CELL_EFFECTIVE, first_row) + "-" + PoiExcelUtil.getPos(CELL_EFFECTIVE, rowIndex) + 
							")=0,0,(" + PoiExcelUtil.getPos(CELL_12,rowIndex) + "-" + PoiExcelUtil.getPos(CELL_13,rowIndex) + ")/(" +
							PoiExcelUtil.getPos(CELL_EFFECTIVE, first_row) + "-" + PoiExcelUtil.getPos(CELL_EFFECTIVE, rowIndex) + "))");
					
					//创建当前月份有效天数列
					PoiExcelUtil.setCell(rowTemp, CELL_EFFECTIVE, currDays);
					//创建大区进口指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONPIMPORTTARGET,dailyReport.getRegionpimporttarget());
					//创建大区国产成人指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONPCHMANTARGET,dailyReport.getRegionpchmantarget());
					//创建大区国产婴儿指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONPCHBABYTARGET,dailyReport.getRegionpchbabytarget());
					//创建大区总指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONPTOTALTARGET,dailyReport.getRegionptotaltarget());
					//创建办事处进口指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONIMPORTTARGET,dailyReport.getRegionimporttarget());
					//创建办事处国产成人指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONCHMANTARGET,dailyReport.getRegionchmantarget());
					//创建办事处国产婴儿指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONCHBABYTARGET,dailyReport.getRegionchbabytarget());
					//创建办事处总指标
					PoiExcelUtil.setCell(rowTemp,CELL_REGIONTOTALTARGET,dailyReport.getRegiontotaltarget());
					//创建经销商进口指标
					PoiExcelUtil.setCell(rowTemp,CELL_DEALERIMPORTTARGET,dailyReport.getDealerimporttarget());
					//创建经销商国产成人指标
					PoiExcelUtil.setCell(rowTemp,CELL_DEALERCHMANTARGET,dailyReport.getDealerchmantarget());
					//创建经销商国产婴儿指标
					PoiExcelUtil.setCell(rowTemp,CELL_DEALERCHBABYTARGET,dailyReport.getDealerchbabytarget());
					//创建经销商总指标
					PoiExcelUtil.setCell(rowTemp,CELL_DEALERTOTALTARGET,dailyReport.getDealertotaltarget());
					//创建门店进口指标
					PoiExcelUtil.setCell(rowTemp,CELL_FRONTIMPORTTARGET,dailyReport.getFrontimporttarget());
					//创建门店国产成人指标
					PoiExcelUtil.setCell(rowTemp,CELL_FRONTCHMANTARGET,dailyReport.getFrontchmantarget());
					//创建门店国产婴儿指标
					PoiExcelUtil.setCell(rowTemp,CELL_FRONTCHBABYTARGET,dailyReport.getFrontchbabytarget());
					//创建门店总指标
					PoiExcelUtil.setCell(rowTemp,CELL_FRONTTOTALTARGET,dailyReport.getFronttotaltarget());
					//创建主管进口指标
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANIMPORTTARGET,dailyReport.getSalemanimporttarget());
					//创建主管国产成人指标
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANCHMANTARGET,dailyReport.getSalemanchmantarget());
					//创建主管国产婴儿指标
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANCHBABYTARGET,dailyReport.getSalemanchbabytarget());
					//创建主管总指标
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANTOTALTARGET,dailyReport.getSalemantotaltarget());
					//创建主管进口网点数
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANIMPORTCOUNT,dailyReport.getSalemanimportcount());
					//创建主管国产成人网点数
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANCHMANCOUNT,dailyReport.getSalemanchmancount());
					//创建主管国产婴儿网点数
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANCHBABYCOUNT,dailyReport.getSalemanchbabycount());
					//创建主管总网点数
					PoiExcelUtil.setCell(rowTemp,CELL_SALEMANTOTALCOUNTT,dailyReport.getSalemantotalcount());
					//设置信息置为false
					flag = false;
					//行数加1
					rowIndex++;
				}
				
				//一次填充每天销售量
				//String everydate = DateUtil.formatDate(dailyReport.getMakedate(),"dd");
				//取得天数
				String everydate = dailyReport.getDealdate().substring(6);
				
				//填充每天价格
				PoiExcelUtil.setCell(rowTemp,(Integer.parseInt(everydate)+firstRowCells.length-1),dailyReport.getPrice());
				
			}
			
		}
	}

}
