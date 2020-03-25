package com.winsafe.drp.action.machin;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-10-14 上午10:19:43 www.winsafe.cn
 */
public class ReloadRecordAction{

	public void ReloadRecorStart() throws Exception{

		//生产数据上传dao
		try {
			String warehousId = "";
			//获取上传文件名称
			
			AppOrgan ao = new AppOrgan();

			AppWarehouse aw = new AppWarehouse();
			
			//首先读取生成报表为“处理中的数据”
			//时间为当天时间的前一天
			AppUploadProduceReport appUploadProduceReport = new AppUploadProduceReport();
			String tempDate = DateUtil.formatDate(DateUtil.addDay2Date(-1, DateUtil.getCurrentDate()), "yyyy-MM-dd");
			List<UploadPrLog> uplList = appUploadProduceReport.getUploadPrLogWithIsDeal(tempDate);
			//循环文件插入数据
			if(uplList!=null && !uplList.isEmpty()){
				for(UploadPrLog uplog : uplList){
					String fileName = uplog.getFilename();
					String firstName = fileName.substring(0, fileName.indexOf("."));
					String[] fileInfo = firstName.split("_");
					//通过机构简称得到机构
					Organ o = ao.getOrganByShortName(fileInfo[0]);
					if(o==null){
						continue;
						
					}else{//机构存在
						//通过organID查找warehouse
						List<Warehouse> ows = aw.getWarehouseListByOID(o.getId());
						//通过仓库简称查找warehouse
						Warehouse w =aw.getWarehouseByShortName(fileInfo[1]);
						boolean flag=false;
						for(Warehouse wh : ows){
							if(wh.equals(w)){						
								warehousId=w.getId();
								flag=true;
								break;
							}
						}
						if(!flag){
							continue;
						}
					}
					
					ThreadReloadUploadProductReport tupr = new ThreadReloadUploadProductReport("D:/bright-is-tomcat/webapps/is/upload/ProduceReport/" +uplog.getFilepath(), uplog.getId(), 10044, warehousId);
					tupr.run();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
