package com.winsafe.drp.exception;

import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;

public class NotExistException extends Exception {
	public NotExistException() {
		super();
	}

	public NotExistException(String msg) {
		super(msg);
	}

}
