package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;


/**
 * @author : jerry
 * @version : 2009-8-26 下午03:49:02
 * www.winsafe.cn
 */
public class AppOrganWithdrawDetail {

	
	public List<OrganWithdrawDetail> getOrganWithdrawDetailAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganWithdrawDetail as owd " +whereSql+" order by owd.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public OrganWithdrawDetail getOrganWithdrawDetailByID(int id)throws Exception{
		String hql = " from OrganWithdrawDetail owd where owd.id=" + id;
		return (OrganWithdrawDetail) EntityManager.find(hql);
	}
	
	public void save(OrganWithdrawDetail organWithdrawDetail)throws Exception{
		EntityManager.save(organWithdrawDetail);
	}
	public void deleteByPIID(String owid)throws Exception{
		String hql = "delete from Organ_Withdraw_Detail where owid = '"+owid+"'";
		EntityManager.updateOrdelete(hql);
	}
	public void delete(OrganWithdrawDetail organWithdrawDetail)throws Exception{
		EntityManager.delete(organWithdrawDetail);
	}
	public List<OrganWithdrawDetail> getOrganWithdrawDetailByOwid(String owid) {
		String hql = " from OrganWithdrawDetail owd  where owd.owid='" + owid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 根据退货单号和退货产品id得到退货明细对象
	 * @param owid 退货单号
	 * @param pid 产品id
	 * @return 退货明细对象,不存在返回Null
	 */
	public OrganWithdrawDetail getOrganWithdrawDetailByOwidPid(String owid,String pid) {
		String hql = " from OrganWithdrawDetail owd where owd.owid='" + owid+"' and owd.productid ='" + pid + "'";
		List<OrganWithdrawDetail> owdds = EntityManager.getAllByHql(hql);
		if(owdds.size() == 0){
			return null;
		}else{
			return owdds.get(0);
		}
	}
	public void updRatifyQuantity(int id, double ratifyquantity, double unitprice)throws Exception{
		String hql = "update Organ_Withdraw_Detail set unitprice="+unitprice+",ratifyquantity="+ratifyquantity+
		", subsum="+unitprice*ratifyquantity+" where id = "+id;
		EntityManager.updateOrdelete(hql);
	}
	
	public List getOrganWithdrawDetail(String pWhereClause) throws Exception {
		String sql = "select ow, owd from OrganWithdraw as ow ,OrganWithdrawDetail as owd,Product as p "
				+ pWhereClause + " order by owd.productid, ow.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganWithdrawDetailByPiidPid(String piid, String batch, String productid)
	throws Exception {
		String sql = " from OrganWithdrawDetail where owid='" + piid
				+ "' and batch='"+batch+"' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
}
