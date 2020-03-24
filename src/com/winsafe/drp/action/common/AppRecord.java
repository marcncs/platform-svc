package com.winsafe.drp.action.common;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.machin.UploadProduceReportAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.cache.SysOSCache;

/**
 * 主要功能:产成品入库
 * 
 * @author RichieYu
 * @version 20100510 Update By WenPing JUN 26
 */
public class AppRecord {
	private Logger logger = Logger.getLogger(AppRecord.class);
	// access连接数据库连接字符
	private static final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
	private static final String accessDBURLSuffix = ";pwd=WinSafe%%304#&305#;useUnicode=false;characterEncoding=gbk";
	//ORACLE数据库时间格式
	private static final String ORACLE_DATE_FORMAT = "yyyy-mm-dd hh24:mi:ss";
	
	private static final String LABEL_PROID = "ProID";
	
	private static final String LABEL_PRONAME = "ProName";
	private static final String LABEL_PRORULE = "ProRule";
	private static final String LABEL_PRODT = "ProDT";
	private static final String LABEL_ITEMCODE = "ItemCode";
	private static final String LABEL_LOTNO = "LotNo";
	private static final String LABEL_LINENO = "LineNo";
	private static final String LABEL_PROCODE = "ProCode";
	private static final String LABEL_PACKCODE = "PackCode";
	private static final String LABEL_BOXCODE = "BoxCode";
	private static final String LABEL_CARTONCODE = "CartonCode";
	private static final String LABEL_PALLETCODE = "PalletCode";
	private static final String LABEL_RECTYPE = "RecType";
	private static final String LABEL_REMARK = "Remark";
	private static final String LABEL_OPTTIME = "OptTime";
	private static final int PACKCODE_LENGTH = 16;
	private static final int BOXCODE_LENGTH = 28;
	private static final int CARTONCODE_LENGTH = 28;
	
	private int errorCount = 0;
	private int successCount = 0;
	
	private String logFilePath = "";
	
	UploadProduceReport uploadProduceReport = new UploadProduceReport();
	ProductIncomeIdcode productIncomeIdcode = new ProductIncomeIdcode();
	Product product = null;
	// 生产数据上传dao
	private AppUploadProduceReport appUploadProduceReport = new AppUploadProduceReport();
	//产品Dao
	private AppProduct ap = new AppProduct();
	//拼SQL
	private StringBuffer sqlBuffer = null;
	
