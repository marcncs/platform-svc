package com.winsafe.drp.exception;


@SuppressWarnings("serial")
public class ObjectExistedException extends Exception {

	public ObjectExistedException() {
		super("对象已存在");
	}
	
	public ObjectExistedException(String message) {
		super(message);
	}

}
