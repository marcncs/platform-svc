package com.winsafe.drp.action.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.SQLGrammarException;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 主要功能:产成品入库
 * @author RichieYu
 * @version 20100510 
 * Update By WenPing JUN 26
 */
public class AppReloadRecord {
	private Logger logger = Logger.getLogger(AppRecord.class);
	//access连接数据库连接字符
	private static final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private static final String accessDBURLSuffix = ";pwd=WinSafe%%304#&305#;useUnicode=false;characterEncoding=gbk";

	//产品编号
	private static final String LABEL_PRODUCT_ID = "ProID";
	//生产日期
	private static final String LABEL_PRODUCT_DATE = "ProDT";
	//分装日期
	private static final String LABEL_PKGDATE = "PackDate";
	//批号
	private static final String LABEL_LOTNO = "LotNo";
	//产线编号
	private static final String LABEL_LINENo = "LineNo";
	//物流码
	private static final String LABEL_UNIT_NO = "ProCode";	
	//外箱物流编码
	private static final String LABEL_BOX_CODE = "CartonCode";
	//积分（防伪）编码
	private static final String LABEL_P_NUM = "Points_Number";	
	//生产记录时间
	private static final String LABEL_PRCDATE = "OptTime";
	//批次内外箱的顺序号
	private static final String LABEL_CARTON_NO = "CartonNo";
	//虚拟托盘编码
	private static final String LABEL_PALLET_NO = "PalletNo";
	//生产记录的类型
	private static final String LABEL_RECTYPE = "RecType";
	
	
	private Connection getAccessDBConnection(String filename) throws Exception {

		String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;

		return DriverManager.getConnection(databaseURL, "", "");
	}
	UploadProduceReport uploadProduceReport = new UploadProduceReport();
	ProductIncomeIdcode productIncomeIdcode = new ProductIncomeIdcode();
	Product product=null;
	//生产数据上传dao
	private AppUploadProduceReport appUploadProduceReport = new AppUploadProduceReport();
	//产成品入库dao
	private AppProductIncome appProductIncome = new AppProductIncome();
	//产成品入库明细dao
	private AppProductIncomeDetail appProductIncomeDetail = new AppProductIncomeDetail();
	//产成品入库条码dao
	private AppProductIncomeIdcode appProductIncomeIdcode = new AppProductIncomeIdcode();
	//条码dao
//	private AppCodeUnit appCodeUnit = new AppCodeUnit();
	//产品dao
	private AppProduct appProduct = new AppProduct();
	//条码规则dao
//	private CodeRuleService codeRuleService = new CodeRuleService();
	//入库目标仓库配置dao
//	private AppExportAssign appExportAssign = new AppExportAssign();
	//用户dao
	private AppUsers appUsers = new AppUsers();
	//产品dao
	private AppProduct ap = new AppProduct();
	//物流码前缀dao
	private AppICode  ai = new AppICode();
	//Funit
	private AppFUnit af = new AppFUnit();
	//Idcode 
//	private AppIdcode aic = new AppIdcode();
//	private UploadProduceReport uploadProduceReport;
	/**
	 * 主要功能:上传生产数据
	 * @param filePath 文件临时存放路径
	 * @param logid 生产日志ID
	 * @param userid 当前登陆者ID
	 * @throws Exception
	 */
	public String getRecord(String filePath, Integer logid, Integer userid,String warehousId)  throws Exception {
		// 数据库连接
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		String info=null;
		String repeatinfo = null; 
		try {
			
			HibernateUtil.currentSession(true);
			//首先清空文件
			appUploadProduceReport.delAllRecord();
			//像Record中出入记录
			try{
				
				appUploadProduceReport.insertAllRecord(filePath);
				
				appUploadProduceReport.saveUploadProduceReportByRecord();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			// 新增生产上传
			/*
			rs = EntityManager.query("select * from Record");
			boolean rsFalg = rs.next();
			repeatinfo = this.updateProductIncome(rs,userid,logid,warehousId);;
			if(repeatinfo!=null){
				return repeatinfo;
			}*/
			// 更新日志(状态为处理完)
			updateDeal(logid, 2);

		}catch (Exception e) {
			info= "文件处理失败，请检查文件内容";
			e.printStackTrace();
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
	 * 新增Idcode
	* @param rs
	* @param warehousId
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jun 28, 2012 8:41:56 AM
	 */
	/*
	private void addIdcode(Integer userid,Integer logid,ResultSet rs, String warehousId) throws  Exception {

		while (rs.next()) {
			//得到箱物流码
			String CartonCode = rs.getString(LABEL_BOX_CODE);	
			//查询Idcode中是否存在
			Idcode  ic = aic.getIdcodeById(CartonCode);
			//条码不存在的情况
			if(ic == null){
				//新增Idcode 
				this.addInIdcode(userid,logid,rs,warehousId,CartonCode);
			//存在的情况
			}else{
				//跳过进入下条记录
				continue;
			}
		}
	}
*/
	/**
	 * 增加Idcode对象
	* @param ic
	* @param rs
	* @param warehousId
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jun 28, 2012 8:49:14 AM
	 */
	/*
	private void addInIdcode(Integer userid,Integer logid,ResultSet rs, String warehousId,String CartonCode) throws Exception {
		
		HibernateUtil.currentTransaction();
		Users users = appUsers.getUsersByid(userid);
		String proID = rs.getString(LABEL_PRODUCT_ID);
		//产品ID
		String productid =ai.getProductIdByLcode(proID);
		//得到产品箱条码的数量（单位：kg）
		Double procount=  af.getXQuantity(productid, 2);
		// 生产日期
		String pDate = rs.getString(LABEL_PRODUCT_DATE);
		// 批号
		String lotNo = rs.getString(LABEL_LOTNO);
		
		Idcode ic = new Idcode();
		ic.setIdcode(CartonCode);
		ic.setProductid(productid);
		ic.setProductname(ap.getProductNameByID(ic.getProductid()));
		ic.setBatch(lotNo);
		ic.setProducedate(DateUtil.formatDate(DateUtil.formatStrDate(pDate)));
		ic.setValidate("");
		//产品物流码前缀
		ic.setLcode(proID);
		ic.setStartno(CartonCode);
		ic.setEndno(CartonCode);
		//箱
		ic.setUnitid(2);
		ic.setQuantity(1.0);
//		ic.setFquantity(1.0);
		ic.setFquantity(procount);
		//单位 kg
		ic.setPackquantity(procount);
		ic.setIsuse(1);
		ic.setIsout(0);
		//条码不通过入库单  暂保存为上传记录日志的ID 
		ic.setBillid(logid+"");
		//??
		ic.setIdbilltype(2);
		ic.setMakeorganid(users.getMakeorganid());
		ic.setWarehouseid(warehousId);
		ic.setWarehousebit("000");
		ic.setProvideid("");
		ic.setProvidename("");
		ic.setMakedate(DateUtil.getCurrentDate());
		
		aic.addIdcode(ic);
		
		HibernateUtil.commitTransaction();
	}
	*/

	/**
	 * 主要功能:上传生产数据类型
	 * @param rs
	 * @throws Exception 
	 */
	/*private String uploadProductReport(ResultSet rs) throws Exception{
	//	String lastunitNo="";
		String ID=null;
		//循环遍历上传的数据集合
		while (rs.next()) {
			try {
			
		   String recType =  rs.getString(LABEL_RECTYPE );
		 //如果是预生产报表误传，则不予处理
		   if(recType.equals("Y")){
			   return "上传失败，您当前上传的文件可能是预生产报表<br>如果确定是，请通过【预生产上传】来进行操作";
		   }
				
			ID = rs.getString("ID");
//			System.out.println(ID);
			//得到物流码
			String unitNo = rs.getString(LABEL_UNIT_NO);
			//根据物流码查询数据库是否存在
//			uploadProduceReport = appUploadProduceReport.getUploadProduceReportByUnitNo(unitNo);
			//物流码不存在的情况
//			if(uploadProduceReport==null){
			//	lastunitNo=unitNo;
				//新增生产记录
					this.addProductReport(rs,unitNo,recType);
				} catch (SQLGrammarException e) {
					//org.hibernate.exception.SQLGrammarException: could not insert: [com.winsafe.drp.dao.UploadProduceReport]
					//Caused by: com.microsoft.sqlserver.jdbc.SQLServerException: 不能在具有唯一索引 'IX_upload_produce_report' 的对象 'dbo.upload_produce_report' 中插入重复键的行。
					e.printStackTrace();
					//continue;
					return "上传失败！<br/>当前文件中行号为【"+ID+"】的记录中<br/>产品(二维码)物流编码(列名为ProCode)已存在系统中";
					
				}catch (SQLException e) {
					//[Microsoft][ODBC Microsoft Access Driver] 无效的书签
					continue;
				}
//			}
		}
		
		//游标回到初始化
		rs.beforeFirst();
		return null;
	}
	
	/**
	 * 主要功能:新增生产上传
	 * @param uploadProduceReport 生产上传对象
	 * @param rs access数据
	 * @throws Exception
	 */
//	Set<String> unitNoSet = new HashSet<String>();
	/*private void addProductReport(ResultSet rs,String unitNo,String recType) throws Exception{
		
		String icode=null;
		String proid;
		String productname=null ;
//		if(unitNoSet.contains(unitNo)){
//			return;
//		}
//		unitNoSet.add(unitNo);
		
		//新建上传生产上传对象
		//UploadProduceReport uploadProduceReport = new UploadProduceReport();
		// 生产上传对象字段设置
		// 根据表名得到当前ID
//		Long id = Long.valueOf(MakeCode.getExcIDByRandomTableName("upload_produce_report", 0, ""));		
//		uploadProduceReport.setId(id);
		
		proid=rs.getString(LABEL_PRODUCT_ID);
		uploadProduceReport.setProid(proid);		
		
		//产品编号
		if(!proid.equals(icode) && null != icode){
			product = ap.getProductEntityByICode(icode);
			if(product!=null){
				productname = product.getProductname();
			}
		}
		//产品名称
		uploadProduceReport.setProname(productname);
		// 生产记录日期
		uploadProduceReport.setProdt(DateUtil.StringToDatetime(rs.getString(LABEL_PRCDATE)));
		// 批号
		uploadProduceReport.setLotno(rs.getString(LABEL_LOTNO));
		// 物流码
		uploadProduceReport.setUnitno(unitNo);
		//外箱物流码编号
		uploadProduceReport.setUnitnocode(rs.getString(LABEL_BOX_CODE));
		//积分（防伪）编码
		uploadProduceReport.setTraynocode(rs.getString(LABEL_P_NUM));
		//产线编号
		uploadProduceReport.setBc(rs.getString(LABEL_LINENo));
		//生产日期
		uploadProduceReport.setPdate(DateUtil.formatStrDate(rs.getString(LABEL_PRODUCT_DATE)));
		//分装日期
		uploadProduceReport.setPrcdate(rs.getString(LABEL_PKGDATE));
		//批次内外箱的顺序号
		uploadProduceReport.setCartonNo(rs.getString(LABEL_CARTON_NO));
		//虚拟托盘编码
		uploadProduceReport.setPalletNo(rs.getString(LABEL_PALLET_NO));
		//生产记录的类型
//		uploadProduceReport.setRecType(rs.getString(LABEL_RECTYPE ));
		uploadProduceReport.setRecType(recType);
//		uploadProduceReport.setIsexportincome(1);
		//保存生产上传数据
		appUploadProduceReport.saveUploadProduceReport(uploadProduceReport);
		product=null;
	}
*/
	/**
	 * 主要功能:更新产品入库
	 * @param rs access数据源
	 * @param wid 目标入库仓库id
	 * @throws Exception
	 */

	private String updateProductIncome(ResultSet rs,Integer userid,Integer logid,String warehousId) throws Exception{
		//当前产成品入库对象
		ProductIncome currentProductIncome = null;
		Product product = null;
		Double procount = 0d;
		Double totalkgquantity = 0d;
		int flag = 0;
		ProductIncomeDetail piddetail=null;
//		boolean isExist;
		String proID;
		String idCode;
		String lotNo;
		String lineNo;
		String pDate;
		String rectype;
		String lastproID = "";
		String lastidCode = "";
		String lastLotNo = "";
		int reVal;
//		long begin=0;
		// 循环遍历上传的数据集合
		while (rs.next()) {
			
		try {
			// 物流码前缀
			proID = rs.getString(LABEL_PRODUCT_ID);
			// 箱条码
			idCode = rs.getString(LABEL_BOX_CODE);
			// 批号
			lotNo = rs.getString(LABEL_LOTNO);
			// 生产日期
			pDate = rs.getString(LABEL_PRODUCT_DATE);
			// 生产记录的类型
			rectype = rs.getString(LABEL_RECTYPE);
			//产线编号
			lineNo= rs.getString(LABEL_LINENo);
			
//			HibernateUtil.currentTransaction();
			// 新增产成品入库单
			if (currentProductIncome == null) {
				currentProductIncome = this.addProductIncome(userid, logid, warehousId);
			}
			
			if (!rectype.toLowerCase().equals("s")) {
				
				//组拼 批次
				lotNo=pDate.replace("-", "")+lineNo+lotNo;
				
				// 如果下一行的产品id不等于上一行的产品id  或者产品的批次不同
				if (!proID.equals(lastproID) || !lotNo.equals(lastLotNo)) {

					// 读取下一种产品时，更新上一种产品的数量
					if (flag == 1) {
						piddetail = appProductIncomeDetail.getPIDetailByPiidPid(currentProductIncome.getId(), product.getId(),lastLotNo);
						// 追加明细数量,将上一产品的数量追加到数据库中
						this.updateProductIncomeDetail(piddetail, totalkgquantity);
						// 恢复totalkgquantity为0
						totalkgquantity = 0d;
					}

					lastproID = proID;
					lastidCode = idCode;
					lastLotNo = lotNo;
					
					// 根据物流码前缀找到对应的产品
					product = appProduct.getProductEntityByICode(proID);
					
					// 如果产品为NULL，跳过执行下一行记录 ADD 13/07/18 WEI.LI
					if(null == product){
						System.out.println("根据 "+proID+" 查询产品数据不存在!");
						continue;
					}
					
					// 得到产品箱条码的数量（单位：kg）
					procount = af.getXQuantity(product.getId(), 2);

					// 新增产成品入库单明细(初始化数量为0)
					this.addProductIncomeDetail(currentProductIncome.getId(),product, 0, lotNo);

					// 根据条码查询数据库是否存在(修改表结构 IDCode唯一索引)
					//isExist = appProductIncomeIdcode.ProductIncomeIdcodeIsExistByUnitNo(idCode);
//					if (!appProductIncomeIdcode.getProductIncomeIdcodeByNativeSql(idCode)) {
						
						// 更新产成品入库单明细(数量为一箱的数量)
//						this.addProductIncomeDetail(product, procount, lotNo);
						
						// 新增产品条码
						reVal = this.addProductIncomeIdCode(currentProductIncome.getId(),idCode, pDate, product, procount, lotNo,proID);
						if(reVal>0){
							piddetail = appProductIncomeDetail.getPIDetailByPiidPid(currentProductIncome.getId(), product.getId(),lotNo);
							// 追加明细数量
							updateProductIncomeDetail(piddetail, procount);
						}
//					}
				} else {
					// 同种产品 新的箱码
					if (!idCode.equals(lastidCode)) {
						lastidCode = idCode;
						flag = 1;

						// 根据条码查询数据库是否存在
						//isExist = appProductIncomeIdcode.ProductIncomeIdcodeIsExistByUnitNo(idCode);
//						if (!appProductIncomeIdcode.getProductIncomeIdcodeByNativeSql(idCode)) {
							
							// 新增产品条码
							reVal = this.addProductIncomeIdCode(currentProductIncome.getId(),idCode, pDate, product, procount, lotNo,proID);
							if(reVal>0){
								// 累加明细数量
								totalkgquantity = ArithDouble.add(totalkgquantity, procount);
							}
//						}

					}
				}
				
			}
		} catch (Exception e) {
//				e.printStackTrace();
				continue;
		}

	}
		//更新最后一种产品的数量
		piddetail = appProductIncomeDetail.getPIDetailByPiidPid(currentProductIncome.getId(), product.getId(),lastLotNo);
		// 追加明细数量
		this.updateProductIncomeDetail(piddetail, totalkgquantity);
		
		//update 2013-01-09 
		//清除数量为0的产品明细
		return delZeroQuantityInDetail(currentProductIncome);
	}
	
	/**
	 * 删除明细中数量为0的产品
	* @param currentProductIncome
	* @author wenping   
	 * @throws Exception 
	* @CreateTime Jan 9, 2013 9:33:36 AM
	 */
	private String  delZeroQuantityInDetail(ProductIncome currentProductIncome) throws Exception {
		List<ProductIncomeDetail> pidList = appProductIncomeDetail.getProductIncomeDetailByPbId(currentProductIncome.getId());
		if(pidList==null){
			EntityManager.delete(currentProductIncome);
			return "文件重复上传，请检查文件内容";
		}
		for(ProductIncomeDetail pid:  pidList){
			if(pid.getQuantity()==0){
				EntityManager.delete(pid);
			}
		}
		
		pidList = appProductIncomeDetail.getProductIncomeDetailByPbId(currentProductIncome.getId());
		if(pidList.size()==0){
			EntityManager.delete(currentProductIncome);
			return "文件重复上传，请检查文件内容";
		}
		
		return null;
	}

	/**
	 * 主要功能:根据物流码和批次查找产成品入库单(暂不使用)
	 * @param unitNo 物流码
	 * @param unitNo 批号
	 * @return 产成品入库单对象
	 * @throws Exception
	 */
	/*private ProductIncome checkProductIncomeExist(String unitNo,String lotNo) throws Exception{
		// 根据物流码和批次查找
		ProductIncome productIncome = appProductIncome.findProductIncomeByUnitNoAndLotNo(unitNo,lotNo);
		// 存在的场合
		if(productIncome != null){
			return productIncome;
		//不存在的场合返回NULL
		}else{
			return null;
		}
	}*/
/*
	private void updateProductIncomeIdcodeQuantity(ProductIncomeIdcode productIncomeIdcode, Double procount) throws Exception {
//		HibernateUtil.currentTransaction();
		productIncomeIdcode.setPackquantity(productIncomeIdcode.getPackquantity()+procount);
		productIncomeIdcode.setQuantity(productIncomeIdcode.getQuantity()+procount);
		appProductIncomeIdcode.updProductIncomeIdcode(productIncomeIdcode);
//		HibernateUtil.commitTransaction();
	}
*/
	/**
	 * 主要内容: 更新产成品入库明细
	 * @param product 产品
	 * @throws Exception
	 */
	private void updateProductIncomeDetail(ProductIncomeDetail productIncomeDetail,Double quantity) throws Exception{
		//查出产成品入库单明细
//		HibernateUtil.currentTransaction();
		if(productIncomeDetail!=null){
			productIncomeDetail.setQuantity(productIncomeDetail.getQuantity() + quantity);
			productIncomeDetail.setFactquantity(productIncomeDetail.getFactquantity() + quantity);
			appProductIncomeDetail.updProductIncomeDetail(productIncomeDetail);
		}
//		HibernateUtil.commitTransaction();
		 
	}
	
	/**
	 * 主要功能:新增产成品入库
	 * @param userid 登陆者id
	 * @param logid 日志id
	 * @return 产成品入库单
	 * @throws Exception
	 */
	private ProductIncome addProductIncome(Integer userid,Integer logid,String warehousId) throws Exception{
//		HibernateUtil.currentTransaction();
		Users users = appUsers.getUsersByid(userid);
		// 得到当前用户所默认设置的产成品入库仓库(7月16日开始更改)
		//String wid = appExportAssign.getExportAssignWid(userid);
		//仓库ID
		String wid=warehousId;
		
		ProductIncome productIncome = new ProductIncome();
		// 生成入库单号
		String piid = MakeCode.getExcIDByRandomTableName("product_income", 2, "PE");
		productIncome.setId(piid);
		//目标入库仓库id
		productIncome.setWarehouseid(wid);
		//批号
		productIncome.setHandwordcode(logid.toString());
		//入库时间
		productIncome.setIncomedate(DateUtil.getCurrentDate());
		productIncome.setIncomesort(0);
		//入库单备注
		productIncome.setRemark(new String("生产报表转产成品入库".getBytes(),"gbk"));
		productIncome.setIsaudit(0);
		productIncome.setAuditid(0);
		//制单人
		productIncome.setMakeorganid(users.getMakeorganid());
		productIncome.setMakedeptid(users.getMakedeptid());
		productIncome.setMakeid(userid);
		productIncome.setMakedate(DateUtil.getCurrentDate());
		productIncome.setKeyscontent(productIncome.getId() + "," + logid);
		//新增入库单
		appProductIncome.addProductIncome(productIncome);
//		HibernateUtil.commitTransaction();
		return productIncome;
	}
	
	/**
	 * 主要功能:新增产成品入库明细
	 * @param product 产品
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void addProductIncomeDetail(String pid ,Product product, double quantity,String lotNo) throws NumberFormatException, Exception{
//		HibernateUtil.currentTransaction();
		//新增产成品入库明细对象
		ProductIncomeDetail productIncomeDetail = new ProductIncomeDetail();
		//设置属性
		//id
		productIncomeDetail.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(	"product_income_detail", 0, "")));
		//产成品单id
		productIncomeDetail.setPiid(pid);
		//产品id
		productIncomeDetail.setProductid(product.getId());
		//产品名称
		productIncomeDetail.setProductname(product.getProductname());
		//产品类型
		productIncomeDetail.setSpecmode(product.getSpecmode());
		//产品单位 kg
		productIncomeDetail.setUnitid(product.getCountunit());
		//产品数量 以最小单位kg计算的数量
		productIncomeDetail.setQuantity(quantity);
		productIncomeDetail.setFactquantity(quantity);
		//产品单价
		productIncomeDetail.setCostprice(product.getCost());
		//产品总价
		productIncomeDetail.setCostsum(productIncomeDetail.getQuantity()* productIncomeDetail.getCostprice());
		//制单时间
		productIncomeDetail.setMakedate(DateUtil.getCurrentDate());
		productIncomeDetail.setBatch(lotNo);
		productIncomeDetail.setConfirmQuantity(0d);
		//新增操作
		appProductIncomeDetail.addProductIncomeDetail(productIncomeDetail);
		productIncomeDetail=null;
//		HibernateUtil.commitTransaction();
	}
	
	/**
	 * 主要功能:新增产成品条码
	 * @param idcode 条码
	 * @param pDate 生产日期
	 * @param product 产品
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private int addProductIncomeIdCode(String pid,String idCode,String pDate,Product product,Double quantity,String lotNo,String proID) throws NumberFormatException, Exception{
//		HibernateUtil.currentTransaction();
		//		// 取包装代码(N:箱)
//		String pack_Type = codeRuleService.getUcode(idCode);
//		CodeUnit codeUnit = appCodeUnit.getCodeUnitByID(pack_Type);
		// 新增产品入库条码对象
		//ProductIncomeIdcode productIncomeIdcode = new ProductIncomeIdcode();
		// 配置属性
		// id
		productIncomeIdcode.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_income_idcode", 0, "")));
		//产成品入库单id
		productIncomeIdcode.setPiid(pid);
		//产品id
		productIncomeIdcode.setProductid(product.getId());
		productIncomeIdcode.setIsidcode(1);
		//默认库位000
		productIncomeIdcode.setWarehousebit("000");
		//批次
		productIncomeIdcode.setBatch(lotNo);
		//生产日期
		productIncomeIdcode.setProducedate(DateUtil.formatDate(DateUtil.formatStrDate(pDate)));
		productIncomeIdcode.setVad("");
		//单位
		productIncomeIdcode.setUnitid(2);
		productIncomeIdcode.setQuantity(1d);
		//箱条码
		productIncomeIdcode.setIdcode(idCode);
		//物流条码
		productIncomeIdcode.setLcode(proID);
		//起始条码
		productIncomeIdcode.setStartno(idCode);
		//结束条码
		productIncomeIdcode.setEndno(idCode);
		//数量 最小单位kg
		productIncomeIdcode.setPackquantity(quantity);
		//制单日
		productIncomeIdcode.setMakedate(DateUtil.getCurrentDate());
		//新增产品入库条码
		return appProductIncomeIdcode.insertProductIncomeIdcode(productIncomeIdcode);
//		HibernateUtil.commitTransaction();
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
