package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIdcode {

	private AppUploadProduceReport appupr = new AppUploadProduceReport();

	public Idcode getBoxIdcode(String idcode) throws Exception {
		UploadProduceReport aUploadProduceReport = appupr.getUploadProduceReportByUnitNo(idcode);
		if (aUploadProduceReport == null) {
			return null;
		}
		String boxIdcode = aUploadProduceReport.getBoxCode();

		return getIdcodeById(boxIdcode);
	}

	/**
	 * 根据Idcode获取批次 Create Time: Oct 12, 2011 10:39:25 AM
	 * 
	 * @param idCode
	 * @return
	 * @throws Exception
	 * @author dufazuo
	 */
	public Idcode getBatchByIdCode(String idCode) throws Exception {
		String hql = "from Idcode where idcode = '" + idCode + "'";
		return (Idcode) EntityManager.find(hql);
	}

	public List searchIdcode(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from Idcode " + pWhereClause + " order by idcode ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List searchIdcode(String pWhereClause) throws Exception {
		String sql = " from Idcode " + pWhereClause + " order by makedate ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List searchIdcode() throws Exception {
		String sql = " from Idcode  order by makedate ";
		return EntityManager.getAllByHql(sql);
	}

	public void addIdcode(Idcode pii) throws Exception {
		String sql = "insert into idcode(idcode,productid,productname,batch,producedate,validate,lcode,startno,endno,unitid,quantity,fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit,ProvideID,ProvideName,MakeDate,PColumn)  select '"
				+ pii.getIdcode()
				+ "','"
				+ pii.getProductid()
				+ "','"
				+ pii.getProductname()
				+ "','"
				+ pii.getBatch()
				+ "','"
				+ pii.getProducedate()
				+ "','"
				+ pii.getVad()
				+ "','"
				+ pii.getLcode()
				+ "','"
				+ pii.getStartno()
				+ "','"
				+ pii.getEndno()
				+ "',"
				+ pii.getUnitid()
				+ ","
				+ pii.getQuantity()
				+ ","
				+ pii.getFquantity()
				+ ","
				+ pii.getPackquantity()
				+ ","
				+ pii.getIsuse()
				+ ","
				+ pii.getIsout()
				+ ",'"
				+ pii.getBillid()
				+ "',"
				+ pii.getIdbilltype()
				+ ",'"
				+ pii.getMakeorganid()
				+ "','"
				+ pii.getWarehouseid()
				+ "','"
				+ pii.getWarehousebit()
				+ "','"
				+ pii.getProvideid()
				+ "','"
				+ pii.getProvidename()
				+ "',getdate(), month(getdate()) "
				+ "  where not exists(select idcode from idcode as a where a.idcode='"
				+ pii.getIdcode()
				+ "' "
				+ " and a.batch='"
				+ pii.getBatch()
				+ "' and a.startno='"
				+ pii.getStartno()
				+ "' and a.endno='" + pii.getEndno() + "' )";
		EntityManager.updateOrdelete(sql);
	}

	public void addIdcode2(Idcode pii) throws Exception {
		String sql = "insert into idcode(idcode,productid,productname,batch,producedate,vad,lcode,startno,endno,unitid,quantity,fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit,ProvideID,ProvideName, boxcode, cartonCode, palletCode , ncLotNo,MakeDate,PColumn)  select '"
				+ pii.getIdcode()
				+ "','"
				+ pii.getProductid()
				+ "','"
				+ pii.getProductname()
				+ "','"
				+ pii.getBatch()
				+ "','"
				+ pii.getProducedate()
				+ "','"
				+ pii.getVad()
				+ "','"
				+ pii.getLcode()
				+ "','"
				+ pii.getStartno()
				+ "','"
				+ pii.getEndno()
				+ "',"
				+ pii.getUnitid()
				+ ","
				+ pii.getQuantity()
				+ ","
				+ pii.getFquantity()
				+ ","
				+ pii.getPackquantity()
				+ ","
				+ pii.getIsuse()
				+ ","
				+ pii.getIsout()
				+ ",'"
				+ pii.getBillid()
				+ "',"
				+ pii.getIdbilltype()
				+ ",'"
				+ pii.getMakeorganid()
				+ "','"
				+ pii.getWarehouseid()
				+ "','"
				+ pii.getWarehousebit()
				+ "','"
				+ pii.getProvideid()
				+ "','"
				+ pii.getProvidename()
				+ "','"
				+ pii.getBoxCode()
				+ "','"
				+ pii.getCartonCode()
				+ "','"
				+ pii.getPalletCode()
				+ "','"
				+ pii.getNcLotNo()
				+ "',sysdate, to_char(sysdate,'MM') "
				+ " from dual where not exists(select idcode from idcode a where a.idcode='"
				+ pii.getIdcode() + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	public String addIdcodeByBuffer(Idcode pii) throws Exception {
		String sql = "insert into idcode(idcode,productid,productname,batch,producedate,vad,lcode,startno,endno,unitid,quantity,fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit,ProvideID,ProvideName, boxcode, cartonCode, palletCode , ncLotNo,MakeDate,PColumn)  select '"
				+ pii.getIdcode()
				+ "','"
				+ pii.getProductid()
				+ "','"
				+ pii.getProductname()
				+ "','"
				+ pii.getBatch()
				+ "','"
				+ pii.getProducedate()
				+ "','"
				+ pii.getVad()
				+ "','"
				+ pii.getLcode()
				+ "','"
				+ pii.getStartno()
				+ "','"
				+ pii.getEndno()
				+ "',"
				+ pii.getUnitid()
				+ ","
				+ pii.getQuantity()
				+ ","
				+ pii.getFquantity()
				+ ","
				+ pii.getPackquantity()
				+ ","
				+ pii.getIsuse()
				+ ","
				+ pii.getIsout()
				+ ",'"
				+ pii.getBillid()
				+ "',"
				+ pii.getIdbilltype()
				+ ",'"
				+ pii.getMakeorganid()
				+ "','"
				+ pii.getWarehouseid()
				+ "','"
				+ pii.getWarehousebit()
				+ "','"
				+ pii.getProvideid()
				+ "','"
				+ pii.getProvidename()
				+ "','"
				+ pii.getBoxCode()
				+ "','"
				+ pii.getCartonCode()
				+ "','"
				+ pii.getPalletCode()
				+ "','"
				+ pii.getNcLotNo()
				+ "',sysdate, to_char(sysdate,'MM') "
				+ " from dual where not exists(select idcode from idcode a where a.idcode='"
				+ pii.getIdcode() + "') ;";
		//EntityManager.updateOrdelete(sql);
		return sql;
	}
	

	public void updIdcode(Idcode pii) throws Exception {
		EntityManager.update(pii);
	}

	
	
	public Idcode getIdcodeById(String idcode) throws Exception {
		String sql = "from Idcode where idcode='" + idcode + "'";
		return (Idcode) EntityManager.find(sql);
	}
	
	/**
	 * 查询条码：
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	public Idcode getIdcodeByCode(String idcode) throws Exception {
		String sql = "from Idcode where idcode='" + idcode + "' ";
		return (Idcode) EntityManager.find(sql);
	}
	/**
	 * 获取中盒中所有的散条码状态
	 * Create Time 2014-4-17 下午06:47:46 
	 * @param boxIdcdoe
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public Object[] getBoxStatus(String boxIdcdoe) throws Exception {
		Object[] result = new String[6];
		String sql = "select count(*),sum(isuse),sum(isout),max(productid),max(batch),max(producedate) from Idcode where boxCode='" + boxIdcdoe + "'";
		result = (Object[]) EntityManager.find(sql);
		return result;
	}
	
	/**
	 * 获取外箱中所有的散条码状态
	 * Create Time 2014-4-17 下午06:47:46 
	 * @param boxIdcdoe
	 * @return
	 * @throws Exception
	 * @author lipeng
	 */
	public Object[] getCartonCodeStatus(String cartonCode) throws Exception {
		Object[] result = new String[6];
		String sql = "select count(*),sum(isuse),sum(isout),max(productid),max(batch),max(producedate) from Idcode where cartonCode='" + cartonCode + "'";
		result = (Object[]) EntityManager.find(sql);
		return result;
	}
	
	/**
	 * 获取外箱中所有的散条码的当前状态
	 * Create Time 2014-4-17 下午06:47:46 
	 * @param boxIdcdoe
	 * @return
	 * @throws Exception
	 * @author houzhen
	 */
	public Object[] getCartonCodeCurrStatus(String cartonCode) throws Exception {
		Object[] result = new String[6];
		String sql = "select count(*),min(isuse),max(isout),max(productid),max(batch),max(warehouseid),max(producedate) from Idcode where cartonCode='" + cartonCode + "'";
		result = (Object[]) EntityManager.find(sql);
		return result;
	}
	
	/**
	 * 获取中盒中所有的散条码的当前状态
	 * Create Time 2014-4-17 下午06:47:46 
	 * @param boxIdcdoe
	 * @return
	 * @throws Exception
	 * @author houzhen
	 */
	public Object[] getBoxCurrStatus(String boxIdcdoe) throws Exception {
		Object[] result = new String[6];
		String sql = "select count(*),min(isuse),max(isout),max(productid),max(batch),max(warehouseid),max(producedate) from Idcode where boxCode='" + boxIdcdoe + "'";
		result = (Object[]) EntityManager.find(sql);
		return result;
	}

	public boolean IsExistIdcodeById(String idcode) throws Exception {
		String sql = "select count(*) from Idcode where idcode='" + idcode + "'";
		return EntityManager.getRecordCount(sql) > 0;
	}
	
	public boolean IsExistIdcodeByPid(String pid) throws Exception {
//		String sql = "select count(*) from Idcode where productid='" + pid + "'";
		String sql = "select count(*) from PrintJob where productId='" + pid + "'";
		return EntityManager.getRecordCount(sql) > 0;
	}

	public Idcode getIdcodeById(String idcode, int isuse) throws Exception {
		String sql = "from Idcode where idcode='" + idcode + "' and isuse=" + isuse;
		return (Idcode) EntityManager.find(sql);
	}
	
	public List getIdcodeByIds(String idcodes, int isuse) throws Exception {
		String sql = "from Idcode where idcode in (" + idcodes + ") and isuse=" + isuse;
		return EntityManager.getAllByHql(sql);
	}

	public Idcode getIdcodeByStartno(String startno) throws Exception {
		String sql = "from Idcode where startno<='" + startno + "' and endno>='" + startno + "'";
		return (Idcode) EntityManager.find(sql);
	}

	/**
	 * 获取条码，或者该条码所在的托条码
	 * 
	 * @param startno
	 * @param endno
	 * @return
	 * @throws Exception
	 */
	public Idcode getIdcodeByWLM(String startno, String endno) throws Exception {
		// String sql =
		// "from Idcode where startno<='"+startno+"' and endno>='"+startno+
		// "' and startno<='"+endno+"' and endno>='"+endno+"' and quantity>0 and isout=0 order by fquantity ";
		String sql = "from Idcode where '" + startno + "' between startno and endno " + " and '" + endno
				+ "' between startno and endno and quantity>0 and isout=0 order by fquantity ";
		return (Idcode) EntityManager.find(sql);
	}

	/**
	 * 判断条码或者该条码所在的托条码是否存在
	 * 
	 * @param startno
	 * @param endno
	 * @return
	 * @throws Exception
	 */
	public boolean isAlreadyByWLM(String startno, String endno) throws Exception {
		String sql = "select count(idcode) from Idcode where '" + startno + "' between startno and endno " + " and '" + endno
				+ "' between startno and endno ";
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断条码是否被拆分过
	 * 
	 * @param startno
	 * @param endno
	 * @return
	 * @throws Exception
	 */
	public boolean isBreak(String startno, String endno) throws Exception {
		String sql = "select count(idcode) from Idcode where startno between '" + startno + "' and '" + endno
				+ "' and endno between '" + startno + "' and '" + endno + "' ";
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public void updIsUse(String idcode, int isuse) throws Exception {
		String sql = "update idcode set isuse=" + isuse + " where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 
	 * //按箱条码,对idcode表进行标志(标志当前仓库,并把NCLotno 记录盘库的单据号)
	 * @param idcode
	 * @throws Exception
	 */
	public void updWidAndNclByIdcode(String warehouseid,String ncltono,String idcode) throws Exception {
		String sql = "update idcode set warehouseid='"+warehouseid+"' ,nclotno='"+ncltono+"'  where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 
	 * (主要清理掉warehouseid,warehousebit,isused,isout)null  000  1 0
	 * @param idcode
	 * @throws Exception
	 */
	public void updIdcode(String idcode) throws Exception {
		String sql = "update idcode set warehouseid='"+null+"' ,warehousebit='000' , isuse=1  , isout=0  where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIdcodeWarehouse(String warehouseId) throws Exception {
		String sql = "update idcode set warehouseid='"+null+"' ,warehousebit='000' , isuse=1  , isout=0  where warehouseid='" + warehouseId + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIsUseOut(String idcode, int isuse, int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout=" + isout + " where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	public void updBoxIsUseOut(String idcode, int isuse, int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout=" + isout + " where boxCode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	public void updCartonIsUseOut(String idcode, int isuse, int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout=" + isout + " where cartonCode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIdcodeUseByCodeColumn(String codeColumn, int isuse, int isout,String code) throws Exception{
		String sql = "update idcode set isuse = "+isuse+", isout = "+isout+" where "+codeColumn+" = '"+code+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	/**
	 * 更新条码状态
	 * @param idcode
	 * @param makeorganid
	 * @param warehouseid
	 * @param warehousebit
	 * @param isuse
	 * @param isout
	 * @throws Exception
	 */
	public void updIsUse(String idcode, String makeorganid, String warehouseid, String warehousebit, int isuse, int isout)
			throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ warehousebit + "',isuse=" + isuse + ",isout=" + isout + " where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	public String updIsUseByBuffer(String idcode, String makeorganid, String warehouseid, String warehousebit, int isuse, int isout)
	throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid
				+ "',warehouseid='" + warehouseid + "',warehousebit='"
				+ warehousebit + "',isuse=" + isuse + ",isout=" + isout
				+ " where idcode='" + idcode + "' ;";
		return sql;
	}	
	
	public void updBoxIsUse(String idcode, String makeorganid, String warehouseid, String warehousebit, int isuse, int isout)
	throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ warehousebit + "',isuse=" + isuse + ",isout=" + isout + " where boxCode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updCartonCodeIsUse(String idcode, String makeorganid, String warehouseid, String warehousebit, int isuse, int isout)
	throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ warehousebit + "',isuse=" + isuse + ",isout=" + isout + " where cartonCode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIdcodeByCodeColumn(String idcode, String makeorganid, String warehouseid, String warehousebit, String codeColumn, int isuse, int isout)
	throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ warehousebit + "',isuse=" + isuse + ",isout=" + isout + " where "+codeColumn+"='" + idcode + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void delIdcodeByid(String idcode) throws Exception {
		String sql = "delete from idcode where idcode='" + idcode + "'";
		EntityManager.updateOrdelete(sql);

	}
	public void delIdcodeByids(String idcodes) throws Exception {
		String sql = "delete from idcode where idcode in (" + idcodes + ")";
		EntityManager.updateOrdelete(sql);
	}

	public List getUseIdcode(String productid) throws Exception {
		String sql = "from Idcode where productid='" + productid + "' and isuse=1";
		return EntityManager.getAllByHql(sql);
	}

	public Idcode getIdcodeByIdAndWarehouse(String idcode, String warehouseid) throws Exception {
		String sql = "from Idcode where idcode='" + idcode + "' and warehouseid='" + warehouseid + "'";
		return (Idcode) EntityManager.find(sql);
	}
	public List getIdcodeByIdsAndWarehouse(String idcodes, String warehouseid) throws Exception {
		String sql = "from Idcode where idcode in (" + idcodes + ") and warehouseid='" + warehouseid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public Idcode getIdcodeByWLMII(String startno, String endno) throws Exception {
		String sql = "from Idcode where ('" + startno + "' between startno and endno)" + " and ('" + endno
				+ "' between startno and endno ) and (quantity>0) and (isout=0) order by fquantity ";
		return (Idcode) EntityManager.find(sql);
	}

	public List<Idcode> queryByInIdcode(String idcodes) {
		String sql = "from Idcode where idcode in(" + idcodes + ")";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<Idcode> queryLikeIdcode(String idcode) {
		String sql = "from Idcode where idcode like '" + idcode + "%'";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * 批量更新条码
	 * 
	 * @param makeorganid
	 * @param warehouseid
	 * @param idcodes
	 * @throws Exception
	 * @author wenping
	 * @CreateTime Jan 15, 2013 11:28:36 AM
	 */
	public void bulkUpdIdcode(String makeorganid, String warehouseid, String idcodes) throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ Constants.WAREHOUSE_BIT_DEFAULT + "',isuse=1,isout=0 where idcode in(" + idcodes + ")";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 经销商入库批量更改条码
	 * @param makeorganid
	 * @param warehouseid
	 * @param idcodes
	 * @throws Exception
	 */
	public void bulkUpdIdcode2(String makeorganid, String warehouseid, String idcodes) throws Exception {
		String sql = "update idcode set makeorganid='" + makeorganid + "',warehouseid='" + warehouseid + "',warehousebit='"
				+ Constants.WAREHOUSE_BIT_DEFAULT + "',dealerreceive=isnull(dealerreceive,'')+'" + warehouseid + ",',isuse=1,isout=0 where idcode in(" + idcodes + ")";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 将条码的dealerreceive设置为空
	 * @param ttid
	 * @throws Exception
	 */
	public void updDealerreceiveNullByTtid(String ttid) throws Exception {
		String sql = "update idcode set dealerreceive=null where idcode in (select idcode from take_ticket_idcode where ttid='" + ttid + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 将条码的dealerreceive设置为仓库
	 * @param warehouseid
	 * @param ttid
	 * @throws Exception
	 */
	public void updIdcodeDealerreceiveValByttid(String warehouseid,String ttid) throws Exception {
		String sql = "update idcode set dealerreceive='" + warehouseid + ",' where idcode in (select idcode from take_ticket_idcode where ttid='" + ttid + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * @param warehouseid
	 * @param ttid
	 * @throws Exception
	 */
	public void updIdcodeByttid(String ttid,int isuse,int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout= " + isout + " where idcode in (select idcode from take_ticket_idcode where ttid='" + ttid + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIdcodeByttid(String ttid,String wid) throws Exception {
		String sql = "update idcode set  warehouseid = '" + wid + "'  where idcode in (select idcode from take_ticket_idcode where ttid='" + ttid + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 批量更新条码数量
	 * @param idcode
	 * @param isuse
	 * @param isout
	 * @throws Exception
	 */
	public void updIsUseOutByIdcodes(String idcodes, int isuse, int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout=" + isout + " where idcode in (" + idcodes + ")";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 返回批量添加条码的sql
	 * @param pii
	 * @throws Exception
	 */
	public String reAddIdcode2Sql(Idcode pii) throws Exception {
		String sql = "insert into idcode(idcode,productid,productname,batch,producedate,validate,lcode,startno,endno,unitid,quantity,fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit,ProvideID,ProvideName,MakeDate,PColumn)  select '"
				+ pii.getIdcode()
				+ "','"
				+ pii.getProductid()
				+ "','"
				+ pii.getProductname()
				+ "','"
				+ pii.getBatch()
				+ "','"
				+ pii.getProducedate()
				+ "','"
				+ pii.getVad()
				+ "','"
				+ pii.getLcode()
				+ "','"
				+ pii.getStartno()
				+ "','"
				+ pii.getEndno()
				+ "',"
				+ pii.getUnitid()
				+ ","
				+ pii.getQuantity()
				+ ","
				+ pii.getFquantity()
				+ ","
				+ pii.getPackquantity()
				+ ","
				+ pii.getIsuse()
				+ ","
				+ pii.getIsout()
				+ ",'"
				+ pii.getBillid()
				+ "',"
				+ pii.getIdbilltype()
				+ ",'"
				+ pii.getMakeorganid()
				+ "','"
				+ pii.getWarehouseid()
				+ "','"
				+ pii.getWarehousebit()
				+ "','"
				+ pii.getProvideid()
				+ "','"
				+ pii.getProvidename()
				+ "',getdate(), month(getdate()) "
				+ "  where not exists(select idcode from idcode as a where a.idcode='"
				+ pii.getIdcode() + "');";
		return sql;
	}
	
	public void addIdcode3(String sql) throws Exception {
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 根据打印任务添加条码
	 * 
	 * @param printJobId 打印任务ID
	 * @param unitId 产品单位ID
	 * @param warehousId 
	 * @param boxQuantity 
	 * @throws Exception
	 */
	public void addIdcodeByPrintJob(Integer printJobId, Integer unitId, String warehousId, Double packQuantity) throws Exception {
		String sql = "insert into idcode(IDCODE, PRODUCTID, PRODUCTNAME,BATCH,PRODUCEDATE, UNITID, QUANTITY,ISUSE,ISOUT,MAKEORGANID,CARTONCODE,WAREHOUSEID,PACKQUANTITY,WAREHOUSEBIT)" +
						   " select cc.CARTON_CODE , cc.PRODUCT_ID, pj.MATERIAL_NAME, pj.BATCH_NUMBER, pj.PRODUCTION_DATE,"+unitId+",1,1,0,pj.CREATE_USER,cc.CARTON_CODE," +warehousId+","+packQuantity+",'"+Constants.WAREHOUSE_BIT_DEFAULT +"'"+
						   " from CARTON_CODE cc join PRINT_JOB pj on CC.PRINT_JOB_ID = PJ.PRINT_JOB_ID where PJ.PRINT_JOB_ID = " + printJobId+ " and not exists(select idcode from idcode a where a.idcode=cc.CARTON_CODE)";
		EntityManager.executeUpdate(sql);
	}
	
	/**
	 * 将ttid的条码复制到单据条码表中
	 * 
	 * @param bsort
	 * @param ttid
	 * @param billid
	 * @throws Exception
	 */
	public Integer getCodeQuantity(int bSort, String productId, String billNo) {
		String tablename = Constants.TT_IDCODE_TABLE[bSort];
		String column = Constants.TT_MAIN_COLUMN[bSort];
		String sql = "select count(*) from " + tablename + " where "+column+" = '"+billNo+"' and productid = '" + productId+"'";
		return EntityManager.getRecordCountBySql(sql);
	}
	
	public boolean isInUseById(String idcode) throws Exception {
		String sql = "select count(*) from Idcode where idcode='" + idcode + "' and isuse = 0";
		return EntityManager.getRecordCount(sql) > 0;
	}

	public void addIdcodesFromSapDelivery(List<Idcode> idcodeList) {
		List<String> batchSqls = new ArrayList<String>();
		for(Idcode pii : idcodeList) {
			String sql = "insert into idcode(idcode,productid,productname,batch,producedate,vad,lcode,startno,endno,unitid,quantity,fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit,ProvideID,ProvideName, boxcode, cartonCode, palletCode , ncLotNo,MakeDate,PColumn)  select '"
				+ StringUtil.removeNull(pii.getIdcode())
				+ "','"
				+ StringUtil.removeNull(pii.getProductid())
				+ "','"
				+ StringUtil.removeNull(pii.getProductname())
				+ "','"
				+ StringUtil.removeNull(pii.getBatch())
				+ "','"
				+ StringUtil.removeNull(pii.getProducedate())
				+ "','"
				+ StringUtil.removeNull(pii.getVad())
				+ "','"
				+ StringUtil.removeNull(pii.getLcode())
				+ "','"
				+ StringUtil.removeNull(pii.getStartno())
				+ "','"
				+ StringUtil.removeNull(pii.getEndno())
				+ "',"
				+ pii.getUnitid()
				+ ","
				+ pii.getQuantity()
				+ ","
				+ pii.getFquantity()
				+ ","
				+ pii.getPackquantity()
				+ ","
				+ pii.getIsuse()
				+ ","
				+ pii.getIsout()
				+ ",'"
				+ StringUtil.removeNull(pii.getBillid())
				+ "',"
				+ pii.getIdbilltype()
				+ ",'"
				+ StringUtil.removeNull(pii.getMakeorganid())
				+ "','"
				+ StringUtil.removeNull(pii.getWarehouseid())
				+ "','"
				+ StringUtil.removeNull(pii.getWarehousebit())
				+ "','"
				+ StringUtil.removeNull(pii.getProvideid())
				+ "','"
				+ StringUtil.removeNull(pii.getProvidename())
				+ "','"
				+ StringUtil.removeNull(pii.getBoxCode())
				+ "','"
				+ StringUtil.removeNull(pii.getCartonCode())
				+ "','"
				+ StringUtil.removeNull(pii.getPalletCode())
				+ "','"
				+ StringUtil.removeNull(pii.getNcLotNo())
				+ "',sysdate, to_char(sysdate,'MM') "
				+ " from dual where not exists(select idcode from idcode a where a.idcode='"
				+ pii.getIdcode() + "')";
			batchSqls.add(sql);
			
			if(batchSqls.size() == Constants.DB_BULK_SIZE){
				EntityManager.executeBatch(batchSqls);
				batchSqls.clear();
			}
		}
		if(batchSqls.size() > 0) {
			EntityManager.executeBatch(batchSqls);
		}
	}
	
	public void updIdcodeBySamId(String billNo, int isuse,int isout) throws Exception {
		String sql = "update idcode set isuse=" + isuse + ",isout= " + isout + " where idcode in (select idcode from STOCK_ALTER_MOVE_IDCODE where samid='" + billNo + "')";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIdcodeBySamId(String billNo,String wid) throws Exception {
		String sql = "update idcode set  warehouseid = '" + wid + "'  where idcode in (select idcode from STOCK_ALTER_MOVE_IDCODE where samid='" + billNo + "')";
		EntityManager.updateOrdelete(sql);
	}

	public List<Idcode> getOutIdcodeBySamid(String samid) {
		String sql = "from Idcode where idcode in(select idcode from TakeTicketIdcode where ttid =(select id from TakeTicket where billno = '"+samid+"'))";
		return EntityManager.getAllByHql(sql);
	}

	public List<Idcode> getInIdcodeBySamid(String samid) {
		String sql = "from Idcode where idcode in(select idcode from StockAlterMoveIdcode where samid ='"+samid+"')";
		return EntityManager.getAllByHql(sql);
	}

	public List<Idcode> getIdcodeByOwid(String owid) {
		String sql = "from Idcode where idcode in(select idcode from OrganWithdrawIdcode where owid ='"+owid+"')";
		return EntityManager.getAllByHql(sql);
	}

	public void doIntegral(String billNo) throws Exception {
		String sql = "update idcode set isintegral = 1 where idcode in ( " +
				"select idcode from TAKE_TICKET_IDCODE where ttid = (select id from TAKE_TICKET where billno = '"+billNo+"') " +
				")";
		EntityManager.updateOrdelete(sql);
	}
	
	public void undoIntegral(String billNo) throws Exception {
		String sql = "update idcode set isintegral = 0 where idcode in ( " +
				"select idcode from ORGAN_WITHDRAW_IDCODE where owid = '"+billNo+"' " +
				")";
		EntityManager.updateOrdelete(sql);
	}

	public void dodelcodeIntegral(Map<String,Boolean> hasDeletedCodeMap) throws HibernateException, SQLException, Exception {
		StringBuffer buffer = new StringBuffer();
		for(String idcode : hasDeletedCodeMap.keySet()) {
			buffer.append(idcode).append(",");
		}
		String idcodes = buffer.toString();
		String sql = "update idcode set isintegral = 0 where idcode in ("+idcodes.substring(0,idcodes.length()-1 )+")";
		System.out.println(sql);
		EntityManager.updateOrdelete(sql);
	}
	
	public void doIntegral(String billNo, String productId) throws Exception {
		String sql = "update idcode set isintegral = 1 where idcode in ( " +
				"select idcode from STOCK_ALTER_MOVE_IDCODE where samid = '"+billNo+"' and productId = '"+productId+"') ";
		EntityManager.updateOrdelete(sql); 
	}

	public List<Map<String, String>> getAllowReturnedIntegralCodeSet(
			String idcodes, String toOrganId, String fromOrganId) throws HibernateException, SQLException { 
		String sql = "select sami.idcode from STOCK_ALTER_MOVE_IDCODE sami " +
//				"join ( select max(id) id, idcode from STOCK_ALTER_MOVE_IDCODE where idcode in ("+idcodes+")" +
//				"group by IDCODE " +
//				") temp on sami.id = temp.id " +
				"join STOCK_ALTER_MOVE sam on sam.id = sami.samid and sami.idcode in ("+idcodes+") and sam.outorganid = '"+fromOrganId+"' and sam.receiveorganid = '"+toOrganId+"' and sam.iscomplete = 1 " +
				"join idcode ic on ic.idcode = sami.idcode and ic.isintegral = 1 ";
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getAllowReturnedIntegralCode(String idcodes) throws HibernateException, SQLException { 
		/*String sql = "select sami.idcode,sam.receiveorganid inoid,sam.receiveorganidname inoname,sam.outorganid oid,sam.outorganname oname from STOCK_ALTER_MOVE_IDCODE sami  " +
				"join ( select min(sami.id) id, sami.idcode from STOCK_ALTER_MOVE_IDCODE sami " +
				"join STOCK_ALTER_MOVE sam on sam.id = sami.samid and sami.idcode in ("+idcodes+") and sam.iscomplete = 1" +
				"join Organ o on o.id = sam.outorganid and o.iskeyretailer = 1 " +
				"group by sami.IDCODE " + 
				") temp on sami.id = temp.id " +
				"join STOCK_ALTER_MOVE sam on sam.id = sami.samid and sam.iscomplete = 1 " +
				"join idcode ic on ic.idcode = sami.idcode and ic.isintegral = 1 ";*/
		String sql = "select tti.idcode,sam.receiveorganid inoid,sam.receiveorganidname inoname,sam.outorganid oid,sam.outorganname oname from TAKE_TICKET_IDCODE tti join ( " +
				"select max(tti.id) id,IDCODE from TAKE_TICKET_IDCODE tti " +
				"join TAKE_TICKET tt on tt.id = tti.ttid and tti.idcode in ("+idcodes+") and tti.bonuspoint>0 " +
				"join Organ o on o.id = tt.oid and o.iskeyretailer = 1 " +
				"GROUP BY tti.idcode " +
				") temp on temp.id = tti.id " +
				"join TAKE_TICKET tt on tt.id = tti.ttid " +
				"join idcode ic on ic.idcode = tti.idcode and ic.isintegral = 1 " +
				"join STOCK_ALTER_MOVE sam on sam.id = tt.billno and sam.iscomplete = 1 ";
		return EntityManager.jdbcquery(sql);
	} 
	
	public List<Map<String, String>> getNotAllowedOutIntegralCode(String idcodes) throws HibernateException, SQLException { 
		String sql = "select tti.idcode,sam.receiveorganid inoid,sam.receiveorganidname inoname,sam.outorganid oid,sam.outorganname oname from TAKE_TICKET_IDCODE tti join ( " +
				"select max(tti.id) id,IDCODE from TAKE_TICKET_IDCODE tti where tti.idcode in ("+idcodes+") "+//and tti.bonuspoint>0  " +
				"GROUP BY tti.idcode " +   
				") temp on temp.id = tti.id " +
				"join TAKE_TICKET tt on tt.id = tti.ttid " +
//				"join Organ o on o.id = tt.oid and o.iskeyretailer = 1 " +
				"join STOCK_ALTER_MOVE sam on sam.id = tt.billno ";//and sam.iscomplete = 1 ";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getAlreadyReturnedIntegralCode(
			String idcodes) throws HibernateException, SQLException { 
		String sql = "select tti.idcode,ow.porganid oid,ow.porganname oname,ow.makedate from TAKE_TICKET_IDCODE tti join ( " +
			"select max(tti.id) id,IDCODE from TAKE_TICKET_IDCODE tti where tti.idcode in ("+idcodes+") " +
			"GROUP BY tti.idcode " +
			") temp on temp.id = tti.id " +
			"join TAKE_TICKET tt on tt.id = tti.ttid " +
			"join ORGAN_WITHDRAW ow on ow.id = tt.billno and ow.iscomplete = 1 ";
		return EntityManager.jdbcquery(sql);
	}

	public void getReturnCodeInfo(String idcodes) {
		// TODO Auto-generated method stub
		
	}

	public List<Map<String, String>> getCodeFlows(String idcode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select outo.id outoid,outo.organname outOName,TT.MAKEDATE outDate,INO.organname inOName,ino.id inoid,sam.receivedate saminDate,ow.receivedate owindate,TTI.BONUSPOINT outBonusPoint, sami.bonuspoint inBonusPoint, tt.BSORT type from TAKE_TICKET_IDCODE tti");
		sql.append(" join TAKE_TICKET tt on tti.ttid = tt.id and tti.idcode = '"+idcode+"'");
		sql.append(" join ORGAN outo on tt.oid = outo.id and outo.organtype=" + OrganType.Dealer.getValue()+" and outo.organmodel <> "+DealerType.PD.getValue());
		sql.append(" join ORGAN ino on tt.inoid = ino.id and ino.organtype=" + OrganType.Dealer.getValue());
		sql.append(" LEFT JOIN STOCK_ALTER_MOVE sam on tt.billno = sam.id");
		sql.append(" LEFT JOIN ORGAN_WITHDRAW ow on tt.billno = ow.id");
		sql.append(" LEFT JOIN STOCK_ALTER_MOVE_IDCODE sami on sami.idcode = tti.idcode and sam.id = sami.samid");
		sql.append(" ORDER BY tt.makedate desc");
		return EntityManager.jdbcquery(sql.toString());
	}

	public String getIdcodeBelongedInOrganId(String cartonCode) {
		String hql = "select w.makeorganid from Idcode ic,Warehouse w where ic.warehouseid = w.id and ic.idcode = '"+cartonCode+"'";
		return (String)EntityManager.find(hql);
	}

}