	private Connection getAccessDBConnection(String filename) throws Exception {
		//设置编码获取中文
		Properties p = new Properties();
		p.setProperty("charSet", "gbk");
		String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;
		return DriverManager.getConnection(databaseURL, p);
	}

	
	/**
	 * 主要功能:上传生产数据
	 * 
	 * @param filePath
	 *            文件临时存放路径
	 * @param logid
	 *            生产日志ID
	 * @param userid
	 *            当前登陆者ID
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public String getRecord(String filePath, Integer logid, String warehouseid
			) throws Exception {
		// 数据库连接
		Connection con = null;
		Connection oralceConn = null;
		Statement statement = null;
		ResultSet rs = null;
		String info = null;
		String errorinfo = null;
		
		//生成日志记录文件
		String time = DateUtil.getCurrentDateTimeString();
		logFilePath = filePath.substring(0,filePath.lastIndexOf("_"))+"_LOG"+time+".txt";
		try {
			// 得到数据库连接
			con = getAccessDBConnection(filePath);
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// 从access中读出数据
			rs = statement.executeQuery("select * from Record");
			oralceConn = HibernateUtil.currentSession(true).connection();
			// 更新日志(状态为处理中)
			updateDeal(logid, 1);
			// 新增生产上传
			errorinfo = this.uploadProductReport(oralceConn, rs, warehouseid);
			if (errorinfo != null) {
				return errorinfo;
			}
			/*
			 * 关闭产成品入库单，改为定时任务，每日24时启动，根据当天的所有产品生成一张产成品入库单 UpdateDate 2014-03-31
			 * @author Andy.liu
			 */
			
			
			
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			info = "文件处理失败，请检查文件内容";
			logger.error("", e);
			// 如有异常回滚事物
			HibernateUtil.rollbackTransaction();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.error("", e);
				}
			}
			if(oralceConn != null){
				try {
					oralceConn.close();
				} catch (SQLException e) {
					logger.error("", e);
				}
			}
		}
		
		// 更新日志(状态为处理完) 并添加日志错误信息
		updateDeal(logid, 2);
		return info;
	}
	

	/**
	 * 主要功能:上传生产数据类型
	 * 
	 * @param rs
	 * @throws Exception
	 */
	private String uploadProductReport(Connection conn,ResultSet rs, String warehouseid) throws Exception {
		String ID = null;
		// 循环遍历上传的数据集合
		sqlBuffer = new StringBuffer();
		int i = 0;
		System.out.println(new Date());
		sqlBuffer.append("BEGIN \r\n");
		while(rs.next()) {
			try {
				ID = rs.getString("ID");
				// 得到物流码
				String proCode = rs.getString(LABEL_PROCODE);
				String rectype = rs.getString(LABEL_RECTYPE);
				if("R".equals(rectype)){
					continue;
				}
				// 新增生产记录 拼SQL
				this.addProductReport(rs, proCode, rectype, warehouseid);
				i++;
			} catch (SQLGrammarException e) {
				e.printStackTrace();
				return "上传失败！<br/>当前文件中行号为【" + ID
						+ "】的记录中<br/>产品(二维码)物流编码(列名为ProCode)已存在系统中";
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
			//拼完3条就插入
			if(i != 0&&i % 3 == 0){
				sqlBuffer.append("END;");
				appUploadProduceReport.insertRecord(conn,sqlBuffer.toString());;
				//清空数据
				sqlBuffer = null;
				sqlBuffer = new StringBuffer();
				sqlBuffer.append("BEGIN \r\n");
			}
			
		}
		successCount = i - errorCount;
		//循环完后不足的继续插入
		if(sqlBuffer.length()>8){
			sqlBuffer.append("END;");
			appUploadProduceReport.insertRecord(conn,sqlBuffer.toString());;
			sqlBuffer = null;
		}
		
		
		System.out.println(new Date());
//		// 游标回到初始化
//		rs.beforeFirst();
		return null;
	}
	
	
	

	/**
	 * 主要功能:新增生产上传
	 * 
	 * @param uploadProduceReport
	 *            生产上传对象
	 * @param rs
	 *            access数据
	 * @throws Exception
	 */
	// Set<String> unitNoSet = new HashSet<String>();
	private void addProductReport(ResultSet rs, String proCode, String recType, String warehouseid)
			throws Exception {
		String cartonCode = rs.getString(LABEL_CARTONCODE);
		String boxCode = rs.getString(LABEL_BOXCODE);
		String packCode = rs.getString(LABEL_PACKCODE);
		String productid = rs.getString(LABEL_PROID);
		//验证码是否正确
		String flag = checkCode(rs,productid,packCode,boxCode,cartonCode);
		if(flag.equals("true")){
			//如有错误，则该条记录不入库
			errorCount++;
			return;
		}
//		Product p = ap.getProductByID(productid);
//		String flag = p.getProductname();
		sqlBuffer.append("INSERT INTO UPLOAD_PRODUCE_REPORT(");
		sqlBuffer.append("PROID, PRONAME, PRODT, PRORULE, ITEMCODE, LOTNO, LINENO, PROCODE,");
		sqlBuffer.append("PACKCODE, BOXCODE, CARTONCODE, PALLETCODE, RECTYPE, REMARK,MAKEDATE,OPTTIME,");
		sqlBuffer.append("WAREHOUSEID, ISINCOME, NCLOTNO)SELECT ");
		//PROID 产品编码
		sqlBuffer.append("'"+productid+"',");
		//PRONAME 产品名称
		sqlBuffer.append("'"+ flag +"',");
		//PRODT 生产记录日期
		String proDt = rs.getString(LABEL_PRODT);
		sqlBuffer.append("TO_DATE('"+proDt+"','"+ORACLE_DATE_FORMAT+"'),");
		//PRORULE 
		sqlBuffer.append("'"+rs.getString(LABEL_PRORULE)+"',");
		//ITEMCODE
		sqlBuffer.append("'"+rs.getString(LABEL_ITEMCODE)+"',");
		//LOTNO  IS系统批次，为了更好的拣货  格式为年月日:20140416   
		sqlBuffer.append("'"+proDt.replace("-", "").replace("/", "").replace(" ", "").replace(":", "").substring(0, 8)+"',");
		//LINENO 
		sqlBuffer.append("'"+rs.getString(LABEL_LINENO)+"',");
		//PROCODE 
		sqlBuffer.append("'"+proCode+"',"); 
		//PACKCODE 
		
		sqlBuffer.append("'"+packCode+"',");
		//BOXCODE 
		sqlBuffer.append("'"+boxCode+"',");
		//CARTONCODE
		sqlBuffer.append("'"+cartonCode+"',");
		//PALLETCODE
		sqlBuffer.append("'"+rs.getString(LABEL_PALLETCODE)+"',");
		//RECTYPE 
		sqlBuffer.append("'"+recType+"',");
		//REMARK 
		sqlBuffer.append("'"+rs.getString(LABEL_REMARK)+"',");
		//MAKEDATE 
		sqlBuffer.append("TO_DATE('"+DateUtil.getCurrentDateTime()+"','"+ORACLE_DATE_FORMAT+"'),");
		//OPTTIME 
		sqlBuffer.append("TO_DATE('"+rs.getString(LABEL_OPTTIME)+"','"+ORACLE_DATE_FORMAT+"'),");
		//WAREHOUSEID 入库仓库
		sqlBuffer.append("'"+warehouseid+"',");
		//ISINCOME
		sqlBuffer.append("'0',");
		//NCLOTNO  实际批次
		sqlBuffer.append("'"+rs.getString(LABEL_LOTNO)+"'");
		sqlBuffer.append(" FROM DUAL WHERE ");
		sqlBuffer.append(" NOT EXISTS (SELECT ID FROM UPLOAD_PRODUCE_REPORT WHERE  PACKCODE = '"+packCode+"'); \r\n");
	}
	
	

	/**
	 * 主要功能:更新生产上传日志
	 * 
	 * @param logid
	 *            登陆者id
	 * @param isdeal
	 *            是否处理标识符
	 */
	public void updateDeal(Integer logid, Integer isdeal) {
		try {
			HibernateUtil.currentTransaction();
			RecordDao rDao = new RecordDao();
			//修改日志状态
			rDao.updateDeal(logid, isdeal);
			//添加日志错误信息
			
			rDao.updateLog(logid, logFilePath, errorCount);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("", e);
			HibernateUtil.rollbackTransaction();
		} finally {
			// HibernateUtil.closeSession();
		}
	}
	
	
	/**
	 * 产品编码验证： 产品编号是否存在于数据库中，并返回产品名称
	 * 小盒码验证：位数
	 * 中盒码验证、外箱码验证：位数、第一位标识、产品前缀
	 * Create Time 2014-04-18 下午16:20 
	 * @author Andy.liu
	 * @return 如果没有错误，则返回产品名称productName，如果有错误则返回的是"true" String类型
	 */
	public String checkCode(ResultSet rs ,String productid, String packCode, String boxCode, String cartonCode) throws Exception{
		String flag = "false";
		Product p = ap.getProductByID(productid);
		if(p == null){
			writeErrorLogToTxt("错误行数："+rs.getRow()+"，错误内容：产品编号："+productid+"不存在!");
			flag = "true";
			//如果产品都没有，直接返回
			return flag;
		}else{
			flag = p.getProductname();
		}
		
		//验证小盒码 返回null则没有错误
		String checkPack = checkPakcCode(packCode); 
		//验证中盒码  返回null则没有错误
		String checkBox = checkBoxCode(p,boxCode);
		//验证外箱码  返回null则没有错误
		String checkCarton = checkCartonCode(p,cartonCode);
		
		if(!StringUtil.isEmpty(checkPack)){
			//如果不为null，则有错误 将错误写入txt文件中
			writeErrorLogToTxt("错误行数："+rs.getRow()+"，错误内容："+checkPack);
			flag = "true";
		}
		if(!StringUtil.isEmpty(checkBox)){
			//如果不为null 则有错误，将错误写入txt文件中
			writeErrorLogToTxt("错误行数："+rs.getRow()+"，错误内容："+checkBox);
			flag = "true";
		}
		if(!StringUtil.isEmpty(checkCarton)){
			//外箱码有错误则不为null 写入日志中
			writeErrorLogToTxt("错误行数："+rs.getRow()+"，错误内容："+checkCarton);
			flag = "true";
		}		
		
		return flag;
	}
	
	/**
	 * 小盒验证：
	 * 只包含位数
	 * Create Time 2014-04-18 下午16:30
	 * @param packCode
	 * @return null 无错误
	 * @throws Exception
	 */
	public String checkPakcCode(String packCode) throws Exception{
		if(packCode.length()!=PACKCODE_LENGTH){
			return "小盒码-数量长度错误！";
		}
		return null;
	}
	
	/**
	 * 中盒验证:
	 * 包括码位数、第一位标识、产品前缀
	 * Create Time 2014-04-18 下午16:30
	 * @return null 无错误
	 * @throws Exception
	 */
	public String checkBoxCode(Product p, String boxCode) throws Exception{
		String icode = boxCode.substring(1, 5);
		String id = ap.getProductByICode1(icode);
		int index = boxCode.indexOf("H");
		if(index!=0){
			return "中盒码-第一位标识错误！";
		}
		if(StringUtil.isEmpty(id)){
			return "中盒码-系统产品码不存在！";
		}
		if(p.getProductname().equals(id)){
			return "中盒码-系统产品码不匹配！";
		}
		if(boxCode.length()!=BOXCODE_LENGTH){
			return "中盒码-长度错误！";
		}
		return null;
	} 
	
	/**
	 * 外箱验证：
	 * 包括码位数、第一位标识、产品前缀
	 * Create Time 2014-04-18 下午16:30
	 * @return null 无错误
	 * @throws Exception
	 */
	public String checkCartonCode(Product p, String cartonCode) throws Exception{
		String icode = cartonCode.substring(1, 5);
		String id = ap.getProductByICode1(icode);
		int index = cartonCode.indexOf("X");
		if(index!=0){
			return "外箱码-第一位标识错误！";
		}
		if(StringUtil.isEmpty(id)){
			return "外箱码-系统产品码不存在！";
		}
		if(p.getProductname().equals(id)){
			return "外箱码-系统产品码不匹配！";
		}
		if(cartonCode.length()!=CARTONCODE_LENGTH){
			return "外箱码-长度错误！";
		}
		return null;
	} 
	
	
	/**
	 * 写日志
	 * 一条日志写一行
	 * @param errorLog
	 * @throws Exception
	 */
	public void writeErrorLogToTxt(String errorLog) throws Exception{
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(logFilePath, true)));
			out.write(errorLog);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	
	

	
}
