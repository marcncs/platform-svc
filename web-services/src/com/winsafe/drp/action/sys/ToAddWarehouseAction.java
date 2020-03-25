package com.winsafe.drp.action.sys;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.healthmarketscience.jackcess.complex.ComplexValue.Id;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.Internation;

public class ToAddWarehouseAction extends BaseAction {

	private static Logger logger = Logger.getLogger(ToAddWarehouseAction.class);
	
	private AppOrgan appOrgan = new AppOrgan();
	
	private CountryAreaService aca = new CountryAreaService();
	
	private AppOlinkMan appO = new AppOlinkMan();
	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			initdata(request);
			String useflagselect = Internation.getSelectTagByKeyAll("UseSign",
					request, "useflag", false, null);
			String warehousepropertyselect = Internation.getSelectTagByKeyAll(
					"WarehouseProperty", request, "warehouseproperty", false,
					null);
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
			if (organ.getId().equals(organNow.getId()) && nowOrganIsPd && !sysPro.getProperty("enAbleCreateWarehouseForPD").equals("1")) {
				request.setAttribute("result", "PD不能新增自己仓库，新增失败");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			List list = aca.getProvinceObj();
			List<Olinkman> olist = appO.getOlinkmanByCid(request.getParameter("OID"));
			
			request.setAttribute("useflagselect", useflagselect);
			request.setAttribute("warehousepropertyselect",
					warehousepropertyselect);
			request.setAttribute("oid", request.getParameter("OID"));
			request.setAttribute("cals", list);
			request.setAttribute("olist", olist);
			request.setAttribute("isPd", isPd);

			return mapping.findForward("toadd");
		} catch (Exception e) {
			logger.error("",e);
		}
		return new ActionForward(mapping.getInput());
	}
}
