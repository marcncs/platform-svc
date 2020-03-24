package com.winsafe.drp.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ScannerUser;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.StringUtil;

/**
 * Project:bright->Class:DownloadOrganAction.java
 * <p style="font-size:16px;">
 * Description：
 * </p>
 * Create Time Oct 8, 2011 4:30:25 PM
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class DownloadWarehouseAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		// 采集器编号
		String scanid = request.getParameter("scannerid");
		String type = request.getParameter("type");
		AppOrgan appOrgan = new AppOrgan();
		AppWarehouse appWarehouse = new AppWarehouse();
		AppUserVisit visit = new AppUserVisit();
		BufferedOutputStream bos = null;
		try
		{
			ScannerUser scanner = appOrgan.getScannerUserScanner(scanid);
			//判断采集器是否存在
			if (scanner != null)
			{
				String warehouseStr = "";
				List<Warehouse> warehouseList=null;
				if(StringUtil.isEmpty(type)){

					UserVisit userVisit = visit.getUserVisitByUserID(Integer.parseInt(scanner.getUserid()));
					String organvisit = userVisit.getVisitorgan();
					String[] orvi = organvisit.split(",");
					for (int i = 0; i < orvi.length; i++)
					{
	
						//只查询属性是“产成品入库”的
						warehouseList = appWarehouse.getWarehouseListByOIDWProperty(orvi[i], 3);
						if (warehouseList != null && warehouseList.size() > 0)
						{
							for (Warehouse warehouse : warehouseList)
							{
								if(!warehouseStr.contains(warehouse.getId()))
								{
									warehouseStr += ";" + warehouse.getId() + "," + warehouse.getWarehousename();
								}
							}
						}
					}
				}else if("productIncome".equalsIgnoreCase(type)){
					AppRuleUserWH aruw = new AppRuleUserWH();
					//获取管辖仓库的产成品入库仓库
					warehouseList = aruw.queryRuleUserWh(scanner.getUserid(),3);
					if (warehouseList != null && warehouseList.size() > 0)
					{
						for (Warehouse warehouse : warehouseList)
						{
							if(!warehouseStr.contains(warehouse.getId()))
							{
								warehouseStr += ";" + warehouse.getId() + "," + warehouse.getWarehousename();
							}
						}
					}
					
				}else if("stockMove".equalsIgnoreCase(type)){
					warehouseList = appWarehouse.queryStockMoveWarehouse(scanner.getUserid());
					if (warehouseList != null && warehouseList.size() > 0)
					{
						for (Warehouse warehouse : warehouseList)
						{
							if(!warehouseStr.contains(warehouse.getId()))
							{
								warehouseStr += ";" + warehouse.getId() + "," + warehouse.getWarehousename();
							}
						}
					}
				}


				if(!"".equals(warehouseStr))
				{
					bos = new BufferedOutputStream(response.getOutputStream());
					bos.write(warehouseStr.substring(1).getBytes("utf-8"));
					bos.write("\r\n".getBytes());
					bos.flush();
					bos.close();
				}
				else
				{
					bos = new BufferedOutputStream(response.getOutputStream());
					bos.write("没有对应该采集器编号的仓库信息！".getBytes("utf-8"));
					bos.write("\r\n".getBytes());
					bos.flush();
					bos.close();
				}
			}
			else
			{
				bos = new BufferedOutputStream(response.getOutputStream());
				bos.write("采集器编号获取不到！".getBytes("utf-8"));
				bos.write("\r\n".getBytes());
				bos.flush();
				bos.close();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bos != null)
			{
				try
				{
					bos.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 根据长度补空格(&nbsp)
	 * @param protoStr
	 * @param length
	 * @return
	 */
	private String fixSpace(String protoStr, int length)
	{
		String regex = "";
		for (int i = 0; i < length; i++)
		{
			regex += "^";
		}
		String total = (protoStr + regex).substring(0, length);
		// 替换成空格
		String returnVal = total.replace("^", "&nbsp;");
		return returnVal;
	}
}
