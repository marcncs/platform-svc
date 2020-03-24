package com.winsafe.hbm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


/*压缩文件夹*/
public class RarFile {
	private String currentZipFilePath;
	private String sourceFilePath;
	private ZipOutputStream zos;
	private FileInputStream fis;

	public RarFile(String sourceFilePath, String path) {
		try {
			this.currentZipFilePath = path;
			this.sourceFilePath = sourceFilePath;
			zos = new ZipOutputStream(new FileOutputStream(currentZipFilePath));
			zos.setEncoding("GBK");
			// 设定文件压缩级别
			zos.setLevel(9);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在当前条目中写入具体内容
	public void writeToEntryZip(String filePath) {
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		byte[] buff = new byte[1024];
		int len = 0;
		try {
			while ((len = fis.read(buff)) != -1) {
				zos.write(buff, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	// 添加文件条目
	public void addFileEntryZip(String fileName) {
		try {
			zos.putNextEntry(new ZipEntry(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDirectoryEntryZip(String directoryName) {
		try {
			zos.putNextEntry(new ZipEntry(directoryName + "/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 遍历文件夹
	public void listMyDirectory(String filePath) {
		File f = new File(filePath);
		// 设置条目名称(此步骤非常关键)
		String entryName = f.getName();
		// 获取文件物理路径
		String absolutePath = f.getAbsolutePath();
		if (f.isDirectory()) {
			addDirectoryEntryZip(entryName);
		} else {
			try {
				addFileEntryZip(new String(entryName.getBytes(),"gb2312"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writeToEntryZip(absolutePath);
		}
	}

	// 主要流程
	public void mainWorkFlow() {
		listMyDirectory(this.sourceFilePath);
		if (zos != null)
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/*
	 * public static void main(String[] args) { new
	 * RarFile("E:/123.xlsx").mainWorkFlow(); }
	 */
}
