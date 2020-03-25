package com.winsafe.drp.server;

import java.io.File;
import java.util.ArrayList;

import com.winsafe.sap.dao.DealSapCodeUpload;
import com.winsafe.sap.pojo.UploadSAPLog;
/**
 * 处理上传文件
 * Project:is->Class:UploadIdcodeService.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time 2013-11-4 下午03:43:12 
 * @author <a href='peng.li@winsafe.cn'>lipeng</a>
 * @version 0.8
 */
/**
 * @author lyn.xie
 *
 */
public class DealUploadIdcodeService 
{
	/**
	 * 无单出库
	 * 
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @param username
	 * @return
	 * @throws Exception
	 * @author wenping
	 * @CreateTime Jan 16, 2013 2:03:28 PM
	 */
	public ArrayList<String> dealNoBillStockAlterMove(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealNoBillStockAlterMove dealStockAlterMove = new DealNoBillStockAlterMove(filePath, fileAddress, iuid, username);
		return dealStockAlterMove.deal();
	}
	
	public ArrayList<String> dealBKDNoBillStockAlterMove(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealBKDNoBillStockAlterMove dealStockAlterMove = new DealBKDNoBillStockAlterMove(filePath, fileAddress, iuid, username);
		return dealStockAlterMove.deal();
	}
	
	public ArrayList<String> dealNoBillStockAlterMoveByProduct(String filePath, String fileAddress, int iuid, String username)
		throws Exception {
		DealNoBillStockAlterMoveByProduct noBillSam = new DealNoBillStockAlterMoveByProduct(filePath, fileAddress, iuid, username);
		return noBillSam.deal();
	}
	/**
	 * 无单退货
	 * 
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @param username
	 * @return
	 * @throws Exception
	 * @CreateTime Jan 21, 2013 10:55:07 AM
	 */
	public ArrayList<String> dealNoBillOrganWithdraw(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealNoBillOrganWithdraw dealOrganWithdraw = new DealNoBillOrganWithdraw(filePath, fileAddress, iuid, username);
		return dealOrganWithdraw.deal();
	}
	
	public ArrayList<String> dealBKDNoBillOrganWithdraw(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealBKDNoBillOrganWithdraw dealOrganWithdraw = new DealBKDNoBillOrganWithdraw(filePath, fileAddress, iuid, username);
		return dealOrganWithdraw.deal();
	}
	/**
	 * 
	 * 无单退回工厂
	 * Create Time 2014-12-18 上午09:46:43 
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> dealNoBillPlantWithdraw(String filePath, String fileAddress, int iuid, String username)
		throws Exception {
		DealNoBillPlantWithdraw dealPlantWithdraw = new DealNoBillPlantWithdraw(filePath, fileAddress, iuid, username);
		return dealPlantWithdraw.deal();
	}
	/**
	 * 经销商入库签收
	 * 
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @param username
	 * @return
	 * @throws Exception
	 * @author wenping
	 * @CreateTime Jan 16, 2013 2:03:04 PM
	 */
	public ArrayList<String> dealReceiveIncome(String filePath, String fileAddress, int iuid, String username) throws Exception {
		DealUploadIdcodeReceiveIncome dealReceiveIncome = new DealUploadIdcodeReceiveIncome(filePath, fileAddress, iuid, username);
		return dealReceiveIncome.deal();
	}
	/**
	 * 经销商转仓
	 * 
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @param username
	 * @return
	 * @throws Exception
	 * @author wenping
	 * @CreateTime Jan 21, 2013 10:18:10 AM
	 */
	public ArrayList<String> dealNoBillStockMove(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealNoBillStockMove dealStockMove = new DealNoBillStockMove(filePath, fileAddress, iuid, username);
		return dealStockMove.deal();
	}
	
