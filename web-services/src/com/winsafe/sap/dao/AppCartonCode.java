package com.winsafe.sap.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.CartonCode;
@SuppressWarnings("unchecked")
public class AppCartonCode {
	
	public void addCartonCode(CartonCode d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public List getCartonCode(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from CartonCode as o "+whereSql +" order by o.cartonCode desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List getCartonCodeOrder(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from CartonCode as o "+whereSql +" order by o.cartonCode ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public List<CartonCode> getCartonCodeByPrintjobId(Integer printJobId) {
		String hql = " from CartonCode as c where c.printJobId= " + printJobId + " order by printSeq";
		return  EntityManager.getAllByHql(hql);
	}
	
	
	/**
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 * 根据箱码获取产品号
	 */
	
	public CartonCode getByCartonCode(String code) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("code", code);
		String sql = " from CartonCode as c where cartonCode=:code";
		return (CartonCode) EntityManager.find(sql, param);
	}
	/**
	 * 根据mpin码查找箱码
	 * Create Time 2015-1-14 下午06:08:30 
	 * @param code
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public CartonCode getByOutPin(String outPinCode) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("outPinCode", outPinCode);
		String sql = " from CartonCode as c where outPinCode=:outPinCode";
		return (CartonCode) EntityManager.find(sql, param);
	}
	
	/**
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 * 根据原Mpin码得到cartoncode
	 */
	
	public CartonCode getByOutAndIn(String code) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("code", code);
		String sql = " from CartonCode as c where outPinCode=:code or innerPinCode=:code";
		return (CartonCode) EntityManager.find(sql, param);
	}
	
	
	/**
	 * @author ryan.xi
	 * @param printJobId
	 * @return
	 * @throws Exception
	 * 根据打印任务ID获取要导出的箱码数据
	 */
	public List<CartonCode> getCartonCodeForExcPut(Integer printJobId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("printJobId", printJobId);
		String hql = "select new CartonCode(cartonCode, palletCode, outPinCode,  printSeq) from CartonCode where printJobId = :printJobId order by palletCode, cartonCode";
		return  EntityManager.getAllByHql(hql, param);
	}

	public void delByPrintJobId(Integer printJobId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("printJobId", printJobId);
		String hql = "delete from Carton_code where Print_job_id = ?";
		EntityManager.executeUpdateBySql(hql, param);
	}

	public void addCartonCodes(TreeSet<CartonCode> cartonCodes,
			Map<String, Integer> countInPalletMap) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(CartonCode cc :cartonCodes) {
			StringBuffer sb = new StringBuffer();
			sb.append("insert into Carton_code (Carton_code, Pallet_code, out_pin_code, inner_pin_code, create_date, material_code, Product_ID, Print_job_id, primary_code_status, print_seq, count_in_pallet, carton_seq) values ('");
			sb.append(cc.getCartonCode());
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getPalletCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getOutPinCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getInnerPinCode()));
			sb.append("' , SYSDATE, '");
			sb.append(StringUtil.removeNull(cc.getMaterialCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getProductID()));
			sb.append("' , ");
			sb.append(cc.getPrintJobId());
			sb.append(" , ");
			sb.append(cc.getPrimaryCodeStatus());
			sb.append(" , ");
			sb.append(cc.getPrintSeq());
			sb.append(" , ");
			sb.append(countInPalletMap.get(cc.getPalletCode()) == null ? "''" : countInPalletMap.get(cc.getPalletCode()));
			sb.append(" , ");
			sb.append(cc.getCartonSeq() == null ? "''" :cc.getCartonSeq());
			sb.append(")");
			batchSqls.add(sb.toString());
		}
		EntityManager.executeBatch(batchSqls);
	}
	
	public void addCartonCodes(TreeSet<CartonCode> cartonCodes) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(CartonCode cc :cartonCodes) {
			StringBuffer sb = new StringBuffer();
			sb.append("insert into Carton_code (Carton_code, Pallet_code, out_pin_code, inner_pin_code, create_date, material_code, Product_ID, Print_job_id, primary_code_status, print_seq, count_in_pallet, carton_seq) values ('");
			sb.append(cc.getCartonCode());
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getPalletCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getOutPinCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getInnerPinCode()));
			sb.append("' , SYSDATE, '");
			sb.append(StringUtil.removeNull(cc.getMaterialCode()));
			sb.append("' , '");
			sb.append(StringUtil.removeNull(cc.getProductID()));
			sb.append("' , ");
			sb.append(cc.getPrintJobId());
			sb.append(" , ");
			sb.append(cc.getPrimaryCodeStatus());
			sb.append(" , ");
			sb.append(cc.getPrintSeq());
			sb.append(" , ");
			sb.append(cc.getCountInPallet());
			sb.append(" , ");
			sb.append(cc.getCartonSeq() == null ? "''" :cc.getCartonSeq());
			sb.append(")");
			batchSqls.add(sb.toString());
		}
		EntityManager.executeBatch(batchSqls);
	}
	
	/**
	 * 判断箱码存在
	 * @param code
	 * @return
	 */
	public boolean isCartonCodeExists(String code) {
		Map<String,Object> param = new HashMap<>();
	    param.put("code", code);
		String sql = "select count(*) from Carton_Code where carton_Code =?";
		return EntityManager.getRecordCountBySql(sql, param) > 0;
	}
	
	/**
	 * 根据托码获取箱码
	 * @param code
	 * @return 
	 * @throws Exception 
	 */
	public List<CartonCode> getByPalletCode(String code) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("code", code);
		String hql = " from CartonCode  where palletCode =:code";
		return  EntityManager.getAllByHql(hql, param);
	}

	public List<Map<String, String>> getTraceInfo(String cartonCode) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("cartonCode", cartonCode);
		String hql = "select pj.PACKAGING_DATE packagingDate, pj.MATERIAL_BATCH_NO materialBatchNo, p.INSPECTIONINSTITUTION, p.PRODUCTNAME,p.REGCERTUSER,pj.batch_number batch,pj.PRINTING_DATE inspectiondate,p.standardname,p.id productid,o.organname,o.organType,o.organmodel,PJ.CREATE_DATE createDate,pj.print_job_id printJobId,p.SPECMODE,pj.production_Date productionDate,p.productType "+
			",p.regcertcodefixed,pj.PLANT_NAME plantName,pj.MATERIAL_CODE materialCode,pj.EXPIRY_DATE expiryDate,cc.OUT_PIN_CODE outPincode "+
	        "from CARTON_CODE cc " +
			"join PRODUCT p on CC.PRODUCT_ID=p.ID " + 
			"LEFT JOIN PRINT_JOB pj on cc.print_job_id = pj.print_job_id " +
			"LEFT JOIN Organ o ON o.oecode = pj.plant_code " +
			"LEFT JOIN UPLOAD_PRODUCE_LOG upl on upl.printjobid=pj.print_job_id " + 
			"where CC.CARTON_CODE = ?";
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		map.put("cartonCode", cartonCode);
//		return EntityManager.getAllMapListBySql(hql, map);
		return EntityManager.jdbcquery(hql, param);
	}
	
	public List<Map<String, String>> getTraceInfoByPrintJobId(int printJobId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("printJobId", printJobId);
		String hql = "select pj.PACKAGING_DATE packagingDate, pj.MATERIAL_BATCH_NO materialBatchNo,p.INSPECTIONINSTITUTION, p.PRODUCTNAME,p.REGCERTUSER,pj.batch_number batch,pj.PRINTING_DATE inspectiondate,p.standardname,p.id productid,o.organname,o.organType,o.organmodel,PJ.CREATE_DATE createDate,pj.print_job_id printJobId,p.SPECMODE,pj.production_Date productionDate,p.productType,pj.PLANT_NAME plantName,pj.MATERIAL_CODE materialCode,pj.EXPIRY_DATE expiryDate from PRINT_JOB pj " +
			"join PRODUCT p on p.id = PJ.PRODUCT_ID and PJ.PRINT_JOB_ID = ? " + 
			" LEFT JOIN Organ o ON o.oecode = pj.plant_code ";
		return EntityManager.jdbcquery(hql, param);
	}

	public List<String> getCodeListByPrintJobId(Integer printJobId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("printJobId", printJobId);
		String hql = "select cartonCode from CartonCode  where printJobId =:printJobId order by printSeq";
		return  EntityManager.getAllByHql(hql, param);
	}
	
	public static void main(String[] args) throws Exception {
		AppCartonCode apc = new AppCartonCode();
		Object result = apc.getCodeListByPrintJobId(5445);
//		List result = apc.getCartonCodeForExcPut(5444);
//		apc.delByPrintJobId(5444);
//		HibernateUtil.commitTransaction();
		System.out.println("--------result:"+result);
	}

	public List<Map<String, String>> getFlowByCartonCode(String cartonCode) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("idcode", cartonCode);
	    StringBuffer sql = new StringBuffer();
	    sql.append("select outw.warehousename fromW, outo.organname fromOrg, to_char(tt.auditdate,'yyyy-mm-dd hh24:mi:ss') deliveryDate, inw.warehousename toW, ino.organname toOrg,tt.bsort, tt.billNo,tt.nccode sapNo, ");
	    sql.append("CASE WHEN tt.bsort=1 THEN '发货单' WHEN tt.bsort=2 THEN '转仓单' ELSE '退货单' END billType,  ");
	    sql.append("CASE WHEN tt.bsort=1 THEN to_char(sam.receiveDate,'yyyy-mm-dd hh24:mi:ss') WHEN tt.bsort=2 THEN to_char(sm.receiveDate,'yyyy-mm-dd hh24:mi:ss') ELSE to_char(ow.receiveDate,'yyyy-mm-dd hh24:mi:ss') END receiveDate from TAKE_TICKET_IDCODE tti  ");
	    sql.append("join TAKE_TICKET tt on tti.ttid=tt.id and tti.idcode=? ");
	    sql.append("join WAREHOUSE outw on outw.id=tt.warehouseid ");
	    sql.append("join organ outo on outo.id = tt.oid ");
	    sql.append("join WAREHOUSE inw on inw.id=tt.inwarehouseid  ");
	    sql.append("join organ ino on ino.id = tt.inoid ");
	    sql.append("LEFT JOIN STOCK_ALTER_MOVE sam on sam.id = tt.billno and tt.bsort =1 ");
	    sql.append("LEFT JOIN STOCK_MOVE sm on sm.id = tt.billno and tt.bsort =2 ");
	    sql.append("LEFT JOIN ORGAN_WITHDRAW ow on ow.id = tt.billno and tt.bsort =7 ");
	    sql.append("order by tt.auditdate desc ");
		return EntityManager.jdbcquery(sql.toString(), param);
	}
}

