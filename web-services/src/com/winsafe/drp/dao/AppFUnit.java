package com.winsafe.drp.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.util.HtmlSelect;

public class AppFUnit {

	public void addFUnit(Object ob)throws Exception{
		EntityManager.save(ob);
	}
	
	public void addFUnit(Object ob[])throws Exception{
		EntityManager.save(ob);
	}
	
	public Map<String,FUnit> getAllMap() {
		Map<String, FUnit> resultMap = new HashMap<String, FUnit>();
		List<FUnit> list = getAll();
		for(FUnit fUnit : list){
			if(fUnit == null) continue;
			resultMap.put(fUnit.getProductid()+"_"+fUnit.getFunitid(), fUnit);
		}
		return resultMap;
	}
	public List<FUnit> getAll(){
		String sql=" from FUnit ";
		return  EntityManager.getAllByHql(sql);
	}
	
	public void delFUnitByPID(String id)throws Exception{
		String sql="delete from F_Unit where productid='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delFUnitByPIDNoIsMain(String id,Integer ismain)throws Exception{
		String sql="delete from F_Unit where productid='"+id+"' and ismain="+ismain;
		EntityManager.updateOrdelete(sql);
	}
	
	public List getFUnitByProductID(String productid)throws Exception{
		String sql=" from FUnit where productid='"+productid+"' order by id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getFUnitByProductIDNoIsMain(String productid)throws Exception{
		String sql=" from FUnit where productid='"+productid+"' and ismain=0";
		return  EntityManager.getAllByHql(sql);
	}
	
	public FUnit getFUnitExceptFUnitid(String productid,String exceptUnit){
		String sql=" from FUnit where productid='"+productid+"' and funitid not in("+exceptUnit+")";
		return  (FUnit) EntityManager.find(sql);
	}
	
	public FUnit getFUnit(String productid,int unitid){
		String sql=" from FUnit where productid='"+productid+"' and funitid="+unitid;
		return  (FUnit) EntityManager.find(sql);
	}
	
	public double getXQuantity(String productid, int unitid)throws Exception{
		String sql=" select xquantity from FUnit where productid='"+productid+"' and funitid="+unitid;
		return EntityManager.getdoubleSum(sql);		
	}
	
	/**
	 * 得到实际数量
	 * @param productid
	 * @return
	 */
	public double getPkgQuantityInProductIncomeIdcode(String productid,String piid){
		ProductIncomeIdcode c; 
		String sql=" select packquantity from ProductIncomeIdcode where productid='"+productid+"' and piid = '"+ piid +"'";
		return EntityManager.getdoubleSum2(sql);
	}
	/**
	 * 得到实际数量
	 * @param productid
	 * @return
	 */
	public double getPkgQuantityInTakeTicket(String productid,String ttid){
		TakeTicketIdcode c; 
		
		String sql=" select packquantity from TakeTicketIdcode where productid='"+productid+"' and ttid = '"+ ttid +"'";
		return EntityManager.getdoubleSum2(sql);
	}
	
	public double getPkgQuantityInTakeTicket(String productid,String ttid,String batch)
	{
		String sql=" select packquantity from TakeTicketIdcode where productid='"+productid+"' and ttid = '"+ ttid +"' and batch = '"+ batch +"'";
		return EntityManager.getdoubleSum2(sql);
	}
	
	
	public double getQuantity(String productid, int unitid, double quantity)throws Exception{
		double xquantity = getXQuantity(productid, unitid);	
		if(0.0 == xquantity)
		{
			throw new RuntimeException("单位不存在");
		}
		return ArithDouble.mul(quantity, xquantity);
	}
	
	public double getQuantity2(String productid, int unitid, double quantity)throws Exception{
		double xquantity = getXQuantity(productid, unitid);	
		if(0.0 == xquantity)
		{
			return xquantity;
		}
		return quantity * xquantity;
	}
	
	public double getDivQuantity(String productid, int unitid, double quantity)throws Exception{
		double xquantity = getXQuantity(productid, unitid);	
		if(0.0 == xquantity)
		{
			throw new RuntimeException("单位不存在");
		}
		return ArithDouble.div(quantity, xquantity);
	}
	

	public int getStockpileQuantity(String productid, int ounitid, double minQuantity)throws Exception{		
		double xquantity = getXQuantity(productid, ounitid);		
		return (int)Math.floor(minQuantity/xquantity);	
	}
	
