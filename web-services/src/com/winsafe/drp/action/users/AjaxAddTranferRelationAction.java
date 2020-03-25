package com.winsafe.drp.action.users;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;

public class AjaxAddTranferRelationAction extends BaseAction{
	private static Logger logger = Logger.getLogger(AjaxAddTranferRelationAction.class);
	private AppMakeConf appmc = new AppMakeConf();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		try {
			//bkr主键
			String bkrid = request.getParameter("bkrid");
			//bkd或者pd主键
			String parentid = request.getParameter("parentid");
			// 进货关系表的主键
			String sid = request.getParameter("sid");
			AppSTransferRelation app = new AppSTransferRelation();
			
			AppOrgan appOrgan = new AppOrgan();
			Organ parentOrgan = appOrgan.getOrganByID(parentid);
			
			if (StringUtil.isEmpty(sid)) {
				//添加
				STransferRelation s = app.queryby2(bkrid, parentid);
				if (s==null) {
					s = new STransferRelation();
					s.setModifieDate(new Date());
					s.setOrganizationId(bkrid);
					s.setOppOrganId(parentid);
					app.addSTransferRelation(s);
				}
				// 增加PD用户(管辖上级机构)的业务往来权限
				if(parentOrgan.getOrganType() == 2 && parentOrgan.getOrganModel() == 1) {
					addOrganVisit(parentid, bkrid);
				}
				
			} else {
				// 修改
				STransferRelation s = app.getSTransferRelationById(sid);
				
				if(!s.getOppOrganId().equals(parentid)) {
					delOrganVisit(s.getOppOrganId(), bkrid);
					// 增加用户(管辖上级机构)的业务往来权限
					if(parentOrgan.getOrganType() == 2 && parentOrgan.getOrganModel() == 1) {
						addOrganVisit(parentid, bkrid);
					}
				}
				s.setModifieDate(new Date());
				s.setOppOrganId(parentid);
				app.updSTransferRelation(s);
			}
			json.put("success", "true");
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.close();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("",e);
			json.put("success", "false");
		}
		return null;
	}
	
	private void delOrganVisit(String parentid,String oid) throws Exception {
		//增加业务往来机构
		aov.delOrganVisit(oid, parentid);
		//增加业务往来仓库
		appWV.delWarehousVisit(oid, parentid);
	}

	/**
	 * 新增业务往来权限
	 * @throws Exception 
	 */
	private void addOrganVisit(String parentid,String oid) throws Exception{
		//增加业务往来机构
		aov.addOrganVisit(oid, parentid);
		//增加业务往来仓库
		appWV.addWarehousVisit(oid, parentid);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
}
