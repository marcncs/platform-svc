package com.winsafe.drp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.jdbc.Work;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-9-4 下午12:02:18
 * www.winsafe.cn
 */
public class AppUploadProduceReport {

	public List<UploadProduceReport> getUploadProduceReportAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from UploadProduceReport as ot " +whereSql+" order by ot.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List<UploadProduceReport> getUploadProduceReportByUnitnocode(String unitnocode)throws Exception{
		String hql =" from UploadProduceReport as ot where unitnocode='"+unitnocode+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public UploadProduceReport getUploadProduceReportByID(Integer id)throws Exception{
		String hql = " from UploadProduceReport ot where ot.id='" + id+"'";
		return (UploadProduceReport) EntityManager.find(hql);
	}
	
	public UploadProduceReport getUploadProduceReportByUnitNo(String unitno)throws Exception{
		String hql = " from UploadProduceReport ot where ot.unitno='" + unitno+"'";
		return (UploadProduceReport) EntityManager.find(hql);
	}
	
	public boolean getReportByUnitNo(String unitno)throws Exception{
		String hql = "select count(*)  from UploadProduceReport ot where ot.unitno='" + unitno+"'";
		return EntityManager.getRecordCount(hql) > 0;
	}
	
	public boolean getReportByUnitNoInSql(String unitno)throws Exception{
		String sql = "select top 1 id from upload_produce_report ot where ot.unitno='" + unitno+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	
	
	public void save(UploadProduceReport upr)throws Exception{
//		EntityManager.save(upr);
		EntityManager.save3(upr);
	}
	
//	public void saveUploadProduceReport(UploadProduceReport upr) throws Exception{		
//		String sql = "insert into upload_produce_report(proid,proname,prodt,lotno,unitno,unitnocode,traynocode," +
//				"bc,pdate,prcdate,cartonNo,palletNo,recType)  select '"
//			+ upr.getProid()+ "','"+ upr.getProname()+ "',TO_DATE('"+DateUtil.formatDateTime(upr.getProdt())+"','yyyy-mm-dd hh24:mi:ss'),'"
//			+ upr.getLotno()+ "','"+upr.getUnitno()+"','"+upr.getUnitnocode()+"','"+upr.getTraynocode()+"','"
//			+ upr.getBc()+"',TO_DATE('"+DateUtil.formatDate(upr.getPdate())+"','yyyy-mm-dd hh24:mi:ss'),'"+upr.getPrcdate()+"','"+upr.getCartonNo()+"','"+upr.getPalletNo()+"','"
//			+ upr.getRecType()+"'"
//			+ " from dual where not exists(select id from upload_produce_report  a where a.unitno='"+ upr.getUnitno() + "')";
//	     EntityManager.updateOrdelete(sql);
//		}
	
	public void update(UploadProduceReport upr)throws Exception{
		EntityManager.update(upr);
	}
	public void delete(UploadProduceReport upr)throws Exception{
		EntityManager.delete(upr);
	}
	
	public int getUploadProduceReportCount(String begindate,String enddate)throws Exception{
		String hql=null;
		if(begindate==null && enddate==null){
			return 0;
		}else if(begindate==null && enddate!=null){
			hql = "select count(*)  from UploadProduceReport where   pdate<='"+enddate+"'";
		}else if(begindate!=null && enddate==null){
			hql = "select count(*)  from UploadProduceReport where   pdate>='"+begindate+"'";
		}else{
			hql = "select count(*)  from UploadProduceReport where   pdate>='"+begindate+"' and pdate<='"+enddate+ " 23:59:59'";
		}
		return EntityManager.getRecordCount(hql);
	}
	
	public void  update(String hql)throws Exception{
		EntityManager.updateOrdelete(hql);
	}
	
	public UploadProduceReport getUploadProduceReport(String proid,String prcdate)throws Exception{
		String hql = " from UploadProduceReport ot where ot.proid='" + proid+"' and prcdate='" +prcdate+ "'";
		return (UploadProduceReport) EntityManager.find(hql);
	}
	
	public List<UploadPrLog> getUploadPrLogWithIsDeal(String tempDate)throws Exception{
		String hql = "from UploadPrLog where isdeal=1 and convert(varchar(10),makedate,120)<='" + tempDate + "'";
		return EntityManager.getAllByHql(hql);
	}
	
	public void saveUploadProduceReportByRecord() throws Exception{		
		String sql = "insert into upload_produce_report(proid,proname,prodt,lotno,unitno," + 
				"unitnocode,traynocode,bc,pdate,prcdate,cartonNo,palletNo,recType)" +   
				" select r.ProID,p.productname,r.ProDT,r.LotNo,r.ProCode," +
				"r.CartonCode,r.Points_Number,r.[LineNo],r.ProDT,r.PackDate,r.CartonNo,r.PalletNo,r.RecType " +
				"from Record as r,product as p where r.proid=p.id";
	     EntityManager.updateOrdelete(sql);
	}
	
	public void delAllRecord()throws Exception{
		String delSql = "delete from record";
		EntityManager.updateOrdelete(delSql);
	}
	
	public void insertAllRecord(String url)throws Exception{
		String delSql = "insert into Record SELECT * " +      
				"FROM   opendatasource('Microsoft.Jet.OLEDB.4.0',  " +  
				"'Data Source=\"" + url + "\";Jet OLEDB:Database Password=WinSafe%%304#&305#')...[Record] as r " +
				"where r.rectype<>'Y' and  not exists(select id from upload_produce_report as upr where r.cartoncode=upr.unitnocode )";
		EntityManager.updateOrdelete(delSql);
	}
	public void insertRecord(Connection conn, String sql) throws Exception{
		Statement st =conn.createStatement();
		int result = st.executeUpdate(sql);
		st.close();
	}

	public void addReports(List<UploadProduceReport> reports) {
		Work work = getWork(reports);
		EntityManager.executeWork(work);
		
	}
	
	private Work getWork(final List<UploadProduceReport> reports) {
		Work work = new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Statement statement = conn.createStatement();
				for(UploadProduceReport cc :reports) {
					StringBuffer sb = new StringBuffer();
					sb.append("insert into UPLOAD_PRODUCE_REPORT (PROID, PRONAME, PRODT, LINENO, CARTONCODE, BOXCODE,PACKCODE, RECTYPE, OPTTIME, NCLOTNO, MAKEDATE) values ('");
					sb.append(cc.getProId());
					sb.append("' , '");
					sb.append(cc.getProName());
					sb.append("' , TO_DATE('");
					sb.append(DateUtil.formatDate(cc.getProDt(), "yyyy-MM-dd HH:mm:ss"));
					sb.append("', 'yyyy-mm-dd hh24:mi:ss') , '");
					sb.append(cc.getLineNo());
					sb.append("' , '");
					sb.append(cc.getCartonCode());
					sb.append("' , '");
					sb.append(cc.getBoxCode());
					sb.append("' , '");
					sb.append(cc.getPackCode());
					sb.append("' , '");
					sb.append(cc.getRecType());
					sb.append("' , TO_DATE('");
					sb.append(DateUtil.formatDate(cc.getOptTime(), "yyyy-MM-dd HH:mm:ss"));
					sb.append("', 'yyyy-mm-dd hh24:mi:ss') , '");
					sb.append(cc.getNcLotNo());
					sb.append("' , SYSDATE )");
					statement.addBatch(sb.toString());
				}
				
				statement.executeBatch();
				statement.close();
			}
		};  
		
		return work;
	}
}
