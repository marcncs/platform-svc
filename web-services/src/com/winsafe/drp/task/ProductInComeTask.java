package com.winsafe.drp.task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 生成入库单定时任务
 * 
 * @author Andy.liu
 * 
 */
public class ProductInComeTask extends Thread {
	//同步锁
	private static Object lock = new Object();
	private static boolean isRunning=false;
	
	/** 入库单查询的开始时间 */
	private String startDate;
	/** 入库单查询的结束时间 */
	private String endDate;

	/**
	 * 初始化数据
	 */
	public ProductInComeTask() {
	}

	/**
	 * 入口
	 */
	public void run() {
		if (!isRunning) {
			synchronized(lock){
				try {
					isRunning = true;
					DBUserLog.addUserLog1(1, "自动生成入库单据---开始---");
					// 获取最后一次生成入库单的时间
 					String date = this.getLastInComeDate();
					// 没有获取到时间则数据库没有数据。直接退出
					if (StringUtil.isEmpty(date) || "null".equals(date) ) {
						return;
					}
					date = date.replace("/", "-");
					Date lastDate = DateUtil.StringToDate(date, "yyyy-MM-dd ");
					lastDate = DateUtil.setHHmmss(lastDate, 0, 0, 0);
//					Date currnetDate = DateUtil.StringToDate(DateUtil.getCurrentDateTime(),"yyyy-MM-dd ");
					Date currnetDate = DateUtil.addDay2Date(1,DateUtil.setHHmmss(DateUtil.getCurrentDate(),0,0,0) );
					while (lastDate.before(currnetDate)) {
						// 根据最后生成时间获取当前需要生成的时间段
						startDate = DateUtil.formatDate(lastDate, "yyyy-MM-dd")+ " 00:00:00";
						endDate = DateUtil.getAfterDay(startDate);
						// 执行新增入库单
						try {
							HibernateUtil.currentSession(true);
							//判断是否有数据在决定是否生成入库单
							if(checkReportCount()){
								this.produceIncome();
							}
							HibernateUtil.commitTransaction();
						} catch (Exception e) {
							HibernateUtil.rollbackTransaction();
						} finally {
							//HibernateUtil.closeSession();
						}
						lastDate = DateUtil.StringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
					}
				} catch (Exception e) {
					try {
						DBUserLog.addUserLog1(1, "自动生成入库单据发生异常");
					} catch (Exception e1) {
					}
				}finally{
					isRunning = false;
					try {
						DBUserLog.addUserLog1(1, "自动生成入库单据---结束---");
					} catch (Exception e) {
					}
				}
			}
		}
		
	}

	/**
	 * 获取导入单中startDate至endDate时间段的所有当前用户的产品
	 */
	private void produceIncome() throws Exception {
		/*
		 * 1，创建入库单
		 * 2，获取入库单ID将upload_produce_report表中当天数据全部取出插入product_income_idcode表中
		 * 3，group by product_income_idcode中的产品种类数量，插入product_income_detail表中
		 * 4，修改当天upload_produce_report表中所有产品状态为1（已入库） 备注：入库单为一仓库一单；group by
		 * upload_produce_report表中仓库ID 获取仓库数量
		 */ 
		/*-------------------------------------------------------------*/
		String piid = "";
		// 获取当前时间所有的仓库ID，根据仓库ID决定生成入库单，一库一单
		List<String> list = getWareHouseId();
		for (String warehouseid : list) {
			// 创建入库单
			piid = this.addProductIncome(warehouseid);
			// piid为空，则没有入库单
			// 新增入库产品条码
			this.addProductIncomeIdCode(piid);
			// 新增入库单明细
			this.addProductIncomeDetail(piid);
			// 修改upload_produce_report表中产品的状态
			this.updateReportStatus();
		}
	}

