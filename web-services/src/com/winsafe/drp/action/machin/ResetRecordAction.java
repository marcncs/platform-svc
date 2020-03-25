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
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-10-14 上午10:19:43 www.winsafe.cn
 */
public class ResetRecordAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String warehousId = "";
			AppOrgan ao = new AppOrgan();

			AppWarehouse aw = new AppWarehouse();
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Integer id = Integer.valueOf(request.getParameter("ID"));
			RecordDao appRecord = new RecordDao();
			UploadPrLog uplog = appRecord.getRecordById(id);

			String fileName = uplog.getFilename();
			String firstName = fileName.substring(0, fileName.indexOf("."));
			String[] fileInfo = firstName.split("_");
			//通过机构简称得到机构
			Organ o = ao.getOrganByShortName(fileInfo[0]);
			if(o==null){
				request.setAttribute("result", "databases.del.success");
				
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
					request.setAttribute("result", "databases.del.success");
				}
			}
			System.out.println("rest upload produce thread");
			ThreadReloadUploadProductReport tupr = new ThreadReloadUploadProductReport("D:/bright-is-tomcat/webapps/is/upload/ProduceReport/" +uplog.getFilepath(), uplog.getId(), 10044, warehousId);
			tupr.run();
			
			request.setAttribute("result", "databases.del.success");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}
