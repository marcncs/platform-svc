package com.winsafe.drp.action.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;

import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.hbm.entity.HibernateUtil;

/**
 * 预生产报表上传
 */
public class AppPreRecord {
	private Logger logger = Logger.getLogger(AppPreRecord.class);
	//access连接数据库连接字符
	private static final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private static final String accessDBURLSuffix = ";pwd=WinSafe%%304#&305#;useUnicode=false;characterEncoding=gbk";

//	//产品编号
//	private static final String LABEL_PRODUCT_ID = "ProID";
//	//生产日期
//	private static final String LABEL_PRODUCT_DATE = "ProDT";
	//分装日期
	private static final String LABEL_PKGDATE = "PackDate";
//	//批号
//	private static final String LABEL_LOTNO = "LotNo";
//	//产线编号
//	private static final String LABEL_LINENo = "LineNo";
	//物流码
	private static final String LABEL_UNIT_NO = "ProCode";	
	//外箱物流编码
//	private static final String LABEL_BOX_CODE = "CartonCode";
//	//积分（防伪）编码
//	private static final String LABEL_P_NUM = "Points_Number";	
//	//生产记录时间
//	private static final String LABEL_PRCDATE = "OptTime";
//	//批次内外箱的顺序号
//	private static final String LABEL_CARTON_NO = "CartonNo";
//	//虚拟托盘编码
//	private static final String LABEL_PALLET_NO = "PalletNo";
	//生产记录的类型
	private static final String LABEL_RECTYPE = "RecType";
	
	
	private Connection getAccessDBConnection(String filename) throws Exception {

		String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;

		return DriverManager.getConnection(databaseURL, "", "");
	}

	//生产数据上传dao
	private AppUploadProduceReport appUploadProduceReport = new AppUploadProduceReport();
	/**
	 * 主要功能:上传生产数据
	 * @param filePath 文件临时存放路径
	 * @param logid 生产日志ID
	 * @throws Exception
	 */
	public String getRecord(String filePath, Integer logid)  throws Exception {
		// 数据库连接
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		String info=null;
		try {
			// 得到数据库连接
			con = getAccessDBConnection(filePath);
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			// 从access中读出数据
			rs = statement.executeQuery("select * from Record");
			HibernateUtil.currentSession(true);
			// 更新日志(状态为处理中)
			updateDeal(logid, 1);
			// 预生产上传
			this.uploadProductReport(rs);
			// 更新日志(状态为处理完)
			updateDeal(logid, 2);
			// 关闭数据库连接
//			rs.close();
//			statement.close();
//			System.out.println("预生产报表处理完毕");
		}catch (Exception e) {
			info= "文件处理失败，请检查文件内容";

			logger.error("", e);
			//如有异常回滚事物
			HibernateUtil.rollbackTransaction();
		} finally {
			//关闭hibernate连接
		//	HibernateUtil.closeSession();
			//关闭access数据库连接
			if(rs!=null){
				try {
					rs.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			
			if(statement!=null){
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
		}
		return info;
	} 
	
	/**
	 * 主要功能:上传生产数据类型
	 * @param rs
	 * @throws Exception 
	 */
	private String uploadProductReport(ResultSet rs) throws Exception{
//		int sum = 0;
		//循环遍历上传的数据集合
		while (rs.next()) {
			try {
			//得到物流码
			String unitNo = rs.getString(LABEL_UNIT_NO);
			String rectype = rs.getString(LABEL_RECTYPE );
			if(rectype.equals("Y")){
				//System.out.println("y");
				/*//根据物流码查询数据库是否存在
				UploadProduceReport uploadProduceReport = appUploadProduceReport.getUploadProduceReportByUnitNo(unitNo);
				if(uploadProduceReport!=null){
					System.out.println("exist");
					uploadProduceReport.setPrcdate(rs.getString(LABEL_PKGDATE));
					appUploadProduceReport.update(uploadProduceReport);
				}*/
				
				String sql = "update upload_produce_report set PrcDate='"+rs.getString(LABEL_PKGDATE)+"' , recType_y='Y' where UnitNo='"+unitNo+"'";
				
				appUploadProduceReport.update(sql);
//				if(n==1){
//					sum++;
//				}
				
			 }
	     } catch (Exception e) {
				//[Microsoft][ODBC Microsoft Access Driver] 无效的书签
				continue;
			}
		}
//		System.out.println("共更新："+ sum);
		
//		//游标回到初始化
//		rs.beforeFirst();
		return null;
	}
	
	/**
	 * 主要功能:更新生产上传日志
	 * @param logid 登陆者id
	 * @param isdeal 是否处理标识符
	 */
	public void updateDeal(Integer logid, Integer isdeal) {
		try {
			HibernateUtil.currentTransaction();
			RecordDao rDao = new RecordDao();
			rDao.updateDeal(logid, isdeal);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("", e);
			HibernateUtil.rollbackTransaction();
		} finally {
//			HibernateUtil.closeSession();
		}
	} 
}
