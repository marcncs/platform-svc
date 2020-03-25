package com.winsafe.drp.util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 文件工具类(共通类)
 * 
 * @author RichieYu(俞子坚)
 * @version 0.7
 * @date Apr 9, 2010
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);
    /**
     * 主要功能:获得控制台用户输入的信息
     * 
     * @return 控制台的字符串
     * @throws IOException
     * 
     */
    public static String getInputMessage() throws IOException {
		System.out.println("请输入您的命令∶");
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++)
		    ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
    }

    /**
     * 主要功能:以文件流的方式复制文件
     * 
     * @param src:文件源目录
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
		    file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
		    for (int i = 0; i < c; i++)
			out.write(buffer[i]);
		}
		in.close();
		out.close();
    }

    /**
     * 主要功能:写文件
     * 利用PrintStream写文件 
     * 文件输出示例
     */
    public static void PrintStreamDemo(String target_dir){
        try{
            FileOutputStream out=new FileOutputStream(target_dir);
            PrintStream p=new PrintStream(out);
            for(int i=0;i<10;i++)
                p.println("This is "+i+" line");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 主要功能:写文件
     * 利用StringBuffer写文件 
     * 文件输出示例
     */
    public static void StringBufferDemo() throws IOException{
        File file=new File("/root/sms.log");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        for(int i=0;i<10000;i++){
            StringBuffer sb=new StringBuffer();
            sb.append("这是第"+i+"行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
            out.write(sb.toString().getBytes("utf-8"));
        }        
        out.close();
    }
    
    /**
     * 写文件(相对路径)
     * @param path 相对路径
     * @param buf byte[]数组
     * @throws IOException
     */
    public static void writeFile(String path,byte[] buf) throws IOException{
        File file=new File(path);
        //先删除再重新生成文件
        if(file.exists()){
        	file.delete();
        } 
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file,true);        
        out.write(buf);
        out.close();
    }
    
    
    
    /** 
     * 主要功能:文件重命名
     * @param path 文件目录
     * @param oldname  原来的文件名
     * @param newname 新文件名
     */
    public static void renameFile(String path,String oldname,String newname){
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newname+"已经存在！");
            else{
                oldfile.renameTo(newfile);
            } 
        }         
    }
    
    /** 
     * 主要功能:转移文件目录
     * @param filename 文件名
     * @param oldpath 旧目录
     * @param newpath 新目录
     * @param cover 若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件，否则不操作
     */
    public static void changeDirectory(String filename,String oldpath,String newpath,boolean cover){
        if(!oldpath.equals(newpath)){
            File oldfile=new File(oldpath+"/"+filename);
            File newfile=new File(newpath+"/"+filename);
            if(newfile.exists()){//若在待转移目录下，已经存在待转移文件
                if(cover)//覆盖
                    oldfile.renameTo(newfile);
                else
                    System.out.println("在新目录下已经存在："+filename);
            }
            else{
                oldfile.renameTo(newfile);
            }
        }       
    }
    
    
    /** 
     * 主要功能:从目录中读取xml文件
     * @param path 文件目录
     * @return
     * @throws DocumentException
     * @throws IOException
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */
    public static Document readXml(String path)throws DocumentException, IOException, ParserConfigurationException, SAXException{
        File file=new File(path);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		/*
		 * builder.parse()方法将给定文件的内容解析为一个 XML 文档， 并且返回一个新的 DOM Document对象。
		 */
		Document document = builder.parse(file);
        return document;
    }
    
    /**
     * 主要功能:创建文件夹
     * @param path  目录
     */
    public static void createDir(String path){
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdir();
    }
     
    /** 
     * 主要功能:创建新文件
     * @param path 目录
     * @param filename 文件名
     * @throws IOException
     */
    public static void createFile(String path,String filename) throws IOException{
        File file=new File(path+"/"+filename);
        if(!file.exists())
            file.createNewFile();
    }
    /** 
     * 主要功能:删除文件
     * @param path 目录
     * @param filename 文件名
     */
    public static void delFile(String path,String filename){
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile())
            file.delete();
    }
    /**
     * 主要功能:递归删除文件夹
     * @param path
     */
    public static void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+"/"+tmp[i].getName());
                }
                else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }
    
    /**
     * 主要功能:读文件
     * @param path
     * @return
     * @throws IOException
     */
    public static String BufferedReaderDemo(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp+" ");
            temp=br.readLine();
        }
        return sb.toString();
    }
    
    /** 
     * 主要功能:读文件
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFileInputStream(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis=new FileInputStream(file);
        byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        while((fis.read(buf))!=-1){
            sb.append(new String(buf));    
            buf=new byte[1024];//重新生成，避免和上次读取的数据重复
        }
        return sb.toString();
    }
    
    /**
     * 读文件
     * @param path(相对路径)
     * @return 文件byte[]
     * @throws IOException
     */
    public static byte[] readKeyFile(String path) throws IOException{
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis=new FileInputStream(file);
        byte[] buf = new byte[1024];
        fis.read(buf);
        fis.close();
        byte[] realByte = FileUtil.removeNullBytes(buf);
        return realByte;
    }
    
    /**
     * 去除空的数组集合
     * @param fileByte 原数组
     * @return 收缩后的数组
     */
    public static byte[] removeNullBytes(byte[] fileByte){
    	int realLength = 0;
    	for (int i = 0; i < fileByte.length; i++) {
			if(fileByte[i] != 0){
				realLength = i + 1;
			}else{
				break;
			}
		}
    	byte[] realByte = new byte[realLength];
    	for (int i = 0; i < realByte.length; i++) {
			realByte[i] = fileByte[i];
		}
    	return realByte;
    }
    
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
//    String da1="2010-08-09 00:00:00";
//    String da2="2010-02-09 00:00:00";
//    Date date1=Dateutil.StringToDatetime(da1);
//    Date date2=Dateutil.StringToDatetime(da2);
//    double d1=date1.getTime();
//    double d2=date2.getTime();
//    double a=2.592E9;
//    int temp=(int) ((d1-d2)/a);
//    System.out.println(temp);
    createFile();
    }
    
    public static void createFile() throws IOException{
    	RC4 rc4 = new RC4();
		String valiDate = "winsafe#2013/7/12#bright";
		byte[] fileByte = rc4.encrypt(valiDate);
		FileUtil.writeFile("d:/key.sn", fileByte);
    }
    
    public static void readFile() throws IOException{
    	byte[] fileByte = readKeyFile("..//key.sn");
    	byte[] realByte = FileUtil.removeNullBytes(fileByte);
    	RC4 rc4 = new RC4();
    	byte[] date = rc4.encrypt(realByte);
    	System.out.println(new String(date));
    }
    
    /**
     * 主要功能:以文件流的方式复制文件
     * 
     * @param src:文件源目录
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static void copyFile(InputStream in, String dest, String fileName) throws IOException {
		File filePath = new File(dest);
		if (!filePath.exists())
			filePath.mkdirs();
		File file = new File(filePath + "/" + fileName);
		if(!file.exists()) 
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
		    for (int i = 0; i < c; i++)
			out.write(buffer[i]);
		}
		in.close();
		out.close();
    }
    
    /**
     * 将多个文件压缩打包
     * @param srcfile
     * @param zipfile
     * @throws Exception
     */
    public static void zipFiles(File[] srcfile, File zipfile) throws Exception {  
        byte[] buf = new byte[1024];  
        ZipOutputStream out = null;
        FileInputStream in = null; 
        try {  
            //ZipOutputStream类：完成文件或文件夹的压缩  
            out = new ZipOutputStream(new FileOutputStream(zipfile));  
            for (int i = 0; i < srcfile.length; i++) {  
                in = new FileInputStream(srcfile[i]);  
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                out.closeEntry();  
                in.close();  
            }
        } catch (Exception e) {  
        	logger.error("failed to create zip File",e);
        }  finally {
        	if(in != null) {
        		in.close();
        	}
        	if(out != null) {
        		out.close();
        	}
        }
    }  
    
    public static List<File> unZipFiles(File zipfile, String descDir) throws Exception {
		List<File> files = new ArrayList<File>();
		InputStream in = null;
		OutputStream out = null;
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				try {
					ZipEntry entry = (ZipEntry) entries.nextElement();
					String zipEntryName = entry.getName();
					File file = new File(descDir + zipEntryName);
					in = zf.getInputStream(entry);
					out = new FileOutputStream(file);
					byte[] buf1 = new byte[1024];
					int len;
					while ((len = in.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					out.flush();
					files.add(file);
				} finally {
					if(in!=null) {
						in.close();
					}
					if(out!=null) {
						out.close();
					}
				}
			}
		} catch (Exception e) {
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
		}
		return files;
	}

}
