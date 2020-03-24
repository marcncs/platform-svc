package com.winsafe.drp.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveDetailFormBean;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class StockAlterMoveImportAllService {

	AppStockAlterMove asm = new AppStockAlterMove();
	AppProduct ap = new AppProduct();
	AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
	AppOrgan appOrgan = new AppOrgan();
	AppWarehouse appWarehouse = new AppWarehouse();
	AppBaseResource appBaseResource = new AppBaseResource();
	AppUserVisit appUserVisit = new AppUserVisit();
	AppWarehouseVisit appWarehouseVisit = new AppWarehouseVisit();
	AppOrganProduct appOrganProduct=new AppOrganProduct(); 
	
	List<StockAlterMoveDetail> insertstockAlterMoveDetails = new ArrayList<StockAlterMoveDetail>();
	List<StockAlterMoveDetail> updStockAlterMoveDetails = new ArrayList<StockAlterMoveDetail>();
	Set<String>   pSet=new  HashSet<String>();
	String insertsql;
	String updsql;
	StringBuilder errorMsg = new StringBuilder();
	// 仓库信息
	String outwarehouseid ;
	String inwarehouseid ;
	String receiveOrganid ;
	String organname ;
	String receOrganname;
	UsersBean user;
	String beach;
	/**
	 * 默认构造器
	 */
	public StockAlterMoveImportAllService() {
	}
	public String dealFile(String filepath, UsersBean users,String includewhid, int billsort) {
		// 初始化
		String result = null;
		Date currentDate = Dateutil.getCurrentDate();
		initValue();
		beach="X999"+DateUtil.formatDate(Dateutil.getCurrentDate(), "yyyyMMdd");
		user = users;
		Workbook wb = null;
		int rowCount = 0;
		int successCount = 0;
		try {
			wb = Workbook.getWorkbook(new File(filepath));
			Sheet sheet = wb.getSheet(0);
			rowCount = sheet.getRows();
			StockAlterMove sm = null;
			int state = 0;
			int num = 1;
			String styleNo = getCellValue(sheet, 1, num++);       // 样式编号
			String styleName = getCellValue(sheet, 1, num++);     // 样式名称
			String organName = getCellValue(sheet, 1, num++);     // 公司全名
			String organoecode = getCellValue(sheet, 1, num++);   // 公司编号
			String olinkmanvalue = getCellValue(sheet, 1, num++); // 联系人
			String address = getCellValue(sheet, 1, num++);       // 地址
			String otel = getCellValue(sheet, 1, num++);          // 电话
			String zipcode = getCellValue(sheet, 1, num++);       // 邮编
			String taxNo = getCellValue(sheet, 1, num++);         // 税号
			String bank = getCellValue(sheet, 1, num++);          // 开户银行
			String bankno = getCellValue(sheet, 1, num++);        // 公司账号
			String organremake = getCellValue(sheet, 1, num++);   // 公司备注
			String makeorganman = getCellValue(sheet, 1, num++);  // 制单人
			String makedate = getCellValue(sheet, 1, num++);      // 日期
			String nccode = getCellValue(sheet, 1, num++);        // 单据编号
			String operateman = getCellValue(sheet, 1, num++);    // 经手人
			String receiveorganname = getCellValue(sheet, 1, num++); // 往来单位名称
			String receivewarehousename = getCellValue(sheet, 1, num++); // 出货仓库名称
			String explain = getCellValue(sheet, 1, num++);       // 说明
			String remake = getCellValue(sheet, 1, num++);        // 摘要
			String iswaybill = getCellValue(sheet, 1, num++);     // 是否过账
			//验证信息
			checkVallid(styleNo, organoecode, receiveorganname, nccode,receivewarehousename,billsort);
			//验证产品是否上架
		    getOrganProduct(organoecode); 
			// 查找数据库中的记录
			sm = asm.getStockAlterMoveByNCcode(nccode);
			if (sm == null) { // 新增订购单
				state = 1;
				sm = new StockAlterMove();
				String smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2, "OM");
				sm.setId(smid);
				sm.setMovedate(currentDate);
				sm.setOutwarehouseid(outwarehouseid);
				sm.setInwarehouseid(inwarehouseid);
				sm.setIsmaketicket(0);
				sm.setIsreceiveticket(0);
				sm.setIsshipment(0);
				sm.setIsaudit(0);
				sm.setIsblankout(0);
				sm.setIscomplete(0);
				sm.setMakeorganid(users.getMakeorganid());
				sm.setMakeorganidname(organname);
				sm.setMakedeptid(users.getUserid());
				sm.setMakedate(currentDate);
				sm.setMakeid(users.getUserid());
				sm.setIstally(0);
				sm.setTallyid(0);
				sm.setReceiveorganid(receiveOrganid);
				sm.setReceiveorganidname(receOrganname);
				sm.setNccode(nccode);
				StringBuffer keyscontent = new StringBuffer();
				keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",").append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
				sm.setTotalsum(0d);
				sm.setTakestatus(0);
				sm.setKeyscontent(keyscontent.toString());
				sm.setBsort(billsort);
				sm.setOtel("13013013013"); //默认值
				sm.setOlinkman(makeorganman);
				asm.saveStockAlterMove(sm);
			} else if (sm.getIsaudit() == null || sm.getIsaudit() == 0) { // 更新操作
				state = 2;
				sm.setMovedate(currentDate);
				sm.setOutwarehouseid(outwarehouseid);
				sm.setInwarehouseid(inwarehouseid);
				sm.setIsaudit(0);
				sm.setMakeorganid(users.getMakeorganid());
				sm.setMakeorganidname(organname);
				sm.setMakedeptid(users.getUserid());
				sm.setMakedate(currentDate);
				sm.setMakeid(users.getUserid());
				sm.setReceiveorganid(receiveOrganid);
				sm.setReceiveorganidname(receOrganname);
				//sm.setNccode(nccode);
				sm.setOtel("13013013013");
				asm.updstockAlterMove(sm);
			} else {
				errorMsg.append("单据信息错误，该单据不能修改<br>");
				state = 3;
				throw new Exception("单据信息错误，该单据不能修改");
			}
			// 处理订单的详情信息
			List<StockAlterMoveDetailFormBean> lBeans = new ArrayList<StockAlterMoveDetailFormBean>();
			StockAlterMoveDetailFormBean stockAlterMoveDetailFormBean = null;
			switch (billsort) {
			case 5://销售折扣单
				for (int i = 50; i < rowCount; i++) {
					stockAlterMoveDetailFormBean = new StockAlterMoveDetailFormBean();
					// 读取文件
					String productNcode = getCellValue(sheet, 1, i);
					if (productNcode == null || productNcode.length() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行产品编号不能为空<br>");
						continue;
					}
					List pList = ap.getProductByNccode(productNcode);
					if (pList == null || pList.size() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行系统中不存在对应产品信息<br>");
						continue;
					}
//					else {
//						//验证产品是否上架
//						Product p=(Product) pList.get(0);
//						if (pSet.add(p.getId())) {
//							errorMsg.append("单据信息错误，第"+(i+1)+"行产品未上架<br>");
//							continue;
//						}
//					}
					String productname = getCellValue(sheet, 2, i);
					String productidcode = getCellValue(sheet, 3, i);
					String unit = getCellValue(sheet, 4, i);
					// 获取到单位对应的编号
					if (unit != null) {
						BaseResource bs = appBaseResource.getBaseResourceKey("CountUnit", unit);
						if (bs != null) {
							stockAlterMoveDetailFormBean.setUnit(bs.getTagsubkey());
						}
					}
					String totalmany = getCellValue(sheet, 5, i);
					// 设置值
					stockAlterMoveDetailFormBean.setProductnccode(productNcode);
					stockAlterMoveDetailFormBean.setProductName(productname);
					stockAlterMoveDetailFormBean.setIdcode(productidcode);
					if (totalmany != null && totalmany.length()>0) {
						stockAlterMoveDetailFormBean.setQuantity(Double.valueOf(totalmany));
					}
					lBeans.add(stockAlterMoveDetailFormBean);
					stockAlterMoveDetailFormBean = null;
					successCount++;
				}
				break;
			case 6://配货出库单
				for (int i = 49; i < rowCount; i++) {
					stockAlterMoveDetailFormBean = new StockAlterMoveDetailFormBean();
					// 读取文件
					String productNcode = getCellValue(sheet, 1, i);
					if (productNcode == null || productNcode.length() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行产品编号不能为空<br>");
						continue;
					}
					List pList = ap.getProductByNccode(productNcode);
					if (pList == null || pList.size() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行系统中不存在对应产品信息<br>");
						continue;
					}
//					else {
//						//验证产品是否上架
//						Product p=(Product) pList.get(0);
//						if (pSet.add(p.getId())) {
//							errorMsg.append("单据信息错误，第"+(i+1)+"行产品未上架<br>");
//							continue;
//						}
//					}
					String productname = getCellValue(sheet, 2, i);
					String productidcode = getCellValue(sheet, 3, i);
					String unit = getCellValue(sheet, 4, i);
					String totalmany = getCellValue(sheet, 5, i);
					// 获取到单位对应的编号
					if (unit != null) {
						BaseResource bs = appBaseResource.getBaseResourceKey("CountUnit", unit);
						if (bs != null) {
							stockAlterMoveDetailFormBean.setUnit(bs.getTagsubkey());
						}
					}
					stockAlterMoveDetailFormBean.setProductnccode(productNcode);
					stockAlterMoveDetailFormBean.setProductName(productname);
					stockAlterMoveDetailFormBean.setIdcode(productidcode);
					if (totalmany != null && totalmany.length()>0) {
						stockAlterMoveDetailFormBean.setQuantity(Double.valueOf(totalmany));
					}
					lBeans.add(stockAlterMoveDetailFormBean);
					stockAlterMoveDetailFormBean = null;
					successCount++;
				}
				break;
			case 7://销售退货折扣单
				for (int i = 45; i < rowCount; i++) {
					stockAlterMoveDetailFormBean = new StockAlterMoveDetailFormBean();
					// 读取文件
					String productNcode = getCellValue(sheet, 1, i);
					if (productNcode == null || productNcode.length() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行产品编号不能为空<br>");
						continue;
					}
					List pList = ap.getProductByNccode(productNcode);
					if (pList == null || pList.size() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行系统中不存在对应产品信息<br>");
						continue;
					}
//					else {
//						//验证产品是否上架
//						Product p=(Product) pList.get(0);
//						if (pSet.add(p.getId())) {
//							errorMsg.append("单据信息错误，第"+(i+1)+"行产品未上架<br>");
//							continue;
//						}
//					}
					String productname = getCellValue(sheet, 2, i);
					String productidcode = getCellValue(sheet, 3, i);
					String unit = getCellValue(sheet, 4, i);
					String totalmany = getCellValue(sheet, 5, i);
					// 获取到单位对应的编号
					if (unit != null) {
						BaseResource bs = appBaseResource.getBaseResourceKey("CountUnit", unit);
						if (bs != null) {
							stockAlterMoveDetailFormBean.setUnit(bs.getTagsubkey());
						}
					}
					stockAlterMoveDetailFormBean.setProductnccode(productNcode);
					stockAlterMoveDetailFormBean.setProductName(productname);
					stockAlterMoveDetailFormBean.setIdcode(productidcode);
					if (totalmany != null && totalmany.length()>0) {
						stockAlterMoveDetailFormBean.setQuantity(Double.valueOf(totalmany));
					}
					lBeans.add(stockAlterMoveDetailFormBean);
					stockAlterMoveDetailFormBean = null;
					successCount++;
				}
				break;
			case 8://赠送单
				for (int i = 24; i < rowCount; i++) {
					stockAlterMoveDetailFormBean = new StockAlterMoveDetailFormBean();
					// 读取文件
					String productNcode = getCellValue(sheet, 1, i);
					if (productNcode == null || productNcode.length() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行产品编号不能为空<br>");
						continue;
					}
					List pList = ap.getProductByNccode(productNcode);
					if (pList == null || pList.size() == 0) {
						errorMsg.append("单据信息错误，第"+(i+1)+"行系统中不存在对应产品信息<br>");
						continue;
					}
//					else {
//						//验证产品是否上架
//						Product p=(Product) pList.get(0);
//						if (pSet.add(p.getId())) {
//							errorMsg.append("单据信息错误，第"+(i+1)+"行产品未上架<br>");
//							continue;
//						}
//					}
					String productname = getCellValue(sheet, 2, i);
					String productidcode = getCellValue(sheet, 3, i);
					String unit = getCellValue(sheet, 4, i);
					String totalmany = getCellValue(sheet, 5, i);
					// 获取到单位对应编号
					if (unit != null) {
						BaseResource bs = appBaseResource.getBaseResourceKey("CountUnit", unit);
						if (bs != null) {
							stockAlterMoveDetailFormBean.setUnit(bs.getTagsubkey());
						}
					}
					stockAlterMoveDetailFormBean.setProductnccode(productNcode);
					stockAlterMoveDetailFormBean.setProductName(productname);
					stockAlterMoveDetailFormBean.setIdcode(productidcode);
					if (totalmany != null && totalmany.length()>0) {
						stockAlterMoveDetailFormBean.setQuantity(Double.valueOf(totalmany));
					}
					lBeans.add(stockAlterMoveDetailFormBean);
					stockAlterMoveDetailFormBean = null;
					successCount++;
				}
				break;
			default:
				break;
			}
			// 整理数据 生成或者更新订购单明细
			addSmd(state, sm, lBeans);
			// 批量更新
			insertvolumeSmd(insertstockAlterMoveDetails);
			updtvolumeSmd(updStockAlterMoveDetails);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		if (billsort == 5) {
			result = "总数量：" + (rowCount - 50) + "，成功数量：" + successCount+ "，失败数量：" + (rowCount - 50 - successCount) + "<br>";
		} else if (billsort == 6) {
			result = "总数量：" + (rowCount - 49) + "，成功数量：" + successCount+ "，失败数量：" + (rowCount - 49 - successCount) + "<br>";
		} else if (billsort == 7) {
			result = "总数量：" + (rowCount - 45) + "，成功数量：" + successCount+ "，失败数量：" + (rowCount - 45 - successCount) + "<br>";
		} else {
			result = "总数量：" + (rowCount - 24) + "，成功数量：" + successCount+ "，失败数量：" + (rowCount - 24 - successCount) + "<br>";
		}
		return result + "<font color='red'>" + errorMsg.toString() + "</font>";
	}
	/**
	 * 初始化信息
	 */
    private void initValue() {
    	insertstockAlterMoveDetails.clear();
		updStockAlterMoveDetails.clear();
		errorMsg.delete(0, errorMsg.length());
		pSet.clear();
		insertsql = "";
		updsql = "";
		outwarehouseid = null;
		inwarehouseid = null;
		receiveOrganid = null;
		organname = null;
	    receOrganname=null;
	    beach=null;
	    
	}

