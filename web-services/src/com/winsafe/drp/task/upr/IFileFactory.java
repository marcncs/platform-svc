package com.winsafe.drp.task.upr;

public class IFileFactory {
	
	
	public static IFile getInstence(int identity, String filePath) throws Exception{
		IFile file = null;
		switch (identity) {
		case 1:
			file = new TFile(filePath);
			break;
		case 2:
			file = new TSmbFile(filePath);
			break;
		}
		return file;
	}

}
