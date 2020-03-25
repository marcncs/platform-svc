package com.winsafe.drp.action.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;

import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 主要功能:产成品入库
 * @author RichieYu
 * @version 20100510 Update By WenPing JUN 26
 */
public class AppRecordNew {
	private Logger logger = Logger.getLogger(AppRecordNew.class);
	//ORACLE数据库时间格式
	private static final String ORACLE_DATE_FORMAT = "yyyy-mm-dd hh24:mi:ss";
	
	private static final String accessDBPwd= "WinSafe%%304#&305#";
	
	/** 产品集合缓存*/
	private Map<String, Product> proMap ;
	/** 产品前缀集合缓存*/
	private Map<String, ICode> icodeMap;
	

	
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
	//产品前缀Dao
	private AppICode ai = new AppICode();
	//拼SQL
	private StringBuffer sqlBuffer = null;
	/**
	 * 获取mdb数据库
	 * 此连接方式不使用odbc桥,不受windows平台的限制
	 * 支持2000-2010
	 * 2003之前的版本,不使用密码,也可打开加密的文件
	 * Create Time 2014-4-24 下午01:34:31 
	 * @param filename
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	private Database getAccessDB(String filename) throws Exception {
		File dbFile = new File(filename);
		Database database = null;
		if(dbFile.exists()){
			database = new DatabaseBuilder(dbFile).setCodecProvider(new CryptCodecProvider(accessDBPwd)).open();
		}
		return database;
	}

	/**
	 * 缓存
	 * @throws Exception
	 */
	public void cacheMap() throws Exception{
		getProduct4Map();
		getICode4Map();
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
	public String getRecord(String filePath, Integer logid, String warehouseid, String logFilePath
			) throws Exception {
		cacheMap();
		// 数据库连接
		Database db = null;
		Connection oralceConn = null;
		String info = null;
		String errorinfo = null;
		Table record = null;
		
		try {
//			//生成日志记录文件
			this.logFilePath = logFilePath;
			// 得到数据库
			db = getAccessDB(filePath);
			if(db == null){
				throw new Exception("access数据文件不存在");
			}
			//得到表中的数据
			record = db.getTable("Record");
			if(record == null){
				throw new Exception("数据文件中的record不存在");
			}
			oralceConn = HibernateUtil.currentSession(true).connection();
			// 更新日志(状态为处理中)
			updateDeal(logid, 1);
			// 新增生产上传
			errorinfo = this.uploadProductReport(oralceConn, record, warehouseid);
			if (errorinfo != null) {
				return errorinfo;
			}
		} catch (Exception e) {
			info = "文件处理失败，请检查文件内容";
			logger.error("", e);
			// 如有异常回滚事物
			HibernateUtil.rollbackTransaction();
		} finally {
			//关闭数据文件
			if(db != null){
				db.close();
			}
			if(oralceConn != null){
				try {
					oralceConn.close();
				} catch (SQLException e) {
					logger.error("", e);
				}
			}
		}
		// 更新日志(状态为处理完)
		updateDeal(logid, 2);
		return info;
	}
	

	/**
	 * 主要功能:上传生产数据类型
	 * 
	 * @param rs
	 * @throws Exception
	 */
	private String uploadProductReport(Connection conn,Table table, String warehouseid) throws Exception {
		//记录总数
		Integer ID = null;
		sqlBuffer = new StringBuffer();
		sqlBuffer.append("BEGIN \r\n");
		
		int i = 0;
		// 循环遍历上传的数据集合
//		System.out.println("循环开始:"+new Date());
		for(Row row : table){
			if(row == null) continue;

			try {
				ID = (Integer) row.get("ID");
				// 得到物流码
				String proCode = (String) row.get(LABEL_PROCODE);
				String rectype = (String) row.get(LABEL_RECTYPE);
				if(!"N".equals(rectype)){
					continue;
				}
				// 新增生产记录 拼SQL
				this.addProductReport(ID, row, proCode, rectype, warehouseid);
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
				if(sqlBuffer.length()<10){
					continue;
				}
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
//		System.out.println("循环结束:"+new Date());
		return null;
	}
	
	
	

	/**
	 * 主要功能:新增生产上传
	 * @param i  行号  MDB中的ID
	 * @param row 
	 * @param proCode
	 * @param recType
	 * @param warehouseid
	 * @throws Exception
	 */
	private void addProductReport(int i,Row row, String proCode, String recType, String warehouseid)
			throws Exception {
		String cartonCode = (String) row.get(LABEL_CARTONCODE);
		String boxCode = (String) row.get(LABEL_BOXCODE);
		String packCode = (String) row.get(LABEL_PACKCODE);
		String productid = (String) row.get(LABEL_PROID);
		//验证码是否正确
		String flag = checkCode(i,productid,packCode,boxCode,cartonCode);
		if(flag.equals("true")){
			//如有错误，则该条记录不入库
			errorCount++;
			return;
		}
		sqlBuffer.append("INSERT INTO UPLOAD_PRODUCE_REPORT(");
		sqlBuffer.append("PROID, PRONAME, PRODT, PRORULE, ITEMCODE, LOTNO, LINENO, PROCODE,");
		sqlBuffer.append("PACKCODE, BOXCODE, CARTONCODE, PALLETCODE, RECTYPE, REMARK,MAKEDATE,OPTTIME,");
		sqlBuffer.append("WAREHOUSEID, ISINCOME, NCLOTNO)SELECT ");
		//PROID 产品编码
		sqlBuffer.append("'"+productid+"',");
		//PRONAME 产品名称
		sqlBuffer.append("'"+ flag +"',");
		//PRODT 生产记录日期
		String proDt = (String) row.get(LABEL_PRODT);
		sqlBuffer.append("TO_DATE('"+proDt+"','"+ORACLE_DATE_FORMAT+"'),");
		//PRORULE 
		sqlBuffer.append("'"+row.get(LABEL_PRORULE)+"',");
		//ITEMCODE
		sqlBuffer.append("'"+row.get(LABEL_ITEMCODE)+"',");
		//LOTNO  IS系统批次，为了更好的拣货  格式为年月日:20140416   
		sqlBuffer.append("'"+proDt.replace("-", "").replace("/", "").replace(" ", "").replace(":", "").substring(0, 8)+"',");
		//LINENO 
		sqlBuffer.append("'"+row.get(LABEL_LINENO)+"',");
		//PROCODE 
		sqlBuffer.append("'"+proCode+"',"); 
		//PACKCODE 
		
		sqlBuffer.append("'"+packCode+"',");
		//BOXCODE 
		sqlBuffer.append("'"+boxCode+"',");
		//CARTONCODE
		sqlBuffer.append("'"+cartonCode+"',");
		//PALLETCODE
		sqlBuffer.append("'"+row.get(LABEL_PALLETCODE)+"',");
		//RECTYPE 
		sqlBuffer.append("'"+recType+"',");
		//REMARK 
		sqlBuffer.append("'"+row.get(LABEL_REMARK)+"',");
		//MAKEDATE 
		sqlBuffer.append("TO_DATE('"+DateUtil.getCurrentDateTime()+"','"+ORACLE_DATE_FORMAT+"'),");
		//OPTTIME 
		sqlBuffer.append("TO_DATE('"+DateUtil.formatDateTime((Date)row.get(LABEL_OPTTIME))+"','"+ORACLE_DATE_FORMAT+"'),");
		//WAREHOUSEID 入库仓库
		sqlBuffer.append("'"+warehouseid+"',");
		//ISINCOME
		sqlBuffer.append("'0',");
		//NCLOTNO  实际批次
		sqlBuffer.append("'"+row.get(LABEL_LOTNO)+"'");
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
	public String checkCode(int i,String productid, String packCode, String boxCode, String cartonCode) throws Exception{
		String flag = "false";
		Product p = proMap.get(productid);
		if(p == null){
			writeErrorLogToTxt("错误行数："+i+"，错误内容：产品编号："+productid+"不存在!");
			flag = "true";
			//如果产品都没有，直接返回
			return flag;
		}else{
			flag = p.getProductname();
		}
		//验证小盒码 返回null则没有错误
		String checkPack = checkPakcCode(p, packCode); 
		//验证中盒码  返回null则没有错误
		String checkBox = checkBoxCode(p,boxCode);
		//验证外箱码  返回null则没有错误
		String checkCarton = checkCartonCode(p,cartonCode);
		
		if(!StringUtil.isEmpty(checkPack)){
			//如果不为null，则有错误 将错误写入txt文件中
			writeErrorLogToTxt("错误行数："+i+"，错误内容："+checkPack);
			flag = "true";
		}
		if(!StringUtil.isEmpty(checkBox)){
			//如果不为null 则有错误，将错误写入txt文件中
			writeErrorLogToTxt("错误行数："+i+"，错误内容："+checkBox);
			flag = "true";
		}
		if(!StringUtil.isEmpty(checkCarton)){
			//外箱码有错误则不为null 写入日志中
			writeErrorLogToTxt("错误行数："+i+"，错误内容："+checkCarton);
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
	public String checkPakcCode(Product p,String packCode) throws Exception{
		if(packCode.length()!=PACKCODE_LENGTH){
			return "小盒码-数量长度错误！";
		}
		String icode = packCode.substring(1, 5);
		ICode i = icodeMap.get(icode);
		if(i==null){
			return "小盒码-系统产品码不存在！";
		}
		if(!p.getId().equals(i.getProductid())){
			return "小盒码-系统产品码不匹配！";
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
		ICode i = icodeMap.get(icode);
		int index = boxCode.indexOf("H");
		if(index!=0){
			return "中盒码-第一位标识错误！";
		}
		if(i==null){
			return "中盒码-系统产品码不存在！";
		}
		if(!p.getId().equals(i.getProductid())){
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
		ICode i = icodeMap.get(icode);
		int index = cartonCode.indexOf("X");
		if(index!=0){
			return "外箱码-第一位标识错误！";
		}
		if(i==null){
			return "外箱码-系统产品码不存在！";
		}
		if(!p.getId().equals(i.getProductid())){
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
	
	
	
	
	
	
	/**
	 * 获取所有的产品，用于缓存
	 * @throws Exception
	 */
	public void getProduct4Map() throws Exception{
		proMap = new HashMap<String, Product>();
		List<Product> list = ap.getProductAll();
		for (Product p : list) {
			proMap.put(p.getId(), p);
		}
	}
	
	public void getICode4Map() throws Exception {
		icodeMap=new HashMap<String, ICode>();
		List<ICode> list = ai.getICodeAll();
		for (ICode i : list) {
			icodeMap.put(i.getLcode(), i);
		}
	}
	
	
	

	
}
