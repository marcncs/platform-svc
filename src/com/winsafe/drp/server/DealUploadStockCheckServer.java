package com.winsafe.drp.server;

import java.util.ArrayList;

public class DealUploadStockCheckServer {
	
	
	public void dealBarcodeUpload(String filePath, String fileAddress,
			int iuid, String username) throws Exception {
		DealBarcodeUpload dealbarcodeupload = new DealBarcodeUpload(filePath,
				fileAddress, iuid, username);
		dealbarcodeupload.deal();
	}

}
