package com.winsafe.drp.task.upr;

import java.util.List;

public interface IFile {

	
	String getName() throws Exception;
	
	Object[] getListFiles() throws Exception;
	
	void deleteFile() throws Exception;
	
	List getAllFiles() throws Exception;
	
	List getAllFiles(String path) throws Exception;
	
	List getFiles() throws Exception;
	
}
