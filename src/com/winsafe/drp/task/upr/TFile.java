package com.winsafe.drp.task.upr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TFile  extends File implements IFile{


	public List fileList ;
	
	private String path;
	
	public TFile(String pathname) {
		super(pathname);
		this.path = pathname;
		fileList = new ArrayList();
	}

	@Override
	public Object[] getListFiles() {
		return super.listFiles();
	}

	@Override
	public void deleteFile() throws Exception {
		super.delete();
	}

	public static void copyFile(String src, String dest) throws Exception {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
		    file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024*8];
		while ((c = in.read(buffer)) != -1) {
			out.write(buffer, 0, c);
		}
		in.close();
		out.close();
		
	}
	
	
	public static void mkdir(String dest) throws Exception {
		File file = new File(dest);
		if(!file.exists()){
			file.mkdir();
		}
		
	}
	
	public static void mkdirs(String dest) throws Exception {
		File file = new File(dest);
		if(!file.exists()){
			file.mkdirs();
		}
		
	}
	@Override
	public List getAllFiles(String path) throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		for (File file2 : files) {
			if(!file2.isDirectory()){
				fileList.add(file2);
			}else{
				getAllFiles(file2.getPath());
			}
		}
		return fileList;
	}

	@Override
	public List getAllFiles() throws Exception {
		return getAllFiles(this.path);
	}
	
	@Override
	public List getFiles() throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		if (null==files) {  //无文件
			return null;
		}
		for (File file2 : files) {
			if(!file2.isDirectory()){
				fileList.add(file2);
			}
		}
		return fileList;
	}

}
