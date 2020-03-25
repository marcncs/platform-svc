package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.server.DealUploadIdcode;
import com.winsafe.drp.server.DealUploadIdcodeProductIncome;
import com.winsafe.drp.server.DealUploadIdcodeStockAlterMove;
import com.winsafe.drp.server.DealUploadIdcodeTakeTicket;

/**
 * Project:bright->Class:UploadIdcodeTimelyAction.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time Oct 11, 2011 6:09:17 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class UploadIdcodeTimelyAction extends BaseAction
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		DealUploadIdcode dui = null;
		try
		{
			//条码处理返回结果信息
			String msg = null;
 			//获取采集器上传的采集条码
			String uploadidcode = request.getParameter("icode");
			String uploadType = request.getParameter("uploadType");
			if(!StringUtil.isEmpty(uploadidcode))
			{
				//产成品入库
				if(null != uploadType && "7".equals(uploadType))
				{
					dui = new DealUploadIdcodeProductIncome();
				}
				//检货出库
				if(null != uploadType && "1".equals(uploadType))
				{
					dui = new DealUploadIdcodeTakeTicket();
				}
				//订购入库
				if(null != uploadType && "4".equals(uploadType))
				{
					dui = new DealUploadIdcodeStockAlterMove();
				}
				if(null != dui)
				{
					msg = dui.addIdcode(uploadidcode);
					if(null != msg)
					{
						//提示采集器上传错误信息
						writer.write("2:" + msg);
						writer.flush();
						writer.close();
						return null;
					}
				}
			}
			else
			{
				//采集器上传的条码为空，提示采集器上传错误信息
				writer.write("1:采集器上传的条码为空");
				writer.flush();
				writer.close();
				return null;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		writer.write("0:成功");
		writer.flush();
		writer.close();
		return null;
	}
}
