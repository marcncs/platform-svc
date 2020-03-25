package com.winsafe.drp.action.warehouse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;

/**
 * Project:bright->Class:DownloadIdcodeUploadLogToScanner.java
 * <p style="font-size:16px;">Description：采集器下载条码上传日志</p>
 * Create Time Oct 12, 2011 2:30:48 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class DownloadIdcodeUploadLogToScannerAction extends BaseAction
{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String billsort = request.getParameter("billsort");
		String scannerId = request.getParameter("scannerid");
		String type = request.getParameter("type");
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());;
		AppIdcodeUpload aiu = null;
		List<IdcodeUpload> list = null;
		try
		{
			//如果单据类型获取正确，则查询跟单据类型相关的上传信息
			if(null != billsort && !"".equals(billsort))
			{
				aiu = new AppIdcodeUpload();
				list = aiu.queryIdcodeUpload(Integer.valueOf(billsort), 10, scannerId);
				if(null != list && list.size() > 0)
				{
					if("80".equals(type)){
						StringBuffer sb = new StringBuffer();
						for(IdcodeUpload iu : list)
						{
							sb.append(DateUtil.formatDateTime(iu.getMakedate()));
							sb.append(",");
							sb.append(iu.getValinum());
							sb.append(",");
							sb.append(iu.getFailnum());
							sb.append(",");
							sb.append(iu.getFailfilepath());
							sb.append(";");
							bos.write(sb.toString().getBytes("utf-8"));
							bos.flush();
							sb = new StringBuffer();
						}
					}else{
						for(IdcodeUpload iu : list)
						{
							bos.write(StringUtil.fillBack(DateUtil.formatDateTime(iu.getMakedate()), 19, Constants.TXT_FILL_STRING).getBytes("utf-8"));
							bos.write(StringUtil.fillBack(iu.getValinum().toString(), 5, Constants.TXT_FILL_STRING).getBytes("utf-8"));
							bos.write(StringUtil.fillBack(iu.getFailnum().toString(), 5, Constants.TXT_FILL_STRING).getBytes("utf-8"));
							bos.write("\r\n".getBytes("utf-8"));
							bos.flush();
						}
					}
				}
				else
				{
					bos.write("无上传日志信息！".getBytes("utf-8"));
					bos.flush();
					bos.close();
				}
				return null;
			}
			else
			{
				bos.write("要查看上传日志的类型错误！".getBytes("utf-8"));
				bos.flush();
				bos.close();
			}
		}
		catch (Exception e) 
		{
			bos.write("上传日志下载失败！".getBytes("utf-8"));
			bos.flush();
			bos.close();
			e.printStackTrace();
		}
		finally
		{
			if ( bos != null )
			{
				try 
				{
					bos.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