	/**
	 * 检查是否有数据
	 * @return 有则返回true，无则返回false
	 * @throws Exception
	 */
	private boolean checkReportCount() throws Exception {
		int count = 0;
		String sql = "SELECT COUNT(ID)  FROM UPLOAD_PRODUCE_REPORT WHERE "
				+ "MAKEDATE >= TO_DATE('" + startDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') AND " + "MAKEDATE < TO_DATE('"
				+ endDate + "', 'yyyy-mm-dd hh24:mi:ss') AND " + "ISINCOME = 0";
		ResultSet rs = EntityManager.query2(sql);
		if (rs.next()) {
			count = rs.getInt(1);
		}
		return count == 0 ? false : true;

	}

	/**
	 * 主要功能:新增产成品入库单
	 * @param warehousId 仓库ID
	 * @return 产成品入库单ID
	 * @throws Exception
	 */
	private String addProductIncome(String warehousId) throws Exception {
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("Record", 0, ""));
		// 生成入库单号
		String piid = MakeCode.getExcIDByRandomTableName("product_income", 2,"PE");
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("INSERT INTO PRODUCT_INCOME(");
		sqlbuffer.append("ID, HANDWORDCODE, WAREHOUSEID, INCOMEDATE, INCOMESORT, REMARK, ISAUDIT, ");
		sqlbuffer.append("AUDITID, MAKEORGANID, MAKEID, MAKEDATE, KEYSCONTENT, CONFIMSTATE");
		sqlbuffer.append(")SELECT ");
		// ID 编号
		sqlbuffer.append("'" + piid + "',");
		// HANDWORDCODE 手工单号
		sqlbuffer.append("'" + id.toString() + "',");
		// WAREHOUSEID 目标入库仓库id
		sqlbuffer.append("'" + warehousId + "',");
		// INCOMEDATE 入库日期
		sqlbuffer.append("TO_DATE('" + DateUtil.getCurrentDateTime()+ "','yyyy-mm-dd hh24:mi:ss'),");
		// INCOMESORT 入库类别编号
		sqlbuffer.append("0,");
		// REMARK 备注
		sqlbuffer.append("'生产报表转产成品入库',");
		// ISAUDIT 是否复核
		sqlbuffer.append("0,");
		// AUDITID 审核人
		sqlbuffer.append("0,");
		//MAKEORGANID 制单机构    (总机构)
		sqlbuffer.append("'00000001',");
		//MAKEID 制单人 (系统管理员)
		sqlbuffer.append("'1',");
		// MAKEDATE 制单日期
		sqlbuffer.append("TO_DATE('" + DateUtil.getCurrentDateTime()+ "','yyyy-mm-dd hh24:mi:ss'),");
		// KEYSCONTENT 关键字
		sqlbuffer.append("'" + piid + "," + id + "',");
		//CONFIMSTATE
		sqlbuffer.append("'0'");
		sqlbuffer.append("FROM DUAL WHERE NOT EXISTS (");
		sqlbuffer.append("SELECT ID FROM PRODUCT_INCOME WHERE ID='" + piid+ "'");
		sqlbuffer.append(")");
		EntityManager.updateOrdelete(sqlbuffer.toString());
		return piid;

