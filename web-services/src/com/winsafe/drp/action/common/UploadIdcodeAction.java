package com.winsafe.drp.action.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.ScannerUser;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.DealUploadIdcode;
import com.winsafe.drp.server.DealUploadIdcodeDrawShipmentBill;
import com.winsafe.drp.server.DealUploadIdcodeOrganTradesP;
import com.winsafe.drp.server.DealUploadIdcodeOrganTradesT;
import com.winsafe.drp.server.DealUploadIdcodeOrganWithdraw;
import com.winsafe.drp.server.DealUploadIdcodeOtherIncome;
import com.winsafe.drp.server.DealUploadIdcodeOtherShipmentBill;
import com.winsafe.drp.server.DealUploadIdcodeProductIncome;
import com.winsafe.drp.server.DealUploadIdcodeProductInterconvert;
import com.winsafe.drp.server.DealUploadIdcodePurchaseIncome;
import com.winsafe.drp.server.DealUploadIdcodePurchaseTrades;
import com.winsafe.drp.server.DealUploadIdcodeSaleTrades;
import com.winsafe.drp.server.DealUploadIdcodeStockAlterMove;
import com.winsafe.drp.server.DealUploadIdcodeStockCheck;
import com.winsafe.drp.server.DealUploadIdcodeStockMove;
import com.winsafe.drp.server.DealUploadIdcodeSupplySaleMove;
import com.winsafe.drp.server.DealUploadIdcodeTakeTicket;
import com.winsafe.drp.server.DealUploadIdcodeTakeTicketAuto;
import com.winsafe.drp.server.DealUploadIdcodeWithdraw;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UploadIdcodeAction extends BaseAction
{

	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UsersBean users = UserManager.getUser(request);
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String username = request.getParameter("username");
		ArrayList<String> billErrorList;
		try
		{
			//将ActionForm强制转换成IdcodeUploadForm（该对象中包含一个FormFile类型的属性，即条码文件）
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			//文件扩展名	
			String extName = null;
			//文件名
			String filerealname = "";
			//备份上传文件路径
			String fileAddress = "";
			//备份上传文件路径2
			String fileAddressBak = "";
			String realPath = request.getSession().getServletContext().getRealPath("/");
			//IdcodeUpload的ID
			String iuId = "";
			Integer billsort = mf.getBillsort();
			//如果上传文件不为空
			if (idcodefile != null && !idcodefile.equals(""))
			{
				
				filerealname = idcodefile.getFileName();
				//如果上传的文件类型为TXT，则设置extName值
				if (idcodefile.getContentType() != null)
				{
//					if (idcodefile.getContentType().indexOf("text") >= 0)
//					{
						extName = ".txt";
//					}
				}
				//如果上传的文件类型正确，则在本地备份
				if (extName != null)
				{
					iuId = MakeCode.getExcIDByRandomTableName("idcode_upload", 0, "");
					InputStream fis = idcodefile.getInputStream();
					String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + filerealname;
					
					String uploadPath = Constants.IDCODE_UPLOAD_PATH[billsort] + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
					String uploadBakPath = "../" + Constants.IDCODE_UPLOAD_PATH[billsort] + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
							+ "/";

					File dirFile = new File(realPath + uploadPath);
					if (!dirFile.exists())
					{
						dirFile.mkdirs();
					}
					File dirFile2 = new File(realPath + uploadBakPath);
					if (!dirFile2.exists())
					{
						dirFile2.mkdirs();
					}
					//保存原文件(2处备份)
					fileAddress = uploadPath + saveFileName;
					fileAddressBak = uploadBakPath + saveFileName;
					OutputStream fos = new FileOutputStream(realPath + fileAddress);
					OutputStream fos2 = new FileOutputStream(realPath + fileAddressBak);
					byte[] buffer = new byte[2048];
					int bytesRead = 0;
					while ((bytesRead = fis.read(buffer, 0, 2048)) != -1)
					{
						fos.write(buffer, 0, bytesRead);
						fos2.write(buffer, 0, bytesRead);
					}
					fos.close();
					fos2.close();
					fis.close();
				}
			}
			if (!iuId.equals(""))
			{	//新增上传日志
				IdcodeUpload iu = new IdcodeUpload();
				if (null != users)
				{
					iu.setMakeorganid(users.getMakeorganid());
					iu.setMakedeptid(users.getMakedeptid());
					iu.setMakeid(users.getUserid());
					
					iu.setUpusername(users.getLoginname());
				}
				if (null != username && !"".equals(username))
				{
					AppUsers appUsers = new AppUsers();
					Users user = appUsers.getUsers(username);
					if(user != null){
						iu.setMakeorganid(user.getMakeorganid());
						iu.setMakedeptid(user.getMakedeptid());
						iu.setMakeid(user.getUserid());
						
						iu.setUpusername(user.getLoginname());
					}
				}
				iu.setId(Integer.valueOf(iuId));
				iu.setFilename(filerealname);
				iu.setBillsort(billsort);
				iu.setIsdeal(0);
				iu.setValinum(0);
				iu.setFailnum(0);
				iu.setFilepath(fileAddress);
				iu.setFailfilepath("");
				iu.setMakedate(DateUtil.getCurrentDate());
				iu.setIsupload(0);
				iu.setPhysicalpath(realPath);
				iu.setIsticket(0); //0为有单1为无单
				AppIdcodeUpload app = new AppIdcodeUpload();
				app.addIdcodeUpload(iu);
				
				HibernateUtil.commitTransaction();
				threadDealCode(mf.getBillsort(), realPath, fileAddress, iu.getId());
				//返回信息
				if (null != username && !"".equals(username)) {
					return output2Scanner("0:文件已上传，正在处理中，条码上传详细信息请查看条码上传日志", writer);
				} else {
					request.setAttribute("result", "文件已上传，正在处理中，条码上传详细信息请查看条码上传 日志");
					return mapping.findForward("uploadmsg");
				}
				
				/*//如果是转仓
				if(mf.getBillsort()==17){
					//转仓——>无单转仓
					String MSG = dealStockMoveIdcode(realPath, fileAddress, iu.getId());			
					
					if (null != scannerId && !"".equals(scannerId))
					{
						return output2Scanner("0:" + MSG , writer);
					}
					else
					{
						request.setAttribute("result", MSG);
						return mapping.findForward("uploadmsg");
					}
				}else{

				billErrorList = threadDealCode(mf.getBillsort(), realPath, fileAddress, iu.getId());

				//批量的只提示上传成功
				if (billErrorList != null && !billErrorList.isEmpty() && billErrorList.size() == 1)
				{
						if (null != scannerId && !"".equals(scannerId))
						{
							return output2Scanner("0:" + billErrorList.get(billErrorList.size() - 1), writer);
						}
						else
						{
							request.setAttribute("result", "databases.input.success");
							return mapping.findForward("success");
						}
				}
				else
				{
						if (null != scannerId && !"".equals(scannerId))
						{
							return output2Scanner("0:" + billErrorList.get(billErrorList.size() - 1), writer);
						}
						else
						{
//							request.setAttribute("els", billErrorList.subList(0, billErrorList.size() - 1));
//							request.setAttribute("billsort", mf.getBillsort());
//							return mapping.findForward("error");
							request.setAttribute("result", billErrorList.get(billErrorList.size() - 1)+",详细信息请查看条码上传日志");
							return mapping.findForward("uploadmsg");
						}
				}
				
			 }*/
				
		}
			else
			{
				if (null != username && !"".equals(username))
				{
					return output2Scanner("0:上传文件失败", writer);
				}
				else
				{
					request.setAttribute("result", "上传文件失败，请重试");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if (null != username && !"".equals(username))
			{
				return output2Scanner("0:上传文件失败", writer);
			}
			else
			{
				request.setAttribute("result", "上传文件失败，请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
		}

	}

	/**
	 * 输出信息到采集器 Create Time: Oct 12, 2011 5:54:28 PM
	 * @param billErrorList
	 * @param writer
	 * @return
	 * @author dufazuo
	 */
	private ActionForward output2Scanner(String msg, PrintWriter writer)
	{
		writer.write(msg);
		writer.flush();
		return null;
	}

	private ArrayList<String> threadDealCode(int billosrt, String filePath, String fileAddress, int iuid) throws Exception
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
				deal = new DealUploadIdcodeStockAlterMove(filePath, fileAddress, iuid);
				break;

			case 5:
				deal = new DealUploadIdcodeStockMove(filePath, fileAddress, iuid);
				break;

			case 6:
				deal = new DealUploadIdcodeSupplySaleMove(filePath, fileAddress, iuid);
				break;

			case 7:
				deal = new DealUploadIdcodeProductInterconvert(filePath, fileAddress, iuid);
				break;

			case 8:
				deal = new DealUploadIdcodeOrganWithdraw(filePath, fileAddress, iuid);
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

			case 16:
				deal = new DealUploadIdcodeDrawShipmentBill(filePath, fileAddress, iuid);
				break;
		}
		//deal.start(); 
		//修改成单线程系统
		return deal.run();

	}
	private String  dealStockMoveIdcode(String filePath, String fileAddress, int iuid) throws Exception{
		DealUploadIdcodeTakeTicketAuto deal = new DealUploadIdcodeTakeTicketAuto(filePath, fileAddress, iuid);
		return deal.run();
	}
}
