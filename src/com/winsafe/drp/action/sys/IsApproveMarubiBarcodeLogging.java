package com.winsafe.drp.action.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFwmCreateCode;
import com.winsafe.drp.dao.FwmCreate;
import com.winsafe.drp.util.FileUtil;
import com.winsafe.getNumber.GetFWNumber;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RarFile;

public class IsApproveMarubiBarcodeLogging extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String id = request.getParameter("ID");
			String realPath = request.getRealPath("/");
			AppFwmCreateCode afcc = new AppFwmCreateCode();
			FwmCreate fc = afcc.getFwmCreateByID(Integer.valueOf(id));
			if(null != fc){
				//条码生成中
				fc.setIsDeal(1);
				//已审核
				fc.setIsApprove(1);
				//更新
				afcc.UpdateFwmCreate(fc);
				//声明线程内,并通过有参构造函数传值
				ThreadCreateFwm threadcreatefwm = new ThreadCreateFwm(
						fc.getFwmClientName(), 
						fc.getFwmClientCode(), 
						fc.getFwmNum(), 
						fc.getFwmDaoFormat(), 
						realPath, 
						Integer.valueOf(id), 
						fc.getFwmCreateDate(), 
						fc.getIcode(), 
						fc.getFwmProductName());
				//启动线程
				threadcreatefwm.start();
				threadcreatefwm.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "审批失败，请联系管理员!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
		request.setAttribute("result", "审批成功，正在生成防伪物流码!");
		return new ActionForward("/sys/lockrecordclose2.jsp");
	}
	
	/**
	 * 生成防伪码的线程类
	 * @author wei.li
	 */
	class ThreadCreateFwm extends Thread {
		int id = 0;
		int fwmnum = 0;
		String daoformat = null;
		private Connection con = null;
		private Statement statement = null;
		private PreparedStatement ps = null;
		String realPath = null;
		Date riqi = null;
		String productcode = null;
		String productname = null;
		String clientname = null;
		Properties properties = getParpertiesInto();
		String driver = properties.getProperty("connection.driver");
		String url = properties.getProperty("connection.url");
		String user = properties.getProperty("connection.username");
		String password = properties.getProperty("connection.password");
		private Object lock = new Object();
		
		public ThreadCreateFwm() {
			super();
		}
		//生成防伪码线程内的构造函数,接收数据
		public ThreadCreateFwm(String clientname, String clientcode, int fwmnum, 
				String daoformat, String realPath, int id, Date riqi, 
				String productcode, String productname) throws Exception {
			this.fwmnum = fwmnum;
			this.daoformat = daoformat;
			this.realPath = realPath;
			this.id = id; 
			this.riqi = riqi;
			this.productcode = productcode; 
			this.productname = new String(productname.getBytes("UTF-8"),"UTF-8");
			this.clientname = new String(clientname.getBytes("UTF-8"),"UTF-8");
		}
		/**
		 * 线程主方法,判断生成文件格式,调用对应方法
		 */
		public void run() {
			try {
				synchronized(lock){
					this.createAccessOrTxtFwm(daoformat);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		/**
		 * 生成AccessOrTxt防伪码文件调用的方法
		 */
		private void createAccessOrTxtFwm(String daoformat) {
			//生成防伪码后使用的TXT文件名
			int txtFileName = 1;
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String riqi = format.format(this.riqi);
				Date date = format.parse(riqi);
				riqi = riqi.replace("-", "").replace(" ", "").replace(":", "");
				//声明不同企业、产品名称和时间的文件前缀
				String fileNamePrefix = clientname + "_" + productname + "_" + riqi.substring(0, 8).replace("-", "") + "_" + riqi.substring(8, 12);
				//得到文件后缀
				String fileNamePostfix = this.getFileNamePostfix(daoformat);
				//生成防伪码文件夹的名称
				String path = "../data/" + fileNamePrefix + "/";
				//计算开始时间
				long startdate = System.currentTimeMillis();
				String filename = fileNamePrefix + "_" + txtFileName + fileNamePostfix;
				//生成全部防伪码
				this.getCreatePrepareFwm(fwmnum, date, path, filename, daoformat);
				//计算结束时间
				long enddate = System.currentTimeMillis();
				System.out.println("生成时间为："+((enddate-startdate)/1000)+"秒");
				//更改生成状态的信息
				AppFwmCreateCode afcc = new AppFwmCreateCode();
				FwmCreate fwmcreate = afcc.getFwmCreateByID(id);
				fwmcreate.setIsDeal(2);
				afcc.UpdateFwmCreate(fwmcreate);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		/**
		 * 生成防伪码的准备方法
		 */
		private void getCreatePrepareFwm(int wfwmAllmAll, Date date, String path, String fileName, String daoformat) throws Exception{
			if(0 == wfwmAllmAll){
				return;
			}
			//声明Access中ID的开始变量
			int accessId = 1;
			int accessBoxId = 1;
			//声明文件和输出流
			File file = null;
			FileOutputStream fos = null;
			if ("txt".equals(daoformat)) {
				file = saveFile(path, realPath, fileName);
				fos = new FileOutputStream(file);
				//执行生成防伪码的方法
				this.getCreateFwm(wfwmAllmAll, accessId, accessBoxId, date, path, fileName, daoformat, fos, statement);
			}else{
				//暂无逻辑
			}
		}
		/**
		 * 生成防伪码的方法
		 * @throws Exception 
		 */
		private void getCreateFwm(int wfwmAllmAll, int accessid, int accessBoxId, Date date, String path, String fileName, 
				String daoformat, FileOutputStream fos, Statement statement) throws Exception{
			//连接数据库
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			//插入数据库表的SQL语句
			String sql = "insert into HProNumber (FW_Number,AddDT,FWFindCount,FWFindDT,PointsDT,ActivatedDT, " +
					"IsPoints,IsActivated,ProNo,Other) values (?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			try {
				//得到年份的最后两位
				String lastDate = DateUtil.getCurrentDateString();
				lastDate = lastDate.substring(0, 4);
				lastDate = lastDate.substring(lastDate.length() - 1, lastDate.length());
				int i = 1;
				while (i <= Integer.valueOf(wfwmAllmAll)) {
					//16位防伪码
					String fangweima = lastDate + productcode + GetFWNumber.getFWNumber(11);
					ps.setString(1, fangweima);
					ps.setTimestamp(2, new Timestamp(new Date().getTime()));
					ps.setInt(3, 0);
					ps.setDate(4, null);
					ps.setDate(5, null);
					ps.setDate(6, null);
					ps.setInt(7, 0);
					ps.setInt(8, 0);
					ps.setString(9, null);
					ps.setString(10, null);
					try {
						//判断是否将数据添加数据库成功
						if (ps.executeUpdate() != 0) {
							if ("txt".equals(daoformat)) {
								//得到最终要写入文档的内容
								String last = fangweima + ",http://winsafe.cn/?wm=" + fangweima + "\r\n";
								fos.write(last.getBytes());
							}else{
								//暂无逻辑
							}
							i++;
						}
					} catch (Exception e) {
						continue;
					}
				}
			} catch (RuntimeException e) {
				fos.close();
				return;
			} finally {
				if(statement!=null){
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try{
					if(con!=null && !con.isClosed()){
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps.close();
			}
			String zipFilePath = fileName.substring(0,fileName.length()-4) + ".zip";
			saveFile(path, realPath, zipFilePath);
			this.getCreateZip(realPath + path + fileName,realPath + path + zipFilePath);
			if ("txt".equals(daoformat)){
				fos.close();
			}
		}
		
		/**
		 * 生成压缩文件的方法
		 */
		private void getCreateZip(String txtFileFullPath,String zipFileFullPath) throws Exception{
			//生成ZIP压缩包文件
			new RarFile(txtFileFullPath,zipFileFullPath).mainWorkFlow();
		}
		/**
		 * 根据不同的生成格式返回不同的文件后缀
		 */
		private String getFileNamePostfix(String daoformat){
			String fileNamePostfix = null;
			if ("access".equals(daoformat)) {
				fileNamePostfix = ".mdb";
			} else if ("txt".equals(daoformat)) {
				fileNamePostfix = ".txt";
			}else{
				//暂无逻辑
			}
			return fileNamePostfix;
		}
		/**
		 * 读取连接数据库配置文件信息
		 */
		private Properties getParpertiesInto(){
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("database.properties");
			Properties p = new Properties();
			try {
				p.load(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return p;
		}
		/**
		 * 生成服务器上的文件
		 */
		private File saveFile(String fileName, String realPath, String filename)
				throws IOException {
			System.out.println("============start===========");
			System.out.println("fileName=" + fileName);
			System.out.println("realPath=" + realPath);
			System.out.println("filename=" + filename);
			System.out.println("============end===========");
			File dir = new File(realPath + fileName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			dir = new File(realPath + fileName + "/" + filename);
			return dir;
		}
	}
}