		// ProductIncome productIncome = new ProductIncome();
		// productIncome.setId(piid);
		// // 目标入库仓库id
		// productIncome.setWarehouseid(warehousId);
		// // 手工单
		// productIncome.setHandwordcode(id.toString());
		// // 入库时间
		// productIncome.setIncomedate(DateUtil.getCurrentDate());
		// productIncome.setIncomesort(0);
		// // 入库单备注
		// productIncome.setRemark(new String("生产报表转产成品入库".getBytes(), "gbk"));
		// //是否审核
		// productIncome.setIsaudit(0);
		// //审核人
		// productIncome.setAuditid(0);
		// //制单日期
		// productIncome.setMakedate(DateUtil.getCurrentDate());
		// productIncome.setKeyscontent(productIncome.getId() + "," + id);
		// // 新增入库单
		// appProductIncome.addProductIncome(productIncome);
		// return productIncome;
	}

	/**
	 * 主要功能:新增产成品入库明细
	 * @param piid 入库单ID
	 * @throws Exception
	 */
	private void addProductIncomeDetail(String piid) throws Exception {
		StringBuffer sqlbuffer = new StringBuffer();

		sqlbuffer.append("INSERT INTO PRODUCT_INCOME_DETAIL(");
		sqlbuffer.append("ID, PIID, PRODUCTID, PRODUCTNAME, SPECMODE, UNITID, QUANTITY, ");
		sqlbuffer.append("FACTQUANTITY, COSTPRICE, COSTSUM, MAKEDATE, BATCH ,CONFIRMQUANTITY");
		sqlbuffer.append(")SELECT ROWNUM + ");
		sqlbuffer.append(""+ Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_income_detail", 0, "")) + ",");
		// PIID 入库单
		sqlbuffer.append("'" + piid + "',");
		// PRODUCTID 产品ID
		sqlbuffer.append("A.PRODUCTID,");
		// PRODUCTNAME 产品名称
		sqlbuffer.append("P.PRODUCTNAME,");
		// SPECMODE 规格
		sqlbuffer.append("P.SPECMODE,");
		// UNITID 单位编号
		sqlbuffer.append("P.COUNTUNIT,");
		// QUANTITY 数量
		sqlbuffer.append("TOTAL,");
		// FACTQUANTITY 实际数量
		sqlbuffer.append("TOTAL,");
		// COSTPRICE 产品单价
		sqlbuffer.append("P.COST,");
		// COSTSUM 产品总价
		sqlbuffer.append("TOTAL*P.COST,");
		// MAKEDATE 制单时间
		sqlbuffer.append("TO_DATE('" + DateUtil.getCurrentDateTime()+ "','YYYY-MM-DD HH24:MI:SS'),");
		// BATCH 批次
		sqlbuffer.append("A.BATCH,");
		// CONFIRMQUANTITY
		sqlbuffer.append("0");
		sqlbuffer.append(" FROM (SELECT PRODUCTID ,COUNT(*) TOTAL, BATCH FROM PRODUCT_INCOME_IDCODE ");
		sqlbuffer.append(" WHERE PIID = '"+piid+"'");
		sqlbuffer.append(" GROUP BY PRODUCTID ,BATCH ) A ,PRODUCT P");
		sqlbuffer.append(" WHERE A.PRODUCTID = P.ID ");
		System.out.println("新增产成品入库明细SQL语句：" + sqlbuffer.toString());
		EntityManager.updateOrdelete(sqlbuffer.toString());
		
		//更新基础表
		String sql = "update make_conf set CurrentValue =(select max(id)+1 from product_income_detail) where TableName='product_income_detail'";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 主要功能:新增产成品条码
	 * @param piid 入库单ID
	 * @throws Exception
	 */
	private void addProductIncomeIdCode(String piid)
			throws NumberFormatException, Exception {
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append("INSERT INTO PRODUCT_INCOME_IDCODE(");
		sqlbuffer.append("PIID, PRODUCTID, ISIDCODE, WAREHOUSEBIT, BATCH, ");
		sqlbuffer.append("PRODUCEDATE, VAD, UNITID, QUANTITY, PACKQUANTITY, BOXCODE, ");
		sqlbuffer.append("CARTONCODE, IDCODE, MAKEDATE, PALLETCODE, NCLOTNO");
		sqlbuffer.append(") SELECT ");
		// PIID 入库单
		sqlbuffer.append("'" + piid + "',");
		// PRODUCTID 产品ID
		sqlbuffer.append("R.PROID,");
		// ISIDCODE 是否条码
		sqlbuffer.append("1,");
		// WAREHOUSEBIT 仓位
		sqlbuffer.append("'000',");
		// BATCH 批次
		sqlbuffer.append("R.LOTNO,");
		// PRODUCEDATE 生产日期
		sqlbuffer.append("to_char(r.prodt,'yyyy-mm-dd hh24:mi:ss'),");
		// VAD 有效期
		sqlbuffer.append("'',");
		// UNITID 单位
		sqlbuffer.append("F.FUNITID,");
		// QUANTITY 数量
		sqlbuffer.append("1,");
		// PACKQUANTITY 包装数量
		sqlbuffer.append("F.XQUANTITY,");
		// BOXCODE = 中盒条码
		sqlbuffer.append("R.BOXCODE,");
		// CARTONCODE = 外箱条码
		sqlbuffer.append("R.CARTONCODE,");
		// IDCODE 小盒条码
		sqlbuffer.append("R.PACKCODE,");
		// MAKEDATE 制单日
		sqlbuffer.append("to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss'),");
		// PALLETCODE 托盘条码
		sqlbuffer.append("R.PALLETCODE,");
		// NCLOTNO 实际批次
		sqlbuffer.append("R.NCLOTNO");
		sqlbuffer.append(" FROM UPLOAD_PRODUCE_REPORT R , F_UNIT F WHERE R.PROID = F.PRODUCTID AND FUNITID = 1");
		sqlbuffer.append("  AND R.MAKEDATE >=TO_DATE('"+ startDate + "','yyyy-mm-dd hh24:mi:ss') ");
		sqlbuffer.append(" AND R.MAKEDATE < TO_DATE('" + endDate+ "','yyyy-mm-dd hh24:mi:ss') AND R.ISINCOME = 0");
		System.out.println("新增产成品条码SQL语句：" + sqlbuffer.toString());
		EntityManager.updateOrdelete(sqlbuffer.toString());
	}

	/**
	 * 获取最后入库时间
	 */
	private String getLastInComeDate() {
		//String idCodeSql = "SELECT MAX(MAKEDATE) FROM PRODUCT_INCOME ";
		String reportSql = "SELECT MIN(MAKEDATE) FROM UPLOAD_PRODUCE_REPORT WHERE ISINCOME = 0";
		String resultDate = "";
		try {
//			ResultSet rs = EntityManager.query2(idCodeSql);
//			if (rs.next()) {
//				resultDate = rs.getString(1);
//			}
//			rs.close();
			//if (StringUtil.isEmpty(resultDate)) {
				ResultSet rs = EntityManager.query2(reportSql);
				if (rs.next()) {
					resultDate = rs.getString(1);
				}
				rs.close();
			//}
		} catch (Exception e) {
			System.err.println("error: found LastIncomeDate error");
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 修改当前时间下所有UPLOAD_PRODUCE_REPORT表中产品状态为1，已入库
	 * 
	 * @throws Exception
	 */
	private void updateReportStatus() throws Exception {
		String sql = "UPDATE UPLOAD_PRODUCE_REPORT SET ISINCOME = 1 WHERE MAKEDATE >= "
				+ "TO_DATE('"
				+ startDate
				+ "','yyyy-mm-dd hh24:mi:ss') AND MAKEDATE < TO_DATE('"
				+ endDate 
				+ "','yyyy-mm-dd hh24:mi:ss')";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 获取所有仓库ID
	 * 
	 * @return
	 */
	private List<String> getWareHouseId() {
		String sql = "SELECT COUNT(*), WAREHOUSEID FROM UPLOAD_PRODUCE_REPORT WHERE MAKEDATE >= "
				+ "TO_DATE('"
				+ startDate
				+ "','yyyy-mm-dd hh24:mi:ss') AND MAKEDATE < TO_DATE('"
				+ endDate
				+ "','yyyy-mm-dd hh24:mi:ss')"
				+ " GROUP BY WAREHOUSEID";
		List<String> list = new ArrayList<String>();
		String warehouseid = "";
		try {
			ResultSet rs = EntityManager.query2(sql);
			while (rs.next()) {
				warehouseid = rs.getString("WAREHOUSEID");
				list.add(warehouseid);
			}
			rs.close();
		} catch (Exception e) {
			System.err.println("error : found warehouseid error");
			e.printStackTrace();
		}
		return list;
	}

}
