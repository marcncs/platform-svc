package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.PlantType;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppOrganProduct {
	private AppOrgan appo=new AppOrgan();
	
	public void addOrganProduct(Object d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void addOrganProductNoExist(OrganProduct p)throws Exception{
		String sql="insert into organ_product(id,organid,productid) select "+p.getId()+",'"+p.getOrganid()+"','"+p.getProductid()+"' from dual where not exists (select id from organ_product where organid='"+p.getOrganid()+"' and productid='"+p.getProductid()+"')";
		EntityManager.updateOrdelete(sql);
	}

	
	public void delOrganProduct(Long opid)throws Exception{
		String sql="delete from organ_product where id="+opid;
		EntityManager.updateOrdelete(sql);
	}
	
	public void delOrganProduct(String opids)throws Exception{
		String sql="delete from organ_product where id in("+opids+")";
		EntityManager.executeUpdate(sql);
	}
	
	
	public List getOrganProduct(int pagesize, String whereSql, SimplePageInfo tmpPgInfo) throws Exception{
		String sql=" from OrganProduct as op "+whereSql +" order by op.id";
		return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	}
	
	public List getOrganProductAlready(int pagesize, String whereSql, SimplePageInfo tmpPgInfo) throws Exception{
		String sql="select op.id,op.productid from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	}
	
	public List getOrganProductAlready(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		//#start modified by ryan.xi at 20150602 
//		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,p.countunit as countunit,p.sunit as sunit,p.mCode as mCode) from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,p.countunit as countunit,p.sunit as sunit,p.mCode as mCode,p.packSizeName as packSizeName) from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		//#end modified by ryan.xi at 20150602
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getOrganProductAlready(HttpServletRequest request, int pagesize, String whereSql, Map<String,Object> param) throws Exception{
		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,p.countunit as countunit,p.sunit as sunit,p.mCode as mCode,p.packSizeName as packSizeName) from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		return PageQuery.hbmQuery(request, hql, pagesize, param);
	}
	
	public List getOrganProduct(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,p.countunit as countunit,p.sunit as sunit) from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getOrganProductObj(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql="select p from OrganProduct as op,Product as p "+whereSql +" order by NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M')";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getOrganProductByTrades(HttpServletRequest request, int pagesize, String whereSql)throws Exception{
		String sql = "select p.id,p.productname,p.specmode,p.countunit,p.sunit,ps.warehouseid,sum(ps.stockpile) as stockpile from Organ_Product as op,Product as p,Product_Stockpile as ps "+whereSql+" group by p.id,p.productname,p.specmode,p.countunit,p.sunit,ps.warehouseid";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}
	
	public OrganProduct getOrganProductByID(Long id)throws Exception{
		return (OrganProduct)EntityManager.find("from OrganProduct as op where op.id="+id);
	}

	public List getOrganProductAlready(String whereSql) {
		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,p.countunit as countunit,p.sunit as sunit,p.isbatch as isbatch, p.packSizeName as packsizename) from OrganProduct as op,Product as p "+whereSql +" order by op.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getRuleOrganProduct(Integer userid) {
		String hql="select op,p from OrganProduct op,UserVisit uv,Product p where p.id=op.productid " +
				" and op.organid = uv.visitorgan and  uv.userid="+ userid +" and p.id=op.productid and p.useflag=1 order by p.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getVisitOrganProduct(Integer userid) {
		String hql="select op,p from OrganProduct op,OrganVisit uv,Product p where p.id=op.productid " +
				" and op.organid = uv.visitorgan and  uv.userid="+ userid +" and p.id=op.productid and p.useflag=1 order by p.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getOrganProductPrice(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql="select new map(op.id as id,op.productid as productid,p.productname as productname,p.specmode as specmode,op.unitid as unitid,op.unitprice as unitprice,op.frate as frate) from OrganPriceii as op,Product as p "+whereSql +" order by op.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public int getCountByProductID(String pid)throws Exception{
		String sql= "select count(id) from OrganProduct where productid='"+pid+"'";
		return EntityManager.getRecordCount(sql);
	}
	
	/**
	 * 根据产品ID集合查出未上架的机构集合
	 * @param productIds 产品ID汇总
	 * @return 机构集合
	 */
	public Set<Organ> getNotExistOrgan(String productIds){
		String[] ids = productIds.split(",");
		Set<Organ> allOrgans = new HashSet<Organ>();
		for (int i = 0; i < ids.length; i++) {
			String hql = "from Organ o where o.id not in (select op.organid from OrganProduct op where op.productid = '" + ids[i] +"')";
			List<Organ> organs = EntityManager.getAllByHql(hql);
			for (Organ organ : organs) {
				allOrgans.add(organ);
			}
		}
		return allOrgans;
	}
	
	/**
	 * 根据机构和产品ID集合,上架
	 * @param pids 产品集合
	 * @param oids 机构集合
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void addProductsOrgans(String[] pids,String[] oids) throws NumberFormatException, Exception{
		//循环机构
		for (int i = 0; i < pids.length; i++) {
			String pid = pids[i];
			//循环产品
			for (int j = 0; j < oids.length; j++) {
				if(StringUtil.isEmpty(pids[j]) || StringUtil.isEmpty(oids[j])){
					continue;
				}
				OrganProduct organProduct = new OrganProduct();
				//取得当前ID
				organProduct.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"organ_product", 0, "")));
				organProduct.setOrganid(oids[j]);
				organProduct.setProductid(pid);
				//添加产品上架
				addOrganProductNoExist(organProduct);
			}
		}
	}
	/**
	 * 上架所有产品
	 * Create Time 2013-11-12 上午10:30:53 
	 * @param oid
	 * @throws Exception
	 * @author lipeng
	 */
	public void addAllProduct(String oid) throws  Exception{
		//先上架所有产品,再修改make_conf中organ_product的值
		String sql = "insert into organ_product " +
				" select (select max(id) from organ_product)+row_number() over ( order by id ),'" + oid +  "',product.id from product"
				+ " ;  " 
				+ "update make_conf set CurrentValue =(select max(id)+1 from organ_product) where TableName='organ_product';";
		
		EntityManager.updateOrdelete(sql); 
	}
	
	/**
	 * 上架所有产品
	 * Create Time 2013-11-12 上午10:30:53 
	 * @param oid
	 * @throws Exception
	 * @author lipeng
	 */
	public void addAllProductToOrganProduct(String oid) throws  Exception{
		//先上架所有产品,再修改make_conf中organ_product的值
		String sql = "insert into organ_product " +
				" select (select max(id) from organ_product)+row_number() over ( order by id ),'" + oid +  "',product.id from product";
		//更新organ_product
		EntityManager.updateOrdelete(sql); 
		//更新make_conf
		//sql = "update make_conf set CurrentValue =(select max(id)+1 from organ_product) where TableName='organ_product'";
		//EntityManager.updateOrdelete(sql); 
	}
	
	
	public void addProductsOrgans(String pid) throws NumberFormatException, Exception{
		List<Organ> organList=appo.getAllOrgan();
		if(organList==null || organList.isEmpty()){
			return;
		}
		//循环产品
		for (Organ organ:organList) {
			OrganProduct organProduct = new OrganProduct();
			//取得当前ID
			organProduct.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"organ_product", 0, "")));
			organProduct.setOrganid(organ.getId());
			organProduct.setProductid(pid);
			//添加产品上架
			addOrganProductNoExist(organProduct);
		}
		
//		Session session;
//		Statement s = null;
//		ResultSet st = null;
//		String organid;
//		try
//		{
//			session = HibernateUtil.currentSession();
//			String sql="select id from organ";
//			s = session.connection().createStatement();
//			st = s.executeQuery(sql);
//			while (st.next())
//			{
//				organid = st.getString(1);
//				OrganProduct organProduct = new OrganProduct();
//				//取得当前ID
//				organProduct.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//						"organ_product", 0, "")));
//				organProduct.setOrganid(organid);
//				organProduct.setProductid(pid);
//				//添加产品上架
//				addOrganProductNoExist(organProduct);
//			}
//		}
//		catch(HibernateException e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//		finally
//		{
//			st.close();
//		}
	}

	public List<Product> getOrganProductByOIDs(String organids)throws Exception{
		return EntityManager.getAllByHql("select p from Product as p where p.id in (select op.productid from OrganProduct as op where op.organid in ("+organids+") group by op.productid)");
	}
	
	/**
	 * 通过机构id获取信息
	 * @param pagesize
	 * @param whereSql
	 * @param tmpPgInfo
	 * @return
	 * @throws Exception
	 */
	public List getOrganProductByOrganid(String  oid) throws Exception{
		String hql=" from OrganProduct as op  where op.organid='"+oid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getOrganProductObj(String whereSql) throws Exception{
		String hql="select p from OrganProduct as op,Product as p "+whereSql +" order by p.id";
		return EntityManager.getAllByHql(hql);
	}
	
	public void addAllOrgan(String pid) throws  Exception{
		//先上架所有产品,再修改make_conf中organ_product的值
		String sql = "insert into organ_product " +
				" select (select nvl(max(id),0) from organ_product)+row_number() over ( order by id ),o.id,'" + pid +  "' from organ o where o.id not in (" +
						"select id from ORGAN where ORGANTYPE="+OrganType.Plant.getValue()+" and ORGANMODEL="+PlantType.Toller.getValue()+") "; 
		EntityManager.updateOrdelete(sql); 
	}
	
	/**
	 * 判断该用户是否有该产品的使用权
	 * @param organids
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product getOrganProductByPid(String organids,String id)throws Exception{
		return (Product) EntityManager.find("select p from Product as p where p.id in (select op.productid from OrganProduct as op where op.organid in ("+organids+") group by op.productid) and p.useflag=1 and p.id='"+id+"'");
	}
}
