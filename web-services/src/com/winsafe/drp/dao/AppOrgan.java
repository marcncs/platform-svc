package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.hbm.util.Encrypt; 
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppOrgan {
	
	public void AddOrgan(Organ d) throws Exception {		
		EntityManager.save(d);		
	}
	
	/**
	 * 
	 * Create Time: Oct 8, 2011 4:42:02 PM 
	 * @param scanid
	 * @return 
	 * @author dufazuo
	 */
	public ScannerUser getScannerUserScanner(String scanid)
	{
		return (ScannerUser)EntityManager.find("from ScannerUser where scanner='"+scanid+"'");
	}
	
	public List getOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from Organ as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List<Map<String,String>> getOrganList(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select * from Organ o "+whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
		} else {
			sql+=" order by id desc ";
			return EntityManager.jdbcquery(sql);
		}
		
	} 
	
	public List<Map<String,String>> getOrganList(HttpServletRequest request, int pageSize, String whereSql, Map<String, Object> param) throws Exception{
		String sql="select * from Organ o "+whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize, param);
		} else {
			sql+=" order by id desc ";
			return EntityManager.jdbcquery(sql, param);
		}
		
	} 
	
	public List getOrgansb(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from Organ as o "+whereSql +"  and o.organModel in (1,2)  and o.organType=2 order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public String getOcodeByParent(String strparentid)throws Exception{		
//		int length=strparentid.length()+4;
//		long intacode=Long.valueOf(strparentid+"0001");
//		String sql="from Organ where sysid like '"+strparentid+"%' and length(sysid)="+length+" order by sysid desc " ;
//		Organ as=(Organ)EntityManager.find(sql);
//		if( as != null ){ 
//			intacode=Long.valueOf(as.getSysid())+1;
//		}
//		return String.valueOf(intacode);
		String i = "", k = "";
		int length=strparentid.length() + 4;
		String intacode = strparentid + "0001";
		DecimalFormat format = new DecimalFormat("0000");
		String sql="from Organ where sysid like '"+strparentid+"%' and length(sysid)="+length+" order by sysid desc " ;
		Organ as=(Organ)EntityManager.find(sql);
		if( as != null ){
			String sysid = as.getSysid();
			i = sysid.substring(0, sysid.length() - 4);
			k = sysid.substring(sysid.length() -4);
			intacode = i + format.format((Integer.valueOf(k) + 1));
		}
		return intacode;
	}
	
	public void updOrgan(Organ o)throws Exception {		
		EntityManager.update(o);		
	}
	
	public void updOrganBBeach(List list)throws Exception {		
		EntityManager.updateByBeach(list);	
	}
	
	public Organ getOrganByID(String id)throws Exception{
		Map<String,Object> param = new HashMap<>();
	    param.put("id", id.trim());
		return (Organ)EntityManager.find("from Organ where id=:id", param);
	}
	
	
	public Organ getBKROrganByID(String id)throws Exception{
		return (Organ)EntityManager.find("from Organ where id='"+id.trim()+"'  and organModel<>1  and organType=2");
	}
	
	public Organ getBKDPDOrganByID(String id)throws Exception{
		return (Organ)EntityManager.find("from Organ where id='"+id.trim()+"'  and organModel in (1,2) and organType=2");
	}
	
	public Organ getOrganByID_Isrepeal(String id)throws Exception{
		return (Organ)EntityManager.find("from Organ where id='"+id+"' and isrepeal = 0");
	}
	
	public Organ getOrganByWarehouseid(String warehouseid)throws Exception{
		return (Organ)EntityManager.find("select o from Organ as o,Warehouse as w where w.makeorganid=o.id and w.id='"+warehouseid+"'");
	}
	
	public List getAllOrgan()throws Exception{
		return EntityManager.getAllByHql(" from Organ ");
	}
	public List getAllOrganId()throws Exception{
		return EntityManager.getAllByHql(" select id from Organ ");
	}
	
	public List<Organ> getOrganByWhere(String whereSql) throws Exception{
		String hql=" from Organ as o "+whereSql +" order by o.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<Map<String,String>> getOrganListByWhere(String whereSql) throws Exception{
		String sql="select * from Organ o "+whereSql +" order by id desc";
		return EntityManager.jdbcquery(sql);
	}
	
	public List<String> getOrganIdByWhere(String whereSql) throws Exception{
		String hql=" select o.id from Organ as o "+whereSql +" order by o.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getOrganToDown(String sysid)throws Exception{
		String sql=" from Organ  where sysid like '"+sysid+"%' order by sysid ";
		return EntityManager.getAllByHql(sql);
	}
	
	public void updSysid(String oldSysid, String newSysid)throws Exception{
		String sql=" update organ set sysid=replace(sysid, '"+oldSysid+"', '"+newSysid+"')  where sysid like '"+oldSysid+"%'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List<Organ> getOrganLikeSysid(String sysid) throws Exception{
		String hql=" from Organ as o where o.isrepeal=0 and  sysid like '"+sysid+"%' and length(sysid)>"+sysid.length()+"  order by sysid";
		return EntityManager.getAllByHql(hql);
	}
	
	
	 public List getTreeByCate(String parentid) throws Exception{
		String sql=" from Organ as o where o.parentid='"+parentid+"'";
		return EntityManager.getAllByHql(sql);
	}
	 
	 public List<Organ> getListByParentId(String parentid) throws Exception{
			String sql=" from Organ as o where o.parentid='"+parentid+"'";
			return EntityManager.getAllByHql(sql);
	}

	public List getOrgan(String whereSql) {
		String hql=" from Organ as o "+whereSql +" order by o.id";
		return EntityManager.getAllByHql(hql);
		//PageQuery.hbmQuery(request, hql, pagesize)
	}
	
	public List getWarehouseOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql= "select distinct o.id,o.oecode,o.organname,o.otel,o.omobile, "+
		" o.ofax,o.oemail,o.parentid,o.province,o.city,o.areas,o.oaddr " + 
		" from warehouse_visit  wv,warehouse  w, organ  o "+whereSql;		
		return PageQuery.jdbcSqlserverQuery(request, sql, pageSize);
	}
	
	public Organ getByOecode(String oecode) throws NotExistException{
		String sql = " from Organ as w where w.oecode='" + oecode + "'";
		return (Organ) EntityManager.find(sql);
	}
	
	public List<Organ> getByOecode2(String oecode) throws NotExistException{
		String sql = " from Organ as w where w.oecode='" + oecode + "' and w.isrepeal = 0";
		return  EntityManager.getAllByHql(sql);
	}
	public Organ getBySysId(String sysid) throws NotExistException{
		Organ temp=new Organ();
		String sql = " from Organ as w where w.SysId='" + sysid + "'";
		temp = (Organ) EntityManager.find(sql);
		if(temp==null){
			throw new NotExistException("基础数据错误，找不到相对应的机构");
		}
		return temp;
	}
	//根据机构名得到机构id
	public Organ getOrganByOrganName(String organname)throws Exception{
		return (Organ)EntityManager.find("from Organ where organname='"+organname+"'");
	}
	
	//根据机构简称得到机构
	public Organ getOrganByShortName(String shortname)throws Exception{
		return (Organ)EntityManager.find("from Organ where shortname='"+shortname+"'");
	}
	
	public List<Organ> queryByInOrgan(String ids) throws Exception{
		String sql = "  from Organ where id in(" + ids+ ")";
		List<Organ> os = EntityManager.getAllByHql(sql);
		return os;
	}

	public void insertOrgan(String organid,Integer salemainid) throws HibernateException, SQLException, Exception  {
		
		String sql = "  insert into saleman_organ(id,organid,salemanid,type)" +
				"(select isnull(max(id),0)+1 , '"+organid+"' ,"+salemainid+", 1  from  saleman_organ) ";
		EntityManager.updateOrdelete(sql);
	}
	public void deleteOrgan(String organid) throws HibernateException, SQLException, Exception  {
		String sql = "delete   from  saleman_organ  where  organid="+organid; 
		EntityManager.updateOrdelete(sql);
		
	}

	public List getOrganByRegionareaid(String psid) {
		String sql = " from Organ as w where w.regionareaid='" + psid + "' and w.isrepeal = 0";
		return  EntityManager.getAllByHql(sql);
	}
	
	
	public void  updOrganByjdbc(String sql) throws SQLException {
		EntityManager.updByJDBC(sql);
	}
	
	public List<Organ> getAllOrganByOecode(String oecode) throws NotExistException{
		String sql = " from Organ as w where w.oecode='" + oecode + "'";
		return  EntityManager.getAllByHql(sql);
	}

	public List<Organ> getAllOrganByOecodeAndOrganType(String oecode,String condition) throws NotExistException{
		String sql = " from Organ as w where w.oecode='" + oecode + "'" + condition;
		return  EntityManager.getAllByHql(sql);
	}
	
	public List getFontOrganByWhere(String whereSql) throws Exception{
		String hql="select o1.bigregionname,o1.officename,o2.oecode as dealeroecode,o2.organname as dealerorganname," +
					"o1.oecode,o1.organname,ol.addr,ol.name as olname,ol.officetel,ru.userlogin,ru.username" + 
					" from " +
					"organ as o1 " +
					"inner join " +
					"organ as o2 on o1.parentid=o2.id and len(o1.sysid)=13 " +
					"left outer join " +
					"olinkman as ol on o1.id=ol.cid " +
					"left outer join " +
					"region_users as ru on o1.salemanid=ru.userid " + whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public List<Organ> getOrganIdAndNames() {
		String hql=" select new Organ(id, organname, oecode) from Organ where isrepeal = 0 and (organType = 1 or (organType = 2 and organModel = 1)) order by id";
		return EntityManager.getAllByHql(hql);
	}
	
	public Integer getOrganCountByOeCode(String oeCode) {
		String sql = "select count(id) from organ where oecode = '"+oeCode+"'";
		return EntityManager.getRecordCountBySql(sql);
	}

	public Organ getOrganByNameAndParentId(String parentId, String organName) throws NotExistException {
		String sql = " from Organ as o where o.parentid='" + parentId + "' and o.organname='"+organName+"'  and o.isrepeal = 0 ";
		Organ temp = (Organ) EntityManager.find(sql);
		if(temp==null){
			throw new NotExistException("基础数据错误，找不到上级机构编号为"+parentId+",名称为"+organName+"的机构");
		}
		return temp;
	}

	public Organ getByOecodeAndParentId(String oecode, String parentId) throws NotExistException{
		String sql = " from Organ as w where w.oecode='" + oecode + "' and w.parentid='" + parentId + "' and w.isrepeal = 0 ";
		return (Organ) EntityManager.find(sql);
	}

	public Organ getPdbyOecode(String oecode) {
		String sql = " from Organ as w where w.oecode='" + oecode + "' and w.organType= 2 and w.organModel = 1 ";
		return (Organ) EntityManager.find(sql);
	}
	
	public Organ getOrganByNameAndPId(String parentId, String organName) throws NotExistException {
		String sql = " from Organ as o where o.parentid='" + parentId + "' and o.organname='"+organName+"'";
		return(Organ) EntityManager.find(sql);
	}
	
	public boolean isOrganExists(String parentId, String organName) throws NotExistException {
		String sql = " select count(*) from organ where PARENTID = '"+parentId+"' and ORGANNAME = '"+organName+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public Organ getParentOrganById(String organId) {
		String sql = " from Organ as o where o.id=(select parentid from Organ where id = 'organId')";
		return(Organ) EntityManager.find(sql);
	}

	public List<Organ> getOrganByIds(String ids) {
		String sql = " from Organ as o where o.id in ("+ids+")";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 判断手机号是否重复
	 * @param mobile
	 * @param isUpdate 
	 * @return
	 */
	public boolean isUsersExists(String mobile, String organId) {
		mobile = Encrypt.getSecret(mobile, 3);
		String sql = "select count(*) from Organ where omobile = '"+mobile+"' and validate_status <> 2 and ISREPEAL <> 1 ";
		if(organId != null) {
			sql += " and id <> '"+organId+"'";
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	
	/**
	 * 验证同一个上级机构下的机构，是否存在机构名称,详细地址重复
	 * @param parentId
	 * @param organName
	 * @return
	 * @throws NotExistException
	 */
	public boolean isOrganParentIdExists(String parentId, String organName,String address, String organId) throws NotExistException {
		String sql = " select count(*) from organ where parentid = '"+parentId+"' and organname = '"+organName+"' and oaddr='"+address+"' and validate_status <> 2 and ISREPEAL <> 1 ";
		if(organId != null) {
			sql += " and id <> '"+organId+"'";
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<Map<String,String>> getKeyRetailerOrgan(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String hql="select o.id,o.organname,o.oecode,o.organtype,o.organmodel,o.omobile,PO.ORGANNAME parentidname,ca1.areaname provincename,ca2.areaname cityname,ca3.areaname areasname " +
				"from Organ o join organ po on o.PARENTID = PO.id " +
				"left join COUNTRY_AREA ca1 on ca1.id = o.province " +
				"left join COUNTRY_AREA ca2 on ca2.id = o.city " +
				"left join COUNTRY_AREA ca3 on ca3.id = o.areas " +
				" "+whereSql +" order by o.id desc";
		return PageQuery.jdbcSqlserverQuery(request, "id", hql, pageSize);
	}
	
	public List<Map<String,String>> getSelectOrgan(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql=" select o.id,o.OECODE,o.ORGANNAME,o.OTEL,o.OMOBILE,o.OADDR,o.OFAX,o.OEMAIL,o.RATE,o.PROMPT,ca1.areaname PROVINCEName,ca2.areaname CITYName,ca3.areaname AREASName,o.CITY,o.AREAS,o.PARENTID,po.ORGANNAME parentidname,o.RANK,o.organtype,o.organmodel " +
				"from ORGAN o LEFT JOIN ORGAN po on o.PARENTID=PO.ID LEFT JOIN COUNTRY_AREA ca1 on ca1.id = o.province " +
				"LEFT JOIN COUNTRY_AREA ca2 on ca2.id = o.city LEFT JOIN COUNTRY_AREA ca3 on ca3.id = o.areas " +
				whereSql +" order by o.id desc";
		return PageQuery.jdbcSqlserverQuery(request, " id desc ", sql, pageSize);
//		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public List<String> getOrganIdListByOrganType(Integer organType, Integer organModel) {
		String hql = "select id from Organ where isrepeal = 0 and organType="+organType+" and organModel="+organModel;
		return EntityManager.getAllByHql(hql); 
	}
	public static void main(String[] args) throws Exception {
		AppOrgan appOrgan = new AppOrgan();
		Object obj = appOrgan.getOrganByID("10001546");
		System.out.println(obj);
	}

	public List<Map<String, String>> getValidateLoaclInfo(String validateLoaclId) throws HibernateException, SQLException {
		String sql = "select NAME,LEG,C_DATE,RE_CODE,STATUS from CUSTOMER_LOCAL_INFO where ID='"+validateLoaclId+"'";
		return EntityManager.jdbcquery(sql); 
	}
	
}

