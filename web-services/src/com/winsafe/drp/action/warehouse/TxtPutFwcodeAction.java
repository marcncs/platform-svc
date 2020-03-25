package com.winsafe.drp.action.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.exception.CustomException;
import com.winsafe.common.util.DBManager;
import com.winsafe.common.util.FileUtil;
import com.winsafe.common.util.Function;
import com.winsafe.common.util.ZipFile;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.filters.PurviewFilter;

public class TxtPutFwcodeAction extends BaseAction {
	Logger logger = Logger.getLogger(TxtPutFwcodeAction.class);

	public synchronized ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream responseOut = null;
		InputStream in = null;
		try {
			UsersBean users = UserManager.getUser(request);
  
			logger.error("下载防伪数据 ,Loginname:"+users.getLoginname()+",Userid:"+users.getUserid());
			
			super.initdata(request);

			String tempDir = request.getRealPath("/") + "/FwcodeTemp/";
			FileUtil.createDir(tempDir);
			/*
			 * 删除昨天以前的临时文件
			 */
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			FileUtil.deleteBeforeDate(tempDir, c.getTime());
			
			
			File temp = new File(tempDir + Function.getRandomString()+".txt");
			if (!temp.exists()) {
				temp.createNewFile();
			}
			//生成防伪码数据文件
			writeFile(request,temp);
			
			File zFile = new File(tempDir + Function.getRandomString()+".zip");
			if (zFile.exists()) {
				if(!zFile.delete()){
					throw new CustomException("操作失败！");
				}
			}
			
			//压缩加密防伪码数据文件
			ZipFile zip = new ZipFile(temp.getPath(),zFile.getPath());
			zip.mainWorkFlow();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=fwcode_" + sdf.format(new Date())
							+ ".zip");
			response.setContentType("application/octet-stream");

			responseOut = response.getOutputStream();
			
			in = new FileInputStream(zFile);
			FileUtil.copy(in, responseOut);

			responseOut.flush();
		} catch (CustomException e) {
			return ReturnPage.getReturn(request, "1", e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (responseOut != null) {
				try {
					responseOut.close();
				} catch (Exception e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}

		}
		return null;
	}

	private synchronized void writeFile(HttpServletRequest request,File temp) throws Exception {

		FileOutputStream fos = null;
		FileLock flock = null;

		try {

			fos = new FileOutputStream(temp);
//			// 设置成独占
//			FileChannel fc = fos.getChannel();
//			flock = fc.tryLock();
//			if (flock == null) {
//				throw new CustomException("导出失败，另一个用户正在使用");
//			}
			
			String startdate = request.getParameter("startdate");
			String enddate = request.getParameter("enddate");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(enddate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			date = c.getTime();
			enddate=sdf.format(date);

			StringBuilder sql = new StringBuilder();
			sql.append("select pii.idcode,p.NCcode,pii.makedate ");
			sql.append("  from Product_Income_Idcode pii");
			sql.append(" inner join product p");
			sql.append("    on pii.productid = p.id");
			sql.append("   and p.psid like '"+Constants.PS_CODE_1+"%' ");
			sql.append(" where pii.makedate>='" + startdate + "' ");
			sql.append("   and pii.makedate<'" + enddate + "'");

			int maxLen = 100;
			int i = 0;
			Object[] objs;
			String idcode;
			String productNccode = null;
			Timestamp makedate = null;
			StringBuilder sb = new StringBuilder();
			Map<String, Object[]> map = new HashMap<String, Object[]>();
			ResultSet rs = EntityManager.query2(sql.toString());
			while (rs.next()) {
				i++;
				idcode = rs.getString(1);
				productNccode = rs.getString(2);
				makedate = rs.getTimestamp(3);

				objs = new Object[3];
				objs[0] = idcode;
				objs[1] = productNccode;
				objs[2] = makedate;
				map.put(idcode, objs);

				sb.append("'");
				sb.append(idcode);
				sb.append("',");
				if (i >= maxLen) {
					sb.delete(sb.length() - 1, sb.length());

					writeTxt(fos, sb.toString(), map);

					map.clear();
					sb.delete(0, sb.length());
					i = 0;
				}
			}
			if (sb.length() > 0) {
				sb.delete(sb.length() - 1, sb.length());
				writeTxt(fos, sb.toString(), map);
			}
		}catch(OverlappingFileLockException e){
			throw new CustomException("导出失败，另一个用户正在使用");
		}catch (Exception e) {
			throw e;
		} finally {
			if (flock != null) {
				try {
					flock.release();
				} catch (Exception e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
			// 释放连接
			freeConnection();
		}
	}

	private SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm");

	private void writeTxt(OutputStream out, String param,
			Map<String, Object[]> map) throws Exception {


			StringBuilder sb = new StringBuilder();
	
			// 连接数据库
			checkConnection();
	
			Date date;
	
			String fwcode;
			String box_fwcode;
			Object[] objs;
	
			String sql = _sql.replace("{0}", param);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				fwcode = rs.getString(1);
				box_fwcode = rs.getString(2);
	
				objs = map.get(box_fwcode);
				try{
				sb.delete(0, sb.length());
				sb.append(fwcode);
				sb.append(",");
				sb.append((String) objs[1]);
				sb.append(",");
				date = new Date(((Timestamp) objs[2]).getTime());
				sb.append(yyyyMMddHHmm.format(date));
				sb.append("\r\n");
				out.write(sb.toString().getBytes("utf-8"));
				}catch(Exception e){
					String msg="param:"+param+",box_fwcode:"+box_fwcode+",fwcode:"+fwcode+",objs:"+objs;

					logger.error(msg, e);
					throw e;
				}
			}
	
	}

	/**
	 * Sql语句
	 */
	private final static String _sql = "select Points_Number,CartonBarcode from BdPointsNumber where CartonBarcode in({0})";

	/**
	 * 数据库连接
	 */
	private Connection conn = null;

	/**
	 * 执行sql对象
	 */
	private Statement stmt = null;

	/**
	 * 连接数据库
	 * 
	 * @throws SQLException
	 */
	private void checkConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = DBManager.getConnection();
			stmt = null;
		}
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		stmt.setQueryTimeout(60*60);
	}

	private void closeStatement(Statement s) {
		try {
			if (s != null) {
				s.close();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 释放连接
	 */
	private void freeConnection() {
		DBManager.freeConnection(conn);
		closeStatement(stmt);

		stmt = null;

		conn = null;
	}
}