	/***
	 * 以下为有单
	 * @param billosrt
	 * @param filePath
	 * @param fileAddress
	 * @param iuid
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> threadDealCode(int billosrt, String filePath, String fileAddress, int iuid) throws Exception
	{
		
		DealUploadIdcode deal = null;
		switch (billosrt)
		{

			case 0:
				deal = new DealUploadIdcodePurchaseIncome(filePath, fileAddress, iuid);
				break;

			case 1:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;

			case 2:
				deal = new DealUploadIdcodeStockCheck(filePath, fileAddress, iuid);
				break;

			case 3:
				deal = new DealUploadIdcodeProductIncome(filePath, fileAddress, iuid);
				break;

			case 4:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;

			case 5:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;
			case 16:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;
			case 8:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;
			case 20:
				deal = new DealUploadIdcodeTakeTicket(filePath, fileAddress, iuid);
				break;

			case 6:
				deal = new DealUploadIdcodeSupplySaleMove(filePath, fileAddress, iuid);
				break;

			case 7:
				deal = new DealUploadIdcodeProductInterconvert(filePath, fileAddress, iuid);
				break;
			case 9:
				deal = new DealUploadIdcodeOrganTradesP(filePath, fileAddress, iuid);
				break;

			case 10:
				deal = new DealUploadIdcodeOrganTradesT(filePath, fileAddress, iuid);
				break;

			case 11:
				deal = new DealUploadIdcodeOtherShipmentBill(filePath, fileAddress, iuid);
				break;

			case 12:
				deal = new DealUploadIdcodeOtherIncome(filePath, fileAddress, iuid);
				break;

			case 13:
				deal = new DealUploadIdcodePurchaseTrades(filePath, fileAddress, iuid);
				break;

			case 14:
				deal = new DealUploadIdcodeWithdraw(filePath, fileAddress, iuid);
				break;

			case 15:
				deal = new DealUploadIdcodeSaleTrades(filePath, fileAddress, iuid);
				break;

//			case 16:
//				deal = new DealUploadIdcodeDrawShipmentBill(filePath, fileAddress, iuid);
//				break;
		}
		return deal.run();

	}
	public String  dealStockMoveIdcode(String filePath, String fileAddress, int iuid) throws Exception{
		DealUploadIdcodeTakeTicketAuto deal = new DealUploadIdcodeTakeTicketAuto(filePath, fileAddress, iuid);
		return deal.run();
	}
	
	/**
	 * 以下为手工数据上传
	 */
	
	/**
	 * 无单一级经销商出库
	 * 
	 */
	public ArrayList<String> dealNoBillStockAlterMoveByFail(String filePath, String fileAddress, int iuid, String username)
			throws Exception {
		DealNoBillStockAlterMoveByFail dealfail = new DealNoBillStockAlterMoveByFail(filePath, fileAddress, iuid, username);
		return dealfail.deal();
	}
	/**
	 * 经销商入库签收
	 */
	public ArrayList<String> dealReceiveIncomeByFail(String filePath, String fileAddress, int iuid, String username) throws Exception {
		DealUploadIdcodeReceiveIncomeByFail dealfail = new DealUploadIdcodeReceiveIncomeByFail(filePath, fileAddress, iuid, username);
		return dealfail.deal();
	}
	
	/**
	 * 经销商[不在系统中]入库
	 * 
	 */
	
	public ArrayList<String> dealReceiveIncomeByNotExist(String filePath, String fileAddress, int iuid, String username) throws Exception {
		DealUploadIdcodeReceiveIncomeByNotExist dealnotexist = new DealUploadIdcodeReceiveIncomeByNotExist(filePath, fileAddress, iuid, username);
		return dealnotexist.deal();
	}
	
	
	/**
	 * 经销商[不在系统中]出库
	 * 
	 */
	
	public ArrayList<String> dealNoBillStockAlterMoveByNotExist(String filePath, String fileAddress, int iuid, String username) throws Exception {
		DealNoBillStockAlterMoveByNotExist dealnotexist = new DealNoBillStockAlterMoveByNotExist(filePath, fileAddress, iuid, username);
		return dealnotexist.deal();
	}
	
	
	/**
	 * SAP码上传
	 * @param sapUploadLog 
	 */
	public StringBuffer dealSapCodeUpload(File file, UploadSAPLog sapUploadLog) throws Exception {
		DealSapCodeUpload sapCodeUpload = new DealSapCodeUpload(file, sapUploadLog);
		return sapCodeUpload.deal();
	}
}
