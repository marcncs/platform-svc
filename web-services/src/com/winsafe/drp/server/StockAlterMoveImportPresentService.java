package com.winsafe.drp.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMoveDetailFormBean;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 赠送单
 *
 */
public class StockAlterMoveImportPresentService {

	AppStockAlterMove asm = new AppStockAlterMove();
	AppProduct ap = new AppProduct();
	AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
	AppOrgan appOrgan=new  AppOrgan();
	AppWarehouse appWarehouse=new AppWarehouse();
	AppBaseResource appBaseResource=new AppBaseResource();
	AppUserVisit appUserVisit=new  AppUserVisit();
	AppWarehouseVisit appWarehouseVisit=new AppWarehouseVisit();
	
	String  insertsql;
	String  updsql;
	List< StockAlterMoveDetail> insertstockAlterMoveDetails=new ArrayList<StockAlterMoveDetail>();
	List<StockAlterMoveDetail>  updStockAlterMoveDetails=new ArrayList<StockAlterMoveDetail>();
	
	public StockAlterMoveImportPresentService() {
	}
	
	public String dealFile(String filepath, UsersBean users, String includewhid) {
		Workbook wb = null;
		Date currentDate =Dateutil.StringToDate(Dateutil.getCurrentDateString()) ;
		StringBuilder errorMsg = new StringBuilder();
		int rowCount = 0;
		int successCount = 0;
		//初始化
		insertstockAlterMoveDetails.clear();
		updStockAlterMoveDetails.clear();
		insertsql="";
		updsql="";
		try {
			wb = Workbook.getWorkbook(new File(filepath));
			Sheet sheet = wb.getSheet(0);
			rowCount = sheet.getRows();

			StockAlterMove sm = null;
			int state = 0;
			//获取订购单信息
			int num=1;
			//仓库信息
			String  outwarehouseid = null;
			String  inwarehouseid = null;
			String receiveOrganid = null;
			String organname=null;
			

			String styleNo=getCellValue(sheet,    1, num++);// 样式编号
			String styleName=getCellValue(sheet,  1, num++);// 样式名称
			String organName=getCellValue(sheet,  1, num++); // 公司全名
			String organoecode=getCellValue(sheet ,   1, num++); // 公司编号
			String  olinkmanvalue=getCellValue(sheet,1,num++); //联系人
			String address=getCellValue(sheet,1,num++); //地址
			String otel=getCellValue(sheet,1,num++); //电话
			String zipcode=getCellValue(sheet,1,num++); //邮编
			String taxNo=getCellValue(sheet,1,num++); //税号
			String bank=getCellValue(sheet,1,num++); //开户银行
			String bankno=getCellValue(sheet,1,num++); //公司账号
			String organremake=getCellValue(sheet,1,num++); //公司备注
			String makeorganman=getCellValue(sheet,1,num++); //制单人
			String makedate=getCellValue(sheet,1,num++); //日期
			String nccode=getCellValue(sheet,1,num++); //单据编号
			String operateman=getCellValue(sheet,1,num++); //经手人
			String receiveorganname=getCellValue(sheet,1,num++); //往来单位名称
			String receivewarehousename=getCellValue(sheet,1,num++); //出货仓库名称
			String explain=getCellValue(sheet,1,num++); //说明
			String remake=getCellValue(sheet,1,num++); //摘要
			String iswaybill=getCellValue(sheet,1,num++); //是否过账
			
	          
			
			//验证信息是否有效
			
			//判断文件是否匹配
			if (styleNo.equals("")) {
				errorMsg.append("单据信息错误，样式编号不能为空<br>");
				throw new Exception("单据信息错误，样式编号不能为空");
			}
			if (!"2710".equals(styleNo)) {
				errorMsg.append("单据信息错误，赠送单据样式编号有误<br>");
				throw new Exception("单据信息错误，赠送单据样式编号有误");
			}
			
			boolean flag=false;
			if (organoecode==null || organoecode.length()==0 ) {
				errorMsg.append("单据信息错误，公司编号不能为空<br>");
				throw new Exception("单据信息错误，公司编号不能为空");
			}else {
				Organ o = appOrgan.getByOecode(organoecode);
				if (o==null ) {
					errorMsg.append("单据信息错误，公司编号对应的机构系统中不存在<br>");
					throw new Exception("公司编号对应的机构信息不存在");
				}else {
					 organname=o.getOrganname();
					 String oid= o.getId();
					 Warehouse w =appWarehouse.getWarehouseByOID(oid);
					 if (w==null) {
						 errorMsg.append("单据信息错误，系统总仓库信息不存在<br>");
						 throw new Exception("单据信息错误，系统总仓库信息不存在");
					}
					 if (w!=null) {
						 outwarehouseid=w.getId();
					}
				}
			}
			
			if (receiveorganname==null || receivewarehousename.length()==0 ) {
				errorMsg.append("单据信息错误，来往单位不能为空<br>");
				throw new Exception("单据信息错误，来往单位不能为空");
			}else {
				Organ o = appOrgan.getOrganByOrganName(receiveorganname);
				if (o==null) {
					errorMsg.append("单据信息错误，系统中来往单位不存在<br>");
					throw new Exception("单据信息错误，来往单位不能为空");
				}
				if (o!=null) {
					//机构业务往来
					UserVisit uv =appUserVisit.getUserVisitByUserID(users.getUserid());
				  	String  vos= uv.getVisitorgan();
				  	if (vos!=null ) {
				  		String[] buff=vos.split(",");
				  		if (buff!=null&& buff.length>0) {
				  			for (int i = 0; i < buff.length; i++) {
								  if (buff[i].equals(o.getId())){
									  flag=true;
									  break;
								  }
							}
						}
					}
				  	if (!flag) {
				  		errorMsg.append("单据信息错误，与来往单位没有业务往来<br>");
						throw new Exception("单据信息错误，与来往单位没有业务往来");
					}
				  	//判断是否匹配
				   receiveOrganid=o.getId();
				}
			}
			//单号
			if (nccode==null || nccode.equals("")) {
				errorMsg.append("单据信息错误，单据编号不能为空<br>");
				throw new Exception("单据信息错误，单据编号不能为空");
			}
			if (receivewarehousename==null || receivewarehousename.length()==0 ) {
				errorMsg.append("单据信息错误，仓库名称不能为空<br>");
				throw new Exception("单据信息错误，系统中无对应的来往仓库名称");
			}else {
				 Warehouse w =appWarehouse.getWarehouseByWarehouseName(receivewarehousename);
				if (w==null ) {
					 errorMsg.append("单据信息错误，系统中仓库信息不存在<br>");
					 throw new Exception("单据信息错误，系统中仓库信息不存在");
				}
				 if (w!=null ) {
					inwarehouseid=w.getId();
						//机构许可
					List<WarehouseVisit>   lWarehouseVisits=appWarehouseVisit.getWarehouseVisitByUID(users.getUserid());
						
					 if (lWarehouseVisits!=null && lWarehouseVisits.size()>0) {
						  for (int i = 0; i < lWarehouseVisits.size(); i++) {
							  WarehouseVisit wv=lWarehouseVisits.get(i);
							  if (wv!=null) {
								  if (wv.getWid().equals(inwarehouseid)) {
									  flag=true;
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
					sm.setTotalsum(null);
					sm.setPaymentmode(null);
					sm.setInvmsg(null);
					sm.setTransportaddr(null);
					sm.setTransportaddr(null);
					sm.setIsmaketicket(0);
					sm.setTickettitle(null);
					sm.setOlinkman(olinkmanvalue);
					sm.setOtel(otel);
					sm.setIsreceiveticket(0);
					sm.setMovecause(null);
					sm.setIsshipment(0);
					sm.setRemark(remake);
					sm.setIsaudit(0);
					sm.setIsblankout(0);
					sm.setIscomplete(0);
					sm.setAuditdate(null);
					sm.setAuditid(null);
					sm.setMakeorganid(users.getMakeorganid());
					sm.setMakeorganidname(organname);
					sm.setMakedeptid(users.getUserid());
					sm.setMakedate(currentDate);
					sm.setMakeid(users.getUserid());
					sm.setIstally(0);
					sm.setTallyid(0);
					sm.setReceiveorganid(receiveOrganid);
					sm.setReceiveorganidname(receiveorganname);
					sm.setReceivedate(null);
					sm.setNccode(nccode);
					
					StringBuffer keyscontent = new StringBuffer();
					keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",")
					.append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
					sm.setTotalsum(0d);
					sm.setTakestatus(0);
					sm.setKeyscontent(keyscontent.toString());
					sm.setBsort(8);
					asm.saveStockAlterMove(sm);

				} else if (sm.getIsaudit() == null || sm.getIsaudit() == 0) { // 更新操作
					state = 2;
					sm.setMovedate(Dateutil.getCurrentDate());
					sm.setOutwarehouseid(outwarehouseid);
					sm.setInwarehouseid(inwarehouseid);
					sm.setTotalsum(null);
					sm.setPaymentmode(null);
					sm.setInvmsg(null);
					sm.setTransportaddr(null);
					sm.setTransportaddr(null);
					sm.setIsmaketicket(null);
					sm.setTickettitle(null);
					sm.setOlinkman(olinkmanvalue);
					sm.setOtel(otel);
					sm.setMovecause(null);
					sm.setRemark(remake);
					sm.setIsaudit(0);
					sm.setMakeorganid(users.getMakeorganid());
					sm.setMakeorganidname(organname);
					sm.setMakedeptid(users.getUserid());
					sm.setMakedate(currentDate);
					sm.setMakeid(users.getUserid());
					sm.setIsshipment(null);
					sm.setIstally(null);
					sm.setTallyid(null);
					sm.setReceiveorganid(receiveOrganid);
					sm.setReceiveorganidname(receiveorganname);
					sm.setReceivedate(null);
					sm.setNccode(nccode);
					sm.setTotalsum(0d);
					sm.setTakestatus(0);
					
					StringBuffer keyscontent = new StringBuffer();
					keyscontent.append(sm.getId()).append(",").append(sm.getOlinkman()).append(",").append(sm.getOtel()).append(",").append(sm.getMakeorganidname());
					sm.setKeyscontent(keyscontent.toString());
					asm.saveStockAlterMove(sm);
				} else {
					errorMsg.append("单据信息错误，该单据不能修改<br>");
					state = 3;
					throw new Exception("单据信息错误，该单据不能修改");
				}
			 //处理订单的详情信息
			    List<StockAlterMoveDetailFormBean> lBeans=new ArrayList<StockAlterMoveDetailFormBean>();
			    StockAlterMoveDetailFormBean stockAlterMoveDetailFormBean=null;
				for (int i = 24; i < rowCount; i++) {
					 stockAlterMoveDetailFormBean=new StockAlterMoveDetailFormBean();
					// 读取文件
					String productNcode=getCellValue(sheet,1,i);
					
					if (productNcode==null ||productNcode.length()==0 ) {
						errorMsg.append("单据信息错误，产品编号不能为空<br>");
						continue;
					}
					List  pList=  ap.getProductByNccode(productNcode);
					if (pList==null || pList.size()==0) {
						errorMsg.append("单据信息错误，系统中不存在对应产品信息<br>");
						continue;
					}
					String productname=getCellValue(sheet,2,i);
					String productidcode=getCellValue(sheet,3,i);
					String unit=getCellValue(sheet,4,i);
					String totalmany=getCellValue(sheet,5,i);
					
					
					//获取到单位对应的
					if (unit!=null) {
						BaseResource  bs =appBaseResource.getBaseResourceKey("CountUnit",unit);
						if (bs!=null) {
							stockAlterMoveDetailFormBean.setUnit(bs.getTagsubkey());
						}
					}
					stockAlterMoveDetailFormBean.setProductnccode(productNcode);
					stockAlterMoveDetailFormBean.setProductName(productname);
					stockAlterMoveDetailFormBean.setIdcode(productidcode);
					if (totalmany!=null ) {
						stockAlterMoveDetailFormBean.setQuantity(Double.valueOf(totalmany));
					}
					lBeans.add(stockAlterMoveDetailFormBean);
					stockAlterMoveDetailFormBean=null;
					successCount++;
				}
				//整理数据
				//生成或者更新订购单明细
				addSmd(state,sm,lBeans);
				//批量更新
				insertvolumeSmd(insertstockAlterMoveDetails);
				updtvolumeSmd(updStockAlterMoveDetails);
				HibernateUtil.commitTransaction();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (wb != null) {
				wb.close();
			}
		}
		String t="总数量："+(rowCount-24)+"，成功数量："+successCount+"，失败数量："+(rowCount-24-successCount)+"<br>";
		return t+"<font color='red'>"+errorMsg.toString()+"</font>";
	}
	/**
	 * 批量更新操作
	 * @param updStockAlterMoveDetails2
	 * @throws Exception 
	 */
	private void updtvolumeSmd(List<StockAlterMoveDetail> updStockAlterMoveDetails) throws Exception {
		 if (updStockAlterMoveDetails!=null && updStockAlterMoveDetails.size()>0) {
			  for (int i = 0; i < updStockAlterMoveDetails.size(); i++) {
				  StockAlterMoveDetail smd=updStockAlterMoveDetails.get(i);
				    StringBuffer sb = new StringBuffer();
			   sb.append(" update  stock_alter_move_detail set ");
			   sb.append(" quantity= ");
			   sb.append(smd.getQuantity());
			   sb.append(" where id=");
			   sb.append(smd.getId()+";");
			   updsql+=sb.toString();
			}
			  
		}
     if (updsql.length()>0) {
    	 asmd.updstockAlterMoveDetailBySql(updsql);
      }
		 
	}
	/**
	 * 批量插入操作
	 * @param insertstockAlterMoveDetails2
	 * @throws Exception 
	 */
	  private void insertvolumeSmd(List<StockAlterMoveDetail> insertstockAlterMoveDetails) throws Exception {
		
		  if (insertstockAlterMoveDetails!=null && insertstockAlterMoveDetails.size()>0) {
			  for (int i = 0; i < insertstockAlterMoveDetails.size(); i++) {
				  StockAlterMoveDetail smd=insertstockAlterMoveDetails.get(i);
				    StringBuffer sb = new StringBuffer();
					sb.append("insert into stock_alter_move_detail(id,");
					sb.append("samid,");
					sb.append("productid,");
					sb.append("productname ,");
					sb.append("specmode ,");
					sb.append("unitid ,");
					sb.append("nccode ,");
					sb.append("quantity ");
					sb.append(")");
					sb.append("values (");
					sb.append(smd.getId()+",");
					sb.append("'"+smd.getSamid()+"' ,");
					sb.append("'"+smd.getProductid()+"' ,");
					sb.append("'"+smd.getProductname()+"' ,");
					sb.append("'"+smd.getSpecmode()+"' ,");
					sb.append(smd.getUnitid()+",");
					sb.append("'"+smd.getNccode()+"' ,");
					sb.append(smd.getQuantity());
					sb.append(");");
					insertsql+=sb.toString();
			}
			  
		}
		  
		  if (insertsql!=null && insertsql.length() >0) {
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
     */
	private void addSmd(int state, StockAlterMove sm,List<StockAlterMoveDetailFormBean> lBeans) throws Exception {
		
		if (lBeans!=null && lBeans.size() >0) {
			StockAlterMoveDetail smd = null;
			for(int  i=0;i<lBeans.size();i++ ){
				StockAlterMoveDetailFormBean sf=lBeans.get(i);
			    String  productnccode =	sf.getProductnccode();
			     Product p= ap.findProductByNccode(productnccode);
			      List<StockAlterMoveDetail> list = asmd.getStockAlterMoveDetailByPiidPid(sm.getId(),p.getId());
			      if (list.size()==0) {
					addSmd(sf,sm,p);
				  }else {
				        smd = list.get(0);
					    updSmd(sf,smd);
//					smd.setProductid(p.getId());
//					smd.setProductname(p.getProductname());
//					smd.setSpecmode(p.getSpecmode());
//					smd.setQuantity(sf.getQuantity()+smd.getQuantity());
//					asmd.updstockAlterMove(smd);
				}
			}
		}
	}
	
/**
 * 更新详情
 * @param sf
 * @param smd
 */
private void updSmd(StockAlterMoveDetailFormBean sf, StockAlterMoveDetail smd) {
	smd.setQuantity(sf.getQuantity()+smd.getQuantity());
	updStockAlterMoveDetails.add(smd);
}
/**
 * 新增详情
 * @param sf
 * @param sm
 * @param p
 * @throws Exception
 */

private void addSmd(StockAlterMoveDetailFormBean sf, StockAlterMove sm,Product p) throws  Exception {
		StockAlterMoveDetail smd = new StockAlterMoveDetail();
		smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("stock_alter_move_detail", 0, "")));
		smd.setSamid(sm.getId());
		smd.setProductid(p.getId());
		smd.setProductname(p.getProductname());
		smd.setSpecmode(p.getSpecmode());
		smd.setUnitid(p.getSunit());
		smd.setNccode(sm.getNccode());
		smd.setQuantity(sf.getQuantity());
		//asmd.addStockAlterMoveDetail(smd);
		insertstockAlterMoveDetails.add(smd);
		/**
		 * 
	    StringBuffer sb = new StringBuffer();
		sb.append("insert into stock_alter_move_detail(id,");
		sb.append("samid,");
		sb.append("productid,");
		sb.append("productname ,");
		sb.append("specmode ,");
		sb.append("unitid ,");
		sb.append("nccode ,");
		sb.append("quantity ");
		sb.append(")");
		sb.append(" select ");
		sb.append("(select max(id) from stock_alter_move_detail)+row_number() over ( order by id ) as id,");
		sb.append("'"+sm.getId()+"',");
		sb.append("'"+p.getId()+"',");
		sb.append("'"+p.getProductname()+"',");
		sb.append("'"+p.getSpecmode()+"',");
		sb.append(p.getSunit()+",");
		sb.append("'"+sm.getNccode()+"',");
		sb.append(sf.getQuantity() );
		sb.append(" from ");
		sb.append("stock_alter_move_detail   ");
		sb.append(";");
		//更新表id
		sb.append("update stock_alter_move_detail set id =(select max(id)+1 from stock_alter_move_detail ) ; ");
		//sb.append("where TableName='stock_alter_move_detail';");
		insertsql+=sb.toString();
		*/
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
