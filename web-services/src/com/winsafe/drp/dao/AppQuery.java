package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppQuery {
	/**
	 * @author jason.huang
	 * @param dpd
	 *            保存query
	 */

	public void addQuery(Object dpd) throws Exception {
		EntityManager.save(dpd);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            whereSql 获取Query信息
	 */
	public List selectQuery(HttpServletRequest request, int pageSize, String whereSql)
			throws Exception {
		String hql = " from Query q " + whereSql + " order by q.findDt desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	/**
	 * @author jason.huang
	 * @param s
	 *            whereSql 获取Query信息
	 */
	public List selectQuery(String whereSql) throws Exception {
		String hql = " from Query q " + whereSql + " order by q.findDt desc";
		return EntityManager.getAllByHql(hql);
	}

	/**
	 * 获取queryById
	 * 
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 * 
	 */

	public Query getQueryByID(String id) throws Exception {
		String sql = " from Query as q where q.proNumber='" + id + "' order by findDt desc";
		return (Query) EntityManager.find(sql);
	}

	/**
	 * 
	 * 更新query信息
	 * 
	 * @author jason.huang
	 * @param s
	 * @throws Exception
	 * 
	 */

	public void updateQuery(Query q) throws Exception {
		EntityManager.update(q);
	}

	/**
	 * 获取ip地址
	 * 
	 * @author jason.huang
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * 更新query信息
	 * 
	 * @author jason.huang
	 * @param s
	 * @throws Exception
	 * 
	 */

	public void updateQueryByPid(int queryNum, String pronumber) throws Exception {
		String sql = " update query set queryNum=" + queryNum + " where pro_Number='" + pronumber
				+ "' ";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * @author jason.huang
	 * @param s
	 *            whereSql 获取Query信息
	 */
	public List selectQueryByArea() throws Exception {
		String whereSql = " where telnumber is not null and (areas is null or city is null)";
		String hql = " from Query q " + whereSql + " order by q.findDt asc";
		return EntityManager.getAllByHql(hql);
	}

	public List<Map<String, String>> getFlowByCartonCode(String cartonCode) throws Exception {
		String sql ="select o.organname from TAKE_TICKET_IDCODE tti "
				+ "join TAKE_TICKET tt on TTI.ttid=TT.id and TT.ISAUDIT=1 "
				+ "join organ o on o.id = TT.INOID "
				+ "join organ outo on outo.id = TT.OID "
				+ "where idcode ='"+cartonCode+"' "
				+ "and ((outo.organtype="+OrganType.Plant.getValue()+") or (outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel in ("+DealerType.PD.getValue()+"))) ORDER BY tti.id DESC";
		return EntityManager.jdbcquery(sql);
	}
	
	public List selectQuerybySql(HttpServletRequest request, int pageSize, String whereSql)
			throws Exception {
		String timeBeginDate = request.getParameter("BeginDate");
		if (timeBeginDate == null || "".equals(timeBeginDate)) {
			timeBeginDate = DateUtil.getCurrentDateString();
			request.setAttribute("BeginDate", timeBeginDate);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select q.PRO_NUMBER proNumber,PC.CARTON_CODE cartonCode,cc.out_pin_code mPin,PC.PRIMARY_CODE primaryCode,p.productname,p.specmode,q.FINDDT,q.FINDTYPE,q.TELNUMBER,q.AREAS,q.CITY,q.QUERYNUM from QUERY q ");
		sql.append("left join PRIMARY_CODE pc on (q.PRO_NUMBER=pc.PRIMARY_CODE or q.PRO_NUMBER=pc.COVERT_CODE) ");
		sql.append("LEFT JOIN CARTON_CODE cc on cc.carton_code=pc.CARTON_CODE ");
		sql.append("LEFT JOIN PRODUCT p on p.id=q.productid where 1=1 ");
		//查询码
		if(!StringUtil.isEmpty(request.getParameter("proNumber"))) {
			sql.append(" and q.PRO_NUMBER='").append(request.getParameter("proNumber")).append("' ");
		}
		//箱码
		if(!StringUtil.isEmpty(request.getParameter("cartonCode"))) {
			sql.append(" and (q.PRO_NUMBER='").append(request.getParameter("cartonCode")).append("' or cc.carton_code='").append(request.getParameter("cartonCode")).append("') ");
		}
		//小包装码
		if(!StringUtil.isEmpty(request.getParameter("primaryCode"))) {
			sql.append(" and pc.PRIMARY_CODE='").append(request.getParameter("primaryCode")).append("' ");
		}
		//产品名规格
		if(!StringUtil.isEmpty(request.getParameter("ProductName"))&&!StringUtil.isEmpty(request.getParameter("packSizeName"))) {
			sql.append("and p.productName='").append(request.getParameter("ProductName")).append("' and p.specMode='").append(request.getParameter("packSizeName")).append("' ");
		} else if(!StringUtil.isEmpty(request.getParameter("ProductName"))&&StringUtil.isEmpty(request.getParameter("packSizeName"))) {
			sql.append(" and p.productName='").append(request.getParameter("ProductName")).append("' ");
		}
		//IP地址
		if(!StringUtil.isEmpty(request.getParameter("ipAddr"))) {
			sql.append(" and q.TELNUMBER='").append(request.getParameter("ipAddr")).append("' ");
		}
		if(!StringUtil.isEmpty(timeBeginDate)) {
			sql.append(" and q.FINDDT>=to_date('").append(timeBeginDate).append("','yyyy-MM-dd hh24:mi:ss')");
		}
		if(!StringUtil.isEmpty(request.getParameter("EndDate"))) {
			sql.append(" and q.FINDDT<=to_date('").append(request.getParameter("EndDate")).append("','yyyy-MM-dd hh24:mi:ss')");
		}
		
		if(!StringUtil.isEmpty(request.getParameter("queryMode"))) {
			sql.append(" and q.FINDTYPE=").append(request.getParameter("queryMode"));
		}
		if(!StringUtil.isEmpty(request.getParameter("ipAddr"))) {
			sql.append(" and q.TELNUMBER<='").append(request.getParameter("ipAddr")).append("'");
		}
		if(!StringUtil.isEmpty(request.getParameter("qcount"))) {
			sql.append(" and q.queryNum>=").append(request.getParameter("qcount"));
		}
		
		if(pageSize>0) {
			return PageQuery.jdbcSqlserverQuery(request, "FINDDT desc ", sql.toString(), pageSize);
		} else {
			return EntityManager.jdbcquery(sql.toString());
		}
		
	}

}