/**
 * 获取机构中已上架的产品
 * @param organoecode
 * @throws Exception 
 */
	private void getOrganProduct(String organoecode) throws Exception {
	    Organ organ=appOrgan.getByOecode(organoecode);
	    if (organ!=null ) {
	      String organid=organ.getId();
	     List<OrganProduct> opList= appOrganProduct.getOrganProductByOrganid(organid);
		     if (opList!=null && opList.size()>0) {
			    	 for (Iterator  it =opList.iterator(); it.hasNext();) {
			    		 OrganProduct op= (OrganProduct) it.next();
			    		 pSet.add(op.getProductid());
					}
			 }
		}
	}

	/**
	 * 验证信息
	 * @param styleNo
	 * @param organoecode
	 * @param receiveorganname
	 * @param nccode
	 * @param receivewarehousename
	 * @param billsort 
	 * @throws Exception
	 */
	private void checkVallid(String styleNo, String organoecode,String receiveorganname, String nccode, String receivewarehousename, int billsort)throws Exception {
		// 判断文件是否匹配
		boolean flag = false;
		//判断文件类型
		if (styleNo.equals("")) {
			errorMsg.append("单据信息错误，样式编号不能为空<br>");
			throw new Exception("单据信息错误，样式编号不能为空");
		}
		//wenjian leixing 
		if (billsort==5) {
		}
	    if (billsort==6) {
		}
	    if (billsort==7) {
	    }
	    if (billsort==8) {
	    }
	
		
		
		//判断机构编号是否存在
		if (organoecode == null || organoecode.length() == 0) {
			errorMsg.append("单据信息错误，公司编号不能为空<br>");
			throw new Exception("单据信息错误，公司编号不能为空");
		} else {
			Organ o = appOrgan.getByOecode(organoecode);
			if (o == null) {
				errorMsg.append("单据信息错误，公司编号对应的机构系统中不存在<br>");
				throw new Exception("公司编号对应的机构信息不存在");
			} else {
				organname = o.getOrganname();
				String oid = o.getId();
				Warehouse w = appWarehouse.getWarehouseByOID(oid);
				if (w == null) {
					errorMsg.append("单据信息错误，系统中出库仓库信息不存在<br>");
					throw new Exception("单据信息错误，系统中出库仓库信息不存在");
				}
				if (w != null) {
					outwarehouseid = w.getId();
				}
			}
		}
		//判断收货机构是否存在
		if (receiveorganname == null || receivewarehousename.length() == 0) {
			errorMsg.append("单据信息错误，来往单位不能为空<br>");
			throw new Exception("单据信息错误，来往单位不能为空");
		} else {
			Organ o = appOrgan.getOrganByOrganName(receiveorganname);
			if (o == null) {
				errorMsg.append("单据信息错误，系统中来往单位不存在<br>");
				throw new Exception("单据信息错误，来往单位不能为空");
			}
			if (o != null) {
				// 机构业务往来
				UserVisit uv = appUserVisit.getUserVisitByUserID(user.getUserid());
				String vos = uv.getVisitorgan();
				if (vos != null) {
					String[] buff = vos.split(",");
					if (buff != null && buff.length > 0) {
						for (int i = 0; i < buff.length; i++) {
							if (buff[i].equals(o.getId())) {
								flag = true;
								break;
							}
						}
					}
				}
				if (!flag) {
					errorMsg.append("单据信息错误，与来往单位没有业务往来<br>");
					throw new Exception("单据信息错误，与来往单位没有业务往来");
				}
				// 判断是否匹配
				receiveOrganid = o.getId();
				receOrganname= o.getOrganname();
			}
		}
		// 订购单号是否为空
		if (nccode == null || nccode.equals("")) {
			errorMsg.append("单据信息错误，单据编号不能为空<br>");
			throw new Exception("单据信息错误，单据编号不能为空");
		}
		//来往仓库的名称存在否
		if (receivewarehousename == null || receivewarehousename.length() == 0) {
			errorMsg.append("单据信息错误，仓库名称不能为空<br>");
			throw new Exception("单据信息错误，系统中无对应的来往仓库名称");
		} else {
			Warehouse w = appWarehouse.getWarehouseByWarehouseName(receivewarehousename);
			if (w == null) {
				errorMsg.append("单据信息错误，系统中仓库信息不存在<br>");
				throw new Exception("单据信息错误，系统中仓库信息不存在");
			}
			if (w != null) {
				inwarehouseid = w.getId();
				// 机构许可
				List<WarehouseVisit> lWarehouseVisits = appWarehouseVisit.getWarehouseVisitByUID(user.getUserid());
				if (lWarehouseVisits != null && lWarehouseVisits.size() > 0) {
					for (int i = 0; i < lWarehouseVisits.size(); i++) {
						WarehouseVisit wv = lWarehouseVisits.get(i);
						if (wv != null) {
							if (wv.getWid().equals(inwarehouseid)) {
								flag = true;
								break;
							}
						}
					}
				}
				if (!flag) {
					errorMsg.append("单据信息错误，与仓库没有业务往来<br>");
					throw new Exception("单据信息错误，系统中仓库信息不存在");
				}
				if (!w.getMakeorganid().equals(receiveOrganid)) {
					errorMsg.append("单据信息错误，来往单位和仓库不匹配<br>");
					throw new Exception("单据信息错误，来往单位和仓库不匹配");
				}
			}
		}
	}
	private void updtvolumeSmd(List<StockAlterMoveDetail> updStockAlterMoveDetails2)throws Exception {
		if (updStockAlterMoveDetails != null && updStockAlterMoveDetails.size() > 0) {
			for (int i = 0; i < updStockAlterMoveDetails.size(); i++) {
				StockAlterMoveDetail smd = updStockAlterMoveDetails.get(i);
				StringBuffer sb = new StringBuffer();
				sb.append(" update  stock_alter_move_detail set ");
				sb.append(" quantity= ");
				sb.append(smd.getQuantity());
				sb.append(" where id=");
				sb.append(smd.getId() + ";");
				updsql += sb.toString();
				
				if ((i+1)%Constants.DB_BULK_SIZE==0) {
					asmd.updstockAlterMoveDetailBySql(updsql);
					updsql="";
				}
			}
		}
		if (updsql.length() > 0) {
			asmd.updstockAlterMoveDetailBySql(updsql);
		}
	}
	private void insertvolumeSmd(List<StockAlterMoveDetail> insertstockAlterMoveDetails2)throws Exception {
		if (insertstockAlterMoveDetails != null && insertstockAlterMoveDetails.size() > 0) {
			for (int i = 0; i < insertstockAlterMoveDetails.size(); i++) {
				StockAlterMoveDetail smd = insertstockAlterMoveDetails.get(i);
				StringBuffer sb = new StringBuffer();
				sb.append("insert into stock_alter_move_detail(id,");
				sb.append("samid,");
				sb.append("productid,");
				sb.append("productname ,");
				sb.append("specmode ,");
				sb.append("unitid ,");
				sb.append("nccode ,");
				sb.append("quantity, ");
				sb.append("BoxNum ,");
				sb.append("ScatterNum ,");
				sb.append("Batch ");
				sb.append(")");
				sb.append("values (");
				sb.append(smd.getId() + ",");
				sb.append("'" + smd.getSamid() + "' ,");
				sb.append("'" + smd.getProductid() + "' ,");
				sb.append("'" + smd.getProductname() + "' ,");
				sb.append("'" + smd.getSpecmode() + "' ,");
				sb.append(smd.getUnitid() + ",");
				sb.append("'" + smd.getNccode() + "' ,");
				sb.append(smd.getQuantity()+",");
				sb.append(smd.getBoxnum()+",");
				sb.append(smd.getScatternum()+",");
				sb.append("'"+smd.getBatch()+"'" );
				sb.append(");");
				insertsql += sb.toString();
				
				if ((i+1)%Constants.DB_BULK_SIZE==0) {
					asmd.insertstockAlterMoveDetailBySql(insertsql);
					insertsql="";
				}
			}
		}
		if (insertsql != null && insertsql.length() > 0) {
			asmd.insertstockAlterMoveDetailBySql(insertsql);
		}
	}
	/**
	 * 1:新增；2:更新；3:已复合
	 * @param state
	 * @param sm
	 * @param lBeans
	 * @throws Exception
	 * @throws Exception
	 * @throws Exception
	 */
	private void addSmd(int state, StockAlterMove sm,List<StockAlterMoveDetailFormBean> lBeans) throws Exception {
		if (lBeans != null && lBeans.size() > 0) {
			StockAlterMoveDetail smd = null;
			for (int i = 0; i < lBeans.size(); i++) {
				StockAlterMoveDetailFormBean sf = lBeans.get(i);
				String productnccode = sf.getProductnccode();
				Product p = ap.findProductByNccode(productnccode);
				List<StockAlterMoveDetail> list = asmd.getStockAlterMoveDetailByPiidPid(sm.getId(), p.getId());
				if (list.size() == 0) {
					addSmd(sf, sm, p);
				} else {
					smd = list.get(0);
				    Double  newQuantity=sf.getQuantity()*p.getBoxquantity();
					//updSmd(sf, smd);
					updSmd(newQuantity,smd);
				}
			}
		}
	}
	/**
	 * 更新详情
	 * @param newQuantity
	 * @param smd
	 */
	private void updSmd(Double newQuantity, StockAlterMoveDetail smd) {
		smd.setQuantity(newQuantity + smd.getQuantity());
		updStockAlterMoveDetails.add(smd);
	}
	/**
	 * 更新详情
	 * 
	 * @param sf
	 * @param smd
	 */
	private void updSmd(StockAlterMoveDetailFormBean sf,StockAlterMoveDetail smd) {
		smd.setQuantity(sf.getQuantity() + smd.getQuantity());
		updStockAlterMoveDetails.add(smd);
	}
	private void addSmd(StockAlterMoveDetailFormBean sf, StockAlterMove sm,Product p) throws  Exception {
		
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(sm.getId());
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
		smd.setUnitid(p.getSunit());
		smd.setNccode(sm.getNccode());
		smd.setBatch(beach);
		smd.setQuantity(sf.getQuantity()*p.getBoxquantity());
		smd.setBoxnum(sf.getQuantity().intValue());
		smd.setScatternum(0.0);
		
		// asmd.addStockAlterMoveDetail(smd);
		insertstockAlterMoveDetails.add(smd);
	}

	/**
	 * 获取对应的值
	 * 
	 * @param sheet
	 * @param col
	 * @param row
	 * @return
	 */
	private String getCellValue(Sheet sheet, int col, int row) {
		Cell cell = sheet.getCell(col, row);
		if (cell != null) {
			String value = cell.getContents();
			if (!StringUtil.isEmpty(value)) {
				return value.trim();
			}
		}
		return null;
	}
}