	public double getStockpileQuantity2(String productid, int ounitid, double minQuantity)throws Exception{		
		if ( minQuantity == 0 ){
			return 0.00;
		}
		double xquantity = getXQuantity(productid, ounitid);
		if(0.0 == xquantity)
			return minQuantity;
		return ArithDouble.div(minQuantity, xquantity);
	}
	
	
	public String getStockpileQuantity(HttpServletRequest request,String productid, int sUnitid, int minUnitid, double minQuantity)throws Exception{
		if ( sUnitid == minUnitid ){
			return minQuantity+HtmlSelect.getResourceName(request, "CountUnit", minUnitid);
		}
		
		double xquantity = getXQuantity(productid, sUnitid);		
		int a = (int)Math.floor(minQuantity/xquantity);
		int b = (int)(minQuantity-a*xquantity);
		if ( b == 0 ){
			return a+HtmlSelect.getResourceName(request, "CountUnit", sUnitid);
		}
		return a+HtmlSelect.getResourceName(request, "CountUnit", sUnitid)+b+HtmlSelect.getResourceName(request, "CountUnit", minUnitid);
	}
	
	
	
	public List<FUnit> queryFUnitByPids(String ids) throws Exception{
		String sql = "  from FUnit where productid in(" + ids+ ")";
		List<FUnit> ps = EntityManager.getAllByHql(sql);
		return ps;
		
	}
	
	public List<FUnit> queryNotMainFUnitByPid(String id) throws NotExistException{
		String sql = "  from FUnit where productid = '" + id + "' and ismain = 0";
		List<FUnit> ps = EntityManager.getAllByHql(sql);
		if(ps == null || ps.size() == 0) {
			throw new NotExistException("找不到编号为"+ id +"的产品对应的包装比例关系");
		}
		return ps;
		
	}

	public List<FUnit> getNotMainFUnitsByMCode(String materialCode) {
		String hql = "  from FUnit f join Product p on f.productid = p.id where p.mCode = '" + materialCode +"' and f.ismain = 0";
		List<FUnit> ps = EntityManager.getAllByHql(hql);
		return ps;
	}
	
	public Integer getOuterPackFUnitsByProductId(String productId) {
//		String hql = "select FUNITID from F_UNIT where XQUANTITY in ( select max(XQUANTITY) from F_UNIT where PRODUCTID = '" + productId +"')";
		String hql = "select FUNITID from F_UNIT where XQUANTITY in ( select max(XQUANTITY) from F_UNIT where PRODUCTID = '" + productId +"') and PRODUCTID = '" + productId +"'";
		BigDecimal ps = (BigDecimal)EntityManager.queryResultByNativeSql(hql);
		if(ps != null) {
			return ps.intValue();
		}
		return null;
	}
	
	public Double getQuantityByProductId(String productId) {
//		String hql = "select FUNITID from F_UNIT where XQUANTITY in ( select max(XQUANTITY) from F_UNIT where PRODUCTID = '" + productId +"')";
		String hql = "select XQUANTITY from F_UNIT where XQUANTITY = ( select max(XQUANTITY) from F_UNIT where PRODUCTID = '" + productId +"') and PRODUCTID = '" + productId +"'";
		BigDecimal ps = (BigDecimal)EntityManager.queryResultByNativeSql(hql);
		if(ps != null) {
			return ps.doubleValue();
		}
		return null;
	}
	
	
	public Map<String,FUnit> getAllMapByPidsAndUnitId(String ids,Integer unitId) {
		Map<String, FUnit> resultMap = new HashMap<String, FUnit>();
		List<FUnit> list = queryFUnitByPidsAndUnitId(ids,unitId);
		for(FUnit fUnit : list){
			if(fUnit == null) continue;
			resultMap.put(fUnit.getProductid(), fUnit);
		}
		return resultMap;
	}
	
	public Map<String,FUnit> getAllMapByUnitId(Integer unitId) {
		List<FUnit> list = getAllByUnitId(unitId);
		return getMapByProductId(list);
	} 
	
	private Map<String, FUnit> getMapByProductId(List<FUnit> list) {
		Map<String, FUnit> resultMap = new HashMap<String, FUnit>();
		for(FUnit fUnit : list){
			if(fUnit == null) continue;
			resultMap.put(fUnit.getProductid(), fUnit);
		}
		return resultMap;
	}

	public List<FUnit> getAllByUnitId(Integer unitId){
		String sql=" from FUnit where funitid = " + unitId;
		return  EntityManager.getAllByHql(sql);
	}
	
	public List<FUnit> queryFUnitByPidsAndUnitId(String ids,Integer unitId){
		String sql = "  from FUnit where productid in(" + ids+ ") and funitid = " + unitId;
		List<FUnit> ps = EntityManager.getAllByHql(sql);
		return ps;
		
	}

}
