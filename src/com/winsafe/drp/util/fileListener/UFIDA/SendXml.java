package com.winsafe.drp.util.fileListener.UFIDA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class SendXml implements Runnable {
	private static final Logger log = Logger.getLogger(SendXml.class);

	private ResXmlBean resXmlBean;
	private String fileName;
	
	
	//延迟间隔时间
	private long intervalTime;
	private Thread t = new Thread(this);
	
	
	public void start(boolean isDamon) {
		t.setDaemon(isDamon);
		t.start();
	}
	
	//构造函数
	public SendXml(ResXmlBean resXmlBean,String fileName,long intervalTime){
		this.resXmlBean=resXmlBean;
		this.fileName=fileName;
		//延迟时间配置
		this.intervalTime = intervalTime; 
		
	}
	
	@Override
	public void run() {
		//若发送成功则结束循环
		boolean flag=true;
		while (flag) {
			flag=this.creatResponseXml(this.resXmlBean, this.fileName);
			try {
				t.sleep(intervalTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 生成回答报文XML
	 * 
	 * @param resXmlBean返回报文的对象
	 * @return flag若发送成功则返回一个false用来结束发送
	 */
	public boolean creatResponseXml(ResXmlBean resXmlBean,String fileName) {
		 boolean flag=false;
		if (fileName == null) {
			fileName = resXmlBean.getCgeneralhid();
		}
		fileName=(fileName.substring(0, fileName.length() - 4)+"_");
		// setFileName(fileName.split(".")[0]);
		// 为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			log.error("------连接------" + pce.toString());
			return true;
		}
		Document doc = null;
		doc = db.newDocument();

		// 下面是建立XML文档内容的过程，先建立根元素
		Element root = doc.createElement("response");
		// 根元素添加上文档
		doc.appendChild(root);

		// 完成cgeneralhid节点
		Element elementCgeneralhid = doc.createElement("cgeneralhid");
		root.appendChild(elementCgeneralhid);
		Text textId = doc.createTextNode(resXmlBean.getCgeneralhid());
		elementCgeneralhid.appendChild(textId); // 将文本节点放在cgeneralhid节点上

		// 完成state节点
		Element elementState = doc.createElement("state");
		root.appendChild(elementState);
		Text textState = doc.createTextNode(resXmlBean.getState());
		elementState.appendChild(textState);

		// 完成detail节点
		Element elementDetail = doc.createElement("detail");
		root.appendChild(elementDetail);
		Text textDetail = doc.createTextNode(resXmlBean.getDetail());
		elementDetail.appendChild(textDetail);

		try {
			String path = "D:\\sinoagriFtp\\nc\\handled\\" + fileName
					+ "Response.xml";
			FileOutputStream outStream = new FileOutputStream(path);
			OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
//			((XmlDocument) doc).write(outWriter, "GB2312");
			outWriter.close();
			outStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("------创建xml------" + e.toString());
			return true;
		}

		flag=sendTheXml(fileName);
		return flag;
	}

	public boolean sendTheXml(String localFileName) {
		boolean flag=false;
		/*
		String localfilename = "D:\\sinoagriFtp\\nc\\handled\\" + localFileName
				+ "Response.xml";
		String remotefilename = localFileName + "Response.xml";
		String path = "/winsafe/needHandle";
		FtpClient ftpClient = new FtpClient();
		String ftpUrl="";
		String ftpUserName="";
		String ftpPassword="";
		String ftpPort="";
		try {
			ftpUrl=ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("ftp_url");
			ftpUserName=ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("ftp_userame");
			ftpPassword=ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("ftp_password");
			ftpPort=ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("ftp_port");
			ftpClient.openServer(ftpUrl, Integer.valueOf(ftpPort));
			ftpClient.login(ftpUserName, ftpPassword);
			if (path.length() != 0) {
				ftpClient.cd(path);
			}
		} catch (Exception e) {
			log.error("------连接服务器------" + e.toString());
			return true;
		}

		try {
			TelnetOutputStream os = ftpClient.put(remotefilename);
			java.io.File file_in = new java.io.File(localfilename);
			FileInputStream is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			log.error("------上传文件------" + ex.toString());
			return true;
		}

		try {
			ftpClient.closeServer();
		} catch (Exception e) {
			log.error("------关闭连接服务器------" + e.toString());
			return true;
		}
		*/
		return flag;
	}

}
