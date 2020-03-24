package com.winsafe.drp.action.sys;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.JProperties;

public class ToUpdWarehouseAction extends Action {
	private AppOrgan appOrgan = new AppOrgan();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");

		try {
			Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);

			// 选中机构是否为PD的标识
			Boolean isPd = null;
			// 当前机构是否为PD的标识
			Boolean nowOrganIsPd = false;
			
			//查看机构的类型
			Organ organ = appOrgan.getOrganByID(request.getParameter("OID"));
			if(organ != null && organ.getOrganType() != null && organ.getOrganModel() != null 
				&&	organ.getOrganType() == 2 && organ.getOrganModel() == 1){
				isPd = true;
			}
			
			//查看当前机构类型
			Organ organNow = appOrgan.getOrganByID(UserManager.getUser(request).getMakeorganid());
			if(organNow != null && organNow.getOrganType() != null && organNow.getOrganModel() != null 
				&&	organNow.getOrganType() == 2 && organNow.getOrganModel() == 1){
				nowOrganIsPd = true;
			}
			
			//如果该机构为PD,根据配置文件判断是否允许新增仓库
			if (organ.getId().equals(organNow.getId()) && nowOrganIsPd && !sysPro.getProperty("enAbleUpdateWarehouseForPD").equals("1")) {
				request.setAttribute("result", "PD不能修改仓库，修改失败");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			AppWarehouse aw = new AppWarehouse();
			Warehouse w = aw.getWarehouseByID(id);

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> olist = appO.getOlinkmanByCid(request
					.getParameter("OID"));
			CountryAreaService aca = new CountryAreaService();
			List list = aca.getProvinceObj();
			request.setAttribute("olist", olist);
			request.setAttribute("wf", w);
			request.setAttribute("cals", list);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// HibernateUtil.closeSession();
		}
		return mapping.findForward("updDept");
	}

}
