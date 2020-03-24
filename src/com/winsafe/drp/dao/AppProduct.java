package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CartonSeqLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;

public class AppProduct {

	public List getProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from Product " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getProduct(HttpServletRequest request, int pagesize, String pWhereClause, Map<String,Object> param) throws Exception {
		String hql = " from Product " + pWhereClause + " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize, param);
	}

	public Map<String, Product> getProductMap() throws Exception {
		List<Product> productlist = getProductAll();
		Map<String, Product> returnmap = new HashMap<String, Product>();
		for (Product product : productlist) {
			if (product == null) {
				continue;
			}
			returnmap.put(product.getId(), product);
		}
		return returnmap;
	}

	public List<Product> getProductAll() throws Exception {
		return EntityManager.getAllByHql("from Product ");
	}

	public List<Product> getProductByUserFlag(Integer useFlag) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("useFlag", useFlag);
		return EntityManager.getAllByHql("from Product where useflag = :useFlag order by id ", param);
	}

	public Double getProductPriceByOIDPIDRate(String oid, String pid, int unitid, Integer rate) throws Exception {
		String sql = "select op.unitprice from OrganPrice as op where op.organid='" + oid + "' and op.productid='" + pid + "' and op.unitid=" + unitid + " and op.policyid=" + rate;
		return EntityManager.getdoubleSum(sql);
	}

	public Double getProductPriceByOidPidCid(String oid, String pid, int unitid, String cid) throws Exception {
		String sql = "select op.unitprice from OrganPrice as op,MemberGrade as mg,Customer as c " + "where op.policyid=mg.policyid and c.rate=mg.id and c.cid='" + cid + "' " + "and op.organid='"
				+ oid + "' and op.productid='" + pid + "' and op.unitid=" + unitid;
		return EntityManager.getdoubleSum(sql);
	}

	public Double getDitchPriceByOIDPID(String oid, String pid, int unitid) throws Exception {
		String sql = "select op.unitprice from OrganPriceii as op where op.organid='" + oid + "' and op.productid='" + pid + "' and op.unitid=" + unitid;
		return EntityManager.getdoubleSum(sql);
	}

	public List getProductnew(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from Product as p " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getProductNotInOrganProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from Product as p " + pWhereClause + " ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getOrganProductProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " select p from OrganProduct as op,Product as p " + pWhereClause + " ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getSingleProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "select p.id,p.productname,p.specmode,p.psid,p.countunit,p.productcode,p.barcode,p.useflag from Product as p " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addProduct(Object product) throws Exception {
		EntityManager.save(product);
	}

	public Product getProductByID(String id) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("id", id);
		String sql = " from Product as p where p.id=:id";
		return (Product) EntityManager.find(sql, param);
	}

	public List<Product> getProductByNccode(String nccode) throws Exception {
		String sql = " from Product as p where p.nccode='" + nccode + "'";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * 根据物流码找到对应的产品集合
	 * 
	 * @param icode
	 * @return
	 * @throws Exception
	 */
	public ResultSet getProductByICode(String icode) throws Exception {

		String sql = "select p.id from Product  p,I_Code  ic where p.id = ic.productid and ic.lcode='" + icode + "'";
		return EntityManager.query2(sql);
	}

	public String getProductByICode1(String icode) throws Exception {
		ResultSet rs = getProductByICode(icode);
		if (rs.next()) {
			return rs.getString(1);
		}
		if (rs != null) {
			rs.close();
		}
		return null;

	}

	/**
	 * 根据物流码找到对应的产品对象
	 * 
	 * @param icode
	 * @return
	 * @throws Exception
	 */
	public Product getProductEntityByICode(String icode) throws Exception {

		String sql = "select p.id from Product as p,ICode as ic where p.id = ic.productid and ic.lcode='" + icode.trim() + "'";
		List<String> resultList = EntityManager.getAllByHql(sql);
		if (resultList.size() > 0) {
			AppProduct appProduct = new AppProduct();
			return appProduct.getProductByID(resultList.get(0));
		}
		return null;
	}

	public String getProductNameByID(String id) throws Exception {
		Product p = getProductByID(id);
		if (p == null) {
			return "";
		}
		return p.getProductname();
	}

	public String getProductIdByName(String productname) throws Exception {
		List lp = getProductByName(productname);
		if (lp.size() > 0) {
			Product p = new Product();
			p = (Product) lp.get(0);
			return p.getId();
		}
		return "";
	}

	public Product getProductByIdcode(String idcode) throws Exception {
		String sql = " from Product as p where p.productcodedef='" + idcode + "' or p.productcode='" + idcode + "'";
		if (idcode != null && idcode.length() == 21) {
			sql = " from Product as p where p.productcodedef='" + idcode.substring(0, 13) + "' or p.productcode='" + idcode.substring(0, 9) + "' or p.productcode='" + idcode.substring(0, 13) + "'";
		}
		return (Product) EntityManager.find(sql);
	}

	public void updProduct(Product p) throws Exception {
		EntityManager.update(p);
	}

	public void delProduct(String id) throws Exception {
		String sql = "delete from product where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getStockConstrue(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id,p.productname,p.countunit,p.leaststock,p.standardstock,p.tiptopstock,sum(ps.stockpile) from Product as p,ProductStockpile as ps " + pWhereClause
				+ " group by p.id  order by p.id desc";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getMakePurchaseApply(String pWhereClause) throws Exception {
		String sql = "select p.id,p.productname,p.countunit,p.standardpurchase,p.leaststock,p.standardstock,p.tiptopstock,sum(ps.stockpile) from Product as p,ProductStockpile as ps " + pWhereClause
				+ " group by p.id  order by p.id desc";
		return EntityManager.getAllByHql(sql);
	}

	public List<Product> findByWhereSql(String whereSql) throws Exception {
		String sql = " from Product as p " + whereSql + "order by p.id desc";
		return EntityManager.getAllByHql(sql);
	}

	public List getSelectProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from Product as p " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getSelectProductAndStockpile(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select p.id as id,p.productname as productname, p.countunit as countunit, p.specmode as specmode, p.mCode as mCode, p.sunit as sunit, p.boxquantity as boxquantity, p.packSizeName as packSizeName, sum((ps.stockpile * p.boxquantity)) as stockpile " +
				" from Product p " +
				" join Product_Stockpile ps " +
				" on ps.productid = p.id " + pWhereClause +
				" group by p.id ,p.productname, p.countunit , p.specmode , p.mCode , p.sunit , p.boxquantity , p.packSizeName " +
				" HAVING sum((ps.stockpile * p.boxquantity)) > 0 " +
				" order by p.id desc ";
		return PageQuery.jdbcSqlserverQuery(request, "id", sql, pagesize);
	}

	public List getSelectProductName(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select distinct productname from Product p " + pWhereClause;//,regCertCode,standardName
		return PageQuery.jdbcSqlserverQuery(request, " NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M')", sql.toString(), pagesize);
	}

	public List findProductPrice(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select p.id,p.productname,p.specmode,op.unitid,op.unitprice from Product as p,OrganPrice as op  " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List findProductPrice(String pWhereClause) throws Exception {
		String sql = "select p.id,p.productname,p.specmode,op.unitid,op.unitprice from Product as p,OrganPrice as op  " + pWhereClause + " order by p.id desc,op.unitid desc";
		return EntityManager.getAllByHql(sql);
	}

	public List getSelectSaleOrderProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select p, o from Product as p ,OrganProduct as op, OrganPrice as o " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getSelectSaleOrderFee(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select p.id, p.productname,p.specmode,p.countunit, " + "p.wise from Product as p ,OrganProduct as op " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getSelectPeddleOrderProduct(int pagesize, String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select p.id, p.productname,p.specmode,p.countunit, " + "p.wise " + "from Product as p ,OrganProduct as op " + pWhereClause + " order by p.id desc";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getSelectAllProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from Product as p " + pWhereClause + " order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getAllProduct() throws Exception {
		String sql = "select p.id,p.countunit,p.productcode,p.barcode from Product as p";
		return EntityManager.getAllByHql(sql);
	}

	public int getCountProductByPSID(String dcode) throws Exception {
		String sql = "select count(p.id) from Product as p where p.psid='" + dcode + "'";
		return EntityManager.getRecordCount(sql);
	}

	public int getProductID(String id) throws Exception {
		String sql = "select count(*) from Product where id='" + id + "'";
		return EntityManager.getRecordCountQuery(sql);
	}

	public String getMaxProductIdByBrand(String psid) throws Exception {
		String sql = "select MAX(SUBSTRING(id, LEN(id)-3, 4)) from Product where psid='" + psid + "'";
		return (String) EntityManager.find(sql);
	}

	public List getProductByName(String pname) throws Exception {
		String sql = " from Product as p where p.productname='" + pname + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getmCodeById(String id) throws Exception {
		String sql = " from Product as p where p.id='" + id + "'";
		return EntityManager.getAllByHql(sql);
	}

	public void updProductCodeByID(String pid, String productcode) throws Exception {
		String sql = "update Product set productcode='" + productcode + "' where id='" + pid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getProduct(String whereStr) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("from Product");
		if (null != whereStr && !"".equals(whereStr)) {
			hql.append(whereStr);
		}
		hql.append(" order by productname");
		return EntityManager.getAllByHql(hql.toString());
	}

	public void updProductPYCode(String productid, String pycode) throws Exception {
		String sql = "update product set pycode='" + pycode + "' where id='" + productid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 根据用友的导入的NCCODE查询WINSAFE的标准编号
	 * 
	 * @param nccode
	 *            用友提供的编号
	 * @return id WINSAFE的标准编号
	 * @throws Exception
	 */
	public Product getByNCcode(String nccode) throws NotExistException {
		Product temp = new Product();
		String sql = " from Product as p where p.nccode='" + nccode + "'";
		temp = (Product) EntityManager.find(sql);
		if (temp == null) {
			try {
				DBUserLog.addUserLog(0, 99, "基础数据错误，找不到相对应的产品" + nccode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new NotExistException("基础数据错误，找不到相对应的产品");
		}
		return temp;

	}

	public Product getByNCcode(String nccode, int i) throws NotExistException {
		Product temp = new Product();
		String sql = " from Product as p where p.nccode='" + nccode + "'";
		temp = (Product) EntityManager.find(sql);
		if (temp == null) {
			try {
				DBUserLog.addUserLog(i, 13, "基础数据错误，找不到相对应的产品" + nccode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new NotExistException("基础数据错误，找不到相对应的产品");
		}
		return temp;

	}

	public List<Product> queryByInProduct(String ids) throws Exception {
		String sql = "  from Product where id in(" + ids + ")";
		List<Product> ps = EntityManager.getAllByHql(sql);
		return ps;

	}

	public Product findProductByNccode(String nccode) throws Exception {
		String sql = " from Product as p where p.nccode='" + nccode + "'";
		return (Product) EntityManager.find(sql);
	}

	public Product findProductByproducrname(String name) throws Exception {
		String sql = " from Product as p where p.productname='" + name + "'";
		return (Product) EntityManager.find(sql);
	}

	public Product findProductByNccodeAndProductname(String nccode, String productname) throws Exception {
		String sql = " from Product as p where p.nccode='" + nccode + "' and  p.productname='" + productname + "'";
		return (Product) EntityManager.find(sql);
	}

	public List getProductUnitById(String whereSql) throws Exception {
		String sql = "select p.id,p.productname,f.funitid,f.xquantity " + " from product as p,f_unit as f,i_code as i where i.productid=p.id and f.productid=p.id and f.ismain<>1 " + whereSql;
		return EntityManager.jdbcquery(sql);
	}

	/**
	 * 根据物流码找到对应的产品对象
	 * 
	 * @param icode
	 * @return
	 * @throws Exception
	 */
	public List getProductsByICodes(String icodes) throws Exception {
		String sql = "select p,ic from Product as p,ICode as ic where p.id = ic.productid and ic.lcode in (" + icodes + ")";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * 根据SAP导入的物料号查询产品信息
	 * 
	 * @param mcode
	 *            SAP提供的物料编号
	 * @return Product 产品信息
	 * @throws Exception
	 */
	public Product getByMCode(String mCode) throws NotExistException {
		Product temp = new Product();
		String sql = " from Product as p where p.useflag = 1 and p.mCode='" + mCode + "'";
		temp = (Product) EntityManager.find(sql);
		if (temp == null) {
			throw new NotExistException("基础数据错误，找不到相对应的产品");
		}
		return temp;

	}

	/**
	 * 
	 * @author jason.huang
	 * @return 获取所有产品名
	 */

	public List getAllProductName() {
		String sqlString = " select DISTINCT productname from Product where useflag=1  order by NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M') ";
		return EntityManager.getAllByHql(sqlString);
	}

	/**
	 * 获取产品到期日期
	 * 
	 * @author jason.huang
	 * @return
	 * 
	 */

	public String getExpireDay(String id, int expireday) {
		String sql = " select TO_CHAR(MAKEDATE+" + expireday + ",'yyyy-mm-dd') from Product where id='" + id + "'";
		return EntityManager.getString(sql);
	}

	/**
	 * 获取产品物料号
	 * 
	 * @author jason.huang
	 * @return
	 */

	public String getMcodeById(String id) throws Exception {
		List lp = getmCodeById(id);
		if (lp.size() > 0) {
			Product p = new Product();
			p = (Product) lp.get(0);
			return p.getmCode();
		}
		return "";
	}

	/**
	 * 根据产品名称获取包装规格
	 * 
	 * @param productName
	 * @return
	 */
	public List<String> getPackSizeNameByProductName(String productName) {
		String sqlString = " select packSizeName from Product where useflag=1 and productname = '" + productName + "' order by packSizeName";
		return EntityManager.getAllByHql(sqlString);
	}
	
	/**
	 * 根据产品名称获取包装规格
	 * 
	 * @param productName
	 * @return
	 */
	public List<String> getSpecmodeByProductName(String productName) {
		String sqlString = " select distinct specmode from Product where useflag=1 and productname = '" + productName + "' order by specmode";
		return EntityManager.getAllByHql(sqlString);
	}

	/**
	 * 根据产品id获取产品
	 * 
	 * @param productId
	 * @return
	 */
	public Product loadProductById(String productId) {
		try {
			return (Product) EntityManager.load(Product.class, productId);
		} catch (Exception e) {
			return null;
		}
	}

	public Product getProductByNameAndPackSize(String name, String packSize) {
		String sql = " from Product as p where p.productname = '" + name + "' and p.packSizeName='" + packSize + "'";
		return (Product) EntityManager.find(sql);
	}

	/**
	 * 从箱码表中获取产品信息
	 * 
	 * @param cartonCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProductInformationByCartonCode(String cartonCode, List<TakeTicketIdcode> ltt) throws Exception {
		// 显示产品信息
		Map<String, Object> messageMap = new HashMap<String, Object>();
		AppCartonCode acc = new AppCartonCode();
		AppProduct ap = new AppProduct();
		AppPrintJob apj = new AppPrintJob();
		CartonCode cc = acc.getByCartonCode(cartonCode);
		if (cc != null) {
			if (!StringUtil.isEmpty(cc.getProductID())) {
				// 返回的产品信息
				Product product = ap.getProductByID(cc.getProductID());
				messageMap.put("product", product);
			}
			if (cc.getPrintJobId() != null && !"".equals(cc.getPrintJobId())) {
				cc.getPrintJobId();
				// 返回的产品信息
				PrintJob printJob = apj.getPrintJobByID(cc.getPrintJobId());
				messageMap.put("printJob", printJob);
			}
		} else if (ltt != null) {
			Product product = ap.getProductByID(ltt.get(0).getProductid());
			messageMap.put("product", product);
		}
		return messageMap;
	}

	/**
	 * 从小码表中获取产品信息
	 * 
	 * @param cartonCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProductInformationByPrimaryCode(PrimaryCode primaryCode) throws Exception {
		// 显示产品信息
		Map<String, Object> messageMap = new HashMap<String, Object>();
		AppCartonCode acc = new AppCartonCode();
		AppProduct ap = new AppProduct();
		AppPrintJob apj = new AppPrintJob();

		// 从小码表中得到产品信息
		if (primaryCode.getPrintJobId() != null) {
			PrintJob printJob = apj.getPrintJobByID(primaryCode.getPrintJobId());
			messageMap.put("pintJob", printJob);
			if (printJob != null && !StringUtil.isEmpty(printJob.getProductId())) {
				Product product = ap.getProductByID(printJob.getProductId());
				messageMap.put("product", product);
			}
		}

		return messageMap;
	}
	
	/**
	 * 先从箱码表中获取产品信息，获取不到，则从小码表中获取
	 * @param idcode 
	 * 
	 * @param cartonCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getProductInformation(CartonCode cc,PrimaryCode pc, Idcode idcode) throws Exception {
		Map<String, Object> messageMap = new HashMap<String, Object>();
		AppCartonCode acc = new AppCartonCode();
		AppProduct ap = new AppProduct();
		AppPrintJob apj = new AppPrintJob();
		// 从箱码表中获取产品信息
		if (cc != null && cc.getPrintJobId() != null) {
			PrintJob printJob  = apj.getPrintJobByID(cc.getPrintJobId());
			messageMap.put("printJob", printJob);
			if (printJob != null && !StringUtil.isEmpty(printJob.getProductId())) {
				Product product = ap.getProductByID(printJob.getProductId());
				messageMap.put("product", product);
			}
			
		} else if(idcode != null) {
			Product product = ap.getProductByID(idcode.getProductid());
			messageMap.put("product", product);
			PrintJob printJob  = apj.getPrintJobByBatAPd(idcode.getProductid(), idcode.getBatch());
			if(printJob==null) {
				printJob = new PrintJob();
				printJob.setBatchNumber(idcode.getBatch());
			}
			messageMap.put("printJob", printJob);
		} else {
			//从小码表中得到产品信息
			if (pc != null && pc.getPrintJobId() != null) {
				PrintJob printJob = apj.getPrintJobByID(pc.getPrintJobId());
				messageMap.put("printJob", printJob);
				if (printJob != null && !StringUtil.isEmpty(printJob.getProductId())) {
					Product product = ap.getProductByID(printJob.getProductId());
					messageMap.put("product", product);
				}
			} else if(pc != null && Constants.PRIMARY_CODE_III_II ==pc.getPrimaryCode().length()) {
				//登记证后六位
				String regCode = pc.getPrimaryCode().substring(1, 7);
				//规格代码 
				String specCode = pc.getPrimaryCode().substring(8, 11);
				Product product = ap.getProductByRegCertAndSpecCode(regCode, specCode);
				messageMap.put("product", product);
				//批次号
				if(!StringUtil.isEmpty(pc.getUploadPrId())) {
					AppCartonSeq appCartonSeq = new AppCartonSeq();
					CartonSeqLog log = appCartonSeq.getCartonSeqLogById(pc.getUploadPrId());
					messageMap.put("cartonSeqLog", log);
				}
			}
		}
		return messageMap;
	}

	public boolean isMcodeAlreadyExists(String mcode) {
		String sql ="select count(*) from Product where mcode = '"+mcode+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	
	public Product getProductByNameAndSpec(String name, String spec) { 
		String sql = " from Product as p where p.productname = '" + name + "' and p.specmode='" + spec + "'";
		return (Product) EntityManager.find(sql);
	}

	public List<Map<String,String>> getProductNameByUserFlag(int i) throws Exception {
		String sql ="select DISTINCT PRODUCTNAME from PRODUCT where USEFLAG=1 order by NLSSORT(productname,'NLS_SORT = SCHINESE_PINYIN_M')";
		return EntityManager.jdbcquery(sql);
	}

	public List<Product> getProductByUseFlagAndPrimaryPrintFlag(int useFlag, int primaryPrintFlag) {
		return EntityManager.getAllByHql("from Product where useflag = " + useFlag + " and (primaryPrintFlag = "+primaryPrintFlag+" or cartonPrintFlag = "+primaryPrintFlag+")");
	}
	
	public Product getProductByRegCertAndSpecCode(String regCertCode, String specCode) throws Exception {
		String sql = " from Product as p where p.regCertCodeFixed='" + regCertCode + "' and specCode='"+specCode+"'";
		return (Product)EntityManager.find(sql); 
	}

	public List<Map<String, String>> getProductsToTransfer(String startTime, String endTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id,ps.sortName,ps.sortnameen,p.mcode,REPLACE(P.matericalchdes, ',', ' ') matericalchdes,	REPLACE(P.matericalendes, ',', ' ') matericalendes,	REPLACE(P.PRODUCTNAME, ',', ' ') PRODUCTNAME,");
		sql.append("REPLACE(P.PRODUCTNAMEEN, ',', ' ') PRODUCTNAMEEN,	REPLACE(P.PACKSIZENAME, ',', ' ') PACKSIZENAME,	REPLACE(P.PACKSIZENAMEEN, ',', ' ') PACKSIZENAMEEN,	REPLACE(P.SPECCODE, ',', ' ') SPECCODE," +
				"p.EXPIRYDAYS,case when p.USEFLAG =1 then '是' else '否' END USEFLAG,br.tagsubvalue COUNTUNIT,");
		sql.append("p.BOXQUANTITY,fu.xquantity from PRODUCT p ");
		sql.append("join PRODUCT_STRUCT ps on p.psid=ps.structcode ");
		sql.append("join F_UNIT fu on fu.productid=p.id and fu.FUNITID="+Constants.DEFAULT_UNIT_ID); 
		sql.append(" join BASE_RESOURCE br on br.tagsubkey = p.COUNTUNIT and br.tagname='CountUnit' ");
		sql.append("where 1=1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append("and ((p.makeDate >=to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss') and p.makeDate <=to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')) or (p.modificationTime >= to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss') and p.modificationTime <= to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss'))) ");
		} else {
			sql.append("and (p.makeDate <=to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss') or p.modificationTime <= to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')) ");
		}
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public Set<String> getNoBonusProductIdSet() throws Exception {
		AppBaseResource abr = new AppBaseResource(); 
		List<BaseResource> pidList = abr.getIsUseBaseResource("NoBonusProduct");
		Set<String> pidSet = new HashSet<String>();
		for (BaseResource br : pidList) {
			if (br == null) { 
				continue;
			}
			pidSet.add(br.getTagsubvalue());
		}
		return pidSet;
	}

	public boolean isRegCertAndSpecCodeAlreadyExists(String regCertCodeFixed,
			String specCode, String productId) {
		String sql ="select count(*) from Product where regCertCodeFixed = '"+regCertCodeFixed+"' and specCode = '"+specCode+"' ";
		if(!StringUtil.isEmpty(productId)) {
			sql += "and id<>'"+productId+"'";
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<Product> getProductByMCodes(String mCodes) {
		String sql = " from Product where useflag=1 and mCode in ("+mCodes+")";
		return EntityManager.getAllByHql(sql);
	}

	public String getPopularProductId(String productName) {
		String hql = "select id from PopularProduct where name='"+productName+"'";
		return (String)EntityManager.find(hql);
	}
	
	public static void main(String[] args) throws Exception {
		AppProduct apc = new AppProduct();
		Object result = apc.getProductByID("033844");
//		List result = apc.getCartonCodeForExcPut(5444);
//		apc.delByPrintJobId(5444);
//		HibernateUtil.commitTransaction();
		System.out.println("--------result:"+result);
	}
}
