package com.winsafe.drp.task.upr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class TSmbFile extends SmbFile implements IFile{
	
	public List list;
	
	private String path;

	public TSmbFile(String url) throws MalformedURLException {
		super(url);
		this.path=url;
		list = new ArrayList();
	}

	@Override
	public Object[] getListFiles() {
		try {
			return super.listFiles();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void copyFile(String src, String dest) throws IOException{
		InputStream in = new SmbFileInputStream(src);
		SmbFile file = new SmbFile(dest);
		if (!file.exists())
		    file.createNewFile();
		OutputStream out = new SmbFileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
		    for (int i = 0; i < c; i++)
			out.write(buffer[i]);
		}
		in.close();
		out.close();
		
	}

	@Override
	public void deleteFile() throws Exception {
		super.delete();
	}
	
	public static void mkdir(String dest) throws Exception {
		SmbFile file = new SmbFile(dest);
		if(!file.exists()){
			file.mkdir();
		}
		
	}

	@Override
	public List getAllFiles(String path) throws Exception {
		SmbFile file = new SmbFile(path);
		SmbFile[] files = file.listFiles();
		for (SmbFile file2 : files) {
			if(!file2.isDirectory()){
				list.add(file2);
			}else{
				getAllFiles(file2.getPath());
			}
		}
		return list;
	}

	@Override
	public List getAllFiles() throws Exception {
		return getAllFiles(path);
	}
	
	@Override
	public List getFiles() throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		for (File file2 : files) {
			if(!file2.isDirectory()){
				list.add(file2);
			}
		}
		return list;
	}
}
